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
		generator.generateWord(item, "verb", core, "a-am-seam");
		generator.generateWord(item, "verb", core, "um-um-seam");
		generator.generateWord(item, "verb", core, "us-um-seam");
		generator.generateWord(item, "verb", core, "ae-ās-seam");
		generator.generateWord(item, "verb", core, "a-a-seam");
		generator.generateWord(item, "verb", core, "ī-ōs-seam");
	}

	private final void generateOstVerb(String item, String core) {
		generator.generateWord(item, "verb", core, "ō-seam");
		generator.generateWord(item, "verb", core, "s-seam");
		generator.generateWord(item, "verb", core, "t-seam");
		generator.generateWord(item, "verb", core, "mus-seam");
		generator.generateWord(item, "verb", core, "tis-seam");
		generator.generateWord(item, "verb", core, "nt-seam");
	}
	
	private final void generateEventGroup(String lowerClass) {
		generator.generateGroup("Event", lowerClass, "speaker", "female", "singular");
		generator.generateGroup("Event", lowerClass, "speaker", "male", "singular");
		generator.generateGroup("Event", lowerClass, "addressee", "female", "singular");
		generator.generateGroup("Event", lowerClass, "addressee", "male", "singular");
		generator.generateGroup("Event", lowerClass, "non-interlocutor", "female", "singular");
		generator.generateGroup("Event", lowerClass, "non-interlocutor", "male", "singular");
		generator.generateGroup("Event", lowerClass, "speaker+", "male-", "plural");
		generator.generateGroup("Event", lowerClass, "speaker+", "male+", "plural");
		generator.generateGroup("Event", lowerClass, "speaker-", "addressee+", "male-", "plural");
		generator.generateGroup("Event", lowerClass, "speaker-", "addressee+", "male+", "plural");
		generator.generateGroup("Event", lowerClass, "speaker-", "addressee-", "male-", "plural");
		generator.generateGroup("Event", lowerClass, "speaker-", "addressee-", "male+", "plural");
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
