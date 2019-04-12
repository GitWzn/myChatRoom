package com.wzn.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ReceiveThread extends Thread {
	private Socket socket;
	private BufferedReader bufferedReader;

	public ReceiveThread(Socket socket) throws UnsupportedEncodingException, IOException {
		this.socket = socket;
		//创建输入流
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
	}

	@Override
	public void run() {
		try {
			while (true){
				//接收服务端发送过来的信息
				System.out.println(bufferedReader.readLine());
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
