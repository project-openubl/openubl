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
package org.openublpe.xmlbuilder.apisigner.models.utils;

import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.representations.idm.ComponentRepresentation;
import org.keycloak.representations.idm.ConfigPropertyRepresentation;
import org.openublpe.xmlbuilder.apisigner.keys.component.ComponentModel;
import org.openublpe.xmlbuilder.apisigner.keys.component.utils.ComponentUtil;
import org.openublpe.xmlbuilder.apisigner.keys.provider.ProviderConfigProperty;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationModel;
import org.openublpe.xmlbuilder.apisigner.representations.idm.OrganizationRepresentation;

import java.util.LinkedList;
import java.util.List;

public class ModelToRepresentation {

    private ModelToRepresentation() {
        // Util Class
    }

    public static OrganizationRepresentation toRepresentation(OrganizationModel model, boolean fullInfo) {
        OrganizationRepresentation rep = new OrganizationRepresentation();
        rep.setId(model.getId());
        rep.setName(model.getName());
        rep.setType(model.getType().toString().toLowerCase());

        if (fullInfo) {
            rep.setDescription(model.getDescription());
            rep.setUseMasterKeys(model.getUseCustomCertificates());
        }

        return rep;
    }

    public static ComponentRepresentation toRepresentation(ComponentModel component, boolean internal, ComponentUtil componentUtil) {
        ComponentRepresentation rep = toRepresentationWithoutConfig(component);
        if (!internal) {
            rep = StripSecretsUtils.strip(componentUtil, rep);
        }
        return rep;
    }

    public static ComponentRepresentation toRepresentationWithoutConfig(ComponentModel component) {
        org.keycloak.representations.idm.ComponentRepresentation rep = new org.keycloak.representations.idm.ComponentRepresentation();
        rep.setId(component.getId());
        rep.setName(component.getName());
        rep.setProviderId(component.getProviderId());
        rep.setProviderType(component.getProviderType());
        rep.setSubType(component.getSubType());
        rep.setParentId(component.getParentId());
        rep.setConfig(new MultivaluedHashMap<>(component.getConfig()));
        return rep;
    }

    public static List<ConfigPropertyRepresentation> toRepresentation(List<ProviderConfigProperty> configProperties) {
        List<org.keycloak.representations.idm.ConfigPropertyRepresentation> propertiesRep = new LinkedList<>();
        for (ProviderConfigProperty prop : configProperties) {
            ConfigPropertyRepresentation propRep = toRepresentation(prop);
            propertiesRep.add(propRep);
        }
        return propertiesRep;
    }

    public static ConfigPropertyRepresentation toRepresentation(ProviderConfigProperty prop) {
        ConfigPropertyRepresentation propRep = new ConfigPropertyRepresentation();
        propRep.setName(prop.getName());
        propRep.setLabel(prop.getLabel());
        propRep.setType(prop.getType());
        propRep.setDefaultValue(prop.getDefaultValue());
        propRep.setOptions(prop.getOptions());
        propRep.setHelpText(prop.getHelpText());
        propRep.setSecret(prop.isSecret());
        return propRep;
    }
}
