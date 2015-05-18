/*******************************************************************************
 * @file   XMLDoubleClickStrategy.java
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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;

/**
 * A strategy class to handle the double click events to select the XML texts.
 *
 * @author Ramakrishnan P
 *
 */
public class XMLDoubleClickStrategy implements ITextDoubleClickStrategy {

  /**
   * The text viewer
   */
  protected ITextViewer fText;

  /**
   * {@inheritDoc}
   */
  @Override
  public void doubleClicked(ITextViewer part) {
    int pos = part.getSelectedRange().x;

    if (pos < 0)
      return;

    fText = part;

    if (!selectComment(pos)) {
      selectWord(pos);
    }
  }

  /**
   * Select the comment section in the XML file.
   *
   * @param caretPos The position of the cursor.
   * @return true if no error and false otherwise.
   */
  protected boolean selectComment(int caretPos) {
    IDocument doc = fText.getDocument();
    int startPos, endPos;

    try {
      int pos = caretPos;
      char c = ' ';

      while (pos >= 0) {
        c = doc.getChar(pos);
        if (c == '\\') {
          pos -= 2;
          continue;
        }
        if ((c == Character.LINE_SEPARATOR) || (c == '\"'))
          break;
        --pos;
      }

      if (c != '\"')
        return false;

      startPos = pos;

      pos = caretPos;
      int length = doc.getLength();
      c = ' ';

      while (pos < length) {
        c = doc.getChar(pos);
        if ((c == Character.LINE_SEPARATOR) || (c == '\"'))
          break;
        ++pos;
      }
      if (c != '\"')
        return false;

      endPos = pos;

      int offset = startPos + 1;
      int len = endPos - offset;
      fText.setSelectedRange(offset, len);
      return true;
    } catch (BadLocationException x) {
    }

    return false;
  }

  /**
   * Select the text section available in the viewer for the given start position and stop position.
   *
   * @param startPos Start position for the cursor.
   * @param stopPos Stop position for the cursor.
   */
  private void selectRange(int startPos, int stopPos) {
    int offset = startPos + 1;
    int length = stopPos - offset;
    fText.setSelectedRange(offset, length);
  }

  /**
   * Select a word in the given cursor position.
   *
   * @param caretPos The position of the cursor.
   * @return true if no error and false otherwise.
   */
  protected boolean selectWord(int caretPos) {

    IDocument doc = fText.getDocument();
    int startPos, endPos;

    try {

      int pos = caretPos;
      char c;

      while (pos >= 0) {
        c = doc.getChar(pos);
        if (!Character.isJavaIdentifierPart(c))
          break;
        --pos;
      }

      startPos = pos;

      pos = caretPos;
      int length = doc.getLength();

      while (pos < length) {
        c = doc.getChar(pos);
        if (!Character.isJavaIdentifierPart(c))
          break;
        ++pos;
      }

      endPos = pos;
      selectRange(startPos, endPos);
      return true;

    } catch (BadLocationException x) {
    }

    return false;
  }
}