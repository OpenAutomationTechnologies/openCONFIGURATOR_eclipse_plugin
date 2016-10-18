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

import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.IAbstractNodeProperties;
import org.epsg.openconfigurator.model.IControlledNodeProperties;
import org.epsg.openconfigurator.model.IRedundantManagingNodeProperties;
import org.epsg.openconfigurator.model.Module;
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

    static final XPathExpression<Element> CN_XPATH_EXPR;

    static final XPathExpression<Element> NODE_ID_COLLECTION_XPATH_EXPR;

    static final XPathExpression<Element> NETWORKCONFIGURATION_XPATH_EXPR;

    static final XPathExpression<Element> GENERATOR_XPATH_EXPR;

    static {
        XPATH_FACTORY_INSTANCE = XPathFactory.instance();

        OPENCONFIGURATOR_NAMESPACE = Namespace.getNamespace("oc",
                "http://sourceforge.net/projects/openconf/configuration");
        XPathBuilder<Element> mnXpathelementBuilder = new XPathBuilder<>(
                "//oc:MN", Filters.element());
        mnXpathelementBuilder.setNamespace(OPENCONFIGURATOR_NAMESPACE);
        MN_XPATH_EXPR = mnXpathelementBuilder
                .compileWith(XPATH_FACTORY_INSTANCE);

        XPathBuilder<Element> nodeCollectionXpathelementBuilder = new XPathBuilder<>(
                "//oc:NodeCollection", Filters.element());
        nodeCollectionXpathelementBuilder
                .setNamespace(OPENCONFIGURATOR_NAMESPACE);
        NODE_ID_COLLECTION_XPATH_EXPR = nodeCollectionXpathelementBuilder
                .compileWith(XPATH_FACTORY_INSTANCE);

        XPathBuilder<Element> netCfgXpathelementBuilder = new XPathBuilder<>(
                "//oc:NetworkConfiguration", Filters.element());
        netCfgXpathelementBuilder.setNamespace(OPENCONFIGURATOR_NAMESPACE);
        NETWORKCONFIGURATION_XPATH_EXPR = netCfgXpathelementBuilder
                .compileWith(XPATH_FACTORY_INSTANCE);

        XPathBuilder<Element> cnXpathelementBuilder = new XPathBuilder<>(
                "//oc:MN", Filters.element());
        cnXpathelementBuilder.setNamespace(OPENCONFIGURATOR_NAMESPACE);
        CN_XPATH_EXPR = cnXpathelementBuilder
                .compileWith(XPATH_FACTORY_INSTANCE);

        XPathBuilder<Element> generatorXpathelementBuilder = new XPathBuilder<>(
                "//oc:Generator", Filters.element());
        generatorXpathelementBuilder.setNamespace(OPENCONFIGURATOR_NAMESPACE);
        GENERATOR_XPATH_EXPR = generatorXpathelementBuilder
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

    public static void addInterfaceList(Document document, final Node node,
            HeadNodeInterface interfaceOfNode, Module module) {

        String interfaceListTagXpath = node.getXpath() + "/oc:"
                + IControlledNodeProperties.INTERFACE_LIST_TAG;

        String interfaceXpath = interfaceListTagXpath + "/oc:"
                + IControlledNodeProperties.INTERFACE_TAG + "[@id='"
                + interfaceOfNode.getInterfaceUId() + "']";

        if (JDomUtil.isXpathPresent(document, interfaceListTagXpath,
                OPENCONFIGURATOR_NAMESPACE)) {

            System.err.println("Interface List Xpath is present...");
            Element newObjElement = new Element(
                    IControlledNodeProperties.INTERFACE_TAG);
            Attribute intAttr = new Attribute(
                    IControlledNodeProperties.INTERFACE_ID,
                    interfaceOfNode.getInterfaceUId());
            newObjElement.setAttribute(intAttr);

            Element moduleObjElement = new Element(
                    IControlledNodeProperties.MODULE_TAG);
            List<Attribute> attribList = moduleObjElement.getAttributes();

            attribList.add(new Attribute(IControlledNodeProperties.MODULE_NAME,
                    module.getModuleName()));

            attribList.add(
                    new Attribute(IControlledNodeProperties.MODULE_POSITION,
                            String.valueOf(module.getPosition())));
            attribList
                    .add(new Attribute(IControlledNodeProperties.MODULE_ADDRESS,
                            String.valueOf(module.getAddress())));

            attribList.add(
                    new Attribute(IControlledNodeProperties.MODULE_PATH_TO_XDC,
                            module.getModulePathToXdc()));

            attribList
                    .add(new Attribute(IControlledNodeProperties.MODULE_ENABLED,
                            String.valueOf(module.isEnabled())));
            System.err.println("Interface XPath == " + interfaceXpath);
            if (JDomUtil.isXpathPresent(document, interfaceXpath,
                    OPENCONFIGURATOR_NAMESPACE)) {
                System.err.println("Interface with Id Xpath is present...");
                JDomUtil.addNewElement(document, interfaceXpath,
                        OPENCONFIGURATOR_NAMESPACE, moduleObjElement);

            } else {
                JDomUtil.addNewElement(document, interfaceListTagXpath,
                        OPENCONFIGURATOR_NAMESPACE, newObjElement);
                JDomUtil.addNewElement(document, interfaceXpath,
                        OPENCONFIGURATOR_NAMESPACE, moduleObjElement);
            }

        } else {

            System.err.println("Creating new element.........");
            Element newElement = new Element(
                    IControlledNodeProperties.INTERFACE_LIST_TAG);
            Element newObjElement = new Element(
                    IControlledNodeProperties.INTERFACE_TAG);

            Attribute intAttr = new Attribute(
                    IControlledNodeProperties.INTERFACE_ID,
                    interfaceOfNode.getInterfaceUId());
            newObjElement.setAttribute(intAttr);

            Element moduleObjElement = new Element(
                    IControlledNodeProperties.MODULE_TAG);
            List<Attribute> attribList = moduleObjElement.getAttributes();

            attribList.add(new Attribute(IControlledNodeProperties.MODULE_NAME,
                    module.getModuleName()));

            attribList.add(
                    new Attribute(IControlledNodeProperties.MODULE_POSITION,
                            String.valueOf(module.getPosition())));

            attribList
                    .add(new Attribute(IControlledNodeProperties.MODULE_ADDRESS,
                            String.valueOf(module.getAddress())));

            attribList.add(
                    new Attribute(IControlledNodeProperties.MODULE_PATH_TO_XDC,
                            module.getModulePathToXdc()));

            attribList
                    .add(new Attribute(IControlledNodeProperties.MODULE_ENABLED,
                            String.valueOf(module.isEnabled())));

            JDomUtil.addNewElement(document, node.getXpath(),
                    OPENCONFIGURATOR_NAMESPACE, newElement);
            JDomUtil.addNewElement(document, interfaceListTagXpath,
                    OPENCONFIGURATOR_NAMESPACE, newObjElement);
            JDomUtil.addNewElement(document, interfaceXpath,
                    OPENCONFIGURATOR_NAMESPACE, moduleObjElement);
            System.err.println("New Element added..........");
        }
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
            System.err.println("Unsupported node ID:" + node.getCnNodeId()
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

    public static void deleteModule(Document document, Module module,
            boolean finalModuleCheck) {
        System.err.println("finalModuleCheck....." + finalModuleCheck);
        if (!finalModuleCheck) {
            JDomUtil.removeElement(document, module.getInterfaceListTagXPath(),
                    OPENCONFIGURATOR_NAMESPACE);
            // JDomUtil.removeElement(document, module.getXpath(),
            // OPENCONFIGURATOR_NAMESPACE);
        } else {
            JDomUtil.removeElement(document, module.getXpath(),
                    OPENCONFIGURATOR_NAMESPACE);
        }
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

    public static void forceActualValue(Document document, Module module,
            PowerlinkObject object, PowerlinkSubobject powerlinkSubobject,
            long newObjectIndex, int newSubObjectIndex) {
        if (powerlinkSubobject != null) {
            System.out.println("Force object actual value:" + object.getIdRaw()
                    + newSubObjectIndex);
        } else {
            System.out
                    .println("Force object actual value:" + object.getIdRaw());
        }
        System.err.println("Is object already forced..."
                + isObjectAlreadyForced(document, module, object,
                        powerlinkSubobject, newObjectIndex, newSubObjectIndex));
        if (isObjectAlreadyForced(document, module, object, powerlinkSubobject,
                newObjectIndex, newSubObjectIndex)) {
            return;
        }

        String forcedTagXpath = module.getXpath() + "/oc:"
                + IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT;
        if (JDomUtil.isXpathPresent(document, forcedTagXpath,
                OPENCONFIGURATOR_NAMESPACE)) {
            Element newObjElement = new Element(
                    IAbstractNodeProperties.NODE_OBJECTS_OBJECT);
            Attribute objAttr = new Attribute(
                    IAbstractNodeProperties.NODE_OBJECTS_INDEX_OBJECT,
                    Long.toHexString(newObjectIndex));
            newObjElement.setAttribute(objAttr);

            if (powerlinkSubobject != null) {
                String subObjectIndex = Integer.toHexString(newSubObjectIndex);
                if (newSubObjectIndex < 16) {
                    subObjectIndex = "0" + subObjectIndex;
                }
                Attribute subObjAttr = new Attribute(
                        IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT,
                        subObjectIndex);
                newObjElement.setAttribute(subObjAttr);
            }

            JDomUtil.addModuleForceObjectElement(document, forcedTagXpath,
                    OPENCONFIGURATOR_NAMESPACE, newObjElement);

        } else {

            Element newObjElement = new Element(
                    IAbstractNodeProperties.NODE_OBJECTS_OBJECT);
            Attribute objAttr = new Attribute(
                    IAbstractNodeProperties.NODE_OBJECTS_INDEX_OBJECT,
                    Long.toHexString(newObjectIndex));
            newObjElement.setAttribute(objAttr);

            if (powerlinkSubobject != null) {
                String subObjectIndex = Integer.toHexString(newSubObjectIndex);
                if (newSubObjectIndex < 16) {
                    subObjectIndex = "0" + subObjectIndex;
                }
                Attribute subObjAttr = new Attribute(
                        IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT,
                        subObjectIndex);
                newObjElement.setAttribute(subObjAttr);
            }

            Element newElement = new Element(
                    IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT);
            newElement.setContent(newObjElement);

            JDomUtil.addModuleForceObjectElement(document, module.getXpath(),
                    OPENCONFIGURATOR_NAMESPACE, newElement);

        }

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
            System.out.println("Force object actual value:" + object.getIdRaw()
                    + subObject.getIdRaw());
        } else {
            System.out
                    .println("Force object actual value:" + object.getIdRaw());
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
                    object.getIdRaw());
            newObjElement.setAttribute(objAttr);

            if (subObject != null) {
                Attribute subObjAttr = new Attribute(
                        IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT,
                        subObject.getIdRaw());
                newObjElement.setAttribute(subObjAttr);
            }
            if (node.isModularheadNode()) {
                int index = getChildIndexbelowNode(document, node);
                index = index - 1;
                JDomUtil.addNewElement(document, forcedTagXpath,
                        OPENCONFIGURATOR_NAMESPACE, newObjElement, index);
            } else {
                JDomUtil.addNewElement(document, forcedTagXpath,
                        OPENCONFIGURATOR_NAMESPACE, newObjElement);
            }
        } else {

            Element newObjElement = new Element(
                    IAbstractNodeProperties.NODE_OBJECTS_OBJECT);
            Attribute objAttr = new Attribute(
                    IAbstractNodeProperties.NODE_OBJECTS_INDEX_OBJECT,
                    object.getIdRaw());
            newObjElement.setAttribute(objAttr);

            if (subObject != null) {
                Attribute subObjAttr = new Attribute(
                        IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT,
                        subObject.getIdRaw());
                newObjElement.setAttribute(subObjAttr);
            }

            Element newElement = new Element(
                    IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT);
            newElement.setContent(newObjElement);
            if (node.isModularheadNode()) {
                int index = getChildIndexbelowNode(document, node);
                index = index - 1;
                JDomUtil.addNewElement(document, node.getXpath(),
                        OPENCONFIGURATOR_NAMESPACE, newElement, index);
            } else {
                JDomUtil.addNewElement(document, node.getXpath(),
                        OPENCONFIGURATOR_NAMESPACE, newElement);
            }
        }
    }

    public static int getChildIndexbelowNode(Document document, Node node) {
        XPathExpression<Element> xpath = JDomUtil.getXPathExpressionElement(
                node.getXpath(), OPENCONFIGURATOR_NAMESPACE);
        List<Element> elementsList = xpath.evaluate(document);
        Element parentElement = elementsList.get(0);
        List<Element> childElement = parentElement.getChildren();
        if (!childElement.isEmpty()) {
            for (int childCount = 1; childCount <= childElement
                    .size(); childCount++) {
                for (Element child : childElement) {
                    int index = parentElement.indexOf(child);
                    if (child.getQualifiedName()
                            .equalsIgnoreCase("InterfaceList")) {
                        return index;
                    }
                }
            }
        } else {
            System.err.println(
                    "No child elements are available for the given Node with ID = "
                            + node.getCnNodeId());
        }
        return 0;
    }

    /**
     * Updates the configuration path of CN in project XML file.
     *
     * @param document Project file instance.
     */
    public static String getParent(Document document) {
        String xpath = "//oc:NetworkConfiguration";
        if (JDomUtil.isXpathPresent(document, xpath,
                OPENCONFIGURATOR_NAMESPACE)) {
            String nodeCollectionXpath = xpath + "/oc:NodeCollection";
            String cnXpath = nodeCollectionXpath + "/oc:CN";//
            if (JDomUtil.isXpathPresent(document, cnXpath,
                    OPENCONFIGURATOR_NAMESPACE)) {
                System.err.println("CN XPath is Present....");
                return JDomUtil.getPathName(document, cnXpath,
                        OPENCONFIGURATOR_NAMESPACE);
            }
        }
        return null;
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
            final Module module, PowerlinkObject object,
            PowerlinkSubobject subObject, long newObjectIndex,
            int newSubObjectIndex) {
        String xpath = module.getXpath() + "/oc:"
                + IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT + "/oc:"
                + IAbstractNodeProperties.NODE_OBJECTS_OBJECT + "[@"
                + IAbstractNodeProperties.NODE_OBJECTS_INDEX_OBJECT + "='"
                + Long.toHexString(newObjectIndex) + "']";
        if (subObject != null) {
            String subObjectIndex = Integer.toHexString(newSubObjectIndex);
            if (newSubObjectIndex < 16) {
                subObjectIndex = "0" + subObjectIndex;
            }
            xpath += "[@" + IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT
                    + "='" + subObjectIndex + "']";
        }

        return JDomUtil.isXpathPresent(document, xpath,
                OPENCONFIGURATOR_NAMESPACE);
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
                + object.getIdRaw() + "']";
        if (subObject != null) {
            xpath += "[@" + IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT
                    + "='" + subObject.getIdRaw() + "']";
        }

        return JDomUtil.isXpathPresent(document, xpath,
                OPENCONFIGURATOR_NAMESPACE);
    }

    public static void removeForcedObject(Document document, Module module,
            PowerlinkObject object, PowerlinkSubobject powerlinkSubobject,
            long newObjectIndex, int newSubObjectIndex) {
        if (powerlinkSubobject != null) {
            System.out.println("removeForcedObject actual value:"
                    + object.getIdRaw() + newSubObjectIndex);
        } else {
            System.out.println(
                    "removeForcedObject actual value:" + object.getIdRaw());
        }

        String xpath = module.getXpath() + "/oc:"
                + IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT + "/oc:"
                + IAbstractNodeProperties.NODE_OBJECTS_OBJECT + "[@"
                + IAbstractNodeProperties.NODE_OBJECTS_INDEX_OBJECT + "='"
                + Long.toHexString(newObjectIndex) + "']";
        if (powerlinkSubobject != null) {
            xpath += "[@" + IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT
                    + "='" + Integer.toHexString(newSubObjectIndex) + "']";
        }

        JDomUtil.removeElement(document, xpath, OPENCONFIGURATOR_NAMESPACE);

        String forcedTagXpath = module.getXpath() + "/oc:"
                + IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT;

        if (JDomUtil.getChildrenCount(document, forcedTagXpath,
                OPENCONFIGURATOR_NAMESPACE) == 0) {
            JDomUtil.removeElement(document, forcedTagXpath,
                    OPENCONFIGURATOR_NAMESPACE);
        }

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
                    + object.getIdRaw() + subObject.getIdRaw());
        } else {
            System.out.println(
                    "removeForcedObject actual value:" + object.getIdRaw());
        }

        String xpath = node.getXpath() + "/oc:"
                + IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT + "/oc:"
                + IAbstractNodeProperties.NODE_OBJECTS_OBJECT + "[@"
                + IAbstractNodeProperties.NODE_OBJECTS_INDEX_OBJECT + "='"
                + object.getIdRaw() + "']";
        if (subObject != null) {
            xpath += "[@" + IAbstractNodeProperties.NODE_OBJECTS_SUBINDEX_OBJECT
                    + "='" + subObject.getIdRaw() + "']";
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

    public static void removeIDEConfigurationSettings(Document document) {
        String xpath = "//oc:IDEConfiguration";
        if (JDomUtil.isXpathPresent(document, xpath,
                OPENCONFIGURATOR_NAMESPACE)) {
            JDomUtil.removeElement(document, xpath, OPENCONFIGURATOR_NAMESPACE);
        } else {
            System.err.println("IDE configuration Xpath not present.");
        }

    }

    /**
     * Update modified values of module in project file.
     *
     * @param document Project file instance
     * @param module Instance of module.
     * @param attributeName Position/Address attribute name
     * @param attributeValue value to be set.
     */
    public static void swapModuleAttributeValue(Document document,
            Module module, String attributeName, String attributeValue) {
        JDomUtil.updateAttribute(document, module.getNameXpath(),
                OPENCONFIGURATOR_NAMESPACE,
                new Attribute(attributeName, attributeValue));
    }

    @SuppressWarnings("unused")
    public static void updateAutoGenerationSettings(Document document) {
        String xpath = "//oc:ProjectConfiguration";
        JDomUtil.updateAttribute(document, xpath, OPENCONFIGURATOR_NAMESPACE,
                new Attribute("activeAutoGenerationSetting", "all"));
        if (JDomUtil.isXpathPresent(document, xpath,
                OPENCONFIGURATOR_NAMESPACE)) {
            String pathSettingsXpath = xpath + "/oc:AutoGenerationSettings";
            if (JDomUtil.isXpathPresent(document, pathSettingsXpath,
                    OPENCONFIGURATOR_NAMESPACE)) {
                JDomUtil.updateAttribute(document, pathSettingsXpath,
                        OPENCONFIGURATOR_NAMESPACE, new Attribute("id", "all"));
                Element noneAutoGenerateElement = new Element(
                        "AutoGenerationSettings");
                Attribute objAttr = new Attribute("id", "none");
                noneAutoGenerateElement.setAttribute(objAttr);

                Element customAutoGenerateElement = new Element(
                        "AutoGenerationSettings");
                Attribute customobjAttr = new Attribute("id", "custom");
                customAutoGenerateElement.setAttribute(customobjAttr);
                String noneXPath = xpath
                        + "/oc:AutoGenerationSettings[@id='none']";
                String customXPath = xpath
                        + "/oc:AutoGenerationSettings[@id='custom']";
                if (!JDomUtil.isXpathPresent(document, noneXPath,
                        OPENCONFIGURATOR_NAMESPACE)) {
                    JDomUtil.addNewElement(document, xpath,
                            OPENCONFIGURATOR_NAMESPACE,
                            noneAutoGenerateElement);
                }

            } else {
                System.err.println("AutoGeneraton setttings Xpath not present");
            }
        } else {
            System.err.println("Project Configuration Xpath not present.");
        }

    }

    /**
     * Updates the configuration path of CN in project XML file.
     *
     * @param document Project file instance.
     */
    public static void updateCNPathTOXDC(Document document) {
        String xpath = "//oc:NetworkConfiguration";
        if (JDomUtil.isXpathPresent(document, xpath,
                OPENCONFIGURATOR_NAMESPACE)) {
            String nodeCollectionXpath = xpath + "/oc:NodeCollection";
            String cnXpath = nodeCollectionXpath + "/oc:CN";//
            if (JDomUtil.isXpathPresent(document, cnXpath,
                    OPENCONFIGURATOR_NAMESPACE)) {
                System.err.println("CN XPath is Present....");
                JDomUtil.updateXDCAttribute(document, cnXpath,
                        OPENCONFIGURATOR_NAMESPACE);
            } else {
                System.err.println("The Cn Xpath not present..");
            }
        }
    }

    /**
     * Update modified time values in project file.
     *
     * @param document Project file instance.
     * @param attributeName Generator attribute name.
     * @param attributeValue value to be set.
     */
    public static void updateGeneratorAttribute(Document document,
            final String attributeName, final String attributeValue) {

        JDomUtil.updateAttribute(document, GENERATOR_XPATH_EXPR,
                new Attribute(attributeName, attributeValue));
    }

    /**
     * Update the configuration file path of MN node
     *
     * @param document Project file instance.
     */
    public static void updateMNPathTOXDC(Document document) {
        String xpath = "//oc:NetworkConfiguration";
        if (JDomUtil.isXpathPresent(document, xpath,
                OPENCONFIGURATOR_NAMESPACE)) {
            String nodeCollectionXpath = xpath + "/oc:NodeCollection";
            String cnXpath = nodeCollectionXpath + "/oc:MN";//
            if (JDomUtil.isXpathPresent(document, cnXpath,
                    OPENCONFIGURATOR_NAMESPACE)) {
                JDomUtil.updateXDCAttribute(document, cnXpath,
                        OPENCONFIGURATOR_NAMESPACE);
            } else {
                System.err.println("The Cn Xpath not present..");
            }
        }
    }

    /**
     * Updates the attribute value of Module
     *
     * @param document Project file instance
     * @param module Instance of Module
     * @param attributeName Name of attribute to be updated.
     * @param attributeValue Value for the attribute update.
     */
    public static void updateModuleAttributeValue(Document document,
            Module module, String attributeName, String attributeValue) {
        JDomUtil.updateAttribute(document, module.getXpath(),
                OPENCONFIGURATOR_NAMESPACE,
                new Attribute(attributeName, attributeValue));
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

    public static void updateOutputPath(Document document) {
        String xpath = "//oc:ProjectConfiguration";
        if (JDomUtil.isXpathPresent(document, xpath,
                OPENCONFIGURATOR_NAMESPACE)) {
            String pathSettingsXpath = xpath + "/oc:PathSettings";
            if (JDomUtil.isXpathPresent(document, pathSettingsXpath,
                    OPENCONFIGURATOR_NAMESPACE)) {
                String pathXPath = pathSettingsXpath + "/oc:Path";
                if (JDomUtil.isXpathPresent(document, pathXPath,
                        OPENCONFIGURATOR_NAMESPACE)) {
                    JDomUtil.updateAttribute(document, pathXPath,
                            OPENCONFIGURATOR_NAMESPACE,
                            new Attribute("path", "output"));
                } else {
                    System.err.println("PAth  Xpath not present");
                }
            } else {
                System.err.println("PAth setttings Xpath not present");
            }
        } else {
            System.err.println("Project Configuration Xpath not present.");
        }
    }

    /**
     * Updates the configuration file of RMN in project XML file
     *
     * @param document Project file instance.
     */
    public static void updateRMNPathTOXDC(Document document) {
        String xpath = "//oc:NetworkConfiguration";
        if (JDomUtil.isXpathPresent(document, xpath,
                OPENCONFIGURATOR_NAMESPACE)) {
            String nodeCollectionXpath = xpath + "/oc:NodeCollection";
            String cnXpath = nodeCollectionXpath + "/oc:RMN";//
            if (JDomUtil.isXpathPresent(document, cnXpath,
                    OPENCONFIGURATOR_NAMESPACE)) {
                System.err.println("CN XPath is Present....");
                JDomUtil.updateXDCAttribute(document, cnXpath,
                        OPENCONFIGURATOR_NAMESPACE);
            } else {
                System.err.println("The Cn Xpath not present..");
            }
        }
    }

}
