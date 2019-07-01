package org.uppermodel.theory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.uppermodel.Inquirer;

public class DecisionAtom implements DecisionNode {
	
	private static final String A = "a".intern();
	private static final String AN = "an".intern();
	private static final String AS = "as".intern();
	private static final String BY = "by".intern();
	private static final String CHOOSE = "choose".intern();
	private static final String DEFAULT = "default".intern();
	private static final String NAME = "name".intern();
	private static final String OF = "of".intern();
	private static final String OR = "or".intern();
	private static final String RESOLVE = "resolve".intern();
	private static final String THE = "the".intern();
	private static final String TREAT = "treat".intern();
	
	public static enum Type {
		choose, defaultOption, allOptions, treat, treatPart,
		resolveName, resolveTypedName
	}
	
	private final String[] choose1 = {CHOOSE, null};
	
	private final String[] defaultOption1 = {CHOOSE, null, BY, DEFAULT};
	
	private final String[] allOptions1 = {CHOOSE, null, OR, null};
	private final String[] allOptions2 = {CHOOSE, null, OR, null, OR, null};
	private final String[] allOptions3 = {CHOOSE, null, OR, null, OR, null, OR, null};
	private final String[] allOptions4 = {CHOOSE, null, OR, null, OR, null, OR, null, OR, null};
	private final String[] allOptions5 = {CHOOSE, null, OR, null, OR, null, OR, null, OR, null, OR, null};
	private final String[] allOptions6 = {CHOOSE, null, OR, null, OR, null, OR, null, OR, null, OR, null, OR, null};
	private final String[] allOptions7 = {CHOOSE, null, OR, null, OR, null, OR, null, OR, null, OR, null, OR, null, OR, null};
	private final String[] allOptions8 = {CHOOSE, null, OR, null, OR, null, OR, null, OR, null, OR, null, OR, null, OR, null, OR, null};
	private final String[] allOptions9 = {CHOOSE, null, OR, null, OR, null, OR, null, OR, null, OR, null, OR, null, OR, null, OR, null, OR, null};
	
	private final String[] resolveName1 = {RESOLVE, NAME, OF, null};
	private final String[] resolveName2 = {RESOLVE, THE, NAME, OF, THE, null};
	
	private final String[] resolveTypedName1 = {RESOLVE, null, NAME, OF, null};
	private final String[] resolveTypedName2 = {RESOLVE, THE, null, NAME, OF, THE, null};
	
	private final String[] treat1 = {TREAT, null, AS, null};
	private final String[] treat2 = {TREAT, THE, null, AS, A, null};
	private final String[] treat3 = {TREAT, THE, null, AS, AN, null};
	
	private final String[] treatPart1 = {TREAT, null, OF, null, AS, null};
	private final String[] treatPart2 = {TREAT, THE, null, OF, THE, null, AS, A, null};
	private final String[] treatPart3 = {TREAT, THE, null, OF, THE, null, AS, AN, null};
	
	private final String[][] statementPatterns = {
			choose1,
			defaultOption1,
			allOptions1, allOptions2, allOptions3,
			allOptions4, allOptions5, allOptions6,
			allOptions7, allOptions8, allOptions9,
			resolveName1, resolveName2,
			treat1, treat2, treat3,
			treatPart1, treatPart2, treatPart3
	};
	
	private List<String> tokens;
	
	private Stratum stratum;
	
	public DecisionAtom(Stratum stratum, String literal) {
		this.stratum = stratum;
		tokens = new LinkedList<>();
		for (String token : literal.split(" ")) {
			tokens.add(token.intern());
		}
	}
	
	public final String operand(int index) {
		if (index < 0) throw new Error();
		if (index >= size()) return null;
		for (String[] tokenPatterns : statementPatterns) {
			if (matches(tokenPatterns)) {
				for (int i = 0; i < tokenPatterns.length; i++) {
					String tokenPattern = tokenPatterns[i];
					if (tokenPattern == null) {
						index--;
					}
					if (index == -1) {
						return tokens.get(i);
					}
				}
				throw new Error();
			}
		}
		throw new Error();
	}
	
	public final boolean valid() {
		for (String[] tokenPatterns : statementPatterns) {
			if (matches(tokenPatterns)) return true;
		}
		return false;
	}
	
	public final int size() {
		for (String[] tokenPatterns : statementPatterns) {
			if (matches(tokenPatterns)) {
				int size = 0;
				for (String tokenPattern : tokenPatterns) {
					if (tokenPattern == null) {
						size++;
					}
				}
				return size;
			}
		}
		throw new Error();
	}
	
