/**************************************************************************************************/
/*! \file
  FILE         : $Source: Fahrzeug.java $
  BESCHREIBUNG : Fahrzeugmodel
                 Modellierung eines Fahrzeugs.
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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/***************************************************************************
CLASS:	Fahrzeug
*//*!
 Die Klasse Fahrzeug hat als Attribute fahrzeugID, hersteller, marke, kraftstaoff,
 leistung, kilometerstand, fahrzeugtyp, ausgeliehen. <br>
 Wir nutzen Properties,um mit Hilfe von ObservableLists Änderungen
 unverzüglich anzeigen zu lassen.

***************************************************************************/

public class Fahrzeug {

	private final IntegerProperty fahrzeugID;
    private final StringProperty hersteller;
    private final StringProperty marke;
    private final StringProperty kraftstoff;
    private final IntegerProperty leistung;
    private final IntegerProperty kilometerstand;
    private final StringProperty fahrzeugtyp;
    private final StringProperty ausgeliehen;
//    private final ObjectProperty<LocalDate> aenderungsdatum;

	/**************************************************************************/
	/*                                                                        */
	/* Constructors                                                           */
	/*                                                                        */
	/**************************************************************************/

    public Fahrzeug() {
        this(0, null, null, null, null, 0, 0);
    }

    /***************************************************************************
    METHODENNAME:	Fahrzeug
    *//*!
     Konstruktor der Klasse Fahrzeug

     \param   fahrzeugID, hersteller, marke, kraftstoff, fahrzeugtyp, leistung,
     		  kilometerstand

     \return  void

    ***************************************************************************/

    public Fahrzeug(int fahrzeugID, String hersteller, String marke, String kraftstoff, String fahrzeugtyp, int leistung, int kilometerstand) {
        this.fahrzeugID = new SimpleIntegerProperty(fahrzeugID);
    	this.hersteller = new SimpleStringProperty(hersteller);
        this.marke = new SimpleStringProperty(marke);
        this.kraftstoff = new SimpleStringProperty(kraftstoff);
        this.fahrzeugtyp = new SimpleStringProperty(fahrzeugtyp);
        this.leistung = new SimpleIntegerProperty(leistung);
        this.kilometerstand = new SimpleIntegerProperty(kilometerstand);

        this.ausgeliehen = new SimpleStringProperty("Nein");
//        this.aenderungsdatum = new SimpleObjectProperty<LocalDate>(LocalDate.of(2017, 2, 21));
    }

	/**************************************************************************/
	/*                                                                        */
	/* Getters and Setters													  */
	/*                                                                        */
	/**************************************************************************/

    public int getFahrzeugID() {
        return fahrzeugID.get();
    }

    public void setFahrzeugID(int fahrzeugID) {
        this.fahrzeugID.set(fahrzeugID);
    }

    public IntegerProperty fahrzeugIDProperty() {
        return fahrzeugID;
    }

	public int getKilometerstand() {
		return kilometerstand.get();
	}

	public void setKilometerstand(int kilometerstand) {
		this.kilometerstand.set(kilometerstand);
	}

	public IntegerProperty kilometerstandProperty() {
		return kilometerstand;
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

    public String getMarke() {
        return marke.get();
    }

    public void setMarke(String marke) {
        this.marke.set(marke);
    }

    public StringProperty markeProperty() {
        return marke;
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

    public String getAusgeliehen() {
        return ausgeliehen.get();
    }

    public void setAusgeliehen(String ausgeliehen) {
        this.ausgeliehen.set(ausgeliehen);
    }

    public StringProperty ausgeliehenProperty() {
        return ausgeliehen;
    }

    public String getKraftstoff() {
        return kraftstoff.get();
    }

    public void setKraftstoff(String kraftstoff) {
        this.kraftstoff.set(kraftstoff);
    }

    public StringProperty kraftstoffProperty() {
        return kraftstoff;
    }

    public int getLeistung() {
        return leistung.get();
    }

    public void setLeistung(int leistung) {
        this.leistung.set(leistung);
    }

    public IntegerProperty leistungProperty() {
        return leistung;
    }

    public String getFahrzeugBeschreibung() {
    	return this.getFahrzeugID() + " - " + this.getHersteller() + " " + this.getMarke();
    }
}

/** @}*/ /*end of doxygen group*/