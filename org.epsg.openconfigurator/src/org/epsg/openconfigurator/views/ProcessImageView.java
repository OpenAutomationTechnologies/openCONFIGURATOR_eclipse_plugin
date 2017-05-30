/*******************************************************************************
 * @file   ProcessImageView.java
 *
 * @author Sree hari Vignesh, Kalycito Infotech Private Limited.
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
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.ProcessImageChannel;
import org.epsg.openconfigurator.resources.IPluginImages;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.xap.Channel;
import org.epsg.openconfigurator.xmlbinding.xap.ProcessImage;

/**
 * View to list the channels of process image file
 *
 * @author Sree hari
 *
 */
public class ProcessImageView extends ViewPart {

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
                if ((tableViewer != null)
                        && !tableViewer.getControl().isDisposed()) {
                    tableViewer.setInput(new Object[0]);
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

    private class ProcessImageContentProvider
            implements IStructuredContentProvider {

        @Override
        public void dispose() {
        }

        @Override
        public Object[] getElements(Object inputElement) {
            String empty = StringUtils.EMPTY;
            if (inputElement instanceof Node) {
                Node node = (Node) inputElement;
                List<Object> channelList = new ArrayList<>();
                if (node.getNodeModel() instanceof TNetworkConfiguration) {

                    List<ProcessImage> processImageList = new ArrayList<>();
                    if (node.getProcessImage() != null) {
                        processImageList = node.getProcessImage()
                                .getProcessImageList();
                    }
                    if (processImageList != null) {
                        for (ProcessImage pcsImg : processImageList) {
                            List<Channel> chnlList = pcsImg.getChannel();
                            for (Channel chnl : chnlList) {
                                processImageChannel = new ProcessImageChannel(
                                        pcsImg, chnl);
                                channelList.add(processImageChannel);
                            }
                            channelList.add(empty);
                        }
                    } else {
                        System.err.println(
                                "The Xap file is not available in the output folder.");
                    }

                    return channelList.toArray();

                }
                channelList.add(empty);
                return channelList.toArray();
            }
            return new Object[0];
        }

        @Override
        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
        }

    }

