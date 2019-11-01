package microcobol;

import java.util.*;

public class Nombre extends Litteral {
	public Nombre(String s)
	{
		valeur = s;
		/* pour supprimer le premier et le dernier quote */
	}

	public String toString() {
		return valeur;
	}
	private String valeur;
}
