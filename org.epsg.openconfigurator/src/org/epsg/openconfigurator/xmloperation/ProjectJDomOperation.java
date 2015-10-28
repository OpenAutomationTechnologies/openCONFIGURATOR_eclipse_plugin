/*******************************************************************************
 * @file   ProjectJDomOperation.java
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

import org.epsg.openconfigurator.model.IAbstractNodeProperties;
import org.epsg.openconfigurator.model.IControlledNodeProperties;
import org.epsg.openconfigurator.model.IRedundantManagingNodeProperties;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

/**
 * Class that performs JDom operations on an openCONFIGURATOR project file.
 *
 * @author Ramakrishnan P
 *
 */
public class ProjectJDomOperation {

    static final XPathFactory XPATH_FACTORY_INSTANCE;
    static final Namespace OPENCONFIGURATOR_NAMESPACE;
    static final XPathExpression<Element> MN_XPATH_EXPR;

    static final XPathExpression<Element> NODE_ID_COLLECTION_XPATH_EXPR;

    static final XPathExpression<Element> NETWORKCONFIGURATION_XPATH_EXPR;

    static {
        XPATH_FACTORY_INSTANCE = XPathFactory.instance();

        OPENCONFIGURATOR_NAMESPACE = Namespace.getNamespace("oc",
                "http://sourceforge.net/projects/openconf/configuration");
        XPathBuilder<Element> mnXpathelementBuilder = new XPathBuilder<Element>(
                "//oc:MN", Filters.element());
        mnXpathelementBuilder.setNamespace(OPENCONFIGURATOR_NAMESPACE);
        MN_XPATH_EXPR = mnXpathelementBuilder
                .compileWith(XPATH_FACTORY_INSTANCE);

        XPathBuilder<Element> nodeCollectionXpathelementBuilder = new XPathBuilder<Element>(
                "//oc:NodeCollection", Filters.element());
        nodeCollectionXpathelementBuilder
                .setNamespace(OPENCONFIGURATOR_NAMESPACE);
        NODE_ID_COLLECTION_XPATH_EXPR = nodeCollectionXpathelementBuilder
                .compileWith(XPATH_FACTORY_INSTANCE);

        XPathBuilder<Element> netCfgXpathelementBuilder = new XPathBuilder<Element>(
                "//oc:NetworkConfiguration", Filters.element());
        netCfgXpathelementBuilder.setNamespace(OPENCONFIGURATOR_NAMESPACE);
        NETWORKCONFIGURATION_XPATH_EXPR = netCfgXpathelementBuilder
                .compileWith(XPATH_FACTORY_INSTANCE);
    }

    /**
     * Add new Controlled node to the openCONFIGURATOR project file.
     *
     * @param document Project file instance.
     * @param node The new CN node instance to be added.
     */
    private static void addControlledNode(Document document, TCN node) {
        Element newElement = new Element(IControlledNodeProperties.CN_TAG);

        List<Attribute> attribList = newElement.getAttributes();
        attribList.add(new Attribute(IAbstractNodeProperties.NODE_ID_OBJECT,
                node.getNodeID()));
        attribList.add(new Attribute(IAbstractNodeProperties.NODE_NAME_OBJECT,
                node.getName()));
        attribList.add(new Attribute(IAbstractNodeProperties.NODE_CONIFG_OBJECT,
                node.getPathToXDC()));

        String forcedMultiplexedCycle = String
                .valueOf(node.getForcedMultiplexedCycle());
        attribList.add(new Attribute(
                IControlledNodeProperties.CN_FORCED_MULTIPLEXED_CYCLE_OBJECT,
                forcedMultiplexedCycle));
        attribList
                .add(new Attribute(IControlledNodeProperties.CN_IS_MULTIPLEXED,
                        String.valueOf(node.isIsMultiplexed())));

        attribList.add(new Attribute(IControlledNodeProperties.CN_IS_CHAINED,
                String.valueOf(node.isIsChained())));
        attribList.add(
                new Attribute(IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_OBJECT,
                        String.valueOf(node.isIsAsyncOnly())));
        attribList.add(new Attribute(
                IAbstractNodeProperties.NODE_IS_TYPE1_ROUTER_OBJECT,
                String.valueOf(node.isIsType1Router())));
        attribList.add(new Attribute(
                IAbstractNodeProperties.NODE_IS_TYPE2_ROUTER_OBJECT,
                String.valueOf(node.isIsType2Router())));
        attribList.add(
                new Attribute(IControlledNodeProperties.CN_IS_MANDATORY_OBJECT,
                        String.valueOf(node.isIsMandatory())));
        attribList.add(new Attribute(
                IControlledNodeProperties.CN_AUTO_START_NODE_OBJECT,
                String.valueOf(node.isAutostartNode())));
        attribList.add(new Attribute(
                IControlledNodeProperties.CN_RESET_IN_OPERATIONAL_OBJECT,
                String.valueOf(node.isResetInOperational())));
        attribList.add(new Attribute(
                IControlledNodeProperties.CN_VERIFY_APP_SW_VERSION_OBJECT,
                String.valueOf(node.isVerifyAppSwVersion())));
        attribList.add(new Attribute(
                IControlledNodeProperties.CN_AUTO_APP_SW_UPDATE_ALLOWED_OBJECT,
                String.valueOf(node.isAutoAppSwUpdateAllowed())));
        attribList.add(new Attribute(
                IControlledNodeProperties.CN_VERIFY_DEVICE_TYPE_OBJECT,
                String.valueOf(node.isVerifyDeviceType())));
        attribList.add(new Attribute(
                IControlledNodeProperties.CN_VERIFY_VENDOR_ID_OBJECT,
                String.valueOf(node.isVerifyVendorId())));
        attribList.add(new Attribute(
                IControlledNodeProperties.CN_VERIFY_REVISION_NUMBER_OBJECT,
                String.valueOf(node.isVerifyRevisionNumber())));
        attribList.add(new Attribute(
                IControlledNodeProperties.CN_VERIFY_PRODUCT_CODE_OBJECT,
                String.valueOf(node.isVerifyProductCode())));
        attribList.add(new Attribute(
                IControlledNodeProperties.CN_VERIFY_SERIAL_NUMBER_OBJECT,
                String.valueOf(node.isVerifySerialNumber())));
        attribList.add(new Attribute(IControlledNodeProperties.CN_ENABLED,
                String.valueOf(node.isEnabled())));

        JDomUtil.addNewElement(document, NODE_ID_COLLECTION_XPATH_EXPR,
                newElement);
    }

