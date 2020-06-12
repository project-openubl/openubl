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

package io.github.project.openubl.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.project.openubl.models.OrganizationModel;
import io.github.project.openubl.models.OrganizationType;
import io.github.project.openubl.representations.idm.OrganizationRepresentation;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import org.junit.jupiter.api.Order;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class OrganizationsResourceTest {

    static final String ORGANIZATIONS_URL = ApiApplication.API_BASE + "/organizations";

    @Test
    @Order(1)
    void testGetInitialListOfOrganizations() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get(ORGANIZATIONS_URL)
                .then()
                .statusCode(200)
                .body("meta.offset", is(0),
                        "meta.limit", is(10),
                        "meta.count", is(1),
                        "links.first", is(notNullValue()),
                        "links.last", is(notNullValue()),
                        "links.next", is(nullValue()),
                        "links.previous", is(nullValue()),
                        "data.size()", is(1),
                        "data[0].id", is(OrganizationModel.MASTER_ID),
                        "data[0].id", is(OrganizationModel.MASTER_ID),
                        "data[0].name", is(OrganizationModel.MASTER_ID),
                        "data[0].description", is(nullValue()),
                        "data[0].type", is(OrganizationType.master.toString()),
                        "data[0].useMasterKeys", is(false),
                        "data[0].settings.ruc", is("12345678912"),
                        "data[0].settings.razonSocial", is("Project OpenUBL"),
                        "data[0].settings.nombreComercial", is(nullValue()),
                        "data[0].settings.sunatUsername", is("12345678912MODDATOS"),
                        "data[0].settings.sunatPassword", is("******"),
                        "data[0].settings.sunatUrlFactura", is("https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService"),
                        "data[0].settings.sunatUrlGuiaRemision", is("https://e-beta.sunat.gob.pe/ol-ti-itemision-guia-gem-beta/billService"),
                        "data[0].settings.sunatUrlPercepcionRetencion", is("https://e-beta.sunat.gob.pe/ol-ti-itemision-otroscpe-gem-beta/billService"),
                        "data[0].settings.address", is(anything()),
                        "data[0].settings.contacto", is(anything())
                );

    }

//    @Test
//    void testGetMasterOrganization() throws Exception {
//        given()
//                .header("Content-Type", "application/json")
//                .when()
//                .get(ORGANIZATIONS_URL + "/" + OrganizationModel.MASTER_ID)
//                .then()
//                .statusCode(200)
//                .body("id", is(OrganizationModel.MASTER_ID),
//                        "name", is(OrganizationModel.MASTER_ID),
//                        "description", is(nullValue()),
//                        "type", is(OrganizationType.master.toString()),
//                        "useMasterKeys", is(false),
//                        "settings.ruc", is("12345678912"),
//                        "settings.razonSocial", is("Project OpenUBL"),
//                        "settings.nombreComercial", is(nullValue()),
//                        "settings.sunatUsername", is("12345678912MODDATOS"),
//                        "settings.sunatPassword", is("******"),
//                        "settings.sunatUrlFactura", is("https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService"),
//                        "settings.sunatUrlGuiaRemision", is("https://e-beta.sunat.gob.pe/ol-ti-itemision-guia-gem-beta/billService"),
//                        "settings.sunatUrlPercepcionRetencion", is("https://e-beta.sunat.gob.pe/ol-ti-itemision-otroscpe-gem-beta/billService"),
//                        "settings.address", is(anything()),
//                        "settings.contacto", is(anything())
//                );
//    }

