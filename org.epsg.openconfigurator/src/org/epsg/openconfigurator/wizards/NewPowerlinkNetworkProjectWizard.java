package org.epsg.openconfigurator.wizards;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class NewPowerlinkNetworkProjectWizard extends Wizard implements INewWizard, IExecutableExtension {

	private WizardNewProjectCreationPage _pageOne;
	private IConfigurationElement _configurationElement;
	
	public NewPowerlinkNetworkProjectWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("New POWERLINK network project");

	}

	@Override
	public boolean performFinish() {
		
		String name = _pageOne.getProjectName();
	    URI location = null;
	    if (!_pageOne.useDefaults()) {
	        location = _pageOne.getLocationURI();
	    }
	 
	    IProject proj = PowerlinkNetworkProjectSupport.createProject(name, location);
	    BasicNewProjectResourceWizard.updatePerspective(_configurationElement);
	    BasicNewProjectResourceWizard.selectAndReveal(proj, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	    
		return true;
	}
	
	@Override
	 public void addPages() {
	 super.addPages();

	 _pageOne = new WizardNewProjectCreationPage("POWERLINK network project wizard");
	 _pageOne.setTitle("POWERLINK network project");
	 _pageOne.setDescription("Creating a new POWERLINK network project");
	 

	 addPage(_pageOne);
	 }

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		_configurationElement = config;
		
	}

}
