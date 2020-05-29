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

import io.github.project.openubl.managers.DocumentsManager;
import io.github.project.openubl.models.DocumentProvider;
import io.github.project.openubl.models.KeyManager;
import io.github.project.openubl.models.OrganizationModel;
import io.github.project.openubl.models.OrganizationProvider;
import io.github.project.openubl.models.jpa.entities.DocumentEntity;
import io.github.project.openubl.representations.idm.DocumentRepresentation;
import io.github.project.openubl.resources.client.XMLBuilderClient;
import io.github.project.openubl.utils.EntityToRepresentation;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.keycloak.common.util.PemUtils;
import org.keycloak.crypto.Algorithm;
import org.keycloak.crypto.KeyUse;
import org.keycloak.crypto.KeyWrapper;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Optional;

@Transactional
@Path("/organizations/{" + DocumentsResource.ORGANIZATION_ID + "}/documents")
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentsResource {

    static final String SIGN_REFERENCE_ID = "Project-OpenUBL";
    static final String ORGANIZATION_ID = "organizationId";

    @Inject
    OrganizationProvider organizationProvider;

    @Inject
    KeyManager keystore;

    @Inject
    DocumentsManager documentsManager;

    @Inject
    DocumentProvider documentProvider;

    @Inject
    @RestClient
    XMLBuilderClient xmlBuilderClient;

    private KeyManager.ActiveRsaKey getActiveRsaKey(OrganizationModel organization) {
        KeyWrapper key;
        if (organization.getUseCustomCertificates()) {
            key = keystore.getActiveKey(organization, KeyUse.SIG, Algorithm.RS256);
        } else {
            OrganizationModel masterOrganization = organizationProvider
                    .getOrganizationById(OrganizationModel.MASTER_ID)
                    .orElseThrow(() -> new IllegalArgumentException("Master organization not found"));
            key = keystore.getActiveKey(masterOrganization, KeyUse.SIG, Algorithm.RS256);
        }
        return new KeyManager.ActiveRsaKey(key.getKid(), (PrivateKey) key.getPrivateKey(), (PublicKey) key.getPublicKey(), key.getCertificate());
    }

    @GET
    @Path("/get/{documentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentRepresentation getDocument(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @PathParam("documentId") Long documentId
    ) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
        Optional<DocumentEntity> document = documentProvider.getDocumentById(documentId, organization);

        if (document.isEmpty()) {
            throw new NotFoundException("Document not found");
        }

        return EntityToRepresentation.toRepresentation(document.get());
    }

    @POST
    @Path("/create/invoice")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentRepresentation createInvoice(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull InvoiceInputModel input
    ) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);

        X509Certificate certificate = activeRsaKey.getCertificate();
        PrivateKey privateKey = activeRsaKey.getPrivateKey();

        String privateRsaKeyPem = PemUtils.encodeKey(privateKey);
        String certificatePem = PemUtils.encodeCertificate(certificate);

        byte[] xml = xmlBuilderClient.createInvoice(privateRsaKeyPem, certificatePem, input);
        DocumentEntity document = documentsManager.createDocument(organization, xml);

        return EntityToRepresentation.toRepresentation(document);
    }

}
