/*******************************************************************************
 * @file   ModifyAutoGenerationSettingsDialog.java
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

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.util.OpenCONFIGURATORProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAutoGenerationSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration;

/**
 * @brief A dialog to modify the list of auto generation IDs.
 *
 * @author Ramakrishnan P
 *
 */
public final class ModifyAutoGenerationSettingsDialog extends TitleAreaDialog {

  /**
   *
   * @brief A cell modifier is used to access the {@link TAutoGenerationSettings} data model from a
   *        cell editor.
   *
   * @author Ramakrishnan P
   *
   */
  private class AutoGenerationSettingsCellModifier implements ICellModifier {

    private Viewer viewer;

    public AutoGenerationSettingsCellModifier(Viewer viewer) {
      this.viewer = viewer;
    }

    @Override
    public boolean canModify(Object element, String property) {

      if (ModifyAutoGenerationSettingsDialog.NAME.equals(property)) {

        if (element instanceof Item)
          element = ((Item) element).getData();

        TAutoGenerationSettings ag = (TAutoGenerationSettings) element;
        if (ag.getId().equalsIgnoreCase(
            OpenCONFIGURATORProjectUtils.AUTO_GENERATION_SETTINGS_ALL_ID)
            || ag.getId().equalsIgnoreCase(
                OpenCONFIGURATORProjectUtils.AUTO_GENERATION_SETTINGS_NONE_ID)
                || ag.getId().equalsIgnoreCase(projectConfiguration.getActiveAutoGenerationSetting())) {
          return false;
        }
      }

      return true;
    }

    @Override
    public Object getValue(Object element, String property) {
      TAutoGenerationSettings ag = (TAutoGenerationSettings) element;
      if (ModifyAutoGenerationSettingsDialog.NAME.equals(property))
        return ag.getId();
      return null;
    }

    @Override
    public void modify(Object element, String property, Object value) {

      if (element instanceof Item)
        element = ((Item) element).getData();

      // Use if needed to change the input value to uppercase
      // value = ((String) value).toUpperCase();

      TAutoGenerationSettings ag = (TAutoGenerationSettings) element;

      if (ModifyAutoGenerationSettingsDialog.NAME.equals(property)) {
        String oldAgSettingId = ag.getId();
        Result libApiRes = OpenConfiguratorCore.GetInstance().ReplaceConfigurationName(networkID,
            oldAgSettingId, (String) value);
        if (!libApiRes.IsSuccessful()) {
          String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
              + libApiRes.GetErrorMessage();
          PluginErrorDialogUtils.displayErrorMessageDialog(getShell(), errorMessage, null);

          System.err.println("ReplaceConfigurationName failed." + oldAgSettingId + ":"
              + (String) value + ". " + errorMessage);
          return;
        }

        ag.setId((String) value);
        dirty = true;

        // If Active setting is same as the element to be modified.. update the active setting also.
        if (oldAgSettingId.equalsIgnoreCase(projectConfiguration.getActiveAutoGenerationSetting())) {
          libApiRes = OpenConfiguratorCore.GetInstance().SetActiveConfiguration(networkID,
              ag.getId());
          if (!libApiRes.IsSuccessful()) {
            String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
                + libApiRes.GetErrorMessage();
            PluginErrorDialogUtils.displayErrorMessageDialog(getShell(), errorMessage, null);

            System.err.println("SetActiveConfiguration failed. " + errorMessage);
            return;
          }

          projectConfiguration.setActiveAutoGenerationSetting(ag.getId());
          dirty = true;
        }
      }

      // Force the viewer to refresh
      viewer.refresh();

    }
  }

  /**
   * @brief A content provider class for the {@link TAutoGenerationSettings} model.
   *
   * @author Ramakrishnan P
   */
  class AutoGenerationSettingsContentProvider implements IStructuredContentProvider {
    /**
     * Disposes any created resources
     */
    @Override
    public void dispose() {
      // Do nothing
    }

    /**
     * Returns the Person objects
     */
    @Override
    public Object[] getElements(Object inputElement) {
      return ((List) inputElement).toArray();
    }

    /**
     * Called when the input changes
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
      // Ignore
    }
  }

  private static final String DIALOG_TITLE = "Build Configuration Groups";
  private static final String DIALOG_DEFAULT_MESSAGE = "Configure the build configuration settings group";
  private static final String NAME = "Group Name";
  private static final String ADD_BUTTON_LABEL = "Add";
  private static final String DELETE_BUTTON_LABEL = "Delete";

  private static final String SETTINGS_ID_EMPTY_VALUES_NOT_ALLOWED = "No empty values are allowed";

  private TableViewer tableViewer;
  private Table table;
  private Button deleteSettingsButton;

  private String networkID;
  private TProjectConfiguration projectConfiguration;
  private boolean dirty = false;

  /**
   * @brief Creates the Dialog instance
   *
   * @param[in] parentShell The parent shell.
   * @param[in,out] projectConfiguration The {@link TProjectConfiguration} model instance.
   */
  public ModifyAutoGenerationSettingsDialog(Shell parentShell, final String networkID,
      TProjectConfiguration projectConfiguration) {
    super(parentShell);
    this.networkID = networkID;
    this.projectConfiguration = projectConfiguration;
  }

