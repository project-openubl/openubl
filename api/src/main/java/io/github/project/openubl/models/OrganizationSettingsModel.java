package io.github.project.openubl.models;

public interface OrganizationSettingsModel {

    String getId();

    String getSunatUsername();
    void setSunatUsername(String sunatUsername);

    String getSunatPassword();
    void setSunatPassword(String sunatPassword);

    String getSunatUrlFacturaElectronica();
    void setSunatUrlFacturaElectronica(String sunatUrlFacturaElectronica);

    String getSunatUrlGuiaRemision();
    void setSunatUrlGuiaRemision(String sunatUrlGuiaRemision);

    String getSunatUrlPercepcionRetencion();
    void setSunatUrlPercepcionRetencion(String sunatUrlPercepcionRetencion);

    String getRuc();
    void setRuc(String ruc);

    String getRazonSocial();
    void setRazonSocial(String razonSocial);

    String getNombreComercial();
    void setNombreComercial(String nombreComercial);

    String getDepartamento();
    void setDepartamento(String departamento);

    String getProvincia();
    void setProvincia(String provincia);

    String getDistrito();
    void setDistrito(String distrito);

    String getUbigeo();
    void setUbigeo(String ubigeo);

    String getDireccion();
    void setDireccion(String direccion);

    String getCodigoPais();
    void setCodigoPais(String codigoPais);

    String getCodigoLocal();
    void setCodigoLocal(String codigoLocal);

    String getUrbanizacion();
    void setUrbanizacion(String urbanizacion);

    String getTelefono();
    void setTelefono(String telefono);

    String getEmail();
    void setEmail(String email);

    class MinData {
        private String ruc;
        private String razonSocial;
        private String sunatUsername;
        private String sunatPassword;
        private String sunatUrlFacturaElectronica;
        private String sunatUrlGuiaRemision;
        private String sunatUrlPercepcionRetencion;

        public String getSunatUrlFacturaElectronica() {
            return sunatUrlFacturaElectronica;
        }

        public void setSunatUrlFacturaElectronica(String sunatUrlFacturaElectronica) {
            this.sunatUrlFacturaElectronica = sunatUrlFacturaElectronica;
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
    }
}
