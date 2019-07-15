package org.uppermodel.expression;

import java.util.Set;

public class Not implements Expression {
	
	private final Expression operand;
	
	public Not(Expression operand) {
		this.operand = operand;
	}

	@Override
	public boolean fulfilled(Set<String> featureLiterals) {
		return operand.fulfilledComplement(featureLiterals);
	}

	@Override
	public boolean fulfilledComplement(Set<String> featureLiterals) {
		return operand.fulfilled(featureLiterals);
	}
	
	@Override
	public final String toString() {
		return "not " + operand.toString();
	}
	
}
