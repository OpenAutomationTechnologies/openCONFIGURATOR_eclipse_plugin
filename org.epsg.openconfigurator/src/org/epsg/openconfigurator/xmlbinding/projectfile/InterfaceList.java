
package org.epsg.openconfigurator.xmlbinding.projectfile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Interface" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Module" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element ref="{http://sourceforge.net/projects/openconf/configuration}InterfaceList" minOccurs="0"/&gt;
 *                             &lt;element name="ForcedObjects" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence maxOccurs="unbounded"&gt;
 *                                       &lt;element ref="{http://sourceforge.net/projects/openconf/configuration}Object"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="name" use="required" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
 *                           &lt;attribute name="position" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                           &lt;attribute name="address" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *                           &lt;attribute name="pathToXDC" use="required"&gt;
 *                             &lt;simpleType&gt;
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI"&gt;
 *                                 &lt;minLength value="1"/&gt;
 *                               &lt;/restriction&gt;
 *                             &lt;/simpleType&gt;
 *                           &lt;/attribute&gt;
 *                           &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="id" use="required" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
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
@XmlType(name = "", propOrder = { "_interface" })
@XmlRootElement(name = "InterfaceList")
public class InterfaceList {

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained
     * within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Module" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element ref="{http://sourceforge.net/projects/openconf/configuration}InterfaceList" minOccurs="0"/&gt;
     *                   &lt;element name="ForcedObjects" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence maxOccurs="unbounded"&gt;
     *                             &lt;element ref="{http://sourceforge.net/projects/openconf/configuration}Object"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="name" use="required" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
     *                 &lt;attribute name="position" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
     *                 &lt;attribute name="address" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
     *                 &lt;attribute name="pathToXDC" use="required"&gt;
     *                   &lt;simpleType&gt;
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI"&gt;
     *                       &lt;minLength value="1"/&gt;
     *                     &lt;/restriction&gt;
     *                   &lt;/simpleType&gt;
     *                 &lt;/attribute&gt;
     *                 &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="id" use="required" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "module" })
    public static class Interface {

        /**
         * <p>
         * Java class for anonymous complex type.
         *
         * <p>
         * The following schema fragment specifies the expected content
         * contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element ref="{http://sourceforge.net/projects/openconf/configuration}InterfaceList" minOccurs="0"/&gt;
         *         &lt;element name="ForcedObjects" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence maxOccurs="unbounded"&gt;
         *                   &lt;element ref="{http://sourceforge.net/projects/openconf/configuration}Object"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="name" use="required" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
         *       &lt;attribute name="position" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
         *       &lt;attribute name="address" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
         *       &lt;attribute name="pathToXDC" use="required"&gt;
         *         &lt;simpleType&gt;
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI"&gt;
         *             &lt;minLength value="1"/&gt;
         *           &lt;/restriction&gt;
         *         &lt;/simpleType&gt;
         *       &lt;/attribute&gt;
         *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "interfaceList", "forcedObjects" })
        public static class Module {

            /**
             * <p>
             * Java class for anonymous complex type.
             *
             * <p>
             * The following schema fragment specifies the expected content
             * contained within this class.
             *
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence maxOccurs="unbounded"&gt;
             *         &lt;element ref="{http://sourceforge.net/projects/openconf/configuration}Object"/&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "object" })
            public static class ForcedObjects {

                @XmlElement(name = "Object", required = true)
                protected List<Object> object;

                /**
                 * Gets the value of the object property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object. This is
                 * why there is not a <CODE>set</CODE> method for the object
                 * property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * 
                 * <pre>
                 * getObject().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Object }
                 *
                 *
                 */
                public List<Object> getObject() {
                    if (object == null) {
                        object = new ArrayList<>();
                    }
                    return object;
                }

            }

            @XmlElement(name = "InterfaceList")
            protected InterfaceList interfaceList;
            @XmlElement(name = "ForcedObjects")
            protected InterfaceList.Interface.Module.ForcedObjects forcedObjects;
            @XmlAttribute(name = "name", required = true)
            protected String name;
            @XmlAttribute(name = "position", required = true)
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger position;
            @XmlAttribute(name = "address")
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger address;
            @XmlAttribute(name = "pathToXDC", required = true)
            protected String pathToXDC;

            @XmlAttribute(name = "enabled")
            protected Boolean enabled;

            /**
             * Gets the value of the address property.
             *
             * @return possible object is {@link BigInteger }
             * 
             */
            public BigInteger getAddress() {
                return address;
            }

            /**
             * Gets the value of the forcedObjects property.
             *
             * @return possible object is
             *         {@link InterfaceList.Interface.Module.ForcedObjects }
             * 
             */
            public InterfaceList.Interface.Module.ForcedObjects getForcedObjects() {
                return forcedObjects;
            }

            /**
             * Gets the value of the interfaceList property.
             *
             * @return possible object is {@link InterfaceList }
             * 
             */
            public InterfaceList getInterfaceList() {
                return interfaceList;
            }

            /**
             * Gets the value of the name property.
             *
             * @return possible object is {@link String }
             * 
             */
            public String getName() {
                return name;
            }

            /**
             * Gets the value of the pathToXDC property.
             *
             * @return possible object is {@link String }
             * 
             */
            public String getPathToXDC() {
                return pathToXDC;
            }

            /**
             * Gets the value of the position property.
             *
             * @return possible object is {@link BigInteger }
             * 
             */
            public BigInteger getPosition() {
                return position;
            }

            /**
             * Gets the value of the enabled property.
             *
             * @return possible object is {@link Boolean }
             * 
             */
            public boolean isEnabled() {
                if (enabled == null) {
                    return true;
                } else {
                    return enabled;
                }
            }

            /**
             * Sets the value of the address property.
             *
             * @param value allowed object is {@link BigInteger }
             * 
             */
            public void setAddress(BigInteger value) {
                address = value;
            }

            /**
             * Sets the value of the enabled property.
             *
             * @param value allowed object is {@link Boolean }
             * 
             */
            public void setEnabled(Boolean value) {
                enabled = value;
            }

            /**
             * Sets the value of the forcedObjects property.
             *
             * @param value allowed object is
             *            {@link InterfaceList.Interface.Module.ForcedObjects }
             * 
             */
            public void setForcedObjects(
                    InterfaceList.Interface.Module.ForcedObjects value) {
                forcedObjects = value;
            }

            /**
             * Sets the value of the interfaceList property.
             *
             * @param value allowed object is {@link InterfaceList }
             * 
             */
            public void setInterfaceList(InterfaceList value) {
                interfaceList = value;
            }

            /**
             * Sets the value of the name property.
             *
             * @param value allowed object is {@link String }
             * 
             */
            public void setName(String value) {
                name = value;
            }

            /**
             * Sets the value of the pathToXDC property.
             *
             * @param value allowed object is {@link String }
             * 
             */
            public void setPathToXDC(String value) {
                pathToXDC = value;
            }

            /**
             * Sets the value of the position property.
             *
             * @param value allowed object is {@link BigInteger }
             * 
             */
            public void setPosition(BigInteger value) {
                position = value;
            }

        }

        @XmlElement(name = "Module", required = true)
        protected List<InterfaceList.Interface.Module> module;

        @XmlAttribute(name = "id", required = true)
        protected String id;

        /**
         * Gets the value of the id property.
         *
         * @return possible object is {@link String }
         * 
         */
        public String getId() {
            return id;
        }

        /**
         * Gets the value of the module property.
         *
         * <p>
         * This accessor method returns a reference to the live list, not a
         * snapshot. Therefore any modification you make to the returned list
         * will be present inside the JAXB object. This is why there is not a
         * <CODE>set</CODE> method for the module property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * 
         * <pre>
         * getModule().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InterfaceList.Interface.Module }
         *
         *
         */
        public List<InterfaceList.Interface.Module> getModule() {
            if (module == null) {
                module = new ArrayList<>();
            }
            return module;
        }

        /**
         * Sets the value of the id property.
         *
         * @param value allowed object is {@link String }
         * 
         */
        public void setId(String value) {
            id = value;
        }

    }

    @XmlElement(name = "Interface", required = true)
    protected List<InterfaceList.Interface> _interface;

    /**
     * Gets the value of the interface property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the interface property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getInterface().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InterfaceList.Interface }
     *
     *
     */
    public List<InterfaceList.Interface> getInterface() {
        if (_interface == null) {
            _interface = new ArrayList<>();
        }
        return _interface;
    }

}
