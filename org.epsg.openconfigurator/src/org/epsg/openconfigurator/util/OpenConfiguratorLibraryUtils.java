/*******************************************************************************
 * @file   OpenCONFIGURATORLibraryUtils.java
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

package org.epsg.openconfigurator.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.AccessType;
import org.epsg.openconfigurator.lib.wrapper.AssignmentCollection;
import org.epsg.openconfigurator.lib.wrapper.CNFeatureEnum;
import org.epsg.openconfigurator.lib.wrapper.Direction;
import org.epsg.openconfigurator.lib.wrapper.DynamicChannelAccessType;
import org.epsg.openconfigurator.lib.wrapper.ErrorCode;
import org.epsg.openconfigurator.lib.wrapper.GeneralFeatureEnum;
import org.epsg.openconfigurator.lib.wrapper.IEC_Datatype;
import org.epsg.openconfigurator.lib.wrapper.MNFeatureEnum;
import org.epsg.openconfigurator.lib.wrapper.MapIterator;
import org.epsg.openconfigurator.lib.wrapper.ModuleAddressing;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.ObjectCollection;
import org.epsg.openconfigurator.lib.wrapper.ObjectType;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.PDOMapping;
import org.epsg.openconfigurator.lib.wrapper.ParameterAccess;
import org.epsg.openconfigurator.lib.wrapper.PlkDataType;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.lib.wrapper.SortMode;
import org.epsg.openconfigurator.lib.wrapper.SortNumber;
import org.epsg.openconfigurator.lib.wrapper.StringCollection;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.NetworkManagement;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.Node.NodeType;
import org.epsg.openconfigurator.model.ObjectDictionary;
import org.epsg.openconfigurator.model.PdoChannel;
import org.epsg.openconfigurator.model.PdoType;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.resources.IOpenConfiguratorResource;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAbstractNode;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAbstractNode.ForcedObjects;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAutoGenerationSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TKeyValuePair;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlinkModularChild;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlinkModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularChild;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.Range;
import org.epsg.openconfigurator.xmlbinding.xdd.TAddressingAttribute;
import org.epsg.openconfigurator.xmlbinding.xdd.TAllowedValues;
import org.epsg.openconfigurator.xmlbinding.xdd.TApplicationLayers;
import org.epsg.openconfigurator.xmlbinding.xdd.TApplicationLayersModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.TApplicationProcess;
import org.epsg.openconfigurator.xmlbinding.xdd.TCNFeatures;
import org.epsg.openconfigurator.xmlbinding.xdd.TDataTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.TDynamicChannel;
import org.epsg.openconfigurator.xmlbinding.xdd.TEnumValue;
import org.epsg.openconfigurator.xmlbinding.xdd.TGeneralFeatures;
import org.epsg.openconfigurator.xmlbinding.xdd.TMNFeatures;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleAddressingChild;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleAddressingHead;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectAccessType;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectPDOMapping;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterGroup;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterGroupList;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList.Parameter;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterTemplate;
import org.epsg.openconfigurator.xmlbinding.xdd.TRange;
import org.epsg.openconfigurator.xmlbinding.xdd.TSortMode;
import org.epsg.openconfigurator.xmlbinding.xdd.TSubrange;
import org.epsg.openconfigurator.xmlbinding.xdd.TTemplateList;
import org.epsg.openconfigurator.xmlbinding.xdd.TValue;
import org.epsg.openconfigurator.xmlbinding.xdd.TVarDeclaration;

/**
 * An utility class to group all the methods that are used to communicate with
 * the library.
 *
 * @author Ramakrishnan P
 *
 */
public class OpenConfiguratorLibraryUtils {

    private static Result addChildModuleParameterGroupReference(
            String networkId, short nodeId,
            TParameterGroup parentParameterGroup, Module module) {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Result libApiRes = new Result();
        String interfaceId = module.getInterfaceOfModule().getInterfaceUId();
        String moduleId = module.getChildID();
        long modulePosition = module.getPosition();
        List<Object> parameterGroupReferenceList = parentParameterGroup
                .getParameterGroupOrParameterRef();

        if (parameterGroupReferenceList.isEmpty()) {
            return libApiRes;
        }

        String parentParameterGroupUniqueId = parentParameterGroup
                .getUniqueID();

        for (Object parameterGroupReference : parameterGroupReferenceList) {
            if (parameterGroupReference instanceof TParameterGroup) {
                TParameterGroup parameterGrp = (TParameterGroup) parameterGroupReference;

                String parameterGroupUniqueId = parameterGrp.getUniqueID();
                if (parameterGroupUniqueId == null) {
                    // FIXME:
                    System.err.println("ERROR");
                    continue;
                }

                System.out.println(
                        "Create Parameter Group : Child parameter goup uniqueID = "
                                + parameterGroupUniqueId
                                + "\n Parent param group unique ID = "
                                + parentParameterGroupUniqueId);

                int bitOffset = parameterGrp.getBitOffset().intValue();

                Object conditionalObjectModel = parameterGrp
                        .getConditionalUniqueIDRef();
                String conditionalUniqueId = StringUtils.EMPTY;
                if (conditionalObjectModel != null) {
                    if (conditionalObjectModel instanceof TParameterList.Parameter) {
                        TParameterList.Parameter parameter = (TParameterList.Parameter) conditionalObjectModel;
                        conditionalUniqueId = parameter.getUniqueID();
                    } else {
                        System.err.println(
                                "ParameterGroup conditional unique id is not an instance of Parameter.! ");
                        continue;
                    }

                    String conditionalValue = parameterGrp
                            .getConditionalValue();
                    if (conditionalValue == null) {
                        conditionalValue = StringUtils.EMPTY;
                    }

                    libApiRes = core.CreateParameterGroup(networkId, nodeId,
                            parameterGroupUniqueId,
                            parentParameterGroupUniqueId, conditionalUniqueId,
                            conditionalValue, bitOffset, interfaceId, moduleId,
                            modulePosition);
                    if (!libApiRes.IsSuccessful()) {
                        System.err
                                .println("Create Module parameter group WARN: "
                                        + getErrorMessage(libApiRes));
                        continue;
                    }
                } else {
                    libApiRes = core.CreateParameterGroup(networkId, nodeId,
                            parameterGroupUniqueId,
                            parentParameterGroupUniqueId, bitOffset,
                            interfaceId, moduleId, modulePosition);
                    if (!libApiRes.IsSuccessful()) {
                        System.err
                                .println("Create Module parameter group WARN: "
                                        + getErrorMessage(libApiRes));
                        continue;
                    }
                }

                libApiRes = addChildModuleParameterGroupReference(networkId,
                        nodeId, parameterGrp, module);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("Create Module parameter group WARN: "
                            + getErrorMessage(libApiRes));
                }
            } else if (parameterGroupReference instanceof TParameterGroup.ParameterRef) {
                TParameterGroup.ParameterRef paramRef = (TParameterGroup.ParameterRef) parameterGroupReference;
                Object paramRefUniqueIdRef = paramRef.getUniqueIDRef();
                if (paramRefUniqueIdRef instanceof Parameter) {
                    Parameter referencedParameter = (Parameter) paramRefUniqueIdRef;

                    String referencedParamUId = referencedParameter
                            .getUniqueID();
                    String paramRefActualValue = paramRef.getActualValue();

                    int bitOffset = paramRef.getBitOffset().intValue();

                    if (paramRefActualValue == null) {

                        if ((bitOffset != 0)) {
                            libApiRes = core.CreateParameterReference(networkId,
                                    nodeId, parentParameterGroupUniqueId,
                                    referencedParamUId, "", bitOffset,
                                    interfaceId, moduleId, modulePosition);
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println(
                                        "Create Module parameter Reference without actual value WARN: "
                                                + getErrorMessage(libApiRes));
                            }
                        } else {
                            libApiRes = core.CreateParameterReference(networkId,
                                    nodeId, parentParameterGroupUniqueId,
                                    referencedParamUId, "", 0, interfaceId,
                                    moduleId, modulePosition);
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println(
                                        "Create Module parameter Reference without actual value WARN: "
                                                + getErrorMessage(libApiRes));
                            }
                        }
                    } else {
                        System.err.println("Parameter ref bit offset = "
                                + paramRef.getBitOffset().intValue());
                        libApiRes = core.CreateParameterReference(networkId,
                                nodeId, parentParameterGroupUniqueId,
                                referencedParamUId, paramRefActualValue,
                                paramRef.getBitOffset().intValue(), interfaceId,
                                moduleId, modulePosition);
                        if (!libApiRes.IsSuccessful()) {
                            System.err.println(
                                    "Create Module parameter Reference with actual value WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                    }
                } else {
                    System.err.println(
                            "ERRROR: ParameterGroupRef contains invalid paramRefUniqueId instance"
                                    + paramRefUniqueIdRef);
                }
            } else {
                System.err.println(
                        "ERRROR: ParameterGroup contains invalid instance"
                                + parameterGroupReference);
            }
        }
        return libApiRes;
    }

