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

import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.util.OpenCONFIGURATORProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAutoGenerationSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TGenerator;
import org.epsg.openconfigurator.xmlbinding.projectfile.TKeyValuePair;
import org.epsg.openconfigurator.xmlbinding.projectfile.TPath;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration;

/**
 * @brief The editor page to manipulate the openCONFIGURATOR project.
 *
 * @author Ramakrishnan P
 *
 */
public final class IndustrialNetworkProjectEditorPage extends FormPage {

  private static final String ID = "org.epsg.openconfigurator.editors.IndustrialNetworkProjectEditorPage";

  private static final String AUTOGENERATIONSETTINGS_SECTION_HEADING = "Build Configuration Settings";
  private static final String AUTOGENERATIONSETTINGS_SECTION_HEADING_DESCRIPTION = "Provides the build configuration settings for the project";
  private static final String AUTOGENERATIONSETTINGS_SECTION_ACTIVEGROUP_LABEL = "Active group:";
  private static final String AUTOGENERATIONSETTINGS_SECTION_MODIFY_LABEL = "Modify...";
  private static final String AUTOGENERATIONSETTINGS_SECTION_INFO_LABEL = "Configure the build configuration specific settings:";
  private static final String AUTOGENERATIONSETTINGS_SECTION_ADD_LABEL = "Add...";
  private static final String AUTOGENERATIONSETTINGS_SECTION_EDIT_LABEL = "Edit...";
  private static final String AUTOGENERATIONSETTINGS_SECTION_DELETE_LABEL = "Delete";

  private static final String GENERATOR_SECTION_HEADING = "Generator";
  private static final String GENERATOR_SECTION_HEADING_DESCRIPTION = "Provides the project file generator information";
  private static final String GENERATOR_SECTION_MODIFIED_BY_LABEL = "Modified By:";
  private static final String GENERATOR_SECTION_CREATED_BY_LABEL = "Created By:";
  private static final String GENERATOR_SECTION_MODIFIED_ON_LABEL = "Modified On:";
  private static final String GENERATOR_SECTION_CREATED_ON_LABEL = "Created On:";
  private static final String GENERATOR_SECTION_VERSION_LABEL = "Version:";
  private static final String GENERATOR_SECTION_TOOL_NAME_LABEL = "Tool Name:";
  private static final String GENERATOR_SECTION_VENDOR_NAME_LABEL = "Vendor:";

  private static final String PATH_SECTION_HEADING = "Project Path Settings";
  private static final String PATH_SECTION_HEADING_DESCRIPTION = "Provides the Path Settings";
  private static final String PATH_SECTION_MODIFY_PATH_LIST_HYPERLINK_LABEL = "Modify the available list of paths";
  private static final String PATH_SECTION_ADD_LABEL = "Add...";
  private static final String PATH_SECTION_OUTPUT_PATH_LABEL = "Output path:";

  private static final int FORM_BODY_MARGIN_TOP = 12;
  private static final int FORM_BODY_MARGIN_BOTTOM = 12;
  private static final int FORM_BODY_MARGIN_LEFT = 6;
  private static final int FORM_BODY_MARGIN_RIGHT = 6;
  private static final int FORM_BODY_HORIZONTAL_SPACING = 20;
  private static final int FORM_BODY_VERTICAL_SPACING = 17;
  private static final int FORM_BODY_NUMBER_OF_COLUMNS = 2;

  private boolean controlChange;
  private boolean dirty = false;
  private TableWrapData td;
  private ScrolledForm form;

  private FormToolkit toolkit;

  private IndustrialNetworkProjectEditor editor;
  private OpenCONFIGURATORProject currentProject;

  /** Generator tag */
  private Text generatorToolNameText;
  private Text generatorVendorText;
  private Text generatorVersionText;
  private Text generatorCreatedByText;
  private Text generatorCreatedOnText;
  private Text generatorModifiedOnText;
  private Text generatorModifiedByText;

  /** Project Configuration tag */
  // private Text projectFilePathText;
  private Combo autoGenerationCombo;
  private Table agSettingsTable;
  private Button btnModifyAutoGenerationSettings;
  private Button addSettingsButton;
  private Button editSettingsButton;
  private Button deleteSettingsButton;

  /** Path Setting tag */
  private Combo pathDropDown;
  private ComboViewer pathComboViewer;

  /**
   * @brief Enables the AutoGenerationSettings group if the argument is true, and disables it
   *        otherwise.
   *
   * @param enabled Enables/disable
   */
  private void setEnabledSettingsControls(boolean enabled) {
    agSettingsTable.setEnabled(enabled);
    addSettingsButton.setEnabled(enabled);
    editSettingsButton.setEnabled(enabled);
    deleteSettingsButton.setEnabled(enabled);
  }

