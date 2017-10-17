/*******************************************************************************
 * @file   XddMarshaller.java
 *
 * @author Christoph Ruecker, B&R Industrial Automation GmbH
 *         Ramakrishnan Periyakaruppan, Kalycito Infotech Private Limited.
 *
 * @since 08.04.2013
 *
 * @copyright (c) 2015, Kalycito Infotech Private Limited,
 *                      B&R Industrial Automation GmbH
 *                      All rights reserved.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;
import org.epsg.openconfigurator.resources.IOpenConfiguratorResource;
import org.epsg.openconfigurator.xmlbinding.firmware.Firmware;
import org.epsg.openconfigurator.xmlbinding.xap.ApplicationProcess;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Handles the marshelling an unmarshelling of the XDD/XDC files.
 *
 * @author Ramakrishnan P
 *
 */
public final class XddMarshaller {
    private static Schema xddSchema;
    private static Schema firmwareSchema;
    private static Schema xapSchema;

    private static final String XDD_SCHEMA_NOT_FOUND = "openCONFIGURATOR project XML schema not found.";
    private static final String XDD_SCHEMA_INVALID = "openCONFIGURATOR project XML schema has errors.";
    private static final String XAP_SCHEMA_INVALID = "XAP XML schema has errors.";

    static {
        XddMarshaller.xddSchema = null;
        XddMarshaller.firmwareSchema = null;
        XddMarshaller.xapSchema = null;

        String xddSchemaPath = null;
        String firmwareSchemaPath = null;
        String xapSchemaPath = null;

        try {
            xddSchemaPath = org.epsg.openconfigurator.Activator
                    .getAbsolutePath(IOpenConfiguratorResource.XDD_SCHEMA);
        } catch (IOException exception) {
            exception.printStackTrace();

            PluginErrorDialogUtils.displayErrorMessageDialog(
                    XddMarshaller.XDD_SCHEMA_NOT_FOUND, exception);
        }

        try {
            firmwareSchemaPath = org.epsg.openconfigurator.Activator
                    .getAbsolutePath(IOpenConfiguratorResource.FIRMWARE_SCHEMA);
        } catch (IOException exception) {
            exception.printStackTrace();

            PluginErrorDialogUtils.displayErrorMessageDialog(
                    XddMarshaller.XDD_SCHEMA_NOT_FOUND, exception);
        }

        try {
            xapSchemaPath = org.epsg.openconfigurator.Activator
                    .getAbsolutePath(IOpenConfiguratorResource.XAP_SCHEMA);
        } catch (IOException exception) {
            exception.printStackTrace();

            PluginErrorDialogUtils.displayErrorMessageDialog(
                    XddMarshaller.XAP_SCHEMA_INVALID, exception);
        }

        if (xddSchemaPath != null) {
            try {
                File xddSchemaFile = new File(xddSchemaPath);
                SchemaFactory schemaFactory = SchemaFactory
                        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                XddMarshaller.xddSchema = schemaFactory
                        .newSchema(xddSchemaFile);
            } catch (SAXException e) {
                e.printStackTrace();
                PluginErrorDialogUtils.displayErrorMessageDialog(
                        XddMarshaller.XDD_SCHEMA_INVALID, e);
            }
        }

        if (xapSchemaPath != null) {
            try {
                File xapSchemaFile = new File(xapSchemaPath);
                SchemaFactory schemaFactory = SchemaFactory
                        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                XddMarshaller.xapSchema = schemaFactory
                        .newSchema(xapSchemaFile);
            } catch (SAXException e) {
                e.printStackTrace();
                PluginErrorDialogUtils.displayErrorMessageDialog(
                        XddMarshaller.XAP_SCHEMA_INVALID, e);
            }
        }

        if (firmwareSchemaPath != null) {
            try {
                File firmwareSchemaFile = new File(firmwareSchemaPath);
                SchemaFactory schemaFactory = SchemaFactory
                        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                XddMarshaller.firmwareSchema = schemaFactory
                        .newSchema(firmwareSchemaFile);
            } catch (SAXException e) {
                e.printStackTrace();
                PluginErrorDialogUtils.displayErrorMessageDialog(
                        XddMarshaller.XDD_SCHEMA_INVALID, e);
            }
        }

    }

