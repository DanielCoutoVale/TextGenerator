package org.uppermodel.theory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.uppermodel.Inquirer;
import org.uppermodel.Moulder;
import org.uppermodel.expression.Expression;
import org.uppermodel.expression.ExpressionTokenList;

public class System {
	
	private String name;
	
	private Expression entryCondition;
	
	private DecisionScript script;

	private Stratum stratum;
	
	public System(Stratum stratum, List<String> lines) {
		this.stratum = stratum;
		if (lines.size() == 0) throw new CompilingError();
		String head = lines.get(0);
		List<String> body = lines.subList(1, lines.size());
		this.name = head.substring(0, head.indexOf(' ')).trim();
		List<String> tokenList = Arrays.asList(head.substring(head.indexOf(' ') + 1).trim().split(" "));
		ExpressionTokenList expressionTokenList = new ExpressionTokenList(tokenList);
		this.entryCondition = expressionTokenList.makeExpression();
		this.script = new DecisionScript(stratum, body);
	}
	
	public final void executeScript(AssociationMap associationMap, Inquirer inquirer, Moulder moulder) {
		Set<String> features = this.script.execute(this, associationMap, inquirer, moulder);
		associationMap.getUnit(stratum).features.addAll(features);
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final boolean enterable(Set<String> features) {
		return this.entryCondition.fulfilled(features);
	}
	
	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(name);
		buffer.append(" ");
		buffer.append(entryCondition);
		buffer.append("\n");
		buffer.append(script);
		return buffer.toString();
	}
	
	public final String toDescription() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(name);
		buffer.append("\n");
		for (String feature : script.getFeatures()) {
			buffer.append("- ");
			buffer.append(feature);
			buffer.append("\n");
		}
		return buffer.toString();
	}
	
	public final Set<String> getFeatures() {
		return script.getFeatures();
	}
	
}
