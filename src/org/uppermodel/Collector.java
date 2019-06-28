package org.uppermodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.uppermodel.expression.Expression;
import org.uppermodel.expression.ExpressionTokenList;
import org.uppermodel.expression.ExpressionTokens;
import org.uppermodel.expression.Gate;
import org.uppermodel.theory.AssociationMap;
import org.uppermodel.theory.Statement;
import org.uppermodel.theory.Stratum;
import org.uppermodel.theory.Unit;

public class Collector {

	private Stratum stratum;
	
	private List<Gate> gates;
	
	public Collector(Stratum stratum, String fileName) throws IOException {
		this.stratum = stratum;
		this.gates = new LinkedList<>();
		load(new FileInputStream(fileName));
	}
	
	public Collector(Stratum stratum, File file) throws IOException {
		this.stratum = stratum;
		this.gates = new LinkedList<>();
		load(new FileInputStream(file));
	}
	
	public Collector(Stratum stratum, InputStream is) throws IOException {
		this.stratum = stratum;
		this.gates = new LinkedList<>();
		load(is);
	}
	
	public Collector(Stratum stratum, File[] files) throws IOException {
		this.stratum = stratum;
		this.gates = new LinkedList<>();
		for (File file : files) {
			load(new FileInputStream(file));
		}
	}

	public final void load(InputStream is) throws IOException {
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		Gate gate = null;
		while (null != (line = br.readLine())) {
			line = line.trim().intern();
			if (gate == null) {
				gate = makeGate(line);
			} else {
				if (line.startsWith("-")) {
					line = line.substring(1).trim().intern();
					Statement statement = new Statement(line);
					if (!statement.valid()) {
						System.err.println("INVALID STATEMENT: " + statement);
					}
					gate.statements.add(statement);
				} else {
					gate = makeGate(line);
				}
			}
		}
		br.close();
	}

	private Gate makeGate(String line) {
		if (line.length() != 0) {
			if (!line.startsWith("-")) {
				Expression entryCondition = makeEntryCondition(line);
				Gate gate = new Gate(entryCondition);
				gates.add(gate);
				return gate;
			} else {
				System.err.println("IGNORED: " + line);
			}
		}
		return null;
	}
	
	private Expression makeEntryCondition(String line) {
		List<String> tokens = new LinkedList<>(Arrays.asList(line.split(" ")));
		for (int i = 0; i < tokens.size(); i++) {
			String token = tokens.get(i);
			while (token.length() > 1) {
				if (token.startsWith("(")) {
					tokens.set(i, token.substring(1));
					token = "(";
					tokens.add(i, token);
				} else
				if (token.endsWith(")")) {
					tokens.set(i, ")");
					token = token.substring(0, token.length() - 1);
					tokens.add(i, token);
				} else {
					break;
				}
			}
		}
		ExpressionTokens exp = new ExpressionTokenList(tokens);
		return exp.makeExpression();
	}

	public void collect(AssociationMap associationMap) {
		Unit unit = associationMap.getUnit(this.stratum);
		Set<String> features = new HashSet<>(unit.features);
		for (Gate gate : gates) {
			if (gate.enterable(features)) {
				unit.statements.addAll(gate.statements);
			}
		}
	}

}
