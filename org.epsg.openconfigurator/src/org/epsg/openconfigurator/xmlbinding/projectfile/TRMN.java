
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * A concrete type for a POWERLINK RMN.
 *
 * <p>Java class for tRMN complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="tRMN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://sourceforge.net/projects/openconf/configuration}tAbstractNode"&gt;
 *       &lt;attribute name="nodeID" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *       &lt;attribute name="pathToXDC"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI"&gt;
 *             &lt;minLength value="1"/&gt;
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
@XmlType(name = "tRMN")
public class TRMN
    extends TAbstractNode
{

    @XmlAttribute(name = "nodeID", required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected short nodeID;
    @XmlAttribute(name = "pathToXDC")
    protected String pathToXDC;

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

}
