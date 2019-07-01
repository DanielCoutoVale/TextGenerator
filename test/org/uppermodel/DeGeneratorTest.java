package org.uppermodel;

import java.io.IOException;

import org.junit.Test;

public class DeGeneratorTest {
	
	private Generator generator;
	
	@Test
	public final void test1() throws IOException {
		generator = new Generator("lang/german");
		generator.setLogging(false);
		generateVerb("base:sagen");
		generateVerb("base:haben");
		generateVerb("base:schlafen");
		generateVerb("base:studieren");
		generateVerb("base:tun");
	}

	private void generateVerb(String lex) {
		System.out.println();
		generator.generateWord(lex, "verb", "ge-core");
		generator.generateWord(lex, "verb", "bare-core", "seam1");
		generator.generateWord(lex, "verb", "bare-core", "seam2");
		generator.generateWord(lex, "verb", "bare-core", "seam3");
		generator.generateWord(lex, "verb", "bare-core", "seam4");
		generator.generateWord(lex, "verb", "bare-core", "seam5");
		generator.generateWord(lex, "verb", "te-core", "seam1");
		generator.generateWord(lex, "verb", "te-core", "seam2");
		generator.generateWord(lex, "verb", "te-core", "seam3");
		generator.generateWord(lex, "verb", "te-core", "seam4");
		generator.generateWord(lex, "verb", "te-core", "seam5");
	}
	
	@Test
	public final void test2() throws IOException {
		generator = new Generator("lang/german");
		generator.setLogging(false);
		generateEventGroup("Singing");
		generateEventGroup("Drinking");
		generateEventGroup("Saying");
		generateEventGroup("Doing");
	}

	private void generateEventGroup(String lowerClass) {
		System.out.println();
		generator.generateGroup("Event", lowerClass, "non-past", "seam1");
		generator.generateGroup("Event", lowerClass, "non-past", "seam2");
		generator.generateGroup("Event", lowerClass, "non-past", "seam3");
		generator.generateGroup("Event", lowerClass, "non-past", "seam4");
		generator.generateGroup("Event", lowerClass, "non-past", "seam5");
		generator.generateGroup("Event", lowerClass, "seam1");
		generator.generateGroup("Event", lowerClass, "seam2");
		generator.generateGroup("Event", lowerClass, "seam3");
		generator.generateGroup("Event", lowerClass, "seam4");
		generator.generateGroup("Event", lowerClass, "seam5");
	}
	
}
