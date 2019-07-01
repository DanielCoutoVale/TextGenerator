package org.uppermodel;

import java.util.HashSet;
import java.util.Set;

import org.uppermodel.theory.AssociationMap;
import org.uppermodel.theory.System;
import org.uppermodel.theory.SystemNetwork;
import org.uppermodel.theory.Unit;

public class Traverser {
	
	private final SystemNetwork network;
	
	private final Inquirer inquirer;

	private Moulder moulder;
	
	public Traverser(SystemNetwork network, Inquirer inquirer, Moulder moulder) {
		this.network = network;
		this.inquirer = inquirer;
		this.moulder = moulder;
	}
	
	public final void traverse(AssociationMap associationMap) {
		Set<System> systems = new HashSet<>(this.network.systems);
		Set<System> entered = new HashSet<>();
		Unit unit = associationMap.getUnit(this.network.stratum);
		do {
			for (System system : systems) {
				if (system.enterable(unit.features)) {
					system.executeScript(associationMap, this.inquirer, this.moulder);
					entered.add(system);
				}
			}
		} while (systems.removeAll(entered));
	}
	
	public final String toNetworkDescription() {
		return this.network.toDescription();
	}
	
}
