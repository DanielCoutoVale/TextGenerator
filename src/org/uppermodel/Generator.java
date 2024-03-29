package org.uppermodel;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.uppermodel.theory.AssociationMap;
import org.uppermodel.theory.LinearStructure;
import org.uppermodel.theory.Stratum;
import org.uppermodel.theory.Structure;
import org.uppermodel.theory.SystemNetwork;
import org.uppermodel.theory.Unit;

public class Generator {
	
	private static final String SPEECH = "Speech".intern();

	public final static Unit newSpeech() {
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

	private final Traverser traverser;

	private final Collector collector;
	
	private final Realizer realizer;
	
	public Generator(String directoryName) throws IOException {
		Resource resource = new Resource(directoryName);
		SystemNetwork network = new SystemNetwork(Stratum.wording, resource.getSystemFiles());
		Inquirer inquirer = new Inquirer(Stratum.meaning, resource.getCallingFiles());
		Moulder moulder = new Moulder(resource.getSpellingFiles());
		this.traverser = new Traverser(network, inquirer, moulder);
		this.collector = new Collector(Stratum.wording, resource.getFeatureFiles());
		this.realizer = new Realizer(moulder);
	}

	public Generator(Traverser traverser, Collector collector, Realizer realizer) {
		this.traverser = traverser;
		this.collector = collector;
		this.realizer = realizer;
	}
	
	public Unit generate(Map<Unit, Unit> spellingMap, Unit speech, Unit meaning, String... features) {
		Unit wording = new Unit();
		wording.features.addAll(Arrays.asList(features));
		AssociationMap map = generate(spellingMap, speech, meaning, wording, null, null, null);
		return map.getUnit(Stratum.wording);
	}
	
	public final AssociationMap generate(Map<Unit, Unit> spellingMap, Unit speech, Unit meaning, Unit wording, Unit calling, Unit spelling, Unit writing) {
		AssociationMap associationMap = new AssociationMap();
		associationMap.setUnit(Stratum.meaning, meaning);
		associationMap.setUnit(Stratum.wording, wording);
		associationMap.setUnit(Stratum.calling, calling);
		associationMap.setUnit(Stratum.spelling, spelling);
		associationMap.setUnit(Stratum.writing, writing);
		associationMap.setUnit(SPEECH, Stratum.meaning, speech);
		wording.features.add("wording");
		this.traverser.traverse(associationMap);
		this.collector.collect(associationMap);
		//System.out.println(associationMap);
		this.realizer.realize(associationMap);
		associationMap.toString();
		//System.out.println(associationMap);
		wording = associationMap.getUnit(Stratum.wording);
		if (wording instanceof Structure) {
			Structure structure = (Structure) wording;
			for (int index = 0; index < structure.constituents.size(); index++) {
				Unit subwording = structure.constituents.get(index);
				String functionA = subwording.functions.get(0);
				Unit submeaning = associationMap.getUnit(functionA, Stratum.meaning);
				Unit subcalling = associationMap.getUnit(functionA, Stratum.calling);
				Unit subspelling = associationMap.getUnit(functionA, Stratum.spelling);
				Unit subwriting = associationMap.getUnit(functionA, Stratum.writing);
				AssociationMap map = this.generate(spellingMap, speech, submeaning, subwording, subcalling, subspelling, subwriting);
				subwording = map.getUnit(Stratum.wording);
				structure.constituents.set(index, subwording);
				subspelling = map.getUnit(Stratum.spelling);
				if (subspelling != null) {
					spellingMap.put(subwording, subspelling);
				}
			}
		}
		return associationMap;
	}
	
	public final void generateInline(String template) {
		System.out.print(template);
	}
	
	public final void generate(String template) {
		System.out.println(template);
	}
	
	public final String generateString(String template) {
		return template;
	}
	
	public final Unit generateSpelling(String template) {
		Unit spelling = new Unit(template);
		return spelling;
	}
	
	public final Unit generateWording(String template, Map<Unit, Unit> spellingMap) {
		Unit wording = new Unit();
		Unit spelling = new Unit(template);
		spellingMap.put(wording, spelling);
		return spelling;
	}
	
	public final void generateInline(String template, String... params) {
		System.out.print(generateString(template, params));
	}
	
	public final void generate(String template, String... params) {
		System.out.println(generateString(template, params));
	}
	
	private final String generateString(String template, String[] params) {
		LinearStructure wording = new LinearStructure();
		List<Integer> ordering = new LinkedList<>();
		wording.features.add("template");
		wording.orderings.add(ordering);
		Map<Unit, Unit> spellingMap = new HashMap<>();
		StringBuffer buffer = new StringBuffer();
		boolean escaped = false;
		Character scoped = null;
		int index = 0;
		for (int i = 0; i < template.length(); i++) {
			char ch = template.charAt(i);
			if (escaped) {
				escaped = false;
				buffer.append(ch);
			}
			switch(ch) {
			default: {
				buffer.append(ch);
				break;
			}
			case '\\':
				escaped = true;
				break;
			case '(':
			case '[':
			case '{': {
				if (scoped != null) throw new Error();
				scoped = ch;
				Unit spelling = new Unit(buffer.toString());
				Unit constituent = new Unit();
				ordering.add(ordering.size());
				wording.constituents.add(constituent);
				spellingMap.put(constituent, spelling);
				buffer = new StringBuffer();
				break;
			}
			case ']': {
				if (scoped != '[') throw new Error();
				if (params.length <= index) throw new Error();
				String param = params[index++];
				Unit spelling = new Unit(param);
				Unit constituent = new Unit();
				ordering.add(ordering.size());
				wording.constituents.add(constituent);
				spellingMap.put(constituent, spelling);
				buffer = new StringBuffer();
				scoped = null;
				break;
			}
			case ')': {
				if (scoped != '(') throw new Error();
				// FIXME buffer should be a lexical item 
				Unit spelling = new Unit(buffer.toString());
				Unit constituent = new Unit();
				ordering.add(ordering.size());
				wording.constituents.add(constituent);
				spellingMap.put(constituent, spelling);
				buffer = new StringBuffer();
				scoped = null;
				break;
			}
			case '}': {
				if (scoped != '{') throw new Error();
				if (params.length <= index) throw new Error();
				String param = params[index++];
				// FIXME param should be a lexical item
				Unit spelling = new Unit(param);
				Unit constituent = new Unit();
				ordering.add(ordering.size());
				wording.constituents.add(constituent);
				spellingMap.put(constituent, spelling);
				buffer = new StringBuffer();
				scoped = null;
				break;

			}
			}
		}
		if (buffer.length() > 0) {
			Unit spelling = new Unit(buffer.toString());
			Unit constituent = new Unit();
			ordering.add(ordering.size());
			wording.constituents.add(constituent);
			spellingMap.put(constituent, spelling);
		}
		return wording.toString(spellingMap);
	}
	
	public final Unit generateSpelling(String template, String... params) {
		Unit spelling = new Unit(generateString(template, params));
		return spelling;
	}
	
	public final Unit generateWording(String template, Map<Unit, Unit> spellingMap, String... params) {
		Unit wording = new Unit();
		Unit spelling = new Unit(generateString(template, params));
		spellingMap.put(wording, spelling);
		return spelling;
	}
	
	public final void generateWordInline(String base, String... features) {
		System.out.print(generateWordAsString(base, features));
	}
	
	public final void generateWord(String base, String... features) {
		System.out.println(generateWordAsString(base, features));
	}
	
	public final String generateWordAsString(String base, String... features) {
		Map<Unit, Unit> spellingMap = new HashMap<>();
		Unit wording = generateWordAsWording(spellingMap, base, features);
		return wording.toString(spellingMap);
	}
	
	public final Unit generateWordAsSpelling(String base, String... features) {
		return new Unit(generateWordAsString(base, features));
	}
	
	public final Unit generateWordAsWording(Map<Unit, Unit> spellingMap, String base, String... features) {
		Unit speech = newSpeech();
		Unit wording = new Unit();
		wording.features.add("wording");
		wording.features.add("words");
		for (String feature : features) {
			wording.features.add(feature);
		}
		Unit calling = new Unit(base);
		AssociationMap map = this.generate(spellingMap, speech, null, wording, calling, null, null);
		return map.getUnit(Stratum.wording);
	}

	public void setLogging(boolean logging) {
		this.realizer.setLogging(logging);
	}

	public final void generateGroupInline(String upperClass, String lowerClass, String... features) {
		System.out.print(generateGroupAsString(upperClass, lowerClass, features));
	}

	public final void generateGroup(String upperClass, String lowerClass, String... features) {
		System.out.println(generateGroupAsString(upperClass, lowerClass, features));
	}

	private final String generateGroupAsString(String upperClass, String lowerClass, String... features) {
		Map<Unit, Unit> spellingMap = new HashMap<>();
		LinearStructure wording = generateGroupAsWording(spellingMap, upperClass, lowerClass, features);
		return wording.toString(spellingMap);
	}

	private LinearStructure generateGroupAsWording(Map<Unit, Unit> spellingMap, String upperClass, String lowerClass,
			String... features) {
		Unit speech = Generator.newSpeech();
		Unit meaning = new Unit();
		meaning.features.add(upperClass);
		meaning.features.add(lowerClass);
		LinearStructure wording = (LinearStructure) this.generate(spellingMap, speech, meaning, features);
		return wording;
	}

	public final String toNetworkDescription() {
		return this.traverser.toNetworkDescription();
	}
	
}
