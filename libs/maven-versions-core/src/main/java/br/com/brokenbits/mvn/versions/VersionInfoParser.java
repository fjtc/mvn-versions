package br.com.brokenbits.mvn.versions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements a maven version parser. This parser can operate in 2 modes,
 * the strict mode and lazy mode.
 * 
 * <p>On strict mode, only versions in the form "x.y[.z][-qualifier][-b]". On lazy mode,
 * only versions in the form "x.y[.z[.b]]" are accepted. 
 * 
 * @author FJTC
 * @since 2014.07.21
 */
public class VersionInfoParser {
	
	/**
	 * A pattern designed to accept versions with the format "x.y[.z[.b]]".
	 */
	private static final Pattern MAJOR_MINOR_REVISION_BUILD_PATTERN = Pattern.compile("^([0-9]+)\\.([0-9]+)(?:\\.([0-9]+))?(?:\\.([0-9]+))?$");
	
	/**
	 * A pattern designed to accept versions with the format "b".
	 */
	private static final Pattern BUILD_PATTERN = Pattern.compile("^([0-9]+)$");
	
	/**
	 * A pattern designed to accept versions with the format "[qualifier][-b]".
	 */
	private static final Pattern QUALIFIER_BUILD_PATTERN = Pattern.compile("^([^\\-]+)?(?:\\-([0-9]+))?$");
	
	/**
	 * A flag that indicates if this parser is strict or lazy.
	 */
	private boolean strict;	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param strict If true, creates a strict parser. Otherwise, creates a lazy one.
	 */
	public VersionInfoParser(boolean strict) {
		this.strict = strict;
	}
	
	/**
	 * Parses a given version string.
	 * 
	 * @param s The version to be parsed.
	 * @return The instance of VersionInfo that contains the version information. 
	 * @throws IllegalArgumentException If the version string is invalid.
	 */
	public VersionInfo parse(String s) throws IllegalArgumentException {
		VersionInfo v;
		String parts[];
		int count;
		
		if (s == null) {
			throw new IllegalArgumentException("The version string cannot be null.");
		}
		
		v = new VersionInfo();
		parts = s.split("\\-", 2);
		
		// Try the first part
		count = parseMajorMinorRevisionBuild(parts[0], v);
		if (count < 0) {
			throw new IllegalArgumentException(String.format("Invalid version string '%1$s'.", s));
		}

		// Validate the result
		if (parts.length == 1) {
			if ((strict) && (count > 3)) {
				throw new IllegalArgumentException(String.format("Invalid version string '%1$s'.", s));
			}
		} else {
			// Try as x.y.z-qualifier-b
			if (count > 3) {
				throw new IllegalArgumentException(String.format("Invalid version string '%1$s'.", s));
			}
			
			// Process qualifier-build
			if (!parseQualifierBuild(parts[1], v)) {
				throw new IllegalArgumentException(String.format("Invalid version string '%1$s'.", s));
			}
		}
		
		return v;
	}
	
	/**
	 * Parses the version string in the format "x.y[.z[.b]]" and sets the major, minor, revision and build
	 * of the VersionInfo instance v.
	 * 
	 * @param s The string to be parsed.
	 * @param v The version to be filled.
	 * @return The number of parts found or -1 if the version string is invalid.
	 */
	private int parseMajorMinorRevisionBuild(String s, VersionInfo v) {
		Matcher m;
		
		
		m = MAJOR_MINOR_REVISION_BUILD_PATTERN.matcher(s);
		if (m.find()) {
			// Major
			v.setMajor(Integer.parseInt(m.group(1)));
			
			// Minor
			v.setMinor(Integer.parseInt(m.group(2)));
			
			// Revision
			if (m.group(3) != null) {
				v.setRevision(Integer.parseInt(m.group(3)));
				
				// Build
				if (m.group(4) != null) {
					v.setBuild(Integer.parseInt(m.group(4)));
					return 4;
				} else {
					return 3;
				}
			} else {
				return 2;
			}
		} else {
			return -1;			
		}
	}
	
	/**
	 * Parses the qualifier-build part in the format "[b]", "[q]" or "[q][-b]". 
	 *  
	 * @param s The string to be parsed.
	 * @param v The version to be filled.
	 * @return True for success of false otherwise.
	 * @exception IllegalArgumentException If the parameters are invalid.
	 */
	private boolean parseQualifierBuild(String s, VersionInfo v) throws IllegalArgumentException{
		Matcher m;
		
		// Try build only
		m = BUILD_PATTERN.matcher(s);
		if (m.find()) {
			// Build only
			v.setBuild(Integer.parseInt(m.group(1)));
			return true;
		} else {
			// Try qualifier-build
			m = QUALIFIER_BUILD_PATTERN.matcher(s);
			if (m.find()) {
				// Qualifier?
				if (m.group(1) != null) {
					v.setQualifier(m.group(1));
					// build?
					if (m.group(2) != null) {
						v.setBuild(Integer.parseInt(m.group(2)));
					}
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
}
