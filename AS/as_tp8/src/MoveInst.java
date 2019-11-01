package microcobol;

import java.util.*;

public class MoveInst extends Instruction {
	public MoveInst(Objet val, Variable var) {
		valeur = val;
		variable = var;
	}

	//retourne une représentation ASCII de l'instruction
	public String toString() {
		return "MOVE " + valeur.toString() + " TO " + variable.toString();
	}

	private Objet valeur;
	private Variable variable;
}
