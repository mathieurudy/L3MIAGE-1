import java.io.*;

public class Lexico{

    // les tokens sont des constantes entières
    // Il y a 1 token par terminal de la grammaire 
    // + des tokens "fin de ligne" "fin de fichier" 
    public static final int 
        TOKEN_INCONNU = 0,  TOKEN_ENTIER = 1, TOKEN_IDENTIF = 2,  
	TOKEN_EOL = 3,      TOKEN_EOF = 4,    TOKEN_PAR_OUV = 5,
        TOKEN_PAR_FER = 6 , TOKEN_PLUS = 7,   TOKEN_MOINS = 8,    
        TOKEN_MULT = 9,     TOKEN_DIV = 10, 
	TOKEN_CRO_OUV = 11, TOKEN_CRO_FER = 12,
	TOKEN_PT_VIRGULE = 13, TOKEN_2_PTS = 14,
	TOKEN_CAR = 15, TOKEN_ACC_OUV = 16,  TOKEN_ACC_FER = 17,
        TOKEN_VIRGULE = 18;

    private int dernierToken ;// le dernier token lu
    private int dernierEntier ;// la valeur du dernier entier lu
    private String dernierMot ;// la chaine lue, valeur du dernier token lu
    // on peut avoir par exemple dernierMot = "reset" 
    //                        et dernierToken = TOKEN_IDENTIF
    private int nbLignes = 1 ;// permet de savoir quelle ligne on lit
                              // c'est utile pour les messages d'erreur

    private StreamTokenizer st ;
    // on n'est pas obligé d'utiliser un StreamTokenizer !
    
    private void setParams(){
	// on paramètre l'objet StreamTokenizer
	st.resetSyntax() ; // tous les caractères deviennent "ordinaires"
	st.eolIsSignificant(true) ; // false par defaut
	st.slashSlashComments(false) ; // false par defaut
	st.slashStarComments(false) ; // false par defaut
	st.lowerCaseMode(false) ;
	st.wordChars('A','Z') ;
        st.wordChars('a','z') ;
	st.wordChars('0','9') ;
    }

    public Lexico(String nomFichier) throws java.io.FileNotFoundException{
	// lecture d'un fichier
	st = new StreamTokenizer(new BufferedReader
				 (new FileReader(nomFichier)));
	setParams() ;
    }

    public Lexico(){ // lecture sur l'entrée standard
	st = new StreamTokenizer(new BufferedReader
				 (new InputStreamReader(System.in)));
	setParams() ;
    }

    private void passerBlanc() throws java.io.IOException{
	// après l'appel à cette méthode, le token courant n'est pas un espace
	while ((char)st.ttype == ' ') st.nextToken() ;
    }

    private boolean carChiffre(char c){
	// retourne vrai SSI c est un chiffre
	return (c >= '0' && c <= '9') ;
    }

    private void tokenDetecte(int leToken) {
	dernierMot = ""+ (char)st.ttype ; 
	dernierToken = leToken ; 
    }

    public int nextToken() throws java.io.IOException{
	// Cette méthode lit un nouveau token
	// C'est la seule méthode publique de la classe Lexico  
	st.nextToken() ; passerBlanc() ; // le token n'est pas un blanc
	int tk = st.ttype ;
	switch (tk){
        case StreamTokenizer.TT_EOL:{ 
	    dernierMot = null ; nbLignes ++ ; 
	    dernierToken = TOKEN_EOL ; break ;
	}
        case StreamTokenizer.TT_EOF:{
	    dernierMot = null ; dernierToken = TOKEN_EOF ; break ;
	}
	case StreamTokenizer.TT_WORD:{ // identificateur ou entier sans signe
	    dernierMot = st.sval ; 
	    if (carChiffre(dernierMot.charAt(0))){ 
		// le premier car est un chiffre 
		try {
		    dernierEntier = Integer.parseInt(dernierMot) ;
		    dernierToken = TOKEN_ENTIER ;
		}catch(Exception e){dernierToken = TOKEN_INCONNU ;}
	    }
	    else // le premier car est une lettre
                if (dernierMot.length() == 1)
		    dernierToken=TOKEN_CAR;
                else
		    dernierToken = TOKEN_IDENTIF ;
	    break ;
	}
	case '+':{ tokenDetecte(TOKEN_PLUS) ; break ;}
	case '-':{ tokenDetecte(TOKEN_MOINS) ; break ;}
	case '*':{ tokenDetecte(TOKEN_MULT) ; break ;}
	case '/':{ tokenDetecte(TOKEN_DIV) ; break ;}
	case '(':{ tokenDetecte(TOKEN_PAR_OUV) ; break ;}
	case ')':{ tokenDetecte(TOKEN_PAR_FER) ; break ;}
	case '[':{ tokenDetecte(TOKEN_CRO_OUV) ; break ;}
	case ']':{ tokenDetecte(TOKEN_CRO_FER) ; break ;}
	case ';':{ tokenDetecte(TOKEN_PT_VIRGULE) ; break ;}
	case ':':{ tokenDetecte(TOKEN_2_PTS) ; break ;}
	case ',':{ tokenDetecte(TOKEN_VIRGULE) ; break ;}
	case '{':{ tokenDetecte(TOKEN_ACC_OUV) ; break ;}
	case '}':{ tokenDetecte(TOKEN_ACC_FER) ; break ;}
	default: { tokenDetecte(TOKEN_INCONNU) ;}
	}
	return dernierToken ;
    }

    public int entierLu(){ return dernierEntier ;}

    public String chaineLue(){ return dernierMot ;}

    public int numeroLigne(){return nbLignes ;}

    public String image(int token){
	switch (token){
	case TOKEN_INCONNU: return "TOKEN_INCONNU" ;
	case TOKEN_ENTIER: return "TOKEN_ENTIER" ;
	case TOKEN_IDENTIF: return "TOKEN_IDENTIF" ;
	case TOKEN_EOL: return "TOKEN_EOL" ;
	case TOKEN_EOF: return "TOKEN_EOF" ;
        case TOKEN_PAR_OUV: return "TOKEN_PAR_OUV" ;
        case TOKEN_PAR_FER: return "TOKEN_PAR_FER" ;
        case TOKEN_PLUS: return "TOKEN_PLUS" ;
	case TOKEN_MOINS: return "TOKEN_MOINS";
	case TOKEN_MULT: return "TOKEN_MULT";
	case TOKEN_DIV: return "TOKEN_DIV";
	case TOKEN_CRO_OUV: return "TOKEN_CRO_OUV" ;
        case TOKEN_CRO_FER: return "TOKEN_CRO_FER" ;
        case TOKEN_PT_VIRGULE: return "TOKEN_PT_VIRGULE" ;
	case TOKEN_2_PTS: return "TOKEN_2_PTS";
	default: return "" ;
	}
    }

    // pour essayer
    public static void main(String args[]) {
	Lexico lex ;
        try{
	    if (args.length == 0) lex = new Lexico() ;
	    else lex = new Lexico(args[0]) ;
	    int tk = lex.nextToken();
	    while (tk != TOKEN_EOF){
		System.out.println(lex.image(tk) + " : " + lex.chaineLue()) ;
		tk = lex.nextToken();
	    }
	}catch(IOException e){
	    System.out.println("erreur d'exécution ");
	    System.out.println("commande : java Lexico ou bien java Lexico nomFichier");
	}
    }
}

