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
		System.out.println();
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
		generateDīcereTypeVerb("base:dīcere");
		generateDīcereTypeVerb("base:dūcere");
		generateDīcereTypeVerb("base:facere");
		generateDīcereTypeVerb("base:audīre");
		generateLoquīTypeVerb("base:loquī");
		generateOstVerb("base:esse", "ō-aspect-core");
		System.out.println();
	}

	private void generateLoquīTypeVerb(String item) {
		generateOstVerb(item, "ō-aspect-core");
		generateOstVerb(item, "bā-branch-core");
		generateOstVerb(item, "b-branch-core");
		generateOstVerb(item, "ē-branch-core");
		generateOstVerb(item, "rē-branch-core");
		generateAumusVerb(item, "ū-aspect-core");
		System.out.println();
	}

	private void generateDīcereTypeVerb(String item) {
		generateOstVerb(item, "ō-aspect-core");
		generateOstVerb(item, "bā-branch-core");
		generateOstVerb(item, "b-branch-core");
		generateOstVerb(item, "ē-branch-core");
		generateOstVerb(item, "rē-branch-core");
		generateOstVerb(item, "ī-aspect-core");
		System.out.println();
	}

	private void generateAumusVerb(String item, String core) {
		generator.generateWord(item, "verb", core, "seamA");
		generator.generateWord(item, "verb", core, "seamB");
		generator.generateWord(item, "verb", core, "seamC");
		generator.generateWord(item, "verb", core, "seamD");
		generator.generateWord(item, "verb", core, "seamE");
		generator.generateWord(item, "verb", core, "seamF");
	}

	private final void generateOstVerb(String item, String core) {
		generator.generateWord(item, "verb", core, "seam1");
		generator.generateWord(item, "verb", core, "seam2");
		generator.generateWord(item, "verb", core, "seam3");
		generator.generateWord(item, "verb", core, "seam4");
		generator.generateWord(item, "verb", core, "seam5");
		generator.generateWord(item, "verb", core, "seam6");
	}
	
	private final void generateEventGroup(String lowerClass) {
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
		System.out.println();
	}
	
	@Test
	public final void test3() throws IOException {
		generator = new Generator("lang/latin");
		generator.setLogging(false);
		generateEventGroup("Saying");
		generateEventGroup("Speaking");
	}
	
}