    /**
     * Add new Node to the openCONFIGURATOR project file.
     *
     * @param document Project file instance.
     * @param node The new node instance to be added.
     */
    public static void addNode(Document document, Node node) {
        if (node.getNodeModel() instanceof TCN) {
            addControlledNode(document, (TCN) node.getNodeModel());
        } else if (node.getNodeModel() instanceof TRMN) {
            addRedundantManagingNode(document, (TRMN) node.getNodeModel());
        } else {
            System.err.println("Unsupported node ID:" + node.getNodeId()
                    + " modelType:" + node.getNodeModel());
        }
    }

    /**
     * Add new Redundant Managing Node to the openCONFIGURATOR project file.
     *
     * @param document Project file instance.
     * @param node The new RMN node instance to be added.
     */
    private static void addRedundantManagingNode(Document document, TRMN node) {

        Element newElement = new Element(
                IRedundantManagingNodeProperties.RMN_TAG);

        List<Attribute> attribList = newElement.getAttributes();
        attribList.add(new Attribute(IAbstractNodeProperties.NODE_ID_OBJECT,
                String.valueOf(node.getNodeID())));
        attribList.add(new Attribute(IAbstractNodeProperties.NODE_NAME_OBJECT,
                node.getName()));
        attribList.add(new Attribute(IAbstractNodeProperties.NODE_CONIFG_OBJECT,
                node.getPathToXDC()));
        attribList.add(
                new Attribute(IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_OBJECT,
                        String.valueOf(node.isIsAsyncOnly())));
        attribList.add(new Attribute(
                IAbstractNodeProperties.NODE_IS_TYPE1_ROUTER_OBJECT,
                String.valueOf(node.isIsType1Router())));
        attribList.add(new Attribute(
                IAbstractNodeProperties.NODE_IS_TYPE2_ROUTER_OBJECT,
                String.valueOf(node.isIsType2Router())));

        JDomUtil.addNewElement(document, NODE_ID_COLLECTION_XPATH_EXPR,
                newElement, 2);
    }

    /**
     * Delete a node from the project file.
     *
     * @param document Project file instance.
     * @param node The node to be removed from the project file.
     */
    public static void deleteNode(Document document, Node node) {
        JDomUtil.removeElement(document, node.getXpath(),
                OPENCONFIGURATOR_NAMESPACE);
    }

