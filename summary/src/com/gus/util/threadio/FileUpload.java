package com.gus.util.threadio;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;

/**
 * 客户端与服务端流传输通讯示例
 * 
 * @author gus97
 * 
 */
public class FileUpload {

	private static FileInputStream fis;

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		HttpURLConnection connection = getConnectByURL("http://127.0.0.1/summary/T1");
		
	
		
		ObjectOutputStream out = new ObjectOutputStream(
				connection.getOutputStream());
		fis = new FileInputStream(new File("d:/test.zip"));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int n;
		while ((n = fis.read(b)) != -1) {
			bos.write(b, 0, n);
		}
		out.writeObject(bos.toByteArray());
		out.flush();
		out.close();

		InputStream in = connection.getInputStream();

		ObjectInputStream ois = new ObjectInputStream(in);
//		Map<String, String> map = (Map<String, String>) ois.readObject();
//		System.out.println(map);
		
		byte[] bc = (byte[]) ois.readObject();

		System.out.println("from server file size:" + bc.length);
		File ret = null;

		BufferedOutputStream stream = null;
		ret = new File("d:/from_server.rar");
		FileOutputStream fstream = new FileOutputStream(ret);
		stream = new BufferedOutputStream(fstream);
		stream.write(bc);
		stream.flush();
		stream.close();

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
}
