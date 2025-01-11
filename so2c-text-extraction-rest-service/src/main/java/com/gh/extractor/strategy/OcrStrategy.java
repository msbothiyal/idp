package com.gh.extractor.strategy;

import com.amazonaws.services.textract.model.GetDocumentAnalysisResult;
import com.amazonaws.services.textract.model.Query;
import com.amazonaws.services.textract.model.StartDocumentAnalysisResult;
import com.gh.extractor.model.ExtractedOcrData;
import com.gh.extractor.model.GetDocumentExtractRequest;
import com.gh.extractor.model.outbound.CDMResponse;
import com.gh.extractor.service.TextractAPIService;
import com.gh.extractor.utility.AmazonTextractUtil;

import java.util.List;
import java.util.Map;

public abstract class OcrStrategy {

    private final TextractAPIService textractAPIService;

    protected OcrStrategy(TextractAPIService textractAPIService) {
        this.textractAPIService = textractAPIService;
    }

    protected Map<String, String> getDocumentAnalysisResults(GetDocumentExtractRequest getDocumentExtractRequest,
                                                             List<Query> textractQueryList) {
        StartDocumentAnalysisResult startDocumentAnalysisResult =
                textractAPIService.startDocumentAnalysisAsync(getDocumentExtractRequest, textractQueryList);
        String jobId = startDocumentAnalysisResult.getJobId();
        GetDocumentAnalysisResult getDocumentAnalysisResult = textractAPIService.getDocumentAnalysis(jobId);

        return AmazonTextractUtil.extractDataFromDocumentAnalysisResult(getDocumentAnalysisResult);
    }

    public abstract CDMResponse getCDMResponse(GetDocumentExtractRequest getDocumentExtractRequest);
    protected abstract Boolean isValidDocument(ExtractedOcrData extractedData);
    protected abstract List<Query> getTextractQueries();
}
