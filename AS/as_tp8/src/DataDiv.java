package microcobol;

import java.util.*;

public class DataDiv {

	public DataDiv(WorkingSect ws) {
		workingsect = ws;
	}

	public String toString() {
		return "DATA DIVISION.\n" + workingsect.toString();
	}

	private WorkingSect workingsect;
}
