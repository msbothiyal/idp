package com.gh.extractor.service;

import com.gh.extractor.model.GetDocumentExtractRequest;
import com.gh.extractor.model.outbound.CDMResponse;

public interface ExtractService {
    CDMResponse generateCDMResponse(GetDocumentExtractRequest getDocumentExtractRequest);
}
