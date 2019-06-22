package org.uppermodel.theory;

import java.util.HashSet;
import java.util.Set;

/**
 * A linguistic concept identified by a key. A linguistic concept is associated
 * with one or more names (synonyms) and it is associated with a particular name
 * in the text.
 * 
 * @author Daniel Couto-Vale
 */
public class Concept {
	
	public Set<String> functions;
	
	public String key;
	
	public Set<String> names;
	
	public String name;
	
	public Concept() {
		this.functions = new HashSet<String>();
		this.names = new HashSet<String>();
	}
	
}
