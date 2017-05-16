/**************************************************************************************************/
/*! \file
  FILE         : $Source: AVLTree.java $
  BESCHREIBUNG : AVLTreemodel
                 Modellierung eines AVL Baumes/Tree
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

/***************************************************************************
CLASS:	AVLTree
*//*!
 Die Klasse AVLTree erbt von der Klasse Tree.
 Zusaetzlicher Attribut ist der Groelternknoten.
 Zusaetzliche Methoden sind Rotationen und die Reparatur.

***************************************************************************/

public class AVLTree extends Tree{

	private Node nodeGrandParent= null;

    /***************************************************************************
    METHODENNAME:	AVLTree
    *//*!
     Konstruktor der Klasse Tree

     \param   void

     \return  void

    ***************************************************************************/

	public AVLTree () {
		super();
	}


    /***************************************************************************
    METHODENNAME:	rightRotate
    *//*!
     Rechte Rotation um einen gegebenen Knoten

         Y        		X
        / \ 	=>     / \
       X  T3 		  T1  Y
      / \ 				 / \
     T1 T2				T2 T3

     \param   Node

     \return  Node

    ***************************************************************************/

	public Node rightRotate(Node Y) {
		/* Platzhalter */
		Node X= Y.getLeftChild();
		Node T2= X.getRightChild();

		/* Rotation */
		X.setRightChild(Y);
		Y.setLeftChild(T2);

		/* gebe neue Wurzel zurueck */
		return X;
	}

    /***************************************************************************
    METHODENNAME:	leftRotate
    *//*!
     linke Rotation um einen gegebenen Knoten

         X				Y
        / \			   / \
       T1  Y	=>    X  T3
          / \   	 / \
         T2 T3      T1 T2

     \param   Node

     \return  Node

    ***************************************************************************/

	public Node leftRotate(Node X) {
		/* Platzhalter */
		Node Y= X.getRightChild();
		Node T2= Y.getLeftChild();

		/* Rotation */
		Y.setLeftChild(X);
		X.setRightChild(T2);

		/* gebe neue Wurzel zurueck */
		return Y;
	}

    /***************************************************************************
    METHODENNAME:	getBalance
    *//*!
     Berechnung der Balance eines Knotens

     \param   Node

     \return  int

    ***************************************************************************/

	public int getBalance(Node node) {
		if(node == null) {
			return 0;
		}

		return (treeHeight(node.getLeftChild()) - treeHeight(node.getRightChild()));
	}

	/***************************************************************************
    METHODENNAME:	addNodeAVL
    *//*!
     Fuegt einen neuen Knoten hinzu und fuehrt ggf. eine Reparatur durch, falls
     die Balance aller Knoten außerhalb [-1; +1] liegt.

     \param   Person

     \return  void

    ***************************************************************************/
	public void addNodeAVL (Person person) {
		/* nutze Methode von Tree */
		addNode(person);

		nodeGrandParent= null;
		nodeGrandParent= findGrandparent(null, null, root);

		/* falls die Balance eines Knotens ausserhalb des Intervals [-1; +1] liegt, so wird repariert */
		if(getBalance(root) > 1 || getBalance(root) < -1 || nodeGrandParent != null) {
			repair(nodeGrandParent);
		}

	}

    /***************************************************************************
    METHODENNAME:	deleteAVL
    *//*!
     Loeschen eines Knotens/Blattes/Wurzel.
     Falls dabei das Balance-Kriterium verletzt wird, so wird repariert.

     \param   Person

     \return  void

    ***************************************************************************/
	public void deleteAVL(Person person) {
		boolean check= false;

		check= delete(person);

		if (check == true) {
			nodeGrandParent= null;
			nodeGrandParent= findGrandparent(null, null, root);
			if(getBalance(root) > 1 || getBalance(root) < -1 || nodeGrandParent != null) {
				repair(nodeGrandParent);
			}
		}
	}

	/***************************************************************************
    METHODENNAME:	findGrandparent
    *//*!
     Gehe Baum durch und finde die Großeltern mit einer Balance von +2 oder -2
     in der untersten Schicht; rekursiv

     \param   Node

     \return  Node

    ***************************************************************************/
	public Node findGrandparent(Node grandParent, Node parent, Node child) {
		grandParent= parent;
		parent= child;

		/* falls ein Knoten das Balance-Kriterium verletzt, so wird er sich gemerkt */
		if(getBalance(child) > 1 || getBalance(child) < -1) {
			if (grandParent != null) {
				nodeGrandParent= grandParent;
			}
		}

		/*gehe alle Knoten rekursiv durch */
		if (parent.getLeftChild() != null) {
			child= parent.getLeftChild();
			findGrandparent(grandParent, parent, child);
		}

		if(parent.getRightChild() != null) {
			child= parent.getRightChild();
			findGrandparent(grandParent, parent, child);
		}

		return nodeGrandParent;
	}


	/***************************************************************************
    METHODENNAME:	repair
    *//*!
     Falls die Balance goeßer 1 oder kleiner -1 ist, so muss durch
     Rotationen repariert werden;

     \param   Node

     \return  void

    ***************************************************************************/

	public void repair(Node grandParent) {

		Node parent= null;

		/* wenn grandParent != null ist, so sind wir ausserhalb der Wurzel */
		if(grandParent != null) {
			if(getBalance(grandParent) > 0) {
				parent= grandParent.getLeftChild();
			}
			if(getBalance(grandParent) < 0) {
				parent= grandParent.getRightChild();
			}
		} else {
			parent= this.root;
		}

		/* Fallunterscheidungen zur Rotationsanwendung */

		if(getBalance(parent) == 2) {

			/* 1. Fall: einfache rechtsrotation */
			if(getBalance(parent.getLeftChild()) == 1) {

				if (grandParent != null) {
					if (getBalance(grandParent) > 0) {
						grandParent.setLeftChild(rightRotate(parent));
					} else {
						grandParent.setRightChild(rightRotate(parent));
					}
				} else {
					root= rightRotate(parent);
				}
			}

			/* 2. Fall: links-rechtsrotation */
			if(getBalance(parent.getLeftChild()) == -1) {
				parent.setLeftChild(leftRotate(parent.getLeftChild()));
				if (grandParent != null) {
					if (getBalance(grandParent) > 0) {
						grandParent.setLeftChild(rightRotate(parent));
					} else {
						grandParent.setRightChild(rightRotate(parent));
					}
				} else {
					root= rightRotate(parent);
				}
			}
		}

		if(getBalance(parent) == -2) {

			/* 3. Fall: einfache linksrotation */
			if(getBalance(parent.getRightChild()) == -1) {

				if (grandParent != null) {
					if (getBalance(grandParent) < 0) {
						grandParent.setRightChild(leftRotate(parent));
					} else {
						grandParent.setLeftChild(leftRotate(parent));
					}
				} else {
					root= leftRotate(parent);
				}
			}

			/* 4. Fall: rechts-linksrotation */
			if(getBalance(parent.getRightChild()) == 1) {
				parent.setRightChild(rightRotate(parent.getRightChild()));
				if (grandParent != null) {
					if (getBalance(grandParent) < 0) {
						grandParent.setRightChild(leftRotate(parent));
					} else {
						grandParent.setLeftChild(leftRotate(parent));
					}
				} else {
					root= leftRotate(parent);
				}
			}
		}
	}
}
/** @}*/ /*end of doxygen group*/