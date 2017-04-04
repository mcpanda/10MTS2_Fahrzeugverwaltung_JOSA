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
/* Class PersonListWrapper                                                */
/*                                                                        */
/**************************************************************************/
/**************************************************************************/
/*                                                                        */
/* Diese Klasse ist notwendig um die Daten in eine xml-Datei zu speichern */
/*                                                                        */
/**************************************************************************/

@XmlRootElement(name = "persons")
public class PersonListWrapper {

    private List<Person> persons;

    @XmlElement(name = "person")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}

/** @}*/ /*end of doxygen group*/