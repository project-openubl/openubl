package io.github.project.openubl.models.jpa.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TEMPLATE_SETTINGS")
public class WSTemplateEntity extends PanacheEntityBase {

    @Id
    @Column(name = "ID")
    @Access(AccessType.PROPERTY)
    private String id;

    @NotNull
    @Column(name = "TEMPLATE_NAME")
    private String templateName;

    @NotNull
    @Column(name = "SUNAT_URL_FACTURA_ELECTRONICA")
    private String sunatUrlFacturaElectronica;

    @NotNull
    @Column(name = "SUNAT_URL_GUIA_REMISION")
    private String sunatUrlGuiaRemision;

    @NotNull
    @Column(name = "SUNAT_URL_PERCEPCION_RETENCION")
    private String sunatUrlPercepcionRetencion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

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
