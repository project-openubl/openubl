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
package io.github.project.openubl.bootstrap;

import io.github.project.openubl.models.*;
import io.github.project.openubl.models.utils.DefaultKeyProviders;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Checks if MASTER organization already exists
 * (if MASTER organization does not exists, it means is the first time the server is being bootstrapped).
 * If is the first time the server is being bootstrapped, then it will create default roles:
 * 1. ROLE "owner" with PERMISSIONS: "organization_admin, organization_edit, organization_view, component_manage, component_view, etc."
 * 2. ROLE: "collaborator" with PERMISSIONS: "document_manage, document_view, etc."
 */
@ApplicationScoped
public class ServerBootstrap {

    private static final Logger logger = Logger.getLogger(ServerBootstrap.class);

    @Inject
    WSTemplateProvider wsTemplateProvider;

    @Inject
    DefaultKeyProviders defaultKeyProviders;

    @Inject
    OrganizationProvider organizationProvider;


    @ConfigProperty(name = "openubl.ws.templates.sunat-beta.url-factura-electronica")
    String sunatBetaURLFactura;

    @ConfigProperty(name = "openubl.ws.templates.sunat-beta.url-guia-remision")
    String sunatBetaURLGuiaRemision;

    @ConfigProperty(name = "openubl.ws.templates.sunat-beta.url-percepcion-retencion")
    String sunatBetaURLPercepcionRetencion;

    @ConfigProperty(name = "openubl.ws.templates.sunat-prod.url-factura-electronica")
    String sunatProdURLFactura;

    @ConfigProperty(name = "openubl.ws.templates.sunat-prod.url-guia-remision")
    String sunatProdURLGuiaRemision;

    @ConfigProperty(name = "openubl.ws.templates.sunat-prod.url-percepcion-retencion")
    String sunatProdURLPercepcionRetencion;


    @ConfigProperty(name = "openubl.organization.master.ruc")
    String orgDemoRuc;

    @ConfigProperty(name = "openubl.organization.master.razon-social")
    String orgDemoRazonSocial;

    @ConfigProperty(name = "openubl.organization.master.sunat-username")
    String orgDemoSunatUsername;

    @ConfigProperty(name = "openubl.organization.master.sunat-password")
    String orgDemoSunatPassword;


    void onStart(@Observes StartupEvent ev) {
        logger.info("Server Bootstrap...");
        bootstrap();
    }

    private void bootstrap() {
        // Create default templates
        Optional<WSTemplateModel> betaWSTemplate = wsTemplateProvider.getById(WSTemplateProvider.SUNAT_BETA);
        if (!betaWSTemplate.isPresent()) {
            createTemplate(WSTemplateProvider.SUNAT_BETA, sunatBetaURLFactura, sunatBetaURLGuiaRemision, sunatBetaURLPercepcionRetencion);
        }
        Optional<WSTemplateModel> prodWSTemplate = wsTemplateProvider.getById(WSTemplateProvider.SUNAT_PROD);
        if (!prodWSTemplate.isPresent()) {
            createTemplate(WSTemplateProvider.SUNAT_PROD, sunatProdURLFactura, sunatProdURLGuiaRemision, sunatProdURLPercepcionRetencion);
        }

        // Create Default Organizations
        Optional<OrganizationModel> masterOrg = organizationProvider.getOrganizationById(OrganizationModel.MASTER_ID);
        if (masterOrg.isEmpty()) {
            createMasterOrganization();
        }
    }

    @Transactional
    private void createTemplate(String templateId, String facturaUrl, String guiaUrl, String percepcionRetencionUrl) {
        WSTemplateModel.MinData templateData = new WSTemplateModel.MinData();
        templateData.setSunatUrlFactura(facturaUrl);
        templateData.setSunatUrlGuiaRemision(guiaUrl);
        templateData.setSunatUrlPercepcionRetencion(percepcionRetencionUrl);

        wsTemplateProvider.add(templateId, templateId, templateData);
    }

    @Transactional
    private void createMasterOrganization() {
        logger.info("Initializing Admin Organization " + OrganizationModel.MASTER_ID);

        // Settings
        WSTemplateModel wsTemplate = wsTemplateProvider
                .getById(WSTemplateProvider.SUNAT_BETA)
                .orElseThrow(() -> new IllegalStateException("Template name=" + WSTemplateProvider.SUNAT_BETA + " not found"));
        OrganizationSettingsModel.MinData orgSettingsMinData = new OrganizationSettingsModel.MinData();
        orgSettingsMinData.setRuc(orgDemoRuc);
        orgSettingsMinData.setRazonSocial(orgDemoRazonSocial);
        orgSettingsMinData.setSunatUsername(orgDemoSunatUsername);
        orgSettingsMinData.setSunatPassword(orgDemoSunatPassword);
        orgSettingsMinData.setSunatUrlFactura(wsTemplate.getSunatUrlFactura());
        orgSettingsMinData.setSunatUrlGuiaRemision(wsTemplate.getSunatUrlGuiaRemision());
        orgSettingsMinData.setSunatUrlPercepcionRetencion(wsTemplate.getSunatUrlPercepcionRetencion());


        OrganizationModel organization = organizationProvider.addOrganization(OrganizationModel.MASTER_ID, OrganizationModel.MASTER_ID, OrganizationType.master, orgSettingsMinData);


        defaultKeyProviders.createProviders(organization);
        logger.info("Default key providers for Admin Organization " + OrganizationModel.MASTER_ID + " have been created");
    }

}
