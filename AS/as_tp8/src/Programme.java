package microcobol;

import java.util.*;

public class Programme {
	private IdDiv iddiv;
	private DataDiv datadiv;
	private ProcDiv procdiv;

	public Programme(IdDiv id, DataDiv dd, ProcDiv pd) {
		this.iddiv = id;
		this.datadiv = dd;
		this.procdiv = pd;
	}

	//retourne une représentation ASCII de l'instruction
	public String toString() {
		return iddiv.toString() + ((datadiv!=null)?datadiv.toString():"")
			+ ((procdiv!=null)?procdiv.toString():"") +
			"END PROGRAMME " + iddiv.nom() + ".\n";
	}
}