  /**
   * @brief Handles the selection events for the AutoGenerationSettings group
   */
  private SelectionAdapter autoGenerationSettingsSelectionAdapter = new SelectionAdapter() {
    @Override
    public void widgetSelected(SelectionEvent e) {
      if (e.widget == autoGenerationCombo) {

        if (autoGenerationCombo.getText() != currentProject.getProjectConfiguration()
            .getActiveAutoGenerationSetting()) {

          Result libApiRes = OpenConfiguratorCore.GetInstance().SetActiveConfiguration(
              editor.getNetworkId(), autoGenerationCombo.getText());
          if (!libApiRes.IsSuccessful()) {
            // Display a dialog to report it to the user
            // TODO: set the combo value back to old one.
            String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
                + libApiRes.GetErrorMessage();
            PluginErrorDialogUtils.displayErrorMessageDialog(getSite().getShell(), errorMessage,
                null);

            System.err.println("SetActiveConfiguration: " + autoGenerationCombo.getText() + " "
                + errorMessage);
            return;
          }

          currentProject.getProjectConfiguration().setActiveAutoGenerationSetting(
              autoGenerationCombo.getText());
          IndustrialNetworkProjectEditorPage.this.setDirty(true);

          IndustrialNetworkProjectEditorPage.this.reloadAutoGenerationSettingsTable();

          if (currentProject.getProjectConfiguration().getActiveAutoGenerationSetting()
              .equalsIgnoreCase(OpenCONFIGURATORProjectUtils.AUTO_GENERATION_SETTINGS_ALL_ID)
              || currentProject.getProjectConfiguration().getActiveAutoGenerationSetting()
                  .equalsIgnoreCase(OpenCONFIGURATORProjectUtils.AUTO_GENERATION_SETTINGS_NONE_ID)) {
            setEnabledSettingsControls(false);
          } else {
            setEnabledSettingsControls(true);
          }
        }

      } else if (e.widget == btnModifyAutoGenerationSettings) {

        ModifyAutoGenerationSettingsDialog magsDialog = new ModifyAutoGenerationSettingsDialog(
            form.getShell(), editor.getNetworkId(), currentProject.getProjectConfiguration());
        magsDialog.open();

        reloadAutoGenerationSettingsCombo();
        updateActiveAutoGenerationSetting();
        if (magsDialog.isDirty())
          IndustrialNetworkProjectEditorPage.this.setDirty(true);

      } else if (e.widget == editSettingsButton) {
        TAutoGenerationSettings activeAgSettings = IndustrialNetworkProjectEditorPage.this
            .getActiveAutoGenerationSetting();

        int[] selectedIndices = agSettingsTable.getSelectionIndices();
        if (selectedIndices.length <= 0) {
          String errorMessage = "No rows selected.";
          PluginErrorDialogUtils
              .displayErrorMessageDialog(getSite().getShell(), errorMessage, null);
          System.err.println(errorMessage);
          return;
        }

        if (selectedIndices.length > 1) {
          String errorMessage = "Multiple rows selection is not supported.";
          PluginErrorDialogUtils
              .displayErrorMessageDialog(getSite().getShell(), errorMessage, null);
          System.err.println(errorMessage);
          return;
        }

        AddEditSettingsDialog addEditSettingsDialog = new AddEditSettingsDialog(form.getShell(),
            activeAgSettings);
        TableItem selectedRow = agSettingsTable.getItem(selectedIndices[0]);
        String currentSelectedSetting = selectedRow.getText(0);
        addEditSettingsDialog.setActiveSettingName(currentSelectedSetting);

        TKeyValuePair newData = OpenCONFIGURATORProjectUtils.getSetting(activeAgSettings,
            currentSelectedSetting);

        if (addEditSettingsDialog.open() == Window.OK) {
          if (addEditSettingsDialog.isDirty()) {

            // Remove the setting from model and library.
            Result libApiRes = OpenConfiguratorCore.GetInstance().RemoveConfigurationSetting(
                editor.getNetworkId(), activeAgSettings.getId(), newData.getName());
            if (!libApiRes.IsSuccessful()) {
              // Display a dialog to report it to the user
              String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
                  + libApiRes.GetErrorMessage();
              PluginErrorDialogUtils.displayErrorMessageDialog(getSite().getShell(), errorMessage,
                  null);
              System.err.println("RemoveConfigurationSetting: " + activeAgSettings.getId() + ":"
                  + newData.getName() + ": " + errorMessage);
              return;
            }

            if (!activeAgSettings.getSetting().remove(newData))
              System.err.println(newData.getName() + " is not available inside the settings list");

            IndustrialNetworkProjectEditorPage.this.setDirty(true);

            TKeyValuePair modifiedData = addEditSettingsDialog.getData();

            libApiRes = OpenConfiguratorCore.GetInstance().CreateConfigurationSetting(
                editor.getNetworkId(), activeAgSettings.getId(), modifiedData.getName(),
                modifiedData.getValue());
            if (!libApiRes.IsSuccessful()) {
              // Display a dialog to report it to the user
              String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
                  + libApiRes.GetErrorMessage();
              PluginErrorDialogUtils.displayErrorMessageDialog(getSite().getShell(), errorMessage,
                  null);
              System.err.println("CreateConfigurationSetting: " + activeAgSettings.getId() + ":"
                  + modifiedData.getName() + ":" + modifiedData.getValue() + ". " + errorMessage);
              return;
            }

            libApiRes = OpenConfiguratorCore.GetInstance().SetConfigurationSettingEnabled(
                editor.getNetworkId(), activeAgSettings.getId(), modifiedData.getName(),
                modifiedData.isEnabled());
            if (!libApiRes.IsSuccessful()) {
              String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
                  + libApiRes.GetErrorMessage();
              PluginErrorDialogUtils.displayErrorMessageDialog(getSite().getShell(), errorMessage,
                  null);
              System.err.println("SetConfigurationSettingEnabled: " + activeAgSettings.getId()
                  + ":" + modifiedData.getName() + ":" + modifiedData.isEnabled() + ". "
                  + errorMessage);
              return;
            }

            activeAgSettings.getSetting().add(modifiedData);
          }

          IndustrialNetworkProjectEditorPage.this.reloadAutoGenerationSettingsTable();
        } else {
          // If cancelled.
        }

      } else if (e.widget == addSettingsButton) {

        TAutoGenerationSettings activeAgSetting = IndustrialNetworkProjectEditorPage.this
            .getActiveAutoGenerationSetting();

        if (activeAgSetting != null) {
          AddEditSettingsDialog addEditSettingsDialog = new AddEditSettingsDialog(form.getShell(),
              activeAgSetting);
          addEditSettingsDialog.setActiveSettingName(null);

          if (addEditSettingsDialog.open() == Window.OK) {
            // User clicked OK; update the label with the input
            TKeyValuePair newSetting = addEditSettingsDialog.getData();

            Result libApiRes = OpenConfiguratorCore.GetInstance().CreateConfigurationSetting(
                editor.getNetworkId(), activeAgSetting.getId(), newSetting.getName(),
                newSetting.getValue());
            if (!libApiRes.IsSuccessful()) {
              // Display a dialog to report it to the user
              String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
                  + libApiRes.GetErrorMessage();
              PluginErrorDialogUtils.displayErrorMessageDialog(getSite().getShell(), errorMessage,
                  null);

              System.err.println("CreateConfigurationSetting: " + activeAgSetting.getId() + ":"
                  + newSetting.getName() + ":" + newSetting.getValue() + ". " + errorMessage);
              return;
            }

            libApiRes = OpenConfiguratorCore.GetInstance().SetConfigurationSettingEnabled(
                editor.getNetworkId(), activeAgSetting.getId(), newSetting.getName(),
                newSetting.isEnabled());
            if (!libApiRes.IsSuccessful()) {
              String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
                  + libApiRes.GetErrorMessage();
              PluginErrorDialogUtils.displayErrorMessageDialog(getSite().getShell(), errorMessage,
                  null);
              System.err.println("SetConfigurationSettingEnabled: " + activeAgSetting.getId() + ":"
                  + newSetting.getName() + ":" + newSetting.isEnabled() + ". " + errorMessage);
              return;
            }

            // Update the model and the table with the newly added value
            List<TKeyValuePair> settingsList = activeAgSetting.getSetting();
            settingsList.add(newSetting);

            IndustrialNetworkProjectEditorPage.this.setDirty(true);

            IndustrialNetworkProjectEditorPage.this.reloadAutoGenerationSettingsTable();
          }

        } else {
          // It got cancelled.
        }
      } else if (e.widget == deleteSettingsButton) {

        TableItem[] selectedItemList = agSettingsTable.getSelection();
        for (TableItem selectedItem : selectedItemList) {
          String selectedItemName = selectedItem.getText(0);
          TAutoGenerationSettings activeAgSetting = IndustrialNetworkProjectEditorPage.this
              .getActiveAutoGenerationSetting();
          if (activeAgSetting != null) {

            List<TKeyValuePair> settingsList = activeAgSetting.getSetting();
            for (TKeyValuePair setting : settingsList) {
              if (setting.getName().equals(selectedItemName)) {

                Result libApiRes = OpenConfiguratorCore.GetInstance().RemoveConfigurationSetting(
                    editor.getNetworkId(), activeAgSetting.getId(), setting.getName());
                if (!libApiRes.IsSuccessful()) {
                  String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
                      + libApiRes.GetErrorMessage();
                  PluginErrorDialogUtils.displayErrorMessageDialog(getSite().getShell(),
                      errorMessage, null);

                  System.err.println("RemoveConfigurationSetting: " + activeAgSetting.getId() + ":"
                      + setting.getName() + " . " + errorMessage);
                  return;
                }

                settingsList.remove(setting);
                IndustrialNetworkProjectEditorPage.this.setDirty(true);
                IndustrialNetworkProjectEditorPage.this.reloadAutoGenerationSettingsTable();
                break;
              }
            }
          }
        }

      } else {
        System.err.println("New widget has registered but not handled." + e.widget.toString());
      }
    }
  };

