Initiation à la compilation
===========================

Ce TP a pour objectif de créer un compilateur *brainfuck* vers *Java*.

Le *brainfuck* est un language minimaliste, constitué uniquement de 8
caractères, mais néanmois *Turing-complet*.
Comme son nom le suggère subtilement, le langage produit des programmes
difficiles à comprendre.


Brainfuck
=========

*brainfuck* contient 8 instructions, codées chacune par un seul caractère,
dont le sens est donné dans le tableau ci-dessous.
Il dispose d’un *ruban* d’octets inialement tous à 0 et d’un pointeur sur
ce ruban, initialement à la première position.

| Car. | Signification                                                          |
|:----:|:-----------------------------------------------------------------------|
| `>`  | incrémente le pointeur                                                 |
| `<`  | décrémente le pointeur                                                 |
| `+`  | incrémente l’octet pointé                                              |
| `-`  | décrémente l’octet pointé                                              |
| `[`  | saute à l’instruction après le `]` correspondant si l’octet pointé est à 0 |
| `]`  | saute à l’instruction après le `[` correspondant                           |
| `.`  | sortie de l’octet pointé                                               |
| `,`  | entrée de l’octet pointé                                               |

Tout autre caractère est considéré comme un commentaire.

En *java*, le ruban peut être matérialisé par un tableau de d’une taille arbitraire, et le pointeur par un entier. On aurait ainsi le code suivant.

```.java
int p = 0;
byte[] ruban = new byte[4096];
```

Il est donc possible de faire alors la substition donnée dans le
tableau ci-après.

| Car. | Code *java*                    |
|:----:|:-------------------------------|
| `>`  | `p++;`                         |
| `<`  | `p--;`                         |
| `+`  | `ruban[p]++;`                  |
| `-`  | `ruban[p]--;`                  |
| `[`  | `while (ruban[p]!=0) {`        |
| `]`  | `}`                            |
| `.`  | `System.out.write(ruban[p]);`  |
| `,`  | `System.in.read(ruban, p, 1);` |

La compilation *brainfuck* vers *Java* se révèle alors extrêmement simple.
Il suffit d’initialiser le ruban et le pointeur, puis de substituer les
instructions une par une.

Créez un fichier `Makefile` (ou équivalent, par exemple un script `sh`,
ou un fichier `build.xml` pour *ant*, ou `CMakefile.txt`…) permettant de :

-   créer un analyseur lexical `Bf.java` à partir d’un fichier *JFlex* `bf.lex`;

-   compiler l’analyseur lexical `Bj.java` en `Bf.class` ;

-   utiliser la classe `Bf` pour compiler le fichier *brainfuck* `hello.bf` en un fichier *Java* `Main.java` ;

-   compiler le programme `Main.java` en `Main.class` ;

-   tester en lançant la classe `Main`.

Écrivez un fichier `bf.lex` en mode *standalone* qui permette d’obtenir un compilateur *brainfuck* vers *Java*.

Indications :

-   les règles peuvent être de la forme `car {System.out.println("commande java");}` ;

-   dans la seconde session (options et déclaration), utilisez les options pour faire afficher au début de l’analyse la déclaration *java* de la classe, de la procédure `main`, du ruban et du pointeur, ainsi que pour faire afficher à la fin de l’analyse le code *java* pour indiquer la fin de la procédure `main` et de la classe ;

-   ahem ahem... `%init`... ahem `%eof` ...
