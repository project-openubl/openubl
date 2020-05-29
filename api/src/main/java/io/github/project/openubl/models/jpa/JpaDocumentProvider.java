package io.github.project.openubl.models.jpa;

import io.github.project.openubl.models.DocumentProvider;
import io.github.project.openubl.models.jpa.entities.DocumentEntity;
import io.github.project.openubl.models.OrganizationModel;
import io.github.project.openubl.models.jpa.entities.OrganizationEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class JpaDocumentProvider implements DocumentProvider {

    @Inject
    EntityManager em;

    @Inject
    DocumentRepository documentRepository;

    @Override
    public DocumentEntity addDocument(OrganizationModel organization) {
        OrganizationEntity organizationEntity = em.find(OrganizationEntity.class, organization.getId());
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setOrganization(organizationEntity);

        documentRepository.persist(documentEntity);
        return documentEntity;
    }

    @Override
    public Optional<DocumentEntity> getDocumentById(Long documentId, OrganizationModel organization) {
        DocumentEntity documentEntity = documentRepository.findById(documentId);
        if (documentEntity == null) {
            return Optional.empty();
        }

        OrganizationEntity organizationEntity = em.find(OrganizationEntity.class, organization.getId());
        if (!documentEntity.getOrganization().equals(organizationEntity)) {
            throw new IllegalStateException("Document[" + documentId + "] does not belong to organization[" + organization.getId() + "]");
        }

        return Optional.of(documentEntity);
    }
}
