package org.uppermodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.uppermodel.theory.Association;
import org.uppermodel.theory.AssociationMap;
import org.uppermodel.theory.LinearStructure;
import org.uppermodel.theory.Stratum;
import org.uppermodel.theory.Structure;
import org.uppermodel.theory.Unit;

public class Inquirer {
	
	private final Stratum stratum;
	
	public Inquirer(Stratum stratum, File[] files) throws IOException {
		this.stratum = stratum;
		for (File file : files) {
			load(new FileInputStream(file));
		}
	}
	
	private final void load(InputStream is) throws IOException {
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		
		while (null != (line = br.readLine())) {
			line = line.trim();
			if (line.length() == 0) continue;
			String[] tokens = line.split(" ");
			String tax = tokens[0];
			LinearStructure calling = new LinearStructure(tax);
			for (int i = 1; i < tokens.length; i++) {
				String lex = tokens[i];
				calling.constituents.add(new Unit(lex));
			}
			this.addCalling(tax, calling);			
		}
		br.close();

	}

	public Association identify(Association association, String function) {
		Unit unit = association.getUnit(stratum);
		if (unit == null) throw new ExecutionError();
		if (!unit.isComposed()) throw new ExecutionError();
		Structure structure = (Structure) unit;
		for (Unit constituent : structure.constituents) {
			if (constituent.functions.contains(function)) {
				Association newAssociation = new Association();
				newAssociation.setUnit(stratum, constituent);
				return newAssociation;
			}
		}
		throw new Error();
	}
	
	public boolean checkClassAttribute(AssociationMap associationMap, String function, String classAttribute) {
		if (classAttribute.equals("item")) {
			Unit calling = associationMap.getUnit(function, Stratum.calling);
			return calling != null;
		}
		Unit unit = associationMap.getUnit(function, stratum);
		if (unit == null) return false;
		return unit.features.contains(classAttribute);
	}
	
	public Map<String, Set<LinearStructure>> callingMap = new HashMap<>();
	
	private final void addCalling(String semanticFeature, LinearStructure calling) {
		Set<LinearStructure> callings = callingMap.get(semanticFeature);
		if (callings == null) {
			callings = new HashSet<LinearStructure>();
			callingMap.put(semanticFeature, callings);
		}
		callings.add(calling);
	}

	public final Unit resolveName(Unit semanticUnit) {
		for (String semanticFeature : semanticUnit.features) {
			if (callingMap.containsKey(semanticFeature)) {
				Set<LinearStructure> callings = callingMap.get(semanticFeature);
				LinearStructure calling = callings.iterator().next();
				for (Unit constituent : calling.constituents) {
					if (constituent.id.startsWith("base:")) {
						return constituent;
					}
				}
			}
		}
		throw new ExecutionError();
	}

	public final Unit resolveTypedName(Unit semanticUnit, String lexicalFeature) {
		for (String semanticFeature : semanticUnit.features) {
			if (callingMap.containsKey(semanticFeature)) {
				Set<LinearStructure> callings = callingMap.get(semanticFeature);
				for (Unit calling : callings) {
					if (calling.features.contains(lexicalFeature)) {
						return calling;
					}
				}
				throw new ExecutionError();
			}
		}
		throw new ExecutionError();
	}
	
}
