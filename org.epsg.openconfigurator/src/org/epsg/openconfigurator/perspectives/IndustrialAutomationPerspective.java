package org.epsg.openconfigurator.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.epsg.openconfigurator.editors.IndustrialNetworkEditor;
import org.epsg.openconfigurator.views.IndustrialNetworkView;

/**
 *  This class is meant to serve as an example for how various contributions 
 *  are made to a perspective. Note that some of the extension point id's are
 *  referred to as API constants while others are hardcoded and may be subject 
 *  to change. 
 */
public class IndustrialAutomationPerspective implements IPerspectiveFactory {

	private IPageLayout factory;

	public IndustrialAutomationPerspective() {
		super();
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

	private void addViews() {
		// Creates the overall folder layout. 
		// Note that each new Folder uses a percentage of the remaining EditorArea.
		
		IFolderLayout bottom =
			factory.createFolder(
				"bottomRight", //NON-NLS-1
				IPageLayout.BOTTOM,
				0.75f,
				factory.getEditorArea());
		bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
		bottom.addView("org.eclipse.ui.views.PropertySheet");
		
		IFolderLayout topLeft =
			factory.createFolder(
				"topLeft", //NON-NLS-1
				IPageLayout.LEFT,
				0.25f,
				factory.getEditorArea());
		topLeft.addView("org.eclipse.ui.navigator.ProjectExplorer"); //NON-NLS-1
		topLeft.addView(IndustrialNetworkView.ID); //NON-NLS-1
		topLeft.addView(IndustrialNetworkEditor.ID); //NON-NLS-1

	}

	private void addActionSets() {
		factory.addActionSet(JavaUI.ID_ACTION_SET);
		factory.addActionSet(JavaUI.ID_ELEMENT_CREATION_ACTION_SET);
		factory.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET); //NON-NLS-1
	}

	private void addPerspectiveShortcuts() {
		factory.addPerspectiveShortcut("org.eclipse.team.ui.TeamSynchronizingPerspective"); //NON-NLS-1
		factory.addPerspectiveShortcut("org.eclipse.team.cvs.ui.cvsPerspective"); //NON-NLS-1
		factory.addPerspectiveShortcut("org.eclipse.ui.resourcePerspective"); //NON-NLS-1
	}

	private void addNewWizardShortcuts() {
		factory.addNewWizardShortcut("org.eclipse.team.cvs.ui.newProjectCheckout");//NON-NLS-1
		factory.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");//NON-NLS-1
		factory.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");//NON-NLS-1
	}

	private void addViewShortcuts() {
		factory.addShowViewShortcut("org.eclipse.ant.ui.views.AntView"); //NON-NLS-1
		factory.addShowViewShortcut("org.eclipse.team.ccvs.ui.AnnotateView"); //NON-NLS-1
		factory.addShowViewShortcut("org.eclipse.pde.ui.DependenciesView"); //NON-NLS-1
		factory.addShowViewShortcut("org.eclipse.jdt.junit.ResultView"); //NON-NLS-1
		factory.addShowViewShortcut("org.eclipse.team.ui.GenericHistoryView"); //NON-NLS-1
		factory.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
		factory.addShowViewShortcut(JavaUI.ID_PACKAGES);
		factory.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		factory.addShowViewShortcut(IPageLayout.ID_OUTLINE);
	}

}
