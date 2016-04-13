package org.epsg.openconfigurator.model;

import org.epsg.openconfigurator.xmlbinding.xdd.Interface;
import org.epsg.openconfigurator.xmlbinding.xdd.RangeList;
import org.epsg.openconfigurator.xmlbinding.xdd.TInterfaceList;

public class HeadNodeInterface {

    private RangeList rangeList;

    private Object uniqueIDRef;

    private Node node;

    public HeadNodeInterface(Node node, Interface interfaces) {
        this.node = node;
        if (interfaces != null) {
            rangeList = interfaces.getRangeList();
            uniqueIDRef = interfaces.getUniqueIDRef();

        }
    }

    public Node getNode() {
        return node;
    }

    public RangeList getRangeList() {
        return rangeList;
    }

    public String getUniqueId() {
        if (uniqueIDRef instanceof TInterfaceList.Interface) {
            TInterfaceList.Interface intfc = (TInterfaceList.Interface) uniqueIDRef;
            return intfc.getUniqueID();
        }
        return null;
    }

    public Object getUniqueIDRef() {
        return uniqueIDRef;
    }

    public void setRangeList(RangeList rangeList) {
        this.rangeList = rangeList;
    }

    public void setUniqueIDRef(Object uniqueIDRef) {
        this.uniqueIDRef = uniqueIDRef;
    }
}
