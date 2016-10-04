/*******************************************************************************
 * @file   JDomUtil.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.input.BOMInputStream;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.xml.sax.InputSource;

/**
 * Util class for JDOM2 operations.
 *
 * @author Ramakrishnan P
 *
 */
public class JDomUtil {

    public static void addModuleForceObjectElement(Document document,
            String forcedTagXpath, Namespace openconfiguratorNamespace,
            Element newObjElement) {
        System.out.println("addNewElement: " + forcedTagXpath);
        addModuleForceObjectElements(document, getXPathExpressionElement(
                forcedTagXpath, openconfiguratorNamespace), newObjElement);

    }

    private static void addModuleForceObjectElements(Document document,
            XPathExpression<Element> xPathExpressionElement,
            Element newObjElement) {
        List<Element> elementsList = xPathExpressionElement.evaluate(document);
        System.err.println("Elements list.." + elementsList);
        for (Element element : elementsList) {
            newObjElement.setNamespace(element.getNamespace());
            for (Element newChildElement : newObjElement.getChildren()) {
                newChildElement.setNamespace(element.getNamespace());
            }

            element.addContent(newObjElement);

        }

    }

    /**
     * Adds the new element under the xpath specified available in the document.
     *
     * @param doc The file instance.
     * @param xpath The parent path.
     * @param namespace Namespace of the element.
     * @param newElement The new element to be added.
     */
    public static void addNewElement(Document doc, String xpath,
            Namespace namespace, Element newElement) {
        System.out.println("addNewElement: " + xpath);
        addNewElement(doc, getXPathExpressionElement(xpath, namespace),
                newElement);
    }

    /**
     * Adds the new element under the xpath specified available in the document.
     *
     * @param doc The file instance.
     * @param xpath The parent path.
     * @param namespace Namespace of the element.
     * @param newElement The new element to be added.
     */
    public static void addNewElement(Document doc, String xpath,
            Namespace namespace, Element newElement, int index) {
        System.out.println("addNewElement: " + xpath);
        addNewElementWithIndex(doc, getXPathExpressionElement(xpath, namespace),
                newElement, index);
    }

    /**
     * Adds the new element under the xpath specified available in the document.
     *
     * @param doc The file instance.
     * @param xpath The parent path.
     * @param newElement The new element to be added.
     */
    public static void addNewElement(Document doc,
            XPathExpression<Element> xpath, Element newElement) {

        List<Element> elementsList = xpath.evaluate(doc);
        for (Element element : elementsList) {
            newElement.setNamespace(element.getNamespace());
            for (Element newChildElement : newElement.getChildren()) {
                newChildElement.setNamespace(element.getNamespace());
            }
            element.addContent(newElement);
        }
    }

    /**
     * Adds the new element under the xpath specified in a specified position
     * available in the document.
     *
     * @param doc The file instance.
     * @param xpath The parent path.
     * @param newElement The new element to be added.
     * @param position The position of the new element.
     */
    public static void addNewElement(Document doc,
            XPathExpression<Element> xpath, Element newElement, int position) {

        if (position < 1) {
            System.err
                    .print("Error invalid position:" + position + " minimum 1");
            return;
        }

        List<Element> elementsList = xpath.evaluate(doc);
        System.out.println("Size" + elementsList.size());
        if (elementsList.size() <= position) {
            Element parentElement = elementsList.get(0);
            newElement.setNamespace(parentElement.getNamespace());
            parentElement.addContent(position, newElement);
        } else {
            System.err.print(
                    "Error Xpath does not evaluate elements in the list");
            return;
        }
    }

    public static void addNewElements(Document doc, String xpath,
            Namespace namespace, Element newElement) {
        System.out.println("addNewElement: " + xpath);
        addNewElements(doc, getXPathExpressionElement(xpath, namespace),
                newElement);
    }

    public static void addNewElements(Document doc,
            XPathExpression<Element> xpath, Element newElement) {

        List<Element> elementsList = xpath.evaluate(doc);
        for (Element element : elementsList) {
            newElement.setNamespace(element.getNamespace());

            for (Element newChildElement : newElement.getChildren()) {
                newChildElement.setNamespace(element.getNamespace());
            }
            newElement.detach();
            element.addContent(newElement);
        }
    }

    /**
     * Adds the new element under the xpath specified available in the document.
     *
     * @param doc The file instance.
     * @param xpath The parent path.
     * @param newElement The new element to be added.
     */
    public static void addNewElementWithIndex(Document doc,
            XPathExpression<Element> xpath, Element newElement, int index) {

        List<Element> elementsList = xpath.evaluate(doc);
        for (Element element : elementsList) {
            newElement.setNamespace(element.getNamespace());
            for (Element newChildElement : newElement.getChildren()) {
                newChildElement.setNamespace(element.getNamespace());
            }
            element.addContent(index, newElement);
        }
    }

