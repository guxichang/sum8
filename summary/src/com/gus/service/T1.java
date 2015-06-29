package com.gus.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gus.util.threadio.FileUpload;

public class T1 extends HttpServlet {

	private static final long serialVersionUID = 7115994007263852514L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//源
		InputStream in = request.getInputStream();
		//目标
		FileOutputStream fos = new FileOutputStream(new File("d:/from_client.rar"));
		//转换
		FileUpload.B2O(in, fos);
		System.out.println("upload ok");

		//源
		FileInputStream fis = new FileInputStream(new File("d:/test.zip"));
		//目标
		OutputStream out =  response.getOutputStream();
		//转换
		FileUpload.B2O(fis, out);
	}
}
