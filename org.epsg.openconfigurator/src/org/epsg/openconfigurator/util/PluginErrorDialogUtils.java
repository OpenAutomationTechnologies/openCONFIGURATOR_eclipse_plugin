/*******************************************************************************
 * @file   PluginUtils.java
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

package org.epsg.openconfigurator.util;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.epsg.openconfigurator.Activator;

/**
 * Utility class for the openCONFIGURATOR plugin for the error dialogs.
 *
 * @author Ramakrishnan P
 *
 */
public final class PluginErrorDialogUtils {

    public static final String INTERNAL_ERROR_MESSAGE = "An internal error occurred";

    /**
     * Displays an error dialog with a given error-message.
     *
     * @param parent Parent window for the dialog.
     * @param errorMessage Error message to be shown in the dialog.
     * @param exception Exception instance if any, can be null.
     * @return the code of the button that was pressed that resulted in this
     *         dialog closing. This will be Dialog.OK if the OK button was
     *         pressed, or Dialog.CANCEL if this dialog's close window
     *         decoration or the ESC key was used.
     */
    public static int displayErrorMessageDialog(final Shell parent,
            final String errorMessage, final Throwable exception) {
        IStatus errorStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 1,
                errorMessage, exception);
        return ErrorDialog.openError(parent,
                PluginErrorDialogUtils.INTERNAL_ERROR_MESSAGE, null,
                errorStatus);
    }
}
