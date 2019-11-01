package microcobol;

import java.util.*;

public class Variable extends Objet {
	public Variable(String s) {
		nom = s;
	}
	public String toString() {
		return nom;
	}

	private String nom;
}
