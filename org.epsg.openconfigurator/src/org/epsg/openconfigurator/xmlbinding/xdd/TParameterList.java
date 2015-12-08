
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for t_parameterList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_parameterList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="parameter" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *                   &lt;choice&gt;
 *                     &lt;group ref="{http://www.ethernet-powerlink.org}g_simple"/&gt;
 *                     &lt;element name="dataTypeIDRef" type="{http://www.ethernet-powerlink.org}t_dataTypeIDRef"/&gt;
 *                     &lt;element name="variableRef" type="{http://www.ethernet-powerlink.org}t_variableRef" maxOccurs="unbounded"/&gt;
 *                   &lt;/choice&gt;
 *                   &lt;element name="conditionalSupport" type="{http://www.ethernet-powerlink.org}t_conditionalSupport" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="denotation" type="{http://www.ethernet-powerlink.org}t_denotation" minOccurs="0"/&gt;
 *                   &lt;element name="actualValue" type="{http://www.ethernet-powerlink.org}t_value" minOccurs="0"/&gt;
 *                   &lt;element name="defaultValue" type="{http://www.ethernet-powerlink.org}t_value" minOccurs="0"/&gt;
 *                   &lt;element name="substituteValue" type="{http://www.ethernet-powerlink.org}t_value" minOccurs="0"/&gt;
 *                   &lt;element name="allowedValues" type="{http://www.ethernet-powerlink.org}t_allowedValues" minOccurs="0"/&gt;
 *                   &lt;element name="unit" type="{http://www.ethernet-powerlink.org}t_unit" minOccurs="0"/&gt;
 *                   &lt;element name="property" type="{http://www.ethernet-powerlink.org}t_property" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attGroup ref="{http://www.ethernet-powerlink.org}ag_parameter"/&gt;
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
@XmlType(name = "t_parameterList", propOrder = {
    "parameter"
})
public class TParameterList {

    @XmlElement(required = true)
    protected List<TParameterList.Parameter> parameter;

