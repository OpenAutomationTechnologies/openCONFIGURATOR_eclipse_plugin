/*******************************************************************************
 * @file   ParameterView.java
 *
 * @author Sree Hari Vignesh, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2017, Kalycito Infotech Private Limited
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;
import org.epsg.openconfigurator.model.DataTypeChoiceType;
import org.epsg.openconfigurator.model.LabelDescription;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.Parameter;
import org.epsg.openconfigurator.model.ParameterGroup;
import org.epsg.openconfigurator.model.ParameterReference;
import org.epsg.openconfigurator.model.VarDecleration;
import org.epsg.openconfigurator.resources.IPluginImages;

/**
 * Parameter view handle the parameter configuration operations for the nodes
 * and modules selected from the {@link IndustrialNetworkView} view.
 *
 * @see IndustrialNetworkView
 * @author Sree Hari Vignesh
 *
 */
public class ParameterView extends ViewPart implements IPropertyListener {

    /**
     * Class to bind zero objects with the tree view.
     *
     * @author Sree Hari
     *
     */
    private static class EmptyParameter {
        @Override
        public String toString() {
            return "Parameters not available.";
        }
    }

    /**
     * PatternFilter class to always show parameters after filtering based on
     * the input text.
     *
     */
    // It is not required to be a static inner class.
    private class ParameterFilter extends PatternFilter {

        @Override
        public Object[] filter(Viewer viewer, Object parent,
                Object[] elements) {
            ArrayList<Object> objList = new ArrayList<>();

            // Display sub-object after filtering of objects.
            if (parent instanceof ParameterGroup) {
                ParameterGroup paramGroup = (ParameterGroup) parent;
                List<ParameterReference> prmGrp = paramGroup
                        .getParameterRefList();
                objList.addAll(prmGrp);
            } else {
                Collections.addAll(objList,
                        super.filter(viewer, parent, elements));
            }

            return objList.toArray();
        }
    }

    /**
     * Part listener to listen to the changes of the source part.
     *
     * @see IndustrialNetworkView
     * @author Sree Hari
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
     * Label provider for the Parameter group and parameter reference
     *
     * @author Sree Hari
     *
     */
    private static class TreeLabelProvider extends LabelProvider {

        Image parameterIcon;
        Image parameterGroupIcon;
        Image parameterReferenceIcon;
        Image varDeclarationIcon;

