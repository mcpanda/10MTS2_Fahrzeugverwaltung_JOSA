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
     Füge neuen Knoten rekursiv hinzu, mit beachtung der AVL Bedingung
     (Balance aller Knoten liegt in (-1; +1).

     \param   Node, Person

     \return  Node

    ***************************************************************************/

	public Node addNodeAVL (Node node, Person person) {
		/* Füge zuerst neue Person hinzu */
		if (node == null) {
			return (new Node(person));
		}

		if(node.ComparePersons(person) > 0) {
			node.leftChild= addNodeAVL(node.leftChild, person);
		} else {
			node.rightChild= addNodeAVL(node.rightChild, person);
		}

		/* überprüfe Balance nach dem hinzufügen eines neuen Knotens */
		int balance= getBalance(node);

		/* Fallunterscheidung */
		/* 1. Fall: links links */
		if (balance > 1 && node.ComparePersons(person) > 0) {
			return rightRotate(node);
		}

		/* 2. Fall: rechts rechts */
		if (balance < -1 && node.ComparePersons(person) < 0) {
			return leftRotate(node);
		}

		/* 3. Fall: links rechts */
		if (balance > 1 && node.ComparePersons(person) < 0) {
			node.leftChild= leftRotate(node.leftChild);
			return rightRotate(node);
		}

		/* 4. Fall: rechts links */
		if (balance < -1 && node.ComparePersons(person) > 0) {
			node.rightChild= rightRotate(node.rightChild);
			return leftRotate(node);
		}

		return node;
	}
}
