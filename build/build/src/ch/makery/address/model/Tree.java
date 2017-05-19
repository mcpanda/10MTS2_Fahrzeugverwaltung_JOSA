/**************************************************************************************************/
/*! \file
  FILE         : $Source: Tree.java $
  BESCHREIBUNG : Treemodel
                 Modellierung eines Baumes/Tree
***************************************************************************************************/

/** \addtogroup Model
 *  @{
 */

package ch.makery.address.model;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import ch.makery.address.model.Node;
import java.util.LinkedList;
import java.util.Queue;

/***************************************************************************
CLASS:	Tree
*//*!
 Die Klasse Tree hat als Attribut root

***************************************************************************/

public class Tree {

	public Node root;			// Wurzel

    /***************************************************************************
    METHODENNAME:	Tree
    *//*!
     Konstruktor der Klasse Tree

     \param   void

     \return  void

    ***************************************************************************/

	public Tree () {
		this.root= null;
	}

    /***************************************************************************
    METHODENNAME:	addNode
    *//*!
     Hinzufuegen eines neuen Knotens, abhaengig ob der Personenvergleich
     positiv oder negativ ist.

     \param   Person

     \return  void

    ***************************************************************************/

	public void addNode(Person newperson) {
		/* erstelle einen Knoten mit der neuen Person */
		Node newNode = new Node(newperson);

		/* wenn der Baum leer ist, so wird der neue Knoten als Wurzel gesetzt */
		if(root == null)
		{
			this.root = newNode;

		} else {
			Node focusNode= root;
			Node parent= null;

			/* gehe alle Unterbaeume durch, bis ein pasender Platz gefunden wird */
			while(true)	{
				parent= focusNode;
				if(focusNode.ComparePersons(newperson) > 0){
					focusNode = focusNode.getLeftChild();
					if(focusNode == null)
					{
						parent.setLeftChild(newNode);
						return;
					}
				} else {
					focusNode = focusNode.getRightChild();
					if(focusNode == null)
					{
						parent.setRightChild(newNode);
						return;
					}
				}
			}
		}
	}

    /***************************************************************************
    METHODENNAME:	delete
    *//*!
     Loeschen eines Knotens/Blattes/Wurzel.

     \param   Person

     \return  boolean

    ***************************************************************************/
	public boolean delete(Person person) {
		Node parent= root;
		Node focusNode= root;

		boolean isLeftChild= false;

		/* Suche den Knoten mit der passenden Person */
		while(focusNode.ComparePersons(person) != 0) {
			parent= focusNode;

			if (focusNode.ComparePersons(person) > 0) {
				isLeftChild= true;
				focusNode= focusNode.getLeftChild();
			} else {
				isLeftChild= false;
				focusNode= focusNode.getRightChild();
			}

			/* wenn wir nichts finden, return false */
			if (focusNode == null) {
				return false;
			}
		}

		/* wenn wir etwas finden, so muessen wir eine Fallunterscheidung machen */
		/* abhaengig, ob der gefundene Knoten Nachfolger hat und ob es sich um die Wurzel handelt */

		/* 1. Fall: Knoten hat keine Nachfolger */
		if(focusNode.getLeftChild() == null && focusNode.getRightChild() == null) {
			/* falls wir die Wurzel gefunden haben und diese keine Blaetter hat, so loeschen wir die Wurzel */
			if (focusNode == root) {
				this.root = null;
			}

			/* falls wir den Knoten auf der linken oder rechten Seite gefunden haben, so muessen wir die Verbindung kappen */
			if(isLeftChild == true) {
				parent.setLeftChild(null);
			} else {
				parent.setRightChild(null);
			}
		}

		/* 2. Fall: Knoten hat nur einen Nachfolger */
		else if (focusNode.getRightChild() == null) {
			/* falls wir die Wurzel gefunden haben */
			if (focusNode == root) {
				root= focusNode.getLeftChild();
			} else if(isLeftChild) {
				parent.setLeftChild(focusNode.getLeftChild());
			} else {
				parent.setRightChild(focusNode.getLeftChild());
			}
		}
		else if (focusNode.getLeftChild() == null) {
			/* falls wir die Wurzel gefunden haben */
			if (focusNode == root) {
				root= focusNode.getRightChild();
			} else if(isLeftChild) {
				parent.setLeftChild(focusNode.getRightChild());
			} else {
				parent.setRightChild(focusNode.getRightChild());
			}
		}

		/* 3. Fall: Knoten hat zwei Nachfolger */
		/* Hierfuer muessen wir das kleinste Objekt in dem rechten SubBaum finden und als neuen Knoten setzen */
		else if(focusNode.getLeftChild() != null && focusNode.getRightChild() != null) {
			/* finde den kleinsten Nachfolger im rechten Sub-Tree */
			Node Nachfolger= findNachfolger(focusNode);

			/* falls wir die Wurzel gefunden haben */
			if (focusNode == root) {
				root= Nachfolger;
			}
			else if (isLeftChild) {
				parent.setLeftChild(Nachfolger);
			} else {
				parent.setRightChild(Nachfolger);
			}
			/* stelle Verknuepfung des Nachfolgers zur restlichen Sub-Baum her */
			Nachfolger.setLeftChild(focusNode.getLeftChild());
		}

		return true;
	}

