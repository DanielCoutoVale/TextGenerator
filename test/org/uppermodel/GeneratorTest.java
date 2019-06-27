package org.uppermodel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.uppermodel.Generator;
import org.uppermodel.theory.LinearStructure;
import org.uppermodel.theory.Unit;

public class GeneratorTest {
	
	private void generateThing(String tax) throws IOException {
		generator = new Generator("lang/latin");
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

	@Test
	public final void test2() throws IOException {
		generator = new Generator("lang/latin");
		generator.generate("I ate a pizza.");
		generator.generate("I ate a [food].", "pizza");
		generator.generate("I ate a [food].", "hotdog");
		generator.generate("I ate [number] (pizza).", "one");
		generator.generate("I ate [number] {food}.", "one", "hotdog");
		generator.generate("I ate [number] (pizza).", "two");
		generator.generate("I ate [number] {food}.", "two", "hotdog");
	}

	@Test
	public final void test3() throws IOException {
		generator = new Generator("lang/german");
		generator.setLogging(false);
		generator.generateWord("base:sagen", "verb", "ge-core");
		generator.generateWord("base:sagen", "verb", "bare-core", "seam1");
		generator.generateWord("base:sagen", "verb", "bare-core", "seam2");
		generator.generateWord("base:sagen", "verb", "bare-core", "seam3");
		generator.generateWord("base:sagen", "verb", "bare-core", "seam4");
		generator.generateWord("base:sagen", "verb", "bare-core", "seam5");
		generator.generateWord("base:sagen", "verb", "te-core", "seam1");
		generator.generateWord("base:sagen", "verb", "te-core", "seam2");
		generator.generateWord("base:sagen", "verb", "te-core", "seam3");
		generator.generateWord("base:sagen", "verb", "te-core", "seam4");
		generator.generateWord("base:sagen", "verb", "te-core", "seam5");
		generator.generateWord("base:haben", "verb", "ge-core");
		generator.generateWord("base:haben", "verb", "bare-core", "seam1");
		generator.generateWord("base:haben", "verb", "bare-core", "seam2");
		generator.generateWord("base:haben", "verb", "bare-core", "seam3");
		generator.generateWord("base:haben", "verb", "bare-core", "seam4");
		generator.generateWord("base:haben", "verb", "bare-core", "seam5");
		generator.generateWord("base:haben", "verb", "te-core", "seam1");
		generator.generateWord("base:haben", "verb", "te-core", "seam2");
		generator.generateWord("base:haben", "verb", "te-core", "seam3");
		generator.generateWord("base:haben", "verb", "te-core", "seam4");
		generator.generateWord("base:haben", "verb", "te-core", "seam5");
		generator.generateWord("base:haben", "verb", "ge-core");
		generator.generateWord("base:schlafen", "verb", "ge-core");
		generator.generateWord("base:schlafen", "verb", "bare-core", "seam1");
		generator.generateWord("base:schlafen", "verb", "bare-core", "seam2");
		generator.generateWord("base:schlafen", "verb", "bare-core", "seam3");
		generator.generateWord("base:schlafen", "verb", "bare-core", "seam4");
		generator.generateWord("base:schlafen", "verb", "bare-core", "seam5");
		generator.generateWord("base:schlafen", "verb", "te-core", "seam1");
		generator.generateWord("base:schlafen", "verb", "te-core", "seam2");
		generator.generateWord("base:schlafen", "verb", "te-core", "seam3");
		generator.generateWord("base:schlafen", "verb", "te-core", "seam4");
		generator.generateWord("base:schlafen", "verb", "te-core", "seam5");
	}
	
}