    /**
     * Gets the value of the parameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TParameterList.Parameter }
     * 
     * 
     */
    public List<TParameterList.Parameter> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<TParameterList.Parameter>();
        }
        return this.parameter;
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
     *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
     *         &lt;choice&gt;
     *           &lt;group ref="{http://www.ethernet-powerlink.org}g_simple"/&gt;
     *           &lt;element name="dataTypeIDRef" type="{http://www.ethernet-powerlink.org}t_dataTypeIDRef"/&gt;
     *           &lt;element name="variableRef" type="{http://www.ethernet-powerlink.org}t_variableRef" maxOccurs="unbounded"/&gt;
     *         &lt;/choice&gt;
     *         &lt;element name="conditionalSupport" type="{http://www.ethernet-powerlink.org}t_conditionalSupport" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="denotation" type="{http://www.ethernet-powerlink.org}t_denotation" minOccurs="0"/&gt;
     *         &lt;element name="actualValue" type="{http://www.ethernet-powerlink.org}t_value" minOccurs="0"/&gt;
     *         &lt;element name="defaultValue" type="{http://www.ethernet-powerlink.org}t_value" minOccurs="0"/&gt;
     *         &lt;element name="substituteValue" type="{http://www.ethernet-powerlink.org}t_value" minOccurs="0"/&gt;
     *         &lt;element name="allowedValues" type="{http://www.ethernet-powerlink.org}t_allowedValues" minOccurs="0"/&gt;
     *         &lt;element name="unit" type="{http://www.ethernet-powerlink.org}t_unit" minOccurs="0"/&gt;
     *         &lt;element name="property" type="{http://www.ethernet-powerlink.org}t_property" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attGroup ref="{http://www.ethernet-powerlink.org}ag_parameter"/&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "labelOrDescriptionOrLabelRef",
        "bool",
        "bitstring",
        "_byte",
        "_char",
        "word",
        "dword",
        "lword",
        "sint",
        "_int",
        "dint",
        "lint",
        "usint",
        "uint",
        "udint",
        "ulint",
        "real",
        "lreal",
        "string",
        "wstring",
        "dataTypeIDRef",
        "variableRef",
        "conditionalSupport",
        "denotation",
        "actualValue",
        "defaultValue",
        "substituteValue",
        "allowedValues",
        "unit",
        "property"
    })
    public static class Parameter {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        @XmlElement(name = "BOOL")
        protected Object bool;
        @XmlElement(name = "BITSTRING")
        protected Object bitstring;
        @XmlElement(name = "BYTE")
        protected Object _byte;
        @XmlElement(name = "CHAR")
        protected Object _char;
        @XmlElement(name = "WORD")
        protected Object word;
        @XmlElement(name = "DWORD")
        protected Object dword;
        @XmlElement(name = "LWORD")
        protected Object lword;
        @XmlElement(name = "SINT")
        protected Object sint;
        @XmlElement(name = "INT")
        protected Object _int;
        @XmlElement(name = "DINT")
        protected Object dint;
        @XmlElement(name = "LINT")
        protected Object lint;
        @XmlElement(name = "USINT")
        protected Object usint;
        @XmlElement(name = "UINT")
        protected Object uint;
        @XmlElement(name = "UDINT")
        protected Object udint;
        @XmlElement(name = "ULINT")
        protected Object ulint;
        @XmlElement(name = "REAL")
        protected Object real;
        @XmlElement(name = "LREAL")
        protected Object lreal;
        @XmlElement(name = "STRING")
        protected Object string;
        @XmlElement(name = "WSTRING")
        protected Object wstring;
        protected TDataTypeIDRef dataTypeIDRef;
        protected List<TVariableRef> variableRef;
        protected List<TConditionalSupport> conditionalSupport;
        protected TDenotation denotation;
        protected TValue actualValue;
        protected TValue defaultValue;
        protected TValue substituteValue;
        protected TAllowedValues allowedValues;
        protected TUnit unit;
        protected List<TProperty> property;
        @XmlAttribute(name = "uniqueID", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String uniqueID;
        @XmlAttribute(name = "access")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String access;
        @XmlAttribute(name = "accessList")
        protected List<String> accessList;
        @XmlAttribute(name = "support")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String support;
        @XmlAttribute(name = "persistent")
        protected Boolean persistent;
        @XmlAttribute(name = "offset")
        protected String offset;
        @XmlAttribute(name = "multiplier")
        protected String multiplier;
        @XmlAttribute(name = "templateIDRef")
        @XmlIDREF
        @XmlSchemaType(name = "IDREF")
        protected Object templateIDRef;

        /**
         * Gets the value of the labelOrDescriptionOrLabelRef property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the labelOrDescriptionOrLabelRef property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLabelOrDescriptionOrLabelRef().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label }
         * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description }
         * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef }
         * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef }
         * 
         * 
         */
        public List<Object> getLabelOrDescriptionOrLabelRef() {
            if (labelOrDescriptionOrLabelRef == null) {
                labelOrDescriptionOrLabelRef = new ArrayList<Object>();
            }
            return this.labelOrDescriptionOrLabelRef;
        }

        /**
         * Gets the value of the bool property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getBOOL() {
            return bool;
        }

        /**
         * Sets the value of the bool property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setBOOL(Object value) {
            this.bool = value;
        }

        /**
         * Gets the value of the bitstring property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getBITSTRING() {
            return bitstring;
        }

        /**
         * Sets the value of the bitstring property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setBITSTRING(Object value) {
            this.bitstring = value;
        }

        /**
         * Gets the value of the byte property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getBYTE() {
            return _byte;
        }

        /**
         * Sets the value of the byte property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setBYTE(Object value) {
            this._byte = value;
        }

        /**
         * Gets the value of the char property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getCHAR() {
            return _char;
        }

        /**
         * Sets the value of the char property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setCHAR(Object value) {
            this._char = value;
        }

        /**
         * Gets the value of the word property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getWORD() {
            return word;
        }

        /**
         * Sets the value of the word property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setWORD(Object value) {
            this.word = value;
        }

        /**
         * Gets the value of the dword property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getDWORD() {
            return dword;
        }

        /**
         * Sets the value of the dword property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setDWORD(Object value) {
            this.dword = value;
        }

        /**
         * Gets the value of the lword property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getLWORD() {
            return lword;
        }

        /**
         * Sets the value of the lword property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setLWORD(Object value) {
            this.lword = value;
        }

        /**
         * Gets the value of the sint property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getSINT() {
            return sint;
        }

        /**
         * Sets the value of the sint property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setSINT(Object value) {
            this.sint = value;
        }

        /**
         * Gets the value of the int property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getINT() {
            return _int;
        }

        /**
         * Sets the value of the int property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setINT(Object value) {
            this._int = value;
        }

        /**
         * Gets the value of the dint property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getDINT() {
            return dint;
        }

        /**
         * Sets the value of the dint property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setDINT(Object value) {
            this.dint = value;
        }

        /**
         * Gets the value of the lint property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getLINT() {
            return lint;
        }

        /**
         * Sets the value of the lint property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setLINT(Object value) {
            this.lint = value;
        }

        /**
         * Gets the value of the usint property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getUSINT() {
            return usint;
        }

        /**
         * Sets the value of the usint property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setUSINT(Object value) {
            this.usint = value;
        }

        /**
         * Gets the value of the uint property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getUINT() {
            return uint;
        }

        /**
         * Sets the value of the uint property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setUINT(Object value) {
            this.uint = value;
        }

        /**
         * Gets the value of the udint property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getUDINT() {
            return udint;
        }

        /**
         * Sets the value of the udint property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setUDINT(Object value) {
            this.udint = value;
        }

        /**
         * Gets the value of the ulint property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getULINT() {
            return ulint;
        }

        /**
         * Sets the value of the ulint property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setULINT(Object value) {
            this.ulint = value;
        }

        /**
         * Gets the value of the real property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getREAL() {
            return real;
        }

        /**
         * Sets the value of the real property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setREAL(Object value) {
            this.real = value;
        }

        /**
         * Gets the value of the lreal property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getLREAL() {
            return lreal;
        }

        /**
         * Sets the value of the lreal property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setLREAL(Object value) {
            this.lreal = value;
        }

        /**
         * Gets the value of the string property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getSTRING() {
            return string;
        }

        /**
         * Sets the value of the string property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setSTRING(Object value) {
            this.string = value;
        }

        /**
         * Gets the value of the wstring property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getWSTRING() {
            return wstring;
        }

        /**
         * Sets the value of the wstring property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setWSTRING(Object value) {
            this.wstring = value;
        }

        /**
         * Gets the value of the dataTypeIDRef property.
         * 
         * @return
         *     possible object is
         *     {@link TDataTypeIDRef }
         *     
         */
        public TDataTypeIDRef getDataTypeIDRef() {
            return dataTypeIDRef;
        }

        /**
         * Sets the value of the dataTypeIDRef property.
         * 
         * @param value
         *     allowed object is
         *     {@link TDataTypeIDRef }
         *     
         */
        public void setDataTypeIDRef(TDataTypeIDRef value) {
            this.dataTypeIDRef = value;
        }

        /**
         * Gets the value of the variableRef property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the variableRef property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVariableRef().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TVariableRef }
         * 
         * 
         */
        public List<TVariableRef> getVariableRef() {
            if (variableRef == null) {
                variableRef = new ArrayList<TVariableRef>();
            }
            return this.variableRef;
        }

        /**
         * Gets the value of the conditionalSupport property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the conditionalSupport property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getConditionalSupport().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TConditionalSupport }
         * 
         * 
         */
        public List<TConditionalSupport> getConditionalSupport() {
            if (conditionalSupport == null) {
                conditionalSupport = new ArrayList<TConditionalSupport>();
            }
            return this.conditionalSupport;
        }

        /**
         * Gets the value of the denotation property.
         * 
         * @return
         *     possible object is
         *     {@link TDenotation }
         *     
         */
        public TDenotation getDenotation() {
            return denotation;
        }

        /**
         * Sets the value of the denotation property.
         * 
         * @param value
         *     allowed object is
         *     {@link TDenotation }
         *     
         */
        public void setDenotation(TDenotation value) {
            this.denotation = value;
        }

        /**
         * Gets the value of the actualValue property.
         * 
         * @return
         *     possible object is
         *     {@link TValue }
         *     
         */
        public TValue getActualValue() {
            return actualValue;
        }

        /**
         * Sets the value of the actualValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link TValue }
         *     
         */
        public void setActualValue(TValue value) {
            this.actualValue = value;
        }

        /**
         * Gets the value of the defaultValue property.
         * 
         * @return
         *     possible object is
         *     {@link TValue }
         *     
         */
        public TValue getDefaultValue() {
            return defaultValue;
        }

        /**
         * Sets the value of the defaultValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link TValue }
         *     
         */
        public void setDefaultValue(TValue value) {
            this.defaultValue = value;
        }

        /**
         * Gets the value of the substituteValue property.
         * 
         * @return
         *     possible object is
         *     {@link TValue }
         *     
         */
        public TValue getSubstituteValue() {
            return substituteValue;
        }

        /**
         * Sets the value of the substituteValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link TValue }
         *     
         */
        public void setSubstituteValue(TValue value) {
            this.substituteValue = value;
        }

        /**
         * Gets the value of the allowedValues property.
         * 
         * @return
         *     possible object is
         *     {@link TAllowedValues }
         *     
         */
        public TAllowedValues getAllowedValues() {
            return allowedValues;
        }

        /**
         * Sets the value of the allowedValues property.
         * 
         * @param value
         *     allowed object is
         *     {@link TAllowedValues }
         *     
         */
        public void setAllowedValues(TAllowedValues value) {
            this.allowedValues = value;
        }

        /**
         * Gets the value of the unit property.
         * 
         * @return
         *     possible object is
         *     {@link TUnit }
         *     
         */
        public TUnit getUnit() {
            return unit;
        }

        /**
         * Sets the value of the unit property.
         * 
         * @param value
         *     allowed object is
         *     {@link TUnit }
         *     
         */
        public void setUnit(TUnit value) {
            this.unit = value;
        }

        /**
         * Gets the value of the property property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the property property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProperty().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TProperty }
         * 
         * 
         */
        public List<TProperty> getProperty() {
            if (property == null) {
                property = new ArrayList<TProperty>();
            }
            return this.property;
        }

        /**
         * Gets the value of the uniqueID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUniqueID() {
            return uniqueID;
        }

        /**
         * Sets the value of the uniqueID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUniqueID(String value) {
            this.uniqueID = value;
        }

        /**
         * Gets the value of the access property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAccess() {
            if (access == null) {
                return "read";
            } else {
                return access;
            }
        }

        /**
         * Sets the value of the access property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAccess(String value) {
            this.access = value;
        }

        /**
         * Gets the value of the accessList property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the accessList property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAccessList().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getAccessList() {
            if (accessList == null) {
                accessList = new ArrayList<String>();
            }
            return this.accessList;
        }

        /**
         * Gets the value of the support property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSupport() {
            return support;
        }

        /**
         * Sets the value of the support property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSupport(String value) {
            this.support = value;
        }

        /**
         * Gets the value of the persistent property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isPersistent() {
            if (persistent == null) {
                return false;
            } else {
                return persistent;
            }
        }

        /**
         * Sets the value of the persistent property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setPersistent(Boolean value) {
            this.persistent = value;
        }

        /**
         * Gets the value of the offset property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOffset() {
            return offset;
        }

        /**
         * Sets the value of the offset property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOffset(String value) {
            this.offset = value;
        }

        /**
         * Gets the value of the multiplier property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMultiplier() {
            return multiplier;
        }

        /**
         * Sets the value of the multiplier property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMultiplier(String value) {
            this.multiplier = value;
        }

        /**
         * Gets the value of the templateIDRef property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getTemplateIDRef() {
            return templateIDRef;
        }

        /**
         * Sets the value of the templateIDRef property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setTemplateIDRef(Object value) {
            this.templateIDRef = value;
        }

    }

}
