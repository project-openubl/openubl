package io.github.project.openubl.events;

import io.github.project.openubl.models.DocumentEvent;
import io.github.project.openubl.models.IndexStatusType;
import io.github.project.openubl.models.OrganizationModel;
import io.github.project.openubl.models.jpa.entities.DocumentEntity;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@ApplicationScoped
public class EventManager {

    @ConfigProperty(name = "openubl.event-manager")
    EventProvider.Type eventManager;

    @Inject
    Event<DocumentEvent.Created> documentCreatedEvent;

    @Inject
    Event<DocumentEvent.Indexing> documentIndexingEvent;

    public void fireEventDocumentCreated(OrganizationModel organization, DocumentEntity documentEntity) {
        // Fire Event
        documentCreatedEvent
                .select(new EventProviderLiteral(eventManager))
                .fire(new DocumentEvent.Created() {
                    @Override
                    public Long getId() {
                        return documentEntity.id;
                    }

                    @Override
                    public String getOrganizationId() {
                        return organization.getId();
                    }
                });
    }

    public void fireEventDocumentIndexing(OrganizationModel organization, DocumentEntity documentEntity, IndexStatusType indexStatusType) {
        documentIndexingEvent
                .select(new EventProviderLiteral(eventManager))
                .fire(new DocumentEvent.Indexing() {
                    @Override
                    public Long getId() {
                        return documentEntity.id;
                    }

                    @Override
                    public String getOrganizationId() {
                        return organization.getId();
                    }

                    @Override
                    public IndexStatusType getIndexStatusType() {
                        return indexStatusType;
                    }
                });
    }
}
