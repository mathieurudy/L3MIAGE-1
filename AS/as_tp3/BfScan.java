import java.io.* ;
import java_cup.runtime.Symbol;

public class BfScan {
  public static void main(String arg[]) throws IOException {
int i=0;
      Bf yy ;
      if (arg.length > 0)
	       yy = new Bf(new BufferedReader(new FileReader(arg[0]))) ;
      else
	       yy = new Bf(new BufferedReader(new InputStreamReader(System.in))) ;
      int symb = yy.yylex();
      while (symb != sym.EOF){
	       System.out.println(yy.yytext()+"//"+(i++));
	       symb = yy.yylex();
      }
  }
}
