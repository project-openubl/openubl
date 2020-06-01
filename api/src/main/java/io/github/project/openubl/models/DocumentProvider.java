package io.github.project.openubl.models;

import io.github.project.openubl.models.jpa.entities.DocumentEntity;

import java.util.Optional;

public interface DocumentProvider {

    DocumentEntity addDocument(OrganizationModel organizationModel, String fileID);

    Optional<DocumentEntity> getDocumentById(Long documentId, OrganizationModel organization);
}
