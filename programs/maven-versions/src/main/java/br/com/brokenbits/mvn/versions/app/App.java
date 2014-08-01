package br.com.brokenbits.mvn.versions.app;

import java.io.PrintWriter;

import br.com.brokenbits.jopts.ArgumentParserException;

public class App {

	
	private Options opts;
	
	private void showHelp() throws ArgumentParserException {
		PrintWriter writer;
		
		System.out.println("Usage: [<options>] <pom file 1> [<pom file 2>...<pom file n>]");
		writer = new PrintWriter(System.out);
		Options.printHelp(writer);
		writer.flush();
	}
	
	public int run(String[] args) throws Exception {

		// Parse the options
		try {
			opts = Options.parse(args);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return 1;			
		}
		
		// Help
		if (opts.showHelp()) {
			showHelp();
			return 1;
		}

		
		return 0;		
	}
	

	public static void main(String[] args) {
		App app = new App();
		
		try {
			System.exit(app.run(args));
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
}