    private static Firmware unmarshallFirmware(final InputSource inputSource)
            throws JAXBException, SAXException, ParserConfigurationException {
        final JAXBContext jc = JAXBContext.newInstance(Firmware.class);
        final SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setXIncludeAware(true);
        spf.setNamespaceAware(true);
        final XMLReader xr = spf.newSAXParser().getXMLReader();
        final SAXSource source = new SAXSource(xr, inputSource);

        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        if (XddMarshaller.firmwareSchema != null) {
            unmarshaller.setSchema(XddMarshaller.firmwareSchema);
        }

        final Firmware firmwareFile = (Firmware) unmarshaller.unmarshal(source);

        return firmwareFile;
    }

    /**
     * Un-marshalls the contents of the input stream into the
     * {@link ISO15745ProfileContainer} instance
     *
     * @param file The XDD/XDC file input stream.
     *
     * @return The XDD/XDC instance.
     *
     * @throws JAXBException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws FileNotFoundException
     */
    public static Firmware unmarshallFirmware(final InputStream file)
            throws JAXBException, SAXException, ParserConfigurationException,
            FileNotFoundException {
        final InputSource input = new InputSource(file);
        return unmarshallFirmware(input);
    }

    /**
     * Un-marshalls the contents of the input stream into the
     * {@link ISO15745ProfileContainer} instance
     *
     * @param file The XDD/XDC file.
     * @return The XDD/XDC instance.
     * @throws JAXBException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    @SuppressWarnings("finally")
    public static Firmware unmarshallFirmwareFile(final File file)
            throws JAXBException, SAXException, ParserConfigurationException,
            IOException {

        InputSource input = null;
        BufferedReader bufferedRdr = null;

        try {

            bufferedRdr = new BufferedReader(new FileReader(file));
            String firmwareline = StringUtils.EMPTY;
            int linesInFile = 3;
            for (int count = 0; count < linesInFile; count++) {
                firmwareline += bufferedRdr.readLine();
                if (firmwareline.contains("/>")) {
                    break; // breaks the loop if firmware header is closed
                }
            }

            int firmwareStrtIndex = firmwareline.indexOf("<");
            int firmwareEndIndex = firmwareline.indexOf(">");
            // Receives only the header of firmware file.
            String firmwareHeader = firmwareline.substring(firmwareStrtIndex,
                    firmwareEndIndex + 1);
            input = new InputSource(new StringReader(firmwareHeader));
            input.setSystemId(file.toURI().toString());
            // return unmarshallFirmware(input);
        } catch (RuntimeException e) {
            if (bufferedRdr != null) {
                bufferedRdr.close();
            }
            throw e;
        } catch (Exception e) {
            if (bufferedRdr != null) {
                bufferedRdr.close();
            }
            e.printStackTrace();
        } finally {
            if (bufferedRdr != null) {
                bufferedRdr.close();
            }
            return unmarshallFirmware(input);
        }
    }

    private static ApplicationProcess unmarshallXap(
            final InputSource inputSource)
            throws JAXBException, SAXException, ParserConfigurationException {
        final JAXBContext jc = JAXBContext
                .newInstance(ApplicationProcess.class);
        final SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setXIncludeAware(true);
        spf.setNamespaceAware(true);
        final XMLReader xr = spf.newSAXParser().getXMLReader();
        final SAXSource source = new SAXSource(xr, inputSource);

        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        if (XddMarshaller.xapSchema != null) {
            unmarshaller.setSchema(XddMarshaller.xapSchema);
        }

        final ApplicationProcess xapFile = (ApplicationProcess) unmarshaller
                .unmarshal(source);

        return xapFile;
    }

    /**
     * Un-marshalls the contents of the input stream into the
     * {@link ApplicationProcess} instance
     *
     * @param file The XAP file input stream.
     *
     * @return The XAP instance.
     *
     * @throws JAXBException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws FileNotFoundException
     */
    public static ApplicationProcess unmarshallXap(final InputStream file)
            throws JAXBException, SAXException, ParserConfigurationException,
            FileNotFoundException {
        final InputSource input = new InputSource(file);
        return unmarshallXap(input);
    }

