package br.com.brokenbits.mvn.versions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionParser {
	
	/**
	 * Pattern for the format x.y.z.b with z and b as optional values.
	 */
	private static final Pattern DOT_PATTERN = 
			Pattern.compile("([0-9]+)\\.([0-9]+)(?:\\.([0-9]+))?(?:\\.([0-9]+))?");

	/**
	 * Pattern for the format x.y.z-q-b with z, q and b as optional values.
	 */
	private static final Pattern MVN_PATTERN =
			Pattern.compile("([0-9]+)\\.([0-9]+)(?:\\.([0-9]+))(?:\\-([^\\-]+))?(?:\\-([0-9]+))?");
	
	private boolean strict;
	
	public VersionParser(boolean strict) {
		this.strict = strict;
	}
	
	public VersionInfo parse(String s) {
		Matcher m;
		VersionInfo v = null;
		
		m = MVN_PATTERN.matcher(s);
		if (m.matches()) {

		} else if (!this.strict) {
			// Try lazy pattern x.y.z.b
			m = DOT_PATTERN.matcher(s);
			if (m.matches()) {
				
			}
		}
		
		//if (v == null) {
		//	throw new IllegalArgumentException(String.format("Invalid version string '%1$s'.", s));
		//}
		return v;
	}
}
