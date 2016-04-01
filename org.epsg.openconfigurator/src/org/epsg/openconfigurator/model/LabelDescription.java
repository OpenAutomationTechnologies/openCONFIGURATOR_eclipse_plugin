/*******************************************************************************
 * @file    LabelDescription.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.epsg.openconfigurator.xmlbinding.xdd.Connector;

/**
 * Class to define the label values of parameter.
 *
 * @author Ramakrishnan P
 *
 */
public class LabelDescription {
    static String displayLang = Locale.getDefault().getDisplayLanguage();

    private HashMap<String, String> labelMap = new HashMap<String, String>();
    private HashMap<String, String> descriptionMap = new HashMap<String, String>();

    /**
     * . Constructor that define the label values of parameter.
     *
     * @param labelDescriptionList list of description availble from the XDD
     *            model.
     */
    public LabelDescription(List<Object> labelDescriptionList) {
        if (labelDescriptionList == null) {
            return;
        }

        for (Object obj : labelDescriptionList) {
            if (obj instanceof Connector.Label) {
                Connector.Label lbl = (Connector.Label) obj;
                if (lbl.getValue() != null) {
                    labelMap.put(lbl.getLang(), lbl.getValue());
                }
            } else if (obj instanceof Connector.Description) {
                Connector.Description desc = (Connector.Description) obj;
                if (desc.getValue() != null) {
                    descriptionMap.put(desc.getLang(), desc.getValue());
                }
            } else if (obj instanceof Connector.LabelRef) {
                // FIXME;
            } else if (obj instanceof Connector.DescriptionRef) {
                // FIXME;
            }
        }
    }

    /**
     * @return The label description of parameter.
     */
    public String getDescription() {
        if (descriptionMap.containsKey(displayLang)) {
            return descriptionMap.get(displayLang);
        } else {
            return descriptionMap.getOrDefault("en", "");
        }
    }

    /**
     * @return Label name of parameter.
     */
    public String getLabel() {
        if (labelMap.containsKey(displayLang)) {
            return labelMap.get(displayLang);
        } else {
            return labelMap.getOrDefault("en", "");
        }
    }

    /**
     * @return The name of parameter from the XDD model.
     */
    public String getText() {
        String label = getLabel();
        String desc = getDescription();
        if ((label != null) && !label.isEmpty()) {
            return label;
        } else if ((desc != null) && !desc.isEmpty()) {
            return desc;
        } else {
            return "";
        }

    }
}
