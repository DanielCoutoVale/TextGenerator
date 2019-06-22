package org.uppermodel.expression;

import java.util.List;
import java.util.Set;

public class And implements Expression {

	private final List<Expression> expressions;

	public And(List<Expression> expressions) {
		this.expressions = expressions;
	}
	
	@Override
	public boolean fulfilled(Set<String> featureLiterals) {
		for (Expression expression : expressions) {
			if (!expression.fulfilled(featureLiterals)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean fulfilledComplement(Set<String> featureLiterals) {
		for (Expression expression : expressions) {
			if (expression.fulfilledComplement(featureLiterals)) {
				return true;
			}
		}
		return false;
	}

}