        // It is not required to be a static inner class.
        TreeLabelProvider() {
            parameterGroupIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.OBD_PARAMETER_GROUP_ICON)
                    .createImage();
            parameterReferenceIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(
                            IPluginImages.OBD_PARAMETER_REFERENCE_ICON)
                    .createImage();
            parameterIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.MODULE_ICON)
                    .createImage();
            varDeclarationIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(
                            IPluginImages.OBD_PARAMETER_VAR_DECLARATION_ICON)
                    .createImage();
        }

        @Override
        public void dispose() {
            parameterGroupIcon.dispose();
            parameterReferenceIcon.dispose();
            parameterIcon.dispose();
            varDeclarationIcon.dispose();
        }

        @Override
        public Image getImage(Object element) {
            if (element instanceof EmptyParameter) {
                // No image is needed for empty contents.
                return null;
            } else if (element instanceof ParameterGroup) {
                return parameterGroupIcon;
            } else if (element instanceof ParameterReference) {
                return parameterReferenceIcon;
            } else if (element instanceof Parameter) {
                return parameterIcon;
            } else if (element instanceof VarDecleration) {
                return varDeclarationIcon;
            } else if (element instanceof String) {
                // No image is needed for empty contents.
                return null;
            }

            return PlatformUI.getWorkbench().getSharedImages()
                    .getImage(ISharedImages.IMG_OBJ_ELEMENT);
        }

        @Override
        public String getText(Object element) {
            if (element instanceof Parameter) {
                Parameter param = (Parameter) element;
                LabelDescription labelDesc = param.getLabelDescription();
                if (labelDesc != null) {
                    return labelDesc.getText();
                }
            } else if (element instanceof ParameterReference) {
                ParameterReference paramRef = (ParameterReference) element;
                LabelDescription labelDesc = paramRef.getLabelDescription();
                if (labelDesc != null) {
                    return labelDesc.getText();
                }
            } else if (element instanceof VarDecleration) {
                VarDecleration varDecl = (VarDecleration) element;
                if (varDecl.getName() != null) {
                    return varDecl.getName();
                }
                LabelDescription labelDesc = varDecl.getLabelDescription();
                if (labelDesc != null) {
                    return labelDesc.getText();
                }
            } else if (element instanceof ParameterGroup) {
                ParameterGroup pgmGrp = (ParameterGroup) element;
                LabelDescription labelDesc = pgmGrp.getLabel();
                if (labelDesc != null) {
                    return labelDesc.getText();
                }
                return pgmGrp.getParamGroupUniqueId();
            } else if (element instanceof String) {
                return "Parameters not available.";
            }
            return element == null ? "" : element.toString();//$NON-NLS-1$
        }
    }

    public static final String ID = "org.epsg.openconfigurator.views.ParameterView"; //$NON-NLS-1$

    public static final String PARAMETER_VIEW_TITLE = "Parameter";

    public static final String PARAMETER_PROPERTIES = "Properties";

    private Action propertiesAction;

    private Action refreshAction;

    /**
     * Label provider.
     */
    private ILabelProvider labelProvider = new TreeLabelProvider();
    private boolean nodeSelection = false;

    private boolean moduleSelection = false;

    /**
     * The corresponding node instance for which the Parameter corresponds to.
     */
    private Node nodeObj;

    /**
     * The corresponding module instance for which the Parameter corresponds to.
     */
    private Module moduleObj;

    /**
     * Source workbench part.
     */
    private IWorkbenchPart sourcePart;

    /**
     * Listener instance to listen to the changes in the source part.
     */
    private PartListener partListener = new PartListener();

    /**
     * Parameter tree viewer.
     */
    private TreeViewer treeViewer;

    /**
     * Selection listener to update the objects and sub-objects in the Object
     * dictionary view.
     */
    ISelectionListener listener = new ISelectionListener() {
        @Override
        public void selectionChanged(IWorkbenchPart part,
                ISelection selection) {

            setPartName(PARAMETER_VIEW_TITLE);

            if (treeViewer == null) {
                return;
            }

            treeViewer.setInput(new EmptyParameter());

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
                    moduleSelection = false;
                    nodeSelection = true;
                    // Set input only if the node is enabled.
                    if (nodeObj.getISO15745ProfileContainer() != null) {
                        if (nodeObj.isEnabled()) {
                            sourcePart = part;
                            setPartName(nodeObj.getNodeIDWithName());
                            if (!nodeObj.hasError()) {
                                treeViewer.setInput(nodeObj);
                            }
                        }
                    }
                } else if (selectedObj instanceof Module) {
                    moduleObj = (Module) selectedObj;
                    nodeSelection = false;
                    moduleSelection = true;
                    if (moduleObj.getISO15745ProfileContainer() != null) {
                        if (moduleObj.isEnabled()) {
                            sourcePart = part;
                            setPartName(moduleObj.getModuleName());
                            if (!moduleObj.hasError()) {
                                treeViewer.setInput(moduleObj);
                            }
                        }
                    }
                } else {
                    nodeSelection = false;
                    moduleSelection = false;
                }
            }

            if (sourcePart != null) {
                sourcePart.getSite().getPage().addPartListener(partListener);
                treeViewer.expandAll();
            }
        }
    };

    /**
     * Content provider to list the object, sub-objects in an hierarchical
     * order.
     */
    private ITreeContentProvider parameterContentProvider = new ITreeContentProvider() {

        @Override
        public void dispose() {
        }

        @Override
        public Object[] getChildren(Object parentElement) {

            if (parentElement instanceof ParameterReference) {
                // Do nothing.
                // TODO: Implement for future enhancement.
            } else if (parentElement instanceof Parameter) {
                Parameter param = (Parameter) parentElement;
                if (param.getDataTypeChoice() == DataTypeChoiceType.STRUCT) {
                    List<VarDecleration> varDeclList = param.getStructDataType()
                            .getVariables();
                    for (VarDecleration var : varDeclList) {
                        System.err.println("Name:" + var.getName());
                    }
                    return varDeclList.toArray();
                } else if (param
                        .getDataTypeChoice() == DataTypeChoiceType.SIMPLE) {
                    // Ignore; This does not have any child variables.
                    // TODO: Implement for future enhancement.
                } else {
                    System.err
                            .println("Unhandled " + param.getDataTypeChoice());
                }
            } else if (parentElement instanceof ParameterGroup) {
                ParameterGroup paramGrp = (ParameterGroup) parentElement;
                return paramGrp.getVisibleObjects().toArray();
            } else {
                System.err.println("GetChildren" + parentElement);
            }
            return new Object[0];
        }

        @Override
        public Object[] getElements(Object inputElement) {

            if (inputElement instanceof Node) {
                Node nodeObj = (Node) inputElement;
                LinkedHashSet<Object> visibleObjectsList = new LinkedHashSet<>();
                List<ParameterGroup> paramGrupList = nodeObj
                        .getObjectDictionary().getParameterGroupList();
                for (ParameterGroup pgmGrp : paramGrupList) {
                    System.err
                            .println("--------> " + pgmGrp.getLabel().getText()
                                    + " m:" + pgmGrp.isConditionsMet() + " v:"
                                    + pgmGrp.isGroupLevelVisible());

                    if (pgmGrp.isConditionsMet()) {
                        if ((pgmGrp.isGroupLevelVisible())
                                && (pgmGrp.isConfigParameter())) {
                            visibleObjectsList.add(pgmGrp);
                        } else if (pgmGrp.isConfigParameter()) {
                            List<ParameterReference> prmRefList = pgmGrp
                                    .getParameterRefList();
                            for (ParameterReference prmRef : prmRefList) {
                                if (prmRef.isVisible()) {
                                    visibleObjectsList.add(prmRef);
                                }
                            }

                            List<ParameterGroup> prmGrpList = pgmGrp
                                    .getParameterGroupList();
                            for (int count = 1; count < prmGrpList
                                    .size(); count++) {
                                for (ParameterGroup prmGrp : prmGrpList) {
                                    if (prmGrp.isGroupLevelVisible()
                                            && prmGrp.isConfigParameter()) {
                                        visibleObjectsList.add(prmGrp);
                                    } else if (prmGrp.isConfigParameter()) {
                                        List<ParameterReference> pgmRefList = prmGrp
                                                .getParameterRefList();
                                        for (ParameterReference prmRef : pgmRefList) {
                                            if (prmRef.isVisible()) {
                                                visibleObjectsList.add(prmRef);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            System.err.println(
                                    "No parameter groups can be configured ");
                        }

                    } else {
                        System.err.println(
                                "parameter group cannot be displayed due to false condition set");
                    }
                }
                if (visibleObjectsList.size() == 0) {
                    visibleObjectsList.add("");
                }
                return visibleObjectsList.toArray();

            } else if (inputElement instanceof Module) {
                Module moduleObj = (Module) inputElement;
                LinkedHashSet<Object> visibleObjectsList = new LinkedHashSet<>();
                List<ParameterGroup> paramGrupList = moduleObj
                        .getObjectDictionary().getParameterGroupList();
                for (ParameterGroup pgmGrp : paramGrupList) {
                    System.err.println(
                            "Module --------> " + pgmGrp.getLabel().getText()
                                    + " m:" + pgmGrp.isConditionsMet() + " v:"
                                    + pgmGrp.isGroupLevelVisible());

                    if (pgmGrp.isConditionsMet()) {
                        if ((pgmGrp.isGroupLevelVisible())
                                && (pgmGrp.isConfigParameter())) {
                            visibleObjectsList.add(pgmGrp);
                        } else if (pgmGrp.isConfigParameter()) {
                            List<ParameterReference> prmRefList = pgmGrp
                                    .getParameterRefList();
                            for (ParameterReference prmRef : prmRefList) {
                                if (prmRef.isVisible()) {
                                    visibleObjectsList.add(prmRef);
                                }
                            }

                            List<ParameterGroup> prmGrpList = pgmGrp
                                    .getParameterGroupList();
                            for (int count = 1; count < prmGrpList
                                    .size(); count++) {
                                for (ParameterGroup prmGrp : prmGrpList) {
                                    if (prmGrp.isGroupLevelVisible()
                                            && prmGrp.isConfigParameter()) {
                                        visibleObjectsList.add(prmGrp);
                                    } else if (prmGrp.isConfigParameter()) {
                                        List<ParameterReference> pgmRefList = prmGrp
                                                .getParameterRefList();
                                        for (ParameterReference prmRef : pgmRefList) {
                                            if (prmRef.isVisible()) {
                                                visibleObjectsList.add(prmRef);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            System.err.println(
                                    "No parameter groups can be configured ");
                        }

                    } else {
                        System.err.println(
                                "parameter group cannot be displayed due to false condition set");
                    }
                }
                if (visibleObjectsList.size() == 0) {
                    visibleObjectsList.add("");
                }
                return visibleObjectsList.toArray();

            }

            return new Object[] { new EmptyParameter() };
        }

        @Override
        public Object getParent(Object element) {
            System.err.println("getParent " + element);
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof Parameter) {
                Parameter param = (Parameter) element;
                switch (param.getDataTypeChoice()) {
                    case STRUCT:
                        System.err.println("hasChildren Parameter "
                                + param.getDataTypeChoice() + " size:"
                                + param.getStructDataType().getVariables()
                                        .size());
                        return ((param.getStructDataType().getVariables()
                                .size() > 0) ? true : false);
                    default:
                        break;
                }
            } else if (element instanceof ParameterGroup) {
                ParameterGroup paramGrp = (ParameterGroup) element;
                return paramGrp.hasVisibleObjects();
            }
            return false;
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput,
                Object newInput) {
        }
    };

    private void contributeToActionBars() {
        IActionBars bars = getViewSite().getActionBars();

        fillLocalToolBar(bars.getToolBarManager());
        bars.updateActionBars();
    }

    /**
     * Create the actions.
     */
    private void createActions() {
        refreshAction = new Action("Refresh") {
            @Override
            public void run() {
                if (moduleSelection) {
                    treeViewer.setInput(moduleObj);
                }
                if (nodeSelection) {
                    treeViewer.setInput(nodeObj);
                } else {
                    treeViewer.refresh();
                    if (moduleSelection) {
                        treeViewer.setInput(moduleObj);
                    }
                    if (nodeSelection) {
                        treeViewer.setInput(nodeObj);
                    }
                }
            }
        };
        refreshAction.setToolTipText("Refresh");
        refreshAction.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.REFRESH_ICON));

        propertiesAction = new Action(PARAMETER_PROPERTIES) {

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

    @Override
    public void createPartControl(Composite parent) {

        PatternFilter filter = new ParameterFilter();
        // PatternFilter filter = new PatternFilter();
        filter.setIncludeLeadingWildcard(true);
        FilteredTree tree = new FilteredTree(parent,
                SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL, filter, true);

        treeViewer = tree.getViewer();

        treeViewer.setContentProvider(parameterContentProvider);
        treeViewer.setLabelProvider(new DecoratingLabelProvider(labelProvider,
                PlatformUI.getWorkbench().getDecoratorManager()
                        .getLabelDecorator()));
        treeViewer.setInput(new Object());
        treeViewer.expandAll();
        createContextMenu(treeViewer);

        createActions();

        contributeToActionBars();
        hookDoubleClickAction();

        getViewSite().getPage().addSelectionListener(IndustrialNetworkView.ID,
                listener);
        getViewSite().setSelectionProvider(treeViewer);

    }

    protected void fillContextMenu(IMenuManager contextMenu) {

        contextMenu.add(propertiesAction);
    }

    private void fillLocalToolBar(IToolBarManager manager) {
        manager.add(refreshAction);
    }

    /**
     * Double Click action for node on Object dictionary view
     */
    private void hookDoubleClickAction() {
        treeViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
            public void doubleClick(DoubleClickEvent event) {
                propertiesAction.run();
            }
        });
    }

    @Override
    public void propertyChanged(Object source, int propId) {

        if (moduleSelection) {
            treeViewer.setInput(moduleObj);
        } else if (nodeSelection) {
            treeViewer.setInput(nodeObj);
        }

    }

    @Override
    public void setFocus() {
        treeViewer.getControl().setFocus();

    }

}
