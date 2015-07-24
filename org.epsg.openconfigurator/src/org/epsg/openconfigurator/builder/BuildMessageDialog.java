/*******************************************************************************
 * @file   BuildMessageDialog.java
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

package org.epsg.openconfigurator.builder;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Message dialog to display the build output.
 *
 * @author Ramakrishnan P
 *
 */
class BuildMessageDialog extends MessageDialog {

    /**
     * Flag to remember the user preferred path check box selection state.
     */
    static private boolean showOutputPath = true;

    /**
     * Dialog message
     */
    private String dialogMessage;

    /**
     * Create the build message dialog.
     *
     * @param parentShell
     */
    public BuildMessageDialog(Shell parentShell, String dialogTitle,
            Image dialogTitleImage, String dialogMessage) {
        super(parentShell, dialogTitle, dialogTitleImage, dialogMessage, 0,
                null, 0);
        this.dialogMessage = dialogMessage;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, "OK", true);
    }

    /**
     * Create contents of the build message dialog.
     *
     * @param parent The parent composite.
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new FillLayout(SWT.VERTICAL));

        Composite composite = new Composite(container,
                SWT.BORDER | SWT.EMBEDDED);
        composite.setTouchEnabled(true);
        composite.setLayout(new GridLayout(1, false));

        Label lblDialogMessage = new Label(composite, SWT.WRAP);
        lblDialogMessage.setText(dialogMessage);

        new Label(composite, SWT.NONE);

        Button btnChkShowOutputPath = new Button(composite, SWT.CHECK);
        btnChkShowOutputPath.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                showOutputPath = !showOutputPath;
            }
        });
        btnChkShowOutputPath.setSelection(showOutputPath);
        btnChkShowOutputPath.setText("Open output location in file browser");
        return container;
    }

    /**
     * Return the initial size of the dialog.
     */
    @Override
    protected Point getInitialSize() {
        return new Point(500, 250);
    }

    /**
     * @return <code>True</code> if the path needs to be open in the System
     *         explorer, <code>False</code> otherwise.
     */
    boolean isOutputPathToBeShown() {
        return showOutputPath;
    }

}
