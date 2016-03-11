/*******************************************************************************
 * @file   XddJdomOperation.java
 *
 * @author Ramakrishnan Periyakaruppan, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2015, Kalycito Infotech Private Limited
 *                    All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the copyright holders nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/

package org.epsg.openconfigurator.xmloperation;

import java.util.List;

import org.epsg.openconfigurator.model.Parameter;
import org.epsg.openconfigurator.model.ParameterReference;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.xpath.XPathExpression;

/**
 * Class that performs JDom operations on an XDD/XDC file.
 *
 * @author Ramakrishnan P
 *
 */
public class XddJdomOperation {

    static final Namespace POWERLINK_XDD_NAMESPACE;

    private static final String OBJECT_ACTUAL_VALUE = "actualValue";
    private static final String PARAMETER_VALUE = "value";
    public static final String PARAMETER_ACTUAL_VALUE = "actualValue";
    public static final String PARAMETER_DEFAULT_VALUE = "defaultValue";
    public static final String PARAMETER_SUBSTITUTE_VALUE = "substituteValue";
    public static final String PARAMETER_ALLOWED_VALUE = "allowedValues";
    public static final String PARAMETER_UNIT_ELEMENT = "unit";
    public static final String PARAMETER_PROPERTY_ELEMENT = "property";
    private static final String FILE_MODIFICATION_TIME = "fileModificationTime";
    private static final String FILE_MODIFICATION_DATE = "fileModificationDate";
    private static final String FILE_MODIFIED_BY = "fileModifiedBy";
    private static final String FILE_MODIFICATION_TIME_XPATH = "//plk:ProfileBody[@"
            + FILE_MODIFICATION_TIME + "]";
    private static final String FILE_MODIFICATION_DATE_XPATH = "//plk:ProfileBody[@"
            + FILE_MODIFICATION_DATE + "]";
    private static final String FILE_MODIFIED_BY_XPATH = "//plk:ProfileBody[@"
            + FILE_MODIFIED_BY + "]";

    static {
        POWERLINK_XDD_NAMESPACE = Namespace.getNamespace("plk",
                "http://www.ethernet-powerlink.org");
    }

    /**
     * Removes the actual value from the XDD/XDC file.
     *
     * @param document XDD/XDC file instance.
     */
    public static void deleteActualValues(Document document) {
        JDomUtil.removeAttributes(document,
                "//plk:Object[@" + OBJECT_ACTUAL_VALUE + "]",
                POWERLINK_XDD_NAMESPACE, OBJECT_ACTUAL_VALUE);
        JDomUtil.removeAttributes(document,
                "//plk:SubObject[@ " + OBJECT_ACTUAL_VALUE + "]",
                POWERLINK_XDD_NAMESPACE, OBJECT_ACTUAL_VALUE);
    }

    /**
     * Removes the actual value of sub-objects from the XDD/XDC file.
     *
     * @param document XDD/XDC file instance.
     * @param powerlinkObj PowerlinkObject instance to get the path of
     *            sub-object.
     */
    public static void deletePowerlinkObjectActualValue(Document document,
            PowerlinkObject powerlinkObj) {

        String xpath = powerlinkObj.getXpath() + "/plk:SubObject[@"
                + OBJECT_ACTUAL_VALUE + "]";
        JDomUtil.removeAttributes(document, xpath, POWERLINK_XDD_NAMESPACE,
                OBJECT_ACTUAL_VALUE);
    }

    /**
     * Get the sequence order or index position of child elements in the
     * parameter of XDD/XDC file.
     *
     * @param document XDD/XDC file instance.
     * @param parameter Instance of parameter.
     * @return The position of parameter child elements.
     */
    public static int getChildIndexbelowActualvalue(Document document,
            Parameter parameter) {
        XPathExpression<Element> xpath = JDomUtil.getXPathExpressionElement(
                parameter.getXpath(), POWERLINK_XDD_NAMESPACE);
        List<Element> elementsList = xpath.evaluate(document);
        Element parentElement = elementsList.get(0);
        List<Element> childElement = parentElement.getChildren();
        if (!childElement.isEmpty()) {
            for (int childCount = 1; childCount <= childElement
                    .size(); childCount++) {
                for (Element child : childElement) {
                    int index = parentElement.indexOf(child);
                    if (child.getQualifiedName() == PARAMETER_DEFAULT_VALUE) {
                        return index;
                    } else if (child
                            .getQualifiedName() == PARAMETER_SUBSTITUTE_VALUE) {
                        return index;
                    } else if (child
                            .getQualifiedName() == PARAMETER_ALLOWED_VALUE) {
                        return index;
                    } else if (child
                            .getQualifiedName() == PARAMETER_UNIT_ELEMENT) {
                        return index;
                    } else if (child
                            .getQualifiedName() == PARAMETER_PROPERTY_ELEMENT) {
                        return index;
                    }
                }
            }
        } else {
            System.err.println(
                    "No child elements are available for the given parameter with uniqueID = "
                            + parameter.getUniqueId());
        }
        return 0;
    }

