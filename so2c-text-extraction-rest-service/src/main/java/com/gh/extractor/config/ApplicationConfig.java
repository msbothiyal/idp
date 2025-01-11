package com.gh.extractor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gh.extractor.model.FacesheetSynonyms;
import com.gh.extractor.model.InsuranceCardSynonyms;
import com.gh.extractor.model.Synonym;
import com.gh.extractor.validation.DocumentExtractRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ApplicationConfig {

    private static final String FACESHEET_SPEC_FILE = "synonyms/facesheet.json";
    private static final String INSURANCE_CARD_SPEC_FILE = "synonyms/insurancecard.json";

    @Bean
    public DocumentExtractRequestValidator documentExtractRequestValidator() {
        return new DocumentExtractRequestValidator();
    }
    @Bean
    public FacesheetSynonyms patientFacesheetFieldSynonymList() throws IOException {
        ClassPathResource res = new ClassPathResource(FACESHEET_SPEC_FILE);
        return new ObjectMapper().readValue(res.getFile(), FacesheetSynonyms.class);
    }
    @Bean
    public InsuranceCardSynonyms insuranceCardFieldSynonymList() throws IOException {
        ClassPathResource res = new ClassPathResource(INSURANCE_CARD_SPEC_FILE);
        return new ObjectMapper().readValue(res.getFile(), InsuranceCardSynonyms.class);
    }
}
