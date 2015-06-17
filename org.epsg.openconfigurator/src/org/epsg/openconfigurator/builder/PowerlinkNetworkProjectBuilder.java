/*******************************************************************************
 * @file   PowerlinkNetworkProjectBuilder.java
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

import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class PowerlinkNetworkProjectBuilder extends IncrementalProjectBuilder {

    class SampleDeltaVisitor implements IResourceDeltaVisitor {
        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse
         * .core.resources.IResourceDelta)
         */
        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            IResource resource = delta.getResource();
            switch (delta.getKind()) {
                case IResourceDelta.ADDED:
                    // handle added resource
                    checkXML(resource);
                    break;
                case IResourceDelta.REMOVED:
                    // handle removed resource
                    break;
                case IResourceDelta.CHANGED:
                    // handle changed resource
                    checkXML(resource);
                    break;
            }
            // return true to continue visiting children.
            return true;
        }
    }

    class SampleResourceVisitor implements IResourceVisitor {
        @Override
        public boolean visit(IResource resource) {
            checkXML(resource);
            // return true to continue visiting children.
            return true;
        }
    }

    class XMLErrorHandler extends DefaultHandler {

        private IFile file;

        public XMLErrorHandler(IFile file) {
            this.file = file;
        }

        private void addMarker(SAXParseException e, int severity) {
            PowerlinkNetworkProjectBuilder.this.addMarker(file, e.getMessage(),
                    e.getLineNumber(), severity);
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            this.addMarker(exception, IMarker.SEVERITY_ERROR);
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            this.addMarker(exception, IMarker.SEVERITY_ERROR);
        }

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            this.addMarker(exception, IMarker.SEVERITY_WARNING);
        }
    }

    public static final String BUILDER_ID = "org.epsg.openconfigurator.industrialNetworkBuilder";

    private static final String MARKER_TYPE = "org.epsg.openconfigurator.xmlProblem";

    private SAXParserFactory parserFactory;

    private void addMarker(IFile file, String message, int lineNumber,
            int severity) {
        try {
            IMarker marker = file
                    .createMarker(PowerlinkNetworkProjectBuilder.MARKER_TYPE);
            marker.setAttribute(IMarker.MESSAGE, message);
            marker.setAttribute(IMarker.SEVERITY, severity);
            if (lineNumber == -1) {
                lineNumber = 1;
            }
            marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
        } catch (CoreException e) {
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
     * java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
            throws CoreException {
        System.out.println("Builder executed");
        if (kind == IncrementalProjectBuilder.FULL_BUILD) {
            fullBuild(monitor);
        } else {
            IResourceDelta delta = getDelta(getProject());
            if (delta == null) {
                fullBuild(monitor);

            } else {
                incrementalBuild(delta, monitor);
            }
        }
        return null;
    }

    void checkXML(IResource resource) {
        if ((resource instanceof IFile) && resource.getName().endsWith(".xml")) {
            IFile file = (IFile) resource;
            deleteMarkers(file);
            XMLErrorHandler reporter = new XMLErrorHandler(file);
            try {
                getParser().parse(file.getContents(), reporter);
            } catch (Exception e1) {
            }
        }
    }

    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {
        // delete markers set and files created
        getProject().deleteMarkers(PowerlinkNetworkProjectBuilder.MARKER_TYPE,
                true, IResource.DEPTH_INFINITE);
    }

    private void deleteMarkers(IFile file) {
        try {
            file.deleteMarkers(PowerlinkNetworkProjectBuilder.MARKER_TYPE,
                    false, IResource.DEPTH_ZERO);
        } catch (CoreException ce) {
        }
    }

    protected void fullBuild(final IProgressMonitor monitor)
            throws CoreException {
        try {
            getProject().accept(new SampleResourceVisitor());
        } catch (CoreException e) {
        }
    }

    private SAXParser getParser() throws ParserConfigurationException,
            SAXException {
        if (parserFactory == null) {
            parserFactory = SAXParserFactory.newInstance();
        }
        return parserFactory.newSAXParser();
    }

    protected void incrementalBuild(IResourceDelta delta,
            IProgressMonitor monitor) throws CoreException {
        // the visitor does the work.
        delta.accept(new SampleDeltaVisitor());
    }
}
