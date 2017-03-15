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
    private final BooleanProperty Ausleihzustand;
    private final BooleanProperty Automatik;
    private final IntegerProperty Kilometerstand;
    private final IntegerProperty Sitzplaetze;

    /**
     * Default constructor.
     */
    public Fahrzeug() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param Hersteller
     * @param Marke
     */
    public Fahrzeug(String Hersteller, String Marke) {
        this.Hersteller = new SimpleStringProperty(Hersteller);
        this.Marke = new SimpleStringProperty(Marke);

        // Some initial dummy data, just for convenient testing.
        this.Kraftstoff = new SimpleStringProperty("some Kraftstoff");
        this.Leistung = new SimpleIntegerProperty(1234);
        this.Fahrzeugidentifikationsnummer = new SimpleStringProperty("some Fahrzeugidentifikationsnummer");
        this.Aenderungsdatum = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
        this.Ausleihzustand= new SimpleBooleanProperty(true);
        this.Automatik= new SimpleBooleanProperty(true);
        this.Kilometerstand = new SimpleIntegerProperty(110);
        this.Sitzplaetze = new SimpleIntegerProperty(4);
        
    }

 
   public boolean getAusleihzustand() {
		return Ausleihzustand.get();
	}
	
	public void setAusleihzustand(boolean Ausleihzustand) {
		this.Ausleihzustand.set(Ausleihzustand);
	}
	
	public BooleanProperty AusleihzustandProperty() {
		return Ausleihzustand;
	}
	
 	public boolean getAutomatik() {
		return Automatik.get();
	}
	
	public void setAutomatik(boolean Automatik) {
		this.Automatik.set(Automatik);
	}
	
	public BooleanProperty AutomatikProperty() {
		return Automatik;
	} 

	public int getSitzplaetze() {
		return Sitzplaetze.get();
	}
	
	public void setSitzplaetze(int Sitzplaetze) {
		this.Sitzplaetze.set(Sitzplaetze);
	}
	
	public IntegerProperty SitzplaetzeProperty() {
		return Sitzplaetze;
	}

	public int getKilometerstand() {
		return Kilometerstand.get();
	}

	public void setKilometerstand(int Kilometerstand) {
		this.Kilometerstand.set(Kilometerstand);
	}

	public IntegerProperty KilometerstandProperty() {
		return Kilometerstand;
	}

    public String getHersteller() {
        return Hersteller.get();
    }

    public void setHersteller(String Hersteller) {
        this.Hersteller.set(Hersteller);
    }

    public StringProperty HerstellerProperty() {
        return Hersteller;
    }

    public String getMarke() {
        return Marke.get();
    }

    public void setMarke(String Marke) {
        this.Marke.set(Marke);
    }

    public StringProperty MarkeProperty() {
        return Marke;
    }

    public String getKraftstoff() {
        return Kraftstoff.get();
    }

    public void setKraftstoff(String Kraftstoff) {
        this.Kraftstoff.set(Kraftstoff);
    }

    public StringProperty KraftstoffProperty() {
        return Kraftstoff;
    }

    public int getLeistung() {
        return Leistung.get();
    }

    public void setLeistung(int Leistung) {
        this.Leistung.set(Leistung);
    }

    public IntegerProperty LeistungProperty() {
        return Leistung;
    }

    public String getFahrzeugidentifikationsnummer() {
        return Fahrzeugidentifikationsnummer.get();
    }

    public void setFahrzeugidentifikationsnummer(String Fahrzeugidentifikationsnummer) {
        this.Fahrzeugidentifikationsnummer.set(Fahrzeugidentifikationsnummer);
    }

    public StringProperty FahrzeugidentifikationsnummerProperty() {
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