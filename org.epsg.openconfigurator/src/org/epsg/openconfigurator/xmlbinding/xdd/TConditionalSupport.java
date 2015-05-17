
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_conditionalSupport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_conditionalSupport"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="paramIDRef" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_conditionalSupport")
public class TConditionalSupport {

    @XmlAttribute(name = "paramIDRef", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object paramIDRef;

    /**
     * Gets the value of the paramIDRef property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getParamIDRef() {
        return paramIDRef;
    }

    /**
     * Sets the value of the paramIDRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setParamIDRef(Object value) {
        this.paramIDRef = value;
    }

}
