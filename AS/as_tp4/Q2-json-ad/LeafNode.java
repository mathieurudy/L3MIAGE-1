import ad.*;

public class LeafNode extends ADNode<Integer, String> {

    public LeafNode(int token, String value) {
	super(token, value);
    }

    public String toString() {
	String result = "";
        result = symtoString() + "\n";
	return result;
    }
    public String toString(String prefix) {
	String result = "";
        result = prefix + symtoString() + "\n";
	return result;
    }
    
    public String symtoString() {
	switch(this.getType()) {
	case sym.EOF:
	    return("(EOF) (token #"+sym.EOF+")");
	case sym.UNKNOWN:
	    return("(UNKNOWN) (token #"+sym.UNKNOWN+")");
	case sym.BEGINARRAY:
	    return("(BEGINARRAY) (token #"+sym.BEGINARRAY+") '['");
	case sym.ENDARRAY:
	    return("(ENDARRAY) (token #"+sym.ENDARRAY+") ']'");
	case sym.BEGINOBJECT:
	    return("(BEGINOBJECT) (token #"+sym.BEGINOBJECT+") '{'");
	case sym.ENDOBJECT:
	    return("(ENDOBJECT) (token #"+sym.ENDOBJECT+") '}'");
	case sym.NAMESEPARATOR:
	    return("(NAMESEPARATOR) (token #"+sym.NAMESEPARATOR+") ':'");
	case sym.VALUESEPARATOR:
	    return("(VALUESEPARATOR) (token #"+sym.VALUESEPARATOR+") ','");
	case sym.FALSE:
	    return("(FALSE) (token #"+sym.FALSE+") 'false'");
	case sym.NULL:
	    return("(NULL) (token #"+sym.NULL+") 'null'");
	case sym.TRUE:
	    return("(TRUE) (token #"+sym.TRUE+") 'true'");
	case sym.NUMBER:
	    return("(NUMBER) (token #"+sym.NUMBER+") "+this.getValue() );
	case sym.STRING:
	    return("(STRING) (token #"+sym.STRING+") " + this.getValue());
	default:
	    return "Unknown";
	}
    }

    public String nodetoString() {
	return symtoString();
    }
}
