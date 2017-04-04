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

/***************************************************************************
CLASS:	Tree
*//*!
 Die Klasse Tree hat als Attribute personID, firstName, lastName,
 street, postalCode, city.

***************************************************************************/

public class Tree {

	Node root;

    /***************************************************************************
    METHODENNAME:	addNode
    *//*!
     Hinzufügen eines neuen Knotens, abhängig ob der Stringvergleich (.compareTo)
     positiv oder negativ ist.

     \param   personID, firstName, lastName, street, postalCode, city

     \return  void

    ***************************************************************************/

	public void addNode(int personID, String firstName, String lastName, String street , int postalCode, String city)
	{
		Node newNode = new Node(personID,firstName, lastName,  street , postalCode,  city);
		if(root == null)
		{
			root = newNode;
		}
		else {
			Node focusNode = root;
			Node parent;
			while(true)
			{
				parent = focusNode;
				if(parent.getFirstName().compareTo(focusNode.getFirstName()) == -1){
					focusNode = focusNode.leftChild;
					if(focusNode == null)
					{
						parent.leftChild = newNode;
						return ;
					}
				}
				else{
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

	public void inOrderTraverseTree(Node focusNode)
	{
		if (focusNode != null)
		inOrderTraverseTree(focusNode.leftChild);
//		System.out.println(focusNode);
		inOrderTraverseTree(focusNode.rightChild);
	}

	public void preOrderTraverseTree(Node focusNode)
	{
		if (focusNode != null)
//		System.out.println(focusNode);
		preOrderTraverseTree(focusNode.leftChild);
		preOrderTraverseTree(focusNode.rightChild);
	}

    public void postOrderTraverseTree(Node focusNode) {
    	if (focusNode != null) {
    		postOrderTraverseTree(focusNode.leftChild);
    		postOrderTraverseTree(focusNode.rightChild);
//    		System.out.println(focusNode);
        }
    }

    /***************************************************************************
    METHODENNAME:	findNote
    *//*!
     Methode zum Finden einer Person anhand des Vornamen im binären Baum

     \param   firstName

     \return  Node

    ***************************************************************************/

    public Node findNode(String firstName)
    {
    	Node focusNode = root;
    	while(focusNode.getFirstName().compareTo(firstName) != 0)
    			{
    				if(firstName.compareTo(focusNode.getFirstName()) == -1 )
    				{
    					focusNode = focusNode.leftChild;
    				}
    				else
    				{
    					focusNode = focusNode.rightChild;
    				}
    				if(focusNode == null)
    					return null;
    			}
//    	if(focusNode.getFirstName().compareTo(firstName) == 0)
//    	{
//    		System.out.println(focusNode.getFirstName() + " " + focusNode.getCity());
//    	}
		return focusNode;

		}

    public static void main (String[] args){

    }
}

/** @}*/ /*end of doxygen group*/