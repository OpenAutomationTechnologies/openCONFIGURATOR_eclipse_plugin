/*******************************************************************************
 * @file   NewPowerlinkNetworkProjectWizard.java
 *
 * @brief  The main wizard interface for creating a new POWERLINK network project.
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
