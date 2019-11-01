import microcobol.*;

import java.util.*;

public class Test {

	public static void main(String args[]) {
		Programme test = new Programme(new IdDiv("test"), null, new ProcDiv(new ArrayList<Paragraphe>()));
		System.out.println(test.toString());
	}
}
