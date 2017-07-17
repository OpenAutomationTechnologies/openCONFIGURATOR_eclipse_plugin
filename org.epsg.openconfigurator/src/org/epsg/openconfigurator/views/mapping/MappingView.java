/*******************************************************************************
 * @file   MappingView.java
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

package org.epsg.openconfigurator.views.mapping;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import org.epsg.openconfigurator.adapters.AbstractNodePropertySource;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.AbstractPowerlinkObject;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.IManagingNodeProperties;
import org.epsg.openconfigurator.model.INetworkProperties;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PdoChannel;
import org.epsg.openconfigurator.model.PdoType;
import org.epsg.openconfigurator.model.PlkOperationMode;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.model.RpdoChannel;
import org.epsg.openconfigurator.model.TpdoChannel;
import org.epsg.openconfigurator.resources.IPluginImages;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.views.IndustrialNetworkView;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xdd.TObject;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectAccessType;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectPDOMapping;
import org.jdom2.JDOMException;

/**
 * Mapping editor based view to handle the mapping operations for the nodes
 * selected from the {@link IndustrialNetworkView} view.
 *
 * @see IndustrialNetworkView
 * @author Ramakrishnan P
 *
 */
public class MappingView extends ViewPart {

    /**
     * Listener to handle the PDO channel selection change events.
     *
     * @author Ramakrishnan P
     *
     */
    private class ChannelSelectionChangedListener
            implements ISelectionChangedListener {

        @Override
        public void selectionChanged(SelectionChangedEvent event) {
            tpdoActionsbuttonGroup.setVisible(false);
            rpdoActionsbuttonGroup.setVisible(false);
            ISelection selection = event.getSelection();
            if (selection instanceof IStructuredSelection) {

                IStructuredSelection ss = (IStructuredSelection) selection;

                if (ss.size() > 1) {
                    System.err.println("Multiple selection not handled.");
                    return;
                }
                Object selectedObj = ss.getFirstElement();

                if (selectedObj == null) {
                    showMessageWindow(MessageDialog.ERROR,
                            "Select any PDO channel.");
                    return;
                }

                if (selectedObj instanceof PdoChannel) {
                    PdoChannel pdoChannel = (PdoChannel) selectedObj;
                    setPdoGuiControlsEnabled(pdoChannel.getPdoType(), true);
                    updateTargetNodeIdList();
                    // Only on network view changes.
                    setPdoNodeId(pdoChannel);
                    updatePdoTable(pdoChannel);
                    updateEnabledMappingEntries(pdoChannel);
                    updateChannelSize(pdoChannel);
                }
            }
        }
    }

