package com.gh.extractor.utility;

import com.gh.extractor.model.ExtractedOcrData;
import com.gh.extractor.model.Synonym;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.util.StringUtils;

import java.util.Map;

public final class ExtractUtil {

    private static LevenshteinDistance ld;

    public static String getFieldValue(ExtractedOcrData extractedOcrData, Synonym synonym) {

        String retrievedFieldVal;
        String fullSynonym;
        for (var lookup : extractedOcrData.getExtractedDataKeyValueMap().entrySet()) {
            for (String prefix : synonym.getPrefixes()) {
                if (!synonym.getSuffixes().isEmpty()) {
                    for (String suffix : synonym.getSuffixes()) {
                        if (!prefix.equals(suffix)) {
                            fullSynonym = String.format("%s %s", prefix.trim(), suffix.trim());
                            retrievedFieldVal = findSynonymValue(fullSynonym, lookup);

                            if (StringUtils.hasLength(retrievedFieldVal)) {
                                return retrievedFieldVal;
                            }
                        }
                    }
                }
                retrievedFieldVal =  findSynonymValue(prefix, lookup);
                if (StringUtils.hasLength(retrievedFieldVal)) {
                    return retrievedFieldVal;
                }
            }
        }
        return null;
    }
    private static String findSynonymValue(String fullSynonym, Map.Entry<String, String> lookup) {
        double fifteenPercentThreshold = lookup.getKey().length() * .15;
        ld = new LevenshteinDistance((int) fifteenPercentThreshold);
        if (ld.apply(fullSynonym, lookup.getKey()) >= 0) {
            return lookup.getValue();
        }
        return null;
    }
}
