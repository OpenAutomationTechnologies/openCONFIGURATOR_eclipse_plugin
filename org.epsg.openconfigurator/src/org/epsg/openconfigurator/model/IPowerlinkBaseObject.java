/*******************************************************************************
 * @file   IPowerlinkBaseObject.java
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

import org.eclipse.core.resources.IProject;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectAccessType;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectPDOMapping;
import org.jdom2.JDOMException;

/**
 * POWERLINK objects values and properties
 *
 * @author Ramakrishnan P
 *
 */
public interface IPowerlinkBaseObject {
    /**
     * @return Access type of POWERLINK object
     */
    public TObjectAccessType getAccessType();

    /**
     * @return Actual and default value of POWERLINK object
     */
    public String getActualDefaultValue();

    /**
     * @return Actual value of POWERLINK object
     */
    public String getActualValue();

    /**
     * @return Error string with respect to error in configuration aspect of an
     *         POWERLINK object
     */
    public String getConfigurationError();

    /**
     * @return Data type of POWERLINK object in a string readable format.
     */
    public String getDataTypeReadable();

    /**
     * @return Default value of POWERLINK object
     */
    public String getDefaultValue();

    /**
     * @return The highest value limit of a POWERLINK object
     */
    public String getHighLimit();

    /**
     * @return Id of POWERLINK object in hexadecimal format
     */
    public String getIdHex();

    /**
     * @return Object ID in hex without 0x.
     */
    public String getIdRaw();

    /**
     * @return The lowest value limit of POWERLINK object
     */
    public String getLowLimit();

    /**
     * @return Object model from the XDC
     */
    public Object getModel();

    /**
     * @return Name of POWERLINK object
     */
    public String getName();

    /**
     * @return Name and Id of POWERLINK object.
     */
    public String getNameWithId();

    /**
     * @return Name of the Project or network Id of POWERLINK network
     */
    public String getNetworkId();

    /**
     * @return Instance of Node with respect to POWERLINK object.
     */
    public Node getNode();

    /**
     * @return Id of node with respect to POWERLINK object.
     */
    public short getNodeId();

    /**
     * @return DataType of the given object.
     */
    public short getObjectType();

    /**
     * @return PDO mapping objects
     */
    public TObjectPDOMapping getPdoMapping();

    /**
     * @return current POWERLINK network project.
     */
    public IProject getProject();

    /**
     * @return Unique ID reference of POWERLINK object from XDC.
     */
    public Object getUniqueIDRef();

    /**
     * @return XPath to find POWERLINK object in the XDC.
     */
    public String getXpath();

    /**
     * @return <code>True</code> if POWERLINK object is forced,
     *         <code>False</code> if not forced.
     */
    public boolean isObjectForced();

    /**
     * @return <code>True</code> if POWERLINK object is mappable to RPDO
     *         channel, <code>False</code> if not mappable.
     */
    public boolean isRpdoMappable();

    /**
     * @return <code>True</code> if POWERLINK object is mappable to TPDO
     *         channel, <code>False</code> if not mappable.
     */
    public boolean isTpdoMappable();

    /**
     * Updates the actual value of POWERLINK object into the XDC file.
     *
     * @param actualValue The value to be updated into the XDC
     * @param writeToXdc Permission check to write into the file.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setActualValue(final String actualValue, boolean writeToXdc)
            throws JDOMException, IOException;

    /**
     * Updates the error flag based on error identified from configuration of
     * POWERLINK object
     *
     * @param errorMessage Message description of error
     */
    public void setError(final String errorMessage);
}