    /**
     * Add the force configuration for the object/sub-object for the given node.
     *
     * @param document The project file instance.
     * @param node The node for which the object/sub-object has to be forced.
     * @param object The object to be forced.
     * @param subObject The sub-object to be forced. Null in case of object.
     */
    public static void forceActualValue(Document document, final Node node,
            PowerlinkObject object, PowerlinkSubobject subObject) {

        if (subObject != null) {
            System.out.println("Force object actual value:"
                    + object.getObjectIdRaw() + subObject.getSubobjectIdRaw());
        } else {
            System.out.println(
                    "Force object actual value:" + object.getObjectIdRaw());
        }

        if (isObjectAlreadyForced(document, node, object, subObject)) {
            return;
        }

        String forcedTagXpath = node.getXpath() + "/oc:"
                + IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT;
        if (JDomUtil.isXpathPresent(document, forcedTagXpath,
                OPENCONFIGURATOR_NAMESPACE)) {
            Element newObjElement = new Element(
                    IAbstractNodeProperties.NODE_OBJECTS_OBJECT);
            Attribute objAttr = new Attribute(
                    IAbstractNodeProperties.NODE_OBJECTS_INDEX_OBJECT,
                    object.getObjectIdRaw());
            newObjElement.setAttribute(objAttr);

            if (subObject != null) {
                Attribute subObjAttr = new Attribute(
                        IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT,
                        subObject.getSubobjectIdRaw());
                newObjElement.setAttribute(subObjAttr);
            }

            JDomUtil.addNewElement(document, forcedTagXpath,
                    OPENCONFIGURATOR_NAMESPACE, newObjElement);
        } else {

            Element newObjElement = new Element(
                    IAbstractNodeProperties.NODE_OBJECTS_OBJECT);
            Attribute objAttr = new Attribute(
                    IAbstractNodeProperties.NODE_OBJECTS_INDEX_OBJECT,
                    object.getObjectIdRaw());
            newObjElement.setAttribute(objAttr);

            if (subObject != null) {
                Attribute subObjAttr = new Attribute(
                        IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT,
                        subObject.getSubobjectIdRaw());
                newObjElement.setAttribute(subObjAttr);
            }

            Element newElement = new Element(
                    IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT);
            newElement.setContent(newObjElement);

            JDomUtil.addNewElement(document, node.getXpath(),
                    OPENCONFIGURATOR_NAMESPACE, newElement);
        }
    }

    /**
     * Check if object is already forced in the project XML file.
     *
     * @param document The project file instance.
     * @param node The node instance.
     * @param object The object to be checked.
     * @param subObject The sub-object to be checked.
     * @return True if available False otherwise.
     */
    private static boolean isObjectAlreadyForced(Document document,
            final Node node, PowerlinkObject object,
            PowerlinkSubobject subObject) {
        String xpath = node.getXpath() + "/oc:"
                + IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT + "/oc:"
                + IAbstractNodeProperties.NODE_OBJECTS_OBJECT + "[@"
                + IAbstractNodeProperties.NODE_OBJECTS_INDEX_OBJECT + "='"
                + object.getObjectIdRaw() + "']";
        if (subObject != null) {
            xpath += "[@" + IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT
                    + "='" + subObject.getSubobjectIdRaw() + "']";
        }

        return JDomUtil.isXpathPresent(document, xpath,
                OPENCONFIGURATOR_NAMESPACE);
    }

    /**
     * Remove the forced object from the project XML file.
     *
     * @param document The project file instance.
     * @param node The node instance.
     * @param object The object to be removed.
     * @param subObject The sub-object to be removed. Null incaseof object.
     */
    public static void removeForcedObject(Document document, final Node node,
            PowerlinkObject object, PowerlinkSubobject subObject) {

        if (subObject != null) {
            System.out.println("removeForcedObject actual value:"
                    + object.getObjectIdRaw() + subObject.getSubobjectIdRaw());
        } else {
            System.out.println("removeForcedObject actual value:"
                    + object.getObjectIdRaw());
        }

        String xpath = node.getXpath() + "/oc:"
                + IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT + "/oc:"
                + IAbstractNodeProperties.NODE_OBJECTS_OBJECT + "[@"
                + IAbstractNodeProperties.NODE_OBJECTS_INDEX_OBJECT + "='"
                + object.getObjectIdRaw() + "']";
        if (subObject != null) {
            xpath += "[@" + IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT
                    + "='" + subObject.getSubobjectIdRaw() + "']";
        }

        JDomUtil.removeElement(document, xpath, OPENCONFIGURATOR_NAMESPACE);

        String forcedTagXpath = node.getXpath() + "/oc:"
                + IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT;

        if (JDomUtil.getChildrenCount(document, forcedTagXpath,
                OPENCONFIGURATOR_NAMESPACE) == 0) {
            JDomUtil.removeElement(document, forcedTagXpath,
                    OPENCONFIGURATOR_NAMESPACE);
        }
    }

    /**
     * Update {@link TNetworkConfiguration} values in the project file.
     *
     * @param document Project file instance.
     * @param attributeName Network attribute name.
     * @param attributeValue Value to be set.
     */
    public static void updateNetworkAttributeValue(Document document,
            final String attributeName, final String attributeValue) {

        JDomUtil.updateAttribute(document, NETWORKCONFIGURATION_XPATH_EXPR,
                new Attribute(attributeName, attributeValue));
    }

    /**
     * Update the attribute values for any node.
     *
     * @param document Project file instance.
     * @param node The node for which the properties are modified.
     * @param attributeName Node attribute name.
     * @param attributeValue Value to be set.
     */
    public static void updateNodeAttributeValue(Document document,
            final Node node, final String attributeName,
            final String attributeValue) {
        JDomUtil.updateAttribute(document, node.getXpath(),
                OPENCONFIGURATOR_NAMESPACE,
                new Attribute(attributeName, attributeValue));
    }
}
