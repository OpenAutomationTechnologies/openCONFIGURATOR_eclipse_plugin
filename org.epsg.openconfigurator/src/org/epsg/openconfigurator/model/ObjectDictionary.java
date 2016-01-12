/*******************************************************************************
 * @file   ObjectDictionary.java
 *
 * @author Ramakrishnan Periyakaruppan, Kalycito Infotech Private Limited.
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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.TApplicationLayers;
import org.epsg.openconfigurator.xmlbinding.xdd.TApplicationProcess;
import org.epsg.openconfigurator.xmlbinding.xdd.TObject;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList;
import org.jdom2.JDOMException;

/**
 * Wrapper class for objects and sub-objects of a node. Lists the objects based
 * on various parameters and retrieves or updates the value of objects or
 * sub-objects.
 *
 * @author Ramakrishnan P
 *
 */
public class ObjectDictionary {

    /**
     * Instance of Node.
     */
    private final Node node;

    /**
     * List of Objects available in the node.
     */
    private final List<PowerlinkObject> objectsList = new ArrayList<PowerlinkObject>();

    /**
     * TPDO mappable objects list.
     */
    private final List<PowerlinkObject> tpdoMappableObjectList = new ArrayList<PowerlinkObject>();

    /**
     * RPDO mappable objects list
     */
    private final List<PowerlinkObject> rpdoMappableObjectList = new ArrayList<PowerlinkObject>();

    /**
     * TPDO channels list.
     */
    private final List<TpdoChannel> tpdoChannelsList = new ArrayList<TpdoChannel>();

    /**
     * RPDO channels list.
     */
    private final List<RpdoChannel> rpdoChannelsList = new ArrayList<RpdoChannel>();
    private List<Parameter> parameterList = new ArrayList<Parameter>();

    /**
     * Constructs object dictionary with following inputs.
     *
     * @param node The instance of node.
     * @param xddModelArg The instance of XDD/XDC file.
     */
    public ObjectDictionary(Node node, ISO15745ProfileContainer xddModelArg) {
        this.node = node;
        setXddModel(xddModelArg);
    }

    /**
     * Get the actual value for the given object id.
     *
     * @param objectId The ID of object to get the actual value.
     * @return ActualValue of object.
     */
    public String getActualValue(final long objectId) {
        PowerlinkObject object = getObject(objectId);
        System.out.println("Object id =" + objectId);
        if (object == null) {
            throw new RuntimeException(
                    "Object 0x" + Long.toHexString(objectId) + " not found!");
        }

        return object.getActualValue();
    }

    /**
     * Get the actual value for the given sub object ID.
     *
     * @param objectId The ID of object to get the actual value.
     * @param subObjectId The ID of sub-object to get the actual value.
     * @return ActualValue of sub-object.
     */
    public String getActualValue(final long objectId, final short subObjectId) {
        PowerlinkSubobject subObject = getSubObject(objectId, subObjectId);
        if (subObject == null) {
            throw new RuntimeException(
                    "SubObject 0x" + Long.toHexString(objectId) + "/0x"
                            + Integer.toHexString(subObjectId) + " not found!");
        }

        return subObject.getActualValue();
    }

    /**
     * @return Instance of node.
     */
    public Node getNode() {
        return node;
    }

    /**
     * Returns the object from the given byte[] type object ID.
     *
     * @param objectId The ID of an object in arrays of bytes.
     * @return Object from the object ID.
     */
    public PowerlinkObject getObject(final byte[] objectId) {
        if (objectId == null) {
            return null;
        }

        String objectIdRaw = DatatypeConverter.printHexBinary(objectId);
        long objectIdL = Long.parseLong(objectIdRaw, 16);
        return getObject(objectIdL);
    }

    /**
     * Returns the object from the given long type object ID.
     *
     * @param objectId The ID of the object in long data type.
     * @return Object from the object ID.
     */
    public PowerlinkObject getObject(final long objectId) {
        for (PowerlinkObject obj : getObjectsList()) {
            if (obj.getId() == objectId) {
                return obj;
            }
        }
        return null;
    }

    /**
     * @return List of objects available in the node.
     */
    public List<PowerlinkObject> getObjectsList() {
        return objectsList;
    }

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    /**
     * @return List of RPDOChannels available in the node.
     */
    public List<RpdoChannel> getRpdoChannelsList() {
        return rpdoChannelsList;
    }

    /**
     * @return List of RPDOMappableObjects available in the node.
     */
    public List<PowerlinkObject> getRpdoMappableObjectList() {
        return rpdoMappableObjectList;
    }

    /**
     * Returns the sub object from the given object and sub object ID.
     *
     * @param objectId The ID of an object in arrays of bytes.
     * @param subObjectId The ID of an sub object in arrays of bytes.
     * @return SubObject from available inputs.
     */
    public PowerlinkSubobject getSubObject(final byte[] objectId,
            final byte[] subObjectId) {
        PowerlinkObject object = getObject(objectId);
        if ((object == null) || (subObjectId == null)) {
            return null;
        }

        return object.getSubObject(subObjectId);
    }

    /**
     * Returns the sub object from the given object and sub object ID.
     *
     * @param objectId Complete ID of the Object.
     * @param subObjectId Complete ID of the sub Object
     * @return SubObject from available inputs.
     */
    public PowerlinkSubobject getSubObject(final long objectId,
            final short subObjectId) {
        PowerlinkObject obj = getObject(objectId);
        if (obj == null) {
            return null;
        }
        return obj.getSubObject(subObjectId);
    }

    /**
     * @return List of TPDOchannels from the available node.
     */
    public List<TpdoChannel> getTpdoChannelsList() {
        return tpdoChannelsList;
    }

