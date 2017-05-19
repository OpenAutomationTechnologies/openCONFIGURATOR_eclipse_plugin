/*******************************************************************************
 * @file   FirmwareFile.java
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.epsg.openconfigurator.xmlbinding.xdd.Firmware;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularChild;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.TDeviceFunction;
import org.epsg.openconfigurator.xmlbinding.xdd.TDeviceIdentity;
import org.epsg.openconfigurator.xmlbinding.xdd.TFirmwareList;
import org.epsg.openconfigurator.xmlbinding.xdd.TModularChildDeviceFunction;
import org.epsg.openconfigurator.xmlbinding.xdd.TModularHeadDeviceFunction;
import org.epsg.openconfigurator.xmlbinding.xdd.TVersion;

/**
 * FirmwareFile class to retrieve the values of firmware list element available
 * in XDD.
 *
 * @author Sree hari Vignesh
 *
 */
public class FirmwareFile {

    private TFirmwareList firmwareList;

    private List<String> uriList = new ArrayList<>();

    private List<BigInteger> deviceRevNoList = new ArrayList<>();

    private List<XMLGregorianCalendar> buildDateList = new ArrayList<>();

    private List<Object> firmwareLabelList = new ArrayList<>();

    private String vendorID;

    private String productId;

    private String deviceRevision;

    /**
     * Constructor that defines the list of firmware from the XDD model.
     *
     * @param xddModel Instance of XDD.
     */
    public FirmwareFile(ISO15745ProfileContainer xddModel) {
        setXddModel(xddModel);
    }

    /**
     * @return List of build date value in XDD.
     */
    public List<XMLGregorianCalendar> getBuildDateList() {
        return buildDateList;
    }

    /**
     * @return List of Device revision number from firmware list in XDD.
     */
    public List<BigInteger> getDeviceRevNoList() {
        return deviceRevNoList;
    }

    /**
     * @return List of firmware labels from firmware list in XDD.
     */
    public List<Object> getFirmwareLabelList() {
        return firmwareLabelList;
    }

    /**
     * @return List of firmware list in XDD.
     */
    public TFirmwareList getFirmwareList() {
        return firmwareList;
    }

    /**
     * @return The value of device revision in the XDD.
     */
    public String getModuleDeviceRevision() {
        return deviceRevision;
    }

    /**
     * @return The product number value of modular child node.
     */
    public String getModuleProductId() {
        return productId;
    }

    /**
     * @return The vendor ID value of modular child node.
     */
    public String getModuleVendorID() {
        return vendorID;
    }

    /**
     * @return List of URI from firmware list in XDD.
     */
    public List<String> getUriList() {
        return uriList;
    }

    /**
     * Updates the value of firmware list from XDD model.
     *
     * @param xddModel XDD instance.
     */
    private void setXddModel(ISO15745ProfileContainer xddModel) {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBodyDatatype = profile
                        .getProfileBody();

                if (profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularHead) {
                    ProfileBodyDevicePowerlinkModularHead devProfile = (ProfileBodyDevicePowerlinkModularHead) profileBodyDatatype;
                    List<TModularHeadDeviceFunction> deviceFuncList = devProfile
                            .getDeviceFunction();
                    for (TModularHeadDeviceFunction modularNodeDeviceFunc : deviceFuncList) {
                        if (modularNodeDeviceFunc.getFirmwareList() != null) {
                            firmwareList = modularNodeDeviceFunc
                                    .getFirmwareList();
                            for (Firmware firmwareOfXdd : firmwareList
                                    .getFirmware()) {
                                uriList.add(firmwareOfXdd.getURI());
                                deviceRevNoList.add(firmwareOfXdd
                                        .getDeviceRevisionNumber());
                                buildDateList.add(firmwareOfXdd.getBuildDate());
                                firmwareLabelList.add(firmwareOfXdd
                                        .getLabelOrDescriptionOrLabelRef());
                            }
                        } else {
                            System.err.println(
                                    "The firmware list not available in the XDD.");
                        }
                    }
                } else if (profileBodyDatatype instanceof ProfileBodyDevicePowerlink) {
                    ProfileBodyDevicePowerlink devProfile = (ProfileBodyDevicePowerlink) profileBodyDatatype;
                    List<TDeviceFunction> deviceFuncList = devProfile
                            .getDeviceFunction();
                    for (TDeviceFunction nodeDeviceFunc : deviceFuncList) {
                        if (nodeDeviceFunc.getFirmwareList() != null) {
                            firmwareList = nodeDeviceFunc.getFirmwareList();
                            for (Firmware firmwareOfXdd : firmwareList
                                    .getFirmware()) {
                                uriList.add(firmwareOfXdd.getURI());
                                deviceRevNoList.add(firmwareOfXdd
                                        .getDeviceRevisionNumber());
                                buildDateList.add(firmwareOfXdd.getBuildDate());
                                firmwareLabelList.add(firmwareOfXdd
                                        .getLabelOrDescriptionOrLabelRef());
                            }
                        } else {
                            System.err.println(
                                    "The firmware list not available in the XDD of node.");
                        }
                    }
                } else if (profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularChild) {
                    ProfileBodyDevicePowerlinkModularChild devProfile = (ProfileBodyDevicePowerlinkModularChild) profileBodyDatatype;
                    List<TModularChildDeviceFunction> deviceFuncList = devProfile
                            .getDeviceFunction();
                    for (TModularChildDeviceFunction moduleDevFunc : deviceFuncList) {
                        if (moduleDevFunc.getFirmwareList() != null) {
                            firmwareList = moduleDevFunc.getFirmwareList();
                            for (Firmware firmwareOfXdd : firmwareList
                                    .getFirmware()) {
                                uriList.add(firmwareOfXdd.getURI());
                                deviceRevNoList.add(firmwareOfXdd
                                        .getDeviceRevisionNumber());
                                buildDateList.add(firmwareOfXdd.getBuildDate());
                                firmwareLabelList.add(firmwareOfXdd
                                        .getLabelOrDescriptionOrLabelRef());
                            }
                        } else {
                            System.err.println(
                                    "The firmware list not available in the XDD of module.");
                        }
                    }

                    TDeviceIdentity deviceIdentity = devProfile
                            .getDeviceIdentity();
                    vendorID = deviceIdentity.getVendorID().getValue();
                    productId = deviceIdentity.getProductID().getValue();
                    for (TVersion ver : deviceIdentity.getVersion()) {
                        if (ver.getVersionType().equalsIgnoreCase("HW")) {
                            deviceRevision = ver.getValue();
                        } else {
                            deviceRevision = StringUtils.EMPTY;
                        }
                    }

                }
            }
        }

    }
}
