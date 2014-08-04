package br.com.brokenbits.mvn.versions.app;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import br.com.brokenbits.jopts.ArgumentParserException;
import br.com.brokenbits.mvn.versions.Artifact;
import br.com.brokenbits.mvn.versions.POMFile;
import br.com.brokenbits.mvn.versions.Version;
import br.com.brokenbits.mvn.versions.util.FileUtils;

public class App {
	
	private static final String POM_PROP_RO = "RO";
	
	public static final int RET_SUCCESS = 0;
	public static final int RET_HELP = 1;
	public static final int RET_WRONG_ARGUMENTS = 2;
	public static final int RET_UNABLE_TO_LOAD_POM = 3;
	public static final int RET_FAIL = 4;
	
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
		File file;
		
		// 
		if (opts.getFileList().size() == 0) {
			System.err.println("Missing POM files.");
			return RET_UNABLE_TO_LOAD_POM;
		}

		for (String f: opts.getFileList()) {
			file = new File(FileUtils.fixPathSeparator(f)); 
			try {
				
				POMFile pom = new POMFile(file);
				pomFiles.add(pom);
			} catch (Exception e) {
				System.err.printf("Unable to load the POM file %1$s.", file.getAbsolutePath());
				return RET_UNABLE_TO_LOAD_POM;
			}
		}
		
		for (String f: opts.getROFileList()) {
			file = new File(FileUtils.fixPathSeparator(f)); 
			try {
				POMFile pom = new POMFile(file);
				pom.setProperty(POM_PROP_RO, Boolean.TRUE);
				pomFiles.add(pom);
			} catch (Exception e) {
				System.err.printf("Unable to load the POM file %1$s.", file.getAbsolutePath());
				return RET_UNABLE_TO_LOAD_POM;
			}
		}
		
		return RET_SUCCESS;
	}
	
	private int updateDependencies() {
		HashMap<String, Artifact> depMap = new HashMap<String, Artifact>();
		
		// Scan all files in order to create the current version map
		for (POMFile pom: pomFiles) {
			try {
				Artifact a = pom.getArtifact();
				depMap.put(a.getGroupArtifact(), a);
			} catch (Exception e) {
				System.err.printf("Unable to get the artifact data from '%1$s'.\n", pom.getFile().getAbsolutePath());
				return RET_FAIL;
			}
		}
		
		// Update the files
		for (POMFile pom: pomFiles) {
			try {
				if (pom.getProperty(POM_PROP_RO) == null) {
					pom.updateDependencies(depMap);
				}
			} catch (Exception e) {
				System.err.printf("Unable to update the map for '%1$s'.\n", pom.getFile().getAbsolutePath());
				return RET_FAIL;
			}
		}
		
		return RET_SUCCESS;		
	}
	
	private int updateVersions() {

		// Scan all files in order to create the current version map
		for (POMFile pom: pomFiles) {
			if (pom.getProperty(POM_PROP_RO) == null) {
				try {
					Version v = pom.getArtifact().getVersion();
					if (v instanceof br.com.brokenbits.mvn.versions.VersionInfo) {
						br.com.brokenbits.mvn.versions.VersionInfo vf = (br.com.brokenbits.mvn.versions.VersionInfo)v;
						
						// Version number
						switch (opts.getUpdatePart()) {
						case BUILD:
							vf.updateBuild(opts.getInc());
							break;
						case REVISION:
							vf.updateRevision(opts.getInc());
							break;
						case MINOR:
							vf.updateMinor(opts.getInc());
							break;
						case MAJOR:
							vf.updateMajor(opts.getInc());
							break;
						default:
							break;
						}
						
						// Qualifier
						if (opts.removeQualifier()) {
							vf.setQualifier(null);
						} else {
							if (opts.getQualifier() != null) {
								vf.setQualifier(opts.getQualifier());
							}
						}
						
						pom.setVersion(vf);
					} else {
						System.out.printf("Cannot update the qualifier of the file '%1$s'\n", pom.getFile().getAbsolutePath());
					}
				} catch (Exception e) {
					System.err.printf("Unable to save the file '%1$s'.\n", pom.getFile().getAbsolutePath());
					return RET_FAIL;
				}
			}
		}
		return RET_SUCCESS;		
	}
		
	private int saveAll() {
		
		// Scan all files in order to create the current version map
		for (POMFile pom: pomFiles) {
			if (pom.getProperty(POM_PROP_RO) == null) {
				try {
					FileUtils.copy(pom.getFile(), new File(pom.getFile().getAbsolutePath() + ".old"));
					pom.save();
				} catch (Exception e) {
					System.err.printf("Unable to save the file '%1$s'.\n", pom.getFile().getAbsolutePath());
					return RET_FAIL;
				}
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
		
		// Update version
		retval = updateVersions();
		if (retval != RET_SUCCESS) {
			return retval;
		}

		// Update the dependencies
		retval = updateDependencies();
		if (retval != RET_SUCCESS) {
			return retval;
		}
		
		// Save all
		retval = saveAll();
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
