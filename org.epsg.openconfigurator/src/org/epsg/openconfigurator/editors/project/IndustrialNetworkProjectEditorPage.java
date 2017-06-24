/*******************************************************************************
 * @file   IndustrialNetworkProjectEditorPage.java
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

package org.epsg.openconfigurator.editors.project;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.views.IndustrialNetworkView;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAutoGenerationSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TGenerator;
import org.epsg.openconfigurator.xmlbinding.projectfile.TKeyValuePair;
import org.epsg.openconfigurator.xmlbinding.projectfile.TPath;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration.PathSettings;

/**
 * The editor page to manipulate the openCONFIGURATOR project.
 *
 * @author Ramakrishnan P
 *
 */
public final class IndustrialNetworkProjectEditorPage extends FormPage {

    /** Identifier */
    private static final String ID = "org.epsg.openconfigurator.editors.IndustrialNetworkProjectEditorPage";

    /** Editor label and error messages */
    private static final String AUTOGENERATIONSETTINGS_SECTION_HEADING = "Auto Build Configurations";
    private static final String AUTOGENERATIONSETTINGS_SECTION_HEADING_DESCRIPTION = "Provides the build configuration for the project.";
    private static final String AUTOGENERATIONSETTINGS_SECTION_ACTIVEGROUP_LABEL = "Active Configuration:";
    private static final String AUTOGENERATIONSETTINGS_SECTION_MODIFY_LABEL = "Modify...";
    private static final String AUTOGENERATIONSETTINGS_SECTION_INFO_LABEL = "Configure the build configuration specific settings:";
    private static final String AUTOGENERATIONSETTINGS_SECTION_ADD_LABEL = "Add...";
    private static final String AUTOGENERATIONSETTINGS_SECTION_EDIT_LABEL = "Edit...";
    private static final String AUTOGENERATIONSETTINGS_SECTION_DELETE_LABEL = "Delete";

    private static final String GENERATOR_SECTION_HEADING = "Project Information";
    private static final String GENERATOR_SECTION_HEADING_DESCRIPTION = "Provides detailed project information.";
    private static final String GENERATOR_SECTION_MODIFIED_BY_LABEL = "Modified By:";
    private static final String GENERATOR_SECTION_CREATED_BY_LABEL = "Created By:";
    private static final String GENERATOR_SECTION_MODIFIED_ON_LABEL = "Modified On:";
    private static final String GENERATOR_SECTION_CREATED_ON_LABEL = "Created On:";
    private static final String GENERATOR_SECTION_VERSION_LABEL = "Version:";
    private static final String GENERATOR_SECTION_TOOL_NAME_LABEL = "Tool Name:";
    private static final String GENERATOR_SECTION_VENDOR_NAME_LABEL = "Vendor:";

    private static final String PATH_SECTION_HEADING = "Path Settings";
    private static final String PATH_SECTION_HEADING_DESCRIPTION = "Provides the path settings for the project.";
    private static final String PATH_SECTION_OUTPUT_PATH_CONFIGURATION_LABEL = "Path Configuration:";
    private static final String PATH_SECTION_INFO_LABEL = "Configure the specific path settings:";
    private static final String PATHSETTINGS_SECTION_ADD_LABEL = "Add...";
    private static final String PATHSETTINGS_SECTION_EDIT_LABEL = "Edit...";
    private static final String PATHSETTINGS_SECTION_DELETE_LABEL = "Delete";

    private static final String NETWORK_VIEW_SECTION_HEADING = "Views";
    private static final String NETWORK_VIEW_SECTION_HYPERLINK_LABEL = "Show POWERLINK network";

    private static final String NO_ROWS_SELECTED_ERROR = "No rows selected.";
    private static final String MULTIPLE_SELECTION_NOT_ALLOWED_ERROR = "Multiple rows selection is not supported.";
    private static final String NO_LISTENERS_REGISTERED_ERROR = "New widget has registered but not handled.";
    private static final String ERROR_MESSAGE = "{0} generation of all the works.";
    private static final String ERROR_INITIALISATION_FAILED = "Error initializing the project configuration data";
    private static final String FORM_EDITOR_PAGE_TITLE = "POWERLINK Project";

    /**
     * Form size
     */
    private static final int FORM_BODY_MARGIN_TOP = 12;
    private static final int FORM_BODY_MARGIN_BOTTOM = 12;
    private static final int FORM_BODY_MARGIN_LEFT = 6;
    private static final int FORM_BODY_MARGIN_RIGHT = 6;
    private static final int FORM_BODY_HORIZONTAL_SPACING = 20;
    private static final int FORM_BODY_VERTICAL_SPACING = 17;
    private static final int FORM_BODY_NUMBER_OF_COLUMNS = 2;

    private static final String[] CUSTOM_CONFIG_PATH = { "CONFIG_TEXT",
            "CONFIG_BINARY", "CONFIG_CHAR_TEXT", "XML_PROCESS_IMAGE",
            "C_PROCESS_IMAGE", "CSHARP_PROCESS_IMAGE" };

    /**
     * Editor dirty flag for this page.
     */
    private boolean dirty = false;

    /**
     * Scrolled form.
     */
    private ScrolledForm form;

    /**
     * Toolkit for the form editor.
     */
    private FormToolkit toolkit;
    private IndustrialNetworkProjectEditor editor;

    private OpenCONFIGURATORProject currentProject;
    /**
     * Controls for generator tag
     */
    private Text generatorToolNameText;
    private Text generatorVendorText;
    private Text generatorVersionText;
    private Text generatorCreatedByText;
    private Text generatorCreatedOnText;
    private Text generatorModifiedOnText;

    private Text generatorModifiedByText;
    /**
     * Controls for project configuration tag
     */
    private Combo autoGenerationCombo;
    private Table agSettingsTable;
    private Button btnModifyAutoGenerationSettings;
    private Button addSettingsButton;
    private Button editSettingsButton;

    private Button deleteSettingsButton;
    /**
     * Controls for path setting tag
     */
    private Combo pathDropDown;
    private Table pathSettingsTable;
    private ComboViewer pathComboViewer;
    private Button addPathSettingsButton;
    private Button editPathSettingsButton;

    private Button deletePathSettingsButton;
    // private ComboViewer pathConfigurationComboViewer;

