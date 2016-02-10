/*******************************************************************************
 * @file   SimpleDataType.java
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

import org.epsg.openconfigurator.lib.wrapper.IEC_Datatype;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList.Parameter;
import org.epsg.openconfigurator.xmlbinding.xdd.TVarDeclaration;

/**
 *
 * @author Ramakrishnan P
 *
 */
public enum SimpleDataType {

    UNDEFINED("UNDEFINED"), BITSTRING("BITSTRING"), BOOL("BOOL"), BYTE(
            "BYTE"), _CHAR("_CHAR"), DINT("DINT"), DWORD("DWORD"), INT(
                    "INT"), LINT("LINT"), LREAL("LREAL"), LWORD("LWORD"), REAL(
                            "REAL"), SINT("SINT"), STRING("STRING"), UDINT(
                                    "UDINT"), UINT("UINT"), ULINT(
                                            "ULINT"), USINT("USINT"), WORD(
                                                    "WORD"), WSTRING("WSTRING");

    public static IEC_Datatype getIEC_DataType(Parameter parameter) {

        if (parameter.getBITSTRING() != null) {
            return IEC_Datatype.BITSTRING;
        } else if (parameter.getBOOL() != null) {
            return IEC_Datatype.BOOL;
        } else if (parameter.getBYTE() != null) {
            return IEC_Datatype.BYTE;
        } else if (parameter.getCHAR() != null) {
            return IEC_Datatype._CHAR;
        } else if (parameter.getDINT() != null) {
            return IEC_Datatype.DINT;
        } else if (parameter.getDWORD() != null) {
            return IEC_Datatype.DWORD;
        } else if (parameter.getINT() != null) {
            return IEC_Datatype.INT;
        } else if (parameter.getLINT() != null) {
            return IEC_Datatype.LINT;
        } else if (parameter.getLREAL() != null) {
            return IEC_Datatype.LREAL;
        } else if (parameter.getLWORD() != null) {
            return IEC_Datatype.LWORD;
        } else if (parameter.getREAL() != null) {
            return IEC_Datatype.REAL;
        } else if (parameter.getSINT() != null) {
            return IEC_Datatype.SINT;
        } else if (parameter.getSTRING() != null) {
            return IEC_Datatype.STRING;
        } else if (parameter.getUDINT() != null) {
            return IEC_Datatype.UDINT;
        } else if (parameter.getUINT() != null) {
            return IEC_Datatype.UINT;
        } else if (parameter.getULINT() != null) {
            return IEC_Datatype.ULINT;
        } else if (parameter.getUSINT() != null) {
            return IEC_Datatype.USINT;
        } else if (parameter.getWORD() != null) {
            return IEC_Datatype.WORD;
        } else if (parameter.getWSTRING() != null) {
            return IEC_Datatype.WSTRING;
        } else {
            System.err.println("Parameter Un handled IEC_Datatype value");
            if (parameter.getDataTypeIDRef() != null) {
                System.err.println(parameter.getDataTypeIDRef());
            } else {
                System.err.println("No uniqueIDRef and simple datatype");
            }
        }

        return IEC_Datatype.UNDEFINED;
    }

