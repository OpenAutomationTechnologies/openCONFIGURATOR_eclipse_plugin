/*******************************************************************************
 * @file   VarDecleration.java
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

import java.math.BigInteger;

import org.epsg.openconfigurator.xmlbinding.xdd.TVarDeclaration;

/**
 * Class to identify and define the variables of var declaration
 *
 * @author Ramakrishnan P
 *
 */
public class VarDecleration {
    private String name;
    private String uniqueId;
    private BigInteger size;
    private String initialValue;
    private LabelDescription label;
    private DataTypeChoice dataType;

    /**
     * Constructor that define the variable values of var declaration
     *
     * @param vardecl XDD model instance of TVarDeclaration
     */
    public VarDecleration(TVarDeclaration vardecl) {
        if (vardecl != null) {
            name = vardecl.getName();
            uniqueId = vardecl.getUniqueID();
            size = vardecl.getSize();
            initialValue = vardecl.getInitialValue();
            label = new LabelDescription(
                    vardecl.getLabelOrDescriptionOrLabelRef());
            dataType = new DataTypeChoice(vardecl);
        } else {
            // ignore
        }
    }

    /**
     * @return The datatype from the data type choice
     */
    public DataTypeChoice getDataType() {
        return dataType;
    }

    /**
     * @return The initial value of var declaration variables.
     */
    public String getInitialValue() {
        return initialValue;
    }

    /**
     * @return The label description of variables
     */
    public LabelDescription getLabelDescription() {
        return label;
    }

    /**
     * @return The name of variables
     */
    public String getName() {
        return name;
    }

    /**
     * @return The size of variables listed in the var declaration
     */
    public long getSize() {
        if (size != null) {
            return size.longValue();
        }
        // Return default value of TVarDeclaration Size attribute
        return 1;
    }

    /**
     * @return The unique ID value of variables in the var declaration
     */
    public String getVarDecUniqueId() {
        return uniqueId;
    }
}
