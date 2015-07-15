
package org.epsg.openconfigurator.xmlbinding.projectfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * The Node collection element is a container for all nodes in a POWERLINK
 *                 network.
 *
 * <p>Java class for tNodeCollection complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="tNodeCollection"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MN" type="{http://sourceforge.net/projects/openconf/configuration}tMN"/&gt;
 *         &lt;element name="RMN" type="{http://sourceforge.net/projects/openconf/configuration}tRMN" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="CN" type="{http://sourceforge.net/projects/openconf/configuration}tCN" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tNodeCollection", propOrder = {
    "mn",
    "rmn",
    "cn"
})
public class TNodeCollection {

    @XmlElement(name = "MN", required = true)
    protected TMN mn;
    @XmlElement(name = "RMN")
    protected List<TRMN> rmn;
    @XmlElement(name = "CN")
    protected List<TCN> cn;

    /**
     * Gets the value of the mn property.
     *
     * @return
     *     possible object is
     *     {@link TMN }
     *
     */
    public TMN getMN() {
        return mn;
    }

    /**
     * Sets the value of the mn property.
     *
     * @param value
     *     allowed object is
     *     {@link TMN }
     *
     */
    public void setMN(TMN value) {
        this.mn = value;
    }

    /**
     * Gets the value of the rmn property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rmn property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRMN().add(newItem);
     * </pre>
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TRMN }
     *
     *
     */
    public List<TRMN> getRMN() {
        if (rmn == null) {
            rmn = new ArrayList<TRMN>();
        }
        return this.rmn;
    }

    /**
     * Gets the value of the cn property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cn property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCN().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TCN }
     *
     *
     */
    public List<TCN> getCN() {
        if (cn == null) {
            cn = new ArrayList<TCN>();
        }
        return this.cn;
    }

}
