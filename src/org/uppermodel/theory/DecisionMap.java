package org.uppermodel.theory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uppermodel.Inquirer;

public class DecisionMap implements DecisionNode {
	
	private final List<DecisionCheck> checks;
	
	private final Map<DecisionCheck, DecisionScript> scriptMap;
	
	public DecisionMap(Stratum stratum, List<String> lines) {
		if (lines.size() < 2) throw new CompilingError();
		this.checks = new LinkedList<>();
		this.scriptMap = new HashMap<DecisionCheck, DecisionScript>();
		int level = lines.get(0).indexOf('-') / 2;
		String checkLine = null;
		List<String> scriptLines = new LinkedList<>();
		for (int l = 0; l < lines.size(); l++) {
			String line = lines.get(l);
			switch (line.charAt(level * 2)) {
			case '-':
				if (checkLine != null) {
					DecisionCheck check = new DecisionCheck(checkLine);
					DecisionScript script = new DecisionScript(stratum, scriptLines);
					this.checks.add(check);
					this.scriptMap.put(check, script);
				}
				checkLine = line.substring(level * 2 + 1).trim();
				scriptLines = new LinkedList<>();
				break;
			default:
				scriptLines.add(line);
				break;
			}
		}
		if (checkLine != null) {
			DecisionCheck check = new DecisionCheck(checkLine);
			DecisionScript script = new DecisionScript(stratum, scriptLines);
			this.checks.add(check);
			this.scriptMap.put(check, script);
		}
	}

	public final String toString(String prefix) {
		StringBuffer buffer = new StringBuffer();
		for (DecisionCheck check : checks) {
			buffer.append(prefix);
			buffer.append("- ");
			buffer.append(check);
			buffer.append("\n");
			DecisionScript script = scriptMap.get(check);
			buffer.append(script.toString(prefix + "  "));
		}
		return buffer.toString();
	}

	public final Set<String> getFeatures() {
		Set<String> features = new HashSet<>();
		for (DecisionScript script : scriptMap.values()) {
			features.addAll(script.getFeatures());
		}
		return features;
	}

	public final void execute(System system, AssociationMap associationMap,
			Inquirer inquirer) {
		for (DecisionCheck check : checks) {
			if (check.execute(associationMap, inquirer)) {
				DecisionScript script = scriptMap.get(check);
				script.execute(system, associationMap, inquirer);
				return;
			}
		}
	}
	
}
