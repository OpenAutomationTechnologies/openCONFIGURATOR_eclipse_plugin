/*******************************************************************************
 * @file   IndustrialAutomationPerspective.java
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

package org.epsg.openconfigurator.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;
import org.epsg.openconfigurator.editors.IndustrialNetworkEditor;
import org.epsg.openconfigurator.views.IndustrialNetworkView;

/**
 * The Industrial automation perspective which arranges the views and editors
 * for manipulation the POWERLINK network projects.
 */
public class IndustrialAutomationPerspective implements IPerspectiveFactory {

    private IPageLayout factory;

    public IndustrialAutomationPerspective() {
        super();
    }

    private void addActionSets() {
        // factory.addActionSet(JavaUI.ID_ACTION_SET);
        // factory.addActionSet(JavaUI.ID_ELEMENT_CREATION_ACTION_SET);
        // factory.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET); //
        // NON-NLS-1
    }

    private void addNewWizardShortcuts() {
        // factory.addNewWizardShortcut("org.eclipse.team.cvs.ui.newProjectCheckout");//NON-NLS-1
        // factory.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");//NON-NLS-1
        // factory.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");//NON-NLS-1
    }

    private void addPerspectiveShortcuts() {
        // factory.addPerspectiveShortcut("org.eclipse.team.ui.TeamSynchronizingPerspective");
        // //NON-NLS-1
        // factory.addPerspectiveShortcut("org.eclipse.team.cvs.ui.cvsPerspective");
        // //NON-NLS-1
        // factory.addPerspectiveShortcut("org.eclipse.ui.resourcePerspective");
        // //NON-NLS-1
    }

    private void addViews() {
        // Creates the overall folder layout.
        // Note that each new Folder uses a percentage of the remaining
        // EditorArea.

        IFolderLayout bottom = factory.createFolder("bottomRight", // NON-NLS-1
                IPageLayout.BOTTOM, 0.75f, factory.getEditorArea());
        bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
        bottom.addView(IPageLayout.ID_PROP_SHEET);

        IFolderLayout topLeft = factory.createFolder("topLeft", // NON-NLS-1
                IPageLayout.LEFT, 0.25f, factory.getEditorArea());
        topLeft.addView(IPageLayout.ID_PROJECT_EXPLORER); // NON-NLS-1
        topLeft.addView(IndustrialNetworkView.ID); // NON-NLS-1
        topLeft.addView(IndustrialNetworkEditor.ID); // NON-NLS-1

        IFolderLayout topRight = factory.createFolder("topRight", // NON-NLS-1
                IPageLayout.RIGHT, 0.25f, factory.getEditorArea());

    }

    private void addViewShortcuts() {
        // factory.addShowViewShortcut("org.eclipse.ant.ui.views.AntView");
        // //NON-NLS-1
        // factory.addShowViewShortcut("org.eclipse.team.ccvs.ui.AnnotateView");
        // //NON-NLS-1
        // factory.addShowViewShortcut("org.eclipse.pde.ui.DependenciesView");
        // //NON-NLS-1
        // factory.addShowViewShortcut("org.eclipse.jdt.junit.ResultView");
        // //NON-NLS-1
        // factory.addShowViewShortcut("org.eclipse.team.ui.GenericHistoryView");
        // //NON-NLS-1
        factory.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
        // factory.addShowViewShortcut(JavaUI.ID_PACKAGES);
        // factory.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
        factory.addShowViewShortcut(IPageLayout.ID_OUTLINE);
    }

    @Override
    public void createInitialLayout(IPageLayout factory) {
        this.factory = factory;
        addViews();
        addActionSets();
        addNewWizardShortcuts();
        addPerspectiveShortcuts();
        addViewShortcuts();
    }

}
