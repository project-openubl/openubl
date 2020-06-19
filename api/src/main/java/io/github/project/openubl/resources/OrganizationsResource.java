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
package io.github.project.openubl.resources;

import io.github.project.openubl.keys.component.utils.ComponentUtil;
import io.github.project.openubl.managers.OrganizationManager;
import io.github.project.openubl.models.*;
import io.github.project.openubl.models.utils.ModelToRepresentation;
import io.github.project.openubl.models.utils.RepresentationToModel;
import io.github.project.openubl.representations.idm.OrganizationRepresentation;
import io.github.project.openubl.representations.idm.PageRepresentation;
import io.github.project.openubl.utils.ResourceUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.net.URISyntaxException;
import java.util.List;

@Transactional
@ApplicationScoped
@Path("organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "organization")
public class OrganizationsResource {

    private static final Logger logger = Logger.getLogger(OrganizationsResource.class);

    @Context
    UriInfo uriInfo;

    @Inject
    KeyManager keystore;

    @Inject
    ComponentUtil componentUtil;

    @Inject
    ComponentProvider componentProvider;

    @Inject
    OrganizationManager organizationManager;

    @Inject
    OrganizationProvider organizationProvider;

    @Inject
    ModelToRepresentation modelToRepresentation;

    @Inject
    RepresentationToModel representationToModel;

    @GET
    @Path("/")
    public PageRepresentation<OrganizationRepresentation> getOrganizations(
            @QueryParam("name") String name,
            @QueryParam("offset") @DefaultValue("0") Integer offset,
            @QueryParam("limit") @DefaultValue("10") Integer limit,
            @QueryParam("sort_by") @DefaultValue("name") List<String> sortBy
    ) throws URISyntaxException {
        PageBean pageBean = ResourceUtils.getPageBean(offset, limit);
        List<SortBean> sortBeans = ResourceUtils.getSortBeans(sortBy, OrganizationModel.SORT_BY_FIELDS);

        PageModel<OrganizationModel> pageModel;
        if (name != null && !name.trim().isEmpty()) {
            pageModel = organizationProvider.getOrganizationsAsPage(name, pageBean, sortBeans);
        } else {
            pageModel = organizationProvider.getOrganizationsAsPage(pageBean, sortBeans);
        }

        List<NameValuePair> queryParameters = ResourceUtils.buildNameValuePairs(offset, limit, sortBeans);
        if (name != null) {
            queryParameters.add(new BasicNameValuePair("name", name));
        }
        return modelToRepresentation.toRepresentation(
                pageModel,
                model -> modelToRepresentation.toRepresentation(model),
                uriInfo,
                queryParameters
        );
    }

    @POST
    @Path("/")
    public OrganizationRepresentation createOrganization(@Valid OrganizationRepresentation representation) {
        if (organizationProvider.getOrganizationByName(representation.getName()).isPresent()) {
            throw new BadRequestException("Organization with name=" + representation.getName() + " already exists");
        }
        OrganizationModel organization = organizationManager.createOrganization(representation);
        return modelToRepresentation.toRepresentation(organization);
    }

    //    @GET
//    @Path("/search")
//    public SearchResultsRepresentation<OrganizationRepresentation> searchOrganizations(
//            @QueryParam("filterText") String filterText,
//            @QueryParam("page") @DefaultValue("0") int page,
//            @QueryParam("pageSize") @DefaultValue("10") int pageSize
//    ) {
//        SearchResultsModel<OrganizationModel> results;
//        if (filterText != null && !filterText.trim().isEmpty()) {
//            results = organizationProvider.searchOrganizations(filterText, page, pageSize);
//        } else {
//            results = organizationProvider.searchOrganizations(page, pageSize);
//        }
//
//        return new SearchResultsRepresentation<>(
//                results.getTotalSize(),
//                results.getModels().stream()
//                        .map(model -> modelToRepresentation.toRepresentation(model, true))
//                        .collect(Collectors.toList())
//        );
//    }
//
//    @GET
//    @Path("/all")
//    public List<OrganizationRepresentation> getAllOrganizations() {
//        return organizationProvider.getOrganizations(-1, -1)
//                .stream()
//                .map(model -> modelToRepresentation.toRepresentation(model, true))
//                .collect(Collectors.toList());
//    }
//
//    @GET
//    @Path("/id-by-name/{organizationName}")
//    public String getOrganizationIdByName(
//            @PathParam("organizationName") String organizationName
//    ) {
//        return organizationProvider.getOrganizationByName(organizationName)
//                .map(OrganizationModel::getId)
//                .orElse(null);
//    }
//
    @GET
    @Path("/{organizationId}")
    public OrganizationRepresentation getOrganization(
            @PathParam("organizationId") String organizationId
    ) {
        return organizationProvider.getOrganizationById(organizationId)
                .map(organizationModel -> modelToRepresentation.toRepresentation(organizationModel))
                .orElseThrow(() -> new NotFoundException("Organization not found"));
    }

    @PUT
    @Path("/{organizationId}")
    public OrganizationRepresentation updateOrganization(
            @PathParam("organizationId") String organizationId,
            OrganizationRepresentation rep
    ) {
        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization not found"));
        representationToModel.updateOrganization(rep, organization);
        return modelToRepresentation.toRepresentation(organization);
    }

//    @DELETE
//    @Path("/{organizationId}")
//    public void deleteOrganization(
//            @PathParam("organizationId") String organizationId
//    ) {
//        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
//        if (OrganizationModel.MASTER_ID.equals(organization.getId())) {
//            throw new BadRequestException("La organización 'master' no puede ser elminada");
//        }
//
//        organizationProvider.deleteOrganization(organization);
//    }
//
//    @GET
//    @Path("/{organizationId}/keys")
//    @Produces(MediaType.APPLICATION_JSON)
//    public KeysMetadataRepresentation getKeyMetadata(
//            @PathParam("organizationId") final String organizationId
//    ) {
//        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
//
//        KeysMetadataRepresentation keys = new KeysMetadataRepresentation();
//        keys.setKeys(new LinkedList<>());
//        keys.setActive(new HashMap<>());
//
//        for (KeyWrapper key : keystore.getKeys(organization)) {
//            KeysMetadataRepresentation.KeyMetadataRepresentation r = new KeysMetadataRepresentation.KeyMetadataRepresentation();
//            r.setProviderId(key.getProviderId());
//            r.setProviderPriority(key.getProviderPriority());
//            r.setKid(key.getKid());
//            r.setStatus(key.getStatus() != null ? key.getStatus().name() : null);
//            r.setType(key.getType());
//            r.setAlgorithm(key.getAlgorithm());
//            r.setPublicKey(key.getPublicKey() != null ? PemUtils.encodeKey(key.getPublicKey()) : null);
//            r.setCertificate(key.getCertificate() != null ? PemUtils.encodeCertificate(key.getCertificate()) : null);
//            keys.getKeys().add(r);
//
//            if (key.getStatus().isActive()) {
//                if (!keys.getActive().containsKey(key.getAlgorithm())) {
//                    keys.getActive().put(key.getAlgorithm(), key.getKid());
//                }
//            }
//        }
//
//        return keys;
//    }
//
//    @GET
//    @Path("/{organizationId}/components")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<ComponentRepresentation> getComponents(
//            @PathParam("organizationId") final String organizationId,
//            @QueryParam("parent") String parent,
//            @QueryParam("type") String type,
//            @QueryParam("name") String name
//    ) {
//        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
//
//        List<ComponentModel> components;
//        if (parent == null && type == null) {
//            components = componentProvider.getComponents(organization);
//        } else if (type == null) {
//            components = componentProvider.getComponents(organization, parent);
//        } else if (parent == null) {
//            components = componentProvider.getComponents(organization, organization.getId(), type);
//        } else {
//            components = componentProvider.getComponents(organization, parent, type);
//        }
//        List<ComponentRepresentation> reps = new LinkedList<>();
//        for (ComponentModel component : components) {
//            if (name != null && !name.equals(component.getName())) continue;
//            ComponentRepresentation rep = null;
//            try {
//                rep = modelToRepresentation.toRepresentation(component, false, componentUtil);
//            } catch (Exception e) {
//                logger.error("Failed to get component list for component model" + component.getName() + "of organization " + organization.getName());
//                rep = modelToRepresentation.toRepresentationWithoutConfig(component);
//            }
//            reps.add(rep);
//        }
//        return reps;
//    }
//
//    @POST
//    @Path("/{organizationId}/components")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createComponent(
//            @PathParam("organizationId") final String organizationId, ComponentRepresentation rep
//    ) {
//        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
//
//        try {
//            ComponentModel model = representationToModel.toModel(rep);
//            if (model.getParentId() == null) model.setParentId(organization.getId());
//
//            model = componentProvider.addComponentModel(organization, model);
//
//            return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId()).build()).build();
//        } catch (ComponentValidationException e) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity(e.getMessage())
//                    .type(MediaType.APPLICATION_JSON)
//                    .build();
//        }
//    }
//
//    @GET
//    @Path("/{organizationId}/components/{componentId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public ComponentRepresentation getComponent(
//            @PathParam("organizationId") final String organizationId,
//            @PathParam("componentId") String componentId
//    ) {
//        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
//
//        ComponentModel model = componentProvider.getComponent(organization, componentId);
//        if (model == null) {
//            throw new NotFoundException("Could not find component");
//        }
//
//        return modelToRepresentation.toRepresentation(model, false, componentUtil);
//    }
//
//    @PUT
//    @Path("/{organizationId}/components/{componentId}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateComponent(
//            @PathParam("organizationId") final String organizationId,
//            @PathParam("componentId") String componentId,
//            ComponentRepresentation rep
//    ) {
//        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
//
//        try {
//            ComponentModel model = componentProvider.getComponent(organization, componentId);
//            if (model == null) {
//                throw new NotFoundException("Could not find component");
//            }
//            representationToModel.updateComponent(rep, model, false, componentUtil);
//
//            componentProvider.updateComponent(organization, model);
//            return Response.noContent().build();
//        } catch (ComponentValidationException e) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity(e.getMessage())
//                    .type(MediaType.APPLICATION_JSON)
//                    .build();
//        }
//    }
//
//    @DELETE
//    @Path("/{organizationId}/components/{componentId}")
//    public void removeComponent(
//            @PathParam("organizationId") final String organizationId,
//            @PathParam("componentId") String componentId
//    ) {
//        OrganizationModel organization = organizationProvider.getOrganizationById(organizationId).orElseThrow(() -> new NotFoundException("Organization not found"));
//
//        ComponentModel model = componentProvider.getComponent(organization, componentId);
//        if (model == null) {
//            throw new NotFoundException("Could not find component");
//        }
//
//        componentProvider.removeComponent(organization, model);
//    }

}
