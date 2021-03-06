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
package io.github.project.openubl.models.utils;

import io.github.project.openubl.models.jpa.entities.DocumentEntity;
import io.github.project.openubl.representations.idm.DocumentRepresentation;

public class EntityToRepresentation {
    private EntityToRepresentation() {
        // Just static methods
    }

    public static DocumentRepresentation toRepresentation(DocumentEntity documentEntity) {
        DocumentRepresentation rep = new DocumentRepresentation();

        rep.setId(documentEntity.getId());
        rep.setFileStorageId(documentEntity.getFileStorageId());
        rep.setDocumentSenderId(documentEntity.getDocumentSenderId());

        // UBL Info
        DocumentRepresentation.UBLInfo ublInfo = new DocumentRepresentation.UBLInfo();
        rep.setUblInfo(ublInfo);

        ublInfo.setDocumentType(documentEntity.getDocumentType() != null ? documentEntity.getDocumentType().toString() : null);
        ublInfo.setDocumentId(documentEntity.getDocumentId());
        ublInfo.setSupplierId(documentEntity.getSupplierId());
        ublInfo.setSupplierName(documentEntity.getSupplierName());
        ublInfo.setSupplierAdditionalName(documentEntity.getSupplierAdditionalName());
        ublInfo.setCustomerName(documentEntity.getCustomerName());
        ublInfo.setCustomerId(documentEntity.getCustomerId());

        return rep;
    }
}
