package io.github.project.openubl.models.jpa.repositories;

import io.github.project.openubl.models.jpa.entities.OrganizationSettingsEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrganizationSettingsRepository implements PanacheRepository<OrganizationSettingsEntity> {
}
