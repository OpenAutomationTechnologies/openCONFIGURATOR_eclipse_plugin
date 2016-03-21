
package org.epsg.openconfigurator.xmlbinding.projectfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies optional configuration options which apply to the IDE only.
 * 
 * <p>Java class for tIDEConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tIDEConfiguration"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ViewSettings" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Setting" type="{http://sourceforge.net/projects/openconf/configuration}tKeyValuePair" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="type" use="required" type="{http://sourceforge.net/projects/openconf/configuration}tView" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="activeViewSetting" type="{http://sourceforge.net/projects/openconf/configuration}tView" default="BASIC" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tIDEConfiguration", propOrder = {
    "viewSettings"
})
public class TIDEConfiguration {

    @XmlElement(name = "ViewSettings", required = true)
    protected List<TIDEConfiguration.ViewSettings> viewSettings;
    @XmlAttribute(name = "activeViewSetting")
    protected TView activeViewSetting;

    /**
     * Gets the value of the viewSettings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the viewSettings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getViewSettings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TIDEConfiguration.ViewSettings }
     * 
     * 
     */
    public List<TIDEConfiguration.ViewSettings> getViewSettings() {
        if (viewSettings == null) {
            viewSettings = new ArrayList<TIDEConfiguration.ViewSettings>();
        }
        return this.viewSettings;
    }

    /**
     * Gets the value of the activeViewSetting property.
     * 
     * @return
     *     possible object is
     *     {@link TView }
     *     
     */
    public TView getActiveViewSetting() {
        if (activeViewSetting == null) {
            return TView.BASIC;
        } else {
            return activeViewSetting;
        }
    }

    /**
     * Sets the value of the activeViewSetting property.
     * 
     * @param value
     *     allowed object is
     *     {@link TView }
     *     
     */
    public void setActiveViewSetting(TView value) {
        this.activeViewSetting = value;
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
     *         &lt;element name="Setting" type="{http://sourceforge.net/projects/openconf/configuration}tKeyValuePair" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="type" use="required" type="{http://sourceforge.net/projects/openconf/configuration}tView" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "setting"
    })
    public static class ViewSettings {

        @XmlElement(name = "Setting")
        protected List<TKeyValuePair> setting;
        @XmlAttribute(name = "type", required = true)
        protected TView type;

        /**
         * Gets the value of the setting property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the setting property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSetting().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TKeyValuePair }
         * 
         * 
         */
        public List<TKeyValuePair> getSetting() {
            if (setting == null) {
                setting = new ArrayList<TKeyValuePair>();
            }
            return this.setting;
        }

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link TView }
         *     
         */
        public TView getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link TView }
         *     
         */
        public void setType(TView value) {
            this.type = value;
        }

    }

}
