/*******************************************************************************
 * @file   XMLConfiguration.java
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

package org.epsg.openconfigurator.editors.project.xml;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

/**
 * Provides the source view configuration for the XML files.
 *
 * @author Ramakrishnan P
 *
 */
public class XMLConfiguration extends SourceViewerConfiguration {

  private XMLDoubleClickStrategy doubleClickStrategy;
  private XMLTagScanner tagScanner;
  private XMLScanner scanner;
  private ColorManager colorManager;

  public XMLConfiguration(ColorManager colorManager) {
    this.colorManager = colorManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
    return new String[] { IDocument.DEFAULT_CONTENT_TYPE, XMLPartitionScanner.XML_COMMENT,
        XMLPartitionScanner.XML_TAG };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer,
      String contentType) {
    if (doubleClickStrategy == null)
      doubleClickStrategy = new XMLDoubleClickStrategy();
    return doubleClickStrategy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
    PresentationReconciler reconciler = new PresentationReconciler();

    DefaultDamagerRepairer defaultDamageRepairer = new DefaultDamagerRepairer(getXMLTagScanner());
    reconciler.setDamager(defaultDamageRepairer, XMLPartitionScanner.XML_TAG);
    reconciler.setRepairer(defaultDamageRepairer, XMLPartitionScanner.XML_TAG);

    defaultDamageRepairer = new DefaultDamagerRepairer(getXMLScanner());
    reconciler.setDamager(defaultDamageRepairer, IDocument.DEFAULT_CONTENT_TYPE);
    reconciler.setRepairer(defaultDamageRepairer, IDocument.DEFAULT_CONTENT_TYPE);

    NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(new TextAttribute(
        colorManager.getColor(IXMLColorConstants.XML_COMMENT)));
    reconciler.setDamager(ndr, XMLPartitionScanner.XML_COMMENT);
    reconciler.setRepairer(ndr, XMLPartitionScanner.XML_COMMENT);

    return reconciler;
  }

  /**
   * @return the XML scanner.
   */
  protected XMLScanner getXMLScanner() {
    if (scanner == null) {
      scanner = new XMLScanner(colorManager);
      scanner.setDefaultReturnToken(new Token(new TextAttribute(colorManager
          .getColor(IXMLColorConstants.DEFAULT))));
    }
    return scanner;
  }

  /**
   * @return the XML tag scanner.
   */
  protected XMLTagScanner getXMLTagScanner() {
    if (tagScanner == null) {
      tagScanner = new XMLTagScanner(colorManager);
      tagScanner.setDefaultReturnToken(new Token(new TextAttribute(colorManager
          .getColor(IXMLColorConstants.TAG))));
    }
    return tagScanner;
  }

}
