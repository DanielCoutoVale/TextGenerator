package org.uppermodel.expression;

import java.util.Set;

public interface Expression {

	boolean fulfilled(Set<String> featureLiterals);

	boolean fulfilledComplement(Set<String> featureLiterals);

}
