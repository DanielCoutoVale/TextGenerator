package org.uppermodel.expression;

import java.util.Set;

public class Garbage implements Expression {

	@Override
	public boolean fulfilled(Set<String> featureLiterals) {
		return false;
	}

	@Override
	public boolean fulfilledComplement(Set<String> featureLiterals) {
		return false;
	}
	
	@Override
	public final String toString() {
		return "#GARBAGE#";
	}

}
