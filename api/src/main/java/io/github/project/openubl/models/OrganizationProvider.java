/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.project.openubl.models;

import java.util.List;
import java.util.Optional;

public interface OrganizationProvider {

    /**
     * Used for create master organization
     */
    OrganizationModel addOrganization(String id, String name, OrganizationType type, OrganizationSettingsModel.MinData data);

    OrganizationModel addOrganization(String name, OrganizationType type, OrganizationSettingsModel.MinData data);

    Optional<OrganizationModel> getOrganizationById(String id);

    Optional<OrganizationModel> getOrganizationByName(String name);

    List<OrganizationModel> getOrganizations(int offset, int limit);

    List<OrganizationModel> getOrganizations(String filterText, int offset, int limit);

    SearchResultsModel<OrganizationModel> searchOrganizations(int page, int pageSize);

    SearchResultsModel<OrganizationModel> searchOrganizations(String filterText, int page, int pageSize);

    PageModel<OrganizationModel> getOrganizationsAsPage(PageBean pageBean, List<SortBean> sortBy);

    PageModel<OrganizationModel> getOrganizationsAsPage(String filterText, PageBean pageBean, List<SortBean> sortBy);

    void deleteOrganization(OrganizationModel organization);
}
