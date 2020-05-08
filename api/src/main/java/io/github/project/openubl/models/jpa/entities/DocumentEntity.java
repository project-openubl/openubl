package io.github.project.openubl.models.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Cacheable
@Entity
@Table(name = "DOCUMENT")
public class DocumentEntity {

    @Id
    @Access(AccessType.PROPERTY)// Relationships often fetch id, but not entity.  This avoids an extra SQL
    @Column(name = "ID", length = 36)
    private String id;

    @NotNull
    @Column(name = "XML_SENDER_ID")
    private String xmlSenderID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXmlSenderID() {
        return xmlSenderID;
    }

    public void setXmlSenderID(String xmlSenderID) {
        this.xmlSenderID = xmlSenderID;
    }

}

