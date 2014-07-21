package br.com.brokenbits.mvn.versions;

/**
 * 
 * @author fjtc
 * @since 2014.07.21
 */
public class VersionString extends Version {

	private String version;
	
	public VersionString(String version) throws IllegalArgumentException {
		this.setString(version);
	}
	
	public void setString(String version){
		if (version == null) {
			throw new IllegalArgumentException("The version string cannot be null.");
		}
		this.version = version;
	}
	
	public String getString(){
		return this.version;
	}	
	
	@Override
	public String toString() {
		return version;
	}
}
