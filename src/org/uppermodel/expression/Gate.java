package org.uppermodel.expression;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.uppermodel.theory.Statement;

public class Gate {
	
	private final Expression entryCondition;
	
	public final List<Statement> statements;
	
	public Gate(Expression entryCondition) {
		this.entryCondition = entryCondition;
		this.statements = new LinkedList<>();
	}
	
	public final boolean enterable(Set<String> features) {
		return entryCondition.fulfilled(features);
	}
	
}
