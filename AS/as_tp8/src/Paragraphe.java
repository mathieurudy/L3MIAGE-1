package microcobol;

import java.util.*;

public class Paragraphe {
	public Paragraphe(String s, List<Phrase> lp) {
		nom = s;
		phrases = lp;
	}

	public String toString() {
		String s = nom + ":\n";
		for (Phrase p : phrases) {
			// s.concat(p.toString());
			s += p;
		}
		return s;
	}

	private String nom;
	private List<Phrase> phrases;
}