  /**
   * @brief Handles the selection for the build configuration settings table.
   */
  private SelectionAdapter autoGenerationSettingsTableAdapter = new SelectionAdapter() {
    @Override
    public void widgetSelected(SelectionEvent e) {
      if (e.widget == agSettingsTable) {
        if (e.detail == SWT.CHECK) {
          TableItem[] selectedItemList = agSettingsTable.getSelection();
          for (TableItem selectedItem : selectedItemList) {

            TAutoGenerationSettings activeAgSetting = IndustrialNetworkProjectEditorPage.this
                .getActiveAutoGenerationSetting();
            if (activeAgSetting != null) {
              List<TKeyValuePair> settingsList = activeAgSetting.getSetting();

              for (TKeyValuePair setting : settingsList) {
                if (selectedItem.getText(0).equals(setting.getName())) {

                  Result libApiRes = OpenConfiguratorCore.GetInstance()
                      .SetConfigurationSettingEnabled(editor.getNetworkId(),
                          activeAgSetting.getId(), setting.getName(), !setting.isEnabled());
                  if (!libApiRes.IsSuccessful()) {
                    String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
                        + libApiRes.GetErrorMessage();
                    PluginErrorDialogUtils.displayErrorMessageDialog(getSite().getShell(),
                        errorMessage, null);

                    System.err
                    .println("SetConfigurationSettingEnabled: " + activeAgSetting.getId() + ":"
                        + setting.getName() + ":" + setting.isEnabled() + ". " + errorMessage);
                    // TODO: Display a dialog to report it to the user
                    return;
                  }

                  setting.setEnabled(!setting.isEnabled());
                  IndustrialNetworkProjectEditorPage.this.setDirty(true);
                }
              }
            }

            // IndustrialNetworkProjectEditorPage.this.firePropertyChange(IEditorPart.PROP_INPUT);
          }
        } else if (e.detail == SWT.NONE) {
          // TODO Handle "Delete" button enable/disable.
        }
      }
    }
  };

