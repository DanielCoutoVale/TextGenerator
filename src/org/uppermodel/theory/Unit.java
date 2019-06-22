package org.uppermodel.theory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Unit {
	
	private static int index = 0;
	
	private static String newId() {
		return "#" + (++index);
	}
	
	public String id;
	
	public final List<String> functions;
	
	public final Set<String> features;
	
	public final List<Statement> statements;
	
	public Unit(String id, HashSet<String> features) {
		this.id = id;
		this.functions = new LinkedList<>();
		this.features = features;
		this.statements = new LinkedList<>();
	}
	
	public Unit(String id) {
		this.id = id;
		this.functions = new LinkedList<>();
		this.features = new HashSet<>();
		this.statements = new LinkedList<>();
	}
	
	public Unit() {
		this.id = newId();
		this.functions = new LinkedList<>();
		this.features = new HashSet<>();
		this.statements = new LinkedList<>();
	}
	
	public Unit(Unit unit) {
		this.id = unit.id;
		this.functions = new LinkedList<>(unit.functions);
		this.features = new HashSet<>(unit.features);
		this.statements = new LinkedList<>(unit.statements);
	}
	
	public Unit(Knob knob) {
		this.id = newId();
		this.functions = new LinkedList<>(knob.functions);
		this.features = new HashSet<>();
		this.statements = new LinkedList<>();
		knob.unit = this;
	}
	
	public Unit(List<Statement> statements) {
		this.id = newId();
		this.functions = new LinkedList<>();
		this.features = new HashSet<>();
		this.statements = new LinkedList<>(statements);
	}

	public String toString() {
		List<String> features = new LinkedList<>(this.features);
		if (statements.size() > 0) {
			return statements.toString();
		}
		functions.sort((a, b) -> a.compareTo(b));
		features.sort((a, b) -> a.compareTo(b));
		features.removeIf(a -> a.endsWith("#complement"));
		StringBuffer buffer = new StringBuffer();
		functions.forEach(function -> buffer.append(function + " "));
		if (id != null) buffer.append(id + " ");
		features.forEach(feature -> buffer.append(feature + " "));
		return "[" + buffer.toString().trim() + "]";
	}

	public boolean isComposed() {
		return this instanceof Structure;
	}
	
	public boolean isLinear() {
		return this instanceof LinearStructure;
	}

	public String toString(Map<Unit, Unit> spellingMap) {
		Unit spelling = spellingMap.get(this);
		if (spelling == null) return this.toString();
		return spelling.id;
	}
	
}
