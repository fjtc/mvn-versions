package br.com.brokenbits.mvn.versions;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.brokenbits.mvn.versions.VersionParser;

public class VersionParserTest {

	@Test
	public void testParse() {
		VersionParser p;
		
		p = new VersionParser(false);
		
		p.parse("1.2");
		
	}

}
