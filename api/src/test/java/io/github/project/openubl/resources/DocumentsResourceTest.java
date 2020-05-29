package io.github.project.openubl.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.project.openubl.representations.idm.DocumentRepresentation;
import io.github.project.openubl.xmlbuilderlib.models.catalogs.Catalog6;
import io.github.project.openubl.xmlbuilderlib.models.input.common.ClienteInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.common.ProveedorInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.DocumentLineInputModel;
import io.github.project.openubl.xmlbuilderlib.models.input.standard.invoice.InvoiceInputModel;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class DocumentsResourceTest {

    static final String ORGANIZATION_ID = "master";

    @Test
    void testInvoice() throws JsonProcessingException {
        // Given
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withNumero(1)
                .withProveedor(ProveedorInputModel.Builder.aProveedorInputModel()
                        .withRuc("12345678912")
                        .withRazonSocial("Project OpenUBL S.A.C.")
                        .build()
                )
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withNombre("Carlos Feria")
                        .withNumeroDocumentoIdentidad("12121212121")
                        .withTipoDocumentoIdentidad(Catalog6.RUC.toString())
                        .build()
                )
                .withDetalle(Arrays.asList(
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item1")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioUnitario(new BigDecimal(100))
                                .build(),
                        DocumentLineInputModel.Builder.aDocumentLineInputModel()
                                .withDescripcion("Item2")
                                .withCantidad(new BigDecimal(10))
                                .withPrecioUnitario(new BigDecimal(100))
                                .build())
                )
                .build();

        // When
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(input);

        ExtractableResponse<Response> extract = given()
                .body(body)
                .header("Content-Type", "application/json")
                .when()
                .post(ApiApplication.API_BASE + "/organizations/" + ORGANIZATION_ID + "/documents/create/invoice/")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("xmlSenderID", notNullValue())
                .extract();

        DocumentRepresentation document = extract.as(DocumentRepresentation.class);

        given()
                .when()
                .get(ApiApplication.API_BASE + "/organizations/" + ORGANIZATION_ID + "/documents/get/" + document.getId())
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("xmlSenderID", notNullValue());
    }

}
