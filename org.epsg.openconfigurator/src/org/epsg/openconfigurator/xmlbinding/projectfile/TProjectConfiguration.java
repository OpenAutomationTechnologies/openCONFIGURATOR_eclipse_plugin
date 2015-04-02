
package org.epsg.openconfigurator.xmlbinding.projectfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies configuration options which apply to the openCONFIGURATOR project
 *                 itself.
 * 
 * <p>Java-Klasse für tProjectConfiguration complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="tProjectConfiguration"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PathSettings" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Path" type="{http://sourceforge.net/projects/openconf/configuration}tPath" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AutoGenerationSettings" type="{http://sourceforge.net/projects/openconf/configuration}tAutoGenerationSettings" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="activeAutoGenerationSetting" use="required" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tProjectConfiguration", propOrder = {
    "pathSettings",
    "autoGenerationSettings"
})
public class TProjectConfiguration {

    @XmlElement(name = "PathSettings")
    protected TProjectConfiguration.PathSettings pathSettings;
    @XmlElement(name = "AutoGenerationSettings", required = true)
    protected List<TAutoGenerationSettings> autoGenerationSettings;
    @XmlAttribute(name = "activeAutoGenerationSetting", required = true)
    protected String activeAutoGenerationSetting;

    /**
     * Ruft den Wert der pathSettings-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TProjectConfiguration.PathSettings }
     *     
     */
    public TProjectConfiguration.PathSettings getPathSettings() {
        return pathSettings;
    }

    /**
     * Legt den Wert der pathSettings-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TProjectConfiguration.PathSettings }
     *     
     */
    public void setPathSettings(TProjectConfiguration.PathSettings value) {
        this.pathSettings = value;
    }

    /**
     * Gets the value of the autoGenerationSettings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the autoGenerationSettings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAutoGenerationSettings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TAutoGenerationSettings }
     * 
     * 
     */
    public List<TAutoGenerationSettings> getAutoGenerationSettings() {
        if (autoGenerationSettings == null) {
            autoGenerationSettings = new ArrayList<TAutoGenerationSettings>();
        }
        return this.autoGenerationSettings;
    }

    /**
     * Ruft den Wert der activeAutoGenerationSetting-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActiveAutoGenerationSetting() {
        return activeAutoGenerationSetting;
    }

    /**
     * Legt den Wert der activeAutoGenerationSetting-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveAutoGenerationSetting(String value) {
        this.activeAutoGenerationSetting = value;
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
     *         &lt;element name="Path" type="{http://sourceforge.net/projects/openconf/configuration}tPath" maxOccurs="unbounded"/&gt;
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
        "path"
    })
    public static class PathSettings {

        @XmlElement(name = "Path", required = true)
        protected List<TPath> path;

        /**
         * Gets the value of the path property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the path property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPath().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TPath }
         * 
         * 
         */
        public List<TPath> getPath() {
            if (path == null) {
                path = new ArrayList<TPath>();
            }
            return this.path;
        }

    }

}
