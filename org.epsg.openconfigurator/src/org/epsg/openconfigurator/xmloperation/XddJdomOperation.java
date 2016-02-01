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

import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Namespace;

/**
 * Class that performs JDom operations on an XDD/XDC file.
 *
 * @author Ramakrishnan P
 *
 */
public class XddJdomOperation {

    static final Namespace POWERLINK_XDD_NAMESPACE;

    private static final String ACTUAL_VALUE = "actualValue";
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
                "//plk:Object[@" + ACTUAL_VALUE + "]", POWERLINK_XDD_NAMESPACE,
                ACTUAL_VALUE);
        JDomUtil.removeAttributes(document,
                "//plk:SubObject[@ " + ACTUAL_VALUE + "]",
                POWERLINK_XDD_NAMESPACE, ACTUAL_VALUE);
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
                + ACTUAL_VALUE + "]";
        JDomUtil.removeAttributes(document, xpath, POWERLINK_XDD_NAMESPACE,
                ACTUAL_VALUE);
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
        Attribute newAttribute = new Attribute(ACTUAL_VALUE, actualValue);
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
        Attribute newAttribute = new Attribute(ACTUAL_VALUE, actualValue);
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
}
