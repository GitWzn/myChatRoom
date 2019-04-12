package com.wzn.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

import com.wzn.messageUtil.SendMessageUtil;

public class ServerThread extends Thread {
	private Socket socket;
	private String username;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;

	public ServerThread(Socket socket) throws IOException {
		this.socket = socket;
		// 从socket中获取输入流(字节流) 封装成字符流
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
		// 从socket中获取输出流(字节流) 封装成字符流
		bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
	}

	@Override
	public void run() {
		// 获取客户的信息
		try {
			this.username = bufferedReader.readLine();
			System.out.println(username + " 进入聊天室");
			// 给出欢迎提示
			SendMessageUtil.sendMessage("欢迎您登陆本聊天室！", bufferedWriter);
			String tageName = "";
			String tageMessage = "";
			String message = "";
			boolean istageName = false;
			while (true) {
				message = bufferedReader.readLine();     //接收客户端的信息
				if (message.equals("")) { // 客户发送的信息不能为空字符
					SendMessageUtil.sendMessage("系统: 不可以发送空信息,请输入信息!!", this.bufferedWriter);
					continue;
				}
				boolean result = message.matches("^@[\\u4e00-\\u9fa5\\w]{2,10}#.+$");
				if (result) {	// 是否符合私聊的前提 正则表达式
					tageName = message.substring(1, message.indexOf("#"));
					tageMessage = message.substring(message.indexOf("#") + 1);
				}
				// 将接收到的信息遍历客户端发送信息
				for (ServerThread client : SuperServer.servers) {
					if (client == this) { 	// 跳过当前发送信息的客户
						continue;
					} else if (result && tageName.equals(client.username)) { // 私聊
						SendMessageUtil.sendMessage("       时间："
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()) + "\n"
								+ this.username + "对你说: " + tageMessage, client.bufferedWriter);
						istageName = true;
						break;
					} else if (!result) { 	// 群聊
						SendMessageUtil.sendMessage("       时间："
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()) + "\n"
								+ this.username + "说: " + message, client.bufferedWriter);
					}
				}
				if (result && !istageName) { // 符合正则但用户名不存在
					SendMessageUtil.sendMessage("您所要私聊的用户不存在!!", this.bufferedWriter);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
