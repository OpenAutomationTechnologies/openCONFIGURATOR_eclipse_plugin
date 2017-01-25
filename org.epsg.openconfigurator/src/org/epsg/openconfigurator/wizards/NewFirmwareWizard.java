package org.epsg.openconfigurator.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;

public class NewFirmwareWizard extends Wizard {

    private static final String WINDOW_TITLE = "POWERLINK firmware wizard";

    /**
     * Add validateFirmwareWizardPage
     */
    private final ValidateFirmwareWizardPage validateFirmwarePage;

    /**
     * Selected node object. The new node will be added below this node.
     */
    private Node selectedNodeObj;
    private Module selectedModuleObj;
    private PowerlinkRootNode nodeList;
    private TNodeCollection nodeCollectionModel;

    public NewFirmwareWizard(PowerlinkRootNode nodeList, Object selectedObj) {
        if (selectedObj == null) {
            System.err.println("Invalid node selection");
        }
        Object nodeModel = null;
        Object moduleModel = null;
        if (selectedObj instanceof Node) {
            Node node = (Node) selectedObj;
            selectedNodeObj = node;
            if (selectedNodeObj != null) {
                nodeModel = selectedNodeObj.getNodeModel();
            }
            if (nodeModel == null) {
                System.err.println("The NodeModel is empty!");
            } else {
                if (nodeModel instanceof TNetworkConfiguration) {
                    TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
                    nodeCollectionModel = net.getNodeCollection();
                }
            }
        } else if (selectedObj instanceof Module) {
            Module module = (Module) selectedObj;
            selectedModuleObj = module;
            if (selectedModuleObj != null) {
                moduleModel = selectedModuleObj.getModelOfModule();
            }
            if (moduleModel == null) {
                System.err.println("The NodeModel is empty!");
            } else {
                if (moduleModel instanceof TNetworkConfiguration) {
                    TNetworkConfiguration net = (TNetworkConfiguration) moduleModel;
                    nodeCollectionModel = net.getNodeCollection();
                }
            }
        }
        this.nodeList = nodeList;

        setWindowTitle(WINDOW_TITLE);
        validateFirmwarePage = new ValidateFirmwareWizardPage(selectedObj);
    }

    /**
     * Add wizard page
     */
    @Override
    public void addPages() {
        super.addPages();
        addPage(validateFirmwarePage);
    }

    @Override
    public boolean performFinish() {
        // TODO Auto-generated method stub
        return true;
    }

}
