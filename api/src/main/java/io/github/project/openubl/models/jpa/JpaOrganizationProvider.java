/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 * <p>
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.eclipse.org/legal/epl-2.0/
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.models.jpa;

import io.github.project.openubl.models.*;
import io.github.project.openubl.models.jpa.entities.OrganizationEntity;
import io.github.project.openubl.models.jpa.entities.OrganizationSettingsEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class JpaOrganizationProvider implements OrganizationProvider {

    @Inject
    EntityManager em;

    @Override
    public OrganizationModel addOrganization(String id, String name, OrganizationType type, OrganizationSettingsModel.MinData data) {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId(id);
        organizationEntity.setName(name);
        organizationEntity.setType(type);
        organizationEntity.setUseCustomCertificates(true);

        OrganizationSettingsEntity settingsEntity = new OrganizationSettingsEntity();
        settingsEntity.setId(UUID.randomUUID().toString());
        settingsEntity.setRuc(data.getRuc());
        settingsEntity.setRazonSocial(data.getRazonSocial());
        settingsEntity.setSunatUsername(data.getSunatUsername());
        settingsEntity.setSunatPassword(data.getSunatPassword());
        settingsEntity.setSunatUrlFactura(data.getSunatUrlFactura());
        settingsEntity.setSunatUrlGuiaRemision(data.getSunatUrlGuiaRemision());
        settingsEntity.setSunatUrlPercepcionRetencion(data.getSunatUrlPercepcionRetencion());

        settingsEntity.setOrganization(organizationEntity);
        organizationEntity.setSettings(settingsEntity);
        em.persist(organizationEntity);

        return new OrganizationAdapter(organizationEntity);
    }

    @Override
    public OrganizationModel addOrganization(String name, OrganizationType type, OrganizationSettingsModel.MinData data) {
        return addOrganization(UUID.randomUUID().toString(), name, type, data);
    }

    @Override
    public Optional<OrganizationModel> getOrganizationById(String id) {
        OrganizationEntity organizationEntity = em.find(OrganizationEntity.class, id);
        if (organizationEntity == null) return Optional.empty();
        return Optional.of(new OrganizationAdapter(organizationEntity));
    }

    @Override
    public Optional<OrganizationModel> getOrganizationByName(String name) {
        TypedQuery<OrganizationEntity> query = em.createNamedQuery("FindByName", OrganizationEntity.class);
        query.setParameter("name", name);

        List<OrganizationEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else if (resultList.size() == 1) {
            return Optional.of(new OrganizationAdapter(resultList.get(0)));
        } else {
            throw new IllegalStateException("More than one Organization with name=" + name);
        }
    }

    @Override
    public List<OrganizationModel> getOrganizations(int offset, int limit) {
        TypedQuery<OrganizationEntity> query = em.createNamedQuery("ListOrganizations", OrganizationEntity.class);
        if (offset != -1) {
            query.setFirstResult(offset);
        }
        if (limit != -1) {
            query.setMaxResults(limit);
        }
        return query.getResultList()
                .stream()
                .map(OrganizationAdapter::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrganizationModel> getOrganizations(String filterText, int offset, int limit) {
        TypedQuery<OrganizationEntity> query = em.createNamedQuery("FilterOrganizations", OrganizationEntity.class);
        query.setParameter("filterText", "%" + filterText.toLowerCase() + "%");
        if (offset != -1) {
            query.setFirstResult(offset);
        }
        if (limit != -1) {
            query.setMaxResults(limit);
        }
        return query.getResultList()
                .stream()
                .map(OrganizationAdapter::new)
                .collect(Collectors.toList());
    }

    @Override
    public SearchResultsModel<OrganizationModel> searchOrganizations(int page, int pageSize) {
        String query = "from OrganizationEntity o";

        PanacheQuery<OrganizationEntity> panacheQuery = OrganizationEntity.find(query);
        panacheQuery.page(new Page(page, pageSize));

        long count = panacheQuery.count();
        List<OrganizationModel> list = panacheQuery.list()
                .stream()
                .map(OrganizationAdapter::new)
                .collect(Collectors.toList());

        return new SearchResultsModel<>(count, list);
    }

    @Override
    public SearchResultsModel<OrganizationModel> searchOrganizations(String filterText, int page, int pageSize) {
        String query = "from OrganizationEntity o where lower(o.name) like :filterText";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filterText", "%" + filterText.toLowerCase() + "%");

        PanacheQuery<OrganizationEntity> panacheQuery = OrganizationEntity.find(query, parameters);
        panacheQuery.page(new Page(page, pageSize));

        long count = panacheQuery.count();
        List<OrganizationModel> list = panacheQuery.list()
                .stream()
                .map(OrganizationAdapter::new)
                .collect(Collectors.toList());

        return new SearchResultsModel<>(count, list);
    }

    @Override
    public PageModel<OrganizationModel> getOrganizationsAsPage(PageBean pageBean, List<SortBean> sortBy) {
        Sort sort = Sort.by();
        sortBy.forEach(f -> sort.and(f.getFieldName(), f.isAsc() ? Sort.Direction.Ascending : Sort.Direction.Descending));

        PanacheQuery<OrganizationEntity> query = OrganizationEntity.findAll(sort)
                .range(pageBean.getOffset(), pageBean.getOffset() + pageBean.getLimit() - 1);

        long count = query.count();
        List<OrganizationModel> list = query.list().stream()
                .map(OrganizationAdapter::new)
                .collect(Collectors.toList());
        return new PageModel<>(pageBean, count, list);
    }

    @Override
    public PageModel<OrganizationModel> getOrganizationsAsPage(String name, PageBean pageBean, List<SortBean> sortBy) {
        Sort sort = Sort.by();
        sortBy.forEach(f -> sort.and(f.getFieldName(), f.isAsc() ? Sort.Direction.Ascending : Sort.Direction.Descending));

        PanacheQuery<OrganizationEntity> query = OrganizationEntity
                .find("From OrganizationEntity as o where lower(o.name) like ?1", "%" + name.toLowerCase() + "%")
                .range(pageBean.getOffset(), pageBean.getOffset() + pageBean.getLimit() - 1);

        long count = query.count();
        List<OrganizationModel> list = query.list().stream()
                .map(OrganizationAdapter::new)
                .collect(Collectors.toList());
        return new PageModel<>(pageBean, count, list);
    }

    @Override
    public void deleteOrganization(OrganizationModel model) {
        OrganizationEntity organization = OrganizationEntity.findById(model.getId());
        em.remove(organization);
    }

}
