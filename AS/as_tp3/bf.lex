import java_cup.runtime.*;

%%
%class Bf
%standalone
%unicode
%char
%line
%column
%public

%init{
	System.out.println("public class Main {");
	System.out.println("public static void main(String[] args) {");
	System.out.println("int p = 0;");
	System.out.println("byte[] ruban = new byte[4096];");
%init}

%eofval{
	System.out.println("System.out.flush();");
	System.out.println("}");
	System.out.println("}");
	return sym.EOF;
%eofval}

%{
  public int line() { return yyline; }
  public int col() { return yycolumn; }
  public int charpos() { return yychar; }
%}

greater_than = ">"
less_than = "<"
plus = "+"
comma = ","
minus = "-"
decimal_point = "."
begin_array = "["
end_array = "]"


%%

{greater_than} {System.out.println("p++;");}
{less_than} {System.out.println("p--;");}
{plus} {System.out.println("ruban[p]++;");}
{minus} {System.out.println("ruban[p]--;");}
{comma} {System.out.println("System.in.read(ruban, p, 1);");}
{decimal_point} {System.out.println("System.out.write(ruban[p]);");}
{begin_array} {System.out.println("while (ruban[p]!=0) {");}
{end_array} {System.out.println("}");}
. {System.out.println("//");}
