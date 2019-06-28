package org.uppermodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.uppermodel.theory.AssociationMap;
import org.uppermodel.theory.Stratum;
import org.uppermodel.theory.Unit;

public class Moulder {
	
	private Map<String, Moulder> map1;
	
	private Map<String, String> map2;
	
	private Map<String, List<String>> featureMap;
	
	public Moulder() {
	}
	
	public Moulder(String fileName) throws IOException {
		featureMap = new HashMap<>();
		load(new FileInputStream(fileName));
	}
	
	public Moulder(File file) throws IOException {
		featureMap = new HashMap<>();
		load(new FileInputStream(file));
	}
	
	public Moulder(InputStream is) throws IOException {
		featureMap = new HashMap<>();
		load(is);
	}
	
	public Moulder(File[] files) throws IOException {
		featureMap = new HashMap<>();
		for (File file : files) {
			load(new FileInputStream(file));
		}
	}

	public final void load(InputStream is) throws IOException {
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while (null != (line = br.readLine())) {
			if (line.length() == 0) continue;
			String[] tokens = line.split(" ");
			String lex = tokens[0].intern();
			List<String> features = new LinkedList<>(Arrays.asList(tokens));
			features.remove(0);
			featureMap.put(lex, features);
			while (null != (line = br.readLine())) {
				if (line.length() == 0) break;
				tokens = line.split(" ");
				String form = tokens[0].intern();
				features = new LinkedList<>(Arrays.asList(tokens));
				features.remove(0);
				if (features.size() > 0) features.remove(0);
				features.add(lex);
				features.sort((a, b) -> a.compareTo(b));
				put(form, 0, features.toArray(new String[0]));
			}
		}
		br.close();
	}
	
	private final void put(String literal, int index, String... features) {
		if (features.length - index == 1) {
			String feature = features[index];
			if (map2 == null) {
				map2 = new HashMap<>();
			}
			map2.put(feature, literal);
		} else if (features.length - index > 1) {
			String feature = features[index];
			if (map1 == null) {
				map1 = new HashMap<>();
			}
			Moulder moulder = map1.get(feature);
			if (moulder == null) {
				moulder = new Moulder();
				map1.put(feature, moulder);
			}
			moulder.put(literal, index + 1, features);
		}
	}
	
	public String get(List<String> features) {
		features.sort((a, b) -> a.compareTo(b));
		return get(0, features.toArray(new String[0]));
	}
	
	public final String get(String... features) {
		return get(new LinkedList<>(Arrays.asList(features)));
	}
	
	private final String get(int index, String... features) {
		if (features.length - index == 1) {
			String feature = features[index];
			if (map2 == null) {
				return null;
			}
			return map2.get(feature);
		} else if (features.length - index > 1) {
			String feature = features[index];
			if (map1 == null) {
				return null;
			}
			Moulder moulder = map1.get(feature);
			if (moulder == null) {
				return null;
			}
			return moulder.get(index + 1, features);
		}
		return null;
	}
	
	public String toString() {
		return "(" + map2 + ":" + map1 + ")";
	}

	public final boolean checkItemClassAttribute(AssociationMap associationMap, String function, String itemClass) {
		Unit calling = associationMap.getUnit(function, Stratum.calling);
		if (calling == null) return false;
		List<String> features = featureMap.get(calling.id);
		if (features == null) return false;
		return features.contains(itemClass);
	}
}
