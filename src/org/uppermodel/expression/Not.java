package org.uppermodel.expression;

import java.util.Set;

public class Not implements Expression {
	
	private final Expression exp;
	
	public Not(Expression exp) {
		this.exp = exp;
	}

	@Override
	public boolean fulfilled(Set<String> featureLiterals) {
		return exp.fulfilledComplement(featureLiterals);
	}

	@Override
	public boolean fulfilledComplement(Set<String> featureLiterals) {
		return exp.fulfilled(featureLiterals);
	}
	
}
