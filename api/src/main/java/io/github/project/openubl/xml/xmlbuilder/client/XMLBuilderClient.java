/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 * <p>
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.eclipse.org/legal/epl-2.0/
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.xml.xmlbuilder.client;

import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.creditNote.CreditNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.note.debitNote.DebitNoteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.PerceptionInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.RetentionInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.SummaryDocumentInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.sunat.VoidedDocumentInputModel;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/documents")
@RegisterRestClient(configKey = "xml-builder-api")
public interface XMLBuilderClient {

    String X_HEADER_PRIVATEKEY = "X-OPENBUL-PRIVATEKEY";
    String X_HEADER_CERTIFICATEKEY = "X-OPENUBL-CERTIFICATEKEY";

    @POST
    @Path("/invoice/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_XML)
    byte[] createDocument(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            InvoiceInputModel input
    );

    @POST
    @Path("/credit-note/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_XML)
    byte[] createDocument(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            CreditNoteInputModel input
    );

    @POST
    @Path("/debit-note/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_XML)
    byte[] createDocument(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            DebitNoteInputModel input
    );

    @POST
    @Path("/perception/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_XML)
    byte[] createDocument(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            PerceptionInputModel input
    );

    @POST
    @Path("/retention/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_XML)
    byte[] createDocument(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            RetentionInputModel input
    );

    @POST
    @Path("/voided-document/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_XML)
    byte[] createDocument(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            VoidedDocumentInputModel input
    );

    @POST
    @Path("/summary-document/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_XML)
    byte[] createDocument(
            @HeaderParam(X_HEADER_PRIVATEKEY) String privateRsaKeyPem,
            @HeaderParam(X_HEADER_CERTIFICATEKEY) String certificatePem,
            SummaryDocumentInputModel input
    );
}
