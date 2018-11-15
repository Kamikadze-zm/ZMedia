package ru.kamikadze_zm.zmedia.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
public class FreeMarkerEmailTemplates {

    private static final Logger LOG = LogManager.getLogger(FreeMarkerEmailTemplates.class);

    private final Configuration configuration;

    @Autowired
    public FreeMarkerEmailTemplates(Configuration configuration) {
        this.configuration = configuration;
    }

    public Template getTemplate(TemplateType templateType) throws RuntimeException {
        try {
            return configuration.getTemplate(templateType.getTemplateFile(), "UTF-8");
        } catch (IOException e) {
            LOG.error("Error of getting email template: ", e);
            throw new RuntimeException(e);
        }
    }

    public Optional<String> getTemplateAsString(TemplateType templateType, Map<String, Object> model) throws RuntimeException {
        Template template = this.getTemplate(templateType);
        String html = null;
        try {
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            LOG.error("Transforming email template to string exception: ", e);
        }
        return Optional.ofNullable(html);
    }

    public static enum TemplateType {
        EMAIL_CONFIRMATION("email-confirmation.ftl"),
        PASSWORD_RESTORING("password-restoring.ftl");

        private final String templateFile;

        private TemplateType(String templateFile) {
            this.templateFile = templateFile;
        }

        public String getTemplateFile() {
            return templateFile;
        }
    }
}
