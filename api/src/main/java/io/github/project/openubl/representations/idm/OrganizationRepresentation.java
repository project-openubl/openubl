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
package io.github.project.openubl.representations.idm;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class OrganizationRepresentation {

    private String id;

    @Pattern(regexp = "^[-a-zA-Z0-9]+$")
    private String name;
    private String description;

    private String type;
    private Boolean useMasterKeys;

    @NotNull
    @Valid
    private Settings settings;

    public static class Settings {
        @NotNull
        @NotEmpty
        @Size(min = 11, max = 11)
        private String ruc;

        @NotNull
        @NotEmpty
        private String razonSocial;
        private String nombreComercial;

        @NotNull
        @NotEmpty
        private String sunatUsername;

        @NotNull
        @NotEmpty
        private String sunatPassword;

        @NotNull
        @NotEmpty
        private String sunatUrlFactura;

        @NotNull
        @NotEmpty
        private String sunatUrlGuiaRemision;

        @NotNull
        @NotEmpty
        private String sunatUrlPercepcionRetencion;

        @Valid
        private Address domicilioFiscal;

        @Valid
        private Contact contacto;

        public String getRuc() {
            return ruc;
        }

        public void setRuc(String ruc) {
            this.ruc = ruc;
        }

        public String getRazonSocial() {
            return razonSocial;
        }

        public void setRazonSocial(String razonSocial) {
            this.razonSocial = razonSocial;
        }

        public String getNombreComercial() {
            return nombreComercial;
        }

        public void setNombreComercial(String nombreComercial) {
            this.nombreComercial = nombreComercial;
        }

        public Address getDomicilioFiscal() {
            return domicilioFiscal;
        }

        public void setDomicilioFiscal(Address domicilioFiscal) {
            this.domicilioFiscal = domicilioFiscal;
        }

        public Contact getContacto() {
            return contacto;
        }

        public void setContacto(Contact contacto) {
            this.contacto = contacto;
        }

        public String getSunatUsername() {
            return sunatUsername;
        }

        public void setSunatUsername(String sunatUsername) {
            this.sunatUsername = sunatUsername;
        }

        public String getSunatPassword() {
            return sunatPassword;
        }

        public void setSunatPassword(String sunatPassword) {
            this.sunatPassword = sunatPassword;
        }

        public String getSunatUrlFactura() {
            return sunatUrlFactura;
        }

        public void setSunatUrlFactura(String sunatUrlFactura) {
            this.sunatUrlFactura = sunatUrlFactura;
        }

        public String getSunatUrlGuiaRemision() {
            return sunatUrlGuiaRemision;
        }

        public void setSunatUrlGuiaRemision(String sunatUrlGuiaRemision) {
            this.sunatUrlGuiaRemision = sunatUrlGuiaRemision;
        }

        public String getSunatUrlPercepcionRetencion() {
            return sunatUrlPercepcionRetencion;
        }

        public void setSunatUrlPercepcionRetencion(String sunatUrlPercepcionRetencion) {
            this.sunatUrlPercepcionRetencion = sunatUrlPercepcionRetencion;
        }
    }

    public static class Address {
        @Size(min = 6, max = 6)
        private String ubigeo;
        private String codigoLocal;
        private String urbanizacion;
        private String provincia;
        private String departamento;
        private String distrito;
        private String direccion;
        private String codigoPais;

        public String getUbigeo() {
            return ubigeo;
        }

        public void setUbigeo(String ubigeo) {
            this.ubigeo = ubigeo;
        }

        public String getCodigoLocal() {
            return codigoLocal;
        }

        public void setCodigoLocal(String codigoLocal) {
            this.codigoLocal = codigoLocal;
        }

        public String getUrbanizacion() {
            return urbanizacion;
        }

        public void setUrbanizacion(String urbanizacion) {
            this.urbanizacion = urbanizacion;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }

        public String getDepartamento() {
            return departamento;
        }

        public void setDepartamento(String departamento) {
            this.departamento = departamento;
        }

        public String getDistrito() {
            return distrito;
        }

        public void setDistrito(String distrito) {
            this.distrito = distrito;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getCodigoPais() {
            return codigoPais;
        }

        public void setCodigoPais(String codigoPais) {
            this.codigoPais = codigoPais;
        }
    }

    public static class Contact {
        @Email
        private String email;
        private String telefono;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUseMasterKeys() {
        return useMasterKeys;
    }

    public void setUseMasterKeys(Boolean useMasterKeys) {
        this.useMasterKeys = useMasterKeys;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
