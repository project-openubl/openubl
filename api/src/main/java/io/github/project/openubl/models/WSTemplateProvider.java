package io.github.project.openubl.models;

import java.util.List;
import java.util.Optional;

public interface WSTemplateProvider {

    String SUNAT_BETA = "sunatBeta";
    String SUNAT_PROD = "sunatProd";

    Optional<WSTemplateModel> getById(String templateId);

    Optional<WSTemplateModel> getByName(String templateName);

    WSTemplateModel add(String templateName, WSTemplateModel.MinData data);

    WSTemplateModel add(String templateId, String templateName, WSTemplateModel.MinData data);

    List<WSTemplateModel> getAll();

}
