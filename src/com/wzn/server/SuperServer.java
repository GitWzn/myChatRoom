package com.wzn.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//超级服务端   用来开启服务器
public class SuperServer {
	public static List<ServerThread> servers = new ArrayList<>();

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// 创建服务端
			serverSocket = new ServerSocket(8899);
			System.out.println("服务端创建成功！！");
			while (true) {
				// 创建与客户端的连接 客户端连接上了就往下执行
				Socket socket = serverSocket.accept();
				// 创建服务线程去执行客户信息的中转 将连接的客户后的服务线程添加到list集合中
				ServerThread server = new ServerThread(socket);
				servers.add(server);
				server.start();   //开启线程
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