  /**
   * @brief Create contents of the footer button bar.
   *
   * @param[in] parent
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    // createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
  }

  /**
   * @brief Create contents of the dialog.
   *
   * @param[in] parent
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    setTitle(DIALOG_TITLE);
    setMessage(DIALOG_DEFAULT_MESSAGE);

    Composite container = new Composite(parent, SWT.NONE);
    container.setLayoutData(new GridData(GridData.FILL_BOTH));
    container.setLayout(new GridLayout(2, false));

    // Add the TableViewer
    tableViewer = new TableViewer(container, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

    tableViewer.setContentProvider(new AutoGenerationSettingsContentProvider());
    tableViewer.setInput(projectConfiguration.getAutoGenerationSettings());

    table = tableViewer.getTable();
    GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
    gd.heightHint = 75;
    table.setLayoutData(gd);

    TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
    final TableColumn nameColumn = viewerColumn.getColumn();
    nameColumn.setText(NAME);

    viewerColumn.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        TAutoGenerationSettings ag = (TAutoGenerationSettings) element;
        return ag.getId();
      }
    });

    tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        ISelection s = event.getSelection();

        if (s instanceof IStructuredSelection && ((IStructuredSelection) s).size() == 1) {
          Object object = ((IStructuredSelection) s).getFirstElement();

          TAutoGenerationSettings ag = (TAutoGenerationSettings) object;

          // If the current selection ID = defaultOutputPath then disable edit/delete button
          if (ag.getId().equalsIgnoreCase(
              OpenCONFIGURATORProjectUtils.AUTO_GENERATION_SETTINGS_ALL_ID)
              || ag.getId().equalsIgnoreCase(
                  OpenCONFIGURATORProjectUtils.AUTO_GENERATION_SETTINGS_NONE_ID)
              || ag.getId().equalsIgnoreCase(projectConfiguration.getActiveAutoGenerationSetting())) {
            deleteSettingsButton.setEnabled(false);
          } else {
            deleteSettingsButton.setEnabled(true);
          }
        } else {
          System.err.println("Selection should be an event of structured selection.");
        }
      }
    });

    for (int i = 0, n = table.getColumnCount(); i < n; i++) {
      table.getColumn(i).pack();
    }
    table.setHeaderVisible(true);
    table.setLinesVisible(true);

    // Add a button to create the new person
    Button btnNewAutoGeneration = new Button(container, SWT.PUSH);
    btnNewAutoGeneration.setText(ADD_BUTTON_LABEL);
    gd = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
    btnNewAutoGeneration.setLayoutData(gd);

    btnNewAutoGeneration.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent event) {

        String newAutoGenerationSettingsID = newAutoGenerationSettingsID();
        Result libApiRes = OpenConfiguratorCore.GetInstance().CreateConfiguration(networkID,
            newAutoGenerationSettingsID);
        if (!libApiRes.IsSuccessful()) {
          // Report to the user about the error from the library
          String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
              + libApiRes.GetErrorMessage();
          PluginErrorDialogUtils.displayErrorMessageDialog(getShell(), errorMessage, null);

          System.err.println("AddConfiguration '" + newAutoGenerationSettingsID + "' fails. "
              + errorMessage);
          return;
        }

        TAutoGenerationSettings ag = new TAutoGenerationSettings();
        ag.setId(newAutoGenerationSettingsID);
        // TODO: Add All default <Settings> tag here. Do we need?
        projectConfiguration.getAutoGenerationSettings().add(0, ag);
        // projectConfiguration.getAutoGenerationSettings().add(ag);
        dirty = true;
        tableViewer.refresh();
      }
    });

    deleteSettingsButton = new Button(container, SWT.PUSH);
    deleteSettingsButton.setText(DELETE_BUTTON_LABEL);
    gd = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
    deleteSettingsButton.setLayoutData(gd);
    deleteSettingsButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent event) {
        removeAutoGenerationSetting(table.getSelectionIndex());

        // Force a refresh to update the elements from the model.
        tableViewer.refresh();
      }
    });

    // create the editors for the column
    TextCellEditor idCellEditor = createIDColumnCellEditor(table);

    // Set the editors, cell modifier, and column properties
    tableViewer.setColumnProperties(new String[] { NAME });
    tableViewer.setCellModifier(new AutoGenerationSettingsCellModifier(tableViewer));
    tableViewer.setCellEditors(new CellEditor[] { idCellEditor });
    tableViewer.refresh();

    return container;
  }

  /**
   * @brief Create the editor for the {@link TAutoGenerationSettings#id}
   *
   *        Additionally it adds the validator to validate the values edited by the user.
   *
   * @param[in] tableObj The table instance where the editor will be added.
   *
   * @return The editor created for the cell.
   */
  private TextCellEditor createIDColumnCellEditor(Table tableObj) {
    final TextCellEditor idCellEditor = new TextCellEditor(tableObj);
    idCellEditor.addListener(new ICellEditorListener() {

      @Override
      public void editorValueChanged(boolean oldValidState, boolean newValidState) {
        // TODO Auto-generated method stub
        setErrorMessage(idCellEditor.getErrorMessage());
      }

      @Override
      public void cancelEditor() {
        // TODO Auto-generated method stub
        setErrorMessage(null);
      }

      @Override
      public void applyEditorValue() {
        // TODO Auto-generated method stub
        setErrorMessage(null);
      }
    });

    idCellEditor.setValidator(new ICellEditorValidator() {

      @Override
      public String isValid(Object value) {
        if (isAutoGenerationIdAlreadyPresent((String) value)) {
          // TODO: fix the strings
          return ((String) value + " already present.");
        }

        if (((String) value).isEmpty())
          return (SETTINGS_ID_EMPTY_VALUES_NOT_ALLOWED);

        return null;
      }
    });

    return idCellEditor;
  }

