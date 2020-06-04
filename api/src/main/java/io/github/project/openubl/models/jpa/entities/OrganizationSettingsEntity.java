package io.github.project.openubl.models.jpa.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ORGANIZATION_SETTINGS")
public class OrganizationSettingsEntity extends PanacheEntityBase {

    @Id
    @Column(name = "ID")
    @Access(AccessType.PROPERTY)
    private String id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey, name = "ORGANIZATION_ID")
    private OrganizationEntity organization;

    @NotNull
    @Column(name = "SUNAT_USERNAME")
    private String sunatUsername;

    @NotNull
    @Column(name = "SUNAT_PASSWORD")
    private String sunatPassword;

    @NotNull
    @Column(name = "SUNAT_URL_FACTURA_ELECTRONICA")
    private String sunatUrlFacturaElectronica;

    @NotNull
    @Column(name = "SUNAT_URL_GUIA_REMISION")
    private String sunatUrlGuiaRemision;

    @NotNull
    @Column(name = "SUNAT_URL_PERCEPCION_RETENCION")
    private String sunatUrlPercepcionRetencion;

    @NotNull
    @Column(name = "RUC")
    private String ruc;

    @NotNull
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;

    @Column(name = "NOMBRE_COMERCIAL")
    private String nombreComercial;

    @Column(name = "UBIGEO")
    private String ubigeo;

    @Column(name = "CODIGO_LOCAL")
    private String codigoLocal;

    @Column(name = "URBANIZACION")
    private String urbanizacion;

    @Column(name = "PROVINCIA")
    private String provincia;

    @Column(name = "DEPARTAMENTO")
    private String departamento;

    @Column(name = "DISTRITO")
    private String distrito;

    @Column(name = "ADDRESS")
    private String direccion;

    @Column(name = "CODIGO_PAIS")
    private String codigoPais;

    @Column(name = "CONTACTO_TELEFONO")
    private String telefono;

    @Column(name = "CONTACTO_EMAIL")
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrganizationEntity getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationEntity organization) {
        this.organization = organization;
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

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
