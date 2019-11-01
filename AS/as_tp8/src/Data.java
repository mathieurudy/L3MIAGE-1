package microcobol;

import java.util.*;

public class Data {
	public Data(String id, String pic) {
		nom = id;
		image = pic;
	}

	public String toString() {
		return "77 " + nom + " PIC " + image + ".\n";
	}

	private String nom;
	private String image;
}
