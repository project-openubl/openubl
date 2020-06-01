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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.project.openubl.events.EventProvider;
import io.github.project.openubl.models.DocumentEvent;
import io.github.project.openubl.xmlsender.idm.DocumentRepresentation;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.jms.*;
import java.lang.IllegalStateException;

@ApplicationScoped
public class JMSEventManager {

    @ConfigProperty(name = "openubl.event-manager.jms.delay")
    Long messageDelay;

    @ConfigProperty(name = "openubl.event-manager.jms.sendFileQueue")
    String sendFileQueue;

    @ConfigProperty(name = "openubl.event-manager.jms.callbackQueue")
    String callbackQueue;

    @ConfigProperty(name = "openubl.event-manager.jms.ticketQueue")
    String ticketQueue;

    @Inject
    ConnectionFactory connectionFactory;

    public void onDocumentCreate(
            @Observes(during = TransactionPhase.AFTER_SUCCESS)
            @EventProvider(EventProvider.Type.jms) DocumentEvent.Created event
    ) {
        produceMessage(sendFileQueue, String.valueOf(event.getId()));
    }

    public void onDocumentRequireCheckTicket(
            @Observes(during = TransactionPhase.AFTER_SUCCESS)
            @EventProvider(EventProvider.Type.jms) DocumentEvent.RequireCheckTicket event
    ) {
        produceMessage(ticketQueue, String.valueOf(event.getId()));
    }

    private void produceMessage(String queueName, String messageBody) {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            JMSProducer producer = context.createProducer();
            producer.setDeliveryDelay(messageDelay);
            Queue queue = context.createQueue(queueName);
            Message message = context.createTextMessage(messageBody);
            producer.send(queue, message);
        }
    }
}
