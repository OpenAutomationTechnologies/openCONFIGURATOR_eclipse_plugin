package org.epsg.openconfigurator.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.epsg.openconfigurator.xmlbinding.xdd.ConnectedModuleList;
import org.epsg.openconfigurator.xmlbinding.xdd.FileList;
import org.epsg.openconfigurator.xmlbinding.xdd.Interface;
import org.epsg.openconfigurator.xmlbinding.xdd.RangeList;
import org.epsg.openconfigurator.xmlbinding.xdd.TInterfaceList;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleAddressingHead;

public class HeadNodeInterface {

    private RangeList rangeList;

    private Object uniqueIDRef;

    private Node node;

    private TInterfaceList.Interface intfc;

    private BigInteger maxModules;
    private ConnectedModuleList connectedModule;
    private FileList fileList;
    private byte[] firmwareList;
    private byte[] identList;
    private List<Object> labelDescription = new ArrayList<Object>();
    private TModuleAddressingHead moduleAddressing;
    private String interfaceType;
    private String interfaceUId;
    private boolean unUsedSlots;
    private boolean multipleModules;

    public HeadNodeInterface(Node node, Interface interfaces) {
        this.node = node;
        if (interfaces != null) {
            rangeList = interfaces.getRangeList();
            uniqueIDRef = interfaces.getUniqueIDRef();
            intfc = (TInterfaceList.Interface) uniqueIDRef;

            maxModules = intfc.getMaxModules();
            connectedModule = intfc.getConnectedModuleList();
            fileList = intfc.getFileList();
            firmwareList = intfc.getFirmwareList();
            identList = intfc.getIdentList();
            labelDescription = intfc.getLabelOrDescriptionOrLabelRef();
            moduleAddressing = intfc.getModuleAddressing();
            interfaceType = intfc.getType();
            interfaceUId = intfc.getUniqueID();
            unUsedSlots = intfc.isUnusedSlots();
            multipleModules = intfc.isMultipleModules();
        }
    }

    public ConnectedModuleList getConnectedModule() {
        return connectedModule;
    }

    public FileList getFileList() {
        return fileList;
    }

    public byte[] getFirmwareList() {
        return firmwareList;
    }

    public byte[] getIdentList() {
        return identList;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public String getInterfaceUId() {
        return interfaceUId;
    }

    public List<Object> getLabelDescription() {
        return labelDescription;
    }

    public BigInteger getMaxModules() {
        return maxModules;
    }

    public TModuleAddressingHead getModuleAddressing() {
        return moduleAddressing;
    }

    public Node getNode() {
        return node;
    }

    public RangeList getRangeList() {
        return rangeList;
    }

    public String getUniqueId() {
        if (uniqueIDRef instanceof TInterfaceList.Interface) {
            return intfc.getUniqueID();
        }
        return null;
    }

    public Object getUniqueIDRef() {
        return uniqueIDRef;
    }

    public boolean isMultipleModules() {
        return multipleModules;
    }

    public boolean isUnUsedSlots() {
        return unUsedSlots;
    }

    public void setConnectedModule(ConnectedModuleList connectedModule) {
        this.connectedModule = connectedModule;
    }

    public void setFileList(FileList fileList) {
        this.fileList = fileList;
    }

    public void setFirmwareList(byte[] firmwareList) {
        this.firmwareList = firmwareList;
    }

    public void setIdentList(byte[] identList) {
        this.identList = identList;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public void setInterfaceUId(String interfaceUId) {
        this.interfaceUId = interfaceUId;
    }

    public void setLabelDescription(List<Object> labelDescription) {
        this.labelDescription = labelDescription;
    }

    public void setMaxModules(BigInteger maxModules) {
        this.maxModules = maxModules;
    }

    public void setModuleAddressing(TModuleAddressingHead moduleAddressing) {
        this.moduleAddressing = moduleAddressing;
    }

    public void setMultipleModules(boolean multipleModules) {
        this.multipleModules = multipleModules;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setRangeList(RangeList rangeList) {
        this.rangeList = rangeList;
    }

    public void setUniqueIDRef(Object uniqueIDRef) {
        this.uniqueIDRef = uniqueIDRef;
    }

    public void setUnUsedSlots(boolean unUsedSlots) {
        this.unUsedSlots = unUsedSlots;
    }
}
