package io.github.project.openubl.xml;

import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface XMLProvider {

    byte[] createXML(X509Certificate certificate, PrivateKey privateKey, InvoiceInputModel input);
}
