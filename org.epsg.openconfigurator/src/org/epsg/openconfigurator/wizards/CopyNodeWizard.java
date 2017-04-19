package org.epsg.openconfigurator.wizards;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.epsg.openconfigurator.model.FirmwareManager;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularHead;

public class CopyNodeWizard extends Wizard {
    private static final String WINDOW_TITLE = "POWERLINK node wizard";
    private static final String ERROR_WHILE_COPYING_XDD = "Error occurred while copying the configuration file.";
    public static final String ERROR_NODE_MODEL = "Invalid node model.";

    private static final String CNPRES_CHAINING_ERROR_MESSAGE = "The node {0} does not support PRes Chaining operation.";
    private static final String CHAINED_STATION_ERROR_MESSAGE = "POWERLINK network with RMN does not support PRes Chaining operation.";
    private static final String MULTIPLEXING_OPERATION_NOT_SUPPORTED_ERROR = "Currently Multiplexing operation not supported.";

    /**
     * Add new node wizard page.
     */
    private final AddControlledNodeWizardPage addNodePage;

    /**
     * Selected node object. The new node will be added below this node.
     */
    private Node selectedNodeObj;
    private PowerlinkRootNode nodeList;
    private TNodeCollection nodeCollectionModel;

    public CopyNodeWizard(PowerlinkRootNode nodeList, Node selectedNodeObj) {
        if (selectedNodeObj == null) {
            System.err.println("Invalid node selection");
        }

        this.selectedNodeObj = selectedNodeObj;
        this.nodeList = nodeList;
        Object nodeModel = null;
        if (this.selectedNodeObj != null) {
            nodeModel = nodeList.getMN().getNodeModel();
        }

        if (nodeModel == null) {
            System.err.println("The NodeModel is empty!");
        } else {
            if (nodeModel instanceof TNetworkConfiguration) {
                TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
                nodeCollectionModel = net.getNodeCollection();
            }
        }
        setWindowTitle(WINDOW_TITLE);
        addNodePage = new AddControlledNodeWizardPage(nodeCollectionModel);
        // addNodePage.resetWizard();
    }

    private boolean addNodeFirmwareFile(Node newNode) {
        List<FirmwareManager> validFwList = new ArrayList<>();
        if (nodeList != null) {
            if (!nodeList.getCnNodeList().isEmpty()) {
                for (Node cnNode : nodeList.getCnNodeList()) {
                    if (cnNode.getVendorIdValue()
                            .equalsIgnoreCase(newNode.getVendorIdValue())) {
                        if (cnNode.getProductCodeValue().equalsIgnoreCase(
                                newNode.getProductCodeValue())) {
                            if (!cnNode.getValidFirmwareList().isEmpty()) {

                                System.err.println(
                                        "The firmware collection values.."
                                                + cnNode.getValidFirmwareList());
                                for (FirmwareManager fwMngr : cnNode
                                        .getValidFirmwareList()) {
                                    validFwList.add(fwMngr);

                                }
                            }
                        }
                    }

                }

                if (!validFwList.isEmpty()) {
                    MessageDialog dialog = new MessageDialog(null,
                            "Add firmware file", null,
                            "The project contains firmware file for Node '"
                                    + newNode.getNodeIDWithName() + "'."
                                    + " \nDo you wish to add the firmware file? ",
                            MessageDialog.WARNING, new String[] { "Yes", "No" },
                            1);
                    int result = dialog.open();
                    if (result != 0) {
                        return true;
                    }
                    Map<String, FirmwareManager> firmwarelist = new HashMap<>();
                    for (FirmwareManager fwMngr : validFwList) {
                        firmwarelist.put(fwMngr.getUri(), fwMngr);

                    }

                    for (FirmwareManager fw : firmwarelist.values()) {

                        FirmwareManager firmwareMngr = new FirmwareManager(
                                newNode, fw.getFirmwareXddModel(),
                                fw.getFirmwareObjModel());
                        newNode.getNodeFirmwareCollection().put(firmwareMngr,
                                firmwareMngr.getFirmwarefileVersion());
                        fw.updateFirmwareInProjectFile(firmwareMngr, newNode,
                                firmwareMngr.getFirmwareObjModel());
                    }

                }
                return true;
            }
        }
        return false;
    }

    /**
     * Add wizard page
     */
    @Override
    public void addPages() {
        super.addPages();
        addPage(addNodePage);

    }

    /**
     * Move to next page or finish the wizard.
     */
    @Override
    public boolean canFinish() {
        addNodePage.resetWizard();
        return addNodePage.isPageComplete() && true;

    }

    public String getNodeId() {
        String nodeId = StringUtils.EMPTY;
        Object nodeModel = addNodePage.getNode();
        if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            nodeId = cn.getNodeID();
        }
        return nodeId;
    }

    public ProfileBodyDataType getProfileBody(
            ISO15745ProfileContainer xddModel) {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBodyDatatype = profile
                        .getProfileBody();
                if (profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularHead) {
                    return profileBodyDatatype;
                }
            }
        }
        return null;
    }

    private boolean handleStationTypeChanged(int selectionIndex, Node cnNode) {

        int val = ((Integer) selectionIndex).intValue();
        if (val == 1) {
            // Checks the value of PresChaining from the XDD
            // model of MN and CN.

            boolean cnPresChaining = cnNode.getNetworkManagement()
                    .getCnFeaturesOfNode().isDLLCNPResChaining();
            if (!cnPresChaining) {
                // do not allow
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        MessageFormat.format(CNPRES_CHAINING_ERROR_MESSAGE,
                                cnNode.getNodeIDWithName()),
                        cnNode.getProject().getName());
                getContainer().showPage(addNodePage);
                return false;
            }

            List<Node> rmnNodes = cnNode.getPowerlinkRootNode()
                    .getRmnNodeList();
            if (rmnNodes.size() > 0) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        CHAINED_STATION_ERROR_MESSAGE,
                        cnNode.getProject().getName());
                getContainer().showPage(addNodePage);
                return false;
            }
        } else if (val == 2) {
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    MULTIPLEXING_OPERATION_NOT_SUPPORTED_ERROR,
                    cnNode.getProject().getName());
            getContainer().showPage(addNodePage);
            return false;
        }
        return true;

    }

    /**
     * Checks for errors in the wizard pages.
     *
     * @return <code>True</code> if errors present, <code>False</code>
     *         otherwise.
     */
    public boolean hasErrors() {
        return addNodePage.hasErrors();
    }

    /**
     * Complete the Wizard page.
     */
    @Override
    public boolean performFinish() {

        Object nodeObject = addNodePage.getNode();

        return true;
    }
}
