import ad.*;

public class RuleNode extends ADNode<Integer, Integer> {

    public RuleNode(int rule, int dotindex) {
	super(rule, dotindex);
    }

    public RuleNode getParent() {
	return (RuleNode)super.getParent();
    }
    
   public String toString() {
	String result = "";
        result = ruletoString() + "\n";
        for (ADNode c: children)
	    result += c.toString("  ");
	return result;
    }
    public String toString(String prefix) {
	String result = "";
        result = prefix + ruletoString() + "\n";
        for (ADNode c: children)
	    result += c.toString(prefix+"  ");
	return result;
    }
    
    public String ruletoString() {
	switch(this.getType()) {
	case rule.JSON_text:
	    return "JSON_text (dot pos = "+this.getValue()+")";
	case rule.OBJECT:
	    return "Object (dot pos = "+this.getValue()+")";	    
	case rule.ARRAY:
	    return "Array (dot pos = "+this.getValue()+")";	    
	case rule.MEMBER:
	    return "Member (dot pos = "+this.getValue()+")";	    
	case rule.VALUE:
	    return "Value (dot pos = "+this.getValue()+")";
	default:
	    return "Unknown";
	}
    }

    public String nodetoString() {
	return ruletoString();
    }

}
