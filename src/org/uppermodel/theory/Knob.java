package org.uppermodel.theory;


import java.util.HashSet;
import java.util.Set;

public class Knob {
	
	public Set<String> functions;
	public Set<Knob> children;
	public Knob parent;
	public Unit unit;
	
	public Knob(String function) {
		functions = new HashSet<>();
		children = new HashSet<>();
		functions.add(function);
	}
	
	public Knob(Knob hub1, Knob hub2) {
		functions = new HashSet<>();
		children = new HashSet<>();
		functions.addAll(hub1.functions);
		functions.addAll(hub2.functions);
		children.addAll(hub1.children);
		children.addAll(hub2.children);
	}

	public void addChild(Knob child) {
		this.children.add(child);
	}

	public void setParent(Knob parent) {
		this.parent = parent;
	}

}
