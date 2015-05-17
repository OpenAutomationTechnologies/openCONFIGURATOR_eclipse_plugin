
package org.epsg.openconfigurator.xmlbinding.xdd;

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
 *         &lt;element name="ProfileHeader" type="{http://www.ethernet-powerlink.org}ProfileHeader_DataType"/&gt;
 *         &lt;element name="ProfileBody" type="{http://www.ethernet-powerlink.org}ProfileBody_DataType"/&gt;
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
    "profileHeader",
    "profileBody"
})
@XmlRootElement(name = "ISO15745Profile")
public class ISO15745Profile {

    @XmlElement(name = "ProfileHeader", required = true)
    protected ProfileHeaderDataType profileHeader;
    @XmlElement(name = "ProfileBody", required = true)
    protected ProfileBodyDataType profileBody;

    /**
     * Gets the value of the profileHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileHeaderDataType }
     *     
     */
    public ProfileHeaderDataType getProfileHeader() {
        return profileHeader;
    }

    /**
     * Sets the value of the profileHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileHeaderDataType }
     *     
     */
    public void setProfileHeader(ProfileHeaderDataType value) {
        this.profileHeader = value;
    }

    /**
     * Gets the value of the profileBody property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileBodyDataType }
     *     
     */
    public ProfileBodyDataType getProfileBody() {
        return profileBody;
    }

    /**
     * Sets the value of the profileBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileBodyDataType }
     *     
     */
    public void setProfileBody(ProfileBodyDataType value) {
        this.profileBody = value;
    }

}
