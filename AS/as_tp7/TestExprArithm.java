import java.io.* ;
import java_cup.runtime.Symbol;
// classe principale pour tester l'analyse syntaxique
public class TestExprArithm {
  public static void main(String arg[]) throws Exception {
    Yylex yy ;
    if (arg.length > 0)
	    yy = new Yylex(new BufferedReader(new FileReader(arg[0]))) ;
    else
      yy = new Yylex(new BufferedReader(new InputStreamReader(System.in))) ;
    parser p = new parser(yy) ;
	  p.parse() ;
  }
}
