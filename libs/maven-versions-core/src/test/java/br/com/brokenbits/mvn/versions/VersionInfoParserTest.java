package br.com.brokenbits.mvn.versions;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.brokenbits.mvn.versions.VersionInfoParser;

public class VersionInfoParserTest {
	
	private static final String FAILED_VERSIONS[] = {
		"",
		" ",
		"1",
		"1.2.3.4.5",
		".2.3.4",
		"1..3.4",
		"1.2..4",
		"1.2.3.",
		"a.2.3.4",
		"1.a.3.4",
		"1.2.a.4",
		"1.2.3.a",
		"1.2.3.4-",
		"1.2.3-",
		"1.2-",
		"1-",
		"1.2.3-4-qualifier",
		"1.2.3.4-qualifier",
		"1.2.3.4-qualifier-qualifier",		
	};

	private static final String FAILED_STRICT_VERSIONS[] = {
		"",
		" ",
		"1",
		"1.2.3.4",
		".2.3",
		"1..3",
		"1.2.",		
		"a.2.3",
		"1.a.3",
		"1.2.a",
		"1.2.3.4-",
		"1.2.3-",
		"1.2-",
		"1-",
		"1.2.3-4-qualifier",
		"1.2.3.4-qualifier",
		"1.2.3.4-qualifier-qualifier"
	};	
	
	@Test
	public void testParse() {
		VersionInfoParser p;
		VersionInfo v;
		
		p = new VersionInfoParser(false);
		
		v = p.parse("1.2");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(0, v.getBuild());
		assertNull(v.getQualifier());
		
		v = p.parse("1.2.3");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(0, v.getBuild());
		assertNull(v.getQualifier());		
		
		v = p.parse("1.2.3.4");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertNull(v.getQualifier());
		
		v = p.parse("1.2-4");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(4, v.getBuild());
		assertNull(v.getQualifier());

		v = p.parse("1.2.3-4");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertNull(v.getQualifier());
		
		v = p.parse("1.2-q-4");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("q", v.getQualifier());

		v = p.parse("1.2.3-q-4");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("q", v.getQualifier());
	}
	
	@Test
	public void testParseFailed() {
		VersionInfoParser p;
		VersionInfo v;
		
		p = new VersionInfoParser(false);
		for (String s: FAILED_VERSIONS) {
			try {
				v = p.parse(s);
				fail(String.format("Version '%1$s' should be invalid.", s));
				assertNull(v);
			} catch (IllegalArgumentException e){}
		}
	}

	@Test
	public void testParseStrict() {
		VersionInfoParser p;
		VersionInfo v;
		
		p = new VersionInfoParser(true);
		
		v = p.parse("1.2");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(0, v.getBuild());
		assertNull(v.getQualifier());
		
		v = p.parse("1.2.3");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(0, v.getBuild());
		assertNull(v.getQualifier());
		
		v = p.parse("1.2-4");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(4, v.getBuild());
		assertNull(v.getQualifier());

		v = p.parse("1.2.3-4");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertNull(v.getQualifier());
		
		v = p.parse("1.2-q-4");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("q", v.getQualifier());

		v = p.parse("1.2.3-q-4");
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("q", v.getQualifier());		
	}
	
	@Test
	public void testParseStrictFailed() {
		VersionInfoParser p;
		VersionInfo v;
		
		p = new VersionInfoParser(true);
		for (String s: FAILED_STRICT_VERSIONS) {
			try {
				v = p.parse(s);
				fail(String.format("Version '%1$s' should be invalid.", s));
				assertNull(v);
			} catch (IllegalArgumentException e){}
		}
	}
}
