package br.com.brokenbits.mvn.versions.app;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppTest {

	@Test
	public void testHelp() throws Exception {
		App a;
		
		a = new App();
		assertEquals(App.RET_HELP, a.run(new String[]{}));
		
		a = new App();
		assertEquals(App.RET_HELP, a.run(new String[]{"-h"}));

		a = new App();
		assertEquals(App.RET_HELP, a.run(new String[]{"-help"}));
		
		a = new App();
		assertEquals(App.RET_HELP, a.run(new String[]{"-help", "--", "pom.xml"}));		
	}

}
