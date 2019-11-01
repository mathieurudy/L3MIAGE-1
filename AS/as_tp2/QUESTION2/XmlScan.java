import java.io.* ;
import java_cup.runtime.Symbol;

public class XmlScan {
  public static void main(String arg[]) throws IOException {
      
      Yylex yy ;
      if (arg.length > 0)
	  yy = new Yylex(new BufferedReader(new FileReader(arg[0]))) ;
      else
	  yy = new Yylex(new BufferedReader(new InputStreamReader(System.in))) ;
      Symbol symb = yy.next_token();

      while (symb.sym != sym.EOF){
	  if (symb.sym == sym.LT) {
	      symb = yy.next_token();
	      if (symb.sym == sym.NAME) {
		  System.out.println("Start element: "+symb.value);
                  do {
		   symb = yy.next_token();
                  } while ((symb.sym != sym.SLASHGT) && (symb.sym != sym.GT));
                  if (symb.sym == sym.SLASHGT)  System.out.println("End element");
	      }
	  } else if (symb.sym == sym.LTSLASH) {
	      symb = yy.next_token();
	      if (symb.sym == sym.NAME) {
		  System.out.println("End element: "+symb.value);
                   do {
		   symb = yy.next_token();
                  } while (symb.sym != sym.GT);
	      }
	  }
	    else  symb = yy.next_token();
      }
      System.out.println("End of scanning");
  }
}
