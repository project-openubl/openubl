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
package io.github.project.openubl.managers;

import io.github.project.openubl.files.FileType;
import io.github.project.openubl.files.FilesManager;
import io.github.project.openubl.files.exceptions.StorageException;
import io.github.project.openubl.models.DocumentProvider;
import io.github.project.openubl.models.OrganizationModel;
import io.github.project.openubl.models.OrganizationProvider;
import io.github.project.openubl.models.jpa.entities.DocumentEntity;
import io.github.project.openubl.xml.xmlbuilder.client.FileUploadForm;
import io.github.project.openubl.xml.xmlbuilder.client.XMLSenderClient;
import io.github.project.openubl.xmlsender.idm.DocumentRepresentation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.util.UUID;

@Transactional
@ApplicationScoped
public class DocumentsManager {

    @Inject
    @RestClient
    XMLSenderClient xmlSenderClient;

    @Inject
    DocumentProvider documentProvider;

    @Inject
    OrganizationProvider organizationProvider;

    @Inject
    FilesManager filesManager;

    public DocumentEntity createDocument(OrganizationModel organization, byte[] xml) throws StorageException {
        DocumentEntity document = documentProvider.addDocument(organization);

        // Save File
        String fileID = filesManager.createFile(xml, FileType.getFilename(UUID.randomUUID().toString(), FileType.XML), FileType.XML);
        if (fileID == null) {
            throw new StorageException("Could not save xml file in storage");
        }
        document.setFileStorageId(fileID);

        // Send
        FileUploadForm fileUploadForm = new FileUploadForm();
        fileUploadForm.file = new ByteArrayInputStream(xml);
        fileUploadForm.customId = encodeCustomId(organization, document);

        DocumentRepresentation rep = xmlSenderClient.createDocument(fileUploadForm);
        document.setDocumentSenderId(rep.getId().toString());

        // return result
        return document;
    }

    public void updateDocumentFromXMLSender(DocumentRepresentation documentRep) {
        CustomId customId = decodeCustomId_organizationId(documentRep.getCustomId());
        OrganizationModel organization = organizationProvider.getOrganizationById(customId.organizationId).orElseThrow(IllegalAccessError::new);
        DocumentEntity document = documentProvider.getDocumentById(customId.documentId, organization).orElseThrow(IllegalAccessError::new);

        String sunatStatus = documentRep.getSunatStatus().getStatus();
        document.setDocumentSenderStatus(sunatStatus);

        DocumentEntity.persist(document);
    }

    private String encodeCustomId(OrganizationModel organization, DocumentEntity document) {
        return organization.getId() + "::" + document.getId();
    }

    private CustomId decodeCustomId_organizationId(String customId) {
        String[] split = customId.split("::");

        CustomId result = new CustomId();
        result.organizationId = split[0];
        result.documentId = Long.parseLong(split[1]);

        return result;
    }

    static class CustomId {
        String organizationId;
        Long documentId;
    }
}
