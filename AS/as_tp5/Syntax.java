import java.io.IOException;

public class Syntax {

	private Lexico lex; // analyseur lexical
	private int leToken; // dernier token lu

	/*
	 * les constructeurs : on peut faire l'analyse à partir d'un fichier ou de
	 * l'entrée standard.
	 */
	public Syntax(String nomFichier) throws java.io.FileNotFoundException, java.io.IOException {
		lex = new Lexico(nomFichier);
		lireLeToken();
	}

	public Syntax() throws java.io.IOException {// lecture sur l'entrée standard
		lex = new Lexico();
		lireLeToken();
	}

	/* gestion des erreurs */
	private void erreur(String pb) throws SyntaxException {
		throw new SyntaxException(pb + " : ligne " + lex.numeroLigne() + "[dernier token :" + lex.image(leToken) + "]");
	}

	private void verifierToken(int tokenAttendu) throws SyntaxException {
		if (leToken != tokenAttendu)
			erreur(lex.image(tokenAttendu) + " token attendu");
	}

	/* les fins de lignes et fin de fichier */
	private void passerLignes() throws java.io.IOException {
		while (leToken == Lexico.TOKEN_EOL)
			leToken = lex.nextToken();
	}

	private boolean finFichier() {
		return (leToken == Lexico.TOKEN_EOF);
	}

	/* lire le token suivant : modifie la variable d'instance leToken */
	private void lireLeToken() throws java.io.IOException {
		leToken = lex.nextToken();
	}

	/* ---------------------------------------- */
	/* analyse syntaxique récursive descendante */
	/* ---------------------------------------- */

	private void terme() throws SyntaxException, IOException {
		System.out.println("terme");
		facteur();
		suiteTerme();
	}

	private void suiteTerme() throws SyntaxException, IOException {
		System.out.println("suiteTerme");
		if (leToken == Lexico.TOKEN_MULT || leToken == Lexico.TOKEN_DIV) {
      lireLeToken();
      facteur();
			suiteTerme();
		}
	}

	private void facteur() throws SyntaxException, IOException {
		System.out.println("facteur");
		//lireLeToken();
		if (leToken == Lexico.TOKEN_PAR_OUV) {
      lireLeToken();
			expression();
      verifierToken(Lexico.TOKEN_PAR_FER);
      lireLeToken();

		} else {
			atome();
		}
	}

	private void atome() throws SyntaxException, IOException {
		System.out.println("atome");
		if (leToken == Lexico.TOKEN_MOINS) {
			lireLeToken();
			this.subAtome();
		}else{
      this.subAtome();

		}
	}

	private void subAtome() throws IOException{
		if(leToken==Lexico.TOKEN_IDENTIF || leToken == Lexico.TOKEN_ENTIER )
			lireLeToken();
	}

	private void suiteExpression() throws SyntaxException, IOException {
		System.out.println("suiteExp");
		if (leToken == Lexico.TOKEN_PLUS || leToken == Lexico.TOKEN_MOINS) {
      lireLeToken();
    	terme();
			suiteExpression();
		}
	}

	private void expression() throws SyntaxException, java.io.IOException {
		System.out.println("expression");
		/* expression -> terme suiteExpression */
		terme();

		suiteExpression();
	}

	/* la seule méthode d'instance qui soit publique */
	public void analyse() throws SyntaxException, java.io.IOException {
    lireLeToken();
		passerLignes();
		while (!finFichier()) {
			expression();
			System.out.println("Expression OK");
      verifierToken(Lexico.TOKEN_EOL);
			passerLignes();
		}
	}

	public static void main(String args[]) throws Exception {
		Syntax synt;
		if (args.length == 0)
			synt = new Syntax();
		else
			synt = new Syntax(args[0]);

		synt.analyse();
	}
}
