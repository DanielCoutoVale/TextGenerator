package org.uppermodel;

import java.util.Map;

import org.uppermodel.theory.AssociationMap;
import org.uppermodel.theory.Stratum;
import org.uppermodel.theory.Structure;
import org.uppermodel.theory.Unit;

public class Generator {
	
	private static final String SPEECH = "Speech".intern();

	private final Traverser traverser;

	private final Collector collector;
	
	private final Realizer realizer;

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
	
}