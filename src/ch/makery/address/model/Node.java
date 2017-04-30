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
//	int personID;
//	String firstName;
//	String lastName;
//	String street;
//	int postalCode;
//	String city;
	Person person;

	Node leftChild;
	Node rightChild;

    /***************************************************************************
    METHODENNAME:	Node
    *//*!
     Konstruktor der Klasse Node

     \param   personID, firstName, lastName, street, postalCode, city

     \return  void

    ***************************************************************************/

//	public Node(int personID, String firstName, String lastName, String street , int postalCode, String city)
//	{
//		this.personID = personID;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.street = street;
//		this.postalCode = postalCode;
//		this.city = city;
//	}

	public Node(Person persons) {
		this.person= persons;

		this.leftChild= null;
		this.rightChild= null;
	}

    /***************************************************************************
    METHODENNAME:	ComparePersons
    *//*!
     Vergleicht Person dieses Knoten mit einer neuen Person
     anhand des Vor- und Nachnamens.
     Liefert +1, falls der Vorname lexikographisch größer ist.
     Liefert -1, falls der Vorname lexikographisch kleiner ist.
     Stimmen die Vornamen überein, so werden zusätzlich die Nachnamen verglichen.
     Stimmen Vor- und Nachnamen überein, so werden zusätzlich die PersonIDs
     verglichen.
     Somit ist herrscht eine totale Ordnung.

     \param   Person

     \return  int

    ***************************************************************************/

	public int ComparePersons (Person newperson) {
		/* vergleiche Vornamen */
		if (person.getFirstName().compareTo(newperson.getFirstName()) < 0) {
			return -1;
		}

		if (person.getFirstName().compareTo(newperson.getFirstName()) > 0) {
			return +1;
		}

		/* Bei Gleichheit der Vornamen, vergleiche Nachnamen */
		if (person.getFirstName().compareTo(newperson.getFirstName()) == 0) {
			if (person.getLastName().compareTo(newperson.getLastName()) < 0) {
				return -1;
			}

			if (person.getLastName().compareTo(newperson.getLastName()) > 0) {
				return +1;
			}

			/* Bei Gleichheit der Nachnamen, vergleiche IDs */
			if (person.getLastName().compareTo(newperson.getLastName()) == 0) {
				if (person.getPersonID() < newperson.getPersonID()) {
					return -1;
				}

				if (person.getPersonID() > newperson.getPersonID()) {
					return +1;
				}
			}
		}
		return 0;
	}

	/**************************************************************************/
	/*                                                                        */
	/* Getters and Setters													  */
	/*                                                                        */
	/**************************************************************************/

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person persons) {
		this.person= persons;
	}

//	public int getPersonID() {
//		return personID;
//	}
//
//	public void setPersonID(int personID) {
//		this.personID = personID;
//	}
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getStreet() {
//		return street;
//	}
//
//	public void setStreet(String street) {
//		this.street = street;
//	}
//
//	public int getPostalCode() {
//		return postalCode;
//	}
//
//	public void setPostalCode(int postalCode) {
//		this.postalCode = postalCode;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public Node getLeftChild() {
//		return leftChild;
//	}
//
//	public void setLeftChild(Node leftChild) {
//		this.leftChild = leftChild;
//	}
//
//	public Node getRightChild() {
//		return rightChild;
//	}
//
//	public void setRightChild(Node rightChild) {
//		this.rightChild = rightChild;
//	}

}

/** @}*/ /*end of doxygen group*/
