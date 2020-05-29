package io.github.project.openubl.models.jpa;

import io.github.project.openubl.models.jpa.entities.DocumentEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocumentRepository implements PanacheRepository<DocumentEntity> {
}
