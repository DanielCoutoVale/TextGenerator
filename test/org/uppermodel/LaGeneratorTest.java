package org.uppermodel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.uppermodel.Generator;
import org.uppermodel.theory.LinearStructure;
import org.uppermodel.theory.Unit;

public class LaGeneratorTest {
	
	private void generateThing(String tax) throws IOException {
		generator = new Generator("lang/latin");
		generator.setLogging(false);
		Unit speech = Generator.newSpeech();
		Unit meaning = new Unit();
		meaning.features.add("Thing");
		meaning.features.add(tax);
		Map<Unit, Unit> spellingMap = new HashMap<>();
		LinearStructure wording = (LinearStructure) this.generator.generate(spellingMap, speech, meaning);
		System.out.println(wording.toString(spellingMap));
	}
	
	private Generator generator;
	
	@Test
	public void test1() throws IOException {
		generateThing("Slave");
		generateThing("SlaveOwner");
	}

//	@Test
//	public final void test4() throws IOException {
//		generator = new Generator("lang/latin");
//		generator.setLogging(false);
//		System.out.println();
//		generator.generate("I ate a pizza.");
//		generator.generate("I ate a [food].", "pizza");
//		generator.generate("I ate a [food].", "hotdog");
//		generator.generate("I ate [number] (pizza).", "one");
//		generator.generate("I ate [number] {food}.", "one", "hotdog");
//		generator.generate("I ate [number] (pizza).", "two");
//		generator.generate("I ate [number] {food}.", "two", "hotdog");
//	}

