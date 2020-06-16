package io.github.project.openubl.models.jpa;

import io.github.project.openubl.models.WSTemplateModel;
import io.github.project.openubl.models.jpa.entities.WSTemplateEntity;

public class WSTemplateAdapter implements WSTemplateModel {

    private final WSTemplateEntity entity;

    public WSTemplateAdapter(WSTemplateEntity entity) {
        this.entity = entity;
    }

    @Override
    public String getId() {
        return entity.getId();
    }

    @Override
    public String getName() {
        return entity.getName();
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
}
