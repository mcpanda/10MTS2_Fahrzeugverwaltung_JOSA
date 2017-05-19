/**************************************************************************************************/
/*! \file
  FILE         : $Source: BuchungListWrapper.java $
  BESCHREIBUNG : Diese Klasse ist notwendig um die Daten in eine xml-Datei zu speichern
  				 und die Daten aus einer xml-Datei zu laden
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

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/***************************************************************************
CLASS:	BuchungListWrapper
*//*!
 Diese Klasse ist notwendig um die Daten in eine xml-Datei zu speichern
 und die Daten aus einer xml-Datei zu laden

***************************************************************************/

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

/** @}*/ /*end of doxygen group*/