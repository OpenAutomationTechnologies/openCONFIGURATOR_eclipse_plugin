/*******************************************************************************
 * @file   ModifyPathSettingsDialog.java
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
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.TPath;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration;

/**
 * A dialog to modify the list of Path present in the project.
 *
 * @author Ramakrishnan P
 *
 */
public class ModifyPathSettingsDialog extends TitleAreaDialog {

    /**
     * A content provider class for the
     * {@link TProjectConfiguration.PathSettings} model.
     *
     * @author Ramakrishnan P
     *
     */
    private class PathSettingsContentProvider
            implements IStructuredContentProvider {
        /**
         * Disposes any created resources
         */
        @Override
        public void dispose() {
            // Do nothing
        }

        /**
         * Returns the input elements
         */
        @Override
        public Object[] getElements(Object inputElement) {
            return ((List) inputElement).toArray();
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput,
                Object newInput) {
            // ignore.
        }
    }

    /**
     * Cell modifier for the list of path settings.
     *
     * @author Ramakrishnan P
     *
     */
    private class PathSettingsIdCellModifier implements ICellModifier {

        private Viewer viewer;

        public PathSettingsIdCellModifier(Viewer viewer) {
            this.viewer = viewer;
        }

        @Override
        public boolean canModify(Object element, String property) {
            if (ModifyPathSettingsDialog.NAME.equals(property)) {

                if (element instanceof Item) {
                    element = ((Item) element).getData();
                }

                TPath path = (TPath) element;
                if (path.getId().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_ID)) {
                    return false;
                }

                return true;
            }

            return false;
        }

        @Override
        public Object getValue(Object element, String property) {
            TPath path = (TPath) element;
            if (ModifyPathSettingsDialog.NAME.equals(property)) {
                return path.getId();
            }
            return null;
        }

