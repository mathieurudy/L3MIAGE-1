package microcobol;

import java.util.*;

public class AddInst extends Instruction {
	public AddInst(Objet val, Variable var) {
		valeur = val;
		variable = var;
	}

	//retourne une représentation ASCII de l'instruction
	public String toString() {
		return "ADD " + valeur.toString() + " TO " + variable.toString();
	}

	private Objet valeur;
	private Variable variable;
}
