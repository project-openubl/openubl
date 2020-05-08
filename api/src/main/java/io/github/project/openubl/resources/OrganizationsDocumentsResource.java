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
package io.github.project.openubl.resources;

import io.github.project.openubl.xmlbuilderlib.config.XMLBuilderConfig;
import io.github.project.openubl.xmlbuilderlib.facade.DocumentFacade;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import io.github.project.openubl.xmlbuilderlib.utils.SystemClock;
import org.keycloak.crypto.Algorithm;
import org.keycloak.crypto.KeyUse;
import org.keycloak.crypto.KeyWrapper;
import io.github.project.openubl.models.KeyManager;
import io.github.project.openubl.models.OrganizationModel;
import io.github.project.openubl.models.OrganizationProvider;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

@Path("/organizations/{" + OrganizationsDocumentsResource.ORGANIZATION_ID + "}/documents")
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationsDocumentsResource {

    static final String SIGN_REFERENCE_ID = "SignID";
    static final String ORGANIZATION_ID = "organizationId";

//    @Inject
//    KeyManager keystore;
//
//    @Inject
//    OrganizationProvider organizationProvider;
//
//    @Inject
//    XMLBuilderConfig xmlBuilderConfig;
//
//    @Inject
//    SystemClock systemClock;
//
//    private KeyManager.ActiveRsaKey getActiveRsaKey(OrganizationModel organization) {
//        KeyWrapper key;
//        if (organization.getUseCustomCertificates()) {
//            key = keystore.getActiveKey(organization, KeyUse.SIG, Algorithm.RS256);
//        } else {
//            OrganizationModel masterOrganization = organizationProvider.getOrganizationById(OrganizationModel.MASTER_ID)
//                    .orElseThrow(() -> new IllegalArgumentException("No se encontró la organización master"));
//            key = keystore.getActiveKey(masterOrganization, KeyUse.SIG, Algorithm.RS256);
//        }
//        return new KeyManager.ActiveRsaKey(key.getKid(), (PrivateKey) key.getPrivateKey(), (PublicKey) key.getPublicKey(), key.getCertificate());
//    }
//
//    private Document signXML(KeyManager.ActiveRsaKey activeRsaKey, String xml)
//            throws ParserConfigurationException, SAXException, IOException, NoSuchAlgorithmException, XMLSignatureException, InvalidAlgorithmParameterException, MarshalException {
//        return XMLSigner.firmarXML(xml, SIGN_REFERENCE_ID, activeRsaKey.getCertificate(), activeRsaKey.getPrivateKey());
//    }
//
//    @POST
//    @Path("/invoice/create")
//    @Produces(MediaType.TEXT_XML)
//    public Response createInvoiceXml(
//            @PathParam(ORGANIZATION_ID) String organizationId,
//            @NotNull InvoiceInputModel input
//    ) throws Exception {
//        String xml = DocumentFacade.createXML(input, xmlBuilderConfig, systemClock);
//        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organización no encontrada"));
//        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);
//
//        Document xmlSignedDocument;
//        try {
//            xmlSignedDocument = signXML(activeRsaKey, xml);
//        } catch (ParserConfigurationException | SAXException | IOException | NoSuchAlgorithmException | XMLSignatureException | InvalidAlgorithmParameterException | MarshalException e) {
//            throw new InternalServerErrorException(e);
//        }
//
//        return Response.ok().build();
//    }
}