    /**
     * @return List of TPDOmappable objects available in the node.
     */
    public List<PowerlinkObject> getTpdoMappableObjectList() {
        return tpdoMappableObjectList;
    }

    /**
     * Returns actual or default value of object from the given object ID.
     *
     * @param objectId Complete ID of the Object.
     * @return Actual or default value of the object.
     */
    public String getValue(final long objectId) {
        PowerlinkObject object = getObject(objectId);
        if (object == null) {
            throw new RuntimeException(
                    "Object 0x" + Long.toHexString(objectId) + " not found!");
        }

        return object.getActualDefaultValue();
    }

    /**
     * Returns actual or default value of sub object from the given object and
     * sub object ID.
     *
     * @param objectId Complete ID of the Object.
     * @param subObjectId Complete ID of the sub object.
     * @return Actual or default value of the sub-object.
     */
    public String getValue(final long objectId, final short subObjectId) {
        PowerlinkSubobject subObject = getSubObject(objectId, subObjectId);
        if (subObject == null) {
            throw new RuntimeException(
                    "SubObject 0x" + Long.toHexString(objectId) + "/0x"
                            + Integer.toHexString(subObjectId) + " not found!");
        }

        return subObject.getActualDefaultValue();
    }

    /**
     * Sets the given actual value to subObject of given object and sub object
     * ID.
     *
     * @param objectId Complete ID of the Object.
     * @param subObjectId Complete ID of the sub object.
     * @param actualValue Actual value of the sub-object
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setActualValue(final long objectId, final short subObjectId,
            final String actualValue) throws JDOMException, IOException {
        PowerlinkSubobject subObject = getSubObject(objectId, subObjectId);
        if (subObject == null) {
            throw new RuntimeException(
                    "SubObject 0x" + Long.toHexString(objectId) + "/0x"
                            + Integer.toHexString(subObjectId) + " not found!");
        }

        subObject.setActualValue(actualValue, true);
    }

    /**
     * Sets the given actual value to the object of given object ID.
     *
     * @param objectId Complete ID of the Object.
     * @param actualValue Actual value of the sub-object
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setActualValue(final long objectId, final String actualValue)
            throws JDOMException, IOException {
        PowerlinkObject object = getObject(objectId);
        if (object == null) {
            throw new RuntimeException(
                    "Object 0x" + Long.toHexString(objectId) + " not found!");
        }

        object.setActualValue(actualValue, true);
    }

    /**
     * Sets XDD/XDC model from the available inputs.
     *
     * @param xddModel The instance of XDD/XDC file.
     */
    public void setXddModel(ISO15745ProfileContainer xddModel) {

        objectsList.clear();
        rpdoMappableObjectList.clear();
        tpdoMappableObjectList.clear();
        rpdoChannelsList.clear();
        tpdoChannelsList.clear();

        if (xddModel != null) {

            List<PowerlinkObject> commParamObjList = new ArrayList<PowerlinkObject>();
            List<PowerlinkObject> mapParamObjList = new ArrayList<PowerlinkObject>();

            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBodyDatatype = profile
                        .getProfileBody();
                if (profileBodyDatatype instanceof ProfileBodyDevicePowerlink) {
                    ProfileBodyDevicePowerlink devProfile = (ProfileBodyDevicePowerlink) profileBodyDatatype;
                    List<TApplicationProcess> appProcessList = devProfile
                            .getApplicationProcess();
                    for (TApplicationProcess appProcess : appProcessList) {
                        TParameterList paramList = appProcess
                                .getParameterList();
                        if (paramList == null) {
                            continue;
                        }

                        List<TParameterList.Parameter> parameterModelList = paramList
                                .getParameter();
                        for (TParameterList.Parameter param : parameterModelList) {
                            parameterList.add(new Parameter(param));
                        }
                    }
                }

                if (profileBodyDatatype instanceof ProfileBodyCommunicationNetworkPowerlink) {
                    ProfileBodyCommunicationNetworkPowerlink commProfile = (ProfileBodyCommunicationNetworkPowerlink) profileBodyDatatype;
                    TApplicationLayers.ObjectList tempObjectLists = commProfile
                            .getApplicationLayers().getObjectList();
                    List<TObject> objList = tempObjectLists.getObject();
                    for (TObject obj : objList) {
                        PowerlinkObject plkObj = new PowerlinkObject(node, obj);
                        objectsList.add(plkObj);

                        if (plkObj.getIdHex().startsWith("0x14")
                                || plkObj.getIdHex().startsWith("0x18")) {
                            commParamObjList.add(plkObj);
                        } else if (plkObj.getIdHex().startsWith("0x16")
                                || plkObj.getIdHex().startsWith("0x1A")) {
                            mapParamObjList.add(plkObj);
                        }

                        if (plkObj.hasRpdoMappableSubObjects()
                                || plkObj.isRpdoMappable()) {
                            rpdoMappableObjectList.add(plkObj);
                        }

                        if (plkObj.hasTpdoMappableSubObjects()
                                || plkObj.isTpdoMappable()) {
                            tpdoMappableObjectList.add(plkObj);
                        }
                    }
                }
            }

            for (int cnt = 0; cnt < commParamObjList.size(); cnt++) {
                try {
                    PowerlinkObject commParam = commParamObjList.get(cnt);
                    PowerlinkObject mapParam = mapParamObjList.get(cnt);

                    char mapParamId = mapParam.getIdHex().charAt(3);

                    if (mapParamId == '6') {
                        rpdoChannelsList.add(
                                new RpdoChannel(node, commParam, mapParam));
                    } else if ((mapParamId == 'A') || (mapParamId == 'a')) {
                        tpdoChannelsList.add(
                                new TpdoChannel(node, commParam, mapParam));
                    } else {
                        System.err.println("Invalid PDO detected!");
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
