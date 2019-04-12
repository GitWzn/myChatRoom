package com.wzn.messageUtil;

import java.io.BufferedWriter;
import java.io.IOException;

//抽取常用的发送信息的功能进行封装
public class SendMessageUtil {
	public static void sendMessage (String message, BufferedWriter bufferedWriter) throws IOException{
		bufferedWriter.write(message);
		bufferedWriter.newLine();
		bufferedWriter.flush();
	}
}
