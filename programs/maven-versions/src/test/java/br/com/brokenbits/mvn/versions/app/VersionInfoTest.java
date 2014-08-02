package br.com.brokenbits.mvn.versions.app;

import static org.junit.Assert.*;

import org.junit.Test;

public class VersionInfoTest {

	@Test
	public void testGetVersion() {
		String v;
		
		v = VersionInfo.getVersion();
		assertNotNull(v);
	}
	
	@Test
	public void testGetFinalName() {
		String v;
		
		v = VersionInfo.getFinalName();
		assertNotNull(v);
	}	
}
