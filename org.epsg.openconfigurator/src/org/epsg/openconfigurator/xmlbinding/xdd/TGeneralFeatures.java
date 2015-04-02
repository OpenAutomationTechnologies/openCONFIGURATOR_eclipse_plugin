
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für t_GeneralFeatures complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
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
 *       &lt;attribute name="NMTFlushArpEntry" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTNetHostNameSet" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTMaxCNNodeID" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="239" /&gt;
 *       &lt;attribute name="NMTMaxCNNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="239" /&gt;
 *       &lt;attribute name="NMTMaxHeartbeats" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="254" /&gt;
 *       &lt;attribute name="NMTNodeIDByHW" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
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
 *       &lt;attribute name="PHYExtEPLPorts" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="1" /&gt;
 *       &lt;attribute name="PHYHubDelay" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="460" /&gt;
 *       &lt;attribute name="PHYHubIntegrated" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
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
    @XmlAttribute(name = "NMTFlushArpEntry")
    protected Boolean nmtFlushArpEntry;
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

    /**
     * Ruft den Wert der cfmConfigManager-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der cfmConfigManager-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCFMConfigManager(Boolean value) {
        this.cfmConfigManager = value;
    }

    /**
     * Ruft den Wert der dllErrBadPhysMode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der dllErrBadPhysMode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDLLErrBadPhysMode(Boolean value) {
        this.dllErrBadPhysMode = value;
    }

    /**
     * Ruft den Wert der dllErrMacBuffer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der dllErrMacBuffer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDLLErrMacBuffer(Boolean value) {
        this.dllErrMacBuffer = value;
    }

    /**
     * Ruft den Wert der dllFeatureCN-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der dllFeatureCN-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDLLFeatureCN(Boolean value) {
        this.dllFeatureCN = value;
    }

    /**
     * Ruft den Wert der dllFeatureMN-Eigenschaft ab.
     * 
     */
    public boolean isDLLFeatureMN() {
        return dllFeatureMN;
    }

    /**
     * Legt den Wert der dllFeatureMN-Eigenschaft fest.
     * 
     */
    public void setDLLFeatureMN(boolean value) {
        this.dllFeatureMN = value;
    }

    /**
     * Ruft den Wert der nmtBootTimeNotActive-Eigenschaft ab.
     * 
     */
    public long getNMTBootTimeNotActive() {
        return nmtBootTimeNotActive;
    }

    /**
     * Legt den Wert der nmtBootTimeNotActive-Eigenschaft fest.
     * 
     */
    public void setNMTBootTimeNotActive(long value) {
        this.nmtBootTimeNotActive = value;
    }

    /**
     * Ruft den Wert der nmtCycleTimeGranularity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getNMTCycleTimeGranularity() {
        if (nmtCycleTimeGranularity == null) {
            return  1L;
        } else {
            return nmtCycleTimeGranularity;
        }
    }

    /**
     * Legt den Wert der nmtCycleTimeGranularity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNMTCycleTimeGranularity(Long value) {
        this.nmtCycleTimeGranularity = value;
    }

    /**
     * Ruft den Wert der nmtCycleTimeMax-Eigenschaft ab.
     * 
     */
    public long getNMTCycleTimeMax() {
        return nmtCycleTimeMax;
    }

    /**
     * Legt den Wert der nmtCycleTimeMax-Eigenschaft fest.
     * 
     */
    public void setNMTCycleTimeMax(long value) {
        this.nmtCycleTimeMax = value;
    }

    /**
     * Ruft den Wert der nmtCycleTimeMin-Eigenschaft ab.
     * 
     */
    public long getNMTCycleTimeMin() {
        return nmtCycleTimeMin;
    }

    /**
     * Legt den Wert der nmtCycleTimeMin-Eigenschaft fest.
     * 
     */
    public void setNMTCycleTimeMin(long value) {
        this.nmtCycleTimeMin = value;
    }

    /**
     * Ruft den Wert der nmtMinRedCycleTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getNMTMinRedCycleTime() {
        if (nmtMinRedCycleTime == null) {
            return  0L;
        } else {
            return nmtMinRedCycleTime;
        }
    }

    /**
     * Legt den Wert der nmtMinRedCycleTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNMTMinRedCycleTime(Long value) {
        this.nmtMinRedCycleTime = value;
    }

    /**
     * Ruft den Wert der nmtEmergencyQueueSize-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getNMTEmergencyQueueSize() {
        if (nmtEmergencyQueueSize == null) {
            return  0L;
        } else {
            return nmtEmergencyQueueSize;
        }
    }

    /**
     * Legt den Wert der nmtEmergencyQueueSize-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNMTEmergencyQueueSize(Long value) {
        this.nmtEmergencyQueueSize = value;
    }

    /**
     * Ruft den Wert der nmtErrorEntries-Eigenschaft ab.
     * 
     */
    public long getNMTErrorEntries() {
        return nmtErrorEntries;
    }

    /**
     * Legt den Wert der nmtErrorEntries-Eigenschaft fest.
     * 
     */
    public void setNMTErrorEntries(long value) {
        this.nmtErrorEntries = value;
    }

    /**
     * Ruft den Wert der nmtFlushArpEntry-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtFlushArpEntry-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTFlushArpEntry(Boolean value) {
        this.nmtFlushArpEntry = value;
    }

    /**
     * Ruft den Wert der nmtNetHostNameSet-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtNetHostNameSet-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTNetHostNameSet(Boolean value) {
        this.nmtNetHostNameSet = value;
    }

    /**
     * Ruft den Wert der nmtMaxCNNodeID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Short }
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
     * Legt den Wert der nmtMaxCNNodeID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNMTMaxCNNodeID(Short value) {
        this.nmtMaxCNNodeID = value;
    }

    /**
     * Ruft den Wert der nmtMaxCNNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Short }
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
     * Legt den Wert der nmtMaxCNNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNMTMaxCNNumber(Short value) {
        this.nmtMaxCNNumber = value;
    }

    /**
     * Ruft den Wert der nmtMaxHeartbeats-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Short }
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
     * Legt den Wert der nmtMaxHeartbeats-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNMTMaxHeartbeats(Short value) {
        this.nmtMaxHeartbeats = value;
    }

    /**
     * Ruft den Wert der nmtNodeIDByHW-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtNodeIDByHW-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTNodeIDByHW(Boolean value) {
        this.nmtNodeIDByHW = value;
    }

    /**
     * Ruft den Wert der nmtProductCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getNMTProductCode() {
        if (nmtProductCode == null) {
            return  0L;
        } else {
            return nmtProductCode;
        }
    }

    /**
     * Legt den Wert der nmtProductCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNMTProductCode(Long value) {
        this.nmtProductCode = value;
    }

    /**
     * Ruft den Wert der nmtPublishActiveNodes-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtPublishActiveNodes-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTPublishActiveNodes(Boolean value) {
        this.nmtPublishActiveNodes = value;
    }

    /**
     * Ruft den Wert der nmtPublishConfigNodes-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtPublishConfigNodes-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTPublishConfigNodes(Boolean value) {
        this.nmtPublishConfigNodes = value;
    }

    /**
     * Ruft den Wert der nmtPublishEmergencyNew-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtPublishEmergencyNew-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTPublishEmergencyNew(Boolean value) {
        this.nmtPublishEmergencyNew = value;
    }

    /**
     * Ruft den Wert der nmtPublishNodeState-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtPublishNodeState-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTPublishNodeState(Boolean value) {
        this.nmtPublishNodeState = value;
    }

    /**
     * Ruft den Wert der nmtPublishOperational-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtPublishOperational-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTPublishOperational(Boolean value) {
        this.nmtPublishOperational = value;
    }

    /**
     * Ruft den Wert der nmtPublishPreOp1-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtPublishPreOp1-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTPublishPreOp1(Boolean value) {
        this.nmtPublishPreOp1 = value;
    }

    /**
     * Ruft den Wert der nmtPublishPreOp2-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtPublishPreOp2-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTPublishPreOp2(Boolean value) {
        this.nmtPublishPreOp2 = value;
    }

    /**
     * Ruft den Wert der nmtPublishReadyToOp-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtPublishReadyToOp-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTPublishReadyToOp(Boolean value) {
        this.nmtPublishReadyToOp = value;
    }

    /**
     * Ruft den Wert der nmtPublishStopped-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtPublishStopped-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTPublishStopped(Boolean value) {
        this.nmtPublishStopped = value;
    }

    /**
     * Ruft den Wert der nmtPublishTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nmtPublishTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTPublishTime(Boolean value) {
        this.nmtPublishTime = value;
    }

    /**
     * Ruft den Wert der nmtRevisionNo-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getNMTRevisionNo() {
        if (nmtRevisionNo == null) {
            return  0L;
        } else {
            return nmtRevisionNo;
        }
    }

    /**
     * Legt den Wert der nmtRevisionNo-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNMTRevisionNo(Long value) {
        this.nmtRevisionNo = value;
    }

    /**
     * Ruft den Wert der nwlForward-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nwlForward-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNWLForward(Boolean value) {
        this.nwlForward = value;
    }

    /**
     * Ruft den Wert der nwlicmpSupport-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nwlicmpSupport-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNWLICMPSupport(Boolean value) {
        this.nwlicmpSupport = value;
    }

    /**
     * Ruft den Wert der nwlipSupport-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der nwlipSupport-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNWLIPSupport(Boolean value) {
        this.nwlipSupport = value;
    }

    /**
     * Ruft den Wert der pdoGranularity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Short }
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
     * Legt den Wert der pdoGranularity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setPDOGranularity(Short value) {
        this.pdoGranularity = value;
    }

    /**
     * Ruft den Wert der pdoMaxDescrMem-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getPDOMaxDescrMem() {
        if (pdoMaxDescrMem == null) {
            return  4294967295L;
        } else {
            return pdoMaxDescrMem;
        }
    }

    /**
     * Legt den Wert der pdoMaxDescrMem-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPDOMaxDescrMem(Long value) {
        this.pdoMaxDescrMem = value;
    }

    /**
     * Ruft den Wert der pdorpdoChannelObjects-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Short }
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
     * Legt den Wert der pdorpdoChannelObjects-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setPDORPDOChannelObjects(Short value) {
        this.pdorpdoChannelObjects = value;
    }

    /**
     * Ruft den Wert der pdorpdoChannels-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getPDORPDOChannels() {
        if (pdorpdoChannels == null) {
            return  256;
        } else {
            return pdorpdoChannels;
        }
    }

    /**
     * Legt den Wert der pdorpdoChannels-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPDORPDOChannels(Integer value) {
        this.pdorpdoChannels = value;
    }

    /**
     * Ruft den Wert der pdorpdoCycleDataLim-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getPDORPDOCycleDataLim() {
        if (pdorpdoCycleDataLim == null) {
            return  4294967295L;
        } else {
            return pdorpdoCycleDataLim;
        }
    }

    /**
     * Legt den Wert der pdorpdoCycleDataLim-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPDORPDOCycleDataLim(Long value) {
        this.pdorpdoCycleDataLim = value;
    }

    /**
     * Ruft den Wert der pdorpdoOverallObjects-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getPDORPDOOverallObjects() {
        if (pdorpdoOverallObjects == null) {
            return  65535;
        } else {
            return pdorpdoOverallObjects;
        }
    }

    /**
     * Legt den Wert der pdorpdoOverallObjects-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPDORPDOOverallObjects(Integer value) {
        this.pdorpdoOverallObjects = value;
    }

    /**
     * Ruft den Wert der pdoSelfReceipt-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der pdoSelfReceipt-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPDOSelfReceipt(Boolean value) {
        this.pdoSelfReceipt = value;
    }

    /**
     * Ruft den Wert der pdotpdoChannelObjects-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Short }
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
     * Legt den Wert der pdotpdoChannelObjects-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setPDOTPDOChannelObjects(Short value) {
        this.pdotpdoChannelObjects = value;
    }

    /**
     * Ruft den Wert der pdotpdoCycleDataLim-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getPDOTPDOCycleDataLim() {
        if (pdotpdoCycleDataLim == null) {
            return  4294967295L;
        } else {
            return pdotpdoCycleDataLim;
        }
    }

    /**
     * Legt den Wert der pdotpdoCycleDataLim-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPDOTPDOCycleDataLim(Long value) {
        this.pdotpdoCycleDataLim = value;
    }

    /**
     * Ruft den Wert der pdotpdoOverallObjects-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getPDOTPDOOverallObjects() {
        if (pdotpdoOverallObjects == null) {
            return  65535;
        } else {
            return pdotpdoOverallObjects;
        }
    }

    /**
     * Legt den Wert der pdotpdoOverallObjects-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPDOTPDOOverallObjects(Integer value) {
        this.pdotpdoOverallObjects = value;
    }

    /**
     * Ruft den Wert der phyExtEPLPorts-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getPHYExtEPLPorts() {
        if (phyExtEPLPorts == null) {
            return ((short) 1);
        } else {
            return phyExtEPLPorts;
        }
    }

    /**
     * Legt den Wert der phyExtEPLPorts-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setPHYExtEPLPorts(Short value) {
        this.phyExtEPLPorts = value;
    }

    /**
     * Ruft den Wert der phyHubDelay-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getPHYHubDelay() {
        if (phyHubDelay == null) {
            return  460L;
        } else {
            return phyHubDelay;
        }
    }

    /**
     * Legt den Wert der phyHubDelay-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPHYHubDelay(Long value) {
        this.phyHubDelay = value;
    }

    /**
     * Ruft den Wert der phyHubIntegrated-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPHYHubIntegrated() {
        if (phyHubIntegrated == null) {
            return false;
        } else {
            return phyHubIntegrated;
        }
    }

    /**
     * Legt den Wert der phyHubIntegrated-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPHYHubIntegrated(Boolean value) {
        this.phyHubIntegrated = value;
    }

    /**
     * Ruft den Wert der phyHubJitter-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getPHYHubJitter() {
        if (phyHubJitter == null) {
            return  70L;
        } else {
            return phyHubJitter;
        }
    }

    /**
     * Legt den Wert der phyHubJitter-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPHYHubJitter(Long value) {
        this.phyHubJitter = value;
    }

    /**
     * Ruft den Wert der rt1RT1SecuritySupport-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der rt1RT1SecuritySupport-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRT1RT1SecuritySupport(Boolean value) {
        this.rt1RT1SecuritySupport = value;
    }

    /**
     * Ruft den Wert der rt1RT1Support-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der rt1RT1Support-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRT1RT1Support(Boolean value) {
        this.rt1RT1Support = value;
    }

    /**
     * Ruft den Wert der rt2RT2Support-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der rt2RT2Support-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRT2RT2Support(Boolean value) {
        this.rt2RT2Support = value;
    }

    /**
     * Ruft den Wert der sdoClient-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der sdoClient-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSDOClient(Boolean value) {
        this.sdoClient = value;
    }

    /**
     * Ruft den Wert der sdoCmdFileRead-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der sdoCmdFileRead-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSDOCmdFileRead(Boolean value) {
        this.sdoCmdFileRead = value;
    }

    /**
     * Ruft den Wert der sdoCmdFileWrite-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der sdoCmdFileWrite-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSDOCmdFileWrite(Boolean value) {
        this.sdoCmdFileWrite = value;
    }

    /**
     * Ruft den Wert der sdoCmdLinkName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der sdoCmdLinkName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSDOCmdLinkName(Boolean value) {
        this.sdoCmdLinkName = value;
    }

    /**
     * Ruft den Wert der sdoCmdReadAllByIndex-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der sdoCmdReadAllByIndex-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSDOCmdReadAllByIndex(Boolean value) {
        this.sdoCmdReadAllByIndex = value;
    }

    /**
     * Ruft den Wert der sdoCmdReadByName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der sdoCmdReadByName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSDOCmdReadByName(Boolean value) {
        this.sdoCmdReadByName = value;
    }

    /**
     * Ruft den Wert der sdoCmdReadMultParam-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der sdoCmdReadMultParam-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSDOCmdReadMultParam(Boolean value) {
        this.sdoCmdReadMultParam = value;
    }

    /**
     * Ruft den Wert der sdoCmdWriteAllByIndex-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der sdoCmdWriteAllByIndex-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSDOCmdWriteAllByIndex(Boolean value) {
        this.sdoCmdWriteAllByIndex = value;
    }

    /**
     * Ruft den Wert der sdoCmdWriteByName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der sdoCmdWriteByName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSDOCmdWriteByName(Boolean value) {
        this.sdoCmdWriteByName = value;
    }

    /**
     * Ruft den Wert der sdoCmdWriteMultParam-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der sdoCmdWriteMultParam-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSDOCmdWriteMultParam(Boolean value) {
        this.sdoCmdWriteMultParam = value;
    }

    /**
     * Ruft den Wert der sdoMaxConnections-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getSDOMaxConnections() {
        if (sdoMaxConnections == null) {
            return  1L;
        } else {
            return sdoMaxConnections;
        }
    }

    /**
     * Legt den Wert der sdoMaxConnections-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSDOMaxConnections(Long value) {
        this.sdoMaxConnections = value;
    }

    /**
     * Ruft den Wert der sdoMaxParallelConnections-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getSDOMaxParallelConnections() {
        if (sdoMaxParallelConnections == null) {
            return  1L;
        } else {
            return sdoMaxParallelConnections;
        }
    }

    /**
     * Legt den Wert der sdoMaxParallelConnections-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSDOMaxParallelConnections(Long value) {
        this.sdoMaxParallelConnections = value;
    }

    /**
     * Ruft den Wert der sdoSeqLayerTxHistorySize-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getSDOSeqLayerTxHistorySize() {
        if (sdoSeqLayerTxHistorySize == null) {
            return  5;
        } else {
            return sdoSeqLayerTxHistorySize;
        }
    }

    /**
     * Legt den Wert der sdoSeqLayerTxHistorySize-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSDOSeqLayerTxHistorySize(Integer value) {
        this.sdoSeqLayerTxHistorySize = value;
    }

    /**
     * Ruft den Wert der sdoServer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der sdoServer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSDOServer(Boolean value) {
        this.sdoServer = value;
    }

}
