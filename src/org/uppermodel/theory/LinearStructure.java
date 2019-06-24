package org.uppermodel.theory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LinearStructure extends Structure {
	
	public List<List<Integer>> orderings;
	
	public LinearStructure() {
		orderings = new LinkedList<>();
	}
	
	public LinearStructure(Structure structure) {
		super(structure);
		orderings = new LinkedList<>();
	}
	
	public LinearStructure(LinearStructure linearStructure) {
		super(linearStructure);
		orderings = linearStructure.orderings;
	}
	
	public String toString() {
		List<String> strings = new LinkedList<>();
		for (List<Integer> ordering : orderings) {
			StringBuffer buffer = new StringBuffer();
			for (Integer order : ordering) {
				Unit constituent = constituents.get(order);
				if (buffer.length() != 0) {
					buffer.append(" ");
				}
				buffer.append(constituent.toString());
			}
			strings.add(buffer.toString());
		}
		strings.sort((a, b) -> a.compareTo(b));
		StringBuffer buffer = new StringBuffer();
		for (String string : strings) {
			if (buffer.length() != 0) {
				buffer.append(" or ");
			}
			buffer.append("(");
			buffer.append(string);
			buffer.append(")");
		}
		return buffer.toString();
	}

	public String toString(Map<Unit, Unit> spellingMap) {
		List<String> strings = new LinkedList<>();
		for (List<Integer> ordering : orderings) {
			StringBuffer buffer = new StringBuffer();
			for (Integer order : ordering) {
				Unit constituent = constituents.get(order);
				if (buffer.length() != 0) {
					if (spaced()) buffer.append(" ");
				}
				buffer.append(constituent.toString(spellingMap));
			}
			strings.add(buffer.toString());
		}
		strings.sort((a, b) -> a.compareTo(b));
		StringBuffer buffer = new StringBuffer();
		for (String string : strings) {
			if (buffer.length() != 0) {
				buffer.append(" or ");
			}
			if (marked()) buffer.append("(");
			buffer.append(string);
			if (marked()) buffer.append(")");
		}
		return buffer.toString();
	}
	
	private boolean marked() {
		if (this.features.contains("words")) return false;
		if (this.features.contains("template")) return false;
		return true;
	}
	
	private boolean spaced() {
		if (this.features.contains("words")) return false;
		if (this.features.contains("template")) return false;
		return true;
	}
	
}
