package io.github.project.openubl.models.jpa.entities;

import io.github.project.openubl.models.IndexStatusType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DOCUMENT")
@Cacheable
public class DocumentEntity extends PanacheEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "ORGANIZATION_ID")
    public OrganizationEntity organization;

    @Column(name = "FILE_ID")
    public String fileId;

    @Enumerated(EnumType.STRING)
    @Column(name = "INDEX_STATUS")
    public IndexStatusType indexStatus;

    @Column(name = "DOCUMENT_TYPE")
    public String documentType;

    @Column(name = "DOCUMENT_ID")
    public String documentID;

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public IndexStatusType getIndexStatus() {
        return indexStatus;
    }

    public void setIndexStatus(IndexStatusType indexStatus) {
        this.indexStatus = indexStatus;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

}

