
package ch.makery.address.model;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/
import java.io.IOException;
import java.io.File;
import java.util.prefs.Preferences;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ch.makery.address.model.Person;
import ch.makery.address.model.Buchung;
import ch.makery.address.model.BuchungListWrapper;
import ch.makery.address.model.Fahrzeug;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.model.FahrzeugListWrapper;
import ch.makery.address.model.Tree;
import ch.makery.address.model.Nodee;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.FahrzeugEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.FahrzeugOverviewController;
import ch.makery.address.view.RootLayoutController;
import ch.makery.address.view.BirthdayStatisticsController;
import ch.makery.address.view.BuchungEditDialogController;
import ch.makery.address.view.BuchungOverviewController;

// mainApp.getPersonData();   / Person Liste
/*
 *
 * public Tree sortieren (ObservableList<Person> persons) {
 * persons.get(i).getLastName();
 * Tree baum= new Tree();
 *
 * for (int i= 0; i < persons.size(); i++)
 *
 * for(Person p : persons) {
 *
 * p.getLastName();
 * }
 *
 * return baum;
 *
 */



import ch.makery.address.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tree {

	Nodee root;
	public void addNodee(int personID, String firstName, String lastName, String street , int postalCode, String city)
	{
		Nodee newNodee = new Nodee(personID,firstName, lastName,  street , postalCode,  city);
		if(root == null)
		{
			root = newNodee;
		}
		else {
			Nodee focusNodee = root;
			Nodee parent;
			while(true)
			{
				parent = focusNodee;
				if(parent.getFirstName().compareTo(focusNodee.getFirstName()) == -1){
					focusNodee = focusNodee.leftChild;
					if(focusNodee == null)
					{
						parent.leftChild = newNodee;
						return ;
					}
				}
				else{
					focusNodee = focusNodee.rightChild;
					if(focusNodee == null)
					{
						parent.rightChild = newNodee;
						return;
					}
				}

			}
		}
	}

	public void inOrderTraverseTree(Nodee focusNodee)
	{
		if (focusNodee != null)
		inOrderTraverseTree(focusNodee.leftChild);
		System.out.println(focusNodee);
		inOrderTraverseTree(focusNodee.rightChild);
	}


	public void preOrderTraverseTree(Nodee focusNodee)
	{
		if (focusNodee != null)
		System.out.println(focusNodee);
		preOrderTraverseTree(focusNodee.leftChild);
		preOrderTraverseTree(focusNodee.rightChild);
	}




    public void postOrderTraverseTree(Nodee focusNodee) {
    	if (focusNodee != null) {
    		postOrderTraverseTree(focusNodee.leftChild);
    		postOrderTraverseTree(focusNodee.rightChild);
    		System.out.println(focusNodee);
        }
    }

    public Nodee findNodee(String firstName)
    {
    	Nodee focusNodee = root;
    	while(focusNodee.getFirstName().compareTo(firstName) != 0)
    			{
    				if(firstName.compareTo(focusNodee.getFirstName()) == -1 )
    				{
    					focusNodee = focusNodee.leftChild;
    				}
    				else
    				{
    					focusNodee = focusNodee.rightChild;
    				}
    				if(focusNodee == null)
    					return null;
    			}
    	if(focusNodee.getFirstName().compareTo(firstName) == 0)
    	{
    		System.out.println(focusNodee.getFirstName() + " " + focusNodee.getCity());
    	}
		return focusNodee;

		}


    public static void main (String[] args){

    }


}



//public class Node{
//
//	int personID;
//	String firstName;
//	String lastName;
//	String street;
//	int postalCode;
//	String city;
//	Node leftChild;
//	Node rightChild;
//
//	Node(int personID, String firstName, String lastName, String street , int postalCode, String city)
//	{
//		this.personID = personID;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.street = street;
//		this.postalCode = postalCode;
//		this.city = city;
//	}
//
////mainApp.getPersonData();
//
////	Node(ObservableList<Person> persons)
////	{
////
////		for(Person p : persons) {
////			this.personID= p.getPersonID();
////		}
////		this.personID = persons.get(i).getPersonID();
////
////
////	}
//
//	public String toString()
//	{
//
//		return firstName;
//	}
//
//
//
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
//}


