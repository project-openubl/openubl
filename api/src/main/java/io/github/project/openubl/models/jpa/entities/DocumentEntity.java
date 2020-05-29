package io.github.project.openubl.models.jpa.entities;

import io.github.project.openubl.models.jpa.entities.OrganizationEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DOCUMENT")
@Cacheable
public class DocumentEntity extends PanacheEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "organization_id")
    public OrganizationEntity organization;

    @Column(name = "XML_SENDER_ID")
    public Long xmlSenderID;

    @Column(name = "XML_SENDER_SUNAT_STATUS")
    public String xmlSenderSUNATStatus;

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public Long getXmlSenderID() {
        return xmlSenderID;
    }

    public void setXmlSenderID(Long xmlSenderID) {
        this.xmlSenderID = xmlSenderID;
    }

    public String getXmlSenderSUNATStatus() {
        return xmlSenderSUNATStatus;
    }

    public void setXmlSenderSUNATStatus(String xmlSenderSUNATStatus) {
        this.xmlSenderSUNATStatus = xmlSenderSUNATStatus;
    }
}

