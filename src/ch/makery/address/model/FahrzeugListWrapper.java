package ch.makery.address.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 *
 * @author Marco Jakob
 */
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