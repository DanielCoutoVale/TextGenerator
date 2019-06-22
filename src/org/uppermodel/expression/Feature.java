package org.uppermodel.expression;

import java.util.Set;

public class Feature implements Expression {
	
	private final String literal;
	private final String complementLiteral;

	public Feature(String literal) {
		this.literal = literal.intern();
		this.complementLiteral = (literal + "#complement").intern();
	}

	@Override
	public final boolean fulfilled(Set<String> featureLiterals) {
		return featureLiterals.contains(literal);
	}
	
	@Override
	public final boolean fulfilledComplement(Set<String> featureLiterals) {
		return featureLiterals.contains(complementLiteral);
	}
	
	@Override
	public final String toString() {
		return literal;
	}
	
}
