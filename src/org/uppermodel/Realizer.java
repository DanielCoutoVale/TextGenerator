package org.uppermodel;

import static org.uppermodel.theory.Statement.Type.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.uppermodel.theory.AssociationMap;
import org.uppermodel.theory.Knob;
import org.uppermodel.theory.KnobMap;
import org.uppermodel.theory.LinearStructure;
import org.uppermodel.theory.Pair;
import org.uppermodel.theory.Statement;
import org.uppermodel.theory.Stratum;
import org.uppermodel.theory.Structure;
import org.uppermodel.theory.Unit;

public class Realizer {
		
	private final Moulder moulder;
	private boolean logging;

	public Realizer(Moulder moulder) {
		this.moulder = moulder;
		this.logging = true;
	}
	
	public Unit realize(List<Statement> statements) {
		Unit wording = new Unit();
		wording.statements.addAll(statements);
		AssociationMap associationMap = new AssociationMap();
		associationMap.setUnit(Stratum.wording, wording);
		realize(associationMap);
		return wording;
	}
	
	public final void realize(AssociationMap associationMap) {
		Unit wording = associationMap.getUnit(Stratum.wording);
		Unit calling = associationMap.getUnit(Stratum.calling);
		List<Statement> statements = wording.statements;
		
		// Logging
		if (logging)
		for (Statement statement : statements) {
			if (statement.type() == log) {
				for (int i = 0; i < statement.size(); i++) {
					if (i != 0) System.out.print(" ");
					String operand = statement.operand(i);
					if (operand.equals("#")) operand = wording.id;
					System.out.print(operand);
				}
				System.out.println();
			}
		}
		
		// Spelling
		List<String> moulds = new LinkedList<>();
		for (Statement statement : statements) {
			if (statement.type() == prepare) {
				if (calling != null && calling.id != null) {
					String mould = calling.id;
					moulds.add(mould);
				}
			}
		}
		for (Statement statement : statements) {
			if (statement.type() == mould) {
				String mould = statement.operand(0);
				moulds.add(mould);
			}
		}
		if (moulds.size() > 0) {
			String literal = moulder.get(moulds);
			associationMap.setUnit(Stratum.spelling, new Unit(literal, new HashSet<>(moulds)));
			return;
		}

		// Wording
		KnobMap map = new KnobMap();
		for (Statement statement : statements) {
			if (statement.type() == insert) {
				String operand = statement.operand(0);
				map.put(operand, new Knob(operand));
			}
		}
		for (Statement statement : statements) {
			if (statement.type() == expand) {
				String operand1 = statement.operand(0);
				String operand2 = statement.operand(1);
				map.put(operand2, new Knob(operand2));
				Knob knob1 = map.get(operand1);
				Knob knob2 = map.get(operand2);
				knob1.addChild(knob2);
				knob2.setParent(knob1);
			}
		}
		for (Statement statement : statements) {
			if (statement.type() == conflate) {
				String operand1 = statement.operand(0);
				String operand2 = statement.operand(1);
				Knob knob1 = map.get(operand1);
				Knob knob2 = map.get(operand2);
				Knob knob3 = new Knob(knob1, knob2);
				for (String function : knob3.functions) {
					map.put(function, knob3);
				}
			}
		}
		LinearStructure structure = new LinearStructure();
		structure.features.addAll(wording.features);
		structure.functions.addAll(wording.functions);
		for (Knob knob : new HashSet<Knob>(map.values())) {
			if (knob.children.size() == 0) {
				structure.constituents.add(new Unit(knob));
			}
		}
		Unit iUnit = null;
		for (Statement statement : statements) {
			if (statement.type() == placeAtFront) {
				if (iUnit != null) throw new Error();
				String operand = statement.operand(0);
				Knob iKnob = map.get(operand);
				if (iKnob.children.size() > 0) throw new Error();
				iUnit = iKnob.unit;
			}
		}
		Unit fUnit = null;
		for (Statement statement : statements) {
			if (statement.type() == placeAtBack) {
				if (fUnit != null) throw new Error();
				String operand = statement.operand(0);
				Knob fKnob = map.get(operand);
				if (fKnob.children.size() > 0) throw new Error();
				fUnit = fKnob.unit;
			}
		}
		List<Pair> oPairs = new LinkedList<>();
		for (Statement statement : statements) {
			if (statement.type() == placeJustBefore) {
				String operand1 = statement.operand(0);
				String operand2 = statement.operand(1);
				Knob knob1 = map.get(operand1);
				Knob knob2 = map.get(operand2);
				if (knob1.children.size() > 0) throw new Error();
				if (knob2.children.size() > 0) throw new Error();
				Pair oPair = new Pair(knob1.unit, knob2.unit);
				oPairs.add(oPair);
			}
		}
		List<Pair> pPairs = new LinkedList<>();
		for (Statement statement : statements) {
			if (statement.type() == placeBefore) {
				String operand1 = statement.operand(0);
				String operand2 = statement.operand(1);
				Knob knob1 = map.get(operand1);
				Knob knob2 = map.get(operand2);
				collectPairs(pPairs, knob1, knob2);
			}
		}
		
		List<Integer> prefix = new LinkedList<>();
		List<Integer> remainder = new LinkedList<>();
		for (int i = 0; i < structure.constituents.size(); i++) {
			remainder.add(i);
		}
		appendOrderings(structure, prefix, remainder, iUnit, fUnit, oPairs, pPairs);
		for (Unit constituent : structure.constituents) {
			for (String function : constituent.functions) {
				associationMap.setUnit(function, Stratum.wording, constituent);
			}
		}
		for (Statement statement : statements) {
			if (statement.type() == lexify) {
				String operand1 = statement.operand(0);
				String operand2 = statement.operand(1);
				Knob knob1 = map.get(operand1);
				if (knob1.children.size() > 0) throw new Error();
				associationMap.setUnit(operand1, Stratum.calling, new Unit(operand2));
			}
		}
		for (Statement statement : statements) {
			if (statement.type() == lexifyDown) {
				String operand1 = statement.operand(0);
				Knob knob1 = map.get(operand1);
				if (knob1.children.size() > 0) throw new Error();
				Unit unit = associationMap.getUnit(Stratum.calling);
				associationMap.setUnit(operand1, Stratum.calling, unit);
			}
		}
		for (Statement statement : statements) {
			if (statement.type() == inflectify) {
				String operand1 = statement.operand(0);
				String operand2 = statement.operand(1);
				Knob knob1 = map.get(operand1);
				if (knob1.children.size() > 0) throw new Error();
				Unit unit1 = knob1.unit;
				unit1.features.add(operand2);
			}
		}
		for (Statement statement : statements) {
			if (statement.type() == classify) {
				String operand1 = statement.operand(0);
				String operand2 = statement.operand(1);
				Knob knob1 = map.get(operand1);
				if (knob1.children.size() > 0) throw new Error();
				Unit unit1 = knob1.unit;
				unit1.features.add(operand2);
			}
		}
		for (Statement statement : statements) {
			if (statement.type() == outclassify) {
				String operand1 = statement.operand(0);
				String operand2 = statement.operand(1);
				Knob knob1 = map.get(operand1);
				if (knob1.children.size() > 0) throw new Error();
				Unit unit1 = knob1.unit;
				unit1.features.add((operand2 + "#complement").intern());
			}
		}
		associationMap.setUnit(Stratum.wording, structure);
		for (Unit constituent : structure.constituents) {
			for (String function : constituent.functions) {
				associationMap.setUnit(function, Stratum.wording, constituent);
			}
		}
	}

