/*******************************************************************************
 * @file   ModuleAddressVerifyListener.java
 *
 * @author Sree Hari Vignesh, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2016, Kalycito Infotech Private Limited
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

package org.epsg.openconfigurator.validation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

/**
 * Describes the verification of address for module.
 *
 * @author SreeHari
 *
 */
public class ModuleAddressVerifyListener implements VerifyListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.VerifyListener#verifyText(org.eclipse.swt.events.
     * VerifyEvent)
     */
    @Override
    public void verifyText(VerifyEvent event) {
        // Assume we don't allow it
        event.doit = false;

        // Get the character typed
        char inputChar = event.character;

        String text = ((Text) event.widget).getText();

        // Disallow '-' or ' ' as the first character
        if (((inputChar == ' ') || (inputChar == '-'))
                && (text.length() == 0)) {
            return;
        }

        // Allow 0-9
        if (Character.isDigit(inputChar)) {
            event.doit = true;
            return;
        }

        if ((inputChar == '-') || (inputChar == '_')) {
            event.doit = true;
            return;
        }

        // Allow space
        if (Character.isSpaceChar(inputChar)) {
            event.doit = true;
            return;
        }

        // Allow arrow keys and backspace and delete keys
        if ((inputChar == SWT.BS) || (inputChar == SWT.ARROW_LEFT)
                || (inputChar == SWT.ARROW_RIGHT) || (inputChar == SWT.DEL)) {
            event.doit = true;
            return;
        }
    }

}
