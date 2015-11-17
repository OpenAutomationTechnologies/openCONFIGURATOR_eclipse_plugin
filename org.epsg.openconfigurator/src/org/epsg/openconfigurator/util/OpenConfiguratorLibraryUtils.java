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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.epsg.openconfigurator.lib.wrapper.AccessType;
import org.epsg.openconfigurator.lib.wrapper.CNFeatureEnum;
import org.epsg.openconfigurator.lib.wrapper.DynamicChannelAccessType;
import org.epsg.openconfigurator.lib.wrapper.GeneralFeatureEnum;
import org.epsg.openconfigurator.lib.wrapper.IEC_Datatype;
import org.epsg.openconfigurator.lib.wrapper.MNFeatureEnum;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.ObjectType;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.PDOMapping;
import org.epsg.openconfigurator.lib.wrapper.ParameterAccess;
import org.epsg.openconfigurator.lib.wrapper.PlkDataType;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.resources.IOpenConfiguratorResource;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAbstractNode;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAutoGenerationSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TKeyValuePair;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.TApplicationLayers;
import org.epsg.openconfigurator.xmlbinding.xdd.TApplicationLayers.DynamicChannels;
import org.epsg.openconfigurator.xmlbinding.xdd.TApplicationProcess;
import org.epsg.openconfigurator.xmlbinding.xdd.TCNFeatures;
import org.epsg.openconfigurator.xmlbinding.xdd.TDataTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.TDynamicChannel;
import org.epsg.openconfigurator.xmlbinding.xdd.TGeneralFeatures;
import org.epsg.openconfigurator.xmlbinding.xdd.TMNFeatures;
import org.epsg.openconfigurator.xmlbinding.xdd.TNetworkManagement;
import org.epsg.openconfigurator.xmlbinding.xdd.TObject;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectAccessType;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectPDOMapping;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList.Parameter;
import org.epsg.openconfigurator.xmlbinding.xdd.TSubrange;
import org.epsg.openconfigurator.xmlbinding.xdd.TVarDeclaration;

/**
 * An utility class to group all the methods that are used to communicate with
 * the library.
 *
 * @author Ramakrishnan P
 *
 */
public class OpenConfiguratorLibraryUtils {

