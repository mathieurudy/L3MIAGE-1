package microcobol;

import java.util.*;

public class Phrase {
	public Phrase(Instruction inst) {
		instruction = inst;
	}

	public String toString() {
		return instruction.toString() + ".\n";
	}

	private Instruction instruction;
}
