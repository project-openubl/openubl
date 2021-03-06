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
import io.github.project.openubl.representations.idm.OrganizationRepresentation;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.representations.idm.ComponentRepresentation;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class RepresentationToModel {

    public boolean validField(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public void updateOrganization(OrganizationRepresentation rep, OrganizationModel model) {

        if (rep.getDescription() != null) {
            model.setDescription(rep.getDescription().trim());
        }
        if (rep.getUseCustomCertificates() != null) {
            model.setUseCustomCertificates(rep.getUseCustomCertificates());
        }

        // Settings
        if (rep.getSettings() != null) {
            OrganizationSettingsModel modelSettings = model.getSettings();

            if (validField(rep.getSettings().getRuc())) {
                modelSettings.setRuc(rep.getSettings().getRuc().trim());
            }
            if (validField(rep.getSettings().getRazonSocial())) {
                modelSettings.setRazonSocial(rep.getSettings().getRazonSocial().trim());
            }
            if (validField(rep.getSettings().getNombreComercial())) {
                modelSettings.setNombreComercial(rep.getSettings().getNombreComercial().trim());
            }
            if (validField(rep.getSettings().getSunatUsername())) {
                modelSettings.setSunatUsername(rep.getSettings().getSunatUsername().trim());
            }
            if (validField(rep.getSettings().getSunatPassword()) && !rep.getSettings().getSunatPassword().trim().equals("******")) {
                modelSettings.setSunatPassword(rep.getSettings().getSunatPassword().trim());
            }
            if (validField(rep.getSettings().getSunatUrlFactura())) {
                modelSettings.setSunatUrlFactura(rep.getSettings().getSunatUrlFactura().trim());
            }
            if (validField(rep.getSettings().getSunatUrlGuiaRemision())) {
                modelSettings.setSunatUrlGuiaRemision(rep.getSettings().getSunatUrlGuiaRemision().trim());
            }
            if (validField(rep.getSettings().getSunatUrlPercepcionRetencion())) {
                modelSettings.setSunatUrlPercepcionRetencion(rep.getSettings().getSunatUrlPercepcionRetencion().trim());
            }

            if (rep.getSettings().getContacto() != null) {
                if (validField(rep.getSettings().getContacto().getTelefono())) {
                    modelSettings.setTelefono(rep.getSettings().getContacto().getTelefono().trim());
                }
                if (validField(rep.getSettings().getContacto().getEmail())) {
                    modelSettings.setEmail(rep.getSettings().getContacto().getEmail().trim());
                }
            }

            if (rep.getSettings().getDomicilioFiscal() != null) {
                if (validField(rep.getSettings().getDomicilioFiscal().getUbigeo())) {
                    modelSettings.setUbigeo(rep.getSettings().getDomicilioFiscal().getUbigeo().trim());
                }
                if (validField(rep.getSettings().getDomicilioFiscal().getDepartamento())) {
                    modelSettings.setDepartamento(rep.getSettings().getDomicilioFiscal().getDepartamento().trim());
                }
                if (validField(rep.getSettings().getDomicilioFiscal().getProvincia())) {
                    modelSettings.setProvincia(rep.getSettings().getDomicilioFiscal().getProvincia().trim());
                }
                if (validField(rep.getSettings().getDomicilioFiscal().getDistrito())) {
                    modelSettings.setDistrito(rep.getSettings().getDomicilioFiscal().getDistrito().trim());
                }
                if (validField(rep.getSettings().getDomicilioFiscal().getDireccion())) {
                    modelSettings.setDireccion(rep.getSettings().getDomicilioFiscal().getDireccion().trim());
                }
                if (validField(rep.getSettings().getDomicilioFiscal().getUrbanizacion())) {
                    modelSettings.setUrbanizacion(rep.getSettings().getDomicilioFiscal().getUrbanizacion().trim());
                }
                if (validField(rep.getSettings().getDomicilioFiscal().getCodigoLocal())) {
                    modelSettings.setCodigoLocal(rep.getSettings().getDomicilioFiscal().getCodigoLocal().trim());
                }
                if (validField(rep.getSettings().getDomicilioFiscal().getCodigoPais())) {
                    modelSettings.setCodigoPais(rep.getSettings().getDomicilioFiscal().getCodigoPais().trim());
                }
            }
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

}
