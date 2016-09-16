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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.LabelDescription;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.Parameter;
import org.epsg.openconfigurator.model.ParameterReference;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.xmlbinding.xdd.TAllowedValues;
import org.epsg.openconfigurator.xmlbinding.xdd.TDataTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterGroup;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterGroup.ParameterRef;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterTemplate;
import org.epsg.openconfigurator.xmlbinding.xdd.TRange;
import org.epsg.openconfigurator.xmlbinding.xdd.TValue;
import org.epsg.openconfigurator.xmlbinding.xdd.TVarDeclaration;
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
    public static final String CONNECTED_MODULE_LIST = "connectedModuleList";
    public static final String CONNECTED_MODULE = "connectedModule";
    public static final String CHILD_ID = "childIDRef";
    public static final String POSITION = "position";
    public static final String ADDRESS = "address";
    public static final String APPLICATION_PROCESS = "ApplicationProcess";
    public static final String TEMPLATE_LIST = "templateList";
    public static final String PARAMETER_LIST = "parameterList";
    public static final String DATATYPE_LIST = "dataTypeList";
    public static final String PARAMETER_GROUP_LIST = "parameterGroupList";
    public static final String PARAMETER_GROUP = "parameterGroup";
    public static final String PARAMETER_REFERENCE = "parameterRef";
    public static final String PARAMETER_DEFAULT_VALUE = "defaultValue";
    public static final String PARAMETER_SUBSTITUTE_VALUE = "substituteValue";
    public static final String PARAMETER_ALLOWED_VALUE = "allowedValues";
    public static final String PARAMETER_UNIT_ELEMENT = "unit";
    public static final String PARAMETER_PROPERTY_ELEMENT = "property";
    private static final String FILE_MODIFICATION_TIME = "fileModificationTime";
    private static final String FILE_MODIFICATION_DATE = "fileModificationDate";
    private static final String FILE_MODIFIED_BY = "fileModifiedBy";
    private static final String APPLICATION_PROCESS_XPATH = "//plk:ProfileBody"
            + "/plk:ApplicationProcess";

    private static final String FILE_MODIFICATION_TIME_XPATH = "//plk:ProfileBody[@"
            + FILE_MODIFICATION_TIME + "]";
    private static final String FILE_MODIFICATION_DATE_XPATH = "//plk:ProfileBody[@"
            + FILE_MODIFICATION_DATE + "]";
    private static final String FILE_MODIFIED_BY_XPATH = "//plk:ProfileBody[@"
            + FILE_MODIFIED_BY + "]";
    private static final String INTERFACE_LIST_XPATH = "//plk:DeviceManager"
            + "/plk:moduleManagement" + "/plk:interfaceList";

    private static final String PARAMETER_LIST_XPATH = APPLICATION_PROCESS_XPATH
            + "/plk:parameterList";
    private static final String TEMPLATE_LIST_XPATH = APPLICATION_PROCESS_XPATH
            + "/plk:templateList";
    private static final String PARAMETER_GROUP_LIST_XPATH = APPLICATION_PROCESS_XPATH
            + "/plk:parameterGroupList";

    static {
        POWERLINK_XDD_NAMESPACE = Namespace.getNamespace("plk",
                "http://www.ethernet-powerlink.org");
    }

    /**
     * Update connected modules list in the head node XDC.
     *
     * @param document Document instance to be updated.
     * @param headNodeInterface Instance of head node interfaces.
     * @param moduleCollection Collection of modules connected to head node.
     */
    public static void addConnectedModules(Document document,
            HeadNodeInterface headNodeInterface,
            Map<Integer, Module> moduleCollection) {
        String uniqueID = headNodeInterface.getInterfaceUniqueId();
        String interfaceXpath = INTERFACE_LIST_XPATH
                + "/plk:interface[@uniqueID='" + uniqueID + "']";
        String connectedModuleXPath = interfaceXpath
                + "/plk:connectedModuleList";
        if (JDomUtil.isXpathPresent(document, connectedModuleXPath,
                POWERLINK_XDD_NAMESPACE)) {
            Collection<Module> moduleList = moduleCollection.values();
            for (Module module : moduleList) {
                String childID = module.getChildID();
                String position = String.valueOf(module.getPosition());
                String address = String.valueOf(module.getAddress());
                String connectedModulesXPath = connectedModuleXPath
                        + "/plk:connectedModule[@childIDRef='" + childID + "']";
                if (JDomUtil.isXpathPresent(document, connectedModulesXPath,
                        POWERLINK_XDD_NAMESPACE)) {
                    Attribute positionAttribute = new Attribute(POSITION,
                            position);
                    JDomUtil.updateAttribute(document, connectedModulesXPath,
                            POWERLINK_XDD_NAMESPACE, positionAttribute);
                    Attribute addressAttribute = new Attribute(ADDRESS,
                            address);
                    JDomUtil.updateAttribute(document, connectedModulesXPath,
                            POWERLINK_XDD_NAMESPACE, addressAttribute);
                } else {
                    Element newModuleObjElement = new Element(CONNECTED_MODULE);
                    List<Attribute> attribList = newModuleObjElement
                            .getAttributes();
                    attribList.add(new Attribute(CHILD_ID, childID));
                    attribList.add(new Attribute(POSITION, position));
                    attribList.add(new Attribute(ADDRESS, address));

                    JDomUtil.addNewElement(document, connectedModuleXPath,
                            POWERLINK_XDD_NAMESPACE, newModuleObjElement);
                }
            }
        } else {
            if (JDomUtil.isXpathPresent(document, interfaceXpath,
                    POWERLINK_XDD_NAMESPACE)) {
                Element newObjElement = new Element(CONNECTED_MODULE_LIST);
                JDomUtil.addNewElement(document, interfaceXpath,
                        POWERLINK_XDD_NAMESPACE, newObjElement);
                Collection<Module> moduleList = moduleCollection.values();
                for (Module module : moduleList) {
                    String childID = module.getChildID();
                    String position = String.valueOf(module.getPosition());
                    String address = String.valueOf(module.getAddress());
                    Element newModuleObjElement = new Element(CONNECTED_MODULE);
                    List<Attribute> attribList = newModuleObjElement
                            .getAttributes();
                    attribList.add(new Attribute(CHILD_ID, childID));
                    attribList.add(new Attribute(POSITION, position));
                    attribList.add(new Attribute(ADDRESS, address));

                    JDomUtil.addNewElement(document, connectedModuleXPath,
                            POWERLINK_XDD_NAMESPACE, newModuleObjElement);
                }

            }
        }

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

    public static void deleteConnectedModules(Document document, Module module,
            boolean finalModuleCheck) {
        String uniqueID = module.getInterfaceOfModule().getInterfaceUniqueId();
        String interfaceXpath = INTERFACE_LIST_XPATH
                + "/plk:interface[@uniqueID='" + uniqueID + "']";
        String connectedModuleXPath = interfaceXpath
                + "/plk:connectedModuleList";
        String connectedModulesXPath = connectedModuleXPath
                + "/plk:connectedModule[@childIDRef='" + module.getChildID()
                + "']";
        if (finalModuleCheck) {
            JDomUtil.removeElement(document, connectedModulesXPath,
                    POWERLINK_XDD_NAMESPACE);
        } else {
            JDomUtil.removeElement(document, connectedModuleXPath,
                    POWERLINK_XDD_NAMESPACE);
        }

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
                    if (child.getQualifiedName()
                            .equalsIgnoreCase(PARAMETER_DEFAULT_VALUE)) {
                        return index;
                    } else if (child.getQualifiedName()
                            .equalsIgnoreCase(PARAMETER_SUBSTITUTE_VALUE)) {
                        return index;
                    } else if (child.getQualifiedName()
                            .equalsIgnoreCase(PARAMETER_ALLOWED_VALUE)) {
                        return index;
                    } else if (child.getQualifiedName()
                            .equalsIgnoreCase(PARAMETER_UNIT_ELEMENT)) {
                        return index;
                    } else if (child.getQualifiedName()
                            .equalsIgnoreCase(PARAMETER_PROPERTY_ELEMENT)) {
                        return index;
                    }
                }
            }
        } else {
            System.err.println(
                    "No child elements are available for the given parameter with uniqueID = "
                            + parameter.getParameterUniqueId());
        }
        return 0;
    }

    private static String getIEC_DataType(TParameterList.Parameter parameter) {
        // TODO: Provide support for datatypeID ref also.

        // parameter.getDataTypeIDRef()
        if (parameter.getBITSTRING() != null) {
            return "BITSTRING";
        } else if (parameter.getBOOL() != null) {
            return "BOOL";
        } else if (parameter.getBYTE() != null) {
            return "BYTE";
        } else if (parameter.getCHAR() != null) {
            return "CHAR";
        } else if (parameter.getDINT() != null) {
            return "DINT";
        } else if (parameter.getDWORD() != null) {
            return "DWORD";
        } else if (parameter.getINT() != null) {
            return "INT";
        } else if (parameter.getLINT() != null) {
            return "LINT";
        } else if (parameter.getLREAL() != null) {
            return "LREAL";
        } else if (parameter.getLWORD() != null) {
            return "LWORD";
        } else if (parameter.getREAL() != null) {
            return "REAL";
        } else if (parameter.getSINT() != null) {
            return "SINT";
        } else if (parameter.getSTRING() != null) {
            return "STRING";
        } else if (parameter.getUDINT() != null) {
            return "UDINT";
        } else if (parameter.getUINT() != null) {
            return "UINT";
        } else if (parameter.getULINT() != null) {
            return "ULINT";
        } else if (parameter.getUSINT() != null) {
            return "USINT";
        } else if (parameter.getWORD() != null) {
            return "WORD";
        } else if (parameter.getWSTRING() != null) {
            return "WSTRING";
        } else {
            System.err.println("Parameter Un handled IEC_Datatype value");
            if (parameter.getDataTypeIDRef() != null) {
                System.err.println(parameter.getDataTypeIDRef());
            } else {
                System.err.println("No uniqueIDRef");
            }
        }

        return "UNDEFINED";
    }

    private static String getIEC_DataType(
            TParameterTemplate parameterTemplate) {
        // TODO: Provide support for datatypeID ref also.

        // parameter.getDataTypeIDRef()
        if (parameterTemplate.getBITSTRING() != null) {
            return "BITSTRING";
        } else if (parameterTemplate.getBOOL() != null) {
            return "BOOL";
        } else if (parameterTemplate.getBYTE() != null) {
            return "BYTE";
        } else if (parameterTemplate.getCHAR() != null) {
            return "CHAR";
        } else if (parameterTemplate.getDINT() != null) {
            return "DINT";
        } else if (parameterTemplate.getDWORD() != null) {
            return "DWORD";
        } else if (parameterTemplate.getINT() != null) {
            return "INT";
        } else if (parameterTemplate.getLINT() != null) {
            return "LINT";
        } else if (parameterTemplate.getLREAL() != null) {
            return "LREAL";
        } else if (parameterTemplate.getLWORD() != null) {
            return "LWORD";
        } else if (parameterTemplate.getREAL() != null) {
            return "REAL";
        } else if (parameterTemplate.getSINT() != null) {
            return "SINT";
        } else if (parameterTemplate.getSTRING() != null) {
            return "STRING";
        } else if (parameterTemplate.getUDINT() != null) {
            return "UDINT";
        } else if (parameterTemplate.getUINT() != null) {
            return "UINT";
        } else if (parameterTemplate.getULINT() != null) {
            return "ULINT";
        } else if (parameterTemplate.getUSINT() != null) {
            return "USINT";
        } else if (parameterTemplate.getWORD() != null) {
            return "WORD";
        } else if (parameterTemplate.getWSTRING() != null) {
            return "WSTRING";
        } else {
            System.err.println("Parameter Un handled IEC_Datatype value");
            if (parameterTemplate.getDataTypeIDRef() != null) {
                System.err.println(parameterTemplate.getDataTypeIDRef());
            } else {
                System.err.println("No uniqueIDRef");
            }
        }

        return "UNDEFINED";
    }

    private static String getIEC_DataType(TVarDeclaration parameter) {
        // TODO: Provide support for datatypeID ref also.

        if (parameter.getBITSTRING() != null) {
            return "BITSTRING";
        } else if (parameter.getBOOL() != null) {
            return "BOOL";
        } else if (parameter.getBYTE() != null) {
            return "BYTE";
        } else if (parameter.getCHAR() != null) {
            return "CHAR";
        } else if (parameter.getDINT() != null) {
            return "DINT";
        } else if (parameter.getDWORD() != null) {
            return "DWORD";
        } else if (parameter.getINT() != null) {
            return "INT";
        } else if (parameter.getLINT() != null) {
            return "LINT";
        } else if (parameter.getLREAL() != null) {
            return "LREAL";
        } else if (parameter.getLWORD() != null) {
            return "LWORD";
        } else if (parameter.getREAL() != null) {
            return "REAL";
        } else if (parameter.getSINT() != null) {
            return "SINT";
        } else if (parameter.getSTRING() != null) {
            return "STRING";
        } else if (parameter.getUDINT() != null) {
            return "UDINT";
        } else if (parameter.getUINT() != null) {
            return "UINT";
        } else if (parameter.getULINT() != null) {
            return "ULINT";
        } else if (parameter.getUSINT() != null) {
            return "USINT";
        } else if (parameter.getWORD() != null) {
            return "WORD";
        } else if (parameter.getWSTRING() != null) {
            return "WSTRING";
        } else {
            System.err.println("Parameter Un handled IEC_Datatype value");
            if (parameter.getDataTypeIDRef() != null) {
                System.err.println(parameter.getDataTypeIDRef());
            } else {
                System.err.println("No uniqueIDRef");
            }
        }

        return "UNDEFINED";
    }

    private static void removeNodeApplicationProcess(Document document) {
        String paramDataTypeXpath = APPLICATION_PROCESS_XPATH + "/plk:"
                + DATATYPE_LIST + "/plk:struct";
        String paramGrpXpath = PARAMETER_GROUP_LIST_XPATH
                + "/plk:parameterGroup";
        String paramtempXpath = TEMPLATE_LIST_XPATH + "/plk:parameterTemplate";
        String paramXpath = PARAMETER_LIST_XPATH + "/plk:parameter";

        if (!JDomUtil.isXpathPresent(document, paramDataTypeXpath,
                POWERLINK_XDD_NAMESPACE)) {
            JDomUtil.removeElement(document,
                    APPLICATION_PROCESS_XPATH + "/plk:" + DATATYPE_LIST,
                    POWERLINK_XDD_NAMESPACE);
        }

        if (!JDomUtil.isXpathPresent(document, paramGrpXpath,
                POWERLINK_XDD_NAMESPACE)) {
            JDomUtil.removeElement(document, PARAMETER_GROUP_LIST_XPATH,
                    POWERLINK_XDD_NAMESPACE);
        }

        if (!JDomUtil.isXpathPresent(document, paramtempXpath,
                POWERLINK_XDD_NAMESPACE)) {
            JDomUtil.removeElement(document, TEMPLATE_LIST_XPATH,
                    POWERLINK_XDD_NAMESPACE);
        }

        if (!JDomUtil.isXpathPresent(document, paramXpath,
                POWERLINK_XDD_NAMESPACE)) {
            JDomUtil.removeElement(document, PARAMETER_LIST_XPATH,
                    POWERLINK_XDD_NAMESPACE);
        }
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
        System.err
                .println("Sub_Object xpath for node.." + subobject.getXpath());
        JDomUtil.setAttribute(document, subobject.getXpath(),
                POWERLINK_XDD_NAMESPACE, newAttribute);
    }

    private static void updateApplicationProcess(Document document) {

        String xpath = "//plk:ProfileBody[@*[local-name()='type']='ProfileBody_Device_Powerlink_Modular_Head']";
        String applicationProcessXpath = xpath + "/plk:ApplicationProcess";

        if (JDomUtil.isXpathPresent(document, applicationProcessXpath,
                POWERLINK_XDD_NAMESPACE)) {
            System.err.println("prfileBody Xpath present....");
            Element newdataTypeElement = new Element(DATATYPE_LIST);
            JDomUtil.addNewElement(document, APPLICATION_PROCESS_XPATH,
                    POWERLINK_XDD_NAMESPACE, newdataTypeElement);

            Element newTempElement = new Element(TEMPLATE_LIST);
            JDomUtil.addNewElement(document, APPLICATION_PROCESS_XPATH,
                    POWERLINK_XDD_NAMESPACE, newTempElement);

            Element newParamElement = new Element(PARAMETER_LIST);
            JDomUtil.addNewElement(document, APPLICATION_PROCESS_XPATH,
                    POWERLINK_XDD_NAMESPACE, newParamElement);

            Element newParamGroupElement = new Element(PARAMETER_GROUP_LIST);
            JDomUtil.addNewElement(document, APPLICATION_PROCESS_XPATH,
                    POWERLINK_XDD_NAMESPACE, newParamGroupElement);

        } else {
            Element newObjElement = new Element(APPLICATION_PROCESS);
            JDomUtil.addNewElement(document, xpath, POWERLINK_XDD_NAMESPACE,
                    newObjElement);
            Element newdataTypeElement = new Element(DATATYPE_LIST);
            JDomUtil.addNewElement(document, APPLICATION_PROCESS_XPATH,
                    POWERLINK_XDD_NAMESPACE, newdataTypeElement);

            Element newTempElement = new Element(TEMPLATE_LIST);
            JDomUtil.addNewElement(document, APPLICATION_PROCESS_XPATH,
                    POWERLINK_XDD_NAMESPACE, newTempElement);

            Element newParamElement = new Element(PARAMETER_LIST);
            JDomUtil.addNewElement(document, APPLICATION_PROCESS_XPATH,
                    POWERLINK_XDD_NAMESPACE, newParamElement);

            Element newParamGroupElement = new Element(PARAMETER_GROUP_LIST);
            JDomUtil.addNewElement(document, APPLICATION_PROCESS_XPATH,
                    POWERLINK_XDD_NAMESPACE, newParamGroupElement);

        }

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
     * Update the XDC of head node with respect to connected child modules.
     *
     * @param document XDD/XDC file Instance
     * @param module Instance of Module.
     * @param plkSubObj Instance of POWERLINK sub-Object.
     * @param plkObj Instance of POWERLINK object.
     * @param node Instance of NODe.
     * @param moduleObjectIndex Value of module object index.
     * @param moduleSubObjectIndex Value of module sub-object index.
     */
    public static void updateHeadNodeXDC(Document document, Module module,
            PowerlinkSubobject plkSubObj, PowerlinkObject plkObj, Node node,
            long moduleObjectIndex, int moduleSubObjectIndex) {
        String objectXpath = "//plk:Object[@index='"
                + Long.toHexString(moduleObjectIndex) + "']";
        String uniqueIdRefXPath = objectXpath + "/plk:SubObject[@uniqueIDRef='"
                + plkSubObj.getUniqueIDRef() + "']";
        if (JDomUtil.isXpathPresent(document, uniqueIdRefXPath,
                POWERLINK_XDD_NAMESPACE)) {
            String mappingXpath = objectXpath + "/plk:SubObject[@PDOmapping='"
                    + "']";
            if (JDomUtil.isXpathPresent(document, mappingXpath,
                    POWERLINK_XDD_NAMESPACE)) {
                System.out.println("Xpath of mapping parameter present");
            } else {
                System.out.println("Xpath of mapping parameter not present");
            }
        }

    }

    /**
     * Update the index of object to the module from the library.
     *
     * @param document XDD/XDC file instance.
     * @param module Instance of Module
     * @param moduleObjectIndex Value of module object.
     * @param object Instance of POWERLINK object.
     */
    public static void updateModuleObjectIndex(Document document, Module module,
            long moduleObjectIndex, PowerlinkObject object) {
        String Xpath = "//plk:Object[@name='" + object.getName() + "']";
        if (JDomUtil.isXpathPresent(document, Xpath, POWERLINK_XDD_NAMESPACE)) {
            Attribute newAttribute = new Attribute("index",
                    Long.toHexString(moduleObjectIndex));
            JDomUtil.setAttribute(document, Xpath, POWERLINK_XDD_NAMESPACE,
                    newAttribute);
        }

    }

    /**
     * Update the head node XDC with the module object list and application
     * process.
     *
     * @param document XDD/XDC file instance.
     * @param node Instance of Node.
     */
    public static void updateModuleObjectInNode(Document document, Node node) {

        if (node.isModularheadNode()) {
            List<HeadNodeInterface> interfaceList = node.getHeadNodeInterface();
            for (HeadNodeInterface interfaces : interfaceList) {
                Collection<Module> moduleList = interfaces.getModuleCollection()
                        .values();
                for (Module module : moduleList) {

                    List<PowerlinkObject> plkObjList = module
                            .getObjectDictionary().getObjectsList();

                    for (PowerlinkObject plkObj : plkObjList) {

                        long moduleObjectIndex = OpenConfiguratorLibraryUtils
                                .getModuleObjectsIndex(plkObj.getModule(),
                                        plkObj.getId());

                        String Xpath = "//plk:ObjectList";
                        String objectXpath = "//plk:Object[@index='"
                                + Long.toHexString(moduleObjectIndex) + "']";
                        if (JDomUtil.isXpathPresent(document, objectXpath,
                                POWERLINK_XDD_NAMESPACE)) {
                            System.err.println("The Object index present..");
                        } else {
                            Element newObjElement = new Element("Object");
                            List<Attribute> attribList = newObjElement
                                    .getAttributes();
                            System.err.println(
                                    "The object Xpath while generating the XDC are ..."
                                            + objectXpath);
                            attribList.add(new Attribute("index",
                                    Long.toHexString(moduleObjectIndex)));
                            attribList.add(
                                    new Attribute("name", plkObj.getName()));
                            if (plkObj.getObjectType() != 0) {
                                attribList.add(new Attribute("objectType",
                                        String.valueOf(
                                                plkObj.getObjectType())));
                            }
                            if (!(plkObj.getDataTypeReadable().isEmpty())) {
                                attribList.add(new Attribute("dataType",
                                        plkObj.getDataTypeReadable()));
                            }
                            if (!(plkObj.getAccessTypeReadable().isEmpty())) {
                                attribList.add(new Attribute("accessType",
                                        plkObj.getAccessTypeReadable()));
                            }
                            if (!(plkObj.getDefaultValue().isEmpty())) {
                                attribList.add(new Attribute("defaultValue",
                                        plkObj.getDefaultValue()));
                            }

                            if (!(plkObj.getActualValue().isEmpty())) {
                                attribList.add(new Attribute("actualValue",
                                        plkObj.getActualValue()));
                            }

                            if (!(plkObj.getHighLimit().isEmpty())) {
                                attribList.add(new Attribute("highLimit",
                                        plkObj.getHighLimit()));
                            }
                            if (!(plkObj.getLowLimit().isEmpty())) {
                                attribList.add(new Attribute("lowLimit",
                                        plkObj.getLowLimit()));
                            }
                            if (!(plkObj.getPDOMappingReadable().isEmpty())) {
                                attribList.add(new Attribute("PDOmapping",
                                        plkObj.getPDOMappingReadable()));
                            }

                            if (plkObj.getUniqueIDRef() != null) {
                                if (!(plkObj.getUniqueIDRef()
                                        .equals(StringUtils.EMPTY))) {

                                    attribList.add(new Attribute("uniqueIDRef",
                                            plkObj.getUniqueId(
                                                    plkObj.getUniqueIDRef())));
                                }
                            }

                            JDomUtil.addNewElement(document, Xpath,
                                    POWERLINK_XDD_NAMESPACE, newObjElement);
                            if (plkObj.getUniqueIDRef() != null) {

                                String uniqueIdRefXPath = "//plk:Object[@uniqueIDRef='"
                                        + plkObj.getUniqueId(
                                                plkObj.getUniqueIDRef())
                                        + "']";
                                if (JDomUtil.isXpathPresent(document,
                                        uniqueIdRefXPath,
                                        POWERLINK_XDD_NAMESPACE)) {
                                    String mappingXpath = "//plk:Object[@PDOmapping='"
                                            + plkObj.getPDOMappingReadable()
                                            + "']";
                                    if (JDomUtil.isXpathPresent(document,
                                            mappingXpath,
                                            POWERLINK_XDD_NAMESPACE)) {
                                        System.err.println(
                                                "Xpath of mapping parameter present");
                                        if (JDomUtil.isXpathPresent(document,
                                                APPLICATION_PROCESS_XPATH,
                                                POWERLINK_XDD_NAMESPACE)) {
                                            System.err.println(
                                                    "The Xpath Already available.....");
                                        } else {
                                            updateApplicationProcess(document);
                                            System.err.println(
                                                    "The Xpath Already  not available.....");

                                        }
                                        updateNodeApplicationProcessofObject(
                                                document, module, plkObj);

                                        updateObjectUniqueIdRef(document,
                                                module, uniqueIdRefXPath,
                                                plkObj.getUniqueIDRef());
                                    } else {

                                        String removeSubObjectXpath = "//plk:ObjectList"
                                                + "/plk:Object[@index='"
                                                + Long.toHexString(
                                                        moduleObjectIndex)
                                                + "']";

                                        JDomUtil.removeAttributes(document,
                                                removeSubObjectXpath,
                                                POWERLINK_XDD_NAMESPACE,
                                                "uniqueIDRef");

                                        String actualValue = plkObj
                                                .getActualValue(plkObj
                                                        .getUniqueIDRef());
                                        if ((actualValue == null)
                                                || (actualValue
                                                        .equalsIgnoreCase(
                                                                StringUtils.EMPTY))) {
                                            System.err.println(
                                                    "Actual Value Empty...");
                                        } else {
                                            Attribute newAttribute = new Attribute(
                                                    OBJECT_ACTUAL_VALUE,
                                                    actualValue);
                                            JDomUtil.setAttribute(document,
                                                    objectXpath,
                                                    POWERLINK_XDD_NAMESPACE,
                                                    newAttribute);
                                        }
                                    }
                                }

                            }

                        }

                        List<PowerlinkSubobject> plkSubObjList = plkObj
                                .getSubObjects();
                        for (PowerlinkSubobject plkSubObj : plkSubObjList) {
                            HashMap<Long, Integer> subObjectEntries = new HashMap<>();
                            int moduleSubObjectIndex = OpenConfiguratorLibraryUtils
                                    .getModuleObjectsSubIndex(
                                            plkObj.getModule(), plkSubObj,
                                            plkSubObj.getObject().getId());

                            String xpath = "//plk:Object[@index='"
                                    + Long.toHexString(moduleObjectIndex)
                                    + "']";
                            String subobjectXpath = xpath
                                    + "/plk:SubObject[@subIndex='"
                                    + Long.toHexString(moduleSubObjectIndex)
                                            .toUpperCase()
                                    + "']";

                            String numberOfEntriesXpath = xpath
                                    + "/plk:SubObject[@subIndex='" + "00"
                                    + "']";

                            if (JDomUtil.isXpathPresent(document,
                                    numberOfEntriesXpath,
                                    POWERLINK_XDD_NAMESPACE)) {
                                System.out
                                        .println("Number of entries available");
                            } else {
                                Element newSubObjElement = new Element(
                                        "SubObject");
                                List<Attribute> attribList = newSubObjElement
                                        .getAttributes();

                                attribList.add(new Attribute("subIndex", "00"));

                                attribList.add(new Attribute("name",
                                        "NumberOfEntries"));

                                attribList
                                        .add(new Attribute("objectType", "7"));

                                attribList
                                        .add(new Attribute("dataType", "0005"));

                                attribList.add(
                                        new Attribute("accessType", "const"));

                                attribList.add(new Attribute("defaultValue",
                                        String.valueOf(plkSubObjList.size())));

                                attribList
                                        .add(new Attribute("PDOmapping", "no"));

                                JDomUtil.addNewElement(document, xpath,
                                        POWERLINK_XDD_NAMESPACE,
                                        newSubObjElement);
                            }
                            int subIndex = moduleSubObjectIndex;
                            String subobjectXpathCheck = StringUtils.EMPTY;

                            if (subIndex <= 15) {
                                subobjectXpathCheck = xpath
                                        + "/plk:SubObject[@subIndex='0"
                                        + subIndex + "']";
                                if (subObjectEntries.containsValue(
                                        (int) moduleObjectIndex)) {
                                    subObjectEntries.put(moduleObjectIndex,
                                            subIndex);
                                }

                            } else {
                                subobjectXpathCheck = xpath
                                        + "/plk:SubObject[@subIndex='"
                                        + subIndex + "']";
                                if (subObjectEntries.containsValue(
                                        (int) moduleObjectIndex)) {
                                    subObjectEntries.put(moduleObjectIndex,
                                            subIndex);
                                }
                            }

                            if (JDomUtil.isXpathPresent(document,
                                    subobjectXpathCheck,
                                    POWERLINK_XDD_NAMESPACE)) {
                                System.out.println(
                                        "Sub-object index already available.");
                            } else if (JDomUtil.isXpathPresent(document, xpath,
                                    POWERLINK_XDD_NAMESPACE)) {
                                Element newSubObjElement = new Element(
                                        "SubObject");
                                List<Attribute> attribList = newSubObjElement
                                        .getAttributes();

                                if (subIndex <= 15) {
                                    attribList.add(new Attribute("subIndex",
                                            "0" + Integer
                                                    .toHexString(
                                                            moduleSubObjectIndex)
                                                    .toUpperCase()));
                                } else {
                                    attribList.add(new Attribute("subIndex",
                                            Integer.toHexString(
                                                    moduleSubObjectIndex)
                                                    .toUpperCase()));
                                }
                                attribList.add(new Attribute("name",
                                        plkSubObj.getName()));
                                if (plkSubObj.getObjectType() != 0) {
                                    attribList.add(new Attribute("objectType",
                                            String.valueOf(plkSubObj
                                                    .getObjectType())));
                                }
                                if (plkSubObj.getDataType() != null) {
                                    attribList.add(new Attribute("dataType",
                                            DatatypeConverter.printHexBinary(
                                                    plkSubObj.getDataType())));
                                }
                                if (!(plkSubObj.getAccessTypeReadable()
                                        .isEmpty())) {
                                    attribList.add(new Attribute("accessType",
                                            plkSubObj.getAccessTypeReadable()));
                                }
                                if (!(plkSubObj.getDefaultValue().isEmpty())) {
                                    attribList.add(new Attribute("defaultValue",
                                            plkSubObj.getDefaultValue()));
                                }

                                if (!(plkSubObj.getActualValue().isEmpty())) {
                                    attribList.add(new Attribute("actualValue",
                                            plkSubObj.getActualValue()));
                                }

                                if (!(plkSubObj.getHighLimit().isEmpty())) {
                                    attribList.add(new Attribute("highLimit",
                                            plkSubObj.getHighLimit()));
                                }
                                if (!(plkSubObj.getLowLimit().isEmpty())) {
                                    attribList.add(new Attribute("lowLimit",
                                            plkSubObj.getLowLimit()));
                                }

                                if (!(plkSubObj.getPDOMappingReadable()
                                        .isEmpty())) {
                                    attribList.add(new Attribute("PDOmapping",
                                            plkSubObj.getPDOMappingReadable()));
                                }

                                if (plkSubObj.getUniqueIDRef() != null) {
                                    attribList.add(new Attribute("uniqueIDRef",
                                            plkSubObj.getUniqueId(plkSubObj
                                                    .getUniqueIDRef())));
                                }

                                JDomUtil.addNewElement(document, xpath,
                                        POWERLINK_XDD_NAMESPACE,
                                        newSubObjElement);
                            } else {
                                System.err.println(
                                        "Sub-ObjectList Xpath not found in head node XDC file.");
                            }
                            if (plkSubObj.getUniqueIDRef() != null) {

                                String uniqueIdRefXPath = xpath
                                        + "/plk:SubObject[@uniqueIDRef='"
                                        + plkSubObj.getUniqueId(
                                                plkSubObj.getUniqueIDRef())
                                        + "']";
                                if (JDomUtil.isXpathPresent(document,
                                        uniqueIdRefXPath,
                                        POWERLINK_XDD_NAMESPACE)) {
                                    String mappingXpath = objectXpath
                                            + "/plk:SubObject[@PDOmapping='"
                                            + plkSubObj.getPDOMappingReadable()
                                            + "']";
                                    if (JDomUtil.isXpathPresent(document,
                                            mappingXpath,
                                            POWERLINK_XDD_NAMESPACE)) {
                                        System.err.println(
                                                "Xpath of mapping parameter present");
                                        if (JDomUtil.isXpathPresent(document,
                                                APPLICATION_PROCESS_XPATH,
                                                POWERLINK_XDD_NAMESPACE)) {
                                            System.err.println(
                                                    "The Xpath Already available.....");
                                        } else {
                                            updateApplicationProcess(document);
                                            System.err.println(
                                                    "The Xpath Already  not available.....");

                                        }
                                        updateNodeApplicationProcess(document,
                                                module, plkSubObj);

                                        updateObjectUniqueIdRef(document,
                                                module, uniqueIdRefXPath,
                                                plkSubObj.getUniqueIDRef());
                                    } else {

                                        String subIndexVal = Long
                                                .toHexString(
                                                        moduleSubObjectIndex)
                                                .toUpperCase();
                                        if (subIndex <= 15) {
                                            subIndexVal = "0" + subIndexVal;
                                        }

                                        String removeSubObjectXpath = "//plk:ObjectList"
                                                + "/plk:Object[@index='"
                                                + Long.toHexString(
                                                        moduleObjectIndex)
                                                + "']"
                                                + "/plk:SubObject[@subIndex='"
                                                + subIndexVal + "']";

                                        JDomUtil.removeAttributes(document,
                                                removeSubObjectXpath,
                                                POWERLINK_XDD_NAMESPACE,
                                                "uniqueIDRef");

                                        String actualValue = plkSubObj
                                                .getActualValue(plkSubObj
                                                        .getUniqueIDRef());

                                        if ((actualValue == null)
                                                || (actualValue
                                                        .equalsIgnoreCase(
                                                                StringUtils.EMPTY))) {
                                            actualValue = plkSubObj
                                                    .getActualValueFromLibrary(
                                                            subIndex,
                                                            moduleObjectIndex);
                                        }
                                        System.err.println("Actual value....."
                                                + actualValue + " Object .."
                                                + Long.toHexString(
                                                        moduleObjectIndex)
                                                + " subObject.." + Integer
                                                        .toHexString(subIndex));
                                        if ((actualValue != null)) {
                                            Attribute newAttribute = new Attribute(
                                                    OBJECT_ACTUAL_VALUE,
                                                    actualValue);
                                            JDomUtil.setAttribute(document,
                                                    subobjectXpath,
                                                    POWERLINK_XDD_NAMESPACE,
                                                    newAttribute);
                                        }
                                        String defaultValue = plkSubObj
                                                .getdefaultValue(plkSubObj
                                                        .getUniqueIDRef());

                                        if ((defaultValue != null)) {
                                            Attribute newAttribute = new Attribute(
                                                    "defaultValue",
                                                    defaultValue);
                                            JDomUtil.setAttribute(document,
                                                    subobjectXpath,
                                                    POWERLINK_XDD_NAMESPACE,
                                                    newAttribute);
                                        }
                                        String removeactualValueXpath = "//plk:ObjectList"
                                                + "/plk:Object[@index='"
                                                + Long.toHexString(
                                                        moduleObjectIndex)
                                                + "']"
                                                + "/plk:SubObject[@actualValue='']";
                                        if (JDomUtil.isXpathPresent(document,
                                                removeactualValueXpath,
                                                POWERLINK_XDD_NAMESPACE)) {
                                            JDomUtil.removeAttributes(document,
                                                    removeactualValueXpath,
                                                    POWERLINK_XDD_NAMESPACE,
                                                    "actualValue");
                                        }
                                    }
                                }

                            }

                        }

                    }

                }

            }
        }

    }

    /**
     * Update the head node XDC with the module sub-objects.
     *
     * @param document XDD/XDC file instance.
     * @param module Instance of Module.
     * @param subObject Instance of POWERLINK sub-object.
     * @param node Instance of Node.
     * @param index Index value of POWERLINK object
     * @param subindex sub-index value of POWERLINK sub-object.
     */
    public static void updateModuleSubObjectInNode(Document document,
            Module module, PowerlinkSubobject subObject, Node node, long index,
            int subindex) {
        String Xpath = "//plk:Object[@index='" + Long.toHexString(index) + "']";
        String subobjectXpath = Xpath + "/plk:SubObject[@subIndex='"
                + Long.toHexString(subindex) + "']";
        if (JDomUtil.isXpathPresent(document, subobjectXpath,
                POWERLINK_XDD_NAMESPACE)) {
            System.out.println("Sub-object index already available.");
        } else if (JDomUtil.isXpathPresent(document, Xpath,
                POWERLINK_XDD_NAMESPACE)) {
            Element newSubObjElement = new Element("SubObject");
            List<Attribute> attribList = newSubObjElement.getAttributes();

            attribList.add(new Attribute("subIndex", subObject.getIdRaw()));
            attribList.add(new Attribute("name", subObject.getName()));
            if (subObject.getObjectType() != 0) {
                attribList.add(new Attribute("objectType",
                        String.valueOf(subObject.getObjectType())));
            }
            if (subObject.getDataType() != null) {
                attribList.add(new Attribute("dataType", DatatypeConverter
                        .printHexBinary(subObject.getDataType())));
            }
            if (!(subObject.getAccessTypeReadable().isEmpty())) {
                attribList.add(new Attribute("accessType",
                        subObject.getAccessTypeReadable()));
            }
            if (!(subObject.getDefaultValue().isEmpty())) {
                attribList.add(new Attribute("defaultValue",
                        subObject.getDefaultValue()));
            }
            if (!(subObject.getActualValue().isEmpty())) {
                attribList.add(new Attribute("actualValue",
                        subObject.getActualValue()));
            }
            if (!(subObject.getHighLimit().isEmpty())) {
                attribList.add(
                        new Attribute("highLimit", subObject.getHighLimit()));
            }
            if (!(subObject.getLowLimit().isEmpty())) {
                attribList.add(
                        new Attribute("lowLimit", subObject.getLowLimit()));
            }
            JDomUtil.addNewElement(document, Xpath, POWERLINK_XDD_NAMESPACE,
                    newSubObjElement);
        } else {
            System.err.println(
                    "Sub-ObjectList Xpath not found in head node XDC file.");
        }
    }

    /**
     * Update the application process of module into head node XDC.
     *
     * @param document XDD/XDC file instance.
     * @param module Instance of Module.
     * @param plkSubObj Instance of POWERLINK sub-object.
     */
    private static void updateNodeApplicationProcess(Document document,
            Module module, PowerlinkSubobject plkSubObj) {
        Object uniqueIdRef = plkSubObj.getUniqueIDRef();

        if (uniqueIdRef instanceof TParameterList.Parameter) {
            TParameterList.Parameter parameter = (TParameterList.Parameter) uniqueIdRef;

            updateParameterListToHeadXDC(document, parameter, module);

        } else if (uniqueIdRef instanceof TParameterGroup) {
            TParameterGroup parameterGrp = (TParameterGroup) uniqueIdRef;
            String headpgmgrpxpath = APPLICATION_PROCESS_XPATH + "/plk:"
                    + PARAMETER_GROUP_LIST;

            String newUniqueId = OpenConfiguratorLibraryUtils
                    .getModuleParameterUniqueID(module,
                            parameterGrp.getUniqueID());

            updateParameterGroupInXDC(document, module, plkSubObj, parameterGrp,
                    headpgmgrpxpath, "parameterGroup");
            String childPrmXpath = headpgmgrpxpath
                    + "/plk:parameterGroup[@uniqueID='" + newUniqueId + "']";

            List<Object> parameterGroupReferenceList = parameterGrp
                    .getParameterGroupOrParameterRef();
            // Redundant null check is made to prevent the project from any
            // unexpected failure.
            if (parameterGroupReferenceList != null) {
                for (Object parameterGroupReference : parameterGroupReferenceList) {
                    if (parameterGroupReference instanceof TParameterGroup) {
                        TParameterGroup paramGrp = (TParameterGroup) parameterGroupReference;
                        updateParameterGroupsInXDC(document, module, plkSubObj,
                                paramGrp, childPrmXpath, PARAMETER_GROUP);
                    } else if (parameterGroupReference instanceof TParameterGroup.ParameterRef) {
                        TParameterGroup.ParameterRef parameterReferenceModel = (TParameterGroup.ParameterRef) parameterGroupReference;
                        updateParameterReferenceInXDC(document, module,
                                plkSubObj, parameterReferenceModel,
                                childPrmXpath);

                    }
                }
            }

        }
        removeNodeApplicationProcess(document);
    }

    /**
     * Update the application process of module into head node XDC.
     *
     * @param document XDD/XDC file instance.
     * @param module Instance of Module.
     * @param plkSubObj Instance of POWERLINK sub-object.
     */
    private static void updateNodeApplicationProcessofObject(Document document,
            Module module, PowerlinkObject plkObj) {
        Object uniqueIdRef = plkObj.getUniqueIDRef();

        if (uniqueIdRef instanceof TParameterList.Parameter) {
            TParameterList.Parameter parameter = (TParameterList.Parameter) uniqueIdRef;

            updateParameterListToHeadXDC(document, parameter, module);

        } else if (uniqueIdRef instanceof TParameterGroup) {
            TParameterGroup parameterGrp = (TParameterGroup) uniqueIdRef;
            String headpgmgrpxpath = APPLICATION_PROCESS_XPATH + "/plk:"
                    + PARAMETER_GROUP_LIST;

            String newUniqueId = OpenConfiguratorLibraryUtils
                    .getModuleParameterUniqueID(module,
                            parameterGrp.getUniqueID());

            updateParameterGroupofObjectInXDC(document, module, plkObj,
                    parameterGrp, headpgmgrpxpath, "parameterGroup");
            String childPrmXpath = headpgmgrpxpath
                    + "/plk:parameterGroup[@uniqueID='" + newUniqueId + "']";

            List<Object> parameterGroupReferenceList = parameterGrp
                    .getParameterGroupOrParameterRef();
            // Redundant null check is made to prevent the project from any
            // unexpected failure.
            if (parameterGroupReferenceList != null) {
                for (Object parameterGroupReference : parameterGroupReferenceList) {
                    if (parameterGroupReference instanceof TParameterGroup) {
                        TParameterGroup paramGrp = (TParameterGroup) parameterGroupReference;
                        updateParameterGroupsInXDC(document, module, plkObj,
                                paramGrp, childPrmXpath, PARAMETER_GROUP,
                                newUniqueId);

                    } else if (parameterGroupReference instanceof TParameterGroup.ParameterRef) {
                        TParameterGroup.ParameterRef parameterReferenceModel = (TParameterGroup.ParameterRef) parameterGroupReference;
                        updateParameterReferenceInXDC(document, module, plkObj,
                                parameterReferenceModel, childPrmXpath);

                    }
                }
            }

        }

    }

    public static void updateNumberOfEntries(Document document, Node node) {
        if (node.isModularheadNode()) {
            List<HeadNodeInterface> interfaceList = node.getHeadNodeInterface();
            for (HeadNodeInterface interfaces : interfaceList) {
                Collection<Module> moduleList = interfaces.getModuleCollection()
                        .values();
                for (Module module : moduleList) {
                    List<PowerlinkObject> plkObjList = module
                            .getObjectDictionary().getObjectsList();

                    for (PowerlinkObject plkObj : plkObjList) {

                        long moduleObjectIndex = OpenConfiguratorLibraryUtils
                                .getModuleObjectsIndex(plkObj.getModule(),
                                        plkObj.getId());

                        String objectXpath = "//plk:Object[@index='"
                                + Long.toHexString(moduleObjectIndex) + "']";
                        int entriesDefaultValue = 0;
                        if (JDomUtil.isXpathPresent(document, objectXpath,
                                POWERLINK_XDD_NAMESPACE)) {

                            for (int count = 0; count < 255; count++) {

                                String defaultValue = Long.toHexString(count)
                                        .toUpperCase();
                                if (count <= 15) {
                                    defaultValue = "0" + Long.toHexString(count)
                                            .toUpperCase();
                                }

                                String xpath = "//plk:Object[@index='"
                                        + Long.toHexString(moduleObjectIndex)
                                        + "']";
                                String subobjectXpath = xpath
                                        + "/plk:SubObject[@subIndex='"
                                        + defaultValue + "']";
                                String numberOfEntriesXpath = xpath
                                        + "/plk:SubObject[@subIndex='" + "00"
                                        + "']";

                                if (JDomUtil.isXpathPresent(document,
                                        subobjectXpath,
                                        POWERLINK_XDD_NAMESPACE)) {
                                    entriesDefaultValue = entriesDefaultValue
                                            + 1;
                                    Attribute newAttribute = new Attribute(
                                            "defaultValue", String.valueOf(
                                                    entriesDefaultValue - 1));
                                    JDomUtil.updateAttribute(document,
                                            numberOfEntriesXpath,
                                            POWERLINK_XDD_NAMESPACE,
                                            newAttribute);
                                }
                            }

                        }
                    }

                }
            }
        }
    }

    private static void updateObjectUniqueIdRef(Document document,
            Module module, String xpath, Object uniqueIdRef) {
        String uniqueId = StringUtils.EMPTY;
        if (uniqueIdRef instanceof TParameterList.Parameter) {
            TParameterList.Parameter parameter = (TParameterList.Parameter) uniqueIdRef;
            String parameterUniqueId = parameter.getUniqueID();
            uniqueId = OpenConfiguratorLibraryUtils
                    .getModuleParameterUniqueID(module, parameterUniqueId);

        } else if (uniqueIdRef instanceof TParameterGroup) {
            TParameterGroup parameterGroup = (TParameterGroup) uniqueIdRef;
            String parameterGrpUniqueId = parameterGroup.getUniqueID();
            uniqueId = OpenConfiguratorLibraryUtils
                    .getModuleParameterUniqueID(module, parameterGrpUniqueId);
        }

        Attribute uniqueIDAttribute = new Attribute("uniqueIDRef", uniqueId);
        JDomUtil.updateAttribute(document, xpath, POWERLINK_XDD_NAMESPACE,
                uniqueIDAttribute);

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

    private static void updateParameterGroupInXDC(Document document,
            Module module, PowerlinkSubobject plkSubObj,
            TParameterGroup parameterGrp, String xpath, String parentChild) {
        String olduniqueId = parameterGrp.getUniqueID();
        String uniqueId = OpenConfiguratorLibraryUtils
                .getModuleParameterUniqueID(module, olduniqueId);

        Element newParamGroup = new Element(parentChild);
        List<Attribute> attribList = newParamGroup.getAttributes();
        attribList.add(new Attribute("uniqueID", uniqueId));
        if (parameterGrp.getKindOfAccess() != null) {
            attribList.add(new Attribute("kindOfAccess",
                    parameterGrp.getKindOfAccess()));
        }
        if (parameterGrp.getConditionalUniqueIDRef() != null) {
            String oldparamUniqueId = plkSubObj.getConditionalUniqueId(
                    parameterGrp.getConditionalUniqueIDRef());
            String paramUniqueId = OpenConfiguratorLibraryUtils
                    .getModuleParameterUniqueID(module, oldparamUniqueId);
            attribList.add(
                    new Attribute("conditionalUniqueIDRef", paramUniqueId));
        }
        if (parameterGrp.getConditionalValue() != null) {
            attribList.add(new Attribute("conditionalValue",
                    parameterGrp.getConditionalValue()));
        }
        if (parameterGrp.getBitOffset() != null) {
            attribList.add(new Attribute("bitOffset",
                    String.valueOf(parameterGrp.getBitOffset())));
        }

        attribList.add(new Attribute("configParameter", plkSubObj
                .getConfigParameter(parameterGrp.isConfigParameter())));
        attribList.add(new Attribute("groupLevelVisible", plkSubObj
                .getGroupLevelVisible(parameterGrp.isGroupLevelVisible())));
        JDomUtil.addNewElement(document, xpath, POWERLINK_XDD_NAMESPACE,
                newParamGroup);

        String parameterTemplateXpath = PARAMETER_GROUP_LIST_XPATH
                + "/plk:parameterGroup[@uniqueID='" + uniqueId + "']";

        Element newLabelelement = new Element("label");
        List<Attribute> attribListlbl = newLabelelement.getAttributes();
        attribListlbl.add(new Attribute("lang", "en"));
        newLabelelement.setText(new LabelDescription(
                parameterGrp.getLabelOrDescriptionOrLabelRef()).getText());

        JDomUtil.addNewElement(document, parameterTemplateXpath,
                POWERLINK_XDD_NAMESPACE, newLabelelement);

        if (parameterGrp
                .getConditionalUniqueIDRef() instanceof TParameterList.Parameter) {
            TParameterList.Parameter param = (TParameterList.Parameter) parameterGrp
                    .getConditionalUniqueIDRef();
            updateParameterListsToHeadXDC(document, param, module);
        }
    }

    private static void updateParameterGroupofObjectInXDC(Document document,
            Module module, PowerlinkObject plkObj, TParameterGroup parameterGrp,
            String xpath, String parentChild) {
        String olduniqueId = parameterGrp.getUniqueID();
        String uniqueId = OpenConfiguratorLibraryUtils
                .getModuleParameterUniqueID(module, olduniqueId);

        Element newParamGroup = new Element(parentChild);
        List<Attribute> attribList = newParamGroup.getAttributes();
        attribList.add(new Attribute("uniqueID", uniqueId));
        if (parameterGrp.getKindOfAccess() != null) {
            attribList.add(new Attribute("kindOfAccess",
                    parameterGrp.getKindOfAccess()));
        }
        if (parameterGrp.getConditionalUniqueIDRef() != null) {
            String oldparamUniqueId = plkObj.getConditionalUniqueId(
                    parameterGrp.getConditionalUniqueIDRef());
            String paramUniqueId = OpenConfiguratorLibraryUtils
                    .getModuleParameterUniqueID(module, oldparamUniqueId);
            attribList.add(
                    new Attribute("conditionalUniqueIDRef", paramUniqueId));
        }
        if (parameterGrp.getConditionalValue() != null) {
            attribList.add(new Attribute("conditionalValue",
                    parameterGrp.getConditionalValue()));
        }
        if (parameterGrp.getBitOffset() != null) {
            attribList.add(new Attribute("bitOffset",
                    String.valueOf(parameterGrp.getBitOffset())));
        }

        attribList.add(new Attribute("configParameter",
                plkObj.getConfigParameter(parameterGrp.isConfigParameter())));
        attribList.add(new Attribute("groupLevelVisible", plkObj
                .getGroupLevelVisible(parameterGrp.isGroupLevelVisible())));
        JDomUtil.addNewElement(document, xpath, POWERLINK_XDD_NAMESPACE,
                newParamGroup);

        String parameterTemplateXpath = PARAMETER_GROUP_LIST_XPATH
                + "/plk:parameterGroup[@uniqueID='" + uniqueId + "']";

        Element newLabelelement = new Element("label");
        List<Attribute> attribListlbl = newLabelelement.getAttributes();
        attribListlbl.add(new Attribute("lang", "en"));
        newLabelelement.setText(new LabelDescription(
                parameterGrp.getLabelOrDescriptionOrLabelRef()).getText());

        JDomUtil.addNewElement(document, parameterTemplateXpath,
                POWERLINK_XDD_NAMESPACE, newLabelelement);

        if (parameterGrp
                .getConditionalUniqueIDRef() instanceof TParameterList.Parameter) {
            TParameterList.Parameter param = (TParameterList.Parameter) parameterGrp
                    .getConditionalUniqueIDRef();
            updateParameterListsToHeadXDC(document, param, module);
        }
    }

    private static void updateParameterGroupsInXDC(Document document,
            Module module, PowerlinkObject plkObj, TParameterGroup parameterGrp,
            String xpath, String parentChild, String newUniqueID) {
        String olduniqueId = parameterGrp.getUniqueID();
        String uniqueId = OpenConfiguratorLibraryUtils
                .getModuleParameterUniqueID(module, olduniqueId);

        Element newParamGroup = new Element(parentChild);
        List<Attribute> attribList = newParamGroup.getAttributes();
        attribList.add(new Attribute("uniqueID", uniqueId));
        if (parameterGrp.getKindOfAccess() != null) {
            attribList.add(new Attribute("kindOfAccess",
                    parameterGrp.getKindOfAccess()));
        }
        if (parameterGrp.getConditionalUniqueIDRef() != null) {
            String oldparamUniqueId = plkObj.getConditionalUniqueId(
                    parameterGrp.getConditionalUniqueIDRef());
            String paramUniqueId = OpenConfiguratorLibraryUtils
                    .getModuleParameterUniqueID(module, oldparamUniqueId);

            attribList.add(
                    new Attribute("conditionalUniqueIDRef", paramUniqueId));
        }
        if (parameterGrp.getConditionalValue() != null) {
            attribList.add(new Attribute("conditionalValue",
                    parameterGrp.getConditionalValue()));
        }
        if (parameterGrp.getBitOffset() != null) {
            attribList.add(new Attribute("bitOffset",
                    String.valueOf(parameterGrp.getBitOffset())));
        }

        attribList.add(new Attribute("configParameter",
                plkObj.getConfigParameter(parameterGrp.isConfigParameter())));
        attribList.add(new Attribute("groupLevelVisible", plkObj
                .getGroupLevelVisible(parameterGrp.isGroupLevelVisible())));
        JDomUtil.addNewElement(document, xpath, POWERLINK_XDD_NAMESPACE,
                newParamGroup);

        String parameterTemplateXpath = PARAMETER_GROUP_LIST_XPATH
                + "/plk:parameterGroup[@uniqueID='" + newUniqueID + "']"
                + "/plk:parameterGroup[@uniqueID='" + uniqueId + "']";

        Element newLabelelement = new Element("label");
        List<Attribute> attribListlbl = newLabelelement.getAttributes();
        attribListlbl.add(new Attribute("lang", "en"));
        newLabelelement.setText(new LabelDescription(
                parameterGrp.getLabelOrDescriptionOrLabelRef()).getText());

        JDomUtil.addNewElement(document, parameterTemplateXpath,
                POWERLINK_XDD_NAMESPACE, newLabelelement);

        if (parameterGrp
                .getConditionalUniqueIDRef() instanceof TParameterList.Parameter) {
            TParameterList.Parameter param = (TParameterList.Parameter) parameterGrp
                    .getConditionalUniqueIDRef();
            updateParameterListsToHeadXDC(document, param, module);
        }

        List<Object> parameterGroupReferenceList = parameterGrp
                .getParameterGroupOrParameterRef();
        String paramGrpXpath = xpath + "/plk:parameterGroup[@uniqueID='"
                + uniqueId + "']";
        // Redundant null check is made to prevent the project from any
        // unexpected failure.
        if (parameterGroupReferenceList != null) {
            for (Object parameterGroupReference : parameterGroupReferenceList) {
                if (parameterGroupReference instanceof TParameterGroup) {
                    TParameterGroup paramGrp = (TParameterGroup) parameterGroupReference;
                    updateParameterGroupsInXDC(document, module, plkObj,
                            paramGrp, paramGrpXpath, PARAMETER_GROUP, uniqueId);

                } else if (parameterGroupReference instanceof TParameterGroup.ParameterRef) {
                    TParameterGroup.ParameterRef parameterReferenceModel = (TParameterGroup.ParameterRef) parameterGroupReference;
                    updateParameterReferenceInXDC(document, module, plkObj,
                            parameterReferenceModel, paramGrpXpath);

                }
            }
        }

    }

    private static void updateParameterGroupsInXDC(Document document,
            Module module, PowerlinkSubobject plkSubObj,
            TParameterGroup parameterGrp, String xpath, String parentChild) {
        String olduniqueId = parameterGrp.getUniqueID();
        String uniqueId = OpenConfiguratorLibraryUtils
                .getModuleParameterUniqueID(module, olduniqueId);

        Element newParamGroup = new Element(parentChild);
        List<Attribute> attribList = newParamGroup.getAttributes();
        attribList.add(new Attribute("uniqueID", uniqueId));
        if (parameterGrp.getKindOfAccess() != null) {
            attribList.add(new Attribute("kindOfAccess",
                    parameterGrp.getKindOfAccess()));
        }
        if (parameterGrp.getConditionalUniqueIDRef() != null) {
            String oldparamUniqueId = plkSubObj.getConditionalUniqueId(
                    parameterGrp.getConditionalUniqueIDRef());
            String paramUniqueId = OpenConfiguratorLibraryUtils
                    .getModuleParameterUniqueID(module, oldparamUniqueId);

            attribList.add(
                    new Attribute("conditionalUniqueIDRef", paramUniqueId));
        }
        if (parameterGrp.getConditionalValue() != null) {
            attribList.add(new Attribute("conditionalValue",
                    parameterGrp.getConditionalValue()));
        }
        if (parameterGrp.getBitOffset() != null) {
            attribList.add(new Attribute("bitOffset",
                    String.valueOf(parameterGrp.getBitOffset())));
        }

        attribList.add(new Attribute("configParameter", plkSubObj
                .getConfigParameter(parameterGrp.isConfigParameter())));
        attribList.add(new Attribute("groupLevelVisible", plkSubObj
                .getGroupLevelVisible(parameterGrp.isGroupLevelVisible())));
        JDomUtil.addNewElement(document, xpath, POWERLINK_XDD_NAMESPACE,
                newParamGroup);

        String parameterTemplateXpath = xpath
                + "/plk:parameterGroup[@uniqueID='" + uniqueId + "']";

        Element newLabelelement = new Element("label");
        List<Attribute> attribListlbl = newLabelelement.getAttributes();
        attribListlbl.add(new Attribute("lang", "en"));
        newLabelelement.setText(new LabelDescription(
                parameterGrp.getLabelOrDescriptionOrLabelRef()).getText());

        JDomUtil.addNewElements(document, parameterTemplateXpath,
                POWERLINK_XDD_NAMESPACE, newLabelelement);

        if (parameterGrp
                .getConditionalUniqueIDRef() instanceof TParameterList.Parameter) {
            TParameterList.Parameter param = (TParameterList.Parameter) parameterGrp
                    .getConditionalUniqueIDRef();
            updateParameterListsToHeadXDC(document, param, module);
        }

        List<Object> parameterGroupReferenceList = parameterGrp
                .getParameterGroupOrParameterRef();
        String paramGrpXpath = xpath + "/plk:parameterGroup[@uniqueID='"
                + uniqueId + "']";
        if (parameterGroupReferenceList != null) {
            // Redundant null check is made to prevent the project from any
            // unexpected failure.
            for (Object parameterGroupReference : parameterGroupReferenceList) {
                if (parameterGroupReference instanceof TParameterGroup) {
                    TParameterGroup paramGrp = (TParameterGroup) parameterGroupReference;
                    updateParameterGroupsInXDC(document, module, plkSubObj,
                            paramGrp, paramGrpXpath, PARAMETER_GROUP);

                } else if (parameterGroupReference instanceof TParameterGroup.ParameterRef) {
                    TParameterGroup.ParameterRef parameterReferenceModel = (TParameterGroup.ParameterRef) parameterGroupReference;
                    updateParameterReferenceInXDC(document, module, plkSubObj,
                            parameterReferenceModel, paramGrpXpath);
                }
            }
        }

    }

    @SuppressWarnings("unused")
    private static void updateParameterListsToHeadXDC(Document document,
            TParameterList.Parameter parameter, Module module) {

        String oldparamUniqueId = parameter.getUniqueID();
        String paramUniqueId = OpenConfiguratorLibraryUtils
                .getModuleParameterUniqueID(module, oldparamUniqueId);

        String access = parameter.getAccess();
        if (access == null) {
            access = "undefined";
        }

        TParameterList.Parameter parameterModel = parameter;

        String parameterUniqueId = parameterModel.getUniqueID();
        String accessparam = parameterModel.getAccess();

        if (JDomUtil.isXpathPresent(document, PARAMETER_LIST_XPATH,
                POWERLINK_XDD_NAMESPACE)) {
            String parameterTemplateXpath = PARAMETER_LIST_XPATH
                    + "/plk:parameter[@uniqueID='" + paramUniqueId + "']";
            if (JDomUtil.isXpathPresent(document, parameterTemplateXpath,
                    POWERLINK_XDD_NAMESPACE)) {
            } else {
                Element newParamTempElement = new Element("parameter");
                List<Attribute> attribList = newParamTempElement
                        .getAttributes();
                attribList.add(new Attribute("uniqueID", paramUniqueId));
                attribList.add(new Attribute("access", accessparam));
                JDomUtil.addNewElement(document, PARAMETER_LIST_XPATH,
                        POWERLINK_XDD_NAMESPACE, newParamTempElement);

                Element newParamTempLabelElement = new Element("label");
                List<Attribute> attribListlbl = newParamTempLabelElement
                        .getAttributes();
                attribListlbl.add(new Attribute("lang", "en-us"));
                newParamTempLabelElement.setText(new LabelDescription(
                        parameterModel.getLabelOrDescriptionOrLabelRef())
                                .getText());
                JDomUtil.addNewElements(document, parameterTemplateXpath,
                        POWERLINK_XDD_NAMESPACE, newParamTempLabelElement);

                Element dataTypeElement = new Element(
                        getIEC_DataType(parameterModel));
                JDomUtil.addNewElements(document, parameterTemplateXpath,
                        POWERLINK_XDD_NAMESPACE, dataTypeElement);

                Element actualValueElement = new Element("actualValue");
                List<Attribute> attribListactual = actualValueElement
                        .getAttributes();
                if (parameterModel.getActualValue() != null) {
                    attribListactual.add(new Attribute("value",
                            parameterModel.getActualValue().getValue()));
                    JDomUtil.addNewElements(document, parameterTemplateXpath,
                            POWERLINK_XDD_NAMESPACE, actualValueElement);
                }

                Element defaultValueElement = new Element("defaultValue");
                List<Attribute> attribListdefault = defaultValueElement
                        .getAttributes();
                if (parameterModel.getDefaultValue() != null) {
                    attribListdefault.add(new Attribute("value",
                            parameterModel.getDefaultValue().getValue()));
                    JDomUtil.addNewElements(document, parameterTemplateXpath,
                            POWERLINK_XDD_NAMESPACE, defaultValueElement);
                }

                TAllowedValues allowedValuesModel = parameter
                        .getAllowedValues();
                if (allowedValuesModel != null) {
                    String allowedModulesXPath = parameterTemplateXpath
                            + "/plk:allowedValues";
                    Element allowedValueElement = new Element("allowedValues");
                    JDomUtil.addNewElements(document, parameterTemplateXpath,
                            POWERLINK_XDD_NAMESPACE, allowedValueElement);

                    List<TValue> parameterAllowedValuesList = allowedValuesModel
                            .getValue();
                    // Create a string collection with allowed values list.
                    for (TValue parameterAllowedValue : parameterAllowedValuesList) {
                        Element valueElement = new Element("value");
                        List<Attribute> attribValue = valueElement
                                .getAttributes();
                        attribValue.add(new Attribute("value",
                                parameterAllowedValue.getValue()));
                        JDomUtil.addNewElements(document, allowedModulesXPath,
                                POWERLINK_XDD_NAMESPACE, valueElement);

                        Element labelElement = new Element("label");
                        String allowedModuleslabelXPath = allowedModulesXPath
                                + "/plk:value[@value='"
                                + parameterAllowedValue.getValue() + "']";
                        List<Attribute> attriblbl = labelElement
                                .getAttributes();
                        attriblbl.add(new Attribute("lang", "en"));
                        labelElement.setText(
                                new LabelDescription(parameterAllowedValue
                                        .getLabelOrDescriptionOrLabelRef())
                                                .getText());
                        JDomUtil.addNewElements(document,
                                allowedModuleslabelXPath,
                                POWERLINK_XDD_NAMESPACE, labelElement);

                    }

                    List<TRange> rangeList = allowedValuesModel.getRange();
                    for (TRange range : rangeList) {

                        TRange.MinValue minValueModel = range.getMinValue();
                        TRange.MaxValue maxValueModel = range.getMaxValue();

                        String minValue = StringUtils.EMPTY;
                        String maxValue = StringUtils.EMPTY;

                        if (minValueModel != null) {
                            minValue = minValueModel.getValue();
                            Element valueElement = new Element("range");

                            JDomUtil.addNewElements(document,
                                    allowedModulesXPath,
                                    POWERLINK_XDD_NAMESPACE, valueElement);

                            Element minvalueElement = new Element("minValue");
                            List<Attribute> attribValue = minvalueElement
                                    .getAttributes();
                            attribValue.add(new Attribute("value", minValue));
                            JDomUtil.addNewElements(document,
                                    allowedModulesXPath + "/plk:range",
                                    POWERLINK_XDD_NAMESPACE, minvalueElement);
                        }

                        if (maxValueModel != null) {
                            maxValue = maxValueModel.getValue();
                            Element maxvalueElement = new Element("maxValue");
                            List<Attribute> attribValue = maxvalueElement
                                    .getAttributes();
                            attribValue.add(new Attribute("value", minValue));
                            JDomUtil.addNewElements(document,
                                    allowedModulesXPath + "/plk:range",
                                    POWERLINK_XDD_NAMESPACE, maxvalueElement);
                        }

                    }
                }
            }
        } else {
            Element newTempElement = new Element(PARAMETER_LIST);
            JDomUtil.addNewElements(document, APPLICATION_PROCESS_XPATH,
                    POWERLINK_XDD_NAMESPACE, newTempElement);
            Element newParamTempElement = new Element("parameter");
            List<Attribute> attribList = newParamTempElement.getAttributes();
            attribList.add(new Attribute("uniqueID", parameterUniqueId));
            attribList.add(new Attribute("access", accessparam));

            JDomUtil.addNewElement(document, PARAMETER_LIST_XPATH,
                    POWERLINK_XDD_NAMESPACE, newParamTempElement);

            String parameterTemplateXpath = PARAMETER_LIST_XPATH
                    + "/plk:parameter[@uniqueID='" + parameterUniqueId + "']";
            Element newParamTempLabelElement = new Element("label");
            List<Attribute> attribListlbl = newParamTempLabelElement
                    .getAttributes();
            attribListlbl.add(new Attribute("lang", "en-us"));

            JDomUtil.addNewElements(document, parameterTemplateXpath,
                    POWERLINK_XDD_NAMESPACE, newParamTempLabelElement);
            Element dataTypeElement = new Element(
                    getIEC_DataType(parameterModel));
            JDomUtil.addNewElements(document, parameterTemplateXpath,
                    POWERLINK_XDD_NAMESPACE, dataTypeElement);
            Element defaultValueElement = new Element("defaultValue");
            List<Attribute> attribListdefault = defaultValueElement
                    .getAttributes();
            if (parameterModel.getDefaultValue() != null) {
                attribListdefault.add(new Attribute("value",
                        parameterModel.getDefaultValue().getValue()));
                JDomUtil.addNewElements(document, parameterTemplateXpath,
                        POWERLINK_XDD_NAMESPACE, defaultValueElement);
            }

            Element actualValueElement = new Element("actualValue");
            List<Attribute> attribListactual = actualValueElement
                    .getAttributes();
            if (parameterModel.getActualValue() != null) {
                attribListactual.add(new Attribute("value",
                        parameterModel.getActualValue().getValue()));
                JDomUtil.addNewElements(document, parameterTemplateXpath,
                        POWERLINK_XDD_NAMESPACE, actualValueElement);
            }

            TAllowedValues allowedValuesModel = parameter.getAllowedValues();
            if (allowedValuesModel != null) {
                String allowedModulesXPath = parameterTemplateXpath
                        + "/plk:allowedValues";
                Element allowedValueElement = new Element("allowedValues");
                JDomUtil.addNewElements(document, parameterTemplateXpath,
                        POWERLINK_XDD_NAMESPACE, allowedValueElement);
                List<TValue> parameterAllowedValuesList = allowedValuesModel
                        .getValue();
                // Create a string collection with allowed values list.

                for (TValue parameterAllowedValue : parameterAllowedValuesList) {
                    Element valueElement = new Element("value");
                    List<Attribute> attribValue = valueElement.getAttributes();
                    attribValue.add(new Attribute("value",
                            parameterAllowedValue.getValue()));
                    JDomUtil.addNewElements(document, allowedModulesXPath,
                            POWERLINK_XDD_NAMESPACE, valueElement);

                }

                List<TRange> rangeList = allowedValuesModel.getRange();
                for (TRange range : rangeList) {

                    TRange.MinValue minValueModel = range.getMinValue();
                    TRange.MaxValue maxValueModel = range.getMaxValue();

                    String minValue = StringUtils.EMPTY;
                    String maxValue = StringUtils.EMPTY;

                    if (minValueModel != null) {
                        minValue = minValueModel.getValue();
                        Element valueElement = new Element("range");

                        JDomUtil.addNewElements(document, allowedModulesXPath,
                                POWERLINK_XDD_NAMESPACE, valueElement);

                        Element minvalueElement = new Element("minValue");
                        List<Attribute> attribValue = minvalueElement
                                .getAttributes();
                        attribValue.add(new Attribute("value", minValue));
                        JDomUtil.addNewElements(document,
                                allowedModulesXPath + "/plk:range",
                                POWERLINK_XDD_NAMESPACE, minvalueElement);
                    }

                    if (maxValueModel != null) {
                        maxValue = maxValueModel.getValue();
                        Element maxvalueElement = new Element("maxValue");
                        List<Attribute> attribValue = maxvalueElement
                                .getAttributes();
                        attribValue.add(new Attribute("value", maxValue));
                        JDomUtil.addNewElements(document,
                                allowedModulesXPath + "/plk:range",
                                POWERLINK_XDD_NAMESPACE, maxvalueElement);
                    }

                }
            }

        }

        Object paramTempladeIDRef = parameter.getTemplateIDRef();
        if (paramTempladeIDRef != null) {

            if (paramTempladeIDRef instanceof TParameterTemplate) {
                TParameterTemplate parameteratemplateModel = (TParameterTemplate) paramTempladeIDRef;

                if (JDomUtil.isXpathPresent(document, TEMPLATE_LIST_XPATH,
                        POWERLINK_XDD_NAMESPACE)) {
                    updatePrameterTemplateListInHeadNode(document,
                            parameteratemplateModel);
                } else {
                    Element newTempElement = new Element(TEMPLATE_LIST);
                    JDomUtil.addNewElements(document, APPLICATION_PROCESS_XPATH,
                            POWERLINK_XDD_NAMESPACE, newTempElement);

                    updatePrameterTemplateListInHeadNode(document,
                            parameteratemplateModel);
                }
            }

        }
    }

    @SuppressWarnings("unused")
    private static void updateParameterListToHeadXDC(Document document,
            TParameterList.Parameter parameter, Module module) {

        String oldparamUniqueId = parameter.getUniqueID();
        String paramUniqueId = OpenConfiguratorLibraryUtils
                .getModuleParameterUniqueID(module, oldparamUniqueId);

        System.err.println("New param UniqueID..." + paramUniqueId);

        TParameterList.Parameter parameterModel = parameter;

        String parameterUniqueId = parameterModel.getUniqueID();
        String accessparam = parameterModel.getAccess();

        if (accessparam == null) {
            accessparam = "undefined";
        }

        if (JDomUtil.isXpathPresent(document, PARAMETER_LIST_XPATH,
                POWERLINK_XDD_NAMESPACE)) {

            Element newParamTempElement = new Element("parameter");
            List<Attribute> attribList = newParamTempElement.getAttributes();
            attribList.add(new Attribute("uniqueID", paramUniqueId));
            attribList.add(new Attribute("access", accessparam));
            JDomUtil.addNewElement(document, PARAMETER_LIST_XPATH,
                    POWERLINK_XDD_NAMESPACE, newParamTempElement);

            String parameterTemplateXpath = PARAMETER_LIST_XPATH
                    + "/plk:parameter[@uniqueID='" + paramUniqueId + "']";
            if (JDomUtil.isXpathPresent(document, parameterTemplateXpath,
                    POWERLINK_XDD_NAMESPACE)) {
                Element newParamTempLabelElement = new Element("label");
                List<Attribute> attribListlbl = newParamTempLabelElement
                        .getAttributes();
                attribListlbl.add(new Attribute("lang", "en-us"));
                newParamTempLabelElement.setText(new LabelDescription(
                        parameterModel.getLabelOrDescriptionOrLabelRef())
                                .getText());
                JDomUtil.addNewElement(document, parameterTemplateXpath,
                        POWERLINK_XDD_NAMESPACE, newParamTempLabelElement);

                if (parameterModel.getDataTypeIDRef() != null) {
                    Element dataTypeIdRefElement = new Element("dataTypeIDRef");
                    List<Attribute> attribListdataType = dataTypeIdRefElement
                            .getAttributes();

                    attribListdataType.add(new Attribute("uniqueIDRef",
                            module.getParamUniqueID(parameterModel
                                    .getDataTypeIDRef().getUniqueIDRef())));
                    JDomUtil.addNewElement(document, parameterTemplateXpath,
                            POWERLINK_XDD_NAMESPACE, dataTypeIdRefElement);
                    updateStructDataType(document,
                            parameterModel.getDataTypeIDRef().getUniqueIDRef());
                } else {

                    Element dataTypeElement = new Element(
                            getIEC_DataType(parameterModel));
                    JDomUtil.addNewElement(document, parameterTemplateXpath,
                            POWERLINK_XDD_NAMESPACE, dataTypeElement);
                }

                Element actualValueElement = new Element("actualValue");
                List<Attribute> attribListactual = actualValueElement
                        .getAttributes();
                if (parameterModel.getActualValue() != null) {
                    attribListactual.add(new Attribute("value",
                            parameterModel.getActualValue().getValue()));
                    JDomUtil.addNewElement(document, parameterTemplateXpath,
                            POWERLINK_XDD_NAMESPACE, actualValueElement);
                }

                Element defaultValueElement = new Element("defaultValue");
                List<Attribute> attribListdefault = defaultValueElement
                        .getAttributes();
                if (parameterModel.getDefaultValue() != null) {
                    attribListdefault.add(new Attribute("value",
                            parameterModel.getDefaultValue().getValue()));
                    JDomUtil.addNewElement(document, parameterTemplateXpath,
                            POWERLINK_XDD_NAMESPACE, defaultValueElement);
                }

                TAllowedValues allowedValuesModel = parameter
                        .getAllowedValues();
                if (allowedValuesModel != null) {
                    String allowedModulesXPath = parameterTemplateXpath
                            + "/plk:allowedValues";
                    Element allowedValueElement = new Element("allowedValues");
                    JDomUtil.addNewElement(document, parameterTemplateXpath,
                            POWERLINK_XDD_NAMESPACE, allowedValueElement);

                    List<TValue> parameterAllowedValuesList = allowedValuesModel
                            .getValue();
                    // Create a string collection with allowed values list.
                    for (TValue parameterAllowedValue : parameterAllowedValuesList) {
                        Element valueElement = new Element("value");
                        List<Attribute> attribValue = valueElement
                                .getAttributes();
                        attribValue.add(new Attribute("value",
                                parameterAllowedValue.getValue()));
                        JDomUtil.addNewElement(document, allowedModulesXPath,
                                POWERLINK_XDD_NAMESPACE, valueElement);

                        Element labelElement = new Element("label");
                        String allowedModuleslabelXPath = allowedModulesXPath
                                + "/plk:value[@value='"
                                + parameterAllowedValue.getValue() + "']";
                        List<Attribute> attriblbl = labelElement
                                .getAttributes();
                        attriblbl.add(new Attribute("lang", "en"));
                        labelElement.setText(
                                new LabelDescription(parameterAllowedValue
                                        .getLabelOrDescriptionOrLabelRef())
                                                .getText());
                        JDomUtil.addNewElement(document,
                                allowedModuleslabelXPath,
                                POWERLINK_XDD_NAMESPACE, labelElement);

                    }

                    List<TRange> rangeList = allowedValuesModel.getRange();
                    for (TRange range : rangeList) {

                        TRange.MinValue minValueModel = range.getMinValue();
                        TRange.MaxValue maxValueModel = range.getMaxValue();

                        String minValue = StringUtils.EMPTY;
                        String maxValue = StringUtils.EMPTY;

                        if (minValueModel != null) {
                            minValue = minValueModel.getValue();
                            Element valueElement = new Element("range");

                            JDomUtil.addNewElement(document,
                                    allowedModulesXPath,
                                    POWERLINK_XDD_NAMESPACE, valueElement);

                            Element minvalueElement = new Element("minValue");
                            List<Attribute> attribValue = minvalueElement
                                    .getAttributes();
                            attribValue.add(new Attribute("value", minValue));
                            JDomUtil.addNewElement(document,
                                    allowedModulesXPath + "/plk:range",
                                    POWERLINK_XDD_NAMESPACE, minvalueElement);
                        }

                        if (maxValueModel != null) {
                            maxValue = maxValueModel.getValue();
                            Element maxvalueElement = new Element("maxValue");
                            List<Attribute> attribValue = maxvalueElement
                                    .getAttributes();
                            attribValue.add(new Attribute("value", minValue));
                            JDomUtil.addNewElement(document,
                                    allowedModulesXPath + "/plk:range",
                                    POWERLINK_XDD_NAMESPACE, maxvalueElement);
                        }

                    }
                }
            }
        } else {
            Element newTempElement = new Element(PARAMETER_LIST);
            JDomUtil.addNewElement(document, APPLICATION_PROCESS_XPATH,
                    POWERLINK_XDD_NAMESPACE, newTempElement);
            Element newParamTempElement = new Element("parameter");
            List<Attribute> attribList = newParamTempElement.getAttributes();
            attribList.add(new Attribute("uniqueID", parameterUniqueId));
            attribList.add(new Attribute("access", accessparam));

            JDomUtil.addNewElement(document, PARAMETER_LIST_XPATH,
                    POWERLINK_XDD_NAMESPACE, newParamTempElement);

            String parameterTemplateXpath = PARAMETER_LIST_XPATH
                    + "/plk:parameter[@uniqueID='" + parameterUniqueId + "']";
            Element newParamTempLabelElement = new Element("label");
            List<Attribute> attribListlbl = newParamTempLabelElement
                    .getAttributes();
            attribListlbl.add(new Attribute("lang", "en-us"));

            JDomUtil.addNewElement(document, parameterTemplateXpath,
                    POWERLINK_XDD_NAMESPACE, newParamTempLabelElement);
            Element dataTypeElement = new Element(
                    getIEC_DataType(parameterModel));
            JDomUtil.addNewElement(document, parameterTemplateXpath,
                    POWERLINK_XDD_NAMESPACE, dataTypeElement);
            Element defaultValueElement = new Element("defaultValue");
            List<Attribute> attribListdefault = defaultValueElement
                    .getAttributes();
            if (parameterModel.getDefaultValue() != null) {
                attribListdefault.add(new Attribute("value",
                        parameterModel.getDefaultValue().getValue()));
                JDomUtil.addNewElement(document, parameterTemplateXpath,
                        POWERLINK_XDD_NAMESPACE, defaultValueElement);
            }

            Element actualValueElement = new Element("actualValue");
            List<Attribute> attribListactual = actualValueElement
                    .getAttributes();
            if (parameterModel.getActualValue() != null) {
                attribListactual.add(new Attribute("value",
                        parameterModel.getActualValue().getValue()));
                JDomUtil.addNewElement(document, parameterTemplateXpath,
                        POWERLINK_XDD_NAMESPACE, actualValueElement);
            }

            TAllowedValues allowedValuesModel = parameter.getAllowedValues();
            if (allowedValuesModel != null) {
                String allowedModulesXPath = parameterTemplateXpath
                        + "/plk:allowedValues";
                Element allowedValueElement = new Element("allowedValues");
                JDomUtil.addNewElement(document, parameterTemplateXpath,
                        POWERLINK_XDD_NAMESPACE, allowedValueElement);
                List<TValue> parameterAllowedValuesList = allowedValuesModel
                        .getValue();
                // Create a string collection with allowed values list.
                for (TValue parameterAllowedValue : parameterAllowedValuesList) {
                    Element valueElement = new Element("value");
                    List<Attribute> attribValue = valueElement.getAttributes();
                    attribValue.add(new Attribute("value",
                            parameterAllowedValue.getValue()));
                    JDomUtil.addNewElement(document, allowedModulesXPath,
                            POWERLINK_XDD_NAMESPACE, valueElement);

                }

                List<TRange> rangeList = allowedValuesModel.getRange();
                for (TRange range : rangeList) {

                    TRange.MinValue minValueModel = range.getMinValue();
                    TRange.MaxValue maxValueModel = range.getMaxValue();

                    String minValue = StringUtils.EMPTY;
                    String maxValue = StringUtils.EMPTY;

                    if (minValueModel != null) {
                        minValue = minValueModel.getValue();
                        Element valueElement = new Element("range");

                        JDomUtil.addNewElement(document, allowedModulesXPath,
                                POWERLINK_XDD_NAMESPACE, valueElement);

                        Element minvalueElement = new Element("minValue");
                        List<Attribute> attribValue = minvalueElement
                                .getAttributes();
                        attribValue.add(new Attribute("value", minValue));
                        JDomUtil.addNewElement(document,
                                allowedModulesXPath + "/plk:range",
                                POWERLINK_XDD_NAMESPACE, minvalueElement);
                    }

                    if (maxValueModel != null) {
                        maxValue = maxValueModel.getValue();
                        Element maxvalueElement = new Element("maxValue");
                        List<Attribute> attribValue = maxvalueElement
                                .getAttributes();
                        attribValue.add(new Attribute("value", maxValue));
                        JDomUtil.addNewElement(document,
                                allowedModulesXPath + "/plk:range",
                                POWERLINK_XDD_NAMESPACE, maxvalueElement);
                    }

                }
            }

        }

        Object paramTempladeIDRef = parameter.getTemplateIDRef();
        if (paramTempladeIDRef != null) {

            if (paramTempladeIDRef instanceof TParameterTemplate) {
                TParameterTemplate parameteratemplateModel = (TParameterTemplate) paramTempladeIDRef;

                if (JDomUtil.isXpathPresent(document, TEMPLATE_LIST_XPATH,
                        POWERLINK_XDD_NAMESPACE)) {
                    updatePrameterTemplateListInHeadNode(document,
                            parameteratemplateModel);
                } else {
                    Element newTempElement = new Element(TEMPLATE_LIST);
                    JDomUtil.addNewElement(document, APPLICATION_PROCESS_XPATH,
                            POWERLINK_XDD_NAMESPACE, newTempElement);

                    updatePrameterTemplateListInHeadNode(document,
                            parameteratemplateModel);
                }
            }

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

    private static void updateParameterReferenceInXDC(Document document,
            Module module, PowerlinkObject plkSubObj,
            ParameterRef parameterReferenceModel, String childPrmXpath) {
        String uniqueId = plkSubObj
                .getUniqueId(parameterReferenceModel.getUniqueIDRef());

        String parameterUniqueId = uniqueId;
        String newUniqueId = OpenConfiguratorLibraryUtils
                .getModuleParameterUniqueID(module, parameterUniqueId);

        Element newParamRef = new Element(PARAMETER_REFERENCE);
        List<Attribute> attribList = newParamRef.getAttributes();
        attribList.add(new Attribute("uniqueIDRef", newUniqueId));
        if (parameterReferenceModel.getActualValue() != null) {
            attribList.add(new Attribute("actualValue",
                    parameterReferenceModel.getActualValue()));
        }

        attribList.add(new Attribute("locked", plkSubObj
                .getlockedParameter(parameterReferenceModel.isLocked())));

        attribList.add(new Attribute("visible", plkSubObj
                .getVisibleParameter(parameterReferenceModel.isVisible())));

        if (parameterReferenceModel.getBitOffset() != null) {
            attribList.add(new Attribute("bitOffset",
                    String.valueOf(parameterReferenceModel.getBitOffset())));
        }

        JDomUtil.addNewElement(document, childPrmXpath, POWERLINK_XDD_NAMESPACE,
                newParamRef);

        if (parameterReferenceModel
                .getUniqueIDRef() instanceof TParameterList.Parameter) {
            TParameterList.Parameter param = (TParameterList.Parameter) parameterReferenceModel
                    .getUniqueIDRef();
            updateParameterListToHeadXDC(document, param, module);

        }

    }

    private static void updateParameterReferenceInXDC(Document document,
            Module module, PowerlinkSubobject plkSubObj,
            ParameterRef parameterReferenceModel, String childPrmXpath) {
        String uniqueId = plkSubObj
                .getUniqueId(parameterReferenceModel.getUniqueIDRef());

        String parameterUniqueId = uniqueId;
        String newUniqueId = OpenConfiguratorLibraryUtils
                .getModuleParameterUniqueID(module, parameterUniqueId);

        Element newParamRef = new Element(PARAMETER_REFERENCE);
        List<Attribute> attribList = newParamRef.getAttributes();
        attribList.add(new Attribute("uniqueIDRef", newUniqueId));
        if (parameterReferenceModel.getActualValue() != null) {
            attribList.add(new Attribute("actualValue",
                    parameterReferenceModel.getActualValue()));
        }

        attribList.add(new Attribute("locked", plkSubObj
                .getlockedParameter(parameterReferenceModel.isLocked())));

        attribList.add(new Attribute("visible", plkSubObj
                .getVisibleParameter(parameterReferenceModel.isVisible())));

        if (parameterReferenceModel.getBitOffset() != null) {
            attribList.add(new Attribute("bitOffset",
                    String.valueOf(parameterReferenceModel.getBitOffset())));
        }

        JDomUtil.addNewElement(document, childPrmXpath, POWERLINK_XDD_NAMESPACE,
                newParamRef);

        if (parameterReferenceModel
                .getUniqueIDRef() instanceof TParameterList.Parameter) {
            TParameterList.Parameter param = (TParameterList.Parameter) parameterReferenceModel
                    .getUniqueIDRef();
            updateParameterListsToHeadXDC(document, param, module);

        }

    }

    @SuppressWarnings("unused")
    private static void updatePrameterTemplateListInHeadNode(Document document,
            TParameterTemplate parameteratemplateModel) {

        String parameterTemplateUniqueID = parameteratemplateModel
                .getUniqueID();
        String accessparamTemplate = parameteratemplateModel.getAccess();

        if (accessparamTemplate == null) {
            accessparamTemplate = "undefined";
        }

        Element newParamTempElement = new Element("parameterTemplate");
        List<Attribute> attribList = newParamTempElement.getAttributes();
        attribList.add(new Attribute("uniqueID", parameterTemplateUniqueID));
        attribList.add(new Attribute("access", accessparamTemplate));

        JDomUtil.addNewElement(document, TEMPLATE_LIST_XPATH,
                POWERLINK_XDD_NAMESPACE, newParamTempElement);

        String parameterTemplateXpath = TEMPLATE_LIST_XPATH
                + "/plk:parameterTemplate[@uniqueID='"
                + parameterTemplateUniqueID + "']";
        Element newParamTempLabelElement = new Element("label");
        List<Attribute> attribListlbl = newParamTempLabelElement
                .getAttributes();
        attribListlbl.add(new Attribute("lang", "en-us"));
        JDomUtil.addNewElement(document, parameterTemplateXpath,
                POWERLINK_XDD_NAMESPACE, newParamTempLabelElement);
        Element dataTypeElement = new Element(
                getIEC_DataType(parameteratemplateModel));
        JDomUtil.addNewElement(document, parameterTemplateXpath,
                POWERLINK_XDD_NAMESPACE, dataTypeElement);

        Element actualValueElement = new Element("actualValue");
        List<Attribute> attribListactual = actualValueElement.getAttributes();

        if (parameteratemplateModel.getActualValue() != null) {
            attribListactual.add(new Attribute("value",
                    parameteratemplateModel.getActualValue().getValue()));
            JDomUtil.addNewElement(document, parameterTemplateXpath,
                    POWERLINK_XDD_NAMESPACE, actualValueElement);
        }

        Element defaultValueElement = new Element("defaultValue");
        List<Attribute> attribListdefault = defaultValueElement.getAttributes();
        if (parameteratemplateModel.getDefaultValue() != null) {
            attribListdefault.add(new Attribute("value",
                    parameteratemplateModel.getDefaultValue().getValue()));
            JDomUtil.addNewElement(document, parameterTemplateXpath,
                    POWERLINK_XDD_NAMESPACE, defaultValueElement);
        }

        TAllowedValues allowedValuesModel = parameteratemplateModel
                .getAllowedValues();
        if (allowedValuesModel != null) {
            String allowedModulesXPath = parameterTemplateXpath
                    + "/plk:allowedValues";
            Element allowedValueElement = new Element("allowedValues");
            JDomUtil.addNewElement(document, parameterTemplateXpath,
                    POWERLINK_XDD_NAMESPACE, allowedValueElement);
            List<TValue> parameterAllowedValuesList = allowedValuesModel
                    .getValue();
            // Create a string collection with allowed values list.
            for (TValue parameterAllowedValue : parameterAllowedValuesList) {
                Element valueElement = new Element("value");
                List<Attribute> attribValue = valueElement.getAttributes();
                attribValue.add(new Attribute("value",
                        parameterAllowedValue.getValue()));
                JDomUtil.addNewElement(document, allowedModulesXPath,
                        POWERLINK_XDD_NAMESPACE, valueElement);

            }

            List<TRange> rangeList = allowedValuesModel.getRange();
            for (TRange range : rangeList) {

                TRange.MinValue minValueModel = range.getMinValue();
                TRange.MaxValue maxValueModel = range.getMaxValue();

                String minValue = StringUtils.EMPTY;
                String maxValue = StringUtils.EMPTY;

                if (minValueModel != null) {
                    minValue = minValueModel.getValue();
                    Element valueElement = new Element("range");

                    JDomUtil.addNewElement(document, allowedModulesXPath,
                            POWERLINK_XDD_NAMESPACE, valueElement);

                    Element minvalueElement = new Element("minValue");
                    List<Attribute> attribValue = minvalueElement
                            .getAttributes();
                    attribValue.add(new Attribute("value", maxValue));
                    JDomUtil.addNewElement(document,
                            allowedModulesXPath + "/plk:range",
                            POWERLINK_XDD_NAMESPACE, minvalueElement);
                }

                if (maxValueModel != null) {
                    maxValue = maxValueModel.getValue();
                    Element maxvalueElement = new Element("maxValue");
                    List<Attribute> attribValue = maxvalueElement
                            .getAttributes();
                    attribValue.add(new Attribute("value", maxValue));
                    JDomUtil.addNewElement(document,
                            allowedModulesXPath + "/plk:range",
                            POWERLINK_XDD_NAMESPACE, maxvalueElement);
                }

            }
        }

    }

    private static void updateStructDataType(Document document, Object object) {
        String dataTypeListXpath = APPLICATION_PROCESS_XPATH + "/plk:"
                + DATATYPE_LIST;

        if (JDomUtil.isXpathPresent(document, dataTypeListXpath,
                POWERLINK_XDD_NAMESPACE)) {

            if (object instanceof TDataTypeList.Struct) {
                TDataTypeList.Struct structDt = (TDataTypeList.Struct) object;
                Element newStructElement = new Element("struct");
                List<Attribute> attribList = newStructElement.getAttributes();
                attribList.add(new Attribute("name", structDt.getName()));
                attribList
                        .add(new Attribute("uniqueID", structDt.getUniqueID()));
                JDomUtil.addNewElements(document, dataTypeListXpath,
                        POWERLINK_XDD_NAMESPACE, newStructElement);

                String parameterStructXpath = dataTypeListXpath
                        + "/plk:struct[@uniqueID='" + structDt.getUniqueID()
                        + "']";
                Element newStructTempLabelElement = new Element("label");
                List<Attribute> attribListlbl = newStructTempLabelElement
                        .getAttributes();
                attribListlbl.add(new Attribute("lang", "en-us"));
                newStructTempLabelElement.setText(new LabelDescription(
                        structDt.getLabelOrDescriptionOrLabelRef()).getText());
                JDomUtil.addNewElements(document, parameterStructXpath,
                        POWERLINK_XDD_NAMESPACE, newStructTempLabelElement);

                List<TVarDeclaration> varDeclist = structDt.getVarDeclaration();

                for (TVarDeclaration varDecl : varDeclist) {
                    Element newStructVarDeclarationElement = new Element(
                            "varDeclaration");
                    List<Attribute> attribListvar = newStructVarDeclarationElement
                            .getAttributes();
                    attribListvar.add(new Attribute("name", varDecl.getName()));
                    attribListvar.add(
                            new Attribute("uniqueID", varDecl.getUniqueID()));
                    JDomUtil.addNewElements(document, parameterStructXpath,
                            POWERLINK_XDD_NAMESPACE,
                            newStructVarDeclarationElement);

                    String varDeclXpath = parameterStructXpath
                            + "/plk:varDeclaration[@uniqueID='"
                            + varDecl.getUniqueID() + "']";
                    Element dataTypeElement = new Element(
                            getIEC_DataType(varDecl));
                    JDomUtil.addNewElements(document, varDeclXpath,
                            POWERLINK_XDD_NAMESPACE, dataTypeElement);

                }

            }
        } else {
            Element newParamElement = new Element(DATATYPE_LIST);
            JDomUtil.addNewElement(document, APPLICATION_PROCESS_XPATH,
                    POWERLINK_XDD_NAMESPACE, newParamElement);

            if (object instanceof TDataTypeList.Struct) {
                TDataTypeList.Struct structDt = (TDataTypeList.Struct) object;
                Element newStructElement = new Element("struct");
                List<Attribute> attribList = newStructElement.getAttributes();
                attribList.add(new Attribute("name", structDt.getName()));
                attribList
                        .add(new Attribute("uniqueID", structDt.getUniqueID()));
                JDomUtil.addNewElements(document, dataTypeListXpath,
                        POWERLINK_XDD_NAMESPACE, newStructElement);

                String parameterStructXpath = dataTypeListXpath
                        + "/plk:struct[@uniqueID='" + structDt.getUniqueID()
                        + "']";
                Element newStructTempLabelElement = new Element("label");
                List<Attribute> attribListlbl = newStructTempLabelElement
                        .getAttributes();
                attribListlbl.add(new Attribute("lang", "en-us"));
                newStructTempLabelElement.setText(new LabelDescription(
                        structDt.getLabelOrDescriptionOrLabelRef()).getText());
                JDomUtil.addNewElements(document, parameterStructXpath,
                        POWERLINK_XDD_NAMESPACE, newStructTempLabelElement);

                List<TVarDeclaration> varDeclist = structDt.getVarDeclaration();

                for (TVarDeclaration varDecl : varDeclist) {
                    Element newStructVarDeclarationElement = new Element(
                            "varDeclaration");
                    List<Attribute> attribListvar = newStructVarDeclarationElement
                            .getAttributes();
                    attribListvar.add(new Attribute("name", varDecl.getName()));
                    attribListvar.add(
                            new Attribute("uniqueID", varDecl.getUniqueID()));
                    JDomUtil.addNewElements(document, parameterStructXpath,
                            POWERLINK_XDD_NAMESPACE,
                            newStructVarDeclarationElement);

                    String varDeclXpath = parameterStructXpath
                            + "/plk:varDeclaration[@uniqueID='"
                            + varDecl.getUniqueID() + "']";
                    Element dataTypeElement = new Element(
                            getIEC_DataType(varDecl));
                    JDomUtil.addNewElements(document, varDeclXpath,
                            POWERLINK_XDD_NAMESPACE, dataTypeElement);

                }

            }
        }
    }
}
