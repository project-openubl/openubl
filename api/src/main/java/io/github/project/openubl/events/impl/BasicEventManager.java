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
package io.github.project.openubl.events.impl;

import io.github.project.openubl.events.EventProvider;
import io.github.project.openubl.managers.IndexManager;
import io.github.project.openubl.models.DocumentEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

@ApplicationScoped
public class BasicEventManager {

    private static final Logger LOG = Logger.getLogger(BasicEventManager.class);

    @Inject
    IndexManager indexManager;

    public void onDocumentCreate(
            @Observes(during = TransactionPhase.AFTER_SUCCESS)
            @EventProvider(EventProvider.Type.basic) DocumentEvent.Created event
    ) {
        indexManager.indexDocumentById(event.getOrganizationId(), event.getId());
    }

}
