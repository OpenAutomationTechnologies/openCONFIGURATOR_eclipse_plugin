/*******************************************************************************
 * @file   AllowedValues.java
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

import java.util.ArrayList;
import java.util.List;

import org.epsg.openconfigurator.xmlbinding.xdd.TAllowedValues;
import org.epsg.openconfigurator.xmlbinding.xdd.TAllowedValuesTemplate;
import org.epsg.openconfigurator.xmlbinding.xdd.TRange;
import org.epsg.openconfigurator.xmlbinding.xdd.TValue;

/**
 *
 * @author Ramakrishnan P
 *
 */
public class AllowedValues {

    private List<String> valuesList = new ArrayList<>();
    private List<Range> rangeList = new ArrayList<>();

    public AllowedValues(TAllowedValues allowedValuesModel) {
        if (allowedValuesModel != null) {
            Object templateIdRef = allowedValuesModel.getTemplateIDRef();
            if (templateIdRef == null) {
                addRangeList(allowedValuesModel.getRange());
                addValueList(allowedValuesModel.getValue());
            } else {
                if (templateIdRef instanceof TAllowedValuesTemplate) {
                    TAllowedValuesTemplate allowedValuesTemplate = (TAllowedValuesTemplate) templateIdRef;
                    addRangeList(allowedValuesTemplate.getRange());
                    addValueList(allowedValuesTemplate.getValue());
                } else {
                    System.err.println(
                            "Allowed values has undefined templateIdRef. Type:"
                                    + templateIdRef);
                }
            }
        } else {
            // ignore.
        }
    }

    private void addRangeList(List<TRange> rangeElements) {
        for (TRange rangeModel : rangeElements) {
            Range range = new Range(rangeModel);
            rangeList.add(range);
        }
    }

    private void addValueList(List<TValue> valueElements) {
        for (TValue valueModel : valueElements) {
            valuesList.add(valueModel.getValue());
        }
    }

    public List<Range> getRangeList() {
        return rangeList;
    }

    public List<String> getValuesList() {
        return valuesList;
    }
}
