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

import io.github.project.openubl.files.exceptions.StorageException;
import io.github.project.openubl.managers.DocumentsManager;
import io.github.project.openubl.models.*;
import io.github.project.openubl.models.jpa.entities.DocumentEntity;
import io.github.project.openubl.models.utils.ModelToRepresentation;
import io.github.project.openubl.representations.idm.DocumentRepresentation;
import io.github.project.openubl.models.utils.EntityToRepresentation;
import io.github.project.openubl.representations.idm.OrganizationRepresentation;
import io.github.project.openubl.representations.idm.PageRepresentation;
import io.github.project.openubl.utils.ResourceUtils;
import io.github.project.openubl.xml.XMLProvider;
import io.github.project.openubl.xmlbuilderlib.models.input.common.DireccionInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.common.ProveedorInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.creditNote.CreditNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.debitNote.DebitNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.keycloak.crypto.Algorithm;
import org.keycloak.crypto.KeyUse;
import org.keycloak.crypto.KeyWrapper;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.net.URISyntaxException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Optional;

@Transactional
@Path("/organizations/{" + DocumentsResource.ORGANIZATION_ID + "}/documents")
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentsResource {

    static final String ORGANIZATION_ID = "organizationId";

    @Context
    UriInfo uriInfo;

    @Inject
    KeyManager keystore;

    @Inject
    OrganizationProvider organizationProvider;

    @Inject
    DocumentsManager documentsManager;

    @Inject
    DocumentProvider documentProvider;

    @Inject
    XMLProvider xmlProvider;

    @Inject
    ModelToRepresentation modelToRepresentation;

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
    @Path("/")
    public PageRepresentation<DocumentRepresentation> getDocuments(
            @QueryParam("offset") @DefaultValue("0") Integer offset,
            @QueryParam("limit") @DefaultValue("10") Integer limit,
            @QueryParam("sort_by") @DefaultValue("createdOn") List<String> sortBy
    ) throws URISyntaxException {
        PageBean pageBean = ResourceUtils.getPageBean(offset, limit);
        List<SortBean> sortBeans = ResourceUtils.getSortBeans(sortBy);

        PageModel<DocumentEntity> pageModel = documentProvider.getDocumentsAsPage(pageBean, sortBeans);
        List<NameValuePair> queryParameters = ResourceUtils.buildNameValuePairs(offset, limit, sortBeans);

        return modelToRepresentation.toRepresentation(
                pageModel,
                EntityToRepresentation::toRepresentation,
                uriInfo,
                queryParameters
        );
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

    private void enrichInput(OrganizationModel organization, ProveedorInputModel input) {
        OrganizationSettingsModel settings = organization.getSettings();

        input.setRuc(settings.getRuc());
        input.setRazonSocial(settings.getRazonSocial());
        input.setNombreComercial(settings.getNombreComercial());

        input.setDireccion(new DireccionInputModel());
        input.getDireccion().setDepartamento(settings.getDepartamento());
        input.getDireccion().setProvincia(settings.getProvincia());
        input.getDireccion().setDistrito(settings.getDistrito());
        input.getDireccion().setDireccion(settings.getDireccion());
        input.getDireccion().setCodigoPais(settings.getCodigoPais());
        input.getDireccion().setCodigoLocal(settings.getCodigoLocal());
        input.getDireccion().setUrbanizacion(settings.getUrbanizacion());
    }

    @POST
    @Path("/invoice/create")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentRepresentation createInvoice(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull InvoiceInputModel input
    ) {
        return createDocument(organizationId, input);
    }

    @POST
    @Path("/invoice/credit-note")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentRepresentation createCreditNote(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull CreditNoteInputModel input
    ) {
        return createDocument(organizationId, input);
    }

    @POST
    @Path("/invoice/debit-note")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentRepresentation createDebitNote(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull DebitNoteInputModel input
    ) {
        return createDocument(organizationId, input);
    }

    @POST
    @Path("/invoice/perception")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentRepresentation createPerception(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull PerceptionInputModel input
    ) {
        return createDocument(organizationId, input);
    }

    @POST
    @Path("/invoice/retention")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentRepresentation createRetention(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull RetentionInputModel input
    ) {
        return createDocument(organizationId, input);
    }

    @POST
    @Path("/invoice/voided-document")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentRepresentation createVoidedDocument(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull VoidedDocumentInputModel input
    ) {
        return createDocument(organizationId, input);
    }

    @POST
    @Path("/invoice/summary-document")
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentRepresentation createSummaryDocument(
            @PathParam(ORGANIZATION_ID) String organizationId,
            @NotNull SummaryDocumentInputModel input
    ) {
        return createDocument(organizationId, input);
    }

    private DocumentRepresentation createDocument(String organizationId, Object input) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));

        // Extract certificate
        KeyManager.ActiveRsaKey activeRsaKey = getActiveRsaKey(organization);
        X509Certificate certificate = activeRsaKey.getCertificate();
        PrivateKey privateKey = activeRsaKey.getPrivateKey();

        byte[] xml;
        if (input instanceof InvoiceInputModel) {
            InvoiceInputModel invoice = (InvoiceInputModel) input;
            enrichInput(organization, invoice.getProveedor());

            xml = xmlProvider.createXML(certificate, privateKey, invoice);
        } else if (input instanceof CreditNoteInputModel) {
            CreditNoteInputModel creditNote = (CreditNoteInputModel) input;
            enrichInput(organization, creditNote.getProveedor());

            xml = xmlProvider.createXML(certificate, privateKey, creditNote);
        } else if (input instanceof DebitNoteInputModel) {
            DebitNoteInputModel debitNote = (DebitNoteInputModel) input;
            enrichInput(organization, debitNote.getProveedor());

            xml = xmlProvider.createXML(certificate, privateKey, debitNote);
        } else if (input instanceof PerceptionInputModel) {
            PerceptionInputModel perception = (PerceptionInputModel) input;
            enrichInput(organization, perception.getProveedor());

            xml = xmlProvider.createXML(certificate, privateKey, perception);
        } else if (input instanceof RetentionInputModel) {
            RetentionInputModel retention = (RetentionInputModel) input;
            enrichInput(organization, retention.getProveedor());

            xml = xmlProvider.createXML(certificate, privateKey, retention);
        } else if (input instanceof VoidedDocumentInputModel) {
            VoidedDocumentInputModel voidedDocument = (VoidedDocumentInputModel) input;
            enrichInput(organization, voidedDocument.getProveedor());

            xml = xmlProvider.createXML(certificate, privateKey, voidedDocument);
        } else if (input instanceof SummaryDocumentInputModel) {
            SummaryDocumentInputModel voidedDocument = (SummaryDocumentInputModel) input;
            enrichInput(organization, voidedDocument.getProveedor());

            xml = xmlProvider.createXML(certificate, privateKey, voidedDocument);
        } else {
            throw new BadRequestException("Invalid input");
        }

        DocumentEntity document;
        try {
            document = documentsManager.createDocument(organization, xml);
        } catch (StorageException e) {
            throw new InternalServerErrorException(e);
        }

        return EntityToRepresentation.toRepresentation(document);
    }

}
