package org.uppermodel.expression;

public class ExpressionToken implements ExpressionTokens {
	
	String token;
	
	public ExpressionToken(String token) {
		this.token = token.intern();
	}

	@Override
	public Expression makeExpression() {
		return new Feature(token);
	}
	
	public String toString() {
		return token;
	}
	
}
