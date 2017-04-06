/**************************************************************************************************/
/*! \file
  FILE         : $Source: Person.java $
  BESCHREIBUNG : Personenmodel
                 Modellierung einer Person.
***************************************************************************************************/

/** \defgroup Model Model
 *  @{
 */

package ch.makery.address.model;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ch.makery.address.util.LocalDateAdapter;

/***************************************************************************
CLASS:	Person
*//*!
 Die Klasse Person hat als Attribute personID, firstName, lastName, street,
 postalCode, city, lizenz, ausgeliehen, birthday. <br>
 Wir nutzen Properties,um mit Hilfe von ObservableLists Änderungen
 unverzüglich anzeigen zu lassen.

***************************************************************************/

public class Person {

	private final IntegerProperty personID;				//!< personID
    private final StringProperty firstName;				//!< firstName
    private final StringProperty lastName;				//!< lastName
    private final StringProperty street;				//!< street
    private final IntegerProperty postalCode;			//!< postalCode
    private final StringProperty city;					//!< city
    private final StringProperty lizenz;				//!< lizenz
    private final StringProperty ausgeliehen;			//!< ausgeliehen
    private final ObjectProperty<LocalDate> birthday;	//!< birthday

	/**************************************************************************/
	/*                                                                        */
	/* Constructors                                                           */
	/*                                                                        */
	/**************************************************************************/

    public Person() {
        this(0, null, null, null, null, 0, null);
    }

    /***************************************************************************
    METHODENNAME:	Person
    *//*!
     Konstruktor der Klasse Person

     \param   personID, fistName, lastName, street, lizenz, postal, city

     \return  void

    ***************************************************************************/

    public Person(int personID, String firstName, String lastName, String street, String lizenz, int postal, String city) {
    	this.personID = new SimpleIntegerProperty(personID);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.street = new SimpleStringProperty(street);
        this.lizenz = new SimpleStringProperty(lizenz);
        this.postalCode = new SimpleIntegerProperty(postal);
        this.city = new SimpleStringProperty(city);

        this.ausgeliehen = new SimpleStringProperty("Nein");
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }

	/**************************************************************************/
	/*                                                                        */
	/* Getters and Setters													  */
	/*                                                                        */
	/**************************************************************************/

    public int getPersonID() {
        return personID.get();
    }

    public void setPersonID(int personID) {
        this.personID.set(personID);
    }

    public IntegerProperty personIDProperty() {
        return personID;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public String getLizenz() {
        return lizenz.get();
    }

    public void setLizenz(String lizenz) {
        this.lizenz.set(lizenz);
    }

    public StringProperty lizenzProperty() {
        return lizenz;
    }

    public String getAusgeliehen() {
        return ausgeliehen.get();
    }

    public void setAusgeliehen(String ausgeliehen) {
        this.ausgeliehen.set(ausgeliehen);
    }

    public StringProperty ausgeliehenProperty() {
        return ausgeliehen;
    }

    public int getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public String getPersonBeschreibung() {
    	return this.getPersonID() + " - " + this.getFirstName() + " " + this.getLastName();
    }
}

/** @}*/ /*end of doxygen group*/