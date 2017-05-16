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
 Die Klasse Node hat als Attribute person, leftChild, rightChild

***************************************************************************/

public class Node {
	private Person person;

	private Node leftChild;
	private Node rightChild;

    /***************************************************************************
    METHODENNAME:	Node
    *//*!
     Konstruktor der Klasse Node

     \param   person

     \return  void

    ***************************************************************************/

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
     Liefert +1, falls der Vorname lexikographisch groeﬂer ist.
     Liefert -1, falls der Vorname lexikographisch kleiner ist.
     Stimmen die Vornamen ueberein, so werden zusaetzlich die Nachnamen verglichen.
     Stimmen Vor- und Nachnamen ueberein, so werden zusaetzlich die PersonIDs
     verglichen.
     Somit herrscht eine totale Ordnung.

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

	public Node getLeftChild() {
		return this.leftChild;
	}

	public void setLeftChild(Node node) {
		this.leftChild= node;
	}

	public Node getRightChild() {
		return this.rightChild;
	}

	public void setRightChild(Node node) {
		this.rightChild= node;
	}
}

/** @}*/ /*end of doxygen group*/