//    @Test
//    void testCreateOrganizationWithMinData() throws Exception {
//        // GIVEN
//        OrganizationRepresentation organization = new OrganizationRepresentation();
//        organization.setName("myCompanyName");
//        organization.setSettings(new OrganizationRepresentation.Settings());
//        organization.getSettings().setRuc("12312312312");
//        organization.getSettings().setRazonSocial("My company name");
//        organization.getSettings().setSunatUsername("myUsername");
//        organization.getSettings().setSunatPassword("myPassword");
//        organization.getSettings().setSunatUrlFactura("myUrl1");
//        organization.getSettings().setSunatUrlGuiaRemision("myUrl2");
//        organization.getSettings().setSunatUrlPercepcionRetencion("myUrl3");
//
//        String body = new ObjectMapper().writeValueAsString(organization);
//
//        // WHEN
//        given()
//                .body(body)
//                .header("Content-Type", "application/json")
//                .when()
//                .post(ORGANIZATIONS_URL)
//                .then()
//                .body("id", is(notNullValue()),
//                        "name", is(organization.getName()),
//                        "description", is(nullValue()),
//                        "type", is(OrganizationType.common.toString()),
//                        "useMasterKeys", is(false),
//                        "settings.ruc", is("12312312312"),
//                        "settings.razonSocial", is("My company name"),
//                        "settings.nombreComercial", is(nullValue()),
//                        "settings.sunatUsername", is("myUsername"),
//                        "settings.sunatPassword", is("******"),
//                        "settings.sunatUrlFactura", is("myUrl1"),
//                        "settings.sunatUrlGuiaRemision", is("myUrl2"),
//                        "settings.sunatUrlPercepcionRetencion", is("myUrl3"),
//                        "settings.address", is(anything()),
//                        "settings.contacto", is(anything()));
//    }

//    @Test
//    void testUpdateOrganization() throws Exception {
//        // GIVEN
//        String organizationId = "master";
//
//        OrganizationRepresentation organization = new OrganizationRepresentation();
//        organization.setName("myNewMasterName");
//        organization.setDescription("myNewMasterDescription");
//        organization.setUseMasterKeys(false);
//        organization.setType("common"); // this field should never change, check asserts
//
//        String body = new ObjectMapper().writeValueAsString(organization);
//
//        // WHEN
//        Response response = given()
//                .body(body)
//                .header("Content-Type", "application/json")
//                .when()
//                .put(ORGANIZATIONS_URL + "/" + organizationId)
//                .thenReturn();
//
//        // THEN
//        assertEquals(200, response.getStatusCode());
//        ResponseBody responseBody = response.getBody();
//
//        OrganizationRepresentation output = new ObjectMapper().readValue(responseBody.asInputStream(), OrganizationRepresentation.class);
//        assertEquals(organization.getName(), output.getName());
//        assertEquals(organization.getDescription(), output.getDescription());
//        assertEquals(organization.getUseMasterKeys(), output.getUseMasterKeys());
//        assertEquals(OrganizationType.master.toString(), output.getType());
//    }
//
//    @Test
//    void testGetKeyMetadata() throws Exception {
//        // GIVEN
//        String organizationId = "master";
//
//        // WHEN
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .when()
//                .get(ORGANIZATIONS_URL + "/" + organizationId + "/keys")
//                .thenReturn();
//
//        // THEN
//        assertEquals(200, response.getStatusCode());
//        ResponseBody responseBody = response.getBody();
//
//        KeysMetadataRepresentation output = new ObjectMapper().readValue(responseBody.asInputStream(), KeysMetadataRepresentation.class);
//        assertNotNull(output);
//        assertFalse(output.getActive().isEmpty());
//        assertFalse(output.getKeys().isEmpty());
//    }
//
//    @Test
//    void testGetComponents() throws Exception {
//        // GIVEN
//        String organizationId = "master";
//
//        // WHEN
//        Response response = given()
//                .header("Content-Type", "application/json")
//                .when()
//                .get(ORGANIZATIONS_URL + "/" + organizationId + "/components")
//                .thenReturn();
//
//        // THEN
//        assertEquals(200, response.getStatusCode());
//        ResponseBody responseBody = response.getBody();
//
//        List<ComponentRepresentation> output = new ObjectMapper().readValue(responseBody.asInputStream(), List.class);
//        assertNotNull(output);
//        assertFalse(output.isEmpty());
//    }
}
