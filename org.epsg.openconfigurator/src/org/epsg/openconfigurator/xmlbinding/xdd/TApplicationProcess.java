
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für t_ApplicationProcess complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
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
     * Ruft den Wert der dataTypeList-Eigenschaft ab.
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
     * Legt den Wert der dataTypeList-Eigenschaft fest.
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
     * Ruft den Wert der functionTypeList-Eigenschaft ab.
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
     * Legt den Wert der functionTypeList-Eigenschaft fest.
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
     * Ruft den Wert der templateList-Eigenschaft ab.
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
     * Legt den Wert der templateList-Eigenschaft fest.
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
     * Ruft den Wert der parameterList-Eigenschaft ab.
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
     * Legt den Wert der parameterList-Eigenschaft fest.
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
     * Ruft den Wert der parameterGroupList-Eigenschaft ab.
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
     * Legt den Wert der parameterGroupList-Eigenschaft fest.
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
