package io.github.project.openubl.models.jpa.entities;

import io.github.project.openubl.models.DocumentType;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "DOCUMENT")
@Cacheable
public class DocumentEntity extends PanacheEntityBase {

    @Id
    @Column(name = "ID")
    @Access(AccessType.PROPERTY)
    private String id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "ORGANIZATION_ID")
    private OrganizationEntity organization;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

//    @NotNull
    @Column(name = "FILE_STORAGE_ID")
    private String fileStorageId;

    @Column(name = "DOCUMENT_SENDER_ID")
    private String documentSenderId;

    @Column(name = "DOCUMENT_SENDER_STATUS")
    private String documentSenderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE")
    private DocumentType documentType;

    @Column(name = "DOCUMENT_ID")
    private String documentId;

    @Column(name = "SUPPLIER_ID")
    private String supplierId;

    @Column(name = "SUPPLIER_NAME")
    private String supplierName;

    @Column(name = "SUPPLIER_ADDITIONAL_NAME")
    private String supplierAdditionalName;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Version
    @Column(name = "VERSION")
    private int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
    }

    public String getFileStorageId() {
        return fileStorageId;
    }

    public void setFileStorageId(String fileStorageId) {
        this.fileStorageId = fileStorageId;
    }

    public String getDocumentSenderId() {
        return documentSenderId;
    }

    public void setDocumentSenderId(String documentSenderId) {
        this.documentSenderId = documentSenderId;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAdditionalName() {
        return supplierAdditionalName;
    }

    public void setSupplierAdditionalName(String supplierAdditionalName) {
        this.supplierAdditionalName = supplierAdditionalName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDocumentSenderStatus() {
        return documentSenderStatus;
    }

    public void setDocumentSenderStatus(String documentSenderStatus) {
        this.documentSenderStatus = documentSenderStatus;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}

