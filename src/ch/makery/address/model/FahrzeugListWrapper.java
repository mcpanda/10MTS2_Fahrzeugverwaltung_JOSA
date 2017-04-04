/**************************************************************************************************/
/*! \file
  FILE         : $Source: FahrzeugListWrapper.java $
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

/**************************************************************************/
/*                                                                        */
/* Class FahrzeugListWrapper                                              */
/*                                                                        */
/**************************************************************************/

@XmlRootElement(name = "fahrzeugs")
public class FahrzeugListWrapper {

    private List<Fahrzeug> fahrzeugs;

    @XmlElement(name = "fahrzeug")
    public List<Fahrzeug> getFahrzeugs() {
        return fahrzeugs;
    }

    public void setFahrzeugs(List<Fahrzeug> fahrzeugs) {
        this.fahrzeugs = fahrzeugs;
    }
}

/** @}*/ /*end of doxygen group*/