/*******************************************************************************
 * @file   FirmwareManager.java
 *
 * @author Sree hari Vignesh, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2017, Kalycito Infotech Private Limited
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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.FirmwareList;
import org.epsg.openconfigurator.xmlbinding.projectfile.FirmwareList.Firmware;

/**
 * Wrapper class for the firmware file attached to node or module.
 *
 * @author Sree Hari
 *
 */
public class FirmwareManager {

    private Node node;

    private Module module;

    private IProject project;

    private IFile projectXml;

    private Firmware firmwareObjModel;

    private org.epsg.openconfigurator.xmlbinding.firmware.Firmware firmwareXddModel;

    private List<FirmwareList.Firmware> firmwareList = new ArrayList<>();

    private List<Integer> firmwarefileVerList = new ArrayList<>();

    private int firmwarefileVersion;

    private Object nodeOrModuleObject;

    public FirmwareManager() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor to initialize firmware variables.
     *
     * @param nodeOrModule Instance of Node or Module.
     * @param firmwareXddModel Instance of firmware file header.
     * @param firmwareObjModel Project file instance of firmware file.
     */
    public FirmwareManager(Object nodeOrModule,
            org.epsg.openconfigurator.xmlbinding.firmware.Firmware firmwareXddModel,
            Firmware firmwareObjModel) {
        nodeOrModuleObject = nodeOrModule;
        if (nodeOrModule instanceof Node) {
            node = (Node) nodeOrModule;
            project = node.getProject();
            projectXml = node.getProjectXml();
        } else if (nodeOrModule instanceof Module) {
            module = (Module) nodeOrModule;
            project = module.getProject();
            projectXml = module.getNode().getProjectXml();
        }

        if (firmwareXddModel != null) {
            this.firmwareXddModel = firmwareXddModel;
            Charset charset = Charset.forName("UTF-8");
            byte[] devRev;
            devRev = String.valueOf(firmwareXddModel.getVar())
                    .getBytes(charset);
            firmwareObjModel.setDeviceRevision(devRev);

            byte[] prodNum = String.valueOf(firmwareXddModel.getDev())
                    .getBytes(charset);
            firmwareObjModel.setProductNumber(prodNum);

            byte[] applDate = String.valueOf(firmwareXddModel.getApplSwDate())
                    .getBytes(charset);
            firmwareObjModel.setDate(applDate);

            byte[] applTime = String.valueOf(firmwareXddModel.getApplSwTime())
                    .getBytes(charset);
            firmwareObjModel.setTime(applTime);

            // TODO: To be updated in the future version
            firmwareObjModel.setLocked(false);

            boolean keepHeader = Boolean.valueOf(
                    String.valueOf(firmwareXddModel.getKeepXmlheader()));
            firmwareObjModel.setKeepHeader(keepHeader);

            this.firmwareObjModel = firmwareObjModel;
            firmwarefileVersion = firmwareXddModel.getVer().intValue();
            firmwareList.add(firmwareObjModel);
        } else {
            System.err.println("The firmware XDD model is empty!");
        }

    }

    /**
     * @return The value of date from firmware file header.
     */
    public String getApplSwDate() {
        String date = StringUtils.EMPTY;
        if (firmwareXddModel != null) {
            date = Long.toHexString(firmwareXddModel.getApplSwDate())
                    .toUpperCase();
            for (int count = date.length(); count < 8; count++) {
                date = "0" + date;
            }
        }
        return date;
    }

    /**
     * @return The value of time from firmware file header.
     */
    public String getApplSwTime() {
        String time = StringUtils.EMPTY;
        if (firmwareXddModel != null) {
            time = Long.toHexString(firmwareXddModel.getApplSwTime())
                    .toUpperCase();
            for (int count = time.length(); count < 8; count++) {
                time = "0" + time;
            }
        }
        return time;
    }

