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
package org.openublpe.xmlbuilder.apisigner.models.jpa.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.openublpe.xmlbuilder.apisigner.models.OrganizationType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORGANIZATION", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"NAME"})
})
@NamedQueries(value = {
        @NamedQuery(name = "FindByName", query = "select o from OrganizationEntity o where o.name = :name"),
        @NamedQuery(name = "ListOrganizations", query = "select o from OrganizationEntity o"),
        @NamedQuery(name = "FilterOrganizations", query = "select o from OrganizationEntity o where lower(o.name) like :filterText")
})
public class OrganizationEntity extends PanacheEntityBase {

    @Id
    @Column(name = "ID")
    @Access(AccessType.PROPERTY)
    private String id;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "TYPE")
    private OrganizationType type;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
//    @Type(type = "org.hibernate.type.TrueFalseType")
    @Column(name = "USE_CUSTOM_CERTIFICATES")
    private boolean useCustomCertificates;

    @Version
    @Column(name = "VERSION")
    private int version;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "organization")
    private Set<ComponentEntity> components = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OrganizationEntity)) {
            return false;
        }
        OrganizationEntity other = (OrganizationEntity) obj;
        if (id != null) {
            if (!id.equals(other.id)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public boolean isUseCustomCertificates() {
        return useCustomCertificates;
    }

    public void setUseCustomCertificates(boolean useCustomCertificates) {
        this.useCustomCertificates = useCustomCertificates;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Set<ComponentEntity> getComponents() {
        return components;
    }

    public void setComponents(Set<ComponentEntity> components) {
        this.components = components;
    }
}
