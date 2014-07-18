package br.com.brokenbits.mvn.versions;


public class VersionParser {
	
	private boolean strict;
	
	public VersionParser(boolean strict) {
		this.strict = strict;
	}
	
	public VersionInfo parse(String s) {
		VersionInfo v;
		String parts[];
		
		v = new VersionInfo();
		parts = s.split("\\-", 2);
		if (parts.length > 1) {
			if (strict) {
				// Try as x.y.z
				parseMajorMinorRevision(parts[0], v, 3);
			} else {
				// Try as x.y.z.b
				parseMajorMinorRevision(parts[0], v, 4);
			}
		} else {
			// Try as x.y.z-qualifier-b

			// Process x.y.z
			parseMajorMinorRevision(parts[0], v, 3);
			
			// Process qualifier-build
			parseQualifierBuild(parts[1], v);
		}
		
		return v;
	}
	
	private void parseMajorMinorRevision(String s, VersionInfo v, int maxParts) {
		String parts[];

		// Check for empty string
		if (s.isEmpty()) {
			throw new IllegalArgumentException("The version is");
		}
		
		// Split on points
		parts = s.split("\\.");

		// Check the maximum parts
		if (parts.length > maxParts) {
			throw new IllegalArgumentException("Invalid version. Too much parts.");
		}
		
		// Missing part
		if (parts.length == 1) {
			throw new IllegalArgumentException("Missing minor.");
		}

		// Major
		try {
			v.setMajor(Integer.parseInt(parts[0]));
		}catch (NumberFormatException e) {
			throw new IllegalArgumentException("Major is not a valid number.");
		}

		// Minor
		try {
			v.setMinor(Integer.parseInt(parts[1]));
		}catch (NumberFormatException e) {
			throw new IllegalArgumentException("Minor is not a valid number.");
		}

		// Revision
		if (parts.length > 2) {
			try {
				v.setRevision(Integer.parseInt(parts[2]));
			}catch (NumberFormatException e) {
				throw new IllegalArgumentException("Revision is not a valid number.");
			}
		}
		
		// Build
		if (parts.length > 3) {
			try {
				v.setBuild(Integer.parseInt(parts[3]));
			}catch (NumberFormatException e) {
				throw new IllegalArgumentException("Build is not a valid number.");
			}
		}
	}
	
	private void parseQualifierBuild(String s, VersionInfo v) {
		String parts[];
		int build;
		String qualifier;
		
		parts = s.split("\\-", 2);
		// Process part 1
		try {
			build = Integer.parseInt(parts[0]);
			qualifier = null;
		} catch (NumberFormatException e) {
			qualifier = parts[0];
			build = -1;
		}

		if (parts.length == 1) {
			// Build or qualifier
			if (qualifier != null) {
				v.setQualifier(qualifier);
			} else {
				v.setBuild(build);
			}			
		} else {
			// qualifier and build
			if (qualifier == null) {
				throw new IllegalArgumentException("Invalid qualifier.");
			}
			v.setQualifier(qualifier);
			
			try {
				build = Integer.parseInt(parts[1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid build number.");				
			}
			v.setBuild(build);
		}
	}
}