        @Override
        public void modify(Object element, String property, Object value) {

            if (element instanceof Item) {
                element = ((Item) element).getData();
            }

            TPath path = (TPath) element;

            // If Active setting is same as the element to be modified.. update
            // the active setting also.
            if (path.getId()
                    .equalsIgnoreCase(pathSettingsModel.getActivePath())) {
                pathSettingsModel.setActivePath(((String) value).trim());
                dirty = true;
            }

            if (ModifyPathSettingsDialog.NAME.equals(property)) {
                path.setId(((String) value).trim());

                dirty = true;
            }

            // Force the viewer to refresh
            viewer.refresh();

        }
    }

    /**
     * Dialog messages and labels.
     */
    private static final String DIALOG_TITLE = "Available Output Locations";
    private static final String DIALOG_DEFAULT_MESSAGE = "Modify the list of available output locations.";

    private static final String NAME = "Name";
    private static final String PATH = "Location";

    private static final String ADD_LABEL = "Add...";
    private static final String EDIT_LABEL = "Edit...";
    private static final String DELETE_LABEL = "Delete";

    private static final String SETTINGS_ID_EMPTY_VALUES_NOT_ALLOWED = "No empty values are allowed";
    private static final String REMOVE_PATH_ERROR = "Error in removing the TPath. ID:{0}";

    /**
     * Column labels for the path settings table.
     */
    private static final String columns[] = { ModifyPathSettingsDialog.NAME,
            ModifyPathSettingsDialog.PATH };

    /**
     * Dialog dirty flag.
     */
    private boolean dirty = false;

    /**
     * Table viewer for the path settings list.
     */
    private TableViewer tableViewer;

    /**
     * Table in the table viewer.
     */
    private Table table;

    /**
     * Add new path button.
     */
    private Button btnNewPath;

    /**
     * Edit path button.
     */
    private Button btnEditPath;

    /**
     * Delete path button.
     */
    private Button deleteSettingsButton;

    /**
     * Path settings model.
     */
    private TProjectConfiguration.PathSettings pathSettingsModel;

    /**
     * Create the modify path settings dialog.
     *
     * @param parentShell Parent shell.
     * @param pathSettings Path settings model.
     */
    public ModifyPathSettingsDialog(Shell parentShell,
            TProjectConfiguration.PathSettings pathSettings) {
        super(parentShell);
        pathSettingsModel = pathSettings;
    }

    /**
     * Create contents of the footer button bar.
     *
     * @param parent Parent control.
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
                true);
    }

    /**
     * Create contents of the dialog.
     *
     * @param parent Parent control.
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        setTitle(ModifyPathSettingsDialog.DIALOG_TITLE);
        setMessage(ModifyPathSettingsDialog.DIALOG_DEFAULT_MESSAGE);
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new GridLayout(2, false));

        // Add the TableViewer
        tableViewer = new TableViewer(container, SWT.SINGLE | SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);

        table = tableViewer.getTable();
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
        gd.heightHint = 75;
        table.setLayoutData(gd);

        TableViewerColumn nameViewerColumn = new TableViewerColumn(tableViewer,
                SWT.NONE);
        final TableColumn nameColumn = nameViewerColumn.getColumn();
        nameColumn.setText(ModifyPathSettingsDialog.columns[0]);

        nameViewerColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                TPath ag = (TPath) element;
                return ag.getId();
            }
        });

        TableViewerColumn pathViewerColumn = new TableViewerColumn(tableViewer,
                SWT.NONE);

        final TableColumn pathColumn = pathViewerColumn.getColumn();
        pathColumn.setText(ModifyPathSettingsDialog.columns[1]);

        pathViewerColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                TPath ag = (TPath) element;
                return ag.getPath();
            }
        });

        tableViewer.setContentProvider(new PathSettingsContentProvider());
        tableViewer.setInput(pathSettingsModel.getPath());
        tableViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {

                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        ISelection s = event.getSelection();

                        if ((s instanceof IStructuredSelection)
                                && (((IStructuredSelection) s).size() == 1)) {
                            Object object = ((IStructuredSelection) s)
                                    .getFirstElement();
                            TPath path = (TPath) object;

                            // If the current selection ID = defaultOutputPath
                            // then disable edit/delete button
                            if (path.getId().equalsIgnoreCase(
                                    OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_ID)) {
                                btnEditPath.setEnabled(false);
                                deleteSettingsButton.setEnabled(false);
                            } else {
                                btnEditPath.setEnabled(true);
                                deleteSettingsButton.setEnabled(true);
                            }

                        } else {
                            System.err.println(
                                    "Selection should be an event of structured selection.");
                        }
                    }
                });

        for (int i = 0, n = table.getColumnCount(); i < n; i++) {
            table.getColumn(i).pack();
        }
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        // Add resize listener to auto resize according to the cell contents.
        table.addListener(SWT.Resize, new Listener() {
            @Override
            public void handleEvent(Event event) {
                int width = table.getClientArea().width - nameColumn.getWidth();
                pathColumn.setWidth(width);
            }
        });

        // Add a button to create the new Path
        btnNewPath = new Button(container, SWT.PUSH);
        btnNewPath.setText(ModifyPathSettingsDialog.ADD_LABEL);
        gd = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
        btnNewPath.setLayoutData(gd);

        btnNewPath.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                TPath path = new TPath();
                AddEditTPathDialog pathDialog = new AddEditTPathDialog(
                        getShell(), pathSettingsModel, path);

                if (pathDialog.open() == Window.OK) {
                    if (pathDialog.isDirty()) {
                        dirty = true;
                    }
                    pathSettingsModel.getPath().add(path);
                    tableViewer.refresh();
                }
            }
        });

        btnEditPath = new Button(container, SWT.PUSH);
        btnEditPath.setText(ModifyPathSettingsDialog.EDIT_LABEL);
        gd = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
        btnEditPath.setLayoutData(gd);
        btnEditPath.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                TPath path = pathSettingsModel.getPath()
                        .get(table.getSelectionIndex());

                if (!path.getId().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_ID)) {
                    AddEditTPathDialog pathDialog = new AddEditTPathDialog(
                            getShell(), pathSettingsModel, path);

                    if (pathDialog.open() == Window.OK) {
                        if (pathDialog.isDirty()) {
                            dirty = true;
                        }

                        tableViewer.refresh();
                    }
                }
            }
        });

        deleteSettingsButton = new Button(container, SWT.PUSH);
        deleteSettingsButton.setText(ModifyPathSettingsDialog.DELETE_LABEL);
        gd = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
        deleteSettingsButton.setLayoutData(gd);
        deleteSettingsButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                removePath(table.getSelectionIndex());
                dirty = true;
                // Force a refresh to update the elements from the model.
                tableViewer.refresh();
            }
        });

        // create the editors for the column
        TextCellEditor idCellEditor = createIDColumnCellEditor(table);
        // Set the editors, cell modifier, and column properties
        tableViewer.setColumnProperties(
                new String[] { ModifyPathSettingsDialog.NAME });
        tableViewer
                .setCellModifier(new PathSettingsIdCellModifier(tableViewer));
        tableViewer.setCellEditors(new CellEditor[] { idCellEditor });
        tableViewer.refresh();
        return parent;
    }

    /**
     * Creates the cell editor for the Path ID column.
     *
     * @param tableObj The table in which the contents are available.
     * @return The text cell editor for the ID column.
     */
    private TextCellEditor createIDColumnCellEditor(Table tableObj) {
        final TextCellEditor idCellEditor = new TextCellEditor(tableObj);
        idCellEditor.addListener(new ICellEditorListener() {

            @Override
            public void applyEditorValue() {
                setErrorMessage(null);
            }

            @Override
            public void cancelEditor() {
                setErrorMessage(null);
            }

            @Override
            public void editorValueChanged(boolean oldValidState,
                    boolean newValidState) {
                setErrorMessage(idCellEditor.getErrorMessage());
            }
        });

        idCellEditor.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                if (OpenConfiguratorProjectUtils.isPathIdAlreadyPresent(
                        pathSettingsModel, (String) value)) {
                    return ((String) value + " already present.");
                }

                if (((String) value).isEmpty()) {
                    return (ModifyPathSettingsDialog.SETTINGS_ID_EMPTY_VALUES_NOT_ALLOWED);
                }

                return null;
            }
        });

        return idCellEditor;
    }

    /**
     * Return the initial size of the dialog.
     */
    @Override
    protected Point getInitialSize() {
        return new Point(650, 300);
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
     * Returns true to set the dialog re-sizable always.
     */
    @Override
    protected boolean isResizable() {
        return true;
    }

    /**
     * Removes the {@link TPath} at the specified position in this list
     * (optional operation). Additionally this function resets the
     * {@link TProjectConfiguration.PathSettings.activePathSetting} if the
     * current activePathSetting element is removed.
     *
     * @param index The index of the Path element to be removed
     * @return <code>true</code> if the element is successfully removed.
     *         <code>false</code> otherwise.
     */
    private boolean removePath(final int index) {
        List<TPath> pathList = pathSettingsModel.getPath();

        String pathIdToBeRemoved = null;
        try {
            TPath pathTobeRemoved = pathList.get(index);
            pathIdToBeRemoved = pathTobeRemoved.getId();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.err.println(MessageFormat
                    .format(ModifyPathSettingsDialog.REMOVE_PATH_ERROR, index));
            return false;
        }

        if ((pathIdToBeRemoved == null) || (pathIdToBeRemoved.isEmpty())) {
            System.err.println(MessageFormat
                    .format(ModifyPathSettingsDialog.REMOVE_PATH_ERROR, index));
            return false;
        }

        pathList.remove(index); // No need to return the removed element.

        if (pathIdToBeRemoved
                .equalsIgnoreCase(pathSettingsModel.getActivePath())) {
            // The item about to be removed is same as the activePathSetting.
            // Update the activePathSetting setting to 0, then remove it.
            pathSettingsModel.setActivePath((pathList.get(0).getId()).trim());
        }

        dirty = true;
        return true;
    }

}
