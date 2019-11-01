Utilisation de CUP
==================

Ce TP a pour objectif de vous initier à l’utilisation de générateurs d’analyseurs syntaxiques, au travers de l’implémentation d’une calculette. Ces générateurs construisent un programme capable de reconnaître les phrases d’une grammaire dont la spécification est donnée dans un fichier. L’utilisateur ajoute généralement des **actions sémantiques** qui seront exécutées au fur et à mesure de cette reconnaissance.

Nous allons utiliser ici le couple d’outils (JFlex/CUP) destinés à générer des analyseurs en Java. Sachez qu’il existe également des outils similaires pour la plupart des autres langages (Lex/Yacc-Bison en C pour le plus connu). CUP, le générateur d’analyseur syntaxique que nous utilisons, construit un automate LALR(1) à partir du fichier de spécification d’une grammaire.

CUP: un générateur d’analyseurs syntaxiques
-------------------------------------------

CUP permet de produire un analyseur syntaxique à partir d’une grammaire spécifiée dans un fichier contenant les règles de production. Voici un exemple de fichier de spécification pour les expressions arithmétiques. La de ces expressions arithmétiques est la suivante :

|              |       |                                                                  |
|-------------:|:-----:|:-----------------------------------------------------------------|
|  Instructions| → | Inst **;** Instructions ∣ epsilon                   |
|          Inst| → | Affect ∣ Expr                                                    |
|        Affect| → | **set** **id** **=** Expr |
|          Expr| → | Expr **+** Terme ∣ Expr **−** Terme ∣ Terme                      |
|         Terme| → | Terme **\*** Fact ∣ Terme **/** Fact ∣ Fact                      |
|       Facteur| → | **(** Expr **)** ∣ Atome ∣ **+** Atome ∣ **−** Atome             |
|         Atome| → | **id**  ∣ **entier**                   |

Cette grammaire respecte l’associativité et les priorités des opérateurs arithmétiques. Elle est non-ambiguë.

Le document suivant vous explique le format de ce fichier de spécification, le manuel vous sera utile si vous souhaitez plus de précisions.

  - <http://www.fil.univ-lille1.fr/~levaire/as/cup.html>;

  - <http://www2.cs.tum.edu/projects/cup/docs.php>  ;

Mise en œuvre
-------------

On exécute CUP, pour un fichier de spécification `specification.cup` donné, par la commande suivante :

    java -cp /usr/share/java/cup.jar java_cup.Main < specification.cup

Les fichiers `parser.java` et `sym.java` sont alors produits. Une fois ces fichiers compilés, on peut utiliser la classe `parser` générée dans un programme de la façon suivante :

    // yy est un objet analyseur lexical de la classe Yylex,
    // l'instruction suivante construit un nouveau parseur p
    parser p = new parser(yy);

    // on lance l'exécution de l'analyse syntaxique 
     p.parse();

Pour l’exemple des expressions arithmétiques, nous allons compiler le fichier de spécification pour l’analyse lexical `lexiArithm.lex` et le fichier de spécification CUP `ExprArithm.cup` de la manière suivante:


    [levaire@lxo12 TPCUP]$ jflex lexiArithm.lex
    Reading "lexiArithm.lex"
    Constructing NFA : 61 states in NFA
    Converting NFA to DFA : 
    .......................
    25 states before minimization, 17 states in minimized DFA
    Writing code to "Yylex.java"
    [levaire@lxo12 TPCUP]$ java -cp /usr/share/java/cup.jar java_cup.Main < ExprArithm.cup
    Opening files...
    Parsing specification from standard input...
    Checking specification...
    Building parse tables...
      Computing non-terminal nullability...
      Computing first sets...
      Building state machine...
      Filling in tables...
      Checking for non-reduced productions...
    Writing parser...
    Closing files...
    ------- CUP v0.11a Parser Generation Summary -------
      0 errors and 0 warnings
      13 terminals, 8 non-terminals, and 18 productions declared, 
      producing 32 unique parse states.
      0 terminals declared but not used.
      0 non-terminals declared but not used.
      0 productions never reduced.
      0 conflicts detected (0 expected).
      Code written to "parser.java", and "sym.java".
    ---------------------------------------------------- (v0.10k)
    [levaire@lxo12 TPCUP]$ 

A ce stade on dispose des trois nouveaux fichiers source Java `Yylex.java`, `parser.java` et `sym.java`, qui à eux trois permettent de construire un analyseur syntaxique. Le fichier `TestExprArithm.java` permet de tester cet analyseur.


    [levaire@lxo12 TPCUP]$ javac -cp /usr/share/java/cup.jar LexicalException.java sym.java Yylex.java parser.java TestExprArithm.java
    [levaire@lxo12 TPCUP]$ java -cp /usr/share/java/cup.jar:. TestExprArithm entreeTest.txt
    Reduction atome -> entier
    Reduction facteur -> atome
    Reduction terme -> facteur
    Reduction expr -> terme
    Reduction inst -> expr
    Reduction atome -> entier
    Reduction facteur -> atome
    ...

Le fichier de spécification (par les actions sémantiques qui y sont définies) ne fait ici qu’afficher les réductions des règles de productions.

Actions sémantiques
===================

CUP associe à chaque terminal et non terminal d’une grammaire un objet de type `Symbol` pendant la reconnaissance d’un mot. Ces objets contiennent un champ `value`, qui permet de donner une valeur au mot que l’on reconnaît. Les types des valeurs dépendent du terminal ou du non terminal. Ces champs valeur seront modifiables en associant une action sémantique à chaque règle de la grammaire. Les actions sémantiques sont spécifiées en faisant suivre chaque règle de la grammaire par du code Java placé entre `{:` et `:}`. Ce code est exécuté à chaque réduction de la règle: la valeur associée au non terminal membre gauche de la règle y est accessible par le mot clé `RESULT`, les valeurs des non terminaux en membre droit y sont accessibles en placant des étiquettes directement dans la règle. Voir le pour plus de détails. Dans un premier temps étudier simplement le code fourni dans la suite.

Il est ainsi possible de réaliser le calcul correspondant à chaque expression en modifiant les actions sémantiques associées aux règles de production. Le fichier de spécification CUP contient les modifications nécessaires, en particulier l’utilisation d’une `HashMap` pour stocker la valeur des variables.


    [levaire@lxo12 TPCUP]$ java -cp /usr/share/java/cup.jar java_cup.Main < ExprArithmAttr.cup
    Opening files...
    Parsing specification from standard input...
    Checking specification...
    Building parse tables...
      Computing non-terminal nullability...
      Computing first sets...
      Building state machine...
      Filling in tables...
      Checking for non-reduced productions...
    Writing parser...
    Closing files...
    ------- CUP v0.11a Parser Generation Summary -------
      0 errors and 0 warnings
      13 terminals, 8 non-terminals, and 18 productions declared, 
      producing 32 unique parse states.
      0 terminals declared but not used.
      0 non-terminals declared but not used.
      0 productions never reduced.
      0 conflicts detected (0 expected).
      Code written to "parser.java", and "sym.java".
    ---------------------------------------------------- (v0.10k)
    [levaire@lxo12 TPCUP]$ javac -cp /usr/share/java/cup.jar:. sym.java Yylex.java parser.java TestExprArithm.java
    Note: parser.java uses unchecked or unsafe operations.
    Note: Recompile with -Xlint:unchecked for details.
    [levaire@lxo12 TPCUP]$ java -cp /usr/share/java/cup.jar:. TestExprArithm entreeTest.txt
    =>5
    =>76
    ajout de (a,45)
    =>5
    ajout de (b,447)
    =>267
    [levaire@lxo12 TPCUP]$

Ajoutez dans le fichier `ExprArithmAttr.cup` le token factorielle en modifiant la règle de production pour un facteur de la manière suivante:

|         |       |                                                                                            |
|--------:|:-----:|:-------------------------------------------------------------------------------------------|
|  Facteur| → | **(** Expr **)** ∣ Atome ∣ **+** Atome ∣ **−** Atome ∣ **!** **(** Expr **)** |

Donnez une action sémantique à la nouvelle règle de production

|         |       |                                    |
|--------:|:-----:|:-----------------------------------|
|  Facteur| → | **!** **(** Expr **)** |

pour calculer la factorielle demandée (on supposera que la factorielle d’un nombre négatif vaut l’opposé de la factorielle de la valeur absolue de ce nombre).

Conflits Shift/Reduce
=====================

Un exemple de grammaire produisant des conflits décalage/réduction avec CUP (et tout autre générateur d’analyseur syntaxique) est la grammaire des expressions arithmétiques sous sa forme ambigüe. Rappelez vous que nous avions rendu notre grammaire d’expressions non ambigue et non récursive gauche dès le début pour rendre possible son analyse LL(1) dans le TP1. Dans l’étape précédente nous avons par contre utilisé une forme récursive gauche de la grammaire: c’était interdit pour l’analyse LL(1), mais cela est recommandé pour les analyses LR. Les grammaires récursives gauches générent en effet moins d’états dans les automates LALR(1). Nous reprenons maintenant une grammaire d’expressions récursive et ambigüe.

Soit la grammaire d’expressions arithmétiques suivante:

|              |       |                                                                  |
|-------------:|:-----:|:-----------------------------------------------------------------|
|  Instructions| - | Inst **;** Instructions ∣ epsilon                   |
|          Inst| - | Affect ∣ Expr                                                    |
|        Affect| - | **set** **id** **=** Expr |
|          Expr| - | Expr **+** Expr                                                  |
|              |   ∣   | Expr **−** Expr                                                  |
|              |   ∣   | Expr **\*** Expr                                                 |
|              |   ∣   | Expr **/** Expr                                                  |
|              |   ∣   | **(** Expr **)**                                                 |
|              |   ∣   | Atome                                                            |
|              |   ∣   | **+** Atome                                                      |
|              |   ∣   | **−** Atome                                                      |
|         Atome| - | **id**  ∣ **entier**                   |

