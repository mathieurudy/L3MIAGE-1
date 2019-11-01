package microcobol;

import java.util.*;

public class StopInst extends Instruction {
	public StopInst() {
	}

	//retourne une représentation ASCII de l'instruction
	public String toString() {
		return "STOP RUN";
	}
}
