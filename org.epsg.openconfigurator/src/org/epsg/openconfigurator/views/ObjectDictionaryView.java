/*******************************************************************************
 * @file   ObjectDictionaryView.java
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

package org.epsg.openconfigurator.views;

import java.util.List;

import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.xmlbinding.xdd.TObject;

/**
 * View to list the objects and subobjects of a node.
 *
 * @author Ramakrishnan P
 *
 */
public class ObjectDictionaryView extends ViewPart {

    /**
     * Class to bind zero objects with the tree view.
     *
     * @author Ramakrishnan P
     *
     */
    private class EmptyObjectDictionary {
        @Override
        public String toString() {
            return "Object dictionary not available.";
        }
    }

    /**
     * Part listener to listen to the changes of the source part.
     *
     * @see IndustrialNetworkView
     * @author Ramakrishnan P
     *
     */
    private class PartListener implements IPartListener {
        @Override
        public void partActivated(IWorkbenchPart part) {
        }

        @Override
        public void partBroughtToTop(IWorkbenchPart part) {
        }

        @Override
        public void partClosed(IWorkbenchPart part) {
            if (sourcePart == part) {
                if (sourcePart != null) {
                    sourcePart.getSite().getPage()
                            .removePartListener(partListener);
                }
                sourcePart = null;
                if ((treeViewer != null)
                        && !treeViewer.getControl().isDisposed()) {
                    treeViewer.setInput(new Object[0]);
                }
            }
        }

        @Override
        public void partDeactivated(IWorkbenchPart part) {
        }

        @Override
        public void partOpened(IWorkbenchPart part) {
        }
    }

    /**
     * Label provider for the objects and sub-objects.
     *
     * @author Ramakrishnan P
     *
     */
    private class TreeLabelProvider extends LabelProvider {
        Image objectIcon;
        Image subObjectIcon;

        TreeLabelProvider() {
            objectIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.OBD_OBJECT_ICON)
                    .createImage();
            subObjectIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.OBD_SUB_OBJECT_ICON)
                    .createImage();
        }

        @Override
        public void dispose() {
            objectIcon.dispose();
            subObjectIcon.dispose();
        }

        @Override
        public Image getImage(Object element) {

            if (element instanceof PowerlinkObject) {
                return objectIcon;
            } else if (element instanceof PowerlinkSubobject) {
                return subObjectIcon;
            } else if (element instanceof EmptyObjectDictionary) {
                // No image is needed for empty contents.
                return null;
            }

            return PlatformUI.getWorkbench().getSharedImages()
                    .getImage(ISharedImages.IMG_OBJ_ELEMENT);
        }

        @Override
        public String getText(Object element) {
            if (element instanceof PowerlinkObject) {
                return ((PowerlinkObject) element).getText();
            } else if (element instanceof PowerlinkSubobject) {
                return ((PowerlinkSubobject) element).getText();
            }
            return element == null ? "" : element.toString();//$NON-NLS-1$
        }
    }

    public static final String ID = "org.epsg.openconfigurator.views.ObjectDictionaryView"; //$NON-NLS-1$

    /**
     * Selection listener to update the objects and sub-objects in the Object
     * dictionary view.
     */
    ISelectionListener listener = new ISelectionListener() {
        @Override
        public void selectionChanged(IWorkbenchPart part,
                ISelection selection) {

            if (treeViewer == null) {
                return;
            }

            if (sourcePart != null) {
                sourcePart.getSite().getPage().removePartListener(partListener);
                sourcePart = null;
            }

            // change the viewer input since the workbench selection has
            // changed.
            if (selection instanceof IStructuredSelection) {

                IStructuredSelection ss = (IStructuredSelection) selection;
                if (ss.size() > 1) {
                    System.err.println("Multiple selection not handled.");
                    treeViewer.setInput(new EmptyObjectDictionary());
                    return;
                }
                Object selectedObj = ss.getFirstElement();

                if (selectedObj == null) {
                    treeViewer.setInput(new EmptyObjectDictionary());
                    return;
                }

                if (selectedObj instanceof TreeSelection) {
                    treeViewer.setInput(new EmptyObjectDictionary());
                }

                if (selectedObj instanceof Node) {
                    nodeObj = (Node) selectedObj;
                    setPartName(nodeObj.getNodeIDWithName());
                    treeViewer.setInput(nodeObj);
                }
                sourcePart = part;
            }

            if (sourcePart != null) {
                sourcePart.getSite().getPage().addPartListener(partListener);
            }
        }
    };

    /**
     * Content provider to list the object, sub-objects in an hierarchical
     * order.
     */
    private ITreeContentProvider objectDictionaryContentProvider = new ITreeContentProvider() {

        @Override
        public void dispose() {
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof TObject) {
                TObject object = (TObject) parentElement;
                return object.getSubObject().toArray();
            }
            if (parentElement instanceof PowerlinkObject) {
                PowerlinkObject objItem = (PowerlinkObject) parentElement;
                return objItem.getSubObjects().toArray();
            }
            return null;
        }

        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof Node) {
                Node nodeObj = (Node) inputElement;

                List<PowerlinkObject> objectsList = nodeObj.getObjectsList();

                return objectsList.toArray();
            }

            return new Object[] { new EmptyObjectDictionary() };
        }

        @Override
        public Object getParent(Object element) {
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof TObject) {
                TObject object = (TObject) element;
                return ((object.getSubObject().size() > 0) ? true : false);
            }
            if (element instanceof PowerlinkObject) {
                PowerlinkObject objItem = (PowerlinkObject) element;
                return ((objItem.getSubObjects().size() > 0) ? true : false);
            }
            return false;
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput,
                Object newInput) {
        }
    };

    /**
     * Object dictionary tree viewer.
     */
    private TreeViewer treeViewer;

    /**
     * Label provider.
     */
    private ILabelProvider labelProvider = new TreeLabelProvider();

    /**
     * The corresponding node instance for which the object dictionary
     * corresponds to.
     */
    private Node nodeObj;

    /**
     * Source workbench part.
     */
    private IWorkbenchPart sourcePart;

    /**
     * Listener instance to listen to the changes in the source part.
     */
    private PartListener partListener = new PartListener();

    /**
     * Create contents of the object dictionary view part.
     *
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {

        PatternFilter filter = new PatternFilter();
        FilteredTree tree = new FilteredTree(parent,
                SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL, filter, true);

        treeViewer = tree.getViewer();

        treeViewer.setContentProvider(objectDictionaryContentProvider);
        treeViewer.setLabelProvider(new DecoratingLabelProvider(labelProvider,
                PlatformUI.getWorkbench().getDecoratorManager()
                        .getLabelDecorator()));
        treeViewer.setInput(new Object());
        getViewSite().getPage().addSelectionListener(IndustrialNetworkView.ID,
                listener);
        getViewSite().setSelectionProvider(treeViewer);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (sourcePart != null) {
            sourcePart.getSite().getPage().removePartListener(partListener);
        }

        getViewSite().getPage()
                .removeSelectionListener(IndustrialNetworkView.ID, listener);
    }

    public Control getControl() {
        if (treeViewer == null) {
            return null;
        }
        return treeViewer.getControl();
    }

    @Override
    public void setFocus() {
        treeViewer.getControl().setFocus();
    }
}
