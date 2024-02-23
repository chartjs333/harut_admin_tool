package org.medical.hub.services;

import org.medical.hub.models.Options;
import org.medical.hub.repository.OptionRepository;
import org.medical.hub.request.OptionSpamRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OptionServiceImpl implements OptionService {

    private Logger logger = LoggerFactory.getLogger(OptionServiceImpl.class);
    private final String EMAIL_SPAM_CONTENT = "email_spam_content";
    private final OptionRepository optionRepository;

    @Autowired
    public OptionServiceImpl(OptionRepository optionRepository ) {
        this.optionRepository = optionRepository;
    }

    @Override
    public String getSpamContent() {
        Optional<Options> byName = this.optionRepository.findByName(EMAIL_SPAM_CONTENT);
        if(byName.isEmpty()){
            return "";
        }

        return byName.get().getValue();
    }

    @Override
    public void saveSpam(OptionSpamRequest request) {
        Optional<Options> byName = this.optionRepository.findByName(EMAIL_SPAM_CONTENT);
        Options options = byName.orElseGet(Options::new);

        options.setName(EMAIL_SPAM_CONTENT);
        options.setValue(request.getContent());
        this.optionRepository.save(options);
    }
}
