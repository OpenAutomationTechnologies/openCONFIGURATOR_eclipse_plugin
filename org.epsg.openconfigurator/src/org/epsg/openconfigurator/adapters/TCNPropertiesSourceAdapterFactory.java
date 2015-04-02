package org.epsg.openconfigurator.adapters;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;

public class TCNPropertiesSourceAdapterFactory implements IAdapterFactory {

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IPropertySource.class)
			return new TCNPropertySource((TCN)adaptableObject);
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] {IPropertySource.class};
	}

}
