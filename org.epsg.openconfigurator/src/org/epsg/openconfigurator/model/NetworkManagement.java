/*******************************************************************************
 * @file   NetworkManagement.java
 *
 * @author Sree hari Vignesh, Kalycito Infotech Private Limited.
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

import java.util.List;

import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlinkModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.TCNFeatures;
import org.epsg.openconfigurator.xmlbinding.xdd.TGeneralFeatures;
import org.epsg.openconfigurator.xmlbinding.xdd.TMNFeatures;
import org.epsg.openconfigurator.xmlbinding.xdd.TNetworkManagement;

/**
 * NetworkManagement class for the features available in the Network management
 * attribute of XDD/XDC file.
 *
 * @author SreeHari
 *
 */
public class NetworkManagement {

    private TGeneralFeatures generalFeatures;
    private TMNFeatures mnFeatures;
    private TCNFeatures cnFeatures;

    private Node node;

    /**
     * Constructor to set the value of node and XDD model.
     *
     * @param node The instance of Node model.
     * @param xddModel The instance of XDD model.
     */
    public NetworkManagement(Node node, ISO15745ProfileContainer xddModel) {
        this.node = node;
        setXddModel(xddModel);
    }

    /**
     * @return Instance of TCNFeatures.
     */
    public TCNFeatures getCnFeaturesOfNode() {
        return cnFeatures;
    }

    /**
     * @return Instance of TGeneralFeatures.
     */
    public TGeneralFeatures getGeneralFeatures() {
        return generalFeatures;
    }

    /**
     * @return Instance of TMNFeatures.
     */
    public TMNFeatures getMnFeaturesOfNode() {
        return mnFeatures;
    }

    /**
     * @return Instance of Node.
     */
    public Node getNode() {
        return node;
    }

    private void setXddModel(ISO15745ProfileContainer xddModel) {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBodyDatatype = profile
                        .getProfileBody();
                if (profileBodyDatatype instanceof ProfileBodyCommunicationNetworkPowerlink) {
                    ProfileBodyCommunicationNetworkPowerlink commProfile = (ProfileBodyCommunicationNetworkPowerlink) profileBodyDatatype;
                    TNetworkManagement nmtMgmt = commProfile
                            .getNetworkManagement();

                    if (nmtMgmt != null) {
                        generalFeatures = nmtMgmt.getGeneralFeatures();
                        mnFeatures = nmtMgmt.getMNFeatures();
                        cnFeatures = nmtMgmt.getCNFeatures();
                        // unused right now.
                        // diagnositc = nmtMgmt.getDiagnostic();
                        // deviceComissioning =
                        // nmtMgmt.getDeviceCommissioning();
                    }
                    // XDD model for modular head node
                } else if (profileBodyDatatype instanceof ProfileBodyCommunicationNetworkPowerlinkModularHead) {
                    ProfileBodyCommunicationNetworkPowerlinkModularHead commProfile = (ProfileBodyCommunicationNetworkPowerlinkModularHead) profileBodyDatatype;
                    TNetworkManagement nmtMgmt = commProfile
                            .getNetworkManagement();

                    if (nmtMgmt != null) {
                        generalFeatures = nmtMgmt.getGeneralFeatures();
                        mnFeatures = nmtMgmt.getMNFeatures();
                        cnFeatures = nmtMgmt.getCNFeatures();
                        // unused right now.
                        // diagnositc = nmtMgmt.getDiagnostic();
                        // deviceComissioning =
                        // nmtMgmt.getDeviceCommissioning();
                    }
                } else {
                    System.out.println("ERROR unhandled Profile body datatype"
                            + profileBodyDatatype);
                }
            }
        }
    }
}