	public final Type type() {
		if (matches(choose1)) return Type.choose;
		if (matches(defaultOption1)) return Type.defaultOption;
		if (matches(allOptions1)) return Type.allOptions;
		if (matches(allOptions2)) return Type.allOptions;
		if (matches(allOptions3)) return Type.allOptions;
		if (matches(allOptions4)) return Type.allOptions;
		if (matches(allOptions5)) return Type.allOptions;
		if (matches(allOptions6)) return Type.allOptions;
		if (matches(allOptions7)) return Type.allOptions;
		if (matches(allOptions8)) return Type.allOptions;
		if (matches(allOptions9)) return Type.allOptions;
		if (matches(resolveName1)) return Type.resolveName;
		if (matches(resolveName2)) return Type.resolveName;
		if (matches(resolveTypedName1)) return Type.resolveTypedName;
		if (matches(resolveTypedName2)) return Type.resolveTypedName;
		if (matches(treat1)) return Type.treat;
		if (matches(treat2)) return Type.treat;
		if (matches(treat3)) return Type.treat;
		if (matches(treatPart1)) return Type.treatPart;
		if (matches(treatPart2)) return Type.treatPart;
		if (matches(treatPart3)) return Type.treatPart;
		return null;
	}
	
	private final boolean matches(String[] tokenPatterns) {
		if (tokens.size() != tokenPatterns.length) {
			return false;
		}
		for (int i = 0; i < tokenPatterns.length; i++) {
			String tokenPattern = tokenPatterns[i];
			if (tokenPattern == null) continue;
			String token = tokens.get(i);
			if (tokenPattern != token) return false;
		}
		return true;
	}
	
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		for (String token : this.tokens) {
			if (buffer.length() != 0) {
				buffer.append(" ");
			}
			buffer.append(token);
		}
		return buffer.toString();
	}
	
	public final boolean hasFeature() {
		switch(type()) {
		case choose:
		case defaultOption:
			return true;
		default:
			return false;
		}
	}
	
	public final boolean hasFeatures() {
		switch(type()) {
		case allOptions:
			return true;
		default:
			return false;
		}
	}
	
	public final String getFeature() {
		return operand(0);
	}
	
	public final Set<String> getFeatures() {
		Set<String> features = new HashSet<>();
		for (int o = 0;; o++) {
			String operand = operand(o);
			if (operand == null) break;
			features.add(operand);
		}
		return features;
	}

	public final void execute(System system, AssociationMap associationMap, Inquirer inquirer) {
		switch(type()) {
		case choose: {
			Unit unit = associationMap.getUnit(stratum);
			Set<String> features = system.getFeatures();
			String operand = operand(0);
			for (String feature : features) {
				if (feature.equals(operand)) {
					unit.features.add(feature);
				} else {
					unit.features.add(feature + "#complement");
				}
			}
			break;
		}
		case defaultOption: {
			Unit unit = associationMap.getUnit(stratum);
			Set<String> features = system.getFeatures();
			String operand = operand(0);
			Set<String> common = new HashSet<>(features);
			common.retainAll(unit.features);
			if (common.size() == 1) {
				operand = common.iterator().next();
			}
			for (String feature : features) {
				if (feature.equals(operand)) {
					unit.features.add(feature);
				} else {
					unit.features.add(feature + "#complement");
				}
			}
			break;
		}
		case allOptions:
			break;
		case resolveName: {
			Unit meaning = associationMap.getUnit(operand(0), Stratum.meaning);
			Unit calling = inquirer.resolveName(meaning);
			associationMap.setUnit(operand(0), Stratum.calling, calling);
			break;
		}
		case resolveTypedName: {
			Unit meaning = associationMap.getUnit(operand(1), Stratum.meaning);
			Unit calling = inquirer.resolveTypedName(meaning, operand(0));
			associationMap.setUnit(operand(1), Stratum.calling, calling);
			break;
		}
		case treat: {
			Unit meaning = associationMap.getUnit(operand(0), Stratum.meaning);
			associationMap.setUnit(operand(1), Stratum.meaning, meaning);
			break;
		}
		case treatPart: {
			String operandA = operand(0);
			String operandB = operand(1);
			String operandC = operand(2);
			associationMap.put(operandC, inquirer.identify(associationMap.get(operandB), operandA));
			break;
		}
		}
	}
	
}