  public IndustrialNetworkProjectEditorPage(IndustrialNetworkProjectEditor editor,
      final String title) {
    super(editor, ID, title);
    this.editor = editor;
  }

  /**
   * @brief Adds the listener to the controls available in the project editor page.
   */
  private void addListenersToContorls() {
    autoGenerationCombo.addSelectionListener(autoGenerationSettingsSelectionAdapter);
    btnModifyAutoGenerationSettings.addSelectionListener(autoGenerationSettingsSelectionAdapter);
    addSettingsButton.addSelectionListener(autoGenerationSettingsSelectionAdapter);
    editSettingsButton.addSelectionListener(autoGenerationSettingsSelectionAdapter);
    deleteSettingsButton.addSelectionListener(autoGenerationSettingsSelectionAdapter);
    agSettingsTable.addSelectionListener(autoGenerationSettingsTableAdapter);
    pathComboViewer.addSelectionChangedListener(outputPathSelectionListener);
  }

  /**
   * @brief Handles the selection events from the output path combobox.
   */
  private ISelectionChangedListener outputPathSelectionListener = new ISelectionChangedListener() {
    @Override
    public void selectionChanged(SelectionChangedEvent event) {
      IStructuredSelection selection = (IStructuredSelection) event.getSelection();
      if (selection.size() > 0) {
        currentProject.getProjectConfiguration().getPathSettings()
            .setActivePath(((TPath) selection.getFirstElement()).getId());
        setDirty(true);
      }
    }
  };

