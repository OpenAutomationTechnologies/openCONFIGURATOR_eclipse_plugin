
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_Diagnostic complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_Diagnostic"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded"&gt;
 *         &lt;element name="ErrorList" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Error" type="{http://www.ethernet-powerlink.org}ErrorConstant_DataType" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="StaticErrorBitField" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ErrorBit" type="{http://www.ethernet-powerlink.org}ErrorBit_DataType" maxOccurs="64"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_Diagnostic", propOrder = {
    "errorListOrStaticErrorBitField"
})
public class TDiagnostic {

    @XmlElements({
        @XmlElement(name = "ErrorList", type = TDiagnostic.ErrorList.class),
        @XmlElement(name = "StaticErrorBitField", type = TDiagnostic.StaticErrorBitField.class)
    })
    protected List<Object> errorListOrStaticErrorBitField;

    /**
     * Gets the value of the errorListOrStaticErrorBitField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorListOrStaticErrorBitField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorListOrStaticErrorBitField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TDiagnostic.ErrorList }
     * {@link TDiagnostic.StaticErrorBitField }
     * 
     * 
     */
    public List<Object> getErrorListOrStaticErrorBitField() {
        if (errorListOrStaticErrorBitField == null) {
            errorListOrStaticErrorBitField = new ArrayList<Object>();
        }
        return this.errorListOrStaticErrorBitField;
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
     *         &lt;element name="Error" type="{http://www.ethernet-powerlink.org}ErrorConstant_DataType" maxOccurs="unbounded"/&gt;
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
        "error"
    })
    public static class ErrorList {

        @XmlElement(name = "Error", required = true)
        protected List<ErrorConstantDataType> error;

        /**
         * Gets the value of the error property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the error property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getError().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ErrorConstantDataType }
         * 
         * 
         */
        public List<ErrorConstantDataType> getError() {
            if (error == null) {
                error = new ArrayList<ErrorConstantDataType>();
            }
            return this.error;
        }

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
     *         &lt;element name="ErrorBit" type="{http://www.ethernet-powerlink.org}ErrorBit_DataType" maxOccurs="64"/&gt;
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
        "errorBit"
    })
    public static class StaticErrorBitField {

        @XmlElement(name = "ErrorBit", required = true)
        protected List<ErrorBitDataType> errorBit;

        /**
         * Gets the value of the errorBit property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the errorBit property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getErrorBit().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ErrorBitDataType }
         * 
         * 
         */
        public List<ErrorBitDataType> getErrorBit() {
            if (errorBit == null) {
                errorBit = new ArrayList<ErrorBitDataType>();
            }
            return this.errorBit;
        }

    }

}