    private static Result addChildParameterGroupReference(String networkId,
            short nodeId, TParameterGroup parentParameterGroup) {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Result libApiRes = new Result();
        String interfaceId = StringUtils.EMPTY;
        String moduleId = StringUtils.EMPTY;
        long position = 0;
        List<Object> parameterGroupReferenceList = parentParameterGroup
                .getParameterGroupOrParameterRef();

        if (parameterGroupReferenceList.isEmpty()) {
            return libApiRes;
        }

        String parentParameterGroupUniqueId = parentParameterGroup
                .getUniqueID();

        for (Object parameterGroupReference : parameterGroupReferenceList) {
            if (parameterGroupReference instanceof TParameterGroup) {
                TParameterGroup parameterGrp = (TParameterGroup) parameterGroupReference;

                String parameterGroupUniqueId = parameterGrp.getUniqueID();
                if (parameterGroupUniqueId == null) {
                    // FIXME:
                    System.err.println("ERROR");
                    continue;
                }

                System.out.println(
                        "Create Parameter Group : Child parameter goup uniqueID = "
                                + parameterGroupUniqueId
                                + "\n Parent param group unique ID = "
                                + parentParameterGroupUniqueId);

                int bitOffset = parameterGrp.getBitOffset().intValue();

                Object conditionalObjectModel = parameterGrp
                        .getConditionalUniqueIDRef();
                String conditionalUniqueId = StringUtils.EMPTY;
                if (conditionalObjectModel != null) {
                    if (conditionalObjectModel instanceof TParameterList.Parameter) {
                        TParameterList.Parameter parameter = (TParameterList.Parameter) conditionalObjectModel;
                        conditionalUniqueId = parameter.getUniqueID();
                    } else {
                        System.err.println(
                                "ParameterGroup conditional unique id is not an instance of Parameter.! ");
                        continue;
                    }

                    String conditionalValue = parameterGrp
                            .getConditionalValue();
                    if (conditionalValue == null) {
                        conditionalValue = StringUtils.EMPTY;
                    }

                    libApiRes = core.CreateParameterGroup(networkId, nodeId,
                            parameterGroupUniqueId,
                            parentParameterGroupUniqueId, conditionalUniqueId,
                            conditionalValue, bitOffset, interfaceId, moduleId,
                            position);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("Create parameter group WARN: "
                                + getErrorMessage(libApiRes));
                        continue;
                    }
                } else {
                    libApiRes = core.CreateParameterGroup(networkId, nodeId,
                            parameterGroupUniqueId,
                            parentParameterGroupUniqueId, bitOffset,
                            interfaceId, moduleId, position);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("Create parameter group WARN: "
                                + getErrorMessage(libApiRes));
                        continue;
                    }
                }

                libApiRes = addChildParameterGroupReference(networkId, nodeId,
                        parameterGrp);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("Create parameter group WARN: "
                            + getErrorMessage(libApiRes));
                }
            } else if (parameterGroupReference instanceof TParameterGroup.ParameterRef) {
                TParameterGroup.ParameterRef paramRef = (TParameterGroup.ParameterRef) parameterGroupReference;
                Object paramRefUniqueIdRef = paramRef.getUniqueIDRef();
                if (paramRefUniqueIdRef instanceof Parameter) {
                    Parameter referencedParameter = (Parameter) paramRefUniqueIdRef;

                    String referencedParamUId = referencedParameter
                            .getUniqueID();
                    String paramRefActualValue = paramRef.getActualValue();

                    int bitOffset = paramRef.getBitOffset().intValue();

                    if (paramRefActualValue == null) {

                        if ((bitOffset != 0)) {
                            libApiRes = core.CreateParameterReference(networkId,
                                    nodeId, parentParameterGroupUniqueId,
                                    referencedParamUId, "", bitOffset,
                                    interfaceId, moduleId, position);
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println(
                                        "Create parameter Reference without actual value WARN: "
                                                + getErrorMessage(libApiRes));
                            }
                        } else {
                            libApiRes = core.CreateParameterReference(networkId,
                                    nodeId, parentParameterGroupUniqueId,
                                    referencedParamUId, "", 0, interfaceId,
                                    moduleId, position);
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println(
                                        "Create parameter Reference without actual value WARN: "
                                                + getErrorMessage(libApiRes));
                            }
                        }
                    } else {
                        System.err.println("Parameter ref bit offset = "
                                + paramRef.getBitOffset().intValue());
                        libApiRes = core.CreateParameterReference(networkId,
                                nodeId, parentParameterGroupUniqueId,
                                referencedParamUId, paramRefActualValue,
                                paramRef.getBitOffset().intValue(), interfaceId,
                                moduleId, position);
                        if (!libApiRes.IsSuccessful()) {
                            System.err.println(
                                    "Create parameter Reference with actual value WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                    }
                } else {
                    System.err.println(
                            "ERRROR: ParameterGroupRef contains invalid paramRefUniqueId instance"
                                    + paramRefUniqueIdRef);
                }
            } else {
                System.err.println(
                        "ERRROR: ParameterGroup contains invalid instance"
                                + parameterGroupReference);
            }
        }
        return libApiRes;
    }

    /**
     * Add the datatype list from the XDC into the library.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param dataTypeList The datatype list instance.
     * @return Result instance from the library.
     */
    private static Result addDataTypeList(final String networkId,
            final short nodeId, TDataTypeList dataTypeList) {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Result libApiRes = new Result();

        if (dataTypeList == null) {
            return libApiRes;
        }

        List<Object> dtObjectList = dataTypeList.getArrayOrStructOrEnum();
        for (Object dtObject : dtObjectList) {
            if (dtObject instanceof TDataTypeList.Struct) {

                TDataTypeList.Struct structDt = (TDataTypeList.Struct) dtObject;
                String structUniqueId = structDt.getUniqueID();
                if (structUniqueId == null) {
                    structUniqueId = StringUtils.EMPTY;
                }

                String structName = structDt.getName();
                if (structName == null) {
                    structName = StringUtils.EMPTY;
                }

                System.out.println("CreateStructDatatype" + " nodeId:" + nodeId
                        + " structUniqueId:" + structUniqueId + " structName:"
                        + structName);

                libApiRes = core.CreateStructDatatype(networkId, nodeId,
                        structUniqueId, structName);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("CreateStructDatatype WARN: "
                            + getErrorMessage(libApiRes));
                    continue;
                }

                List<TVarDeclaration> varDeclList = structDt
                        .getVarDeclaration();
                for (TVarDeclaration varDecl : varDeclList) {

                    long varDeclSize = 0;
                    if (varDecl.getSize() != null) {
                        varDeclSize = varDecl.getSize().longValue();
                    }

                    String varDeclName = varDecl.getName();
                    if (varDeclName == null) {
                        varDeclName = StringUtils.EMPTY;
                    }

                    String initialValue = varDecl.getInitialValue();
                    if (initialValue == null) {
                        initialValue = StringUtils.EMPTY;
                    }

                    String varDeclUniqueId = varDecl.getUniqueID();
                    if (varDeclUniqueId == null) {
                        varDeclUniqueId = StringUtils.EMPTY;
                    }

                    IEC_Datatype iecDataType = getIEC_DataType(varDecl);

                    System.out.println("CreateVarDeclaration: varDeclUniqueId:"
                            + varDeclUniqueId + " varDeclName:" + varDeclName
                            + " Dt:" + iecDataType + " size:" + varDeclSize
                            + " value:" + initialValue);

                    libApiRes = core.CreateVarDeclaration(networkId, nodeId,
                            structUniqueId, varDeclUniqueId, varDeclName,
                            iecDataType, varDeclSize, initialValue);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("CreateVarDeclaration WARN: "
                                + getErrorMessage(libApiRes));
                    }
                }
            } else if (dtObject instanceof TDataTypeList.Array) {

                TDataTypeList.Array arrayDt = (TDataTypeList.Array) dtObject;
                List<TSubrange> subRangeList = arrayDt.getSubrange();
                TSubrange subRange = subRangeList.get(0);

                // arrayDt.getDataTypeIDRef().getUniqueIDRef().toString();
                IEC_Datatype iecDataType = getIEC_DataType(arrayDt);

                libApiRes = core.CreateArrayDatatype(networkId, nodeId,
                        arrayDt.getUniqueID(), arrayDt.getName(),
                        subRange.getLowerLimit().longValue(),
                        subRange.getUpperLimit().longValue(), iecDataType);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("CreateArrayDatatype WARN: "
                            + getErrorMessage(libApiRes));
                }
            } else if (dtObject instanceof TDataTypeList.Enum) {

                TDataTypeList.Enum enumDt = (TDataTypeList.Enum) dtObject;
                if (enumDt.getUniqueID() != null) {
                    List<TEnumValue> enumValueList = enumDt.getEnumValue();
                    for (TEnumValue value : enumValueList) {
                        if ((value.getValue() != null)
                                || (!value.getValue().isEmpty())) {
                            libApiRes = core.CreateEnumValue(networkId, nodeId,
                                    enumDt.getUniqueID(), enumDt.getName(),
                                    value.getValue());
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println("CreateEnumDatatype WARN: "
                                        + getErrorMessage(libApiRes));
                            }
                        }
                    }

                    IEC_Datatype iecDataType = getIEC_DataType(enumDt);

                    if (iecDataType != null) {
                        libApiRes = core.CreateEnumDatatype(networkId, nodeId,
                                enumDt.getUniqueID(), enumDt.getName(),
                                iecDataType);
                        if (!libApiRes.IsSuccessful()) {
                            System.err.println("CreateEnumDatatype WARN: "
                                    + getErrorMessage(libApiRes));
                        }

                        if (enumDt.getSize() != null) {
                            libApiRes = core.CreateEnumDatatype(networkId,
                                    nodeId, enumDt.getUniqueID(),
                                    enumDt.getName(), iecDataType,
                                    enumDt.getSize().intValue());
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println("CreateEnumDatatype WARN: "
                                        + getErrorMessage(libApiRes));
                            }
                        }
                    }
                }
            } else if (dtObject instanceof TDataTypeList.Derived) {
                // TODO: Add derived datatype list
            }
        }

        return libApiRes;
    }

    private static Result addDataTypeList(String networkId, short nodeId,
            TDataTypeList dataTypeList, Module module) {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Result libApiRes = new Result();
        String interfaceId = module.getInterfaceOfModule().getInterfaceUId();
        String moduleId = module.getChildID();
        long position = module.getPosition();
        if (dataTypeList == null) {
            return libApiRes;
        }

        List<Object> dtObjectList = dataTypeList.getArrayOrStructOrEnum();
        for (Object dtObject : dtObjectList) {
            if (dtObject instanceof TDataTypeList.Struct) {

                TDataTypeList.Struct structDt = (TDataTypeList.Struct) dtObject;
                String structUniqueId = structDt.getUniqueID();
                if (structUniqueId == null) {
                    structUniqueId = StringUtils.EMPTY;
                }

                String structName = structDt.getName();
                if (structName == null) {
                    structName = StringUtils.EMPTY;
                }

                System.out.println("CreateStructDatatype" + " nodeId:" + nodeId
                        + " structUniqueId:" + structUniqueId + " structName:"
                        + structName);

                libApiRes = core.CreateStructDatatype(networkId, nodeId,
                        structUniqueId, structName, interfaceId, moduleId,
                        position);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("CreateStructDatatype WARN: "
                            + getErrorMessage(libApiRes));
                    continue;
                }

                List<TVarDeclaration> varDeclList = structDt
                        .getVarDeclaration();
                for (TVarDeclaration varDecl : varDeclList) {

                    long varDeclSize = 0;
                    if (varDecl.getSize() != null) {
                        varDeclSize = varDecl.getSize().longValue();
                    }

                    String varDeclName = varDecl.getName();
                    if (varDeclName == null) {
                        varDeclName = StringUtils.EMPTY;
                    }

                    String initialValue = varDecl.getInitialValue();
                    if (initialValue == null) {
                        initialValue = StringUtils.EMPTY;
                    }

                    String varDeclUniqueId = varDecl.getUniqueID();
                    if (varDeclUniqueId == null) {
                        varDeclUniqueId = StringUtils.EMPTY;
                    }

                    IEC_Datatype iecDataType = getIEC_DataType(varDecl);

                    System.out.println("CreateVarDeclaration: varDeclUniqueId:"
                            + varDeclUniqueId + " varDeclName:" + varDeclName
                            + " Dt:" + iecDataType + " size:" + varDeclSize
                            + " value:" + initialValue);

                    libApiRes = core.CreateVarDeclaration(networkId, nodeId,
                            structUniqueId, varDeclUniqueId, varDeclName,
                            iecDataType, varDeclSize, initialValue, interfaceId,
                            moduleId, position);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("CreateVarDeclaration WARN: "
                                + getErrorMessage(libApiRes));
                    }
                }
            } else if (dtObject instanceof TDataTypeList.Array) {

                TDataTypeList.Array arrayDt = (TDataTypeList.Array) dtObject;
                List<TSubrange> subRangeList = arrayDt.getSubrange();
                TSubrange subRange = subRangeList.get(0);

                // arrayDt.getDataTypeIDRef().getUniqueIDRef().toString();
                IEC_Datatype iecDataType = getIEC_DataType(arrayDt);

                libApiRes = core.CreateArrayDatatype(networkId, nodeId,
                        arrayDt.getUniqueID(), arrayDt.getName(),
                        subRange.getLowerLimit().longValue(),
                        subRange.getUpperLimit().longValue(), iecDataType,
                        interfaceId, moduleId, position);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("CreateArrayDatatype WARN: "
                            + getErrorMessage(libApiRes));
                }
            } else if (dtObject instanceof TDataTypeList.Enum) {

                TDataTypeList.Enum enumDt = (TDataTypeList.Enum) dtObject;
                if (enumDt.getUniqueID() != null) {
                    List<TEnumValue> enumValueList = enumDt.getEnumValue();
                    for (TEnumValue value : enumValueList) {
                        if ((value.getValue() != null)
                                || (!value.getValue().isEmpty())) {
                            libApiRes = core.CreateEnumValue(networkId, nodeId,
                                    enumDt.getUniqueID(), enumDt.getName(),
                                    value.getValue(), interfaceId, moduleId,
                                    position);
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println("CreateEnumDatatype WARN: "
                                        + getErrorMessage(libApiRes));
                            }
                        }
                    }

                    IEC_Datatype iecDataType = getIEC_DataType(enumDt);

                    if (iecDataType != null) {
                        libApiRes = core.CreateEnumDatatype(networkId, nodeId,
                                enumDt.getUniqueID(), enumDt.getName(),
                                iecDataType, 0, interfaceId, moduleId,
                                position);
                        if (!libApiRes.IsSuccessful()) {
                            System.err.println("CreateEnumDatatype WARN: "
                                    + getErrorMessage(libApiRes));
                        }

                        if (enumDt.getSize() != null) {
                            libApiRes = core.CreateEnumDatatype(networkId,
                                    nodeId, enumDt.getUniqueID(),
                                    enumDt.getName(), iecDataType,
                                    enumDt.getSize().intValue(), interfaceId,
                                    moduleId, position);
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println("CreateEnumDatatype WARN: "
                                        + getErrorMessage(libApiRes));
                            }
                        }
                    }
                }
            } else if (dtObject instanceof TDataTypeList.Derived) {
                // TODO: Add derived datatype list
            }
        }

        return libApiRes;
    }

    /**
     * Add dynamic channels into the library for TApplication layer model.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param dynamicChannels Instance of TApplication layer model.
     * @return Result instance from the library.
     */
    private static Result addDynamicChannels(String networkId, short nodeId,
            TApplicationLayers.DynamicChannels dynamicChannels) {
        Result libApiRes = new Result();
        if (dynamicChannels == null) {
            return libApiRes;
        }

        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        List<TDynamicChannel> dynamicChannelsList = dynamicChannels
                .getDynamicChannel();
        for (TDynamicChannel dynamicChannel : dynamicChannelsList) {

            PlkDataType dataType = getObjectDatatype(
                    dynamicChannel.getDataType());

            DynamicChannelAccessType accessType = getDynamicChannelAccessType(
                    dynamicChannel.getAccessType());

            long startIndex = 0;
            if (dynamicChannel.getStartIndex() != null) {
                String startIndexStr = DatatypeConverter
                        .printHexBinary(dynamicChannel.getStartIndex());
                startIndex = Long.parseLong(startIndexStr, 16);
            }

            long endIndex = 0;
            if (dynamicChannel.getEndIndex() != null) {
                String endIndexStr = DatatypeConverter
                        .printHexBinary(dynamicChannel.getEndIndex());
                endIndex = Long.parseLong(endIndexStr, 16);
            }

            long addressOffset = 0;
            if (dynamicChannel.getAddressOffset() != null) {
                String addressOffsetStr = DatatypeConverter
                        .printHexBinary(dynamicChannel.getAddressOffset());
                addressOffset = Long.parseLong(addressOffsetStr, 16);
            }

            short bitAlignment = 0;
            if (dynamicChannel.getBitAlignment() != null) {
                bitAlignment = dynamicChannel.getBitAlignment().shortValue();
            }
            libApiRes = core.CreateDynamicChannel(networkId, nodeId, dataType,
                    accessType, startIndex, endIndex,
                    dynamicChannel.getMaxNumber(), addressOffset, bitAlignment);
            if (!libApiRes.IsSuccessful()) {
                System.err.println("WARN: " + getErrorMessage(libApiRes));
            }
        }
        return libApiRes;
    }

    /**
     * Add dynamic channels into the library for TApplicationLayersModularHead
     * model.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param dynamicChannels Instance of TApplicationLayersModularHead.
     * @return Result instance from the library.
     */
    private static Result addDynamicChannels(String networkId, short nodeId,
            TApplicationLayersModularHead.DynamicChannels dynamicChannels) {
        Result libApiRes = new Result();
        if (dynamicChannels == null) {
            return libApiRes;
        }

        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        List<TDynamicChannel> dynamicChannelsList = dynamicChannels
                .getDynamicChannel();
        for (TDynamicChannel dynamicChannel : dynamicChannelsList) {

            PlkDataType dataType = getObjectDatatype(
                    dynamicChannel.getDataType());

            DynamicChannelAccessType accessType = getDynamicChannelAccessType(
                    dynamicChannel.getAccessType());

            long startIndex = 0;
            if (dynamicChannel.getStartIndex() != null) {
                String startIndexStr = DatatypeConverter
                        .printHexBinary(dynamicChannel.getStartIndex());
                startIndex = Long.parseLong(startIndexStr, 16);
            }

            long endIndex = 0;
            if (dynamicChannel.getEndIndex() != null) {
                String endIndexStr = DatatypeConverter
                        .printHexBinary(dynamicChannel.getEndIndex());
                endIndex = Long.parseLong(endIndexStr, 16);
            }

            long addressOffset = 0;
            if (dynamicChannel.getAddressOffset() != null) {
                String addressOffsetStr = DatatypeConverter
                        .printHexBinary(dynamicChannel.getAddressOffset());
                addressOffset = Long.parseLong(addressOffsetStr, 16);
            }

            short bitAlignment = 0;
            if (dynamicChannel.getBitAlignment() != null) {
                bitAlignment = dynamicChannel.getBitAlignment().shortValue();
            }
            libApiRes = core.CreateDynamicChannel(networkId, nodeId, dataType,
                    accessType, startIndex, endIndex,
                    dynamicChannel.getMaxNumber(), addressOffset, bitAlignment);
            if (!libApiRes.IsSuccessful()) {
                System.err.println("WARN: " + getErrorMessage(libApiRes));
            }
        }
        return libApiRes;
    }

    /**
     * Adds the Modular head node into library
     *
     * @param node Instance of Node
     * @return Result of library.
     */
    public static Result addModularHeadNode(Node node) {
        Result libApiRes = new Result();

        libApiRes = createModularHeadNode(node);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }
        // Update the LossOfSocTolerance value into library during
        // initialization of project.
        if (node.getNodeType() == NodeType.MANAGING_NODE) {
            if (node.getNodeModel() instanceof TNetworkConfiguration) {
                TNetworkConfiguration ntwrkConfg = (TNetworkConfiguration) node
                        .getNodeModel();
                long lossOfSocTolerance = ntwrkConfg.getLossOfSocTolerance()
                        .longValue();
                // Workaround:
                // only set the LossOfSocTolerance if not default value
                if (lossOfSocTolerance != IPowerlinkConstants.LOSS_OF_SOC_TOLERANCE_DEFAULT_VALUE) {
                    libApiRes = OpenConfiguratorCore.GetInstance()
                            .SetLossOfSocTolerance(node.getNetworkId(),
                                    node.getNodeId(), lossOfSocTolerance);
                    if (!libApiRes.IsSuccessful()) {
                        return libApiRes;
                    }
                } else {
                    System.out.println(
                            "LossOfSocTolerance has the default value(100000ns). Not setting to the library!!");
                }
            }
        }

        libApiRes = addNodeAssignments(node);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        libApiRes = updateForcedObjectsIntoLibary(node);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        return libApiRes;
    }

    /**
     * Adda module to library
     *
     * @param module Instance of module.
     * @return Result instance from library.
     */
    public static Result addModule(final Module module) {
        Result libApiRes = new Result();

        libApiRes = createModule(module);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        return libApiRes;

    }

    private static Result addModuleObjectDictionary(Module module,
            ObjectDictionary objectDict) throws NullPointerException {
        Result libApiRes = null;
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Node node = module.getNode();

        List<PowerlinkObject> plkObjects = objectDict.getObjectsList();
        for (PowerlinkObject object : plkObjects) {
            ObjectType objectType = getObjectType(object.getObjectType());
            PDOMapping mapping = getPdoMapping(object.getPdoMapping());

            if (((object.getObjectType() != 7) || ((object.getObjectType() == 7)
                    && (object.getDataType() != null)))
                    && (object.getUniqueIDRef() == null)) {
                // Normal objects with dataType and without uniqueIdRef.

                PlkDataType dataType = getObjectDatatype(object.getDataType());
                AccessType accessType = getAccessType(object.getAccessType());

                String actualValue = object.getActualValue();

                // An workaround to avoid the library setting the actualValue
                // for non writable sub-objects.
                if ((accessType == AccessType.CONST)
                        || (accessType == AccessType.RO)) {
                    actualValue = StringUtils.EMPTY;
                }

                libApiRes = core.CreateModuleObject(node.getNetworkId(),
                        node.getNodeId(),
                        module.getInterfaceOfModule().getInterfaceUId(),
                        module.getChildID(), module.getPosition(),
                        object.getId(), objectType, object.getName(), dataType,
                        accessType, mapping, object.getDefaultValue(),
                        actualValue, object.getRangeSelector());

                System.err.println("Module Object Index.... "
                        + getModuleObjectIndex(module));

                if (libApiRes.IsSuccessful()) {
                    libApiRes = core.SetObjectLimits(node.getNetworkId(),
                            node.getNodeId(), getModuleObjectIndex(module),
                            object.getLowLimit(), object.getHighLimit());
                    if (!libApiRes.IsSuccessful()) {
                        object.setError(getErrorMessage(libApiRes));
                        System.err.println("SetObjectLimits WARN: "
                                + getErrorMessage(libApiRes) + " Index..."
                                + getLong(object.getIndex()));
                    }
                    // else {
                    // try {
                    // OpenConfiguratorProjectUtils
                    // .updateModuleObjectIndex(module,
                    // getModuleObjectIndex(module),
                    // object);
                    // } catch (JDOMException | IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }
                    // else {
                    // try {
                    // OpenConfiguratorProjectUtils
                    // .updateModuleObjectInNode(node, object,
                    // module,
                    // getModuleObjectIndex(module));
                    // } catch (JDOMException | IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }
                } else {
                    object.setError(getErrorMessage(libApiRes));
                    OpenConfiguratorMessageConsole.getInstance()
                            .printLibraryErrorMessage(libApiRes);
                    System.err.println("WARN: " + getErrorMessage(libApiRes));
                }
            } else if ((object.getDataType() != null)
                    && (object.getUniqueIDRef() != null)) {
                if (object
                        .getUniqueIDRef() instanceof TParameterList.Parameter) {
                    Parameter parameter = (Parameter) object.getUniqueIDRef();
                    libApiRes = core
                            .CreateModuleParameterObject(node.getNetworkId(),
                                    node.getNodeId(), node.getInterface()
                                            .getInterfaceUId(),
                            module.getChildID(), module.getPosition(),
                            object.getId(), objectType, object.getName(),
                            getObjectDatatype(object.getDataType()),
                            getAccessType(object.getAccessType()), mapping,
                            parameter.getUniqueID(), object.getDefaultValue(),
                            object.getActualValue(), object.getRangeSelector());

                    if (!libApiRes.IsSuccessful()) {
                        object.setError(getErrorMessage(libApiRes));
                        OpenConfiguratorMessageConsole.getInstance()
                                .printLibraryErrorMessage(libApiRes);
                        System.err.println("CreateParameterObject WARN: "
                                + getErrorMessage(libApiRes));
                    }
                    // else {
                    // try {
                    // OpenConfiguratorProjectUtils
                    // .updateModuleObjectIndex(module,
                    // getModuleObjectIndex(module),
                    // object);
                    // } catch (JDOMException | IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }
                    // else {
                    // try {
                    // OpenConfiguratorProjectUtils
                    // .updateModuleObjectInNode(node, object,
                    // module,
                    // getModuleObjectIndex(module));
                    // } catch (JDOMException | IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }
                } else if (object.getUniqueIDRef() instanceof TParameterGroup) {
                    TParameterGroup parameter = (TParameterGroup) object
                            .getUniqueIDRef();
                    libApiRes = core
                            .CreateModuleParameterObject(node.getNetworkId(),
                                    node.getNodeId(), node.getInterface()
                                            .getInterfaceUId(),
                            module.getChildID(), module.getPosition(),
                            object.getId(), objectType, object.getName(),
                            getObjectDatatype(object.getDataType()),
                            getAccessType(object.getAccessType()), mapping,
                            parameter.getUniqueID(), object.getDefaultValue(),
                            object.getActualValue(), object.getRangeSelector());
                    if (!libApiRes.IsSuccessful()) {
                        object.setError(getErrorMessage(libApiRes));
                        OpenConfiguratorMessageConsole.getInstance()
                                .printLibraryErrorMessage(libApiRes);
                        System.err.println("CreateParameterObject WARN: "
                                + getErrorMessage(libApiRes));
                    }
                    // else {
                    // try {
                    // OpenConfiguratorProjectUtils
                    // .updateModuleObjectIndex(module,
                    // getModuleObjectIndex(module),
                    // object);
                    // } catch (JDOMException | IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }
                    // else {
                    // try {
                    // OpenConfiguratorProjectUtils
                    // .updateModuleObjectInNode(node, object,
                    // module,
                    // getModuleObjectIndex(module));
                    // } catch (JDOMException | IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }
                }

            } else if ((object.getDataType() == null)
                    && (object.getUniqueIDRef() != null)) {
                PlkDataType datatype = PlkDataType.UNDEFINED;
                if (object
                        .getUniqueIDRef() instanceof TParameterList.Parameter) {
                    Parameter parameter = (Parameter) object.getUniqueIDRef();
                    libApiRes = core
                            .CreateModuleParameterObject(node.getNetworkId(),
                                    node.getNodeId(), node.getInterface()
                                            .getInterfaceUId(),
                            module.getChildID(), module.getPosition(),
                            object.getId(), objectType, object.getName(),
                            getObjectDatatype(object.getDataType()),
                            getAccessType(object.getAccessType()), mapping,
                            parameter.getUniqueID(), object.getDefaultValue(),
                            object.getActualValue(), object.getRangeSelector());
                    if (!libApiRes.IsSuccessful()) {
                        object.setError(getErrorMessage(libApiRes));
                        OpenConfiguratorMessageConsole.getInstance()
                                .printLibraryErrorMessage(libApiRes);
                        System.err.println("CreateParameterObject WARN: "
                                + getErrorMessage(libApiRes));
                    }
                    // else {
                    // try {
                    // OpenConfiguratorProjectUtils
                    // .updateModuleObjectIndex(module,
                    // getModuleObjectIndex(module),
                    // object);
                    // } catch (JDOMException | IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }
                    // else {
                    // try {
                    // OpenConfiguratorProjectUtils
                    // .updateModuleObjectInNode(node, object,
                    // module,
                    // getModuleObjectIndex(module));
                    // } catch (JDOMException | IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }
                } else if (object.getUniqueIDRef() instanceof TParameterGroup) {
                    TParameterGroup parameter = (TParameterGroup) object
                            .getUniqueIDRef();
                    libApiRes = core
                            .CreateModuleParameterObject(node.getNetworkId(),
                                    node.getNodeId(), node.getInterface()
                                            .getInterfaceUId(),
                            module.getChildID(), module.getPosition(),
                            object.getId(), objectType, object.getName(),
                            getObjectDatatype(object.getDataType()),
                            getAccessType(object.getAccessType()), mapping,
                            parameter.getUniqueID(), object.getDefaultValue(),
                            object.getActualValue(), object.getRangeSelector());
                    if (!libApiRes.IsSuccessful()) {
                        object.setError(getErrorMessage(libApiRes));
                        OpenConfiguratorMessageConsole.getInstance()
                                .printLibraryErrorMessage(libApiRes);
                        System.err.println("CreateParameterObject WARN: "
                                + getErrorMessage(libApiRes));
                    }
                    // else {
                    // try {
                    // OpenConfiguratorProjectUtils
                    // .updateModuleObjectIndex(module,
                    // getModuleObjectIndex(module),
                    // object);
                    // } catch (JDOMException | IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }
                    // else {
                    // try {
                    // OpenConfiguratorProjectUtils
                    // .updateModuleObjectInNode(node, object,
                    // module,
                    // getModuleObjectIndex(module));
                    // } catch (JDOMException | IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }
                }

            } else {
                // Cannot happen
                System.err.println(
                        "ERROR: Invalid object without dataType and UniqueIdRef. Id:"
                                + object.getIdHex());
            }

            long objectIndex = getModuleObjectIndex(module);
            System.err.println("Object Index...." + objectIndex);
            libApiRes = addSubObjects(module, object, objectIndex);

            if (!libApiRes.IsSuccessful()) {
                System.err.println("WARN: " + getErrorMessage(libApiRes));
            }
        }

        return libApiRes;

    }

    private static Result addModuleParameterGroup(String networkId,
            short nodeId, TParameterGroupList parameterGroupList,
            Module module) {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Result libApiRes = new Result();
        String interfaceId = module.getInterfaceOfModule().getInterfaceUId();
        String moduleId = module.getChildID();
        long modulePosition = Long.valueOf(module.getPosition());
        if (parameterGroupList != null) {
            List<TParameterGroup> parameterGroup = parameterGroupList
                    .getParameterGroup();

            for (TParameterGroup parameterGrp : parameterGroup) {
                String parameterGroupUniqueId = parameterGrp.getUniqueID();

                if (parameterGroupUniqueId == null) {
                    // FIXME:
                    System.err.println("ERRR......");
                    continue;
                }
                System.err.println("Node Id.." + nodeId);
                System.err.println("Network Id.." + networkId);
                System.err.println("Module Id..." + moduleId);
                System.err.println("Interface Id..." + interfaceId);
                System.err.println("Module position..." + modulePosition);
                System.err.println(
                        "Parameter group Id..." + parameterGroupUniqueId);
                libApiRes = core.CreateParameterGroup(networkId, nodeId,
                        parameterGroupUniqueId, interfaceId, moduleId,
                        modulePosition);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("Create Module parameter group WARN: "
                            + getErrorMessage(libApiRes));
                    continue;
                }

                // Add child parameterGroup/parameterReferenceList...
                libApiRes = addChildModuleParameterGroupReference(networkId,
                        nodeId, parameterGrp, module);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println(
                            "Create  Module parameter group child WARN: "
                                    + getErrorMessage(libApiRes));
                    continue;
                }
            }
        }
        return libApiRes;
    }

    /**
     * Add the network management information into the library.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param networkManagement The network management instance from the
     *            XDD/XDC.
     * @return Result instance from the library.
     */
    private static Result addNetworkManagement(String networkId, short nodeId,
            NetworkManagement networkManagement) {
        Result libApiRes = new Result();
        if (networkManagement == null) {
            return libApiRes;
        }
        libApiRes = addNetworkManagementGeneralFeatures(networkId, nodeId,
                networkManagement.getGeneralFeatures());
        libApiRes = addNetworkManagementMnFeatures(networkId, nodeId,
                networkManagement.getMnFeatures());
        libApiRes = addNetworkManagementCnFeatures(networkId, nodeId,
                networkManagement.getCnFeatures());
        return libApiRes;
    }

    /**
     * Add the network management CN features available in the nodes XDC.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param cnFeatures The CN features instance from the XDC.
     * @return Result instance from the library.
     */
    private static Result addNetworkManagementCnFeatures(String networkId,
            short nodeId, TCNFeatures cnFeatures) {
        Result libApiRes = new Result();
        if (cnFeatures == null) {
            return libApiRes;
        }
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();

        CNFeatureEnum[] cnFeaturesList = CNFeatureEnum.values();
        for (CNFeatureEnum cnFeature : cnFeaturesList) {
            String value = StringUtils.EMPTY;
            switch (cnFeature) {
                case DLLCNFeatureMultiplex:
                    value = String
                            .valueOf(cnFeatures.isDLLCNFeatureMultiplex());
                    break;
                case DLLCNPResChaining:
                    value = String.valueOf(cnFeatures.isDLLCNPResChaining());
                    break;
                // case NMTCNPreOp2ToReady2Op:
                // value = String
                // .valueOf(cnFeatures.getNMTCNPreOp2ToReady2Op());
                // break;
                case NMTCNSoC2PReq:
                    value = String.valueOf(cnFeatures.getNMTCNSoC2PReq());
                    break;
                case NMTCNSetNodeNumberTime:
                    value = String
                            .valueOf(cnFeatures.getNMTCNSetNodeNumberTime());
                    break;
                case NMTCNDNA:
                    value = String.valueOf(cnFeatures.isNMTCNDNA());
                    break;
                case NMTCNMaxAInv:
                    value = String.valueOf(cnFeatures.getNMTCNMaxAInv());
                    break;
                default:
                    System.err.println("CNFeature enum unsupported");
            }
            libApiRes = core.SetFeatureValue(networkId, nodeId, cnFeature,
                    value);
            if (!libApiRes.IsSuccessful()) {
                System.err.println(
                        "SetFeatureValue WARN: " + getErrorMessage(libApiRes));
            }
        }
        return libApiRes;
    }

    /**
     * Add the network management General features available in the nodes XDC.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param generalFeatures The General features instance from the XDC.
     * @return Result instance from the library.
     */
    private static Result addNetworkManagementGeneralFeatures(String networkId,
            short nodeId, TGeneralFeatures generalFeatures) {
        Result libApiRes = new Result();
        if (generalFeatures == null) {
            return libApiRes;
        }
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        GeneralFeatureEnum[] generalFeaturesList = GeneralFeatureEnum.values();
        for (GeneralFeatureEnum generalFeature : generalFeaturesList) {
            String value = StringUtils.EMPTY;

            switch (generalFeature) {
                case CFMConfigManager:
                    value = String
                            .valueOf(generalFeatures.isCFMConfigManager());
                    break;
                case DLLErrBadPhysMode:
                    value = String
                            .valueOf(generalFeatures.isDLLErrBadPhysMode());
                    break;
                case DLLErrMacBuffer:
                    value = String.valueOf(generalFeatures.isDLLErrMacBuffer());
                    break;
                case DLLFeatureCN:
                    value = String.valueOf(generalFeatures.isDLLFeatureCN());
                    break;
                case DLLFeatureMN:
                    value = String.valueOf(generalFeatures.isDLLFeatureMN());
                    break;
                case NMTBootTimeNotActive:
                    value = String
                            .valueOf(generalFeatures.getNMTBootTimeNotActive());
                    break;
                case NMTCycleTimeGranularity:
                    value = String.valueOf(
                            generalFeatures.getNMTCycleTimeGranularity());
                    break;
                case NMTCycleTimeMax:
                    value = String
                            .valueOf(generalFeatures.getNMTCycleTimeMax());
                    break;
                case NMTCycleTimeMin:
                    value = String
                            .valueOf(generalFeatures.getNMTCycleTimeMin());
                    break;
                case NMTMinRedCycleTime:
                    value = String
                            .valueOf(generalFeatures.getNMTMinRedCycleTime());
                    break;
                case NMTEmergencyQueueSize:
                    value = String.valueOf(
                            generalFeatures.getNMTEmergencyQueueSize());
                    break;
                case NMTErrorEntries:
                    value = String
                            .valueOf(generalFeatures.getNMTErrorEntries());
                    break;
                case NMTExtNmtCmds:
                    value = String.valueOf(generalFeatures.isNMTExtNmtCmds());
                    break;
                case NMTFlushArpEntry:
                    value = String
                            .valueOf(generalFeatures.isNMTFlushArpEntry());
                    break;
                case NMTIsochronous:
                    value = String.valueOf(generalFeatures.isNMTIsochronous());
                    break;
                case NMTNetHostNameSet:
                    value = String
                            .valueOf(generalFeatures.isNMTNetHostNameSet());
                    break;
                case NMTMaxCNNodeID:
                    value = String.valueOf(generalFeatures.getNMTMaxCNNodeID());
                    break;
                case NMTMaxCNNumber:
                    value = String.valueOf(generalFeatures.getNMTMaxCNNumber());
                    break;

                case NMTMaxHeartbeats:
                    value = String
                            .valueOf(generalFeatures.getNMTMaxHeartbeats());
                    break;
                case NMTNodeIDByHW:
                    value = String.valueOf(generalFeatures.isNMTNodeIDByHW());
                    break;
                case NMTNodeIDBySW:
                    value = String.valueOf(generalFeatures.isNMTNodeIDBySW());
                    break;
                case NMTProductCode:
                    value = String.valueOf(generalFeatures.getNMTProductCode());
                    break;
                case NMTPublishActiveNodes:
                    value = String
                            .valueOf(generalFeatures.isNMTPublishActiveNodes());
                    break;
                case NMTPublishConfigNodes:
                    value = String
                            .valueOf(generalFeatures.isNMTPublishConfigNodes());
                    break;
                case NMTPublishEmergencyNew:
                    value = String.valueOf(
                            generalFeatures.isNMTPublishEmergencyNew());
                    break;
                case NMTPublishNodeState:
                    value = String
                            .valueOf(generalFeatures.isNMTPublishNodeState());
                    break;
                case NMTPublishOperational:
                    value = String
                            .valueOf(generalFeatures.isNMTPublishOperational());
                    break;
                case NMTPublishPreOp1:
                    value = String
                            .valueOf(generalFeatures.isNMTPublishPreOp1());
                    break;
                case NMTPublishPreOp2:
                    value = String
                            .valueOf(generalFeatures.isNMTPublishPreOp2());
                    break;
                case NMTPublishReadyToOp:
                    value = String
                            .valueOf(generalFeatures.isNMTPublishReadyToOp());
                    break;
                case NMTPublishStopped:
                    value = String
                            .valueOf(generalFeatures.isNMTPublishStopped());
                    break;

                case NMTPublishTime:
                    value = String.valueOf(generalFeatures.isNMTPublishTime());
                    break;
                case NMTRevisionNo:
                    value = String.valueOf(generalFeatures.getNMTRevisionNo());
                    break;
                case NWLForward:
                    value = String.valueOf(generalFeatures.isNWLForward());
                    break;
                case NWLICMPSupport:
                    value = String.valueOf(generalFeatures.isNWLICMPSupport());
                    break;
                case NWLIPSupport:
                    value = String.valueOf(generalFeatures.isNWLIPSupport());
                    break;
                case PDODynamicMapping:
                    value = String
                            .valueOf(generalFeatures.isPDODynamicMapping());
                    break;
                case PDOGranularity:
                    value = String.valueOf(generalFeatures.getPDOGranularity());
                    break;

                case PDOMaxDescrMem:
                    value = String.valueOf(generalFeatures.getPDOMaxDescrMem());
                    break;
                case PDORPDOChannelObjects:
                    value = String.valueOf(
                            generalFeatures.getPDORPDOChannelObjects());
                    break;
                case PDORPDOChannels:
                    value = String
                            .valueOf(generalFeatures.getPDORPDOChannels());
                    break;
                case PDORPDOCycleDataLim:
                    value = String
                            .valueOf(generalFeatures.getPDORPDOCycleDataLim());
                    break;
                case PDORPDOOverallObjects:
                    value = String.valueOf(
                            generalFeatures.getPDORPDOOverallObjects());
                    break;
                case PDOSelfReceipt:

                    break;
                case PDOTPDOChannelObjects:
                    value = String.valueOf(
                            generalFeatures.getPDOTPDOChannelObjects());
                    break;
                case PDOTPDOCycleDataLim:
                    value = String
                            .valueOf(generalFeatures.getPDOTPDOCycleDataLim());
                    break;
                case PDOTPDOOverallObjects:
                    value = String.valueOf(
                            generalFeatures.getPDOTPDOOverallObjects());
                    break;
                case PHYExtEPLPorts:
                    value = String.valueOf(generalFeatures.getPHYExtEPLPorts());
                    break;
                case PHYHubDelay:
                    value = String.valueOf(generalFeatures.getPHYHubDelay());
                    break;
                case PHYHubIntegrated:
                    value = String
                            .valueOf(generalFeatures.isPHYHubIntegrated());
                    break;
                case PHYHubJitter:
                    value = String.valueOf(generalFeatures.getPHYHubJitter());
                    break;
                case RT1RT1SecuritySupport:
                    value = String
                            .valueOf(generalFeatures.isRT1RT1SecuritySupport());
                    break;
                case RT1RT1Support:
                    value = String.valueOf(generalFeatures.isRT1RT1Support());
                    break;
                case RT2RT2Support:
                    value = String.valueOf(generalFeatures.isRT2RT2Support());
                    break;
                case SDOClient:
                    value = String.valueOf(generalFeatures.isSDOClient());
                    break;
                case SDOCmdFileRead:
                    value = String.valueOf(generalFeatures.isSDOCmdFileRead());
                    break;
                case SDOCmdFileWrite:
                    value = String.valueOf(generalFeatures.isSDOCmdFileWrite());
                    break;
                case SDOCmdLinkName:
                    value = String.valueOf(generalFeatures.isSDOCmdLinkName());
                    break;
                case SDOCmdReadAllByIndex:
                    value = String
                            .valueOf(generalFeatures.isSDOCmdReadAllByIndex());
                    break;
                case SDOCmdReadByName:
                    value = String
                            .valueOf(generalFeatures.isSDOCmdReadByName());
                    break;
                case SDOCmdReadMultParam:
                    value = String
                            .valueOf(generalFeatures.isSDOCmdReadMultParam());
                    break;
                case SDOCmdWriteAllByIndex:
                    value = String
                            .valueOf(generalFeatures.isSDOCmdWriteAllByIndex());
                    break;
                case SDOCmdWriteByName:
                    value = String
                            .valueOf(generalFeatures.isSDOCmdWriteByName());
                    break;
                case SDOCmdWriteMultParam:
                    value = String
                            .valueOf(generalFeatures.isSDOCmdWriteMultParam());
                    break;
                case SDOMaxConnections:
                    value = String
                            .valueOf(generalFeatures.getSDOMaxConnections());
                    break;
                case SDOMaxParallelConnections:
                    value = String.valueOf(
                            generalFeatures.getSDOMaxParallelConnections());
                    break;
                case SDOSeqLayerTxHistorySize:
                    value = String.valueOf(
                            generalFeatures.getSDOSeqLayerTxHistorySize());
                    break;
                case SDOServer:
                    value = String.valueOf(generalFeatures.isSDOServer());
                    break;
                case SDOSupportASnd:
                    value = String.valueOf(generalFeatures.isSDOSupportASnd());
                    break;
                case SDOSupportPDO:
                    value = String.valueOf(generalFeatures.isSDOSupportPDO());
                    break;
                case SDOSupportUdpIp:
                    value = String.valueOf(generalFeatures.isSDOSupportUdpIp());
                    break;
                case DLLMultiplePReqPRes:
                    value = String
                            .valueOf(generalFeatures.isDLLMultiplePReqPRes());
                    break;
                default:
                    System.err.println(
                            "General feature not handled" + generalFeature);
                    break;
            }
            libApiRes = core.SetFeatureValue(networkId, nodeId, generalFeature,
                    value);
            if (!libApiRes.IsSuccessful()) {
                System.err.println("SetGeneralFeatureValue  WARN: "
                        + getErrorMessage(libApiRes));
            }
        }
        return libApiRes;
    }

    /**
     * Add the network management MN features available in the nodes XDC.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param mnFeatures The MN features instance from the XDC.
     * @return Result instance from the library.
     */
    private static Result addNetworkManagementMnFeatures(String networkId,
            short nodeId, TMNFeatures mnFeatures) {
        Result libApiRes = new Result();
        if (mnFeatures == null) {
            return libApiRes;
        }
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        MNFeatureEnum[] mnFeaturesList = MNFeatureEnum.values();
        for (MNFeatureEnum mnFeature : mnFeaturesList) {
            String value = "";
            switch (mnFeature) {
                case DLLErrMNMultipleMN:
                    value = String.valueOf(mnFeatures.isDLLErrMNMultipleMN());
                    break;
                case DLLMNFeatureMultiplex:
                    value = String
                            .valueOf(mnFeatures.isDLLMNFeatureMultiplex());
                    break;
                case DLLMNPResChaining:
                    value = String.valueOf(mnFeatures.isDLLMNPResChaining());
                    break;
                case DLLMNFeaturePResTx:
                    value = String.valueOf(mnFeatures.isDLLMNFeaturePResTx());
                    break;
                case NMTMNASnd2SoC:
                    value = String.valueOf(mnFeatures.getNMTMNASnd2SoC());
                    break;
                case NMTMNBasicEthernet:
                    value = String.valueOf(mnFeatures.isNMTMNBasicEthernet());
                    break;
                case NMTMNMultiplCycMax:
                    value = String.valueOf(mnFeatures.getNMTMNMultiplCycMax());
                    break;
                case NMTMNPRes2PReq:
                    value = String.valueOf(mnFeatures.getNMTMNPRes2PReq());
                    break;
                case NMTMNPRes2PRes:
                    value = String.valueOf(mnFeatures.getNMTMNPRes2PRes());
                    break;
                case NMTMNPResRx2SoA:
                    value = String.valueOf(mnFeatures.getNMTMNPResRx2SoA());
                    break;
                case NMTMNPResTx2SoA:
                    value = String.valueOf(mnFeatures.getNMTMNPResTx2SoA());
                    break;
                case NMTMNSoA2ASndTx:
                    value = String.valueOf(mnFeatures.getNMTMNSoA2ASndTx());
                    break;
                case NMTMNSoC2PReq:
                    value = String.valueOf(mnFeatures.getNMTMNSoC2PReq());
                    break;
                case NMTNetTime:
                    value = String.valueOf(mnFeatures.isNMTNetTime());
                    break;
                case NMTNetTimeIsRealTime:
                    value = String.valueOf(mnFeatures.isNMTNetTimeIsRealTime());
                    break;
                case NMTRelativeTime:
                    value = String.valueOf(mnFeatures.isNMTRelativeTime());
                    break;
                case NMTServiceUdpIp:
                    value = String.valueOf(mnFeatures.isNMTServiceUdpIp());
                    break;
                case NMTSimpleBoot:
                    value = String.valueOf(mnFeatures.isNMTSimpleBoot());
                    break;
                case PDOTPDOChannels:
                    value = String.valueOf(mnFeatures.getPDOTPDOChannels());
                    break;
                case NMTMNDNA:
                    value = String.valueOf(mnFeatures.isNMTMNDNA());
                    break;
                case NMTMNRedundancy:
                    value = String.valueOf(mnFeatures.isNMTMNRedundancy());
                    break;
                case DLLMNRingRedundancy:
                    value = String.valueOf(mnFeatures.isDLLMNRingRedundancy());
                    break;
                default:
                    System.err.println("MN feature not handled:" + mnFeature);
                    break;
            }

            libApiRes = core.SetFeatureValue(networkId, nodeId, mnFeature,
                    value);
            if (!libApiRes.IsSuccessful()) {
                System.err.println("SetMNFeatureValue WARN: "
                        + getErrorMessage(libApiRes));
            }
        }
        return libApiRes;
    }

    /**
     * Add node into the library.
     *
     * @param node Node instance.
     * @return Result instance from the library.
     */
    public static Result addNode(Node node) {
        Result libApiRes = new Result();

        libApiRes = createNode(node);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }
        // Update the LossOfSocTolerance value into library during
        // initialization of project.
        if (node.getNodeType() == NodeType.MANAGING_NODE) {
            if (node.getNodeModel() instanceof TNetworkConfiguration) {
                TNetworkConfiguration ntwrkConfg = (TNetworkConfiguration) node
                        .getNodeModel();
                long lossOfSocTolerance = ntwrkConfg.getLossOfSocTolerance()
                        .longValue();
                // Workaround:
                // only set the LossOfSocTolerance if not default value
                if (lossOfSocTolerance != IPowerlinkConstants.LOSS_OF_SOC_TOLERANCE_DEFAULT_VALUE) {
                    libApiRes = OpenConfiguratorCore.GetInstance()
                            .SetLossOfSocTolerance(node.getNetworkId(),
                                    node.getNodeId(), lossOfSocTolerance);
                    if (!libApiRes.IsSuccessful()) {
                        return libApiRes;
                    }
                } else {
                    System.out.println(
                            "LossOfSocTolerance has the default value(100000ns). Not setting to the library!!");
                }
            }
        }

        libApiRes = addNodeAssignments(node);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        libApiRes = updateForcedObjectsIntoLibary(node);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        return libApiRes;
    }

    /**
     * Add node assignment value bit into library.
     *
     * @param node Node instance.
     * @return Result instance from the library.
     */
    public static Result addNodeAssignments(final Node node) {
        Result libApiRes = null;
        TAbstractNode abstractNode = null;
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        if (node.getNodeModel() instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) node
                    .getNodeModel();
            abstractNode = net.getNodeCollection().getMN();

            TMN mnInst = net.getNodeCollection().getMN();
            libApiRes = setNodeAssignment(NodeAssignment.NMT_NODEASSIGN_MN_PRES,
                    node, mnInst.isTransmitsPRes());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

        } else if (node.getNodeModel() instanceof TCN) {
            TCN cnModel = (TCN) node.getNodeModel();
            abstractNode = cnModel;

            libApiRes = setNodeAssignment(
                    NodeAssignment.NMT_NODEASSIGN_NODE_IS_CN, node, true);
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

            libApiRes = setNodeAssignment(
                    NodeAssignment.NMT_NODEASSIGN_MANDATORY_CN, node,
                    cnModel.isIsMandatory());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

            libApiRes = setNodeAssignment(
                    NodeAssignment.NMT_NODEASSIGN_START_CN, node,
                    cnModel.isAutostartNode());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

            libApiRes = setNodeAssignment(
                    NodeAssignment.NMT_NODEASSIGN_KEEPALIVE, node,
                    cnModel.isResetInOperational());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

            libApiRes = setNodeAssignment(
                    NodeAssignment.NMT_NODEASSIGN_SWVERSIONCHECK, node,
                    cnModel.isVerifyAppSwVersion());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

            libApiRes = setNodeAssignment(
                    NodeAssignment.NMT_NODEASSIGN_SWUPDATE, node,
                    cnModel.isAutoAppSwUpdateAllowed());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

            if (cnModel.isIsChained()) {
                libApiRes = core.SetOperationModeChained(node.getNetworkId(),
                        node.getNodeId());
                if (!libApiRes.IsSuccessful()) {
                    return libApiRes;
                }
            }

            if (cnModel.isIsMultiplexed()) {
                libApiRes = core.SetOperationModeMultiplexed(
                        node.getNetworkId(), node.getNodeId(),
                        (short) cnModel.getForcedMultiplexedCycle());
                if (!libApiRes.IsSuccessful()) {
                    return libApiRes;
                }
            }

        } else if (node.getNodeModel() instanceof TRMN) {
            abstractNode = (TRMN) node.getNodeModel();

            libApiRes = setNodeAssignment(
                    NodeAssignment.NMT_NODEASSIGN_NODE_IS_CN, node, true);
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

        } else {
            System.err.println("Un-Supported node Type");
            return new Result(ErrorCode.UNHANDLED_EXCEPTION,
                    "Un-Supported node Type");
        }

        libApiRes = setNodeAssignment(NodeAssignment.MNT_NODEASSIGN_VALID, node,
                true);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        libApiRes = setNodeAssignment(NodeAssignment.NMT_NODEASSIGN_NODE_EXISTS,
                node, true);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        // Update the abstract node properties into the library.
        libApiRes = setNodeAssignment(
                NodeAssignment.NMT_NODEASSIGN_ASYNCONLY_NODE, node,
                abstractNode.isIsAsyncOnly());
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        libApiRes = setNodeAssignment(NodeAssignment.NMT_NODEASSIGN_RT1, node,
                abstractNode.isIsType1Router());
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        libApiRes = setNodeAssignment(NodeAssignment.NMT_NODEASSIGN_RT2, node,
                abstractNode.isIsType2Router());
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        return libApiRes;
    }

    private static Result addObjectDictionary(Node node,
            ObjectDictionary objectDict) {
        Result libApiRes = null;
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();

        List<PowerlinkObject> plkObjects = objectDict.getObjectsList();
        for (PowerlinkObject object : plkObjects) {
            ObjectType objectType = getObjectType(object.getObjectType());
            PDOMapping mapping = getPdoMapping(object.getPdoMapping());

            if (((object.getObjectType() != 7) || ((object.getObjectType() == 7)
                    && (object.getDataType() != null)))
                    && (object.getUniqueIDRef() == null)) {
                // Normal objects with dataType and without uniqueIdRef.

                PlkDataType dataType = getObjectDatatype(object.getDataType());
                AccessType accessType = getAccessType(object.getAccessType());

                String actualValue = object.getActualValue();

                // An workaround to avoid the library setting the actualValue
                // for non writable sub-objects.
                if ((accessType == AccessType.CONST)
                        || (accessType == AccessType.RO)) {
                    actualValue = StringUtils.EMPTY;
                }

                libApiRes = core.CreateObject(node.getNetworkId(),
                        node.getNodeId(), object.getId(), objectType,
                        object.getName(), dataType, accessType, mapping,
                        object.getDefaultValue(), actualValue);
                if (libApiRes.IsSuccessful()) {
                    libApiRes = core.SetObjectLimits(node.getNetworkId(),
                            node.getNodeId(), object.getId(),
                            object.getLowLimit(), object.getHighLimit());
                    if (!libApiRes.IsSuccessful()) {
                        object.setError(getErrorMessage(libApiRes));
                        System.err.println("SetObjectLimits WARN: "
                                + getErrorMessage(libApiRes));
                    }
                } else {
                    object.setError(getErrorMessage(libApiRes));
                    OpenConfiguratorMessageConsole.getInstance()
                            .printLibraryErrorMessage(libApiRes);
                    System.err.println("WARN: " + getErrorMessage(libApiRes));
                }
            } else if ((object.getDataType() != null)
                    && (object.getUniqueIDRef() != null)) {
                if (object
                        .getUniqueIDRef() instanceof TParameterList.Parameter) {
                    Parameter parameter = (Parameter) object.getUniqueIDRef();
                    libApiRes = core.CreateParameterObject(node.getNetworkId(),
                            node.getNodeId(), object.getId(), objectType,
                            object.getName(),
                            getObjectDatatype(object.getDataType()),
                            getAccessType(object.getAccessType()), mapping,
                            parameter.getUniqueID(), object.getDefaultValue(),
                            object.getActualValue());
                    if (!libApiRes.IsSuccessful()) {
                        object.setError(getErrorMessage(libApiRes));
                        OpenConfiguratorMessageConsole.getInstance()
                                .printLibraryErrorMessage(libApiRes);
                        System.err.println("CreateParameterObject WARN: "
                                + getErrorMessage(libApiRes));
                    }
                } else if (object.getUniqueIDRef() instanceof TParameterGroup) {
                    TParameterGroup parameter = (TParameterGroup) object
                            .getUniqueIDRef();
                    libApiRes = core.CreateParameterObject(node.getNetworkId(),
                            node.getNodeId(), object.getId(), objectType,
                            object.getName(),
                            getObjectDatatype(object.getDataType()),
                            getAccessType(object.getAccessType()), mapping,
                            parameter.getUniqueID(), object.getDefaultValue(),
                            object.getActualValue());
                    if (!libApiRes.IsSuccessful()) {
                        object.setError(getErrorMessage(libApiRes));
                        OpenConfiguratorMessageConsole.getInstance()
                                .printLibraryErrorMessage(libApiRes);
                        System.err.println("CreateParameterObject WARN: "
                                + getErrorMessage(libApiRes));
                    }
                }

            } else if ((object.getDataType() == null)
                    && (object.getUniqueIDRef() != null)) {
                PlkDataType datatype = PlkDataType.UNDEFINED;
                if (object
                        .getUniqueIDRef() instanceof TParameterList.Parameter) {
                    Parameter parameter = (Parameter) object.getUniqueIDRef();
                    libApiRes = core.CreateParameterObject(node.getNetworkId(),
                            node.getNodeId(), object.getId(), objectType,
                            object.getName(), datatype,
                            getAccessType(object.getAccessType()), mapping,
                            parameter.getUniqueID(), object.getDefaultValue(),
                            object.getActualValue());
                    if (!libApiRes.IsSuccessful()) {
                        object.setError(getErrorMessage(libApiRes));
                        OpenConfiguratorMessageConsole.getInstance()
                                .printLibraryErrorMessage(libApiRes);
                        System.err.println("CreateParameterObject WARN: "
                                + getErrorMessage(libApiRes));
                    }
                } else if (object.getUniqueIDRef() instanceof TParameterGroup) {
                    TParameterGroup parameter = (TParameterGroup) object
                            .getUniqueIDRef();
                    libApiRes = core.CreateParameterObject(node.getNetworkId(),
                            node.getNodeId(), object.getId(), objectType,
                            object.getName(), datatype,
                            getAccessType(object.getAccessType()), mapping,
                            parameter.getUniqueID(), object.getDefaultValue(),
                            object.getActualValue());
                    if (!libApiRes.IsSuccessful()) {
                        object.setError(getErrorMessage(libApiRes));
                        OpenConfiguratorMessageConsole.getInstance()
                                .printLibraryErrorMessage(libApiRes);
                        System.err.println("CreateParameterObject WARN: "
                                + getErrorMessage(libApiRes));
                    }
                }
            } else {
                // Cannot happen
                System.err.println(
                        "ERROR: Invalid object without dataType and UniqueIdRef. Id:"
                                + object.getIdHex());
            }

            libApiRes = addSubObjects(node, object);
            if (!libApiRes.IsSuccessful()) {
                System.err.println("WARN: " + getErrorMessage(libApiRes));
            }
        }

        return libApiRes;
    }

    /**
     * Add the configurations in openCONFIGURTOR project into the Network
     * available in the library.
     *
     * @param projectModel openCONFIGURATOR project instance.
     * @param networkId The Network ID.
     *
     * @return Result from the openCONFIGURATOR library.
     */
    public static Result addOpenCONFIGURATORProject(
            final OpenCONFIGURATORProject projectModel,
            final String networkId) {

        Result libApiRes = OpenConfiguratorLibraryUtils.addProjectConfiguration(
                projectModel.getProjectConfiguration(), networkId);

        return libApiRes;
    }

    private static Result addParameterGroup(String networkId, short nodeId,
            TParameterGroupList parameterGroupElement) {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Result libApiRes = new Result();
        String interfaceID = StringUtils.EMPTY;
        String moduleID = StringUtils.EMPTY;
        long position = 0;
        if (parameterGroupElement != null) {
            List<TParameterGroup> parameterGroup = parameterGroupElement
                    .getParameterGroup();

            for (TParameterGroup parameterGrp : parameterGroup) {
                String parameterGroupUniqueId = parameterGrp.getUniqueID();

                if (parameterGroupUniqueId == null) {
                    // FIXME:
                    System.err.println("ERRR......");
                    continue;
                }

                libApiRes = core.CreateParameterGroup(networkId, nodeId,
                        parameterGroupUniqueId, interfaceID, moduleID,
                        position);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("Create parameter group WARN: "
                            + getErrorMessage(libApiRes));
                    continue;
                }

                // Add child parameterGroup/parameterReferenceList...
                libApiRes = addChildParameterGroupReference(networkId, nodeId,
                        parameterGrp);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("Create parameter group WARN: "
                            + getErrorMessage(libApiRes));
                    continue;
                }
            }
        }
        return libApiRes;
    }

    /**
     * Add the list of Parameter instances from the XDC into the library.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param parameterListElement The parameter list instance.
     * @return Result instance from the library.
     */
    private static Result addParameterList(final String networkId,
            final short nodeId,
            List<TParameterList.Parameter> parameterListModel) {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Result libApiRes = new Result();

        for (TParameterList.Parameter parameter : parameterListModel) {
            ParameterAccess access = getParameterAccess(parameter.getAccess());
            if (access == null) {
                access = ParameterAccess.undefined;
            }
            String parameterUniqueId = parameter.getUniqueID();

            Object paramTempladeIDRef = parameter.getTemplateIDRef();
            if (paramTempladeIDRef != null) {

                if (paramTempladeIDRef instanceof TParameterTemplate) {
                    TParameterTemplate parameteratemplateModel = (TParameterTemplate) paramTempladeIDRef;
                    ParameterAccess accessparamTemplate = getParameterAccess(
                            parameter.getAccess());
                    if (accessparamTemplate == null) {
                        accessparamTemplate = ParameterAccess.noAccess;
                    }
                    System.out.println(
                            "Create Parameter template : Parameter UID = "
                                    + parameterUniqueId
                                    + " \nParameter template UID = "
                                    + parameteratemplateModel.getUniqueID()
                                    + "\nParameter Access "
                                    + accessparamTemplate);
                    libApiRes = core.CreateParameter(networkId, nodeId,
                            parameterUniqueId, accessparamTemplate,
                            parameteratemplateModel.getUniqueID());
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("CreateParameter Template WARN: "
                                + getErrorMessage(libApiRes));
                        continue;
                    }

                    TAllowedValues allowedValuesModel = parameter
                            .getAllowedValues();
                    if (allowedValuesModel != null) {
                        List<TValue> parameterAllowedValuesList = allowedValuesModel
                                .getValue();
                        // Create a string collection with allowed values list.
                        StringCollection allowedValues = new StringCollection();
                        for (TValue parameterAllowedValue : parameterAllowedValuesList) {

                            allowedValues.add(parameterAllowedValue.getValue());
                        }

                        libApiRes = core.SetParameterAllowedValues(networkId,
                                nodeId, parameterUniqueId, allowedValues);
                        if (!libApiRes.IsSuccessful()) {
                            System.err.println(
                                    "SetParameter Allowed Values list WARN: "
                                            + getErrorMessage(libApiRes));
                        }

                        List<TRange> rangeList = allowedValuesModel.getRange();
                        for (TRange range : rangeList) {

                            TRange.MinValue minValueModel = range.getMinValue();
                            TRange.MaxValue maxValueModel = range.getMaxValue();

                            String minValue = StringUtils.EMPTY;
                            String maxValue = StringUtils.EMPTY;

                            if (minValueModel != null) {
                                minValue = minValueModel.getValue();
                            }

                            if (maxValueModel != null) {
                                maxValue = maxValueModel.getValue();
                            }

                            libApiRes = core.SetParameterAllowedRange(networkId,
                                    nodeId, parameterUniqueId, minValue,
                                    maxValue);
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println(
                                        "SetParameter Allowed Range WARN: "
                                                + getErrorMessage(libApiRes));
                            }
                        }
                    }

                    TValue defaultValueModel = parameter.getDefaultValue();
                    if (defaultValueModel != null) {
                        libApiRes = core.SetParameterDefaultValue(networkId,
                                nodeId, parameterUniqueId,
                                defaultValueModel.getValue());
                        if (!libApiRes.IsSuccessful()) {
                            System.err
                                    .println("SetParameter Default value WARN: "
                                            + getErrorMessage(libApiRes)
                                            + libApiRes.GetErrorType());
                        }
                    }
                    TValue actualValueModel = parameter.getActualValue();
                    if (actualValueModel != null) {
                        libApiRes = core.SetParameterActualValue(networkId,
                                nodeId, parameterUniqueId,
                                actualValueModel.getValue());
                        if (!libApiRes.IsSuccessful()) {
                            System.err
                                    .println("SetParameter Actual value WARN: "
                                            + getErrorMessage(libApiRes)
                                            + libApiRes.GetErrorType());
                        }
                    }

                } else {
                    System.out
                            .println("Other than parameter template instance");

                }
            } else {

                if (parameter.getDataTypeIDRef() == null) {

                    IEC_Datatype iecDataType = getIEC_DataType(parameter);
                    libApiRes = core.CreateParameter(networkId, nodeId,
                            parameter.getUniqueID(), access, iecDataType,
                            false);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("Create Parameter data type WARN: "
                                + getErrorMessage(libApiRes));
                    }
                } else {
                    // FIXME: could be variableRef also
                    Object dataTypeObj = parameter.getDataTypeIDRef()
                            .getUniqueIDRef();
                    if (dataTypeObj instanceof TDataTypeList.Struct) {
                        TDataTypeList.Struct structDt = (TDataTypeList.Struct) dataTypeObj;
                        String uniqueIdRef = structDt.getUniqueID();
                        libApiRes = core.CreateParameter(networkId, nodeId,
                                parameter.getUniqueID(), uniqueIdRef, access,
                                false);
                        if (!libApiRes.IsSuccessful()) {
                            System.err.println("CreateParameter UID WARN: "
                                    + getErrorMessage(libApiRes));
                        }
                    } else {
                        System.err.println(
                                "Unhandled datatypeObj: " + dataTypeObj);
                    }
                }

                TAllowedValues allowedValuesModel = parameter
                        .getAllowedValues();
                if (allowedValuesModel != null) {
                    List<TValue> parameterAllowedValuesList = allowedValuesModel
                            .getValue();
                    // Create a string collection with allowed values list.
                    StringCollection allowedValues = new StringCollection();
                    for (TValue parameterAllowedValue : parameterAllowedValuesList) {

                        allowedValues.add(parameterAllowedValue.getValue());
                    }

                    libApiRes = core.SetParameterAllowedValues(networkId,
                            nodeId, parameterUniqueId, allowedValues);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println(
                                "SetParameter Allowed Values list WARN: "
                                        + getErrorMessage(libApiRes));
                    }

                    List<TRange> rangeList = allowedValuesModel.getRange();
                    for (TRange range : rangeList) {

                        TRange.MinValue minValueModel = range.getMinValue();
                        TRange.MaxValue maxValueModel = range.getMaxValue();

                        String minValue = StringUtils.EMPTY;
                        String maxValue = StringUtils.EMPTY;

                        if (minValueModel != null) {
                            minValue = minValueModel.getValue();
                        }

                        if (maxValueModel != null) {
                            maxValue = maxValueModel.getValue();
                        }

                        libApiRes = core.SetParameterAllowedRange(networkId,
                                nodeId, parameterUniqueId, minValue, maxValue);
                        if (!libApiRes.IsSuccessful()) {
                            System.err
                                    .println("SetParameter Allowed Range WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                    }
                }

                TValue defaultValueModel = parameter.getDefaultValue();
                if (defaultValueModel != null) {
                    libApiRes = core.SetParameterDefaultValue(networkId, nodeId,
                            parameterUniqueId, defaultValueModel.getValue());
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("SetParameter Default value WARN: "
                                + getErrorMessage(libApiRes));
                    }
                }

                TValue actualValueModel = parameter.getActualValue();
                if (actualValueModel != null) {
                    libApiRes = core.SetParameterActualValue(networkId, nodeId,
                            parameterUniqueId, actualValueModel.getValue());
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("SetParameter Actual value WARN: "
                                + getErrorMessage(libApiRes));
                    }
                }
            }
        }
        return libApiRes;
    }

    private static Result addParameterList(String networkId, short nodeId,
            List<TParameterList.Parameter> parameterListModel, Module module) {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Result libApiRes = new Result();

        String interfaceId = module.getInterfaceOfModule().getInterfaceUId();
        String moduleId = module.getChildID();
        long position = module.getPosition();

        for (TParameterList.Parameter parameter : parameterListModel) {
            ParameterAccess access = getParameterAccess(parameter.getAccess());
            if (access == null) {
                access = ParameterAccess.undefined;
            }
            String parameterUniqueId = parameter.getUniqueID();

            Object paramTempladeIDRef = parameter.getTemplateIDRef();
            if (paramTempladeIDRef != null) {

                if (paramTempladeIDRef instanceof TParameterTemplate) {
                    TParameterTemplate parameteratemplateModel = (TParameterTemplate) paramTempladeIDRef;
                    ParameterAccess accessparamTemplate = getParameterAccess(
                            parameter.getAccess());
                    if (accessparamTemplate == null) {
                        accessparamTemplate = ParameterAccess.noAccess;
                    }
                    System.out.println(
                            "Create Parameter template : Parameter UID = "
                                    + parameterUniqueId
                                    + " \nParameter template UID = "
                                    + parameteratemplateModel.getUniqueID()
                                    + "\nParameter Access "
                                    + accessparamTemplate);
                    libApiRes = core.CreateParameter(networkId, nodeId,
                            parameterUniqueId, accessparamTemplate,
                            parameteratemplateModel.getUniqueID(), interfaceId,
                            moduleId, position);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("CreateParameter Template WARN: "
                                + getErrorMessage(libApiRes));
                        continue;
                    }

                    System.err.println(
                            "Parameter Name=....." + parameterUniqueId);
                    String newParameterUniqueId = getModuleParameterUniqueID(
                            module, parameterUniqueId);
                    TAllowedValues allowedValuesModel = parameter
                            .getAllowedValues();
                    if (allowedValuesModel != null) {
                        List<TValue> parameterAllowedValuesList = allowedValuesModel
                                .getValue();
                        // Create a string collection with allowed values list.
                        StringCollection allowedValues = new StringCollection();
                        for (TValue parameterAllowedValue : parameterAllowedValuesList) {

                            allowedValues.add(parameterAllowedValue.getValue());
                        }

                        libApiRes = core.SetParameterAllowedValues(networkId,
                                nodeId, newParameterUniqueId, allowedValues);
                        if (!libApiRes.IsSuccessful()) {
                            System.err.println(
                                    "Set Parameter Allowed Values list for module WARN: "
                                            + getErrorMessage(libApiRes));
                        }

                        List<TRange> rangeList = allowedValuesModel.getRange();
                        for (TRange range : rangeList) {

                            TRange.MinValue minValueModel = range.getMinValue();
                            TRange.MaxValue maxValueModel = range.getMaxValue();

                            String minValue = StringUtils.EMPTY;
                            String maxValue = StringUtils.EMPTY;

                            if (minValueModel != null) {
                                minValue = minValueModel.getValue();
                            }

                            if (maxValueModel != null) {
                                maxValue = maxValueModel.getValue();
                            }

                            libApiRes = core.SetParameterAllowedRange(networkId,
                                    nodeId, newParameterUniqueId, minValue,
                                    maxValue);
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println(
                                        "SetParameter Allowed Range WARN: "
                                                + getErrorMessage(libApiRes));
                            }
                        }
                    }

                    TValue defaultValueModel = parameter.getDefaultValue();
                    if (defaultValueModel != null) {
                        libApiRes = core.SetParameterDefaultValue(networkId,
                                nodeId, newParameterUniqueId,
                                defaultValueModel.getValue());
                        if (!libApiRes.IsSuccessful()) {
                            System.err
                                    .println("SetParameter Default value WARN: "
                                            + getErrorMessage(libApiRes)
                                            + libApiRes.GetErrorType());
                        }
                    }
                    TValue actualValueModel = parameter.getActualValue();
                    if (actualValueModel != null) {
                        libApiRes = core.SetParameterActualValue(networkId,
                                nodeId, newParameterUniqueId,
                                actualValueModel.getValue());
                        if (!libApiRes.IsSuccessful()) {
                            System.err
                                    .println("SetParameter Actual value WARN: "
                                            + getErrorMessage(libApiRes)
                                            + libApiRes.GetErrorType());
                        }
                    }

                } else {
                    System.out
                            .println("Other than parameter template instance");

                }
            } else {

                if (parameter.getDataTypeIDRef() == null) {
                    System.err.println("Module ID..." + moduleId);
                    IEC_Datatype iecDataType = getIEC_DataType(parameter);
                    libApiRes = core.CreateParameter(networkId, nodeId,
                            parameter.getUniqueID(), access, iecDataType, false,
                            interfaceId, moduleId, position);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println(
                                "Create Module Parameter data type WARN: "
                                        + getErrorMessage(libApiRes));
                    }
                } else {
                    // FIXME: could be variableRef also
                    Object dataTypeObj = parameter.getDataTypeIDRef()
                            .getUniqueIDRef();
                    if (dataTypeObj instanceof TDataTypeList.Struct) {
                        TDataTypeList.Struct structDt = (TDataTypeList.Struct) dataTypeObj;
                        String uniqueIdRef = structDt.getUniqueID();
                        libApiRes = core.CreateParameter(networkId, nodeId,
                                parameter.getUniqueID(), uniqueIdRef, access,
                                false, interfaceId, moduleId, position);
                        if (!libApiRes.IsSuccessful()) {
                            System.err.println("CreateParameter UID WARN: "
                                    + getErrorMessage(libApiRes));
                        }
                    } else {
                        System.err.println(
                                "Unhandled datatypeObj: " + dataTypeObj);
                    }
                }
                String newParameterUniqueId = getModuleParameterUniqueID(module,
                        parameterUniqueId);
                TAllowedValues allowedValuesModel = parameter
                        .getAllowedValues();
                if (allowedValuesModel != null) {
                    List<TValue> parameterAllowedValuesList = allowedValuesModel
                            .getValue();
                    // Create a string collection with allowed values list.
                    StringCollection allowedValues = new StringCollection();
                    for (TValue parameterAllowedValue : parameterAllowedValuesList) {

                        allowedValues.add(parameterAllowedValue.getValue());
                    }

                    libApiRes = core.SetParameterAllowedValues(networkId,
                            nodeId, newParameterUniqueId, allowedValues);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println(
                                "SetParameter Allowed Values list WARN: "
                                        + getErrorMessage(libApiRes));
                    }

                    List<TRange> rangeList = allowedValuesModel.getRange();
                    for (TRange range : rangeList) {

                        TRange.MinValue minValueModel = range.getMinValue();
                        TRange.MaxValue maxValueModel = range.getMaxValue();

                        String minValue = StringUtils.EMPTY;
                        String maxValue = StringUtils.EMPTY;

                        if (minValueModel != null) {
                            minValue = minValueModel.getValue();
                        }

                        if (maxValueModel != null) {
                            maxValue = maxValueModel.getValue();
                        }

                        libApiRes = core.SetParameterAllowedRange(networkId,
                                nodeId, newParameterUniqueId, minValue,
                                maxValue);
                        if (!libApiRes.IsSuccessful()) {
                            System.err
                                    .println("SetParameter Allowed Range WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                    }
                }

                TValue defaultValueModel = parameter.getDefaultValue();
                if (defaultValueModel != null) {
                    libApiRes = core.SetParameterDefaultValue(networkId, nodeId,
                            newParameterUniqueId, defaultValueModel.getValue());
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("SetParameter Default value WARN: "
                                + getErrorMessage(libApiRes));
                    }
                }

                TValue actualValueModel = parameter.getActualValue();
                if (actualValueModel != null) {
                    libApiRes = core.SetParameterActualValue(networkId, nodeId,
                            newParameterUniqueId, actualValueModel.getValue());
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("SetParameter Actual value WARN: "
                                + getErrorMessage(libApiRes));
                    }
                }
            }
        }
        return libApiRes;
    }

    private static Result addParameterTemplateList(String networkId,
            short nodeId, List<TParameterTemplate> parameterTemplate) {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Result libApiRes = new Result();

        for (TParameterTemplate parameter : parameterTemplate) {
            ParameterAccess access = getParameterAccess(parameter.getAccess());
            if (access == null) {
                access = ParameterAccess.undefined;
            }
            String parameterUniqueId = parameter.getUniqueID();

            Object paramTempladeIDRef = parameter.getTemplateIDRef();

            if (parameter.getDataTypeIDRef() == null) {

                IEC_Datatype iecDataType = getIEC_DataType(parameter);
                libApiRes = core.CreateParameter(networkId, nodeId,
                        parameter.getUniqueID(), access, iecDataType, true);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("CreateParameter data type WARN: "
                            + getErrorMessage(libApiRes));
                }
            } else {
                // FIXME: could be variableRef also
                Object dataTypeObj = parameter.getDataTypeIDRef()
                        .getUniqueIDRef();
                if (dataTypeObj instanceof TDataTypeList.Struct) {
                    TDataTypeList.Struct structDt = (TDataTypeList.Struct) dataTypeObj;
                    String uniqueIdRef = structDt.getUniqueID();
                    libApiRes = core.CreateParameter(networkId, nodeId,
                            parameter.getUniqueID(), uniqueIdRef, access, true);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("CreateParameter UID WARN: "
                                + getErrorMessage(libApiRes));
                    }
                } else {
                    System.err.println("Unhandled datatypeObj: " + dataTypeObj);
                }
            }

            if (paramTempladeIDRef != null) {
                if (paramTempladeIDRef instanceof TParameterTemplate) {
                    TParameterTemplate parameteratemplateModel = (TParameterTemplate) paramTempladeIDRef;
                    System.out.println(
                            "Create Parameter template : Parameter UID = "
                                    + parameterUniqueId
                                    + " \nParameter template UID = "
                                    + parameteratemplateModel.getUniqueID());
                    libApiRes = core.CreateParameter(networkId, nodeId,
                            parameterUniqueId, access,
                            parameteratemplateModel.getUniqueID());
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("CreateParameter Template WARN: "
                                + getErrorMessage(libApiRes));
                        continue;
                    }

                    TAllowedValues allowedValuesModel = parameter
                            .getAllowedValues();
                    if (allowedValuesModel != null) {
                        List<TValue> parameterAllowedValuesList = allowedValuesModel
                                .getValue();
                        // Create a string collection with allowed values list.
                        StringCollection allowedValues = new StringCollection();
                        for (TValue parameterAllowedValue : parameterAllowedValuesList) {

                            allowedValues.add(parameterAllowedValue.getValue());
                        }

                        libApiRes = core.SetParameterAllowedValues(networkId,
                                nodeId, parameterUniqueId, allowedValues);
                        if (!libApiRes.IsSuccessful()) {
                            System.err.println(
                                    "SetParameter Allowed Values list WARN: "
                                            + getErrorMessage(libApiRes));
                        }

                        List<TRange> rangeList = allowedValuesModel.getRange();
                        for (TRange range : rangeList) {

                            TRange.MinValue minValueModel = range.getMinValue();
                            TRange.MaxValue maxValueModel = range.getMaxValue();

                            String minValue = StringUtils.EMPTY;
                            String maxValue = StringUtils.EMPTY;

                            if (minValueModel != null) {
                                minValue = minValueModel.getValue();
                            }

                            if (maxValueModel != null) {
                                maxValue = maxValueModel.getValue();
                            }

                            libApiRes = core.SetParameterAllowedRange(networkId,
                                    nodeId, parameterUniqueId, minValue,
                                    maxValue);
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println(
                                        "SetParameter Allowed Range WARN: "
                                                + getErrorMessage(libApiRes));
                            }
                        }
                    }

                    TValue defaultValueModel = parameter.getDefaultValue();
                    if (defaultValueModel != null) {
                        libApiRes = core.SetParameterDefaultValue(networkId,
                                nodeId, parameterUniqueId,
                                defaultValueModel.getValue());
                        if (!libApiRes.IsSuccessful()) {
                            System.err
                                    .println("SetParameter Default value WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                    }

                    TValue actualValueModel = parameter.getActualValue();
                    if (actualValueModel != null) {
                        libApiRes = core.SetParameterActualValue(networkId,
                                nodeId, parameterUniqueId,
                                actualValueModel.getValue());
                        if (!libApiRes.IsSuccessful()) {
                            System.err
                                    .println("SetParameter Actual value WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                    }
                }
            } else {
                System.err.println("Parameter template ID  == null");
            }

        }

        return libApiRes;
    }

    private static Result addParameterTemplateList(String networkId,
            short nodeId, List<TParameterTemplate> parameterTemplate,
            Module module) {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Result libApiRes = new Result();
        String interfaceId = module.getInterfaceOfModule().getInterfaceUId();
        String moduleId = module.getChildID();
        long position = module.getPosition();
        System.err.println("Module Id in parameter template..." + moduleId);
        System.err
                .println("Module position in parameter template..." + position);
        for (TParameterTemplate parameter : parameterTemplate) {
            ParameterAccess access = getParameterAccess(parameter.getAccess());
            if (access == null) {
                access = ParameterAccess.undefined;
            }
            String parameterUniqueId = parameter.getUniqueID();

            Object paramTempladeIDRef = parameter.getTemplateIDRef();

            if (parameter.getDataTypeIDRef() == null) {

                IEC_Datatype iecDataType = getIEC_DataType(parameter);
                libApiRes = core.CreateParameter(networkId, nodeId,
                        parameter.getUniqueID(), access, iecDataType, true,
                        interfaceId, moduleId, position);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println(
                            "Create Parameter data type  with no datatypeId ref WARN : "
                                    + getErrorMessage(libApiRes));
                }
            } else {
                // FIXME: could be variableRef also
                Object dataTypeObj = parameter.getDataTypeIDRef()
                        .getUniqueIDRef();
                if (dataTypeObj instanceof TDataTypeList.Struct) {
                    TDataTypeList.Struct structDt = (TDataTypeList.Struct) dataTypeObj;
                    String uniqueIdRef = structDt.getUniqueID();
                    libApiRes = core.CreateParameter(networkId, nodeId,
                            parameter.getUniqueID(), uniqueIdRef, access, true,
                            interfaceId, moduleId, position);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println(
                                "CreateParameter UID  of Struct data typeWARN: "
                                        + getErrorMessage(libApiRes));
                    }
                } else {
                    System.err.println("Unhandled datatypeObj: " + dataTypeObj);
                }
            }

            if (paramTempladeIDRef != null) {
                if (paramTempladeIDRef instanceof TParameterTemplate) {
                    TParameterTemplate parameteratemplateModel = (TParameterTemplate) paramTempladeIDRef;
                    System.out.println(
                            "Create Parameter template : Parameter UID = "
                                    + parameterUniqueId
                                    + " \nParameter template UID = "
                                    + parameteratemplateModel.getUniqueID());
                    libApiRes = core.CreateParameter(networkId, nodeId,
                            parameterUniqueId, access,
                            parameteratemplateModel.getUniqueID(), interfaceId,
                            moduleId, position);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("CreateParameter Template WARN: "
                                + getErrorMessage(libApiRes));
                        continue;
                    }

                    String newParameterUniqueId = getModuleParameterUniqueID(
                            module, parameterUniqueId);
                    System.err.println(
                            "Parameter Name=....." + parameterUniqueId);

                    TAllowedValues allowedValuesModel = parameter
                            .getAllowedValues();
                    if (allowedValuesModel != null) {
                        List<TValue> parameterAllowedValuesList = allowedValuesModel
                                .getValue();
                        // Create a string collection with allowed values list.
                        StringCollection allowedValues = new StringCollection();
                        for (TValue parameterAllowedValue : parameterAllowedValuesList) {

                            allowedValues.add(parameterAllowedValue.getValue());
                        }

                        libApiRes = core.SetParameterAllowedValues(networkId,
                                nodeId, newParameterUniqueId, allowedValues);
                        if (!libApiRes.IsSuccessful()) {
                            System.err.println(
                                    "SetParameter Allowed Values list WARN: "
                                            + getErrorMessage(libApiRes));
                        }

                        List<TRange> rangeList = allowedValuesModel.getRange();
                        for (TRange range : rangeList) {

                            TRange.MinValue minValueModel = range.getMinValue();
                            TRange.MaxValue maxValueModel = range.getMaxValue();

                            String minValue = StringUtils.EMPTY;
                            String maxValue = StringUtils.EMPTY;

                            if (minValueModel != null) {
                                minValue = minValueModel.getValue();
                            }

                            if (maxValueModel != null) {
                                maxValue = maxValueModel.getValue();
                            }

                            libApiRes = core.SetParameterAllowedRange(networkId,
                                    nodeId, newParameterUniqueId, minValue,
                                    maxValue);
                            if (!libApiRes.IsSuccessful()) {
                                System.err.println(
                                        "SetParameter Allowed Range WARN: "
                                                + getErrorMessage(libApiRes));
                            }
                        }
                    }

                    TValue defaultValueModel = parameter.getDefaultValue();
                    if (defaultValueModel != null) {
                        libApiRes = core.SetParameterDefaultValue(networkId,
                                nodeId, newParameterUniqueId,
                                defaultValueModel.getValue());
                        if (!libApiRes.IsSuccessful()) {
                            System.err
                                    .println("SetParameter Default value WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                    }

                    TValue actualValueModel = parameter.getActualValue();
                    if (actualValueModel != null) {
                        libApiRes = core.SetParameterActualValue(networkId,
                                nodeId, newParameterUniqueId,
                                actualValueModel.getValue());
                        if (!libApiRes.IsSuccessful()) {
                            System.err
                                    .println("SetParameter Actual value WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                    }
                }
            } else {
                System.err.println("Parameter template ID  == null");
            }

        }

        return libApiRes;
    }

    /**
     * Add the project configuration details from {@link TProjectConfiguration}
     * into the openCONFIGUATOR core library.
     *
     * @param projectConfiguration TProjectConfiguration instance.
     * @param networkId The Network ID.
     *
     * @return Result from the openCONFIGURATOR library.
     */
    private static Result addProjectConfiguration(
            final TProjectConfiguration projectConfiguration,
            final String networkId) {

        // AutoGenerationSettings set into the library
        java.util.List<TAutoGenerationSettings> agSettingsList = projectConfiguration
                .getAutoGenerationSettings();
        Result libApiRes = new Result();
        for (TAutoGenerationSettings agSetting : agSettingsList) {

            libApiRes = OpenConfiguratorCore.GetInstance()
                    .CreateConfiguration(networkId, agSetting.getId());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

            java.util.List<TKeyValuePair> settingList = agSetting.getSetting();
            for (TKeyValuePair setting : settingList) {

                libApiRes = OpenConfiguratorCore.GetInstance()
                        .CreateConfigurationSetting(networkId,
                                agSetting.getId(), setting.getName(),
                                setting.getValue());
                if (!libApiRes.IsSuccessful()) {
                    return libApiRes;
                }

                libApiRes = OpenConfiguratorCore.GetInstance()
                        .SetConfigurationSettingEnabled(networkId,
                                agSetting.getId(), setting.getName(),
                                setting.isEnabled());
                if (!libApiRes.IsSuccessful()) {
                    return libApiRes;
                }
            }
        }

        // ActiveAutoGenerationSetting
        libApiRes = OpenConfiguratorCore.GetInstance().SetActiveConfiguration(
                networkId,
                projectConfiguration.getActiveAutoGenerationSetting());

        return libApiRes;
    }

    private static Result addSubObjects(Module module, PowerlinkObject object,
            long index) {
        Result libApiRes = new Result();
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Node node = module.getNode();

        List<PowerlinkSubobject> subObjectsList = object.getSubObjects();

        for (PowerlinkSubobject subObject : subObjectsList) {
            System.err.println(
                    "sub-Objects list..................................................................................."
                            + subObject.getIdHex());
            ObjectType subObjectType = getObjectType(subObject.getObjectType());
            PDOMapping pdoMapping = getPdoMapping(subObject.getPdoMapping());

            if (subObject.getUniqueIDRef() == null) {

                PlkDataType dataType = getObjectDatatype(
                        subObject.getDataType());

                AccessType accessType = getAccessType(
                        subObject.getAccessType());

                String actualValue = subObject.getActualValue();

                // An workaround to avoid the library setting the actualValue
                // for non writable sub-objects.
                if ((accessType == AccessType.CONST)
                        || (accessType == AccessType.RO)) {
                    actualValue = StringUtils.EMPTY;
                }

                libApiRes = core.CreateModuleSubObject(node.getNetworkId(),
                        node.getNodeId(),
                        module.getInterfaceOfModule().getInterfaceUId(),
                        module.getChildID(), module.getPosition(),
                        object.getId(), subObject.getId(), subObjectType,
                        subObject.getName(), dataType, accessType, pdoMapping,
                        subObject.getDefaultValue(), actualValue);

                if (libApiRes.IsSuccessful()) {
                    System.err.println("Index#####......" + index);
                    libApiRes = core.SetSubObjectLimits(node.getNetworkId(),
                            node.getNodeId(), index, subObject.getId(),
                            subObject.getLowLimit(), subObject.getHighLimit());
                    if (!libApiRes.IsSuccessful()) {
                        subObject.setError(getErrorMessage(libApiRes));
                        System.err.println("Module sub-object limits WARN: "
                                + getErrorMessage(libApiRes));
                    }
                    // else {
                    // try {
                    // OpenConfiguratorProjectUtils
                    // .updateModuleSubObjectInNode(node, module,
                    // index, subObject);
                    // } catch (JDOMException | IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // }

                } else {
                    subObject.setError(getErrorMessage(libApiRes));
                    OpenConfiguratorMessageConsole.getInstance()
                            .printLibraryErrorMessage(libApiRes);
                    System.err.println("Module sub-object.. WARN: "
                            + getErrorMessage(libApiRes));
                }
            } else {
                // Domain objects.
                if ((subObject.getUniqueIDRef() != null)
                        && (subObject.getDataType() != null)) {
                    if (subObject
                            .getUniqueIDRef() instanceof TParameterList.Parameter) {
                        Parameter parameter = (Parameter) subObject
                                .getUniqueIDRef();
                        libApiRes = core.CreateModuleParameterSubObject(
                                node.getNetworkId(), node.getNodeId(),
                                node.getInterface().getInterfaceUId(),
                                module.getChildID(), module.getPosition(),
                                object.getId(), subObject.getId(),
                                subObjectType, subObject.getName(),
                                getObjectDatatype(subObject.getDataType()),
                                getAccessType(subObject.getAccessType()),
                                pdoMapping, parameter.getUniqueID(),
                                subObject.getDefaultValue(),
                                subObject.getActualValue());

                        if (!libApiRes.IsSuccessful()) {
                            subObject.setError(getErrorMessage(libApiRes));
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(libApiRes);
                            System.err.println(
                                    "Create Parameter sub-Object WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                        // else {
                        // try {
                        // OpenConfiguratorProjectUtils
                        // .updateModuleSubObjectInNode(node,
                        // module, index, subObject);
                        // } catch (JDOMException | IOException e) {
                        // // TODO Auto-generated catch block
                        // e.printStackTrace();
                        // }
                        // }
                    } else if (subObject
                            .getUniqueIDRef() instanceof TParameterGroup) {
                        TParameterGroup parameterGrp = (TParameterGroup) subObject
                                .getUniqueIDRef();

                        libApiRes = core.CreateModuleParameterSubObject(
                                node.getNetworkId(), node.getNodeId(),
                                node.getInterface().getInterfaceUId(),
                                module.getChildID(), module.getPosition(),
                                object.getId(), subObject.getId(),
                                subObjectType, subObject.getName(),
                                getObjectDatatype(subObject.getDataType()),
                                getAccessType(subObject.getAccessType()),
                                pdoMapping, parameterGrp.getUniqueID(),
                                subObject.getDefaultValue(),
                                subObject.getActualValue());
                        if (!libApiRes.IsSuccessful()) {
                            subObject.setError(getErrorMessage(libApiRes));
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(libApiRes);
                            System.err.println(
                                    "Create Parameter.............sub-object of parameter group WARN: "
                                            + getErrorMessage(libApiRes)
                                            + libApiRes.GetErrorType()
                                            + libApiRes.GetErrorMessage()
                                            + " Datatype == "
                                            + getObjectDatatype(
                                                    subObject.getDataType())
                                                            .name());
                        } else {
                            System.err.println(
                                    "Sub-object created successfully..");
                        }
                        // else {
                        // try {
                        // OpenConfiguratorProjectUtils
                        // .updateModuleSubObjectInNode(node,
                        // module, index, subObject);
                        // } catch (JDOMException | IOException e) {
                        // // TODO Auto-generated catch block
                        // e.printStackTrace();
                        // }
                        // }
                    } else {
                        System.err.println(
                                "ERROR: New instance available as a UniqueIdReference");
                    }
                } else if ((subObject.getUniqueIDRef() != null)
                        && (subObject.getDataType() == null)) {
                    PlkDataType dataType = PlkDataType.UNDEFINED;
                    if (subObject
                            .getUniqueIDRef() instanceof TParameterList.Parameter) {
                        Parameter parameter = (Parameter) subObject
                                .getUniqueIDRef();
                        libApiRes = core.CreateModuleParameterSubObject(
                                node.getNetworkId(), node.getNodeId(),
                                node.getInterface().getInterfaceUId(),
                                module.getChildID(), module.getPosition(),
                                object.getId(), subObject.getId(),
                                subObjectType, subObject.getName(),
                                getObjectDatatype(subObject.getDataType()),
                                getAccessType(subObject.getAccessType()),
                                pdoMapping, parameter.getUniqueID(),
                                subObject.getDefaultValue(),
                                subObject.getActualValue());
                        if (!libApiRes.IsSuccessful()) {
                            subObject.setError(getErrorMessage(libApiRes));
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(libApiRes);
                            System.err.println(
                                    "Create Parameter sub-Object WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                        // else {
                        // try {
                        // OpenConfiguratorProjectUtils
                        // .updateModuleSubObjectInNode(node,
                        // module, index, subObject);
                        // } catch (JDOMException | IOException e) {
                        // // TODO Auto-generated catch block
                        // e.printStackTrace();
                        // }
                        // }
                    } else if (subObject
                            .getUniqueIDRef() instanceof TParameterGroup) {
                        TParameterGroup parameterGrp = (TParameterGroup) subObject
                                .getUniqueIDRef();
                        libApiRes = core.CreateModuleParameterSubObject(
                                node.getNetworkId(), node.getNodeId(),
                                node.getInterface().getInterfaceUId(),
                                module.getChildID(), module.getPosition(),
                                object.getId(), subObject.getId(),
                                subObjectType, subObject.getName(),
                                getObjectDatatype(subObject.getDataType()),
                                getAccessType(subObject.getAccessType()),
                                pdoMapping, parameterGrp.getUniqueID(),
                                subObject.getDefaultValue(),
                                subObject.getActualValue());
                        if (!libApiRes.IsSuccessful()) {
                            subObject.setError(getErrorMessage(libApiRes));
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(libApiRes);
                            System.err.println(
                                    "Create Parameter sub-object of parameter group WARN: "
                                            + getErrorMessage(libApiRes)
                                            + libApiRes.GetErrorType()
                                            + libApiRes.GetErrorMessage()
                                            + " Datatype == "
                                            + getObjectDatatype(
                                                    subObject.getDataType())
                                                            .name());
                        }
                        // else {
                        // try {
                        // OpenConfiguratorProjectUtils
                        // .updateModuleSubObjectInNode(node,
                        // module, index, subObject);
                        // } catch (JDOMException | IOException e) {
                        // // TODO Auto-generated catch block
                        // e.printStackTrace();
                        // }
                        // }
                    } else {
                        System.err.println(
                                "ERROR: New instance available as a UniqueIdReference");
                    }
                } else {
                    subObject.setError(
                            "Invalid subObject.getUniqueIDRef() + subObject.getDataType() ");
                    OpenConfiguratorMessageConsole.getInstance()
                            .printErrorMessage(
                                    "Sub-Object " + subObject.getNameWithId()
                                            + " has not created in the library",
                                    subObject.getNode().getNetworkId());
                    System.err.println(
                            "ERROR: Invalid subObject.getUniqueIDRef():"
                                    + subObject.getUniqueIDRef()
                                    + " SubObject ID " + subObject.getName());
                }
            }
        }

        return libApiRes;
    }

    /**
     * Add the list of sub-objects available in the object into the library.
     *
     * @param Node Instance of Node.
     * @param object Instance of POWERLINK Object to get list of objects.
     * @return Result instance from the library.
     */
    private static Result addSubObjects(final Node node,
            PowerlinkObject object) {

        Result libApiRes = new Result();
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        List<PowerlinkSubobject> subObjectsList = object.getSubObjects();
        for (PowerlinkSubobject subObject : subObjectsList) {

            ObjectType subObjectType = getObjectType(subObject.getObjectType());
            PDOMapping pdoMapping = getPdoMapping(subObject.getPdoMapping());

            if (subObject.getUniqueIDRef() == null) {

                PlkDataType dataType = getObjectDatatype(
                        subObject.getDataType());

                AccessType accessType = getAccessType(
                        subObject.getAccessType());

                String actualValue = subObject.getActualValue();

                // An workaround to avoid the library setting the actualValue
                // for non writable sub-objects.
                if ((accessType == AccessType.CONST)
                        || (accessType == AccessType.RO)) {
                    actualValue = StringUtils.EMPTY;
                }

                libApiRes = core.CreateSubObject(node.getNetworkId(),
                        node.getNodeId(), object.getId(), subObject.getId(),
                        subObjectType, subObject.getName(), dataType,
                        accessType, pdoMapping, subObject.getDefaultValue(),
                        actualValue);
                if (libApiRes.IsSuccessful()) {
                    libApiRes = core.SetSubObjectLimits(node.getNetworkId(),
                            node.getNodeId(), object.getId(), subObject.getId(),
                            subObject.getLowLimit(), subObject.getHighLimit());
                    if (!libApiRes.IsSuccessful()) {
                        subObject.setError(getErrorMessage(libApiRes));
                        System.err
                                .println("WARN: " + getErrorMessage(libApiRes));
                    }
                } else {
                    subObject.setError(getErrorMessage(libApiRes));
                    OpenConfiguratorMessageConsole.getInstance()
                            .printLibraryErrorMessage(libApiRes);
                    System.err.println("WARN: " + getErrorMessage(libApiRes));
                }
            } else {
                // Domain objects.
                if ((subObject.getUniqueIDRef() != null)
                        && (subObject.getDataType() != null)) {
                    if (subObject
                            .getUniqueIDRef() instanceof TParameterList.Parameter) {
                        Parameter parameter = (Parameter) subObject
                                .getUniqueIDRef();
                        libApiRes = core.CreateParameterSubObject(
                                node.getNetworkId(), node.getNodeId(),
                                object.getId(), subObject.getId(),
                                subObjectType, subObject.getName(),
                                getObjectDatatype(subObject.getDataType()),
                                getAccessType(subObject.getAccessType()),
                                pdoMapping, parameter.getUniqueID(),
                                subObject.getDefaultValue(),
                                subObject.getActualValue());
                        if (!libApiRes.IsSuccessful()) {
                            subObject.setError(getErrorMessage(libApiRes));
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(libApiRes);
                            System.err.println(
                                    "Create Parameter sub-Object WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                    } else if (subObject
                            .getUniqueIDRef() instanceof TParameterGroup) {
                        TParameterGroup parameterGrp = (TParameterGroup) subObject
                                .getUniqueIDRef();
                        libApiRes = core.CreateParameterSubObject(
                                node.getNetworkId(), node.getNodeId(),
                                object.getId(), subObject.getId(),
                                subObjectType, subObject.getName(),
                                getObjectDatatype(subObject.getDataType()),
                                getAccessType(subObject.getAccessType()),
                                pdoMapping, parameterGrp.getUniqueID(),
                                subObject.getDefaultValue(),
                                subObject.getActualValue());
                        if (!libApiRes.IsSuccessful()) {
                            subObject.setError(getErrorMessage(libApiRes));
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(libApiRes);
                            System.err.println(
                                    "Create Parameter sub-object of parameter group WARN: "
                                            + getErrorMessage(libApiRes)
                                            + libApiRes.GetErrorType()
                                            + libApiRes.GetErrorMessage()
                                            + " Datatype == "
                                            + getObjectDatatype(
                                                    subObject.getDataType())
                                                            .name());
                        }
                    } else {
                        System.err.println(
                                "ERROR: New instance available as a UniqueIdReference");
                    }
                } else if ((subObject.getUniqueIDRef() != null)
                        && (subObject.getDataType() == null)) {
                    PlkDataType dataType = PlkDataType.UNDEFINED;
                    if (subObject
                            .getUniqueIDRef() instanceof TParameterList.Parameter) {
                        Parameter parameter = (Parameter) subObject
                                .getUniqueIDRef();
                        libApiRes = core.CreateParameterSubObject(
                                node.getNetworkId(), node.getNodeId(),
                                object.getId(), subObject.getId(),
                                subObjectType, subObject.getName(), dataType,
                                getAccessType(subObject.getAccessType()),
                                pdoMapping, parameter.getUniqueID(),
                                subObject.getDefaultValue(),
                                subObject.getActualValue());
                        if (!libApiRes.IsSuccessful()) {
                            subObject.setError(getErrorMessage(libApiRes));
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(libApiRes);
                            System.err.println(
                                    "Create Parameter sub-Object WARN: "
                                            + getErrorMessage(libApiRes));
                        }
                    } else if (subObject
                            .getUniqueIDRef() instanceof TParameterGroup) {
                        TParameterGroup parameterGrp = (TParameterGroup) subObject
                                .getUniqueIDRef();
                        libApiRes = core.CreateParameterSubObject(
                                node.getNetworkId(), node.getNodeId(),
                                object.getId(), subObject.getId(),
                                subObjectType, subObject.getName(), dataType,
                                getAccessType(subObject.getAccessType()),
                                pdoMapping, parameterGrp.getUniqueID(),
                                subObject.getDefaultValue(),
                                subObject.getActualValue());
                        if (!libApiRes.IsSuccessful()) {
                            subObject.setError(getErrorMessage(libApiRes));
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(libApiRes);
                            System.err.println(
                                    "Create Parameter sub-object of parameter group WARN: "
                                            + getErrorMessage(libApiRes)
                                            + libApiRes.GetErrorType()
                                            + libApiRes.GetErrorMessage()
                                            + " Datatype == "
                                            + getObjectDatatype(
                                                    subObject.getDataType())
                                                            .name());
                        }
                    } else {
                        System.err.println(
                                "ERROR: New instance available as a UniqueIdReference");
                    }
                } else {
                    subObject.setError(
                            "Invalid subObject.getUniqueIDRef() + subObject.getDataType() ");
                    OpenConfiguratorMessageConsole.getInstance()
                            .printErrorMessage(
                                    "Sub-Object " + subObject.getNameWithId()
                                            + " has not created in the library",
                                    subObject.getNode().getNetworkId());
                    System.err.println(
                            "ERROR: Invalid subObject.getUniqueIDRef():"
                                    + subObject.getUniqueIDRef()
                                    + " SubObject ID " + subObject.getName());
                }
            }
        }

        return libApiRes;
    }

    /**
     * Clears the mapping values in the given channel.
     *
     * @param channel The channel values to be cleared.
     * @return The result from the library.
     */
    public static Result clearChannelMapping(PdoChannel channel) {
        System.out
                .println("ClearMappingChannel: " + channel.getNode().getNodeId()
                        + " Direction:" + getDirection(channel.getPdoType())
                        + " ChannelNumber:" + channel.getChannelNumber());
        return OpenConfiguratorCore.GetInstance().ClearMappingChannel(
                channel.getNode().getNetworkId(), channel.getNode().getNodeId(),
                getDirection(channel.getPdoType()), channel.getChannelNumber());
    }

    /**
     * Clears the mapping values present in the given subobject.
     *
     * @param channel The mapping channel.
     * @param mappingSubObject The subobject for which the values has to be
     *            cleared.
     * @return The result from the library.
     */
    public static Result clearSelectedmappingobject(PdoChannel channel,
            PowerlinkSubobject mappingSubObject) {

        System.out
                .println("ClearMappingObject: " + channel.getNode().getNodeId()
                        + " Direction:" + getDirection(channel.getPdoType())
                        + " ChannelNumber:" + channel.getChannelNumber()
                        + " Position:" + mappingSubObject.getId());

        return OpenConfiguratorCore.GetInstance().ClearMappingObject(
                channel.getNode().getNetworkId(), channel.getNode().getNodeId(),
                getDirection(channel.getPdoType()), channel.getChannelNumber(),
                mappingSubObject.getId());
    }

    private static Result createModularHeadNode(Node node) {
        Result libApiRes = OpenConfiguratorCore.GetInstance()
                .CreateModularHeadNode(node.getNetworkId(), node.getNodeId(),
                        node.getName());
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        libApiRes = importXddModel(node);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        // FIXME: this is a workaround for the issue with the library.
        // The library returns error if the node is set "enabled" for MN.
        if (node.getNodeId() == IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
            return libApiRes;
        }

        HeadNodeInterface headNodeInterface = node.getInterface();
        libApiRes = OpenConfiguratorCore.GetInstance()
                .CreateInterface(node.getNetworkId(), node.getNodeId(),
                        headNodeInterface.getInterfaceUId(),
                        headNodeInterface.getInterfaceType(),
                        getModuleAddressing(
                                headNodeInterface.getModuleAddressing()),
                headNodeInterface.getMaxModules().longValue(),
                headNodeInterface.isUnUsedSlots(),
                headNodeInterface.isMultipleModules());

        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        List<Range> rangeList = headNodeInterface.getlistofRange();
        for (Range range : rangeList) {
            libApiRes = OpenConfiguratorCore.GetInstance().CreateRange(
                    node.getNetworkId(), node.getNodeId(),
                    headNodeInterface.getInterfaceUId(), range.getName(),
                    getLong(range.getBaseIndex()), getLong(range.getMaxIndex()),
                    getLong(range.getMaxSubIndex()),
                    range.getSortStep().longValue(),
                    getSortMode(range.getSortMode()),
                    getSortNumber(range.getSortNumber()),
                    getPdoMapping(range.getPDOmapping()));

            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }
        }

        // TODO: Create Range for modular head node.

        libApiRes = OpenConfiguratorCore.GetInstance().EnableNode(
                node.getNetworkId(), node.getNodeId(), node.isEnabled());
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        return libApiRes;
    }

    private static Result createModule(Module module) {
        Node node = module.getNode();
        Result libApiRes = OpenConfiguratorCore.GetInstance().CreateModule(
                node.getNetworkId(), node.getNodeId(),
                node.getInterface().getInterfaceUId(), module.getChildID(),
                module.getPosition(), module.getAddress(),
                module.getModuleType(), module.getModuleName(),
                getModuleAddressing(module.getModuleAddressing()),
                module.getMinPosition(), module.getMaxPosition(),
                module.getMinAddress(), module.getMaxAddress(),
                module.getMaxCount());
        System.err.println("Node id.." + node.getNodeId() + " interfaceID.. "
                + node.getInterface().getInterfaceUId() + " Module Id. . "
                + module.getChildID());
        if (!libApiRes.IsSuccessful()) {
            System.err.println("Create Module..... lib error");
            return libApiRes;
        }

        libApiRes = importXddModel(module);
        if (!libApiRes.IsSuccessful()) {
            System.err.println("Import XDD Module..... lib error");
            return libApiRes;
        }

        return libApiRes;
    }

    private static Result createNode(final Node node) {
        Result libApiRes = OpenConfiguratorCore.GetInstance().CreateNode(
                node.getNetworkId(), node.getNodeId(), node.getName(),
                (node.getNodeType() == Node.NodeType.REDUNDANT_MANAGING_NODE
                        ? true : false));
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        libApiRes = importXddModel(node);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        // FIXME: this is a workaround for the issue with the library.
        // The library returns error if the node is set "enabled" for MN.
        if (node.getNodeId() == IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
            return libApiRes;
        }

        libApiRes = OpenConfiguratorCore.GetInstance().EnableNode(
                node.getNetworkId(), node.getNodeId(), node.isEnabled());
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        return libApiRes;
    }

    /**
     * Force the object's actual value to the library.
     *
     * @param plkObject The object for which the value has to be forced.
     * @param force True to force.
     * @return The result from the library.
     */
    public static Result forceObject(PowerlinkObject plkObject, boolean force) {
        Result res = OpenConfiguratorCore.GetInstance().SetObjectActualValue(
                plkObject.getNetworkId(), plkObject.getNodeId(),
                plkObject.getId(), plkObject.getActualValue(), force, false);
        return res;
    }

    /**
     * Force the sub-object's actual value to the library.
     *
     * @param plkSubObject The sub-object for which the value has to be forced.
     * @param force True to force.
     * @return The result from the library.
     */
    public static Result forceSubObject(PowerlinkSubobject plkSubObject,
            boolean force) {
        String actualValue = plkSubObject.getActualValue();
        if (actualValue == null) {
            actualValue = "";
        }

        Result res = OpenConfiguratorCore.GetInstance().SetSubObjectActualValue(
                plkSubObject.getNetworkId(), plkSubObject.getNodeId(),
                plkSubObject.getObject().getId(), plkSubObject.getId(),
                actualValue, force, false);
        return res;
    }

    /**
     * Get the library compatible accessType for the one in the XDC.
     *
     * @param accessTypeXdc AccessType from the XDC.
     *
     * @return The compatible library accessType.
     */
    public static AccessType getAccessType(TObjectAccessType accessTypeXdc) {
        AccessType accessType = AccessType.UNDEFINED;
        if (accessTypeXdc != null) {
            switch (accessTypeXdc.value()) {
                case "ro":
                    accessType = AccessType.RO;
                    break;
                case "wo":
                    accessType = AccessType.WO;
                    break;
                case "rw":
                    accessType = AccessType.RW;
                    break;
                case "const":
                    accessType = AccessType.CONST;
                    break;
                default:
                    break;
            }
        }
        return accessType;
    }

    /**
     * Get PDO channel object actual value.
     *
     * @param pdoChannel PDO channel instance.
     * @param objectJCollection Collection of POWERLINK objects.
     * @return Result from the library.
     */
    public static Result getChannelObjectsWithActualValue(
            final PdoChannel pdoChannel,
            java.util.LinkedHashMap<java.util.Map.Entry<Long, Integer>, String> objectJCollection) {
        ObjectCollection objectCollection = new ObjectCollection();
        Result res = OpenConfiguratorCore.GetInstance().GetChannelActualValues(
                pdoChannel.getNode().getNetworkId(),
                pdoChannel.getNode().getNodeId(),
                OpenConfiguratorLibraryUtils
                        .getDirection(pdoChannel.getPdoType()),
                pdoChannel.getChannelNumber(), objectCollection);

        if (!res.IsSuccessful()) {
            return res;
        }

        for (MapIterator iterator = objectCollection.iterator(); iterator
                .hasNext();) {
            String actualValue = iterator.GetValue();

            Map.Entry<Long, Integer> entryVal = new AbstractMap.SimpleEntry<Long, Integer>(
                    iterator.GetKey().getFirst(),
                    iterator.GetKey().getSecond());
            objectJCollection.put(entryVal, actualValue);
            iterator.next();
        }

        return res;
    }

    /**
     * Get the size of TPDO/RPDO channels.
     *
     * @param channel The mapping channel.
     * @return The total size of channel.
     */
    public static long getChannelSize(PdoChannel channel) {
        long[] tempSize = new long[1];
        Result res = OpenConfiguratorCore.GetInstance().GetChannelSize(
                channel.getNode().getNetworkId(), channel.getNode().getNodeId(),
                getDirection(channel.getPdoType()), channel.getChannelNumber(),
                tempSize);
        return tempSize[0];
    }

    /**
     * Get the library compatible Direction based on the given PDO type.
     *
     * @param pdoType The PDO type.
     * @return Library compatible Direction.
     */
    public static Direction getDirection(PdoType pdoType) {
        Direction dir;
        switch (pdoType) {
            case TPDO:
                dir = Direction.TX;
                break;
            case RPDO:
                dir = Direction.RX;
                break;
            default:
                dir = Direction.RX;
                System.err.println("Invalid PDO type.");
                break;
        }

        return dir;
    }

    /**
     * Get the library compatible dynamicChannel accessType for the one in the
     * XDC.
     *
     * @param dynAccessTypXdc AccessType from the XDC.
     * @return The compatible library dynamicChannel accessType.
     */
    private static DynamicChannelAccessType getDynamicChannelAccessType(
            String dynAccessTypXdc) {
        DynamicChannelAccessType dynamicChannelAccessType = DynamicChannelAccessType.UNDEFINED;
        if (dynAccessTypXdc != null) {
            switch (dynAccessTypXdc.trim()) {
                case "readOnly":
                    dynamicChannelAccessType = DynamicChannelAccessType.readOnly;
                    break;
                case "writeOnly":
                    dynamicChannelAccessType = DynamicChannelAccessType.writeOnly;
                    break;
                case "readWriteOutput":
                    dynamicChannelAccessType = DynamicChannelAccessType.readWriteOutput;
                    break;
                default:
                    break;
            }
        }
        return dynamicChannelAccessType;
    }

    /**
     * Return the error message in a format from the openCONFIGURATOR result API
     * library.
     *
     * @param result The result from openCONFIGURATOR library.
     * @return The error message from the result instance.
     */
    public static String getErrorMessage(final Result result) {
        String errorMesage = result.GetErrorMessage();
        if (errorMesage.isEmpty()) {
            errorMesage = result.GetErrorType().toString();
            System.err.println("ErrorMessage is empty. Error Code:"
                    + result.GetErrorType());
        }
        return errorMesage;
    }

    /**
     * Returns the IEC datatype associated with the parameter.
     *
     * @param parameter The parameter instance from the XDC.
     * @return The IEC datatype of the parameter.
     */
    private static IEC_Datatype getIEC_DataType(Parameter parameter) {
        // TODO: Provide support for datatypeID ref also.

        // parameter.getDataTypeIDRef()
        if (parameter.getBITSTRING() != null) {
            return IEC_Datatype.BITSTRING;
        } else if (parameter.getBOOL() != null) {
            return IEC_Datatype.BOOL;
        } else if (parameter.getBYTE() != null) {
            return IEC_Datatype.BYTE;
        } else if (parameter.getCHAR() != null) {
            return IEC_Datatype._CHAR;
        } else if (parameter.getDINT() != null) {
            return IEC_Datatype.DINT;
        } else if (parameter.getDWORD() != null) {
            return IEC_Datatype.DWORD;
        } else if (parameter.getINT() != null) {
            return IEC_Datatype.INT;
        } else if (parameter.getLINT() != null) {
            return IEC_Datatype.LINT;
        } else if (parameter.getLREAL() != null) {
            return IEC_Datatype.LREAL;
        } else if (parameter.getLWORD() != null) {
            return IEC_Datatype.LWORD;
        } else if (parameter.getREAL() != null) {
            return IEC_Datatype.REAL;
        } else if (parameter.getSINT() != null) {
            return IEC_Datatype.SINT;
        } else if (parameter.getSTRING() != null) {
            return IEC_Datatype.STRING;
        } else if (parameter.getUDINT() != null) {
            return IEC_Datatype.UDINT;
        } else if (parameter.getUINT() != null) {
            return IEC_Datatype.UINT;
        } else if (parameter.getULINT() != null) {
            return IEC_Datatype.ULINT;
        } else if (parameter.getUSINT() != null) {
            return IEC_Datatype.USINT;
        } else if (parameter.getWORD() != null) {
            return IEC_Datatype.WORD;
        } else if (parameter.getWSTRING() != null) {
            return IEC_Datatype.WSTRING;
        } else {
            System.err.println("Parameter Un handled IEC_Datatype value");
            if (parameter.getDataTypeIDRef() != null) {
                System.err.println(parameter.getDataTypeIDRef());
            } else {
                System.err.println("No uniqueIDRef");
            }
        }

        return IEC_Datatype.UNDEFINED;
    }

    /**
     * Returns the IEC datatype associated with the Array.
     *
     * @param arrayDt
     * @return The IEC datatype of the Array list..
     */
    private static IEC_Datatype getIEC_DataType(TDataTypeList.Array arrayDt) {

        if (arrayDt.getBITSTRING() != null) {
            return IEC_Datatype.BITSTRING;
        } else if (arrayDt.getBOOL() != null) {
            return IEC_Datatype.BOOL;
        } else if (arrayDt.getBYTE() != null) {
            return IEC_Datatype.BYTE;
        } else if (arrayDt.getCHAR() != null) {
            return IEC_Datatype._CHAR;
        } else if (arrayDt.getDINT() != null) {
            return IEC_Datatype.DINT;
        } else if (arrayDt.getDWORD() != null) {
            return IEC_Datatype.DWORD;
        } else if (arrayDt.getINT() != null) {
            return IEC_Datatype.INT;
        } else if (arrayDt.getLINT() != null) {
            return IEC_Datatype.LINT;
        } else if (arrayDt.getLREAL() != null) {
            return IEC_Datatype.LREAL;
        } else if (arrayDt.getLWORD() != null) {
            return IEC_Datatype.LWORD;
        } else if (arrayDt.getREAL() != null) {
            return IEC_Datatype.REAL;
        } else if (arrayDt.getSINT() != null) {
            return IEC_Datatype.SINT;
        } else if (arrayDt.getSTRING() != null) {
            return IEC_Datatype.STRING;
        } else if (arrayDt.getUDINT() != null) {
            return IEC_Datatype.UDINT;
        } else if (arrayDt.getUINT() != null) {
            return IEC_Datatype.UINT;
        } else if (arrayDt.getULINT() != null) {
            return IEC_Datatype.ULINT;
        } else if (arrayDt.getUSINT() != null) {
            return IEC_Datatype.USINT;
        } else if (arrayDt.getWORD() != null) {
            return IEC_Datatype.WORD;
        } else if (arrayDt.getWSTRING() != null) {
            return IEC_Datatype.WSTRING;
        } else {
            System.err.println("Array Un handled IEC_Datatype value");
        }

        return IEC_Datatype.UNDEFINED;
    }

    private static IEC_Datatype getIEC_DataType(TDataTypeList.Enum enumDt) {

        if (enumDt.getBITSTRING() != null) {
            return IEC_Datatype.BITSTRING;
        } else if (enumDt.getBOOL() != null) {
            return IEC_Datatype.BOOL;
        } else if (enumDt.getBYTE() != null) {
            return IEC_Datatype.BYTE;
        } else if (enumDt.getCHAR() != null) {
            return IEC_Datatype._CHAR;
        } else if (enumDt.getDINT() != null) {
            return IEC_Datatype.DINT;
        } else if (enumDt.getDWORD() != null) {
            return IEC_Datatype.DWORD;
        } else if (enumDt.getINT() != null) {
            return IEC_Datatype.INT;
        } else if (enumDt.getLINT() != null) {
            return IEC_Datatype.LINT;
        } else if (enumDt.getLREAL() != null) {
            return IEC_Datatype.LREAL;
        } else if (enumDt.getLWORD() != null) {
            return IEC_Datatype.LWORD;
        } else if (enumDt.getREAL() != null) {
            return IEC_Datatype.REAL;
        } else if (enumDt.getSINT() != null) {
            return IEC_Datatype.SINT;
        } else if (enumDt.getSTRING() != null) {
            return IEC_Datatype.STRING;
        } else if (enumDt.getUDINT() != null) {
            return IEC_Datatype.UDINT;
        } else if (enumDt.getUINT() != null) {
            return IEC_Datatype.UINT;
        } else if (enumDt.getULINT() != null) {
            return IEC_Datatype.ULINT;
        } else if (enumDt.getUSINT() != null) {
            return IEC_Datatype.USINT;
        } else if (enumDt.getWORD() != null) {
            return IEC_Datatype.WORD;
        } else if (enumDt.getWSTRING() != null) {
            return IEC_Datatype.WSTRING;
        } else {
            System.err.println("Array Un handled IEC_Datatype value");
        }

        return IEC_Datatype.UNDEFINED;
    }

    private static IEC_Datatype getIEC_DataType(
            TParameterTemplate parameterTemplate) {
        // TODO: Provide support for datatypeID ref also.

        // parameter.getDataTypeIDRef()
        if (parameterTemplate.getBITSTRING() != null) {
            return IEC_Datatype.BITSTRING;
        } else if (parameterTemplate.getBOOL() != null) {
            return IEC_Datatype.BOOL;
        } else if (parameterTemplate.getBYTE() != null) {
            return IEC_Datatype.BYTE;
        } else if (parameterTemplate.getCHAR() != null) {
            return IEC_Datatype._CHAR;
        } else if (parameterTemplate.getDINT() != null) {
            return IEC_Datatype.DINT;
        } else if (parameterTemplate.getDWORD() != null) {
            return IEC_Datatype.DWORD;
        } else if (parameterTemplate.getINT() != null) {
            return IEC_Datatype.INT;
        } else if (parameterTemplate.getLINT() != null) {
            return IEC_Datatype.LINT;
        } else if (parameterTemplate.getLREAL() != null) {
            return IEC_Datatype.LREAL;
        } else if (parameterTemplate.getLWORD() != null) {
            return IEC_Datatype.LWORD;
        } else if (parameterTemplate.getREAL() != null) {
            return IEC_Datatype.REAL;
        } else if (parameterTemplate.getSINT() != null) {
            return IEC_Datatype.SINT;
        } else if (parameterTemplate.getSTRING() != null) {
            return IEC_Datatype.STRING;
        } else if (parameterTemplate.getUDINT() != null) {
            return IEC_Datatype.UDINT;
        } else if (parameterTemplate.getUINT() != null) {
            return IEC_Datatype.UINT;
        } else if (parameterTemplate.getULINT() != null) {
            return IEC_Datatype.ULINT;
        } else if (parameterTemplate.getUSINT() != null) {
            return IEC_Datatype.USINT;
        } else if (parameterTemplate.getWORD() != null) {
            return IEC_Datatype.WORD;
        } else if (parameterTemplate.getWSTRING() != null) {
            return IEC_Datatype.WSTRING;
        } else {
            System.err.println("Parameter Un handled IEC_Datatype value");
            if (parameterTemplate.getDataTypeIDRef() != null) {
                System.err.println(parameterTemplate.getDataTypeIDRef());
            } else {
                System.err.println("No uniqueIDRef");
            }
        }

        return IEC_Datatype.UNDEFINED;
    }

    /**
     * Returns the IEC datatype associated with the Array.
     *
     * @param varDecl
     * @return The IEC datatype of the TvarDecleration.
     */
    private static IEC_Datatype getIEC_DataType(TVarDeclaration varDecl) {

        if (varDecl.getBITSTRING() != null) {
            return IEC_Datatype.BITSTRING;
        } else if (varDecl.getBOOL() != null) {
            return IEC_Datatype.BOOL;
        } else if (varDecl.getBYTE() != null) {
            return IEC_Datatype.BYTE;
        } else if (varDecl.getCHAR() != null) {
            return IEC_Datatype._CHAR;
        } else if (varDecl.getDINT() != null) {
            return IEC_Datatype.DINT;
        } else if (varDecl.getDWORD() != null) {
            return IEC_Datatype.DWORD;
        } else if (varDecl.getINT() != null) {
            return IEC_Datatype.INT;
        } else if (varDecl.getLINT() != null) {
            return IEC_Datatype.LINT;
        } else if (varDecl.getLREAL() != null) {
            return IEC_Datatype.LREAL;
        } else if (varDecl.getLWORD() != null) {
            return IEC_Datatype.LWORD;
        } else if (varDecl.getREAL() != null) {
            return IEC_Datatype.REAL;
        } else if (varDecl.getSINT() != null) {
            return IEC_Datatype.SINT;
        } else if (varDecl.getSTRING() != null) {
            return IEC_Datatype.STRING;
        } else if (varDecl.getUDINT() != null) {
            return IEC_Datatype.UDINT;
        } else if (varDecl.getUINT() != null) {
            return IEC_Datatype.UINT;
        } else if (varDecl.getULINT() != null) {
            return IEC_Datatype.ULINT;
        } else if (varDecl.getUSINT() != null) {
            return IEC_Datatype.USINT;
        } else if (varDecl.getWORD() != null) {
            return IEC_Datatype.WORD;
        } else if (varDecl.getWSTRING() != null) {
            return IEC_Datatype.WSTRING;
        } else {
            System.err.println("varDecl: Un handled IEC_Datatype value");

            if (varDecl.getDataTypeIDRef() != null) {
                System.err.println(varDecl.getDataTypeIDRef());
            } else {
                System.err.println("No uniqueIDRef");
            }
        }

        return IEC_Datatype.UNDEFINED;
    }

    private static int[] getintArray(short data) {
        int intArray[] = new int[1];
        System.err.println("Data ----" + data);
        if (data != 0) {
            try {
                String dataTypeS = String.valueOf(data);

                for (int i = 0; i < 1; i++) {
                    intArray[i] = Integer.parseInt(dataTypeS);
                }
                return intArray;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return intArray;
    }

    private static int[] getIntArray(byte[] data) {
        int intArray[] = new int[1];
        if (data != null) {
            try {
                String dataTypeS = DatatypeConverter.printHexBinary(data);

                for (int i = 0; i < 1; i++) {
                    intArray[i] = Integer.parseInt(dataTypeS, 16);
                    System.err.println("Long Array/...." + intArray[i]);
                }
                return intArray;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return intArray;
    }

    private static long getLong(byte[] data) {
        if (data != null) {
            try {

                String dataTypeS = DatatypeConverter.printHexBinary(data);
                long dataType_num = Long.parseLong(dataTypeS, 16);
                return dataType_num;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private static long[] getlongArray(byte[] data) {
        long longArray[] = { 0 };

        if (data != null) {
            try {
                longArray = new long[2];
                String dataTypeS = DatatypeConverter.printHexBinary(data);

                for (int i = 0; i < 2; i++) {
                    longArray[i] = Long.parseLong(dataTypeS);
                }
                return longArray;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } else {

            return longArray;
        }
        return longArray;
    }

    private static ModuleAddressing getModuleAddressing(
            TModuleAddressingChild moduleaddressingOfModule) {
        ModuleAddressing moduleAddressing = ModuleAddressing.NEXT;
        if (moduleaddressingOfModule != null) {
            switch (moduleaddressingOfModule.value()) {
                case "manual":
                    moduleAddressing = ModuleAddressing.MANUAL;
                    break;
                case "position":
                    moduleAddressing = ModuleAddressing.POSITION;
                    break;
                case "next":
                    moduleAddressing = ModuleAddressing.NEXT;
                default:
                    break;
            }
        }
        return moduleAddressing;
    }

    private static ModuleAddressing getModuleAddressing(
            TModuleAddressingHead moduleaddressingOfNode) {
        ModuleAddressing moduleAddressing = ModuleAddressing.NEXT;
        if (moduleaddressingOfNode != null) {
            switch (moduleaddressingOfNode.value()) {
                case "manual":
                    moduleAddressing = ModuleAddressing.MANUAL;
                    break;
                case "position":
                    moduleAddressing = ModuleAddressing.POSITION;
                    break;
                default:
                    break;
            }
        }
        return moduleAddressing;
    }

    /**
     * Get the object index index of module from the library
     *
     * @param module Instance of module
     * @return Library result instance.
     */
    public static long getModuleObjectIndex(Module module) {
        Result libApiRes = new Result();
        Node node = module.getNode();

        long[] index = new long[1];
        int[] subIndex = new int[1];

        libApiRes = OpenConfiguratorCore.GetInstance()
                .GetModuleObjectCurrentIndex(node.getNetworkId(),
                        node.getNodeId(), node.getInterface().getInterfaceUId(),
                        module.getChildID(), module.getPosition(), 0000, -1,
                        index, subIndex);
        System.out.println("Current Index....." + index[0]);
        System.out.println("Current sub Index....." + subIndex[0]);
        System.out.println("Child ID....." + module.getChildID());
        System.out.println("Child position....." + module.getPosition());
        if (!libApiRes.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(libApiRes);
        }
        return index[0];
    }

    /**
     * Get the object index index of module from the library
     *
     * @param module Instance of module
     * @param subObject Instance of PowerlinkSubobject.
     * @return Library result instance.
     */
    public static long getModuleObjectIndex(Module module,
            PowerlinkSubobject subObject) {
        Result libApiRes = new Result();
        Node node = module.getNode();

        long[] index = new long[1];
        int[] subIndex = new int[1];

        libApiRes = OpenConfiguratorCore.GetInstance()
                .GetModuleObjectCurrentIndex(node.getNetworkId(),
                        node.getNodeId(), node.getInterface().getInterfaceUId(),
                        module.getChildID(), module.getPosition(), 0000,
                        subObject.getId(), index, subIndex);
        System.out.println("Current Index....." + index[0]);
        System.out.println("Current sub Index....." + subIndex[0]);
        System.out.println("Child ID....." + module.getChildID());
        System.out.println("Child position....." + module.getPosition());
        if (!libApiRes.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(libApiRes);
        }
        return index[0];
    }

    /**
     * Get the sub object sub index of module from the library
     *
     * @param module Instance of module
     * @param subObject Instance of PowerlinkSubObject.
     * @return
     */
    public static int getModuleObjectSubIndex(Module module,
            PowerlinkSubobject subObject) {
        Result libApiRes = new Result();
        Node node = module.getNode();

        long[] index = new long[1];
        int[] subIndex = new int[1];

        libApiRes = OpenConfiguratorCore.GetInstance()
                .GetModuleObjectCurrentIndex(node.getNetworkId(),
                        node.getNodeId(), node.getInterface().getInterfaceUId(),
                        module.getChildID(), module.getPosition(), 0000,
                        subObject.getId(), index, subIndex);
        System.out.println("Current Index....." + index[0]);
        System.out.println("Current sub Index....." + subIndex[0]);
        System.out.println("Child ID....." + module.getChildID());
        System.out.println("Child position....." + module.getPosition());
        if (!libApiRes.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(libApiRes);
        }
        return subIndex[0];
    }

    /**
     * Get unique Id of parameter for module from the library.
     *
     * @param module Instance of mOdule.
     * @param oldParameterName parameter name of module
     * @return new parameter name of module.
     */
    public static String getModuleParameterUniqueID(Module module,
            String oldParameterName) {
        Node node = module.getNode();
        String[] parameterName = new String[1];
        Result libApiRes = OpenConfiguratorCore.GetInstance()
                .GetModuleParameterCurrentName(node.getNetworkId(),
                        node.getNodeId(),
                        module.getInterfaceOfModule().getInterfaceUId(),
                        module.getChildID(), module.getPosition(),
                        oldParameterName, parameterName);
        if (!libApiRes.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(libApiRes);
        }
        return parameterName[0];
    }

    /**
     * Get node assignment value.
     *
     * @param node The node.
     * @return The node assignment value available for the given node.
     */
    public static long getNodeAssignment(final Node node) {
        long nodeAssignValue = 0L;

        AssignmentCollection assignmentColl = new AssignmentCollection();

        Result libApiRes = OpenConfiguratorCore.GetInstance().GetNodeAssignment(
                node.getNetworkId(), node.getNodeId(), assignmentColl);
        if (!libApiRes.IsSuccessful()) {
            System.err.println("ERROR: " + getErrorMessage(libApiRes));
            return nodeAssignValue;
        }

        for (int i = 0; i < assignmentColl.size(); i++) {
            nodeAssignValue |= assignmentColl.get(i);
        }

        return nodeAssignValue;
    }

    /**
     * Get POWERLINK datatype for the given datatype ID.
     *
     * @param dataTypeRaw Datatype ID available in the XDC.
     * @return The POWERLINK datatype.
     */
    public static PlkDataType getObjectDatatype(byte[] dataTypeRaw) {
        PlkDataType plkDataType = PlkDataType.UNDEFINED;
        if (dataTypeRaw != null) {
            try {

                String dataTypeS = DatatypeConverter
                        .printHexBinary(dataTypeRaw);
                int dataType_num = Integer.parseInt(dataTypeS, 16);
                plkDataType = PlkDataType.swigToEnum(dataType_num);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return plkDataType;
    }

    /**
     * Get actual value of POWERLINK objects.
     *
     * @param node Node instance.
     * @param objectJCollection Collection of POWERLINK objects.
     * @return Result from the openCONFIGURATOR library.
     */
    public static Result getObjectsWithActualValue(final Node node,
            java.util.LinkedHashMap<java.util.Map.Entry<Long, Integer>, String> objectJCollection) {
        ObjectCollection objectCollection = new ObjectCollection();
        Result res = OpenConfiguratorCore.GetInstance()
                .GetObjectsWithActualValue(node.getNetworkId(),
                        node.getNodeId(), objectCollection);
        if (!res.IsSuccessful()) {
            return res;
        }

        for (MapIterator iterator = objectCollection.iterator(); iterator
                .hasNext();) {
            String actualValue = iterator.GetValue();

            Map.Entry<Long, Integer> entryVal = new AbstractMap.SimpleEntry<Long, Integer>(
                    iterator.GetKey().getFirst(),
                    iterator.GetKey().getSecond());
            objectJCollection.put(entryVal, actualValue);
            iterator.next();
        }

        return res;
    }

    /**
     * Get the library compatible object type for the given object type
     * available in the XDC.
     *
     * @param objectTypeXdc Object type available in the XDC.
     * @return The library compatible object type.
     */
    private static ObjectType getObjectType(short objectTypeXdc) {
        ObjectType objectType = ObjectType.INVALID;
        switch (objectTypeXdc) {
            case 5:
                objectType = ObjectType.DEFTYPE;
                break;
            case 6:
                objectType = ObjectType.DEFSTRUCT;
                break;
            case 7:
                objectType = ObjectType.VAR;
                break;
            case 8:
                objectType = ObjectType.ARRAY;
                break;
            case 9:
                objectType = ObjectType.RECORD;
                break;
            default:
        }
        return objectType;
    }

    /**
     * Get the library compatible parameter access type for the given parameter
     * access type available in the XDC.
     *
     * @param parameterAccessXdc Parameter access type available in the XDC.
     *
     * @return The library compatible parameter access type.
     */
    private static ParameterAccess getParameterAccess(
            String parameterAccessXdc) {
        ParameterAccess access = ParameterAccess.noAccess;
        if (parameterAccessXdc != null) {
            switch (parameterAccessXdc.trim()) {
                case "constant":
                    access = ParameterAccess.constant;
                    break;
                case "read":
                    access = ParameterAccess.read;
                    break;
                case "write":
                    access = ParameterAccess.write;
                    break;
                case "readWrite":
                    access = ParameterAccess.readWrite;
                    break;
                case "readWriteInput":
                    access = ParameterAccess.readWriteInput;
                    break;
                case "readWriteOutput":
                    access = ParameterAccess.readWriteOutput;
                    break;
                default:
                    break;
            }
        }
        return access;
    }

    /**
     * Get the library compatible PDO mapping for the given PDOmapping available
     * in the XDC.
     *
     * @param pdoMappingXdc PDOmapping value available in the XDC.
     * @return The library compatible PDOmapping
     */
    private static PDOMapping getPdoMapping(TObjectPDOMapping pdoMappingXdc) {
        PDOMapping pdoMapping = PDOMapping.UNDEFINED;
        if (pdoMappingXdc != null) {
            switch (pdoMappingXdc.value()) {
                case "no":
                    pdoMapping = PDOMapping.NO;
                    break;
                case "default":
                    pdoMapping = PDOMapping.DEFAULT;
                    break;
                case "optional":
                    pdoMapping = PDOMapping.OPTIONAL;
                    break;
                case "TPDO":
                    pdoMapping = PDOMapping.TPDO;
                    break;
                case "RPDO":
                    pdoMapping = PDOMapping.RPDO;
                    break;
                default:
            }
        }

        return pdoMapping;
    }

    private static SortMode getSortMode(TSortMode sortmodeXDC) {
        SortMode sortMode = SortMode.INDEX;
        if (sortmodeXDC != null) {
            switch (sortmodeXDC.value()) {
                case "index":
                    sortMode = SortMode.INDEX;
                    break;
                case "subindex":
                    sortMode = SortMode.SUBINDEX;
                    break;
                default:
            }
        }
        return sortMode;
    }

    private static SortNumber getSortNumber(
            TAddressingAttribute sortNumberXDC) {
        SortNumber sortNumber = SortNumber.CONTINUOUS;
        if (sortNumberXDC != null) {
            switch (sortNumberXDC.value()) {
                case "continuous":
                    sortNumber = SortNumber.CONTINUOUS;
                    break;
                case "address":
                    sortNumber = SortNumber.ADDRESS;
                    break;
                default:
            }
        }
        return sortNumber;
    }

    private static int[] getSubObjectIndex(short value) {
        int subIndex = value;
        int[] subIndexArray = new int[] { subIndex };
        return subIndexArray;
    }

    private static Result importProfileBodyCommunicationNetworkPowerlink(
            final Node node,
            final ProfileBodyCommunicationNetworkPowerlink commProfile) {
        Result libApiRes = new Result();

        libApiRes = addObjectDictionary(node, node.getObjectDictionary());
        if (!libApiRes.IsSuccessful()) {
            System.err.println("WARN: " + getErrorMessage(libApiRes));
        }

        if (node.getNodeId() > 239) {
            libApiRes = addDynamicChannels(node.getNetworkId(),
                    node.getNodeId(),
                    commProfile.getApplicationLayers().getDynamicChannels());
            if (!libApiRes.IsSuccessful()) {
                System.err.println("WARN: " + getErrorMessage(libApiRes));
            }
        }

        libApiRes = addNetworkManagement(node.getNetworkId(), node.getNodeId(),
                node.getNetworkManagement());
        return libApiRes;
    }

    private static Result importProfileBodyCommunicationNetworkPowerlinkModularChild(
            Module module,
            ProfileBodyCommunicationNetworkPowerlinkModularChild profileBodyDatatype) {

        Result libApiRes = new Result();

        libApiRes = addModuleObjectDictionary(module,
                module.getObjectDictionary());
        if (!libApiRes.IsSuccessful()) {
            System.err.println("Add module object dictionary WARN: "
                    + getErrorMessage(libApiRes));
        }

        return libApiRes;
    }

    private static Result importProfileBodyCommunicationNetworkPowerlinkModularHead(
            Node node,
            ProfileBodyCommunicationNetworkPowerlinkModularHead modularCommProfile) {
        Result libApiRes = new Result();

        libApiRes = addObjectDictionary(node, node.getObjectDictionary());
        if (!libApiRes.IsSuccessful()) {
            System.err.println("WARN: " + getErrorMessage(libApiRes));
        }

        if (node.getNodeId() > 239) {
            libApiRes = addDynamicChannels(node.getNetworkId(),
                    node.getNodeId(), modularCommProfile.getApplicationLayers()
                            .getDynamicChannels());
            if (!libApiRes.IsSuccessful()) {
                System.err.println("WARN: " + getErrorMessage(libApiRes));
            }
        }

        libApiRes = addNetworkManagement(node.getNetworkId(), node.getNodeId(),
                node.getNetworkManagement());
        return libApiRes;
    }

    private static Result importProfileBodyDevicePowerlink(final Node node,
            final ProfileBodyDevicePowerlink devProfile) {
        Result libApiRes = new Result();
        List<TApplicationProcess> appProcessList = devProfile
                .getApplicationProcess();
        for (TApplicationProcess appProcess : appProcessList) {

            libApiRes = addDataTypeList(node.getNetworkId(), node.getNodeId(),
                    appProcess.getDataTypeList());
            if (!libApiRes.IsSuccessful()) {
                System.err.println(
                        "Datatype WARN: " + getErrorMessage(libApiRes));
            }

            TTemplateList templateList = appProcess.getTemplateList();
            if (templateList != null) {
                libApiRes = addParameterTemplateList(node.getNetworkId(),
                        node.getNodeId(), templateList.getParameterTemplate());
                if (!libApiRes.IsSuccessful()) {
                    System.err.println(
                            "Template WARN: " + getErrorMessage(libApiRes));
                }
            }

            TParameterList parameterList = appProcess.getParameterList();
            if (parameterList != null) {
                libApiRes = addParameterList(node.getNetworkId(),
                        node.getNodeId(), parameterList.getParameter());
                if (!libApiRes.IsSuccessful()) {
                    System.err.println(
                            "Parameter WARN: " + getErrorMessage(libApiRes));
                }
            }

            libApiRes = addParameterGroup(node.getNetworkId(), node.getNodeId(),
                    appProcess.getParameterGroupList());
            if (!libApiRes.IsSuccessful()) {
                System.err.println(
                        "Parameter group WARN: " + getErrorMessage(libApiRes));
            }
        }
        return libApiRes;
    }

    private static Result importProfileBodyDevicePowerlinkModularChild(
            Module module,
            ProfileBodyDevicePowerlinkModularChild childModularProfile) {
        Result libApiRes = new Result();
        Node node = module.getNode();
        List<TApplicationProcess> appProcessList = childModularProfile
                .getApplicationProcess();

        for (TApplicationProcess appProcess : appProcessList) {
            libApiRes = addDataTypeList(node.getNetworkId(), node.getNodeId(),
                    appProcess.getDataTypeList(), module);
            if (!libApiRes.IsSuccessful()) {
                System.err.println("Add DataType list WARN: "
                        + getErrorMessage(libApiRes));
            }

            TTemplateList templateList = appProcess.getTemplateList();
            if (templateList != null) {
                libApiRes = addParameterTemplateList(node.getNetworkId(),
                        node.getNodeId(), templateList.getParameterTemplate(),
                        module);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("Add Parameter template list WARN: "
                            + getErrorMessage(libApiRes));
                }
            }
            TParameterList parameterList = appProcess.getParameterList();
            if (parameterList != null) {
                libApiRes = addParameterList(node.getNetworkId(),
                        node.getNodeId(), parameterList.getParameter(), module);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("Add Parameter list WARN: "
                            + getErrorMessage(libApiRes));
                }
            }

            TParameterGroupList parameterGroupList = appProcess
                    .getParameterGroupList();
            if (parameterGroupList != null) {
                libApiRes = addModuleParameterGroup(node.getNetworkId(),
                        node.getNodeId(), parameterGroupList, module);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("Add Parameter group list WARN: "
                            + getErrorMessage(libApiRes));
                }
            }

        }

        return libApiRes;
    }

    private static Result importProfileBodyDevicePowerlinkModularHead(Node node,
            final ProfileBodyDevicePowerlinkModularHead modularDevProfile) {
        Result libApiRes = new Result();
        List<TApplicationProcess> appProcessList = modularDevProfile
                .getApplicationProcess();
        for (TApplicationProcess appProcess : appProcessList) {

            libApiRes = addDataTypeList(node.getNetworkId(), node.getNodeId(),
                    appProcess.getDataTypeList());
            if (!libApiRes.IsSuccessful()) {
                System.err.println("WARN: " + getErrorMessage(libApiRes));
            }

            TTemplateList templateList = appProcess.getTemplateList();
            if (templateList != null) {
                libApiRes = addParameterTemplateList(node.getNetworkId(),
                        node.getNodeId(), templateList.getParameterTemplate());
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("WARN: " + getErrorMessage(libApiRes));
                }
            }

            TParameterList parameterList = appProcess.getParameterList();
            if (parameterList != null) {
                libApiRes = addParameterList(node.getNetworkId(),
                        node.getNodeId(), parameterList.getParameter());
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("WARN: " + getErrorMessage(libApiRes));
                }
            }

            TParameterGroupList parameterGroupList = appProcess
                    .getParameterGroupList();
            if (parameterGroupList != null) {
                libApiRes = addParameterGroup(node.getNetworkId(),
                        node.getNodeId(), parameterGroupList);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("WARN: " + getErrorMessage(libApiRes));
                }
            }
        }
        return libApiRes;
    }

    private static Result importXddModel(final Module module) {
        Result libApiRes = new Result();
        List<ISO15745Profile> profiles = module.getISO15745ProfileContainer()
                .getISO15745Profile();
        for (ISO15745Profile profile : profiles) {
            ProfileBodyDataType profileBodyDatatype = profile.getProfileBody();
            if (profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularChild) {
                libApiRes = importProfileBodyDevicePowerlinkModularChild(module,
                        (ProfileBodyDevicePowerlinkModularChild) profileBodyDatatype);
            } else if (profileBodyDatatype instanceof ProfileBodyCommunicationNetworkPowerlinkModularChild) {
                libApiRes = importProfileBodyCommunicationNetworkPowerlinkModularChild(
                        module,
                        (ProfileBodyCommunicationNetworkPowerlinkModularChild) profileBodyDatatype);
            } else {
                System.err.println(
                        "Unknown profile body datatype:" + profileBodyDatatype);
            }
        }

        return libApiRes;
    }

    private static Result importXddModel(final Node node) {

        Result libApiRes = new Result();

        List<ISO15745Profile> profiles = node.getISO15745ProfileContainer()
                .getISO15745Profile();
        for (ISO15745Profile profile : profiles) {
            ProfileBodyDataType profileBodyDatatype = profile.getProfileBody();
            if (profileBodyDatatype instanceof ProfileBodyDevicePowerlink) {
                libApiRes = importProfileBodyDevicePowerlink(node,
                        (ProfileBodyDevicePowerlink) profileBodyDatatype);
            } else if (profileBodyDatatype instanceof ProfileBodyCommunicationNetworkPowerlink) {
                libApiRes = importProfileBodyCommunicationNetworkPowerlink(node,
                        (ProfileBodyCommunicationNetworkPowerlink) profileBodyDatatype);
            } else if (profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularHead) {
                libApiRes = importProfileBodyDevicePowerlinkModularHead(node,
                        (ProfileBodyDevicePowerlinkModularHead) profileBodyDatatype);
            } else if (profileBodyDatatype instanceof ProfileBodyCommunicationNetworkPowerlinkModularHead) {
                libApiRes = importProfileBodyCommunicationNetworkPowerlinkModularHead(
                        node,
                        (ProfileBodyCommunicationNetworkPowerlinkModularHead) profileBodyDatatype);
            } else {
                System.err.println(
                        "Unknown profile body datatype:" + profileBodyDatatype);
            }
        }

        return libApiRes;
    }

    /**
     * Initializes the openCONFIGURATOR library with configurations. Example:
     * Boost logging configuration.
     *
     * @return Result The result from openCONFIGURATOR library.
     *
     * @throws IOException if the configuration files are not found.
     */
    public static Result initOpenConfiguratorLibrary() throws IOException {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();

        File libraryLogFile = new File(ResourcesPlugin.getWorkspace().getRoot()
                .getLocation().toString()
                + IOpenConfiguratorResource.LIBRARY_LOG_FILE_PATH);
        if (libraryLogFile.exists() && libraryLogFile.canWrite()) {
            Files.write(
                    Paths.get(ResourcesPlugin.getWorkspace().getRoot()
                            .getLocation().toString()
                            + IOpenConfiguratorResource.LIBRARY_LOG_FILE_PATH),
                    new byte[] {});
        }

        String loggingPath = ResourcesPlugin.getWorkspace().getRoot()
                .getLocation().toString() + "/.metadata/.plugins/"
                + org.epsg.openconfigurator.Activator.PLUGIN_ID + "/";
        Result libApiRes = core.InitEclipseLoggingPath(loggingPath);

        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        // Add future initializations.

        return libApiRes;
    }

    /**
     * Loads openCONFIGURATOR and its dependent libraries.
     *
     * @throws UnsatisfiedLinkError If the library is not found in the class
     *             path.
     * @throws SecurityException if a security manager exists and its checkLink
     *             method doesn't allow loading of the specified dynamic library
     */
    public static void loadOpenConfiguratorLibrary()
            throws UnsatisfiedLinkError, SecurityException {
        if (SystemUtils.IS_OS_LINUX) {
            System.loadLibrary("boost_date_time"); //$NON-NLS-1$
            System.loadLibrary("boost_system"); //$NON-NLS-1$
            System.loadLibrary("boost_chrono"); //$NON-NLS-1$
            System.loadLibrary("boost_filesystem"); //$NON-NLS-1$
            System.loadLibrary("boost_thread"); //$NON-NLS-1$
            System.loadLibrary("boost_log"); //$NON-NLS-1$
            System.loadLibrary("boost_log_setup"); //$NON-NLS-1$
        } else if (SystemUtils.IS_OS_WINDOWS) {

            // System.loadLibrary("boost_regex-vc110-mt-1_58");
            // System.loadLibrary("boost_date_time-vc110-mt-1_58");
            // // $NON-NLS-1$
            // System.loadLibrary("boost_system-vc110-mt-1_58"); //$NON-NLS-1$
            // System.loadLibrary("boost_chrono-vc110-mt-1_58"); //$NON-NLS-1$
            // System.loadLibrary("boost_filesystem-vc110-mt-1_58");
            // // $NON-NLS-1$
            // System.loadLibrary("boost_thread-vc110-mt-1_58"); //$NON-NLS-1$
            // System.loadLibrary("boost_log-vc110-mt-1_58"); //$NON-NLS-1$
            // System.loadLibrary("boost_log_setup-vc110-mt-1_58");
            // $NON-NLS-1$
            // Temporarily debug versions.

            System.loadLibrary("boost_regex-vc110-mt-gd-1_58");
            System.loadLibrary("boost_date_time-vc110-mt-gd-1_58");
            // $NON-NLS-1$
            System.loadLibrary("boost_system-vc110-mt-gd-1_58");
            // $NON-NLS-1$
            System.loadLibrary("boost_chrono-vc110-mt-gd-1_58");
            // $NON-NLS-1$
            System.loadLibrary("boost_filesystem-vc110-mt-gd-1_58");
            // $NON-NLS-1$
            System.loadLibrary("boost_thread-vc110-mt-gd-1_58");
            // $NON-NLS-1$
            System.loadLibrary("boost_log-vc110-mt-gd-1_58"); //$NON-NLS-1$
            System.loadLibrary("boost_log_setup-vc110-mt-gd-1_58");
            // // // $NON-NLS-1$
        } else {
            System.err.println("Unsupported system");
        }
        System.loadLibrary("openconfigurator_core_lib"); //$NON-NLS-1$
        System.loadLibrary("openconfigurator_core_wrapper_java"); //$NON-NLS-1$
    }

    /**
     * Map all available objects for the given channel.
     *
     * @param channel Mapping channel.
     * @return Result from the library.
     */
    public static Result mappAvailableObjects(PdoChannel channel) {
        return OpenConfiguratorCore.GetInstance().MapAllObjectsToChannel(
                channel.getNode().getNetworkId(), channel.getNode().getNodeId(),
                getDirection(channel.getPdoType()), channel.getChannelNumber(),
                true);
    }

    /**
     * Map all available objects for the given channel.
     *
     * @param pdoChannel Mapping channel.
     * @param mappingSubObject Mapping sub-object of channel.
     * @param objectTobeMapped Mapping object of channel.
     * @param moduleObjectIndex Mapping object index of channel.
     * @return Result from the library.
     */
    public static Result mappModuleObjectToChannel(PdoChannel pdoChannel,
            PowerlinkSubobject mappingSubObject,
            PowerlinkObject objectTobeMapped, long moduleObjectIndex) {
        return OpenConfiguratorCore.GetInstance().MapObjectToChannel(
                pdoChannel.getNode().getNetworkId(),
                pdoChannel.getNode().getNodeId(),
                getDirection(pdoChannel.getPdoType()),
                pdoChannel.getChannelNumber(), mappingSubObject.getId(),
                moduleObjectIndex, pdoChannel.getTargetNodeId(), true);
    }

    /**
     * Map all available sub-objects for the given channel.
     *
     * @param pdoChannel Mapping channel.
     * @param mappingSubObject Mapping sub-object of channel.
     * @param objectTobeMapped Mapping object of channel.
     * @param moduleObjectIndex Mapping object index of channel.
     * @param moduleSubObjectIndex Mapping sub object index of channel
     * @return Result from the library.
     */
    public static Result mappModuleSubObjectToChannel(PdoChannel pdoChannel,
            PowerlinkSubobject mappingSubObject,
            PowerlinkSubobject subObjectTobeMapped, long moduleObjectIndex,
            int moduleSubObjectIndex) {

        System.err.println("mappModuleSubObjectToChannel ->Network ID:"
                + pdoChannel.getNode().getNetworkId() + "NodeId"
                + pdoChannel.getNode().getNodeId() + " "
                + mappingSubObject.getObject().getIdHex() + "/"
                + moduleObjectIndex + " --- " + subObjectTobeMapped.getId());

        return OpenConfiguratorCore.GetInstance().MapSubObjectToChannel(
                pdoChannel.getNode().getNetworkId(),
                pdoChannel.getNode().getNodeId(),
                getDirection(pdoChannel.getPdoType()),
                pdoChannel.getChannelNumber(), mappingSubObject.getId(),
                moduleObjectIndex, moduleSubObjectIndex,
                pdoChannel.getTargetNodeId(), true);
    }

    /**
     * Map the given object to the given channel.
     *
     * @param channel The channel in which the object to be mapped.
     * @param mappingSubObject The mapping sub-object in which the mapping value
     *            has to be persisted.
     * @param objectToBeMapped The object to be mapped.
     * @return Result from the library.
     */
    public static Result mappObjectToChannel(PdoChannel channel,
            PowerlinkSubobject mappingSubObject,
            PowerlinkObject objectToBeMapped) {
        System.out.println("mappObjectToChannel ->ChannelNumber:"
                + channel.getChannelNumber() + channel.getPdoType() + " "
                + mappingSubObject.getObject().getIdHex() + "/"
                + mappingSubObject.getIdHex() + " --- "
                + objectToBeMapped.getIdHex());

        return OpenConfiguratorCore.GetInstance().MapObjectToChannel(
                channel.getNode().getNetworkId(), channel.getNode().getNodeId(),
                getDirection(channel.getPdoType()), channel.getChannelNumber(),
                mappingSubObject.getId(), objectToBeMapped.getId(),
                channel.getTargetNodeId(), true);
    }

    /**
     * Map the given sub-object to the given channel.
     *
     * @param channel The channel in which the object to be mapped.
     * @param mappingSubObject The mapping sub-object in which the mapping value
     *            has to be persisted.
     * @param subObjectTobeMapped The sub-object to be mapped.
     * @return Result from the library.
     */
    public static Result mappSubObjectToChannel(PdoChannel channel,
            PowerlinkSubobject mappingSubObject,
            PowerlinkSubobject subObjectTobeMapped) {

        System.out.println("MappSubObjectToChannel ->ChannelNumber:"
                + channel.getChannelNumber() + channel.getPdoType() + " "
                + mappingSubObject.getObject().getIdHex() + "/"
                + mappingSubObject.getIdHex() + " --- "
                + subObjectTobeMapped.getObject().getIdHex() + "/"
                + subObjectTobeMapped.getIdHex());

        return OpenConfiguratorCore.GetInstance().MapSubObjectToChannel(
                channel.getNode().getNetworkId(), channel.getNode().getNodeId(),
                getDirection(channel.getPdoType()), channel.getChannelNumber(),
                mappingSubObject.getId(),
                subObjectTobeMapped.getObject().getId(),
                subObjectTobeMapped.getId(), channel.getTargetNodeId(), true);
    }

    /**
     * Move the mapping from the current position to the new position.
     *
     * @param channel The channel in which the object to be mapped.
     * @param currentPosition The current position of the value to be moved.
     * @param newPosition The new position of the value.
     * @return Result from the library.
     */
    public static Result moveMappingObject(PdoChannel channel,
            int currentPosition, int newPosition) {
        System.out.println("MoveMappingObject -> Node:"
                + channel.getNode().getNodeId() + " Direction:"
                + getDirection(channel.getPdoType()) + " ChannelNumber:"
                + channel.getChannelNumber() + " CurrentPosition:"
                + currentPosition + " NewPosition:" + newPosition);
        return OpenConfiguratorCore.GetInstance().MoveMappingObject(
                channel.getNode().getNetworkId(), channel.getNode().getNodeId(),
                getDirection(channel.getPdoType()), channel.getChannelNumber(),
                currentPosition, newPosition);
    }

    /**
     * Moves the module from one to another position.
     *
     * @param module Instance of Module.
     * @param oldPosition Old position of module.
     * @param position New position of Module.
     * @return Result from the library.
     */
    public static Result moveModule(Module module, int oldPosition,
            int position) {
        Node node = module.getNode();
        Result res = OpenConfiguratorCore.GetInstance().MoveModule(
                node.getNetworkId(), node.getNodeId(),
                module.getInterfaceOfModule().getInterfaceUId(),
                module.getChildID(), oldPosition, position);
        return res;
    }

    /**
     * Removes the module from library
     *
     * @param module Instance of Module.
     * @return Result from the library.
     */
    public static Result removeModule(Module module) {
        return OpenConfiguratorCore.GetInstance().RemoveModule(
                module.getNode().getNetworkId(), module.getNode().getNodeId(),
                module.getInterfaceOfModule().getInterfaceUId(),
                module.getChildID(), module.getPosition());
    }

    /**
     * Remove the node from the library.
     *
     * @param node The node instance.
     * @return Result from the openCONFIGURATOR library.
     */
    public static Result removeNode(Node node) {
        return OpenConfiguratorCore.GetInstance()
                .RemoveNode(node.getNetworkId(), node.getNodeId());
    }

    /**
     * Sets address value to Module
     *
     * @param module Instance of module
     * @return Result from the library.
     */
    public static Result setModuleAddress(Module module) {
        Node node = module.getNode();
        Result res = OpenConfiguratorCore.GetInstance().SetModuleAddress(
                node.getNetworkId(), node.getNodeId(),
                module.getInterfaceOfModule().getInterfaceUId(),
                module.getChildID(), module.getPosition(), module.getAddress());
        return res;
    }

    /**
     * Set actual value to the module object.
     *
     * @param plkObject Instance of POwerlink object
     * @param value value to be updated
     * @param newObjectIndex Index of object
     * @return Result from the library.
     */
    public static Result setModuleObjectActualValue(PowerlinkObject plkObject,
            String value, long newObjectIndex) {
        Result res = OpenConfiguratorCore.GetInstance().SetObjectActualValue(
                plkObject.getNetworkId(), plkObject.getNodeId(), newObjectIndex,
                value, plkObject.isObjectForced(), false);
        return res;
    }

    /**
     * Set actual value to the module sub-object.
     *
     * @param plkObject Instance of POwerlink sub-object
     * @param value value to be updated
     * @param newObjectIndex Index of object
     * @return Result from the library.
     */
    public static Result setModuleSubObjectActualValue(
            PowerlinkSubobject plkSubObject, String actualValue,
            long newObjectIndex) {
        Result res = OpenConfiguratorCore.GetInstance().SetSubObjectActualValue(
                plkSubObject.getNetworkId(), plkSubObject.getNodeId(),
                newObjectIndex, plkSubObject.getId(), actualValue,
                plkSubObject.isObjectForced(), false);

        return res;
    }

    /**
     * Add or remove the node assignment value.
     *
     * @param nodeAssign The node assignment value to be added or removed.
     * @param node Node to which the value to be applied.
     * @param value <code> true</code> to add the node assignment value,
     *            <code>False</code> to remove the node assignment value.
     * @return Result from the openCONFIGURATOR library.
     */
    public static Result setNodeAssignment(NodeAssignment nodeAssign, Node node,
            boolean value) {
        Result libApiRes = new Result();
        final String networkId = node.getNetworkId();
        final short nodeId = node.getNodeId();
        if (value) {
            libApiRes = OpenConfiguratorCore.GetInstance()
                    .AddNodeAssignment(networkId, nodeId, nodeAssign);
        } else {
            libApiRes = OpenConfiguratorCore.GetInstance()
                    .RemoveNodeAssignment(networkId, nodeId, nodeAssign);
        }
        return libApiRes;
    }

    /**
     * Set the actual value of the object into the library.
     *
     * @param plkObject The object.
     * @param actualValue The value to be updated in the library.
     * @return Result from the library.
     */
    public static Result setObjectActualValue(PowerlinkObject plkObject,
            String actualValue) {
        Result res = OpenConfiguratorCore.GetInstance().SetObjectActualValue(
                plkObject.getNetworkId(), plkObject.getNodeId(),
                plkObject.getId(), actualValue, plkObject.isObjectForced(),
                false);
        return res;
    }

    /**
     * Set the actual value of the sub-object into the library.
     *
     * @param plkSubObject The sub-object.
     * @param actualValue The value to be updated in the library.
     * @return Result from the library.
     */
    public static Result setSubObjectActualValue(
            PowerlinkSubobject plkSubObject, String actualValue) {
        System.out.println("Set SubObject ActualValue--- "
                + plkSubObject.getObject().getIdHex() + "/"
                + plkSubObject.getIdHex() + " Value:" + actualValue);
        Result res = OpenConfiguratorCore.GetInstance().SetSubObjectActualValue(
                plkSubObject.getNetworkId(), plkSubObject.getNodeId(),
                plkSubObject.getObject().getId(), plkSubObject.getId(),
                actualValue, plkSubObject.isObjectForced(), false);

        return res;
    }

    public static Result toggleEnableDisable(Module module) {
        return OpenConfiguratorCore.GetInstance().EnableModule(
                module.getNode().getNetworkId(), module.getNode().getNodeId(),
                module.getInterfaceOfModule().getInterfaceUId(),
                module.getChildID(), module.getPosition(), !module.isEnabled());
    }

    /**
     * Toggles the enable disable flag in the openCONFIGURATOR library.
     *
     * @param node The node instance.
     * @return Result from the openCONFIGURATOR library.
     */
    public static Result toggleEnableDisable(Node node) {
        return OpenConfiguratorCore.GetInstance().EnableNode(
                node.getNetworkId(), node.getNodeId(), !node.isEnabled());
    }

    /**
     * Update force configurations into the library.
     *
     * @param node Node instance.
     *
     * @return Result from the library.
     */
    private static Result updateForcedObjectsIntoLibary(Node node) {
        Result libApiRes = new Result();
        TAbstractNode abstractNode = null;
        if (node.getNodeModel() instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) node
                    .getNodeModel();
            abstractNode = net.getNodeCollection().getMN();
        } else if (node.getNodeModel() instanceof TCN) {
            TCN cnModel = (TCN) node.getNodeModel();
            abstractNode = cnModel;
        } else if (node.getNodeModel() instanceof TRMN) {
            abstractNode = (TRMN) node.getNodeModel();
        } else {
            System.err.println("Un-Supported node Type");
            return new Result(ErrorCode.UNHANDLED_EXCEPTION,
                    "Un-Supported node Type");
        }

        ForcedObjects forcedObjects = abstractNode.getForcedObjects();

        if (forcedObjects == null) {
            // Ignore if no objects are forced and return success.
            return libApiRes;
        }

        for (org.epsg.openconfigurator.xmlbinding.projectfile.Object forcedObj : forcedObjects
                .getObject()) {
            byte[] forcedObjectId = forcedObj.getIndex();
            byte[] forcedSubObjectId = forcedObj.getSubindex();

            if (forcedSubObjectId == null) {
                PowerlinkObject plkObj = node.getObjectDictionary()
                        .getObject(forcedObjectId);
                if (plkObj == null) {
                    OpenConfiguratorMessageConsole.getInstance()
                            .printErrorMessage(
                                    "Object ID 0x"
                                            + DatatypeConverter.printHexBinary(
                                                    forcedObjectId)
                                    + " is forced and is not available in the XDD/XDC file.",
                                    node.getProject().getName());
                    continue;
                }

                libApiRes = OpenConfiguratorLibraryUtils.forceObject(plkObj,
                        true);
            } else {
                PowerlinkSubobject plkSubObj = node.getObjectDictionary()
                        .getSubObject(forcedObjectId, forcedSubObjectId);

                if (plkSubObj == null) {
                    System.err.println(
                            "Object is forced which is not available in the XDD/XDC file");

                    OpenConfiguratorMessageConsole.getInstance()
                            .printErrorMessage(
                                    "Object ID 0x"
                                            + DatatypeConverter.printHexBinary(
                                                    forcedObjectId)
                                    + "/0x"
                                    + DatatypeConverter
                                            .printHexBinary(forcedSubObjectId)
                                    + " is forced and is not available in the XDD/XDC file.",
                                    node.getNetworkId());
                    continue;
                }

                libApiRes = OpenConfiguratorLibraryUtils
                        .forceSubObject(plkSubObj, true);
            }

            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }
        }

        return libApiRes;
    }

    /**
     * Validate the value of forced object.
     *
     * @param plkObject The instance of POWERLINK object.
     * @return The result from openCONFIGURATOR library
     */
    public static Result validateForceObjectActualValue(
            PowerlinkObject plkObject) {
        return validateObjectActualValue(plkObject, plkObject.getActualValue());
    }

    /**
     * Validate the value of forced sub-object.
     *
     * @param plkSubObject The instance of POWERLINK sub-object.
     * @return The result from openCONFIGURATOR library.
     */
    public static Result validateForceSubObjectActualValue(
            PowerlinkSubobject plkSubObject) {
        return validateSubobjectActualValue(plkSubObject,
                plkSubObject.getActualValue());
    }

    /**
     * Validate value of module Object
     *
     * @param plkObject Instance of POWERLINK object
     * @param value The actual value to be validated
     * @param newObjectIndex The index of object from the library
     * @return The result from openCONFIGURATOR library.
     */
    public static Result validateModuleObjectActualValue(
            PowerlinkObject plkObject, String value, long newObjectIndex) {
        Result res = OpenConfiguratorCore.GetInstance().SetObjectActualValue(
                plkObject.getNetworkId(), plkObject.getNodeId(), newObjectIndex,
                value, plkObject.isObjectForced(), true);
        return res;
    }

    /**
     * Validate value of module sub-Object
     * 
     * @param plkSubObject Instance of POWERLINK object
     * @param value The actual value to be validated
     * @param newObjectIndex The index of sub-object from the library
     * @return The result from openCONFIGURATOR library.
     */
    public static Result validateModuleSubobjectActualValue(
            PowerlinkSubobject plkSubObject, String value,
            long newObjectIndex) {
        Result res = OpenConfiguratorCore.GetInstance().SetSubObjectActualValue(
                plkSubObject.getNetworkId(), plkSubObject.getNodeId(),
                newObjectIndex, plkSubObject.getId(), value,
                plkSubObject.isObjectForced(), true);
        return res;
    }

    /**
     * Validate the actual value of POWERLINK object.
     *
     * @param plkObject The instance of POWERLINK object.
     * @param actualValue The value given to the object.
     * @return The result from openCONFIGURATOR library.
     */
    public static Result validateObjectActualValue(PowerlinkObject plkObject,
            String actualValue) {
        Result res = OpenConfiguratorCore.GetInstance().SetObjectActualValue(
                plkObject.getNetworkId(), plkObject.getNodeId(),
                plkObject.getId(), actualValue, plkObject.isObjectForced(),
                true);
        return res;
    }

    /**
     * validate the actual value of POWERLINK object related with node
     * properties.
     *
     * @param networkId The ID of the project
     * @param nodeId The instance of node to which the value should be
     *            validated.
     * @param objectId The complete ID of object.
     * @param actualValue Value given for validation.
     * @param isForced <code>True</code> If the value of object is forced.
     *            <code>False</code> If the value of object is not forced
     * @return The result from openCONFIGURATOR library.
     */
    public static Result validateObjectActualValue(String networkId,
            short nodeId, long objectId, String actualValue, boolean isForced) {
        Result res = OpenConfiguratorCore.GetInstance().SetObjectActualValue(
                networkId, nodeId, objectId, actualValue, isForced, true);
        return res;
    }

    /**
     * Validate the actual value of POWERLINK sub-object.
     *
     * @param plkSubObject The instance of POWERLINK sub-object.
     * @param actualValue The value given to the sub-object.
     * @return The result from openCONFIGURATOR library.
     */
    public static Result validateSubobjectActualValue(
            PowerlinkSubobject plkSubObject, String actualValue) {
        Result res = OpenConfiguratorCore.GetInstance().SetSubObjectActualValue(
                plkSubObject.getNetworkId(), plkSubObject.getNodeId(),
                plkSubObject.getObject().getId(), plkSubObject.getId(),
                actualValue, plkSubObject.isObjectForced(), true);
        return res;
    }

    /**
     * validate the actual value of POWERLINK sub object related with node
     * properties.
     *
     * @param networkId The ID of the project
     * @param nodeId The instance of node to which the value should be
     *            validated.
     * @param objectId The complete ID of object.
     * @param subObjectId The complete ID of sub object.
     * @param actualValue Value given for validation.
     * @param isForced <code>True</code> If the value of object is forced.
     *            <code>False</code> If the value of object is not forced
     * @return The result from openCONFIGURATOR library.
     */
    public static Result validateSubobjectActualValue(String networkId,
            short nodeId, long objectId, short subObjectId, String actualValue,
            boolean isForced) {
        Result res = OpenConfiguratorCore.GetInstance().SetSubObjectActualValue(
                networkId, nodeId, objectId, subObjectId, actualValue, isForced,
                true);
        return res;
    }
}
