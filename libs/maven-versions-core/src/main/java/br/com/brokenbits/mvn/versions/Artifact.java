package br.com.brokenbits.mvn.versions;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.brokenbits.mvn.versions.util.DOMUtil;

public class Artifact {
	
	private String groupID;
	private String artifactID;
	private Version version;
	
	public Artifact(){
	}
	
	public Artifact(Node root) throws IllegalArgumentException {
		Node node;
		
		// Get groupId
		node = DOMUtil.getFirstNodeByName(root, "groupId");
		if (node == null) {
			throw new IllegalArgumentException("groupId not found.");
		}
		this.groupID = node.getTextContent();
		
		// Get artifactId
		node = DOMUtil.getFirstNodeByName(root, "artifactId");
		if (node == null) {
			throw new IllegalArgumentException("artifactId not found.");
		}
		this.artifactID = node.getTextContent();

		// Version
		node = DOMUtil.getFirstNodeByName(root, "version");
		if (node != null) {
			this.version = VersionParser.parse(node.getTextContent(), false);
		}
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
