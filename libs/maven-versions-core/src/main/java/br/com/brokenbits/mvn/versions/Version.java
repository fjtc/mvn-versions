package br.com.brokenbits.mvn.versions;

/**
 * This class is the base class for all Version classes.
 * 
 * @author fjtc
 * @since 2014.07.21
 */
public class Version {
	
	/**
	 * Compares two versions. The default implementation performs a lexicographic
	 * comparison between the string representation (toString()) of both instances.
	 * 
	 * @param v The other version to be compared.
	 * @return 0 if both versions are the same, a positive value if this is newer
	 * than v or a negative value if v is newer than this.  
	 */
	public int compareTo(Version v) {
		return this.toString().compareToIgnoreCase(v.toString());
	}
}
