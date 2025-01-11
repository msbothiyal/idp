package com.gh.extractor.model.builder;

import com.gh.extractor.model.ExtractedOcrData;
import com.gh.extractor.model.Synonym;
import com.gh.extractor.utility.ExtractUtil;

public abstract class OcrDataEntityBuilder {

    protected ExtractedOcrData extractedOcrData;

    protected OcrDataEntityBuilder(ExtractedOcrData extractedOcrData) {
        this.extractedOcrData = extractedOcrData;
    }

    protected String findValue(Synonym targetSynonym) {
        return ExtractUtil.getFieldValue(this.extractedOcrData, targetSynonym);
    }
}
