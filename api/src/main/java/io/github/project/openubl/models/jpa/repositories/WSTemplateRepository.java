package io.github.project.openubl.models.jpa.repositories;

import io.github.project.openubl.models.jpa.entities.WSTemplateEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WSTemplateRepository implements PanacheRepository<WSTemplateEntity> {
}
