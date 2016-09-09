/*******************************************************************************
 * @file   AddEditSettingsDialog.java
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.lib.wrapper.StringCollection;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAutoGenerationSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TKeyValuePair;

/**
 * A dialog to modify the build configuration settings in the library. It
 * modifies the 'Settings' tag of the project XML.
 *
 * @author Ramakrishnan P
 *
 */
public final class AddEditSettingsDialog extends TitleAreaDialog {

    /**
     * A wrapper class to wrap the build configurations from the library.
     *
     * @author Ramakrishnan P
     */
    private class BuilderConfiguration {
        /**
         * The name of the configuration.
         */
        private final String name;

        /**
         * Determines the availability of the configuration in the builder vs
         * the library.
         */
        private boolean alreadyAvailable;

        /**
         * Constructor
         *
         * @param name The name of the configuration.
         */
        public BuilderConfiguration(String name) {
            // It is not required to be a static inner class.
            this.name = name;
        }

        /**
         * Returns the name corresponding to the builder configuration.
         *
         * @return The name.
         */
        public String getName() {
            return name;
        }

        /**
         * @return true if already available, false otherwise.
         */
        public boolean isAlreadyAvailable() {
            return alreadyAvailable;
        }

        /**
         * Set true if the builder configuration is already available or false
         * otherwise.
         *
         * @param alreadyAvailable Flag to set the configuration already
         *            available.
         */
        public void setAlreadyAvailable(boolean alreadyAvailable) {
            this.alreadyAvailable = alreadyAvailable;
        }
    }

    /**
     * Dialog strings and messages.
     */
    private static final String DIALOG_TITLE = "Configure Setting - ";
    private static final String DIALOG_MESSAGE = "Add a setting to build configuration.";
    private static final String VALUE_ERROR_MESSAGE = "Enter a valid value.\nFormat: NodeID;NodeID; eg:1;32;110;";
    private static final String VALUE_TOOL_TIP = "Empty: all nodes.\nCustom format: NodeID;NodeID; eg:1;32;110;";
    private static final String NAME_LABEL = "Name:";
    private static final String VALUE_LABEL = "Value:";
    private static final String ACTIVE_LABEL = "Active:";
    private static final String INVALID_SETTINGS_TYPE = "Select a valid Settings type.";
    private static final String EMPTY_SETTINGS_TYPE_ERROR = "No new settings are available. Try editing from the settings table.";
    private static final String ERROR_INVALID_NODE_ID = "{0} is not a valid node ID.";

    /**
     * Builder settings value.
     */
    private Text value;

    /**
     * Builder settings type combo box.
     */
    private Combo settingsTypeCombo;

    /**
     * Builder settings active/not active check box.
     */
    private Button bntActive;

    /**
     * Dialog dirty flag.
     */
    private boolean dirty = false;

    /**
     * Store the active settings type name.
     */
    private String activeSettingName;

    /**
     * List of build configurations.
     */
    private List<BuilderConfiguration> builderConfig = new ArrayList<>();

    /**
     * The Setting data model from the openCONFIGURATOR project.
     */
    private TKeyValuePair activeSetting = new TKeyValuePair();

    /**
     * The AutoGenerationSettings data model from the openCONFIGURATOR project.
     */
    private TAutoGenerationSettings agSettings;

    /**
     * Creates the add/edit settings dialog.
     *
     * @param parentShell Parent Shell.
     * @param autoGenerationSettings The auto generation settings model.
     */
    public AddEditSettingsDialog(Shell parentShell,
            TAutoGenerationSettings autoGenerationSettings) {
        super(parentShell);

        agSettings = autoGenerationSettings;

        // Create the builderConfiguration based on the input from the library.
        StringCollection support = new StringCollection();
        Result libApiRes = OpenConfiguratorCore.GetInstance()
                .GetSupportedSettingIds(support);
        if (!libApiRes.IsSuccessful()) {
            // Display a dialog to report it to the user.
            String errorMessage = OpenConfiguratorLibraryUtils
                    .getErrorMessage(libApiRes);
            System.err.println(errorMessage);
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    libApiRes);
            return;
        }

