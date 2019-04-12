package com.wzn.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

//客户端启动
public class Client {
	public static void main(String[] args) {
		try {
			//创建客户端  连接服务端   指定IP地址和端口
			Socket socket = new Socket("127.0.0.1", 8899);
			System.out.println("客户端创建成功!!");
			//创建发送信息的线程     开启线程
			new SendThread(socket).start();
			//创建接收信息的线程     开启线程
			new ReceiveThread(socket).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
