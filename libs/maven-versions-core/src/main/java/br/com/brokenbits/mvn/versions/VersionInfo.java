package br.com.brokenbits.mvn.versions;

import java.util.regex.Pattern;

/**
 * This class implements a Maven version.
 * 
 * @author fjtc
 * @since 2014.07.15
 */
public class VersionInfo {
	
	/**
	 * This is the special qualifier that defines a SNAPSHOT.
	 */
	public static final String SNAPSHOT = "SNAPSHOT";
	
	private static final Pattern QUALIFIER_PATTERN = Pattern.compile("[\\s\\-]+"); 

	/**
	 * The major.
	 */
	private int major;
	
	/**
	 * The minor.
	 */
	private int minor;
	
	/**
	 * The revision.
	 */
	private int revision;
	
	/**
	 * The qualifier.
	 */
	private String qualifier;

	/**
	 * The build.
	 */
	private int build;
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param major The major value. Cannot be negative.
	 * @param minor The minor value. Cannot be negative.
	 * @param revision The revision value. Cannot be negative.
	 * @param qualifier The qualifier or null if it is not set.
	 * @param build The build value. Cannot be negative.
	 * @throws IllegalArgumentException Case one of the parameters is invalid.
	 */
	public VersionInfo(int major, int minor, int revision, String qualifier, int build) throws IllegalArgumentException {
		this.setMajor(major);
		this.setMinor(minor);
		this.setRevision(revision);
		this.setQualifier(qualifier);
		this.setBuild(build);
	}
	
	/**
	 * Returns the major. 
	 * 
	 * @return The major value.
	 */
	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		if (major < 0) {
			throw new IllegalArgumentException("Major cannot be negative.");
		}
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		if (minor < 0) {
			throw new IllegalArgumentException("Minor cannot be negative.");
		}
		this.minor = minor;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		if (revision < 0) {
			throw new IllegalArgumentException("Revision cannot be negative.");
		}
		this.revision = revision;
	}

	/**
	 * Sets this version to snapshot or not.
	 * 
	 * If v is true, the qualifier will be set to "SNAPSHOT". If v is false and
	 * qualifier is "SNAPSHOT", the qualifier will be removed. If v is false and
	 * the qualifier is not "SNAPSHOT", no changes will be made.
	 * 
	 * @param v If true, sets this version to snapshot otherwise removes the snapshot flag.
	 */
	public void setSnapshot(boolean v){
		
		if (v) {
			this.qualifier = SNAPSHOT;
		} else {
			if (isSnapshot()) {
				this.qualifier = null;
			}
		}
	}
	
	public boolean isSnapshot(){
		if (qualifier == null) {
			return false;
		} else {
			return qualifier.equalsIgnoreCase(SNAPSHOT);
		}		
	}
	
	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		
		if (qualifier != null) {
			qualifier = qualifier.trim();
			if (qualifier.isEmpty()) {
				qualifier = null;
			}
		}
		this.qualifier = qualifier;
	}

	public int getBuild() {
		return build;
	}

	public void setBuild(int build) {
		if (build < 0) {
			throw new IllegalArgumentException("Build cannot be negative.");
		}
		this.build = build;
	}

	/**
	 * Compares this version with another.
	 * 
	 * @param v The other version.
	 * @return 0 if the versions are the same, a positive value if this version is 
	 * newer or a negative value if this version is older.
	 */
	public int compareTo(VersionInfo v) {
		int delta;
		
		// Compare major
		delta = this.major - v.major;
		if (delta != 0) {
			return delta;
		}
		
		// Compare minor
		delta = this.minor - v.minor;
		if (delta != 0) {
			return delta;
		}
		
		// Compare revision
		delta = this.revision - v.revision;
		if (delta != 0) {
			return delta;
		}

		// Compare qualifiers
		delta = compareQualifiers(v);
		if (delta != 0) {
			return delta;
		}
		
		// Compare build
		delta = this.build - v.build;
		if (delta != 0) {
			return delta;
		}
		
		return 0;
	}

	private int compareQualifiers(VersionInfo v) {
		int delta;
		
		if ((qualifier != null) && (v.qualifier != null)) {
			delta = qualifier.compareToIgnoreCase(v.qualifier);
			if (delta != 0) {
				if (this.isSnapshot()) {
					return 1;
				} else if (v.isSnapshot()) {
					return -1;
				} else {
					return delta;
				}
			} else {
				// They have the same qualifier
				return 0;
			}
		} else {
			if (qualifier != null) {
				// This instance has a qualifier.
				return -1;				
			} else if (v.qualifier != null) {
				// The other instance has a qualifier.
				return 1;
			} else {
				// No qualifiers!
				return 0;
			}
		}		
	}

	/**
	 * Converts this version to string.
	 * 
	 * @return The string representation of this version.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(major);
		sb.append('.');
		sb.append(minor);
		if (revision != 0) {
			sb.append('.');
			sb.append(revision);
		}
		if (qualifier != null) {
			sb.append('-');
			sb.append(qualifier);
		}
		if (build != 0) {
			sb.append('-');
			sb.append(String.format("%1$03d", build));
		}
		return sb.toString();
	}	
}
