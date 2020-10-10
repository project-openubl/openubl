package io.github.project.openubl.xml;

import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.creditNote.CreditNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.debitNote.DebitNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.*;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface XMLProvider {

    byte[] createXML(X509Certificate certificate, PrivateKey privateKey, InvoiceInputModel input);

    byte[] createXML(X509Certificate certificate, PrivateKey privateKey, CreditNoteInputModel input);

    byte[] createXML(X509Certificate certificate, PrivateKey privateKey, DebitNoteInputModel input);

    byte[] createXML(X509Certificate certificate, PrivateKey privateKey, PerceptionInputModel input);

    byte[] createXML(X509Certificate certificate, PrivateKey privateKey, RetentionInputModel input);

    byte[] createXML(X509Certificate certificate, PrivateKey privateKey, VoidedDocumentInputModel input);

    byte[] createXML(X509Certificate certificate, PrivateKey privateKey, SummaryDocumentInputModel input);
}
