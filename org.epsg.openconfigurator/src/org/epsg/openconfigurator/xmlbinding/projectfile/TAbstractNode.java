
package org.epsg.openconfigurator.xmlbinding.projectfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * An abstract type for a POWERLINK node (MN or CN). 
 * 
 * <p>Java-Klasse für tAbstractNode complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="tAbstractNode"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ForcedObjects" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element ref="{http://sourceforge.net/projects/openconf/configuration}Object"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
 *       &lt;attribute name="isAsyncOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="isType1Router" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="isType2Router" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tAbstractNode", propOrder = {
    "forcedObjects"
})
@XmlSeeAlso({
    TCN.class,
    TMN.class
})
public abstract class TAbstractNode {

    @XmlElement(name = "ForcedObjects")
    protected TAbstractNode.ForcedObjects forcedObjects;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "isAsyncOnly")
    protected Boolean isAsyncOnly;
    @XmlAttribute(name = "isType1Router")
    protected Boolean isType1Router;
    @XmlAttribute(name = "isType2Router")
    protected Boolean isType2Router;

    /**
     * Ruft den Wert der forcedObjects-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TAbstractNode.ForcedObjects }
     *     
     */
    public TAbstractNode.ForcedObjects getForcedObjects() {
        return forcedObjects;
    }

    /**
     * Legt den Wert der forcedObjects-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TAbstractNode.ForcedObjects }
     *     
     */
    public void setForcedObjects(TAbstractNode.ForcedObjects value) {
        this.forcedObjects = value;
    }

    /**
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Ruft den Wert der isAsyncOnly-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIsAsyncOnly() {
        if (isAsyncOnly == null) {
            return false;
        } else {
            return isAsyncOnly;
        }
    }

    /**
     * Legt den Wert der isAsyncOnly-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAsyncOnly(Boolean value) {
        this.isAsyncOnly = value;
    }

    /**
     * Ruft den Wert der isType1Router-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIsType1Router() {
        if (isType1Router == null) {
            return false;
        } else {
            return isType1Router;
        }
    }

    /**
     * Legt den Wert der isType1Router-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsType1Router(Boolean value) {
        this.isType1Router = value;
    }

    /**
     * Ruft den Wert der isType2Router-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIsType2Router() {
        if (isType2Router == null) {
            return false;
        } else {
            return isType2Router;
        }
    }

    /**
     * Legt den Wert der isType2Router-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsType2Router(Boolean value) {
        this.isType2Router = value;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence maxOccurs="unbounded"&gt;
     *         &lt;element ref="{http://sourceforge.net/projects/openconf/configuration}Object"/&gt;
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
        "object"
    })
    public static class ForcedObjects {

        @XmlElement(name = "Object", required = true)
        protected List<Object> object;

        /**
         * Gets the value of the object property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the object property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getObject().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Object }
         * 
         * 
         */
        public List<Object> getObject() {
            if (object == null) {
                object = new ArrayList<Object>();
            }
            return this.object;
        }

    }

}
