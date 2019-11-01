Automates à pile
================

Nous utilisons dans ce TP le logiciel
[JFLAP](https://www2.cs.duke.edu/csed/jflap/) conçu pour expérimenter
les concepts de la théorie des langages formels et de la théorie des
automates. Le logiciel consiste en une archive Java
`JFLAP7.1.jar` (disponible sur
inscription) fournie dans le dépôt gitlab. Vous lancez son exécution
avec la commande `java -jar JFLAP7.1.jar`.

### Tutoriel

Réalisez le
[tutoriel](https://www2.cs.duke.edu/csed/jflap/tutorial/pda/construct/index.html)
sur la création et la simulation des automates à pile dans
*JFLAP*.

---
#### Question 1
> Vous allez utiliser *JFLAP* pour répondre aux questions suivantes.
> 1.  Recréez dans *JFLAP*
    l’automate à pile de la figure [*1*](#q1automata).
> 2.  Ecrivez les transitions correspondantes.
> 3.  Cet automate est-il déterministe ?
> 4.  Donnez la suite de transitions effectuées par l’automate pour le
    mot `aabbbb`.
> 5.  Quel est le langage reconnu par cet automate ?



 ![](http://www.fil.univ-lille1.fr/~levaire/as/figs/pda-q1automata.png)

###### <a name="#q1automata"></a>  Figure 1: Automate de la question 1.


** *JFLAP* représente la pile de droite (bas) à gauche (sommet), à l’inverse du
cours.**

---

### Palindromes

Pour un mot w, on note w&#x0305; le miroir de w.

#### Question 2
> Construire avec *JFLAP* un automate à pile déterministe acceptant
L = {wcw&#x0305; | w ∈ {a +b\*}}, le langage des palindromes sur
{a, b} avec marqueur central c.


#### Question 3
> Construire avec *JFLAP* un automate à pile non déterministe acceptant
L = {ww&#x0305; | w ∈ {(a +b)\*}, le langage des palindromes de longueur paire
sur {a, b}.

---

### Expressions arithmétiques

Nous reprenons la grammaire des expressions arithmétiques vue dans un
précédent TP, sous une forme modifiée de sorte que les noms de variables
soient constitués d’un seul caractère (c’est une contrainte de
*JFLAP*):

```
   E   →   T   G                                                       
   G   →   +    T    G                
   G   →   -    T    G                
   G   →   є                                                           
   T   →   F    U                                                      
   U   →   *    F    U               
   U   →   /    F    U                
   U   →   є                                                           
   F   →   (    E    )                                       
   F   →   A                                                           
   A   →   i ∣ n ∣ - i ∣ - n                                    
```

#### Question 4 
> Construire avec *JFLAP* un automate à pile acceptant
par pile vide le langage associé à cette grammaire en utilisant la
méthode descendante. Il n’est pas nécessaire ici de créer une variable
par terminal, puisqu’il est possible d’empiler/dépiler des terminaux
dans *JFLAP*. Ajouter par contre une
transition initiale* `(q0, є, Z) → (q1, E)` pour empiler l’axiome
E. Toutes les transitions seront ensuite de q1 vers q1.



#### Question 5
> Simuler la reconnaissance du
mot `i + i` (Input -&gt; Step withclosure) et observer à chaque pas
l’ensemble des configurations possibles affichées par *JFLAP*.
Cela illustre l’explosion combinatoire qui peut survenir si on utilise un algorithme naïf pour construire un analyseur syntaxique à partir de l’automate à pile associé à une grammaire.

---

### JSON

Nous reprenons la grammaire de JSON vue
dans un précédent TP, sous une forme modifiée de sorte que les noms de
variables soient constitués d’un seul caractère

```
     J   →   O     ∣     A                                             
     O   →   {    M    N     }    ∣    {  }                                 
     M   →   s    :    V                                                  
     N   →   ,    M    N    ∣    є    
     A   →   [    V    B     ]    ∣    [   ]                              
     B   →   ,    V    B    ∣    є    
     V   →   s ∣ n ∣ f ∣ t ∣ z ∣ O ∣ A      
```


#### Question 6
> Construire avec
*JFLAP* un automate à pile acceptant
par pile vide le langage associé à cette grammaire en utilisant la
méthode ascendante. Empilez directement les terminaux comme précedemment, et
faites attention à l’ordre des éléments de la pile. La transition vidant la
pile quand l’axiome est en sommet de pile s’écrit en JFLAP (q, є, JZ) → (q, є).


#### Question 7
> Simuler la reconnaissance du mot `{s:[n,n],s:f}` sans pas-à-pas
(Input -> Fast Run) et observer l’explosion combinatoire des configurations
possibles signalées par *JFLAP*. Cette explosion provient des règles B → є et
N → є créant des transitions possibles à chaque pas. Observons précisèment
quand B doit vraiment donner le mot vide: quand le terminal est ] et que le
sommet de pile contient V précédé d’une virgule. On va donc remplacer la
transition (q, є, є) → (q, B) de l’automate par la transition
(q, ], V,) → (q, ]B). Faites de même avec N. La simulation doit maintenant fonctionner.


------------------------------------------------------------------------
