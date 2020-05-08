package io.github.project.openubl.xmlbuilder;

import io.github.project.openubl.xmlbuilderlib.config.DefaultXMLBuilderConfig;
import io.github.project.openubl.xmlbuilderlib.config.XMLBuilderConfig;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class XMLBuilderConfigProducer {

    XMLBuilderConfig config;

    @PostConstruct
    public void init() {
        config = new DefaultXMLBuilderConfig();
    }

    @Produces
    public XMLBuilderConfig getXMLBuilderConfig() {
        return config;
    }
}
