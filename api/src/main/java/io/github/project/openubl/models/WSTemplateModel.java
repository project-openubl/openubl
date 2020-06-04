package io.github.project.openubl.models;

public interface WSTemplateModel {

    String getId();

    String getTemplateName();

    String getSunatUrlFacturaElectronica();

    void setSunatUrlFacturaElectronica(String sunatUrlFacturaElectronica);

    String getSunatUrlGuiaRemision();

    void setSunatUrlGuiaRemision(String sunatUrlGuiaRemision);

    String getSunatUrlPercepcionRetencion();

    void setSunatUrlPercepcionRetencion(String sunatUrlPercepcionRetencion);

    class MinData {
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
    }
}
