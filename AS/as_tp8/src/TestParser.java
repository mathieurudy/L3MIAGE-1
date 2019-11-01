import java.io.* ;
import java_cup.runtime.Symbol;
import microcobol.*;

// classe principale pour tester l'analyse syntaxique
public class TestParser {
  public static void main(String arg[]) throws Exception {
    Yylex yy ;
    Programme prog;
    if (arg.length > 0)
            yy = new Yylex(new BufferedReader(new FileReader(arg[0]))) ;
    else
      yy = new Yylex(new BufferedReader(new InputStreamReader(System.in))) ;
    parser p = new parser(yy) ;
    prog = (Programme)p.parse().value;
    System.out.println(prog.toString());
  }
}
