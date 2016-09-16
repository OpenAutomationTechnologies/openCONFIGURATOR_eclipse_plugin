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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
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
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.model.VarDecleration;
import org.epsg.openconfigurator.resources.IPluginImages;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;

/**
 * View to list the objects and subobjects of a node.
 *
 * @author Ramakrishnan P
 *
 */
public class ObjectDictionaryView extends ViewPart
        implements IPropertyListener {

    /**
     * Class to bind zero objects with the tree view.
     *
     * @author Ramakrishnan P
     *
     */
    private class EmptyObjectDictionary {
        @Override
        public String toString() {
            if (parametersVisible) {
                return "Parameters not available.";
            }
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
     * PatternFilter class to always show sub objects after filtering of
     * objects.
     */
    // It is not required to be a static inner class.
    private class PowerlinkObjectPatternFilter extends PatternFilter {

        @Override
        public Object[] filter(Viewer viewer, Object parent,
                Object[] elements) {
            ArrayList<Object> objList = new ArrayList<>();

            // Display sub-object after filtering of objects.
            if (parent instanceof PowerlinkObject) {
                PowerlinkObject plkobj = (PowerlinkObject) parent;
                List<PowerlinkSubobject> plksub = plkobj.getSubObjects();
                objList.addAll(plksub);
            } else {
                Collections.addAll(objList,
                        super.filter(viewer, parent, elements));
            }

            return objList.toArray();
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
        Image parameterIcon;
        Image parameterGroupIcon;
        Image parameterReferenceIcon;
        Image varDeclarationIcon;

        // It is not required to be a static inner class.
        TreeLabelProvider() {
            objectIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.OBD_OBJECT_ICON)
                    .createImage();
            subObjectIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.OBD_SUB_OBJECT_ICON)
                    .createImage();
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
            objectIcon.dispose();
            subObjectIcon.dispose();
            parameterGroupIcon.dispose();
            parameterReferenceIcon.dispose();
            parameterIcon.dispose();
            varDeclarationIcon.dispose();
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
            if (element instanceof PowerlinkObject) {
                PowerlinkObject object = (PowerlinkObject) element;
                if (object.isModuleObject()) {
                    long objectIndex = OpenConfiguratorLibraryUtils
                            .getModuleObjectsIndex(object.getModule(),
                                    object.getId());
                    if (objectIndex != 0) {
                        return object.getNameWithId(objectIndex);
                    }
                }
                return ((PowerlinkObject) element).getNameWithId();
            } else if (element instanceof PowerlinkSubobject) {
                PowerlinkSubobject subObject = (PowerlinkSubobject) element;
                if (subObject.isModule()) {
                    int subObjectIndex = OpenConfiguratorLibraryUtils
                            .getModuleObjectsSubIndex(subObject.getModule(),
                                    subObject, subObject.getObject().getId());
                    return subObject.getNameWithId(subObjectIndex);
                }
                return ((PowerlinkSubobject) element).getNameWithId();
            } else if (element instanceof Parameter) {
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
                return pgmGrp.getUniqueId();
            } else if (element instanceof String) {
                return "Parameters not available.";
            }
            return element == null ? "" : element.toString();//$NON-NLS-1$
        }
    }

    public static final String ID = "org.epsg.openconfigurator.views.ObjectDictionaryView"; //$NON-NLS-1$

    public static final String OBJECT_DICTIONARY_VIEW_TITLE = "Object Dictionary";

    // Object dictionary filters title
    public static final String HIDE_NON_MAPPABLE_OBJECTS = "Hide Non Mappable Objects";

    public static final String HIDE_COMMUNICATION_PROFILE_AREA_OBJECTS = "Hide Communication Profile Area Objects(0x1000-0x1FFF)";
    public static final String HIDE_STANDARDISED_DEVICE_PROFILE_AREA_OBJECTS = "Hide Standardised Device Profile Area Objects(0x6000-0x9FFF)";
    public static final String HIDE_NON_FORCED_OBJECTS = "Hide NonForced Objects";
    public static final String OBJECT_PROPERTIES = "Properties";

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
                    moduleSelection = false;
                    nodeSelection = true;
                    // Set input only if the node is enabled.
                    if (nodeObj.isEnabled()) {
                        sourcePart = part;
                        setPartName(nodeObj.getNodeIDWithName());
                        treeViewer.setInput(nodeObj);
                    }
                } else if (selectedObj instanceof Module) {
                    moduleObj = (Module) selectedObj;
                    nodeSelection = false;
                    moduleSelection = true;
                    if (moduleObj.isEnabled()) {
                        sourcePart = part;
                        setPartName(moduleObj.getModuleName());
                        treeViewer.setInput(moduleObj);
                    }
                } else {
                    nodeSelection = false;
                    moduleSelection = false;
                }
            }

            if (sourcePart != null) {
                sourcePart.getSite().getPage().addPartListener(partListener);
                if (parametersVisible) {
                    treeViewer.expandAll();
                }
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
            } else if (parentElement instanceof ParameterReference) {
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
                if (parametersVisible) {

                    LinkedHashSet<Object> visibleObjectsList = new LinkedHashSet<>();
                    List<ParameterGroup> paramGrupList = nodeObj
                            .getObjectDictionary().getParameterGroupList();
                    for (ParameterGroup pgmGrp : paramGrupList) {
                        System.err.println(
                                "--------> " + pgmGrp.getLabel().getText()
                                        + " m:" + pgmGrp.isConditionsMet()
                                        + " v:" + pgmGrp.isGroupLevelVisible());

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
                                                    visibleObjectsList
                                                            .add(prmRef);
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
                List<PowerlinkObject> objectsList = nodeObj
                        .getObjectDictionary().getObjectsList();

                return objectsList.toArray();
            } else if (inputElement instanceof Module) {
                Module moduleObj = (Module) inputElement;
                if (parametersVisible) {

                    LinkedHashSet<Object> visibleObjectsList = new LinkedHashSet<>();
                    List<ParameterGroup> paramGrupList = moduleObj
                            .getObjectDictionary().getParameterGroupList();
                    for (ParameterGroup pgmGrp : paramGrupList) {
                        System.err.println("Module --------> "
                                + pgmGrp.getLabel().getText() + " m:"
                                + pgmGrp.isConditionsMet() + " v:"
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
                                                    visibleObjectsList
                                                            .add(prmRef);
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
                List<PowerlinkObject> objectsList = moduleObj
                        .getObjectDictionary().getObjectsList();
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
            } else if (element instanceof Parameter) {
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

    private boolean communicationProfileObjectsVisible = true;
    private boolean standardisedDeviceProfileObjectsVisible = true;
    private boolean nonMappableObjectsVisible = true;
    private boolean forcedObjectsVisible = true;
    private boolean parametersVisible = false;

    private boolean nodeSelection = false;
    private boolean moduleSelection = false;

    private Action hideCommunicationProfileObjects;
    private Action hideStandardisedDeviceProfileObjects;
    private Action hideNonMappableObjects;
    private Action hideNonForcedObjects;
    private Action propertiesAction;
    private Action toggleParameterView;
    private Action refreshAction;

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

    private Module moduleObj;

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
        refreshAction = new Action("Refresh") {
            @Override
            public void run() {
                if (parametersVisible) {
                    if (moduleSelection) {
                        treeViewer.setInput(moduleObj);
                    }
                    if (nodeSelection) {
                        treeViewer.setInput(nodeObj);
                    }
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

        toggleParameterView = new Action("Switch to Parameter View",
                IAction.AS_CHECK_BOX) {
            @Override
            public void run() {
                if (toggleParameterView.isChecked()) {
                    parametersVisible = true;
                    toggleParameterView
                            .setToolTipText("Switch to Object Dictionary View");
                    contributeToActionBars();
                } else {
                    parametersVisible = false;
                    toggleParameterView
                            .setToolTipText("Switch to Parameter View");
                    contributeToActionBars();
                }
                treeViewer.refresh();
            }
        };
        toggleParameterView.setToolTipText("Switch to Parameter View");
        toggleParameterView.setImageDescriptor(
                org.epsg.openconfigurator.Activator.getImageDescriptor(
                        IPluginImages.OBD_PARAMETER_GROUP_ICON));

        hideNonMappableObjects = new Action(HIDE_NON_MAPPABLE_OBJECTS,
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
        hideNonMappableObjects.setToolTipText(HIDE_NON_MAPPABLE_OBJECTS);
        hideNonMappableObjects.setImageDescriptor(
                org.epsg.openconfigurator.Activator.getImageDescriptor(
                        IPluginImages.OBD_HIDE_NON_MAPPABLE_ICON));
        hideNonMappableObjects.setChecked(false);

        hideCommunicationProfileObjects = new Action(
                HIDE_COMMUNICATION_PROFILE_AREA_OBJECTS, IAction.AS_CHECK_BOX) {
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
        hideCommunicationProfileObjects
                .setToolTipText(HIDE_COMMUNICATION_PROFILE_AREA_OBJECTS);
        hideCommunicationProfileObjects.setImageDescriptor(
                org.epsg.openconfigurator.Activator.getImageDescriptor(
                        IPluginImages.OBD_HIDE_COMMUNICATION_DEVICE_PROFILE_ICON));
        hideCommunicationProfileObjects.setChecked(false);

        hideStandardisedDeviceProfileObjects = new Action(
                HIDE_STANDARDISED_DEVICE_PROFILE_AREA_OBJECTS,
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
        hideStandardisedDeviceProfileObjects
                .setToolTipText(HIDE_STANDARDISED_DEVICE_PROFILE_AREA_OBJECTS);
        hideStandardisedDeviceProfileObjects.setImageDescriptor(
                org.epsg.openconfigurator.Activator.getImageDescriptor(
                        IPluginImages.OBD_HIDE_STANDARDISED_DEVICE_PROFILE_ICON));
        hideStandardisedDeviceProfileObjects.setChecked(false);

        hideNonForcedObjects = new Action(HIDE_NON_FORCED_OBJECTS,
                IAction.AS_CHECK_BOX) {

            @Override
            public void run() {
                if (hideNonForcedObjects.isChecked()) {
                    forcedObjectsVisible = false;
                } else {
                    forcedObjectsVisible = true;
                }
                treeViewer.refresh();
            }
        };
        hideNonForcedObjects.setToolTipText(HIDE_NON_FORCED_OBJECTS);
        hideNonForcedObjects.setImageDescriptor(
                org.epsg.openconfigurator.Activator.getImageDescriptor(
                        IPluginImages.OBD_HIDE_NON_FORCED_ICON));
        hideNonForcedObjects.setChecked(false);

        propertiesAction = new Action(OBJECT_PROPERTIES) {
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

        PatternFilter filter = new PowerlinkObjectPatternFilter();
        // PatternFilter filter = new PatternFilter();
        filter.setIncludeLeadingWildcard(true);
        FilteredTree tree = new FilteredTree(parent,
                SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL, filter, true);

        treeViewer = tree.getViewer();

        treeViewer.setContentProvider(objectDictionaryContentProvider);
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
        treeViewer.addFilter(new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {

                if (element instanceof PowerlinkObject) {
                    PowerlinkObject obj = (PowerlinkObject) element;
                    if (!communicationProfileObjectsVisible) {
                        if ((obj.getId() >= IPowerlinkConstants.COMMUNICATION_PROFILE_START_INDEX)
                                && (obj.getId() < IPowerlinkConstants.MANUFACTURER_PROFILE_START_INDEX)) {
                            return false;
                        }

                    }

                    if (!standardisedDeviceProfileObjectsVisible) {
                        if ((obj.getId() >= IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_START_INDEX)
                                && (obj.getId() <= IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_END_INDEX)) {
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
                    if (!forcedObjectsVisible) {
                        if (!obj.isObjectForced()) {
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
        if (parametersVisible) {
            manager.removeAll();
            manager.add(refreshAction);
            manager.add(toggleParameterView);
        } else {
            manager.removeAll();
            manager.add(refreshAction);
            manager.add(toggleParameterView);
            manager.add(new Separator());
            manager.add(hideNonMappableObjects);
            manager.add(hideCommunicationProfileObjects);
            manager.add(hideStandardisedDeviceProfileObjects);
            manager.add(hideNonForcedObjects);
        }
    }

    public Control getControl() {
        if (treeViewer == null) {
            return null;
        }
        return treeViewer.getControl();
    }

    public void handleRefresh() {

        if (parametersVisible) {
            treeViewer.setInput(moduleObj);
            treeViewer.expandAll();
        } else {
            treeViewer.setInput(nodeObj);
        }
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
        if (parametersVisible) {
            treeViewer.setInput(moduleObj);
        } else {
            treeViewer.setInput(nodeObj);
        }
    }

    @Override
    public void setFocus() {
        treeViewer.getControl().setFocus();
    }

}
