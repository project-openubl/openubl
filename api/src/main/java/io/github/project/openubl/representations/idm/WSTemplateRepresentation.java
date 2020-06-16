package io.github.project.openubl.representations.idm;

public class WSTemplateRepresentation {

    private String name;
    private String sunatUrlFactura;
    private String sunatUrlGuiaRemision;
    private String sunatUrlPercepcionRetencion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
