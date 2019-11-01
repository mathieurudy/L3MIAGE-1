%%
%public
%class Mfa
%standalone
%8bit
%%
(a|b)*abb        { System.out.println("Pattern "+yytext()+" matches."); }
/*.*|\n {} */ /* pour n'afficher que les motifs qui correspondent, complique l'automate */