    /**
     * @return The revision number of firmware from firmware file.
     */
    public String getdevRevNumber() {
        String revNum = StringUtils.EMPTY;
        if (firmwareXddModel != null) {
            revNum = Long.toHexString(firmwareXddModel.getVar()).toUpperCase();
            for (int count = revNum.length(); count < 8; count++) {
                revNum = "0" + revNum;
            }
        }
        return revNum;
    }

    /**
     * @return The configuration file path of firmware.
     */
    public String getFirmwareConfigPath() {
        return firmwareObjModel.getURI();
    }

    /**
     * @return List of file version available in project file.
     */
    public List<Integer> getFirmwarefileVerList() {
        return firmwarefileVerList;
    }

    /**
     * @return Version of firmware file.
     */
    public int getFirmwarefileVersion() {
        return firmwareXddModel.getVer().intValue();
    }

    /**
     * @return List of firmware available in project file.
     */
    public List<FirmwareList.Firmware> getFirmwareList() {
        return firmwareList;
    }

    /**
     * @return Locked value of firmware manager
     */
    public String getLocked() {
        String lockedFirmware = "00";

        if (firmwareObjModel.isLocked()) {
            lockedFirmware = "01";
            return lockedFirmware;
        }

        return lockedFirmware;
    }

    /**
     * @return The instance of Module.
     */
    public Module getModule() {
        return module;
    }

    /**
     * @return The new name of firmware file based on naming convention
     */
    public String getNewFirmwareFileName() {
        String newFirmwareName = StringUtils.EMPTY;
        newFirmwareName = getVendorId() + "_" + getProductNumber() + "_"
                + getdevRevNumber();
        return newFirmwareName;
    }

    /**
     * @return The instance of Node.
     */
    public Node getNode() {
        return node;
    }

    /**
     * @return The nodeId value of node and module.
     */
    public String getNodeId() {
        String nodeId = StringUtils.EMPTY;
        if (nodeOrModuleObject instanceof Node) {
            Node node = (Node) nodeOrModuleObject;
            nodeId = node.getNodeIdString();
        } else if (nodeOrModuleObject instanceof Module) {
            Module module = (Module) nodeOrModuleObject;
            nodeId = module.getNode().getNodeIdString();
        }
        return nodeId;
    }

    /**
     * @return The value of firmware product number from firmware file.
     */
    public String getProductNumber() {
        String prodNum = StringUtils.EMPTY;
        if (firmwareXddModel != null) {
            prodNum = Long.toHexString(firmwareXddModel.getDev()).toUpperCase();
            for (int count = prodNum.length(); count < 8; count++) {
                prodNum = "0" + prodNum;
            }
        }
        return prodNum;
    }

    /**
     * @return Instance of POWERLINK project.
     */
    public IProject getProject() {
        return project;
    }

    /**
     * @return Instance of project XML file.
     */
    public IFile getProjectXml() {
        return projectXml;
    }

    /**
     * @return The path of firmware file attached to node or module.
     */
    public String getUri() {
        return firmwareObjModel.getURI();
    }

    /**
     * @return Value of vendor ID from node or module.
     */
    public String getVendorId() {
        String vendorId = StringUtils.EMPTY;
        if (node != null) {
            vendorId = node.getVendorIdValue();
        } else {
            vendorId = module.getVenIdValue();
        }
        if (vendorId.contains("x")) {
            vendorId = vendorId.substring(2);
        }
        for (int count = vendorId.length(); count < 8; count++) {
            vendorId = "0" + vendorId;
        }
        return vendorId;
    }

    /**
     * @return <code>True</code> if XML header value is true, <code>False</code>
     *         otherwise
     */
    public boolean isKeepXmlHeader() {
        return firmwareObjModel.isKeepHeader();
    }

    /**
     * Sets the path of firmware file attached to node or module.
     *
     * @param relativePath The value of firmware file path.
     */
    public void setUri(String relativePath) {
        firmwareObjModel.setURI(relativePath);
    }

}
