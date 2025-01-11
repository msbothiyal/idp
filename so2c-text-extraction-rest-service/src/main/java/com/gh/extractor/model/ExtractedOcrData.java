package com.gh.extractor.model;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
public class ExtractedOcrData {
    private Map<String, String> extractedDataKeyValueMap;

    protected ExtractedOcrData(Map<String, String> extractedDataKeyValueMap) {
        this.extractedDataKeyValueMap = extractedDataKeyValueMap;
    }
}
