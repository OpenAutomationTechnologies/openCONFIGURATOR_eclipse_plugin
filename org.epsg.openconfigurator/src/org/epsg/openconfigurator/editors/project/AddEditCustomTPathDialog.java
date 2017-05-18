/*******************************************************************************
 * @file   AddEditCustomTPathDialog.java
 *
 * @author Suresh Babu, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2017, Kalycito Infotech Private Limited
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.TPath;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration;

/**
 * A dialog to modify the path settings of the project XML.
 */
public final class AddEditCustomTPathDialog extends TitleAreaDialog {

    /**
     * Dialog strings and messages.
     */
    private final static String NAME_LABEL = "Name:";
    private final static String LOCATION_LABEL = "Location:";
    private final static String BROWSE_LABEL = "Browse...";
    private final static String BROWSE_LOCATION_TITLE = "Choose the path for the generated output files";

    private final static String DIALOG_ADD_TITLE = "Add Location";
    private final static String DIALOG_EDIT_TITLE = "Edit Location";
    private final static String DIALOG_DEFAULT_MESSAGE = "Choose the path for the generated output files";

    private final static String NAME_FIELD_MESSAGE_EMPTY_NAME = "Enter any name to identify the location";
    private final static String NAME_FIELD_MESSAGE_NAME_ALREADY_EXISTS = "The name already exists!";
    private final static String NAME_FIELD_MESSAGE_NAME_INVALID = "Enter a valid name";

    private final static String LOCATION_FIELD_MESSAGE_MUST_SPEFIFY_PATH = "Must specify a location.";
    private final static String LOCATION_FIELD_MESSAGE_INVALID_PATH = "Choose a valid location for the output files";

    /**
     * Path name text box.
     */
    private Combo txtName;

    /**
     * Path location text box.
     */
    private Text txtLocation;

    /**
     * Dialog dirty flag.
     */
    private boolean dirty = false;

    /**
     * Path settings model.
     */
    private TPath path = new TPath();

    boolean editable = false;

    /**
     * Path settings parent model.
     */
    private TProjectConfiguration.PathSettings pathSettingsModel;

    /**
     * Create the Add/Edit Path settings dialog.
     *
     * @param parentShell The parent shell.
     * @param pathSettingsModel The parent path settings model.
     * @param path The path to be edited. Can be null if needs to be added.
     */
    public AddEditCustomTPathDialog(Shell parentShell,
            TProjectConfiguration.PathSettings pathSettingsModel,
            final TPath path, boolean edit) {
        super(parentShell);

        if (path != null) {
            this.path = path;
        }

        editable = edit;

        this.pathSettingsModel = pathSettingsModel;

    }

    /**
     * Handles the button pressed events for the buttons in the footer of the
     * dialog.
     *
     * @see IDialogConstants.*_ID constants
     */
    @Override
    protected void buttonPressed(int buttonId) {
        if (buttonId == IDialogConstants.OK_ID) {

            if (isPageComplete()) {
                if (path != null) {
                    path.setId(txtName.getText().trim());
                    path.setPath(txtLocation.getText());
                }
                okPressed();
            }

        } else if (buttonId == IDialogConstants.CANCEL_ID) {
            cancelPressed();
        }
    }

