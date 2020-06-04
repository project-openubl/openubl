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
    public String getTemplateName() {
        return entity.getTemplateName();
    }

    @Override
    public String getSunatUrlFacturaElectronica() {
        return entity.getSunatUrlFacturaElectronica();
    }

    @Override
    public void setSunatUrlFacturaElectronica(String sunatUrlFacturaElectronica) {
        entity.setSunatUrlFacturaElectronica(sunatUrlFacturaElectronica);
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
