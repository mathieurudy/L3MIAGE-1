import java.io.* ;
import java_cup.runtime.Symbol;

// classe principale pour tester
public class TestLexiArithm {
  public static void main(String arg[]) throws IOException, LexicalException {
 
   Yylex yy ;
    if (arg.length > 0)
	    yy = new Yylex(new BufferedReader(new FileReader(arg[0]))) ;
    else
      yy = new Yylex(new BufferedReader(new InputStreamReader(System.in))) ;
	  Symbol symb = yy.next_token();
    // la fin de fichier est codée par un Symbol d'unite lexicale sym.EOF
	  while (symb.sym != sym.EOF){
      System.out.println(symb) ;
      symb = yy.next_token();
    }
    System.out.println("c'est fini");
  }
}
