package org.uppermodel.theory;

public class Association {
	
	public Unit meaning;
	
	public Unit wording;
	
	public Unit calling;
	
	public Unit spelling;
	
	public Unit writing;

	public Unit getUnit(Stratum stratum) {
		switch(stratum) {
		case meaning:
			return meaning;
		case wording:
			return wording;
		case calling:
			return calling;
		case spelling:
			return spelling;
		case writing:
			return writing;
		default:
			return null;
		}
	}

	public void setUnit(Stratum stratum, Unit unit) {
		switch(stratum) {
		case meaning:
			meaning = unit;
			break;
		case wording:
			wording = unit;
			break;
		case calling:
			calling = unit;
			break;
		case spelling:
			spelling = unit;
			break;
		case writing:
			writing = unit;
			break;
		}
	}
	
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{\n");
		buffer.append("  meaning: ");
		buffer.append(meaning);
		buffer.append("\n");
		buffer.append("  wording: ");
		buffer.append(wording);
		buffer.append("\n");
		buffer.append("  calling: ");
		buffer.append(calling);
		buffer.append("\n");
		buffer.append("  spelling: ");
		buffer.append(spelling);
		buffer.append("\n");
		buffer.append("  writing: ");
		buffer.append(writing);
		buffer.append("\n}\n");
		return buffer.toString();
	}
	
}
