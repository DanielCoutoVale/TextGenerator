package org.uppermodel.theory;

import java.util.LinkedList;
import java.util.List;

public class Structure extends Unit {
	
	public List<Unit> constituents;
	
	public Structure() {
		constituents = new LinkedList<>();
	}
	
	public Structure(Unit unit) {
		super(unit);
		constituents = new LinkedList<>();
	}
	
	public Structure(Structure structure) {
		super(structure);
		constituents = structure.constituents;
	}

	public Structure(String id) {
		super(id);
		constituents = new LinkedList<>();
	}
	
}
