
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
     * Ruft den Wert der generator-Eigenschaft ab.
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
     * Legt den Wert der generator-Eigenschaft fest.
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
     * Ruft den Wert der ideConfiguration-Eigenschaft ab.
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
     * Legt den Wert der ideConfiguration-Eigenschaft fest.
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
     * Ruft den Wert der projectConfiguration-Eigenschaft ab.
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
     * Legt den Wert der projectConfiguration-Eigenschaft fest.
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
     * Ruft den Wert der networkConfiguration-Eigenschaft ab.
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
     * Legt den Wert der networkConfiguration-Eigenschaft fest.
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
     * Ruft den Wert der version-Eigenschaft ab.
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

}
