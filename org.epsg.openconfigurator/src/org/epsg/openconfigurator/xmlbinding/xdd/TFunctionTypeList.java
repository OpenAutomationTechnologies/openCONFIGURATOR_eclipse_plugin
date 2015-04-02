
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für t_functionTypeList complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_functionTypeList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="functionType" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
 *                   &lt;element name="versionInfo" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="organization" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="author" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="date" use="required" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="interfaceList"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="inputVars" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="outputVars" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="configVars" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="functionInstanceList" type="{http://www.ethernet-powerlink.org}t_functionInstanceList" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                 &lt;attribute name="package" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
@XmlType(name = "t_functionTypeList", propOrder = {
    "functionType"
})
public class TFunctionTypeList {

    @XmlElement(required = true)
    protected List<TFunctionTypeList.FunctionType> functionType;

    /**
     * Gets the value of the functionType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the functionType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFunctionType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TFunctionTypeList.FunctionType }
     * 
     * 
     */
    public List<TFunctionTypeList.FunctionType> getFunctionType() {
        if (functionType == null) {
            functionType = new ArrayList<TFunctionTypeList.FunctionType>();
        }
        return this.functionType;
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
     *       &lt;sequence&gt;
     *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
     *         &lt;element name="versionInfo" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="organization" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="author" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="date" use="required" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="interfaceList"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="inputVars" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="outputVars" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="configVars" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="functionInstanceList" type="{http://www.ethernet-powerlink.org}t_functionInstanceList" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *       &lt;attribute name="package" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
        "versionInfo",
        "interfaceList",
        "functionInstanceList"
    })
    public static class FunctionType {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        @XmlElement(required = true)
        protected List<TFunctionTypeList.FunctionType.VersionInfo> versionInfo;
        @XmlElement(required = true)
        protected TFunctionTypeList.FunctionType.InterfaceList interfaceList;
        protected TFunctionInstanceList functionInstanceList;
        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "uniqueID", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String uniqueID;
        @XmlAttribute(name = "package")
        protected String _package;

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
         * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label }
         * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description }
         * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef }
         * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef }
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
         * Gets the value of the versionInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the versionInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVersionInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TFunctionTypeList.FunctionType.VersionInfo }
         * 
         * 
         */
        public List<TFunctionTypeList.FunctionType.VersionInfo> getVersionInfo() {
            if (versionInfo == null) {
                versionInfo = new ArrayList<TFunctionTypeList.FunctionType.VersionInfo>();
            }
            return this.versionInfo;
        }

        /**
         * Ruft den Wert der interfaceList-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link TFunctionTypeList.FunctionType.InterfaceList }
         *     
         */
        public TFunctionTypeList.FunctionType.InterfaceList getInterfaceList() {
            return interfaceList;
        }

        /**
         * Legt den Wert der interfaceList-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link TFunctionTypeList.FunctionType.InterfaceList }
         *     
         */
        public void setInterfaceList(TFunctionTypeList.FunctionType.InterfaceList value) {
            this.interfaceList = value;
        }

        /**
         * Ruft den Wert der functionInstanceList-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link TFunctionInstanceList }
         *     
         */
        public TFunctionInstanceList getFunctionInstanceList() {
            return functionInstanceList;
        }

        /**
         * Legt den Wert der functionInstanceList-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link TFunctionInstanceList }
         *     
         */
        public void setFunctionInstanceList(TFunctionInstanceList value) {
            this.functionInstanceList = value;
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
         * Ruft den Wert der uniqueID-Eigenschaft ab.
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
         * Legt den Wert der uniqueID-Eigenschaft fest.
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
         * Ruft den Wert der package-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPackage() {
            return _package;
        }

        /**
         * Legt den Wert der package-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPackage(String value) {
            this._package = value;
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
         *       &lt;sequence&gt;
         *         &lt;element name="inputVars" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="outputVars" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="configVars" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
         *                 &lt;/sequence&gt;
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
            "inputVars",
            "outputVars",
            "configVars"
        })
        public static class InterfaceList {

            protected TFunctionTypeList.FunctionType.InterfaceList.InputVars inputVars;
            protected TFunctionTypeList.FunctionType.InterfaceList.OutputVars outputVars;
            protected TFunctionTypeList.FunctionType.InterfaceList.ConfigVars configVars;

            /**
             * Ruft den Wert der inputVars-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link TFunctionTypeList.FunctionType.InterfaceList.InputVars }
             *     
             */
            public TFunctionTypeList.FunctionType.InterfaceList.InputVars getInputVars() {
                return inputVars;
            }

            /**
             * Legt den Wert der inputVars-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link TFunctionTypeList.FunctionType.InterfaceList.InputVars }
             *     
             */
            public void setInputVars(TFunctionTypeList.FunctionType.InterfaceList.InputVars value) {
                this.inputVars = value;
            }

            /**
             * Ruft den Wert der outputVars-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link TFunctionTypeList.FunctionType.InterfaceList.OutputVars }
             *     
             */
            public TFunctionTypeList.FunctionType.InterfaceList.OutputVars getOutputVars() {
                return outputVars;
            }

            /**
             * Legt den Wert der outputVars-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link TFunctionTypeList.FunctionType.InterfaceList.OutputVars }
             *     
             */
            public void setOutputVars(TFunctionTypeList.FunctionType.InterfaceList.OutputVars value) {
                this.outputVars = value;
            }

            /**
             * Ruft den Wert der configVars-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link TFunctionTypeList.FunctionType.InterfaceList.ConfigVars }
             *     
             */
            public TFunctionTypeList.FunctionType.InterfaceList.ConfigVars getConfigVars() {
                return configVars;
            }

            /**
             * Legt den Wert der configVars-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link TFunctionTypeList.FunctionType.InterfaceList.ConfigVars }
             *     
             */
            public void setConfigVars(TFunctionTypeList.FunctionType.InterfaceList.ConfigVars value) {
                this.configVars = value;
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
             *       &lt;sequence&gt;
             *         &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
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
                "varDeclaration"
            })
            public static class ConfigVars {

                @XmlElement(required = true)
                protected List<TVarDeclaration> varDeclaration;

                /**
                 * Gets the value of the varDeclaration property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the varDeclaration property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getVarDeclaration().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link TVarDeclaration }
                 * 
                 * 
                 */
                public List<TVarDeclaration> getVarDeclaration() {
                    if (varDeclaration == null) {
                        varDeclaration = new ArrayList<TVarDeclaration>();
                    }
                    return this.varDeclaration;
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
             *       &lt;sequence&gt;
             *         &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
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
                "varDeclaration"
            })
            public static class InputVars {

                @XmlElement(required = true)
                protected List<TVarDeclaration> varDeclaration;

                /**
                 * Gets the value of the varDeclaration property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the varDeclaration property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getVarDeclaration().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link TVarDeclaration }
                 * 
                 * 
                 */
                public List<TVarDeclaration> getVarDeclaration() {
                    if (varDeclaration == null) {
                        varDeclaration = new ArrayList<TVarDeclaration>();
                    }
                    return this.varDeclaration;
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
             *       &lt;sequence&gt;
             *         &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
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
                "varDeclaration"
            })
            public static class OutputVars {

                @XmlElement(required = true)
                protected List<TVarDeclaration> varDeclaration;

                /**
                 * Gets the value of the varDeclaration property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the varDeclaration property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getVarDeclaration().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link TVarDeclaration }
                 * 
                 * 
                 */
                public List<TVarDeclaration> getVarDeclaration() {
                    if (varDeclaration == null) {
                        varDeclaration = new ArrayList<TVarDeclaration>();
                    }
                    return this.varDeclaration;
                }

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
         *       &lt;sequence&gt;
         *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="organization" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="author" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="date" use="required" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "labelOrDescriptionOrLabelRef"
        })
        public static class VersionInfo {

            @XmlElements({
                @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
                @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
                @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
                @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
            })
            protected List<Object> labelOrDescriptionOrLabelRef;
            @XmlAttribute(name = "organization", required = true)
            protected String organization;
            @XmlAttribute(name = "version", required = true)
            protected String version;
            @XmlAttribute(name = "author", required = true)
            protected String author;
            @XmlAttribute(name = "date", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar date;

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
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label }
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description }
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef }
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef }
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
             * Ruft den Wert der organization-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOrganization() {
                return organization;
            }

            /**
             * Legt den Wert der organization-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOrganization(String value) {
                this.organization = value;
            }

            /**
             * Ruft den Wert der version-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVersion() {
                return version;
            }

            /**
             * Legt den Wert der version-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVersion(String value) {
                this.version = value;
            }

            /**
             * Ruft den Wert der author-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAuthor() {
                return author;
            }

            /**
             * Legt den Wert der author-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAuthor(String value) {
                this.author = value;
            }

            /**
             * Ruft den Wert der date-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getDate() {
                return date;
            }

            /**
             * Legt den Wert der date-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setDate(XMLGregorianCalendar value) {
                this.date = value;
            }

        }

    }

}
