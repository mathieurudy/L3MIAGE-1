package microcobol;

import java.util.*;

public class SubstractInst extends Instruction {
	public SubstractInst(Objet val, Variable var) {
		valeur = val;
		variable = var;
	}

	//retourne une représentation ASCII de l'instruction
	public String toString() {
		return "SUBSTRACT " + valeur.toString() + " FROM " + variable.toString();
	}

	private Objet valeur;
	private Variable variable;
}