    /**
     * Handles the selection events for the AutoGenerationSettings group
     */
    private SelectionAdapter autoGenerationSettingsSelectionAdapter = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent e) {
            if (e.widget == autoGenerationCombo) {

                if (!autoGenerationCombo.getText().equalsIgnoreCase(
                        currentProject.getProjectConfiguration()
                                .getActiveAutoGenerationSetting())) {

                    Result libApiRes = OpenConfiguratorCore.GetInstance()
                            .SetActiveConfiguration(editor.getNetworkId(),
                                    autoGenerationCombo.getText());
                    if (!libApiRes.IsSuccessful()) {
                        // Display a dialog to report it to the user
                        // TODO: set the combo value back to old one.
                        String errorMessage = OpenConfiguratorLibraryUtils
                                .getErrorMessage(libApiRes);
                        System.err.println(autoGenerationCombo.getText() + " "
                                + errorMessage);

                        PluginErrorDialogUtils.showMessageWindow(
                                MessageDialog.ERROR, libApiRes);
                        return;
                    }

                    currentProject.getProjectConfiguration()
                            .setActiveAutoGenerationSetting(
                                    autoGenerationCombo.getText().trim());
                    IndustrialNetworkProjectEditorPage.this.setDirty(true);

                    IndustrialNetworkProjectEditorPage.this
                            .reloadAutoGenerationSettingsTable();

                    // Enable editable settings controls for only other than all
                    // and none which are by default empty.
                    if (currentProject.getProjectConfiguration()
                            .getActiveAutoGenerationSetting().equalsIgnoreCase(
                                    OpenConfiguratorProjectUtils.AUTO_GENERATION_SETTINGS_ALL_ID)
                            || currentProject.getProjectConfiguration()
                                    .getActiveAutoGenerationSetting()
                                    .equalsIgnoreCase(
                                            OpenConfiguratorProjectUtils.AUTO_GENERATION_SETTINGS_NONE_ID)) {
                        setEnabledSettingsControls(false);
                    } else {
                        setEnabledSettingsControls(true);
                    }
                }

            } else if (e.widget == btnModifyAutoGenerationSettings) {

                ModifyAutoGenerationSettingsDialog magsDialog = new ModifyAutoGenerationSettingsDialog(
                        form.getShell(), editor.getNetworkId(),
                        currentProject.getProjectConfiguration());
                magsDialog.open();

                reloadAutoGenerationSettingsCombo();
                updateActiveAutoGenerationSetting();
                if (magsDialog.isDirty()) {
                    IndustrialNetworkProjectEditorPage.this.setDirty(true);
                }

            } else if (e.widget == editSettingsButton) {
                TAutoGenerationSettings activeAgSettings = IndustrialNetworkProjectEditorPage.this
                        .getActiveAutoGenerationSetting();

                int[] selectedIndices = agSettingsTable.getSelectionIndices();
                if (selectedIndices.length <= 0) {
                    System.err.println(
                            IndustrialNetworkProjectEditorPage.NO_ROWS_SELECTED_ERROR);
                    PluginErrorDialogUtils.displayErrorMessageDialog(
                            IndustrialNetworkProjectEditorPage.NO_ROWS_SELECTED_ERROR,
                            new Exception());
                    return;
                }

                if (selectedIndices.length > 1) {
                    System.err.println(
                            IndustrialNetworkProjectEditorPage.MULTIPLE_SELECTION_NOT_ALLOWED_ERROR);
                    PluginErrorDialogUtils.displayErrorMessageDialog(
                            IndustrialNetworkProjectEditorPage.MULTIPLE_SELECTION_NOT_ALLOWED_ERROR,
                            new Exception());
                    return;
                }

                AddEditSettingsDialog addEditSettingsDialog = new AddEditSettingsDialog(
                        form.getShell(), activeAgSettings);
                TableItem selectedRow = agSettingsTable
                        .getItem(selectedIndices[0]);
                String currentSelectedSetting = selectedRow.getText(0);
                addEditSettingsDialog
                        .setActiveSettingName(currentSelectedSetting);

                TKeyValuePair newData = OpenConfiguratorProjectUtils
                        .getSetting(activeAgSettings, currentSelectedSetting);

                if (addEditSettingsDialog.open() == Window.OK) {
                    if (addEditSettingsDialog.isDirty()) {

                        // Remove the setting from model and library.
                        Result libApiRes = OpenConfiguratorCore.GetInstance()
                                .RemoveConfigurationSetting(
                                        editor.getNetworkId(),
                                        activeAgSettings.getId(),
                                        newData.getName());
                        if (!libApiRes.IsSuccessful()) {
                            // Display a dialog to report it to the user
                            String errorMessage = OpenConfiguratorLibraryUtils
                                    .getErrorMessage(libApiRes);
                            System.err.println(activeAgSettings.getId() + ":"
                                    + newData.getName() + ": " + errorMessage);
                            PluginErrorDialogUtils.showMessageWindow(
                                    MessageDialog.ERROR, libApiRes);

                            return;
                        }

                        if (!activeAgSettings.getSetting().remove(newData)) {
                            System.err.println(newData.getName()
                                    + IndustrialNetworkProjectEditorPage.ERROR_MESSAGE);
                        }

                        IndustrialNetworkProjectEditorPage.this.setDirty(true);

                        TKeyValuePair modifiedData = addEditSettingsDialog
                                .getData();

                        libApiRes = OpenConfiguratorCore.GetInstance()
                                .CreateConfigurationSetting(
                                        editor.getNetworkId(),
                                        activeAgSettings.getId(),
                                        modifiedData.getName(),
                                        modifiedData.getValue());
                        if (!libApiRes.IsSuccessful()) {
                            // Display a dialog to report it to the user
                            String errorMessage = OpenConfiguratorLibraryUtils
                                    .getErrorMessage(libApiRes);
                            System.err.println(activeAgSettings.getId() + ":"
                                    + modifiedData.getName() + ":"
                                    + modifiedData.getValue() + ". "
                                    + errorMessage);

                            PluginErrorDialogUtils.showMessageWindow(
                                    MessageDialog.ERROR, libApiRes);
                            return;
                        }

                        libApiRes = OpenConfiguratorCore.GetInstance()
                                .SetConfigurationSettingEnabled(
                                        editor.getNetworkId(),
                                        activeAgSettings.getId(),
                                        modifiedData.getName(),
                                        modifiedData.isEnabled());
                        if (!libApiRes.IsSuccessful()) {
                            String errorMessage = OpenConfiguratorLibraryUtils
                                    .getErrorMessage(libApiRes);
                            System.err.println(activeAgSettings.getId() + ":"
                                    + modifiedData.getName() + ":"
                                    + modifiedData.getValue() + ". "
                                    + errorMessage);

                            System.err.println(activeAgSettings.getId() + ":"
                                    + modifiedData.getName() + ":"
                                    + modifiedData.isEnabled() + ". "
                                    + errorMessage);

                            PluginErrorDialogUtils.showMessageWindow(
                                    MessageDialog.ERROR, libApiRes);
                            return;
                        }

                        activeAgSettings.getSetting().add(modifiedData);
                    }

                    IndustrialNetworkProjectEditorPage.this
                            .reloadAutoGenerationSettingsTable();
                } else {
                    // Add edit dialog setting cancelled.
                    System.err.println("Add Edit setting dialog is canceled.");
                }

            } else if (e.widget == addSettingsButton) {

                TAutoGenerationSettings activeAgSetting = IndustrialNetworkProjectEditorPage.this
                        .getActiveAutoGenerationSetting();

                if (activeAgSetting != null) {
                    AddEditSettingsDialog addEditSettingsDialog = new AddEditSettingsDialog(
                            form.getShell(), activeAgSetting);
                    addEditSettingsDialog.setActiveSettingName(null);

                    if (addEditSettingsDialog.open() == Window.OK) {
                        // User clicked OK; update the label with the input
                        TKeyValuePair newSetting = addEditSettingsDialog
                                .getData();

                        Result libApiRes = OpenConfiguratorCore.GetInstance()
                                .CreateConfigurationSetting(
                                        editor.getNetworkId(),
                                        activeAgSetting.getId(),
                                        newSetting.getName(),
                                        newSetting.getValue());
                        if (!libApiRes.IsSuccessful()) {
                            // Display a dialog to report it to the user
                            String errorMessage = OpenConfiguratorLibraryUtils
                                    .getErrorMessage(libApiRes);

                            System.err.println(activeAgSetting.getId() + ":"
                                    + newSetting.getName() + ":"
                                    + newSetting.getValue() + ". "
                                    + errorMessage);
                            PluginErrorDialogUtils.showMessageWindow(
                                    MessageDialog.ERROR, libApiRes);
                            return;
                        }

                        libApiRes = OpenConfiguratorCore.GetInstance()
                                .SetConfigurationSettingEnabled(
                                        editor.getNetworkId(),
                                        activeAgSetting.getId(),
                                        newSetting.getName(),
                                        newSetting.isEnabled());
                        if (!libApiRes.IsSuccessful()) {
                            String errorMessage = OpenConfiguratorLibraryUtils
                                    .getErrorMessage(libApiRes);
                            System.err.println(activeAgSetting.getId() + ":"
                                    + newSetting.getName() + ":"
                                    + newSetting.isEnabled() + ". "
                                    + errorMessage);

                            PluginErrorDialogUtils.showMessageWindow(
                                    MessageDialog.ERROR, libApiRes);
                            return;
                        }

                        // Update the model and the table with the newly added
                        // value
                        List<TKeyValuePair> settingsList = activeAgSetting
                                .getSetting();
                        settingsList.add(newSetting);

                        IndustrialNetworkProjectEditorPage.this.setDirty(true);

                        IndustrialNetworkProjectEditorPage.this
                                .reloadAutoGenerationSettingsTable();
                    }

                } else {
                    // It got cancelled.
                }
            } else if (e.widget == deleteSettingsButton) {

                int[] selectedIndices = agSettingsTable.getSelectionIndices();
                if (selectedIndices.length <= 0) {
                    System.err.println(
                            IndustrialNetworkProjectEditorPage.NO_ROWS_SELECTED_ERROR);
                    PluginErrorDialogUtils.displayErrorMessageDialog(
                            IndustrialNetworkProjectEditorPage.NO_ROWS_SELECTED_ERROR,
                            new Exception());
                    return;
                }

                TableItem[] selectedItemList = agSettingsTable.getSelection();
                for (TableItem selectedItem : selectedItemList) {
                    String selectedItemName = selectedItem.getText(0);
                    TAutoGenerationSettings activeAgSetting = IndustrialNetworkProjectEditorPage.this
                            .getActiveAutoGenerationSetting();
                    if (activeAgSetting != null) {

                        List<TKeyValuePair> settingsList = activeAgSetting
                                .getSetting();
                        for (TKeyValuePair setting : settingsList) {
                            if (setting.getName().equals(selectedItemName)) {

                                Result libApiRes = OpenConfiguratorCore
                                        .GetInstance()
                                        .RemoveConfigurationSetting(
                                                editor.getNetworkId(),
                                                activeAgSetting.getId(),
                                                setting.getName());
                                if (!libApiRes.IsSuccessful()) {
                                    String errorMessage = OpenConfiguratorLibraryUtils
                                            .getErrorMessage(libApiRes);
                                    System.err.println(activeAgSetting.getId()
                                            + ":" + setting.getName() + " . "
                                            + errorMessage);
                                    PluginErrorDialogUtils.showMessageWindow(
                                            MessageDialog.ERROR, libApiRes);
                                    return;
                                }

                                settingsList.remove(setting);
                                IndustrialNetworkProjectEditorPage.this
                                        .setDirty(true);
                                IndustrialNetworkProjectEditorPage.this
                                        .reloadAutoGenerationSettingsTable();
                                break;
                            }
                        }
                    }
                }

            } else {
                System.err.println(
                        IndustrialNetworkProjectEditorPage.NO_LISTENERS_REGISTERED_ERROR
                                + "." + e.widget.toString());
            }
        }
    };

    /**
     * Handles the selection events for the PathSettings group
     */
    private SelectionAdapter pathSettingsSelectionAdapter = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent e) {
            if (e.widget == pathDropDown) {

                if (pathDropDown.getText().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
                    currentProject.getProjectConfiguration()
                            .setActivePathSetting(
                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID);
                } else if (pathDropDown.getText().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                    currentProject.getProjectConfiguration()
                            .setActivePathSetting(
                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID);
                    PathSettings customPathSettings = new PathSettings();
                    customPathSettings.setId(
                            OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID);

                }

                IndustrialNetworkProjectEditorPage.this.setDirty(true);
                IndustrialNetworkProjectEditorPage.this
                        .updateActivePathSetting();
                IndustrialNetworkProjectEditorPage.this
                        .reloadPathSettingsTable();
            } else if (e.widget == editPathSettingsButton) {
                if (pathDropDown.getText().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
                    TPath path = IndustrialNetworkProjectEditorPage.this
                            .getSelectedTPath();
                    if (!path.getPath().equalsIgnoreCase("output")) {
                        int itemToEdit = IndustrialNetworkProjectEditorPage.this
                                .getSelectedTPathIndex();
                        List<PathSettings> pathSettingsList = currentProject
                                .getProjectConfiguration().getPathSettings();
                        PathSettings pathSettings = new PathSettings();

                        for (PathSettings setPath : pathSettingsList) {
                            if (setPath != null) {
                                if (setPath.getId() != null) {
                                    if (setPath.getId().equalsIgnoreCase(
                                            OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
                                        pathSettings = setPath;
                                    }
                                }
                            }
                        }

                        AddEditTPathDialog addEditPathDialog = new AddEditTPathDialog(
                                form.getShell(), pathSettings, path);
                        if (addEditPathDialog.open() == Window.OK) {
                            TPath newSetting = addEditPathDialog.getData();

                            // Update the model and the table with the newly
                            // added value
                            List<TProjectConfiguration.PathSettings> pathList = currentProject
                                    .getProjectConfiguration()
                                    .getPathSettings();

                            for (TProjectConfiguration.PathSettings pathSet : pathList) {
                                if (pathSet.getId() != null) {
                                    if (pathSet.getId().equalsIgnoreCase(
                                            OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
                                        if (newSetting.isActive() == true) {
                                            for (TPath eachPath : pathSet
                                                    .getPath()) {
                                                eachPath.setActive(false);
                                            }
                                        } else {
                                            if (pathSet.getPath()
                                                    .get(itemToEdit)
                                                    .isActive() == true) {
                                                pathSet.getPath().get(0)
                                                        .setActive(true);
                                            }
                                        }

                                        pathSet.getPath().get(itemToEdit)
                                                .setId(newSetting.getId());
                                        pathSet.getPath().get(itemToEdit)
                                                .setPath(newSetting.getPath());
                                        pathSet.getPath().get(itemToEdit)
                                                .setActive(
                                                        newSetting.isActive());
                                    }
                                }
                            }

                            IndustrialNetworkProjectEditorPage.this
                                    .setDirty(true);

                            IndustrialNetworkProjectEditorPage.this
                                    .reloadPathSettingsTable();
                        }
                    }
                } else if (pathDropDown.getText().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                    TPath path = IndustrialNetworkProjectEditorPage.this
                            .getSelectedTPath();
                    int itemToEdit = IndustrialNetworkProjectEditorPage.this
                            .getSelectedTPathIndex();
                    List<PathSettings> pathSettingsList = currentProject
                            .getProjectConfiguration().getPathSettings();
                    PathSettings pathSettings = new PathSettings();

                    for (PathSettings setPath : pathSettingsList) {
                        if (setPath != null) {
                            if (setPath.getId() != null) {
                                if (setPath.getId().equalsIgnoreCase(
                                        OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                                    pathSettings = setPath;
                                }
                            }
                        }
                    }

                    AddEditCustomTPathDialog addEditPathDialog = new AddEditCustomTPathDialog(
                            form.getShell(), pathSettings, path, true);
                    if (addEditPathDialog.open() == Window.OK) {
                        TPath newSetting = addEditPathDialog.getData();
                        System.err.println("Conf checked.." + addEditPathDialog
                                .isAllOutputpathConfigured());
                        List<TProjectConfiguration.PathSettings> pathList = currentProject
                                .getProjectConfiguration().getPathSettings();
                        System.err.println("Path List.." + pathList);
                        for (TProjectConfiguration.PathSettings pathSet : pathList) {
                            System.err
                                    .println("Path Set ID.." + pathSet.getId());
                            if (pathSet.getId() != null) {
                                if (pathSet.getId().equalsIgnoreCase(
                                        OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                                    pathSet.getPath().get(itemToEdit)
                                            .setId(newSetting.getId());
                                    pathSet.getPath().get(itemToEdit)
                                            .setPath(newSetting.getPath());
                                    if (addEditPathDialog
                                            .isAllOutputpathConfigured()) {
                                        for (TPath pathVal : pathSet
                                                .getPath()) {
                                            pathVal.setPath(
                                                    newSetting.getPath());
                                        }
                                    }
                                }
                            }
                        }

                        IndustrialNetworkProjectEditorPage.this.setDirty(true);

                        IndustrialNetworkProjectEditorPage.this
                                .reloadPathSettingsTable();
                    }
                }
            } else if (e.widget == addPathSettingsButton) {
                System.err.println("Add path setting..");
                if (pathDropDown.getText().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
                    // Open the Add dialog box for All path

                } else if (pathDropDown.getText().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                    // Open the Add dialog box for Custom path
                    List<PathSettings> pathSettingsList = currentProject
                            .getProjectConfiguration().getPathSettings();
                    if (pathSettingsList.size() < CUSTOM_CONFIG_PATH.length) {

                        // If number of paths not exceeds 5
                        PathSettings pathSettings = new PathSettings();
                        pathSettings.setId(
                                OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID);
                        boolean isCustomAvailable = false;

                        for (PathSettings setPath : pathSettingsList) {
                            if (setPath != null) {
                                if (setPath.getId() != null) {
                                    if (setPath.getId().equalsIgnoreCase(
                                            OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                                        pathSettings = setPath;
                                        isCustomAvailable = true;
                                    }
                                }
                            }
                        }

                        if (!isCustomAvailable) {
                            pathSettingsList.add(pathSettings);
                        }

                        AddEditCustomTPathDialog addEditPathDialog = new AddEditCustomTPathDialog(
                                form.getShell(), pathSettings, null, false);
                        if (addEditPathDialog.open() == Window.OK) {
                            TPath newSetting = addEditPathDialog.getData();
                            System.err.println(
                                    "Conf checked.." + addEditPathDialog
                                            .isAllOutputpathConfigured());
                            List<TProjectConfiguration.PathSettings> pathList = currentProject
                                    .getProjectConfiguration()
                                    .getPathSettings();

                            for (TProjectConfiguration.PathSettings pathSet : pathList) {
                                if (pathSet.getId() != null) {
                                    if (pathSet.getId().equalsIgnoreCase(
                                            OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                                        pathSet.getPath().add(newSetting);
                                    } else if (!pathSet.getId()
                                            .equalsIgnoreCase(
                                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
                                        PathSettings paths = new PathSettings();
                                        paths.setId(
                                                OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID);
                                        paths.getPath().add(newSetting);
                                        pathList.add(paths);
                                    }
                                } else {
                                    PathSettings paths = new PathSettings();
                                    paths.setId(
                                            OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID);
                                    paths.getPath().add(newSetting);
                                    pathList.add(paths);
                                }
                                if (pathSet.getId().equalsIgnoreCase(
                                        OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                                    if (addEditPathDialog
                                            .isAllOutputpathConfigured()) {
                                        TPath path = new TPath();
                                        path.setId(CUSTOM_CONFIG_PATH[0]);
                                        path.setPath(
                                                OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                                        TPath binPath = new TPath();
                                        binPath.setId(CUSTOM_CONFIG_PATH[1]);
                                        binPath.setPath(
                                                OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                                        TPath path3 = new TPath();
                                        path3.setId(CUSTOM_CONFIG_PATH[2]);
                                        path3.setPath(
                                                OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                                        TPath path4 = new TPath();
                                        path4.setId(CUSTOM_CONFIG_PATH[3]);
                                        path4.setPath(
                                                OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                                        TPath path5 = new TPath();
                                        path5.setId(CUSTOM_CONFIG_PATH[4]);
                                        path5.setPath(
                                                OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                                        TPath path6 = new TPath();
                                        path6.setId(CUSTOM_CONFIG_PATH[5]);
                                        path6.setPath(
                                                OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                                        if (!newSetting.getId()
                                                .equalsIgnoreCase(
                                                        CUSTOM_CONFIG_PATH[0])) {
                                            pathSet.getPath().add(path);
                                        }
                                        if (!newSetting.getId()
                                                .equalsIgnoreCase(
                                                        CUSTOM_CONFIG_PATH[1])) {
                                            pathSet.getPath().add(binPath);
                                        }
                                        if (!newSetting.getId()
                                                .equalsIgnoreCase(
                                                        CUSTOM_CONFIG_PATH[2])) {
                                            pathSet.getPath().add(path3);
                                        }
                                        if (!newSetting.getId()
                                                .equalsIgnoreCase(
                                                        CUSTOM_CONFIG_PATH[3])) {
                                            pathSet.getPath().add(path4);
                                        }
                                        if (!newSetting.getId()
                                                .equalsIgnoreCase(
                                                        CUSTOM_CONFIG_PATH[4])) {
                                            pathSet.getPath().add(path5);
                                        }
                                        if (!newSetting.getId()
                                                .equalsIgnoreCase(
                                                        CUSTOM_CONFIG_PATH[5])) {
                                            pathSet.getPath().add(path6);
                                        }
                                        for (TPath pathVal : pathSet
                                                .getPath()) {

                                            switch (pathVal.getId()) {
                                                case "CONFIG_TEXT":
                                                    pathVal.setPath(newSetting
                                                            .getPath());
                                                    break;
                                                case "CONFIG_BINARY":
                                                    pathVal.setPath(newSetting
                                                            .getPath());
                                                    break;
                                                case "CONFIG_CHAR_TEXT":
                                                    pathVal.setPath(newSetting
                                                            .getPath());
                                                    break;
                                                case "XML_PROCESS_IMAGE":
                                                    pathVal.setPath(newSetting
                                                            .getPath());
                                                    break;
                                                case "C_PROCESS_IMAGE":
                                                    pathVal.setPath(newSetting
                                                            .getPath());
                                                    break;
                                                case "CSHARP_PROCESS_IMAGE":
                                                    pathVal.setPath(newSetting
                                                            .getPath());
                                                    break;
                                                default:
                                                    System.err.println(
                                                            "Path ID invalid.");
                                                    break;
                                            }

                                        }
                                    }
                                }
                            }

                            IndustrialNetworkProjectEditorPage.this
                                    .setDirty(true);

                            IndustrialNetworkProjectEditorPage.this
                                    .reloadPathSettingsTable();
                        } else {
                            if (pathSettings.getPath().isEmpty()) {
                                pathSettingsList.remove(pathSettings);
                            }
                        }
                    }
                }
            } else if (e.widget == deletePathSettingsButton) {
                if (pathDropDown.getText().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
                    TPath path = IndustrialNetworkProjectEditorPage.this
                            .getSelectedTPath();
                    if (!path.getPath().equalsIgnoreCase("output")) {
                        int itemToDelete = IndustrialNetworkProjectEditorPage.this
                                .getSelectedTPathIndex();
                        List<PathSettings> pathSettingsList = currentProject
                                .getProjectConfiguration().getPathSettings();

                        for (PathSettings setPath : pathSettingsList) {
                            if (setPath != null) {
                                if (setPath.getId() != null) {
                                    if (setPath.getId().equalsIgnoreCase(
                                            OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
                                        if (setPath.getPath().get(itemToDelete)
                                                .isActive() == true) {
                                            setPath.getPath().get(0)
                                                    .setActive(true);
                                        }

                                        setPath.getPath().remove(itemToDelete);
                                        break;
                                    }
                                }
                            }
                        }

                        IndustrialNetworkProjectEditorPage.this.setDirty(true);

                        IndustrialNetworkProjectEditorPage.this
                                .reloadPathSettingsTable();
                    }
                } else if (pathDropDown.getText().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {

                    int itemToDelete = IndustrialNetworkProjectEditorPage.this
                            .getSelectedTPathIndex();
                    List<PathSettings> pathSettingsList = currentProject
                            .getProjectConfiguration().getPathSettings();
                    for (PathSettings setPath : pathSettingsList) {
                        if (setPath != null) {
                            if (setPath.getId() != null) {
                                if (setPath.getId().equalsIgnoreCase(
                                        OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                                    setPath.getPath().remove(itemToDelete);
                                    if (setPath.getPath().isEmpty()) {
                                        pathSettingsList.remove(setPath);
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    IndustrialNetworkProjectEditorPage.this.setDirty(true);

                    IndustrialNetworkProjectEditorPage.this
                            .reloadPathSettingsTable();
                }
            }
        }
    };

    /**
     * Handles the selection for the build configuration settings table.
     */
    private SelectionAdapter autoGenerationSettingsTableAdapter = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent e) {
            int[] selectionIndex = agSettingsTable.getSelectionIndices();
            if (selectionIndex.length > 0) {
                editSettingsButton.setEnabled(true);
                deleteSettingsButton.setEnabled(true);
            } else {
                editSettingsButton.setEnabled(false);
                deleteSettingsButton.setEnabled(false);
            }
            if (e.widget == agSettingsTable) {
                if (e.detail == SWT.CHECK) {
                    TableItem[] selectedItemList = agSettingsTable
                            .getSelection();
                    for (TableItem selectedItem : selectedItemList) {

                        TAutoGenerationSettings activeAgSetting = IndustrialNetworkProjectEditorPage.this
                                .getActiveAutoGenerationSetting();
                        if (activeAgSetting != null) {
                            List<TKeyValuePair> settingsList = activeAgSetting
                                    .getSetting();

                            for (TKeyValuePair setting : settingsList) {
                                if (selectedItem.getText(0)
                                        .equals(setting.getName())) {

                                    Result libApiRes = OpenConfiguratorCore
                                            .GetInstance()
                                            .SetConfigurationSettingEnabled(
                                                    editor.getNetworkId(),
                                                    activeAgSetting.getId(),
                                                    setting.getName(),
                                                    !setting.isEnabled());
                                    if (!libApiRes.IsSuccessful()) {
                                        PluginErrorDialogUtils
                                                .showMessageWindow(
                                                        MessageDialog.ERROR,
                                                        libApiRes);
                                        return;
                                    }

                                    setting.setEnabled(!setting.isEnabled());
                                    IndustrialNetworkProjectEditorPage.this
                                            .setDirty(true);
                                }
                            }
                        }
                    }
                } else if (e.detail == SWT.NONE) {
                    // TODO Handle "Delete" button enable/disable.
                }
            }
        }
    };

    /**
     * Handles the selection for the build configuration path settings table.
     */
    private SelectionAdapter pathSettingsTableAdapter = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent e) {
            int[] selectionIndex = pathSettingsTable.getSelectionIndices();
            if (selectionIndex.length > 0) {

                editPathSettingsButton.setEnabled(true);
                deletePathSettingsButton.setEnabled(true);

            } else {
                editPathSettingsButton.setEnabled(false);
                deletePathSettingsButton.setEnabled(false);
            }
            if (e.widget == pathSettingsTable) {
                if (e.detail == SWT.CHECK) {
                    TableItem[] selectedItemList = pathSettingsTable
                            .getSelection();
                    for (TableItem selectedItem : selectedItemList) {

                        System.err.println("selectedItem...." + selectedItem);

                        TAutoGenerationSettings activeAgSetting = IndustrialNetworkProjectEditorPage.this
                                .getActiveAutoGenerationSetting();

                        if (activeAgSetting != null) {
                            List<TKeyValuePair> settingsList = activeAgSetting
                                    .getSetting();

                            for (TKeyValuePair setting : settingsList) {
                                if (selectedItem.getText(0)
                                        .equals(setting.getName())) {

                                    Result libApiRes = OpenConfiguratorCore
                                            .GetInstance()
                                            .SetConfigurationSettingEnabled(
                                                    editor.getNetworkId(),
                                                    activeAgSetting.getId(),
                                                    setting.getName(),
                                                    !setting.isEnabled());
                                    if (!libApiRes.IsSuccessful()) {
                                        PluginErrorDialogUtils
                                                .showMessageWindow(
                                                        MessageDialog.ERROR,
                                                        libApiRes);
                                        return;
                                    }

                                    setting.setEnabled(!setting.isEnabled());
                                    IndustrialNetworkProjectEditorPage.this
                                            .setDirty(true);
                                }
                            }
                        }
                    }
                } else if (e.detail == SWT.NONE) {
                    // TODO Handle "Delete" button enable/disable.
                }
            }
        }
    };

    /**
     * Handles the selection events from the output path combobox.
     */
    private ISelectionChangedListener outputPathSelectionListener = new ISelectionChangedListener() {
        @Override
        public void selectionChanged(SelectionChangedEvent event) {
            IStructuredSelection selection = (IStructuredSelection) event
                    .getSelection();
            if (selection.size() > 0) {
                List<PathSettings> pathSett = currentProject
                        .getProjectConfiguration().getPathSettings();
                PathSettings pathSettings = null;
                for (PathSettings setPath : pathSett) {
                    pathSettings = setPath;
                    pathSettings
                            .setActivePath(((TPath) selection.getFirstElement())
                                    .getId().trim());
                    setDirty(true);
                }
            }
        }
    };

    public IndustrialNetworkProjectEditorPage(
            IndustrialNetworkProjectEditor editor, final String title) {
        super(editor, IndustrialNetworkProjectEditorPage.ID, title);
        this.editor = editor;
    }

    /**
     * Adds the listener to the controls available in the project editor page.
     */
    private void addListenersToContorls() {
        autoGenerationCombo
                .addSelectionListener(autoGenerationSettingsSelectionAdapter);
        btnModifyAutoGenerationSettings
                .addSelectionListener(autoGenerationSettingsSelectionAdapter);
        addSettingsButton
                .addSelectionListener(autoGenerationSettingsSelectionAdapter);
        editSettingsButton
                .addSelectionListener(autoGenerationSettingsSelectionAdapter);
        deleteSettingsButton
                .addSelectionListener(autoGenerationSettingsSelectionAdapter);
        agSettingsTable
                .addSelectionListener(autoGenerationSettingsTableAdapter);
        addPathSettingsButton
                .addSelectionListener(pathSettingsSelectionAdapter);
        editPathSettingsButton
                .addSelectionListener(pathSettingsSelectionAdapter);
        deletePathSettingsButton
                .addSelectionListener(pathSettingsSelectionAdapter);
        pathDropDown.addSelectionListener(pathSettingsSelectionAdapter);
        pathSettingsTable.addSelectionListener(pathSettingsTableAdapter);
        pathComboViewer
                .addSelectionChangedListener(outputPathSelectionListener);

    }

    /**
     * Create the GUI controls for the Generator in the openCONFIGURATOR project
     * model.
     *
     * @param managedForm The instance of the form editor.
     */
    private void createAutoGenerationSettingsSection(
            final IManagedForm managedForm) {
        Section sctnAutoGenerationSettings = managedForm.getToolkit()
                .createSection(managedForm.getForm().getBody(),
                        ExpandableComposite.EXPANDED | Section.DESCRIPTION
                                | ExpandableComposite.TWISTIE
                                | ExpandableComposite.TITLE_BAR);
        managedForm.getToolkit().paintBordersFor(sctnAutoGenerationSettings);
        sctnAutoGenerationSettings.setText(
                IndustrialNetworkProjectEditorPage.AUTOGENERATIONSETTINGS_SECTION_HEADING);
        sctnAutoGenerationSettings.setDescription(
                IndustrialNetworkProjectEditorPage.AUTOGENERATIONSETTINGS_SECTION_HEADING_DESCRIPTION);

        Composite clientComposite = toolkit
                .createComposite(sctnAutoGenerationSettings, SWT.WRAP);
        GridLayout layout = new GridLayout(3, false);
        layout.marginWidth = 2;
        layout.marginHeight = 2;
        clientComposite.setLayout(layout);
        toolkit.paintBordersFor(clientComposite);

        sctnAutoGenerationSettings.setClient(clientComposite);

        Label activeAutoGenerationSettingsLabel = toolkit.createLabel(
                clientComposite,
                IndustrialNetworkProjectEditorPage.AUTOGENERATIONSETTINGS_SECTION_ACTIVEGROUP_LABEL);
        activeAutoGenerationSettingsLabel.setLayoutData(
                new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        activeAutoGenerationSettingsLabel
                .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

        autoGenerationCombo = new Combo(clientComposite, SWT.READ_ONLY);
        autoGenerationCombo.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(autoGenerationCombo, true, true);

        btnModifyAutoGenerationSettings = toolkit.createButton(clientComposite,
                IndustrialNetworkProjectEditorPage.AUTOGENERATIONSETTINGS_SECTION_MODIFY_LABEL,
                SWT.PUSH);
        GridData gd = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        btnModifyAutoGenerationSettings.setLayoutData(gd);

        Label dummyLabel = new Label(clientComposite, SWT.WRAP);
        dummyLabel.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
        dummyLabel.setText(
                IndustrialNetworkProjectEditorPage.AUTOGENERATIONSETTINGS_SECTION_INFO_LABEL);
        toolkit.adapt(dummyLabel, true, true);
        dummyLabel
                .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

        agSettingsTable = toolkit.createTable(clientComposite,
                SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL
                        | SWT.FULL_SELECTION);
        gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 3);
        gd.heightHint = 100;
        agSettingsTable.setLayoutData(gd);
        agSettingsTable.setHeaderVisible(true);
        agSettingsTable.setVisible(true);

        String[] titles = { "Settings Type", "Value" };

        final TableColumn settingsTypeColumn = new TableColumn(agSettingsTable,
                SWT.NONE);
        settingsTypeColumn.setText(titles[0]);
        final TableColumn valueColumn = new TableColumn(agSettingsTable,
                SWT.NONE);
        valueColumn.setText(titles[1]);

        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
            agSettingsTable.getColumn(loopIndex).pack();
        }
        // Composite tpdoActionsbuttonGroup = new Composite(clientComposite,
        // SWT.NONE);
        // tpdoActionsbuttonGroup.setLayoutData(
        // new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
        // tpdoActionsbuttonGroup.setLayout(new GridLayout(3, false));
        //
        // Button tpdoActionsUpButton = new Button(tpdoActionsbuttonGroup,
        // SWT.NONE);
        // tpdoActionsUpButton.setToolTipText("Move up");
        // // tpdoActionsUpButton.setImage(upArrowImage);

        agSettingsTable.addListener(SWT.Resize, new Listener() {
            @Override
            public void handleEvent(Event event) {
                int width = agSettingsTable.getClientArea().width
                        - settingsTypeColumn.getWidth();
                valueColumn.setWidth(width);
            }
        });

        addSettingsButton = toolkit.createButton(clientComposite,
                IndustrialNetworkProjectEditorPage.AUTOGENERATIONSETTINGS_SECTION_ADD_LABEL,
                SWT.PUSH);
        gd = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        addSettingsButton.setLayoutData(gd);

        editSettingsButton = toolkit.createButton(clientComposite,
                IndustrialNetworkProjectEditorPage.AUTOGENERATIONSETTINGS_SECTION_EDIT_LABEL,
                SWT.PUSH);
        gd = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        editSettingsButton.setLayoutData(gd);

        deleteSettingsButton = toolkit.createButton(clientComposite,
                IndustrialNetworkProjectEditorPage.AUTOGENERATIONSETTINGS_SECTION_DELETE_LABEL,
                SWT.PUSH);
        gd = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
        deleteSettingsButton.setLayoutData(gd);

        toolkit.adapt(agSettingsTable, true, true);
    }

    /**
     * Create contents of the form.
     *
     * @param managedForm The instance of the form
     */
    @Override
    protected void createFormContent(final IManagedForm managedForm) {
        toolkit = managedForm.getToolkit();
        form = managedForm.getForm();
        form.setText(IndustrialNetworkProjectEditorPage.FORM_EDITOR_PAGE_TITLE);
        Composite body = form.getBody();
        toolkit.decorateFormHeading(form.getForm());
        toolkit.paintBordersFor(body);
        managedForm.setInput(getEditorInput());

        TableWrapLayout layout = new TableWrapLayout();
        layout.topMargin = IndustrialNetworkProjectEditorPage.FORM_BODY_MARGIN_TOP;
        layout.bottomMargin = IndustrialNetworkProjectEditorPage.FORM_BODY_MARGIN_BOTTOM;
        layout.leftMargin = IndustrialNetworkProjectEditorPage.FORM_BODY_MARGIN_LEFT;
        layout.rightMargin = IndustrialNetworkProjectEditorPage.FORM_BODY_MARGIN_RIGHT;
        layout.horizontalSpacing = IndustrialNetworkProjectEditorPage.FORM_BODY_HORIZONTAL_SPACING;
        layout.verticalSpacing = IndustrialNetworkProjectEditorPage.FORM_BODY_VERTICAL_SPACING;
        layout.makeColumnsEqualWidth = true;
        layout.numColumns = IndustrialNetworkProjectEditorPage.FORM_BODY_NUMBER_OF_COLUMNS;
        body.setLayout(layout);

        createAutoGenerationSettingsSection(managedForm);
        createGeneratorWidgets(managedForm);
        createProjectPathSettingsSection(managedForm);
        createShortcutToNetworkViewWidgets(managedForm);
        addListenersToContorls();
    }

    /**
     * Creates the widgets and controls for the {@link TGenerator} model.
     *
     * @param managedForm The parent form.
     */
    private void createGeneratorWidgets(final IManagedForm managedForm) {
        Section sctnGenerator = toolkit.createSection(
                managedForm.getForm().getBody(),
                ExpandableComposite.EXPANDED | Section.DESCRIPTION
                        | ExpandableComposite.TWISTIE
                        | ExpandableComposite.TITLE_BAR);
        managedForm.getToolkit().paintBordersFor(sctnGenerator);
        sctnGenerator.setText(
                IndustrialNetworkProjectEditorPage.GENERATOR_SECTION_HEADING);
        sctnGenerator.setDescription(
                IndustrialNetworkProjectEditorPage.GENERATOR_SECTION_HEADING_DESCRIPTION);

        Composite client = toolkit.createComposite(sctnGenerator, SWT.WRAP);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 2;
        layout.marginHeight = 2;
        client.setLayout(layout);
        toolkit.paintBordersFor(client);
        sctnGenerator.setClient(client);

        Label generatorvendor = new Label(client, SWT.NONE);
        generatorvendor.setLayoutData(
                new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        generatorvendor.setText(
                IndustrialNetworkProjectEditorPage.GENERATOR_SECTION_VENDOR_NAME_LABEL);
        toolkit.adapt(generatorvendor, true, true);
        generatorvendor
                .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

        generatorVendorText = new Text(client, SWT.NONE | SWT.WRAP);
        generatorVendorText.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(generatorVendorText, true, true);
        generatorVendorText.setEnabled(false);

        Label generatortoolName = new Label(client, SWT.NONE);
        generatortoolName.setLayoutData(
                new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        generatortoolName.setText(
                IndustrialNetworkProjectEditorPage.GENERATOR_SECTION_TOOL_NAME_LABEL);
        toolkit.adapt(generatortoolName, true, true);
        generatortoolName
                .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

        generatorToolNameText = new Text(client, SWT.NONE | SWT.WRAP);
        generatorToolNameText.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(generatorToolNameText, true, true);
        generatorToolNameText.setEnabled(false);

        Label generatorVersion = new Label(client, SWT.NONE);
        generatorVersion.setLayoutData(
                new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        generatorVersion.setText(
                IndustrialNetworkProjectEditorPage.GENERATOR_SECTION_VERSION_LABEL);
        toolkit.adapt(generatorVersion, true, true);
        generatorVersion
                .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

        generatorVersionText = new Text(client, SWT.NONE | SWT.WRAP);
        generatorVersionText.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(generatorVersionText, true, true);
        generatorVersionText.setEnabled(false);

        Label generatorCreatedOn = new Label(client, SWT.NONE);
        generatorCreatedOn.setLayoutData(
                new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        generatorCreatedOn.setText(
                IndustrialNetworkProjectEditorPage.GENERATOR_SECTION_CREATED_ON_LABEL);
        toolkit.adapt(generatorCreatedOn, true, true);
        generatorCreatedOn
                .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

        generatorCreatedOnText = new Text(client, SWT.NONE | SWT.WRAP);
        generatorCreatedOnText.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(generatorCreatedOnText, true, true);
        generatorCreatedOnText.setEnabled(false);

        Label generatorModifiedOn = new Label(client, SWT.NONE);
        generatorModifiedOn.setLayoutData(
                new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        generatorModifiedOn.setText(
                IndustrialNetworkProjectEditorPage.GENERATOR_SECTION_MODIFIED_ON_LABEL);
        toolkit.adapt(generatorModifiedOn, true, true);
        generatorModifiedOn
                .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

        generatorModifiedOnText = new Text(client, SWT.NONE | SWT.WRAP);
        generatorModifiedOnText.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(generatorModifiedOnText, true, true);
        generatorModifiedOnText.setEnabled(false);

        Label generatorCreatedBy = new Label(client, SWT.NONE);
        generatorCreatedBy.setLayoutData(
                new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        generatorCreatedBy.setText(
                IndustrialNetworkProjectEditorPage.GENERATOR_SECTION_CREATED_BY_LABEL);
        toolkit.adapt(generatorCreatedBy, true, true);
        generatorCreatedBy
                .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

        generatorCreatedByText = new Text(client, SWT.NONE | SWT.WRAP);
        generatorCreatedByText.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(generatorCreatedByText, true, true);
        generatorCreatedByText.setEnabled(false);

        Label generatorModifiedBy = new Label(client, SWT.NONE);
        generatorModifiedBy.setLayoutData(
                new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        generatorModifiedBy.setText(
                IndustrialNetworkProjectEditorPage.GENERATOR_SECTION_MODIFIED_BY_LABEL);
        toolkit.adapt(generatorModifiedBy, true, true);
        generatorModifiedBy
                .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

        generatorModifiedByText = new Text(client, SWT.NONE | SWT.WRAP);
        generatorModifiedByText.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(generatorModifiedByText, true, true);
        generatorModifiedByText.setEnabled(false);
    }

    /**
     * Creates the widgets and controls for the
     * {@link TProjectConfiguration.PathSettings} model.
     *
     * @param managedForm The parent form.
     */
    private void createProjectPathSettingsSection(
            final IManagedForm managedForm) {
        Section sctnPathSettings = managedForm.getToolkit().createSection(
                managedForm.getForm().getBody(),
                ExpandableComposite.EXPANDED | Section.DESCRIPTION
                        | ExpandableComposite.TWISTIE
                        | ExpandableComposite.TITLE_BAR);
        managedForm.getToolkit().paintBordersFor(sctnPathSettings);
        sctnPathSettings.setText(
                IndustrialNetworkProjectEditorPage.PATH_SECTION_HEADING);
        sctnPathSettings.setDescription(
                IndustrialNetworkProjectEditorPage.PATH_SECTION_HEADING_DESCRIPTION);

        Composite clientComposite = toolkit.createComposite(sctnPathSettings,
                SWT.WRAP);
        GridLayout layout = new GridLayout(3, false);
        layout.marginHeight = 15;
        layout.marginBottom = 15;
        clientComposite.setLayout(layout);
        toolkit.paintBordersFor(clientComposite);

        sctnPathSettings.setClient(clientComposite);

        Label lblOutputPath = new Label(clientComposite, SWT.NONE);
        lblOutputPath.setLayoutData(
                new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
        lblOutputPath.setText(
                IndustrialNetworkProjectEditorPage.PATH_SECTION_OUTPUT_PATH_CONFIGURATION_LABEL);
        toolkit.adapt(lblOutputPath, true, true);
        lblOutputPath
                .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

        pathDropDown = new Combo(clientComposite,
                SWT.DROP_DOWN | SWT.READ_ONLY);
        pathDropDown.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(pathDropDown, true, true);
        String[] pathConfigItems = {
                OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID,
                OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID };

        pathDropDown.setItems(pathConfigItems);
        // pathDropDown.setText(
        // OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID);

        Label dummyLabel = new Label(clientComposite, SWT.WRAP);
        dummyLabel.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
        dummyLabel.setText(
                IndustrialNetworkProjectEditorPage.PATH_SECTION_INFO_LABEL);
        toolkit.adapt(dummyLabel, true, true);
        dummyLabel
                .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

        pathSettingsTable = toolkit.createTable(clientComposite,
                SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL
                        | SWT.FULL_SELECTION);

        GridData pst = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 3);
        pst.heightHint = 100;
        pathSettingsTable.setLayoutData(pst);
        pathSettingsTable.setHeaderVisible(true);
        pathSettingsTable.setVisible(true);

        String[] titles = { "Path ID", "Path" };

        final TableColumn idColumn = new TableColumn(pathSettingsTable,
                SWT.NONE);
        idColumn.setText(titles[0]);
        final TableColumn valueColumn = new TableColumn(pathSettingsTable,
                SWT.NONE);
        valueColumn.setText(titles[1]);

        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
            pathSettingsTable.getColumn(loopIndex).pack();
        }

        pathSettingsTable.addListener(SWT.Resize, new Listener() {
            @Override
            public void handleEvent(Event event) {
                int width = pathSettingsTable.getClientArea().width
                        - idColumn.getWidth();
                valueColumn.setWidth(width);
            }
        });

        addPathSettingsButton = toolkit.createButton(clientComposite,
                IndustrialNetworkProjectEditorPage.PATHSETTINGS_SECTION_ADD_LABEL,
                SWT.PUSH);
        pst = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        addPathSettingsButton.setLayoutData(pst);

        editPathSettingsButton = toolkit.createButton(clientComposite,
                IndustrialNetworkProjectEditorPage.PATHSETTINGS_SECTION_EDIT_LABEL,
                SWT.PUSH);
        pst = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
        editPathSettingsButton.setLayoutData(pst);
        editPathSettingsButton.setEnabled(false);

        deletePathSettingsButton = toolkit.createButton(clientComposite,
                IndustrialNetworkProjectEditorPage.PATHSETTINGS_SECTION_DELETE_LABEL,
                SWT.PUSH);
        pst = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
        deletePathSettingsButton.setLayoutData(pst);
        deletePathSettingsButton.setEnabled(false);

        toolkit.adapt(pathSettingsTable, true, true);

        pathComboViewer = new ComboViewer(pathDropDown);
        pathComboViewer.setContentProvider(new IStructuredContentProvider() {

            @Override
            public void dispose() {
                // TODO Auto-generated method stub

            }

            @SuppressWarnings("rawtypes")
            @Override
            public Object[] getElements(Object inputElement) {
                return ((List) inputElement).toArray();
            }

            @Override
            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
                // TODO Auto-generated method stub
            }
        });

        pathComboViewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof TPath) {
                    TPath path = (TPath) element;
                    return (path.getId() + " : " + path.getPath());
                }
                return super.getText(element);
            }
        });
    }

    /**
     * Creates the widgets and controls for the {@link TGenerator} model.
     *
     * @param managedForm The parent form.
     */
    private void createShortcutToNetworkViewWidgets(
            final IManagedForm managedForm) {
        Section sctnGenerator = toolkit.createSection(
                managedForm.getForm().getBody(),
                ExpandableComposite.EXPANDED | Section.DESCRIPTION
                        | ExpandableComposite.TWISTIE
                        | ExpandableComposite.TITLE_BAR);
        managedForm.getToolkit().paintBordersFor(sctnGenerator);
        sctnGenerator.setText(
                IndustrialNetworkProjectEditorPage.NETWORK_VIEW_SECTION_HEADING);

        Composite client = toolkit.createComposite(sctnGenerator, SWT.WRAP);
        GridLayout layout = new GridLayout(4, false);
        layout.marginWidth = 2;
        layout.marginHeight = 2;
        client.setLayout(layout);
        toolkit.paintBordersFor(client);
        sctnGenerator.setClient(client);

        Hyperlink link = toolkit.createHyperlink(client,
                IndustrialNetworkProjectEditorPage.NETWORK_VIEW_SECTION_HYPERLINK_LABEL,
                SWT.RIGHT);
        link.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(link, true, true);
        link.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
        link.addHyperlinkListener(new HyperlinkAdapter() {
            @Override
            public void linkActivated(HyperlinkEvent e) {
                try {
                    IViewPart view = PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow().getActivePage()
                            .showView(IndustrialNetworkView.ID);
                    if (view instanceof IndustrialNetworkView) {
                        IndustrialNetworkView networkView = (IndustrialNetworkView) view;
                        networkView.handleRefresh();
                        networkView.editorActivated(getEditor());
                        networkView.handleRefresh();
                    }

                } catch (PartInitException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

    }

    /**
     * Handles the save actions for the project editor page
     */
    @Override
    public void doSave(IProgressMonitor monitor) {

        if (isDirty()) {
            editor.updateModelToSource();
        }
        setDirty(false);
        super.doSave(monitor);
    }

    /**
     * Returns the instance of the active auto generations setting node
     *
     * @return TAutoGenerationSettings
     */
    private TAutoGenerationSettings getActiveAutoGenerationSetting() {
        String activeAutoGenerationSetting = currentProject
                .getProjectConfiguration().getActiveAutoGenerationSetting();
        List<TAutoGenerationSettings> agList = currentProject
                .getProjectConfiguration().getAutoGenerationSettings();
        for (TAutoGenerationSettings ag : agList) {
            if (ag.getId().equals(activeAutoGenerationSetting)) {
                return ag;
            }
        }
        return null;
    }

    /**
     * Returns the {@link TGenerator} instance from the openCONFIGURATOR project
     * model.
     *
     * @return the generator instance
     */
    private TGenerator getGenerator() {
        return currentProject.getGenerator();
    }

    /**
     * Returns the {@link OpenCONFIGURATORProject} instance available in the
     * editor.
     *
     * @return OpenCONFIGURATORProject instance
     */
    public OpenCONFIGURATORProject getOpenCONFIGURATORProjectFromEditor() {
        // TODO: Remove this not needed for the main editor. It has the instance
        // already.
        return currentProject;
    }

    protected TPath getSelectedTPath() {
        TPath path = new TPath();

        if (pathSettingsTable.getItemCount() > 0) {
            TableItem item = pathSettingsTable
                    .getItem(pathSettingsTable.getSelectionIndex());
            path.setId(item.getText(0));
            path.setPath(item.getText(1));
            if (item.getForeground().equals(
                    Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY))) {
                path.setActive(false);
            } else {
                path.setActive(true);
            }

            return path;
        }
        return null;
    }

    protected int getSelectedTPathIndex() {
        if (pathSettingsTable.getItemCount() > 0) {
            return pathSettingsTable.getSelectionIndex();
        }

        return 0;
    }

    /**
     * Initializes the project editor page.
     */
    @Override
    public void init(IEditorSite site, IEditorInput input) {
        super.init(site, input);
    }

    /**
     * Updates the {@link TProjectConfiguration} data from the openCONFIGURATOR
     * project to the UI controls
     */
    private void initProjectConfigurationData() {
        if (currentProject == null) {
            System.err.println(
                    IndustrialNetworkProjectEditorPage.ERROR_INITIALISATION_FAILED);
            return;
        }
        String[] pathConfigItems = {
                OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID,
                OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID };
        pathDropDown.setItems(pathConfigItems);

        List<PathSettings> pathCollection = currentProject
                .getProjectConfiguration().getPathSettings();

        for (PathSettings path : pathCollection) {
            if (path.getId() == null) {
                path.setId(
                        OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID);
            }
        }

        String activePathSetting = StringUtils.EMPTY;
        if (currentProject.getProjectConfiguration()
                .getActivePathSetting() != null) {
            activePathSetting = currentProject.getProjectConfiguration()
                    .getActivePathSetting();
        } else {
            activePathSetting = OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID;
        }

        if (activePathSetting.equalsIgnoreCase(
                OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
            pathDropDown.setText(
                    OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID);
        } else if (activePathSetting.equalsIgnoreCase(
                OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
            pathDropDown.setText(
                    OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID);
        }

        reloadAutoGenerationSettingsCombo();
        updateActivePathSetting();
        updateActiveAutoGenerationSetting();
        reloadAutoGenerationSettingsTable();
        reloadPathSettingsTable();

        if (currentProject.getProjectConfiguration()
                .getActiveAutoGenerationSetting().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.AUTO_GENERATION_SETTINGS_ALL_ID)
                || currentProject.getProjectConfiguration()
                        .getActiveAutoGenerationSetting().equalsIgnoreCase(
                                OpenConfiguratorProjectUtils.AUTO_GENERATION_SETTINGS_NONE_ID)) {
            setEnabledSettingsControls(false);
        } else {
            setEnabledSettingsControls(true);
        }

    }

    /**
     * Updates the {@link TGenerator} data from the openCONFIGURATOR project to
     * the UI controls
     */
    private void initProjectGeneratorData() {
        if (getGenerator() == null) {
            System.err.println("Error initializing the project generator data");
            return;
        }

        if (getGenerator().getVendor() != null) {
            generatorVendorText.setText(getGenerator().getVendor());
        }

        if (getGenerator().getToolName() != null) {
            generatorToolNameText.setText(getGenerator().getToolName());
        }

        if (getGenerator().getToolVersion() != null) {
            generatorVersionText.setText(getGenerator().getToolVersion());
        }

        if (getGenerator().getCreatedOn() != null) {
            generatorCreatedOnText
                    .setText(getGenerator().getCreatedOn().toString());
        }

        if (getGenerator().getModifiedOn() != null) {
            generatorModifiedOnText
                    .setText(getGenerator().getModifiedOn().toString());
        }

        if (getGenerator().getCreatedBy() != null) {
            generatorCreatedByText.setText(getGenerator().getCreatedBy());
        }

        if (getGenerator().getModifiedBy() != null) {
            generatorModifiedByText.setText(getGenerator().getModifiedBy());
        }
    }

    /**
     * Updates the {@link OpenCONFIGURATORProject} data to the UI controls
     */
    private void intiProjectData() {
        initProjectConfigurationData();
        initProjectGeneratorData();
    }

    /**
     * Checks if the given string available in the list of
     * {@link TAutoGenerationSettings}.
     *
     * @param activeAutoGenerationSetting
     * @return True if the setting is found. False otherwise.
     */
    private boolean isActiveAutoGenerationSettingAvailable(
            final String activeAutoGenerationSetting) {

        List<TAutoGenerationSettings> agList = currentProject
                .getProjectConfiguration().getAutoGenerationSettings();
        for (TAutoGenerationSettings ag : agList) {
            if (!ag.getId().isEmpty()) {
                if (ag.getId().equalsIgnoreCase(activeAutoGenerationSetting)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Handles it internally using the dirty flag.
     */
    @Override
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Returns true, since the project editor is an editor. otherwise it will be
     * a form.
     */
    @Override
    public boolean isEditor() {
        return true;
    }

    /**
     * Reloads the Auto Generation settings from the model into the combo.
     */
    private void reloadAutoGenerationSettingsCombo() {
        List<String> items = new ArrayList<>();
        List<TAutoGenerationSettings> agList = currentProject
                .getProjectConfiguration().getAutoGenerationSettings();
        for (TAutoGenerationSettings ag : agList) {
            if (!ag.getId().isEmpty()) {
                items.add(ag.getId());
            }
        }

        String[] agStringList = new String[items.size()];
        items.toArray(agStringList);

        autoGenerationCombo.setItems(agStringList);
    }

    /**
     * Re-load the Setting tag values from the AutoGenerationSettings parent
     * into the table
     */
    private void reloadAutoGenerationSettingsTable() {
        String activeAutoGenerationSetting = currentProject
                .getProjectConfiguration().getActiveAutoGenerationSetting();
        List<TAutoGenerationSettings> agList = currentProject
                .getProjectConfiguration().getAutoGenerationSettings();
        for (TAutoGenerationSettings ag : agList) {
            if (ag.getId().equals(activeAutoGenerationSetting)) {

                agSettingsTable.clearAll();
                agSettingsTable.removeAll();

                List<TKeyValuePair> settingsList = ag.getSetting();
                for (TKeyValuePair setting : settingsList) {
                    TableItem item = new TableItem(agSettingsTable, SWT.NONE);
                    item.setText(0, setting.getName());
                    item.setText(1, setting.getValue());
                    item.setText(2, setting.getValue());
                    item.setChecked(false);
                    // item.setChecked(setting.isEnabled());

                    if (!setting.isEnabled()) {
                        item.setForeground(Display.getDefault()
                                .getSystemColor(SWT.COLOR_DARK_GRAY));
                    }
                }
            }
        }

        for (int loopIndex = 0; loopIndex < agSettingsTable
                .getColumnCount(); loopIndex++) {
            agSettingsTable.getColumn(loopIndex).pack();
        }

        int[] selectionIndex = agSettingsTable.getSelectionIndices();
        if (selectionIndex.length <= 0) {
            editSettingsButton.setEnabled(false);
            deleteSettingsButton.setEnabled(false);
        } else {
            editSettingsButton.setEnabled(true);
            deleteSettingsButton.setEnabled(true);
        }
    }

    /**
     * Re-load the Setting tag values from the PathSettings parent into the
     * table
     */
    private void reloadPathSettingsTable() {
        List<TProjectConfiguration.PathSettings> pathList = currentProject
                .getProjectConfiguration().getPathSettings();

        for (TProjectConfiguration.PathSettings pathSettingList : pathList) {
            if (pathSettingList != null) {
                if (pathSettingList.getId() != null) {
                    if (pathDropDown.getText().equalsIgnoreCase(
                            OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {

                        if (pathSettingList.getId().equalsIgnoreCase(
                                OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
                            pathSettingsTable.clearAll();
                            pathSettingsTable.removeAll();
                            pathSettingsTable.setEnabled(false);
                            pathSettingList.getPath().clear();
                            TPath path = new TPath();
                            path.setId(CUSTOM_CONFIG_PATH[0]);
                            path.setPath(
                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                            TPath binPath = new TPath();
                            binPath.setId(CUSTOM_CONFIG_PATH[1]);
                            binPath.setPath(
                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                            TPath path3 = new TPath();
                            path3.setId(CUSTOM_CONFIG_PATH[2]);
                            path3.setPath(
                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                            TPath path4 = new TPath();
                            path4.setId(CUSTOM_CONFIG_PATH[3]);
                            path4.setPath(
                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                            TPath path5 = new TPath();
                            path5.setId(CUSTOM_CONFIG_PATH[4]);
                            path5.setPath(
                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                            TPath path6 = new TPath();
                            path6.setId(CUSTOM_CONFIG_PATH[5]);
                            path6.setPath(
                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_VALUE);
                            pathSettingList.getPath().add(path);
                            pathSettingList.getPath().add(binPath);
                            pathSettingList.getPath().add(path3);
                            pathSettingList.getPath().add(path4);
                            pathSettingList.getPath().add(path5);
                            pathSettingList.getPath().add(path6);
                            for (TPath setPath : pathSettingList.getPath()) {
                                TableItem item = new TableItem(
                                        pathSettingsTable, SWT.NONE);
                                item.setText(0, setPath.getId());
                                item.setText(1, setPath.getPath());

                                if (!setPath.isActive()) {
                                    item.setForeground(
                                            Display.getDefault().getSystemColor(
                                                    SWT.COLOR_DARK_GRAY));
                                }
                            }
                        }

                    } else if (pathDropDown.getText().equalsIgnoreCase(
                            OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                        if (pathSettingList.getId().equalsIgnoreCase(
                                OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                            pathSettingsTable.clearAll();
                            pathSettingsTable.removeAll();
                            pathSettingsTable.setEnabled(true);
                            System.err.println("Here...1");

                            for (TPath setPath : pathSettingList.getPath()) {
                                TableItem item = new TableItem(
                                        pathSettingsTable, SWT.NONE);
                                item.setText(0, setPath.getId());
                                item.setText(1, setPath.getPath());
                            }
                        } else {
                            pathSettingsTable.clearAll();
                            pathSettingsTable.removeAll();
                            pathSettingsTable.setEnabled(true);

                        }
                    }
                } else {
                    pathSettingsTable.clearAll();
                    pathSettingsTable.removeAll();
                    pathSettingsTable.setEnabled(true);

                    for (TPath setPath : pathSettingList.getPath()) {
                        TableItem item = new TableItem(pathSettingsTable,
                                SWT.NONE);
                        item.setText(0, setPath.getId());
                        item.setText(1, setPath.getPath());

                        if (pathSettingList.getActivePath() != null) {
                            if (!setPath.getId().equalsIgnoreCase(
                                    pathSettingList.getActivePath())) {
                                item.setForeground(Display.getDefault()
                                        .getSystemColor(SWT.COLOR_DARK_GRAY));
                            }
                        }

                    }
                }
            }
        }
        for (int loopIndex = 0; loopIndex < pathSettingsTable
                .getColumnCount(); loopIndex++) {
            pathSettingsTable.getColumn(loopIndex).pack();
        }

        int[] selectionIndex = pathSettingsTable.getSelectionIndices();
        if ((pathSettingsTable.getItemCount() <= 0)
                || (selectionIndex.length <= 0)) {
            editPathSettingsButton.setEnabled(false);
            deletePathSettingsButton.setEnabled(false);
        } else {
            editPathSettingsButton.setEnabled(true);
            deletePathSettingsButton.setEnabled(true);

        }
        if (pathDropDown.getText().equalsIgnoreCase(
                OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
            addPathSettingsButton.setEnabled(false);
        } else {
            addPathSettingsButton.setEnabled(true);
        }

    }

    /**
     * Removes the listener to the controls available in the project editor
     * page.
     */
    private void removeListenersToControls() {
        autoGenerationCombo.removeSelectionListener(
                autoGenerationSettingsSelectionAdapter);
        btnModifyAutoGenerationSettings.removeSelectionListener(
                autoGenerationSettingsSelectionAdapter);
        addSettingsButton.removeSelectionListener(
                autoGenerationSettingsSelectionAdapter);
        editSettingsButton.removeSelectionListener(
                autoGenerationSettingsSelectionAdapter);
        deleteSettingsButton.removeSelectionListener(
                autoGenerationSettingsSelectionAdapter);
        addPathSettingsButton
                .removeSelectionListener(pathSettingsSelectionAdapter);
        editPathSettingsButton
                .removeSelectionListener(pathSettingsSelectionAdapter);
        deletePathSettingsButton
                .removeSelectionListener(pathSettingsSelectionAdapter);
        pathDropDown.removeSelectionListener(pathSettingsSelectionAdapter);
        agSettingsTable
                .removeSelectionListener(autoGenerationSettingsTableAdapter);
    }

    /**
     * Sets the editor dirty and notifies the base editor with
     * editorDirtoStateChanged signal.
     *
     * @param value state of the editor.
     */
    private void setDirty(boolean value) {
        if (dirty != value) {
            dirty = value;
            getEditor().editorDirtyStateChanged();
        }
    }

    /**
     * Enables the AutoGenerationSettings group if the argument is true, and
     * disables it otherwise.
     *
     * @param enabled Enables/disable the settings group
     */
    private void setEnabledSettingsControls(boolean enabled) {
        agSettingsTable.setEnabled(enabled);
        addSettingsButton.setEnabled(enabled);
        editSettingsButton.setEnabled(enabled);
        deleteSettingsButton.setEnabled(enabled);
        int[] selectedIndices = agSettingsTable.getSelectionIndices();
        System.err.println("Selection indices....." + selectedIndices.length);
        if (selectedIndices.length <= 0) {
            editSettingsButton.setEnabled(false);
            deleteSettingsButton.setEnabled(false);
        }

    }

    /**
     * Updates the openCONFIGURATOR objects instance and the values in the UI.
     *
     * @param project OpenCONFIGURATORProject instance
     */
    public void setOpenCONFIGURATORProject(OpenCONFIGURATORProject project) {
        currentProject = project;
        if (project != null) {
            removeListenersToControls();
            intiProjectData();
            addListenersToContorls();

        }
    }

    /**
     * Updates the AutoGenerationSettings combo box from the openCONFIGURATOR
     * project model.
     */
    private void updateActiveAutoGenerationSetting() {
        String activeAutoGenerationSetting = currentProject
                .getProjectConfiguration().getActiveAutoGenerationSetting();

        if (isActiveAutoGenerationSettingAvailable(
                activeAutoGenerationSetting)) {
            autoGenerationCombo.setText(activeAutoGenerationSetting);
        } else {
            System.err.println(
                    "An error occurred in active auto generation setting. "
                            + activeAutoGenerationSetting);
            autoGenerationCombo.select(0);
            if (!autoGenerationCombo.getText().isEmpty()) {
                Result libApiRes = OpenConfiguratorCore.GetInstance()
                        .SetActiveConfiguration(editor.getNetworkId(),
                                autoGenerationCombo.getText());
                if (!libApiRes.IsSuccessful()) {
                    // TODO: Display a dialog to report it to the user
                    // TODO: set the combo value back to old one.
                    System.err.println("SetActiveConfiguration: "
                            + autoGenerationCombo.getText() + "."
                            + OpenConfiguratorLibraryUtils
                                    .getErrorMessage(libApiRes));
                }

                currentProject.getProjectConfiguration()
                        .setActiveAutoGenerationSetting(
                                autoGenerationCombo.getText().trim());

                IndustrialNetworkProjectEditorPage.this.setDirty(true);

            } else {
                // TODO: No autoGeneration settings are available in the list.
                System.err.println("Auto Generation combo box is Empty.");
            }
        }
        IndustrialNetworkProjectEditorPage.this
                .reloadAutoGenerationSettingsTable();
    }

    protected void updateActivePathSetting() {
        if (pathDropDown.getText().equalsIgnoreCase(
                OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
            List<PathSettings> pathSettingsList = currentProject
                    .getProjectConfiguration().getPathSettings();
            pathDropDown.setText(
                    OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID);
            for (PathSettings setPath : pathSettingsList) {
                if (setPath.getId() != null) {
                    if (setPath.getId().equalsIgnoreCase(
                            OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID)) {
                        currentProject.getProjectConfiguration()
                                .setActivePathSetting(
                                        OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID);
                        break;
                    }
                } else if (currentProject.getProjectConfiguration()
                        .getActivePathSetting() != null) {
                    currentProject.getProjectConfiguration()
                            .setActivePathSetting(
                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID);
                }
            }

        } else if (pathDropDown.getText().equalsIgnoreCase(
                OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {

            List<PathSettings> pathSettingsList = currentProject
                    .getProjectConfiguration().getPathSettings();
            pathDropDown.setText(
                    OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID);
            for (PathSettings setPath : pathSettingsList) {
                if (setPath.getId() != null) {
                    if (setPath.getId().equalsIgnoreCase(
                            OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID)) {
                        currentProject.getProjectConfiguration()
                                .setActivePathSetting(
                                        OpenConfiguratorProjectUtils.PATH_SETTINGS_CUSTOM_PATH_ID);
                        break;
                    }
                } else if (currentProject.getProjectConfiguration()
                        .getActivePathSetting() != null) {
                    currentProject.getProjectConfiguration()
                            .setActivePathSetting(
                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_ALL_PATH_ID);
                }
            }
        }

    }

}
