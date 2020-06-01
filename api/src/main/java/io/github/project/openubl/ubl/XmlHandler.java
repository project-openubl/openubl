/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 * <p>
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.eclipse.org/legal/epl-2.0/
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.ubl;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler extends DefaultHandler {

    private static final String CBC = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";
    private static final String CAC = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";

    private static final String ID = "ID";

    private String documentType;
    private String documentID;

    private String currentElement;
    private StringBuilder currentText;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attr) throws SAXException {
        //
        currentElement = localName;

        // Root element
        if (documentType == null) {
            documentType = currentElement;
        }

        //
        if (currentElement.equals(ID) && uri.equals(CBC)) {
            currentText = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currentText != null) {
            String content = currentText.toString().trim();

            if (currentElement.equals(ID) && uri.equals(CBC)) {
                if (documentID == null) {
                    documentID = content;
                }
            }

            currentText = null;
        }

        currentElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentText != null) {
            currentText.append(ch, start, length);
        }
    }

    public XmlContentModel getModel() {
        return XmlContentModel.Builder.aSunatDocumentModel()
                .withDocumentType(documentType)
                .withDocumentID(documentID)
                .build();
    }

}
