package com.g7.wanbao.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperator {
	public FileOperator() {

	}

	public Boolean write(String _path, String _content) {
		try {

			String fpath = _path;

			File file = new File(fpath);

			// If file does not exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(_content);
			bw.close();

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("resource")
	public String read(String _path) {

		BufferedReader br = null;
		String response = null;

		try {

			StringBuffer output = new StringBuffer();
			String fpath = _path;

			br = new BufferedReader(new FileReader(fpath));
			String line = "";
			if ((line = br.readLine()) != null)	output.append(line);
			while ((line = br.readLine()) != null)	output.append("\n" + line);
			response = output.toString();

		} catch (IOException e) {
			e.printStackTrace();
			return null;

		}
		return response;

	}
}