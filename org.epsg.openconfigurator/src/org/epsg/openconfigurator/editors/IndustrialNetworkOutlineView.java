package org.epsg.openconfigurator.editors;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class IndustrialNetworkOutlineView extends ContentOutlinePage {

	public IndustrialNetworkOutlineView(Object documentProvider,
			IndustrialNetworkEditor industrialNetworkEditor) {
		// TODO Auto-generated constructor stub
	}

	public void setInput(IEditorInput editorInput) {
		
	}
	  
	public void createControl(Composite parent) {
	      super.createControl(parent);
	      TreeViewer viewer= getTreeViewer();

	   }

}