    /**
     * Listener to handle the Clear button selection change events.
     *
     * @author Ramakrishnan P
     *
     */
    private class ClearAllObjectsButtonSelectionListenr
            extends SelectionAdapter {
        private ComboViewer channelComboViewer;
        private TableViewer tableViewer;

        public ClearAllObjectsButtonSelectionListenr(
                ComboViewer channelComboViewer, TableViewer tableViewer) {
            this.tableViewer = tableViewer;
            this.channelComboViewer = channelComboViewer;
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            ISelection selection = channelComboViewer.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection stSelection = (IStructuredSelection) selection;
                Object selectedObject = stSelection.getFirstElement();
                if (selectedObject instanceof PdoChannel) {
                    PdoChannel rpdoChannel = (PdoChannel) selectedObject;

                    Result res = OpenConfiguratorLibraryUtils
                            .clearChannelMapping(rpdoChannel);
                    if (!res.IsSuccessful()) {
                        showMessage(res);
                    }

                    try {
                        OpenConfiguratorProjectUtils
                                .updatePdoChannelActualValue(rpdoChannel);
                    } catch (JDOMException | IOException e1) {
                        OpenConfiguratorMessageConsole.getInstance()
                                .printErrorMessage(e1.getMessage(),
                                        nodeObj.getProject().getName());
                        e1.printStackTrace();
                    }

                    tableViewer.refresh();

                    updateEnabledMappingEntries(rpdoChannel);
                    updateChannelSize(rpdoChannel);
                }
            } else {
                System.err.println("Invalid selection type" + selection);
            }
        }
    }

    /**
     * Listener to handle the the update no.of entries modification events..
     *
     * @author Ramakrishnan P
     */
    private class EnabledEntriesTextModifyListener implements ModifyListener {

        private ComboViewer channelComboViewer;
        private TableViewer tableViewer;

        public EnabledEntriesTextModifyListener(ComboViewer channelComboViewer,
                TableViewer tableViewer) {
            this.tableViewer = tableViewer;
            this.channelComboViewer = channelComboViewer;
        }

        @Override
        public void modifyText(ModifyEvent e) {
            if (e.widget instanceof Text) {
                Text pdoEnabledMappingEntries = (Text) e.widget;

                String enabledEntriesActValue = pdoEnabledMappingEntries
                        .getText();

                int enabledEntries = 0;
                if (!enabledEntriesActValue.isEmpty()) {
                    enabledEntries = Integer.decode(enabledEntriesActValue);
                }

                ISelection selection = channelComboViewer.getSelection();
                if (selection instanceof IStructuredSelection) {
                    IStructuredSelection stSelection = (IStructuredSelection) selection;
                    Object selectedObject = stSelection.getFirstElement();
                    if (selectedObject instanceof PdoChannel) {
                        PdoChannel pdoChannel = (PdoChannel) selectedObject;
                        int availableSubObjects = pdoChannel.getMappingParam()
                                .getSubObjects().size() - 1;
                        if (enabledEntries > availableSubObjects) {
                            showMessageWindow(MessageDialog.ERROR,
                                    "Enabled mapping entries ("
                                            + enabledEntriesActValue
                                            + ") exceeds the available subobjects("
                                            + availableSubObjects + ").");
                            return;
                        }

                        PowerlinkSubobject numberOfEntriesSubObject = pdoChannel
                                .getMappingParam().getSubObject((short) 0);
                        Result res = OpenConfiguratorLibraryUtils
                                .setSubObjectActualValue(
                                        numberOfEntriesSubObject,
                                        enabledEntriesActValue);
                        if (!res.IsSuccessful()) {
                            showMessage(res);
                            return;
                        }

                        updateChannelSize(pdoChannel);

                        try {
                            numberOfEntriesSubObject.setActualValue(
                                    enabledEntriesActValue, true);
                            pdoChannel.getNode().getProject().refreshLocal(
                                    IResource.DEPTH_INFINITE,
                                    new NullProgressMonitor());
                        } catch (JDOMException | IOException
                                | CoreException e1) {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printErrorMessage(e1.getMessage(),
                                            nodeObj.getProject().getName());
                            e1.printStackTrace();
                        }
                        tpdoEnabledEntriesCount = getEnabledEntriesCount(
                                pdoChannel.getPdoType());
                        rpdoEnabledEntriesCount = getEnabledEntriesCount(
                                pdoChannel.getPdoType());
                        tableViewer.refresh();
                    }
                } else {
                    System.err.println("Invalid selection type" + selection);
                }
            }
        }
    }

    /**
     * Content provider to list the forced objects and sub_objects of node and
     * module.
     *
     * @author Sree Hari Vignesh B
     *
     */
    private static class ForcedObjectContentProvider
            implements ITreeContentProvider {

        public ForcedObjectContentProvider() {
        }

        @Override
        public void dispose() {
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            return new Object[0];
        }

        @Override
        public Object[] getElements(Object inputElement) {

            if (inputElement instanceof Node) {
                Node node = (Node) inputElement;
                String[] forcedObjs = node.getForcedObjectsString()
                        .split("\\;");
                return forcedObjs;
            }
            if (inputElement instanceof Module) {
                Module module = (Module) inputElement;
                String[] forcedObjs = module.getForcedObjectsString()
                        .split("\\;");
                return forcedObjs;
            }
            return new Object[0];
        }

        @Override
        public Object getParent(Object element) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput,
                Object newInput) {
        }
    }

    /**
     * Label provider for the forced objects and sub-objects.
     *
     * @author Sree Hari Vignesh B
     *
     */
    private static class ForcedObjectLabelProvider extends LabelProvider {

        Image objectIcon;
        Image subObjectIcon;

        public ForcedObjectLabelProvider() {

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
        public Image getImage(Object obj) {
            if (obj instanceof String) {
                String objIndex = (String) obj;
                if (!objIndex.isEmpty()) {
                    if (objIndex.contains("/")) {
                        return subObjectIcon;
                    }
                    return objectIcon;
                }
            }
            return null;
        }

        @Override
        public String getText(Object element) {
            if (element instanceof String) {
                String objIndex = (String) element;
                if (objIndex.isEmpty()) {
                    return NO_FORCED_OBJECTS;
                }
                return objIndex;
            }
            return StringUtils.EMPTY;
        }
    }

    /**
     * Listener to handle the map all available objects button selection events.
     *
     * @author Ramakrishnan P
     *
     */
    private class MapAvailableObjectsButtonSelectionListener
            extends SelectionAdapter {

        private ComboViewer channelComboViewer;
        private TableViewer tableViewer;

        public MapAvailableObjectsButtonSelectionListener(
                ComboViewer channelComboViewer, TableViewer tableViewer) {
            this.tableViewer = tableViewer;
            this.channelComboViewer = channelComboViewer;
        }

        @Override
        public void widgetSelected(SelectionEvent event) {
            ISelection selection = channelComboViewer.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection stSelection = (IStructuredSelection) selection;
                Object selectedObject = stSelection.getFirstElement();
                if (selectedObject instanceof PdoChannel) {
                    PdoChannel pdoChannel = (PdoChannel) selectedObject;
                    Result res = OpenConfiguratorLibraryUtils
                            .mappAvailableObjects(pdoChannel);
                    if (!res.IsSuccessful()) {
                        showMessage(res);
                    }

                    try {
                        OpenConfiguratorProjectUtils
                                .updatePdoChannelActualValue(pdoChannel);
                    } catch (JDOMException | IOException e) {
                        OpenConfiguratorMessageConsole.getInstance()
                                .printErrorMessage(e.getMessage(),
                                        nodeObj.getProject().getName());
                        e.printStackTrace();
                    }
                    tableViewer.refresh();
                    resizeTable(tableViewer, new int[] { 4, 5 });
                    updateEnabledMappingEntries(pdoChannel);
                    updateChannelSize(pdoChannel);
                } else {
                    System.err.println("TpdoChannel is not selected");
                }
            }
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
            updateTargetNodeIdList();
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
     * Listener to handle the clear the selected Mapping object events.
     *
     * @author Ramakrishnan P
     *
     */
    private class PdoActionsClearButtonSelectionListener
            extends SelectionAdapter {
        private ComboViewer channelComboViewer;
        private TableViewer tableViewer;

        public PdoActionsClearButtonSelectionListener(
                ComboViewer channelComboViewer, TableViewer tableViewer) {

            this.tableViewer = tableViewer;
            this.channelComboViewer = channelComboViewer;
        }

        // Remove the selection and refresh the view
        @Override
        public void widgetSelected(SelectionEvent e) {
            ISelection selection = channelComboViewer.getSelection();

            if (selection instanceof IStructuredSelection) {
                IStructuredSelection stSelection = (IStructuredSelection) selection;
                Object selectedObject = stSelection.getFirstElement();
                if (selectedObject instanceof PdoChannel) {
                    PdoChannel pdoChannel = (PdoChannel) selectedObject;

                    ISelection rowSelection = tableViewer.getSelection();
                    if (rowSelection instanceof IStructuredSelection) {
                        IStructuredSelection rowStSelection = (IStructuredSelection) rowSelection;
                        Object selectedRowObject = rowStSelection
                                .getFirstElement();
                        if (selectedRowObject instanceof PowerlinkSubobject) {
                            PowerlinkSubobject currentRowData = (PowerlinkSubobject) selectedRowObject;
                            Result res = OpenConfiguratorLibraryUtils
                                    .clearSelectedmappingobject(pdoChannel,
                                            currentRowData);
                            if (!res.IsSuccessful()) {
                                showMessage(res);
                            }

                            try {
                                OpenConfiguratorProjectUtils
                                        .updatePdoChannelActualValue(
                                                pdoChannel);
                            } catch (JDOMException | IOException e1) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printErrorMessage(e1.getMessage(),
                                                nodeObj.getProject().getName());
                                e1.printStackTrace();
                            }

                            tableViewer.refresh();
                            resizeTable(tableViewer, new int[] { 4, 5 });
                            updateEnabledMappingEntries(pdoChannel);
                            updateChannelSize(pdoChannel);
                        }
                    }
                }
            } else {
                System.err.println("Invalid selection type" + selection);
            }
        }
    }

    /**
     * Provides combobox based editing support to map the Objects and
     * Sub-Objects to the PDO channels.
     *
     * @author Ramakrishnan P
     *
     */
    private class PdoMappingObjectColumnEditingSupport extends EditingSupport {

        private ComboBoxViewerCellEditor cellEditor;
        private ComboViewer channelViewer;

        private PdoMappingObjectColumnEditingSupport(ColumnViewer viewer,
                ComboViewer channelViewer) {
            super(viewer);
            this.channelViewer = channelViewer;

            cellEditor = new ComboBoxViewerCellEditor(
                    (Composite) getViewer().getControl(), SWT.READ_ONLY);

            cellEditor.setActivationStyle(1);

            cellEditor.setContentProvider(ArrayContentProvider.getInstance());
            cellEditor.setLabelProvider(new MappableObjectLableProvider());
        }

        @Override
        protected boolean canEdit(Object element) {
            return true;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return cellEditor;
        }

        @Override
        protected Object getValue(Object element) {
            if (element instanceof PowerlinkSubobject) {
                PowerlinkSubobject mappingSubObj = (PowerlinkSubobject) element;

                return getMappableObject(mappingSubObj);
            }
            return element.toString();
        }

        public void setInput(List<AbstractPowerlinkObject> objectList) {
            if (objectList != null) {
                cellEditor.setInput(objectList.toArray());
            } else {
                cellEditor.setInput(null);
            }
        }

        @Override
        protected void setValue(Object element, Object value) {

            if ((element == null) || (value == null)) {
                System.err.println("No Objects are Found for mapping");
                return;
            }

            if (element instanceof PowerlinkSubobject) {
                PowerlinkSubobject mappingSubObject = (PowerlinkSubobject) element;

                ISelection selection = channelViewer.getSelection();
                if (selection instanceof IStructuredSelection) {

                    IStructuredSelection ss = (IStructuredSelection) selection;
                    if (ss.size() > 1) {
                        System.err.println("Multiple selection not handled.");
                        return;
                    }
                    Object selectedObj = ss.getFirstElement();

                    if (selectedObj == null) {
                        showMessageWindow(MessageDialog.ERROR,
                                "Select any PDO channel.");
                        return;
                    }

                    if (selectedObj instanceof PdoChannel) {
                        PdoChannel pdoChannel = (PdoChannel) selectedObj;

                        if (value instanceof PowerlinkSubobject) {
                            PowerlinkSubobject subObjectTobeMapped = (PowerlinkSubobject) value;
                            if (subObjectTobeMapped.isModule()) {
                                long moduleObjectIndex = OpenConfiguratorLibraryUtils
                                        .getModuleObjectIndex(
                                                subObjectTobeMapped.getModule(),
                                                subObjectTobeMapped,
                                                subObjectTobeMapped.getObject()
                                                        .getId());
                                int moduleSubObjectIndex = OpenConfiguratorLibraryUtils
                                        .getModuleObjectsSubIndex(
                                                subObjectTobeMapped.getModule(),
                                                subObjectTobeMapped,
                                                subObjectTobeMapped.getObject()
                                                        .getId());
                                Result res = OpenConfiguratorLibraryUtils
                                        .mappModuleSubObjectToChannel(
                                                pdoChannel, mappingSubObject,
                                                moduleObjectIndex,
                                                moduleSubObjectIndex);
                                if (!res.IsSuccessful()) {
                                    showMessage(res);
                                }
                            } else {
                                Result res = OpenConfiguratorLibraryUtils
                                        .mappSubObjectToChannel(pdoChannel,
                                                mappingSubObject,
                                                subObjectTobeMapped);
                                if (!res.IsSuccessful()) {
                                    showMessage(res);
                                }
                            }

                        } else if (value instanceof PowerlinkObject) {
                            PowerlinkObject objectTobeMapped = (PowerlinkObject) value;
                            // The variable res is dead stored, so that it may
                            // return value if the
                            // result fails in all below conditions.
                            Result res = new Result();
                            if (objectTobeMapped == emptyObject) {
                                res = OpenConfiguratorLibraryUtils
                                        .clearSelectedmappingobject(pdoChannel,
                                                mappingSubObject);
                            } else {
                                if (objectTobeMapped.isModuleObject()) {
                                    long moduleObjectIndex = OpenConfiguratorLibraryUtils
                                            .getModuleObjectsIndex(
                                                    objectTobeMapped
                                                            .getModule(),
                                                    objectTobeMapped.getId());
                                    res = OpenConfiguratorLibraryUtils
                                            .mappModuleObjectToChannel(
                                                    pdoChannel,
                                                    mappingSubObject,
                                                    objectTobeMapped,
                                                    moduleObjectIndex);
                                } else {
                                    res = OpenConfiguratorLibraryUtils
                                            .mappObjectToChannel(pdoChannel,
                                                    mappingSubObject,
                                                    objectTobeMapped);
                                }

                            }

                            if (!res.IsSuccessful()) {
                                showMessage(res);
                            }
                        }

                        try {
                            OpenConfiguratorProjectUtils
                                    .updatePdoChannelActualValue(pdoChannel);
                        } catch (JDOMException | IOException e) {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printErrorMessage(e.getMessage(),
                                            nodeObj.getProject().getName());
                            e.printStackTrace();
                        }

                        updateEnabledMappingEntries(pdoChannel);
                        updateChannelSize(pdoChannel);
                    }
                }

                if (getViewer() instanceof TableViewer) {
                    ((TableViewer) getViewer()).refresh();
                    resizeTable((TableViewer) getViewer(), new int[] { 4, 5 });
                }

            } else {
                System.err.println(
                        "Invalid object type for a PDO table row. Check the model.");
            }
        }
    }

    /**
     * Label provider for target node list.
     *
     * @author Ramakrishnan P
     *
     */
    private static class PdoNodeListLabelProvider extends LabelProvider {
        private PdoType pdoType;

        // It is not required to be a static inner class.
        public PdoNodeListLabelProvider(PdoType pdoType) {
            this.pdoType = pdoType;
        }

        @Override
        public Image getImage(Object element) {
            return super.getImage(element);
        }

        @Override
        public String getText(Object element) {
            if (element instanceof Node) {
                Node node = (Node) element;
                if (pdoType == PdoType.TPDO) {
                    if (node.getCnNodeIdValue() == IPowerlinkConstants.INVALID_NODE_ID) {
                        return node.getName() + " PRes ("
                                + IPowerlinkConstants.MN_DEFAULT_NODE_ID + ")";
                    } else if (node
                            .getCnNodeIdValue() == IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
                        return node.getName() + " PRes ("
                                + node.getCnNodeIdValue() + ")";
                    } else {
                        return node.getNodeIDWithName();
                    }
                } else if (pdoType == PdoType.RPDO) {
                    if (node.getCnNodeIdValue() == IPowerlinkConstants.INVALID_NODE_ID) {
                        return "MN" + " PReq (" + node.getCnNodeIdValue() + ")";
                    } else if (node
                            .getCnNodeIdValue() == IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
                        return node.getName() + " PRes ("
                                + node.getCnNodeIdValue() + ")";
                    } else {
                        return node.getNodeIDWithName();
                    }
                }
            }

            return element.toString();
        }
    }

    /**
     * Content provider to list the overview of TPDO and RPDO channels.
     *
     * @author Ramakrishnan P
     *
     */
    private class PdoSummaryTableContentProvider
            implements IStructuredContentProvider {
        PdoType pdoType;

        public PdoSummaryTableContentProvider(PdoType pdoType) {
            this.pdoType = pdoType;
        }

        @Override
        public void dispose() {
        }

        @Override
        public Object[] getElements(Object inputElement) {

            if (inputElement instanceof Node) {
                Node node = (Node) inputElement;
                if (pdoType == PdoType.TPDO) {
                    List<TpdoChannel> tpdoChannels = node.getObjectDictionary()
                            .getTpdoChannelsList();

                    if (!tpdoSummaryOnlyShowChannelsWithData) {
                        return tpdoChannels.toArray();
                    }
                    List<TpdoChannel> pdoChannels = new ArrayList<>();
                    for (TpdoChannel tpdo : tpdoChannels) {
                        long size = OpenConfiguratorLibraryUtils
                                .getChannelSize(tpdo);
                        if (size > 0) {
                            pdoChannels.add(tpdo);
                        }
                    }
                    return pdoChannels.toArray();
                } else if (pdoType == PdoType.RPDO) {
                    List<RpdoChannel> rpdoChannels = node.getObjectDictionary()
                            .getRpdoChannelsList();

                    if (!rpdoSummaryOnlyShowChannelsWithData) {
                        return rpdoChannels.toArray();
                    }
                    List<RpdoChannel> pdoChannels = new ArrayList<>();
                    for (RpdoChannel rpdo : rpdoChannels) {
                        long size = OpenConfiguratorLibraryUtils
                                .getChannelSize(rpdo);

                        if (size > 0) {
                            pdoChannels.add(rpdo);
                        }

                    }
                    return pdoChannels.toArray();
                } else {
                    // Do nothing.
                    System.err.println("Invalid PDO type.");
                }
            }
            return new Object[0];
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput,
                Object newInput) {
        }
    }

    /**
     * Label provider for the PDO summary table.
     *
     * @author Ramakrishnan P
     *
     */
    private class PdoSummaryTableLabelProvider extends LabelProvider
            implements ITableLabelProvider {

        PdoType pdoType;

        public PdoSummaryTableLabelProvider(PdoType pdoType) {
            this.pdoType = pdoType;
        }

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {

            String retValue = element.toString();
            if (element instanceof PdoChannel) {
                PdoChannel pdoChannel = (PdoChannel) element;
                switch (columnIndex) {
                    case 0:
                        retValue = pdoChannel.getName();
                        break;
                    case 1:
                        retValue = pdoChannel.getCommunicationParam().getIdHex()
                                + "/" + pdoChannel.getMappingParam().getIdHex();
                        break;
                    case 2:
                        short targetNodeId = pdoChannel.getTargetNodeId();
                        Node targetNode = null;
                        for (Node node : targetNodeIdList) {

                            if (node.getCnNodeIdValue() == targetNodeId) {
                                targetNode = node;
                            }
                        }

                        if (targetNode != null) {

                            if (pdoType == PdoType.TPDO) {
                                if (targetNode
                                        .getCnNodeIdValue() == IPowerlinkConstants.INVALID_NODE_ID) {
                                    return targetNode.getName() + " PRes ("
                                            + IPowerlinkConstants.MN_DEFAULT_NODE_ID
                                            + ")";
                                } else if (nodeObj == targetNode) {
                                    return "Self("
                                            + targetNode.getCnNodeIdValue()
                                            + ")";
                                } else if (targetNode
                                        .getCnNodeIdValue() == IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
                                    // TODO Check;
                                    return targetNode.getName() + " PRes ("
                                            + targetNode.getCnNodeIdValue()
                                            + ")";
                                } else {
                                    return targetNode.getNodeIDWithName();
                                }
                            } else if (pdoType == PdoType.RPDO) {
                                if (targetNode
                                        .getCnNodeIdValue() == IPowerlinkConstants.INVALID_NODE_ID) {
                                    return "MN" + " PReq ("
                                            + targetNode.getCnNodeIdValue()
                                            + ")";
                                } else if (nodeObj == targetNode) {
                                    return "Self("
                                            + targetNode.getCnNodeIdValue()
                                            + ")";
                                } else if (targetNode
                                        .getCnNodeIdValue() == IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
                                    return targetNode.getName() + " PRes ("
                                            + targetNode.getCnNodeIdValue()
                                            + ")";
                                } else {
                                    return targetNode.getNodeIDWithName();
                                }
                            }

                            retValue = targetNode.getNodeIDWithName();
                        } else {
                            retValue = "Not available (" + targetNodeId + ")";
                        }
                        break;
                    case 3:
                        long size = OpenConfiguratorLibraryUtils
                                .getChannelSize(pdoChannel);
                        retValue = String.valueOf(size);
                        break;
                    case 4:
                        retValue = pdoChannel.getMappingVersion();
                        break;
                    default:
                        break;
                }
            }

            return retValue;
        }
    }

    /**
     * Label provider for the PDO mapping table.
     *
     * @author Ramakrishnan P
     *
     */
    private class PdoTableLabelProvider extends LabelProvider
            implements ITableLabelProvider, IColorProvider {

        private PdoType pdoType;

        public PdoTableLabelProvider(PdoType pdoType) {

            this.pdoType = pdoType;

        }

        @Override
        public Color getBackground(Object element) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Image getColumnImage(Object element, int columnIndex) {

            switch (columnIndex) {
                case 0:
                    // No Column - Nothing to do.
                    break;
                case 1:
                    // No images for mappable objects
                    break;
                case 2: // Images for the status column.
                    if (element instanceof PowerlinkSubobject) {

                        PowerlinkSubobject mapParamObj = (PowerlinkSubobject) element;

                        String value = mapParamObj.getActualDefaultValue();

                        if (value.isEmpty()) {
                            value = emptyObject.getActualValue();
                        }

                        if (value.length() != 18) {
                            if ((value.length() == 3)
                                    || (value.length() == 1)) {
                                if (Integer.decode(value) == 0) {
                                    if (mapParamObj.getId() == 1) {
                                        try {
                                            if (!tpdoEnabledMappingEntriesText
                                                    .getText().isEmpty()) {
                                                tpdoEnabledEntriesCount = Integer
                                                        .parseInt(
                                                                tpdoEnabledMappingEntriesText
                                                                        .getText()
                                                                        .trim());
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (mapParamObj.getId() == 1) {
                                        try {
                                            if (!rpdoEnabledMappingEntriesText
                                                    .getText().isEmpty()) {
                                                rpdoEnabledEntriesCount = Integer
                                                        .parseInt(
                                                                rpdoEnabledMappingEntriesText
                                                                        .getText()
                                                                        .trim());
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    return null;
                                }
                            }
                            if (mapParamObj.getId() == 1) {
                                try {
                                    if (!tpdoEnabledMappingEntriesText.getText()
                                            .isEmpty()) {
                                        tpdoEnabledEntriesCount = Integer
                                                .parseInt(
                                                        tpdoEnabledMappingEntriesText
                                                                .getText()
                                                                .trim());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (mapParamObj.getId() == 1) {
                                try {
                                    if (!rpdoEnabledMappingEntriesText.getText()
                                            .isEmpty()) {
                                        rpdoEnabledEntriesCount = Integer
                                                .parseInt(
                                                        rpdoEnabledMappingEntriesText
                                                                .getText()
                                                                .trim());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            return errorImage;
                        }

                        String objectId = value.substring(14, 18);
                        String subObjectId = value.substring(12, 14);
                        long objectIdValue = 0;
                        try {
                            objectIdValue = Long.parseLong(objectId, 16);
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                            if (mapParamObj.getId() == 1) {
                                try {
                                    if (!tpdoEnabledMappingEntriesText.getText()
                                            .isEmpty()) {
                                        tpdoEnabledEntriesCount = Integer
                                                .parseInt(
                                                        tpdoEnabledMappingEntriesText
                                                                .getText()
                                                                .trim());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (mapParamObj.getId() == 1) {
                                try {
                                    if (!rpdoEnabledMappingEntriesText.getText()
                                            .isEmpty()) {
                                        rpdoEnabledEntriesCount = Integer
                                                .parseInt(
                                                        rpdoEnabledMappingEntriesText
                                                                .getText()
                                                                .trim());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            return errorImage;
                        }

                        if (objectIdValue == 0) {
                            if (mapParamObj.getId() == 1) {
                                try {
                                    if (!tpdoEnabledMappingEntriesText.getText()
                                            .isEmpty()) {
                                        tpdoEnabledEntriesCount = Integer
                                                .parseInt(
                                                        tpdoEnabledMappingEntriesText
                                                                .getText()
                                                                .trim());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (mapParamObj.getId() == 1) {
                                try {
                                    if (!rpdoEnabledMappingEntriesText.getText()
                                            .isEmpty()) {
                                        rpdoEnabledEntriesCount = Integer
                                                .parseInt(
                                                        rpdoEnabledMappingEntriesText
                                                                .getText()
                                                                .trim());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            // Ignore empty Object.
                            return null;
                        }

                        PowerlinkObject mappableObject = nodeObj
                                .getObjectDictionary().getObject(objectIdValue);
                        if (nodeObj.isModularheadNode()) {
                            List<HeadNodeInterface> interfaceList = nodeObj
                                    .getHeadNodeInterface();
                            if (interfaceList != null) {
                                for (HeadNodeInterface headNodeInterface : interfaceList) {
                                    Collection<Module> moduleList = headNodeInterface
                                            .getModuleCollection().values();
                                    if (moduleList != null) {
                                        for (Module module : moduleList) {
                                            List<PowerlinkObject> objectList = module
                                                    .getObjectDictionary()
                                                    .getObjectsList();
                                            for (PowerlinkObject object : objectList) {
                                                short subObjectIdValue = 0;
                                                subObjectIdValue = Short
                                                        .parseShort(subObjectId,
                                                                16);
                                                for (PowerlinkSubobject subObject : object
                                                        .getTpdoMappableObjectList()) {
                                                    long objectIndex = OpenConfiguratorLibraryUtils
                                                            .getModuleObjectsIndex(
                                                                    subObject
                                                                            .getModule(),
                                                                    subObject
                                                                            .getObject()
                                                                            .getId());
                                                    int subObjectIndex = OpenConfiguratorLibraryUtils
                                                            .getModuleObjectsSubIndex(
                                                                    subObject
                                                                            .getModule(),
                                                                    subObject,
                                                                    subObject
                                                                            .getObject()
                                                                            .getId());
                                                    if (objectIdValue == objectIndex) {
                                                        if (subObjectIndex == subObjectIdValue) {
                                                            switch (pdoType) {

                                                                case TPDO:
                                                                    System.err
                                                                            .println(
                                                                                    "The IDRaw of object.."
                                                                                            + mapParamObj
                                                                                                    .getId());
                                                                    if (mapParamObj
                                                                            .getId() == 1) {
                                                                        try {
                                                                            if (!tpdoEnabledMappingEntriesText
                                                                                    .getText()
                                                                                    .isEmpty()) {
                                                                                tpdoEnabledEntriesCount = Integer
                                                                                        .parseInt(
                                                                                                tpdoEnabledMappingEntriesText
                                                                                                        .getText()
                                                                                                        .trim());
                                                                            }

                                                                        } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                    count = tpdoEnabledEntriesCount
                                                                            - 1;
                                                                    tpdoEnabledEntriesCount = count;

                                                                    if (tpdoEnabledEntriesCount < 0) {
                                                                        return signedDisableImage;
                                                                    }
                                                                    break;
                                                                case RPDO:
                                                                    if (mapParamObj
                                                                            .getId() == 1) {
                                                                        try {
                                                                            if (!rpdoEnabledMappingEntriesText
                                                                                    .getText()
                                                                                    .isEmpty()) {
                                                                                rpdoEnabledEntriesCount = Integer
                                                                                        .parseInt(
                                                                                                rpdoEnabledMappingEntriesText
                                                                                                        .getText()
                                                                                                        .trim());
                                                                            }

                                                                        } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                    count = rpdoEnabledEntriesCount
                                                                            - 1;
                                                                    rpdoEnabledEntriesCount = count;

                                                                    if (rpdoEnabledEntriesCount < 0) {
                                                                        return signedDisableImage;
                                                                    }
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            return signedYesImage;
                                                        }
                                                    }
                                                }
                                                for (PowerlinkSubobject subObject : object
                                                        .getRpdoMappableObjectList()) {
                                                    long objectIndex = OpenConfiguratorLibraryUtils
                                                            .getModuleObjectsIndex(
                                                                    subObject
                                                                            .getModule(),
                                                                    subObject
                                                                            .getObject()
                                                                            .getId());
                                                    int subObjectIndex = OpenConfiguratorLibraryUtils
                                                            .getModuleObjectsSubIndex(
                                                                    subObject
                                                                            .getModule(),
                                                                    subObject,
                                                                    subObject
                                                                            .getObject()
                                                                            .getId());
                                                    if (objectIdValue == objectIndex) {
                                                        if (subObjectIndex == subObjectIdValue) {
                                                            switch (pdoType) {

                                                                case TPDO:
                                                                    if (mapParamObj
                                                                            .getId() == 1) {
                                                                        try {
                                                                            if (!tpdoEnabledMappingEntriesText
                                                                                    .getText()
                                                                                    .isEmpty()) {
                                                                                tpdoEnabledEntriesCount = Integer
                                                                                        .parseInt(
                                                                                                tpdoEnabledMappingEntriesText
                                                                                                        .getText()
                                                                                                        .trim());
                                                                            }

                                                                        } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                    count = tpdoEnabledEntriesCount
                                                                            - 1;
                                                                    tpdoEnabledEntriesCount = count;

                                                                    if (tpdoEnabledEntriesCount < 0) {
                                                                        return signedDisableImage;
                                                                    }
                                                                    break;
                                                                case RPDO:
                                                                    if (mapParamObj
                                                                            .getId() == 1) {
                                                                        try {
                                                                            if (!rpdoEnabledMappingEntriesText
                                                                                    .getText()
                                                                                    .isEmpty()) {
                                                                                rpdoEnabledEntriesCount = Integer
                                                                                        .parseInt(
                                                                                                rpdoEnabledMappingEntriesText
                                                                                                        .getText()
                                                                                                        .trim());
                                                                            }

                                                                        } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                    count = rpdoEnabledEntriesCount
                                                                            - 1;
                                                                    rpdoEnabledEntriesCount = count;

                                                                    if (rpdoEnabledEntriesCount < 0) {
                                                                        return signedDisableImage;
                                                                    }
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            return signedYesImage;
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    } else {
                                        System.err.println(
                                                "No modules are available under interface.");
                                    }
                                }
                            }
                        }

                        if (mappableObject == null) {
                            if (mapParamObj.getId() == 1) {
                                try {
                                    if (!tpdoEnabledMappingEntriesText.getText()
                                            .isEmpty()) {
                                        tpdoEnabledEntriesCount = Integer
                                                .parseInt(
                                                        tpdoEnabledMappingEntriesText
                                                                .getText()
                                                                .trim());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (mapParamObj.getId() == 1) {
                                try {
                                    if (!rpdoEnabledMappingEntriesText.getText()
                                            .isEmpty()) {
                                        rpdoEnabledEntriesCount = Integer
                                                .parseInt(
                                                        rpdoEnabledMappingEntriesText
                                                                .getText()
                                                                .trim());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            return errorImage;
                        }

                        short subObjectIdValue = 0;
                        try {
                            subObjectIdValue = Short.parseShort(subObjectId,
                                    16);
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                            if (mapParamObj.getId() == 1) {
                                try {
                                    if (!tpdoEnabledMappingEntriesText.getText()
                                            .isEmpty()) {
                                        tpdoEnabledEntriesCount = Integer
                                                .parseInt(
                                                        tpdoEnabledMappingEntriesText
                                                                .getText()
                                                                .trim());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (mapParamObj.getId() == 1) {
                                try {
                                    if (!rpdoEnabledMappingEntriesText.getText()
                                            .isEmpty()) {
                                        rpdoEnabledEntriesCount = Integer
                                                .parseInt(
                                                        rpdoEnabledMappingEntriesText
                                                                .getText()
                                                                .trim());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            return errorImage;
                        }

                        // Check if mapped object is mappable to this PDO.
                        if (mappableObject.getObjectType() == 7) {

                            if (subObjectIdValue != 0) {
                                if (mapParamObj.getId() == 1) {
                                    try {
                                        if (!tpdoEnabledMappingEntriesText
                                                .getText().isEmpty()) {
                                            tpdoEnabledEntriesCount = Integer
                                                    .parseInt(
                                                            tpdoEnabledMappingEntriesText
                                                                    .getText()
                                                                    .trim());
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (mapParamObj.getId() == 1) {
                                    try {
                                        if (!rpdoEnabledMappingEntriesText
                                                .getText().isEmpty()) {
                                            rpdoEnabledEntriesCount = Integer
                                                    .parseInt(
                                                            rpdoEnabledMappingEntriesText
                                                                    .getText()
                                                                    .trim());
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                return errorImage;
                            }

                            switch (pdoType) {
                                case RPDO:
                                    if (!mappableObject.isRpdoMappable()) {
                                        if (mapParamObj.getId() == 1) {
                                            try {
                                                if (!rpdoEnabledMappingEntriesText
                                                        .getText().isEmpty()) {
                                                    rpdoEnabledEntriesCount = Integer
                                                            .parseInt(
                                                                    rpdoEnabledMappingEntriesText
                                                                            .getText()
                                                                            .trim());
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        return warningImage;
                                    }
                                    break;
                                case TPDO:
                                    if (!mappableObject.isTpdoMappable()) {
                                        if (mapParamObj.getId() == 1) {
                                            try {
                                                if (!tpdoEnabledMappingEntriesText
                                                        .getText().isEmpty()) {
                                                    tpdoEnabledEntriesCount = Integer
                                                            .parseInt(
                                                                    tpdoEnabledMappingEntriesText
                                                                            .getText()
                                                                            .trim());
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        return warningImage;
                                    }
                                    break;
                                default:
                            }

                        } else {
                            PowerlinkSubobject mappableSubObject = mappableObject
                                    .getSubObject(subObjectIdValue);
                            if (mappableSubObject == null) {
                                if (mapParamObj.getId() == 1) {
                                    try {
                                        if (!tpdoEnabledMappingEntriesText
                                                .getText().isEmpty()) {
                                            tpdoEnabledEntriesCount = Integer
                                                    .parseInt(
                                                            tpdoEnabledMappingEntriesText
                                                                    .getText()
                                                                    .trim());
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                return errorImage;
                            }

                            switch (pdoType) {
                                case RPDO:
                                    if (!mappableSubObject.isRpdoMappable()) {
                                        if (mapParamObj.getId() == 1) {
                                            try {
                                                if (!rpdoEnabledMappingEntriesText
                                                        .getText().isEmpty()) {
                                                    rpdoEnabledEntriesCount = Integer
                                                            .parseInt(
                                                                    rpdoEnabledMappingEntriesText
                                                                            .getText()
                                                                            .trim());
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        return warningImage;
                                    }
                                    break;
                                case TPDO:
                                    if (!mappableSubObject.isTpdoMappable()) {
                                        if (mapParamObj.getId() == 1) {
                                            try {
                                                if (!tpdoEnabledMappingEntriesText
                                                        .getText().isEmpty()) {
                                                    tpdoEnabledEntriesCount = Integer
                                                            .parseInt(
                                                                    tpdoEnabledMappingEntriesText
                                                                            .getText()
                                                                            .trim());
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        return warningImage;
                                    }

                                    break;
                                default:
                            }

                        }

                        switch (pdoType) {

                            case TPDO:
                                if (mapParamObj.getId() == 1) {
                                    try {
                                        if (!tpdoEnabledMappingEntriesText
                                                .getText().isEmpty()) {
                                            tpdoEnabledEntriesCount = Integer
                                                    .parseInt(
                                                            tpdoEnabledMappingEntriesText
                                                                    .getText()
                                                                    .trim());
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                count = tpdoEnabledEntriesCount - 1;
                                tpdoEnabledEntriesCount = count;

                                if (tpdoEnabledEntriesCount < 0) {
                                    return signedDisableImage;
                                }
                                break;
                            case RPDO:
                                if (mapParamObj.getId() == 1) {
                                    try {
                                        if (!rpdoEnabledMappingEntriesText
                                                .getText().isEmpty()) {
                                            rpdoEnabledEntriesCount = Integer
                                                    .parseInt(
                                                            rpdoEnabledMappingEntriesText
                                                                    .getText()
                                                                    .trim());
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                count = rpdoEnabledEntriesCount - 1;
                                rpdoEnabledEntriesCount = count;

                                if (rpdoEnabledEntriesCount < 0) {
                                    return signedDisableImage;
                                }
                                break;
                            default:
                                break;
                        }

                        return signedYesImage;
                    }
                    break;

                default:
                    break;
            }
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {

            switch (columnIndex) {
                case 0: {
                    if (element instanceof PowerlinkSubobject) {
                        PowerlinkSubobject mappParamSubObj = (PowerlinkSubobject) element;
                        return String.valueOf(mappParamSubObj.getId());
                    }
                    break;
                }
                case 1: {

                    if (element instanceof PowerlinkSubobject) {
                        PowerlinkSubobject data = (PowerlinkSubobject) element;
                        return getMappableObjectName(data);
                    }
                    break;
                }
                case 2:
                    return null;
                case 3: {
                    if (element instanceof PowerlinkSubobject) {
                        PowerlinkSubobject mapParamObj = (PowerlinkSubobject) element;

                        String value = mapParamObj.getActualDefaultValue();

                        if (value.isEmpty()) {
                            return "0";
                        }

                        if ((value.length() == 3) || (value.length() == 1)) {
                            if (Integer.decode(value) == 0) {
                                return "0";
                            }
                        }

                        if (value.length() != 18) {
                            return "-";
                        }

                        String datasize = value.substring(2, 6);
                        int sizeinBytes = 0;
                        try {
                            sizeinBytes = (Integer.parseInt(datasize, 16)) / 8;
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                            return "-";
                        }

                        return Integer.toString(sizeinBytes);
                    }
                    break;
                }
                case 4: {
                    if (element instanceof PowerlinkSubobject) {
                        PowerlinkSubobject mapParamObj = (PowerlinkSubobject) element;

                        String value = mapParamObj.getActualDefaultValue();

                        if (value.isEmpty()) {
                            return emptyObject.getActualValue();
                        }

                        if ((value.length() == 3) || (value.length() == 1)) {
                            if (Integer.decode(value) == 0) {
                                return emptyObject.getActualValue();
                            }
                        }

                        return value;
                    }
                    break;
                }
                case 5:
                case 6:
                    if (element instanceof PowerlinkSubobject) {
                        return StringUtils.EMPTY;
                    }
                    break;
                default:
                    break;
            }
            return element.toString();
        }

        @Override
        public Color getForeground(Object element) {
            // TODO Auto-generated method stub
            return null;
        }
    }

    private static final String NO_FORCED_OBJECTS = "No forced objects.";

    public static final String ID = "org.epsg.openconfigurator.views.MappingView"; // $NON-NLS-0$

    public static final String PDO_CONFIGURATION_TAB_TITLE = "PDO Configuration";

    public static final String PDO_TPDO_CHANNELS_SECTION_TITLE = "TPDO Channels";

    public static final String PDO_RPDO_CHANNELS_SECTION_TITLE = "RPDO Channels";

    /**
     * PDO summary table labels
     */
    public static final String PDO_SECTION_CHANNEL_LABEL = "Channel";

    public static final String PDO_SECTION_OBJECTS_LABEL = "Objects";

    public static final String PDO_SECTION_SEND_TO_NODE_LABEL = "Send To Node";

    public static final String PDO_SECTION_RECEIVE_FROM_NODE_LABEL = "Receive From Node";

    public static final String PDO_SECTION_MAPPING_VERSION_LABEL = "Mapping Version";

    public static final String PDO_SECTION_SHOW_ONLY_NON_EMPTY_LABEL = "Only show non empty channels";

    /**
     * PDO mapping tabel labels.
     */
    public static final String TPDO_TAB_TITLE = "TPDO";

    public static final String RPDO_TAB_TITLE = "RPDO";

    public static final String PDO_CHANNEL_LABEL = "Channel:";

    public static final String PDO_ENABLED_MAPPING_ENTRIES = "Enabled Mapping Entries:";

    public static final String PDO_SEND_TO_NODE_LABEL = "Send to Node:";
    public static final String PDO_RECEIVE_FROM_NODE_LABEL = "Receive from Node:";
    public static final String PDO_CHANNEL_SIZE_LABEL = "Channel Size (bytes): ";
    public static final String MENU_SHOW_ADVANCED_PROPERTIES_LABEL = "Show Advanced Properties";
    public static final String PDO_TABLE_COLUMN_STATUS_LABEL = "Status";

    public static final String PDO_TABLE_COLUMN_NO_LABEL = "No";

    public static final String PDO_TABLE_COLUMN_MAPPED_OBJECT_LABEL = "Mapped Object";
    public static final String PDO_TABLE_COLUMN_SIZE_LABEL = "Size (bytes)";
    public static final String PDO_TABLE_COLUMN_ACTUAL_VALUE_LABEL = "Value";
    public static final String PDO_TABLE_COLUMN_ACTIONS_LABEL = "Actions";
    public static final String PDO_MAPP_ALL_AVAILABLE_OBJECTS_LABEL = "Map all available objects";
    public static final String PDO_CLEAR_ALL_MAPPING_LABEL = "Clear All";
    /**
     * The corresponding node instance for which the mapping editor corresponds
     * to.
     */
    private static Node nodeObj;

    private static final String ERROR_ASYNC_SLOT_TIMEOUT_CANNOT_BE_EMPTY = "Async. slot timeout cannot be empty.";

    private static final String INVALID_RANGE_ASYNC_SLOT_TIMEOUT = "Invalid range for Async. slot timeout.";

    private static final String ERROR_INVALID_VALUE_ASYNCT_SLOT_TIMEOUT = "Invalid value for Async. slot timeout.";

    private static final String ERROR_CYCLE_TIME_CANNOT_BE_EMPTY = "Cycle Time cannot be empty.";

    private static final String ERROR_INVALID_VALUE_CYCLE_TIME = "Invalid value for Cycle-time.";

    private static final String ERROR_ASYNC_MTU_CANNOT_BE_EMPTY = "AsyncMtu cannot be empty.";

    private static final String INVALID_RANGE_ASYNC_MTU = "Invalid range for AsyncMtu.";

    private static final String ERROR_INVALID_VALUE_ASYNC_MTU = "Invalid value for AsyncMtu.";

    private static final String ERROR_PRE_SCALER_CANNOT_BE_EMPTY = "PreScaler cannot be empty.";
    private static final String INVALID_RANGE_PRE_SCALER = "Invalid range for PreScaler.";
    private static final String ERROR_INVALID_VALUE_PRE_SCALER = "Invalid value for PreScaler.";
    public static final String ERROR_LOSS_SOC_TOLERANCE_CANNOT_BE_EMPTY = "Loss of SoC tolerance value cannot be empty.";
    public static final String ERROR_INVALID_VALUE_LOSS_SOC_TOLERANCE = "Invalid Loss of SoC tolerance value.";
    public static final String INVALID_RANGE_LOSS_SOC_TOLERANCE = "[{0}] Actual value {1} with datatype UNSIGNED32 does not fit the datatype limits or format.";
    private static final String ERROR_PRES_TIMEOUT_CANNOT_BE_EMPTY = "PRes TimeOut value cannot be empty.";

    private static final String CHAINED_STATION_ERROR_MESSAGE = "POWERLINK network with RMN does not support PRes Chaining operation.";

    private static final String CNPRES_CHAINING_ERROR_MESSAGE = "The node {0} does not support PRes Chaining operation.";

    private static final String MNPRES_CHAINING_ERROR_MESSAGE = "The MN {0} does not support PRes Chaining operation.";

    private static final String MULTIPLEXING_OPERATION_NOT_SUPPORTED_ERROR = "Currently Multiplexing operation not supported.";

    private static final int MINIMUM_ASYC_MTU_VALUE = 300;

    private static final int MAXIMUM_ASYC_MTU_VALUE = 1500;

    private static final long MINIMUM_ASYNC_SLOT_TIMEOUT = 250;

    private static final int MINIMUM_PRESCALER_VALUE = 0;

    private static final int MAXIMUM_PRESCALER_VALUE = 1000;

    /**
     * Handles LossofSOCTolerance modifications
     *
     * @param value New value for LossOfSoCTolerance
     */
    private static String handleLossOfSoCTolerance(Object value) {
        String lossOfSoc = StringUtils.EMPTY;
        if (value instanceof String) {
            lossOfSoc = (String) value;
            if (lossOfSoc.isEmpty()) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        ERROR_LOSS_SOC_TOLERANCE_CANNOT_BE_EMPTY,
                        nodeObj.getNetworkId());
                return StringUtils.EMPTY;

            }
            try {
                long longValue = Long.decode((String) value);
                // validate the value
                Long maxValue = 4294967295L;
                if ((longValue * 1000) > maxValue) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR,
                            MessageFormat.format(
                                    INVALID_RANGE_LOSS_SOC_TOLERANCE,
                                    nodeObj.getNetworkId(), longValue),
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        ERROR_INVALID_VALUE_LOSS_SOC_TOLERANCE,
                        nodeObj.getNetworkId());
                return StringUtils.EMPTY;

            }

        } else {
            System.err.println(
                    "handleLossOfSoCTolerance: Invalid value type:" + value);
        }

        return lossOfSoc;
    }

    /**
     * Handle NodeId changes in properties
     *
     * @param id New Id for the Node
     * @return Returns a string indicating whether the given value is valid;
     *         null means invalid, and non-null means valid, with the result
     *         being the error message to display to the end user.
     */
    private static String handleSetNodeId(Object id) {
        String nodeId = StringUtils.EMPTY;
        if (id instanceof String) {
            nodeId = (String) id;
            if (nodeId.isEmpty()) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        "Node ID cannot be empty.", nodeObj.getNetworkId());
                return StringUtils.EMPTY;
            }
            try {
                short nodeIDvalue = Short.valueOf(((String) id));
                if (nodeObj.getNodeModel() instanceof TCN) {
                    if ((nodeIDvalue == 0)
                            || (nodeIDvalue >= IPowerlinkConstants.MN_DEFAULT_NODE_ID)) {
                        PluginErrorDialogUtils.showMessageWindow(
                                MessageDialog.ERROR, "Invalid Node ID.",
                                nodeObj.getNetworkId());
                        return StringUtils.EMPTY;
                    }
                } else if (nodeObj.getNodeModel() instanceof TRMN) {
                    if ((nodeIDvalue == 0)
                            || (nodeIDvalue <= IPowerlinkConstants.MN_DEFAULT_NODE_ID)
                            || ((nodeIDvalue > IPowerlinkConstants.RMN_MAX_NODE_ID))) {
                        PluginErrorDialogUtils.showMessageWindow(
                                MessageDialog.ERROR, "Invalid Node ID.",
                                nodeObj.getNetworkId());
                        return StringUtils.EMPTY;
                    }
                }

                if (nodeIDvalue == nodeObj.getCnNodeIdValue()) {
                    return StringUtils.EMPTY;
                }

                boolean nodeIdAvailable = nodeObj.getPowerlinkRootNode()
                        .isNodeIdAlreadyAvailable(nodeIDvalue);
                if (nodeIdAvailable) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR,
                            "Node with id " + nodeIDvalue + " already exists.",
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        "Invalid Node ID.", nodeObj.getNetworkId());
                return StringUtils.EMPTY;
            }
        }
        return nodeId;
    }

    private static String handletransmitPres(Object value) {
        String transmitPres = StringUtils.EMPTY;
        if (value instanceof String) {
            transmitPres = (String) value;

            if (transmitPres.equalsIgnoreCase("0")) {
                transmitPres = "Yes";
            } else {
                transmitPres = "No";
            }
        }
        return transmitPres;
    }

    /**
     * Resize table based on columns content width.
     *
     * @param tblViewer The viewer which holds the SWT table.
     * @param ignoreColumns Column id if the column has to by pass the re-size
     *            process.
     */
    private static void resizeTable(TableViewer tblViewer,
            int[] ignoreColumns) {
        Table table = tblViewer.getTable();
        int totalColumnCount = table.getColumnCount();

        for (int i = 0; i < totalColumnCount; i++) {
            boolean ignorePack = false;
            for (int ignoreColumn : ignoreColumns) {
                if (ignoreColumn == i) {
                    ignorePack = true;
                }
            }
            if (!ignorePack) {
                TableColumn tableColumn = table.getColumn(i);
                tableColumn.pack();
            }
        }

        boolean actualValueColmn = false;
        for (int ignoreColumn : ignoreColumns) {
            if (ignoreColumn == 4) {
                actualValueColmn = true;
            }
        }

        int width = table.getClientArea().width;
        int lastColumnWidth = 0;
        for (int i = 0; i < totalColumnCount; i++) {

            if (actualValueColmn && (i == 4)) {
                continue;
            }

            if (i == (totalColumnCount - 1)) {
                continue;
            }
            lastColumnWidth += table.getColumn(i).getWidth();
        }

        TableColumn tableColumn = table.getColumn(totalColumnCount - 1);
        tableColumn.setWidth(width - lastColumnWidth);
    }

    /**
     * Displays the library message in a dialog.
     *
     * @param messageType The message type determines the image to be displayed.
     * @param res The message to be shown from the library.
     */
    public static void showLibraryMessageWindow(int messageType, Result res) {
        OpenConfiguratorMessageConsole.getInstance()
                .printLibraryErrorMessage(res);
        String libraryMessage = res.GetErrorMessage();
        MessageDialog.open(messageType, Display.getDefault().getActiveShell(),
                "Mapping View", libraryMessage, SWT.NONE);
    }

    /**
     * Displays the message based on the openCONFIGURATOR library.
     *
     * @param res
     */
    public static void showMessage(Result res) {
        showLibraryMessageWindow(MessageDialog.ERROR, res);
    }

    /**
     * Displays the message in a dialog.
     *
     * @param messageType The message type determines the image to be displayed.
     * @param message The message to be shown.
     */
    public static void showMessageWindow(int messageType, String message) {
        String projectName = nodeObj.getProject().getName();
        OpenConfiguratorMessageConsole.getInstance().printErrorMessage(message,
                projectName);
        MessageDialog.open(messageType, Display.getDefault().getActiveShell(),
                "Mapping View", message, SWT.NONE);
    }

    /**
     * Selection listener to display up,down,clear action buttons to move or
     * clear RPDO objects or Sub-Objects
     */
    private ISelectionChangedListener rpdoTableRowSelectionChangeListener = new ISelectionChangedListener() {

        @Override
        public void selectionChanged(SelectionChangedEvent event) {

            for (TableItem ti : rpdoTableViewer.getTable().getItems()) {
                rpdoTableActionsEditor.setEditor(null, ti, 5);
            }

            if (rpdoTableViewer.getTable().getSelectionIndex() == -1) {
                if (rpdoTableViewer.getTable().getItemCount() > 0) {
                    rpdoTableActionsEditor.setEditor(rpdoActionsbuttonGroup,
                            rpdoTableViewer.getTable().getItem(0), 5);
                }
            } else {
                rpdoTableActionsEditor.setEditor(rpdoActionsbuttonGroup,
                        rpdoTableViewer.getTable().getItem(
                                rpdoTableViewer.getTable().getSelectionIndex()),
                        5);
            }
            int itemcount = rpdoTableViewer.getTable().getItemCount();

            int index = rpdoTableViewer.getTable().getSelectionIndex();

            if (index == 0) {
                rpdoActionsUpButton.setEnabled(false);
            } else {
                rpdoActionsUpButton.setEnabled(true);
            }
            if (index == (itemcount - 1)) {
                rpdoActionsDownButton.setEnabled(false);
            } else {
                rpdoActionsDownButton.setEnabled(true);
            }
        }
    };
    /**
     * Selection listener to display up,down,clear action buttons to move or
     * clear TPDO objects or Sub-Objects
     */
    private ISelectionChangedListener tpdoTableRowSelectionChangeListener = new ISelectionChangedListener() {

        @Override
        public void selectionChanged(SelectionChangedEvent event) {

            for (TableItem ti : tpdoTableViewer.getTable().getItems()) {
                tpdoTableActionsEditor.setEditor(null, ti, 5);
            }

            if (tpdoTableViewer.getTable().getSelectionIndex() == -1) {
                if (tpdoTableViewer.getTable().getItemCount() > 0) {
                    tpdoTableActionsEditor.setEditor(tpdoActionsbuttonGroup,
                            tpdoTableViewer.getTable().getItem(0), 5);
                }
            } else {
                tpdoTableActionsEditor.setEditor(tpdoActionsbuttonGroup,
                        tpdoTableViewer.getTable().getItem(
                                tpdoTableViewer.getTable().getSelectionIndex()),
                        5);
            }
            int itemcount = tpdoTableViewer.getTable().getItemCount();

            int index = tpdoTableViewer.getTable().getSelectionIndex();

            if (index == 0) {
                tpdoActionsUpButton.setEnabled(false);
            } else {
                tpdoActionsUpButton.setEnabled(true);
            }
            if (index == (itemcount - 1)) {
                tpdoActionsDownButton.setEnabled(false);
            } else {
                tpdoActionsDownButton.setEnabled(true);
            }
        }
    };

    private Tree lst_no_foi;
    /**
     * Selection listener to update the objects and sub-objects in the mapping
     * view.
     */
    private ISelectionListener networkViewPartSelectionListener = new ISelectionListener() {

        @Override
        public void selectionChanged(IWorkbenchPart part,
                ISelection selection) {

            if (sourcePart != null) {
                sourcePart.getSite().getPage().removePartListener(partListener);
                sourcePart = null;
            }

            setPdoGuiControlsEnabled(PdoType.TPDO, false);
            tpdoActionsbuttonGroup.setVisible(false);

            setPdoGuiControlsEnabled(PdoType.RPDO, false);
            rpdoActionsbuttonGroup.setVisible(false);

            // change the viewer input since the workbench selection has
            // changed.
            if (selection instanceof IStructuredSelection) {

                IStructuredSelection ss = (IStructuredSelection) selection;
                if (ss.size() == 0) {
                    setPartName("Mapping View");

                    // Summary
                    tpdoSummaryTableViewer.setInput(null);
                    rpdoSummaryTableViewer.setInput(null);

                    // TPDO page
                    tpdoChannelComboViewer.setInput(null);
                    tpdoTableViewer.setInput(null);
                    sndtoNodecomboviewer.setInput(null);
                    setPdoGuiControlsEnabled(PdoType.TPDO, false);
                    tpdoActionsbuttonGroup.setVisible(false);
                    tpdoEnabledMappingEntriesText
                            .removeVerifyListener(enabledEntriesVerifyListener);
                    tpdoEnabledMappingEntriesText.setText(StringUtils.EMPTY);
                    tpdoEnabledMappingEntriesText
                            .addVerifyListener(enabledEntriesVerifyListener);
                    tpdoEnabledMappingEntriesText.setEnabled(false);
                    tpdoChannelSize.setText(StringUtils.EMPTY);

                    // RPDO page
                    rpdoChannelComboViewer.setInput(null);
                    rpdoTableViewer.setInput(null);
                    receiveFromNodecomboviewer.setInput(null);
                    setPdoGuiControlsEnabled(PdoType.RPDO, false);
                    rpdoActionsbuttonGroup.setVisible(false);
                    rpdoEnabledMappingEntriesText
                            .removeVerifyListener(enabledEntriesVerifyListener);
                    rpdoEnabledMappingEntriesText.setText(StringUtils.EMPTY);
                    rpdoEnabledMappingEntriesText
                            .addVerifyListener(enabledEntriesVerifyListener);
                    rpdoEnabledMappingEntriesText.setEnabled(false);
                    rpdoChannelSize.setText(StringUtils.EMPTY);

                    return;
                }

                if (ss.size() > 1) {
                    System.err.println("Multiple selection not handled.");
                    return;
                }

                Object selectedObj = ss.getFirstElement();
                if (selectedObj == null) {
                    return;
                }

                sourcePart = part;
                nodeTypeCombo.removeKeyListener(nodeTypeModifyListener);
                nodeTypeCombo.removeKeyListener(transmitPresModifyListener);
                nodeId.removeKeyListener(nodeIDModifyListener);
                lossOfSocTolText.removeKeyListener(lossOfSocModifyListener);
                asyncMtuText.removeKeyListener(asyncMtuModifyListener);
                asyncTimeOutTxt.removeKeyListener(asyncTimeoutModifyListener);
                preScalerText.removeKeyListener(prescalerModifyListener);
                txt_no_PResTimeOut.removeKeyListener(cycleTimeModifyListener);
                txt_no_PResTimeOut.removeKeyListener(presTimeoutModifyListener);

                if (selectedObj instanceof Node) {
                    nodeObj = (Node) selectedObj;
                    setPartName(nodeObj.getNodeIDWithName());

                    // Set null as input to the view, when the node is disabled.
                    displayMappingView(nodeObj);

                } else if (selectedObj instanceof Module) {
                    Module module = (Module) selectedObj;
                    nodeObj = module.getNode();
                    setPartName("Mapping View");

                    // Summary
                    tpdoSummaryTableViewer.setInput(null);
                    rpdoSummaryTableViewer.setInput(null);

                    // TPDO page
                    tpdoChannelComboViewer.setInput(null);
                    tpdoTableViewer.setInput(null);
                    sndtoNodecomboviewer.setInput(null);
                    setPdoGuiControlsEnabled(PdoType.TPDO, false);
                    tpdoActionsbuttonGroup.setVisible(false);
                    tpdoEnabledMappingEntriesText
                            .removeVerifyListener(enabledEntriesVerifyListener);
                    tpdoEnabledMappingEntriesText.setText(StringUtils.EMPTY);
                    tpdoEnabledMappingEntriesText
                            .addVerifyListener(enabledEntriesVerifyListener);
                    tpdoEnabledMappingEntriesText.setEnabled(false);
                    tpdoChannelSize.setText(StringUtils.EMPTY);

                    // RPDO page
                    rpdoChannelComboViewer.setInput(null);
                    rpdoTableViewer.setInput(null);
                    receiveFromNodecomboviewer.setInput(null);
                    setPdoGuiControlsEnabled(PdoType.RPDO, false);
                    rpdoActionsbuttonGroup.setVisible(false);
                    rpdoEnabledMappingEntriesText
                            .removeVerifyListener(enabledEntriesVerifyListener);
                    rpdoEnabledMappingEntriesText.setText(StringUtils.EMPTY);
                    rpdoEnabledMappingEntriesText
                            .addVerifyListener(enabledEntriesVerifyListener);
                    rpdoEnabledMappingEntriesText.setEnabled(false);
                    rpdoChannelSize.setText(StringUtils.EMPTY);

                    lst_no_foi.removeAll();
                    listViewer.setInput(module);

                    nodeTypeCombo.setItems(nodeTypeItems);
                    if (nodeObj
                            .getPlkOperationMode() == PlkOperationMode.CHAINED) {
                        nodeTypeCombo.select(1);

                    } else {
                        nodeTypeCombo.select(0);
                    }

                    addListenersToCnControls();
                    handleGeneralInfoForModule(module);

                    txt_no_nodename.setText(nodeObj.getName());

                    return;
                } else if (selectedObj instanceof HeadNodeInterface) {
                    HeadNodeInterface headintf = (HeadNodeInterface) selectedObj;
                    nodeObj = headintf.getNode();
                    Object nodeObjModel = nodeObj.getNodeModel();

                    Integer nodeIdVal = Integer
                            .valueOf(nodeObj.getNodeIdString());
                    if (nodeIdVal == IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
                        String[] presTimes = { "Yes", "No" };
                        nodeTypeCombo.setItems(presTimes);
                        if (nodeObj
                                .getNodeModel() instanceof TNetworkConfiguration) {
                            TNetworkConfiguration net = (TNetworkConfiguration) nodeObj
                                    .getNodeModel();
                            TMN mn = net.getNodeCollection().getMN();
                            if (mn.isTransmitsPRes()) {

                                nodeTypeCombo.select(0);
                            } else {
                                nodeTypeCombo.select(1);
                            }
                        }
                    } else {
                        nodeTypeCombo.setItems(nodeTypeItems);
                        if (nodeObj
                                .getPlkOperationMode() == PlkOperationMode.CHAINED) {
                            nodeTypeCombo.select(1);

                        } else {
                            nodeTypeCombo.select(0);
                        }
                    }

                    if (nodeObjModel instanceof TCN) {
                        addListenersToCnControls();
                        // nodeId.removeModifyListener(nodeIDModifyListener);
                        handleGeneralInfoForCn(nodeObj);
                    }

                    txt_no_nodename.setText(nodeObj.getName());

                    lst_no_foi.removeAll();
                    listViewer.setInput(nodeObj);

                } else {
                    System.err.println(
                            "Other than node is selected!" + selectedObj);
                }
            }

            if (sourcePart != null) {
                sourcePart.getSite().getPage().addPartListener(partListener);
            }
        }

    };
    /**
     * Source workbench part.
     */
    private IWorkbenchPart sourcePart;

    /**
     * Listener instance to listen to the changes in the source part.
     */
    private final PartListener partListener = new PartListener();

    /**
     * TPDO page controls
     */
    private TabItem tbtmTpdo;
    private ComboViewer tpdoChannelComboViewer;
    private ComboViewer sndtoNodecomboviewer;
    private TableViewer tpdoTableViewer;
    private TableViewerColumn tpdoMappingObjectColumn;
    private TableViewerColumn tpdoActionsColumn;
    private PdoMappingObjectColumnEditingSupport tpdoMappingObjClmnEditingSupport;

    private Composite tpdoPageFooter;

    private Button btnTpdoChannelMapAvailableObjects;
    private Button btnTpdoChannelClearSelectedRows;

    private Text tpdoEnabledMappingEntriesText;

    private Text tpdoChannelSize;

    private TableEditor tpdoTableActionsEditor;

    private Composite tpdoActionsbuttonGroup;

    private Button tpdoActionsUpButton;
    private Button tpdoActionsDownButton;
    private TableColumn tpdoTblclmnActualValue;
    private ISelectionChangedListener tpdoChannelSelectionChangeListener;

    private ISelectionChangedListener tpdoNodeComboSelectionChangeListener;
    private ModifyListener tpdoEnabledMappingEntriesTextModifyListener;

    private SelectionListener tpdoMapAvailableObjectsBtnSelectionListener;

    private SelectionListener tpdoClearAllMappingBtnSelectionListener;

    private SelectionListener tpdoActionsClearBtnSelectionListener;

    private SelectionListener tpdoActionsDownBtnSelectionListener;
    private SelectionListener tpdoActionsUpBtnSelectionListener;
    private Button tpdoBtnCheckButton;

    public boolean tpdoProfileObjectSelection = false;
    private int tpdoEnabledEntriesCount;
    private int count = 0;

    /**
     * RPDO page controls
     */
    private TabItem tbtmRpdo;
    private ComboViewer rpdoChannelComboViewer;

    private ComboViewer receiveFromNodecomboviewer;
    private TableViewer rpdoTableViewer;
    private TableViewerColumn rpdoMappingObjectColumn;
    private TableViewerColumn rpdoActionsColumn;
    private PdoMappingObjectColumnEditingSupport rpdoMappingObjClmnEditingSupport;

    private Composite rpdoPageFooter;
    private Button btnRpdoChannelMapAvailableObjects;
    private Button btnRpdoChannelClearSelectedRows;

    private Text rpdoEnabledMappingEntriesText;
    private Text rpdoChannelSize;
    private TableEditor rpdoTableActionsEditor;
    private Composite rpdoActionsbuttonGroup;
    private Button rpdoActionsUpButton;
    private Button rpdoActionsDownButton;
    private TableColumn rpdoTblclmnActualValue;
    private ISelectionChangedListener rpdoChannelSelectionChangeListener;
    private ISelectionChangedListener rpdoNodeComboSelectionChangeListener;

    private ModifyListener rpdoEnabledMappingEntriesTextModifyListener;

    private SelectionListener rpdoMapAvailableObjectsBtnSelectionListener;

    private SelectionListener rpdoClearAllMappingBtnSelectionListener;
    private SelectionListener rpdoActionsClearBtnSelectionListener;
    private SelectionListener rpdoActionsDownBtnSelectionListener;
    private SelectionListener rpdoActionsUpBtnSelectionListener;
    private Button rpdoBtnCheckButton;
    public boolean rpdoProfileObjectSelection = false;
    private int rpdoEnabledEntriesCount;
    /**
     * Common GUI controls
     */
    private Action showAdvancedview;
    private final Image clearImage;
    private final Image upArrowImage;
    private final Image downArrowImage;

    private final Image warningImage;

    private final Image epsgImage;

    private final Image errorImage;

    private final Image signedYesImage;

    private final Image signedDisableImage;
    private final FormToolkit formToolkit = new FormToolkit(
            Display.getDefault());
    /**
     * Summary Page
     */
    private TabItem tbtmPdoConfiguration;
    private TableViewer tpdoSummaryTableViewer;
    private TableColumn tpdoSummaryClmnMappingVersion;
    private boolean tpdoSummaryOnlyShowChannelsWithData = false;
    private TableViewer rpdoSummaryTableViewer;
    private TableColumn rpdoSummaryClmnMappingVersion;

    private boolean rpdoSummaryOnlyShowChannelsWithData = false;
    /**
     * Common application model data
     */
    private final Node emptyNode;
    private final Node selfReceiptNode;
    private final PowerlinkObject emptyObject;

    private final ArrayList<Node> targetNodeIdList = new ArrayList<>();

    /**
     * VerifyListener to update the number of available Mapping object entries
     */
    private VerifyListener enabledEntriesVerifyListener = new VerifyListener() {

        @Override
        public void verifyText(VerifyEvent e) {
            e.doit = false;

            // Get the character typed
            final char inputChar = e.character;

            // Allow arrow keys and backspace and delete
            // keys
            if ((inputChar == SWT.BS)
                    || (Integer.valueOf(inputChar) == SWT.ARROW_LEFT)
                    || (Integer.valueOf(inputChar) == SWT.ARROW_RIGHT)
                    || (inputChar == SWT.DEL)) {
                e.doit = true;
                return;
            }

            // DisAllow other than 0-9
            if (!Character.isDigit(inputChar)) {
                return;
            }

            final String oldS = ((Text) e.widget).getText();

            final String newS = oldS.substring(0, e.start) + e.text
                    + oldS.substring(e.end);

            try {
                short enabledEntries = Short.decode(newS);
                // TODO: Check the available sub-index in future.
                if ((enabledEntries >= 0x0) && (enabledEntries < 0xFF)) {
                    e.doit = true;
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    };

    private Text txt_no_nodename;

    private Text txt_no_PResTimeOut;

    private Text lossOfSocTolText;

    private Text asyncMtuText;

    private Text asyncTimeOutTxt;

    private Text preScalerText;

    private Text productNameText;

    private Text productIdText;

    private Text vendorNameText;

    private Text vendorIdtext;

    private Combo nodeTypeCombo;

    private Label lblNodeType;

    private Label lblPResTimeout;

    private Label lblLossOfSoc;

    private Label lblAsymcMtu;

    private Label lblAsyncTimeOut;

    private Label lblPrescaler;

    private Label productNameLabel;

    private Label productIdLabel;

    private Label vendorNamelabel;

    private Label vendorIdLabel;

    private String newNodeName;

    private String newNodeId;

    private String newNodType;

    private String newTransmitPres;

    private String newPresTime;

    private String newLossOfSoc;

    private String newAsyncMtu;

    private String newAsynctimeOut;

    private String newPreScaler;

    private String newCycleTime;

    /**
     * Keyboard bindings.
     */
    private KeyAdapter nodeIDModifyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            System.err.println("The state mask event.." + e.stateMask);
            if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)
                    || ((e.stateMask & SWT.MODIFIER_MASK) != 0)) {

                newNodeId = handleSetNodeId(nodeId.getText());
                if ((!newNodeId.equalsIgnoreCase(StringUtils.EMPTY))) {
                    short nodeIDvalue = Short.valueOf((newNodeId));

                    short oldNodeId = nodeObj.getCnNodeIdValue();
                    try {
                        nodeObj.getPowerlinkRootNode().setNodeId(oldNodeId,
                                nodeIDvalue);

                    } catch (InvocationTargetException | IOException
                            | JDOMException | InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    refreshNetworkView();
                } else {
                    // nodeId.setText(nodeObj.getNodeIdString());
                }

            }
        }

    };

    /**
     * Keyboard bindings.
     */
    private KeyAdapter transmitPresModifyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)) {
                String oldPres = StringUtils.EMPTY;

                if (nodeObj.getNodeModel() instanceof TNetworkConfiguration) {
                    TNetworkConfiguration net = (TNetworkConfiguration) nodeObj
                            .getNodeModel();
                    TMN mn = net.getNodeCollection().getMN();
                    if (mn.isTransmitsPRes()) {
                        oldPres = "Yes";
                    } else {
                        oldPres = "No";
                    }
                }
                int presIndex = nodeTypeCombo.getSelectionIndex();
                newTransmitPres = handletransmitPres(String.valueOf(presIndex));

                if ((!newTransmitPres.equalsIgnoreCase(StringUtils.EMPTY))) {
                    if (!newTransmitPres.equalsIgnoreCase(oldPres)) {

                        if (newTransmitPres.equalsIgnoreCase("Yes")) {
                            newTransmitPres = "0";
                        } else {
                            newTransmitPres = "1";
                        }
                        Integer value = Integer.valueOf(newTransmitPres);

                        boolean result = (value == 0) ? true : false;
                        if (nodeObj
                                .getNodeModel() instanceof TNetworkConfiguration) {
                            TNetworkConfiguration net = (TNetworkConfiguration) nodeObj
                                    .getNodeModel();
                            TMN mn = net.getNodeCollection().getMN();
                            mn.setTransmitsPRes(result);
                            try {
                                OpenConfiguratorProjectUtils
                                        .updateNodeAttributeValue(nodeObj,
                                                "transmitsPRes",
                                                String.valueOf(result));
                            } catch (JDOMException | IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            nodeTypeCombo.select(value);
                        }
                        refreshNetworkView();

                    }
                }
            }
        }

    };

    /**
     * Keyboard bindings.
     */
    private KeyAdapter nodeTypeModifyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)) {
                String oldNodetype = StringUtils.EMPTY;
                if (nodeObj.getNodeModel() instanceof TCN) {
                    TCN tcn = (TCN) nodeObj.getNodeModel();
                    if (tcn.isIsChained()) {
                        oldNodetype = "Chained";
                    } else {
                        oldNodetype = "Normal";
                    }
                }

                int nodeType = nodeTypeCombo.getSelectionIndex();
                newNodType = handleNodeTypeChange(nodeType);
                if ((!newNodType.equalsIgnoreCase(StringUtils.EMPTY))) {
                    if (!newNodType.equalsIgnoreCase(oldNodetype)) {
                        if (newNodType.equalsIgnoreCase("Normal")) {
                            newNodType = "0";
                        } else {
                            newNodType = "1";
                        }
                        Integer value = Integer.valueOf(newNodType);
                        PlkOperationMode plkMode = null;
                        Result res = new Result();

                        int val = value.intValue();
                        if (val == 0) { // Normal Station.
                            res = OpenConfiguratorCore.GetInstance()
                                    .ResetOperationMode(nodeObj.getNetworkId(),
                                            nodeObj.getCnNodeIdValue());
                            plkMode = PlkOperationMode.NORMAL;
                        } else if (val == 1) {
                            res = OpenConfiguratorCore.GetInstance()
                                    .SetOperationModeChained(
                                            nodeObj.getNetworkId(),
                                            nodeObj.getCnNodeIdValue());
                            plkMode = PlkOperationMode.CHAINED;
                        }
                        if (plkMode != null) {
                            if (res.IsSuccessful()) {
                                nodeObj.setPlkOperationMode(plkMode);
                            } else {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printLibraryErrorMessage(res);
                            }
                        } else {
                            System.err.println(
                                    "Invalid POWERLINK operation mode");
                        }
                        // Node Assignment values will be modified by the
                        // library. So refresh the project file data.
                        try {
                            OpenConfiguratorProjectUtils
                                    .updateNodeAssignmentValues(nodeObj);

                            // RPDO nodeID will be changed by the library. So
                            // refresh the node XDD data
                            OpenConfiguratorProjectUtils
                                    .persistNodeData(nodeObj);

                            // Updates the generator attributes in project file.
                            OpenConfiguratorProjectUtils
                                    .updateGeneratorInfo(nodeObj);

                        } catch (JDOMException | IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        refreshNetworkView();
                    }
                }
            }
        }

    };

    /**
     * Keyboard bindings.
     */
    private KeyAdapter presTimeoutModifyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)) {
                long presTimeoutInMsVal = nodeObj.getPresTimeoutvalue() / 1000;

                String prestimeout = String.valueOf(presTimeoutInMsVal);
                newPresTime = handlePresTimeout(txt_no_PResTimeOut.getText());
                if ((!newPresTime.equalsIgnoreCase(StringUtils.EMPTY))) {
                    if (!newPresTime.equalsIgnoreCase(prestimeout)) {
                        long presTimeoutInNs = Long.decode(newPresTime)
                                .longValue() * 1000;
                        Result res = OpenConfiguratorCore.GetInstance()
                                .SetPResTimeOut(nodeObj.getNetworkId(),
                                        nodeObj.getCnNodeIdValue(),
                                        presTimeoutInNs);
                        if (res.IsSuccessful()) {
                            try {
                                nodeObj.setCnPresTimeout(
                                        String.valueOf(presTimeoutInNs));

                            } catch (JDOMException | IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }
                    }
                    refreshNetworkView();
                } else {

                    long presTimeoutInMs = nodeObj.getPresTimeoutvalue() / 1000;

                    txt_no_PResTimeOut.setText(String.valueOf(presTimeoutInMs));
                }
            }

        }

    };

    /**
     * Keyboard bindings.
     */
    private KeyAdapter lossOfSocModifyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)) {
                Long value = Long.valueOf(nodeObj.getLossOfSocTolerance());
                long valueInUs = value / 1000;

                String lossOfSoc = String.valueOf(valueInUs);
                newLossOfSoc = handleLossOfSoCTolerance(
                        lossOfSocTolText.getText());
                System.err.println("Loss of Soc..." + newLossOfSoc);
                if ((!newLossOfSoc.equalsIgnoreCase(StringUtils.EMPTY))) {
                    if (!newLossOfSoc.equalsIgnoreCase(lossOfSoc)) {
                        long lossOfSocTolerance = Long.decode(newLossOfSoc)
                                * 1000;
                        Result res = OpenConfiguratorCore.GetInstance()
                                .SetLossOfSocTolerance(nodeObj.getNetworkId(),
                                        nodeObj.getCnNodeIdValue(),
                                        lossOfSocTolerance);
                        if (res.IsSuccessful()) {
                            try {
                                nodeObj.setLossOfSocTolerance(
                                        lossOfSocTolerance);
                            } catch (JDOMException | IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }
                    }
                    refreshNetworkView();
                } else {
                    Long val = Long.valueOf(nodeObj.getLossOfSocTolerance());
                    long valInUs = val / 1000;
                    lossOfSocTolText.setText(String.valueOf(valInUs));
                }
            }
        }

    };

    /**
     * Keyboard bindings.
     */
    private KeyAdapter asyncMtuModifyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)) {
                String asyncMtu = nodeObj.getAsyncMtu();
                newAsyncMtu = handleAsyncMtu(asyncMtuText.getText());
                if ((!newAsyncMtu.equalsIgnoreCase(StringUtils.EMPTY))) {
                    if (!newAsyncMtu.equalsIgnoreCase(asyncMtu)) {
                        Integer asyncMtuValue = Integer.decode(newAsyncMtu);
                        Result res = OpenConfiguratorCore.GetInstance()
                                .SetAsyncMtu(nodeObj.getNetworkId(),
                                        asyncMtuValue);
                        if (res.IsSuccessful()) {
                            try {
                                nodeObj.setAsyncMtuOfNode(asyncMtuValue);
                            } catch (JDOMException | IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }
                    }
                    refreshNetworkView();
                } else {
                    asyncMtuText.setText(nodeObj.getAsyncMtu());
                }
            }
        }

    };

    /**
     * Keyboard bindings.
     */
    private KeyAdapter asyncTimeoutModifyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)) {

                String asyncTime = nodeObj.getAsyncSlotTimeout();
                newAsynctimeOut = handleAsyncSlotTimeout(
                        asyncTimeOutTxt.getText());
                if ((!newAsynctimeOut.equalsIgnoreCase(StringUtils.EMPTY))) {
                    if (newAsynctimeOut.equalsIgnoreCase(asyncTime)) {
                        Result res = OpenConfiguratorCore.GetInstance()
                                .SetAsyncSlotTimeout(nodeObj.getNetworkId(),
                                        nodeObj.getCnNodeIdValue(),
                                        Long.decode(newAsynctimeOut));
                        if (res.IsSuccessful()) {
                            try {
                                nodeObj.setAsyncSlotTimeout(
                                        Long.decode(newAsynctimeOut));
                            } catch (NumberFormatException | JDOMException
                                    | IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }
                    }
                    refreshNetworkView();
                } else {
                    asyncTimeOutTxt.setText(nodeObj.getAsyncSlotTimeout());
                }
            }
        }

    };

    /**
     * Keyboard bindings.
     */
    private KeyAdapter prescalerModifyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)) {
                String preScaler = nodeObj.getPrescaler();
                newPreScaler = handlePreScalerValue(preScalerText.getText());
                if ((!newPreScaler.equalsIgnoreCase(StringUtils.EMPTY))) {
                    if (!newPreScaler.equalsIgnoreCase(preScaler)) {
                        Integer preScalarVal = Integer.decode(newPreScaler);
                        Result res = OpenConfiguratorCore.GetInstance()
                                .SetPrescaler(nodeObj.getNetworkId(),
                                        preScalarVal);
                        if (res.IsSuccessful()) {
                            try {
                                nodeObj.setPrescaler(preScalarVal);
                            } catch (JDOMException | IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }
                    }
                    refreshNetworkView();
                } else {
                    preScalerText.setText(nodeObj.getPrescaler());
                }
            }
        }

    };

    /**
     * Keyboard bindings.
     */
    private KeyAdapter cycleTimeModifyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)) {
                String cycleTime = nodeObj.getCycleTime();
                newCycleTime = handleCycleTime(txt_no_PResTimeOut.getText());
                if ((!newCycleTime.equalsIgnoreCase(StringUtils.EMPTY))) {
                    if (!newCycleTime.equalsIgnoreCase(cycleTime)) {
                        Long cycleTimeValue = Long.decode(newCycleTime);
                        Result res = OpenConfiguratorCore.GetInstance()
                                .SetCycleTime(nodeObj.getNetworkId(),
                                        cycleTimeValue);
                        if (res.IsSuccessful()) {
                            try {
                                nodeObj.setCycleTime(cycleTimeValue);
                            } catch (JDOMException | IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }
                    }
                    refreshNetworkView();
                } else {
                    // txt_no_PResTimeOut.setText(nodeObj.getCycleTime());
                }
            }
        }

    };

    private Text nodeId;

    private String[] nodeTypeItems = { "Normal", "Chained", "Multiplexed" };

    /**
     * Keyboard bindings.
     */
    private KeyAdapter nodeNameModifyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)) {

                String nodeName = nodeObj.getName();
                newNodeName = handleSetNodeName(txt_no_nodename.getText());
                if ((!newNodeName.equalsIgnoreCase(StringUtils.EMPTY))) {
                    if ((!newNodeName.equalsIgnoreCase(nodeName))) {
                        Result res = OpenConfiguratorCore.GetInstance()
                                .SetNodeName(nodeObj.getNetworkId(),
                                        nodeObj.getCnNodeIdValue(),
                                        newNodeName);
                        if (!res.IsSuccessful()) {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        } else {
                            try {
                                nodeObj.setName(newNodeName);

                            } catch (JDOMException | IOException ex) {
                                // TODO Auto-generated catch block
                                ex.printStackTrace();
                            }

                        }
                    }
                    refreshNetworkView();
                } else {

                    txt_no_nodename.setText(nodeObj.getName());
                }
            }
        }

    };

    private TreeViewer listViewer;

    public MappingView() {
        TObject emptyObj = new TObject();
        emptyObj.setName("Empty");
        emptyObj.setIndex(DatatypeConverter.parseHexBinary("0000"));
        emptyObj.setActualValue("0x0000000000000000");
        emptyObj.setDataType(null);
        emptyObj.setDataType(DatatypeConverter.parseHexBinary("000F"));
        emptyObj.setPDOmapping(TObjectPDOMapping.OPTIONAL);
        emptyObj.setAccessType(TObjectAccessType.RW);

        emptyObject = new PowerlinkObject(new Node(), emptyObj);

        TMN preqMn = new TMN();
        preqMn.setName("Broadcast");
        preqMn.setNodeID((short) 0);
        emptyNode = new Node(null, null, preqMn, null);
        TCN selfReceiptModel = new TCN();
        selfReceiptModel.setName("Self Receipt");
        selfReceiptModel.setNodeID(
                String.valueOf(IPowerlinkConstants.PDO_SELF_RECEIPT_NODE_ID));
        selfReceiptNode = new Node(null, null, selfReceiptModel, null);

        clearImage = org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.CLEAR_ICON).createImage();
        upArrowImage = org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.ARROW_UP_ICON).createImage();
        downArrowImage = org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.ARROW_DOWN_ICON)
                .createImage();
        warningImage = org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.WARNING_ICON).createImage();
        errorImage = org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.ERROR_ICON).createImage();
        signedYesImage = org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.SIGNED_YES_ICON)
                .createImage();
        signedDisableImage = org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.SIGNED_DISABLE_ICON)
                .createImage();
        epsgImage = org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.EPSG_IMAGE).createImage();
    }

    private void addListenersToCnControls() {
        nodeTypeCombo.removeKeyListener(transmitPresModifyListener);
        txt_no_PResTimeOut.removeKeyListener(cycleTimeModifyListener);
        txt_no_PResTimeOut.addKeyListener(presTimeoutModifyListener);

        lossOfSocTolText.removeKeyListener(lossOfSocModifyListener);
        asyncMtuText.removeKeyListener(asyncMtuModifyListener);
        asyncTimeOutTxt.removeKeyListener(asyncTimeoutModifyListener);
        preScalerText.removeKeyListener(prescalerModifyListener);

        txt_no_nodename.addKeyListener(nodeNameModifyListener);

        nodeId.setEnabled(true);
        nodeId.addKeyListener(nodeIDModifyListener);

        nodeTypeCombo.addKeyListener(nodeTypeModifyListener);

    }

    private void addListenersToMnControls() {
        nodeTypeCombo.removeKeyListener(nodeTypeModifyListener);
        txt_no_PResTimeOut.removeKeyListener(presTimeoutModifyListener);
        txt_no_PResTimeOut.addKeyListener(cycleTimeModifyListener);

        txt_no_nodename.addKeyListener(nodeNameModifyListener);
        nodeId.removeKeyListener(nodeIDModifyListener);
        nodeId.setEnabled(false);

        nodeTypeCombo.addKeyListener(transmitPresModifyListener);

        lossOfSocTolText.addKeyListener(lossOfSocModifyListener);
        asyncMtuText.addKeyListener(asyncMtuModifyListener);
        asyncTimeOutTxt.addKeyListener(asyncTimeoutModifyListener);
        preScalerText.addKeyListener(prescalerModifyListener);

    }

    private void addListenersToRmnControls() {
        nodeId.addKeyListener(nodeIDModifyListener);
        txt_no_nodename.addKeyListener(nodeNameModifyListener);
    }

    private void contributeToActionBars() {
        IActionBars bars = getViewSite().getActionBars();

        fillLocalToolBar(bars.getToolBarManager());
        bars.updateActionBars();
    }

    /**
     * Create the actions required for the mapping view.
     */
    private void createActions() {
        showAdvancedview = new Action(MENU_SHOW_ADVANCED_PROPERTIES_LABEL,
                IAction.AS_CHECK_BOX) {
            @Override
            public void run() {
                if (showAdvancedview.isChecked()) {

                    tpdoSummaryClmnMappingVersion.setResizable(true);
                    rpdoSummaryClmnMappingVersion.setResizable(true);
                    handleSummaryTableResize(PdoType.TPDO);
                    handleSummaryTableResize(PdoType.RPDO);

                    tpdoTblclmnActualValue.setResizable(true);
                    tpdoTblclmnActualValue.setWidth(140);
                    handlePdoTableResize(PdoType.TPDO,
                            showAdvancedview.isChecked());
                    rpdoTblclmnActualValue.setResizable(true);
                    rpdoTblclmnActualValue.setWidth(140);
                    handlePdoTableResize(PdoType.RPDO,
                            showAdvancedview.isChecked());

                } else {
                    tpdoSummaryClmnMappingVersion.setWidth(0);
                    tpdoSummaryClmnMappingVersion.setResizable(false);
                    rpdoSummaryClmnMappingVersion.setWidth(0);
                    rpdoSummaryClmnMappingVersion.setResizable(false);
                    handleSummaryTableResize(PdoType.TPDO);
                    handleSummaryTableResize(PdoType.RPDO);

                    tpdoTblclmnActualValue.setWidth(0);
                    tpdoTblclmnActualValue.setResizable(false);
                    handlePdoTableResize(PdoType.TPDO,
                            showAdvancedview.isChecked());
                    rpdoTblclmnActualValue.setWidth(0);
                    rpdoTblclmnActualValue.setResizable(false);
                    handlePdoTableResize(PdoType.RPDO,
                            showAdvancedview.isChecked());

                }
            }
        };
        showAdvancedview.setToolTipText(MENU_SHOW_ADVANCED_PROPERTIES_LABEL);
        showAdvancedview.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.FILTER_ICON));
        showAdvancedview.setChecked(false);
        showAdvancedview.run();
    }

    /**
     * Create contents of the view part.
     *
     * @param parent The parent composite.
     */
    @SuppressWarnings("unused")
    @Override
    public void createPartControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new FillLayout(SWT.VERTICAL));

        Composite composite = new Composite(container, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));

        Composite composite_2 = new Composite(composite, SWT.NONE);
        composite_2.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));

        final TabFolder tabFolder = new TabFolder(composite_2, SWT.NONE);
        GridData gd_sctnCnFeatures = new GridData(SWT.FILL, SWT.FILL, true,
                true, 1, 1);
        gd_sctnCnFeatures.widthHint = 341;

        {

            TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
            tabItem.setText("Node Overview");
            Composite composite_1 = new Composite(tabFolder, SWT.NONE);
            tabItem.setControl(composite_1);
            composite_1.setLayout(new GridLayout(2, true));
            composite_1.setLayoutData(
                    new GridData(SWT.FILL, SWT.TOP, true, false));
            composite_1.setRedraw(true);

            Section generalInformation = formToolkit.createSection(composite_1,
                    ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR
                            | Section.DESCRIPTION
                            | ExpandableComposite.TITLE_BAR);
            formToolkit.paintBordersFor(generalInformation);
            generalInformation.setText("General Information");
            generalInformation.setDescription(
                    "Provides the basic properties for the node.");

            generalInformation.setExpanded(true);
            GridData gd_sctnAdvancedConfiguration = new GridData(SWT.FILL,
                    SWT.FILL, true, true, 1, 1);
            gd_sctnAdvancedConfiguration.heightHint = 141;
            gd_sctnAdvancedConfiguration.widthHint = 268;
            generalInformation.setLayoutData(gd_sctnAdvancedConfiguration);
            generalInformation.setRedraw(true);

            Composite composite_10 = formToolkit
                    .createComposite(generalInformation, SWT.WRAP);
            GridLayout layout_adt = new GridLayout(2, false);
            layout_adt.marginWidth = 2;
            layout_adt.marginHeight = 2;
            formToolkit.adapt(composite_10);
            formToolkit.paintBordersFor(composite_10);
            generalInformation.setClient(composite_10);
            composite_10.setLayout(layout_adt);

            Label lblNodeName = new Label(composite_10, SWT.NONE);
            lblNodeName.setText("Node Name:");
            formToolkit.adapt(lblNodeName, true, true);
            lblNodeName.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));

            txt_no_nodename = new Text(composite_10, SWT.BORDER);
            txt_no_nodename.setEditable(true);
            txt_no_nodename.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            formToolkit.adapt(txt_no_nodename, true, true);

            Label lblNodeId = new Label(composite_10, SWT.NONE);
            lblNodeId.setText("Node ID:");
            formToolkit.adapt(lblNodeId, true, true);
            lblNodeId.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));
            nodeId = new Text(composite_10, SWT.BORDER);
            nodeId.setEditable(true);
            nodeId.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            formToolkit.adapt(nodeId, true, true);

            lblNodeType = new Label(composite_10, SWT.NONE);
            GridData gd_lblNodeType = new GridData(SWT.LEFT, SWT.CENTER, false,
                    false, 1, 1);
            gd_lblNodeType.widthHint = 78;
            lblNodeType.setLayoutData(gd_lblNodeType);
            lblNodeType.setText("Transmit PRes:");

            formToolkit.adapt(lblNodeType, true, true);
            lblNodeType.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));

            nodeTypeCombo = new Combo(composite_10, SWT.NONE);

            nodeTypeCombo.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            formToolkit.adapt(nodeTypeCombo);
            formToolkit.paintBordersFor(nodeTypeCombo);

            lblPResTimeout = new Label(composite_10, SWT.NONE);
            GridData gd_lblPResTimeout = new GridData(SWT.LEFT, SWT.CENTER,
                    false, false, 1, 1);
            gd_lblPResTimeout.widthHint = 104;
            lblPResTimeout.setLayoutData(gd_lblPResTimeout);
            lblPResTimeout.setText("Cycle Time (" + "\u00B5" + "s):");

            formToolkit.adapt(lblPResTimeout, true, true);
            lblPResTimeout.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));

            txt_no_PResTimeOut = new Text(composite_10, SWT.BORDER);
            txt_no_PResTimeOut.setEditable(true);
            txt_no_PResTimeOut.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            formToolkit.adapt(txt_no_PResTimeOut, true, true);

            Composite composite_6 = new Composite(composite_1, SWT.NONE);
            composite_6.setLayout(new FormLayout());
            GridData gd_composite_6 = new GridData(SWT.LEFT, SWT.CENTER, false,
                    false, 1, 1);
            gd_composite_6.heightHint = 134;
            gd_composite_6.widthHint = 463;
            composite_6.setLayoutData(gd_composite_6);
            formToolkit.adapt(composite_6);
            formToolkit.paintBordersFor(composite_6);

            CLabel label = new CLabel(composite_6, SWT.NONE);
            FormData fd_label = new FormData();
            fd_label.top = new FormAttachment(0);
            fd_label.right = new FormAttachment(100, -59);
            fd_label.bottom = new FormAttachment(100, -12);
            fd_label.left = new FormAttachment(0, 98);
            label.setLayoutData(fd_label);
            formToolkit.adapt(label);
            formToolkit.paintBordersFor(label);

            label.setImage(epsgImage);

            Section forcedobjectSection = formToolkit.createSection(composite_1,
                    ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR
                            | Section.DESCRIPTION
                            | ExpandableComposite.TITLE_BAR);
            forcedobjectSection.setDescription(
                    "Lists the forced objects and sub-objects.");
            formToolkit.paintBordersFor(forcedobjectSection);
            forcedobjectSection.setText("Forced Objects");
            forcedobjectSection.setExpanded(true);
            GridData gdData_sctnAdvancedConfiguration = new GridData(SWT.FILL,
                    SWT.FILL, true, true, 1, 1);
            gdData_sctnAdvancedConfiguration.heightHint = 222;
            gdData_sctnAdvancedConfiguration.widthHint = 332;
            forcedobjectSection.setLayoutData(gdData_sctnAdvancedConfiguration);
            forcedobjectSection.setRedraw(true);

            Composite composite_101 = formToolkit
                    .createComposite(forcedobjectSection, SWT.WRAP);
            GridLayout layout_ad = new GridLayout(2, false);
            layout_ad.marginWidth = 2;
            layout_ad.marginHeight = 2;
            formToolkit.adapt(composite_101);
            formToolkit.paintBordersFor(composite_101);
            forcedobjectSection.setClient(composite_101);
            composite_101.setLayout(layout_ad);

            listViewer = new TreeViewer(composite_101,
                    SWT.BORDER | SWT.V_SCROLL);
            lst_no_foi = listViewer.getTree();
            GridData gd_list = new GridData(SWT.LEFT, SWT.CENTER, false, false,
                    1, 1);
            gd_list.heightHint = 180;
            gd_list.widthHint = 426;
            lst_no_foi.setLayoutData(gd_list);
            listViewer.setContentProvider(new ForcedObjectContentProvider());
            listViewer.setLabelProvider(new ForcedObjectLabelProvider());

            Label label_7 = new Label(composite_101, SWT.NONE);
            label_7.setText(" ");
            formToolkit.adapt(label_7, true, true);

            Section advancedInfoSection = formToolkit.createSection(composite_1,
                    ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR
                            | Section.DESCRIPTION
                            | ExpandableComposite.TITLE_BAR);
            formToolkit.paintBordersFor(advancedInfoSection);
            advancedInfoSection.setText("Advanced Information");
            advancedInfoSection.setDescription(
                    "Provides the advanced properties for the node.");
            advancedInfoSection.setExpanded(true);
            GridData gdData_sctnforcedObjConfiguration = new GridData(SWT.FILL,
                    SWT.FILL, true, true, 1, 1);
            gdData_sctnforcedObjConfiguration.heightHint = 270;
            gdData_sctnforcedObjConfiguration.widthHint = 332;
            advancedInfoSection
                    .setLayoutData(gdData_sctnforcedObjConfiguration);
            advancedInfoSection.setRedraw(true);

            Composite composite_102 = formToolkit
                    .createComposite(advancedInfoSection, SWT.WRAP);
            GridLayout layout = new GridLayout(2, false);
            layout.marginWidth = 2;
            layout.marginHeight = 2;
            composite_102.setLayout(layout);
            formToolkit.adapt(composite_102);
            formToolkit.paintBordersFor(composite_102);
            advancedInfoSection.setClient(composite_102);

            productNameLabel = new Label(composite_102, SWT.NONE);
            productNameLabel.setText("Product Name:");
            formToolkit.adapt(productNameLabel, true, true);
            productNameLabel.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));

            productNameText = new Text(composite_102, SWT.BORDER);
            productNameText.setEditable(false);
            productNameText.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            formToolkit.adapt(productNameText, true, true);
            productNameText.setEditable(false);

            productIdLabel = new Label(composite_102, SWT.NONE);
            productIdLabel.setText("Product ID:");
            formToolkit.adapt(productIdLabel, true, true);
            productIdLabel.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));

            productIdText = new Text(composite_102, SWT.BORDER);
            productIdText.setEditable(false);
            productIdText.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            formToolkit.adapt(productIdText, true, true);
            productIdText.setEditable(false);

            vendorNamelabel = new Label(composite_102, SWT.NONE);
            vendorNamelabel.setText("Vendor Name:");
            formToolkit.adapt(vendorNamelabel, true, true);
            vendorNamelabel.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));

            vendorNameText = new Text(composite_102, SWT.BORDER);
            vendorNameText.setEditable(false);
            vendorNameText.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            formToolkit.adapt(vendorNameText, true, true);
            vendorNameText.setEditable(false);

            vendorIdLabel = new Label(composite_102, SWT.NONE);
            GridData gd_vendorIdLabel = new GridData(SWT.LEFT, SWT.CENTER,
                    false, false, 1, 1);
            gd_vendorIdLabel.widthHint = 55;
            vendorIdLabel.setLayoutData(gd_vendorIdLabel);
            vendorIdLabel.setText("Vendor ID:");
            formToolkit.adapt(vendorIdLabel, true, true);
            vendorIdLabel.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));

            vendorIdtext = new Text(composite_102, SWT.BORDER);
            vendorIdtext.setEditable(false);
            vendorIdtext.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            formToolkit.adapt(vendorIdtext, true, true);
            vendorIdtext.setEditable(false);

            lblLossOfSoc = new Label(composite_102, SWT.NONE);
            GridData gd_lblProductName = new GridData(SWT.LEFT, SWT.CENTER,
                    false, false, 1, 1);
            gd_lblProductName.heightHint = 15;
            lblLossOfSoc.setLayoutData(gd_lblProductName);
            lblLossOfSoc.setText("Loss of SoC tolerance (" + "\u00B5" + "s):");
            formToolkit.adapt(lblLossOfSoc, true, true);
            lblLossOfSoc.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));

            lossOfSocTolText = new Text(composite_102, SWT.BORDER);
            lossOfSocTolText.setEditable(true);
            lossOfSocTolText.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
            formToolkit.adapt(lossOfSocTolText, true, true);

            lblAsymcMtu = new Label(composite_102, SWT.NONE);
            lblAsymcMtu.setText("Asynchronous MTU size (Bytes):");
            formToolkit.adapt(lblAsymcMtu, true, true);
            lblAsymcMtu.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));

            asyncMtuText = new Text(composite_102, SWT.BORDER);
            asyncMtuText.setEditable(true);
            asyncMtuText.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            formToolkit.adapt(asyncMtuText, true, true);

            lblAsyncTimeOut = new Label(composite_102, SWT.NONE);
            lblAsyncTimeOut.setText("Async Slot Timeout (ns):");
            formToolkit.adapt(lblAsyncTimeOut, true, true);
            lblAsyncTimeOut.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));

            asyncTimeOutTxt = new Text(composite_102, SWT.BORDER);
            asyncTimeOutTxt.setEditable(true);
            asyncTimeOutTxt.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            formToolkit.adapt(asyncTimeOutTxt, true, true);

            lblPrescaler = new Label(composite_102, SWT.NONE);
            lblPrescaler.setText("Prescaler:");
            formToolkit.adapt(lblPrescaler, true, true);
            lblPrescaler.setForeground(
                    formToolkit.getColors().getColor(IFormColors.TITLE));

            preScalerText = new Text(composite_102, SWT.BORDER);
            preScalerText.setEditable(true);
            preScalerText.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            formToolkit.adapt(preScalerText, true, true);

            tbtmPdoConfiguration = new TabItem(tabFolder, SWT.NONE);
            tbtmPdoConfiguration.setText(PDO_CONFIGURATION_TAB_TITLE);
            {
                Composite composite_NewTab = new Composite(tabFolder, SWT.NONE);
                composite_NewTab.setLayout(new GridLayout(1, true));
                composite_NewTab.setLayoutData(
                        new GridData(SWT.FILL, SWT.TOP, true, false));
                composite_NewTab.setRedraw(true);

                Section sctnNewSection = formToolkit.createSection(
                        composite_NewTab,
                        ExpandableComposite.CLIENT_INDENT
                                | ExpandableComposite.EXPANDED
                                | ExpandableComposite.TWISTIE
                                | ExpandableComposite.TITLE_BAR);
                sctnNewSection.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true, 1, 6));
                sctnNewSection.setRedraw(true);
                formToolkit.paintBordersFor(sctnNewSection);
                sctnNewSection.setText(PDO_TPDO_CHANNELS_SECTION_TITLE);
                sctnNewSection.setExpanded(true);

                Composite composite_4 = formToolkit
                        .createComposite(sctnNewSection, SWT.NONE);
                formToolkit.paintBordersFor(composite_4);
                sctnNewSection.setClient(composite_4);
                composite_4.setLayout(new FillLayout(SWT.HORIZONTAL));
                composite_4.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true));

                tpdoSummaryTableViewer = new TableViewer(composite_4,
                        SWT.BORDER | SWT.FULL_SELECTION);
                Table tpdoSummaryTable = tpdoSummaryTableViewer.getTable();
                tpdoSummaryTable.setLinesVisible(true);
                tpdoSummaryTable.setHeaderVisible(true);

                formToolkit.paintBordersFor(tpdoSummaryTable);

                composite_4.setLayoutData(
                        new GridData(SWT.LEFT, SWT.TOP, true, false));

                TableViewerColumn tableViewerColumn = new TableViewerColumn(
                        tpdoSummaryTableViewer, SWT.NONE);
                TableColumn tpdoSummaryTblClmnChannel = tableViewerColumn
                        .getColumn();
                tpdoSummaryTblClmnChannel.setWidth(100);
                tpdoSummaryTblClmnChannel.setText(PDO_SECTION_CHANNEL_LABEL);

                TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
                        tpdoSummaryTableViewer, SWT.NONE);
                TableColumn tblclmnObjects = tableViewerColumn_1.getColumn();
                tblclmnObjects.setWidth(100);
                tblclmnObjects.setText(PDO_SECTION_OBJECTS_LABEL);

                TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
                        tpdoSummaryTableViewer, SWT.NONE);
                TableColumn tblclmnSendToNode = tableViewerColumn_2.getColumn();
                tblclmnSendToNode.setWidth(100);
                tblclmnSendToNode.setText(PDO_SECTION_SEND_TO_NODE_LABEL);

                TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
                        tpdoSummaryTableViewer, SWT.CENTER);
                TableColumn tblclmnSizebytes_1 = tableViewerColumn_3
                        .getColumn();
                tblclmnSizebytes_1.setWidth(100);
                tblclmnSizebytes_1.setText(PDO_TABLE_COLUMN_SIZE_LABEL);

                final TableViewerColumn tpdoSummaryMappingVersionColumn = new TableViewerColumn(
                        tpdoSummaryTableViewer, SWT.NONE);
                tpdoSummaryClmnMappingVersion = tpdoSummaryMappingVersionColumn
                        .getColumn();
                tpdoSummaryClmnMappingVersion.setWidth(100);
                tpdoSummaryClmnMappingVersion
                        .setText(PDO_SECTION_MAPPING_VERSION_LABEL);

                tpdoSummaryTable.addListener(SWT.Resize, new Listener() {
                    @Override
                    public void handleEvent(Event event) {

                        handleSummaryTableResize(PdoType.TPDO);
                    }
                });

                Label lblNewLabel_1 = new Label(composite_NewTab, SWT.NONE);
                formToolkit.adapt(lblNewLabel_1, true, true);

                Section sctnRpdoChannel = formToolkit.createSection(
                        composite_NewTab,
                        ExpandableComposite.CLIENT_INDENT
                                | ExpandableComposite.EXPANDED
                                | ExpandableComposite.TWISTIE
                                | ExpandableComposite.TITLE_BAR);
                sctnRpdoChannel.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true, 1, 6));
                formToolkit.paintBordersFor(sctnRpdoChannel);
                sctnRpdoChannel.setText(PDO_RPDO_CHANNELS_SECTION_TITLE);
                sctnRpdoChannel.setExpanded(true);

                Composite composite_8 = formToolkit
                        .createComposite(sctnRpdoChannel, SWT.NONE);
                formToolkit.paintBordersFor(composite_8);
                sctnRpdoChannel.setClient(composite_8);
                composite_8.setLayout(new FillLayout(SWT.HORIZONTAL));
                composite_8.setLayoutData(
                        new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));

                rpdoSummaryTableViewer = new TableViewer(composite_8,
                        SWT.BORDER | SWT.FULL_SELECTION);
                Table rpdoSummaryTable = rpdoSummaryTableViewer.getTable();
                rpdoSummaryTable.setLinesVisible(true);
                rpdoSummaryTable.setHeaderVisible(true);
                formToolkit.paintBordersFor(rpdoSummaryTable);

                TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(
                        rpdoSummaryTableViewer, SWT.NONE);
                TableColumn tblclmnChannel_1 = tableViewerColumn_5.getColumn();
                tblclmnChannel_1.setWidth(100);
                tblclmnChannel_1.setText(PDO_SECTION_CHANNEL_LABEL);

                TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(
                        rpdoSummaryTableViewer, SWT.NONE);
                TableColumn tblclmnObjects_1 = tableViewerColumn_6.getColumn();
                tblclmnObjects_1.setWidth(100);
                tblclmnObjects_1.setText(PDO_SECTION_OBJECTS_LABEL);

                TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(
                        rpdoSummaryTableViewer, SWT.NONE);
                TableColumn tblclmnReceiveFromNode = tableViewerColumn_7
                        .getColumn();
                tblclmnReceiveFromNode.setWidth(100);
                tblclmnReceiveFromNode
                        .setText(PDO_SECTION_RECEIVE_FROM_NODE_LABEL);

                TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(
                        rpdoSummaryTableViewer, SWT.CENTER);
                TableColumn tblclmnSizebytes_2 = tableViewerColumn_8
                        .getColumn();
                tblclmnSizebytes_2.setWidth(100);
                tblclmnSizebytes_2.setText(PDO_TABLE_COLUMN_SIZE_LABEL);

                TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(
                        rpdoSummaryTableViewer, SWT.NONE);
                rpdoSummaryClmnMappingVersion = tableViewerColumn_9.getColumn();
                rpdoSummaryClmnMappingVersion.setWidth(100);
                rpdoSummaryClmnMappingVersion
                        .setText(PDO_SECTION_MAPPING_VERSION_LABEL);

                rpdoSummaryTable.addListener(SWT.Resize, new Listener() {
                    @Override
                    public void handleEvent(Event event) {

                        handleSummaryTableResize(PdoType.RPDO);
                    }
                });

                tbtmPdoConfiguration.setControl(composite_NewTab);

                tpdoSummaryTableViewer.setContentProvider(
                        new PdoSummaryTableContentProvider(PdoType.TPDO));
                tpdoSummaryTableViewer.setLabelProvider(
                        new PdoSummaryTableLabelProvider(PdoType.TPDO));
                rpdoSummaryTableViewer.setContentProvider(
                        new PdoSummaryTableContentProvider(PdoType.RPDO));
                rpdoSummaryTableViewer.setLabelProvider(
                        new PdoSummaryTableLabelProvider(PdoType.RPDO));
            }

            tbtmTpdo = new TabItem(tabFolder, SWT.NONE);

            tbtmTpdo.setText(TPDO_TAB_TITLE);
            {
                Composite composite_3 = new Composite(tabFolder, SWT.NONE);
                tbtmTpdo.setControl(composite_3);
                composite_3.setLayout(new GridLayout(1, false));

                Composite tpdoHeaderFrame = new Composite(composite_3,
                        SWT.BORDER);
                tpdoHeaderFrame.setLayout(new GridLayout(5, false));
                tpdoHeaderFrame.setLayoutData(
                        new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));

                Label lblChannel = new Label(tpdoHeaderFrame, SWT.NONE);
                lblChannel.setText(PDO_CHANNEL_LABEL);

                tpdoChannelComboViewer = new ComboViewer(tpdoHeaderFrame,
                        SWT.READ_ONLY);
                Combo combo = tpdoChannelComboViewer.getCombo();
                GridData gd_combo = new GridData(SWT.LEFT, SWT.CENTER, false,
                        false, 1, 1);
                gd_combo.widthHint = 175;
                combo.setLayoutData(gd_combo);

                combo.setVisibleItemCount(10);
                combo.pack();

                Label lblNewLabel_3 = new Label(tpdoHeaderFrame, SWT.NONE);
                lblNewLabel_3.setLayoutData(
                        new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
                lblNewLabel_3.setText("        ");

                Label lblEnabledMapping = new Label(tpdoHeaderFrame, SWT.NONE);
                lblEnabledMapping.setText(PDO_ENABLED_MAPPING_ENTRIES);

                tpdoEnabledMappingEntriesText = new Text(tpdoHeaderFrame,
                        SWT.BORDER);
                GridData gd_tpdoEnabledMappingEntriesText = new GridData(
                        SWT.FILL, SWT.CENTER, false, false, 1, 1);
                gd_tpdoEnabledMappingEntriesText.widthHint = 100;
                tpdoEnabledMappingEntriesText
                        .setLayoutData(gd_tpdoEnabledMappingEntriesText);

                Label lblSendToNode = new Label(tpdoHeaderFrame, SWT.NONE);
                lblSendToNode.setText(PDO_SEND_TO_NODE_LABEL);

                sndtoNodecomboviewer = new ComboViewer(tpdoHeaderFrame,
                        SWT.READ_ONLY);
                Combo sndtonodecombo = sndtoNodecomboviewer.getCombo();
                sndtonodecombo.setEnabled(false);

                GridData gd_sndtonodecombo = new GridData(SWT.LEFT, SWT.CENTER,
                        false, false, 1, 1);
                gd_sndtonodecombo.widthHint = 175;
                sndtonodecombo.setLayoutData(gd_sndtonodecombo);

                Label lblNewLabel_4 = new Label(tpdoHeaderFrame, SWT.NONE);
                lblNewLabel_4.setLayoutData(
                        new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
                lblNewLabel_4.setText("        ");

                Label lblTotalSize = new Label(tpdoHeaderFrame, SWT.NONE);
                lblTotalSize.setText(PDO_CHANNEL_SIZE_LABEL);

                tpdoChannelSize = new Text(tpdoHeaderFrame,
                        SWT.READ_ONLY | SWT.BORDER);
                tpdoChannelSize.setEnabled(false);
                GridData gd_tpdoChannelSizeText = new GridData(SWT.FILL,
                        SWT.CENTER, false, false, 1, 1);
                gd_tpdoChannelSizeText.widthHint = 100;
                tpdoChannelSize.setLayoutData(gd_tpdoChannelSizeText);

                Composite composite_7 = new Composite(composite_3, SWT.NONE);
                composite_7.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
                GridLayout gl_composite_7 = new GridLayout(1, false);
                gl_composite_7.marginWidth = 0;
                composite_7.setLayout(gl_composite_7);

                tpdoTableViewer = new TableViewer(composite_7,
                        SWT.BORDER | SWT.FULL_SELECTION);

                Table table = tpdoTableViewer.getTable();
                table.setHeaderVisible(true);
                table.setLinesVisible(true);
                table.addListener(SWT.MeasureItem, new Listener() {

                    @Override
                    public void handleEvent(Event event) {
                        // Resize the row height for the button group to fit
                        event.height = 35;
                    }
                });

                table.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
                tpdoTableViewer.refresh();

                TableColumn tblclmnSno = new TableColumn(table, SWT.CENTER);
                tblclmnSno.setWidth(100);
                tblclmnSno.setText(PDO_TABLE_COLUMN_NO_LABEL);

                tpdoMappingObjectColumn = new TableViewerColumn(tpdoTableViewer,
                        SWT.BORDER | SWT.FULL_SELECTION);

                TableColumn tblclmnMappingObject = tpdoMappingObjectColumn
                        .getColumn();
                tblclmnMappingObject.setWidth(100);
                tblclmnMappingObject
                        .setText(PDO_TABLE_COLUMN_MAPPED_OBJECT_LABEL);

                TableColumn tableColumnStatus = new TableColumn(table,
                        SWT.CENTER);
                tableColumnStatus.setWidth(15);
                tableColumnStatus.setText("");
                tableColumnStatus.setAlignment(SWT.CENTER);

                TableColumn tblclmnSizebytes = new TableColumn(table,
                        SWT.CENTER);
                tblclmnSizebytes.setWidth(100);
                tblclmnSizebytes.setText(PDO_TABLE_COLUMN_SIZE_LABEL);

                tpdoTblclmnActualValue = new TableColumn(table, SWT.NONE);
                tpdoTblclmnActualValue.setWidth(120);
                tpdoTblclmnActualValue
                        .setText(PDO_TABLE_COLUMN_ACTUAL_VALUE_LABEL);
                tpdoTblclmnActualValue.setResizable(true);

                tpdoActionsColumn = new TableViewerColumn(tpdoTableViewer,
                        SWT.NONE);
                TableColumn tblclmnActions = tpdoActionsColumn.getColumn();
                tblclmnActions.setWidth(100);
                tblclmnActions.setText(PDO_TABLE_COLUMN_ACTIONS_LABEL);

                tpdoPageFooter = new Composite(composite_3, SWT.NONE);
                RowLayout rl_tpdoPageFooter = new RowLayout(SWT.HORIZONTAL);
                rl_tpdoPageFooter.justify = true;
                rl_tpdoPageFooter.pack = false;
                rl_tpdoPageFooter.center = true;
                tpdoPageFooter.setLayout(rl_tpdoPageFooter);
                tpdoPageFooter.setLayoutData(
                        new GridData(SWT.CENTER, SWT.FILL, true, false, 1, 1));

                btnTpdoChannelMapAvailableObjects = new Button(tpdoPageFooter,
                        SWT.NONE);
                btnTpdoChannelMapAvailableObjects
                        .setText(PDO_MAPP_ALL_AVAILABLE_OBJECTS_LABEL);

                btnTpdoChannelClearSelectedRows = new Button(tpdoPageFooter,
                        SWT.NONE);
                btnTpdoChannelClearSelectedRows
                        .setText(PDO_CLEAR_ALL_MAPPING_LABEL);

                tpdoActionsbuttonGroup = new Composite(
                        (Composite) tpdoTableViewer.getControl(), SWT.NONE);
                tpdoActionsbuttonGroup.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
                tpdoActionsbuttonGroup.setLayout(new GridLayout(3, false));

                tpdoActionsUpButton = new Button(tpdoActionsbuttonGroup,
                        SWT.NONE);
                tpdoActionsUpButton.setToolTipText("Move up");
                tpdoActionsUpButton.setImage(upArrowImage);

                tpdoActionsDownButton = new Button(tpdoActionsbuttonGroup,
                        SWT.NONE);
                tpdoActionsDownButton.setToolTipText("Move down");
                tpdoActionsDownButton.setImage(downArrowImage);

                Button tpdoActionsClrbutton = new Button(tpdoActionsbuttonGroup,
                        SWT.NONE);
                tpdoActionsClrbutton.setToolTipText("Clear");
                tpdoActionsClrbutton.setImage(clearImage);

                tpdoTableActionsEditor = new TableEditor(table);
                tpdoTableActionsEditor.grabHorizontal = true;
                tpdoTableActionsEditor.setColumn(5);

                tpdoEnabledMappingEntriesTextModifyListener = new EnabledEntriesTextModifyListener(
                        tpdoChannelComboViewer, tpdoTableViewer);
                tpdoMappingObjClmnEditingSupport = new PdoMappingObjectColumnEditingSupport(
                        tpdoTableViewer, tpdoChannelComboViewer);
                tpdoMapAvailableObjectsBtnSelectionListener = new MapAvailableObjectsButtonSelectionListener(
                        tpdoChannelComboViewer, tpdoTableViewer);
                tpdoClearAllMappingBtnSelectionListener = new ClearAllObjectsButtonSelectionListenr(
                        tpdoChannelComboViewer, tpdoTableViewer);
                tpdoActionsUpBtnSelectionListener = new PdoActionsUpButtonSelectionListener(
                        tpdoChannelComboViewer, tpdoTableViewer);
                tpdoActionsDownBtnSelectionListener = new PdoActionsDownButtonSelectionListener(
                        tpdoChannelComboViewer, tpdoTableViewer);
                tpdoActionsClearBtnSelectionListener = new PdoActionsClearButtonSelectionListener(
                        tpdoChannelComboViewer, tpdoTableViewer);
                tpdoNodeComboSelectionChangeListener = new PdoNodeIdComboSelectionChangedListener(
                        tpdoChannelComboViewer);

                new Label(tpdoHeaderFrame, SWT.NONE);
                new Label(tpdoHeaderFrame, SWT.NONE);
                new Label(tpdoHeaderFrame, SWT.NONE);
                new Label(tpdoHeaderFrame, SWT.NONE);

                tpdoBtnCheckButton = new Button(tpdoHeaderFrame, SWT.CHECK);
                tpdoBtnCheckButton.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        tpdoProfileObjectSelection = tpdoBtnCheckButton
                                .getSelection();
                        tpdoMappingObjClmnEditingSupport.setInput(
                                getMappableObjectsList(nodeObj, PdoType.TPDO));
                        tpdoTableViewer.refresh();
                    }
                });
                formToolkit.adapt(tpdoBtnCheckButton, true, true);
                tpdoBtnCheckButton.setText("Hide Profile Objects");

                tpdoChannelComboViewer
                        .setContentProvider(ArrayContentProvider.getInstance());
                tpdoChannelComboViewer
                        .setLabelProvider(new PdoChannelViewerLabelProvider());

                sndtoNodecomboviewer
                        .setContentProvider(ArrayContentProvider.getInstance());
                sndtoNodecomboviewer.setLabelProvider(
                        new PdoNodeListLabelProvider(PdoType.TPDO));
                sndtoNodecomboviewer.addSelectionChangedListener(
                        tpdoNodeComboSelectionChangeListener);

                tpdoTableViewer
                        .setContentProvider(new PdoTableContentProvider());
                tpdoTableViewer.setLabelProvider(
                        new PdoTableLabelProvider(PdoType.TPDO));
                tpdoTableViewer.addSelectionChangedListener(
                        tpdoTableRowSelectionChangeListener);
                tpdoChannelSelectionChangeListener = new ChannelSelectionChangedListener();

                tpdoChannelComboViewer.addSelectionChangedListener(
                        tpdoChannelSelectionChangeListener);
                tpdoMappingObjectColumn
                        .setEditingSupport(tpdoMappingObjClmnEditingSupport);

                tpdoActionsUpButton.addSelectionListener(
                        tpdoActionsUpBtnSelectionListener);
                tpdoActionsDownButton.addSelectionListener(
                        tpdoActionsDownBtnSelectionListener);
                tpdoActionsClrbutton.addSelectionListener(
                        tpdoActionsClearBtnSelectionListener);

                btnTpdoChannelMapAvailableObjects.addSelectionListener(
                        tpdoMapAvailableObjectsBtnSelectionListener);
                btnTpdoChannelClearSelectedRows.addSelectionListener(
                        tpdoClearAllMappingBtnSelectionListener);
                tpdoEnabledMappingEntriesText
                        .addVerifyListener(enabledEntriesVerifyListener);
                tpdoEnabledMappingEntriesText.addModifyListener(
                        tpdoEnabledMappingEntriesTextModifyListener);

            }

            tbtmRpdo = new TabItem(tabFolder, SWT.NONE);
            tbtmRpdo.setText(RPDO_TAB_TITLE);
            {
                Composite composite_3 = new Composite(tabFolder, SWT.NONE);
                tbtmRpdo.setControl(composite_3);
                composite_3.setLayout(new GridLayout(1, false));

                Composite composite_5 = new Composite(composite_3, SWT.BORDER);
                composite_5.setLayout(new GridLayout(5, false));
                composite_5.setLayoutData(
                        new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));

                Label lblChannel = new Label(composite_5, SWT.NONE);
                lblChannel.setText(PDO_CHANNEL_LABEL);

                rpdoChannelComboViewer = new ComboViewer(composite_5,
                        SWT.READ_ONLY);
                Combo combo = rpdoChannelComboViewer.getCombo();
                GridData gd_combo = new GridData(SWT.LEFT, SWT.CENTER, false,
                        false, 1, 1);
                gd_combo.widthHint = 175;
                combo.setLayoutData(gd_combo);

                combo.setVisibleItemCount(10);
                combo.pack();

                Label lblNewLabel = new Label(composite_5, SWT.NONE);
                lblNewLabel.setLayoutData(
                        new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
                lblNewLabel.setText("        ");

                Label lblEnabledMapping = new Label(composite_5, SWT.NONE);
                lblEnabledMapping.setText(PDO_ENABLED_MAPPING_ENTRIES);

                rpdoEnabledMappingEntriesText = new Text(composite_5,
                        SWT.BORDER);
                GridData gd_rpdoEnabledMappingEntriesText = new GridData(
                        SWT.FILL, SWT.CENTER, false, false, 1, 1);
                gd_rpdoEnabledMappingEntriesText.widthHint = 100;
                rpdoEnabledMappingEntriesText
                        .setLayoutData(gd_rpdoEnabledMappingEntriesText);

                Label lblReceiveFromNode = new Label(composite_5, SWT.NONE);
                lblReceiveFromNode.setText(PDO_RECEIVE_FROM_NODE_LABEL);

                receiveFromNodecomboviewer = new ComboViewer(composite_5,
                        SWT.READ_ONLY);
                Combo receiveFromNodeCombo = receiveFromNodecomboviewer
                        .getCombo();
                receiveFromNodeCombo.setEnabled(false);

                GridData gd_sndtonodecombo = new GridData(SWT.LEFT, SWT.CENTER,
                        false, false, 1, 1);
                gd_sndtonodecombo.widthHint = 175;
                receiveFromNodeCombo.setLayoutData(gd_sndtonodecombo);

                Label lblNewLabel_4 = new Label(composite_5, SWT.NONE);
                lblNewLabel_4.setLayoutData(
                        new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
                lblNewLabel_4.setText("        ");

                Label lblTotalSize = new Label(composite_5, SWT.NONE);
                lblTotalSize.setText(PDO_CHANNEL_SIZE_LABEL);

                rpdoChannelSize = new Text(composite_5,
                        SWT.READ_ONLY | SWT.BORDER);
                rpdoChannelSize.setEnabled(false);
                GridData gd_rpdoChannelSizeText = new GridData(SWT.FILL,
                        SWT.CENTER, false, false, 1, 1);
                gd_rpdoChannelSizeText.widthHint = 100;
                rpdoChannelSize.setLayoutData(gd_rpdoChannelSizeText);

                Composite composite_7 = new Composite(composite_3, SWT.NONE);
                composite_7.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
                GridLayout gl_composite_7 = new GridLayout(1, false);
                gl_composite_7.marginWidth = 0;
                composite_7.setLayout(gl_composite_7);

                rpdoTableViewer = new TableViewer(composite_7,
                        SWT.BORDER | SWT.FULL_SELECTION);

                Table table = rpdoTableViewer.getTable();
                table.setHeaderVisible(true);
                table.setLinesVisible(true);
                table.addListener(SWT.MeasureItem, new Listener() {

                    @Override
                    public void handleEvent(Event event) {
                        // Resize the row height for the button group to fit
                        event.height = 35;
                    }
                });

                table.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
                rpdoTableViewer.refresh();

                TableColumn tblclmnSno = new TableColumn(table, SWT.CENTER);
                tblclmnSno.setWidth(100);
                tblclmnSno.setText(PDO_TABLE_COLUMN_NO_LABEL);

                rpdoMappingObjectColumn = new TableViewerColumn(rpdoTableViewer,
                        SWT.BORDER | SWT.FULL_SELECTION);

                TableColumn tblclmnMappingObject = rpdoMappingObjectColumn
                        .getColumn();
                tblclmnMappingObject.setWidth(100);
                tblclmnMappingObject
                        .setText(PDO_TABLE_COLUMN_MAPPED_OBJECT_LABEL);

                TableColumn tableColumnStatus = new TableColumn(table,
                        SWT.CENTER);
                tableColumnStatus.setWidth(15);
                tableColumnStatus.setText("");
                tableColumnStatus.setAlignment(SWT.CENTER);

                TableColumn tblclmnSizebytes = new TableColumn(table,
                        SWT.CENTER);
                tblclmnSizebytes.setWidth(100);
                tblclmnSizebytes.setText(PDO_TABLE_COLUMN_SIZE_LABEL);

                rpdoTblclmnActualValue = new TableColumn(table, SWT.NONE);
                rpdoTblclmnActualValue.setWidth(120);
                rpdoTblclmnActualValue
                        .setText(PDO_TABLE_COLUMN_ACTUAL_VALUE_LABEL);
                rpdoTblclmnActualValue.setResizable(true);

                rpdoActionsColumn = new TableViewerColumn(rpdoTableViewer,
                        SWT.NONE);
                TableColumn tblclmnActions = rpdoActionsColumn.getColumn();
                tblclmnActions.setWidth(100);
                tblclmnActions.setText(PDO_TABLE_COLUMN_ACTIONS_LABEL);

                rpdoPageFooter = new Composite(composite_3, SWT.NONE);
                RowLayout rl_rpdoPageFooter = new RowLayout(SWT.HORIZONTAL);
                rl_rpdoPageFooter.justify = true;
                rl_rpdoPageFooter.pack = false;
                rl_rpdoPageFooter.center = true;
                rpdoPageFooter.setLayout(rl_rpdoPageFooter);
                rpdoPageFooter.setLayoutData(
                        new GridData(SWT.CENTER, SWT.FILL, true, false, 1, 1));

                btnRpdoChannelMapAvailableObjects = new Button(rpdoPageFooter,
                        SWT.NONE);
                btnRpdoChannelMapAvailableObjects
                        .setText(PDO_MAPP_ALL_AVAILABLE_OBJECTS_LABEL);

                btnRpdoChannelClearSelectedRows = new Button(rpdoPageFooter,
                        SWT.NONE);
                btnRpdoChannelClearSelectedRows
                        .setText(PDO_CLEAR_ALL_MAPPING_LABEL);

                rpdoActionsbuttonGroup = new Composite(
                        (Composite) rpdoTableViewer.getControl(), SWT.NONE);
                rpdoActionsbuttonGroup.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
                rpdoActionsbuttonGroup.setLayout(new GridLayout(3, false));

                rpdoActionsUpButton = new Button(rpdoActionsbuttonGroup,
                        SWT.NONE);
                rpdoActionsUpButton.setToolTipText("Move up");
                rpdoActionsUpButton.setImage(upArrowImage);

                rpdoActionsDownButton = new Button(rpdoActionsbuttonGroup,
                        SWT.NONE);
                rpdoActionsDownButton.setToolTipText("Move down");
                rpdoActionsDownButton.setImage(downArrowImage);

                Button clrbutton = new Button(rpdoActionsbuttonGroup, SWT.NONE);
                clrbutton.setToolTipText("Clear");
                clrbutton.setImage(clearImage);

                rpdoTableActionsEditor = new TableEditor(table);
                rpdoTableActionsEditor.grabHorizontal = true;
                rpdoTableActionsEditor.setColumn(5);

                receiveFromNodecomboviewer
                        .setContentProvider(ArrayContentProvider.getInstance());
                receiveFromNodecomboviewer.setLabelProvider(
                        new PdoNodeListLabelProvider(PdoType.RPDO));
                rpdoNodeComboSelectionChangeListener = new PdoNodeIdComboSelectionChangedListener(
                        rpdoChannelComboViewer);
                receiveFromNodecomboviewer.addSelectionChangedListener(
                        rpdoNodeComboSelectionChangeListener);
                rpdoTableViewer
                        .setContentProvider(new PdoTableContentProvider());
                rpdoTableViewer.setLabelProvider(
                        new PdoTableLabelProvider(PdoType.RPDO));
                rpdoTableViewer.addSelectionChangedListener(
                        rpdoTableRowSelectionChangeListener);
                rpdoChannelSelectionChangeListener = new ChannelSelectionChangedListener();
                rpdoChannelComboViewer.addSelectionChangedListener(
                        rpdoChannelSelectionChangeListener);
                rpdoChannelComboViewer
                        .setContentProvider(ArrayContentProvider.getInstance());
                rpdoChannelComboViewer
                        .setLabelProvider(new PdoChannelViewerLabelProvider());

                rpdoMappingObjClmnEditingSupport = new PdoMappingObjectColumnEditingSupport(
                        rpdoTableViewer, rpdoChannelComboViewer);
                rpdoMappingObjectColumn
                        .setEditingSupport(rpdoMappingObjClmnEditingSupport);

                rpdoMapAvailableObjectsBtnSelectionListener = new MapAvailableObjectsButtonSelectionListener(
                        rpdoChannelComboViewer, rpdoTableViewer);
                btnRpdoChannelMapAvailableObjects.addSelectionListener(
                        rpdoMapAvailableObjectsBtnSelectionListener);

                rpdoClearAllMappingBtnSelectionListener = new ClearAllObjectsButtonSelectionListenr(
                        rpdoChannelComboViewer, rpdoTableViewer);
                btnRpdoChannelClearSelectedRows.addSelectionListener(
                        rpdoClearAllMappingBtnSelectionListener);

                rpdoEnabledMappingEntriesTextModifyListener = new EnabledEntriesTextModifyListener(
                        rpdoChannelComboViewer, rpdoTableViewer);
                rpdoEnabledMappingEntriesText.addModifyListener(
                        rpdoEnabledMappingEntriesTextModifyListener);
                rpdoEnabledMappingEntriesText
                        .addVerifyListener(enabledEntriesVerifyListener);
                rpdoActionsUpBtnSelectionListener = new PdoActionsUpButtonSelectionListener(
                        rpdoChannelComboViewer, rpdoTableViewer);
                rpdoActionsUpButton.addSelectionListener(
                        rpdoActionsUpBtnSelectionListener);
                rpdoActionsDownBtnSelectionListener = new PdoActionsDownButtonSelectionListener(
                        rpdoChannelComboViewer, rpdoTableViewer);
                rpdoActionsDownButton.addSelectionListener(
                        rpdoActionsDownBtnSelectionListener);
                rpdoActionsClearBtnSelectionListener = new PdoActionsClearButtonSelectionListener(
                        rpdoChannelComboViewer, rpdoTableViewer);

                new Label(composite_5, SWT.NONE);
                new Label(composite_5, SWT.NONE);
                new Label(composite_5, SWT.NONE);
                new Label(composite_5, SWT.NONE);

                rpdoBtnCheckButton = new Button(composite_5, SWT.CHECK);
                rpdoBtnCheckButton.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        rpdoProfileObjectSelection = rpdoBtnCheckButton
                                .getSelection();
                        rpdoMappingObjClmnEditingSupport.setInput(
                                getMappableObjectsList(nodeObj, PdoType.RPDO));
                        rpdoTableViewer.refresh();
                    }
                });

                formToolkit.adapt(rpdoBtnCheckButton, true, true);
                rpdoBtnCheckButton.setText("Hide Profile Objects");

                clrbutton.addSelectionListener(
                        rpdoActionsClearBtnSelectionListener);
            }
        }

        /**
         * SelectionListener to switch between various tabs in Mapping view
         */
        tabFolder.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(
                    org.eclipse.swt.events.SelectionEvent event) {
                if (tabFolder.getSelection()[0] == tbtmPdoConfiguration) {
                    tpdoSummaryTableViewer.refresh();
                    rpdoSummaryTableViewer.refresh();
                }

                if (tabFolder.getSelection()[0] == tbtmTpdo) {
                    tpdoTableViewer.refresh();
                    // updatePdoTable(
                    // nodeObj.getObjectDictionary().getTpdoChannel());
                }

                if (tabFolder.getSelection()[0] == tbtmRpdo) {
                    rpdoTableViewer.refresh();
                    // updatePdoTable(
                    // nodeObj.getObjectDictionary().getRpdoChannel());
                }
            }

        });

        createActions();
        contributeToActionBars();
        initializeToolBar();

        initializeMenu();

        getViewSite().getPage().addSelectionListener(IndustrialNetworkView.ID,
                networkViewPartSelectionListener);
    }

    public void displayMappingView(Node nodeObj) {
        if (!nodeObj.isEnabled()
                || (nodeObj.getISO15745ProfileContainer() == null)) {
            setPartName("Mapping View");

            // Summary
            tpdoSummaryTableViewer.setInput(null);
            rpdoSummaryTableViewer.setInput(null);

            // TPDO page
            tpdoChannelComboViewer.setInput(null);
            tpdoTableViewer.setInput(null);
            sndtoNodecomboviewer.setInput(null);
            setPdoGuiControlsEnabled(PdoType.TPDO, false);
            tpdoActionsbuttonGroup.setVisible(false);
            tpdoEnabledMappingEntriesText
                    .removeVerifyListener(enabledEntriesVerifyListener);
            tpdoEnabledMappingEntriesText.setText(StringUtils.EMPTY);
            tpdoEnabledMappingEntriesText
                    .addVerifyListener(enabledEntriesVerifyListener);
            tpdoEnabledEntriesCount = getEnabledEntriesCount(PdoType.TPDO);
            tpdoEnabledMappingEntriesText.setEnabled(false);
            tpdoChannelSize.setText(StringUtils.EMPTY);

            // RPDO page
            rpdoChannelComboViewer.setInput(null);
            rpdoTableViewer.setInput(null);
            receiveFromNodecomboviewer.setInput(null);
            setPdoGuiControlsEnabled(PdoType.RPDO, false);
            rpdoActionsbuttonGroup.setVisible(false);
            rpdoEnabledMappingEntriesText
                    .removeVerifyListener(enabledEntriesVerifyListener);
            rpdoEnabledMappingEntriesText.setText(StringUtils.EMPTY);
            rpdoEnabledMappingEntriesText
                    .addVerifyListener(enabledEntriesVerifyListener);
            rpdoEnabledEntriesCount = getEnabledEntriesCount(PdoType.RPDO);
            rpdoEnabledMappingEntriesText.setEnabled(false);
            rpdoChannelSize.setText(StringUtils.EMPTY);
            return;
        }

        updateTargetNodeIdList();

        // PDO summary page
        tpdoSummaryTableViewer.setInput(nodeObj);
        rpdoSummaryTableViewer.setInput(nodeObj);

        // TPDO page
        List<TpdoChannel> tpdoChannels = nodeObj.getObjectDictionary()
                .getTpdoChannelsList();
        tpdoChannelComboViewer.setInput(tpdoChannels);
        if (tpdoChannels.size() > 0) {
            ISelection channelSelection = new StructuredSelection(
                    tpdoChannels.get(0));
            tpdoChannelComboViewer.setSelection(channelSelection, true);
            tpdoMappingObjClmnEditingSupport
                    .setInput(getMappableObjectsList(nodeObj, PdoType.TPDO));
        } else {
            tpdoTableViewer.setInput(null);
            sndtoNodecomboviewer.setInput(null);
            sndtoNodecomboviewer.setSelection(null, true);
        }

        handlePdoTableResize(PdoType.TPDO, showAdvancedview.isChecked());
        tpdoEnabledEntriesCount = getEnabledEntriesCount(PdoType.TPDO);
        System.err.println("The count for enabled entries in TPDO..."
                + tpdoEnabledEntriesCount);
        // RPDO Page
        List<RpdoChannel> rpdoChannels = nodeObj.getObjectDictionary()
                .getRpdoChannelsList();
        rpdoChannelComboViewer.setInput(rpdoChannels);
        if (rpdoChannels.size() > 0) {
            ISelection channelSelection = new StructuredSelection(
                    rpdoChannels.get(0));
            rpdoChannelComboViewer.setSelection(channelSelection, true);
            rpdoMappingObjClmnEditingSupport
                    .setInput(getMappableObjectsList(nodeObj, PdoType.RPDO));
        } else {
            rpdoTableViewer.setInput(null);
            receiveFromNodecomboviewer.setInput(null);
            receiveFromNodecomboviewer.setSelection(null, true);
        }

        handlePdoTableResize(PdoType.RPDO, showAdvancedview.isChecked());
        rpdoEnabledEntriesCount = getEnabledEntriesCount(PdoType.RPDO);
        Object nodeObjModel = nodeObj.getNodeModel();

        Integer nodeIdVal = Integer.valueOf(nodeObj.getNodeIdString());
        if (nodeIdVal == IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
            String[] presItems = { "Yes", "No" };
            nodeTypeCombo.setItems(presItems);
            if (nodeObj.getNodeModel() instanceof TNetworkConfiguration) {
                TNetworkConfiguration net = (TNetworkConfiguration) nodeObj
                        .getNodeModel();
                TMN mn = net.getNodeCollection().getMN();
                if (mn.isTransmitsPRes()) {

                    nodeTypeCombo.select(0);
                } else {
                    nodeTypeCombo.select(1);
                }
            }
        } else {

            nodeTypeCombo.setItems(nodeTypeItems);
            if (nodeObj.getPlkOperationMode() == PlkOperationMode.CHAINED) {
                nodeTypeCombo.select(1);

            } else {
                nodeTypeCombo.select(0);
            }
        }

        if (nodeObjModel instanceof TCN) {
            addListenersToCnControls();
            handleGeneralInfoForCn(nodeObj);
        } else if (nodeObjModel instanceof TNetworkConfiguration) {
            addListenersToMnControls();
            handleGeneralInfoForMn(nodeObj);
        } else if (nodeObjModel instanceof TRMN) {
            addListenersToRmnControls();
            handleGeneralInfoForRmn(nodeObj);
        } else if (nodeObjModel instanceof TMN) {
            addListenersToMnControls();
            handleGeneralInfoForMn(nodeObj);
        }

        txt_no_nodename.setText(nodeObj.getName());

        lst_no_foi.removeAll();

        listViewer.setInput(nodeObj);

    }

    @Override
    public void dispose() {
        super.dispose();
        if (sourcePart != null) {
            sourcePart.getSite().getPage().removePartListener(partListener);
        }

        getViewSite().getPage().removeSelectionListener(
                IndustrialNetworkView.ID, networkViewPartSelectionListener);

        clearImage.dispose();
        upArrowImage.dispose();
        downArrowImage.dispose();
        warningImage.dispose();
        errorImage.dispose();
        signedYesImage.dispose();
        signedDisableImage.dispose();
        epsgImage.dispose();
    }

    /**
     * List of actions in view tool bar.
     *
     * @param manager
     */
    private void fillLocalToolBar(IToolBarManager manager) {
        manager.removeAll();
        manager.add(showAdvancedview);
    }

    private int getEnabledEntriesCount(PdoType pdoType) {
        try {
            switch (pdoType) {
                case TPDO:
                    if (!tpdoEnabledMappingEntriesText.getText().isEmpty()) {
                        tpdoEnabledEntriesCount = Integer.parseInt(
                                tpdoEnabledMappingEntriesText.getText().trim());
                    }
                    return tpdoEnabledEntriesCount;

                case RPDO:
                    if (!rpdoEnabledMappingEntriesText.getText().isEmpty()) {
                        rpdoEnabledEntriesCount = Integer.parseInt(
                                rpdoEnabledMappingEntriesText.getText().trim());
                    }
                    return rpdoEnabledEntriesCount;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Returns the mappable object based on the mapping value available in the
     * given mapping sub-object.
     *
     * @param mappingSubObj The mapping sub-object.
     * @return The object.
     */
    private AbstractPowerlinkObject getMappableObject(
            PowerlinkSubobject mappingSubObj) {
        String value = mappingSubObj.getActualDefaultValue();

        if (value.isEmpty()) {
            return emptyObject;
        }

        if (value.length() != 18) {
            if ((value.length() == 3) || (value.length() == 1)) {
                if (Integer.decode(value) == 0) {
                    return emptyObject;
                }
            }
            return null;
        }

        String objectId = value.substring(14, 18);
        long objectIdValue = Long.parseLong(objectId, 16);

        if (objectIdValue == 0) {
            return emptyObject;
        }

        PowerlinkObject mappableObject = nodeObj.getObjectDictionary()
                .getObject(objectIdValue);
        if (mappableObject != null) {
            String subObjectId = value.substring(12, 14);
            short subObjectIdValue = Short.parseShort(subObjectId, 16);
            if (mappableObject.getObjectType() == 7) {
                if (subObjectIdValue == 0) {
                    return mappableObject;
                }
            }

            PowerlinkSubobject mappableSubObject = mappableObject
                    .getSubObject(subObjectIdValue);
            if (mappableSubObject != null) {
                return mappableSubObject;
            }
        }

        return null;
    }

    /**
     * Returns the name of the object available in the mapping value.
     *
     * @param mappingSubObj The mapping sub-object in which the mapping value is
     *            present.
     * @return The mappable object name.
     */
    private String getMappableObjectName(PowerlinkSubobject mappingSubObj) {

        String value = mappingSubObj.getActualDefaultValue();

        if (value.isEmpty()) {
            return emptyObject.getNameWithId();
        }

        if (value.length() != 18) {
            if ((value.length() == 3) || (value.length() == 1)) {
                if (Integer.decode(value) == 0) {
                    return emptyObject.getNameWithId();
                }
            }
            return "Invalid value(" + value + ")";
        }

        String objectId = value.substring(14, 18);
        long objectIdValue = 0;
        try {
            objectIdValue = Long.parseLong(objectId, 16);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return "Invalid value(" + value + ")";
        }

        if (objectIdValue == 0) {
            return emptyObject.getNameWithId();
        }

        String subObjectId = value.substring(12, 14);

        if (nodeObj.isModularheadNode()) {
            List<HeadNodeInterface> interfaceList = nodeObj
                    .getHeadNodeInterface();
            if (interfaceList != null) {
                for (HeadNodeInterface headNodeInterface : interfaceList) {
                    Collection<Module> moduleList = headNodeInterface
                            .getModuleCollection().values();
                    if (moduleList != null) {
                        for (Module module : moduleList) {
                            List<PowerlinkObject> objectList = module
                                    .getObjectDictionary().getObjectsList();
                            for (PowerlinkObject object : objectList) {
                                if (object.isTpdoMappable()
                                        || object.isRpdoMappable()) {
                                    long objectIndex = OpenConfiguratorLibraryUtils
                                            .getModuleObjectsIndex(
                                                    object.getModule(),
                                                    object.getId());
                                    return object.getNameWithId(objectIndex);
                                }

                                short subObjectIdValue = 0;
                                subObjectIdValue = Short.parseShort(subObjectId,
                                        16);
                                for (PowerlinkSubobject subObject : object
                                        .getTpdoMappableObjectList()) {
                                    long objectIndex = OpenConfiguratorLibraryUtils
                                            .getModuleObjectsIndex(
                                                    subObject.getModule(),
                                                    subObject.getObject()
                                                            .getId());
                                    int subObjectIndex = OpenConfiguratorLibraryUtils
                                            .getModuleObjectsSubIndex(
                                                    subObject.getModule(),
                                                    subObject,
                                                    subObject.getObject()
                                                            .getId());
                                    if (objectIdValue == objectIndex) {
                                        if (subObjectIndex == subObjectIdValue) {
                                            return subObject.getNameWithId(
                                                    objectIndex,
                                                    subObjectIndex);
                                        }
                                    }
                                }
                                for (PowerlinkSubobject subObject : object
                                        .getRpdoMappableObjectList()) {
                                    long objectIndex = OpenConfiguratorLibraryUtils
                                            .getModuleObjectsIndex(
                                                    subObject.getModule(),
                                                    subObject.getObject()
                                                            .getId());
                                    int subObjectIndex = OpenConfiguratorLibraryUtils
                                            .getModuleObjectsSubIndex(
                                                    subObject.getModule(),
                                                    subObject,
                                                    subObject.getObject()
                                                            .getId());
                                    if (objectIdValue == objectIndex) {
                                        if (subObjectIndex == subObjectIdValue) {
                                            return subObject.getNameWithId(
                                                    objectIndex,
                                                    subObjectIndex);
                                        }
                                    }
                                }

                            }
                        }
                    } else {
                        System.err.println(
                                "No modules are available under interface.");
                    }
                }
            }
        }

        PowerlinkObject mappableObject = nodeObj.getObjectDictionary()
                .getObject(objectIdValue);

        if (mappableObject != null) {
            short subObjectIdValue = 0;
            try {
                subObjectIdValue = Short.parseShort(subObjectId, 16);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                return "Invalid value(" + value + ")";
            }

            if (mappableObject.getObjectType() == 7) {
                if (subObjectIdValue == 0) {
                    return mappableObject.getNameWithId();
                }
            }

            PowerlinkSubobject mappableSubObject = mappableObject
                    .getSubObject(subObjectIdValue);
            if (mappableSubObject != null) {
                return mappableSubObject.getUniqueName();
            }
        }

        return "Not available (0x" + objectId + ")/(0x" + subObjectId + ")";
    }

    /**
     * Returns the list of available Mappable Objects or Sub-Objects in the
     * nodes object dictionary.
     *
     * @param nodeObj The node instance.
     * @param pdoType The PDO type.
     * @return The objects list.
     */
    private List<AbstractPowerlinkObject> getMappableObjectsList(Node nodeObj,
            PdoType pdoType) {
        List<AbstractPowerlinkObject> objectList = new ArrayList<>();
        objectList.add(emptyObject);

        if (nodeObj.isModularheadNode()) {
            List<HeadNodeInterface> interfaceList = nodeObj
                    .getHeadNodeInterface();
            if (interfaceList != null) {
                for (HeadNodeInterface headNodeInterface : interfaceList) {
                    Collection<Module> moduleList = headNodeInterface
                            .getModuleCollection().values();
                    if (moduleList != null) {
                        for (Module module : moduleList) {
                            if (pdoType == PdoType.TPDO) {
                                List<PowerlinkObject> tpdoMappableObjListOfModule = module
                                        .getObjectDictionary()
                                        .getTpdoMappableObjectList();
                                for (PowerlinkObject plkObj : tpdoMappableObjListOfModule) {
                                    if (plkObj.isTpdoMappable()) {
                                        long objectIndex = OpenConfiguratorLibraryUtils
                                                .getModuleObjectsIndex(
                                                        plkObj.getModule(),
                                                        plkObj.getId());
                                        if (tpdoProfileObjectSelection) {
                                            if ((objectIndex < IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_START_INDEX)
                                                    || (objectIndex > IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_END_INDEX)) {
                                                objectList.add(plkObj);
                                            }

                                        } else {
                                            objectList.add(plkObj);
                                        }
                                    }

                                    for (PowerlinkSubobject plkSubobj : plkObj
                                            .getTpdoMappableObjectList()) {
                                        if (tpdoProfileObjectSelection) {
                                            long objectIndex = OpenConfiguratorLibraryUtils
                                                    .getModuleObjectsIndex(
                                                            plkSubobj
                                                                    .getModule(),
                                                            plkSubobj
                                                                    .getObject()
                                                                    .getId());
                                            if ((objectIndex < IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_START_INDEX)
                                                    || (objectIndex > IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_END_INDEX)) {
                                                objectList.add(plkSubobj);
                                            }

                                        } else {
                                            objectList.add(plkSubobj);
                                        }
                                    }
                                }
                            } else if (pdoType == PdoType.RPDO) {
                                List<PowerlinkObject> rpdoMappableObjListOfModule = module
                                        .getObjectDictionary()
                                        .getRpdoMappableObjectList();

                                for (PowerlinkObject plkObj : rpdoMappableObjListOfModule) {
                                    if (plkObj.isRpdoMappable()) {
                                        long objectIndex = OpenConfiguratorLibraryUtils
                                                .getModuleObjectsIndex(
                                                        plkObj.getModule(),
                                                        plkObj.getId());
                                        if (rpdoProfileObjectSelection) {
                                            if ((objectIndex < IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_START_INDEX)
                                                    || (objectIndex > IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_END_INDEX)) {
                                                objectList.add(plkObj);
                                            }

                                        } else {
                                            objectList.add(plkObj);
                                        }
                                    }

                                    for (PowerlinkSubobject plkSubobj : plkObj
                                            .getRpdoMappableObjectList()) {
                                        if (rpdoProfileObjectSelection) {
                                            long objectIndex = OpenConfiguratorLibraryUtils
                                                    .getModuleObjectsIndex(
                                                            plkSubobj
                                                                    .getModule(),
                                                            plkSubobj
                                                                    .getObject()
                                                                    .getId());
                                            if ((objectIndex < IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_START_INDEX)
                                                    || (objectIndex > IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_END_INDEX)) {
                                                objectList.add(plkSubobj);
                                            }

                                        } else {
                                            objectList.add(plkSubobj);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        System.err.println(
                                "No modules are available under interface.");
                    }
                }
            }
        }

        if (pdoType == PdoType.TPDO) {
            List<PowerlinkObject> tpdoMappableObjList = nodeObj
                    .getObjectDictionary().getTpdoMappableObjectList();

            for (PowerlinkObject plkObj : tpdoMappableObjList) {
                if (plkObj.isTpdoMappable()) {
                    if (tpdoProfileObjectSelection) {
                        if ((plkObj
                                .getId() < IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_START_INDEX)
                                || (plkObj
                                        .getId() > IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_END_INDEX)) {
                            objectList.add(plkObj);
                        }
                    } else {
                        objectList.add(plkObj);
                    }
                }

                for (PowerlinkSubobject plkSubobj : plkObj
                        .getTpdoMappableObjectList()) {
                    if (tpdoProfileObjectSelection) {
                        if ((plkSubobj.getObject()
                                .getId() < IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_START_INDEX)
                                || (plkSubobj.getObject()
                                        .getId() > IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_END_INDEX)) {
                            objectList.add(plkSubobj);
                        }
                    } else {
                        objectList.add(plkSubobj);
                    }
                }
            }
        } else if (pdoType == PdoType.RPDO) {
            List<PowerlinkObject> rpdoMappableObjList = nodeObj
                    .getObjectDictionary().getRpdoMappableObjectList();

            for (PowerlinkObject plkObj : rpdoMappableObjList) {
                if (plkObj.isRpdoMappable()) {
                    if (rpdoProfileObjectSelection) {
                        if ((plkObj
                                .getId() < IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_START_INDEX)
                                || (plkObj
                                        .getId() > IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_END_INDEX)) {
                            objectList.add(plkObj);
                        }
                    } else {
                        objectList.add(plkObj);
                    }
                }

                for (PowerlinkSubobject plkSubobj : plkObj
                        .getRpdoMappableObjectList()) {
                    if (rpdoProfileObjectSelection) {
                        if ((plkSubobj.getObject()
                                .getId() < IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_START_INDEX)
                                || (plkSubobj.getObject()
                                        .getId() > IPowerlinkConstants.STANDARDISED_DEVICE_PROFILE_END_INDEX)) {
                            objectList.add(plkSubobj);
                        }
                    } else {
                        objectList.add(plkSubobj);
                    }
                }
            }
        } else {
            System.err.println("Invalid pdo type" + pdoType);
        }

        return objectList;
    }

    /**
     * @return The root node instance available from the
     *         {@link IndustrialNetworkView}.
     */
    private PowerlinkRootNode getRootNode() {
        if (sourcePart instanceof IndustrialNetworkView) {
            IndustrialNetworkView netView = (IndustrialNetworkView) sourcePart;

            return netView.getNodeList();
        }
        return null;
    }

    /**
     * Handles the Async MTU assignment.
     *
     * @param value New value of AsyncMtu
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleAsyncMtu(Object value) {
        String asyncMtu = StringUtils.EMPTY;
        if (value instanceof String) {
            asyncMtu = (String) value;
            if (asyncMtu.isEmpty()) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        ERROR_ASYNC_MTU_CANNOT_BE_EMPTY,
                        nodeObj.getNetworkId());
                return StringUtils.EMPTY;

            }
            try {
                int asyncMtuValue = Integer.decode((String) value);
                if ((asyncMtuValue < MINIMUM_ASYC_MTU_VALUE)) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR, INVALID_RANGE_ASYNC_MTU,
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;

                }
                if ((asyncMtuValue > MAXIMUM_ASYC_MTU_VALUE)) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR, INVALID_RANGE_ASYNC_MTU,
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;

                }
                // validate the value with openCONFIGURATOR library.
                PowerlinkSubobject asyncMtuSubobj = nodeObj
                        .getObjectDictionary()
                        .getSubObject(INetworkProperties.ASYNC_MTU_OBJECT_ID,
                                INetworkProperties.ASYNC_MTU_SUBOBJECT_ID);
                if (asyncMtuSubobj == null) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR,
                            AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND,
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;
                }

                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateSubobjectActualValue(nodeObj.getNetworkId(),
                                nodeObj.getCnNodeIdValue(),
                                INetworkProperties.ASYNC_MTU_OBJECT_ID,
                                INetworkProperties.ASYNC_MTU_SUBOBJECT_ID,
                                String.valueOf(asyncMtuValue), false);
                if (!validateResult.IsSuccessful()) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR, OpenConfiguratorLibraryUtils
                                    .getErrorMessage(validateResult),
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        ERROR_INVALID_VALUE_ASYNC_MTU, nodeObj.getNetworkId());
                return StringUtils.EMPTY;

            }
        } else {
            System.err.println("ERROR invalid value type");
        }
        return asyncMtu;
    }

    /**
     * Handles the AsyncSlot timeout assignment.
     *
     * @param value New value of AsyncSlot timeout.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleAsyncSlotTimeout(Object value) {
        String asyncTimeOut = StringUtils.EMPTY;
        if (value instanceof String) {
            asyncTimeOut = (String) value;
            if (asyncTimeOut.isEmpty()) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        ERROR_ASYNC_SLOT_TIMEOUT_CANNOT_BE_EMPTY,
                        nodeObj.getNetworkId());
                return StringUtils.EMPTY;
            }
            try {
                long resultVal = Long.decode((String) value);
                if ((resultVal < MINIMUM_ASYNC_SLOT_TIMEOUT)) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR,
                            INVALID_RANGE_ASYNC_SLOT_TIMEOUT,
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;
                }

                // validate the value with openCONFIGURATOR library.
                PowerlinkSubobject asyncslotTimeoutSubobj = nodeObj
                        .getObjectDictionary().getSubObject(
                                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_OBJECT_ID,
                                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_SUBOBJECT_ID);
                if (asyncslotTimeoutSubobj == null) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR,
                            AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND,
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;

                }
                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateSubobjectActualValue(nodeObj.getNetworkId(),
                                nodeObj.getCnNodeIdValue(),
                                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_OBJECT_ID,
                                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_SUBOBJECT_ID,
                                String.valueOf(resultVal), false);

                if (!validateResult.IsSuccessful()) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR, OpenConfiguratorLibraryUtils
                                    .getErrorMessage(validateResult),
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        ERROR_INVALID_VALUE_ASYNCT_SLOT_TIMEOUT,
                        nodeObj.getNetworkId());
                return StringUtils.EMPTY;

            }
        }

        return asyncTimeOut;
    }

    /**
     * Handles the cycle time modifications.
     *
     * @param value New value for Cycle time.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleCycleTime(Object value) {
        String cycleTime = StringUtils.EMPTY;
        if (value instanceof String) {
            cycleTime = (String) value;
            if (cycleTime.isEmpty()) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        ERROR_CYCLE_TIME_CANNOT_BE_EMPTY,
                        nodeObj.getNetworkId());
                return StringUtils.EMPTY;

            }
            try {
                // validate the value with openCONFIGURATOR library.
                PowerlinkObject cycleTimeObj = nodeObj.getObjectDictionary()
                        .getObject(INetworkProperties.CYCLE_TIME_OBJECT_ID);
                if (cycleTimeObj == null) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR,
                            AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND,
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;

                }
                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateObjectActualValue(nodeObj.getNetworkId(),
                                nodeObj.getCnNodeIdValue(),
                                INetworkProperties.CYCLE_TIME_OBJECT_ID,
                                (String) value, false);
                if (!validateResult.IsSuccessful()) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR, OpenConfiguratorLibraryUtils
                                    .getErrorMessage(validateResult),
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        ERROR_INVALID_VALUE_CYCLE_TIME, nodeObj.getNetworkId());
                return StringUtils.EMPTY;

            }
        } else {
            System.err.println("ERROR invalid value type");
        }
        return cycleTime;
    }

    private void handleGeneralInfoForCn(Node nodeObj) {
        txt_no_PResTimeOut.setVisible(true);
        nodeTypeCombo.setVisible(true);
        lblNodeType.setVisible(true);
        lblPResTimeout.setVisible(true);
        nodeId.setEnabled(true);
        nodeId.setText(String.valueOf(nodeObj.getCnNodeIdValue()));

        long presTimeoutInMs = nodeObj.getPresTimeoutvalue() / 1000;

        txt_no_PResTimeOut.setText(String.valueOf(presTimeoutInMs));
        if (nodeObj.getPlkOperationMode() == PlkOperationMode.CHAINED) {
            nodeTypeCombo.select(1);

        } else {
            nodeTypeCombo.select(0);
        }

        lblPResTimeout.setText("PRes Timeout (" + "\u00B5" + "s):");
        lblPResTimeout.setForeground(
                formToolkit.getColors().getColor(IFormColors.TITLE));

        lblNodeType.setText("Node Type:");
        lblNodeType.setForeground(
                formToolkit.getColors().getColor(IFormColors.TITLE));

        lblLossOfSoc.setVisible(false);
        lossOfSocTolText.setVisible(false);
        lblAsymcMtu.setVisible(false);
        asyncMtuText.setVisible(false);
        lblAsyncTimeOut.setVisible(false);
        asyncTimeOutTxt.setVisible(false);
        lblPrescaler.setVisible(false);
        preScalerText.setVisible(false);

    }

    private void handleGeneralInfoForMn(Node nodeObj) {
        txt_no_PResTimeOut.setVisible(true);
        nodeTypeCombo.setVisible(true);
        lblNodeType.setVisible(true);
        lblPResTimeout.setVisible(true);
        nodeId.setEnabled(false);
        nodeId.setText(String.valueOf(IPowerlinkConstants.MN_DEFAULT_NODE_ID));
        if (!nodeObj.getCycleTime().isEmpty()) {
            Long cycleTime = Long.decode(nodeObj.getCycleTime());
            txt_no_PResTimeOut.setText(String.valueOf(cycleTime));
        } else {
            txt_no_PResTimeOut.setText(StringUtils.EMPTY);
        }
        if (nodeObj.getNodeModel() instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeObj
                    .getNodeModel();
            TMN mn = net.getNodeCollection().getMN();
            if (mn.isTransmitsPRes()) {

                nodeTypeCombo.select(0);
            } else {
                nodeTypeCombo.select(1);
            }
        }

        lblPResTimeout.setText("Cycle Time (" + "\u00B5" + "s):");
        lblNodeType.setText("Transmit PRes:");
        lblPResTimeout.setForeground(
                formToolkit.getColors().getColor(IFormColors.TITLE));
        lblNodeType.setForeground(
                formToolkit.getColors().getColor(IFormColors.TITLE));

        productNameLabel.setText("Product Name:");
        productNameText.setText(nodeObj.getProductName());
        productIdLabel.setText("Product ID:");
        productIdText.setText(nodeObj.getProductCodeValue());
        vendorNamelabel.setText("Vendor Name:");
        vendorNameText.setText(nodeObj.getVendorName());
        vendorIdLabel.setText("Vendor ID:");
        vendorIdtext.setText(nodeObj.getVendorIdValue());

        lblLossOfSoc.setVisible(true);
        lossOfSocTolText.setVisible(true);
        lblAsymcMtu.setVisible(true);
        asyncMtuText.setVisible(true);
        lblAsyncTimeOut.setVisible(true);
        asyncTimeOutTxt.setVisible(true);
        lblPrescaler.setVisible(true);
        preScalerText.setVisible(true);

        Long val = Long.valueOf(nodeObj.getLossOfSocTolerance());
        long valInUs = val / 1000;

        lossOfSocTolText.setText(String.valueOf(valInUs));
        lossOfSocTolText.setEditable(true);
        if (nodeObj.getAsyncMtu().isEmpty()) {
            asyncMtuText.setText(StringUtils.EMPTY);
        } else {
            Long asyncmtu = Long.decode(nodeObj.getAsyncMtu());
            asyncMtuText.setText(String.valueOf(asyncmtu));
        }
        asyncMtuText.setEditable(true);
        if (nodeObj.getAsyncSlotTimeout().isEmpty()) {
            asyncTimeOutTxt.setText(StringUtils.EMPTY);
        } else {
            Long asyncTimeOut = Long.decode(nodeObj.getAsyncSlotTimeout());
            asyncTimeOutTxt.setText(String.valueOf(asyncTimeOut));
        }
        asyncTimeOutTxt.setEditable(true);
        if (nodeObj.getPrescaler().isEmpty()) {
            preScalerText.setText(StringUtils.EMPTY);
        } else {
            Long prescaler = Long.decode(nodeObj.getPrescaler());
            preScalerText.setText(String.valueOf(prescaler));
        }
        preScalerText.setEditable(true);

    }

    private void handleGeneralInfoForModule(Module moduleObj) {

        Node nodeObj = moduleObj.getNode();
        txt_no_PResTimeOut.setVisible(true);
        nodeTypeCombo.setVisible(true);
        lblNodeType.setVisible(true);
        lblPResTimeout.setVisible(true);
        nodeId.setEnabled(true);
        nodeId.setText(String.valueOf(nodeObj.getCnNodeIdValue()));

        long presTimeoutInMs = nodeObj.getPresTimeoutvalue() / 1000;

        txt_no_PResTimeOut.setText(String.valueOf(presTimeoutInMs));
        if (nodeObj.getPlkOperationMode() == PlkOperationMode.CHAINED) {
            nodeTypeCombo.select(1);

        } else {
            nodeTypeCombo.select(0);
        }

        lblPResTimeout.setText("PRes Timeout (" + "\u00B5" + "s):");
        lblNodeType.setText("Node Type:");

        lblLossOfSoc.setVisible(false);
        lossOfSocTolText.setVisible(false);
        lblAsymcMtu.setVisible(false);
        asyncMtuText.setVisible(false);
        lblAsyncTimeOut.setVisible(false);
        asyncTimeOutTxt.setVisible(false);
        lblPrescaler.setVisible(false);
        preScalerText.setVisible(false);

        productNameLabel.setText("Product Name:");
        productNameText.setText(nodeObj.getProductName());
        productIdLabel.setText("Product ID:");
        productIdText.setText(nodeObj.getProductCodeValue());
        vendorNamelabel.setText("Vendor Name:");
        vendorNameText.setText(nodeObj.getVendorName());
        vendorIdLabel.setText("Vendor ID:");
        vendorIdtext.setText(nodeObj.getVendorIdValue());

    }

    private void handleGeneralInfoForRmn(Node nodeObj2) {
        nodeId.setEnabled(true);
        txt_no_PResTimeOut.setVisible(false);
        nodeTypeCombo.setVisible(false);
        lblNodeType.setVisible(false);
        lblPResTimeout.setVisible(false);

        nodeId.setText(String.valueOf(nodeObj2.getCnNodeIdValue()));

        lblLossOfSoc.setVisible(false);
        lossOfSocTolText.setVisible(false);
        lblAsymcMtu.setVisible(false);
        asyncMtuText.setVisible(false);
        lblAsyncTimeOut.setVisible(false);
        asyncTimeOutTxt.setVisible(false);
        lblPrescaler.setVisible(false);
        preScalerText.setVisible(false);

        productNameLabel.setText("Product Name:");
        productNameText.setText(nodeObj2.getProductName());
        productIdLabel.setText("Product ID:");
        productIdText.setText(nodeObj2.getProductCodeValue());
        vendorNamelabel.setText("Vendor Name:");
        vendorNameText.setText(nodeObj2.getVendorName());
        vendorIdLabel.setText("Vendor ID:");
        vendorIdtext.setText(nodeObj2.getVendorIdValue());

    }

    /**
     * Handle node type changes.
     *
     * @param value The new node type.
     *
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleNodeTypeChange(Object value) {
        String nodeType = StringUtils.EMPTY;
        if (value instanceof Integer) {
            int val = ((Integer) value).intValue();
            nodeType = String.valueOf(val);
            if (val == 1) {
                // Checks the value of PresChaining from the XDD
                // model of MN and CN.
                Node mnNode = nodeObj.getPowerlinkRootNode().getMN();
                boolean mnPresChaining = mnNode.getNetworkManagement()
                        .getMnFeaturesOfNode().isDLLMNPResChaining();
                if (!mnPresChaining) {

                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR,
                            MessageFormat.format(MNPRES_CHAINING_ERROR_MESSAGE,
                                    mnNode.getNodeIDWithName()),
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;
                }

                boolean cnPresChaining = nodeObj.getNetworkManagement()
                        .getCnFeaturesOfNode().isDLLCNPResChaining();
                if (!cnPresChaining) {
                    // do not allow

                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR,
                            MessageFormat.format(CNPRES_CHAINING_ERROR_MESSAGE,
                                    nodeObj.getNodeIDWithName()),
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;

                }

                List<Node> rmnNodes = nodeObj.getPowerlinkRootNode()
                        .getRmnNodeList();
                if (rmnNodes.size() > 0) {

                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR, CHAINED_STATION_ERROR_MESSAGE,
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;

                }
            } else if (val == 2) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        MULTIPLEXING_OPERATION_NOT_SUPPORTED_ERROR,
                        nodeObj.getNetworkId());
                return StringUtils.EMPTY;

            }
        } else {
            System.err.println("Invalid value type");
        }

        if (nodeType.equalsIgnoreCase("0")) {
            nodeType = "Normal";
        } else {
            nodeType = "Chained";
        }

        return nodeType;
    }

    private void handlePdoTableResize(PdoType pdoType,
            boolean advancedViewChecked) {
        switch (pdoType) {
            case TPDO: {
                if (!advancedViewChecked) {
                    resizeTable(tpdoTableViewer, new int[] { 4, 5 });
                } else {
                    resizeTable(tpdoTableViewer, new int[] { 5 });
                }
                break;
            }
            case RPDO: {
                if (!advancedViewChecked) {
                    resizeTable(rpdoTableViewer, new int[] { 4, 5 });
                } else {
                    resizeTable(rpdoTableViewer, new int[] { 5 });
                }
                break;
            }
            default:
                break;
        }
    }

    /**
     * Handles the Pre-Scaler value changes.
     *
     * @param value New value for Pre-Scaler.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handlePreScalerValue(Object value) {
        String preScaler = StringUtils.EMPTY;
        if (value instanceof String) {
            preScaler = (String) value;
            if (preScaler.isEmpty()) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        ERROR_PRE_SCALER_CANNOT_BE_EMPTY,
                        nodeObj.getNetworkId());
                return StringUtils.EMPTY;

            }
            try {
                int preScalarVal = Integer.decode((String) value);

                if ((preScalarVal < MINIMUM_PRESCALER_VALUE)) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR, INVALID_RANGE_PRE_SCALER,
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;

                }
                if ((preScalarVal > MAXIMUM_PRESCALER_VALUE)) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR, INVALID_RANGE_PRE_SCALER,
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;

                }
                // validate the value with openCONFIGURATOR library.
                PowerlinkSubobject preScalerSubObj = nodeObj
                        .getObjectDictionary()
                        .getSubObject(INetworkProperties.PRESCALER_OBJECT_ID,
                                INetworkProperties.PRESCALER_SUBOBJECT_ID);
                if (preScalerSubObj == null) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR,
                            AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND,
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;

                }
                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateSubobjectActualValue(nodeObj.getNetworkId(),
                                nodeObj.getCnNodeIdValue(),
                                INetworkProperties.PRESCALER_OBJECT_ID,
                                INetworkProperties.PRESCALER_SUBOBJECT_ID,
                                String.valueOf(preScalarVal), false);
                if (!validateResult.IsSuccessful()) {
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR, OpenConfiguratorLibraryUtils
                                    .getErrorMessage(validateResult),
                            nodeObj.getNetworkId());
                    return StringUtils.EMPTY;
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();

                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        ERROR_INVALID_VALUE_PRE_SCALER, nodeObj.getNetworkId());
                return StringUtils.EMPTY;

            }
        }
        return preScaler;
    }

    /**
     * Handle PRes timeout changes in the properties.
     *
     * @param value New value for the PRes timeout.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handlePresTimeout(Object value) {
        String presTimeOut = StringUtils.EMPTY;
        if (value instanceof String) {
            presTimeOut = (String) value;
            if (presTimeOut.isEmpty()) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        ERROR_PRES_TIMEOUT_CANNOT_BE_EMPTY,
                        nodeObj.getNetworkId());
                return StringUtils.EMPTY;

            }
            // validate the value with openCONFIGURATOR library.
            long presTimeoutInNs = Long.decode((String) value).longValue()
                    * 1000;
            Result validateResult = OpenConfiguratorLibraryUtils
                    .validateSubobjectActualValue(nodeObj.getNetworkId(),
                            IPowerlinkConstants.MN_DEFAULT_NODE_ID,
                            INetworkProperties.POLL_RESPONSE_TIMEOUT_OBJECT_ID,
                            nodeObj.getCnNodeIdValue(),
                            String.valueOf(presTimeoutInNs), false);
            if (!validateResult.IsSuccessful()) {
                PluginErrorDialogUtils.showMessageWindow(
                        MessageDialog.ERROR, OpenConfiguratorLibraryUtils
                                .getErrorMessage(validateResult),
                        nodeObj.getNetworkId());
                return StringUtils.EMPTY;
            }
        } else {
            System.err
                    .println("handlePresTimeout: Invalid value type:" + value);
        }

        return presTimeOut;
    }

    protected String handleSetNodeName(Object name) {
        String nodeName = StringUtils.EMPTY;
        if (name instanceof String) {
            nodeName = ((String) name);
            if (nodeName.isEmpty()) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        "Node name cannot be empty.", nodeObj.getNetworkId());
                return StringUtils.EMPTY;
            }

            // Space as first character is not allowed. ppc:tNonEmptyString
            if (nodeName.charAt(0) == ' ') {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        "Invalid name", nodeObj.getNetworkId());
                return StringUtils.EMPTY;
            }

            Result res = OpenConfiguratorCore.GetInstance().SetNodeName(
                    nodeObj.getNetworkId(), nodeObj.getCnNodeIdValue(),
                    nodeName);
            if (!res.IsSuccessful()) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        OpenConfiguratorLibraryUtils.getErrorMessage(res),
                        nodeObj.getNetworkId());
                return StringUtils.EMPTY;
            }
        } else {
            System.err.println("handleSetNodeName: Invalid value type:" + name);
        }
        return nodeName;
    }

    private void handleSummaryTableResize(PdoType pdoType) {
        switch (pdoType) {
            case TPDO: {
                Table table = tpdoSummaryTableViewer.getTable();
                int totalColumnCount = table.getColumnCount();
                if (!showAdvancedview.isChecked()) {
                    totalColumnCount -= 1;
                }

                int width = table.getClientArea().width;

                int finalWidth = width / totalColumnCount;
                for (TableColumn tblClmn : table.getColumns()) {
                    if (!showAdvancedview.isChecked()) {
                        if (tblClmn != tpdoSummaryClmnMappingVersion) {
                            tblClmn.setWidth(finalWidth);
                        }
                    } else {
                        tblClmn.setWidth(finalWidth);
                    }
                }
                break;
            }
            case RPDO: {
                Table table = rpdoSummaryTableViewer.getTable();
                int totalColumnCount = table.getColumnCount();
                if (!showAdvancedview.isChecked()) {
                    totalColumnCount -= 1;
                }

                int width = table.getClientArea().width;
                int finalWidth = width / totalColumnCount;
                for (TableColumn tblClmn : table.getColumns()) {
                    if (!showAdvancedview.isChecked()) {
                        if (tblClmn != rpdoSummaryClmnMappingVersion) {
                            tblClmn.setWidth(finalWidth);
                        }
                    } else {
                        tblClmn.setWidth(finalWidth);
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    /**
     * Initialize the menu.
     */
    private void initializeMenu() {
        // Initialize menu for mapping view.
    }

    /**
     * Initialize the toolbar.
     */
    private void initializeToolBar() {
        // Initialize toolbar for mapping view.
    }

    public void refreshNetworkView() {
        IViewPart viewPart;
        try {
            viewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage().showView(IndustrialNetworkView.ID);
            if (viewPart instanceof IndustrialNetworkView) {
                IndustrialNetworkView industrialView = (IndustrialNetworkView) viewPart;
                industrialView.getViewer().refresh();
                industrialView.handleRefresh();

            }
        } catch (PartInitException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    @Override
    public void setFocus() {
        // Set the focus
    }

    /**
     * Enables or Disables the PDO GUI Controls
     *
     * @param pdoType
     * @param enabled
     */
    private void setPdoGuiControlsEnabled(PdoType pdoType, boolean enabled) {
        if (pdoType == PdoType.RPDO) {
            btnRpdoChannelMapAvailableObjects.setEnabled(enabled);
            btnRpdoChannelClearSelectedRows.setEnabled(enabled);
            // rpdoEnabledMappingEntriesSpinner.setEnabled(enabled);
            receiveFromNodecomboviewer.getCombo().setEnabled(enabled);
        } else if (pdoType == PdoType.TPDO) {
            btnTpdoChannelMapAvailableObjects.setEnabled(enabled);
            btnTpdoChannelClearSelectedRows.setEnabled(enabled);
            // tpdoEnabledMappingEntriesSpinner.setEnabled(enabled);
        } else {
            System.err.println("Invalid PDO type" + pdoType);
        }
    }

    /**
     * sets the NodeId for PDO channel
     *
     * @param pdoChannel
     */
    private void setPdoNodeId(PdoChannel pdoChannel) {
        PowerlinkSubobject nodeIdSubObject = pdoChannel.getCommunicationParam()
                .getSubObject((short) 01);
        String actualValue = nodeIdSubObject.getActualDefaultValue();
        if (actualValue.isEmpty()) {
            actualValue = "0";
        }

        short targetNodeId = Short.decode(actualValue);
        Node targetNode = null;
        for (Node node : targetNodeIdList) {

            if (node.getCnNodeIdValue() == targetNodeId) {
                targetNode = node;
            }
        }

        if (pdoChannel.getPdoType() == PdoType.TPDO) {
            sndtoNodecomboviewer.setInput(targetNodeIdList);
            if (targetNode != null) {
                ISelection selection = new StructuredSelection(targetNode);

                sndtoNodecomboviewer.removeSelectionChangedListener(
                        tpdoNodeComboSelectionChangeListener);
                sndtoNodecomboviewer.setSelection(selection, true);
                sndtoNodecomboviewer.addSelectionChangedListener(
                        tpdoNodeComboSelectionChangeListener);
            }
        } else if (pdoChannel.getPdoType() == PdoType.RPDO) {
            receiveFromNodecomboviewer.setInput(targetNodeIdList);
            if (targetNode != null) {
                ISelection selection = new StructuredSelection(targetNode);

                receiveFromNodecomboviewer.removeSelectionChangedListener(
                        rpdoNodeComboSelectionChangeListener);
                receiveFromNodecomboviewer.setSelection(selection, true);
                receiveFromNodecomboviewer.addSelectionChangedListener(
                        rpdoNodeComboSelectionChangeListener);
            }
        } else {
            System.err.println("Invalid PDO type" + pdoChannel.getPdoType());
        }
    }

    /**
     * updates the size of channel
     *
     * @param pdoChannel
     */
    private void updateChannelSize(PdoChannel pdoChannel) {

        long size = OpenConfiguratorLibraryUtils.getChannelSize(pdoChannel);

        if (pdoChannel.getPdoType() == PdoType.TPDO) {
            tpdoChannelSize.setText(String.valueOf(size));
        } else if (pdoChannel.getPdoType() == PdoType.RPDO) {
            rpdoChannelSize.setText(String.valueOf(size));
        } else {
            System.err.println("Invalid pdo type" + pdoChannel.getPdoType());
        }
    }

    /**
     * updates the number of Objects or sub-objects Mapped.
     *
     * @param pdoChannel
     */
    private void updateEnabledMappingEntries(PdoChannel pdoChannel) {
        if (pdoChannel.getPdoType() == PdoType.TPDO) {
            tpdoEnabledMappingEntriesText.setEnabled(true);
            tpdoEnabledMappingEntriesText.removeModifyListener(
                    tpdoEnabledMappingEntriesTextModifyListener);
            tpdoEnabledMappingEntriesText
                    .removeVerifyListener(enabledEntriesVerifyListener);
            tpdoEnabledMappingEntriesText.setText(
                    String.valueOf(pdoChannel.getEnabledNumberofEntries()));

            tpdoEnabledMappingEntriesText
                    .addVerifyListener(enabledEntriesVerifyListener);
            tpdoEnabledMappingEntriesText.addModifyListener(
                    tpdoEnabledMappingEntriesTextModifyListener);
            tpdoEnabledEntriesCount = getEnabledEntriesCount(PdoType.TPDO);

        } else if (pdoChannel.getPdoType() == PdoType.RPDO) {
            // RPDO
            rpdoEnabledMappingEntriesText.setEnabled(true);
            rpdoEnabledMappingEntriesText.removeModifyListener(
                    rpdoEnabledMappingEntriesTextModifyListener);
            rpdoEnabledMappingEntriesText
                    .removeVerifyListener(enabledEntriesVerifyListener);
            rpdoEnabledMappingEntriesText.setText(
                    String.valueOf(pdoChannel.getEnabledNumberofEntries()));

            rpdoEnabledMappingEntriesText
                    .addVerifyListener(enabledEntriesVerifyListener);
            rpdoEnabledMappingEntriesText.addModifyListener(
                    rpdoEnabledMappingEntriesTextModifyListener);
            rpdoEnabledEntriesCount = getEnabledEntriesCount(PdoType.RPDO);
        } else {
            System.err.println("Invalid pdo type" + pdoChannel.getPdoType());
        }
        updatePdoTable(pdoChannel);
    }

    /**
     * updates the PDO table based on the PDO channel parameters
     *
     * @param pdoChannel
     */
    private void updatePdoTable(PdoChannel pdoChannel) {
        if (pdoChannel.getPdoType() == PdoType.TPDO) {
            tpdoTableViewer.setInput(pdoChannel.getMappingParam());
            resizeTable(tpdoTableViewer, new int[] { 4, 5 });
        } else if (pdoChannel.getPdoType() == PdoType.RPDO) {
            rpdoTableViewer.setInput(pdoChannel.getMappingParam());
            resizeTable(rpdoTableViewer, new int[] { 4, 5 });
        } else {
            System.err.println("Invalid pdo type" + pdoChannel.getPdoType());
        }
    }

    /**
     * updates the NodeID of Mapping Channels
     */
    private void updateTargetNodeIdList() {

        PowerlinkRootNode rootNode = getRootNode();

        targetNodeIdList.clear();

        if (rootNode != null) {
            targetNodeIdList.add(rootNode.getMN());
            targetNodeIdList.add(emptyNode);
            List<Node> tempCnNodeList = rootNode.getCnNodeList();
            for (Node tempNode : tempCnNodeList) {
                if (nodeObj == tempNode) {
                    if (tempNode.isPDOSelfReceipt()) {
                        targetNodeIdList.add(selfReceiptNode);
                    }
                } else {
                    targetNodeIdList.add(tempNode);
                }
            }

            List<Node> tempRmnNodeList = rootNode.getRmnNodeList();
            for (Node tempNode : tempRmnNodeList) {
                if (nodeObj == tempNode) {
                    if (tempNode.isPDOSelfReceipt()) {
                        targetNodeIdList.add(selfReceiptNode);
                    }
                } else {
                    targetNodeIdList.add(tempNode);
                }
            }
        }
    }
}
