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

import io.github.project.openubl.models.*;
import io.github.project.openubl.models.utils.ModelToRepresentation;
import io.github.project.openubl.representations.idm.PageRepresentation;
import io.github.project.openubl.representations.idm.WSTemplateRepresentation;
import io.github.project.openubl.utils.ResourceUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.net.URISyntaxException;
import java.util.List;

@Transactional
@ApplicationScoped
@Path("templates")
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "templates")
public class WSTemplatesResource {

    @Context
    UriInfo uriInfo;

    @Inject
    WSTemplateProvider wsTemplateProvider;

    @Inject
    ModelToRepresentation modelToRepresentation;

    @GET
    @Path("/ws")
    @Produces(MediaType.APPLICATION_JSON)
    public PageRepresentation<WSTemplateRepresentation> getWSTemplates(
            @QueryParam("name") String name,
            @QueryParam("offset") @DefaultValue("0") Integer offset,
            @QueryParam("limit") Integer limit,
            @QueryParam("sort_by") @DefaultValue("name") List<String> sortBy
    ) throws URISyntaxException {
        PageBean pageBean = ResourceUtils.getPageBean(offset, limit);
        List<SortBean> sortBeans = ResourceUtils.getSortBeans(sortBy, WSTemplateModel.SORT_BY_FIELDS);

        PageModel<WSTemplateModel> pageModel;
        if (name != null && !name.trim().isEmpty()) {
            pageModel = wsTemplateProvider.getTemplatesAsPage(name, pageBean, sortBeans);
        } else {
            pageModel = wsTemplateProvider.getTemplatesAsPage(pageBean, sortBeans);
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

}
