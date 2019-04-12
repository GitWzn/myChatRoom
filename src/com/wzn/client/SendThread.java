package com.wzn.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.wzn.messageUtil.SendMessageUtil;

public class SendThread extends Thread {
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;

	public SendThread(Socket socket) throws IOException {
		this.socket = socket;
		//创建输入流    变成键盘输入
		this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		//创建输出流    将信息发送给服务端
		this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
	}

	@Override
	public void run() {
		try {
			System.out.println("请输入您的用户名：");
			while (true){
				//键盘输入信息      将用户名发送给服务端
				SendMessageUtil.sendMessage(bufferedReader.readLine(), bufferedWriter);
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
