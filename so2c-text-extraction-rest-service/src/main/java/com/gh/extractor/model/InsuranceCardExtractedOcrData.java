package com.gh.extractor.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class InsuranceCardExtractedOcrData extends ExtractedOcrData {

    private InsuranceCardSynonyms insuranceCardSynonyms;

    @Builder
    InsuranceCardExtractedOcrData(Map<String, String> extractedDataKeyValueMap, InsuranceCardSynonyms insuranceCardSynonyms) {
        super(extractedDataKeyValueMap);
        this.insuranceCardSynonyms = insuranceCardSynonyms;
    }
}
