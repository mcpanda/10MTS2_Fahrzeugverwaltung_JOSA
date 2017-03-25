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

/**************************************************************************/
/*                                                                        */
/* Class Buchung                                                          */
/*                                                                        */
/**************************************************************************/

public class Buchung {

	private final IntegerProperty buchungID;
    private final IntegerProperty personID;
    private final StringProperty lastname;
    private final IntegerProperty fahrzeugID;
    private final StringProperty hersteller;
    private final StringProperty fahrzeugtyp;
    private final ObjectProperty<LocalDate> ausleihdatum;
    private final ObjectProperty<LocalDate> rueckgabedatum;
    private final IntegerProperty leihdauer;

	/**************************************************************************/
	/*                                                                        */
	/* Constructurs                                                           */
	/*                                                                        */
	/**************************************************************************/

    public Buchung() {
        this(0, 0, 0, 0);
    }

    public Buchung(int buchungID, int personID, int fahrzeugID, int leihdauer) {
        this.buchungID = new SimpleIntegerProperty(buchungID);
    	this.personID = new SimpleIntegerProperty(personID);
        this.fahrzeugID = new SimpleIntegerProperty(fahrzeugID);
        this.leihdauer = new SimpleIntegerProperty(leihdauer);

        this.lastname = new SimpleStringProperty("");
        this.hersteller = new SimpleStringProperty("");
        this.fahrzeugtyp = new SimpleStringProperty("");
        this.ausleihdatum = new SimpleObjectProperty<LocalDate>(LocalDate.of(2017, 2, 21));
        this.rueckgabedatum = new SimpleObjectProperty<LocalDate>(LocalDate.of(2017, 2, 22));
    }

	/**************************************************************************/
	/*                                                                        */
	/* Getters and Setters													  */
	/* 																		  */
	/* Wir nutzen Properties um Änderungen unverzüglich anzeigen zu lassen    */
	/*                                                                        */
	/**************************************************************************/

    public int getBuchungID() {
        return buchungID.get();
    }

    public void setBuchungID(int buchungID) {
        this.buchungID.set(buchungID);
    }

    public IntegerProperty buchungIDProperty() {
        return buchungID;
    }

    public int getPersonID() {
        return personID.get();
    }

    public void setPersonID(int personID) {
        this.personID.set(personID);
    }

    public IntegerProperty personIDProperty() {
        return personID;
    }

    public int getFahrzeugID() {
        return fahrzeugID.get();
    }

    public void setFahrzeugID(int fahrzeugID) {
        this.fahrzeugID.set(fahrzeugID);
    }

    public IntegerProperty fahrzeugIDProperty() {
        return fahrzeugID;
    }

	public int getLeihdauer() {
		return leihdauer.get();
	}

	public void setLeihdauer(int leihdauer) {
		this.leihdauer.set(leihdauer);
	}

	public IntegerProperty leihdauerProperty() {
		return leihdauer;
	}

    public String getFahrzeugtyp() {
        return fahrzeugtyp.get();
    }

    public void setFahrzeugtyp(String fahrzeugtyp) {
        this.fahrzeugtyp.set(fahrzeugtyp);
    }

    public StringProperty fahrzeugtypProperty() {
        return fahrzeugtyp;
    }

    public String getLastname() {
        return lastname.get();
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public String getHersteller() {
        return hersteller.get();
    }

    public void setHersteller(String hersteller) {
        this.hersteller.set(hersteller);
    }

    public StringProperty herstellerProperty() {
        return hersteller;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getAusleihdatum() {
        return ausleihdatum.get();
    }

    public void setAusleihdatum(LocalDate ausleihdatum) {
        this.ausleihdatum.set(ausleihdatum);
    }

    public ObjectProperty<LocalDate> ausleihdatumProperty() {
        return ausleihdatum;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getRueckgabedatum() {
        return rueckgabedatum.get();
    }

    public void setRueckgabedatum(LocalDate rueckgabedatum) {
        this.rueckgabedatum.set(rueckgabedatum);
    }

    public ObjectProperty<LocalDate> rueckgabedatumProperty() {
        return rueckgabedatum;
    }
}