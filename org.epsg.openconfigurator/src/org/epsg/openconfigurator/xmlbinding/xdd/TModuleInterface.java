
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Defines the single module interface.
 * 			
 * 
 * <p>Java class for t_moduleInterface complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_moduleInterface"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *         &lt;element ref="{http://www.ethernet-powerlink.org}fileList"/&gt;
 *         &lt;element ref="{http://www.ethernet-powerlink.org}moduleTypeList"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="childID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" /&gt;
 *       &lt;attribute name="moduleAddressing" use="required" type="{http://www.ethernet-powerlink.org}t_moduleAddressingChild" /&gt;
 *       &lt;attribute name="minAddress" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="1" /&gt;
 *       &lt;attribute name="maxAddress" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="minPosition" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="1" /&gt;
 *       &lt;attribute name="maxPosition" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="maxCount" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="0" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_moduleInterface", propOrder = {
    "labelOrDescriptionOrLabelRef",
    "fileList",
    "moduleTypeList"
})
public class TModuleInterface {

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
    @XmlAttribute(name = "childID", required = true)
    protected String childID;
    @XmlAttribute(name = "type", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String type;
    @XmlAttribute(name = "moduleAddressing", required = true)
    protected TModuleAddressingChild moduleAddressing;
    @XmlAttribute(name = "minAddress")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger minAddress;
    @XmlAttribute(name = "maxAddress")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxAddress;
    @XmlAttribute(name = "minPosition")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger minPosition;
    @XmlAttribute(name = "maxPosition")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxPosition;
    @XmlAttribute(name = "maxCount")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger maxCount;

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
     * Gets the value of the childID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildID() {
        return childID;
    }

    /**
     * Sets the value of the childID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildID(String value) {
        this.childID = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the moduleAddressing property.
     * 
     * @return
     *     possible object is
     *     {@link TModuleAddressingChild }
     *     
     */
    public TModuleAddressingChild getModuleAddressing() {
        return moduleAddressing;
    }

    /**
     * Sets the value of the moduleAddressing property.
     * 
     * @param value
     *     allowed object is
     *     {@link TModuleAddressingChild }
     *     
     */
    public void setModuleAddressing(TModuleAddressingChild value) {
        this.moduleAddressing = value;
    }

    /**
     * Gets the value of the minAddress property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinAddress() {
        if (minAddress == null) {
            return new BigInteger("1");
        } else {
            return minAddress;
        }
    }

    /**
     * Sets the value of the minAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinAddress(BigInteger value) {
        this.minAddress = value;
    }

    /**
     * Gets the value of the maxAddress property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxAddress() {
        return maxAddress;
    }

    /**
     * Sets the value of the maxAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxAddress(BigInteger value) {
        this.maxAddress = value;
    }

    /**
     * Gets the value of the minPosition property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMinPosition() {
        if (minPosition == null) {
            return new BigInteger("1");
        } else {
            return minPosition;
        }
    }

    /**
     * Sets the value of the minPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMinPosition(BigInteger value) {
        this.minPosition = value;
    }

    /**
     * Gets the value of the maxPosition property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxPosition() {
        return maxPosition;
    }

    /**
     * Sets the value of the maxPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxPosition(BigInteger value) {
        this.maxPosition = value;
    }

    /**
     * Gets the value of the maxCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxCount() {
        if (maxCount == null) {
            return new BigInteger("0");
        } else {
            return maxCount;
        }
    }

    /**
     * Sets the value of the maxCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxCount(BigInteger value) {
        this.maxCount = value;
    }

}