    /**
     * Un-marshalls the contents of the input stream into the
     * {@link ApplicationProcess} instance
     *
     * @param file The XAP file.
     * @return The Application process instance.
     * @throws JAXBException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    public static ApplicationProcess unmarshallXapFile(final File file)
            throws JAXBException, SAXException, ParserConfigurationException,
            IOException {

        BOMInputStream bomIn = null;
        Reader reader = null;
        InputSource input = null;

        try {
            bomIn = new BOMInputStream(new FileInputStream(file), false);
            reader = new InputStreamReader(bomIn);
            input = new InputSource(reader);
            input.setSystemId(file.toURI().toString());
            return unmarshallXap(input);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            if (bomIn != null) {
                bomIn.close();
            }
            e.printStackTrace();
        } finally {
            if (bomIn != null) {
                bomIn.close();
            }
        }

        return unmarshallXap(input);
    }

    private static ISO15745ProfileContainer unmarshallXDD(
            final InputSource inputSource)
            throws JAXBException, SAXException, ParserConfigurationException {
        final JAXBContext jc = JAXBContext
                .newInstance(ISO15745ProfileContainer.class);
        final SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setXIncludeAware(true);
        spf.setNamespaceAware(true);
        final XMLReader xr = spf.newSAXParser().getXMLReader();
        final SAXSource source = new SAXSource(xr, inputSource);

        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        if (XddMarshaller.xddSchema != null) {
            unmarshaller.setSchema(XddMarshaller.xddSchema);
        }

        final ISO15745ProfileContainer xddFile = (ISO15745ProfileContainer) unmarshaller
                .unmarshal(source);

        return xddFile;
    }

    /**
     * Un-marshalls the contents of the input stream into the
     * {@link ISO15745ProfileContainer} instance
     *
     * @param file The XDD/XDC file input stream.
     *
     * @return The XDD/XDC instance.
     *
     * @throws JAXBException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws FileNotFoundException
     */
    public static ISO15745ProfileContainer unmarshallXDD(final InputStream file)
            throws JAXBException, SAXException, ParserConfigurationException,
            FileNotFoundException {
        final InputSource input = new InputSource(file);
        return unmarshallXDD(input);
    }

    /**
     * Un-marshalls the contents of the input stream into the
     * {@link ISO15745ProfileContainer} instance
     *
     * @param file The XDD/XDC file.
     * @return The XDD/XDC instance.
     * @throws JAXBException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    public static ISO15745ProfileContainer unmarshallXDDFile(final File file)
            throws JAXBException, SAXException, ParserConfigurationException,
            IOException {

        BOMInputStream bomIn = null;
        Reader reader = null;
        InputSource input = null;

        try {
            bomIn = new BOMInputStream(new FileInputStream(file), false);
            reader = new InputStreamReader(bomIn);
            input = new InputSource(reader);
            input.setSystemId(file.toURI().toString());
            return unmarshallXDD(input);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            if (bomIn != null) {
                bomIn.close();
            }
            e.printStackTrace();
        } finally {
            if (bomIn != null) {
                bomIn.close();
            }
        }

        return unmarshallXDD(input);

    }

    /**
     * Private constructor to disable the instantiation
     */
    private XddMarshaller() {

    }
}
