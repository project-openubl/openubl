package io.github.project.openubl.managers;

import io.github.project.openubl.events.EventManager;
import io.github.project.openubl.files.FilesManager;
import io.github.project.openubl.models.DocumentProvider;
import io.github.project.openubl.models.IndexStatusType;
import io.github.project.openubl.models.OrganizationModel;
import io.github.project.openubl.models.OrganizationProvider;
import io.github.project.openubl.models.jpa.entities.DocumentEntity;
import io.github.project.openubl.ubl.XmlContentModel;
import io.github.project.openubl.ubl.XmlContentProvider;
import org.xml.sax.SAXException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Transactional
@ApplicationScoped
public class IndexManager {

    @Inject
    DocumentProvider documentProvider;

    @Inject
    OrganizationProvider organizationProvider;

    @Inject
    FilesManager filesManager;

    @Inject
    XmlContentProvider xmlContentProvider;

    @Inject
    EventManager eventManager;

    public void indexDocumentById(String organizationId, Long id) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(IllegalAccessError::new);
        DocumentEntity document = documentProvider.getDocumentById(id, organization).orElseThrow(IllegalAccessError::new);

        // Fire Event
        eventManager.fireEventDocumentIndexing(organization, document, IndexStatusType.IN_PROGRESS);

        // Fetch file
        String fileId = document.getFileId();
        byte[] xmlFile = filesManager.getFileAsBytesAfterUnzip(fileId);

        XmlContentModel xmlContent;
        try {
            xmlContent = xmlContentProvider.getSunatDocument(new ByteArrayInputStream(xmlFile));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            document.setIndexStatus(IndexStatusType.ERROR);
            eventManager.fireEventDocumentIndexing(organization, document, IndexStatusType.ERROR);
            return;
        }

        // update document
        document.setDocumentType(xmlContent.getDocumentType());
        document.setDocumentID(xmlContent.getDocumentID());

        eventManager.fireEventDocumentIndexing(organization, document, IndexStatusType.SUCCESS);
    }

}
