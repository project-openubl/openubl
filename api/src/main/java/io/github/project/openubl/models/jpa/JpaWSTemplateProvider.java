package io.github.project.openubl.models.jpa;

import io.github.project.openubl.models.*;
import io.github.project.openubl.models.jpa.entities.OrganizationEntity;
import io.github.project.openubl.models.jpa.entities.WSTemplateEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;

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
    public Optional<WSTemplateModel> getByName(String name) {
        WSTemplateEntity entity = WSTemplateEntity.find("name", name).firstResult();
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(new WSTemplateAdapter(entity));
    }

    @Override
    public WSTemplateModel add(String name, WSTemplateModel.MinData data) {
        return add(UUID.randomUUID().toString(), name, data);
    }

    @Override
    public WSTemplateModel add(String templateId, String name, WSTemplateModel.MinData data) {
        WSTemplateEntity entity = new WSTemplateEntity();

        entity.setId(templateId);
        entity.setName(name);

        entity.setSunatUrlFactura(data.getSunatUrlFactura());
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

    @Override
    public PageModel<WSTemplateModel> getTemplatesAsPage(PageBean pageBean, List<SortBean> sortBy) {
        Sort sort = Sort.by();
        sortBy.forEach(f -> sort.and(f.getFieldName(), f.isAsc() ? Sort.Direction.Ascending : Sort.Direction.Descending));

        PanacheQuery<WSTemplateEntity> query = WSTemplateEntity.findAll(sort)
                .range(pageBean.getOffset(), pageBean.getLimit());

        long count = query.count();
        List<WSTemplateModel> list = query.list().stream()
                .map(WSTemplateAdapter::new)
                .collect(Collectors.toList());
        return new PageModel<>(pageBean, count, list);
    }

    @Override
    public PageModel<WSTemplateModel> getTemplatesAsPage(String name, PageBean pageBean, List<SortBean> sortBy) {
        Sort sort = Sort.by();
        sortBy.forEach(f -> sort.and(f.getFieldName(), f.isAsc() ? Sort.Direction.Ascending : Sort.Direction.Descending));

        PanacheQuery<WSTemplateEntity> query = WSTemplateEntity
                .find("From WSTemplateEntity as o where lower(o.name) like ?1", "%" + name.toLowerCase() + "%")
                .range(pageBean.getOffset(), pageBean.getLimit());

        long count = query.count();
        List<WSTemplateModel> list = query.list().stream()
                .map(WSTemplateAdapter::new)
                .collect(Collectors.toList());
        return new PageModel<>(pageBean, count, list);
    }
}
