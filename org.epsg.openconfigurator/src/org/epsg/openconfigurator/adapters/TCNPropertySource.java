package org.epsg.openconfigurator.adapters;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;

public class TCNPropertySource implements IPropertySource {

	private final TCN tcn;

	public TCNPropertySource(TCN adaptableObject) {
		this.tcn = adaptableObject;
	}
	@Override
	public Object getEditableValue() {
		return this.tcn;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new TextPropertyDescriptor("NodeName", "Node Name"),
				new TextPropertyDescriptor("NodeID", "Node ID"),
				new TextPropertyDescriptor("XDCPath", "XDC Path"),
				new TextPropertyDescriptor("Forced", "Forced Multiplexed Cycle"),
				new TextPropertyDescriptor("Chained", "Chained")
		};
	}

	@Override
	public Object getPropertyValue(Object id) {
		if ("NodeName".equals(id)) return this.tcn.getName();
		else if ("NodeID".equals(id)) return this.tcn.getNodeID();
		else if ("XDCPath".equals(id)) return this.tcn.getPathToXDC();
		else if ("Forced".equals(id)) return this.tcn.getForcedMultiplexedCycle();
		else if ("Chained".equals(id)) return this.tcn.isIsChained();
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {

		
	}

	@Override
	public void setPropertyValue(Object id, Object value) 
	{
		if ("NodeName".equals(id)) this.tcn.setName((String)value);
		else if ("NodeID".equals(id)) this.tcn.setNodeID((String)value);
		else if ("XDCPath".equals(id)) this.tcn.setPathToXDC((String)value);
		else if ("Forced".equals(id)) this.tcn.setForcedMultiplexedCycle((int)value);
		else if ("Chained".equals(id)) this.tcn.setIsChained((boolean) value);
	}

}
