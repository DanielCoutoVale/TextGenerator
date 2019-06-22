package org.uppermodel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.uppermodel.theory.Association;
import org.uppermodel.theory.AssociationMap;
import org.uppermodel.theory.Stratum;
import org.uppermodel.theory.Structure;
import org.uppermodel.theory.Unit;

public class Inquirer {
	
	private final Stratum stratum;
	
	public Inquirer(Stratum stratum) {
		this.stratum = stratum;
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
	
	public Map<String, Set<Unit>> callingMap = new HashMap<>();
	
	public final void addCallings(String semanticFeature, Set<Unit> lexicalUnits) {
		this.callingMap.put(semanticFeature, lexicalUnits);
	}

	public final Unit resolveName(Unit semanticUnit) {
		for (String semanticFeature : semanticUnit.features) {
			if (callingMap.containsKey(semanticFeature)) {
				Set<Unit> callings = callingMap.get(semanticFeature);
				return callings.iterator().next();
			}
		}
		throw new ExecutionError();
	}

	public final Unit resolveTypedName(Unit semanticUnit, String lexicalFeature) {
		for (String semanticFeature : semanticUnit.features) {
			if (callingMap.containsKey(semanticFeature)) {
				Set<Unit> callings = callingMap.get(semanticFeature);
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
