package io.github.project.openubl.models;

import io.github.project.openubl.models.jpa.entities.DocumentEntity;

import java.util.List;
import java.util.Optional;

public interface DocumentProvider {

    DocumentEntity addDocument(OrganizationModel organizationModel);

    Optional<DocumentEntity> getDocumentById(Long documentId, OrganizationModel organization);

    PageModel<DocumentEntity> getDocumentsAsPage(PageBean pageBean, List<SortBean> sortBy);
}
