package microcobol;

import java.util.*;

enum OpComp {
	EGAL ("="),
	SUP (">"),
	INF ("<");

	private String nom;

	OpComp(String s) {
		nom = s;
	}

	public String toString() {
		return nom;
	}
}

public class Condition {
	public Condition(Objet x, Objet y, Boolean neg, String op)
	{
		objet1 = x;
		objet2 = y;
		negation = neg;
		switch(op) {
			case "=":
				opcomp = OpComp.EGAL;
				break;
			case ">":
				opcomp = OpComp.SUP;
				break;
			case "<":
				opcomp = OpComp.INF;
				break;
			default:
		}

	}

	public String toString() {
		return objet1.toString() + " IS " + (negation?"NOT ":"") + opcomp.toString() + " " + objet2.toString();
	}
	private Objet objet1;
	private Objet objet2;
	private Boolean negation;
	private OpComp opcomp;
}