    /**
     * Label provider for the PDO summary table.
     *
     * @author Sree hari
     *
     */
    private class ProcessImageLabelProvider extends LabelProvider
            implements ITableLabelProvider {

        @Override
        public Image getColumnImage(Object element, int columnIndex) {

            if (element instanceof ProcessImageChannel) {
                ProcessImageChannel channel = (ProcessImageChannel) element;
                switch (columnIndex) {
                    case 0:
                        if (channel.getProcessImageType()
                                .equalsIgnoreCase("input")) {
                            return outChannelImage;
                        }

                        if (channel.getProcessImageType()
                                .equalsIgnoreCase("output")) {
                            return inChannelImage;
                        }
                        break;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {

            String retValue = element.toString();
            if (element instanceof ProcessImageChannel) {
                ProcessImageChannel channel = (ProcessImageChannel) element;
                switch (columnIndex) {
                    case 0:
                        if (showAdvancedview.isChecked()) {
                            retValue = channel.getChannelName();
                        } else {
                            retValue = getShortChannelName(
                                    channel.getChannelName());
                        }
                        break;
                    case 1:
                        retValue = getNodeName(channel.getChannelName());
                        break;
                    case 2:
                        retValue = getIecDataType(channel.getChannelType());
                        break;

                    default:
                        break;
                }
            }

            return retValue;
        }

    }

    public static final String ID = "org.epsg.openconfigurator.views.ProcessImageView"; // $NON-NLS-0$

    public static final String MENU_SHOW_COMPLETE_CHANNEL_NAME_LABEL = "Show Complete Channel Name";
    public static final String MENU_SHOW_SHORT_CHANNEL_NAME_LABEL = "Show Short Channel Name";

    // Returns the shortest name for the datatypes
    private static String getIecDataType(String channelType) {
        switch (channelType) {
            case "BITSTRING":
                return "BITSTRING";
            case "BOOL":
                return "BOOL";
            case "BYTE":
                return "BYTE";
            case "CHAR":
                return "CHAR";
            case "WORD":
                return "WORD";
            case "DWORD":
                return "DWORD";
            case "LWORD":
                return "LWORD";
            case "Integer8":
                return "SINT";
            case "Integer16":
                return "INT";
            case "Integer32":
                return "DINT";
            case "Integer64":
                return "LINT";
            case "Unsigned8":
                return "USINT";
            case "Unsigned16":
                return "UINT";
            case "Unsigned32":
                return "UDINT";
            case "Unsigned64":
                return "ULINT";
            case "REAL":
                return "REAL";
            case "LREAL":
                return "LREAL";
            case "STRING":
                return "STRING";
            case "WSTRING":
                return "WSTRING";
            default:
                return "UNDEFINED";

        }
    }

    // Returns the short name of channel based on the given channel.
    private static String getShortChannelName(String channelName) {
        String shortChannelName = StringUtils.EMPTY;
        for (int index = channelName.indexOf(
                "."); index >= 0; index = channelName.indexOf(".", index + 1)) {
            shortChannelName = channelName.substring(index);
            int count = StringUtils.countMatches(shortChannelName, ".");
            if (count == 2) {
                return shortChannelName.substring(1);
            }
        }
        return shortChannelName;
    }

    private ProcessImageChannel processImageChannel;

    private final Image inChannelImage;

    private final Image outChannelImage;

    private Node nodeObj;

    private TableViewer tableViewer;

    /**
     * Source workbench part.
     */
    private IWorkbenchPart sourcePart;

    /**
     * Listener instance to listen to the changes in the source part.
     */
    private PartListener partListener = new PartListener();

    /**
     * Selection listener to update the objects and sub-objects in the Object
     * dictionary view.
     */
    ISelectionListener listener = new ISelectionListener() {
        @Override
        public void selectionChanged(IWorkbenchPart part,
                ISelection selection) {

            setPartName("I/O Mapping");

            if (tableViewer == null) {
                return;
            }

            if (sourcePart != null) {
                sourcePart.getSite().getPage().removePartListener(partListener);
            }

            // tableViewer.setInput(null);

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
                    Object nodeModel = nodeObj.getNodeModel();
                    if (nodeModel instanceof TNetworkConfiguration) {
                        tableViewer.setInput(nodeObj);
                    } else {
                        tableViewer.setInput(null);
                    }

                }
            }

            if (sourcePart != null) {
                sourcePart.getSite().getPage().addPartListener(partListener);
            }
        }
    };

    private Action showAdvancedview;

    private TableColumn channelNameColumn;

    public ProcessImageView() {

        inChannelImage = org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.IN_CHANNEL_ICON)
                .createImage();
        outChannelImage = org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.OUT_CHANNEL_ICON)
                .createImage();
    }

    private void contributeToActionBars() {
        IActionBars bars = getViewSite().getActionBars();

        fillLocalToolBar(bars.getToolBarManager());
        bars.updateActionBars();
    }

    private void createActions() {
        showAdvancedview = new Action(MENU_SHOW_COMPLETE_CHANNEL_NAME_LABEL,
                IAction.AS_CHECK_BOX) {
            @Override
            public void run() {
                if (showAdvancedview.isChecked()) {
                    tableViewer.setInput(nodeObj);
                    handleProcessImageTableResize();
                    showAdvancedview
                            .setToolTipText(MENU_SHOW_SHORT_CHANNEL_NAME_LABEL);
                    channelNameColumn.setText("Channel Name");

                } else {
                    tableViewer.refresh();
                    handleProcessImageTableResize();
                    showAdvancedview.setToolTipText(
                            MENU_SHOW_COMPLETE_CHANNEL_NAME_LABEL);
                    channelNameColumn.setText("Channel Name (Short)");
                }
            }
        };
        showAdvancedview.setToolTipText(MENU_SHOW_COMPLETE_CHANNEL_NAME_LABEL);
        showAdvancedview.setImageDescriptor(org.epsg.openconfigurator.Activator
                .getImageDescriptor(IPluginImages.FILTER_ICON));
        showAdvancedview.setChecked(false);
        showAdvancedview.run();
    }

    @Override
    public void createPartControl(Composite parent) {
        tableViewer = new TableViewer(parent,
                SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
        Table processImageTable = tableViewer.getTable();
        processImageTable.setLinesVisible(true);
        processImageTable.setHeaderVisible(true);

        TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer,
                SWT.CENTER);
        channelNameColumn = tableViewerColumn.getColumn();
        channelNameColumn.setAlignment(SWT.CENTER);
        channelNameColumn.setWidth(500);
        channelNameColumn.setText("Channel Name");

        TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
                tableViewer, SWT.CENTER);
        TableColumn nodeNameTblClmnChannel = tableViewerColumn_3.getColumn();
        nodeNameTblClmnChannel.setAlignment(SWT.CENTER);
        nodeNameTblClmnChannel.setWidth(200);
        nodeNameTblClmnChannel.setText("Node");

        TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
                tableViewer, SWT.NONE);
        TableColumn tblclmnSendToNode = tableViewerColumn_2.getColumn();
        tblclmnSendToNode.setAlignment(SWT.CENTER);
        tblclmnSendToNode.setWidth(200);
        tblclmnSendToNode.setText("Data Type");

        createActions();
        contributeToActionBars();

        getViewSite().getPage().addSelectionListener(IndustrialNetworkView.ID,
                listener);

        tableViewer.setContentProvider(new ProcessImageContentProvider());
        tableViewer.setLabelProvider(new ProcessImageLabelProvider());

        processImageTable.addListener(SWT.Resize, new Listener() {
            @Override
            public void handleEvent(Event event) {

                handleProcessImageTableResize();
            }

        });

    }

    @Override
    public void dispose() {
        super.dispose();
        if (sourcePart != null) {
            sourcePart.getSite().getPage().removePartListener(partListener);
        }

        inChannelImage.dispose();
        outChannelImage.dispose();

        getViewSite().getPage()
                .removeSelectionListener(IndustrialNetworkView.ID, listener);

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

    // Returns the name of node based on the given channel.
    private String getNodeName(String channelname) {
        String nodeName = StringUtils.EMPTY;

        int index = channelname.indexOf('.');
        int nodeIndex = channelname.indexOf('N');
        String nodeId = channelname.substring(nodeIndex + 1, index);
        nodeName = nodeObj.getNodeName(nodeId);
        return nodeName;
    }

    // Resizes the table based on advanced view selection
    private void handleProcessImageTableResize() {
        {
            Table table = tableViewer.getTable();
            int totalColumnCount = table.getColumnCount();
            int width = table.getClientArea().width;
            int finalWidth = width / totalColumnCount;
            for (TableColumn tblClmn : table.getColumns()) {
                if (showAdvancedview.isChecked()) {
                    if (tblClmn.getText().equalsIgnoreCase("Channel Name")) {
                        tblClmn.setWidth(finalWidth + 150);
                        break;
                    }
                }
                tblClmn.setWidth(finalWidth);
            }
        }
    }

    @Override
    public void setFocus() {
        // TODO Auto-generated method stub
    }

}
