
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.math.BigInteger;
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
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for t_interfaceList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_interfaceList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="interface" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *                   &lt;element ref="{http://www.ethernet-powerlink.org}fileList"/&gt;
 *                   &lt;element ref="{http://www.ethernet-powerlink.org}moduleTypeList"/&gt;
 *                   &lt;element ref="{http://www.ethernet-powerlink.org}connectedModuleList" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                 &lt;attribute name="maxModules" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                 &lt;attribute name="unusedSlots" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                 &lt;attribute name="moduleAddressing" use="required" type="{http://www.ethernet-powerlink.org}t_moduleAddressingHead" /&gt;
 *                 &lt;attribute name="multipleModules" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *                 &lt;attribute name="identList" type="{http://www.ethernet-powerlink.org}dt_ParameterIndex" /&gt;
 *                 &lt;attribute name="firmwareList" type="{http://www.ethernet-powerlink.org}dt_ParameterIndex" /&gt;
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
@XmlType(name = "t_interfaceList", propOrder = {
    "_interface"
})
public class TInterfaceList {

    @XmlElement(name = "interface", required = true)
    protected List<TInterfaceList.Interface> _interface;

    /**
     * Gets the value of the interface property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interface property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInterface().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TInterfaceList.Interface }
     * 
     * 
     */
    public List<TInterfaceList.Interface> getInterface() {
        if (_interface == null) {
            _interface = new ArrayList<TInterfaceList.Interface>();
        }
        return this._interface;
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
     *         &lt;element ref="{http://www.ethernet-powerlink.org}fileList"/&gt;
     *         &lt;element ref="{http://www.ethernet-powerlink.org}moduleTypeList"/&gt;
     *         &lt;element ref="{http://www.ethernet-powerlink.org}connectedModuleList" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *       &lt;attribute name="maxModules" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
     *       &lt;attribute name="unusedSlots" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *       &lt;attribute name="moduleAddressing" use="required" type="{http://www.ethernet-powerlink.org}t_moduleAddressingHead" /&gt;
     *       &lt;attribute name="multipleModules" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
     *       &lt;attribute name="identList" type="{http://www.ethernet-powerlink.org}dt_ParameterIndex" /&gt;
     *       &lt;attribute name="firmwareList" type="{http://www.ethernet-powerlink.org}dt_ParameterIndex" /&gt;
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
        "fileList",
        "moduleTypeList",
        "connectedModuleList"
    })
    public static class Interface {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        @XmlElement(required = true)
        protected FileList fileList;
        @XmlElement(required = true)
        protected ModuleTypeList moduleTypeList;
        protected ConnectedModuleList connectedModuleList;
        @XmlAttribute(name = "uniqueID", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String uniqueID;
        @XmlAttribute(name = "maxModules", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger maxModules;
        @XmlAttribute(name = "unusedSlots", required = true)
        protected boolean unusedSlots;
        @XmlAttribute(name = "moduleAddressing", required = true)
        protected TModuleAddressingHead moduleAddressing;
        @XmlAttribute(name = "multipleModules")
        protected Boolean multipleModules;
        @XmlAttribute(name = "identList")
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        protected byte[] identList;
        @XmlAttribute(name = "firmwareList")
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        protected byte[] firmwareList;

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
         * Gets the value of the fileList property.
         * 
         * @return
         *     possible object is
         *     {@link FileList }
         *     
         */
        public FileList getFileList() {
            return fileList;
        }

        /**
         * Sets the value of the fileList property.
         * 
         * @param value
         *     allowed object is
         *     {@link FileList }
         *     
         */
        public void setFileList(FileList value) {
            this.fileList = value;
        }

        /**
         * Gets the value of the moduleTypeList property.
         * 
         * @return
         *     possible object is
         *     {@link ModuleTypeList }
         *     
         */
        public ModuleTypeList getModuleTypeList() {
            return moduleTypeList;
        }

        /**
         * Sets the value of the moduleTypeList property.
         * 
         * @param value
         *     allowed object is
         *     {@link ModuleTypeList }
         *     
         */
        public void setModuleTypeList(ModuleTypeList value) {
            this.moduleTypeList = value;
        }

        /**
         * Gets the value of the connectedModuleList property.
         * 
         * @return
         *     possible object is
         *     {@link ConnectedModuleList }
         *     
         */
        public ConnectedModuleList getConnectedModuleList() {
            return connectedModuleList;
        }

        /**
         * Sets the value of the connectedModuleList property.
         * 
         * @param value
         *     allowed object is
         *     {@link ConnectedModuleList }
         *     
         */
        public void setConnectedModuleList(ConnectedModuleList value) {
            this.connectedModuleList = value;
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
         * Gets the value of the maxModules property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMaxModules() {
            return maxModules;
        }

        /**
         * Sets the value of the maxModules property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMaxModules(BigInteger value) {
            this.maxModules = value;
        }

        /**
         * Gets the value of the unusedSlots property.
         * 
         */
        public boolean isUnusedSlots() {
            return unusedSlots;
        }

        /**
         * Sets the value of the unusedSlots property.
         * 
         */
        public void setUnusedSlots(boolean value) {
            this.unusedSlots = value;
        }

        /**
         * Gets the value of the moduleAddressing property.
         * 
         * @return
         *     possible object is
         *     {@link TModuleAddressingHead }
         *     
         */
        public TModuleAddressingHead getModuleAddressing() {
            return moduleAddressing;
        }

        /**
         * Sets the value of the moduleAddressing property.
         * 
         * @param value
         *     allowed object is
         *     {@link TModuleAddressingHead }
         *     
         */
        public void setModuleAddressing(TModuleAddressingHead value) {
            this.moduleAddressing = value;
        }

        /**
         * Gets the value of the multipleModules property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isMultipleModules() {
            if (multipleModules == null) {
                return true;
            } else {
                return multipleModules;
            }
        }

        /**
         * Sets the value of the multipleModules property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setMultipleModules(Boolean value) {
            this.multipleModules = value;
        }

        /**
         * Gets the value of the identList property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getIdentList() {
            return identList;
        }

        /**
         * Sets the value of the identList property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdentList(byte[] value) {
            this.identList = value;
        }

        /**
         * Gets the value of the firmwareList property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getFirmwareList() {
            return firmwareList;
        }

        /**
         * Sets the value of the firmwareList property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFirmwareList(byte[] value) {
            this.firmwareList = value;
        }

    }

}
