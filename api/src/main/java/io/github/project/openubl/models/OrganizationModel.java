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
package io.github.project.openubl.models;

public interface OrganizationModel extends Model {

    String MASTER_ID = "master";

    String[] SORT_BY_FIELDS = {"name"};

    OrganizationType getType();

    String getName();

    String getDescription();

    void setDescription(String description);

    boolean getUseCustomCertificates();

    void setUseCustomCertificates(boolean useCustomCertificates);

    OrganizationSettingsModel getSettings();
}
