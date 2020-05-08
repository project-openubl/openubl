/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openubl.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

public class XmlSignatureHelper {

    private XmlSignatureHelper() {
        // Helper class
    }

    public static DocumentBuilder newDocumentBuilder(Boolean disallowDoctypeDecl) throws ParserConfigurationException {
        return newDocumentBuilder(disallowDoctypeDecl, null);
    }

    public static DocumentBuilder newDocumentBuilder(Boolean disallowDoctypeDecl, Schema schema) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setValidating(false);
        // avoid external entity attacks
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        boolean isDissalowDoctypeDecl = disallowDoctypeDecl == null ? true : disallowDoctypeDecl;
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", isDissalowDoctypeDecl);
        // avoid overflow attacks
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        if (schema != null) {
            dbf.setSchema(schema);
        }

        return dbf.newDocumentBuilder();
    }

    public static Document convertStringToXMLDocument(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        //API to obtain DOM Document instance
        DocumentBuilder builder = XmlSignatureHelper.newDocumentBuilder(true);
        //Parse the content to Document object
        return builder.parse(new InputSource(new StringReader(xmlString)));
    }

}
