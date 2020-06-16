/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.managers;

import io.github.project.openubl.keys.KeyProvider;
import io.github.project.openubl.models.*;
import io.github.project.openubl.models.utils.DefaultKeyProviders;
import io.github.project.openubl.models.utils.RepresentationToModel;
import io.github.project.openubl.representations.idm.OrganizationRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class OrganizationManager {

    @Inject
    ComponentProvider componentProvider;

    @Inject
    DefaultKeyProviders defaultKeyProviders;

    @Inject
    OrganizationProvider organizationProvider;

    @Inject
    RepresentationToModel representationToModel;

    public OrganizationModel createOrganization(OrganizationRepresentation rep) {
        OrganizationSettingsModel.MinData settingsMinData = new OrganizationSettingsModel.MinData();
        settingsMinData.setRuc(rep.getSettings().getRuc());
        settingsMinData.setRazonSocial(rep.getSettings().getRazonSocial());
        settingsMinData.setSunatUsername(rep.getSettings().getSunatUsername());
        settingsMinData.setSunatPassword(rep.getSettings().getSunatPassword());
        settingsMinData.setSunatUrlFactura(rep.getSettings().getSunatUrlFactura());
        settingsMinData.setSunatUrlGuiaRemision(rep.getSettings().getSunatUrlGuiaRemision());
        settingsMinData.setSunatUrlPercepcionRetencion(rep.getSettings().getSunatUrlPercepcionRetencion());

        OrganizationModel organization = organizationProvider.addOrganization(rep.getName(), OrganizationType.common, settingsMinData);
        representationToModel.updateOrganization(rep, organization);

        // Certificate
        if (componentProvider.getComponents(organization, organization.getId(), KeyProvider.class.getName()).isEmpty()) {
            defaultKeyProviders.createProviders(organization);
        }

        return organization;
    }
}
