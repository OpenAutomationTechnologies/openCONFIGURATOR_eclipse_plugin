/*******************************************************************************
 * @file   ProcessImage.java
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

import java.util.List;

import org.epsg.openconfigurator.xmlbinding.xap.ApplicationProcess;

/**
 * Wrapper class of XAP XML file
 *
 * @author Sree Hari
 *
 */
public class ProcessImage {

    private Node node;
    private ApplicationProcess processImg;

    private List<org.epsg.openconfigurator.xmlbinding.xap.ProcessImage> processImageList;

    /**
     * Constructor to initialize process Image variables
     *
     * @param node Instance of node
     * @param xapFile XML instance of XAP file
     */
    public ProcessImage(Node node, ApplicationProcess xapFile) {
        this.node = node;
        processImg = xapFile;
        if (processImg != null) {
            processImageList = processImg.getProcessImage();
        } else {
            processImageList = null;
        }
    }

    /**
     * @return Instance of XAP file
     */
    public ApplicationProcess getAppProcess() {
        return processImg;
    }

    /**
     * @return Instance of Node
     */
    public Node getNode() {
        return node;
    }

    /**
     * @return List of process image available in the XAP file
     */
    public List<org.epsg.openconfigurator.xmlbinding.xap.ProcessImage> getProcessImageList() {
        return processImageList;
    }

    /**
     * Sets the instance of XAP file
     *
     * @param appProcess XML instance of application process
     */
    public void setAppProcess(ApplicationProcess appProcess) {
        processImg = appProcess;
    }

    /**
     * Sets the Node instance
     *
     * @param node Instance of Node
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * Sets the list of available process image elements
     *
     * @param processImageList List of process image available in XAP file
     */
    public void setProcessImageList(
            List<org.epsg.openconfigurator.xmlbinding.xap.ProcessImage> processImageList) {
        this.processImageList = processImageList;
    }

}
