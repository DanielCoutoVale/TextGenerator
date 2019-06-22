package org.uppermodel.theory;

import java.util.HashMap;

public class AssociationMap extends HashMap<String, Association> {

	private static final String UNIT = "Unit".intern();
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -1397017788536250614L;
	
	public final Unit getUnit(Stratum stratum) {
		return this.getUnit(UNIT, stratum);
	}
	
	public final Unit getUnit(String function, Stratum stratum) {
		Association association = this.get(function);
		return association.getUnit(stratum);
	}

	public final void setUnit(Stratum stratum, Unit unit) {
		setUnit(UNIT, stratum, unit);
	}

	public final void setUnit(String function, Stratum stratum, Unit unit) {
		Association association = this.get(function);
		if (association == null) {
			association = new Association();
			this.put(function, association);
		}
		association.setUnit(stratum, unit);
	}
	
}
