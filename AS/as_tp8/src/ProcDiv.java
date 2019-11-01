package microcobol;

import java.util.*;

public class ProcDiv {
	public ProcDiv(List<Paragraphe> lp) {
		paragraphes = lp;
	}

	//retourne une représentation ASCII de l'instruction
	public String toString() {
		String s = "PROCEDURE DIVISION.\n";
		for (Paragraphe p : paragraphes) {
			// s.concat(p.toString());
			s += p;
		}
		return s;
	}

	private List<Paragraphe> paragraphes;
}
