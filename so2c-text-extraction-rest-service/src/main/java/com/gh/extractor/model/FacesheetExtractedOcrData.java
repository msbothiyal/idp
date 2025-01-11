package com.gh.extractor.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
public class FacesheetExtractedOcrData extends ExtractedOcrData {

    private FacesheetSynonyms facesheetSynonyms;

    @Builder
    public FacesheetExtractedOcrData(Map<String, String> extractedDataKeyValueMap, FacesheetSynonyms facesheetSynonyms) {
        super(extractedDataKeyValueMap);
        this.facesheetSynonyms = facesheetSynonyms;
    }
}
