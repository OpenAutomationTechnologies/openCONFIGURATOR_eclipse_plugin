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
import org.epsg.openconfigurator.views.IndustrialNetworkView;
import org.epsg.openconfigurator.views.ObjectDictionaryView;
import org.epsg.openconfigurator.views.ParameterView;
import org.epsg.openconfigurator.views.ProcessImageView;
import org.epsg.openconfigurator.views.mapping.MappingView;

/**
 * The Industrial automation perspective which arranges the views and editors
 * for manipulation the POWERLINK network projects.
 */
public class IndustrialAutomationPerspective implements IPerspectiveFactory {

    private IPageLayout factory;

    public IndustrialAutomationPerspective() {
        super();
    }

    private void addNewWizardShortcuts() {
        factory.addNewWizardShortcut(
                "org.epsg.openconfigurator.newproject.wizard"); // NON-NLS-1
    }

    private void addViews() {
        // Creates the overall folder layout.
        // Note that each new Folder uses a percentage of the remaining
        // EditorArea.
        String editorArea = factory.getEditorArea();
        IFolderLayout topLeft = factory.createFolder("topLeft", // NON-NLS-1
                IPageLayout.LEFT, 0.25f, editorArea);
        topLeft.addView(IPageLayout.ID_PROJECT_EXPLORER);
        topLeft.addView(IndustrialNetworkView.ID);

        IFolderLayout bottomLeft = factory.createFolder("bottomLeft", // NON-NLS-1
                IPageLayout.BOTTOM, 0.25f, "topLeft");
        bottomLeft.addView(IPageLayout.ID_PROP_SHEET);

        IFolderLayout bottomRight = factory.createFolder("bottomRight", // NON-NLS-1
                IPageLayout.BOTTOM, 0.75f, editorArea);
        bottomRight.addView(IConsoleConstants.ID_CONSOLE_VIEW);

        bottomRight.addView(IPageLayout.ID_PROBLEM_VIEW);
        bottomRight.addView(IPageLayout.ID_PROGRESS_VIEW);

        IFolderLayout centralEditorArea = factory.createFolder(
                "centralEditorArea", IPageLayout.LEFT, 0.50f, editorArea); // NON-NLS-1
        centralEditorArea.addPlaceholder(MappingView.ID);
        centralEditorArea.addPlaceholder(ProcessImageView.ID);

        IFolderLayout topRight = factory.createFolder("topRight", // NON-NLS-1
                IPageLayout.RIGHT, 0.75f, editorArea);
        topRight.addPlaceholder(ObjectDictionaryView.ID);
        topRight.addView(ParameterView.ID);
    }

    private void addViewShortcuts() {
        factory.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
        factory.addShowViewShortcut(IPageLayout.ID_PROGRESS_VIEW);
        factory.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
        factory.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);

        factory.addShowViewShortcut(IndustrialNetworkView.ID);
        factory.addShowViewShortcut(ObjectDictionaryView.ID);
        factory.addShowViewShortcut(MappingView.ID);
        factory.addShowViewShortcut(ParameterView.ID);
        factory.addShowViewShortcut(ProcessImageView.ID);
    }

    @Override
    public void createInitialLayout(IPageLayout factory) {
        this.factory = factory;
        addViews();
        addNewWizardShortcuts();
        addViewShortcuts();
    }

}
