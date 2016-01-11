/*******************************************************************************
 * @file   IndustrialNetworkView.java
 *
 * @brief  Lists the nodes and the modules in the project.
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

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.editors.project.IndustrialNetworkProjectEditor;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.resources.IPluginImages;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.views.mapping.MappingView;
import org.epsg.openconfigurator.wizards.NewNodeWizard;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.jdom2.JDOMException;

/**
 * Industrial network view to list all the nodes available in the project.
 *
 * @author Ramakrishnan P
 *
 */
public class IndustrialNetworkView extends ViewPart
        implements ILinkedWithEditorView, IPropertyListener {

    /**
     * Class to bind zero nodes with the tree view.
     *
     * @author Ramakrishnan P
     *
     */
    private class EmptyNetworkView {
        @Override
        public String toString() {
            return "Network elements not available.";
        }
    }

    /**
     * Node ID based comparator.
     *
     * An exception the MN node id will remain at the top.
     *
     * @author Ramakrishnan P
     *
     */
    private class NodeIdBasedSorter extends ViewerComparator {

        @Override
        public int compare(Viewer viewer, Object e1, Object e2) {
            if ((e1 instanceof Node) && (e2 instanceof Node)) {

                Node nodeFirst = (Node) e1;
                Node nodeSecond = (Node) e2;

                if (nodeSecond
                        .getNodeId() == IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
                    return 255;
                } else {
                    return nodeFirst.getNodeId() - nodeSecond.getNodeId();
                }
            }
            return super.compare(viewer, e1, e2);
        }
    }

    /**
     * Content provider to list the nodes available in the project.
     *
     * @author Ramakrishnan P
     *
     */
    private class ViewContentProvider implements ITreeContentProvider {

        @Override
        public void dispose() {
        }

        @Override
        public Object[] getChildren(Object parent) {

            if (parent instanceof PowerlinkRootNode) {
                PowerlinkRootNode powerlinkRoot = (PowerlinkRootNode) parent;
                Object[] nodeList = powerlinkRoot.getNodeList(parent);
                if (nodeList.length == 0) {
                    return new Object[] { new EmptyNetworkView() };
                } else {
                    return nodeList;
                }
            } else if (parent instanceof Node) {
                Node node = (Node) parent;

                Object nodeObjectModel = node.getNodeModel();

                if (nodeObjectModel instanceof TNetworkConfiguration) {
                    return rootNode.getRmnNodeList().toArray();
                } else if (nodeObjectModel instanceof TCN) {
                    // TODO implement for Modular CN
                } else if (nodeObjectModel instanceof TRMN) {
                    // TODO implement for Modular RMN
                }
            }

            System.err.println("Returning empty object. Parent:" + parent);
            return null;
        }

        @Override
        public Object[] getElements(Object parent) {

            if (parent == null) {
                return new Object[] { new EmptyNetworkView() };
            } else {
                return getChildren(parent);
            }
        }

        @Override
        public Object getParent(Object child) {

            if (child instanceof Node) {
                Node node = (Node) child;

                Object nodeObjectModel = node.getNodeModel();

                if (nodeObjectModel instanceof TNetworkConfiguration) {
                    return rootNode;
                } else if (nodeObjectModel instanceof TCN) {
                    return rootNode;
                } else if (nodeObjectModel instanceof TRMN) {
                    return rootNode.getMN();
                }
            }

            return null;
        }

        @Override
        public boolean hasChildren(Object parent) {
            if (parent instanceof Node) {
                Node node = (Node) parent;

                Object nodeObjectModel = node.getNodeModel();

                if (nodeObjectModel instanceof TNetworkConfiguration) {
                    ArrayList<Node> nodeList = rootNode.getRmnNodeList();
                    return (nodeList.size() > 0 ? true : false);
                } else if (nodeObjectModel instanceof TCN) {
                    // TODO implement for Modular CN
                    return false;
                } else if (nodeObjectModel instanceof TRMN) {
                    // TODO implement for Modular RMN
                    return false;
                }
            }

            return false;
        }

        @Override
        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
        }
    }

    /**
     * Label provider to display the text information for each node.
     *
     * @author Ramakrishnan P
     *
     */
    private class ViewLabelProvider extends LabelProvider
            implements IColorProvider {

        Image mnIcon;
        Image cnEnabledIcon;
        Image cnDisabledIcon;
        Image rmnIcon;

        ViewLabelProvider() {
            mnIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.MN_ICON).createImage();
            cnEnabledIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.CN_ICON).createImage();
            cnDisabledIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.CN_DISABLED_ICON)
                    .createImage();
            rmnIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.RMN_ICON).createImage();
        }

        @Override
        public void dispose() {
            mnIcon.dispose();
            cnEnabledIcon.dispose();
            cnDisabledIcon.dispose();
            rmnIcon.dispose();
        };

        @Override
        public Color getBackground(Object element) {
            return null;
        }

        @Override
        public Color getForeground(Object element) {
            if (element instanceof Node) {
                Node nodeObj = (Node) element;

                if (!nodeObj.isEnabled()) {
                    return Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
                }
            }

            return null;
        }

        @Override
        public Image getImage(Object obj) {

            if (obj instanceof Node) {
                Node node = (Node) obj;

                Object nodeObjectModel = node.getNodeModel();
                if (nodeObjectModel instanceof TNetworkConfiguration) {
                    return mnIcon;
                }
                if (nodeObjectModel instanceof TCN) {
                    TCN cnModel = (TCN) nodeObjectModel;
                    if (cnModel.isEnabled()) {
                        return cnEnabledIcon;
                    } else {
                        return cnDisabledIcon;
                    }
                }
                if (nodeObjectModel instanceof TRMN) {
                    return rmnIcon;
                }
            } else if (obj instanceof EmptyNetworkView) {
                return null;
            }

            return PlatformUI.getWorkbench().getSharedImages()
                    .getImage(ISharedImages.IMG_TOOL_FORWARD);
        }

        @Override
        public String getText(Object obj) {

            if (obj instanceof Node) {
                Node node = (Node) obj;
                return node.getNodeIDWithName();
            }
            return obj.toString();
        }
    }

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "org.epsg.openconfigurator.views.IndustrialNetworkView";

    /**
     * The root node of the Industrial network view.
     */
    private PowerlinkRootNode rootNode = new PowerlinkRootNode();

    /**
     * Tree viewer to list the nodes.
     */
    private TreeViewer viewer;

    /**
     * Add new node.
     */
    private Action addNewNode;
    /**
     * Show object dictionary.
     */
    private Action showObjectDictionary;
    /**
     * Show Properties action.
     */
    private Action showProperties;
    /**
     * Delete node action.
     */
    private Action deleteNode;

    /**
     * Enable/Disable node action.
     */
    private Action enableDisableNode;

    /**
     * Refresh the Industrial network view action.
     */
    private Action refreshAction;

    /**
     * Show PDO mapping action.
     */
    private Action showPdoMapping;

    /**
     * Call back to handle the selection changed events.
     */
    private ISelectionChangedListener viewerSelectionChangedListener = new ISelectionChangedListener() {

        @Override
        public void selectionChanged(SelectionChangedEvent event) {
            contributeToActionBars();
        }
    };

    /**
     * Keyboard bindings.
     */
    private KeyAdapter treeViewerKeyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            if (e.keyCode == SWT.DEL) {
                IStructuredSelection selection = (IStructuredSelection) viewer
                        .getSelection();
                handleRemoveNode(selection);
            } else if (e.keyCode == SWT.F5) {
                handleRefresh();
            }
        }
    };

    private LinkWithEditorPartListener linkWithEditorPartListener = new LinkWithEditorPartListener(
            this);

    /**
     * The constructor.
     */
    public IndustrialNetworkView() {
    }

    /**
     * Contribute to the action bars in the tree viewer.
     */
    private void contributeToActionBars() {
        IActionBars bars = getViewSite().getActionBars();
        fillLocalPullDown(bars.getMenuManager());
        fillLocalToolBar(bars.getToolBarManager());
        bars.updateActionBars();
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    @Override
    public void createPartControl(Composite parent) {
        viewer = new TreeViewer(parent,
                SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
        viewer.setContentProvider(new ViewContentProvider());
        viewer.setLabelProvider(new ViewLabelProvider());
        viewer.setComparator(new NodeIdBasedSorter());
        viewer.expandAll();

        makeActions();
        hookContextMenu();
        hookDoubleClickAction();

        getSite().getPage().addPartListener(linkWithEditorPartListener);
        getSite().setSelectionProvider(viewer);
        viewer.addSelectionChangedListener(viewerSelectionChangedListener);
        viewer.getControl().addKeyListener(treeViewerKeyListener);
    }

    @Override
    public void dispose() {
        viewer.setSelection(TreeSelection.EMPTY);
    }

    @Override
    public void editorActivated(IEditorPart activeEditor) {
        if (!(activeEditor instanceof IndustrialNetworkProjectEditor)) {
            return;
        }

        IndustrialNetworkProjectEditor activeEditorTemp = (IndustrialNetworkProjectEditor) activeEditor;
        rootNode.setNodeCollection(activeEditorTemp.getNodeCollection());
        Control control = viewer.getControl();
        if ((control != null) && !control.isDisposed()) {
            viewer.setInput(rootNode);
        }
    }

    @Override
    public void editorClosed(IEditorPart activeEditor) {

        if (!(activeEditor instanceof IndustrialNetworkProjectEditor)) {
            return;
        }

        rootNode.setNodeCollection(new HashMap<Short, Node>());
        Control control = viewer.getControl();
        if ((control != null) && !control.isDisposed()) {
            viewer.setInput(rootNode);
        }
        viewer.setSelection(TreeSelection.EMPTY);
    }

    /**
     * Prepares the context menu in the given menu manager based on the node
     * selected in the tree viewer.
     *
     * @param manager The menu manager instance.
     */
    private void fillContextMenu(IMenuManager manager) {
        if (viewer.getSelection().isEmpty()) {
            return;
        }
        if (viewer.getSelection() instanceof IStructuredSelection) {
            IStructuredSelection selections = (IStructuredSelection) viewer
                    .getSelection();
            Object object = selections.getFirstElement();

            if (object instanceof Node) {
                Node node = (Node) object;

                Object nodeObjectModel = node.getNodeModel();

                if (nodeObjectModel instanceof TRMN) {
                    manager.add(showPdoMapping);
                    manager.add(showObjectDictionary);
                    manager.add(new Separator());
                    manager.add(deleteNode);
                } else if (nodeObjectModel instanceof TNetworkConfiguration) {
                    manager.add(addNewNode);
                    manager.add(new Separator());
                    manager.add(showPdoMapping);
                    manager.add(showObjectDictionary);
                    manager.add(new Separator());
                } else if (nodeObjectModel instanceof TCN) {
                    manager.add(enableDisableNode);
                    manager.add(new Separator());
                    manager.add(showPdoMapping);
                    manager.add(showObjectDictionary);
                    manager.add(new Separator());
                    manager.add(deleteNode);
                }

                manager.add(new Separator());
                manager.add(showProperties);
            }
        }
    }

    private void fillLocalPullDown(IMenuManager manager) {
        manager.removeAll();
        fillContextMenu(manager);
    }

    private void fillLocalToolBar(IToolBarManager manager) {
        manager.removeAll();

        manager.add(refreshAction);
        manager.add(showPdoMapping);
        manager.add(showObjectDictionary);
        manager.add(showProperties);
    }

    public PowerlinkRootNode getNodeList() {
        return rootNode;
    }

    private void handleEnableDisable(IStructuredSelection selection) {
        if (selection.isEmpty()) {
            showMessage("No selection");
            return;
        }

        List selectedObjectsList = selection.toList();

        for (Object selectedObject : selectedObjectsList) {
            if (selectedObject instanceof Node) {
                Node node = (Node) selectedObject;
                Result res = OpenConfiguratorLibraryUtils
                        .toggleEnableDisable(node);
                if (res.IsSuccessful()) {
                    try {
                        rootNode.toggleEnableDisable(node);
                    } catch (JDOMException | IOException e) {
                        OpenConfiguratorMessageConsole.getInstance()
                                .printErrorMessage(e.getMessage());
                        e.printStackTrace();
                    }

                    handleRefresh();
                } else {
                    showMessage(
                            OpenConfiguratorLibraryUtils.getErrorMessage(res));
                }
            }
        }
    }

    private void handleRefresh() {
        viewer.setInput(viewer.getInput());
        viewer.expandAll();
    }

    public void handleRemoveNode(IStructuredSelection selection) {
        if (selection.isEmpty()) {
            showMessage("No selection");
            return;
        }

        List selectedObjectsList = selection.toList();

        for (Object selectedObject : selectedObjectsList) {
            if (selectedObject instanceof Node) {
                Node node = (Node) selectedObject;

                Object nodeObjectModel = node.getNodeModel();
                if ((nodeObjectModel instanceof TRMN)
                        || (nodeObjectModel instanceof TCN)) {

                    MessageDialog dialog = new MessageDialog(null,
                            "Delete node", null,
                            "Are you sure you want to delete the node '"
                                    + node.getNodeIDWithName() + "'",
                            MessageDialog.QUESTION,
                            new String[] { "Yes", "No" }, 1);
                    int result = dialog.open();
                    if (result == 0) {
                        Result res = OpenConfiguratorLibraryUtils
                                .removeNode(node);
                        if (res.IsSuccessful()) {
                            try {
                                rootNode.removeNode(node);
                            } catch (JDOMException | IOException e) {
                                if (e instanceof NoSuchFileException) {
                                    OpenConfiguratorMessageConsole.getInstance()
                                            .printErrorMessage("The file "
                                                    + e.getMessage()
                                                    + " cannot be found.");
                                } else {
                                    OpenConfiguratorMessageConsole.getInstance()
                                            .printErrorMessage(e.getMessage());
                                }
                                e.printStackTrace();
                            }
                            handleRefresh();
                        } else {
                            showMessage(OpenConfiguratorLibraryUtils
                                    .getErrorMessage(res));
                        }
                    }
                } else {
                    showMessage("Delete this node not supported!");
                }
            } else {
                System.err.println("Invalid tree item instance");
            }
        }
    }

    private void hookContextMenu() {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                IndustrialNetworkView.this.fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        menuMgr.setRemoveAllWhenShown(true);
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);
    }

    private void hookDoubleClickAction() {
        viewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
            public void doubleClick(DoubleClickEvent event) {
                showPdoMapping.run();
            }
        });
    }

    private void makeActions() {
        addNewNode = new Action("Add Node...") {
            @Override
            public void run() {
                ISelection nodeTreeSelection = viewer.getSelection();
                if ((nodeTreeSelection != null)
                        && (nodeTreeSelection instanceof IStructuredSelection)) {
                    IStructuredSelection strucSelection = (IStructuredSelection) nodeTreeSelection;
                    Object selectedObject = strucSelection.getFirstElement();
                    if ((selectedObject instanceof Node)) {
                        NewNodeWizard newNodeWizard = new NewNodeWizard(
                                rootNode.getNodeCollection(),
                                (Node) selectedObject);
                        if (!newNodeWizard.hasErrors()) {
                            WizardDialog wd = new WizardDialog(
                                    Display.getDefault().getActiveShell(),
                                    newNodeWizard);
                            wd.setTitle(newNodeWizard.getWindowTitle());
                            wd.open();
                        } else {
                            showMessage(
                                    "Internal error occurred. Please try again later");
                        }

                        handleRefresh();
                    }
                } else {
                    showMessage("Invalid selection");
                }
            }
        };
        addNewNode.setToolTipText("Add a node in the network.");
        addNewNode
                .setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                        .getImageDescriptor(ISharedImages.IMG_OBJ_ADD));

        enableDisableNode = new Action("Enable/Disable") {
            @Override
            public void run() {
                ISelection selection = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getSelectionService()
                        .getSelection();
                if ((selection != null)
                        & (selection instanceof IStructuredSelection)) {
                    handleEnableDisable((IStructuredSelection) selection);
                }
            }
        };
        enableDisableNode.setToolTipText("Enable/Disable");
        enableDisableNode.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.DISABLE_NODE_ICON));

        showObjectDictionary = new Action("Show Object Dictionary") {
            @Override
            public void run() {
                try {

                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().showView(ObjectDictionaryView.ID);
                    viewer.setSelection(viewer.getSelection());

                } catch (PartInitException e) {
                    e.printStackTrace();
                    showMessage("Error openning Object Dictionary");
                }
            }
        };
        showObjectDictionary.setToolTipText("Show Object Dictionary");
        showObjectDictionary
                .setImageDescriptor(org.epsg.openconfigurator.Activator
                        .getImageDescriptor(IPluginImages.OBD_ICON));

        showPdoMapping = new Action("Show Mapping View") {
            @Override
            public void run() {

                try {
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().showView(MappingView.ID);
                    viewer.setSelection(viewer.getSelection());
                } catch (PartInitException e) {
                    e.printStackTrace();
                    showMessage("Error openning MappingView");
                }
            }
        };
        showPdoMapping.setToolTipText("Show Mapping View");
        showPdoMapping.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.MAPPING_ICON));

        showProperties = new Action("Properties") {

            @Override
            public void run() {
                super.run();
                try {
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage()
                            .showView(IPageLayout.ID_PROP_SHEET);
                    viewer.setSelection(viewer.getSelection());
                } catch (PartInitException e) {
                    e.printStackTrace();
                    showMessage("Error opening properties view");
                }
            }
        };
        showProperties.setToolTipText("Properties");
        showProperties.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.PROPERTIES_ICON));

        deleteNode = new Action("Remove") {
            @Override
            public void run() {

                ISelection selection = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getSelectionService()
                        .getSelection();
                if ((selection != null)
                        && (selection instanceof IStructuredSelection)) {
                    handleRemoveNode((IStructuredSelection) selection);
                }
            }
        };
        deleteNode.setToolTipText("Remove");
        deleteNode
                .setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                        .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));

        refreshAction = new Action("Refresh (F5)") {
            @Override
            public void run() {
                handleRefresh();
            }
        };
        refreshAction.setToolTipText("Refresh (F5)");
        refreshAction.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.REFRESH_ICON));
    }

    @Override
    public void propertyChanged(Object source, int propId) {
        handleRefresh();
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    @Override
    public void setFocus() {
        viewer.getControl().setFocus();
    }

    private void showMessage(String message) {
        MessageDialog.openInformation(viewer.getControl().getShell(),
                "POWERLINK Network", message);
    }

}
