package org.uppermodel.expression;

import java.util.List;
import java.util.Set;

public class Or implements Expression {
	
	private final List<Expression> members;

	public Or(List<Expression> members) {
		this.members = members;
	}

	@Override
	public boolean fulfilled(Set<String> featureLiterals) {
		for (Expression member : members) {
			if (member.fulfilled(featureLiterals)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean fulfilledComplement(Set<String> featureLiterals) {
		for (Expression member : members) {
			if (!member.fulfilledComplement(featureLiterals)) {
				return false;
			}
		}
		return true;
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
