
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für t_variableRef complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_variableRef"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="instanceIDRef" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="uniqueIDRef" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="variableIDRef"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="uniqueIDRef" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="memberRef" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="uniqueIDRef" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
 *                 &lt;attribute name="index" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="position" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="1" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_variableRef", propOrder = {
    "instanceIDRef",
    "variableIDRef",
    "memberRef"
})
public class TVariableRef {

    @XmlElement(required = true)
    protected List<TVariableRef.InstanceIDRef> instanceIDRef;
    @XmlElement(required = true)
    protected TVariableRef.VariableIDRef variableIDRef;
    protected List<TVariableRef.MemberRef> memberRef;
    @XmlAttribute(name = "position")
    @XmlSchemaType(name = "unsignedByte")
    protected Short position;

    /**
     * Gets the value of the instanceIDRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instanceIDRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstanceIDRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TVariableRef.InstanceIDRef }
     * 
     * 
     */
    public List<TVariableRef.InstanceIDRef> getInstanceIDRef() {
        if (instanceIDRef == null) {
            instanceIDRef = new ArrayList<TVariableRef.InstanceIDRef>();
        }
        return this.instanceIDRef;
    }

    /**
     * Ruft den Wert der variableIDRef-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TVariableRef.VariableIDRef }
     *     
     */
    public TVariableRef.VariableIDRef getVariableIDRef() {
        return variableIDRef;
    }

    /**
     * Legt den Wert der variableIDRef-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TVariableRef.VariableIDRef }
     *     
     */
    public void setVariableIDRef(TVariableRef.VariableIDRef value) {
        this.variableIDRef = value;
    }

    /**
     * Gets the value of the memberRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the memberRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMemberRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TVariableRef.MemberRef }
     * 
     * 
     */
    public List<TVariableRef.MemberRef> getMemberRef() {
        if (memberRef == null) {
            memberRef = new ArrayList<TVariableRef.MemberRef>();
        }
        return this.memberRef;
    }

    /**
     * Ruft den Wert der position-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getPosition() {
        if (position == null) {
            return ((short) 1);
        } else {
            return position;
        }
    }

    /**
     * Legt den Wert der position-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setPosition(Short value) {
        this.position = value;
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
     *       &lt;attribute name="uniqueIDRef" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class InstanceIDRef {

        @XmlAttribute(name = "uniqueIDRef", required = true)
        @XmlIDREF
        @XmlSchemaType(name = "IDREF")
        protected Object uniqueIDRef;

        /**
         * Ruft den Wert der uniqueIDRef-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getUniqueIDRef() {
            return uniqueIDRef;
        }

        /**
         * Legt den Wert der uniqueIDRef-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setUniqueIDRef(Object value) {
            this.uniqueIDRef = value;
        }

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
     *       &lt;attribute name="uniqueIDRef" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
     *       &lt;attribute name="index" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class MemberRef {

        @XmlAttribute(name = "uniqueIDRef")
        @XmlIDREF
        @XmlSchemaType(name = "IDREF")
        protected Object uniqueIDRef;
        @XmlAttribute(name = "index")
        protected Long index;

        /**
         * Ruft den Wert der uniqueIDRef-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getUniqueIDRef() {
            return uniqueIDRef;
        }

        /**
         * Legt den Wert der uniqueIDRef-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setUniqueIDRef(Object value) {
            this.uniqueIDRef = value;
        }

        /**
         * Ruft den Wert der index-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getIndex() {
            return index;
        }

        /**
         * Legt den Wert der index-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setIndex(Long value) {
            this.index = value;
        }

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
     *       &lt;attribute name="uniqueIDRef" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class VariableIDRef {

        @XmlAttribute(name = "uniqueIDRef", required = true)
        @XmlIDREF
        @XmlSchemaType(name = "IDREF")
        protected Object uniqueIDRef;

        /**
         * Ruft den Wert der uniqueIDRef-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getUniqueIDRef() {
            return uniqueIDRef;
        }

        /**
         * Legt den Wert der uniqueIDRef-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setUniqueIDRef(Object value) {
            this.uniqueIDRef = value;
        }

    }

}
