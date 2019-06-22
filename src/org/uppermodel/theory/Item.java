package org.uppermodel.theory;

import java.util.HashSet;
import java.util.Set;

/**
 * A lexical item.
 * 
 * @author Daniel Couto-Vale
 */
public class Item {
	
	public Set<String> functions;

	public String key;
	
	public Item() {
		functions = new HashSet<>();
	}
	
}
