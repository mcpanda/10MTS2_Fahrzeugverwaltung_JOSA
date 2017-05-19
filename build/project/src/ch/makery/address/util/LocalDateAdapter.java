package ch.makery.address.util;

/**************************************************************************/
/*                                                                        */
/* Import Section                                                         */
/*                                                                        */
/**************************************************************************/

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**************************************************************************/
/*                                                                        */
/* Class LocalDateAdapter                                                 */
/*                                                                        */
/**************************************************************************/
/**************************************************************************/
/*                                                                        */
/* Adapter (for JAXB) to convert between the LocalDate and the ISO 8601   */
/*                                                                        */
/**************************************************************************/

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}