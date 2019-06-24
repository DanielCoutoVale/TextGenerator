package org.uppermodel;

import java.io.File;

public class Resource {

	private final File directory;

	public Resource(File directory) {
		this.directory = directory;
	}
	
	public Resource(String directoryName) {
		this(new File(directoryName));
	}

	public final File[] getSystemFiles() {
		return directory.listFiles(file -> file.getName().endsWith("systems"));
	} 
	
	public final File[] getFeatureFiles() {
		return directory.listFiles(file -> file.getName().endsWith("features"));
	}
	
	public final File[] getSpellingFiles() {
		return directory.listFiles(file -> file.getName().endsWith("spellings"));
	}
	
	public final File[] getCallingFiles() {
		return directory.listFiles(file -> file.getName().endsWith("callings"));
	}
	
}
