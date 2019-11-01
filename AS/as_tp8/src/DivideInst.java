package microcobol;

import java.util.*;

public class DivideInst extends Instruction {
	public DivideInst(Objet val, Variable var) {
		valeur = val;
		variable = var;
	}

	//retourne une représentation ASCII de l'instruction
	public String toString() {
		return "DIVIDE " + valeur.toString() + " INTO " + variable.toString();
	}

	private Objet valeur;
	private Variable variable;
}
