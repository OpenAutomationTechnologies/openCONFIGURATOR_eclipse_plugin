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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
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
import org.epsg.openconfigurator.xmlbinding.projectfile.TAutoGenerationSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TKeyValuePair;

/**
 * A dialog to modify the build configuration settings in the library. It
 * modifies the <Settings> tag of the project XML.
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
        private boolean alreadyAvailable;
        private String name;

        BuilderConfiguration(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean isAlreadyAvailable() {
            return alreadyAvailable;
        }

        public void setAlreadyAvailable(boolean alreadyAvailable) {
            this.alreadyAvailable = alreadyAvailable;
        }

    }

    /**
     * List of build configurations.
     */
    private List<BuilderConfiguration> builderConfig = new ArrayList<BuilderConfiguration>();

    private final String VALUE_ERROR_MESSAGE = "Enter a valid value. Format: NodeID;NodeID; eg:1;32;110;";

    /** UI Controls */
    private Text value;
    private Combo settingsTypeCombo;
    private Button bntActive;

    private boolean dirty = false;

    private final String DIALOG_TITLE = "Configure settings - ";
    private final String DIALOG_MESSAGE = "Configure the build configuration settings";

    private String activeSettingName;

    // private int activeSettingIndex = -1;

    private String[] listOfInputNames = { "Test1", "Test2", "Test3", "Test4" };

    /**
     * Data model from the openCONFIGURATOR project file. The <Setting> tag.
     */
    TKeyValuePair activeSetting = new TKeyValuePair();
    TAutoGenerationSettings agSettings;

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

        for (String s : listOfInputNames) {
            builderConfig.add(new BuilderConfiguration(s));
        }

        for (BuilderConfiguration builderConfig : builderConfig) {
            for (TKeyValuePair tempSetting : agSettings.getSetting()) {
                if (tempSetting.getName().equals(builderConfig.getName())) {
                    builderConfig.setAlreadyAvailable(true);
                }
            }
        }
    }

    /**
     * Adds the listener to the controls available in the dialog.
     */
    private void addControlListeners() {
        settingsTypeCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                dirty = true;
            }
        });

        value.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                setErrorMessage(null);

                isValueValid(value.getText().trim());

                dirty = true;
            }
        });

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
                if ((inputChar == SWT.BS) || (inputChar == SWT.ARROW_LEFT)
                        || (inputChar == SWT.ARROW_RIGHT)
                        || (inputChar == SWT.DEL)) {
                    event.doit = true;
                }
            }
        });

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

            if (isPageComplete()) {
                activeSetting.setName(settingsTypeCombo.getText());
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
        lblSettingsName.setText("Name:");
        lblSettingsName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
                false, 1, 1));

        settingsTypeCombo = new Combo(container, SWT.READ_ONLY);
        settingsTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                true, false, 1, 1));

        Label lblValue = new Label(container, SWT.CENTER);
        lblValue.setText("Value:");
        lblValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
                1, 1));

        value = new Text(container, SWT.LEFT | SWT.SINGLE | SWT.BORDER);
        value.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
                1));

        Label lblActive = new Label(container, SWT.CENTER);
        lblActive.setText("Active:");
        lblActive.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
                false, 1, 1));

        bntActive = new Button(container, SWT.CHECK);
        bntActive.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
                false, 1, 1));
        bntActive.setSelection(true);

        initValuesFromModel();
        addControlListeners();
        return container;
    }

    /**
     * Returns the updated model.
     *
     * @return TKeyValuePair
     */
    public TKeyValuePair getData() {
        return activeSetting;
    }

    /**
     * Returns the list of settings names from the builder configurations.
     *
     * @return String[] list of builder configuration names.
     */
    private String[] getSettingListFromBuilderConfiguraion() {
        ArrayList<String> stringList = new ArrayList<String>();
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
                setErrorMessage("Select a valid Settings type");
            } else {
                setErrorMessage("No new Settings are available. Try editing from the settings table.");
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
     * Check for the valid value.
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
                        setErrorMessage(element + " is not a valid node ID");
                    }
                } catch (NumberFormatException exception) {
                    setErrorMessage(element + " is not a valid node ID");
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
                if (tempSetting.getName().equals(activeSettingName)) {
                    activeSetting = tempSetting;
                    break;
                }
            }
        }
    }

}
