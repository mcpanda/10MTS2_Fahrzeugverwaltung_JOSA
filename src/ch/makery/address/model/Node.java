/**************************************************************************************************/
/*! \file
  FILE         : $Source: Node.java $
  BESCHREIBUNG : Nodemodel
                 Modellierung eines Knoten/Node mit linken und rechten Abzweigungen (Kindern)
***************************************************************************************************/

/** \addtogroup Model
 *  @{
 */

package ch.makery.address.model;

/***************************************************************************
CLASS:	Node
*//*!
 Die Klasse Node hat als Attribute personID, firstName, lastName, street,
 postalCode, city, leftChild, rightChild.

***************************************************************************/

public class Node {
	int personID;
	String firstName;
	String lastName;
	String street;
	int postalCode;
	String city;

	Node leftChild;
	Node rightChild;

    /***************************************************************************
    METHODENNAME:	Node
    *//*!
     Konstruktor der Klasse Node

     \param   personID, firstName, lastName, street, postalCode, city

     \return  void

    ***************************************************************************/

	public Node(int personID, String firstName, String lastName, String street , int postalCode, String city)
	{
		this.personID = personID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
	}

	/**************************************************************************/
	/*                                                                        */
	/* Getters and Setters													  */
	/*                                                                        */
	/**************************************************************************/

	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

}

/** @}*/ /*end of doxygen group*/
