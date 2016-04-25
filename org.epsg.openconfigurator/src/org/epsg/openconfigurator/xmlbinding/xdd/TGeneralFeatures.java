
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for t_GeneralFeatures complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="t_GeneralFeatures"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="CFMConfigManager" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="DLLErrBadPhysMode" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="DLLErrMacBuffer" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="DLLFeatureCN" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="DLLFeatureMN" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="NMTBootTimeNotActive" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="NMTCycleTimeGranularity" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="1" /&gt;
 *       &lt;attribute name="NMTCycleTimeMax" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="NMTCycleTimeMin" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="NMTMinRedCycleTime" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="0" /&gt;
 *       &lt;attribute name="NMTEmergencyQueueSize" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="0" /&gt;
 *       &lt;attribute name="NMTErrorEntries" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="NMTExtNmtCmds" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTFlushArpEntry" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTIsochronous" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="NMTNetHostNameSet" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTMaxCNNodeID" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="239" /&gt;
 *       &lt;attribute name="NMTMaxCNNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="239" /&gt;
 *       &lt;attribute name="NMTMaxHeartbeats" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="254" /&gt;
 *       &lt;attribute name="NMTNodeIDByHW" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="NMTNodeIDBySW" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTProductCode" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="0" /&gt;
 *       &lt;attribute name="NMTPublishActiveNodes" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTPublishConfigNodes" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTPublishEmergencyNew" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTPublishNodeState" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTPublishOperational" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTPublishPreOp1" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTPublishPreOp2" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTPublishReadyToOp" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTPublishStopped" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTPublishTime" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTRevisionNo" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="0" /&gt;
 *       &lt;attribute name="NWLForward" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NWLICMPSupport" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NWLIPSupport" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="PDODynamicMapping" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="PDOGranularity" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="8" /&gt;
 *       &lt;attribute name="PDOMaxDescrMem" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="4294967295" /&gt;
 *       &lt;attribute name="PDORPDOChannelObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="254" /&gt;
 *       &lt;attribute name="PDORPDOChannels" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" default="256" /&gt;
 *       &lt;attribute name="PDORPDOCycleDataLim" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="4294967295" /&gt;
 *       &lt;attribute name="PDORPDOOverallObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" default="65535" /&gt;
 *       &lt;attribute name="PDOSelfReceipt" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="PDOTPDOChannelObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="254" /&gt;
 *       &lt;attribute name="PDOTPDOCycleDataLim" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="4294967295" /&gt;
 *       &lt;attribute name="PDOTPDOOverallObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" default="65535" /&gt;
 *       &lt;attribute name="PHYExtEPLPorts" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="2" /&gt;
 *       &lt;attribute name="PHYHubDelay" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="460" /&gt;
 *       &lt;attribute name="PHYHubIntegrated" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="PHYHubJitter" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="70" /&gt;
 *       &lt;attribute name="RT1RT1SecuritySupport" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="RT1RT1Support" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="RT2RT2Support" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOClient" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="SDOCmdFileRead" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOCmdFileWrite" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOCmdLinkName" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOCmdReadAllByIndex" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOCmdReadByName" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOCmdReadMultParam" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOCmdWriteAllByIndex" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOCmdWriteByName" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOCmdWriteMultParam" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOMaxConnections" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="1" /&gt;
 *       &lt;attribute name="SDOMaxParallelConnections" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="1" /&gt;
 *       &lt;attribute name="SDOSeqLayerTxHistorySize" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" default="5" /&gt;
 *       &lt;attribute name="SDOServer" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="SDOSupportASnd" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOSupportPDO" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="SDOSupportUdpIp" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="DLLMultiplePReqPRes" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_GeneralFeatures")
public class TGeneralFeatures {

