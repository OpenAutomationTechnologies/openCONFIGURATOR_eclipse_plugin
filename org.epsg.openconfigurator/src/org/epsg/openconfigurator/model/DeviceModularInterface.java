/*******************************************************************************
 * @file   DeviceModularInterface.java
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.epsg.openconfigurator.xmlbinding.xdd.FileList;
import org.epsg.openconfigurator.xmlbinding.xdd.ModuleTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleAddressingChild;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleInterface;

/**
 * Describes the module interface of child modules.
 *
 * @author SreeHari
 *
 */
public class DeviceModularInterface {

    private Node node;

    private List<Object> labellist = new ArrayList<>();

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

    /**
     * Constructor that describes the module interface of modules from the XDD.
     *
     * @param node Instance of Node.
     * @param moduleInterfaceModel XDD instance of ModuleInterface.
     */
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

    /**
     * @return The Module ID.
     */
    public String getChildID() {
        return childID;
    }

    /**
     * @return The list of file from the Module XDD.
     */
    public FileList getFileList() {
        return fileList;
    }

    /**
     * @return The list of labels for module.
     */
    public List<Object> getLabellist() {
        return labellist;
    }

    /**
     * @return The maximum address value of module.
     */
    public BigInteger getMaxAddress() {
        return maxAddress;
    }

    /**
     * @return The maximum count of modules.
     */
    public BigInteger getMaxCount() {
        return maxCount;
    }

    /**
     * @return The maximum position of modules.
     */
    public BigInteger getMaxPosition() {
        return maxPosition;
    }

    /**
     * @return The minimum address value of module.
     */
    public BigInteger getMinAddress() {
        return minAddress;
    }

    /**
     * @return The minimum position of module.
     */
    public BigInteger getMinPosition() {
        return minPosition;
    }

    /**
     * @return The instance of TModuleAddressingHead.
     */
    public TModuleAddressingChild getModuleAddressing() {
        return moduleAddressing;
    }

    /**
     * @return The list of module type to be connected to it.
     */
    public ModuleTypeList getModuleTypeList() {
        return moduleTypeList;
    }

    /**
     * @return Instance of Node.
     */
    public Node getNode() {
        return node;
    }

    /**
     * @return The type of module.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the module ID to module.
     *
     * @param childID ID to be inserted.
     */
    public void setChildID(String childID) {
        this.childID = childID;
    }

    /**
     * Sets the file list to module.
     *
     * @param fileList List of file to be inserted.
     */
    public void setFileList(FileList fileList) {
        this.fileList = fileList;
    }

    /**
     * Sets the label list to module.
     *
     * @param labellist List of labels to be inserted.
     */
    public void setLabellist(List<Object> labellist) {
        this.labellist = labellist;
    }

    /**
     * Sets the maximum address to the module.
     *
     * @param maxAddress The value of address to be modified.
     */
    public void setMaxAddress(BigInteger maxAddress) {
        this.maxAddress = maxAddress;
    }

    /**
     * Sets the maximum count to the module.
     *
     * @param maxCount The value of count to be modified.
     */
    public void setMaxCount(BigInteger maxCount) {
        this.maxCount = maxCount;
    }

    /**
     * Sets the maximum position to the module.
     *
     * @param maxPosition The value of position to be modified.
     */
    public void setMaxPosition(BigInteger maxPosition) {
        this.maxPosition = maxPosition;
    }

    /**
     * Sets the minimum address to the module.
     *
     * @param minAddress The value of address to be modified.
     */
    public void setMinAddress(BigInteger minAddress) {
        this.minAddress = minAddress;
    }

    /**
     * Sets the minimum position to the module.
     *
     * @param minPosition The value of position to be modified.
     */
    public void setMinPosition(BigInteger minPosition) {
        this.minPosition = minPosition;
    }

    /**
     * Sets the addressing of module instance from XDD instance.
     *
     * @param moduleAddressing Instance of TModuleAddressingHead.
     */
    public void setModuleAddressing(TModuleAddressingChild moduleAddressing) {
        this.moduleAddressing = moduleAddressing;
    }

    /**
     * Sets the type of Module to be connected.
     *
     * @param moduleTypeList Instance of ModuletypeList.
     */
    public void setModuleTypeList(ModuleTypeList moduleTypeList) {
        this.moduleTypeList = moduleTypeList;
    }

    /**
     * Sets the Instance to Node.
     *
     * @param node Instance of Node.
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * Sets the type of Module.
     *
     * @param type The type of module.
     */
    public void setType(String type) {
        this.type = type;
    }
}