        for (int i = 0; i < support.size(); i++) {
            BuilderConfiguration cfg = new BuilderConfiguration(support.get(i));
            builderConfig.add(cfg);
        }

        // Set AlreadyAvailable option if the builder configuration is already
        // configured in the AutoGenerationSettings.
        for (BuilderConfiguration builderConfig : builderConfig) {
            for (TKeyValuePair tempSetting : agSettings.getSetting()) {
                if (tempSetting.getName()
                        .equalsIgnoreCase(builderConfig.getName())) {
                    builderConfig.setAlreadyAvailable(true);
                }
            }
        }
    }

    /**
     * Adds the listener to the controls available in the dialog.
     */
    private void addControlListeners() {
        /**
         * Settings type combo box selection change listener.
         */
        settingsTypeCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                dirty = true;
            }
        });

        /**
         * Value text box - text modify listener.
         */
        value.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                setErrorMessage(null);

                isValueValid(value.getText().trim());

                dirty = true;
            }
        });

        /**
         * Input verify listener for the value text box.
         */
        value.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent event) {

                // Assume we don't allow it
                event.doit = false;

                // Get the character typed
                char inputChar = event.character;

                // Allow 0-9
                if (Character.isDigit(inputChar)) {
                    event.doit = true;
                }

                // Allow delimiter
                if (inputChar == ';') {
                    event.doit = true;
                }

                // Allow arrow keys and backspace and delete keys
                if ((inputChar == SWT.BS)
                        || (Integer.valueOf(inputChar) == SWT.ARROW_LEFT)
                        || (Integer.valueOf(inputChar) == SWT.ARROW_RIGHT)
                        || (inputChar == SWT.DEL)) {
                    event.doit = true;
                }
            }
        });

        /**
         * Active/In-Active check box selection changed listener.
         */
        bntActive.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                dirty = true;
            }
        });
    }

    /**
     * Handles the button pressed events for the buttons in the footer of the
     * dialog.
     */
    @Override
    protected void buttonPressed(int buttonId) {
        if (buttonId == IDialogConstants.OK_ID) {

            /**
             * Check for the page is complete or not.
             */
            if (isPageComplete()) {
                activeSetting.setName(settingsTypeCombo.getText().trim());
                activeSetting.setValue(value.getText());
                activeSetting.setEnabled(bntActive.getSelection());
                okPressed();
            }

        } else if (buttonId == IDialogConstants.CANCEL_ID) {
            cancelPressed();
        }
    }

    /**
     * Create contents of the footer button bar.
     *
     * @param parent Parent composite
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
                true);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
    }

    /**
     * Create contents of the Add/Edit settings dialog window.
     *
     * @param parent Parent composite
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        setTitle(DIALOG_TITLE + agSettings.getId());
        setMessage(DIALOG_MESSAGE);

        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 10;
        layout.marginHeight = 10;
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(layout);

        Label lblSettingsName = new Label(container, SWT.CENTER);
        lblSettingsName.setText(NAME_LABEL);
        lblSettingsName.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        settingsTypeCombo = new Combo(container, SWT.READ_ONLY);
        settingsTypeCombo.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Label lblValue = new Label(container, SWT.CENTER);
        lblValue.setText(VALUE_LABEL);
        lblValue.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        value = new Text(container, SWT.LEFT | SWT.SINGLE | SWT.BORDER);
        value.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        value.setToolTipText(VALUE_TOOL_TIP);

        Label lblActive = new Label(container, SWT.CENTER);
        lblActive.setText(ACTIVE_LABEL);
        lblActive.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        bntActive = new Button(container, SWT.CHECK);
        bntActive.setLayoutData(
                new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        bntActive.setSelection(true);

        /**
         * Initialize the values from the project model.
         */
        initValuesFromModel();

        /**
         * Add listeners to the dialog controls.
         */
        addControlListeners();

        return container;
    }

    /**
     * Returns the updated model.
     *
     * @return TKeyValuePair the active setting.
     */
    public TKeyValuePair getData() {
        return activeSetting;
    }

    /**
     * Returns the list of settings names from the builder configurations.
     *
     * @return String[] List of builder configuration names.
     */
    private String[] getSettingListFromBuilderConfiguraion() {
        ArrayList<String> stringList = new ArrayList<>();
        for (BuilderConfiguration builderConfiguration : builderConfig) {
            if (!builderConfiguration.isAlreadyAvailable()) {
                stringList.add(builderConfiguration.getName());
            }
        }

        return stringList.toArray(new String[stringList.size()]);
    }

    /**
     * Initializes the controls with the data from the model.
     */
    private void initValuesFromModel() {

        // Adds the settings list from the library builder configuration.
        settingsTypeCombo.setItems(getSettingListFromBuilderConfiguraion());
        settingsTypeCombo.select(0);

        if (activeSettingName != null) {
            settingsTypeCombo.add(activeSettingName);
        }

        if (activeSetting == null) {
            return;
        }

        if ((activeSetting.getName() != null)
                && !activeSetting.getName().isEmpty()) {
            settingsTypeCombo.setText(activeSetting.getName());
        }

        if (activeSetting.getValue() != null) {
            value.setText(activeSetting.getValue());
        }

        bntActive.setSelection(activeSetting.isEnabled());
    }

    /**
     * Checks for the dialog's dirty state.
     *
     * @return true if the data is modified, false otherwise.
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Validates the input from the dialog controls.
     *
     * @return Returns true if the inputs are valid, false otherwise.
     */
    private boolean isPageComplete() {
        boolean settingsTypeValid = false;
        boolean valueValid = false;

        if ((settingsTypeCombo.getText() != null)
                && !settingsTypeCombo.getText().isEmpty()) {
            settingsTypeValid = true;
            setErrorMessage(null);
        } else {
            if (settingsTypeCombo.getItemCount() > 0) {
                setErrorMessage(INVALID_SETTINGS_TYPE);
            } else {
                setErrorMessage(EMPTY_SETTINGS_TYPE_ERROR);
            }
        }

        if ((value.getText() != null) && isValueValid(value.getText().trim())) {
            valueValid = true;
        } else {
            setErrorMessage(VALUE_ERROR_MESSAGE);
        }

        return (settingsTypeValid && valueValid);
    }

    /**
     * Returns true to set the dialog re-sizable always.
     */
    @Override
    protected boolean isResizable() {
        return true;
    }

    /**
     * Check for the valid value from the dialog.
     *
     * @param inputValue
     * @return true if valid, false otherwise.
     */
    private boolean isValueValid(final String inputValue) {
        boolean retValue = false;
        if (!inputValue.isEmpty()) {

            String[] nodeIdList = inputValue.split(";");

            for (String element : nodeIdList) {
                try {
                    // We need to allow 1 to 255
                    short shortValue = Short.parseShort(element);
                    if ((shortValue > 0) && (shortValue < 256)) {
                        retValue = true;
                    } else {
                        setErrorMessage(MessageFormat
                                .format(ERROR_INVALID_NODE_ID, element));
                    }
                } catch (NumberFormatException exception) {
                    exception.printStackTrace();
                    setErrorMessage(MessageFormat.format(ERROR_INVALID_NODE_ID,
                            element));
                }
            }
        } else {
            // Value can be empty
            retValue = true;
        }

        return retValue;
    }

    /**
     * Finds and sets the setting from the model based activeSettingName.
     *
     * @param activeSettingName Name of the setting.
     */
    public void setActiveSettingName(final String activeSettingName) {
        this.activeSettingName = activeSettingName;

        if (activeSettingName != null) {
            for (TKeyValuePair tempSetting : agSettings.getSetting()) {
                if (tempSetting.getName().equalsIgnoreCase(activeSettingName)) {
                    activeSetting.setName(tempSetting.getName().trim());
                    activeSetting.setValue(tempSetting.getValue());
                    activeSetting.setEnabled(tempSetting.isEnabled());
                    break;
                }
            }
        }
    }

}
