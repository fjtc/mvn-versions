package br.com.brokenbits.mvn.versions.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileUtilsTest {
	
	private Random random = new Random();
	
	private void writeFile(File f, byte buff[]) throws IOException {
		FileOutputStream out;
		
		out = new FileOutputStream(f);
		try {
			out.write(buff);
		} finally {
			out.close();
		}		
	}
	
	private byte[] readFile(File f) throws IOException {
		FileInputStream in;
		FileChannel inChannel;
		ByteBuffer buff;
		byte result[];
		
		in = new FileInputStream(f);
		try {
			inChannel = in.getChannel();
			result = new byte[(int)inChannel.size()];
			buff = ByteBuffer.wrap(result);
			inChannel.read(buff);
			return result;
		} finally {
			in.close();
		}
	}
	
	private File createTempFile() throws IOException {
		File f;
		
		f = File.createTempFile("test", ".tmp");
		f.deleteOnExit();
		return f;
	}
	
	@Test
	public void testCopy() throws IOException {
		byte src[];
		byte dst[];
		File srcFile;
		File dstFile;
		
		src = new byte[2048];
		random.nextBytes(src);
		
		srcFile = createTempFile();
		dstFile = createTempFile();
		
		// Write the source file
		writeFile(srcFile, src);

		// Copy
		FileUtils.copy(srcFile, dstFile);
		
		// Read the destination file
		dst = readFile(dstFile);
		
		// Compare the result
		assertArrayEquals(src, dst);
	}

}