    /**
     * Add new child element into the parameter of XDD/XDC file.
     *
     * @param doc XDD/XDC file instance.
     * @param xpath Path value where the actual value element has to be placed.
     * @param namespace The namespace of the attribute.
     * @param newElement The new element to be added.
     * @param index The position where the element has to be added.
     */
    public static void addNewParameterElement(Document doc, String xpath,
            Namespace namespace, Element newElement, int index) {
        System.out.println("addNewElement: " + xpath);
        addNewParameterElement(doc, getXPathExpressionElement(xpath, namespace),
                newElement, index);
    }

    /**
     * Add new child element into the parameter of XDD/XDC file.
     *
     * @param doc XDD/XDC file instance.
     * @param xpath The parent Xpath.
     * @param newElement The new element to be added.
     * @param index The position where the element has to be added.
     */
    public static void addNewParameterElement(Document doc,
            XPathExpression<Element> xpath, Element newElement, int index) {

        List<Element> elementsList = xpath.evaluate(doc);

        for (Element element : elementsList) {
            newElement.setNamespace(element.getNamespace());
            for (Element newChildElement : newElement.getChildren()) {
                newChildElement.setNamespace(element.getNamespace());
            }
            element.addContent(index, newElement);

        }
    }

    /**
     * Get the count of child elements under parent element.
     *
     * @param document XDD/XDC file instance.
     * @param xpath The parent Xpath.
     * @param namespace The namespace of the attribute.
     * @return The total number of child elements available.
     */
    public static int getChildrenCount(Document document, String xpath,
            Namespace namespace) {
        XPathExpression<Element> xpathExpr = getXPathExpressionElement(xpath,
                namespace);
        Element parentElement = xpathExpr.evaluateFirst(document);
        return parentElement.getChildren().size();
    }

    /**
     * Get the XDD/XDC file
     *
     * @param xmlFile File instance
     * @return XDD/XDC file instance
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static org.jdom2.Document getXmlDocument(final File xmlFile)
            throws JDOMException, IOException {
        BOMInputStream bomIn = null;
        org.jdom2.Document document = null;

        try {
            bomIn = new BOMInputStream(new FileInputStream(xmlFile), false);
            Reader reader = new InputStreamReader(bomIn);
            InputSource input = new InputSource(reader);
            input.setSystemId(xmlFile.toURI().toString());

            SAXBuilder builder = new SAXBuilder();
            document = builder.build(input);

            reader.close();

            return document;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            if (bomIn != null) {
                bomIn.close();
            }
            e.printStackTrace();
        } finally {
            if (bomIn != null) {
                bomIn.close();
            }
        }
        return document;
    }

    /**
     * Get the Xpath expression from the given element.
     *
     * @param xpathValue The element value of Xpath.
     * @param namespace The namespace of the attribute.
     * @return The Xpath value element from the given expression.
     */
    public static XPathExpression<Element> getXPathExpressionElement(
            String xpathValue, Namespace namespace) {
        XPathBuilder<Element> elementBuilder = new XPathBuilder<>(xpathValue,
                Filters.element());
        elementBuilder.setNamespace(namespace);
        return elementBuilder.compileWith(XPathFactory.instance());
    }

    /**
     * Check the availability of given xpath element from the XDD/XDC file.
     *
     * @param document XDD/XDC file instance
     * @param xpath
     * @param namespace
     * @return <code>True</code> if element is present, <code>False</code> false
     *         otherwise.
     */
    public static boolean isXpathPresent(Document document, String xpath,
            Namespace namespace) {
        XPathExpression<Element> xpathExpr = getXPathExpressionElement(xpath,
                namespace);
        List<Element> elements = xpathExpr.evaluate(document);
        return (elements.size() > 0);
    }

    /**
     * Check the availability of given xpath element from the XDD/XDC file.
     *
     * @param document XDD/XDC file instance
     * @param xpath
     * @param namespace
     * @return <code>True</code> if element is present, <code>False</code> false
     *         otherwise.
     */
    public static boolean isXpathPresent(Document document,
            XPathExpression<Element> xpathExpr) {
        List<Element> elements = xpathExpr.evaluate(document);
        return (elements.size() > 0);
    }

    /**
     * Removes the attributes found on the result of the Xpath from the
     * document.
     *
     * @param document The file instance.
     * @param xpath The parent path.
     * @param namespace The namespace of the attribute.
     * @param attributeName The attribute name to be removed.
     */
    public static void removeAttributes(Document document, String xpath,
            Namespace namespace, String attributeName) {
        JDomUtil.removeAttributes(document,
                getXPathExpressionElement(xpath, namespace), attributeName);
    }

    /**
     * Removes the attributes found on the result of the Xpath from the
     * document.
     *
     * @param document The file instance.
     * @param xpathExpr The parent path.
     * @param attributeName The attribute name to be removed.
     */
    public static void removeAttributes(Document document,
            XPathExpression<Element> xpathExpr, String attributeName) {
        List<Element> elementsList = xpathExpr.evaluate(document);
        System.out.println("xpath:" + xpathExpr.getExpression() + " Size"
                + elementsList.size());
        for (Element element : elementsList) {
            element.removeAttribute(attributeName);
            System.err.println("Attribute removed...@#@#$%");
        }
    }

