package io.github.project.openubl.models;

public interface WSTemplateModel {

    String[] SORT_BY_FIELDS = {"name"};

    String getId();

    String getName();

    String getSunatUrlFactura();

    void setSunatUrlFactura(String sunatUrlFactura);

    String getSunatUrlGuiaRemision();

    void setSunatUrlGuiaRemision(String sunatUrlGuiaRemision);

    String getSunatUrlPercepcionRetencion();

    void setSunatUrlPercepcionRetencion(String sunatUrlPercepcionRetencion);

    class MinData {
        private String sunatUrlFactura;
        private String sunatUrlGuiaRemision;
        private String sunatUrlPercepcionRetencion;

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
}
