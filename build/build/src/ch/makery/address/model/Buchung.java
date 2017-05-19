/**************************************************************************************************/
/*! \file
  FILE         : $Source: Buchung.java $
  BESCHREIBUNG : Buchungsmodel
                 Modellierung einer Buchung mit eindeutiger BuchungsID. Über die PersonID und
                 FahrzeugID lassen sich Verknüpfungen zu bestehenden Fahrzeugen und Personen
                 erstellen.
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

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ch.makery.address.util.LocalDateAdapter;

/***************************************************************************
CLASS:	Buchung
*//*!
 Die Klasse Buchung hat als Attribute buchungID, personID, fahrzeugID,
 ausleihdatum, rueckgabedatum.<br>
 Wir nutzen Properties,um mit Hilfe von ObservableLists Änderungen
 unverzüglich anzeigen zu lassen.

***************************************************************************/

public class Buchung {

	private final IntegerProperty buchungID;				//!< BuchungID
    private final IntegerProperty personID;					//!< PersonID
    private final IntegerProperty fahrzeugID;				//!< Fahrzeug ID
    private final ObjectProperty<LocalDate> ausleihdatum;	//!< Ausleihdatum
    private final ObjectProperty<LocalDate> rueckgabedatum;	//!< Rueckgabedatum
    private final IntegerProperty leihdauer;				//!< Leihdauer

	/**************************************************************************/
	/*                                                                        */
	/* Constructor                                                            */
	/*                                                                        */
	/**************************************************************************/

    public Buchung() {
        this(0, 0, 0, 0);
    }

    /***************************************************************************
    METHODENNAME:	Buchung
    *//*!
     Konstruktor der Klasse Buchung

     \param   buchungID, personID, fahrzeugID, leihdauer

     \return  void

    ***************************************************************************/

    public Buchung(int buchungID, int personID, int fahrzeugID, int leihdauer) {
        this.buchungID = new SimpleIntegerProperty(buchungID);
    	this.personID = new SimpleIntegerProperty(personID);
        this.fahrzeugID = new SimpleIntegerProperty(fahrzeugID);
        this.leihdauer = new SimpleIntegerProperty(leihdauer);

        this.ausleihdatum = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.rueckgabedatum = new SimpleObjectProperty<LocalDate>(LocalDate.now());
    }

	/**************************************************************************/
	/*                                                                        */
	/* Getters and Setters													  */
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

/** @}*/ /*end of doxygen group*/