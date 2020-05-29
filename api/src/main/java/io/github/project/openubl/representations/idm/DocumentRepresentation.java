package io.github.project.openubl.representations.idm;

public class DocumentRepresentation {
    private Long id;
    private Long xmlSenderID;
    private String xmlSenderSUNATStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
