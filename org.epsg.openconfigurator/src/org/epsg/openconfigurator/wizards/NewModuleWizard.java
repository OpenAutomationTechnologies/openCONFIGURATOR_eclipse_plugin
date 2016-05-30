package org.epsg.openconfigurator.wizards;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

public class NewModuleWizard extends Wizard {

    private static final String WINDOW_TITLE = "POWERLINK module wizard";

    /**
     * Add new module wizard page.
     */
    private final AddModuleWizardPage addModulePage;

    /**
     * Add validateXddWizardPage
     */
    private final ValidateXddModuleWizardPage validateXddModulePage;

    private final AddChildModuleWizardPage addChildmodulePage;

    private HeadNodeInterface selectedNodeObj;

    private PowerlinkRootNode rootNode;

    public NewModuleWizard(PowerlinkRootNode nodeList,
            HeadNodeInterface selectedNodeObj) {
        if (selectedNodeObj == null) {
            System.err.println("Invalid Interface selection");
        }
        rootNode = nodeList;
        this.selectedNodeObj = selectedNodeObj;

        setWindowTitle(WINDOW_TITLE);
        addModulePage = new AddModuleWizardPage(selectedNodeObj);
        validateXddModulePage = new ValidateXddModuleWizardPage(
                selectedNodeObj);
        addChildmodulePage = new AddChildModuleWizardPage(selectedNodeObj);
    }

    @Override
    public void addPages() {
        super.addPages();
        addPage(addModulePage);
        addPage(validateXddModulePage);
        addPage(addChildmodulePage);
        validateXddModulePage.setPreviousPage(addModulePage);
        addChildmodulePage.setPreviousPage(validateXddModulePage);

    }

    @Override
    public boolean canFinish() {
        if (getContainer().getCurrentPage() == addModulePage) {
            return false;
        } else if (getContainer().getCurrentPage() == validateXddModulePage) {
            return false;
        } else if (getContainer().getCurrentPage() == addChildmodulePage) {
            boolean canFnsh = validateXddModulePage.isPageComplete()
                    && addModulePage.isPageComplete()
                    && addChildmodulePage.isPageComplete();
            System.err.println("Can Finish ... " + canFnsh);
            return validateXddModulePage.isPageComplete()
                    && addModulePage.isPageComplete()
                    && addChildmodulePage.isPageComplete();

        }
        return true;
    }

    @Override
    public boolean performFinish() {
        Object moduleObject = addModulePage.getModulemodel();
        Path xdcPath = validateXddModulePage.getNodeConfigurationPath();
        int position = addChildmodulePage.getPosition();
        int address = addChildmodulePage.getAddress();
        boolean enabled = addChildmodulePage.isEnabled();

        ISO15745ProfileContainer xddModel = null;
        try {
            xddModel = XddMarshaller.unmarshallXDDFile(xdcPath.toFile());
        } catch (FileNotFoundException | UnsupportedEncodingException
                | JAXBException | SAXException
                | ParserConfigurationException e2) {
            if ((e2.getMessage() != null) && !e2.getMessage().isEmpty()) {
                validateXddModulePage.getErrorStyledText(e2.getMessage());
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        e2.getMessage(), "");
            } else if ((e2.getCause() != null)
                    && (e2.getCause().getMessage() != null)
                    && !e2.getCause().getMessage().isEmpty()) {
                validateXddModulePage
                        .getErrorStyledText(e2.getCause().getMessage());
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        e2.getCause().getMessage(),
                        selectedNodeObj.getNode().getNetworkId());
            }
            e2.printStackTrace();
            return false;
        }

        if (moduleObject instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleObject;
            module.setPathToXDC(xdcPath.toString());
            module.setPosition(BigInteger.valueOf(position));
            module.setEnabled(enabled);
            module.setAddress(BigInteger.valueOf(address));
        } else {
            System.err.println("Invalid Module model");
        }

        Module newModule = new Module(rootNode,
                selectedNodeObj.getNode().getProjectXml(), moduleObject,
                selectedNodeObj.getNode(), xddModel, selectedNodeObj);

        try {
            OpenConfiguratorProjectUtils
                    .importModuleConfigurationFile(newModule);
        } catch (IOException e1) {

            e1.printStackTrace();
        }

        // TODO: Update the added module into library.

        selectedNodeObj.getModuleCollection()
                .put(new Integer(newModule.getPosition()), newModule);

        System.err.println("Module collection values ... "
                + selectedNodeObj.getModuleCollection().values());

        try {
            OpenConfiguratorProjectUtils.addModuleNode(
                    selectedNodeObj.getNode(), selectedNodeObj, newModule);
        } catch (JDOMException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            selectedNodeObj.getNode().getProject().refreshLocal(
                    IResource.DEPTH_INFINITE, new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
        }

        return true;
    }

}
