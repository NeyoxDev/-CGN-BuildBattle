package fr.neyox.bb.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFiles {

	public static void copyFolder(File src, File dest) throws IOException {
		System.out.println("src: " + src.getAbsolutePath() +" , dest: " + dest.getAbsolutePath());
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			byte[] b = new byte[1204];
			int length;

			while ((length = in.read()) > 0) {
				out.write(b, 0, length);
			}
			in.close();
			out.close();
		}

	}

	public static void delete(File file) {
		if (file.exists()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					delete(files[i]);
				} else {
					files[i].delete();
				}
			}

		}
	}

}
