package org.uppermodel.theory;

import java.util.LinkedList;
import java.util.List;

import org.uppermodel.Inquirer;

public class DecisionCheck {
	
	private static final String A = "a".intern();
	private static final String AN = "an".intern();
	private static final String IF = "if".intern();
	private static final String IS = "is".intern();
	private static final String OTHERWISE = "otherwise".intern();
	private static final String THE = "the".intern();
	
	public static enum Type {
		classAttribute, otherwise
	}

	private final String[] classAttribute1 = {IF, null, IS, null};
	private final String[] classAttribute2 = {IF, THE, null, IS, A, null};
	private final String[] classAttribute3 = {IF, THE, null, IS, AN, null};
	
	private final String[] otherwise1 = {OTHERWISE};
	
	private final String[][] statementPatterns = {
			classAttribute1, classAttribute2, classAttribute3,
			otherwise1
	};
	
	private List<String> tokens;

	public DecisionCheck(String literal) {
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

	public Type type() {
		if (matches(classAttribute1)) return Type.classAttribute;
		if (matches(classAttribute2)) return Type.classAttribute;
		if (matches(classAttribute3)) return Type.classAttribute;
		if (matches(otherwise1)) return Type.otherwise;
		return null;
	}

	private boolean matches(String[] tokenPatterns) {
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
	
	@Override
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

	public boolean execute(AssociationMap associationMap, Inquirer inquirer) {
		switch(type()) {
		case classAttribute: {
			return inquirer.checkClassAttribute(associationMap, operand(0), operand(1));
		}
		case otherwise:
			return true;
		default:
			return false;
		}
	}
	
}
