package io.github.project.openubl.models.jpa;

import io.github.project.openubl.models.OrganizationSettingsModel;
import io.github.project.openubl.models.jpa.entities.OrganizationSettingsEntity;

public class OrganizationSettingsAdapter implements OrganizationSettingsModel {

    private OrganizationSettingsEntity entity;

    public OrganizationSettingsAdapter(OrganizationSettingsEntity entity) {
        this.entity = entity;
    }

    @Override
    public String getId() {
        return entity.getId();
    }

    @Override
    public String getSunatUsername() {
        return entity.getSunatUsername();
    }

    @Override
    public void setSunatUsername(String sunatUsername) {
        entity.setSunatUsername(sunatUsername);
    }

    @Override
    public String getSunatPassword() {
        return entity.getSunatPassword();
    }

    @Override
    public void setSunatPassword(String sunatPassword) {
        entity.setSunatPassword(sunatPassword);
    }

    @Override
    public String getSunatUrlFactura() {
        return entity.getSunatUrlFactura();
    }

    @Override
    public void setSunatUrlFactura(String sunatUrlFactura) {
        entity.setSunatUrlFactura(sunatUrlFactura);
    }

    @Override
    public String getSunatUrlGuiaRemision() {
        return entity.getSunatUrlGuiaRemision();
    }

    @Override
    public void setSunatUrlGuiaRemision(String sunatUrlGuiaRemision) {
        entity.setSunatUrlGuiaRemision(sunatUrlGuiaRemision);
    }

    @Override
    public String getSunatUrlPercepcionRetencion() {
        return entity.getSunatUrlPercepcionRetencion();
    }

    @Override
    public void setSunatUrlPercepcionRetencion(String sunatUrlPercepcionRetencion) {
        entity.setSunatUrlPercepcionRetencion(sunatUrlPercepcionRetencion);
    }

    @Override
    public String getRuc() {
        return entity.getRuc();
    }

    @Override
    public void setRuc(String ruc) {
        entity.setRuc(ruc);
    }

    @Override
    public String getRazonSocial() {
        return entity.getRazonSocial();
    }

    @Override
    public void setRazonSocial(String razonSocial) {
        entity.setRazonSocial(razonSocial);
    }

    @Override
    public String getNombreComercial() {
        return entity.getNombreComercial();
    }

    @Override
    public void setNombreComercial(String nombreComercial) {
        entity.setNombreComercial(nombreComercial);
    }

    @Override
    public String getDepartamento() {
        return entity.getDepartamento();
    }

    @Override
    public void setDepartamento(String departamento) {
        entity.setDepartamento(departamento);
    }

    @Override
    public String getProvincia() {
        return entity.getProvincia();
    }

    @Override
    public void setProvincia(String provincia) {
        entity.setProvincia(provincia);
    }

    @Override
    public String getDistrito() {
        return entity.getDistrito();
    }

    @Override
    public void setDistrito(String distrito) {
        entity.setDistrito(distrito);
    }

    @Override
    public String getUbigeo() {
        return entity.getUbigeo();
    }

    @Override
    public void setUbigeo(String ubigeo) {
        entity.setUbigeo(ubigeo);
    }

    @Override
    public String getDireccion() {
        return entity.getDireccion();
    }

    @Override
    public void setDireccion(String direccion) {
        entity.setDireccion(direccion);
    }

    @Override
    public String getCodigoPais() {
        return entity.getCodigoPais();
    }

    @Override
    public void setCodigoPais(String codigoPais) {
        entity.setCodigoPais(codigoPais);
    }

    @Override
    public String getCodigoLocal() {
        return entity.getCodigoLocal();
    }

    @Override
    public void setCodigoLocal(String codigoLocal) {
        entity.setCodigoLocal(codigoLocal);
    }

    @Override
    public String getUrbanizacion() {
        return entity.getUrbanizacion();
    }

    @Override
    public void setUrbanizacion(String urbanizacion) {
        entity.setUrbanizacion(urbanizacion);
    }

    @Override
    public String getTelefono() {
        return entity.getTelefono();
    }

    @Override
    public void setTelefono(String telefono) {
        entity.setTelefono(telefono);
    }

    @Override
    public String getEmail() {
        return entity.getEmail();
    }

    @Override
    public void setEmail(String email) {
        entity.setEmail(email);
    }

}
