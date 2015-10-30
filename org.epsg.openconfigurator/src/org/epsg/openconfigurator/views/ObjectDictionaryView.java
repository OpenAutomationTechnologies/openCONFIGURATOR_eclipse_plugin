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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.resources.IPluginImages;

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

    public static final String OBJECT_DICTIONARY_VIEW_TITLE = "Object Dictionary";

    /**
     * Selection listener to update the objects and sub-objects in the Object
     * dictionary view.
     */
    ISelectionListener listener = new ISelectionListener() {
        @Override
        public void selectionChanged(IWorkbenchPart part,
                ISelection selection) {

            setPartName(OBJECT_DICTIONARY_VIEW_TITLE);

            if (treeViewer == null) {
                return;
            }

            treeViewer.setInput(new EmptyObjectDictionary());

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
                    return;
                }
                Object selectedObj = ss.getFirstElement();

                if (selectedObj == null) {
                    return;
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
            System.err.println("getParent " + element);
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
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

    private boolean communicationProfileObjectsVisible = true;
    private boolean standardisedDeviceProfileObjectsVisible = true;
    private boolean nonMappableObjectsVisible = true;

    private Action hideCommunicationProfileObjects;
    private Action hideStandardisedDeviceProfileObjects;
    private Action hideNonMappableObjects;
    private Action propertiesAction;

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

    private void contributeToActionBars() {
        IActionBars bars = getViewSite().getActionBars();

        fillLocalToolBar(bars.getToolBarManager());
        bars.updateActionBars();
    }

    /**
     * Create the actions.
     */
    private void createActions() {
        hideNonMappableObjects = new Action("Hide Non Mappable Objects",
                IAction.AS_CHECK_BOX) {
            @Override
            public void run() {
                if (hideNonMappableObjects.isChecked()) {
                    nonMappableObjectsVisible = false;
                } else {
                    nonMappableObjectsVisible = true;
                }
                treeViewer.refresh();
            }
        };
        hideNonMappableObjects.setToolTipText("Hide Non Mappable Objects");
        hideNonMappableObjects.setImageDescriptor(
                org.epsg.openconfigurator.Activator.getImageDescriptor(
                        IPluginImages.OBD_HIDE_NON_MAPPABLE_ICON));
        hideNonMappableObjects.setChecked(false);

        hideCommunicationProfileObjects = new Action(
                "Hide Communication Profile Area Objects(0x1000-0x1FFF)",
                IAction.AS_CHECK_BOX) {
            @Override
            public void run() {
                if (hideCommunicationProfileObjects.isChecked()) {
                    communicationProfileObjectsVisible = false;
                } else {
                    communicationProfileObjectsVisible = true;
                }
                treeViewer.refresh();
            }
        };
        hideCommunicationProfileObjects.setToolTipText(
                "Hide Communication Profile Area Objects(0x1000-0x1FFF)");
        hideCommunicationProfileObjects.setImageDescriptor(
                org.epsg.openconfigurator.Activator.getImageDescriptor(
                        IPluginImages.OBD_HIDE_COMMUNICATION_DEVICE_PROFILE_ICON));
        hideCommunicationProfileObjects.setChecked(false);

        hideStandardisedDeviceProfileObjects = new Action(
                "Hide Standardised Device Profile Area Objects(0x6000-0x9FFF)",
                IAction.AS_CHECK_BOX) {
            @Override
            public void run() {
                if (hideStandardisedDeviceProfileObjects.isChecked()) {
                    standardisedDeviceProfileObjectsVisible = false;
                } else {
                    standardisedDeviceProfileObjectsVisible = true;
                }
                treeViewer.refresh();
            }
        };
        hideStandardisedDeviceProfileObjects.setToolTipText(
                "Hide Standardised Device Profile Area Objects(0x6000-0x9FFF)");
        hideStandardisedDeviceProfileObjects.setImageDescriptor(
                org.epsg.openconfigurator.Activator.getImageDescriptor(
                        IPluginImages.OBD_HIDE_STANDARDISED_DEVICE_PROFILE_ICON));
        hideStandardisedDeviceProfileObjects.setChecked(false);

        propertiesAction = new Action("Properties") {
            @Override
            public void run() {
                super.run();
                try {
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage()
                            .showView(IPageLayout.ID_PROP_SHEET);
                    treeViewer.setSelection(treeViewer.getSelection());
                } catch (PartInitException e) {
                    e.printStackTrace();

                }
            }
        };
        propertiesAction.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.PROPERTIES_ICON));
    }

    protected void createContextMenu(Viewer viewer) {
        MenuManager contextMenu = new MenuManager("ViewerMenu");
        contextMenu.setRemoveAllWhenShown(true);
        contextMenu.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager mgr) {
                fillContextMenu(mgr);
            }
        });

        Menu menu = contextMenu.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
    }

    /**
     * Create contents of the object dictionary view part.
     *
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {

        PatternFilter filter = new PatternFilter();
        filter.setIncludeLeadingWildcard(true);
        FilteredTree tree = new FilteredTree(parent,
                SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL, filter, true);

        treeViewer = tree.getViewer();

        treeViewer.setContentProvider(objectDictionaryContentProvider);
        treeViewer.setLabelProvider(new DecoratingLabelProvider(labelProvider,
                PlatformUI.getWorkbench().getDecoratorManager()
                        .getLabelDecorator()));
        treeViewer.setInput(new Object());
        createContextMenu(treeViewer);

        createActions();

        contributeToActionBars();

        initializeToolBar();

        getViewSite().getPage().addSelectionListener(IndustrialNetworkView.ID,
                listener);
        getViewSite().setSelectionProvider(treeViewer);
        treeViewer.addFilter(new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {

                if (element instanceof PowerlinkObject) {
                    PowerlinkObject obj = (PowerlinkObject) element;
                    if (!communicationProfileObjectsVisible) {
                        if ((obj.getObjectId() >= 0x1000)
                                && (obj.getObjectId() < 0x2000)) {
                            return false;
                        }

                    }

                    if (!standardisedDeviceProfileObjectsVisible) {
                        if ((obj.getObjectId() >= 0x6000)
                                && (obj.getObjectId() < 0x9FFF)) {
                            return false;
                        }
                    }

                    if (!nonMappableObjectsVisible) {
                        if (!obj.isTpdoMappable() && !obj.isRpdoMappable()
                                && !obj.hasTpdoMappableSubObjects()
                                && !obj.hasRpdoMappableSubObjects()) {
                            return false;
                        }
                    }
                } else if (element instanceof PowerlinkSubobject) {
                    PowerlinkSubobject subObj = (PowerlinkSubobject) element;
                    if (!nonMappableObjectsVisible) {
                        if (!subObj.isTpdoMappable()
                                && !subObj.isRpdoMappable()) {
                            return false;
                        }
                    }
                }
                return true;
            }
        });
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

    protected void fillContextMenu(IMenuManager contextMenu) {

        contextMenu.add(propertiesAction);
    }

    private void fillLocalToolBar(IToolBarManager manager) {
        manager.removeAll();
        manager.add(hideNonMappableObjects);
        manager.add(hideCommunicationProfileObjects);
        manager.add(hideStandardisedDeviceProfileObjects);
    }

    public Control getControl() {
        if (treeViewer == null) {
            return null;
        }
        return treeViewer.getControl();
    }

    private void initializeToolBar() {
        IToolBarManager toolbarManager = getViewSite().getActionBars()
                .getToolBarManager();
    }

    @Override
    public void setFocus() {
        treeViewer.getControl().setFocus();
    }
}
