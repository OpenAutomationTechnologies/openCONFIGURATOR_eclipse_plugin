
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_ApplicationProcess complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_ApplicationProcess"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataTypeList" type="{http://www.ethernet-powerlink.org}t_dataTypeList" minOccurs="0"/&gt;
 *         &lt;element name="functionTypeList" type="{http://www.ethernet-powerlink.org}t_functionTypeList" minOccurs="0"/&gt;
 *         &lt;element name="functionInstanceList" type="{http://www.ethernet-powerlink.org}t_functionInstanceList" minOccurs="0"/&gt;
 *         &lt;element name="templateList" type="{http://www.ethernet-powerlink.org}t_templateList" minOccurs="0"/&gt;
 *         &lt;element name="parameterList" type="{http://www.ethernet-powerlink.org}t_parameterList"/&gt;
 *         &lt;element name="parameterGroupList" type="{http://www.ethernet-powerlink.org}t_parameterGroupList" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_ApplicationProcess", propOrder = {
    "dataTypeList",
    "functionTypeList",
    "functionInstanceList",
    "templateList",
    "parameterList",
    "parameterGroupList"
})
public class TApplicationProcess {

    protected TDataTypeList dataTypeList;
    protected TFunctionTypeList functionTypeList;
    protected TFunctionInstanceList functionInstanceList;
    protected TTemplateList templateList;
    @XmlElement(required = true)
    protected TParameterList parameterList;
    protected TParameterGroupList parameterGroupList;

    /**
     * Gets the value of the dataTypeList property.
     * 
     * @return
     *     possible object is
     *     {@link TDataTypeList }
     *     
     */
    public TDataTypeList getDataTypeList() {
        return dataTypeList;
    }

    /**
     * Sets the value of the dataTypeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDataTypeList }
     *     
     */
    public void setDataTypeList(TDataTypeList value) {
        this.dataTypeList = value;
    }

    /**
     * Gets the value of the functionTypeList property.
     * 
     * @return
     *     possible object is
     *     {@link TFunctionTypeList }
     *     
     */
    public TFunctionTypeList getFunctionTypeList() {
        return functionTypeList;
    }

    /**
     * Sets the value of the functionTypeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TFunctionTypeList }
     *     
     */
    public void setFunctionTypeList(TFunctionTypeList value) {
        this.functionTypeList = value;
    }

    /**
     * Gets the value of the functionInstanceList property.
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
     * Sets the value of the functionInstanceList property.
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
     * Gets the value of the templateList property.
     * 
     * @return
     *     possible object is
     *     {@link TTemplateList }
     *     
     */
    public TTemplateList getTemplateList() {
        return templateList;
    }

    /**
     * Sets the value of the templateList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TTemplateList }
     *     
     */
    public void setTemplateList(TTemplateList value) {
        this.templateList = value;
    }

    /**
     * Gets the value of the parameterList property.
     * 
     * @return
     *     possible object is
     *     {@link TParameterList }
     *     
     */
    public TParameterList getParameterList() {
        return parameterList;
    }

    /**
     * Sets the value of the parameterList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TParameterList }
     *     
     */
    public void setParameterList(TParameterList value) {
        this.parameterList = value;
    }

    /**
     * Gets the value of the parameterGroupList property.
     * 
     * @return
     *     possible object is
     *     {@link TParameterGroupList }
     *     
     */
    public TParameterGroupList getParameterGroupList() {
        return parameterGroupList;
    }

    /**
     * Sets the value of the parameterGroupList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TParameterGroupList }
     *     
     */
    public void setParameterGroupList(TParameterGroupList value) {
        this.parameterGroupList = value;
    }

}
