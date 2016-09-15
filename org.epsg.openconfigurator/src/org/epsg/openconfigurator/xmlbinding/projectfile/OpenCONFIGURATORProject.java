
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="Generator" type="{http://sourceforge.net/projects/openconf/configuration}tGenerator" minOccurs="0"/&gt;
 *         &lt;element name="IDEConfiguration" type="{http://sourceforge.net/projects/openconf/configuration}tIDEConfiguration" minOccurs="0"/&gt;
 *         &lt;element name="ProjectConfiguration" type="{http://sourceforge.net/projects/openconf/configuration}tProjectConfiguration"/&gt;
 *         &lt;element name="NetworkConfiguration" type="{http://sourceforge.net/projects/openconf/configuration}tNetworkConfiguration"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="version" type="{http://sourceforge.net/projects/openconf/configuration}tVersion" fixed="1.0.0" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "generator",
    "ideConfiguration",
    "projectConfiguration",
    "networkConfiguration"
})
@XmlRootElement(name = "openCONFIGURATORProject")
public class OpenCONFIGURATORProject {

    @XmlElement(name = "Generator")
    protected TGenerator generator;
    @XmlElement(name = "IDEConfiguration")
    protected TIDEConfiguration ideConfiguration;
    @XmlElement(name = "ProjectConfiguration", required = true)
    protected TProjectConfiguration projectConfiguration;
    @XmlElement(name = "NetworkConfiguration", required = true)
    protected TNetworkConfiguration networkConfiguration;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Gets the value of the generator property.
     * 
     * @return
     *     possible object is
     *     {@link TGenerator }
     *     
     */
    public TGenerator getGenerator() {
        return generator;
    }

    /**
     * Sets the value of the generator property.
     * 
     * @param value
     *     allowed object is
     *     {@link TGenerator }
     *     
     */
    public void setGenerator(TGenerator value) {
        this.generator = value;
    }

    /**
     * Gets the value of the ideConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link TIDEConfiguration }
     *     
     */
    public TIDEConfiguration getIDEConfiguration() {
        return ideConfiguration;
    }

    /**
     * Sets the value of the ideConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIDEConfiguration }
     *     
     */
    public void setIDEConfiguration(TIDEConfiguration value) {
        this.ideConfiguration = value;
    }

    /**
     * Gets the value of the projectConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link TProjectConfiguration }
     *     
     */
    public TProjectConfiguration getProjectConfiguration() {
        return projectConfiguration;
    }

    /**
     * Sets the value of the projectConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link TProjectConfiguration }
     *     
     */
    public void setProjectConfiguration(TProjectConfiguration value) {
        this.projectConfiguration = value;
    }

    /**
     * Gets the value of the networkConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link TNetworkConfiguration }
     *     
     */
    public TNetworkConfiguration getNetworkConfiguration() {
        return networkConfiguration;
    }

    /**
     * Sets the value of the networkConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNetworkConfiguration }
     *     
     */
    public void setNetworkConfiguration(TNetworkConfiguration value) {
        this.networkConfiguration = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "1.0.0";
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
