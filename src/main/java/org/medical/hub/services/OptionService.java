package org.medical.hub.services;

import org.medical.hub.request.CreateEmailTemplateRequest;
import org.medical.hub.request.OptionSpamRequest;

public interface OptionService {

    String getSpamContent();

    void saveSpam(OptionSpamRequest request);
}
