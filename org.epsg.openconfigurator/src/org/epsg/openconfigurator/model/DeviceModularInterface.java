package org.epsg.openconfigurator.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.epsg.openconfigurator.xmlbinding.xdd.FileList;
import org.epsg.openconfigurator.xmlbinding.xdd.ModuleTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.RangeList;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleAddressingChild;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleInterface;

public class DeviceModularInterface {

    private Node node;

    private List<Object> labellist = new ArrayList<Object>();

    private FileList fileList;

    private ModuleTypeList moduleTypeList;

    private String childID;

    private String type;

    private TModuleAddressingChild moduleAddressing;

    private BigInteger minAddress;

    private BigInteger maxAddress;

    private BigInteger minPosition;

    private BigInteger maxPosition;

    private BigInteger maxCount;

    private RangeList rangeList;

    private Object uniqueIDRef;

    public DeviceModularInterface(Node node,
            TModuleInterface moduleInterfaceModel) {
        this.node = node;
        if (moduleInterfaceModel != null) {
            labellist = moduleInterfaceModel.getLabelOrDescriptionOrLabelRef();

            fileList = moduleInterfaceModel.getFileList();

            moduleTypeList = moduleInterfaceModel.getModuleTypeList();

            childID = moduleInterfaceModel.getChildID();

            type = moduleInterfaceModel.getType();

            moduleAddressing = moduleInterfaceModel.getModuleAddressing();

            minAddress = moduleInterfaceModel.getMinAddress();

            maxAddress = moduleInterfaceModel.getMaxAddress();

            minPosition = moduleInterfaceModel.getMinPosition();

            maxPosition = moduleInterfaceModel.getMaxPosition();

            maxCount = moduleInterfaceModel.getMaxCount();
        }

    }

    public String getChildID() {
        return childID;
    }

    public FileList getFileList() {
        return fileList;
    }

    public List<Object> getLabellist() {
        return labellist;
    }

    public BigInteger getMaxAddress() {
        return maxAddress;
    }

    public BigInteger getMaxCount() {
        return maxCount;
    }

    public BigInteger getMaxPosition() {
        return maxPosition;
    }

    public BigInteger getMinAddress() {
        return minAddress;
    }

    public BigInteger getMinPosition() {
        return minPosition;
    }

    public TModuleAddressingChild getModuleAddressing() {
        return moduleAddressing;
    }

    public ModuleTypeList getModuleTypeList() {
        return moduleTypeList;
    }

    public Node getNode() {
        return node;
    }

    public String getType() {
        return type;
    }

    public void setChildID(String childID) {
        this.childID = childID;
    }

    public void setFileList(FileList fileList) {
        this.fileList = fileList;
    }

    public void setLabellist(List<Object> labellist) {
        this.labellist = labellist;
    }

    public void setMaxAddress(BigInteger maxAddress) {
        this.maxAddress = maxAddress;
    }

    public void setMaxCount(BigInteger maxCount) {
        this.maxCount = maxCount;
    }

    public void setMaxPosition(BigInteger maxPosition) {
        this.maxPosition = maxPosition;
    }

    public void setMinAddress(BigInteger minAddress) {
        this.minAddress = minAddress;
    }

    public void setMinPosition(BigInteger minPosition) {
        this.minPosition = minPosition;
    }

    public void setModuleAddressing(TModuleAddressingChild moduleAddressing) {
        this.moduleAddressing = moduleAddressing;
    }

    public void setModuleTypeList(ModuleTypeList moduleTypeList) {
        this.moduleTypeList = moduleTypeList;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setType(String type) {
        this.type = type;
    }
}
