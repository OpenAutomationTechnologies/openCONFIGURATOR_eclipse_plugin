/*******************************************************************************
 * @file   HeadNodeInterface.java
 *
 * @author Sree Hari Vignesh, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2016, Kalycito Infotech Private Limited
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

package org.epsg.openconfigurator.model;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.xdd.ConnectedModuleList;
import org.epsg.openconfigurator.xmlbinding.xdd.FileList;
import org.epsg.openconfigurator.xmlbinding.xdd.Interface;
import org.epsg.openconfigurator.xmlbinding.xdd.RangeList;
import org.epsg.openconfigurator.xmlbinding.xdd.TInterfaceList;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleAddressingHead;
import org.jdom2.JDOMException;

/**
 * Describes the interface of modular head node.
 *
 * @author SreeHari
 *
 */
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
    private List<Object> labelDescription = new ArrayList<>();
    private TModuleAddressingHead moduleAddressing;
    private String interfaceType;
    private String interfaceUId;
    private boolean unUsedSlots;
    private boolean multipleModules;

    private Map<Integer, Module> moduleCollection = new HashMap<>();

    private Map<String, Module> moduleNameCollection = new HashMap<>();

    private Map<Integer, Module> addressCollection = new HashMap<>();

    private List<org.epsg.openconfigurator.xmlbinding.xdd.Range> listOfRange = new ArrayList<>();

    /**
     * Constructor that describes the interface of modular head node from XDD
     * Instance.
     *
     * @param node Instance of Modular head node.
     * @param interfaces XDD instance of Interface.
     */
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

    /**
     * Receives the address collection of module.
     *
     * @return Collection of module address.
     */
    public Map<Integer, Module> getAddressCollection() {
        return addressCollection;
    }

    /**
     * Receives the connected modules of head node interface.
     *
     * @return Instance of connected moduleList.
     */
    public ConnectedModuleList getConnectedModule() {
        return connectedModule;
    }

    /**
     * Receives the filelist of connected modules.
     *
     * @return Instance of FileList.
     */
    public FileList getFileList() {
        return fileList;
    }

    /**
     * Receives the firmware list of module.
     *
     * @return value of firmware.
     */
    public byte[] getFirmwareList() {
        return firmwareList;
    }

    /**
     * Receives the identlist of Module
     *
     * @return The value of identList from module.
     */
    public byte[] getIdentList() {
        return identList;
    }

    /**
     * @return The interface type of head node.
     */
    public String getInterfaceType() {
        return interfaceType;
    }

    /**
     * @return The ID of head node interface.
     */
    public String getInterfaceUId() {
        return interfaceUId;
    }

    /**
     * @return Unique Id of interface from XDD model.
     */
    public String getInterfaceUniqueId() {
        if (uniqueIDRef instanceof TInterfaceList.Interface) {
            return intfc.getUniqueID();
        }
        return null;
    }

    /**
     * @return the label description of head node interface.
     */
    public List<Object> getLabelDescription() {
        return labelDescription;
    }

    /**
     * @return The range list from the XDD model.
     */
    public List<org.epsg.openconfigurator.xmlbinding.xdd.Range> getlistofRange() {
        listOfRange = rangeList.getRange();
        return listOfRange;
    }

    /**
     * @return The value of maximum number of modules connected to interface.
     */
    public BigInteger getMaxModules() {
        if (maxModules != null) {
            return maxModules;
        }
        return null;
    }

    /**
     * @return The instance of TModuleAddressingHead.
     */
    public TModuleAddressingHead getModuleAddressing() {
        return moduleAddressing;
    }

    /**
     * @return The collection of modules connected to head node interface.
     */
    public Map<Integer, Module> getModuleCollection() {
        return moduleCollection;
    }

    /**
     * @return The collection of module names connected to head node interface.
     */
    public Map<String, Module> getModuleNameCollection() {
        return moduleNameCollection;
    }

    /**
     * @return the Instance of Node.
     */
    public Node getNode() {
        return node;
    }

    /**
     * @return The range List of interface.
     */
    public RangeList getRangeList() {
        return rangeList;
    }

    /**
     * @return The uniqueIdRef object of interface.
     */
    public Object getUniqueIDRef() {
        return uniqueIDRef;
    }

    /**
     * @return <true> if interface has modules connected, <false> otherwise.
     */
    public boolean hasModules() {
        if (moduleCollection.size() == 0) {
            return false;
        }
        return true;

    }

    /**
     * @return <true> if any slots of interface is unused, <false> otherwise.
     */
    public boolean isInterfaceUnUsedSlots() {
        return unUsedSlots;
    }

    /**
     * @return <true> if multiple type of modules can be connected to the
     *         interface, <false> otherwise.
     */
    public boolean isMultipleModules() {
        return multipleModules;
    }

    /**
     * Sets the connected module to java model from XDD instance.
     *
     * @param connectedModule XDD instance of connected module list.
     */
    public void setConnectedModule(ConnectedModuleList connectedModule) {
        this.connectedModule = connectedModule;
    }

    /**
     * Sets the file list to java model from XDD instance.
     *
     * @param fileList XDD instance of FileList
     */
    public void setFileList(FileList fileList) {
        this.fileList = fileList;
    }

    /**
     * Sets the firmware list to java model from XDD instance.
     *
     * @param firmwareList The value of firmware.
     */
    public void setFirmwareList(byte[] firmwareList) {
        this.firmwareList = firmwareList;
    }

    /**
     * Sets the ident list to java model from XDD instance.
     *
     * @param identList The value of identList.
     */
    public void setIdentList(byte[] identList) {
        this.identList = identList;
    }

    /**
     * Sets the type of interface.
     *
     * @param interfaceType The String value of interface type.
     */
    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    /**
     * Sets the ID of interface.
     *
     * @param interfaceUId The String value of interface ID.
     */
    public void setInterfaceUId(String interfaceUId) {
        this.interfaceUId = interfaceUId;
    }

    /**
     * Sets the label description to interface.
     *
     * @param labelDescription List of labels of interface.
     */
    public void setLabelDescription(List<Object> labelDescription) {
        this.labelDescription = labelDescription;
    }

    /**
     * Sets the maximum number of modules connected to interface.
     *
     * @param maxModules The values of max modules that can be connected.
     */
    public void setMaxModules(BigInteger maxModules) {
        this.maxModules = maxModules;
    }

    /**
     * Sets the module addressing scheme connected to interface.
     *
     * @param moduleAddressing Instance of TModuleAddressingHead.
     */
    public void setModuleAddressing(TModuleAddressingHead moduleAddressing) {
        this.moduleAddressing = moduleAddressing;
    }

    /**
     * Sets the multiple modules connected to interface.
     *
     * @param multipleModules <true> if multiple modules allowed, <false>
     *            otherwise.
     */
    public void setMultipleModules(boolean multipleModules) {
        this.multipleModules = multipleModules;
    }

    /**
     * Sets the instance of Node
     *
     * @param node Instance of Node.
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * Sets the list of range to the head interface.
     *
     * @param rangeList XDD instance of RangeList.
     */
    public void setRangeList(RangeList rangeList) {
        this.rangeList = rangeList;
    }

    /**
     * Sets the uniqueIdRef to the head interface.
     *
     * @param uniqueIDRef object of uniqueID.
     */
    public void setUniqueIDRef(Object uniqueIDRef) {
        this.uniqueIDRef = uniqueIDRef;
    }

    /**
     * Sets the value of unused slots to head node interface.
     *
     * @param unUsedSlots <true> if unused slots available, <false> otherwise.
     */
    public void setInterfaceUnUsedSlots(boolean unUsedSlots) {
        this.unUsedSlots = unUsedSlots;
    }

    /**
     * Updates the connected module list in project file.
     *
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void updateConnectedModuleList() throws JDOMException, IOException {
        OpenConfiguratorProjectUtils.updateConnectedModuleList(getNode(), this,
                moduleCollection);
    }

    /**
     * Updates the interface list in the project file.
     *
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public synchronized void updateInterfaceList()
            throws JDOMException, IOException {

        org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface interfaceObj = new org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface();
        interfaceObj.setId(getInterfaceUId());
        node.updateInterfaceValue(interfaceObj);
    }
}
