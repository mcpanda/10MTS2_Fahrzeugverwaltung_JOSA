package ch.makery.address.model;

public class Nodee {
	int personID;
	String firstName;
	String lastName;
	String street;
	int postalCode;
	String city;
	Nodee leftChild;
	Nodee rightChild;

	Nodee(int personID, String firstName, String lastName, String street , int postalCode, String city)
	{
		this.personID = personID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
	}

//mainApp.getPersonData();

//	Nodee(ObservableList<Person> persons)
//	{
//
//		for(Person p : persons) {
//			this.personID= p.getPersonID();
//		}
//		this.personID = persons.get(i).getPersonID();
//
//
//	}

	public String toString()
	{

		return firstName;
	}



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

	public Nodee getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Nodee leftChild) {
		this.leftChild = leftChild;
	}

	public Nodee getRightChild() {
		return rightChild;
	}

	public void setRightChild(Nodee rightChild) {
		this.rightChild = rightChild;
	}

}
