package br.com.brokenbits.mvn.versions;

import static org.junit.Assert.*;

import org.junit.Test;

public class VersionInfoTest {

	@Test
	public void testVersionInfoIntIntIntStringIntNoQualifier() {
		VersionInfo v;

		// All distinct
		v = new VersionInfo(1, 2, 3, null, 4);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertNull(v.getQualifier());
		
		// All zeroes
		v = new VersionInfo(0, 0, 0, null, 0);
		assertFalse(v.isSnapshot());
		assertEquals(0, v.getMajor());
		assertEquals(0, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(0, v.getBuild());
		assertNull(v.getQualifier());		
	}
	
	public void testVersionInfoIntIntIntStringInt() {
		VersionInfo v;
		
		// All distinct
		v = new VersionInfo(1, 2, 3, "qualifier", 4);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		// All zeroes
		v = new VersionInfo(0, 0, 0, "qualifier", 0);
		assertFalse(v.isSnapshot());
		assertEquals(0, v.getMajor());
		assertEquals(0, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(0, v.getBuild());
		assertEquals("qualifier", v.getQualifier());		
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testVersionInfoIntIntIntStringIntNegativeMajor() {
		VersionInfo v;
		
		v = new VersionInfo(-1, 1, 1, null, 1);
		assertNull(v);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testVersionInfoIntIntIntStringIntNegativeMinor() {
		VersionInfo v;
		
		v = new VersionInfo(1, -1, 1, null, 1);
		assertNull(v);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testVersionInfoIntIntIntStringIntNegativeRevision() {
		VersionInfo v;
		
		v = new VersionInfo(1, 1, -1, null, 1);
		assertNull(v);
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public void testVersionInfoIntIntIntStringIntNegativeBuild() {
		VersionInfo v;
		
		v = new VersionInfo(1, 1, 1, null, -1);
		assertNull(v);
	}	

	@Test
	public void testGetSetMajor() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 4);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v.setMajor(100);
		assertFalse(v.isSnapshot());
		assertEquals(100, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v.setMajor(0);
		assertFalse(v.isSnapshot());
		assertEquals(0, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetMajorNegative() {
		VersionInfo v;
		
		v = new VersionInfo();
		v.setMajor(-1);
	}

	@Test
	public void testGetSetMinor() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 4);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v.setMinor(200);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(200, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v.setMinor(0);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(0, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());		
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetMinorNegative() {
		VersionInfo v;
		
		v = new VersionInfo();
		v.setMinor(-1);
	}

	@Test
	public void testGetSetRevision() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 4);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v.setRevision(300);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(300, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v.setRevision(0);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());		
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetRevision() {
		VersionInfo v;
		
		v = new VersionInfo();
		v.setRevision(-1);
	}

	@Test
	public void testSetIsSnapshot() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 4);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());

		v.setSnapshot(false);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());

		v.setQualifier(null);
		v.setSnapshot(false);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertNull(v.getQualifier());		
		
		v.setSnapshot(true);
		assertTrue(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("SNAPSHOT", v.getQualifier());
		
		v.setSnapshot(false);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertNull(v.getQualifier());
	}

	@Test
	public void testGetSetQualifier() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 4);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());

		v.setQualifier("other");
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("other", v.getQualifier());
		
		v.setQualifier("other1");
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("other1", v.getQualifier());
		
		v.setQualifier(null);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertNull(v.getQualifier());		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetSetQualifierFailEmpty() {
		VersionInfo v;
		
		v = new VersionInfo();
		v.setQualifier("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetSetQualifierFailSpaces() {
		VersionInfo v;
		
		v = new VersionInfo();
		v.setQualifier(" ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetSetQualifierFailWithSpacesLeft() {
		VersionInfo v;
		
		v = new VersionInfo();
		v.setQualifier(" qualifier");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetSetQualifierFailWithSpacesRight() {
		VersionInfo v;
		
		v = new VersionInfo();
		v.setQualifier("qualifier ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetSetQualifierFailWithSpacesMiddle() {
		VersionInfo v;
		
		v = new VersionInfo();
		v.setQualifier("qualifie r");
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetSetQualifierFailWithMinus() {
		VersionInfo v;
		
		v = new VersionInfo();
		v.setQualifier("qualifie-r");
	}	

	@Test
	public void testGetSetBuild() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 4);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(4, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v.setBuild(400);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(400, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v.setBuild(0);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(0, v.getBuild());
		assertEquals("qualifier", v.getQualifier());	
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetBuild() {
		VersionInfo v;
		
		v = new VersionInfo();
		v.setBuild(-1);	
	}

	@Test
	public void testCompareTo() {
		VersionInfo v1;
		VersionInfo v2;
		VersionString v3;
		
		// Equal
		v1 = new VersionInfo(1, 2, 3, null, 4);
		v2 = new VersionInfo(1, 2, 3, null, 4);
		assertTrue(v1.compareTo(v2) == 0);
		
		v1 = new VersionInfo(1, 2, 3, "null", 4);
		v2 = new VersionInfo(1, 2, 3, "null", 4);
		assertTrue(v1.compareTo(v2) == 0);
		
		v1 = new VersionInfo(1, 2, 3, "Null", 4);
		v2 = new VersionInfo(1, 2, 3, "null", 4);
		assertTrue(v1.compareTo(v2) == 0);			
		
		// Build
		v1 = new VersionInfo(1, 2, 3, null, 4);
		v2 = new VersionInfo(1, 2, 3, null, 0);
		assertTrue(v1.compareTo(v2) > 0);
		
		v1 = new VersionInfo(1, 2, 3, "null", 4);
		v2 = new VersionInfo(1, 2, 3, "null", 0);
		assertTrue(v1.compareTo(v2) > 0);

		v1 = new VersionInfo(1, 2, 3, null, 0);
		v2 = new VersionInfo(1, 2, 3, null, 4);
		assertTrue(v1.compareTo(v2) < 0);
		
		v1 = new VersionInfo(1, 2, 3, "null", 0);
		v2 = new VersionInfo(1, 2, 3, "null", 4);
		assertTrue(v1.compareTo(v2) < 0);
		
		// Revision
		v1 = new VersionInfo(1, 2, 3, null, 4);
		v2 = new VersionInfo(1, 2, 0, null, 4);
		assertTrue(v1.compareTo(v2) > 0);
		
		v1 = new VersionInfo(1, 2, 3, "null", 4);
		v2 = new VersionInfo(1, 2, 0, "null", 4);
		assertTrue(v1.compareTo(v2) > 0);

		v1 = new VersionInfo(1, 2, 0, null, 4);
		v2 = new VersionInfo(1, 2, 3, null, 4);
		assertTrue(v1.compareTo(v2) < 0);
		
		v1 = new VersionInfo(1, 2, 0, "null", 4);
		v2 = new VersionInfo(1, 2, 3, "null", 4);
		assertTrue(v1.compareTo(v2) < 0);
		
		// Minor
		v1 = new VersionInfo(1, 2, 3, null, 4);
		v2 = new VersionInfo(1, 0, 3, null, 4);
		assertTrue(v1.compareTo(v2) > 0);
		
		v1 = new VersionInfo(1, 2, 3, "null", 4);
		v2 = new VersionInfo(1, 0, 3, "null", 4);
		assertTrue(v1.compareTo(v2) > 0);

		v1 = new VersionInfo(1, 0, 3, null, 4);
		v2 = new VersionInfo(1, 2, 3, null, 4);
		assertTrue(v1.compareTo(v2) < 0);
		
		v1 = new VersionInfo(1, 0, 3, "null", 4);
		v2 = new VersionInfo(1, 2, 3, "null", 4);
		assertTrue(v1.compareTo(v2) < 0);				
		
		// Major
		v1 = new VersionInfo(1, 2, 3, null, 4);
		v2 = new VersionInfo(0, 2, 3, null, 4);
		assertTrue(v1.compareTo(v2) > 0);
		
		v1 = new VersionInfo(1, 2, 3, "null", 4);
		v2 = new VersionInfo(0, 2, 3, "null", 4);
		assertTrue(v1.compareTo(v2) > 0);

		v1 = new VersionInfo(0, 2, 3, null, 4);
		v2 = new VersionInfo(1, 2, 3, null, 4);
		assertTrue(v1.compareTo(v2) < 0);
		
		v1 = new VersionInfo(0, 2, 3, "null", 4);
		v2 = new VersionInfo(1, 2, 3, "null", 4);
		assertTrue(v1.compareTo(v2) < 0);				
		
		// Qualifiers
		v1 = new VersionInfo(1, 2, 3, null, 4);
		v2 = new VersionInfo(1, 2, 3, "A", 4);
		assertTrue(v1.compareTo(v2) > 0);
		
		v1 = new VersionInfo(1, 2, 3, "A", 4);
		v2 = new VersionInfo(1, 2, 3, null, 4);
		assertTrue(v1.compareTo(v2) < 0);
		
		v1 = new VersionInfo(1, 2, 3, "B", 4);
		v2 = new VersionInfo(1, 2, 3, "A", 4);
		assertTrue(v1.compareTo(v2) > 0);
		
		v1 = new VersionInfo(1, 2, 3, "A", 4);
		v2 = new VersionInfo(1, 2, 3, "B", 4);
		assertTrue(v1.compareTo(v2) < 0);				
		
		// Snapshot - Always newer than any other qualifier except none.
		v1 = new VersionInfo(1, 2, 3, "SNAPSHOT", 4);
		v2 = new VersionInfo(1, 2, 3, "SNAPSHOT", 4);
		assertTrue(v1.compareTo(v2) == 0);
		
		v1 = new VersionInfo(1, 2, 3, null, 4);
		v2 = new VersionInfo(1, 2, 3, "SNAPSHOT", 4);
		assertTrue(v1.compareTo(v2) > 0);

		v1 = new VersionInfo(1, 2, 3, "SNAPSHOT", 4);
		v2 = new VersionInfo(1, 2, 3, null, 4);
		assertTrue(v1.compareTo(v2) < 0);
		
		v1 = new VersionInfo(1, 2, 3, "Z", 4);
		v2 = new VersionInfo(1, 2, 3, "SNAPSHOT", 4);
		assertTrue(v1.compareTo(v2) < 0);

		v1 = new VersionInfo(1, 2, 3, "SNAPSHOT", 4);
		v2 = new VersionInfo(1, 2, 3, "Z", 4);
		assertTrue(v1.compareTo(v2) > 0);
		
		// VersionString
		v1 = new VersionInfo(1, 2, 3, "SNAPSHOT", 4);
		v3 = new VersionString("1.2.3-SNAPSHOT-004");
		assertTrue(v1.compareTo(v3) == 0);
		
		v1 = new VersionInfo(1, 2, 3, "SNAPSHOT", 4);
		v3 = new VersionString("1.2.3.4.5");
		assertTrue(v1.compareTo(v3) < 0);
		
		v1 = new VersionInfo(1, 2, 3, null, 0);
		v3 = new VersionString("1.2.2");
		assertTrue(v1.compareTo(v3) > 0);
	}

	@Test
	public void testToString() {
		VersionInfo v;
		
		// With qualifier
		v = new VersionInfo(1, 2, 3, "qualifier", 4);
		assertEquals("1.2.3-qualifier-004", v.toString());
		
		v = new VersionInfo(1, 2, 0, "qualifier", 4);
		assertEquals("1.2-qualifier-004", v.toString());	
		
		v = new VersionInfo(1, 2, 3, "qualifier", 0);
		assertEquals("1.2.3-qualifier", v.toString());
		
		v = new VersionInfo(1, 2, 0, "qualifier", 0);
		assertEquals("1.2-qualifier", v.toString());
		
		v = new VersionInfo(0, 0, 0, "qualifier", 0);
		assertEquals("0.0-qualifier", v.toString());
		
		// No qualifier
		v = new VersionInfo(1, 2, 3, null, 4);
		assertEquals("1.2.3-004", v.toString());
		
		v = new VersionInfo(1, 2, 0, null, 4);
		assertEquals("1.2-004", v.toString());	
		
		v = new VersionInfo(1, 2, 3, null, 0);
		assertEquals("1.2.3", v.toString());
		
		v = new VersionInfo(1, 2, 0, null, 0);
		assertEquals("1.2", v.toString());				
		
		v = new VersionInfo(0, 0, 0, null, 0);
		assertEquals("0.0", v.toString());
	}
	
	@Test
	public void testUpdateMajor() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 5);
		v.updateMajor(1);
		assertFalse(v.isSnapshot());
		assertEquals(2, v.getMajor());
		assertEquals(0, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(0, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v = new VersionInfo(1, 2, 3, null, 5);
		v.updateMajor(2);
		assertFalse(v.isSnapshot());
		assertEquals(3, v.getMajor());
		assertEquals(0, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(0, v.getBuild());
		assertNull(v.getQualifier());		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateMajorFail() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 5);
		v.updateMajor(-1);
	}

	@Test
	public void testUpdateMinor() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 5);
		v.updateMinor(1);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(3, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(0, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v = new VersionInfo(1, 2, 3, null, 5);
		v.updateMinor(2);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(4, v.getMinor());
		assertEquals(0, v.getRevision());
		assertEquals(0, v.getBuild());
		assertNull(v.getQualifier());		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateMinorFail() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 5);
		v.updateMinor(-1);
	}
	
	@Test
	public void testUpdateRevision() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 5);
		v.updateRevision(1);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(4, v.getRevision());
		assertEquals(0, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v = new VersionInfo(1, 2, 3, null, 5);
		v.updateRevision(2);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(5, v.getRevision());
		assertEquals(0, v.getBuild());
		assertNull(v.getQualifier());		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateRevisionFail() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 5);
		v.updateRevision(-1);
	}
	
	@Test
	public void testUpdateBuild() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 5);
		v.updateBuild(1);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(6, v.getBuild());
		assertEquals("qualifier", v.getQualifier());
		
		v = new VersionInfo(1, 2, 3, null, 5);
		v.updateBuild(2);
		assertFalse(v.isSnapshot());
		assertEquals(1, v.getMajor());
		assertEquals(2, v.getMinor());
		assertEquals(3, v.getRevision());
		assertEquals(7, v.getBuild());
		assertNull(v.getQualifier());		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateBuildFail() {
		VersionInfo v;
		
		v = new VersionInfo(1, 2, 3, "qualifier", 5);
		v.updateBuild(-1);
	}	
}