    /**
     * Adds a Controlled node in the project file into the library.
     *
     * @param networkId Network ID.
     * @param cn The controlled node instance.
     *
     * @return Result instance from the library.
     */
    private static Result addControlledNode(final String networkId,
            final TCN cn) {
        Result libApiRes = OpenConfiguratorCore.GetInstance()
                .CreateNode(networkId, new Short(cn.getNodeID()), cn.getName());
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
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
                String structUniqueId = StringUtils.EMPTY;
                if (structDt.getUniqueID() != null) {
                    structUniqueId = structDt.getUniqueID();
                }

                String structName = StringUtils.EMPTY;
                if (structDt.getName() != null) {
                    structName = structDt.getName();
                }

                libApiRes = core.CreateStructDatatype(networkId, nodeId,
                        structUniqueId, structName);

                if (libApiRes.IsSuccessful()) {

                    List<TVarDeclaration> varDeclList = structDt
                            .getVarDeclaration();
                    for (TVarDeclaration varDecl : varDeclList) {

                        long varDeclSize = 0;
                        if (varDecl.getSize() != null) {
                            varDeclSize = Long.parseLong(varDecl.getSize());
                        }

                        String varDeclName = StringUtils.EMPTY;
                        if (varDecl.getName() != null) {
                            varDeclName = varDecl.getName();
                        }

                        String initialValue = StringUtils.EMPTY;
                        if (varDecl.getInitialValue() != null) {
                            initialValue = varDecl.getInitialValue();
                        }

                        String varDeclUniqueId = StringUtils.EMPTY;
                        if (varDecl.getUniqueID() != null) {
                            varDeclUniqueId = varDecl.getUniqueID();
                        }

                        IEC_Datatype iecDataType = getIEC_DataType(varDecl);
                        libApiRes = core.CreateVarDeclaration(networkId, nodeId,
                                structUniqueId, varDeclUniqueId, varDeclName,
                                iecDataType, varDeclSize, initialValue);
                        if (!libApiRes.IsSuccessful()) {
                            System.err.println("CreateVarDeclaration WARN: "
                                    + getErrorMessage(libApiRes));
                        }
                    }
                } else {
                    System.err.println("CreateStructDatatype WARN: "
                            + getErrorMessage(libApiRes));
                }
            } else if (dtObject instanceof TDataTypeList.Array) {

                TDataTypeList.Array arrayDt = (TDataTypeList.Array) dtObject;
                List<TSubrange> subRangeList = arrayDt.getSubrange();
                TSubrange subRange = subRangeList.get(0);

                // arrayDt.getDataTypeIDRef().getUniqueIDRef().toString();
                IEC_Datatype iecDataType = getIEC_DataType(arrayDt);

                libApiRes = core.CreateArrayDatatype(networkId, nodeId,
                        arrayDt.getUniqueID(), arrayDt.getName(),
                        subRange.getLowerLimit(), subRange.getUpperLimit(),
                        iecDataType);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("CreateArrayDatatype WARN: "
                            + getErrorMessage(libApiRes));
                }
            } else if (dtObject instanceof TDataTypeList.Enum) {

            } else if (dtObject instanceof TDataTypeList.Derived) {

            }
        }

        return libApiRes;
    }

    /**
     * Add dynamic channels into the library.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param dynamicChannels
     * @return Result instance from the library.
     */
    private static Result addDynamicChannels(String networkId, short nodeId,
            DynamicChannels dynamicChannels) {
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
     * Add managing node into the library.
     *
     * @param networkId Network ID.
     * @param networkConfiguration The network configuration available in the
     *            project file.
     * @return Result instance from the library.
     */
    private static Result addManagingNode(final String networkId,
            final TNetworkConfiguration networkConfiguration) {

        Result libApiRes = new Result();

        TNodeCollection nodeCollection = networkConfiguration
                .getNodeCollection();
        if (nodeCollection != null) {
            TMN mn = nodeCollection.getMN();
            if (mn != null) {
                libApiRes = OpenConfiguratorCore.GetInstance()
                        .CreateNode(networkId, mn.getNodeID(), mn.getName());
                if (!libApiRes.IsSuccessful()) {
                    return libApiRes;
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
     * @param networkManagement The network management instance from the project
     *            XML.
     * @return Result instance from the library.
     */
    private static Result addNetworkManagement(String networkId, short nodeId,
            TNetworkManagement networkManagement) {
        Result libApiRes = new Result();
        if (networkManagement == null) {
            return libApiRes;
        }
        libApiRes = addNetworkManagementGeneralFeatures(networkId, nodeId,
                networkManagement.getGeneralFeatures());
        libApiRes = addNetworkManagementMnFeatures(networkId, nodeId,
                networkManagement.getMNFeatures());
        libApiRes = addNetworkManagementCnFeatures(networkId, nodeId,
                networkManagement.getCNFeatures());
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
                case NMTCNSoC2PReq:
                    value = String.valueOf(cnFeatures.getNMTCNSoC2PReq());
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

                case NMTFlushArpEntry:
                    value = String
                            .valueOf(generalFeatures.isNMTFlushArpEntry());
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
                case NMTSimpleBoot:
                    value = String.valueOf(mnFeatures.isNMTSimpleBoot());
                    break;
                case PDOTPDOChannels:
                    value = String.valueOf(mnFeatures.getPDOTPDOChannels());
                    break;
                case NMTMNRedundancy:
                    value = String.valueOf(mnFeatures.isNMTMNRedundancy());
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
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param node Node instance.
     * @return Result instance from the library.
     */
    public static Result addNode(final String networkId, final short nodeId,
            Node node) {
        Result libApiRes = new Result();
        TAbstractNode abstractNode = null;
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        if (node.getNodeModel() instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) node
                    .getNodeModel();
            abstractNode = net.getNodeCollection().getMN();

            libApiRes = addManagingNode(node.getNetworkId(), net);
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }
            libApiRes = importXddModel(node.getNetworkId(), node.getNodeId(),
                    node.getISO15745ProfileContainer());

            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

            TMN mnInst = net.getNodeCollection().getMN();
            libApiRes = setNodeAssignment(NodeAssignment.NMT_NODEASSIGN_MN_PRES,
                    node, mnInst.isTransmitsPRes());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

        } else if (node.getNodeModel() instanceof TCN) {
            TCN cnModel = (TCN) node.getNodeModel();
            abstractNode = cnModel;

            libApiRes = addControlledNode(node.getNetworkId(), cnModel);
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }
            libApiRes = importXddModel(node.getNetworkId(), node.getNodeId(),
                    node.getISO15745ProfileContainer());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

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

            libApiRes = addRmnIntoLibrary(node.getNetworkId(),
                    (TRMN) node.getNodeModel());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }
            libApiRes = importXddModel(node.getNetworkId(), node.getNodeId(),
                    node.getISO15745ProfileContainer());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

            libApiRes = setNodeAssignment(
                    NodeAssignment.NMT_NODEASSIGN_NODE_IS_CN, node, true);
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

        } else {
            System.err.println("Un-Supported node Type");
            return libApiRes;
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

    /**
     * Add the list of Objects into the library.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param objectList Object list instance from the XDC.
     * @return Result instance from the library.
     */
    public static Result addObjectList(final String networkId,
            final short nodeId, TApplicationLayers.ObjectList objectList) {
        Result libApiRes = null;
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();

        List<TObject> objects = objectList.getObject();
        for (TObject object : objects) {
            String objectID = DatatypeConverter
                    .printHexBinary(object.getIndex());
            long objectIdL = Long.parseLong(objectID, 16);

            ObjectType objectType = getObjectType(object.getObjectType());

            String objectName = StringUtils.EMPTY;
            if (object.getName() != null) {
                objectName = object.getName();
            }

            PDOMapping mapping = getPdoMapping(object.getPDOmapping());

            if (object.getUniqueIDRef() == null) {
                // Non-Domain objects

                PlkDataType dataType = getObjectDatatype(object.getDataType());
                AccessType accessType = getAccessType(object.getAccessType());

                String defaultValue = StringUtils.EMPTY;
                if (object.getDefaultValue() != null) {
                    defaultValue = object.getDefaultValue();
                }

                String actualValue = StringUtils.EMPTY;
                if (object.getActualValue() != null) {
                    actualValue = object.getActualValue();
                }

                libApiRes = core.CreateObject(networkId, nodeId, objectIdL,
                        objectType, objectName, dataType, accessType, mapping,
                        defaultValue, actualValue);
                if (libApiRes.IsSuccessful()) {
                    String highLimit = object.getHighLimit();
                    if (highLimit == null) {
                        highLimit = "";
                    }

                    String lowLimit = object.getLowLimit();
                    if (lowLimit == null) {
                        lowLimit = "";
                    }

                    libApiRes = core.SetObjectLimits(networkId, nodeId,
                            objectIdL, lowLimit, highLimit);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("SetObjectLimits WARN: "
                                + getErrorMessage(libApiRes));
                    }
                } else {
                    System.err.println("WARN: " + getErrorMessage(libApiRes));
                }
            } else {
                // Domain objects.

                if (object
                        .getUniqueIDRef() instanceof TParameterList.Parameter) {
                    Parameter parameter = (Parameter) object.getUniqueIDRef();

                    libApiRes = core.CreateDomainObject(networkId, nodeId,
                            objectIdL, objectType, object.getName(), mapping,
                            parameter.getUniqueID());
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("CreateDomainObject WARN: "
                                + getErrorMessage(libApiRes));
                    }
                } else {
                    System.err.println("ERROR: Invalid object.getUniqueIDRef():"
                            + object.getUniqueIDRef());
                }
            }

            libApiRes = addSubObjectList(networkId, nodeId, objectIdL,
                    object.getSubObject());
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

    /**
     * Add the list of Parameter instances from the XDC into the library.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param parameterListElement The paramerter list instance.
     * @return Result instance from the library.
     */
    private static Result addParameterList(final String networkId,
            final short nodeId, TParameterList parameterListElement) {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        Result libApiRes = new Result();

        if (parameterListElement == null) {
            return libApiRes;
        }

        List<Parameter> parameterList = parameterListElement.getParameter();
        for (Parameter parameter : parameterList) {
            ParameterAccess access = getParameterAccess(parameter.getAccess());

            if (parameter.getDataTypeIDRef() == null) {

                IEC_Datatype iecDataType = getIEC_DataType(parameter);
                libApiRes = core.CreateParameter(networkId, nodeId,
                        parameter.getUniqueID(), access, iecDataType);
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("CreateParameter WARN: "
                            + getErrorMessage(libApiRes));
                }
            } else {
                Object dataTypeObj = parameter.getDataTypeIDRef()
                        .getUniqueIDRef();
                if (dataTypeObj instanceof TDataTypeList.Struct) {
                    TDataTypeList.Struct structDt = (TDataTypeList.Struct) dataTypeObj;
                    String uniqueIdRef = structDt.getUniqueID();
                    libApiRes = core.CreateParameter(networkId, nodeId,
                            parameter.getUniqueID(), uniqueIdRef, access);
                    if (!libApiRes.IsSuccessful()) {
                        System.err.println("CreateParameter UID WARN: "
                                + getErrorMessage(libApiRes));
                    }
                } else {
                    System.err.println("Unhandled datatypeObj: " + dataTypeObj);
                }
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

    /**
     * Add the redundant MN into the library.
     *
     * @param networkId Network ID.
     * @param rmn Redundant MN instance.
     * @return Result instance from the library.
     */
    private static Result addRmnIntoLibrary(final String networkId,
            final TRMN rmn) {
        Result libApiRes;
        libApiRes = OpenConfiguratorCore.GetInstance().CreateNode(networkId,
                rmn.getNodeID(), rmn.getName(), true);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }
        return libApiRes;
    }

    /**
     * Add the list of sub-objects into the library.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param objectId Object ID.
     * @param subObjectList The list of subObjects to be added.
     * @return Result instance from the library.
     */
    public static Result addSubObjectList(final String networkId,
            final short nodeId, long objectId,
            List<TObject.SubObject> subObjectList) {
        Result libApiRes = new Result();
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        for (TObject.SubObject subObject : subObjectList) {

            String subObjectID = DatatypeConverter
                    .printHexBinary(subObject.getSubIndex());
            short subObjectIDL = Short.parseShort(subObjectID, 16);

            ObjectType subObjectType = getObjectType(subObject.getObjectType());

            String subObjectName = StringUtils.EMPTY;
            if (subObject.getName() != null) {
                subObjectName = subObject.getName();
            }

            PDOMapping pdoMapping = getPdoMapping(subObject.getPDOmapping());
            if (subObject.getUniqueIDRef() == null) {

                PlkDataType dataType = getObjectDatatype(
                        subObject.getDataType());

                AccessType accessType = getAccessType(
                        subObject.getAccessType());

                String defaultValue = StringUtils.EMPTY;
                if (subObject.getDefaultValue() != null) {
                    defaultValue = subObject.getDefaultValue();
                }

                String actualValue = StringUtils.EMPTY;
                if (subObject.getActualValue() != null) {
                    actualValue = subObject.getActualValue();
                }

                libApiRes = core.CreateSubObject(networkId, nodeId, objectId,
                        subObjectIDL, subObjectType, subObjectName, dataType,
                        accessType, pdoMapping, defaultValue, actualValue);

                if (libApiRes.IsSuccessful()) {
                    String highLimit = StringUtils.EMPTY;
                    if (subObject.getHighLimit() != null) {
                        highLimit = subObject.getHighLimit();
                    }

                    String lowLimit = StringUtils.EMPTY;
                    if (subObject.getLowLimit() != null) {
                        lowLimit = subObject.getLowLimit();
                    }

                    libApiRes = core.SetSubObjectLimits(networkId, nodeId,
                            objectId, subObjectIDL, lowLimit, highLimit);
                    if (!libApiRes.IsSuccessful()) {
                        System.err
                                .println("WARN: " + getErrorMessage(libApiRes));
                    }

                } else {
                    System.err.println("WARN: " + getErrorMessage(libApiRes));
                }
            } else {
                // Domain objects.

                if (subObject
                        .getUniqueIDRef() instanceof TParameterList.Parameter) {
                    Parameter parameter = (Parameter) subObject
                            .getUniqueIDRef();
                    libApiRes = core.CreateDomainSubObject(networkId, nodeId,
                            objectId, subObjectIDL, subObjectType,
                            subObjectName, pdoMapping, parameter.getUniqueID());
                    if (!libApiRes.IsSuccessful()) {
                        System.err
                                .println("WARN: " + getErrorMessage(libApiRes));
                    }
                } else {
                    System.err.println(
                            "ERROR: Invalid subObject.getUniqueIDRef():"
                                    + subObject.getUniqueIDRef());
                }
            }
        }
        return libApiRes;
    }

    /**
     * Get the library compatible accessType for the one in the XDC.
     *
     * @param accessTypeXdc AccessType from the XDC.
     *
     * @return The compatible library accessType.
     */
    private static AccessType getAccessType(TObjectAccessType accessTypeXdc) {
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
        String errorMessage = "Code:" + result.GetErrorType().ordinal() + "\t"
                + result.GetErrorMessage();
        System.out.println(result.GetErrorType().name());
        return errorMessage;
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

    /**
     * Get POWERLINK datatype for the given datatype ID.
     *
     * @param dataTypeRaw Datatype ID available in the XDC.
     * @return The POWERLINK datatype.
     */
    private static PlkDataType getObjectDatatype(byte[] dataTypeRaw) {
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

    /**
     * Import the details available in the XDD into the library.
     *
     * @param networkId Network ID.
     * @param nodeId The node ID.
     * @param xdd The XDC/XDD instance.
     * @return Result instance from the library.
     */
    private static Result importXddModel(final String networkId,
            final short nodeId, ISO15745ProfileContainer xdd) {

        Result libApiRes = new Result();

        List<ISO15745Profile> profiles = xdd.getISO15745Profile();
        for (ISO15745Profile profile : profiles) {
            ProfileBodyDataType profileBodyDatatype = profile.getProfileBody();
            if (profileBodyDatatype instanceof ProfileBodyDevicePowerlink) {
                ProfileBodyDevicePowerlink devProfile = (ProfileBodyDevicePowerlink) profileBodyDatatype;
                List<TApplicationProcess> appProcessList = devProfile
                        .getApplicationProcess();
                for (TApplicationProcess appProcess : appProcessList) {

                    libApiRes = addParameterList(networkId, nodeId,
                            appProcess.getParameterList());
                    if (!libApiRes.IsSuccessful()) {
                        System.err
                                .println("WARN: " + getErrorMessage(libApiRes));
                    }

                    libApiRes = addDataTypeList(networkId, nodeId,
                            appProcess.getDataTypeList());
                    if (!libApiRes.IsSuccessful()) {
                        System.err
                                .println("WARN: " + getErrorMessage(libApiRes));
                    }

                }
            } else if (profile
                    .getProfileBody() instanceof ProfileBodyCommunicationNetworkPowerlink) {
                ProfileBodyCommunicationNetworkPowerlink commProfile = (ProfileBodyCommunicationNetworkPowerlink) profileBodyDatatype;

                libApiRes = addObjectList(networkId, nodeId,
                        commProfile.getApplicationLayers().getObjectList());
                if (!libApiRes.IsSuccessful()) {
                    System.err.println("WARN: " + getErrorMessage(libApiRes));
                }

                if (nodeId > 239) {
                    libApiRes = addDynamicChannels(networkId, nodeId,
                            commProfile.getApplicationLayers()
                                    .getDynamicChannels());
                    if (!libApiRes.IsSuccessful()) {
                        System.err
                                .println("WARN: " + getErrorMessage(libApiRes));
                    }
                }

                libApiRes = addNetworkManagement(networkId, nodeId,
                        commProfile.getNetworkManagement());

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
        String boostLogSettingsFile = new String();

        boostLogSettingsFile = org.epsg.openconfigurator.Activator
                .getAbsolutePath(
                        IOpenConfiguratorResource.BOOST_LOG_CONFIGURATION);

        System.out.println("Path: " + boostLogSettingsFile);
        String fileContents = new String(
                Files.readAllBytes(Paths.get(boostLogSettingsFile)));
        // Init logger class with configuration file path
        Result libApiRes = core.InitLoggingConfiguration(fileContents);
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
            System.loadLibrary("boost_date_time-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_system-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_chrono-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_filesystem-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_thread-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_log-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_log_setup-vc110-mt-1_54"); //$NON-NLS-1$
            // Temporarily debug versions.
            // System.loadLibrary("boost_date_time-vc110-mt-gd-1_54");
            // //$NON-NLS-1$
            // System.loadLibrary("boost_system-vc110-mt-gd-1_54");
            // //$NON-NLS-1$
            // System.loadLibrary("boost_chrono-vc110-mt-gd-1_54");
            // //$NON-NLS-1$
            // System.loadLibrary("boost_filesystem-vc110-mt-gd-1_54");
            // //$NON-NLS-1$
            // System.loadLibrary("boost_thread-vc110-mt-gd-1_54");
            // //$NON-NLS-1$
            // System.loadLibrary("boost_log-vc110-mt-gd-1_54"); //$NON-NLS-1$
            // System.loadLibrary("boost_log_setup-vc110-mt-gd-1_54");
            // //$NON-NLS-1$
        } else {
            System.err.println("Unsupported system");
        }
        System.loadLibrary("openconfigurator_core_lib"); //$NON-NLS-1$
        System.loadLibrary("openconfigurator_core_wrapper_java"); //$NON-NLS-1$
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
     * Toggles the enable disable flag in the openCONFIGURATOR library.
     *
     * @param node The node instance.
     * @return Result from the openCONFIGURATOR library.
     */
    public static Result toggleEnableDisable(Node node) {
        return OpenConfiguratorCore.GetInstance().EnableNode(
                node.getNetworkId(), node.getNodeId(), !node.isEnabled());
    }
}
