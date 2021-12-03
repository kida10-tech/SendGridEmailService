package com.sendgridemail.template.service.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendgrid.*;
import com.sendgridemail.template.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImplementation implements EmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImplementation.class);

	public String sendTemplate() throws IOException {

		Email from = new Email("Example email");
		Email to = new Email("Example email");
		Mail mail = new Mail();
		DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
		personalization.addTo(to);
		mail.setFrom(from);
		mail.setSubject("Example subject");

		personalization.addDynamicTemplateData("key", "value");
		mail.addPersonalization(personalization);
		mail.setTemplateId("Template-id");
		//You can save the api-key as an environment variable as well
		SendGrid sg = new SendGrid("SendGrid-Api-Key");
		Request request = new Request();

		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			logger.info(response.getBody());
			return response.getBody();
		} catch (IOException ex) {
			throw ex;
		}
	}

	//Customization message
	private static class DynamicTemplatePersonalization extends Personalization {

		@JsonProperty(value = "dynamic_template_data")
		private Map<String, String> dynamic_template_data;

		@JsonProperty("dynamic_template_data")
		public Map<String, String> getDynamicTemplateData() {
			if (dynamic_template_data == null) {
				return Collections.<String, String>emptyMap();
			}
			return dynamic_template_data;
		}

		public void addDynamicTemplateData(String key, String value) {
			if (dynamic_template_data == null) {
				dynamic_template_data = new HashMap<String, String>();
				dynamic_template_data.put(key, value);
			} else {
				dynamic_template_data.put(key, value);
			}
		}

	}

}
