package org.epsg.openconfigurator.editors;

import java.io.File;
import java.net.URL;

import javax.annotation.PreDestroy;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class IndustrialNetworkEditorNetworkTab extends Composite {

	public IndustrialNetworkEditorNetworkTab(Composite parent, int style) {
		super(parent, style);
		Composite composite = new Composite(parent, SWT.NONE);
	    composite.setLayout(new GridLayout(1, false));
		createImage();
		viewer = new TreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setExpandPreCheckFilters(true);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(File.listRoots());
	}

	private TreeViewer viewer;
	private Image image;

	private void createImage() {
		Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/releng_gear.gif"), null);
		ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		this.image = imageDcr.createImage();
	}

	class ViewContentProvider implements ITreeContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return (File[]) inputElement;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			File file = (File) parentElement;
			return file.listFiles();
		}

		@Override
		public Object getParent(Object element) {
			File file = (File) element;
			return file.getParentFile();
		}

		@Override
		public boolean hasChildren(Object element) {
			File file = (File) element;
			if (file.isDirectory()) {
				return true;
			}
			return false;
		}

	}

	class ViewLabelProvider extends StyledCellLabelProvider {
		@Override
		public void update(ViewerCell cell) {
			Object element = cell.getElement();
			StyledString text = new StyledString();
			File file = (File) element;
			if (file.isDirectory()) {
				text.append(getFileName(file));
				cell.setImage(image);
				String[] files = file.list();
				if (files != null) {
					text.append(" (" + files.length + ") ",
							StyledString.COUNTER_STYLER);
				}
			} else {
				text.append(getFileName(file));
			}
			cell.setText(text.toString());
			cell.setStyleRanges(text.getStyleRanges());
			super.update(cell);

		}

		private String getFileName(File file) {
			String name = file.getName();
			return name.isEmpty() ? file.getPath() : name;
		}
	}

	@PreDestroy
	public void dispose() {
		image.dispose();
	}

}
