
package org.epsg.openconfigurator.xmlbinding.projectfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;p xmlns:oc="http://sourceforge.net/projects/openconf/configuration" xmlns:ppc="http://ethernet-powerlink.org/POWERLINK" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt; One Node to rule them all,&lt;br/&gt; One Node to find them,&lt;br/&gt; One Node to bring them all and in the
 *                     darkness bind them.&lt;br/&gt; In the Land of Mordor where the Shadows lie. &lt;/p&gt;
 * </pre>
 *  A concrete type for a
 *                 POWERLINK MN. 
 * 
 * <p>Java class for tMN complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tMN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://sourceforge.net/projects/openconf/configuration}tAbstractNode"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RMNList" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="RMN" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="nodeIDRef" use="required"&gt;
 *                             &lt;simpleType&gt;
 *                               &lt;union memberTypes=" {http://sourceforge.net/projects/openconf/configuration}tDefaultRedundantMNNodeID {http://sourceforge.net/projects/openconf/configuration}tRegularCNNodeID"&gt;
 *                               &lt;/union&gt;
 *                             &lt;/simpleType&gt;
 *                           &lt;/attribute&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="nodeID" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" fixed="240" /&gt;
 *       &lt;attribute name="pathToXDC"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI"&gt;
 *             &lt;minLength value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="transmitsPRes" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="asyncSlotTimeout"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt"&gt;
 *             &lt;minInclusive value="250"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="aSndMaxNumber" default="1"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedByte"&gt;
 *             &lt;minInclusive value="1"/&gt;
 *             &lt;maxInclusive value="254"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tMN", propOrder = {
    "rmnList"
})
public class TMN
    extends TAbstractNode
{

    @XmlElement(name = "RMNList")
    protected TMN.RMNList rmnList;
    @XmlAttribute(name = "nodeID", required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected short nodeID;
    @XmlAttribute(name = "pathToXDC")
    protected String pathToXDC;
    @XmlAttribute(name = "transmitsPRes")
    protected Boolean transmitsPRes;
    @XmlAttribute(name = "asyncSlotTimeout")
    protected Long asyncSlotTimeout;
    @XmlAttribute(name = "aSndMaxNumber")
    protected Short aSndMaxNumber;

    /**
     * Gets the value of the rmnList property.
     * 
     * @return
     *     possible object is
     *     {@link TMN.RMNList }
     *     
     */
    public TMN.RMNList getRMNList() {
        return rmnList;
    }

    /**
     * Sets the value of the rmnList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TMN.RMNList }
     *     
     */
    public void setRMNList(TMN.RMNList value) {
        this.rmnList = value;
    }

    /**
     * Gets the value of the nodeID property.
     * 
     */
    public short getNodeID() {
        return nodeID;
    }

    /**
     * Sets the value of the nodeID property.
     * 
     */
    public void setNodeID(short value) {
        this.nodeID = value;
    }

    /**
     * Gets the value of the pathToXDC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathToXDC() {
        return pathToXDC;
    }

    /**
     * Sets the value of the pathToXDC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathToXDC(String value) {
        this.pathToXDC = value;
    }

    /**
     * Gets the value of the transmitsPRes property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isTransmitsPRes() {
        if (transmitsPRes == null) {
            return false;
        } else {
            return transmitsPRes;
        }
    }

    /**
     * Sets the value of the transmitsPRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTransmitsPRes(Boolean value) {
        this.transmitsPRes = value;
    }

    /**
     * Gets the value of the asyncSlotTimeout property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAsyncSlotTimeout() {
        return asyncSlotTimeout;
    }

    /**
     * Sets the value of the asyncSlotTimeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAsyncSlotTimeout(Long value) {
        this.asyncSlotTimeout = value;
    }

    /**
     * Gets the value of the aSndMaxNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getASndMaxNumber() {
        if (aSndMaxNumber == null) {
            return ((short) 1);
        } else {
            return aSndMaxNumber;
        }
    }

    /**
     * Sets the value of the aSndMaxNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setASndMaxNumber(Short value) {
        this.aSndMaxNumber = value;
    }


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
     *         &lt;element name="RMN" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="nodeIDRef" use="required"&gt;
     *                   &lt;simpleType&gt;
     *                     &lt;union memberTypes=" {http://sourceforge.net/projects/openconf/configuration}tDefaultRedundantMNNodeID {http://sourceforge.net/projects/openconf/configuration}tRegularCNNodeID"&gt;
     *                     &lt;/union&gt;
     *                   &lt;/simpleType&gt;
     *                 &lt;/attribute&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "rmn"
    })
    public static class RMNList {

        @XmlElement(name = "RMN", required = true)
        protected List<TMN.RMNList.RMN> rmn;

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
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TMN.RMNList.RMN }
         * 
         * 
         */
        public List<TMN.RMNList.RMN> getRMN() {
            if (rmn == null) {
                rmn = new ArrayList<TMN.RMNList.RMN>();
            }
            return this.rmn;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="nodeIDRef" use="required"&gt;
         *         &lt;simpleType&gt;
         *           &lt;union memberTypes=" {http://sourceforge.net/projects/openconf/configuration}tDefaultRedundantMNNodeID {http://sourceforge.net/projects/openconf/configuration}tRegularCNNodeID"&gt;
         *           &lt;/union&gt;
         *         &lt;/simpleType&gt;
         *       &lt;/attribute&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class RMN {

            @XmlAttribute(name = "nodeIDRef", required = true)
            protected String nodeIDRef;

            /**
             * Gets the value of the nodeIDRef property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNodeIDRef() {
                return nodeIDRef;
            }

            /**
             * Sets the value of the nodeIDRef property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNodeIDRef(String value) {
                this.nodeIDRef = value;
            }

        }

    }

}
