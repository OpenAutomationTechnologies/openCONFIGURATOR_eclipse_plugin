
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für t_indicatorList complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_indicatorList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LEDList" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="LED" type="{http://www.ethernet-powerlink.org}t_LED" maxOccurs="unbounded"/&gt;
 *                   &lt;element name="combinedState" type="{http://www.ethernet-powerlink.org}t_combinedState" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_indicatorList", propOrder = {
    "ledList"
})
public class TIndicatorList {

    @XmlElement(name = "LEDList")
    protected TIndicatorList.LEDList ledList;

    /**
     * Ruft den Wert der ledList-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TIndicatorList.LEDList }
     *     
     */
    public TIndicatorList.LEDList getLEDList() {
        return ledList;
    }

    /**
     * Legt den Wert der ledList-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TIndicatorList.LEDList }
     *     
     */
    public void setLEDList(TIndicatorList.LEDList value) {
        this.ledList = value;
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
     *         &lt;element name="LED" type="{http://www.ethernet-powerlink.org}t_LED" maxOccurs="unbounded"/&gt;
     *         &lt;element name="combinedState" type="{http://www.ethernet-powerlink.org}t_combinedState" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "led",
        "combinedState"
    })
    public static class LEDList {

        @XmlElement(name = "LED", required = true)
        protected List<TLED> led;
        protected List<TCombinedState> combinedState;

        /**
         * Gets the value of the led property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the led property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLED().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TLED }
         * 
         * 
         */
        public List<TLED> getLED() {
            if (led == null) {
                led = new ArrayList<TLED>();
            }
            return this.led;
        }

        /**
         * Gets the value of the combinedState property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the combinedState property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCombinedState().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TCombinedState }
         * 
         * 
         */
        public List<TCombinedState> getCombinedState() {
            if (combinedState == null) {
                combinedState = new ArrayList<TCombinedState>();
            }
            return this.combinedState;
        }

    }

}
