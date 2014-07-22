package br.com.brokenbits.mvn.versions.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {
	
	public static final void copy(File src, File dst) throws IOException {
		FileInputStream in;
		FileOutputStream out;
		FileChannel inChannel;
		FileChannel outChannel;
		
		in = new FileInputStream(src);
		try {
			out = new FileOutputStream(dst);
			try {
				inChannel = in.getChannel();
				outChannel = out.getChannel();
				outChannel.transferFrom(inChannel, 0, inChannel.size());
			} finally {
				out.close();
			}			
		} finally {
			in.close();
		}
	}
}
