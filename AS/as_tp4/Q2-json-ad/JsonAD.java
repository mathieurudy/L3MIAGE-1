import java.io.* ;
import java_cup.runtime.Symbol;

public class JsonAD {


	public static RuleNode parse(Yylex yy, boolean debug) throws LexicalException, SyntaxException, IOException {
		RuleNode root=new RuleNode(rule.JSON_text, 0);
		RuleNode current=root;
		RuleNode newrule;
		LeafNode newleaf;
		Symbol symb = yy.next_token();
		//if (debug) System.out.println("Read symbol: "+symb.sym + " = " + yy.yytext());

		while (symb.sym != sym.EOF){
	    //if (debug) System.out.println("In rule: "+ current.getType() + " pos " + current.getValue() + " sym " + symb.sym);
		    if (debug) {                            // option -d
				System.out.print("\033[H\033[2J");  // Clearscreen (Unix only)
				System.out.flush();
				System.out.println("Token lu: "+ symb.sym + " = " + yy.yytext());
				System.out.println();
				System.out.print(root.toString());
				System.out.println();
			}
			switch (current.getType()) {
			    case rule.JSON_text:                              // Axiome
				if (current.getValue() == 0) {                // Début de règle
				    switch(symb.sym) {                        // symbole lu ==
				    	case sym.BEGINOBJECT:                     // premier terminal de la règle Object
						newrule=new RuleNode(rule.OBJECT, 0); // on crée un noeud OBJECT
						current.appendChild(newrule);         // que l'on ajoute à la fin du noeud courant
						current=newrule;                      // et on descend dans l'arbre
						break;
					    case sym.BEGINARRAY:                      // premier terminal de la règle Array
						newrule=new RuleNode(rule.ARRAY, 0);  // on crée un noeud ARRAY
						current.appendChild(newrule);         // que l'on ajoute à la fin du noeud courant
						current=newrule;                      // et on descend dans l'arbre
						break;
					    default:                                  // c'est assez clair je pense
					    throw new SyntaxException("waiting '{' or '[' at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
					}
				} else {                                      // on est en fin de règle
			                                              // Fin de l'axiome et symb!=sym.EOF
				  throw new SyntaxException("waiting EOF at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
				}
				break;
				case rule.OBJECT:
				switch (current.getValue()) {
				case 0:                                      // Object -> . begin-object (Member ( value-separator Member )*)? end-object
					switch(symb.sym) {                       // symbole lu ==
					case sym.BEGINOBJECT:                     // begin-object
						newleaf=new LeafNode(sym.BEGINOBJECT, yy.yytext());
						current.appendChild(newleaf);        // on crée un noeud terminal que l'on ajoute au noeud courant
						current.setValue(current.getValue() + 1); // on avance dans la règle
						symb = yy.next_token();                   // et on lit le token suivant
						break;
					default:
						throw new SyntaxException("waiting '{' at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
					}
					break;
				case 1:                                     // Object -> begin-object . (Member ( value-separator Member )*)? end-object
					switch(symb.sym) {
					case sym.ENDOBJECT:                      // ce qui suit le point peut se dériver en le mot vide, jusque end-object
						newleaf=new LeafNode(sym.ENDOBJECT, yy.yytext());
						current.appendChild(newleaf);       // on crée un noeud terminal que l'on ajoute au noeud courant
						current.setValue(current.getValue() + 1); // on avance dans la règle
						symb = yy.next_token();                   // et on lit le token suivant
						current=current.getParent();              // on est en fin de règle, on remonte
						current.setValue(current.getValue() + 1); // et on avance dans la règle parente
						break;
					case sym.FALSE:                         // les terminaux possibles en début de Member
					case sym.NULL:
					case sym.TRUE:
					case sym.BEGINOBJECT:
					case sym.BEGINARRAY:
					case sym.NUMBER:
					case sym.STRING:
						newrule=new RuleNode(rule.MEMBER, 0);// on crée un noeud Member
						current.appendChild(newrule);       // que l'on ajoute à la fin du noeud courant
						current=newrule;                    // et on descend dans l'arbre
						break;
					default:
						throw new SyntaxException("waiting Member or '}' at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
					}
					break;
				case 2:                                     // Object -> begin-object (Member . ( value-separator  Member )*)? end-object
					switch(symb.sym) {
					case sym.ENDOBJECT:                      // ce qui suit le point peut se dériver en le mot vide, jusque end-object
						newleaf=new LeafNode(sym.ENDOBJECT, yy.yytext()); // cf case 1:
						current.appendChild(newleaf);
						current.setValue(current.getValue() + 1);
						current=current.getParent();
						current.setValue(current.getValue() + 1);
						symb = yy.next_token();
						break;
					case sym.VALUESEPARATOR:
						newleaf=new LeafNode(sym.VALUESEPARATOR, yy.yytext());
						current.appendChild(newleaf);
						current.setValue(current.getValue() + 1);
						symb = yy.next_token();
						break;
					default:
						throw new SyntaxException("waiting ',' or '}' at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
					}
					break;
				case 3:                                     // Object -> begin-object (Member ( value-separator . Member )*)? end-object
					switch (symb.sym) {
					case sym.FALSE:                         // les terminaux possibles en début de Member
					case sym.NULL:
					case sym.TRUE:
					case sym.BEGINOBJECT:
					case sym.BEGINARRAY:
					case sym.NUMBER:
					case sym.STRING:
						newrule=new RuleNode(rule.MEMBER, 0);// on crée un noeud Member
						current.appendChild(newrule);       // que l'on ajoute à la fin du noeud courant
						current=newrule;                    // et on descend dans l'arbre
						break;
					default:
						throw new SyntaxException("waiting Member at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
					}
					break;
				case 4:                                     // Object -> begin-object (Member ( value-separator Member ).*)? end-object
					// pour la répétition on se replace au début
					current.setValue(2);                    // Object -> begin-object (Member . ( value-separator  Member )*)? end-object
					break;
				case 5:
					switch (symb.sym) {
					case sym.ENDOBJECT:
						current=current.getParent();              // on est en fin de règle, on remonte
						current.setValue(current.getValue() + 1); // et on avance dans la règle parente
						break;
					default :
						throw new SyntaxException("waiting ',' or '}' at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
					}
					break;
				default:                                    // Normalement impossible ! et pas une SyntaxException d'ailleurs
					throw new SyntaxException("Internal parser error in OBJECT");
				}
				break;
				case rule.ARRAY:
				switch (current.getValue()) {
					case 0:                                      // Array -> . begin-array  (Value  ( value-separator  Value )*)?  end-array
				    switch(symb.sym) {                       // symbole lu ==
					    case sym.BEGINARRAY:                     // begin-array
					    newleaf=new LeafNode(sym.BEGINARRAY, yy.yytext());
						current.appendChild(newleaf);        // on crée un noeud terminal que l'on ajoute au noeud courant
						current.setValue(current.getValue() + 1); // on avance dans la règle
						symb = yy.next_token();                   // et on lit le token suivant
						break;
						default:
						throw new SyntaxException("waiting '[' at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
					}
					break;
					case 1:                                     // Array -> begin-array . (Value  ( value-separator  Value )*)?  end-array
					switch(symb.sym) {
					    case sym.ENDARRAY:                      // ce qui suit le point peut se dériver en le mot vide, jusque end-array
					    newleaf=new LeafNode(sym.ENDARRAY, yy.yytext());
						current.appendChild(newleaf);       // on crée un noeud terminal que l'on ajoute au noeud courant
						current.setValue(current.getValue() + 1); // on avance dans la règle
						symb = yy.next_token();                   // et on lit le token suivant
						current=current.getParent();              // on est en fin de règle, on remonte
						current.setValue(current.getValue() + 1); // et on avance dans la règle parente
						break;
					    case sym.FALSE:                         // les terminaux possibles en début de Value
					    case sym.NULL:
					    case sym.TRUE:
					    case sym.BEGINOBJECT:
					    case sym.BEGINARRAY:
					    case sym.NUMBER:
					    case sym.STRING:
						newrule=new RuleNode(rule.VALUE, 0);// on crée un noeud Value
						current.appendChild(newrule);       // que l'on ajoute à la fin du noeud courant
						current=newrule;                    // et on descend dans l'arbre
						break;
						default:
						throw new SyntaxException("waiting Value or ']' at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
					}
					break;
					case 2:                                     // Array -> begin-array (Value . ( value-separator  Value )*)?  end-array
					switch(symb.sym) {
					    case sym.ENDARRAY:                      // ce qui suit le point peut se dériver en le mot vide, jusque end-array
						newleaf=new LeafNode(sym.ENDARRAY, yy.yytext()); // cf case 1:
						current.appendChild(newleaf);
						current.setValue(current.getValue() + 1);
						current=current.getParent();
						current.setValue(current.getValue() + 1);
						symb = yy.next_token();
						break;
						case sym.VALUESEPARATOR:
						newleaf=new LeafNode(sym.VALUESEPARATOR, yy.yytext());
						current.appendChild(newleaf);
						current.setValue(current.getValue() + 1);
						symb = yy.next_token();
						break;
						default:
						throw new SyntaxException("waiting ',' or ']' at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
					}
					break;
					case 3:                                     // Array -> begin-array (Value ( value-separator . Value )*)?  end-array
					switch (symb.sym) {
					    case sym.FALSE:                         // les terminaux possibles en début de Value
					    case sym.NULL:
					    case sym.TRUE:
					    case sym.BEGINOBJECT:
					    case sym.BEGINARRAY:
					    case sym.NUMBER:
					    case sym.STRING:
						newrule=new RuleNode(rule.VALUE, 0);// on crée un noeud Value
						current.appendChild(newrule);       // que l'on ajoute à la fin du noeud courant
						current=newrule;                    // et on descend dans l'arbre
						break;
						default:
						throw new SyntaxException("waiting Value at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
					}
					break;
					case 4:                                     // Array -> begin-array (Value ( value-separator Value ).*)?  end-array
					                                            // pour la répétition on se replace au début
					    current.setValue(2);                    // Array -> begin-array (Value . ( value-separator Value )*)?  end-array
					    break;
					default:                                    // Normalement impossible ! et pas une SyntaxException d'ailleurs
					throw new SyntaxException("Internal parser error in ARRAY ");
				}
				break;
				case rule.MEMBER:
				switch (current.getValue()) {
					case 0:                                      // Member -> . string name-separator Value
						switch(symb.sym) {                       // symbole lu ==
						case sym.STRING:
							newleaf=new LeafNode(sym.STRING, yy.yytext());
							current.appendChild(newleaf);        // on crée un noeud terminal que l'on ajoute au noeud courant
							current.setValue(current.getValue() + 1); // on avance dans la règle
							symb = yy.next_token();                   // et on lit le token suivant
							break;
						default:
							throw new SyntaxException("waiting String at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
						}
						break;
					case 1:                                      // Member -> string . name-separator Value
						switch(symb.sym) {                       // symbole lu ==
						case sym.NAMESEPARATOR:
							newleaf=new LeafNode(sym.NAMESEPARATOR, yy.yytext());
							current.appendChild(newleaf);        // on crée un noeud terminal que l'on ajoute au noeud courant
							current.setValue(current.getValue() + 1); // on avance dans la règle
							symb = yy.next_token();                   // et on lit le token suivant
							break;
						default:
							throw new SyntaxException("waiting NameSeparator at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
						}
						break;
					case 2:                                      // Member -> string name-separator . Value
						switch(symb.sym) {   // symbole lu ==
						case sym.NUMBER:
						case sym.STRING:
							newrule=new RuleNode(rule.VALUE, 0);  // on crée un noeud ARRAY
							current.appendChild(newrule);         // que l'on ajoute à la fin du noeud courant
							current=newrule;                      // et on descend dans l'arbre
							break;
						default:
							throw new SyntaxException("waiting String at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
						}
						break;
					case 3:
						current=current.getParent();              // on est en fin de règle, on remonte
						current.setValue(current.getValue() + 1); // et on avance dans la règle parente
						break;
					default:                                    // Normalement impossible ! et pas une SyntaxException d'ailleurs
						throw new SyntaxException("Internal parser error in MEMBER ");
				}
				break;
				case rule.VALUE:
				switch (current.getValue()) {                // number en version simple
					case 0:                                      // Value -> . number
				    switch(symb.sym) {                       // symbole lu ==
					    case sym.FALSE:                         // sym.FALSE
					    newleaf=new LeafNode(sym.FALSE, yy.yytext());
						current.appendChild(newleaf);        // on crée un noeud terminal que l'on ajoute au noeud courant
						current.setValue(current.getValue() + 1); // on avance dans la règle
						symb = yy.next_token();                   // et on lit le token suivant
						break;
						case sym.NULL:                         // sym.NULL
					    newleaf=new LeafNode(sym.NULL, yy.yytext());
						current.appendChild(newleaf);        // on crée un noeud terminal que l'on ajoute au noeud courant
						current.setValue(current.getValue() + 1); // on avance dans la règle
						symb = yy.next_token();                   // et on lit le token suivant
						break;
						case sym.TRUE:                         // sym.TRUE
					    newleaf=new LeafNode(sym.TRUE, yy.yytext());
						current.appendChild(newleaf);        // on crée un noeud terminal que l'on ajoute au noeud courant
						current.setValue(current.getValue() + 1); // on avance dans la règle
						symb = yy.next_token();                   // et on lit le token suivant
						break;
						case sym.BEGINOBJECT:                 // sym.BEGINOBJECT
					    newrule=new RuleNode(rule.OBJECT, 0); // on crée un noeud OBJECT
						current.appendChild(newrule);         // que l'on ajoute à la fin du noeud courant
						current=newrule;                      // et on descend dans l'arbre
						break;
						case sym.BEGINARRAY:                  // sym.BEGINARRAY
					    newrule=new RuleNode(rule.ARRAY, 0); // on crée un noeud ARRAY
						current.appendChild(newrule);         // que l'on ajoute à la fin du noeud courant
						current=newrule;                      // et on descend dans l'arbre
						break;
					    case sym.NUMBER:                         // sym.NUMBER
					    newleaf=new LeafNode(sym.NUMBER, yy.yytext());
						current.appendChild(newleaf);        // on crée un noeud terminal que l'on ajoute au noeud courant
						current.setValue(current.getValue() + 1); // on avance dans la règle
						symb = yy.next_token();                   // et on lit le token suivant
						break;
						case sym.STRING:                         // sym.STRING
					    newleaf=new LeafNode(sym.STRING, yy.yytext());
						current.appendChild(newleaf);        // on crée un noeud terminal que l'on ajoute au noeud courant
						current.setValue(current.getValue() + 1); // on avance dans la règle
						symb = yy.next_token();                   // et on lit le token suivant
						break;
						default:
						throw new SyntaxException("waiting Value at line "+yy.line()+ ", col " +yy.col() + ", found "+yy.yytext());
					}
					break;
					case 1:
				    current=current.getParent();              // on est en fin de règle, on remonte
				    current.setValue(current.getValue() + 1); // et on avance dans la règle parente
				    break;
					default:                                    // Normalement impossible ! et pas une SyntaxException d'ailleurs
					throw new SyntaxException("Internal parser error in VALUE ");
				}
				break;
				default:
				System.out.println("Règle " + current.nodetoString() + " non implémentée ! - réduction immédiate");
				current=current.getParent();
				current.setValue(current.getValue() + 1);
				// c'est normalement une erreur interne
				// throw new SyntaxException("Internal parser error: Unknown rule");
			}
			if (debug) {
					System.in.read();                   // Wait keyboard input
			}
		}
		System.out.println("End of scanning");
		return root;
	}

	public static void main(String arg[]) throws Exception {
		Yylex yy ;
		RuleNode root=null;
		boolean debug = false;
		java.io.ObjectOutputStream outfile;
		if (arg.length > 0) {
			if (arg[0].equals( "-d")) {
				debug = true;
				if (arg.length == 1)
					yy = new Yylex(new BufferedReader(new InputStreamReader(System.in))) ;
				else
					yy = new Yylex(new BufferedReader(new FileReader(arg[1]))) ;
			}
			else yy = new Yylex(new BufferedReader(new FileReader(arg[0]))) ;
		}
		else
			yy = new Yylex(new BufferedReader(new InputStreamReader(System.in))) ;

		root=parse(yy, debug);
		System.out.println("ADNode:\n" + root.toString());
		outfile= new java.io.ObjectOutputStream(new FileOutputStream("result.ast"));
		outfile.writeObject(root);
		outfile.close();
	}
}
