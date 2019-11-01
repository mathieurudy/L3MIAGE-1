package ad;

import java.io.* ;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.UIManager;
import java.util.Iterator;
import java.awt.Dimension;

//import java_cup.runtime.Symbol;
// classe principale pour tester l'analyse syntaxique
public class ADViewer extends JPanel {

    private JTree tree;
    private java.io.ObjectInputStream infile;
    private ADNode root=null;
    private String filename="";
    private boolean debug = false;
    private static boolean useSystemLookAndFeel = true;

    public ADViewer(String fname)  {
	//	super(new GridLayout(1,0));
        filename=fname;
        try {
	infile= new java.io.ObjectInputStream(new FileInputStream(filename));
	root=(ADNode)infile.readObject();
	if (debug) System.out.println("ADNode:\n" + root.toString());
	infile.close();
        } catch (java.io.FileNotFoundException e) {
             System.err.println("File not found: "+filename+"\n"+e);
             System.exit(1);
	}
	catch (java.io.IOException e) {
             System.err.println("Can't open file "+filename+"\n"+e);
             System.exit(1);
	}
	catch (java.lang.ClassNotFoundException e) {
             System.err.println("Can't determine class for ast in "+filename);
             System.exit(1);
	}
        ;
        tree= new JTree(asttotree(root));
        tree.setPreferredSize(new Dimension(800,600));
	JScrollPane treeView = new JScrollPane(tree);
        add(treeView);
    }

    private DefaultMutableTreeNode asttotree(ADNode root) {
	DefaultMutableTreeNode top;
	//if (root.getParent() == null)
	//    top = new DefaultMutableTreeNode("AD for file: "+filename);
        //else
	//    top = new DefaultMutableTreeNode(root.getType().toString()+" "+(root.getValue()==null?" :- none":" :- "+root.getValue().toString()));
	top = new DefaultMutableTreeNode(root.nodetoString());
        for (Iterator<ADNode> asti=root.getChildren(); asti.hasNext(); top.add(asttotree(asti.next())));
       return(top);
    }

    public static void createAndShowGUI(String arg[])  {
        String fname="";
	if (arg.length == 1) fname=arg[0];
	else {
	    System.err.println("Missing filename");
	    System.exit(1);
	}
	if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(
					 UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }
	//Create and set up the window.
	JFrame frame = new JFrame("AST Viewer");

	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//Add content to the window.
	frame.add(new ADViewer(fname));

	//Display the window.
	frame.pack();
	frame.setVisible(true);
    }

        public static void main(final String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(args);
            }
        });
    }



}
