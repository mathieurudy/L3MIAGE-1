import java_cup.runtime.* ;

%%

%cup
%unicode
%char
%line
%column
%public

%yylexthrow{
  LexicalException
%yylexthrow}

%{
  public int line() { return yyline; }
  public int col() { return yycolumn; }
  public int charpos() { return yychar; }
%}

 /* JFlex Macro-definitions */
ws = (\u0020 | \u0009 | \u000A | \u000D)*
begin_array  = {ws} \u005B {ws}
begin_object = {ws} \u007B {ws}
end_array    = {ws} \u005D {ws}
end_object = {ws} \u007D {ws}
name_separator = {ws} \u003A {ws}
value_separator = {ws} \u002C {ws}
false = \u0066 \u0061 \u006C \u0073 \u0065
null = \u006E \u0075 \u006C \006C
true = \u0074 \u0072 \u0075 \u0065
number = {minus}? {int} {frac}? {exp}?
decimal_point = \u002E
digit1_9 = [\u0031-\u0039]
e = (\u0065 | \u0045)
exp = {e} ( {minus} | {plus} )? ({DIGIT})+
frac = {decimal_point} {DIGIT}+
int = {zero} | ({digit1_9} {DIGIT}*)
minus = \u002D
plus = \u002B
zero = \u0030
string = {quotation_mark} ({char})* {quotation_mark}
char = {unescaped} | {escape} (\u0022 | \u005C | \u002F | \u0062 | \u0066 | \u006E | \u0072 | \u0074 | \u0075 ({HEXDIG}{4}) )
escape = \u005C
quotation_mark = \u0022
unescaped = [\u0020-\u0021] | [\u0023-\u005B] | [\U0000005D-\U0010FFFF]
DIGIT = [\u0030-\u0039]
HEXDIG = {DIGIT} | "A" | "B" | "C" | "D" | "E" | "F"
%%
{begin_array} {return new Symbol(sym.BEGINARRAY);}
{end_array} {return new Symbol(sym.ENDARRAY);}
{begin_object} {return new Symbol(sym.BEGINOBJECT);}
{end_object} {return new Symbol(sym.ENDOBJECT);}
{name_separator} {return new Symbol(sym.NAMESEPARATOR);}
{value_separator} {return new Symbol(sym.VALUESEPARATOR);}
{false} {return new Symbol(sym.FALSE);}
{true} {return new Symbol(sym.TRUE);}
{null} {return new Symbol(sym.NULL);}
{number} {return new Symbol(sym.NUMBER);}
{string} {return new Symbol(sym.STRING);}
. {}
