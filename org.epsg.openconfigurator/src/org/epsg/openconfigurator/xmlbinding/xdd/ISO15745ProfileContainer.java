
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.ethernet-powerlink.org}ISO15745Profile" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "iso15745Profile"
})
@XmlRootElement(name = "ISO15745ProfileContainer")
public class ISO15745ProfileContainer {

    @XmlElement(name = "ISO15745Profile", required = true)
    protected List<ISO15745Profile> iso15745Profile;

    /**
     * Gets the value of the iso15745Profile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the iso15745Profile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getISO15745Profile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ISO15745Profile }
     * 
     * 
     */
    public List<ISO15745Profile> getISO15745Profile() {
        if (iso15745Profile == null) {
            iso15745Profile = new ArrayList<ISO15745Profile>();
        }
        return this.iso15745Profile;
    }

}
