package io.github.project.openubl.models.jpa;

import io.github.project.openubl.models.*;
import io.github.project.openubl.models.jpa.entities.DocumentEntity;
import io.github.project.openubl.models.jpa.entities.OrganizationEntity;
import io.github.project.openubl.models.jpa.repositories.DocumentRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        documentEntity.setId(UUID.randomUUID().toString());
        documentEntity.setCreatedOn(new Date());
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

    @Override
    public PageModel<DocumentEntity> getDocumentsAsPage(PageBean pageBean, List<SortBean> sortBy) {
        Sort sort = Sort.by();
        sortBy.forEach(f -> sort.and(f.getFieldName(), f.isAsc() ? Sort.Direction.Ascending : Sort.Direction.Descending));

        PanacheQuery<DocumentEntity> query = DocumentEntity.findAll(sort)
                .range(pageBean.getOffset(), pageBean.getOffset() + pageBean.getLimit() - 1);

        long count = query.count();
        List<DocumentEntity> list = query.list();
        return new PageModel<>(pageBean, count, list);
    }
}
