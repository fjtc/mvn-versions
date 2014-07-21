package br.com.brokenbits.mvn.versions;

public class Version {
	
	public int compareTo(Version v) {
		return this.toString().compareToIgnoreCase(v.toString());
	}
}