	/***************************************************************************
    METHODENNAME:	findNachfolger
    *//*!
     Methode zum Finden des kleinsten Objektes im rechten UnterBaum, dh.
     den Knoten, der ganz links ist.

     \param   Node

     \return  Node

    ***************************************************************************/

	public Node findNachfolger(Node node) {
		Node Nachfolger= null;
		Node parent= null;
		Node focusNode= node.getRightChild();

		/* finde den Knoten, der ganz links steht */
		while (focusNode != null) {
			parent= Nachfolger;
			Nachfolger= focusNode;
			focusNode= focusNode.getLeftChild();
		}

		/* definiere den rightChild vom Nachfolger */
		if(Nachfolger != node.getRightChild()) {
			parent.setLeftChild(Nachfolger.getRightChild());
			Nachfolger.setRightChild(node.getRightChild());
		}

		return Nachfolger;
	}

	/***************************************************************************
    METHODENNAME:	clear
    *//*!
     Loeschen gesamten Baum

     \param   void

     \return  void

    ***************************************************************************/

	public void clear () {
		root= null;
	}

    /***************************************************************************
    METHODENNAME:	findNode
    *//*!
     Methode zum Finden einer Person anhand des Vornamens im binaeren Baum

     \param   firstName

     \return  Node

    ***************************************************************************/

    public Node findNode(String firstName) {
    	Node focusNode = root;

    	while(focusNode.getPerson().getFirstName().compareTo(firstName) != 0) {

    		if(focusNode.getPerson().getFirstName().compareTo(firstName) > 0)
    			{
    				focusNode = focusNode.getLeftChild();
    			} else {
    				focusNode = focusNode.getRightChild();
    			}

    		if(focusNode == null) {
    			return null;
    		}
    	}

		return focusNode;
	}

    /***************************************************************************
    METHODENNAME:	treeHeight
    *//*!
     Methode zur Berechnung der Baumhoehe; rekursiv

     \param   Node

     \return  int

    ***************************************************************************/

    public int treeHeight (Node node) {
    	if (node == null) {
    		return 0;
    	} else {
    		if(treeHeight(node.getLeftChild()) < treeHeight(node.getRightChild())) {
    			return (1 + treeHeight(node.getRightChild()));
    		} else {
    			return (1 + treeHeight(node.getLeftChild()));
    		}
    	}
    }

    /***************************************************************************
    METHODENNAME:	levelOrder
    *//*!
     Methode zum Darstellen des gesamten Baumes; schichtenweise.

     \param   Node

     \return  void

    ***************************************************************************/

    public void levelOrder(Node root) {
    	/* Hilfsliste */
    	Queue<Node> que= new LinkedList<Node>();

    	if (root == null) {
    		return;
    	}

    	que.add(root);

    	/* so lange die Liste nicht leer ist */
    	while (!que.isEmpty()) {
    		/* wir merken uns die Listengroesse */
    		int levelNodes= que.size();
    		/* so lange die Hilfsvariable groesser 0 ist */
    		while (levelNodes > 0) {
    			/* wirf einen Knoten aus der Liste aus und merke sich diesen */
    			Node node= (Node) que.remove();
    			System.out.print(" - ");

    			/* gebe in der Konsole die Vornamen der Nachfolger und des Knotens aus */
    			if (node.getLeftChild() != null) {
    				System.out.print(node.getLeftChild().getPerson().getFirstName() + "|");
    			} else {
    				System.out.print("null|");
    			}
        		System.out.print(node.getPerson().getFirstName());
        		if (node.getRightChild() != null) {
    				System.out.print("|" + node.getRightChild().getPerson().getFirstName());
    			} else {
    				System.out.print("|null");
    			}

        		/* falls der Knoten Nachfolger hatte, so werden diese in die Liste aufgenommen */
        		if (node.getLeftChild() != null) {
        			que.add(node.getLeftChild());
        		}
        		if (node.getRightChild() != null) {
        			que.add(node.getRightChild());
        		}
        		/* reduziere die Hilfsvariable zur Listengroesse */
        		levelNodes--;
    		}
    		System.out.println("");
    	}
    }
}
/** @}*/ /*end of doxygen group*/