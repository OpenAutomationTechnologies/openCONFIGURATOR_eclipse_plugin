/*******************************************************************************
 * @file   ParameterGroup.java
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.epsg.openconfigurator.xmlbinding.xdd.TParameterGroup;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList;

/**
 *
 * @author Ramakrishnan P
 *
 */
public class ParameterGroup {

    private final String uniqueId;

    private LabelDescription label;
    private boolean groupLevelVisible;

    private BigInteger bitOffset;

    private Node node;
    private String xpath;

    private Parameter conditionalParameter;
    private String conditionalValue;
    private HashMap<String, ParameterGroup> parameterGroupMap = new HashMap<String, ParameterGroup>();

    private HashMap<String, ParameterReference> parameterRefMap = new HashMap<String, ParameterReference>();

    private ObjectDictionary objectDictionary;

    public ParameterGroup(Node nodeInstance, ObjectDictionary objectDictionary,
            TParameterGroup grp) {
        this.objectDictionary = objectDictionary;

        uniqueId = grp.getUniqueID();
        node = nodeInstance;
        xpath = "//plk:parameterGroup[@uniqueID='" + uniqueId + "']";
        label = new LabelDescription(grp.getLabelOrDescriptionOrLabelRef());
        groupLevelVisible = grp.isGroupLevelVisible();

        bitOffset = grp.getBitOffset();
        conditionalValue = grp.getConditionalValue();

        Object conditionalObjectModel = grp.getConditionalUniqueIDRef();
        if (conditionalObjectModel != null) {
            if (conditionalObjectModel instanceof TParameterList.Parameter) {
                TParameterList.Parameter parameterModel = (TParameterList.Parameter) conditionalObjectModel;
                conditionalParameter = objectDictionary
                        .getParameter(parameterModel.getUniqueID());
            }
        }

        List<Object> parameterGroupReferenceList = grp
                .getParameterGroupOrParameterRef();
        if (parameterGroupReferenceList != null) {
            for (Object parameterGroupReference : parameterGroupReferenceList) {
                if (parameterGroupReference instanceof TParameterGroup) {
                    TParameterGroup paramGrp = (TParameterGroup) parameterGroupReference;
                    ParameterGroup paramGrpModel = new ParameterGroup(node,
                            objectDictionary, paramGrp);
                    parameterGroupMap.put(paramGrpModel.getUniqueId(),
                            paramGrpModel);
                } else if (parameterGroupReference instanceof TParameterGroup.ParameterRef) {
                    TParameterGroup.ParameterRef parameterReferenceModel = (TParameterGroup.ParameterRef) parameterGroupReference;
                    ParameterReference paramRef = new ParameterReference(node,
                            this, objectDictionary, parameterReferenceModel);
                    parameterRefMap.put(paramRef.getUniqueId(), paramRef);
                }
            }
        }
    }

    public BigInteger getBitOffset() {
        return bitOffset;
    }

    public Parameter getConditionalParameter() {
        return conditionalParameter;
    }

    public String getConditionalValue() {
        return conditionalValue;
    }

    public LabelDescription getLabel() {
        return label;
    }

    public ObjectDictionary getObjectDictionary() {
        return objectDictionary;
    }

    public List<ParameterGroup> getParameterGroupList() {
        List<ParameterGroup> valueList = new ArrayList<ParameterGroup>(
                parameterGroupMap.values());
        return valueList;
    }

    public List<ParameterReference> getParameterRefList() {
        List<ParameterReference> valueList = new ArrayList<ParameterReference>(
                parameterRefMap.values());
        return valueList;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public LinkedHashSet<Object> getVisibleObjects() {

        LinkedHashSet<Object> vSet = new LinkedHashSet<>();
        if (!isConditionsMet()) {
            return vSet;
        }

        Collection<ParameterGroup> pgmGrpList = getParameterGroupList();
        for (ParameterGroup pgmGrp : pgmGrpList) {
            if (pgmGrp.isConditionsMet()) {
                if (pgmGrp.isGroupLevelVisible()) {
                    vSet.add(pgmGrp);
                } else {

                    // TODO: Check the below addAll.
                    vSet.addAll(pgmGrp.getVisibleObjects());

                    List<ParameterReference> prmRefList = pgmGrp
                            .getParameterRefList();
                    for (ParameterReference prmRef : prmRefList) {
                        if (prmRef.isVisible()) {
                            vSet.add(prmRef);
                        }
                    }
                }
            }
        }

        Collection<ParameterReference> prmRefList = getParameterRefList();
        for (ParameterReference prmRef : prmRefList) {
            if (prmRef.isVisible()) {
                vSet.add(prmRef);
            }
        }

        System.out.println("=========================" + uniqueId + " m:"
                + isConditionsMet() + " v:" + isGroupLevelVisible());
        for (Object a : vSet) {
            if (a instanceof ParameterGroup) {
                ParameterGroup b = (ParameterGroup) a;
                System.out.println("--------> " + b.getLabel().getText() + " m:"
                        + b.isConditionsMet() + " v:"
                        + b.isGroupLevelVisible());
            } else if (a instanceof ParameterReference) {
                ParameterReference b = (ParameterReference) a;
                System.out.println(
                        "--------> " + b.getLabelDescription().getText());
            }
        }

        System.out.println("==========END===============");
        return vSet;
    }

    /**
     * @return XPath of Parameter group in the given XDD/XDC file.
     */
    public String getXpath() {
        return xpath;
    }

    public boolean hasVisibleObjects() {
        boolean retVal = false;

        if (!isConditionsMet()) {
            return retVal;
        }

        // Check all the parameterReferences
        Collection<ParameterReference> prmRefList = getParameterRefList();
        for (ParameterReference prmRef : prmRefList) {
            retVal = retVal || prmRef.isVisible();
        }

        // Check all the parameterGroups also
        Collection<ParameterGroup> paramGrp = getParameterGroupList();
        for (ParameterGroup grp : paramGrp) {
            retVal = retVal
                    || (grp.isConditionsMet() && grp.isGroupLevelVisible());
        }

        return retVal;
    }

    public boolean isConditionsMet() {
        boolean conditionalParameterAllowed = false;
        if ((conditionalParameter != null) && (conditionalValue != null)) {
            if (conditionalParameter.getActualValue() != null) {
                if (conditionalValue.equalsIgnoreCase(
                        conditionalParameter.getActualValue())) {
                    conditionalParameterAllowed = true;
                }
            } else if (conditionalParameter.getDefaultValue() != null) {
                if (conditionalValue.equalsIgnoreCase(
                        conditionalParameter.getDefaultValue())) {
                    conditionalParameterAllowed = true;
                }
            }
        } else {
            // Conditional parameter not available. Thus conditions are met.
            conditionalParameterAllowed = true;
        }
        return conditionalParameterAllowed;
    }

    public boolean isGroupLevelVisible() {
        return groupLevelVisible;
    }
}