	private void collectPairs(List<Pair> pPairs, Knob knob1, Knob knob2) {
		if (knob1.children.size() > 0) {
			for (Knob child : knob1.children) {
				collectPairs(pPairs, child, knob2);
			}
			return;
		}
		if (knob2.children.size() > 0) {
			for (Knob child : knob2.children) {
				collectPairs(pPairs, knob1, child);
			}
			return;
		}
		Pair pPair = new Pair(knob1.unit, knob2.unit);
		pPairs.add(pPair);
	}

	private void appendOrderings(LinearStructure structure, List<Integer> prefix, List<Integer> remainder, Unit iUnit, Unit fUnit, List<Pair> oPairs, List<Pair> pPairs) {
		if (remainder.size() == 0) {
			structure.orderings.add(prefix);
		}
		if (fUnit != null) {
			Integer fIndex = structure.constituents.indexOf(fUnit);
			if (!remainder.contains(fIndex)) return;
		}
		if (prefix.size() == 0 && iUnit != null) {
			List<Integer> newPrefix = new LinkedList<>(prefix);
			List<Integer> newRemainder = new LinkedList<>(remainder);
			Integer iIndex = structure.constituents.indexOf(iUnit);
			newPrefix.add(iIndex);
			newRemainder.remove(iIndex);
			if (prohibited(structure, newPrefix, oPairs, pPairs)) return;
			appendOrderings(structure, newPrefix, newRemainder, iUnit, fUnit, oPairs, pPairs);
			return;
		}
		for (int i = 0; i < remainder.size(); i++) {
			List<Integer> newPrefix = new LinkedList<>(prefix);
			List<Integer> newRemainder = new LinkedList<>(remainder);
			newPrefix.add(newRemainder.remove(i));
			if (prohibited(structure, newPrefix, oPairs, pPairs)) continue;
			appendOrderings(structure, newPrefix, newRemainder, iUnit, fUnit, oPairs, pPairs);
		}
	}

	private boolean prohibited(Structure structure, List<Integer> prefix, List<Pair> oPairs, List<Pair> pPairs) {
		Integer bIndex = prefix.get(prefix.size() - 1);
		Unit bUnit = structure.constituents.get(bIndex);
		for (Pair oPair : oPairs) {
			if (oPair.b == bUnit) {
				if (prefix.size() == 1) return true;
				int aIndex1 = prefix.get(prefix.size() - 2);
				int aIndex2 = structure.constituents.indexOf(oPair.a);
				if (aIndex1 != aIndex2) return true;
			}
		}
		for (Pair pPair : pPairs) {
			if (pPair.b == bUnit) {
				Integer aIndex = structure.constituents.indexOf(pPair.a);
				if (!prefix.contains(aIndex)) return true;
			}
		}
		return false;
	}

	public final void setLogging(boolean logging) {
		this.logging = logging;
	}
	
}
