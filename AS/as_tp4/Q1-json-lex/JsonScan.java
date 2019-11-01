import java.io.* ;
import java_cup.runtime.Symbol;

public class JsonScan {
  public static void main(String arg[]) throws IOException, LexicalException {
      
      Yylex yy ;
      if (arg.length > 0)
	  yy = new Yylex(new BufferedReader(new FileReader(arg[0]))) ;
      else
	  yy = new Yylex(new BufferedReader(new InputStreamReader(System.in))) ;
      Symbol symb = yy.next_token();
      while (symb.sym != sym.EOF){
	  System.out.println("Read symbol: "+symb.sym + " = " + yy.yytext());
	  symb = yy.next_token();
      }
      System.out.println("End of scanning");
  }
}
