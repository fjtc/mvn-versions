package br.com.brokenbits.mvn.versions;

/**
 * This class implements
 * 
 * @author fjtc
 * @since 2014.07.21
 */
public class VersionParser {
	
	/**
	 * Parses the version string.
	 * 
	 * @param s The version string to be parsed.
	 * @param strict If true, sets the parser to strict mode, otherwise sets the parser to non-strict mode.
	 * @return A instance of VersionInfo if the version follows the designated structure or an instance of
	 * VersionString if it is a generic version string.
	 * @throws IllegalArgumentException If the version string could not be parsed.
	 */
	public static Version parse(String s, boolean strict) throws IllegalArgumentException {
		VersionInfoParser vip;
		
		if (s == null) {
			throw new IllegalArgumentException("The version string cannot be null.");
		}
		vip = new VersionInfoParser(strict);
		try {
			return vip.parse(s);
		} catch (IllegalArgumentException e) {
			return new VersionString(s);
		}
	}
}