  /**
   * @brief Creates a unique ID from the ID's present in the list of {@link TAutoGenerationSettings}
   *        .
   *
   * @return Unique ID.
   */
  private String newAutoGenerationSettingsID() {
    final String commonName = "custom";

    String uniqueName = commonName;
    int suffixValue = 0;
    while (isAutoGenerationIdAlreadyPresent(uniqueName)) {
      suffixValue++;
      uniqueName = commonName + " " + suffixValue;
    }
    return uniqueName;
  }

  /**
   * @brief Return the initial size of the dialog.
   */
  @Override
  protected Point getInitialSize() {
    return new Point(450, 450);
  }

  /**
   * @brief Checks for new ID is already present in the AutoGenerationSettings or not
   *
   * @param[in] id The new ID to be checked.
   * @return true if already present, false otherwise
   */
  private boolean isAutoGenerationIdAlreadyPresent(final String id) {
    List<TAutoGenerationSettings> agSettingList = projectConfiguration.getAutoGenerationSettings();
    for (TAutoGenerationSettings agSettings : agSettingList) {
      if (agSettings.getId().equalsIgnoreCase(id)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @brief Checks for the dialog's dirty state.
   *
   * @return true if the data is modified, false otherwise.
   */
  public boolean isDirty() {
    return dirty;
  }

  /**
   * @brief Returns true to set the dialog re-sizable always.
   */
  @Override
  protected boolean isResizable() {
    return true;
  }

  /**
   * @brief Removes the {@link TAutoGenerationSettings} at the specified position in this list
   *        (optional operation). Additionally this function resets the
   *        {@link TProjectConfiguration.activeAutoGenerationSetting} if the current
   *        activeAutoGenerationSetting element is removed.
   *
   * @param[in] index The index of the AutoGenerationSetting element to be removed
   * @return <code>true</code> if the element is successfully removed. <code>false</code> otherwise.
   */
  private boolean removeAutoGenerationSetting(final int index) {
    List<TAutoGenerationSettings> agList = projectConfiguration.getAutoGenerationSettings();

    String settingToBeRemoved = null;
    try {
      TAutoGenerationSettings settingTobeRemoved = agList.get(index);
      settingToBeRemoved = settingTobeRemoved.getId();
    } catch (IndexOutOfBoundsException e) {
      System.err.println("Error in removing the autogenerationsetting. ID:" + index);
      return false;
    }

    if ((settingToBeRemoved == null) || (settingToBeRemoved.isEmpty())) {
      System.err.println("Error in removing the autogenerationsetting. ID:" + index);
      return false;
    }

    Result libApiRes = OpenConfiguratorCore.GetInstance().RemoveConfiguration(networkID,
        settingToBeRemoved);
    if (!libApiRes.IsSuccessful()) {
      String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
          + libApiRes.GetErrorMessage();
      PluginErrorDialogUtils.displayErrorMessageDialog(getShell(), errorMessage, null);
      System.err.println("RemoveConfiguation failed. " + errorMessage);
      return libApiRes.IsSuccessful();
    }

    agList.remove(index); // No need to return the removed element.
    if (settingToBeRemoved.equalsIgnoreCase(projectConfiguration.getActiveAutoGenerationSetting())) {
      // The item about to be removed is same as the active auto generation setting.
      // Update the active auto-generation setting to 0, then remove it.
      String currentActiveSetting = agList.get(0).getId();
      libApiRes = OpenConfiguratorCore.GetInstance().SetActiveConfiguration(networkID,
          currentActiveSetting);
      if (!libApiRes.IsSuccessful()) {
        String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
            + libApiRes.GetErrorMessage();
        PluginErrorDialogUtils.displayErrorMessageDialog(getShell(), errorMessage, null);
        System.err.println("SetActiveConfiguration failed. " + errorMessage);
        return libApiRes.IsSuccessful();
      }
      projectConfiguration.setActiveAutoGenerationSetting(currentActiveSetting);
    }

    dirty = true;

    return libApiRes.IsSuccessful();
  }
}