    /**
     * Update the given actual value for the given object in the XDD/XDC
     * document.
     *
     * @param document XDD/XDC file instance.
     * @param object Object that holds the value.
     * @param actualValue The new value to be set.
     */
    public static void updateActualValue(Document document,
            PowerlinkObject object, String actualValue) {
        Attribute newAttribute = new Attribute(OBJECT_ACTUAL_VALUE,
                actualValue);
        JDomUtil.setAttribute(document, object.getXpath(),
                POWERLINK_XDD_NAMESPACE, newAttribute);
    }

    /**
     * Update the given actual value for the given subobject in the XDD/XDC
     * document.
     *
     * @param document XDD/XDC file instance.
     * @param subobject SubObject that holds the value.
     * @param actualValue The new value to be set.
     */
    public static void updateActualValue(Document document,
            PowerlinkSubobject subobject, String actualValue) {
        Attribute newAttribute = new Attribute(OBJECT_ACTUAL_VALUE,
                actualValue);
        JDomUtil.setAttribute(document, subobject.getXpath(),
                POWERLINK_XDD_NAMESPACE, newAttribute);
    }

    /**
     * Update the given name for the given file modified by attribute in the
     * XDD/XDC document
     *
     * @param document XDD/XDC file instance.
     * @param value The user name to be set.
     */
    public static void updateFileModifiedBy(Document document, String value) {
        Attribute newAttribute = new Attribute(FILE_MODIFIED_BY, value);
        JDomUtil.setAttribute(document, FILE_MODIFIED_BY_XPATH,
                POWERLINK_XDD_NAMESPACE, newAttribute);
    }

    /**
     * Update the given date for the given file modified date attribute in the
     * XDD/XDC document
     *
     * @param document XDD/XDC file instance.
     * @param value The current date to be set.
     */
    public static void updateFileModifiedDate(Document document, String value) {
        Attribute newAttribute = new Attribute(FILE_MODIFICATION_DATE, value);
        JDomUtil.setAttribute(document, FILE_MODIFICATION_DATE_XPATH,
                POWERLINK_XDD_NAMESPACE, newAttribute);
    }

    /**
     * Update the given time for the given file modified time attribute in the
     * XDD/XDC document
     *
     * @param document XDD/XDC file instance.
     * @param time The current time to be set.
     */
    public static void updateFileModifiedTime(Document document, String time) {
        Attribute newAttribute = new Attribute(FILE_MODIFICATION_TIME, time);
        JDomUtil.setAttribute(document, FILE_MODIFICATION_TIME_XPATH,
                POWERLINK_XDD_NAMESPACE, newAttribute);

    }

    /**
     * Update actual value element of parameter with the given value.
     *
     * @param document XDD/XDC file instance.
     * @param parameter Instance of parameter.
     * @param actualValue The value to be updated in the XDD/XDC file.
     */
    public static void updateParameterActualValue(Document document,
            Parameter parameter, String actualValue) {
        String Xpath = parameter.getParameterActualValueXpath();
        if (JDomUtil.isXpathPresent(document, Xpath, POWERLINK_XDD_NAMESPACE)) {
            JDomUtil.updateAttribute(document, Xpath, POWERLINK_XDD_NAMESPACE,
                    new Attribute(PARAMETER_VALUE, actualValue));
        } else if (JDomUtil.isXpathPresent(document, parameter.getXpath(),
                POWERLINK_XDD_NAMESPACE)) {
            Element newObjElement = new Element(PARAMETER_ACTUAL_VALUE);
            Attribute paramAttr = new Attribute(PARAMETER_VALUE, actualValue);
            newObjElement.setAttribute(paramAttr);
            // Check for the sequence order of child elements available in the
            // parameter.
            if (getChildIndexbelowActualvalue(document, parameter) != 0) {
                int index = getChildIndexbelowActualvalue(document, parameter)
                        - 1;
                JDomUtil.addNewParameterElement(document, parameter.getXpath(),
                        POWERLINK_XDD_NAMESPACE, newObjElement, index);
            } else {
                JDomUtil.addNewElement(document, parameter.getXpath(),
                        POWERLINK_XDD_NAMESPACE, newObjElement);
            }

        } else {
            System.err.println("Parameter Xpath not present");
        }
    }

    /**
     * Update the actual value attribute of parameter reference with the given
     * value.
     *
     * @param document XDD/XDC file instance.
     * @param parameterReference Instance of Parameter reference.
     * @param actualValue The value to be updated into the XDD/XDC file.
     */
    public static void updateParameterReferenceActualValue(Document document,
            ParameterReference parameterReference, String actualValue) {
        System.err.println("Parameter reference Xpath == "
                + parameterReference.getXpath());
        if (JDomUtil.isXpathPresent(document, parameterReference.getXpath(),
                POWERLINK_XDD_NAMESPACE)) {
            Attribute newAttribute = new Attribute(OBJECT_ACTUAL_VALUE,
                    actualValue);
            JDomUtil.setAttribute(document, parameterReference.getXpath(),
                    POWERLINK_XDD_NAMESPACE, newAttribute);
            System.err.println("Parameter reference value updated");
        } else {
            System.err.println("Parameter reference xpath not found");
        }

    }
}
