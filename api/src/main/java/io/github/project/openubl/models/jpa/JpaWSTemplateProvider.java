package io.github.project.openubl.models.jpa;

import io.github.project.openubl.models.WSTemplateModel;
import io.github.project.openubl.models.WSTemplateProvider;
import io.github.project.openubl.models.jpa.entities.WSTemplateEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class JpaWSTemplateProvider implements WSTemplateProvider {

    @Override
    public Optional<WSTemplateModel> getById(String templateId) {
        WSTemplateEntity entity = WSTemplateEntity.findById(templateId);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(new WSTemplateAdapter(entity));
    }

    @Override
    public Optional<WSTemplateModel> getByName(String templateName) {
        WSTemplateEntity entity = WSTemplateEntity.find("templateName", templateName).firstResult();
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(new WSTemplateAdapter(entity));
    }

    @Override
    public WSTemplateModel add(String templateName, WSTemplateModel.MinData data) {
        return add(UUID.randomUUID().toString(), templateName, data);
    }

    @Override
    public WSTemplateModel add(String templateId, String templateName, WSTemplateModel.MinData data) {
        WSTemplateEntity entity = new WSTemplateEntity();

        entity.setId(templateId);
        entity.setTemplateName(templateName);

        entity.setSunatUrlFacturaElectronica(data.getSunatUrlFacturaElectronica());
        entity.setSunatUrlGuiaRemision(data.getSunatUrlGuiaRemision());
        entity.setSunatUrlPercepcionRetencion(data.getSunatUrlPercepcionRetencion());

        entity.persist();

        return new WSTemplateAdapter(entity);
    }

    @Override
    public List<WSTemplateModel> getAll() {
        List<WSTemplateEntity> entities = WSTemplateEntity.listAll();
        return entities.stream()
                .map(WSTemplateAdapter::new)
                .collect(Collectors.toList());
    }
}
