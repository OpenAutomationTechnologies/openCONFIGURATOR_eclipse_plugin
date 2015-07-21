/*******************************************************************************
 * @file   XddMarshaller.java
 *
 * @author Christoph Ruecker, Bernecker + Rainer Industrie-Elektronik Ges.m.b.H
 *         Ramakrishnan Periyakaruppan, Kalycito Infotech Private Limited.
 *
 * @since 08.04.2013
 *
 * @copyright (c) 2015, Kalycito Infotech Private Limited,
 *                      Bernecker + Rainer Industrie-Elektronik Ges.m.b.H
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.epsg.openconfigurator.model.IPowerlinkProjectSupport;
import org.epsg.openconfigurator.resources.IOpenConfiguratorResource;
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

    private static final String XDD_SCHEMA_NOT_FOUND = "openCONFIGURATOR project XML schema not found.";
    private static final String XDD_SCHEMA_INVALID = "openCONFIGURATOR project XML schema has errors.";

    static {
        XddMarshaller.xddSchema = null;
        String xddSchemaPath = null;
        try {
            xddSchemaPath = org.epsg.openconfigurator.Activator
                    .getAbsolutePath(IOpenConfiguratorResource.XDD_SCHEMA);
        } catch (IOException exception) {
            exception.printStackTrace();

            PluginErrorDialogUtils.displayErrorMessageDialog(
                    org.epsg.openconfigurator.Activator.getDefault()
                            .getWorkbench().getActiveWorkbenchWindow()
                            .getShell(),
                    XddMarshaller.XDD_SCHEMA_NOT_FOUND, exception);
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
                        org.epsg.openconfigurator.Activator.getDefault()
                                .getWorkbench().getActiveWorkbenchWindow()
                                .getShell(),
                        XddMarshaller.XDD_SCHEMA_INVALID, e);
            }
        }
    }

    private static ISO15745ProfileContainer unmarshallXDD(
            final InputSource inputSource) throws JAXBException, SAXException,
                    ParserConfigurationException {
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
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public static ISO15745ProfileContainer unmarshallXDDFile(final File file)
            throws JAXBException, SAXException, ParserConfigurationException,
            FileNotFoundException, UnsupportedEncodingException {

        Reader reader = new InputStreamReader(new FileInputStream(file),
                IPowerlinkProjectSupport.UTF8_ENCODING);
        final InputSource input = new InputSource(reader);
        input.setSystemId(file.toURI().toString());
        return unmarshallXDD(input);
    }

    /**
     * Private constructor to disable the instantiation
     */
    private XddMarshaller() {

    }
}
