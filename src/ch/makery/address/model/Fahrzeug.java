package ch.makery.address.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ch.makery.address.util.LocalDateAdapter;

/**
 * Model class for a Fahrzeug.
 *
 * @author Marco Jakob
 */
public class Fahrzeug {

    private final StringProperty Hersteller;
    private final StringProperty Marke;
    private final StringProperty Kraftstoff;
    private final IntegerProperty Leistung;
    private final StringProperty Fahrzeugidentifikationsnummer;
    private final ObjectProperty<LocalDate> Aenderungsdatum;
    private final StringProperty Ausleihzustand;
    private final StringProperty Automatik;
    private final IntegerProperty Kilometerstand;

    /**
     * Default constructor.
     */
    public Fahrzeug() {
        this(null, null, null, 0, null, null, null, 0);
    }

    /**
     * Constructor with some initial data.
     *
     * @param Hersteller
     * @param Marke
     */
    public Fahrzeug(String Hersteller, String Marke, String kraftstoff, int leistung, String fahrzeugidentifikationsnummer, String ausleihzustand, String automatik, int kilometerstand) {
        this.Hersteller = new SimpleStringProperty(Hersteller);
        this.Marke = new SimpleStringProperty(Marke);
        this.Kraftstoff = new SimpleStringProperty(kraftstoff);
        this.Leistung = new SimpleIntegerProperty(leistung);
        this.Fahrzeugidentifikationsnummer = new SimpleStringProperty(fahrzeugidentifikationsnummer);
        this.Ausleihzustand= new SimpleStringProperty(ausleihzustand);
        this.Automatik= new SimpleStringProperty(automatik);
        this.Kilometerstand = new SimpleIntegerProperty(kilometerstand);

        // Some initial dummy data, just for convenient testing.
        this.Aenderungsdatum = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));

    }


   public String getAusleihzustand() {
		return Ausleihzustand.get();
	}

	public void setAusleihzustand(String Ausleihzustand) {
		this.Ausleihzustand.set(Ausleihzustand);
	}

	public StringProperty ausleihzustandProperty() {
		return Ausleihzustand;
	}

 	public String getAutomatik() {
		return Automatik.get();
	}

	public void setAutomatik(String Automatik) {
		this.Automatik.set(Automatik);
	}

	public StringProperty automatikProperty() {
		return Automatik;
	}

	public int getKilometerstand() {
		return Kilometerstand.get();
	}

	public void setKilometerstand(int Kilometerstand) {
		this.Kilometerstand.set(Kilometerstand);
	}

	public IntegerProperty kilometerstandProperty() {
		return Kilometerstand;
	}

    public String getHersteller() {
        return Hersteller.get();
    }

    public void setHersteller(String Hersteller) {
        this.Hersteller.set(Hersteller);
    }

    public StringProperty herstellerProperty() {
        return Hersteller;
    }

    public String getMarke() {
        return Marke.get();
    }

    public void setMarke(String Marke) {
        this.Marke.set(Marke);
    }

    public StringProperty markeProperty() {
        return Marke;
    }

    public String getKraftstoff() {
        return Kraftstoff.get();
    }

    public void setKraftstoff(String Kraftstoff) {
        this.Kraftstoff.set(Kraftstoff);
    }

    public StringProperty kraftstoffProperty() {
        return Kraftstoff;
    }

    public int getLeistung() {
        return Leistung.get();
    }

    public void setLeistung(int Leistung) {
        this.Leistung.set(Leistung);
    }

    public IntegerProperty leistungProperty() {
        return Leistung;
    }

    public String getFahrzeugidentifikationsnummer() {
        return Fahrzeugidentifikationsnummer.get();
    }

    public void setFahrzeugidentifikationsnummer(String Fahrzeugidentifikationsnummer) {
        this.Fahrzeugidentifikationsnummer.set(Fahrzeugidentifikationsnummer);
    }

    public StringProperty fahrzeugidentifikationsnummerProperty() {
        return Fahrzeugidentifikationsnummer;
    }



    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getAenderungsdatum() {
        return Aenderungsdatum.get();
    }

    public void setAenderungsdatum(LocalDate Aenderungsdatum) {
        this.Aenderungsdatum.set(Aenderungsdatum);
    }

    public ObjectProperty<LocalDate> AenderungsdatumProperty() {
        return Aenderungsdatum;
    }
}