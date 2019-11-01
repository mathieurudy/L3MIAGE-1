public class Commande{

    private Lexico lex ; // analyseur lexical 
    private int leToken ; // dernier token lu
    private static short STDIN=0, FILE=1;
    private static int mode = STDIN;
    private static String message="Entrez une commande: ";

    /* les constructeurs : on peut faire l'analyse à partir d'un fichier 
       ou de l'entrée standard.
    */
    public Commande(String nomFichier) throws java.io.FileNotFoundException, 
					    java.io.IOException{
	lex = new Lexico(nomFichier);
        mode=FILE;
    }

    public Commande() throws java.io.IOException{// lecture sur l'entrée standard
	lex = new Lexico() ;

        mode=STDIN;
    }

    /* gestion des erreurs */
    private void erreur(String pb) throws SyntaxException{
	throw new SyntaxException(pb + " : ligne " + lex.numeroLigne() + "[dernier token :"+
				  lex.image(leToken)+"]") ; 
    }

    private void verifierToken(int tokenAttendu) throws SyntaxException{
	if (leToken != tokenAttendu) 
	    erreur(lex.image(tokenAttendu) + " token attendu") ;
    }  

    /* les fins de lignes et fin de fichier */
    private void passerLignes() throws java.io.IOException{
	while (leToken == Lexico.TOKEN_EOL) {
          if (mode == STDIN) System.out.print(message);
          leToken = lex.nextToken() ;
	}
    }

    private boolean finFichier() {
	return (leToken == Lexico.TOKEN_EOF) ;
    }

    /* lire le token suivant : modifie la variable d'instance leToken */
    private void lireLeToken() throws java.io.IOException{leToken = lex.nextToken() ; }
 
    private void nom() throws SyntaxException, java.io.IOException{
	/* nom -> TOKEN_ID */
	verifierToken(Lexico.TOKEN_IDENTIF);
	lireLeToken();
    }
    private  void variable() throws SyntaxException, java.io.IOException{
	/* variable -> TOKEN_ID | epsilon*/
	if (leToken==Lexico.TOKEN_IDENTIF) {
	    lireLeToken();
	}
    }
    private void parametre() throws SyntaxException, java.io.IOException{
	/* parametre -> TOKEN_ACC_OUV TOKEN_CAR suiteparametre TOKEN_ACC_FER | epsilon */
      	if (leToken==Lexico.TOKEN_ACC_OUV) {
	    lireLeToken();
	    verifierToken(Lexico.TOKEN_CAR);
            lireLeToken();
            suiteparametre();
 	    verifierToken(Lexico.TOKEN_ACC_FER);
            lireLeToken();
	}
    }
    private void suiteparametre() throws SyntaxException, java.io.IOException{
	/* suiteparametre -> TOKEN_VIRGULE TOKEN_CAR suiteparametre | epsilon */
      	if (leToken==Lexico.TOKEN_VIRGULE) {
	    lireLeToken();
	    verifierToken(Lexico.TOKEN_CAR);
            lireLeToken();
            suiteparametre();
	}
    }
    
    private void option()  throws SyntaxException, java.io.IOException{
	/* option -> TOKEN_CRO_OUV uneoption suiteoption TOKEN_CRO_FER | epsilon */
      	if (leToken==Lexico.TOKEN_CRO_OUV) {
	    lireLeToken();
            uneoption();
            suiteoption();
            verifierToken(Lexico.TOKEN_CRO_FER);
            lireLeToken();
	}
    }
    private void uneoption()  throws SyntaxException, java.io.IOException{
	/* uneoption -> TOKEN_CAR | parametre */
        if (leToken==Lexico.TOKEN_CAR) {
	    lireLeToken();
        } else {
	    parametre();
	}
    }      
   
    private void suiteoption()  throws SyntaxException, java.io.IOException{
	/* suiteoption -> TOKEN_VIRGULE uneoption suiteoption | epsilon */
       if (leToken==Lexico.TOKEN_VIRGULE) {
	    lireLeToken();
            uneoption();
            suiteoption();
       }
   }

    
    /* ---------------------------------------- */
    /* analyse syntaxique récursive descendante */
    /* ---------------------------------------- */
    private void commande() throws SyntaxException, java.io.IOException{
	/* commande -> nom  variable parametre option */
        nom();
        variable();
        parametre();
        option();
    }

    /* la seule méthode d'instance qui soit publique */
    public void analyse() throws SyntaxException, java.io.IOException{
	lireLeToken() ;
	passerLignes() ;
	while (! finFichier()){
	    commande() ;
	    verifierToken(Lexico.TOKEN_EOL) ; passerLignes() ;
	}
    }

    public static void main(String args[]) throws Exception{
	Commande synt ;
	if (args.length == 0) synt = new Commande() ;
        else synt = new Commande(args[0]) ;
        if (mode == STDIN) System.out.print(message);
	synt.analyse() ;
    }
}