Créez le fichier `ExprArithmAmbiguous.cup` en modifiant le fichier `ExprArithmAttr.cup` pour qu’il corresponde à cette grammaire.

Lorsque vous allez le compiler avec CUP, celui-ci devrait produire un certain nombre de conflits shift/reduce, avant de vous signaler qu’il abandonne la génération de l’analyseur car il a rencontré trop de conflits.


    levaire@lxo12: $ java -cp /usr/share/java/cup.jar java_cup.Main < ExprArithmAmbiguous.cup 
    Opening files...
    Parsing specification from standard input...
    Checking specification...
    Building parse tables...
      Computing non-terminal nullability...
      Computing first sets...
      Building state machine...
      Filling in tables...
    *** Shift/Reduce conflict found in state #17
      between expr ::= expr MULT expr (*) 
      and     expr ::= expr (*) PLUS expr 
      under symbol PLUS
      Resolved in favor of shifting.

    *** Shift/Reduce conflict found in state #17
      between expr ::= expr MULT expr (*) 
      and     expr ::= expr (*) MOINS expr 
      under symbol MOINS
      Resolved in favor of shifting.
    ....
    *** More conflicts encountered than expected -- parser generation aborted
    Closing files...
    ------- CUP v0.10k Parser Generation Summary -------
      1 error and 16 warnings
      13 terminals, 6 non-terminals, and 14 productions declared, 
      producing 26 unique parse states.
      0 terminals declared but not used.
      0 non-terminals declared but not used.
      0 productions never reduced.
      16 conflicts detected (0 expected).
      No code produced.
    ---------------------------------------------------- (v0.10k)

