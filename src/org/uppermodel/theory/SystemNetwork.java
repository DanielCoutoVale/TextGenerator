package org.uppermodel.theory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class SystemNetwork {
	
	public final Stratum stratum;
	
	public final List<System> systems;
	
	public SystemNetwork(Stratum stratum, String fileName) throws IOException {
		this.stratum = stratum;
		this.systems = new LinkedList<>();
		load(new FileInputStream(fileName));
	}
	
	public SystemNetwork(Stratum stratum, File file) throws IOException {
		this.stratum = stratum;
		this.systems = new LinkedList<>();
		load(new FileInputStream(file));
	}
	
	public SystemNetwork(Stratum stratum, InputStream is) throws IOException {
		this.stratum = stratum;
		this.systems = new LinkedList<>();
		load(is);
	}
	
	private void load(InputStream is) throws IOException {
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		List<String> lines = new LinkedList<>();
		while (null != (line = br.readLine())) {
			if (line.length() == 0) {
				if (lines.size() > 0) {
					System system = new System(stratum, lines);
					this.systems.add(system);
					lines = new LinkedList<>();
				}
			} else {
				lines.add(line);
			}
		}
		if (lines.size() > 0) {
			System system = new System(stratum, lines);
			this.systems.add(system);
		}
		br.close();
	}
	
	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		for (System system : systems) {
			buffer.append("\n");
			buffer.append(system);
		}
		return buffer.toString();
	}

	public final String toDescription() {
		StringBuffer buffer = new StringBuffer();
		for (System system : systems) {
			buffer.append("\n");
			buffer.append(system.toDescription());
		}
		return buffer.toString();
	}
	
}
