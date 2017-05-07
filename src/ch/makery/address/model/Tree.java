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
     Hinzufügen eines neuen Knotens, abhängig ob der Personenvergleich
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

			/* gehe alle Unterbäume durch, bis ein pasender Platz gefunden wird */
			while(true)	{
				parent= focusNode;
				if(focusNode.ComparePersons(newperson) > 0){
					focusNode = focusNode.leftChild;
					if(focusNode == null)
					{
						parent.leftChild = newNode;
						return;
					}
				} else {
					focusNode = focusNode.rightChild;
					if(focusNode == null)
					{
						parent.rightChild = newNode;
						return;
					}
				}
			}
		}
	}

    /***************************************************************************
    METHODENNAME:	delete
    *//*!
     Löschen eines Knotens/Blattes/Wurzel.

     \param   Person

     \return  boolean

    ***************************************************************************/
	public boolean delete(Person person) {
		Node parent= root;
		Node focusNode= root;

		boolean isLeftChild= false;

		while(focusNode.ComparePersons(person) != 0) {
			parent= focusNode;

			if (focusNode.ComparePersons(person) > 0) {
				isLeftChild= true;
				focusNode= focusNode.leftChild;
			} else {
				isLeftChild= false;
				focusNode= focusNode.rightChild;
			}

			/* wenn wir nichts finden, return false */
			if (focusNode == null) {
				return false;
			}
		}

		/* wenn wir etwas finden, so müssen wir eine Fallunterscheidung machen */

		/* 1. Fall: Knoten hat keine Nachfolger */
		if(focusNode.leftChild == null && focusNode.rightChild == null) {
			/* falls wir die wurzel gefunden haben und diese keine Blätter hat, so löschen wir die Wurzel */
			if (focusNode == root) {
				this.root = null;
			}

			/* falls wir den Knoten auf der linken oder rechten seite gefunden haben, so müssen wir die Verbindung kappen */
			if(isLeftChild == true) {
				parent.leftChild= null;
			} else {
				parent.rightChild= null;
			}
		}

		/* 2. Fall: Knoten hat nur einen Nachfolger */
		else if (focusNode.rightChild == null) {
			if (focusNode == root) {
				root= focusNode.leftChild;
			} else if(isLeftChild) {
				parent.leftChild= focusNode.leftChild;
			} else {
				parent.rightChild= focusNode.leftChild;
			}
		}
		else if (focusNode.leftChild == null) {
			if (focusNode == root) {
				root= focusNode.rightChild;
			} else if(isLeftChild) {
				parent.leftChild= focusNode.rightChild;
			} else {
				parent.rightChild= focusNode.rightChild;
			}
		}

		/* 3. Fall: Knoten hat zwei Nachfolger */
		/* Hierfür müssen wir das kleinste Objekt in dem rechten SubBaum finden und als neuen Knoten setzen */
		else if(focusNode.leftChild != null && focusNode.rightChild != null) {
			Node Nachfolger= findNachfolger(focusNode);

			if (focusNode == root) {
				root= Nachfolger;
			}
			else if (isLeftChild) {
				parent.leftChild= Nachfolger;
			} else {
				parent.rightChild= Nachfolger;
			}
			Nachfolger.leftChild= focusNode.leftChild;
		}

		return true;
	}

	/***************************************************************************
    METHODENNAME:	clear
    *//*!
     Löschen gesamten Baum

     \param   void

     \return  void

    ***************************************************************************/

	public void clear () {
		root= null;
	}

	/***************************************************************************
    METHODENNAME:	findNachfolger
    *//*!
     Methode zum Finden des kleinsten Objektes im rechten UnterBaum, dh.
     den linksten Knoten.

     \param   Node

     \return  Node

    ***************************************************************************/

	public Node findNachfolger(Node node) {
		Node Nachfolger= null;
		Node parent= null;
		Node focusNode= node.rightChild;

		/* finde den Knoten, der ganz links steht */
		while (focusNode != null) {
			parent= Nachfolger;
			Nachfolger= focusNode;
			focusNode= focusNode.leftChild;
		}

		/* definiere den rightChild vom Nachfolger */
		if(Nachfolger != node.rightChild) {
			parent.leftChild= Nachfolger.rightChild;
			Nachfolger.rightChild= node.rightChild;
		}

		return Nachfolger;
	}

    /***************************************************************************
    METHODENNAME:	findNode
    *//*!
     Methode zum Finden einer Person anhand des Vornamens im binären Baum

     \param   firstName oder Person

     \return  Node

    ***************************************************************************/

    public Node findNode(String firstName) {
    	Node focusNode = root;

    	while(focusNode.getPerson().getFirstName().compareTo(firstName) != 0) {

    		if(focusNode.getPerson().getFirstName().compareTo(firstName) > 0)
    			{
    				focusNode = focusNode.leftChild;
    			} else {
    				focusNode = focusNode.rightChild;
    			}

    		if(focusNode == null) {
    			return null;
    		}
    	}

		return focusNode;
	}

    /* Überlagerung der findNode Methode */
    public Node findNode(Person person) {
    	Node focusNode = root;

    	while(focusNode.ComparePersons(person) != 0) {
    		if(focusNode.ComparePersons(person) > 1)
    			{
    				focusNode = focusNode.leftChild;
    			} else {
    				focusNode = focusNode.rightChild;
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
     Methode zur Berechnung der Baumhöhe; rekursiv

     \param   Node

     \return  int

    ***************************************************************************/

    public int treeHeight (Node node) {
    	if (node == null) {
    		return 0;
    	} else {
    		if(treeHeight(node.leftChild) < treeHeight(node.rightChild)) {
    			return (1 + treeHeight(node.rightChild));
    		} else {
    			return (1 + treeHeight(node.leftChild));
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
    	Queue<Node> que= new LinkedList<Node>();

    	if (root == null) {
    		return;
    	}

    	que.add(root);

    	while (!que.isEmpty()) {
    		int levelNodes= que.size();
    		while (levelNodes > 0) {
    			Node node= (Node) que.remove();
    			System.out.print(" - ");
    			if (node.leftChild != null) {
    				System.out.print(node.leftChild.getPerson().getFirstName() + "|");
    			} else {
    				System.out.print("null|");
    			}
        		System.out.print(node.getPerson().getFirstName());
        		if (node.rightChild != null) {
    				System.out.print("|" + node.rightChild.getPerson().getFirstName());
    			} else {
    				System.out.print("|null");
    			}

        		if (node.leftChild != null) {
        			que.add(node.leftChild);
        		}
        		if (node.rightChild != null) {
        			que.add(node.rightChild);
        		}
        		levelNodes--;
    		}
    		System.out.println("");
    	}
    }
}
/** @}*/ /*end of doxygen group*/