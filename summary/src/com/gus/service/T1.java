package com.gus.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T1 extends HttpServlet {

	private static final long serialVersionUID = 7115994007263852514L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("resource")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ObjectInputStream ois = new ObjectInputStream(request.getInputStream());

		try {
			byte[] b = (byte[]) ois.readObject();

			System.out.println("from client file size:" + b.length);
			File ret = null;

			BufferedOutputStream stream = null;
			ret = new File("d:/from_client.rar");
			FileOutputStream fstream = new FileOutputStream(ret);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
			stream.flush();
			stream.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//return info
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("gid", "1001");
//		map.put("gname", "gooo");
//		ServletOutputStream out = null;
//		out = response.getOutputStream();
//		ObjectOutputStream outObj = new ObjectOutputStream(out);
//
//		outObj.writeObject(map);
//
//		out.close();
//		outObj.close();
		
		FileInputStream fis = new FileInputStream(new File("d:/test.zip"));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int n;
		while ((n = fis.read(b)) != -1) {
			bos.write(b, 0, n);
		}
		ObjectOutputStream  out =  new ObjectOutputStream(response.getOutputStream());
		out.writeObject(bos.toByteArray());
		out.flush();
		out.close();
	}

}
