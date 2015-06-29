package com.gus.util.threadio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * 客户端与服务端流传输通讯示例
 * 
 * @author gus97
 * 
 */
public class IOStream {

	private static FileInputStream fis;
	private static FileOutputStream fos;

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		HttpURLConnection connection = getConnectByURL("http://127.0.0.1/summary/T1");

		// 目标
		OutputStream out = connection.getOutputStream();
		// 源
		fis = new FileInputStream(new File("d:/test.zip"));
		// 转换
		B2O(fis, out);
		
		// 源
		InputStream in = connection.getInputStream();
		// 目标
		fos = new FileOutputStream("d:/from_server.rar");
		// 转换
		B2O(in, fos);
		System.out.println("download ok");
		
	}

	public static HttpURLConnection getConnectByURL(String url_path)
			throws IOException {
		java.net.URL url = new java.net.URL(url_path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.connect();
		return connection;
	}

	// 输入流写入字节流，字节流写入输出流
	public static void B2O(InputStream in, OutputStream out) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int n;
		while ((n = in.read(b)) != -1) {
			bos.write(b, 0, n);
		}
		out.write(bos.toByteArray());
		out.flush();
		out.close();
		in.close();
	}
}
