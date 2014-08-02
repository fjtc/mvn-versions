package br.com.brokenbits.mvn.versions.app;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.com.brokenbits.jopts.ArgumentParserException;
import br.com.brokenbits.mvn.versions.POMFile;

public class App {
	
	public static final int RET_SUCCESS = 0;
	public static final int RET_HELP = 1;
	public static final int RET_WRONG_ARGUMENTS = 2;
	public static final int RET_UNABLE_TO_LOAD_POM = 3;
	
	private Options opts;

	private ArrayList<POMFile> pomFiles = new ArrayList<POMFile>();	
	
	protected String getHelpMessage() throws ArgumentParserException {
		PrintWriter writer;
		StringWriter sWriter;
		
		sWriter = new StringWriter();
		writer = new PrintWriter(sWriter);
		
		writer.printf("%1$s v%2$s - A simple tool to manage versions inside POM files\n", VersionInfo.getFinalName(), VersionInfo.getVersion());
		writer.printf("Copyright (c) 2014 Fabio Jun Takada Chino. All rights reserved.\n\n");
		writer.printf(
				"Usage: java -jar %1$s.jar [<options>] <pom file 1> [<pom file 2>...<pom file n>]\n\n",
				VersionInfo.getFinalName());
		writer.printf("Options:\n");
		Options.printHelp(writer);
		writer.close();

		return sWriter.toString();
	}
	
	protected int showHelp() throws ArgumentParserException {

		System.out.print(getHelpMessage());
		return RET_HELP;
	}
	
	private int parseOptions(String[] args) {
		// Parse the options
		try {
			opts = Options.parse(args);
			return RET_SUCCESS;
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return RET_WRONG_ARGUMENTS;			
		}
	}
	
	private int loadPOMFiles() {
		
		// 
		if (opts.getFileList().size() == 0) {
			System.err.println("Missing POM files.");
			return RET_UNABLE_TO_LOAD_POM;
		}

		for (String f: opts.getFileList()) {
			try {
				POMFile pom = new POMFile(new File(f));
				pomFiles.add(pom);
			} catch (Exception e) {
				System.err.printf("Unable to load the POM file %1$s.", f);
				return RET_UNABLE_TO_LOAD_POM;
			}
		}
		return RET_SUCCESS;
	}
	
	public int run(String[] args) throws Exception {
		int retval;
		
		// Parse the parameters
		retval = parseOptions(args);
		if (retval != RET_SUCCESS) {
			return retval;
		}
		
		// Help
		if (opts.showHelp()) {
			return showHelp();
		}
		
		// Load all POM files
		retval = loadPOMFiles();
		if (retval != RET_SUCCESS) {
			return retval;
		}
		
		
		
		
		
		
		return RET_SUCCESS;		
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