    /**
     * Create contents of the footer button bar.
     *
     * @param parent
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
                true);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
    }

    /**
     * Create contents of the dialog.
     *
     * @param parent
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        if (path != null) {
            if (path.getId() == null) {
                setTitle(AddEditCustomTPathDialog.DIALOG_ADD_TITLE);
            } else {
                setTitle(AddEditCustomTPathDialog.DIALOG_EDIT_TITLE);
            }

            setMessage(AddEditCustomTPathDialog.DIALOG_DEFAULT_MESSAGE);

            Composite container = new Composite(parent, SWT.NONE);
            container.setLayoutData(new GridData(GridData.FILL_BOTH));
            container.setLayout(new GridLayout(3, false));

            Label lblSettingsName = new Label(container, SWT.CENTER);
            lblSettingsName.setText(AddEditCustomTPathDialog.NAME_LABEL);
            lblSettingsName.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

            txtName = new Combo(container, SWT.READ_ONLY);
            txtName.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

            List<String> idList = new ArrayList<>();

            idList.add("CONFIG_TEXT");
            idList.add("CONFIG_BINARY");
            idList.add("CONFIG_CHAR_TEXT");
            idList.add("XML_PROCESS_IMAGE");
            idList.add("C_PROCESS_IMAGE");
            idList.add("CSHARP_PROCESS_IMAGE");

            System.err.println("Id list..path seting .." + pathSettingsModel);
            for (TPath path : pathSettingsModel.getPath()) {
                System.err.println("Id list.." + idList);
                System.err.println("Id path.." + path.getId());
                if (idList.contains(path.getId())) {
                    idList.remove(path.getId());
                }
            }

            if (!editable) {
                for (String id : idList) {
                    txtName.add(id);
                }
            } else {
                txtName.add(path.getId());
            }
            txtName.select(0);

            if ((path.getId() != null)) {
                txtName.setText(path.getId());
            }
            txtName.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(ModifyEvent e) {

                    setErrorMessage(null);
                    if (txtName.getText().isEmpty()) {
                        setErrorMessage(
                                AddEditCustomTPathDialog.NAME_FIELD_MESSAGE_EMPTY_NAME);
                    }

                    // Check if is ID already present in the model
                    if ((path.getId() != null)
                            && !path.getId().equalsIgnoreCase(txtName.getText())
                            && OpenConfiguratorProjectUtils
                                    .isPathIdAlreadyPresent(pathSettingsModel,
                                            txtName.getText())) {
                        setErrorMessage(
                                AddEditCustomTPathDialog.NAME_FIELD_MESSAGE_NAME_ALREADY_EXISTS);
                    }

                    dirty = true;
                }
            });

            Label lblValue = new Label(container, SWT.CENTER);
            lblValue.setText(AddEditCustomTPathDialog.LOCATION_LABEL);
            lblValue.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

            txtLocation = new Text(container, SWT.NONE);
            txtLocation.setLayoutData(
                    new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            txtLocation.setText("");
            if ((path.getPath() != null)) {
                txtLocation.setText(path.getPath());
            }
            txtLocation.addModifyListener(new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent e) {
                    setErrorMessage(null);

                    if (txtLocation.getText().isEmpty()) {
                        setErrorMessage(
                                AddEditCustomTPathDialog.LOCATION_FIELD_MESSAGE_MUST_SPEFIFY_PATH);
                        return;
                    }

                    File file = new File(txtLocation.getText().trim());
                    if (!file.exists() || !file.isDirectory()) {
                        setErrorMessage("Invalid path specified.");
                        return;
                    }

                    dirty = true;
                }
            });

            Button btnBrowse = new Button(container, SWT.PUSH);
            btnBrowse.setText(AddEditCustomTPathDialog.BROWSE_LABEL);
            GridData gd = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
            btnBrowse.setLayoutData(gd);
            btnBrowse.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent event) {

                    DirectoryDialog dirDialog = new DirectoryDialog(getShell(),
                            SWT.OPEN);
                    dirDialog.setText(
                            AddEditCustomTPathDialog.BROWSE_LOCATION_TITLE);
                    if (!txtLocation.getText().isEmpty()) {
                        dirDialog.setFilterPath(txtLocation.getText());
                    }

                    // Open Dialog and save result of selection
                    String selectedOutputPath = dirDialog.open();
                    if ((selectedOutputPath != null)
                            && !selectedOutputPath.isEmpty()) {
                        txtLocation.setText(selectedOutputPath);
                        dirty = true;
                    }
                }
            });

            return container;

        }
        return null;

    }

    public TPath getData() {
        return path;
    }

    /**
     * Return the initial size of the dialog.
     */
    @Override
    protected Point getInitialSize() {
        return new Point(450, 350);
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
        boolean nameValid = false;
        boolean locationValid = false;

        if ((txtName.getText() != null) && !txtName.getText().isEmpty()) {
            if (path.getId() == null) {
                if (!OpenConfiguratorProjectUtils.isPathIdAlreadyPresent(
                        pathSettingsModel, txtName.getText())) {
                    nameValid = true;
                    setErrorMessage(null);
                } else {
                    setErrorMessage(
                            AddEditCustomTPathDialog.NAME_FIELD_MESSAGE_NAME_ALREADY_EXISTS);
                }
            } else {
                if (path.getId().equalsIgnoreCase(txtName.getText())
                        || !OpenConfiguratorProjectUtils.isPathIdAlreadyPresent(
                                pathSettingsModel, txtName.getText())) {
                    nameValid = true;
                    setErrorMessage(null);
                } else {
                    setErrorMessage(
                            AddEditCustomTPathDialog.NAME_FIELD_MESSAGE_NAME_ALREADY_EXISTS);
                }
            }
        } else {
            setErrorMessage(
                    AddEditCustomTPathDialog.NAME_FIELD_MESSAGE_NAME_INVALID);
        }

        if ((txtLocation.getText() != null)
                && !txtLocation.getText().isEmpty()) {
            File file = new File(txtLocation.getText().trim());
            if (!file.exists() || !file.isDirectory()) {
                setErrorMessage("Invalid path specified.");
            } else {
                locationValid = true;
            }
        } else {
            setErrorMessage(
                    AddEditCustomTPathDialog.LOCATION_FIELD_MESSAGE_INVALID_PATH);
        }

        return (nameValid && locationValid);
    }

}
