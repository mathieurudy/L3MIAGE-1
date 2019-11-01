package ad;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;

public class ADNode<Type, Value>  implements Serializable {
    protected Type type;
    protected Value value;
    protected ArrayList<ADNode> children;
    protected ADNode parent;

    public ADNode() {
	type = null;
        value = null;
        children = new ArrayList<ADNode>();
        parent = null;
    }

    public ADNode(Type t) {
	type = t;
        value = null;
        children = new ArrayList<ADNode>();
        parent = null;
    }
    public ADNode(Type t, Value v) {
	type = t;
        value = v;
        children = new ArrayList<ADNode>();
        parent = null;
    }

    public Type getType() {
	return type;
    }

    public Value getValue() {
	return value;
    }

    public void setValue(Value v) {
	value=v;
    }
    public Iterator<ADNode> getChildren() {
	return children.iterator();
    }

    public ADNode getParent() {
        return parent;
    }

    public void appendChild(ADNode child) {
	children.add(child);
        if (child == null) System.out.println("Appending a null node"); else
        child.setParent(this);
    }

    public void appendChildren(ArrayList<ADNode> childs) {
	children.addAll(childs);
        for (ADNode c:childs) c.setParent(this);
    }

   public void addChild(ADNode child) {
       children.add(0, child);
        if (child == null) System.out.println("Appending a null node"); else
        child.setParent(this);
    }

  public void addChildren(ArrayList<ADNode> childs) {
       children.addAll(0, childs);
        for (ADNode c:childs) c.setParent(this);
    }

    protected void setParent(ADNode p) {
	parent=p;
    }

    public String toString() {
	String result = "";
        result = type.toString() + ":" + ((value!=null)?value.toString():"none") + "\n";
        for (ADNode c: children)
	    result += c.toString("  ");
	return result;
    }
    public String toString(String prefix) {
	String result = "";
        result = prefix + type.toString() + ":" + ((value!=null)?value.toString():"none")  + "\n";
        for (ADNode c: children)
	    result += c.toString(prefix+"  ");
	return result;
    }

    public String nodetoString() {
	String result = "";
        result = type.toString() + ":" + ((value!=null)?value.toString():"none");
	return result;
    }
}
