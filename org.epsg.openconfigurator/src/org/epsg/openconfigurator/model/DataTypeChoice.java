/*******************************************************************************
 * @file   DataTypeChoice.java
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

import org.epsg.openconfigurator.xmlbinding.xdd.TDataTypeIDRef;
import org.epsg.openconfigurator.xmlbinding.xdd.TDataTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList;
import org.epsg.openconfigurator.xmlbinding.xdd.TVarDeclaration;

/**
 * Class to adapt the data type values from the XDD model.
 *
 * @author Ramakrishnan P
 *
 */
public class DataTypeChoice {

    private DataTypeChoiceType choiceType = DataTypeChoiceType.UNDEFINED;
    private SimpleDataType simpleDataType;
    private StructType structDataType;
    // DataTypeIdRef

    /**
     * Constructor to define the data type values from Parameter model.
     *
     * @param param XDD model instance of Parameter.
     */
    public DataTypeChoice(TParameterList.Parameter param) {

        if (param != null) {
            if ((param.getDataTypeIDRef() == null)
                    && param.getVariableRef().isEmpty()) {
                simpleDataType = SimpleDataType.getSimpleDataType(param);
                choiceType = DataTypeChoiceType.SIMPLE;
            } else if (param.getVariableRef().isEmpty()) {
                TDataTypeIDRef dtIdRef = param.getDataTypeIDRef();
                Object dataTypeChoice = dtIdRef.getUniqueIDRef();
                if (dataTypeChoice instanceof TDataTypeList.Struct) {
                    structDataType = new StructType(
                            (TDataTypeList.Struct) dataTypeChoice);
                    choiceType = DataTypeChoiceType.STRUCT;
                } else if (dataTypeChoice instanceof TDataTypeList.Array) {
                    // FIXME Handle array.
                    System.out.println("Array datatypes unhandled");
                    choiceType = DataTypeChoiceType.ARRAY;
                } else {
                    // FIXME Handle enum and derived.
                    System.out.println("Enum and derived datatypes unhandled");
                }
            } else {
                // FIXME Handle variable ref here.
                System.out.println("Variable ref unhandled");
            }
        } else {
            // ignore
        }
    }

    /**
     * Constructor to define the complex data type values from parameter model
     *
     * @param varDecl The value of complex data type.
     */
    public DataTypeChoice(TVarDeclaration varDecl) {
        if (varDecl != null) {
            if (varDecl.getDataTypeIDRef() == null) {
                simpleDataType = SimpleDataType.getSimpleDataType(varDecl);
                choiceType = DataTypeChoiceType.SIMPLE;
            } else {
                // FIXME Handle DataTypeIDRef here.
                System.out.println("TVarDeclaration DataTypeIDRef unhandled");
            }
        }
    }

    /**
     * @return Choice type of data type from the parameter.
     */
    public DataTypeChoiceType getChoiceType() {
        return choiceType;
    }

    /**
     * @return Simple data type of parameter model.
     */
    public SimpleDataType getSimpleDataType() {
        return simpleDataType;
    }

    /**
     * @return Complex data type of parameter model.
     */
    public StructType getStructDataType() {
        return structDataType;
    }
}
