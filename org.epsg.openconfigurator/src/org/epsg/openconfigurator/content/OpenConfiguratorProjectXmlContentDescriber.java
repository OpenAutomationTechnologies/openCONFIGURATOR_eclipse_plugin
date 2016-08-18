/*******************************************************************************
 * @file   OpenconfiguratorProjectXmlContentDescriber.java
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

package org.epsg.openconfigurator.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.content.IContentDescriber;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.XMLContentDescriber;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectMarshaller;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A content describer for openCONFIGURATOR project XML files. This class
 * provides basis for openCONFIGURATOR project XML file-based content
 * describers.
 *
 * @author Ramakrishnan P
 *
 * @see org.eclipse.core.runtime.content.IContentDescriber
 * @see org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject
 */
public class OpenConfiguratorProjectXmlContentDescriber
        extends XMLContentDescriber {

    /**
     *
     * @param contents The contents of the openCONIGURATOR project XML file.
     *
     * @return Code. IContentDescriber.VALID if the file is valid XML schema
     *         otherwise IContentDescriber.INVALID
     *
     * @throws IOException
     */
    private static int validateSchema(final InputSource contents)
            throws IOException {

        if ((contents == null) || (contents.getByteStream() == null)) {
            return IContentDescriber.INVALID;
        }

        try {
            OpenConfiguratorProjectMarshaller.unmarshallOpenConfiguratorProject(
                    contents.getByteStream());
        } catch (JAXBException | SAXException
                | ParserConfigurationException e) {
            // TODO: print in the Console of the Target eclipse application.
            e.printStackTrace();
            return IContentDescriber.INVALID;
        }

        return IContentDescriber.VALID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describe(InputStream contents, IContentDescription description)
            throws IOException {
        // Validate for basic XML describer.
        if (super.describe(contents,
                description) == IContentDescriber.INVALID) {
            return IContentDescriber.INVALID;
        }

        // Reset the contents updated by the super classes
        contents.reset();

        // Validate the openCONFIGURATOR project with the schema.
        return validateSchema(new InputSource(contents));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describe(Reader contents, IContentDescription description)
            throws IOException {
        // Validate for basic XML describer.
        if (super.describe(contents,
                description) == IContentDescriber.INVALID) {
            return IContentDescriber.INVALID;
        }

        // Reset the contents updated by the super classes
        contents.reset();

        // Validate the openCONFIGURATOR project with the schema.
        return validateSchema(new InputSource(contents));
    }
}
