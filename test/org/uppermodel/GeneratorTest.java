package org.uppermodel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.uppermodel.Generator;
import org.uppermodel.theory.LinearStructure;
import org.uppermodel.theory.Structure;
import org.uppermodel.theory.Unit;

public class GeneratorTest {

	private static final String SPEECH = "Speech".intern();

	private final static Unit newSpeech() {
		Structure speech = new Structure(new Unit());
		Unit sayer = new Unit();
		Unit addressee = new Unit();
		speech.constituents.add(sayer);
		speech.constituents.add(addressee);
		speech.features.add(SPEECH);
		sayer.functions.add("sayer");
		addressee.functions.add("addressee");
		return speech;
	}
	
	private Generator generator;
	
	@Before
	public void prepare() throws IOException {
		generator = new Generator("lang/org/uppermodel/latin");
	}
	
	@Test
	public void test1() {
		Unit speech = newSpeech();
		Unit meaning = new Unit();
		meaning.features.add("Thing");
		meaning.features.add("Slave");
		Map<Unit, Unit> spellingMap = new HashMap<>();
		LinearStructure wording = (LinearStructure) this.generator.generate(spellingMap, speech, meaning);
		System.out.println(wording.toString(spellingMap));
		
		
		generator.generate("I ate a pizza.");
		generator.generate("I ate a [food].", "pizza");
		generator.generate("I ate a [food].", "hotdog");
		generator.generate("I ate [number] (pizza).", "one");
		generator.generate("I ate [number] {food}.", "one", "hotdog");
		generator.generate("I ate [number] (pizza).", "two");
		generator.generate("I ate [number] {food}.", "two", "hotdog");
	}
	
}
