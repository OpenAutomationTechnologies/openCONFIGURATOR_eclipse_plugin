package org.epsg.openconfigurator.validation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

public class ModuleAddressVerifyListener implements VerifyListener {

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
