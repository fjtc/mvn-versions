package br.com.brokenbits.mvn.versions;

public class Artifact {
	
	private String groupID;
	private String artifactID;
	private Version version;
	
	public Artifact(){
	}
	
	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getArtifactID() {
		return artifactID;
	}

	public void setArtifactID(String artifactID) {
		this.artifactID = artifactID;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public String getGroupArtifact(){
		return this.groupID + ":" + this.artifactID;
	}
	
	public String getGroupArtifactVersion(){
		return getGroupArtifact() + ":" + this.version.toString();
	}
}
