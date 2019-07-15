package org.uppermodel.expression;

import java.util.List;
import java.util.Set;

public class And implements Expression {

	private final List<Expression> members;

	public And(List<Expression> members) {
		this.members = members;
	}
	
	@Override
	public boolean fulfilled(Set<String> featureLiterals) {
		for (Expression expression : members) {
			if (!expression.fulfilled(featureLiterals)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean fulfilledComplement(Set<String> featureLiterals) {
		for (Expression expression : members) {
			if (expression.fulfilledComplement(featureLiterals)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Expression member : members) {
			if (buffer.length() != 0) {
				buffer.append(" or ");
			}
			buffer.append(member.toString());
		}
		return "(" + buffer.toString() + ")";
	}

}