Vous constatez ici qu’il a décidé pour un certain nombre de cas de choisir un décalage plutôt qu’une réduction: pour le tout premier conflit ci-dessus (MULT/PLUS), si le mot d’entrée est `1 * 2 + 3` et que le pointeur dans le mot est sur le `+`, il ne sait pas si il doit réduire `1 * 2`, ce qui reviendrait à reconnaître `(1 * 2) + 3`, ou décaler, ce qui reviendrait à reconnaître `1 * (2 + 3)`. Ce choix de décaler plutôt que de réduire est le choix par défaut de CUP.

Ce type de conflits peut se résoudre en rendant non ambigue la grammaire comme nous l’avions fait. Cependant, comme ce type de conflits provient d’un problème de priorité d’opérateurs, il est possible de spécifier dans CUP (et les autres générateurs) la priorité et l’associativité des opérateurs. Ceci évite d’avoir à réécrire toute la grammaire et permet de minimiser ainsi le nombre d’états de l’automate LALR final. Par exemple, il vous suffit de rajouter les deux lignes déclarant les priorités (`precedence`) dans votre fichier de spécification CUP, juste après la déclaration des terminaux et des non terminaux.

    terminal SET, POINTV, EGAL ;
    terminal PLUS, MOINS, MULT, DIV ; 
    terminal ENTIER, PAROUV, PARFER, IDENT ;

    /* Non terminaux */

    non terminal instructions, inst, affect, expr, atome ;

    /* Declaration des priorites et des associativites */

    precedence left PLUS, MOINS;
    precedence left MULT, DIV;

