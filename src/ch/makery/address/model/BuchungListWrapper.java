package ch.makery.address.model;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**************************************************************************/
/*                                                                        */
/* Class buchungListWrapper                                              */
/*                                                                        */
/**************************************************************************/
/**************************************************************************/
/*                                                                        */
/* Diese Klasse ist notwendig um die Daten in eine xml-Datei zu speichern */
/*                                                                        */
/**************************************************************************/

@XmlRootElement(name = "buchungs")
public class BuchungListWrapper {

    private List<Buchung> buchungs;

    @XmlElement(name = "buchung")
    public List<Buchung> getBuchungs() {
        return buchungs;
    }

    public void setBuchungs(List<Buchung> buchungs) {
        this.buchungs = buchungs;
    }
}