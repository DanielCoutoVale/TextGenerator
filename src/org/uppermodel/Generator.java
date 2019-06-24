package org.uppermodel;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uppermodel.theory.AssociationMap;
import org.uppermodel.theory.LinearStructure;
import org.uppermodel.theory.Stratum;
import org.uppermodel.theory.Structure;
import org.uppermodel.theory.SystemNetwork;
import org.uppermodel.theory.Unit;

public class Generator {
	
	private static final String SPEECH = "Speech".intern();

	private final Traverser traverser;

	private final Collector collector;
	
	private final Realizer realizer;
	
	public Generator(String directoryName) throws IOException {
		Resource resource = new Resource(directoryName);
		SystemNetwork network = new SystemNetwork(Stratum.wording, resource.getSystemFiles());
		Inquirer inquirer = new Inquirer(Stratum.meaning);
		// FIXME move callings to a file
		Set<Unit> servus = new HashSet<>();
		servus.add(new Unit("lex:servus"));
		inquirer.addCallings("Slave", servus);
		this.traverser = new Traverser(network, inquirer);
		this.collector = new Collector(Stratum.wording, resource.getFeatureFiles());
		Moulder moulder = new Moulder(resource.getSpellingFiles());
		this.realizer = new Realizer(moulder);		
	}

	public Generator(Traverser traverser, Collector collector, Realizer realizer) {
		this.traverser = traverser;
		this.collector = collector;
		this.realizer = realizer;
	}
	
	public Unit generate(Map<Unit, Unit> spellingMap, Unit speech, Unit meaning) {
		Unit wording = new Unit();
		AssociationMap map = generate(spellingMap, speech, meaning, wording, null, null, null);
		return map.getUnit(Stratum.wording);
	}
	
	private final AssociationMap generate(Map<Unit, Unit> spellingMap, Unit speech, Unit meaning, Unit wording, Unit calling, Unit spelling, Unit writing) {
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
		this.realizer.realize(associationMap);
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
	
}
