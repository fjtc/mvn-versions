package br.com.brokenbits.mvn.versions.app;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import br.com.brokenbits.jopts.Argument;
import br.com.brokenbits.jopts.ArgumentHelp;
import br.com.brokenbits.jopts.ArgumentParameterType;
import br.com.brokenbits.jopts.ArgumentParser;
import br.com.brokenbits.jopts.ArgumentParserException;
import br.com.brokenbits.jopts.Arguments;
import br.com.brokenbits.jopts.DuplicatedArgumentException;
import br.com.brokenbits.jopts.InvalidParameterTypeException;
import br.com.brokenbits.jopts.MissingArgumentParameterException;

public class Options {

	private ArrayList<String> fileList = new ArrayList<String>();
	
	private ArrayList<String> roFileList = new ArrayList<String>();
	
	private String qualifier;
	
	private boolean removeQualifier = false;
	
	private int inc = 0;
	
	private VersionPart updatePart = VersionPart.NONE;

	private boolean help;
	
	@Argument(description="POM files. Can be used more than once.")
	public void addFile(String file) {
		fileList.add(file);
	}
	
	@Argument(name="-ro", description="Read-only POM file. Can be used more than once.")
	public void addROFile(String file) {
		roFileList.add(file);
	}	
	
	@Arguments(arguments={
			@Argument(name="-h", description="Show help."),
			@Argument(name="-help", description="Show help.")
	})
	public void setHelp(){
		help = true;
	}
	
	@Argument(name="-qualifier", description="Sets the qualifier to a given value.", uniqueKey="qualifier")			
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
	
	@Argument(name="-snapshot", description="Sets the qualifiler to SNAPSHOT.", uniqueKey="qualifier")			
	public void setSnapshot() {
		this.qualifier = "SNAPSHOT";
	}
	
	@Argument(name="-release", description="Removes all qualifiers (prepare release).", uniqueKey="qualifier")			
	public void setRelease() {
		removeQualifier = true;
	}
	
	@Argument(name="-build", description="Updates the build by adding 1.", uniqueKey="update")			
	public void setUpdateBuild() {
		setUpdateBuild(1);
	}

	@Argument(name="-buildBy", description="Updates the build a given value.", uniqueKey="update")			
	public void setUpdateBuild(long v) {
		this.updatePart = VersionPart.BUILD;
		this.inc = (int)v;
	}
	
	@Argument(name="-revision", description="Updates the revision by 1.", uniqueKey="update")			
	public void setUpdateRevision() {
		setUpdateRevision(1);
	}

	@Argument(name="-revisionBy", description="Updates the revision by a given value.", uniqueKey="update")			
	public void setUpdateRevision(long v) {
		this.updatePart = VersionPart.REVISION;
		this.inc = (int)v;
	}
	
	@Argument(name="-minor", description="Updates the minor by 1.", uniqueKey="update")			
	public void setUpdateMinor() {
		setUpdateMinor(1);
	}

	@Argument(name="-minorBy", description="Updates the minor by a given value.", uniqueKey="update")			
	public void setUpdateMinor(long v) {
		this.updatePart = VersionPart.MINOR;
		this.inc = (int)v;
	}
	
	@Argument(name="-major", description="Updates the major by 1.", uniqueKey="update")			
	public void setUpdateMajor() {
		setUpdateMajor(1);
	}

	@Argument(name="-majorBy", description="Updates the major by a given value.", uniqueKey="update")			
	public void setUpdateMajor(long v) {
		this.updatePart = VersionPart.MAJOR;
		this.inc = (int)v;
	}

	public List<String> getFileList() {
		return fileList;
	}
	
	public List<String> getROFileList() {
		return roFileList;
	}

	public String getQualifier() {
		return qualifier;
	}

	public boolean removeQualifier() {
		return removeQualifier;
	}

	public int getInc() {
		return inc;
	}

	public VersionPart getUpdatePart() {
		return updatePart;
	}
	
	public boolean showHelp(){
		return this.help || (this.fileList.size() == 0);
	}
	
	
	private static ArgumentParser<Options> parser;
	
	private static ArgumentParser<Options> getParser() throws ArgumentParserException {
		if (parser == null) {
			parser = new ArgumentParser<Options>(Options.class);
		}
		return parser;
	}
		
	public static Options parse(String args[]) throws IllegalArgumentException {
		Options options;
		
		try {
			options = new Options();
			getParser().process(args, options);
			return options;
		} catch (MissingArgumentParameterException e) {
			throw new IllegalArgumentException(String.format("Argument '%1$s' requires a parameter.", e.getName()), e);
		} catch (DuplicatedArgumentException e) {
			throw new IllegalArgumentException(String.format("Cannot use '%1$s' with '%2$s'", e.getName(), e.getDuplicated()), e);
		} catch (InvalidParameterTypeException e) {
			throw new IllegalArgumentException(String.format("Invalid parameter for argument '%1$s'.", e.getName()), e);
		} catch (ArgumentParserException e) {
			throw new IllegalArgumentException(String.format("Unable to handle argument '%1$s'.", e.getName()), e);
		}
	}
	
	public static void printHelp(PrintWriter out) throws ArgumentParserException {
		List<ArgumentHelp> args;
		
		args = getParser().generateHelp(null);
		for (ArgumentHelp h: args) {
			if (h.getName() != null) {
				out.print(h.getName());
				if (h.getType() != ArgumentParameterType.NONE) {
					out.print(" <n>");
				}
				out.print(": ");
				out.print(h.getDescription());
				out.println();
			} else {
				out.print("<files>: ");
				out.println(h.getDescription());
			}
		}
		out.flush();
	}
	
	
}
