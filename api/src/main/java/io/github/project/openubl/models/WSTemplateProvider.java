package io.github.project.openubl.models;

import java.util.List;
import java.util.Optional;

public interface WSTemplateProvider {

    String SUNAT_BETA = "sunatBeta";
    String SUNAT_PROD = "sunatProd";

    Optional<WSTemplateModel> getById(String templateId);

    Optional<WSTemplateModel> getByName(String name);

    WSTemplateModel add(String name, WSTemplateModel.MinData data);

    WSTemplateModel add(String templateId, String name, WSTemplateModel.MinData data);

    List<WSTemplateModel> getAll();

    PageModel<WSTemplateModel> getTemplatesAsPage(PageBean pageBean, List<SortBean> sortBy);

    PageModel<WSTemplateModel> getTemplatesAsPage(String name, PageBean pageBean, List<SortBean> sortBy);

}
