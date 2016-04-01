/*******************************************************************************
 * @file   StructType.java
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

import org.epsg.openconfigurator.xmlbinding.xdd.TDataTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.TVarDeclaration;

/**
 * Class that identifies the struvt data type of parameter.
 *
 * @author Ramakrishnan P
 *
 */
public class StructType {
    private String name;
    private LabelDescription label;
    private String uniqueId;
    List<VarDecleration> varDeclList = new ArrayList<>();

    /**
     * Constructor to define the attribute value of complex data types.
     *
     * @param struct Instance of XDD model of struct.
     */
    public StructType(TDataTypeList.Struct struct) {
        if (struct != null) {
            label = new LabelDescription(
                    struct.getLabelOrDescriptionOrLabelRef());
            name = struct.getName();
            uniqueId = struct.getUniqueID();
            List<TVarDeclaration> varDeclModelList = struct.getVarDeclaration();
            for (TVarDeclaration varDeclModel : varDeclModelList) {
                VarDecleration varDecl = new VarDecleration(varDeclModel);
                System.err.println(
                        "Adding Variable in struct " + varDecl.getName());
                varDeclList.add(varDecl);
            }
        }
    }

    /**
     * @return The label description of complex data type in parameter.
     */
    public LabelDescription getLabel() {
        return label;
    }

    /**
     * @return The name given to the complex data types.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Unique ID of struct data type.
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * @return List of vardeclaration available under struct data type.
     */
    public List<VarDecleration> getVarDeclList() {
        return varDeclList;
    }

    /**
     * @return The variables of var declaration from the given data type of
     *         parameter.
     */
    public List<VarDecleration> getVariables() {
        System.err.println("Variable declerations : " + varDeclList.size());
        return varDeclList;
    }
}