	@Test
	public final void test2() throws IOException {
		generator = new Generator("lang/latin");
		generator.setLogging(false);
		System.out.println();
		generator.generateWord("base:dīcere", "verb", "ō-aspect-core", "seam1");
		generator.generateWord("base:dīcere", "verb", "ō-aspect-core", "seam2");
		generator.generateWord("base:dīcere", "verb", "ō-aspect-core", "seam3");
		generator.generateWord("base:dīcere", "verb", "ō-aspect-core", "seam4");
		generator.generateWord("base:dīcere", "verb", "ō-aspect-core", "seam5");
		generator.generateWord("base:dīcere", "verb", "ō-aspect-core", "seam6");
		generator.generateWord("base:dīcere", "verb", "bā-branch-core", "seam1");
		generator.generateWord("base:dīcere", "verb", "bā-branch-core", "seam2");
		generator.generateWord("base:dīcere", "verb", "bā-branch-core", "seam3");
		generator.generateWord("base:dīcere", "verb", "bā-branch-core", "seam4");
		generator.generateWord("base:dīcere", "verb", "bā-branch-core", "seam5");
		generator.generateWord("base:dīcere", "verb", "bā-branch-core", "seam6");
		generator.generateWord("base:dīcere", "verb", "b-branch-core", "seam1");
		generator.generateWord("base:dīcere", "verb", "b-branch-core", "seam2");
		generator.generateWord("base:dīcere", "verb", "b-branch-core", "seam3");
		generator.generateWord("base:dīcere", "verb", "b-branch-core", "seam4");
		generator.generateWord("base:dīcere", "verb", "b-branch-core", "seam5");
		generator.generateWord("base:dīcere", "verb", "b-branch-core", "seam6");
		generator.generateWord("base:dīcere", "verb", "ē-branch-core", "seam1");
		generator.generateWord("base:dīcere", "verb", "ē-branch-core", "seam2");
		generator.generateWord("base:dīcere", "verb", "ē-branch-core", "seam3");
		generator.generateWord("base:dīcere", "verb", "ē-branch-core", "seam4");
		generator.generateWord("base:dīcere", "verb", "ē-branch-core", "seam5");
		generator.generateWord("base:dīcere", "verb", "ē-branch-core", "seam6");
		generator.generateWord("base:dīcere", "verb", "ī-aspect-core", "seam1");
		generator.generateWord("base:dīcere", "verb", "ī-aspect-core", "seam2");
		generator.generateWord("base:dīcere", "verb", "ī-aspect-core", "seam3");
		generator.generateWord("base:dīcere", "verb", "ī-aspect-core", "seam4");
		generator.generateWord("base:dīcere", "verb", "ī-aspect-core", "seam5");
		generator.generateWord("base:dīcere", "verb", "ī-aspect-core", "seam6");
		System.out.println();
		generator.generateWord("base:loquī", "verb", "ō-aspect-core", "seam1");
		generator.generateWord("base:loquī", "verb", "ō-aspect-core", "seam2");
		generator.generateWord("base:loquī", "verb", "ō-aspect-core", "seam3");
		generator.generateWord("base:loquī", "verb", "ō-aspect-core", "seam4");
		generator.generateWord("base:loquī", "verb", "ō-aspect-core", "seam5");
		generator.generateWord("base:loquī", "verb", "ō-aspect-core", "seam6");
		generator.generateWord("base:loquī", "verb", "bā-branch-core", "seam1");
		generator.generateWord("base:loquī", "verb", "bā-branch-core", "seam2");
		generator.generateWord("base:loquī", "verb", "bā-branch-core", "seam3");
		generator.generateWord("base:loquī", "verb", "bā-branch-core", "seam4");
		generator.generateWord("base:loquī", "verb", "bā-branch-core", "seam5");
		generator.generateWord("base:loquī", "verb", "bā-branch-core", "seam6");
		generator.generateWord("base:loquī", "verb", "b-branch-core", "seam1");
		generator.generateWord("base:loquī", "verb", "b-branch-core", "seam2");
		generator.generateWord("base:loquī", "verb", "b-branch-core", "seam3");
		generator.generateWord("base:loquī", "verb", "b-branch-core", "seam4");
		generator.generateWord("base:loquī", "verb", "b-branch-core", "seam5");
		generator.generateWord("base:loquī", "verb", "b-branch-core", "seam6");
		generator.generateWord("base:loquī", "verb", "ē-branch-core", "seam1");
		generator.generateWord("base:loquī", "verb", "ē-branch-core", "seam2");
		generator.generateWord("base:loquī", "verb", "ē-branch-core", "seam3");
		generator.generateWord("base:loquī", "verb", "ē-branch-core", "seam4");
		generator.generateWord("base:loquī", "verb", "ē-branch-core", "seam5");
		generator.generateWord("base:loquī", "verb", "ē-branch-core", "seam6");
		generator.generateWord("base:loquī", "verb", "ū-aspect-core", "seamA");
		generator.generateWord("base:loquī", "verb", "ū-aspect-core", "seamB");
		generator.generateWord("base:loquī", "verb", "ū-aspect-core", "seamC");
		generator.generateWord("base:loquī", "verb", "ū-aspect-core", "seamD");
		generator.generateWord("base:loquī", "verb", "ū-aspect-core", "seamE");
		generator.generateWord("base:loquī", "verb", "ū-aspect-core", "seamF");
		System.out.println();
		generator.generateWord("base:esse", "verb", "ō-aspect-core", "seam1");
		generator.generateWord("base:esse", "verb", "ō-aspect-core", "seam2");
		generator.generateWord("base:esse", "verb", "ō-aspect-core", "seam3");
		generator.generateWord("base:esse", "verb", "ō-aspect-core", "seam4");
		generator.generateWord("base:esse", "verb", "ō-aspect-core", "seam5");
		generator.generateWord("base:esse", "verb", "ō-aspect-core", "seam6");
	}
	
	private final void generateEventGroup(String lowerClass) {
		System.out.println();
		generator.generateGroup("Event", lowerClass, "seam1", "seamA");
		generator.generateGroup("Event", lowerClass, "seam1", "seamC");
		generator.generateGroup("Event", lowerClass, "seam2", "seamA");
		generator.generateGroup("Event", lowerClass, "seam2", "seamC");
		generator.generateGroup("Event", lowerClass, "seam3", "seamA");
		generator.generateGroup("Event", lowerClass, "seam3", "seamC");
		generator.generateGroup("Event", lowerClass, "seam4", "seamD");
		generator.generateGroup("Event", lowerClass, "seam4", "seamF");
		generator.generateGroup("Event", lowerClass, "seam5", "seamD");
		generator.generateGroup("Event", lowerClass, "seam5", "seamF");
		generator.generateGroup("Event", lowerClass, "seam6", "seamD");
		generator.generateGroup("Event", lowerClass, "seam6", "seamF");
	}
	
	@Test
	public final void test3() throws IOException {
		generator = new Generator("lang/latin");
		generator.setLogging(false);
		generateEventGroup("Saying");
		generateEventGroup("Speaking");
	}
	
}
