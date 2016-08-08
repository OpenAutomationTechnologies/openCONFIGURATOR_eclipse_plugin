/*******************************************************************************
 * @file   Range.java
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

import org.epsg.openconfigurator.xmlbinding.xdd.TRange;

/**
 * Class that define the range values of parameter or parameter template.
 *
 * @author Ramakrishnan P
 *
 */
public class Range {
    private String minValue;
    private String maxValue;
    private String step;
    private TRange range;

    /**
     * Constructor of range to define the maximum and minimum value of
     * parameter.
     *
     * @param range The XDD model instance of TRange.
     */
    public Range(TRange range) {
        this.range = range;
        maxValue = range.getMaxValue().getValue();
        minValue = range.getMinValue().getValue();
    }

    /**
     * @return The maximum value of range given in the parameter.
     */
    public String getMaxValue() {
        return maxValue;
    }

    /**
     * @return The minimum value of range given in the parameter.
     */
    public String getMinValue() {
        return minValue;
    }

    /**
     * @return The step value of parameter.
     */
    public String getStep() {
        step = range.getStep().getValue();
        return step;
    }
}
