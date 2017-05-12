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

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
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
import org.epsg.openconfigurator.event.INodePropertyChangeListener;
import org.epsg.openconfigurator.event.NodePropertyChangeEvent;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.IPowerlinkProjectSupport;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.Path;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.model.ProcessImage;
import org.epsg.openconfigurator.resources.IPluginImages;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.views.mapping.MappingView;
import org.epsg.openconfigurator.wizards.CopyNodeWizard;
import org.epsg.openconfigurator.wizards.NewFirmwareWizard;
import org.epsg.openconfigurator.wizards.NewModuleWizard;
import org.epsg.openconfigurator.wizards.NewNodeWizard;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TPath;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration.PathSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xap.ApplicationProcess;
import org.epsg.openconfigurator.xmlbinding.xdd.ModuleType;
import org.epsg.openconfigurator.xmlbinding.xdd.TInterfaceList.Interface;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

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
    // It is not required to be a static inner class.
    private class EmptyNetworkView {
        @Override
        public String toString() {
            return "Network elements not available.";
        }
    }

    /**
     * Node station type and ID based comparator.
     *
     * An exception the MN node id will remain at the top.
     *
     * @author Ramakrishnan P
     *
     */
    // It is not required to be a static inner class.
    private class NodeBasedSorter extends ViewerComparator {

        @Override
        public int compare(Viewer viewer, Object e1, Object e2) {
            int compare = 0;
            if ((e1 instanceof Node) && (e2 instanceof Node)) {

                Node nodeFirst = (Node) e1;
                Node nodeSecond = (Node) e2;

                if (nodeSecond
                        .getCnNodeId() == IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
                    return 255;
                }
                compare = nodeFirst.getPlkOperationMode()
                        .compareTo(nodeSecond.getPlkOperationMode());
                if (compare == 0) {
                    return nodeFirst.getCnNodeId() - nodeSecond.getCnNodeId();
                }

            } else if ((e1 instanceof Module) && (e2 instanceof Module)) {
                Module moduleFirst = (Module) e1;
                Module moduleSecond = (Module) e2;
                return moduleFirst.getPosition() - moduleSecond.getPosition();
            } else {
                compare = e1.toString().compareTo(e2.toString());
            }
            return compare;
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
    // It is not required to be a static inner class.
    private class NodeIdSorter extends ViewerComparator {

        @Override
        public int compare(Viewer viewer, Object e1, Object e2) {
            if ((e1 instanceof Node) && (e2 instanceof Node)) {

                Node nodeFirst = (Node) e1;
                Node nodeSecond = (Node) e2;
                if (nodeSecond
                        .getCnNodeId() == IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
                    return 255;
                }
                return nodeFirst.getCnNodeId() - nodeSecond.getCnNodeId();

            }

            if ((e1 instanceof Module) && (e2 instanceof Module)) {
                Module moduleFirst = (Module) e1;
                Module moduleSecond = (Module) e2;
                return moduleFirst.getPosition() - moduleSecond.getPosition();
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

            if (parent instanceof Node) {
                Node node = (Node) parent;
                Object nodeObjectModel = node.getNodeModel();

                if (nodeObjectModel instanceof TNetworkConfiguration) {
                    return rootNode.getRmnNodeList().toArray();
                } else if (nodeObjectModel instanceof TCN) {
                    if (node.isModularheadNode()) {
                        return node.getHeadNodeInterface().toArray();
                    }
                } else if (nodeObjectModel instanceof TRMN) {
                    // TODO: Implement for modular RMN.
                }
            }

            if (parent instanceof HeadNodeInterface) {
                HeadNodeInterface headNodeInterface = (HeadNodeInterface) parent;
                return headNodeInterface.getModuleCollection().values()
                        .toArray();
            }

            System.err.println("Returning empty object. Parent:" + parent);
            return new Object[0];
        }

        @Override
        public Object[] getElements(Object parent) {
            if (parent == null) {
                return new Object[] { new EmptyNetworkView() };
            }
            if (parent instanceof PowerlinkRootNode) {
                PowerlinkRootNode powerlinkRoot = (PowerlinkRootNode) parent;

                LinkedHashSet<Node> nodeCollection = new LinkedHashSet<>();

                List<Node> nodeList = powerlinkRoot.getNodeLists(parent);

                nodeCollection.addAll(nodeList);

                Object[] obj = nodeCollection.toArray();

                if (nodeCollection.size() == 0) {
                    return new Object[] { new EmptyNetworkView() };
                }
                return obj;
            }
            return new Object[0];

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
            } else if (child instanceof Module) {
                Module module = (Module) child;
                Object moduleModel = module.getModelOfModule();
                if (moduleModel instanceof InterfaceList.Interface.Module) {
                    return module.getInterfaceOfModule();
                }
            }

            return null;
        }

        @Override
        public boolean hasChildren(Object parent) {

            if (parent instanceof Node) {
                Node node = (Node) parent;
                Object nodeObjectModel = node.getNodeModel();
                Object interfaceModel = node.getInterface();
                if (nodeObjectModel instanceof TNetworkConfiguration) {
                    ArrayList<Node> nodeList = rootNode.getRmnNodeList();
                    return (nodeList.size() > 0 ? true : false);
                } else if (nodeObjectModel instanceof TCN) {
                    return true;
                } else if (nodeObjectModel instanceof TRMN) {
                    // TODO implement for Modular RMN
                    return false;
                } else if (interfaceModel != null) {
                    ArrayList<HeadNodeInterface> interfaceList = rootNode
                            .getInterfaceList();
                    return (interfaceList.size() > 0 ? true : false);

                }
            }

            if (parent instanceof HeadNodeInterface) {
                return true;
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
        Image interfaceIcon;
        Image moduleIcon;

        // It is not required to be a static inner class.
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
            interfaceIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.INTERFACE_ICON)
                    .createImage();
            moduleIcon = org.epsg.openconfigurator.Activator
                    .getImageDescriptor(IPluginImages.MODULE_ICON)
                    .createImage();
        }

        @Override
        public void dispose() {
            mnIcon.dispose();
            cnEnabledIcon.dispose();
            cnDisabledIcon.dispose();
            rmnIcon.dispose();
            interfaceIcon.dispose();
            moduleIcon.dispose();
        }

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
            } else if (element instanceof HeadNodeInterface) {
                HeadNodeInterface interfaceModel = (HeadNodeInterface) element;
                if (!interfaceModel.getNode().isEnabled()) {
                    return Display.getDefault().getSystemColor(SWT.COLOR_GRAY);

                }
            } else if (element instanceof Module) {
                Module moduleObj = (Module) element;

                if (!moduleObj.isEnabled()) {
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
                    }
                    return cnDisabledIcon;
                }
                if (nodeObjectModel instanceof TRMN) {
                    return rmnIcon;
                }

            } else if (obj instanceof EmptyNetworkView) {
                return null;
            }

            if (obj instanceof HeadNodeInterface) {
                HeadNodeInterface interfaceModel = (HeadNodeInterface) obj;
                if (interfaceModel.getNode().isEnabled()) {
                    return interfaceIcon;

                }
                return cnDisabledIcon;
            }

            if (obj instanceof Module) {
                Module module = (Module) obj;
                Object moduleObjectModel = module.getModelOfModule();
                if (moduleObjectModel instanceof InterfaceList.Interface.Module) {
                    InterfaceList.Interface.Module moduleModel = (InterfaceList.Interface.Module) moduleObjectModel;
                    if (moduleModel.isEnabled()) {
                        return moduleIcon;
                    }
                    return cnDisabledIcon;
                }

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
            if (obj instanceof HeadNodeInterface) {
                HeadNodeInterface interfaceList = (HeadNodeInterface) obj;
                if (interfaceList.getUniqueIDRef() instanceof Interface) {
                    Interface intfc = (Interface) interfaceList
                            .getUniqueIDRef();
                    return intfc.getUniqueID();
                }
                return obj.toString();
            }
            if (obj instanceof Module) {
                Module module = (Module) obj;
                Object moduleObjModel = module.getModelOfModule();
                if (moduleObjModel instanceof InterfaceList.Interface.Module) {
                    InterfaceList.Interface.Module mod = (InterfaceList.Interface.Module) moduleObjModel;
                    return mod.getName() + " (" + mod.getAddress() + ")";
                }
                // return moduleObjModel.getName() + " (" + module.getAddress()
                // + ")";
            }
            return obj.toString();
        }
    }

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "org.epsg.openconfigurator.views.IndustrialNetworkView";

    // Add new node message strings.
    public static final String ADD_NEW_NODE_ACTION_MESSAGE = "Add Node...";
    public static final String ADD_NEW_MODULE_ACTION_MESSAGE = "Add Module...";
    public static final String ADD_NEW_NODE_ERROR_MESSAGE = "Internal error occurred. Please try again later";
    public static final String ADD_NEW_NODE_INVALID_SELECTION_MESSAGE = "Invalid selection";
    public static final String ADD_NEW_NODE_TOOL_TIP_TEXT = "Add a node in the network.";
    public static final String ADD_FIRMWARE_TOOL_TIP_TEXT = "Add Firmware...";

    // Enable/disable action message string.
    // public static final String ENABLE_DISABLE_ACTION_MESSAGE =
    // "Enable/Disable";
    public static final String ENABLE_ACTION_MESSAGE = "Enable";
    public static final String DISABLE_ACTION_MESSAGE = "Disable";
    public static final String GENERATE_NODE_XDC_ACTION_MESSAGE = "Export node XDC";

    // Object dictionary action message strings.
    public static final String SHOW_OBJECT_DICTIONARY_ACTION_MESSAGE = "Show Object Dictionary";
    public static final String SHOW_PARAMETER_ACTION_MESSAGE = "Show Parameter View";
    public static final String SHOW_OBJECT_DICTIONARY_ERROR_MESSAGE = "Error openning Object Dictionary";
    public static final String SHOW_PARAMETER_ERROR_MESSAGE = "Error openning Parameter";

    // Process Image action message strings.
    public static final String SHOW_IO_MAP_ACTION_MESSAGE = "Show I/O Mapping";
    public static final String SHOW_PROCESS_IMAGE_ERROR_MESSAGE = "Error openning Process Image";

    // Mapping view action message strings.
    public static final String SHOW_MAPING_VIEW_ACTION_MESSAGE = "Show Mapping View";
    public static final String SHOW_MAPING_VIEW_ERROR_MESSAGE = "Error openning MappingView";

    // Properties actions message strings.
    public static final String PROPERTIES_ACTION_MESSAGE = "Properties";
    public static final String PROPERTIES_ERROR_MESSAGE = "Error opening properties view";

    // Remove node action message string.
    public static final String DELETE_NODE_ACTION_MESSAGE = "Remove";

    // Sort node action message string.
    public static final String SORT_NODE_BY_ID_MESSAGE = "Sort by Id";
    public static final String SORT_NODE_BY_STATION_TYPE_MESSAGE = "Sort by station type";

    // Refresh action message string.
    public static final String REFRESH_ACTION_MESSAGE = "Refresh (F5)";

    /**
     * Verifies the enabling of module based on the module type of connected
     * modules.
     *
     * @param module Instance of Module.
     * @return <code>true</code> module is enabled, <code>false</code> module is
     *         disabled.
     */
    private static boolean canModuleEnabled(Module module) {
        boolean enable = false;
        int enabledModulePosition = module.getPosition();
        int previousModulePosition = 0;
        HeadNodeInterface interfaceObj = module.getInterfaceOfModule();
        List<Integer> positionToBeChecked = new ArrayList<>();
        List<Integer> previousPositionList = new ArrayList<>();
        Set<Integer> positionset = interfaceObj.getModuleCollection().keySet();
        for (Integer pos : positionset) {
            if (pos < enabledModulePosition) {
                previousModulePosition = pos;
                previousPositionList.add(previousModulePosition);
            }

            if (pos > enabledModulePosition) {
                positionToBeChecked.add(pos);
            }
        }
        Module enabledPositionModule = interfaceObj.getModuleCollection()
                .get(enabledModulePosition);
        Collections.sort(previousPositionList, Collections.reverseOrder());
        if (previousModulePosition != 0) {
            for (Integer previousPosition : previousPositionList) {
                System.err.println(
                        "Previous position!@#$ . ..." + previousPosition);
                Module previousPositionModule = interfaceObj
                        .getModuleCollection().get(previousPosition);
                if (previousPositionModule.hasError()) {
                    return false;
                }
                List<ModuleType> previousModuleTypeList = previousPositionModule
                        .getModuleInterface().getModuleTypeList()
                        .getModuleType();
                if (previousPositionModule.isEnabled()) {
                    for (ModuleType moduleType : previousModuleTypeList) {
                        String previousModuleType = moduleType.getType();
                        String enabledPositionModuleType = enabledPositionModule
                                .getModuleInterface().getType();
                        System.err.println("Previous Module Position type ...."
                                + previousModuleType);
                        System.err.println("Enabled Module Position type ...."
                                + enabledPositionModuleType);
                        if (previousModuleType
                                .equals(enabledPositionModuleType)) {
                            System.err.println(
                                    "Previous module equals enabled module.....");
                            enable = true;
                        } else {
                            return false;
                        }
                    }
                }
                if (enable) {
                    break;
                }
            }
        } else {
            String previousModuleType = interfaceObj.getInterfaceType();
            String enabledPositionModuleType = enabledPositionModule
                    .getModuleInterface().getType();
            if (previousModuleType.equals(enabledPositionModuleType)) {
                System.err
                        .println("Previous module equals enabled module.....");
                enable = true;
            } else {
                return false;
            }
        }

        for (Integer nextPosition : positionToBeChecked) {
            System.err.println("Next position @#$#%......." + nextPosition);
            Module nextPositionModule = interfaceObj.getModuleCollection()
                    .get(nextPosition);
            if (nextPositionModule.isEnabled()) {
                String nextPositionModuleType = nextPositionModule
                        .getModuleInterface().getType();
                List<ModuleType> enabledModuleTypeList = enabledPositionModule
                        .getModuleInterface().getModuleTypeList()
                        .getModuleType();
                for (ModuleType moduletype : enabledModuleTypeList) {
                    String enabledModuleType = moduletype.getType();
                    System.err.println(
                            "Enabled module type.." + enabledModuleType);
                    System.err.println("Next position module type.."
                            + nextPositionModuleType);
                    if (enabledModuleType.equals(nextPositionModuleType)) {
                        enable = true;

                    } else {
                        enable = false;
                    }
                }
                break;
            }

        }

        System.err.println("Enable..............................." + enable);
        return enable;
    }

    private static void disableModule(Module module)
            throws JDOMException, IOException {
        int removedModulePosition = module.getPosition();
        int previousModulePosition = 0;
        HeadNodeInterface interfaceObj = module.getInterfaceOfModule();
        List<Integer> positionToBeChecked = new ArrayList<>();
        List<Integer> previousPositionToBeChecked = new ArrayList<>();
        Set<Integer> positionset = interfaceObj.getModuleCollection().keySet();
        for (Integer pos : positionset) {
            if (pos < removedModulePosition) {
                previousModulePosition = pos;
                previousPositionToBeChecked.add(pos);
            }

            if (pos > removedModulePosition) {
                positionToBeChecked.add(pos);
            }
        }
        System.err
                .println("Previous Module Position.." + previousModulePosition);
        Collections.sort(previousPositionToBeChecked,
                Collections.reverseOrder());
        if (previousModulePosition != 0) {
            for (Integer previousPosition : previousPositionToBeChecked) {

                Module previousPositionModule = interfaceObj
                        .getModuleCollection().get(previousPosition);
                // String previousPositionModuleType = previousPositionModule
                // .getModuleType();
                if (previousPositionModule.isEnabled()) {
                    List<ModuleType> previousModuleTypeList = previousPositionModule
                            .getModuleInterface().getModuleTypeList()
                            .getModuleType();

                    for (Integer posit : positionToBeChecked) {
                        System.err.println("next Module Position.." + posit);
                        Module mod = interfaceObj.getModuleCollection()
                                .get(posit);
                        if (mod.isEnabled()) {
                            String nextPositionModuleType = mod
                                    .getModuleInterface().getType();

                            for (ModuleType moduleType : previousModuleTypeList) {
                                String previousModuleType = moduleType
                                        .getType();
                                System.err.println("Previous Module type.."
                                        + previousModuleType);
                                System.err.println("Next Module type.."
                                        + nextPositionModuleType);

                                if (previousModuleType.equalsIgnoreCase(
                                        nextPositionModuleType)) {
                                    return;
                                }
                                mod.setEnabled(false);

                            }
                        }
                    }

                }
            }
        } else {
            String previousModuleType = interfaceObj.getInterfaceType();

            for (Integer posit : positionToBeChecked) {
                System.err.println("next Module Position.." + posit);
                Module mod = interfaceObj.getModuleCollection().get(posit);
                if (mod.isEnabled()) {
                    String nextPositionModuleType = mod.getModuleInterface()
                            .getType();

                    if (previousModuleType
                            .equalsIgnoreCase(nextPositionModuleType)) {
                        return;
                    }
                    mod.setEnabled(false);

                }
            }

        }
    }

    /**
     * Verifies the enabling of module based on the module type of connected
     * modules.
     *
     * @param module Instance of Module.
     * @return <code>true</code> module is enabled, <code>false</code> module is
     *         disabled.
     */
    private static String errorModuleEnabled(Module module) {
        boolean enable = false;
        int enabledModulePosition = module.getPosition();
        int previousModulePosition = 0;
        HeadNodeInterface interfaceObj = module.getInterfaceOfModule();
        List<Integer> positionToBeChecked = new ArrayList<>();
        List<Integer> previousPositionList = new ArrayList<>();
        Set<Integer> positionset = interfaceObj.getModuleCollection().keySet();
        for (Integer pos : positionset) {
            if (pos < enabledModulePosition) {
                previousModulePosition = pos;
                previousPositionList.add(previousModulePosition);
            }

            if (pos > enabledModulePosition) {
                positionToBeChecked.add(pos);
            }
        }
        Module enabledPositionModule = interfaceObj.getModuleCollection()
                .get(enabledModulePosition);
        Collections.sort(previousPositionList, Collections.reverseOrder());
        if (previousModulePosition != 0) {
            for (Integer previousPosition : previousPositionList) {
                Module previousPositionModule = interfaceObj
                        .getModuleCollection().get(previousPosition);
                if (previousPositionModule.hasError()) {
                    return "Module " + module.getModuleName()
                            + " cannot be disabled because the Module "
                            + previousPositionModule.getModuleName()
                            + " has invalid configuration file.";
                }
                List<ModuleType> previousModuleTypeList = previousPositionModule
                        .getModuleInterface().getModuleTypeList()
                        .getModuleType();
                if (previousPositionModule.isEnabled()) {
                    for (ModuleType moduleType : previousModuleTypeList) {
                        String previousModuleType = moduleType.getType();
                        String enabledPositionModuleType = enabledPositionModule
                                .getModuleInterface().getType();
                        if (previousModuleType
                                .equals(enabledPositionModuleType)) {
                            System.err.println(
                                    "Previous module equals enabled module.....");
                            enable = true;
                        } else {
                            return "The Module '"
                                    + previousPositionModule.getModuleName()
                                    + "' with module type " + previousModuleType
                                    + " does not match the module '"
                                    + enabledPositionModule.getModuleName()
                                    + "' with module type "
                                    + enabledPositionModuleType + ".";
                        }
                    }
                }
                if (enable) {
                    break;
                }
            }
        } else {
            String previousModuleType = interfaceObj.getInterfaceType();
            String enabledPositionModuleType = enabledPositionModule
                    .getModuleInterface().getType();
            if (previousModuleType.equals(enabledPositionModuleType)) {
                enable = true;
            } else {
                return "The Interface '" + interfaceObj.getInterfaceUId()
                        + "' with type " + previousModuleType
                        + " does not match the module '"
                        + enabledPositionModule.getModuleName()
                        + "' with module type " + enabledPositionModuleType
                        + ".";
            }
        }

        for (Integer nextPosition : positionToBeChecked) {
            Module nextPositionModule = interfaceObj.getModuleCollection()
                    .get(nextPosition);
            if (nextPositionModule.isEnabled()) {
                String nextPositionModuleType = nextPositionModule
                        .getModuleInterface().getType();
                List<ModuleType> enabledModuleTypeList = enabledPositionModule
                        .getModuleInterface().getModuleTypeList()
                        .getModuleType();
                for (ModuleType moduletype : enabledModuleTypeList) {
                    String enabledModuleType = moduletype.getType();
                    if (enabledModuleType.equals(nextPositionModuleType)) {
                        enable = true;

                    } else {
                        return "The Module '"
                                + enabledPositionModule.getModuleName()
                                + "' with module type " + enabledModuleType
                                + " does not match the module '"
                                + nextPositionModule.getModuleName()
                                + "' with module type " + nextPositionModuleType
                                + ".";
                    }
                }
                break;
            }

        }

        System.err.println("Enable..............................." + enable);
        return StringUtils.EMPTY;
    }

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
     * Action to copy the node of POWERLINK project
     */
    private Action copyNode;

    /**
     * IO mapping View
     */
    private Action showIoMapView;

    private Job exportNodeXDC;

    private Action addNewModule;

    /**
     * Show object dictionary.
     */
    private Action showObjectDictionary;

    /**
     * Show parameter view
     */
    private Action showParameter;

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
    private Action enable;

    private Action addFirmwareFile;

    private Action disable;

    private Action generateNodeXDC;

    /**
     * Refresh the Industrial network view action.
     */
    private Action refreshAction;

    /**
     * Action to move the module to previous position
     */
    private Action moveModuleUp;

    /**
     * Action to move the module to next position
     */
    private Action moveModuleDown;

    /**
     * Show PDO mapping action.
     */
    private Action showPdoMapping;

    /**
     * Sort Node action.
     */
    private Action sortNode;

    /**
     * Instance of Xap file
     */
    private ProcessImage processImage;

    private OpenCONFIGURATORProject currentProject;

    /**
     * Call back to handle the selection changed events.
     */
    private ISelectionChangedListener viewerSelectionChangedListener = new ISelectionChangedListener() {

        @Override
        public void selectionChanged(SelectionChangedEvent event) {
            contributeToActionBars();
        }
    };

    private Node nodeToBeCopied = null;

    /**
     * Keyboard bindings.
     */
    private KeyAdapter treeViewerKeyListener = new KeyAdapter() {
        @Override
        public void keyReleased(final KeyEvent e) {
            if (e.keyCode == SWT.DEL) {
                IStructuredSelection selection = (IStructuredSelection) viewer
                        .getSelection();
                try {
                    handleRemoveNode(selection);
                } catch (IOException | JDOMException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else if (e.keyCode == SWT.F5) {
                handleRefresh();
            } else if (((e.stateMask & SWT.CTRL) == SWT.CTRL)
                    && (e.keyCode == 'c')) {
                IStructuredSelection selection = (IStructuredSelection) viewer
                        .getSelection();
                Object selectedObject = selection.getFirstElement();
                if (selectedObject instanceof Node) {
                    Node node = (Node) selectedObject;
                    int nodeId = Integer.valueOf(node.getNodeIdString());
                    if (nodeId <= 240) {
                        nodeToBeCopied = node;
                    } else {
                        nodeToBeCopied = null;
                    }
                } else if (selectedObject instanceof Module) {
                    nodeToBeCopied = null;
                } else if (selectedObject instanceof HeadNodeInterface) {
                    nodeToBeCopied = null;
                }
            } else if (((e.stateMask & SWT.CTRL) == SWT.CTRL)
                    && (e.keyCode == 'v')) {
                if (nodeToBeCopied == null) {
                    return;
                }
                CopyNodeWizard copyNodeWizard = new CopyNodeWizard(rootNode,
                        nodeToBeCopied);
                if (!copyNodeWizard.hasErrors()) {
                    WizardDialog wd = new WizardDialog(
                            Display.getDefault().getActiveShell(),
                            copyNodeWizard);
                    wd.setTitle(copyNodeWizard.getWindowTitle());
                    wd.open();
                } else {
                    showMessage(ADD_NEW_NODE_ERROR_MESSAGE);
                }

                try {
                    nodeToBeCopied.getProject().refreshLocal(
                            IResource.DEPTH_INFINITE,
                            new NullProgressMonitor());
                } catch (CoreException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }

                handleRefresh();
                int nodeId = Integer.valueOf(copyNodeWizard.getNodeId());
                String name = copyNodeWizard.getNodeName();
                int stationTypeChanged = copyNodeWizard
                        .getStationTypeIndex(copyNodeWizard.getStationType());
                try {
                    nodeToBeCopied.copyNode(nodeId, stationTypeChanged, name);

                } catch (JDOMException | InterruptedException exce) {
                    // TODO Auto-generated catch block
                    exce.printStackTrace();
                } catch (IOException exc) {
                    // TODO Auto-generated catch block
                    exc.printStackTrace();
                }

                handleRefresh();

            }

        }

    };

    private LinkWithEditorPartListener linkWithEditorPartListener = new LinkWithEditorPartListener(
            this);

    INodePropertyChangeListener nodePropertyChangeListener = new INodePropertyChangeListener() {
        @Override
        public void nodePropertyChanged(NodePropertyChangeEvent event) {
            Display.getDefault().asyncExec(new Runnable() {

                @Override
                public void run() {
                    handleRefresh();
                }
            });
        }
    };

    private ApplicationProcess xapFile;

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
        viewer.setLabelProvider(new DecoratingLabelProvider(
                new ViewLabelProvider(), PlatformUI.getWorkbench()
                        .getDecoratorManager().getLabelDecorator()));
        viewer.setComparator(new NodeIdSorter());

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
        rootNode = activeEditorTemp.getPowerlinkRootNode();
        rootNode.addNodePropertyChangeListener(nodePropertyChangeListener);

        Control control = viewer.getControl();
        if ((control != null) && !control.isDisposed()) {
            viewer.setInput(rootNode);
            viewer.expandAll();
        }
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
            System.out.println("The selected object element = " + object);
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
                    currentProject = rootNode.getOpenConfiguratorProject();
                    System.err.println("currentProject...." + currentProject);
                    if (currentProject != null) {
                        Path outputpath = getProjectOutputPath(node);
                        final java.nio.file.Path targetPath;

                        if (outputpath.isLocal()) {
                            targetPath = FileSystems.getDefault().getPath(
                                    node.getProject().getLocation().toString(),
                                    outputpath.getPath());
                        } else {
                            targetPath = FileSystems.getDefault()
                                    .getPath(outputpath.getPath());
                        }
                        File outputFile = new File(targetPath.toString());

                        if (outputFile.exists()) {
                            File[] fileList = outputFile.listFiles();
                            for (File file : fileList) {
                                if (file.getName()
                                        .equalsIgnoreCase("xap.xml")) {
                                    try {
                                        xapFile = XddMarshaller
                                                .unmarshallXapFile(file);
                                    } catch (JAXBException | SAXException
                                            | ParserConfigurationException
                                            | IOException e) {
                                        e.printStackTrace();
                                    }
                                    processImage = new ProcessImage(node,
                                            xapFile);
                                    node.setProcessImage(processImage);
                                    manager.add(showIoMapView);
                                }
                            }
                        }

                    }

                    manager.add(new Separator());
                } else if (nodeObjectModel instanceof TCN) {
                    if (node.isEnabled()) {
                        manager.add(addFirmwareFile);
                        manager.add(new Separator());
                        manager.add(disable);
                    } else {
                        manager.add(enable);
                    }
                    if (node.isModularheadNode()) {
                        manager.add(new Separator());
                        manager.add(generateNodeXDC);
                    }
                    // Display list of menu only if the nodes are enabled.
                    if (node.isEnabled()) {
                        manager.add(new Separator());
                        manager.add(showPdoMapping);
                        manager.add(showObjectDictionary);
                        manager.add(showParameter);
                        manager.add(new Separator());
                        manager.add(copyNode);
                    }
                    manager.add(new Separator());
                    manager.add(deleteNode);
                    manager.add(new Separator());
                }
                // Display list of menu only if the nodes are enabled.
                if (node.isEnabled()) {
                    manager.add(new Separator());
                    manager.add(showProperties);
                }
            }
            if (object instanceof HeadNodeInterface) {
                HeadNodeInterface interfaceObj = (HeadNodeInterface) object;
                Node node = interfaceObj.getNode();
                if (node.isEnabled()) {
                    manager.add(addNewModule);
                    manager.add(new Separator());
                    manager.add(showProperties);
                }
            }

            if (object instanceof Module) {
                Module moduleObj = (Module) object;
                Node node = moduleObj.getNode();
                if (moduleObj.hasError()) {
                    manager.add(deleteNode);
                } else {

                    if (node.isEnabled()) {
                        if (moduleObj.isEnabled()) {
                            manager.add(addFirmwareFile);
                            manager.add(new Separator());
                            manager.add(disable);
                        } else {
                            manager.add(enable);
                        }
                        if (moduleObj.isEnabled()) {

                            manager.add(new Separator());
                            if (moduleObj.getPreviousModulePosition(
                                    moduleObj.getPosition()) != 0) {
                                manager.add(moveModuleUp);
                            }
                            if (moduleObj.getNextModulePosition(
                                    moduleObj.getPosition()) != 0) {
                                manager.add(moveModuleDown);
                            }
                            manager.add(new Separator());
                            manager.add(showObjectDictionary);
                            manager.add(showParameter);

                        }
                        manager.add(new Separator());

                    }
                    manager.add(new Separator());
                    manager.add(deleteNode);
                    manager.add(new Separator());
                    if (moduleObj.isEnabled()) {
                        manager.add(new Separator());
                        manager.add(showProperties);
                    }
                }
            }
        }
    }

    private void fillLocalPullDown(IMenuManager manager) {
        manager.removeAll();
        fillContextMenu(manager);
    }

    private void fillLocalToolBar(IToolBarManager manager) {
        manager.removeAll();
        manager.add(sortNode);
        manager.add(new Separator());
        manager.add(refreshAction);
        manager.add(showPdoMapping);
        manager.add(showObjectDictionary);
        manager.add(showParameter);
        manager.add(showProperties);
    }

    /**
     * Exports the module XDC into head Node XDC.
     *
     * @param selection Instance of Module
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void generateXDC(IStructuredSelection selection)
            throws JDOMException, IOException {
        if (selection.isEmpty()) {
            showMessage("No selection");
            return;
        }

        @SuppressWarnings("rawtypes")
        List selectedObjectsList = selection.toList();

        for (Object selectedObject : selectedObjectsList) {
            if (selectedObject instanceof Node) {
                final Node node = (Node) selectedObject;
                exportNodeXDC = new Job("Export module into node XDC.") {

                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        IStatus result;
                        try {
                            result = OpenConfiguratorProjectUtils
                                    .updateModuleObjectInNode(node, monitor);
                            File xdcFile = new File(
                                    node.getAbsolutePathToXdc());

                            Path outputpath = getProjectOutputPath(node);

                            final java.nio.file.Path targetPath;

                            if (outputpath.isLocal()) {
                                targetPath = FileSystems.getDefault().getPath(
                                        node.getProject().getLocation()
                                                .toString(),
                                        outputpath.getPath());
                            } else {
                                targetPath = FileSystems.getDefault()
                                        .getPath(outputpath.getPath());
                            }

                            OpenConfiguratorMessageConsole.getInstance()
                                    .printInfoMessage(
                                            "Generated modular head node XDC at: "
                                                    + targetPath.toString()
                                                    + "\\" + xdcFile.getName(),
                                            node.getProject().getName());
                            return result;
                        } catch (JDOMException | IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return null;
                    }

                };
                exportNodeXDC.schedule();

            }
        }
    }

    /**
     * @return The instance of POWERLINK root node to get the node list.
     */
    public PowerlinkRootNode getNodeList() {
        return rootNode;
    }

    /**
     * @return Returns the output path settings from the project XML.
     */
    public Path getProjectOutputPath(Node node) {
        List<PathSettings> pathSett = node.getCurrentProject()
                .getProjectConfiguration().getPathSettings();
        // pathSett = null;
        for (PathSettings pathSet : pathSett) {
            pathSet.getActivePath();

            String activeOutputPathID = pathSet.getActivePath();
            if (activeOutputPathID == null) {
                if (!pathSet.getPath().isEmpty()) {
                    TPath defaultPath = OpenConfiguratorProjectUtils
                            .getTPath(pathSet, "defaultOutputPath");
                    if (defaultPath != null) {
                        return new Path(defaultPath.getPath(), true);
                    }
                }
            } else {
                TPath defaultPath = OpenConfiguratorProjectUtils
                        .getTPath(pathSet, activeOutputPathID);
                if (defaultPath != null) {
                    if (!defaultPath.getId()
                            .equalsIgnoreCase("defaultOutputPath")) {
                        return new Path(defaultPath.getPath(), false);
                    }
                } else {
                    System.err.println(
                            "Unhandled error occurred. activeOutputPath not found");
                }
            }
        }
        return new Path(IPowerlinkProjectSupport.DEFAULT_OUTPUT_DIR, true);
    }

    private void handleEnableDisable(IStructuredSelection selection) {
        if (selection.isEmpty()) {
            showMessage("No selection");
            return;
        }

        @SuppressWarnings("rawtypes")
        List selectedObjectsList = selection.toList();

        for (Object selectedObject : selectedObjectsList) {
            if (selectedObject instanceof Node) {
                Node node = (Node) selectedObject;
                // The variable res is used within the try block.
                Result res = new Result();
                // checks for valid XDC file
                if (!node.hasError()) {
                    res = OpenConfiguratorLibraryUtils
                            .toggleEnableDisable(node);
                    if (!res.IsSuccessful()) {
                        showMessage(OpenConfiguratorLibraryUtils
                                .getErrorMessage(res));
                        return;
                    }
                }

                try {
                    node.setEnabled(!node.isEnabled());
                    if (node.isModularheadNode()) {
                        List<HeadNodeInterface> interfaceList = node
                                .getHeadNodeInterface();
                        for (HeadNodeInterface interfaces : interfaceList) {
                            Collection<Module> moduleList = interfaces
                                    .getModuleCollection().values();
                            for (Module module : moduleList) {
                                module.setModuleEnabled(node.isEnabled());
                            }
                        }
                    }
                } catch (JDOMException | IOException ex) {
                    OpenConfiguratorMessageConsole.getInstance()
                            .printErrorMessage(ex.getMessage(),
                                    node.getProject().getName());
                    ex.printStackTrace();
                }

                viewer.refresh();

                // Set empty selection when node is disabled.
                if (!node.isEnabled()) {
                    viewer.setSelection(TreeSelection.EMPTY);
                } else {
                    viewer.setSelection(viewer.getSelection());
                    try {
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage()
                                .showView(IPageLayout.ID_PROP_SHEET);
                    } catch (PartInitException e) {
                        System.err.println("Empty Properties sheet");
                        e.printStackTrace();
                    }
                    setFocus();
                }

                try {
                    node.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                            new NullProgressMonitor());
                } catch (CoreException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (selectedObject instanceof Module) {
                Module module = (Module) selectedObject;

                Collection<Module> moduleCollection = module
                        .getInterfaceOfModule().getModuleCollection().values();
                for (Module mod : moduleCollection) {
                    if (mod.hasError()) {
                        PluginErrorDialogUtils.showMessageWindow(
                                MessageDialog.WARNING,
                                "Module " + module.getModuleName()
                                        + " cannot be disabled because the Module "
                                        + mod.getModuleName()
                                        + " has invalid configuration file.",
                                mod.getProject().getName());
                        return;
                    }
                }

                try {
                    boolean validateModule = true;
                    System.err.println("Module is enabled/disabled..."
                            + module.isEnabled());
                    if (!module.isEnabled()) {
                        validateModule = canModuleEnabled(module);
                        System.err.println(
                                "Can module enabled..." + validateModule);
                    } else {
                        if (isNextModulePositionAvailable(module)) {
                            MessageDialog dialog = new MessageDialog(null,
                                    "Disable module", null,
                                    "Disabling the module "
                                            + module.getModuleName()
                                            + " will disable subsequent modules if their module type do not match.",
                                    MessageDialog.WARNING,
                                    new String[] { "Yes", "No" }, 1);
                            int result = dialog.open();
                            if (result == 0) {
                                disableModule(module);
                            } else {
                                return;
                            }
                        }
                    }
                    System.err.println("Validate module .." + validateModule);
                    if (validateModule) {

                        module.setEnabled(!module.isEnabled());
                    } else {
                        showErrorMessage(errorModuleEnabled(module));
                    }
                } catch (JDOMException | IOException e) {
                    OpenConfiguratorMessageConsole.getInstance()
                            .printErrorMessage(e.getMessage(),
                                    module.getNode().getProject().getName());

                    e.printStackTrace();
                }
                handleRefresh();
                viewer.refresh();

                // Set empty selection when node is disabled.
                if (!module.isEnabled()) {
                    viewer.setSelection(TreeSelection.EMPTY);
                } else {
                    viewer.setSelection(viewer.getSelection());
                    try {
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage()
                                .showView(IPageLayout.ID_PROP_SHEET);
                    } catch (PartInitException e) {
                        System.err.println("Empty Properties sheet");
                        e.printStackTrace();
                    }
                    setFocus();
                }

                try {
                    module.getNode().getProject().refreshLocal(
                            IResource.DEPTH_INFINITE,
                            new NullProgressMonitor());
                } catch (CoreException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Refreshes the network view
     */
    public void handleRefresh() {
        viewer.setInput(rootNode);
        viewer.expandAll();
    }

    /**
     * Removes the selected Node or Module from the network view.
     *
     * @param selection Selection instance
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void handleRemoveNode(IStructuredSelection selection)
            throws IOException, JDOMException {
        if (selection.isEmpty()) {
            showMessage("No selection");
            return;
        }

        @SuppressWarnings("rawtypes")
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
                        // checks for valid XDC file

                        try {
                            rootNode.removeNode(node);
                        } catch (JDOMException | IOException e) {
                            if (e instanceof NoSuchFileException) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printErrorMessage(
                                                "The file " + e.getMessage()
                                                        + " cannot be found.",
                                                node.getProject().getName());
                            } else {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printErrorMessage(e.getMessage(),
                                                node.getProject().getName());
                            }
                            e.printStackTrace();
                        }

                        handleRefresh();
                        viewer.refresh();

                        try {
                            node.getProject().refreshLocal(
                                    IResource.DEPTH_INFINITE,
                                    new NullProgressMonitor());
                        } catch (CoreException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {
                    showWarningMessage("Node '" + node.getName()
                            + "' cannot be removed from the network.");
                }
            } else if (selectedObject instanceof Module) {
                Module module = (Module) selectedObject;
                Object moduleObjectModel = module.getModelOfModule();
                if (moduleObjectModel instanceof InterfaceList.Interface.Module) {
                    MessageDialog dialog = new MessageDialog(null,
                            "Delete node", null,
                            "Are you sure you want to delete the module '"
                                    + module.getModuleName() + "'",
                            MessageDialog.QUESTION,
                            new String[] { "Yes", "No" }, 1);

                    int result = dialog.open();
                    if (result == 0) {
                        // checks for valid XDC file
                        boolean finalModuleCheck = isNextModulePositionAvailable(
                                module)
                                || isPreviousModulePositionAvailable(module);
                        System.err.println("next position..."
                                + isNextModulePositionAvailable(module)
                                + " previous position...."
                                + isPreviousModulePositionAvailable(module));
                        rootNode.removeModule(module, finalModuleCheck);

                        handleRefresh();
                        viewer.refresh();

                        try {
                            module.getProject().refreshLocal(
                                    IResource.DEPTH_INFINITE,
                                    new NullProgressMonitor());
                        } catch (CoreException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        validateModule(module);

                        handleRefresh();
                        viewer.refresh();

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

    /**
     * Verifies the next module position availability in the head node
     *
     * @param module Instance of Module
     * @return <code>true</code> if module is available, <code>false</code> if
     *         not available.
     */
    public boolean isNextModulePositionAvailable(Module module) {
        int enabledModulePosition = module.getPosition();
        boolean availableModule = false;
        HeadNodeInterface interfaceObj = module.getInterfaceOfModule();
        List<Integer> positionToBeChecked = new ArrayList<>();
        Set<Integer> positionset = interfaceObj.getModuleCollection().keySet();
        for (Integer pos : positionset) {
            if (pos > enabledModulePosition) {
                positionToBeChecked.add(pos);
            }
        }

        if (positionToBeChecked.size() > 0) {
            availableModule = true;
        }
        return availableModule;

    }

    /**
     * Verifies the previous module position availability in the head node
     *
     * @param module Instance of Module
     * @return <code>true</code> if module is available, <code>false</code> if
     *         not available.
     */
    public boolean isPreviousModulePositionAvailable(Module module) {
        int enabledModulePosition = module.getPosition();
        boolean availableModule = false;
        HeadNodeInterface interfaceObj = module.getInterfaceOfModule();
        List<Integer> positionToBeChecked = new ArrayList<>();
        Set<Integer> positionset = interfaceObj.getModuleCollection().keySet();
        for (Integer pos : positionset) {
            if (pos < enabledModulePosition) {
                positionToBeChecked.add(pos);
            }
        }

        if (positionToBeChecked.size() > 0) {
            availableModule = true;
        }
        return availableModule;
    }

    private void makeActions() {
        addNewNode = new Action(ADD_NEW_NODE_ACTION_MESSAGE) {
            @Override
            public void run() {
                ISelection nodeTreeSelection = viewer.getSelection();
                if ((nodeTreeSelection != null)
                        && (nodeTreeSelection instanceof IStructuredSelection)) {
                    IStructuredSelection strucSelection = (IStructuredSelection) nodeTreeSelection;
                    Object selectedObject = strucSelection.getFirstElement();
                    if ((selectedObject instanceof Node)) {
                        Node selectedNode = (Node) selectedObject;
                        NewNodeWizard newNodeWizard = new NewNodeWizard(
                                rootNode, (Node) selectedObject);
                        if (!newNodeWizard.hasErrors()) {
                            WizardDialog wd = new WizardDialog(
                                    Display.getDefault().getActiveShell(),
                                    newNodeWizard);
                            wd.setTitle(newNodeWizard.getWindowTitle());
                            wd.open();
                        } else {
                            showMessage(ADD_NEW_NODE_ERROR_MESSAGE);
                        }

                        try {
                            selectedNode.getProject().refreshLocal(
                                    IResource.DEPTH_INFINITE,
                                    new NullProgressMonitor());
                        } catch (CoreException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        handleRefresh();
                    }
                } else {
                    showMessage(ADD_NEW_NODE_INVALID_SELECTION_MESSAGE);
                }
            }
        };
        addNewNode.setToolTipText(ADD_NEW_NODE_TOOL_TIP_TEXT);
        addNewNode
                .setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                        .getImageDescriptor(ISharedImages.IMG_OBJ_ADD));

        enable = new Action(ENABLE_ACTION_MESSAGE) {
            @Override
            public void run() {
                ISelection selection = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getSelectionService()
                        .getSelection();
                if ((selection != null)
                        && (selection instanceof IStructuredSelection)) {
                    handleEnableDisable((IStructuredSelection) selection);
                }

            }
        };
        addFirmwareFile = new Action(ADD_FIRMWARE_TOOL_TIP_TEXT) {
            @Override
            public void run() {
                ISelection nodeTreeSelection = viewer.getSelection();
                if ((nodeTreeSelection != null)
                        && (nodeTreeSelection instanceof IStructuredSelection)) {
                    IStructuredSelection strucSelection = (IStructuredSelection) nodeTreeSelection;
                    Object selectedObject = strucSelection.getFirstElement();
                    if ((selectedObject instanceof Node)) {
                        NewFirmwareWizard newFirmwareWizard = new NewFirmwareWizard(
                                rootNode, selectedObject);

                        WizardDialog wd = new WizardDialog(
                                Display.getDefault().getActiveShell(),
                                newFirmwareWizard);
                        wd.setTitle(newFirmwareWizard.getWindowTitle());
                        wd.open();

                        try {
                            if (selectedObject instanceof Node) {
                                ((Node) selectedObject).getProject()
                                        .refreshLocal(IResource.DEPTH_INFINITE,
                                                new NullProgressMonitor());
                            } else {
                                ((Module) selectedObject).getProject()
                                        .refreshLocal(IResource.DEPTH_INFINITE,
                                                new NullProgressMonitor());
                            }
                        } catch (CoreException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        handleRefresh();
                    } else if (selectedObject instanceof Module) {
                        Module selectedModule = (Module) selectedObject;
                        System.err.println(
                                "The can firmware be added .." + selectedModule
                                        .canFirmwareAdded(selectedModule));
                        if (!selectedModule.canFirmwareAdded(selectedModule)) {
                            showErrorMessage("The node '"
                                    + selectedModule.getNode()
                                            .getNodeIDWithName()
                                    + "' does not support firmware update.");

                        } else {

                            NewFirmwareWizard newFirmwareWizard = new NewFirmwareWizard(
                                    rootNode, selectedObject);

                            WizardDialog wd = new WizardDialog(
                                    Display.getDefault().getActiveShell(),
                                    newFirmwareWizard);
                            wd.setTitle(newFirmwareWizard.getWindowTitle());
                            wd.open();
                        }

                        try {
                            selectedModule.getProject().refreshLocal(
                                    IResource.DEPTH_INFINITE,
                                    new NullProgressMonitor());
                        } catch (CoreException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        handleRefresh();
                    }
                }
            }
        };
        addFirmwareFile.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
        disable = new Action(DISABLE_ACTION_MESSAGE) {

            @Override
            public void run() {
                ISelection selection = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getSelectionService()
                        .getSelection();
                if ((selection != null)
                        && (selection instanceof IStructuredSelection)) {
                    handleEnableDisable((IStructuredSelection) selection);
                }

            }
        };
        generateNodeXDC = new Action(GENERATE_NODE_XDC_ACTION_MESSAGE) {

            @Override
            public void run() {

                ISelection selection = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getSelectionService()
                        .getSelection();
                if ((selection != null)
                        && (selection instanceof IStructuredSelection)) {
                    try {

                        generateXDC((IStructuredSelection) selection);
                    } catch (JDOMException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    viewer.refresh();
                }
            }
        };
        generateNodeXDC.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.EXPORT_NODE_ICON));

        moveModuleUp = new Action("Move Up") {

            @Override
            public void run() {
                ISelection selection = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getSelectionService()
                        .getSelection();
                if ((selection != null)
                        && (selection instanceof IStructuredSelection)) {

                    try {
                        moveModuleUp((IStructuredSelection) selection);
                    } catch (JDOMException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    viewer.refresh();
                }
            }
        };
        moveModuleUp.setToolTipText("Move Up");
        moveModuleUp.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.ARROW_UP_ICON));

        moveModuleDown = new Action("Move Down") {

            @Override
            public void run() {
                ISelection selection = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getSelectionService()
                        .getSelection();
                if ((selection != null)
                        && (selection instanceof IStructuredSelection)) {

                    try {
                        moveModuleDown((IStructuredSelection) selection);
                    } catch (JDOMException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    handleRefresh();
                    viewer.refresh();

                }
            }
        };

        moveModuleDown.setToolTipText("Move Down");
        moveModuleDown.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.ARROW_DOWN_ICON));

        enable.setToolTipText(ENABLE_ACTION_MESSAGE);
        enable.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.DISABLE_NODE_ICON));
        disable.setToolTipText(ENABLE_ACTION_MESSAGE);
        disable.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.DISABLE_NODE_ICON));

        copyNode = new Action("Copy Node") {
            @Override
            public void run() {
                super.run();

                // String test1 = JOptionPane
                // .showInputDialog("Enter the node ID: ");
                //
                // int nodeId = Integer.parseInt(test1);

                ISelection nodeTreeSelection = viewer.getSelection();
                if ((nodeTreeSelection != null)
                        && (nodeTreeSelection instanceof IStructuredSelection)) {
                    IStructuredSelection strucSelection = (IStructuredSelection) nodeTreeSelection;
                    Object selectedObject = strucSelection.getFirstElement();
                    if ((selectedObject instanceof Node)) {
                        Node node = (Node) selectedObject;
                        CopyNodeWizard copyNodeWizard = new CopyNodeWizard(
                                rootNode, (Node) selectedObject);
                        if (!copyNodeWizard.hasErrors()) {
                            WizardDialog wd = new WizardDialog(
                                    Display.getDefault().getActiveShell(),
                                    copyNodeWizard);
                            wd.setTitle(copyNodeWizard.getWindowTitle());
                            wd.open();
                        } else {
                            showMessage(ADD_NEW_NODE_ERROR_MESSAGE);
                        }

                        try {
                            node.getProject().refreshLocal(
                                    IResource.DEPTH_INFINITE,
                                    new NullProgressMonitor());
                        } catch (CoreException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        handleRefresh();
                        int nodeId = Integer
                                .valueOf(copyNodeWizard.getNodeId());

                        String name = copyNodeWizard.getNodeName();
                        int stationTypeChanged = copyNodeWizard
                                .getStationTypeIndex(
                                        copyNodeWizard.getStationType());

                        try {
                            node.copyNode(nodeId, stationTypeChanged, name);

                        } catch (JDOMException | InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        handleRefresh();
                    }
                }

            }
        };
        copyNode.setToolTipText("Copy Node");
        copyNode.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.CN_ICON));

        showIoMapView = new Action(SHOW_IO_MAP_ACTION_MESSAGE) {
            @Override
            public void run() {
                try {

                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().showView(ProcessImageView.ID);
                    viewer.setSelection(viewer.getSelection());

                } catch (PartInitException e) {
                    e.printStackTrace();
                    showMessage(SHOW_PROCESS_IMAGE_ERROR_MESSAGE);
                }
            }
        };
        showIoMapView.setToolTipText(SHOW_IO_MAP_ACTION_MESSAGE);
        showIoMapView.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.IO_MAP_ICON));

        showObjectDictionary = new Action(
                SHOW_OBJECT_DICTIONARY_ACTION_MESSAGE) {
            @Override
            public void run() {
                try {

                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().showView(ObjectDictionaryView.ID);
                    viewer.setSelection(viewer.getSelection());

                } catch (PartInitException e) {
                    e.printStackTrace();
                    showMessage(SHOW_OBJECT_DICTIONARY_ERROR_MESSAGE);
                }
            }
        };
        showObjectDictionary
                .setToolTipText(SHOW_OBJECT_DICTIONARY_ACTION_MESSAGE);
        showObjectDictionary
                .setImageDescriptor(org.epsg.openconfigurator.Activator
                        .getImageDescriptor(IPluginImages.OBD_ICON));

        showParameter = new Action(SHOW_PARAMETER_ACTION_MESSAGE) {
            @Override
            public void run() {
                try {

                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().showView(ParameterView.ID);
                    viewer.setSelection(viewer.getSelection());

                } catch (PartInitException e) {
                    e.printStackTrace();
                    showMessage(SHOW_PARAMETER_ERROR_MESSAGE);
                }
            }
        };
        showParameter.setToolTipText(SHOW_PARAMETER_ACTION_MESSAGE);
        showParameter.setImageDescriptor(
                org.epsg.openconfigurator.Activator.getImageDescriptor(
                        IPluginImages.OBD_PARAMETER_REFERENCE_ICON));

        showPdoMapping = new Action(SHOW_MAPING_VIEW_ACTION_MESSAGE) {

            @Override
            public void run() {

                try {
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().showView(MappingView.ID);

                    viewer.setSelection(viewer.getSelection());
                } catch (PartInitException e) {
                    e.printStackTrace();
                    showMessage(SHOW_MAPING_VIEW_ERROR_MESSAGE);
                }
            }
        };
        showPdoMapping.setToolTipText(SHOW_MAPING_VIEW_ACTION_MESSAGE);
        showPdoMapping.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.MAPPING_ICON));

        showProperties = new Action(PROPERTIES_ACTION_MESSAGE) {

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
                    showMessage(PROPERTIES_ERROR_MESSAGE);
                }
            }
        };
        showProperties.setToolTipText(PROPERTIES_ACTION_MESSAGE);
        showProperties.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.PROPERTIES_ICON));

        deleteNode = new Action(DELETE_NODE_ACTION_MESSAGE) {
            @Override
            public void run() {

                ISelection selection = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getSelectionService()
                        .getSelection();
                if ((selection != null)
                        && (selection instanceof IStructuredSelection)) {
                    try {
                        handleRemoveNode((IStructuredSelection) selection);
                    } catch (IOException | JDOMException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    viewer.refresh();
                }
            }
        };
        deleteNode.setToolTipText(DELETE_NODE_ACTION_MESSAGE);
        deleteNode
                .setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                        .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));

        sortNode = new Action(SORT_NODE_BY_STATION_TYPE_MESSAGE,
                IAction.AS_CHECK_BOX) {
            @Override
            public void run() {
                if (sortNode.isChecked()) {
                    sortNode.setToolTipText(SORT_NODE_BY_ID_MESSAGE);
                    viewer.setComparator(new NodeBasedSorter());
                } else {
                    sortNode.setToolTipText(SORT_NODE_BY_STATION_TYPE_MESSAGE);
                    viewer.setComparator(new NodeIdSorter());
                }

            }
        };
        sortNode.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.SORT_ICON));

        refreshAction = new Action(REFRESH_ACTION_MESSAGE) {
            @Override
            public void run() {
                handleRefresh();
            }
        };
        refreshAction.setToolTipText(REFRESH_ACTION_MESSAGE);
        refreshAction.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.REFRESH_ICON));

        addNewModule = new Action(ADD_NEW_MODULE_ACTION_MESSAGE) {
            @Override
            public void run() {
                ISelection nodeTreeSelection = viewer.getSelection();
                if ((nodeTreeSelection != null)
                        && (nodeTreeSelection instanceof IStructuredSelection)) {
                    IStructuredSelection strucSelection = (IStructuredSelection) nodeTreeSelection;
                    Object selectedObject = strucSelection.getFirstElement();
                    if ((selectedObject instanceof HeadNodeInterface)) {
                        NewModuleWizard newModuleWizard = new NewModuleWizard(
                                rootNode, (HeadNodeInterface) selectedObject);

                        WizardDialog wd = new WizardDialog(
                                Display.getDefault().getActiveShell(),
                                newModuleWizard);
                        wd.setTitle(newModuleWizard.getWindowTitle());
                        wd.open();

                        handleRefresh();
                    }
                } else {
                    showMessage(ADD_NEW_NODE_INVALID_SELECTION_MESSAGE);
                }
            }
        };
        addNewModule.setToolTipText(ADD_NEW_NODE_TOOL_TIP_TEXT);
        addNewModule
                .setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                        .getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
    }

    private void moveModuleDown(IStructuredSelection selection)
            throws JDOMException, IOException {
        if (selection.isEmpty()) {
            showMessage("No selection");
            return;
        }

        @SuppressWarnings("rawtypes")
        List selectedObjectsList = selection.toList();

        for (Object selectedObject : selectedObjectsList) {
            if (selectedObject instanceof Module) {
                Module module = (Module) selectedObject;

                int oldPosition = module.getPosition();
                int position = module.getNextModulePosition(oldPosition);
                if (position == 0) {
                    return;
                }
                if (module.validateMoveModuleDownPosition(oldPosition,
                        position)) {
                    if (String
                            .valueOf(module.getInterfaceOfModule()
                                    .getModuleAddressing())
                            .equalsIgnoreCase("MANUAL")) {
                        module.swapManualPosition(oldPosition, position);
                    } else {
                        module.swapPosition(oldPosition, position);
                    }
                } else {
                    showErrorMessage(module.errorOfMoveModuleDownPosition(
                            oldPosition, position));
                }
                System.err.println("The down position == " + position);

            }
        }

    }

    private void moveModuleUp(IStructuredSelection selection)
            throws JDOMException, IOException {
        if (selection.isEmpty()) {
            showMessage("No selection");
            return;
        }

        @SuppressWarnings("rawtypes")
        List selectedObjectsList = selection.toList();

        for (Object selectedObject : selectedObjectsList) {
            if (selectedObject instanceof Module) {
                Module module = (Module) selectedObject;

                int oldPosition = module.getPosition();
                int position = module.getPreviousModulePosition(oldPosition);
                if (position == 0) {
                    return;
                }
                if (module.validateMoveModuleUpPosition(oldPosition,
                        position)) {
                    if (String.valueOf(
                            module.getInterfaceOfModule().getModuleAddressing())
                            .equals("MANUAL")) {

                        module.swapManualPosition(oldPosition, position);

                    } else {
                        module.swapPosition(oldPosition, position);
                    }
                } else {
                    showErrorMessage(module.errorOfMoveModuleUpPosition(
                            oldPosition, position));
                }
                System.err.println("The down position == " + position);

            }
        }

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

    private void showErrorMessage(String message) {
        MessageDialog.openError(viewer.getControl().getShell(),
                "POWERLINK Network", message);
    }

    private void showMessage(String message) {
        MessageDialog.openInformation(viewer.getControl().getShell(),
                "POWERLINK Network", message);
    }

    private void showWarningMessage(String message) {
        MessageDialog.openWarning(viewer.getControl().getShell(),
                "POWERLINK Network", message);
    }

    @SuppressWarnings("null")
    private void validateModule(Module module)
            throws JDOMException, IOException {
        int removedModulePosition = module.getPosition();
        System.err.println("removed position...." + removedModulePosition);
        HeadNodeInterface interfaceObj = module.getInterfaceOfModule();
        List<Integer> positionToBeChecked = new ArrayList<>();
        List<Integer> previousPositionList = new ArrayList<>();
        Set<Integer> positionset = interfaceObj.getModuleCollection().keySet();
        for (Integer pos : positionset) {
            if (pos < removedModulePosition) {
                previousPositionList.add(pos);

            }

            if (pos > removedModulePosition) {
                positionToBeChecked.add(pos);
            }
        }

        Collections.sort(previousPositionList, Collections.reverseOrder());
        if (previousPositionList.size() > 0) {
            for (Integer previousModulePosition : previousPositionList) {
                System.err.println(
                        "previous pos........" + previousModulePosition);
                Module previousPositionModule = interfaceObj
                        .getModuleCollection().get(previousModulePosition);
                List<ModuleType> previousModuleTypeList = previousPositionModule
                        .getModuleInterface().getModuleTypeList()
                        .getModuleType();
                if (previousPositionModule.isEnabled()) {
                    // The variable positionToBeChecked undergoes redundant null
                    // check in order to ensure the lists are not empty, Hence
                    // unnecessary code blocks will not be executed.
                    if (positionToBeChecked != null) {
                        for (Integer posit : positionToBeChecked) {
                            Module mod = interfaceObj.getModuleCollection()
                                    .get(posit);
                            if (mod != null) {
                                String nextPositionModuleType = mod
                                        .getModuleInterface().getType();

                                for (ModuleType moduleType : previousModuleTypeList) {
                                    String previousModuleType = moduleType
                                            .getType();

                                    if (String
                                            .valueOf(interfaceObj
                                                    .getModuleAddressing())
                                            .equalsIgnoreCase("MANUAL")) {
                                        if (previousModuleType.equalsIgnoreCase(
                                                nextPositionModuleType)) {
                                            Module moduleValue = interfaceObj
                                                    .getModuleCollection()
                                                    .get(posit);
                                            int newPosition = posit - 1;

                                            moduleValue.setPositions(String
                                                    .valueOf(newPosition));
                                            OpenConfiguratorProjectUtils
                                                    .updateModuleConfigurationPath(
                                                            moduleValue,
                                                            newPosition);
                                        } else {
                                            Module moduleValue = interfaceObj
                                                    .getModuleCollection()
                                                    .get(posit);
                                            int newPosition = posit - 1;
                                            moduleValue.setPositions(String
                                                    .valueOf(newPosition));
                                            OpenConfiguratorProjectUtils
                                                    .updateModuleConfigurationPath(
                                                            moduleValue,
                                                            newPosition);
                                            mod.setEnabled(false);
                                        }
                                    } else {
                                        if (nextPositionModuleType != null) {
                                            if (previousModuleType
                                                    .equalsIgnoreCase(
                                                            nextPositionModuleType)) {

                                                Module moduleValue = interfaceObj
                                                        .getModuleCollection()
                                                        .get(posit);
                                                int newPosition = posit - 1;

                                                moduleValue.setPositions(String
                                                        .valueOf(newPosition));
                                                OpenConfiguratorProjectUtils
                                                        .updateModuleConfigurationPath(
                                                                moduleValue,
                                                                newPosition);

                                            } else {
                                                System.err.println(
                                                        "Disabled module name.."
                                                                + mod.getModuleName());
                                                System.err.println(
                                                        "posit..... " + posit);

                                                Module moduleValue = interfaceObj
                                                        .getModuleCollection()
                                                        .get(posit);
                                                int newPosition = posit - 1;
                                                System.err.println(
                                                        "new posit..... "
                                                                + newPosition);
                                                moduleValue.setPositions(String
                                                        .valueOf(newPosition));
                                                OpenConfiguratorProjectUtils
                                                        .updateModuleConfigurationPath(
                                                                moduleValue,
                                                                newPosition);
                                                if (mod.isEnabled()) {
                                                    mod.setEnabled(false);
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        System.err.println("Next position unavailable.");
                    }
                    return;
                }
            }
        } else {
            if (positionToBeChecked.size() > 0) {
                for (Integer position : positionToBeChecked) {
                    Module moduleObj = interfaceObj.getModuleCollection()
                            .get(position);
                    String nextModuletype = moduleObj.getModuleInterface()
                            .getType();
                    String previousModuleType = interfaceObj.getInterfaceType();
                    if (String.valueOf(interfaceObj.getModuleAddressing())
                            .equalsIgnoreCase("MANUAL")) {
                        if (previousModuleType
                                .equalsIgnoreCase(nextModuletype)) {
                            Module moduleValue = interfaceObj
                                    .getModuleCollection().get(position);
                            int newPosition = position - 1;
                            System.err.println(
                                    "The module entered critical position....."
                                            + newPosition + "Name..."
                                            + moduleValue.getModuleName());

                            System.err.println(
                                    "The module address entered critical position....."
                                            + newPosition + "Name..."
                                            + moduleValue.getModuleName()
                                            + "Address..."
                                            + moduleValue.getAddress());

                            moduleValue
                                    .setPositions(String.valueOf(newPosition));
                            OpenConfiguratorProjectUtils
                                    .updateModuleConfigurationPath(moduleValue,
                                            newPosition);
                            return;
                        }
                        Module moduleValue = interfaceObj.getModuleCollection()
                                .get(position);
                        int newPosition = position - 1;
                        System.err.println("new posit..... " + newPosition);

                        moduleValue.setPositions(String.valueOf(newPosition));
                        OpenConfiguratorProjectUtils
                                .updateModuleConfigurationPath(moduleValue,
                                        newPosition);
                        moduleValue.setEnabled(false);
                    } else {
                        if (nextModuletype != null) {
                            if (previousModuleType
                                    .equalsIgnoreCase(nextModuletype)) {

                                Module moduleValue = interfaceObj
                                        .getModuleCollection().get(position);
                                int newPosition = position - 1;
                                moduleValue.setPositions(
                                        String.valueOf(newPosition));
                                OpenConfiguratorProjectUtils
                                        .updateModuleConfigurationPath(
                                                moduleValue, newPosition);
                                return;

                            }
                            System.err.println("Disabled module name.."
                                    + moduleObj.getModuleName());
                            System.err.println("posit..... " + position);

                            Module moduleValue = interfaceObj
                                    .getModuleCollection().get(position);
                            int newPosition = position - 1;
                            System.err.println("new posit..... " + newPosition);
                            moduleValue
                                    .setPositions(String.valueOf(newPosition));
                            OpenConfiguratorProjectUtils
                                    .updateModuleConfigurationPath(moduleValue,
                                            newPosition);
                            if (moduleObj.isEnabled()) {
                                moduleObj.setEnabled(false);
                            }
                        }
                    }
                }
            }
        }

        handleRefresh();
        viewer.refresh();
        try {
            module.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