    @XmlAttribute(name = "CFMConfigManager")
    protected Boolean cfmConfigManager;
    @XmlAttribute(name = "DLLErrBadPhysMode")
    protected Boolean dllErrBadPhysMode;
    @XmlAttribute(name = "DLLErrMacBuffer")
    protected Boolean dllErrMacBuffer;
    @XmlAttribute(name = "DLLFeatureCN")
    protected Boolean dllFeatureCN;
    @XmlAttribute(name = "DLLFeatureMN", required = true)
    protected boolean dllFeatureMN;
    @XmlAttribute(name = "NMTBootTimeNotActive", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtBootTimeNotActive;
    @XmlAttribute(name = "NMTCycleTimeGranularity")
    @XmlSchemaType(name = "unsignedInt")
    protected Long nmtCycleTimeGranularity;
    @XmlAttribute(name = "NMTCycleTimeMax", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtCycleTimeMax;
    @XmlAttribute(name = "NMTCycleTimeMin", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtCycleTimeMin;
    @XmlAttribute(name = "NMTMinRedCycleTime")
    @XmlSchemaType(name = "unsignedInt")
    protected Long nmtMinRedCycleTime;
    @XmlAttribute(name = "NMTEmergencyQueueSize")
    @XmlSchemaType(name = "unsignedInt")
    protected Long nmtEmergencyQueueSize;
    @XmlAttribute(name = "NMTErrorEntries", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtErrorEntries;
    @XmlAttribute(name = "NMTExtNmtCmds")
    protected Boolean nmtExtNmtCmds;
    @XmlAttribute(name = "NMTFlushArpEntry")
    protected Boolean nmtFlushArpEntry;
    @XmlAttribute(name = "NMTIsochronous")
    protected Boolean nmtIsochronous;
    @XmlAttribute(name = "NMTNetHostNameSet")
    protected Boolean nmtNetHostNameSet;
    @XmlAttribute(name = "NMTMaxCNNodeID")
    @XmlSchemaType(name = "unsignedByte")
    protected Short nmtMaxCNNodeID;
    @XmlAttribute(name = "NMTMaxCNNumber")
    @XmlSchemaType(name = "unsignedByte")
    protected Short nmtMaxCNNumber;
    @XmlAttribute(name = "NMTMaxHeartbeats")
    @XmlSchemaType(name = "unsignedByte")
    protected Short nmtMaxHeartbeats;
    @XmlAttribute(name = "NMTNodeIDByHW")
    protected Boolean nmtNodeIDByHW;
    @XmlAttribute(name = "NMTNodeIDBySW")
    protected Boolean nmtNodeIDBySW;
    @XmlAttribute(name = "NMTProductCode")
    @XmlSchemaType(name = "unsignedInt")
    protected Long nmtProductCode;
    @XmlAttribute(name = "NMTPublishActiveNodes")
    protected Boolean nmtPublishActiveNodes;
    @XmlAttribute(name = "NMTPublishConfigNodes")
    protected Boolean nmtPublishConfigNodes;
    @XmlAttribute(name = "NMTPublishEmergencyNew")
    protected Boolean nmtPublishEmergencyNew;
    @XmlAttribute(name = "NMTPublishNodeState")
    protected Boolean nmtPublishNodeState;
    @XmlAttribute(name = "NMTPublishOperational")
    protected Boolean nmtPublishOperational;
    @XmlAttribute(name = "NMTPublishPreOp1")
    protected Boolean nmtPublishPreOp1;
    @XmlAttribute(name = "NMTPublishPreOp2")
    protected Boolean nmtPublishPreOp2;
    @XmlAttribute(name = "NMTPublishReadyToOp")
    protected Boolean nmtPublishReadyToOp;
    @XmlAttribute(name = "NMTPublishStopped")
    protected Boolean nmtPublishStopped;
    @XmlAttribute(name = "NMTPublishTime")
    protected Boolean nmtPublishTime;
    @XmlAttribute(name = "NMTRevisionNo")
    @XmlSchemaType(name = "unsignedInt")
    protected Long nmtRevisionNo;
    @XmlAttribute(name = "NWLForward")
    protected Boolean nwlForward;
    @XmlAttribute(name = "NWLICMPSupport")
    protected Boolean nwlicmpSupport;
    @XmlAttribute(name = "NWLIPSupport")
    protected Boolean nwlipSupport;
    @XmlAttribute(name = "PDODynamicMapping")
    protected Boolean pdoDynamicMapping;
    @XmlAttribute(name = "PDOGranularity")
    @XmlSchemaType(name = "unsignedByte")
    protected Short pdoGranularity;
    @XmlAttribute(name = "PDOMaxDescrMem")
    @XmlSchemaType(name = "unsignedInt")
    protected Long pdoMaxDescrMem;
    @XmlAttribute(name = "PDORPDOChannelObjects")
    @XmlSchemaType(name = "unsignedByte")
    protected Short pdorpdoChannelObjects;
    @XmlAttribute(name = "PDORPDOChannels")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer pdorpdoChannels;
    @XmlAttribute(name = "PDORPDOCycleDataLim")
    @XmlSchemaType(name = "unsignedInt")
    protected Long pdorpdoCycleDataLim;
    @XmlAttribute(name = "PDORPDOOverallObjects")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer pdorpdoOverallObjects;
    @XmlAttribute(name = "PDOSelfReceipt")
    protected Boolean pdoSelfReceipt;
    @XmlAttribute(name = "PDOTPDOChannelObjects")
    @XmlSchemaType(name = "unsignedByte")
    protected Short pdotpdoChannelObjects;
    @XmlAttribute(name = "PDOTPDOCycleDataLim")
    @XmlSchemaType(name = "unsignedInt")
    protected Long pdotpdoCycleDataLim;
    @XmlAttribute(name = "PDOTPDOOverallObjects")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer pdotpdoOverallObjects;
    @XmlAttribute(name = "PHYExtEPLPorts")
    @XmlSchemaType(name = "unsignedByte")
    protected Short phyExtEPLPorts;
    @XmlAttribute(name = "PHYHubDelay")
    @XmlSchemaType(name = "unsignedInt")
    protected Long phyHubDelay;
    @XmlAttribute(name = "PHYHubIntegrated")
    protected Boolean phyHubIntegrated;
    @XmlAttribute(name = "PHYHubJitter")
    @XmlSchemaType(name = "unsignedInt")
    protected Long phyHubJitter;
    @XmlAttribute(name = "RT1RT1SecuritySupport")
    protected Boolean rt1RT1SecuritySupport;
    @XmlAttribute(name = "RT1RT1Support")
    protected Boolean rt1RT1Support;
    @XmlAttribute(name = "RT2RT2Support")
    protected Boolean rt2RT2Support;
    @XmlAttribute(name = "SDOClient")
    protected Boolean sdoClient;
    @XmlAttribute(name = "SDOCmdFileRead")
    protected Boolean sdoCmdFileRead;
    @XmlAttribute(name = "SDOCmdFileWrite")
    protected Boolean sdoCmdFileWrite;
    @XmlAttribute(name = "SDOCmdLinkName")
    protected Boolean sdoCmdLinkName;
    @XmlAttribute(name = "SDOCmdReadAllByIndex")
    protected Boolean sdoCmdReadAllByIndex;
    @XmlAttribute(name = "SDOCmdReadByName")
    protected Boolean sdoCmdReadByName;
    @XmlAttribute(name = "SDOCmdReadMultParam")
    protected Boolean sdoCmdReadMultParam;
    @XmlAttribute(name = "SDOCmdWriteAllByIndex")
    protected Boolean sdoCmdWriteAllByIndex;
    @XmlAttribute(name = "SDOCmdWriteByName")
    protected Boolean sdoCmdWriteByName;
    @XmlAttribute(name = "SDOCmdWriteMultParam")
    protected Boolean sdoCmdWriteMultParam;
    @XmlAttribute(name = "SDOMaxConnections")
    @XmlSchemaType(name = "unsignedInt")
    protected Long sdoMaxConnections;
    @XmlAttribute(name = "SDOMaxParallelConnections")
    @XmlSchemaType(name = "unsignedInt")
    protected Long sdoMaxParallelConnections;
    @XmlAttribute(name = "SDOSeqLayerTxHistorySize")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer sdoSeqLayerTxHistorySize;
    @XmlAttribute(name = "SDOServer")
    protected Boolean sdoServer;
    @XmlAttribute(name = "SDOSupportASnd")
    protected Boolean sdoSupportASnd;
    @XmlAttribute(name = "SDOSupportPDO")
    protected Boolean sdoSupportPDO;
    @XmlAttribute(name = "SDOSupportUdpIp")
    protected Boolean sdoSupportUdpIp;
    @XmlAttribute(name = "DLLMultiplePReqPRes")
    protected Boolean dllMultiplePReqPRes;

    /**
     * Gets the value of the nmtBootTimeNotActive property.
     *
     */
    public long getNMTBootTimeNotActive() {
        return nmtBootTimeNotActive;
    }

    /**
     * Gets the value of the nmtCycleTimeGranularity property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getNMTCycleTimeGranularity() {
        if (nmtCycleTimeGranularity == null) {
            return 1L;
        } else {
            return nmtCycleTimeGranularity;
        }
    }

    /**
     * Gets the value of the nmtCycleTimeMax property.
     *
     */
    public long getNMTCycleTimeMax() {
        return nmtCycleTimeMax;
    }

    /**
     * Gets the value of the nmtCycleTimeMin property.
     *
     */
    public long getNMTCycleTimeMin() {
        return nmtCycleTimeMin;
    }

    /**
     * Gets the value of the nmtEmergencyQueueSize property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getNMTEmergencyQueueSize() {
        if (nmtEmergencyQueueSize == null) {
            return 0L;
        } else {
            return nmtEmergencyQueueSize;
        }
    }

    /**
     * Gets the value of the nmtErrorEntries property.
     *
     */
    public long getNMTErrorEntries() {
        return nmtErrorEntries;
    }

    /**
     * Gets the value of the nmtMaxCNNodeID property.
     *
     * @return possible object is {@link Short }
     *
     */
    public short getNMTMaxCNNodeID() {
        if (nmtMaxCNNodeID == null) {
            return ((short) 239);
        } else {
            return nmtMaxCNNodeID;
        }
    }

    /**
     * Gets the value of the nmtMaxCNNumber property.
     *
     * @return possible object is {@link Short }
     *
     */
    public short getNMTMaxCNNumber() {
        if (nmtMaxCNNumber == null) {
            return ((short) 239);
        } else {
            return nmtMaxCNNumber;
        }
    }

    /**
     * Gets the value of the nmtMaxHeartbeats property.
     *
     * @return possible object is {@link Short }
     *
     */
    public short getNMTMaxHeartbeats() {
        if (nmtMaxHeartbeats == null) {
            return ((short) 254);
        } else {
            return nmtMaxHeartbeats;
        }
    }

    /**
     * Gets the value of the nmtMinRedCycleTime property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getNMTMinRedCycleTime() {
        if (nmtMinRedCycleTime == null) {
            return 0L;
        } else {
            return nmtMinRedCycleTime;
        }
    }

    /**
     * Gets the value of the nmtProductCode property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getNMTProductCode() {
        if (nmtProductCode == null) {
            return 0L;
        } else {
            return nmtProductCode;
        }
    }

    /**
     * Gets the value of the nmtRevisionNo property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getNMTRevisionNo() {
        if (nmtRevisionNo == null) {
            return 0L;
        } else {
            return nmtRevisionNo;
        }
    }

    /**
     * Gets the value of the pdoGranularity property.
     *
     * @return possible object is {@link Short }
     *
     */
    public short getPDOGranularity() {
        if (pdoGranularity == null) {
            return ((short) 8);
        } else {
            return pdoGranularity;
        }
    }

    /**
     * Gets the value of the pdoMaxDescrMem property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getPDOMaxDescrMem() {
        if (pdoMaxDescrMem == null) {
            return 4294967295L;
        } else {
            return pdoMaxDescrMem;
        }
    }

    /**
     * Gets the value of the pdorpdoChannelObjects property.
     *
     * @return possible object is {@link Short }
     *
     */
    public short getPDORPDOChannelObjects() {
        if (pdorpdoChannelObjects == null) {
            return ((short) 254);
        } else {
            return pdorpdoChannelObjects;
        }
    }

    /**
     * Gets the value of the pdorpdoChannels property.
     *
     * @return possible object is {@link Integer }
     *
     */
    public int getPDORPDOChannels() {
        if (pdorpdoChannels == null) {
            return 256;
        } else {
            return pdorpdoChannels;
        }
    }

    /**
     * Gets the value of the pdorpdoCycleDataLim property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getPDORPDOCycleDataLim() {
        if (pdorpdoCycleDataLim == null) {
            return 4294967295L;
        } else {
            return pdorpdoCycleDataLim;
        }
    }

    /**
     * Gets the value of the pdorpdoOverallObjects property.
     *
     * @return possible object is {@link Integer }
     *
     */
    public int getPDORPDOOverallObjects() {
        if (pdorpdoOverallObjects == null) {
            return 65535;
        } else {
            return pdorpdoOverallObjects;
        }
    }

    /**
     * Gets the value of the pdotpdoChannelObjects property.
     *
     * @return possible object is {@link Short }
     *
     */
    public short getPDOTPDOChannelObjects() {
        if (pdotpdoChannelObjects == null) {
            return ((short) 254);
        } else {
            return pdotpdoChannelObjects;
        }
    }

    /**
     * Gets the value of the pdotpdoCycleDataLim property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getPDOTPDOCycleDataLim() {
        if (pdotpdoCycleDataLim == null) {
            return 4294967295L;
        } else {
            return pdotpdoCycleDataLim;
        }
    }

    /**
     * Gets the value of the pdotpdoOverallObjects property.
     *
     * @return possible object is {@link Integer }
     *
     */
    public int getPDOTPDOOverallObjects() {
        if (pdotpdoOverallObjects == null) {
            return 65535;
        } else {
            return pdotpdoOverallObjects;
        }
    }

    /**
     * Gets the value of the phyExtEPLPorts property.
     *
     * @return possible object is {@link Short }
     *
     */
    public short getPHYExtEPLPorts() {
        if (phyExtEPLPorts == null) {
            return ((short) 2);
        } else {
            return phyExtEPLPorts;
        }
    }

    /**
     * Gets the value of the phyHubDelay property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getPHYHubDelay() {
        if (phyHubDelay == null) {
            return 460L;
        } else {
            return phyHubDelay;
        }
    }

    /**
     * Gets the value of the phyHubJitter property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getPHYHubJitter() {
        if (phyHubJitter == null) {
            return 70L;
        } else {
            return phyHubJitter;
        }
    }

    /**
     * Gets the value of the sdoMaxConnections property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getSDOMaxConnections() {
        if (sdoMaxConnections == null) {
            return 1L;
        } else {
            return sdoMaxConnections;
        }
    }

    /**
     * Gets the value of the sdoMaxParallelConnections property.
     *
     * @return possible object is {@link Long }
     *
     */
    public long getSDOMaxParallelConnections() {
        if (sdoMaxParallelConnections == null) {
            return 1L;
        } else {
            return sdoMaxParallelConnections;
        }
    }

    /**
     * Gets the value of the sdoSeqLayerTxHistorySize property.
     *
     * @return possible object is {@link Integer }
     *
     */
    public int getSDOSeqLayerTxHistorySize() {
        if (sdoSeqLayerTxHistorySize == null) {
            return 5;
        } else {
            return sdoSeqLayerTxHistorySize;
        }
    }

    /**
     * Gets the value of the cfmConfigManager property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isCFMConfigManager() {
        if (cfmConfigManager == null) {
            return false;
        } else {
            return cfmConfigManager;
        }
    }

    /**
     * Gets the value of the dllErrBadPhysMode property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isDLLErrBadPhysMode() {
        if (dllErrBadPhysMode == null) {
            return false;
        } else {
            return dllErrBadPhysMode;
        }
    }

    /**
     * Gets the value of the dllErrMacBuffer property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isDLLErrMacBuffer() {
        if (dllErrMacBuffer == null) {
            return false;
        } else {
            return dllErrMacBuffer;
        }
    }

    /**
     * Gets the value of the dllFeatureCN property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isDLLFeatureCN() {
        if (dllFeatureCN == null) {
            return true;
        } else {
            return dllFeatureCN;
        }
    }

    /**
     * Gets the value of the dllFeatureMN property.
     *
     */
    public boolean isDLLFeatureMN() {
        return dllFeatureMN;
    }

    /**
     * Gets the value of the dllMultiplePReqPRes property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isDLLMultiplePReqPRes() {
        if (dllMultiplePReqPRes == null) {
            return false;
        } else {
            return dllMultiplePReqPRes;
        }
    }

    /**
     * Gets the value of the nmtExtNmtCmds property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTExtNmtCmds() {
        if (nmtExtNmtCmds == null) {
            return false;
        } else {
            return nmtExtNmtCmds;
        }
    }

    /**
     * Gets the value of the nmtFlushArpEntry property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTFlushArpEntry() {
        if (nmtFlushArpEntry == null) {
            return false;
        } else {
            return nmtFlushArpEntry;
        }
    }

    /**
     * Gets the value of the nmtIsochronous property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTIsochronous() {
        if (nmtIsochronous == null) {
            return true;
        } else {
            return nmtIsochronous;
        }
    }

    /**
     * Gets the value of the nmtNetHostNameSet property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTNetHostNameSet() {
        if (nmtNetHostNameSet == null) {
            return false;
        } else {
            return nmtNetHostNameSet;
        }
    }

    /**
     * Gets the value of the nmtNodeIDByHW property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTNodeIDByHW() {
        if (nmtNodeIDByHW == null) {
            return true;
        } else {
            return nmtNodeIDByHW;
        }
    }

    /**
     * Gets the value of the nmtNodeIDBySW property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTNodeIDBySW() {
        if (nmtNodeIDBySW == null) {
            return false;
        } else {
            return nmtNodeIDBySW;
        }
    }

    /**
     * Gets the value of the nmtPublishActiveNodes property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTPublishActiveNodes() {
        if (nmtPublishActiveNodes == null) {
            return false;
        } else {
            return nmtPublishActiveNodes;
        }
    }

    /**
     * Gets the value of the nmtPublishConfigNodes property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTPublishConfigNodes() {
        if (nmtPublishConfigNodes == null) {
            return false;
        } else {
            return nmtPublishConfigNodes;
        }
    }

    /**
     * Gets the value of the nmtPublishEmergencyNew property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTPublishEmergencyNew() {
        if (nmtPublishEmergencyNew == null) {
            return false;
        } else {
            return nmtPublishEmergencyNew;
        }
    }

    /**
     * Gets the value of the nmtPublishNodeState property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTPublishNodeState() {
        if (nmtPublishNodeState == null) {
            return false;
        } else {
            return nmtPublishNodeState;
        }
    }

    /**
     * Gets the value of the nmtPublishOperational property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTPublishOperational() {
        if (nmtPublishOperational == null) {
            return false;
        } else {
            return nmtPublishOperational;
        }
    }

    /**
     * Gets the value of the nmtPublishPreOp1 property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTPublishPreOp1() {
        if (nmtPublishPreOp1 == null) {
            return false;
        } else {
            return nmtPublishPreOp1;
        }
    }

    /**
     * Gets the value of the nmtPublishPreOp2 property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTPublishPreOp2() {
        if (nmtPublishPreOp2 == null) {
            return false;
        } else {
            return nmtPublishPreOp2;
        }
    }

    /**
     * Gets the value of the nmtPublishReadyToOp property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTPublishReadyToOp() {
        if (nmtPublishReadyToOp == null) {
            return false;
        } else {
            return nmtPublishReadyToOp;
        }
    }

    /**
     * Gets the value of the nmtPublishStopped property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTPublishStopped() {
        if (nmtPublishStopped == null) {
            return false;
        } else {
            return nmtPublishStopped;
        }
    }

    /**
     * Gets the value of the nmtPublishTime property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNMTPublishTime() {
        if (nmtPublishTime == null) {
            return false;
        } else {
            return nmtPublishTime;
        }
    }

    /**
     * Gets the value of the nwlForward property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNWLForward() {
        if (nwlForward == null) {
            return false;
        } else {
            return nwlForward;
        }
    }

    /**
     * Gets the value of the nwlicmpSupport property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNWLICMPSupport() {
        if (nwlicmpSupport == null) {
            return false;
        } else {
            return nwlicmpSupport;
        }
    }

    /**
     * Gets the value of the nwlipSupport property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isNWLIPSupport() {
        if (nwlipSupport == null) {
            return true;
        } else {
            return nwlipSupport;
        }
    }

    /**
     * Gets the value of the pdoDynamicMapping property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isPDODynamicMapping() {
        if (pdoDynamicMapping == null) {
            return true;
        } else {
            return pdoDynamicMapping;
        }
    }

    /**
     * Gets the value of the pdoSelfReceipt property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isPDOSelfReceipt() {
        if (pdoSelfReceipt == null) {
            return false;
        } else {
            return pdoSelfReceipt;
        }
    }

    /**
     * Gets the value of the phyHubIntegrated property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isPHYHubIntegrated() {
        if (phyHubIntegrated == null) {
            return true;
        } else {
            return phyHubIntegrated;
        }
    }

    /**
     * Gets the value of the rt1RT1SecuritySupport property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isRT1RT1SecuritySupport() {
        if (rt1RT1SecuritySupport == null) {
            return false;
        } else {
            return rt1RT1SecuritySupport;
        }
    }

    /**
     * Gets the value of the rt1RT1Support property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isRT1RT1Support() {
        if (rt1RT1Support == null) {
            return false;
        } else {
            return rt1RT1Support;
        }
    }

    /**
     * Gets the value of the rt2RT2Support property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isRT2RT2Support() {
        if (rt2RT2Support == null) {
            return false;
        } else {
            return rt2RT2Support;
        }
    }

    /**
     * Gets the value of the sdoClient property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOClient() {
        if (sdoClient == null) {
            return true;
        } else {
            return sdoClient;
        }
    }

    /**
     * Gets the value of the sdoCmdFileRead property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOCmdFileRead() {
        if (sdoCmdFileRead == null) {
            return false;
        } else {
            return sdoCmdFileRead;
        }
    }

    /**
     * Gets the value of the sdoCmdFileWrite property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOCmdFileWrite() {
        if (sdoCmdFileWrite == null) {
            return false;
        } else {
            return sdoCmdFileWrite;
        }
    }

    /**
     * Gets the value of the sdoCmdLinkName property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOCmdLinkName() {
        if (sdoCmdLinkName == null) {
            return false;
        } else {
            return sdoCmdLinkName;
        }
    }

    /**
     * Gets the value of the sdoCmdReadAllByIndex property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOCmdReadAllByIndex() {
        if (sdoCmdReadAllByIndex == null) {
            return false;
        } else {
            return sdoCmdReadAllByIndex;
        }
    }

    /**
     * Gets the value of the sdoCmdReadByName property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOCmdReadByName() {
        if (sdoCmdReadByName == null) {
            return false;
        } else {
            return sdoCmdReadByName;
        }
    }

    /**
     * Gets the value of the sdoCmdReadMultParam property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOCmdReadMultParam() {
        if (sdoCmdReadMultParam == null) {
            return false;
        } else {
            return sdoCmdReadMultParam;
        }
    }

    /**
     * Gets the value of the sdoCmdWriteAllByIndex property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOCmdWriteAllByIndex() {
        if (sdoCmdWriteAllByIndex == null) {
            return false;
        } else {
            return sdoCmdWriteAllByIndex;
        }
    }

    /**
     * Gets the value of the sdoCmdWriteByName property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOCmdWriteByName() {
        if (sdoCmdWriteByName == null) {
            return false;
        } else {
            return sdoCmdWriteByName;
        }
    }

    /**
     * Gets the value of the sdoCmdWriteMultParam property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOCmdWriteMultParam() {
        if (sdoCmdWriteMultParam == null) {
            return false;
        } else {
            return sdoCmdWriteMultParam;
        }
    }

    /**
     * Gets the value of the sdoServer property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOServer() {
        if (sdoServer == null) {
            return true;
        } else {
            return sdoServer;
        }
    }

    /**
     * Gets the value of the sdoSupportASnd property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOSupportASnd() {
        if (sdoSupportASnd == null) {
            return false;
        } else {
            return sdoSupportASnd;
        }
    }

    /**
     * Gets the value of the sdoSupportPDO property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOSupportPDO() {
        if (sdoSupportPDO == null) {
            return false;
        } else {
            return sdoSupportPDO;
        }
    }

    /**
     * Gets the value of the sdoSupportUdpIp property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isSDOSupportUdpIp() {
        if (sdoSupportUdpIp == null) {
            return false;
        } else {
            return sdoSupportUdpIp;
        }
    }

    /**
     * Sets the value of the cfmConfigManager property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setCFMConfigManager(Boolean value) {
        cfmConfigManager = value;
    }

    /**
     * Sets the value of the dllErrBadPhysMode property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setDLLErrBadPhysMode(Boolean value) {
        dllErrBadPhysMode = value;
    }

    /**
     * Sets the value of the dllErrMacBuffer property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setDLLErrMacBuffer(Boolean value) {
        dllErrMacBuffer = value;
    }

    /**
     * Sets the value of the dllFeatureCN property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setDLLFeatureCN(Boolean value) {
        dllFeatureCN = value;
    }

    /**
     * Sets the value of the dllFeatureMN property.
     *
     */
    public void setDLLFeatureMN(boolean value) {
        dllFeatureMN = value;
    }

    /**
     * Sets the value of the dllMultiplePReqPRes property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setDLLMultiplePReqPRes(Boolean value) {
        dllMultiplePReqPRes = value;
    }

    /**
     * Sets the value of the nmtBootTimeNotActive property.
     *
     */
    public void setNMTBootTimeNotActive(long value) {
        nmtBootTimeNotActive = value;
    }

    /**
     * Sets the value of the nmtCycleTimeGranularity property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setNMTCycleTimeGranularity(Long value) {
        nmtCycleTimeGranularity = value;
    }

    /**
     * Sets the value of the nmtCycleTimeMax property.
     *
     */
    public void setNMTCycleTimeMax(long value) {
        nmtCycleTimeMax = value;
    }

    /**
     * Sets the value of the nmtCycleTimeMin property.
     *
     */
    public void setNMTCycleTimeMin(long value) {
        nmtCycleTimeMin = value;
    }

    /**
     * Sets the value of the nmtEmergencyQueueSize property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setNMTEmergencyQueueSize(Long value) {
        nmtEmergencyQueueSize = value;
    }

    /**
     * Sets the value of the nmtErrorEntries property.
     *
     */
    public void setNMTErrorEntries(long value) {
        nmtErrorEntries = value;
    }

    /**
     * Sets the value of the nmtExtNmtCmds property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTExtNmtCmds(Boolean value) {
        nmtExtNmtCmds = value;
    }

    /**
     * Sets the value of the nmtFlushArpEntry property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTFlushArpEntry(Boolean value) {
        nmtFlushArpEntry = value;
    }

    /**
     * Sets the value of the nmtIsochronous property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTIsochronous(Boolean value) {
        nmtIsochronous = value;
    }

    /**
     * Sets the value of the nmtMaxCNNodeID property.
     *
     * @param value allowed object is {@link Short }
     *
     */
    public void setNMTMaxCNNodeID(Short value) {
        nmtMaxCNNodeID = value;
    }

    /**
     * Sets the value of the nmtMaxCNNumber property.
     *
     * @param value allowed object is {@link Short }
     *
     */
    public void setNMTMaxCNNumber(Short value) {
        nmtMaxCNNumber = value;
    }

    /**
     * Sets the value of the nmtMaxHeartbeats property.
     *
     * @param value allowed object is {@link Short }
     *
     */
    public void setNMTMaxHeartbeats(Short value) {
        nmtMaxHeartbeats = value;
    }

    /**
     * Sets the value of the nmtMinRedCycleTime property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setNMTMinRedCycleTime(Long value) {
        nmtMinRedCycleTime = value;
    }

    /**
     * Sets the value of the nmtNetHostNameSet property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTNetHostNameSet(Boolean value) {
        nmtNetHostNameSet = value;
    }

    /**
     * Sets the value of the nmtNodeIDByHW property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTNodeIDByHW(Boolean value) {
        nmtNodeIDByHW = value;
    }

    /**
     * Sets the value of the nmtNodeIDBySW property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTNodeIDBySW(Boolean value) {
        nmtNodeIDBySW = value;
    }

    /**
     * Sets the value of the nmtProductCode property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setNMTProductCode(Long value) {
        nmtProductCode = value;
    }

    /**
     * Sets the value of the nmtPublishActiveNodes property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTPublishActiveNodes(Boolean value) {
        nmtPublishActiveNodes = value;
    }

    /**
     * Sets the value of the nmtPublishConfigNodes property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTPublishConfigNodes(Boolean value) {
        nmtPublishConfigNodes = value;
    }

    /**
     * Sets the value of the nmtPublishEmergencyNew property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTPublishEmergencyNew(Boolean value) {
        nmtPublishEmergencyNew = value;
    }

    /**
     * Sets the value of the nmtPublishNodeState property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTPublishNodeState(Boolean value) {
        nmtPublishNodeState = value;
    }

    /**
     * Sets the value of the nmtPublishOperational property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTPublishOperational(Boolean value) {
        nmtPublishOperational = value;
    }

    /**
     * Sets the value of the nmtPublishPreOp1 property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTPublishPreOp1(Boolean value) {
        nmtPublishPreOp1 = value;
    }

    /**
     * Sets the value of the nmtPublishPreOp2 property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTPublishPreOp2(Boolean value) {
        nmtPublishPreOp2 = value;
    }

    /**
     * Sets the value of the nmtPublishReadyToOp property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTPublishReadyToOp(Boolean value) {
        nmtPublishReadyToOp = value;
    }

    /**
     * Sets the value of the nmtPublishStopped property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTPublishStopped(Boolean value) {
        nmtPublishStopped = value;
    }

    /**
     * Sets the value of the nmtPublishTime property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNMTPublishTime(Boolean value) {
        nmtPublishTime = value;
    }

    /**
     * Sets the value of the nmtRevisionNo property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setNMTRevisionNo(Long value) {
        nmtRevisionNo = value;
    }

    /**
     * Sets the value of the nwlForward property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNWLForward(Boolean value) {
        nwlForward = value;
    }

    /**
     * Sets the value of the nwlicmpSupport property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNWLICMPSupport(Boolean value) {
        nwlicmpSupport = value;
    }

    /**
     * Sets the value of the nwlipSupport property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setNWLIPSupport(Boolean value) {
        nwlipSupport = value;
    }

    /**
     * Sets the value of the pdoDynamicMapping property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setPDODynamicMapping(Boolean value) {
        pdoDynamicMapping = value;
    }

    /**
     * Sets the value of the pdoGranularity property.
     *
     * @param value allowed object is {@link Short }
     *
     */
    public void setPDOGranularity(Short value) {
        pdoGranularity = value;
    }

    /**
     * Sets the value of the pdoMaxDescrMem property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setPDOMaxDescrMem(Long value) {
        pdoMaxDescrMem = value;
    }

    /**
     * Sets the value of the pdorpdoChannelObjects property.
     *
     * @param value allowed object is {@link Short }
     *
     */
    public void setPDORPDOChannelObjects(Short value) {
        pdorpdoChannelObjects = value;
    }

    /**
     * Sets the value of the pdorpdoChannels property.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setPDORPDOChannels(Integer value) {
        pdorpdoChannels = value;
    }

    /**
     * Sets the value of the pdorpdoCycleDataLim property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setPDORPDOCycleDataLim(Long value) {
        pdorpdoCycleDataLim = value;
    }

    /**
     * Sets the value of the pdorpdoOverallObjects property.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setPDORPDOOverallObjects(Integer value) {
        pdorpdoOverallObjects = value;
    }

    /**
     * Sets the value of the pdoSelfReceipt property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setPDOSelfReceipt(Boolean value) {
        pdoSelfReceipt = value;
    }

    /**
     * Sets the value of the pdotpdoChannelObjects property.
     *
     * @param value allowed object is {@link Short }
     *
     */
    public void setPDOTPDOChannelObjects(Short value) {
        pdotpdoChannelObjects = value;
    }

    /**
     * Sets the value of the pdotpdoCycleDataLim property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setPDOTPDOCycleDataLim(Long value) {
        pdotpdoCycleDataLim = value;
    }

    /**
     * Sets the value of the pdotpdoOverallObjects property.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setPDOTPDOOverallObjects(Integer value) {
        pdotpdoOverallObjects = value;
    }

    /**
     * Sets the value of the phyExtEPLPorts property.
     *
     * @param value allowed object is {@link Short }
     *
     */
    public void setPHYExtEPLPorts(Short value) {
        phyExtEPLPorts = value;
    }

    /**
     * Sets the value of the phyHubDelay property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setPHYHubDelay(Long value) {
        phyHubDelay = value;
    }

    /**
     * Sets the value of the phyHubIntegrated property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setPHYHubIntegrated(Boolean value) {
        phyHubIntegrated = value;
    }

    /**
     * Sets the value of the phyHubJitter property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setPHYHubJitter(Long value) {
        phyHubJitter = value;
    }

    /**
     * Sets the value of the rt1RT1SecuritySupport property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setRT1RT1SecuritySupport(Boolean value) {
        rt1RT1SecuritySupport = value;
    }

    /**
     * Sets the value of the rt1RT1Support property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setRT1RT1Support(Boolean value) {
        rt1RT1Support = value;
    }

    /**
     * Sets the value of the rt2RT2Support property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setRT2RT2Support(Boolean value) {
        rt2RT2Support = value;
    }

    /**
     * Sets the value of the sdoClient property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOClient(Boolean value) {
        sdoClient = value;
    }

    /**
     * Sets the value of the sdoCmdFileRead property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOCmdFileRead(Boolean value) {
        sdoCmdFileRead = value;
    }

    /**
     * Sets the value of the sdoCmdFileWrite property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOCmdFileWrite(Boolean value) {
        sdoCmdFileWrite = value;
    }

    /**
     * Sets the value of the sdoCmdLinkName property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOCmdLinkName(Boolean value) {
        sdoCmdLinkName = value;
    }

    /**
     * Sets the value of the sdoCmdReadAllByIndex property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOCmdReadAllByIndex(Boolean value) {
        sdoCmdReadAllByIndex = value;
    }

    /**
     * Sets the value of the sdoCmdReadByName property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOCmdReadByName(Boolean value) {
        sdoCmdReadByName = value;
    }

    /**
     * Sets the value of the sdoCmdReadMultParam property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOCmdReadMultParam(Boolean value) {
        sdoCmdReadMultParam = value;
    }

    /**
     * Sets the value of the sdoCmdWriteAllByIndex property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOCmdWriteAllByIndex(Boolean value) {
        sdoCmdWriteAllByIndex = value;
    }

    /**
     * Sets the value of the sdoCmdWriteByName property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOCmdWriteByName(Boolean value) {
        sdoCmdWriteByName = value;
    }

    /**
     * Sets the value of the sdoCmdWriteMultParam property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOCmdWriteMultParam(Boolean value) {
        sdoCmdWriteMultParam = value;
    }

    /**
     * Sets the value of the sdoMaxConnections property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setSDOMaxConnections(Long value) {
        sdoMaxConnections = value;
    }

    /**
     * Sets the value of the sdoMaxParallelConnections property.
     *
     * @param value allowed object is {@link Long }
     *
     */
    public void setSDOMaxParallelConnections(Long value) {
        sdoMaxParallelConnections = value;
    }

    /**
     * Sets the value of the sdoSeqLayerTxHistorySize property.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setSDOSeqLayerTxHistorySize(Integer value) {
        sdoSeqLayerTxHistorySize = value;
    }

    /**
     * Sets the value of the sdoServer property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOServer(Boolean value) {
        sdoServer = value;
    }

    /**
     * Sets the value of the sdoSupportASnd property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOSupportASnd(Boolean value) {
        sdoSupportASnd = value;
    }

    /**
     * Sets the value of the sdoSupportPDO property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOSupportPDO(Boolean value) {
        sdoSupportPDO = value;
    }

    /**
     * Sets the value of the sdoSupportUdpIp property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setSDOSupportUdpIp(Boolean value) {
        sdoSupportUdpIp = value;
    }

    /**
     * Gets the value of the dllMultiplePReqPRes property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDLLMultiplePReqPRes() {
        if (dllMultiplePReqPRes == null) {
            return false;
        } else {
            return dllMultiplePReqPRes;
        }
    }

    /**
     * Sets the value of the dllMultiplePReqPRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDLLMultiplePReqPRes(Boolean value) {
        this.dllMultiplePReqPRes = value;
    }

}
