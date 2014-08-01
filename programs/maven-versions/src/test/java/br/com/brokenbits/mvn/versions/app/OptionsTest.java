package br.com.brokenbits.mvn.versions.app;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Test;

public class OptionsTest {

	@Test
	public void testHelp() throws Exception {
		StringWriter sw;
		String m;
		
		sw = new StringWriter();
		Options.printHelp(new PrintWriter(sw));
		sw.close();
		m = sw.toString();
		assertFalse(m.isEmpty());
		System.out.println(m);
	}

	
	@Test
	public void testEmpty() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{});
		assertTrue(o.showHelp());
		assertEquals(0, o.getFileList().size());
		assertEquals(VersionPart.NONE, o.getUpdatePart());
		assertEquals(0, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}	
	
	@Test
	public void testVersion1() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-h"});
		assertTrue(o.showHelp());
		assertEquals(0, o.getFileList().size());
		assertEquals(VersionPart.NONE, o.getUpdatePart());
		assertEquals(0, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}
	
	
	@Test
	public void testVersion2() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-help"});
		assertTrue(o.showHelp());
		assertEquals(0, o.getFileList().size());
		assertEquals(VersionPart.NONE, o.getUpdatePart());
		assertEquals(0, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}
	
	@Test
	public void testUpdateDeps() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.NONE, o.getUpdatePart());
		assertEquals(0, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}
	
	@Test
	public void testBuid1() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-build", "help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.BUILD, o.getUpdatePart());
		assertEquals(1, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}
	
	@Test
	public void testBuid2() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-buildBy", "2", "help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.BUILD, o.getUpdatePart());
		assertEquals(2, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}

	@Test
	public void testRevision1() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-revision", "help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.REVISION, o.getUpdatePart());
		assertEquals(1, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}
	
	@Test
	public void testRevision2() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-revisionBy", "2", "help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.REVISION, o.getUpdatePart());
		assertEquals(2, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}			

	@Test
	public void testMinor1() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-minor", "help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.MINOR, o.getUpdatePart());
		assertEquals(1, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}
	
	@Test
	public void testMinor2() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-minorBy", "2", "help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.MINOR, o.getUpdatePart());
		assertEquals(2, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}
	
	@Test
	public void testMajor1() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-major", "help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.MAJOR, o.getUpdatePart());
		assertEquals(1, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}
	
	@Test
	public void testMajor2() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-majorBy", "2", "help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.MAJOR, o.getUpdatePart());
		assertEquals(2, o.getInc());
		assertNull(o.getQualifier());
		assertFalse(o.removeQualifier());
	}
	
	@Test
	public void testRelease() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-release", "help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.NONE, o.getUpdatePart());
		assertEquals(0, o.getInc());
		assertNull(o.getQualifier());
		assertTrue(o.removeQualifier());
	}	
		
	@Test
	public void testQualifier() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-qualifier", "q", "help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.NONE, o.getUpdatePart());
		assertEquals(0, o.getInc());
		assertEquals("q", o.getQualifier());
		assertFalse(o.removeQualifier());
	}	
	
	@Test
	public void testSnapshot() throws Exception {
		Options o;
		
		o = Options.parse(new String[]{"-snapshot", "help"});
		assertFalse(o.showHelp());
		assertEquals(1, o.getFileList().size());
		assertEquals(VersionPart.NONE, o.getUpdatePart());
		assertEquals(0, o.getInc());
		assertEquals("SNAPSHOT", o.getQualifier());
		assertFalse(o.removeQualifier());
	}	
	
	
	@Test
	public void testQualifiersFailedFail() throws Exception {

		try {
			Options.parse(new String[]{"-snapshot", "-snapshot", "help"});
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		try {
			Options.parse(new String[]{"-snapshot", "-qualifier", "q", "help"});
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		try {
			Options.parse(new String[]{"-snapshot", "-release", "help"});
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testMissing() throws Exception {

		try {
			Options.parse(new String[]{"-qualifier"});
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}	
}
