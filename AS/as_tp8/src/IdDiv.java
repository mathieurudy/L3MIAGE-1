package microcobol;

import java.util.*;

public class IdDiv {
	public IdDiv(String name) {
		program_name = name;
	}

	public String nom() {
		return program_name;
	}

	//retourne une représentation ASCII de l'instruction
	public String toString() {
		return "IDENTIFICATION DIVISION.\n  PROGRAM-ID " + program_name + ".\n";
	}

	private String program_name;
}
