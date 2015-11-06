/*******************************************************************************
 * @file   ObjectDatatype.java
 *
 * @author Ramakrishnan Periyakaruppan, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2015, Kalycito Infotech Private Limited
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

/**
 * POWERLINK object datatype.
 *
 * @author Ramakrishnan P
 *
 */
public class ObjectDatatype {

    /**
     * Returns the datatype in human readable format for the given datatype ID.
     *
     * @param dataTypeId ID of the datatype.
     *
     * @return datatype.
     */
    public static String getDatatypeName(final String dataTypeId) {
        String retValue = "";
        switch (dataTypeId) {
            case "0001":
                retValue = "Boolean";
                break;
            case "0002":
                retValue = "Integer8";
                break;
            case "0003":
                retValue = "Integer16";
                break;
            case "0004":
                retValue = "Integer32";
                break;
            case "0005":
                retValue = "Unsigned8";
                break;
            case "0006":
                retValue = "Unsigned16";
                break;
            case "0007":
                retValue = "Unsigned32";
                break;
            case "0008":
                retValue = "Real32";
                break;
            case "0009":
                retValue = "Visible_String";
                break;
            case "000A":
                retValue = "Octet_String";
                break;
            case "000B":
                retValue = "Unicode_String";
                break;
            case "000C":
                retValue = "Time_of_Day";
                break;
            case "000D":
                retValue = "Time_Diff";
                break;
            case "000F":
                retValue = "Domain";
                break;
            case "0010":
                retValue = "Integer24";
                break;
            case "0011":
                retValue = "Real64";
                break;
            case "0012":
                retValue = "Integer40";
                break;
            case "0013":
                retValue = "Integer48";
                break;
            case "0014":
                retValue = "Integer56";
                break;
            case "0015":
                retValue = "Integer64";
                break;
            case "0016":
                retValue = "Unsigned24";
                break;
            case "0018":
                retValue = "Unsigned40";
                break;
            case "0019":
                retValue = "Unsigned48";
                break;
            case "001A":
                retValue = "Unsigned56";
                break;
            case "001B":
                retValue = "Unsigned64";
                break;
            case "0401":
                retValue = "MAC_ADDRESS";
                break;
            case "0402":
                retValue = "IP_ADDRESS";
                break;
            case "0403":
                retValue = "NETTIME";
                break;

        }
        return retValue;
    }
}