  /**
   * @brief Create the GUI controls for the Generator in the openCONFIGURATOR project model.
   * @param managedForm The instance of the form editor.
   */
  private void createAutoGenerationSettingsSection(final IManagedForm managedForm) {
    Section sctnAutoGenerationSettings = managedForm.getToolkit().createSection(
        managedForm.getForm().getBody(),
        Section.EXPANDED | Section.DESCRIPTION | Section.TWISTIE | Section.TITLE_BAR);
    managedForm.getToolkit().paintBordersFor(sctnAutoGenerationSettings);
    sctnAutoGenerationSettings.setText(AUTOGENERATIONSETTINGS_SECTION_HEADING);
    sctnAutoGenerationSettings.setDescription(AUTOGENERATIONSETTINGS_SECTION_HEADING_DESCRIPTION);

    Composite clientComposite = toolkit.createComposite(sctnAutoGenerationSettings, SWT.WRAP);
    GridLayout layout = new GridLayout(3, false);
    layout.marginWidth = 2;
    layout.marginHeight = 2;
    clientComposite.setLayout(layout);
    toolkit.paintBordersFor(clientComposite);

    sctnAutoGenerationSettings.setClient(clientComposite);

    Label activeAutoGenerationSettingsLabel = toolkit.createLabel(clientComposite,
        AUTOGENERATIONSETTINGS_SECTION_ACTIVEGROUP_LABEL);
    activeAutoGenerationSettingsLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
        false, 1, 1));
    activeAutoGenerationSettingsLabel
        .setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

    autoGenerationCombo = new Combo(clientComposite, SWT.READ_ONLY);
    autoGenerationCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    toolkit.adapt(autoGenerationCombo, true, true);

    btnModifyAutoGenerationSettings = toolkit.createButton(clientComposite,
        AUTOGENERATIONSETTINGS_SECTION_MODIFY_LABEL, SWT.PUSH);
    GridData gd = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
    btnModifyAutoGenerationSettings.setLayoutData(gd);

    Label dummyLabel = new Label(clientComposite, SWT.WRAP);
    dummyLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
    dummyLabel.setText(AUTOGENERATIONSETTINGS_SECTION_INFO_LABEL);
    toolkit.adapt(dummyLabel, true, true);
    dummyLabel.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

    agSettingsTable = toolkit.createTable(clientComposite, SWT.CHECK | SWT.SINGLE | SWT.BORDER
        | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
    gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 3);
    gd.heightHint = 100;
    agSettingsTable.setLayoutData(gd);
    agSettingsTable.setHeaderVisible(true);
    agSettingsTable.setVisible(true);

    String[] titles = { "Settings Type", "Value" };

    final TableColumn settingsTypeColumn = new TableColumn(agSettingsTable, SWT.NONE);
    settingsTypeColumn.setText(titles[0]);
    final TableColumn valueColumn = new TableColumn(agSettingsTable, SWT.NONE);
    valueColumn.setText(titles[1]);

    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
      agSettingsTable.getColumn(loopIndex).pack();
    }

    agSettingsTable.addListener(SWT.Resize, new Listener() {
      @Override
      public void handleEvent(Event event) {
        int width = agSettingsTable.getClientArea().width - settingsTypeColumn.getWidth();
        valueColumn.setWidth(width);
      }
    });

    addSettingsButton = toolkit.createButton(clientComposite,
        AUTOGENERATIONSETTINGS_SECTION_ADD_LABEL, SWT.PUSH);
    gd = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
    addSettingsButton.setLayoutData(gd);

    editSettingsButton = toolkit.createButton(clientComposite,
        AUTOGENERATIONSETTINGS_SECTION_EDIT_LABEL, SWT.PUSH);
    gd = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
    editSettingsButton.setLayoutData(gd);

    deleteSettingsButton = toolkit.createButton(clientComposite,
        AUTOGENERATIONSETTINGS_SECTION_DELETE_LABEL, SWT.PUSH);
    gd = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
    deleteSettingsButton.setLayoutData(gd);

    toolkit.adapt(agSettingsTable, true, true);
  }

  /**
   * @brief Create contents of the form.
   *
   * @param managedForm The instance of the form
   */
  @Override
  protected void createFormContent(final IManagedForm managedForm) {
    toolkit = managedForm.getToolkit();
    form = managedForm.getForm();
    form.setText("POWERLINK Project");
    Composite body = form.getBody();
    toolkit.decorateFormHeading(form.getForm());
    toolkit.paintBordersFor(body);
    managedForm.setInput(getEditorInput());

    TableWrapLayout layout = new TableWrapLayout();
    layout.topMargin = FORM_BODY_MARGIN_TOP;
    layout.bottomMargin = FORM_BODY_MARGIN_BOTTOM;
    layout.leftMargin = FORM_BODY_MARGIN_LEFT;
    layout.rightMargin = FORM_BODY_MARGIN_RIGHT;
    layout.horizontalSpacing = FORM_BODY_HORIZONTAL_SPACING;
    layout.verticalSpacing = FORM_BODY_VERTICAL_SPACING;
    layout.makeColumnsEqualWidth = true;
    layout.numColumns = FORM_BODY_NUMBER_OF_COLUMNS;
    body.setLayout(layout);

    createAutoGenerationSettingsSection(managedForm);
    createGeneratorWidgets(managedForm);
    createProjectPathSettingsSection(managedForm);
    addListenersToContorls();
  }

  /**
   * @brief Creates the widgets and controls for the {@link TGenerator} model.
   *
   * @param managedForm The parent form.
   */
  private void createGeneratorWidgets(final IManagedForm managedForm) {
    Section sctnGenerator = toolkit.createSection(managedForm.getForm().getBody(), Section.EXPANDED
        | Section.DESCRIPTION | Section.TWISTIE | Section.TITLE_BAR);
    managedForm.getToolkit().paintBordersFor(sctnGenerator);
    sctnGenerator.setText(GENERATOR_SECTION_HEADING);
    sctnGenerator.setDescription(GENERATOR_SECTION_HEADING_DESCRIPTION);

    Composite client = toolkit.createComposite(sctnGenerator, SWT.WRAP);
    GridLayout layout = new GridLayout(2, false);
    layout.marginWidth = 2;
    layout.marginHeight = 2;
    client.setLayout(layout);
    toolkit.paintBordersFor(client);
    sctnGenerator.setClient(client);

    Label generatorvendor = new Label(client, SWT.NONE);
    generatorvendor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    generatorvendor.setText(GENERATOR_SECTION_VENDOR_NAME_LABEL);
    toolkit.adapt(generatorvendor, true, true);
    generatorvendor.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

    generatorVendorText = new Text(client, SWT.NONE | SWT.WRAP);
    generatorVendorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    toolkit.adapt(generatorVendorText, true, true);
    generatorVendorText.setEnabled(false);

    Label generatortoolName = new Label(client, SWT.NONE);
    generatortoolName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    generatortoolName.setText(GENERATOR_SECTION_TOOL_NAME_LABEL);
    toolkit.adapt(generatortoolName, true, true);
    generatortoolName.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

    generatorToolNameText = new Text(client, SWT.NONE | SWT.WRAP);
    generatorToolNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    toolkit.adapt(generatorToolNameText, true, true);
    generatorToolNameText.setEnabled(false);

    Label generatorVersion = new Label(client, SWT.NONE);
    generatorVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    generatorVersion.setText(GENERATOR_SECTION_VERSION_LABEL);
    toolkit.adapt(generatorVersion, true, true);
    generatorVersion.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

    generatorVersionText = new Text(client, SWT.NONE | SWT.WRAP);
    generatorVersionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    toolkit.adapt(generatorVersionText, true, true);
    generatorVersionText.setEnabled(false);

    Label generatorCreatedOn = new Label(client, SWT.NONE);
    generatorCreatedOn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    generatorCreatedOn.setText(GENERATOR_SECTION_CREATED_ON_LABEL);
    toolkit.adapt(generatorCreatedOn, true, true);
    generatorCreatedOn.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

    generatorCreatedOnText = new Text(client, SWT.NONE | SWT.WRAP);
    generatorCreatedOnText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    toolkit.adapt(generatorCreatedOnText, true, true);
    generatorCreatedOnText.setEnabled(false);

    Label generatorModifiedOn = new Label(client, SWT.NONE);
    generatorModifiedOn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    generatorModifiedOn.setText(GENERATOR_SECTION_MODIFIED_ON_LABEL);
    toolkit.adapt(generatorModifiedOn, true, true);
    generatorModifiedOn.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

    generatorModifiedOnText = new Text(client, SWT.NONE | SWT.WRAP);
    generatorModifiedOnText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    toolkit.adapt(generatorModifiedOnText, true, true);
    generatorModifiedOnText.setEnabled(false);

    Label generatorCreatedBy = new Label(client, SWT.NONE);
    generatorCreatedBy.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    generatorCreatedBy.setText(GENERATOR_SECTION_CREATED_BY_LABEL);
    toolkit.adapt(generatorCreatedBy, true, true);
    generatorCreatedBy.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

    generatorCreatedByText = new Text(client, SWT.NONE | SWT.WRAP);
    generatorCreatedByText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    toolkit.adapt(generatorCreatedByText, true, true);
    generatorCreatedByText.setEnabled(false);

    Label generatorModifiedBy = new Label(client, SWT.NONE);
    generatorModifiedBy.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    generatorModifiedBy.setText(GENERATOR_SECTION_MODIFIED_BY_LABEL);
    toolkit.adapt(generatorModifiedBy, true, true);
    generatorModifiedBy.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

    generatorModifiedByText = new Text(client, SWT.NONE | SWT.WRAP);
    generatorModifiedByText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    toolkit.adapt(generatorModifiedByText, true, true);
    generatorModifiedByText.setEnabled(false);
  }

  /**
   * @brief Creates the widgets and controls for the {@link TProjectConfiguration.PathSettings}
   *        model.
   *
   * @param managedForm The parent form.
   */
  private void createProjectPathSettingsSection(final IManagedForm managedForm) {
    Section sctnPathSettings = managedForm.getToolkit().createSection(
        managedForm.getForm().getBody(),
        Section.EXPANDED | Section.DESCRIPTION | Section.TWISTIE | Section.TITLE_BAR);
    managedForm.getToolkit().paintBordersFor(sctnPathSettings);
    sctnPathSettings.setText(PATH_SECTION_HEADING);
    sctnPathSettings.setDescription(PATH_SECTION_HEADING_DESCRIPTION);

    Composite clientComposite = toolkit.createComposite(sctnPathSettings, SWT.WRAP);
    GridLayout layout = new GridLayout(3, false);
    layout.marginHeight = 15;
    layout.marginBottom = 15;
    clientComposite.setLayout(layout);
    toolkit.paintBordersFor(clientComposite);

    sctnPathSettings.setClient(clientComposite);

    Label lblOutputPath = new Label(clientComposite, SWT.NONE);
    lblOutputPath.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    lblOutputPath.setText(PATH_SECTION_OUTPUT_PATH_LABEL);
    toolkit.adapt(lblOutputPath, true, true);
    lblOutputPath.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

    pathDropDown = new Combo(clientComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
    pathDropDown.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    toolkit.adapt(pathDropDown, true, true);

    Button btnModifyOutputPath = new Button(clientComposite, SWT.PUSH);
    btnModifyOutputPath.setText(PATH_SECTION_ADD_LABEL);
    btnModifyOutputPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    toolkit.adapt(btnModifyOutputPath, true, true);
    btnModifyOutputPath.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent event) {

        TPath path = new TPath();
        AddEditTPathDialog pathDialog = new AddEditTPathDialog(form.getShell(), currentProject
            .getProjectConfiguration().getPathSettings(), path);
        if (pathDialog.open() == Window.OK) {
          if (pathDialog.isDirty())
            setDirty(true);
          currentProject.getProjectConfiguration().getPathSettings().getPath().add(path);
          pathComboViewer.refresh();
        }

      }
    });

    pathComboViewer = new ComboViewer(pathDropDown);
    pathComboViewer.setContentProvider(new IStructuredContentProvider() {

      @Override
      public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // TODO Auto-generated method stub
      }

      @Override
      public void dispose() {
        // TODO Auto-generated method stub

      }

      @Override
      public Object[] getElements(Object inputElement) {
        return ((List) inputElement).toArray();
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

    Label dummyLabel = toolkit.createLabel(clientComposite, "");
    dummyLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

    Hyperlink link = toolkit.createHyperlink(clientComposite,
        PATH_SECTION_MODIFY_PATH_LIST_HYPERLINK_LABEL, SWT.RIGHT);
    link.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    toolkit.adapt(link, true, true);
    link.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
    link.addHyperlinkListener(new HyperlinkAdapter() {
      @Override
      public void linkActivated(HyperlinkEvent e) {
        ModifyPathSettingsDialog magsDialog = new ModifyPathSettingsDialog(form.getShell(),
            currentProject.getProjectConfiguration().getPathSettings());
        magsDialog.open(); // Ok or Cancel modifies the value.

        if (magsDialog.isDirty())
          IndustrialNetworkProjectEditorPage.this.setDirty(true);

        pathComboViewer.refresh();
      }
    });

  }

  /**
   * @brief Handles the save actions for the project editor page
   */
  @Override
  public void doSave(IProgressMonitor monitor) {

    if (dirty) {
      editor.reloadEditorContentsFromModel();
    }
    setDirty(false);
    super.doSave(monitor);
  }

  /**
   * @brief Returns the instance of the active auto generations setting node
   *
   * @return TAutoGenerationSettings
   */
  private TAutoGenerationSettings getActiveAutoGenerationSetting() {
    String activeAutoGenerationSetting = currentProject.getProjectConfiguration()
        .getActiveAutoGenerationSetting();
    List<TAutoGenerationSettings> agList = currentProject.getProjectConfiguration()
        .getAutoGenerationSettings();
    for (TAutoGenerationSettings ag : agList) {
      if (ag.getId().equals(activeAutoGenerationSetting)) {
        return ag;
      }
    }
    return null;
  }

  /**
   * @brief Returns the {@link TGenerator} instance from the openCONFIGURATOR project model.
   *
   * @return the generator instance
   */
  private TGenerator getGenerator() {
    return currentProject.getGenerator();
  }

  /**
   * @brief Returns the {@link OpenCONFIGURATORProject} instance available in the editor.
   *
   * @return OpenCONFIGURATORProject instance
   */
  public OpenCONFIGURATORProject getOpenCONFIGURATORProject() {
    // TODO: Remove this not needed for the main editor. It has the instance already.
    return currentProject;
  }

  /**
   * @brief Initializes the project editor page.
   */
  @Override
  public void init(IEditorSite site, IEditorInput input) {
    super.init(site, input);
  }

  /**
   * @brief Updates the {@link TProjectConfiguration} data from the openCONFIGURATOR project to the
   *        UI controls
   */
  private void initProjectConfigurationData() {
    if (currentProject == null) {
      System.err.println("Error initializing the project configuration data");
      return;
    }

    pathComboViewer.setInput(currentProject.getProjectConfiguration().getPathSettings().getPath());

    String activePathId = currentProject.getProjectConfiguration().getPathSettings()
        .getActivePath();
    if (activePathId != null && !activePathId.isEmpty()) {
      TPath path = getTPath(activePathId);
      if (path != null) {
        pathDropDown.setText(path.getId() + " : " + path.getPath());
      }
    }

    if (pathDropDown.getText().isEmpty()) {
      try {
        TPath path = currentProject.getProjectConfiguration().getPathSettings().getPath().get(0);
        pathDropDown.setText(path.getId() + " : " + path.getPath());
      } catch (IndexOutOfBoundsException e) {
        System.err.println("No path is available to set.");
      }

    }

    reloadAutoGenerationSettingsCombo();

    updateActiveAutoGenerationSetting();
    reloadAutoGenerationSettingsTable();

    if (currentProject.getProjectConfiguration().getActiveAutoGenerationSetting()
        .equalsIgnoreCase(OpenCONFIGURATORProjectUtils.AUTO_GENERATION_SETTINGS_ALL_ID)
        || currentProject.getProjectConfiguration().getActiveAutoGenerationSetting()
            .equalsIgnoreCase(OpenCONFIGURATORProjectUtils.AUTO_GENERATION_SETTINGS_NONE_ID)) {
      setEnabledSettingsControls(false);
    } else {
      setEnabledSettingsControls(true);
    }
  }

  /**
   * @brief Returns the Path for the given ID.
   *
   * @param id Any string ID.
   *
   * @return the path or null other wise.
   */
  private TPath getTPath(final String id) {
    List<TPath> pathList = currentProject.getProjectConfiguration().getPathSettings().getPath();
    for (TPath path : pathList) {
      if (path.getId().equalsIgnoreCase(id)) {
        return path;
      }
    }

    return null;
  }

  /**
   * @brief Updates the {@link TGenerator} data from the openCONFIGURATOR project to the UI controls
   */
  private void initProjectGeneratorData() {
    if (getGenerator() == null) {
      System.err.println("Error initializing the project generator data");
      return;
    }

    if (getGenerator().getVendor() != null)
      generatorVendorText.setText(getGenerator().getVendor());

    if (getGenerator().getToolName() != null)
      generatorToolNameText.setText(getGenerator().getToolName());

    if (getGenerator().getToolVersion() != null)
      generatorVersionText.setText(getGenerator().getToolVersion());

    if (getGenerator().getCreatedOn() != null)
      generatorCreatedOnText.setText(getGenerator().getCreatedOn().toString());

    if (getGenerator().getModifiedOn() != null)
      generatorModifiedOnText.setText(getGenerator().getModifiedOn().toString());

    if (getGenerator().getCreatedBy() != null)
      generatorCreatedByText.setText(getGenerator().getCreatedBy());

    if (getGenerator().getModifiedBy() != null)
      generatorModifiedByText.setText(getGenerator().getModifiedBy());
  }

  /**
   * @brief Updates the {@link OpenCONFIGURATORProject} data to the UI controls
   */
  private void intiProjectData() {
    initProjectConfigurationData();
    initProjectGeneratorData();
  }

  /**
   * @brief Checks if the given string available in the list of {@link TAutoGenerationSettings}.
   *
   * @param activeAutoGenerationSetting
   * @return True if the setting is found. False otherwise.
   */
  private boolean isActiveAutoGenerationSettingAvailable(final String activeAutoGenerationSetting) {

    List<TAutoGenerationSettings> agList = currentProject.getProjectConfiguration()
        .getAutoGenerationSettings();
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
   * @brief Checks whether the control has changed or not.
   *
   * @return True if the setting is found. False otherwise.
   */
  private boolean isControlChanged() {
    return controlChange;
  }

  /**
   * @brief Handles it internally using the dirty flag.
   */
  @Override
  public boolean isDirty() {
    return dirty;
  }

  /**
   * @brief Returns true, since the project editor is an editor. otherwise it will be a form.
   */
  @Override
  public boolean isEditor() {
    return true;
  }

  /**
   * @brief Reloads the Auto Generation settings from the model into the combo.
   */
  private void reloadAutoGenerationSettingsCombo() {
    List<String> items = new ArrayList<String>();
    List<TAutoGenerationSettings> agList = currentProject.getProjectConfiguration()
        .getAutoGenerationSettings();
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
   * @brief Re-load the <Setting> tag values from the <AutoGenerationSettings> parent into the table
   */
  private void reloadAutoGenerationSettingsTable() {
    String activeAutoGenerationSetting = currentProject.getProjectConfiguration()
        .getActiveAutoGenerationSetting();
    List<TAutoGenerationSettings> agList = currentProject.getProjectConfiguration()
        .getAutoGenerationSettings();
    for (TAutoGenerationSettings ag : agList) {
      if (ag.getId().equals(activeAutoGenerationSetting)) {

        agSettingsTable.clearAll();
        agSettingsTable.removeAll();

        List<TKeyValuePair> settingsList = ag.getSetting();
        for (TKeyValuePair setting : settingsList) {
          TableItem item = new TableItem(agSettingsTable, SWT.NONE);
          item.setText(0, setting.getName());
          item.setText(1, setting.getValue());
          item.setChecked(setting.isEnabled());
        }
      }
    }

    for (int loopIndex = 0; loopIndex < agSettingsTable.getColumnCount(); loopIndex++) {
      agSettingsTable.getColumn(loopIndex).pack();
    }
  }

  /**
   * Removes the listener to the controls available in the project editor page.
   */
  private void removeListenersToControls() {
    autoGenerationCombo.removeSelectionListener(autoGenerationSettingsSelectionAdapter);
    btnModifyAutoGenerationSettings.removeSelectionListener(autoGenerationSettingsSelectionAdapter);
    addSettingsButton.removeSelectionListener(autoGenerationSettingsSelectionAdapter);
    editSettingsButton.removeSelectionListener(autoGenerationSettingsSelectionAdapter);
    deleteSettingsButton.removeSelectionListener(autoGenerationSettingsSelectionAdapter);
    agSettingsTable.removeSelectionListener(autoGenerationSettingsTableAdapter);
  }

  /**
   * @brief Sets the editor dirty and notifies the base editor with editorDirtoStateChanged signal.
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
   * @brief Updates the openCONFIGURATOR objects instance and the values in the UI.
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
   * @brief Updates the AutoGenerationSettings combo box from the openCONFIGURATOR project model.
   */
  private void updateActiveAutoGenerationSetting() {
    String activeAutoGenerationSetting = currentProject.getProjectConfiguration()
        .getActiveAutoGenerationSetting();

    if (isActiveAutoGenerationSettingAvailable(activeAutoGenerationSetting)) {
      autoGenerationCombo.setText(activeAutoGenerationSetting);
    } else {
      System.err.println("An error occurred in active auto generation setting. "
          + activeAutoGenerationSetting);
      autoGenerationCombo.select(0);
      if (!autoGenerationCombo.getText().isEmpty()) {
        Result libApiRes = OpenConfiguratorCore.GetInstance().SetActiveConfiguration(
            editor.getNetworkId(), autoGenerationCombo.getText());
        if (!libApiRes.IsSuccessful()) {
          // TODO: Display a dialog to report it to the user
          // TODO: set the combo value back to old one.
          System.err.println("SetActiveConfiguration: " + autoGenerationCombo.getText()
              + ". Code: " + libApiRes.GetErrorType() + " Message:" + libApiRes.GetErrorMessage());
        }

        currentProject.getProjectConfiguration().setActiveAutoGenerationSetting(
            autoGenerationCombo.getText());

        IndustrialNetworkProjectEditorPage.this.setDirty(true);

      } else {
        // TODO: No autoGeneration settings are available in the list.
      }
    }
    IndustrialNetworkProjectEditorPage.this.reloadAutoGenerationSettingsTable();
  }
}
