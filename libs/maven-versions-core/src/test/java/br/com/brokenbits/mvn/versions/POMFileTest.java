package br.com.brokenbits.mvn.versions;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.brokenbits.mvn.versions.util.FileUtils;

public class POMFileTest {
	
	private static final File TEST_DIR = new File("tmp");
			
	private static final File SRC_FILE = new File("sample", "pom.xml");
	
	private static final File TEST_FILE = new File(TEST_DIR, "pom.xml");
	
	private void copyOriginalPOM() throws Exception {
		TEST_DIR.mkdirs();
		FileUtils.copy(SRC_FILE, TEST_FILE);
	}

	@Test
	public void testPOMFileString() throws Exception {
		POMFile pom;
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE.toString());
		assertNotNull(pom);
		assertEquals(TEST_FILE, pom.getFile());
	}
	
	@Test
	public void testPOMFileFile() throws Exception {
		POMFile pom;
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE);
		assertNotNull(pom);
		assertEquals(TEST_FILE, pom.getFile());
	}
	
	@Test
	public void testSave() throws Exception {
		POMFile pom;
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE);
		TEST_FILE.delete();
		pom.save();
		assertTrue(TEST_FILE.exists());
	}
	
	@Test
	public void testSaveFile() throws Exception {
		POMFile pom;
		File f;
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE);
		
		f = new File(TEST_DIR, "pom2.xml");
		f.delete();
		
		pom.save(f);
		assertTrue(TEST_FILE.exists());
		assertTrue(f.exists());
	}
	
	@Test
	public void testGetArtifact() throws Exception {
		POMFile pom;
		Artifact a;
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE);
		assertNotNull(pom);
		a = pom.getArtifact();
		assertNotNull(a);
		assertEquals("sample-group", a.getGroupID());
		assertEquals("sample-id", a.getArtifactID());
		assertEquals("0.0.1-SNAPSHOT", a.getVersion().toString());
	}
	
	
	@Test
	public void testSetVersion() throws Exception {
		POMFile pom;
		Artifact a1;
		Artifact a2;
		VersionInfo v;
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE);
		assertNotNull(pom);
		
		a1 = pom.getArtifact();
		assertNotNull(a1);		
		v = (VersionInfo)a1.getVersion();
		v.updateMajor(1);
		
		pom.setVersion(v);
		
		a2 = pom.getArtifact();
		assertNotSame(v, a2.getVersion());
		assertEquals(v, a2.getVersion());
	}	
	
	
	@Test
	public void testListDependency() throws Exception {
		POMFile pom;
		List<Artifact> deps;
		Artifact a;
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE);
		assertNotNull(pom);
		
		deps = pom.listDependencies();
		assertEquals(2, deps.size());
		
		a = deps.get(0);
		assertEquals("dependency1-group", a.getGroupID());
		assertEquals("dependency1-id", a.getArtifactID());
		assertEquals("1.0", a.getVersion().toString());
		
		a = deps.get(1);
		assertEquals("dependency2-group", a.getGroupID());
		assertEquals("dependency2-id", a.getArtifactID());
		assertEquals("2.0", a.getVersion().toString());
	}	
	
	@Test
	public void testUpdateDependency() throws Exception {
		POMFile pom;
		List<Artifact> deps;
		HashMap<String, Artifact> m = new HashMap<String, Artifact>();
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE);
		assertNotNull(pom);
		
		deps = pom.listDependencies();
		
		// Update all versions
		for (Artifact a: deps) {
			((VersionInfo)a.getVersion()).updateMajor(1);
			m.put(a.getGroupArtifact(), a);
		}
		
		// Update dependencies
		pom.updateDependencies(m);
		
		// Check the updates
		deps = pom.listDependencies();
		for (Artifact a: deps) {
			Artifact newDep = m.get(a.getGroupArtifact());
			assertNotSame(newDep.getVersion(), a.getVersion());
			assertEquals(newDep.getVersion(), a.getVersion());
		}
		
		//pom.save();
		//System.out.println(TEST_FILE);
	}		
}