    public static IEC_Datatype getIEC_DataType(SimpleDataType simpleDt) {
        IEC_Datatype iecDt = IEC_Datatype.UNDEFINED;

        switch (simpleDt) {
            case BITSTRING:
                iecDt = IEC_Datatype.BITSTRING;
                break;
            case BOOL:
                iecDt = IEC_Datatype.BOOL;
                break;
            case BYTE:
                iecDt = IEC_Datatype.BYTE;
                break;
            case _CHAR:
                iecDt = IEC_Datatype._CHAR;
                break;
            case DINT:
                iecDt = IEC_Datatype.DINT;
                break;
            case DWORD:
                iecDt = IEC_Datatype.DWORD;
                break;
            case INT:
                iecDt = IEC_Datatype.INT;
                break;
            case LINT:
                iecDt = IEC_Datatype.LINT;
                break;
            case LREAL:
                iecDt = IEC_Datatype.LREAL;
                break;
            case LWORD:
                iecDt = IEC_Datatype.LWORD;
                break;
            case REAL:
                iecDt = IEC_Datatype.REAL;
                break;
            case SINT:
                iecDt = IEC_Datatype.SINT;
                break;
            case STRING:
                iecDt = IEC_Datatype.STRING;
                break;
            case UDINT:
                iecDt = IEC_Datatype.UDINT;
                break;
            case UINT:
                iecDt = IEC_Datatype.UINT;
                break;
            case ULINT:
                iecDt = IEC_Datatype.ULINT;
                break;
            case USINT:
                iecDt = IEC_Datatype.USINT;
                break;
            case WORD:
                iecDt = IEC_Datatype.WORD;
                break;
            case WSTRING:
                iecDt = IEC_Datatype.WSTRING;
                break;
            case UNDEFINED:
            default:
                // nothing to do.
                break;
        }

        return iecDt;
    }

    public static SimpleDataType getSimpleDataType(Parameter parameter) {

        if (parameter.getBITSTRING() != null) {
            return BITSTRING;
        } else if (parameter.getBOOL() != null) {
            return BOOL;
        } else if (parameter.getBYTE() != null) {
            return BYTE;
        } else if (parameter.getCHAR() != null) {
            return _CHAR;
        } else if (parameter.getDINT() != null) {
            return DINT;
        } else if (parameter.getDWORD() != null) {
            return DWORD;
        } else if (parameter.getINT() != null) {
            return INT;
        } else if (parameter.getLINT() != null) {
            return LINT;
        } else if (parameter.getLREAL() != null) {
            return LREAL;
        } else if (parameter.getLWORD() != null) {
            return LWORD;
        } else if (parameter.getREAL() != null) {
            return REAL;
        } else if (parameter.getSINT() != null) {
            return SINT;
        } else if (parameter.getSTRING() != null) {
            return STRING;
        } else if (parameter.getUDINT() != null) {
            return UDINT;
        } else if (parameter.getUINT() != null) {
            return UINT;
        } else if (parameter.getULINT() != null) {
            return ULINT;
        } else if (parameter.getUSINT() != null) {
            return USINT;
        } else if (parameter.getWORD() != null) {
            return WORD;
        } else if (parameter.getWSTRING() != null) {
            return WSTRING;
        } else {
            // un-supported
        }

        return UNDEFINED;
    }

    public static SimpleDataType getSimpleDataType(TVarDeclaration varDecl) {

        if (varDecl.getBITSTRING() != null) {
            return BITSTRING;
        } else if (varDecl.getBOOL() != null) {
            return BOOL;
        } else if (varDecl.getBYTE() != null) {
            return BYTE;
        } else if (varDecl.getCHAR() != null) {
            return _CHAR;
        } else if (varDecl.getDINT() != null) {
            return DINT;
        } else if (varDecl.getDWORD() != null) {
            return DWORD;
        } else if (varDecl.getINT() != null) {
            return INT;
        } else if (varDecl.getLINT() != null) {
            return LINT;
        } else if (varDecl.getLREAL() != null) {
            return LREAL;
        } else if (varDecl.getLWORD() != null) {
            return LWORD;
        } else if (varDecl.getREAL() != null) {
            return REAL;
        } else if (varDecl.getSINT() != null) {
            return SINT;
        } else if (varDecl.getSTRING() != null) {
            return STRING;
        } else if (varDecl.getUDINT() != null) {
            return UDINT;
        } else if (varDecl.getUINT() != null) {
            return UINT;
        } else if (varDecl.getULINT() != null) {
            return ULINT;
        } else if (varDecl.getUSINT() != null) {
            return USINT;
        } else if (varDecl.getWORD() != null) {
            return WORD;
        } else if (varDecl.getWSTRING() != null) {
            return WSTRING;
        } else {
            // un-supported
        }

        return UNDEFINED;
    }

    private final String text;

    SimpleDataType(final String name) {
        text = name;
    }

    @Override
    public String toString() {
        return text;
    }
}
