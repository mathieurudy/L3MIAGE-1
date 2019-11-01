package microcobol;

import java.util.*;

public class Chaine extends Litteral {
	public Chaine(String s)
	{
		valeur = s.substring(1,s.length()-1);
		/* pour supprimer le premier et le dernier quote */
	}

	public String toString() {
		return "\"" + valeur + "\"";
	}
	private String valeur;
}
