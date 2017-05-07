/**************************************************************************************************/
/*! \file
  FILE         : $Source: AVLTree.java $
  BESCHREIBUNG : AVLTreemodel
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

/***************************************************************************
CLASS:	AVLTree
*//*!
 Die Klasse AVLTree erbt von der Klasse Tree.
 Zusätzlicher Atrribut ist die Balance.
 Zusätzliche Methoden sind Rotationenund Suchen nach dem Nachnamen.

***************************************************************************/

public class AVLTree extends Tree{

	public Node getGrandParent= null;

    /***************************************************************************
    METHODENNAME:	AVLTree
    *//*!
     Konstruktor der Klasse Tree

     \param   void

     \return  void

    ***************************************************************************/

	public AVLTree () {
		root= null;
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
		Node X= Y.leftChild;
		Node T2= X.rightChild;

		/* Rotation */
		X.rightChild= Y;
		Y.leftChild= T2;

		/* gebe neue Wurzel zurück */
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
		Node Y= X.rightChild;
		Node T2= Y.leftChild;

		/* Rotation */
		Y.leftChild= X;
		X.rightChild= T2;

		/* gebe neue Wurzel zurück */
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

		return (treeHeight(node.leftChild) - treeHeight(node.rightChild));
	}

	/***************************************************************************
    METHODENNAME:	addNodeAVL
    *//*!
     Fügt einen neuen Knoten hinzu und führt ggf. eine Reparatur durch.
     (Balance aller Knoten liegt in (-1; +1)).

     \param   Person

     \return  void

    ***************************************************************************/
	public void addNodeAVL (Person person) {
		addNode(person);

		getGrandParent= null;
		getGrandParent= findGrandparent(null, null, root);
		if(getBalance(root) == 2 || getBalance(root) == -2 || getGrandParent != null) {
			System.out.println("repair");
			repair(getGrandParent);
		}

	}

    /***************************************************************************
    METHODENNAME:	deleteAVL
    *//*!
     Löschen eines Knotens/Blattes/Wurzel.
     Falls dabei das Balance-Kriterium verletzt wird, so wird repariert.

     \param   Person

     \return  void

    ***************************************************************************/
	public void deleteAVL(Person person) {
		boolean check= false;

		check= delete(person);

		if (check == true) {
			getGrandParent= null;
			getGrandParent= findGrandparent(null, null, root);
			if(getBalance(root) == 2 || getBalance(root) == -2 || getGrandParent != null) {

				System.out.println("repair");
				System.out.println("balance root: " + getBalance(root) );
				if (getGrandParent != null) {
					System.out.println("balance grandparent: " + getGrandParent.getPerson().getFirstName() + " " + getBalance(getGrandParent));
				}

				repair(getGrandParent);
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

		if(getBalance(child) == 2 || getBalance(child) == -2) {
			if (grandParent != null) {
				getGrandParent= grandParent;
			}
		}

		if (parent.leftChild != null) {
			child= parent.leftChild;
			findGrandparent(grandParent, parent, child);
		}

		if(parent.rightChild != null) {
			child= parent.rightChild;
			findGrandparent(grandParent, parent, child);
		}

		return getGrandParent;
	}


	/***************************************************************************
    METHODENNAME:	repair
    *//*!
     Gehe Baum nach dem einfügen einer neuen Person in Reihenfolge durch,
     dh. ganz links anfangen, und prüfe die Balance.
     Falls die Balance gößer 1 oder kleiner -1 ist, so muss durch
     Rotationen repariert werden;

     \param   Node

     \return  void

    ***************************************************************************/

	public void repair(Node grandParent) {

		Node parent= null;

		if(grandParent != null) {
			if(getBalance(grandParent) > 0) {
				parent= grandParent.leftChild;
			}
			if(getBalance(grandParent) < 0) {
				parent= grandParent.rightChild;
			}
		} else {
			parent= this.root;
		}

		if(getBalance(parent) == 2) {

			/* 1. Fall: einfache rechtsrotation */
			if(getBalance(parent.leftChild) == 1) {

				if (grandParent != null) {
					if (getBalance(grandParent) > 0) {
						grandParent.leftChild= rightRotate(parent);
					} else {
						grandParent.rightChild= rightRotate(parent);
					}
				} else {
					root= rightRotate(parent);
				}
			}

			/* 2. Fall: links-rechtsrotation */
			if(getBalance(parent.leftChild) == -1) {
				parent.leftChild= leftRotate(parent.leftChild);
				if (grandParent != null) {
					if (getBalance(grandParent) > 0) {
						grandParent.leftChild= rightRotate(parent);
					} else {
						grandParent.rightChild= rightRotate(parent);
					}
				} else {
					root= rightRotate(parent);
				}
			}
		}

		if(getBalance(parent) == -2) {

			/* 3. Fall: einfache linksrotation */
			if(getBalance(parent.rightChild) == -1) {

				if (grandParent != null) {
					if (getBalance(grandParent) < 0) {
						grandParent.rightChild= leftRotate(parent);
					} else {
						grandParent.leftChild= leftRotate(parent);
					}
				} else {
					root= leftRotate(parent);
				}
			}

			/* 4. Fall: rechts-linksrotation */
			if(getBalance(parent.rightChild) == 1) {
				parent.rightChild= rightRotate(parent.rightChild);
				if (grandParent != null) {
					if (getBalance(grandParent) < 0) {
						grandParent.rightChild= leftRotate(parent);
					} else {
						grandParent.leftChild= leftRotate(parent);
					}
				} else {
					root= leftRotate(parent);
				}
			}
		}
	}
}
/** @}*/ /*end of doxygen group*/