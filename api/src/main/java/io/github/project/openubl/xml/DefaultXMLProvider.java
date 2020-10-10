package io.github.project.openubl.xml;

import io.github.project.openubl.xml.xmlbuilder.client.XMLBuilderClient;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.creditNote.CreditNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.debitNote.DebitNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.keycloak.common.util.PemUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

@ApplicationScoped
public class DefaultXMLProvider implements XMLProvider {

    @Inject
    @RestClient
    XMLBuilderClient xmlBuilderClient;

    @Override
    public byte[] createXML(X509Certificate certificate, PrivateKey privateKey, InvoiceInputModel input) {
        String privateRsaKeyPem = PemUtils.encodeKey(privateKey);
        String certificatePem = PemUtils.encodeCertificate(certificate);

        return xmlBuilderClient.createDocument(privateRsaKeyPem, certificatePem, input);
    }

    @Override
    public byte[] createXML(X509Certificate certificate, PrivateKey privateKey, CreditNoteInputModel input) {
        String privateRsaKeyPem = PemUtils.encodeKey(privateKey);
        String certificatePem = PemUtils.encodeCertificate(certificate);

        return xmlBuilderClient.createDocument(privateRsaKeyPem, certificatePem, input);
    }

    @Override
    public byte[] createXML(X509Certificate certificate, PrivateKey privateKey, DebitNoteInputModel input) {
        String privateRsaKeyPem = PemUtils.encodeKey(privateKey);
        String certificatePem = PemUtils.encodeCertificate(certificate);

        return xmlBuilderClient.createDocument(privateRsaKeyPem, certificatePem, input);
    }

    @Override
    public byte[] createXML(X509Certificate certificate, PrivateKey privateKey, PerceptionInputModel input) {
        String privateRsaKeyPem = PemUtils.encodeKey(privateKey);
        String certificatePem = PemUtils.encodeCertificate(certificate);

        return xmlBuilderClient.createDocument(privateRsaKeyPem, certificatePem, input);
    }

    @Override
    public byte[] createXML(X509Certificate certificate, PrivateKey privateKey, RetentionInputModel input) {
        String privateRsaKeyPem = PemUtils.encodeKey(privateKey);
        String certificatePem = PemUtils.encodeCertificate(certificate);

        return xmlBuilderClient.createDocument(privateRsaKeyPem, certificatePem, input);
    }

    @Override
    public byte[] createXML(X509Certificate certificate, PrivateKey privateKey, VoidedDocumentInputModel input) {
        String privateRsaKeyPem = PemUtils.encodeKey(privateKey);
        String certificatePem = PemUtils.encodeCertificate(certificate);

        return xmlBuilderClient.createDocument(privateRsaKeyPem, certificatePem, input);
    }

    @Override
    public byte[] createXML(X509Certificate certificate, PrivateKey privateKey, SummaryDocumentInputModel input) {
        String privateRsaKeyPem = PemUtils.encodeKey(privateKey);
        String certificatePem = PemUtils.encodeCertificate(certificate);

        return xmlBuilderClient.createDocument(privateRsaKeyPem, certificatePem, input);
    }

}
