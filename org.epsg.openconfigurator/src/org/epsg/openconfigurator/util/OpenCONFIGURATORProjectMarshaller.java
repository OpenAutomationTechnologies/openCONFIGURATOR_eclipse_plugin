/*******************************************************************************
 * @file   OpenCONFIGURATORProjectMarshaller.java
 *
 * @brief  Handles the marshelling an unmarshelling of the Project XML files.
 *
 * @author Christoph Ruecker, Bernecker + Rainer Industrie-Elektronik Ges.m.b.H
 *         Ramakrishnan Periyakaruppan, Kalycito Infotech Private Limited.
 *
 * @since 08.04.2013
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *  Handles the marshelling an unmarshelling of the Project XML files.
 */
public final class OpenCONFIGURATORProjectMarshaller {

  private static Schema projectSchema;
  public static final String NAMESPACE_OC_URI = "http://sourceforge.net/projects/openconf/configuration"; ////$NON-NLS-1$
  public static final String NAMESPACE_XSI_LOCATION = NAMESPACE_OC_URI + " openCONFIGURATOR.xsd"; ////$NON-NLS-1$

  static {
    projectSchema = null;
    URI projectSchemaPath = org.epsg.openconfigurator.Activator.getProjectSchemaFile();
    if (projectSchemaPath != null) {
      try {
        File projectSchemaFile = new File(projectSchemaPath);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        projectSchema = schemaFactory.newSchema(projectSchemaFile);
      } catch (SAXException e) {
        e.printStackTrace();
      }
    }
  }

  public static String marshallopenCONFIGURATORProject(final OpenCONFIGURATORProject base)
      throws JAXBException {
    StringWriter writer = new StringWriter();
    final JAXBContext jc = JAXBContext.newInstance(OpenCONFIGURATORProject.class);
    final Marshaller marshaller = jc.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, NAMESPACE_XSI_LOCATION);
    marshaller.marshal(base, writer);
    if (projectSchema != null)
      marshaller.setSchema(projectSchema);

    return writer.toString();
  }

  /**
   * @brief Static method to marshall an openCONFIGURATOR core class structure into a file
   * @param base OpenCONFIGURATOR Project file to marshall
   * @param file File to save the marshalled content into
   * @throws JAXBException
   */
  public static void marshallopenCONFIGURATORProject(final OpenCONFIGURATORProject base,
      final File file) throws JAXBException {
    final JAXBContext jc = JAXBContext.newInstance(OpenCONFIGURATORProject.class);
    final Marshaller marshaller = jc.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, NAMESPACE_XSI_LOCATION);
    marshaller.marshal(base, file);
    if (projectSchema != null)
      marshaller.setSchema(projectSchema);
  }

  /**
   * @brief Static method for unmarshalling a openCONFIGURATOR project xml file into a class
   *        structure
   * @param file File to unmarshall
   * @return openCONFIGURATOR object with the unmarshalled content
   * @throws JAXBException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws FileNotFoundException
   * @throws MalformedURLException
   */
  public static OpenCONFIGURATORProject unmarshallopenCONFIGURATORProject(final File file)
      throws JAXBException, SAXException, ParserConfigurationException, FileNotFoundException,
      MalformedURLException {
    final JAXBContext jc = JAXBContext.newInstance(OpenCONFIGURATORProject.class);
    final SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setXIncludeAware(true);
    spf.setNamespaceAware(true);

    final XMLReader xr = spf.newSAXParser().getXMLReader();
    final InputSource input = new InputSource(new FileInputStream(file));
    input.setSystemId(file.toURI().toString());
    final SAXSource source = new SAXSource(xr, input);

    final Unmarshaller unmarshaller = jc.createUnmarshaller();
    if (projectSchema != null)
      unmarshaller.setSchema(projectSchema);
    final OpenCONFIGURATORProject osddFile = (OpenCONFIGURATORProject) unmarshaller
        .unmarshal(source);

    return osddFile;
  }

  public static OpenCONFIGURATORProject unmarshallopenCONFIGURATORProject(final InputStream file)
      throws JAXBException, SAXException, ParserConfigurationException, FileNotFoundException,
      MalformedURLException {
    final JAXBContext jc = JAXBContext.newInstance(OpenCONFIGURATORProject.class);
    final SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setXIncludeAware(true);
    spf.setNamespaceAware(true);

    final XMLReader xr = spf.newSAXParser().getXMLReader();
    final InputSource input = new InputSource(file);
    final SAXSource source = new SAXSource(xr, input);

    final Unmarshaller unmarshaller = jc.createUnmarshaller();
    if (projectSchema != null)
      unmarshaller.setSchema(projectSchema);
    final OpenCONFIGURATORProject osddFile = (OpenCONFIGURATORProject) unmarshaller
        .unmarshal(source);

    return osddFile;
  }

  /**
   * @brief Private constructor to disable the instantiation
   */
  private OpenCONFIGURATORProjectMarshaller() {

  }

}