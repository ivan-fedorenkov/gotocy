package org.gotocy.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.gotocy.domain.i18n.LocalizedPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * @author ifedorenkov
 */
@Service
public class TemplatesServiceImpl implements TemplatesService {
	private static final Logger logger = LoggerFactory.getLogger(TemplatesServiceImpl.class);

	private final Configuration freemarkerConfig;

	@Autowired
	public TemplatesServiceImpl(Configuration freemarkerConfig) {
		this.freemarkerConfig = freemarkerConfig;
	}

	@Override
	public String processTemplateIntoString(LocalizedPage templatePage, Map<String, Object> model) {
		try (StringReader templateReader = new StringReader(templatePage.getHtml())) {
			return FreeMarkerTemplateUtils.processTemplateIntoString(
				new Template(templatePage.getTitle(), templateReader, freemarkerConfig), model);
		} catch (IOException | TemplateException ioe) {
			logger.error("Failed to process template page '{}'", templatePage.getUrl(), ioe);
			throw new ServiceMethodExecutionException(ioe);
		}
	}

}
