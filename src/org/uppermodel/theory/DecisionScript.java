package org.uppermodel.theory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.uppermodel.Inquirer;
import org.uppermodel.Moulder;

public class DecisionScript {
	
	private final Stratum stratum;
	
	private final List<DecisionNode> nodes;
	
	public DecisionScript(Stratum stratum, List<String> lines) {
		this.stratum = stratum;
		this.nodes = new LinkedList<>();
		if (lines.size() == 0) return;
		int level = lines.get(0).indexOf('-') / 2;
		List<String> subscriptLines = new LinkedList<>();
		for (int l = 0; l < lines.size(); l++) {
			String line = lines.get(l);
			char ch = line.charAt(level * 2);
			switch (ch) {
			case '-':
				if (isMap(subscriptLines)) {
					if (-1 == line.indexOf("- if") && -1 == line.indexOf("- otherwise")) {
						subscriptLines = makeStep(subscriptLines);
					}
					subscriptLines.add(line);
				} else {
					subscriptLines = makeStep(subscriptLines);
					subscriptLines.add(line);
				}
				break;
			default:
				subscriptLines.add(line);
				break;
			}
		}
		makeStep(subscriptLines);
	}

	private boolean isMap(List<String> subscriptLines) {
		if (subscriptLines.size() == 0) return false;
		String head = subscriptLines.get(0);
		return -1 != head.indexOf("- if");
	}

	private List<String> makeStep(List<String> subscriptLines) {
		if (subscriptLines.size() == 1) {
			makeAtom(subscriptLines);
			subscriptLines = new LinkedList<>();
		}
		if (subscriptLines.size() >= 2) {
			makeMap(subscriptLines);
			subscriptLines = new LinkedList<>();
		}
		return subscriptLines;
	}
	
	private final void makeAtom(List<String> lines) {
		String line = lines.get(0);
		int index = line.indexOf('-') + 1;
		String literal = line.substring(index).trim();
		DecisionAtom atom = new DecisionAtom(stratum, literal);
		this.nodes.add(atom);
	}
	
	private final void makeMap(List<String> lines) {
		DecisionMap map = new DecisionMap(stratum, lines);
		this.nodes.add(map);
	}

	public final Set<String> execute(System system, AssociationMap associationMap, Inquirer inquirer, Moulder moulder) {
		Set<String> features = new HashSet<>();
		for (DecisionNode node : nodes) {
			if (node instanceof DecisionAtom) {
				DecisionAtom atom = (DecisionAtom) node;
				atom.execute(system, associationMap, inquirer);
			} else {
				DecisionMap map = (DecisionMap) node;
				map.execute(system, associationMap, inquirer, moulder);
			}
		}
		return features;
	}
	
	@Override
	public final String toString() {
		return toString("");
	}

	public final String toString(String prefix) {
		StringBuffer buffer = new StringBuffer();
		for (DecisionNode node : nodes) {
			if (node instanceof DecisionAtom) {
				DecisionAtom atom = (DecisionAtom) node;
				buffer.append(prefix);
				buffer.append("- ");
				buffer.append(atom);
				buffer.append("\n");
			} else {
				DecisionMap map = (DecisionMap) node;
				buffer.append(map.toString(prefix));
			}
		}
		return buffer.toString();
	}

	public final Set<String> getFeatures() {
		Set<String> features = new HashSet<>();
		for (DecisionNode node : nodes) {
			if (node instanceof DecisionAtom) {
				DecisionAtom atom = (DecisionAtom) node;
				if (atom.hasFeature()) {
					features.add(atom.getFeature());
				}
				if (atom.hasFeatures()) {
					features.addAll(atom.getFeatures());
				}
			} else {
				DecisionMap map = (DecisionMap) node;
				features.addAll(map.getFeatures());
			}
		}
		return features;
	}
	
}
