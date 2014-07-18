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
	
	private static final Pattern NUMERIC_PATTERN = Pattern.compile("[0-9]+");
	
	private static final Pattern QUALIFIER_PATTERN = Pattern.compile("[^\\-\\s]+"); 

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
	 * Creates a new instance of this class. The resulting version will have no qualifier
	 * and the values of major, minor, revision and build will be set to 0.
	 */
	public VersionInfo(){
		this(0, 0, 0, null, 0);
	}
	
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

	/**
	 * Sets the major value.
	 * 
	 * @param major The new value. It cannot be negative.
	 */
	public void setMajor(int major) {
		if (major < 0) {
			throw new IllegalArgumentException("Major cannot be negative.");
		}
		this.major = major;
	}

	/**
	 * Returns the minor. 
	 * 
	 * @return The minor value.
	 */	
	public int getMinor() {
		return minor;
	}

	/**
	 * Sets the minor value.
	 * 
	 * @param minor The new value. It cannot be negative.
	 */
	public void setMinor(int minor) {
		if (minor < 0) {
			throw new IllegalArgumentException("Minor cannot be negative.");
		}
		this.minor = minor;
	}

	/**
	 * Returns the revision. 
	 * 
	 * @return The revision value.
	 */	
	public int getRevision() {
		return revision;
	}

	/**
	 * Sets the revision value.
	 * 
	 * @param revision The new value. It cannot be negative.
	 */
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
	
	/**
	 * Verifies if this version is a snapshot or not.
	 * 
	 * @return true if it is a snapshot or false otherwise.
	 */
	public boolean isSnapshot(){
		if (qualifier == null) {
			return false;
		} else {
			return qualifier.equalsIgnoreCase(SNAPSHOT);
		}		
	}
	
	/**
	 * Returns the qualifier.
	 * 
	 * @return The qualifier string or null if it is not set.
	 */
	public String getQualifier() {
		return qualifier;
	}

	/**
	 * Sets the qualifier. It is important to notice that the qualifier
	 * must be a non empty string with at least one letter. This string cannot contain
	 * '-' 
	 * 
	 * no blanks and no '-' characters.
	 * 
	 * @param qualifier The qualifier or null if this version has no qualifiers.
	 */
	public void setQualifier(String qualifier) {
		
		if (qualifier != null) {
			if (NUMERIC_PATTERN.matcher(qualifier).matches()) {
				throw new IllegalArgumentException(String.format("The qualifier cannot be a numeric value."));
			}			
			if (!QUALIFIER_PATTERN.matcher(qualifier).matches()) {
				throw new IllegalArgumentException(String.format("Invalid qualifier '%1$s'.", qualifier));
			}
		}
		this.qualifier = qualifier;
	}

	/**
	 * Returns the build. 
	 * 
	 * @return The build value.
	 */		
	public int getBuild() {
		return build;
	}

	/**
	 * Sets the build value.
	 * 
	 * @param build  The new value. It cannot be negative.
	 */	
	public void setBuild(int build) {
		if (build < 0) {
			throw new IllegalArgumentException("Build cannot be negative.");
		}
		this.build = build;
	}

	/**
	 * Compares this version with another. The comparison follows the following criteria:
	 * 
	 *   - Compare majors, the greater one is newer;
	 *   - Compare minors, the greater one is newer;
	 *   - Compare revisions, the greater one is newer;
	 *   - Compare the qualifiers. The newer version is defined according to those rules:
	 *     - No qualifier;
	 *     - SNAPSHOT;
	 *     - Lexicographic comparison;
	 *   - Compare build, the greater one is newer;
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

	/**
	 * Compare the qualifiers according to the rules specified by Maven.
	 * 
	 * @param v The other version.
	 * @return 0 if the versions are the same, a positive value if this version is 
	 * newer or a negative value if this version is older.
	 */
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
	 * Converts this version to string. The version will have the following format:
	 * 
	 *   - major.minor[.revision][-qualifier][-build]
	 * 
	 * The revision may be ommited if it is zero, the qualifier is ommited when it is not
	 * present and the build may be ommited if it is zero.
	 * 
	 * @return The string representation of this version.
	 * @note In order to avoid problems with a known issue with maven, the build value will be
	 * written with 3 decimal places. 
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
	
	
	/**
	 * Updates the major value. It sets minor, revision and build to 0.
	 * 
	 * @param v The version to be incremented.
	 * @param increase The amount to be increased. It must be greater than zero.
	 */
	public void updateMajor(int inc) {
		
		if (inc <= 0) {
			throw new IllegalArgumentException("The increment must be greater than 0.");
		}
		major += inc;
		minor = 0;
		revision = 0;
		build = 0;
	}
	
	/**
	 * Updates the minor value. It sets revision and build to 0.
	 * 
	 * @param v The version to be incremented.
	 * @param increase The amount to be increased. It must be greater than zero.
	 */
	public void updateMinor(int inc) {
		
		if (inc <= 0) {
			throw new IllegalArgumentException("The increment must be greater than 0.");
		}
		minor += inc;
		revision = 0;
		build = 0;
	}
	
	/**
	 * Updates the revision value. It sets build to 0.
	 * 
	 * @param v The version to be incremented.
	 * @param increase The amount to be increased. It must be greater than zero.
	 */
	public void updateRevision(int inc) {
		
		if (inc <= 0) {
			throw new IllegalArgumentException("The increment must be greater than 0.");
		}
		revision += inc;
		build = 0;
	}	
	
	/**
	 * Updates the revision value. It sets build to 0.
	 * 
	 * @param v The version to be incremented.
	 * @param increase The amount to be increased. It must be greater than zero.
	 */
	public void updateBuild(int inc) {
		
		if (inc <= 0) {
			throw new IllegalArgumentException("The increment must be greater than 0.");
		}
		build += inc;
	}	
}