    /**
     * Removes the specified Xpath element.
     *
     * @param doc The file instance.
     * @param xpath The path of the element to be removed.
     * @param namespace The namespace of the element to be removed.
     */
    public static void removeElement(Document doc, String xpath,
            Namespace namespace) {
        removeElement(doc, getXPathExpressionElement(xpath, namespace));
    }

    /**
     * Removes the specified Xpath element.
     *
     * @param doc The file instance.
     * @param xpath The path of the element to be removed.
     */
    public static void removeElement(Document doc,
            XPathExpression<Element> xpath) {
        List<Element> elementsList = xpath.evaluate(doc);
        System.out.println("xpath:" + xpath.getExpression() + " Size"
                + elementsList.size());
        for (Element element : elementsList) {
            element.detach();
        }
    }

    /**
     * Add/update attribute for the specified Xpath element.
     *
     * @param doc The file instance.
     * @param xpathValue The path of the element to be updated.
     * @param namespace The namespace of the new attribute.
     * @param newAttribute The new attribute to be added or updated.
     */
    public static void setAttribute(Document doc, String xpathValue,
            Namespace namespace, Attribute newAttribute) {
        setAttribute(doc, getXPathExpressionElement(xpathValue, namespace),
                newAttribute);
    }

    /**
     * Add/update attribute for the specified Xpath element.
     *
     * @param doc The file instance.
     * @param xpath The path of the element to be updated.
     * @param newAttribute The new attribute to be added or updated.
     */
    public static void setAttribute(Document doc,
            XPathExpression<Element> xpath, Attribute newAttribute) {

        List<Element> elementsList = xpath.evaluate(doc);
        for (Element emt : elementsList) {
            emt.setAttribute(newAttribute.clone());
        }
    }

    /**
     * Update attribute value in the given Xpath element.
     *
     * @param doc The file instance.
     * @param xpath The path of the element to be updated.
     * @param namespace The namespace of the new attribute.
     * @param newAttribute The new attribute to be added or updated.
     */
    public static void updateAttribute(Document doc, String xpath,
            Namespace namespace, Attribute newAttribute) {
        XPathExpression<Element> expr = getXPathExpressionElement(xpath,
                namespace);
        updateAttribute(doc, expr, newAttribute);
    }

    /**
     * Update attribute value in the given Xpath element.
     *
     * @param document The file instance.
     * @param xpathExpr The path of the element to be updated.
     * @param newAttribute The new attribute to be added or updated.
     */
    public static void updateAttribute(Document document,
            XPathExpression<Element> xpathExpr, Attribute newAttribute) {

        Element emt = xpathExpr.evaluateFirst(document);
        if (emt != null) {
            emt.getAttributes().add(newAttribute);
            System.err.println("Attribute added into the project file.."
                    + newAttribute.getName() + " VALUE.."
                    + newAttribute.getValue());
        } else {
            System.err.println(xpathExpr.getExpression() + "Element null");
        }
    }

    public static void updateXDCAttribute(Document document,
            String nodeCollectionXpath, Namespace openconfiguratorNamespace) {
        XPathExpression<Element> expr = getXPathExpressionElement(
                nodeCollectionXpath, openconfiguratorNamespace);
        updateXDCAttribute(document, expr);

    }

    private static void updateXDCAttribute(Document document,
            XPathExpression<Element> expr) {
        List<Element> emtList = expr.evaluate(document);
        System.err.println(
                "Evaluating complete document...." + expr.evaluate(document));
        for (Element emt : emtList) {

            if (emt != null) {
                List<Attribute> attributeList = emt.getAttributes();
                for (Attribute attrib : attributeList) {
                    String name = attrib.getName();

                    if (name.equalsIgnoreCase("pathToXDC")) {
                        String value = attrib.getValue();
                        System.err.println("Path To XDC...." + value);
                        Path path = Paths.get(value);
                        path.getFileName();
                        attrib.setValue(
                                "deviceConfiguration/" + path.getFileName());
                    }

                }

            } else {
                System.err.println(expr.getExpression() + "Element null");
            }
        }
    }

    /**
     * Update changes in the project xml file.
     *
     * @param document The file instance.
     * @param xmlFile The Project or XDD file instance.
     * @throws IOException Error with XDD/ XDC file modification.
     */
    public static void writeToProjectXmlDocument(org.jdom2.Document document,
            final File xmlFile) throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        // display nice
        xmlOutput.setFormat(Format.getPrettyFormat());
        FileWriter fileWriter = new FileWriter(xmlFile);
        xmlOutput.output(document, fileWriter);
        fileWriter.close();
    }
}
