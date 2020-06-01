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

import io.github.project.openubl.events.EventManager;
import io.github.project.openubl.events.EventProvider;
import io.github.project.openubl.events.EventProviderLiteral;
import io.github.project.openubl.files.FileType;
import io.github.project.openubl.files.FilesManager;
import io.github.project.openubl.models.*;
import io.github.project.openubl.models.jpa.entities.DocumentEntity;
import io.github.project.openubl.representations.idm.DocumentRepresentation;
import io.github.project.openubl.utils.EntityToRepresentation;
import io.github.project.openubl.xmlsender.idm.ErrorRepresentation;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Path("/organizations/{" + DocumentsResource.ORGANIZATION_ID + "}/documents")
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentsResource {

    static final String ORGANIZATION_ID = "organizationId";


    @Inject
    OrganizationProvider organizationProvider;

    @Inject
    DocumentProvider documentProvider;

    @Inject
    FilesManager filesManager;

    @Inject
    EventManager eventManager;

    @POST
    @Path("/")
    @Consumes("multipart/form-data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadDocument(
            @PathParam(ORGANIZATION_ID) String organizationId,
            MultipartFormDataInput input
    ) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
        byte[] xmlFile = null;

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> fileInputParts = uploadForm.get("file");
        if (fileInputParts == null) {
            ErrorRepresentation error = new ErrorRepresentation("Form[file] is required");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }

        try {
            for (InputPart inputPart : fileInputParts) {
                InputStream fileInputStream = inputPart.getBody(InputStream.class, null);
                xmlFile = IOUtils.toByteArray(fileInputStream);
            }
        } catch (IOException e) {
            throw new BadRequestException("Could not extract required data from upload/form");
        }

        if (xmlFile == null || xmlFile.length == 0) {
            ErrorRepresentation error = new ErrorRepresentation("Form[file] is empty");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }

        // Persist file
        String fileID = filesManager.createFile(xmlFile, UUID.randomUUID().toString(), FileType.XML);
        if (fileID == null) {
            ErrorRepresentation error = new ErrorRepresentation("Error while persisting file");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }

        // Persist in DB
        DocumentEntity documentEntity = documentProvider.addDocument(organization, fileID);

        // Fire event
        eventManager.fireEventDocumentCreated(organization, documentEntity);

        return Response.status(Response.Status.OK)
                .entity(EntityToRepresentation.toRepresentation(documentEntity))
                .build();
    }

    @GET
    @Path("/{documentId}")
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

}