CUP traite ces lignes en considérant les opérateurs d’une ligne plus prioritaires que ceux des lignes précédentes. Dans le conflit MULT/PLUS vu précédemment, il considérera le MULT plus prioritaire que le PLUS, et fera donc une réduction après avoir reconnu `1 * 2` dans le mot d’entrée `1* 2 + 3`. Par contre, pour un conflit PLUS/MULT, il fera un décalage après avoir reconnu `1 + 2` si le mot d’entrée est `1 + 2 * 3`. L’associativité est quant à elle utilisée pour résoudre les conflits concernant des opérateurs de même priorité (PLUS/MOINS par exemple): ainsi `left` déclare une associativité à gauche et indique à CUP d’effectuer une réduction après avoir reconnu `1 + 2` si le mot d’entrée est `1 + 2 - 3`. Pour plus de détails, voir dans le manuel de CUP la section sur les associativités et les précédences <http://www2.cs.tum.edu/projects/cup/docs.php#precedence>.

Modifiez votre fichier `ExprArithmAmbiguous.cup` pour qu’il corresponde à la grammaire:

|              |       |                                                                  |
|-------------:|:-----:|:-----------------------------------------------------------------|
|  Instructions| → | Inst **;** Instructions ∣ epsilon                   |
|          Inst| → | Affect ∣ Expr                                                    |
|        Affect| → | **set** **id** **=** Expr |
|          Expr| → | Expr **+** Expr                                                  |
|              |   ∣   | Expr **−** Expr                                                  |
|              |   ∣   | Expr **\*** Expr                                                 |
|              |   ∣   | Expr **/** Expr                                                  |
|              |   ∣   | **(** Expr **)**                                                 |
|              |   ∣   | Atome                                                            |
|              |   ∣   | **+** Atome                                                      |
|              |   ∣   | **−** Atome                                                      |
|              |   ∣   | **!** Expr                                          |
|         Atome| → | **id**  ∣ **entier**                   |

dans laquelle l’opérateur **factorielle** est plus prioritaire que tout autre opérateur. Noter que l’on peut maintenant supprimer les parenthèses autour de l’argument de la factorielle sans modifier la grammaire. Dans la question précédente, les parenthèses étaient nécessaires pour éviter des conflits shift/reduce.
