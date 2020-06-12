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
package io.github.project.openubl.models.utils;

import io.github.project.openubl.keys.component.ComponentModel;
import io.github.project.openubl.keys.component.utils.ComponentUtil;
import io.github.project.openubl.keys.provider.ProviderConfigProperty;
import io.github.project.openubl.models.OrganizationModel;
import io.github.project.openubl.models.OrganizationSettingsModel;
import io.github.project.openubl.models.PageModel;
import io.github.project.openubl.representations.idm.OrganizationRepresentation;
import io.github.project.openubl.representations.idm.PageRepresentation;
import io.github.project.openubl.resources.ApiApplication;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.representations.idm.ComponentRepresentation;
import org.keycloak.representations.idm.ConfigPropertyRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriInfo;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ModelToRepresentation {

    public OrganizationRepresentation toRepresentation(OrganizationModel model) {
        OrganizationRepresentation rep = new OrganizationRepresentation();
        rep.setId(model.getId());
        rep.setName(model.getName());
        rep.setType(model.getType().toString().toLowerCase());

        rep.setDescription(model.getDescription());
        rep.setUseMasterKeys(model.getUseCustomCertificates());

        // Settings
        OrganizationSettingsModel modelSettings = model.getSettings();
        OrganizationRepresentation.Settings repSettings = new OrganizationRepresentation.Settings();
        rep.setSettings(repSettings);

        repSettings.setRuc(modelSettings.getRuc());
        repSettings.setRazonSocial(modelSettings.getRazonSocial());
        repSettings.setNombreComercial(modelSettings.getNombreComercial());
        repSettings.setSunatUsername(modelSettings.getSunatUsername());
        repSettings.setSunatPassword(modelSettings.getSunatPassword() != null ? "******" : null);
        repSettings.setSunatUrlFactura(modelSettings.getSunatUrlFacturaElectronica());
        repSettings.setSunatUrlGuiaRemision(modelSettings.getSunatUrlGuiaRemision());
        repSettings.setSunatUrlPercepcionRetencion(modelSettings.getSunatUrlPercepcionRetencion());


        OrganizationRepresentation.Address repAddress = new OrganizationRepresentation.Address();
        repSettings.setAddress(repAddress);

        repAddress.setCodigoLocal(modelSettings.getCodigoLocal());
        repAddress.setCodigoPais(modelSettings.getCodigoPais());
        repAddress.setDireccion(modelSettings.getDireccion());
        repAddress.setDepartamento(modelSettings.getDepartamento());
        repAddress.setProvincia(modelSettings.getProvincia());
        repAddress.setDistrito(modelSettings.getDistrito());
        repAddress.setUrbanizacion(modelSettings.getUrbanizacion());
        repAddress.setUbigeo(modelSettings.getUbigeo());


        OrganizationRepresentation.Contact repContact = new OrganizationRepresentation.Contact();
        repSettings.setContact(repContact);

        repContact.setEmail(modelSettings.getEmail());
        repContact.setTelefono(modelSettings.getTelefono());

        return rep;
    }

    public ComponentRepresentation toRepresentation(ComponentModel component, boolean internal, ComponentUtil componentUtil) {
        ComponentRepresentation rep = toRepresentationWithoutConfig(component);
        if (!internal) {
            rep = StripSecretsUtils.strip(componentUtil, rep);
        }
        return rep;
    }

    public ComponentRepresentation toRepresentationWithoutConfig(ComponentModel component) {
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

    public List<ConfigPropertyRepresentation> toRepresentation(List<ProviderConfigProperty> configProperties) {
        List<org.keycloak.representations.idm.ConfigPropertyRepresentation> propertiesRep = new LinkedList<>();
        for (ProviderConfigProperty prop : configProperties) {
            ConfigPropertyRepresentation propRep = toRepresentation(prop);
            propertiesRep.add(propRep);
        }
        return propertiesRep;
    }

    public ConfigPropertyRepresentation toRepresentation(ProviderConfigProperty prop) {
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

    public void updateOrganization(OrganizationRepresentation rep, OrganizationModel model) {
        if (rep.getName() != null) {
            model.setName(rep.getName());
        }

        if (rep.getDescription() != null) {
            model.setDescription(rep.getDescription());
        }

        if (rep.getUseMasterKeys() != null) {
            model.setUseCustomCertificates(rep.getUseMasterKeys());
        }

    }

    public ComponentModel toModel(ComponentRepresentation rep) {
        ComponentModel model = new ComponentModel();
        model.setId(rep.getId());
        model.setParentId(rep.getParentId());
        model.setProviderType(rep.getProviderType());
        model.setProviderId(rep.getProviderId());
        model.setConfig(new MultivaluedHashMap<>());
        model.setName(rep.getName());
        model.setSubType(rep.getSubType());

        if (rep.getConfig() != null) {
            Set<String> keys = new HashSet<>(rep.getConfig().keySet());
            for (String k : keys) {
                List<String> values = rep.getConfig().get(k);
                if (values != null) {
                    ListIterator<String> itr = values.listIterator();
                    while (itr.hasNext()) {
                        String v = itr.next();
                        if (v == null || v.trim().isEmpty()) {
                            itr.remove();
                        }
                    }

                    if (!values.isEmpty()) {
                        model.getConfig().put(k, values);
                    }
                }
            }
        }

        return model;
    }

    public void updateComponent(ComponentRepresentation rep, ComponentModel component, boolean internal, ComponentUtil componentUtil) {
        if (rep.getName() != null) {
            component.setName(rep.getName());
        }

        if (rep.getParentId() != null) {
            component.setParentId(rep.getParentId());
        }

        if (rep.getProviderType() != null) {
            component.setProviderType(rep.getProviderType());
        }

        if (rep.getProviderId() != null) {
            component.setProviderId(rep.getProviderId());
        }

        if (rep.getSubType() != null) {
            component.setSubType(rep.getSubType());
        }

        Map<String, ProviderConfigProperty> providerConfiguration = null;
        if (!internal) {
            providerConfiguration = componentUtil.getComponentConfigProperties(component);
        }

        if (rep.getConfig() != null) {
            Set<String> keys = new HashSet<>(rep.getConfig().keySet());
            for (String k : keys) {
                if (!internal && !providerConfiguration.containsKey(k)) {
                    break;
                }

                List<String> values = rep.getConfig().get(k);
                if (values == null || values.isEmpty() || values.get(0) == null || values.get(0).trim().isEmpty()) {
                    component.getConfig().remove(k);
                } else {
                    ListIterator<String> itr = values.listIterator();
                    while (itr.hasNext()) {
                        String v = itr.next();
                        if (v == null || v.trim().isEmpty() || v.equals(ComponentRepresentation.SECRET_VALUE)) {
                            itr.remove();
                        }
                    }

                    if (!values.isEmpty()) {
                        component.getConfig().put(k, values);
                    }
                }
            }
        }
    }

    public PageRepresentation<OrganizationRepresentation> toRepresentation(
            PageModel<OrganizationModel> model,
            UriInfo serverUriInfo,
            List<NameValuePair> queryParameters
    ) throws URISyntaxException {
        PageRepresentation<OrganizationRepresentation> rep = new PageRepresentation<>();

        // Meta
        PageRepresentation.Meta repMeta = new PageRepresentation.Meta();
        rep.setMeta(repMeta);

        repMeta.setCount(model.getTotalElements());
        repMeta.setOffset(model.getOffset());
        repMeta.setLimit(model.getLimit());

        // Data
        rep.setData(model.getPageElements().stream()
                .map(this::toRepresentation)
                .collect(Collectors.toList())
        );

        // Links
        queryParameters.add(new BasicNameValuePair("limit", String.valueOf(model.getLimit()))); // all links have same 'limit'

        PageRepresentation.Links repLinks = new PageRepresentation.Links();
        rep.setLinks(repLinks);

        // Links first
        URIBuilder uriBuilder = getURIBuilder(serverUriInfo);
        uriBuilder.addParameter("offset", String.valueOf(0));
        uriBuilder.addParameters(queryParameters);
        repLinks.setFirst(uriBuilder.build().toString());

        // Links last
        long offsetLast;
        long numberOfPages = model.getTotalElements() / model.getLimit();
        offsetLast = numberOfPages * model.getLimit();
        if (offsetLast == model.getTotalElements()) {
            offsetLast = offsetLast - model.getLimit();
        }

        uriBuilder = getURIBuilder(serverUriInfo);
        uriBuilder.addParameter("offset", String.valueOf(offsetLast));
        uriBuilder.addParameters(queryParameters);
        repLinks.setLast(uriBuilder.build().toString());

        // Links previous
        if (model.getOffset() != 0) {
            long offsetPrevious = model.getOffset() - model.getLimit();
            if (offsetPrevious < 0) {
                offsetPrevious = 0;
            }

            uriBuilder = getURIBuilder(serverUriInfo);
            uriBuilder.addParameter("offset", String.valueOf(offsetPrevious));
            uriBuilder.addParameters(queryParameters);
            repLinks.setPrevious(uriBuilder.build().toString());
        }

        // Links next
        if (model.getOffset() < model.getTotalElements() - 1) {
            long offsetNext = model.getOffset() + model.getLimit();

            uriBuilder = getURIBuilder(serverUriInfo);
            uriBuilder.addParameter("offset", String.valueOf(offsetNext));
            uriBuilder.addParameters(queryParameters);
            repLinks.setNext(uriBuilder.build().toString());
        }

        return rep;
    }

    private URIBuilder getURIBuilder(UriInfo uriInfo) throws URISyntaxException {
        return new URIBuilder(ApiApplication.API_BASE + uriInfo.getPath());
    }

}
