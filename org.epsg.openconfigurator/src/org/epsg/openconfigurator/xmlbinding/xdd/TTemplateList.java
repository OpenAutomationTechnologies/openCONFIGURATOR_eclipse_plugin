
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_templateList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_templateList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="parameterTemplate" type="{http://www.ethernet-powerlink.org}t_parameterTemplate" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="allowedValuesTemplate" type="{http://www.ethernet-powerlink.org}t_allowedValuesTemplate" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_templateList", propOrder = {
    "parameterTemplate",
    "allowedValuesTemplate"
})
public class TTemplateList {

    protected List<TParameterTemplate> parameterTemplate;
    protected List<TAllowedValuesTemplate> allowedValuesTemplate;

    /**
     * Gets the value of the parameterTemplate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameterTemplate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameterTemplate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TParameterTemplate }
     * 
     * 
     */
    public List<TParameterTemplate> getParameterTemplate() {
        if (parameterTemplate == null) {
            parameterTemplate = new ArrayList<TParameterTemplate>();
        }
        return this.parameterTemplate;
    }

    /**
     * Gets the value of the allowedValuesTemplate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allowedValuesTemplate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllowedValuesTemplate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TAllowedValuesTemplate }
     * 
     * 
     */
    public List<TAllowedValuesTemplate> getAllowedValuesTemplate() {
        if (allowedValuesTemplate == null) {
            allowedValuesTemplate = new ArrayList<TAllowedValuesTemplate>();
        }
        return this.allowedValuesTemplate;
    }

}
