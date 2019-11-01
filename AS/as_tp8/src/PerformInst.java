package microcobol;

import java.util.*;

public class PerformInst extends Instruction {
	public PerformInst(Condition c, List<Instruction> t) {
		condition = c;
		perfTreat = t;
	}

	public String toString() {
		String s = "PERFORM UNTIL " + condition.toString();
		for (Instruction i : perfTreat) {
			s += i + "\n";
		}
		s+="END-PERFORM";
		return s;
	}

	private Condition condition;
	private List<Instruction> perfTreat;
}
