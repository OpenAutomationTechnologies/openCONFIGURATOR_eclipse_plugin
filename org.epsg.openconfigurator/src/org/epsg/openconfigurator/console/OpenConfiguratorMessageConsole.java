/*******************************************************************************
 * @file   OpenConfiguratorMessageConsole.java
 *
 * @brief  Message console provides a console based view which integrates into
 *         eclipse.
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
package org.epsg.openconfigurator.console;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;

/**
 * Displays the error or info or library message in the console view.
 *
 *
 * @author Ramakrishnan P
 *
 */
public class OpenConfiguratorMessageConsole {

    private static SimpleDateFormat sdfDate = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private static OpenConfiguratorMessageConsole console;

    static {
        console = new OpenConfiguratorMessageConsole();
    }

    /**
     * Gets the console instance from the target eclipse application.
     *
     * @param name The name of the console.
     * @return The MessageConsole instance.
     */
    private static synchronized MessageConsole findConsole(String name) {

        // Returns instance of ConsolePlugin
        ConsolePlugin plugin = ConsolePlugin.getDefault();

        // Returns the consoleManager
        IConsoleManager conMan = plugin.getConsoleManager();

        // Returns collection of consoles
        IConsole[] existing = conMan.getConsoles();

        for (IConsole element : existing) {
            if (name.equals(element.getName())) {
                return (MessageConsole) element;
            }
        }

        // No console found, so create a new one
        MessageConsole myConsole = new MessageConsole(name, null);

        // Adds new console
        conMan.addConsoles(new IConsole[] { myConsole });
        return myConsole;
    }

    private static synchronized String getCurrentTime() {
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static OpenConfiguratorMessageConsole getInstance() {
        return console;
    }

    /**
     * List of available console streams
     */
    private MessageConsoleStream infoMessageConsole;

    private MessageConsoleStream errorMessageConsole;

    private MessageConsoleStream libraryMessageConsole;

    private OpenConfiguratorMessageConsole() {
    }

    private MessageConsoleStream getErrorMessageStream() {

        if (errorMessageConsole == null) {
            MessageConsole myConsole = findConsole(
                    org.epsg.openconfigurator.Activator.PLUGIN_ID);
            errorMessageConsole = myConsole.newMessageStream();
            errorMessageConsole.setActivateOnWrite(true);
            errorMessageConsole
                    .setColor(new Color(Display.getDefault(), 255, 0, 0));
        }
        return errorMessageConsole;
    }

    private MessageConsoleStream getInfoMessageStream() {

        if (infoMessageConsole == null) {
            MessageConsole myConsole = findConsole(
                    org.epsg.openconfigurator.Activator.PLUGIN_ID);
            infoMessageConsole = myConsole.newMessageStream();
            infoMessageConsole.setActivateOnWrite(true);
        }
        return infoMessageConsole;
    }

    private MessageConsoleStream getLibraryMessageStream() {

        if (libraryMessageConsole == null) {

            MessageConsole myConsole = findConsole(
                    org.epsg.openconfigurator.Activator.PLUGIN_ID + ".core");
            libraryMessageConsole = myConsole.newMessageStream();
            libraryMessageConsole
                    .setColor(new Color(Display.getDefault(), 255, 0, 0));
            // Avoid switching into the library log while library messages are
            // displayed.
            libraryMessageConsole.setActivateOnWrite(false);
        }
        return libraryMessageConsole;
    }

    /**
     * Displays the given message in the error console.
     *
     * @param message The message to be updated.
     * @param projectName The name of the project to be updated.
     *
     */
    public void printErrorMessage(final String message,
            final String projectName) {
        String fullMessage = "[" + getCurrentTime() + "] [ERROR] " + "["
                + projectName + "] " + message;
        MessageConsoleStream out = getErrorMessageStream();
        out.println(fullMessage);
        try {
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Prints the given message in the info console.
     *
     * @param message The message to be updated.
     * @param projectName The name of the project to be updated.
     *
     */
    public void printInfoMessage(final String message,
            final String projectName) {
        String fullMessage = "[" + getCurrentTime() + "] [INFO] " + "["
                + projectName + "] " + message;
        MessageConsoleStream out = getInfoMessageStream();
        out.println(fullMessage);
        try {
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Displays the given library message in the error console.
     *
     * @param res The result from openCONFIGURATOR library.
     */
    public void printLibraryErrorMessage(final Result res) {

        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                String message = OpenConfiguratorLibraryUtils
                        .getErrorMessage(res);
                String fullMessage = "[" + getCurrentTime() + "] [ERROR] "
                        + message;
                MessageConsoleStream out = getErrorMessageStream();
                out.println(fullMessage);
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Prints the error / info / warn messages from the library.
     *
     * @param message The message to be updated.
     */
    public void printLibraryMessage(final String message) {
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {

                MessageConsoleStream out = getLibraryMessageStream();
                out.println(message);
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
