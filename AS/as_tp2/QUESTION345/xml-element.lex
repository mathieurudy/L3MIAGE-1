
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


LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]


CharData = !(![^<&]*|([^<&]*']]>'[^<&]*))
Content = {CharData}?({CharData}?)*
NameStartChar = ":"|[A-Z]|"_"|[a-z]|['\uC0'-'\uD6']|['\uD8'-'\uF6']|['\uF8'-'\u2FF']|['\u370'-'\u37D']|['\u37F'-'\u1FFF']|['\u200C'-'\u200D']|['\u2070'-'\u218F']|['\u2C00'-'\u2FEF']|['\u3001'-'\uD7FF']|['\uF900'-'\uFDCF']|['\uFDF0'-'\uFFFD']|['\U10000'-'\UEFFFF']
NameChar = {NameStartChar}|"-"|"."|[0-9]|'\uB7'|['\u0300'-'\u036F']|['\u203F'-'\u2040']
Name = {NameStartChar}({NameChar})*

%states INELEMENT

%%
{WhiteSpace} {}
"<" {return new Symbol(sym.LT) ;}
	"</" {return new Symbol(sym.LTSLASH) ;}
	">" {return new Symbol(sym.GT) ;}
	"/>" {return new Symbol(sym.SLASHGT) ;}
<YYINITIAL> {Name} {return new Symbol(sym.NAME, yytext()) ; }
<INELEMENT> {Content} {return new Symbol(sym.CONTENT, yytext()) ;} 
. {return new Symbol(sym.UNKNOWN) ;}
