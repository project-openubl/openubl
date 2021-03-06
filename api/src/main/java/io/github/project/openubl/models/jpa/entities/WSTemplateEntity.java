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
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "SUNAT_URL_FACTURA_ELECTRONICA")
    private String sunatUrlFactura;

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
