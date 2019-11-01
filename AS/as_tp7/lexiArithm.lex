import java.io.* ;
import java_cup.runtime.* ;

%%

%full
%cup
%ignorecase
%eofval{
  return new Symbol(sym.EOF) ;
%eofval}
%yylexthrow{
  LexicalException
%yylexthrow}
%line

SEPARATEUR = " "|\n|\t|\r
LETTRE = [A-Za-z]
CHIFFRE = [0-9]
IDENTIFICATEUR = {LETTRE}({LETTRE}|{CHIFFRE}|"_")*
ENTIER = {CHIFFRE}+
COMMENTAIRE = "//".*
%% 
{COMMENTAIRE}|{SEPARATEUR} {}
"set" {return new Symbol(sym.SET) ; }
"=" {return new Symbol(sym.EGAL) ; }
";" {return new Symbol(sym.POINTV);}
{IDENTIFICATEUR} {return new Symbol(sym.IDENT, yytext());}
{ENTIER} {return new Symbol(sym.ENTIER, new Integer(yytext()));}
"(" {return new Symbol(sym.PAROUV);}
")" {return new Symbol(sym.PARFER);}
"+" {return new Symbol(sym.PLUS);}
"-" {return new Symbol(sym.MOINS);}
"*" {return new Symbol(sym.MULT);}
"/" {return new Symbol(sym.DIV);}
"!" {return new Symbol(sym.EXCLA);}
. {throw new LexicalException("token inconnu : "+yytext()+ " ligne "+yyline);}






