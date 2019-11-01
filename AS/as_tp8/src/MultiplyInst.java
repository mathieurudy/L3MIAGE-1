package microcobol;

import java.util.*;

public class MultiplyInst extends Instruction {
	public MultiplyInst(Objet val, Variable var) {
		valeur = val;
		variable = var;
	}

	//retourne une représentation ASCII de l'instruction
	public String toString() {
		return "MULTIPLY " + valeur.toString() + " BY " + variable.toString();
	}

	private Objet valeur;
	private Variable variable;
}
