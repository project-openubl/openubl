package io.github.project.openubl.representations.idm;

public class DocumentRepresentation {

    private String id;
    private String fileStorageId;
    private String documentSenderId;
    private UBLInfo ublInfo;

    public static class UBLInfo {
        private String documentType;
        private String documentId;
        private String supplierId;
        private String supplierName;
        private String supplierAdditionalName;
        private String customerName;
        private String customerId;

        public String getDocumentType() {
            return documentType;
        }

        public void setDocumentType(String documentType) {
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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public UBLInfo getUblInfo() {
        return ublInfo;
    }

    public void setUblInfo(UBLInfo ublInfo) {
        this.ublInfo = ublInfo;
    }
}
