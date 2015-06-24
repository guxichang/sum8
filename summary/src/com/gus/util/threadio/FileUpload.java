package com.gus.util.threadio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

public class FileUpload {

	private static FileInputStream fis;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		HttpURLConnection connection = getConnectByURL("http://127.0.0.1:8080/summary/T1");
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
		Map<String, String> map = (Map<String, String>) ois.readObject();
		System.out.println(map);

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
