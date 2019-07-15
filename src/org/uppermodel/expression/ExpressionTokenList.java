package org.uppermodel.expression;

import java.util.LinkedList;
import java.util.List;

public class ExpressionTokenList extends LinkedList<ExpressionTokens> implements ExpressionTokens  {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -6172964381587717677L;
	
	private static final String NOT = "not".intern();
	
	private static final String AND = "and".intern();
	
	private static final String OR = "or".intern();
	
	public ExpressionTokenList(List<String> tokens) {
		loop:
		for (int i = 0; i < tokens.size(); i++) {
			String token = tokens.get(i);
			if (token.equals("(")) {
				int count = 1;
				for (int j = i + 1; j < tokens.size(); j++) {
					token = tokens.get(j);
					if (token.equals("(")) {
						count++;
					}
					if (token.equals(")")) {
						count--;
					}
					if (count == 0) {
						ExpressionTokens exp = new ExpressionTokenList(tokens.subList(i + 1, j));
						this.add(exp);
						i = j;
						continue loop;
					}
				}
				System.err.println("ERROR: " + tokens);
				break loop;
			} else {
				this.add(new ExpressionToken(token));
			}
		}
	}
	
	@Override
	public Expression makeExpression() {
		if (this.size() == 1) {
			ExpressionTokens exp = this.get(0);
			return exp.makeExpression();
		} else if (this.size() == 2) {
			if (!(this.get(0) instanceof ExpressionToken)) return new Garbage();
			ExpressionToken expA = (ExpressionToken) this.get(0);
			if (expA.token != NOT) return new Garbage();
			ExpressionTokens expB = this.get(1);
			return new Not(expB.makeExpression());
		} else if (this.size() % 2 == 1) {
			int type = 0;
			for (int i = 1; i < this.size(); i+=2) {
				if (!(this.get(i) instanceof ExpressionToken)) return new Garbage();
				ExpressionToken exp = (ExpressionToken) this.get(i);
				if (exp.token != AND && exp.token != OR) return new Garbage();
				if (type == 0 && exp.token == AND) type = 1;
				if (type == 0 && exp.token == OR) type = 2;
				if (type == 2 && exp.token == AND) return new Garbage();
				if (type == 1 && exp.token == OR) return new Garbage();
			}
			List<Expression> members = new LinkedList<>();
			for (int i = 0; i < this.size(); i+=2) {
				ExpressionTokens exp = this.get(i);
				members.add(exp.makeExpression());
			}
			if (type == 1) return new And(members);
			if (type == 2) return new Or(members);
		}
		return new Garbage();
	}
	
}
