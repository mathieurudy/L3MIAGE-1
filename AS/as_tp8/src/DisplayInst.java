package microcobol;

import java.util.*;

public class DisplayInst extends Instruction {
	public DisplayInst(List<Objet> lo) {
		objets = lo;
	}

	public String toString() {
		String s = "DISPLAY";
		for (Objet o : objets) {
			// s.concat(" " + o.toString());
			s += " " + o;
		}
		return s;
	}

	private List<Objet> objets;
}
