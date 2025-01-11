package com.gh.extractor.service;

import com.amazonaws.services.textract.model.GetDocumentAnalysisResult;
import com.amazonaws.services.textract.model.Query;
import com.amazonaws.services.textract.model.StartDocumentAnalysisResult;
import com.gh.extractor.model.ExtractedOcrData;
import com.gh.extractor.model.GetDocumentExtractRequest;

import java.util.List;

/*
 * @author msbothiyal
 * @date 28/07/22,4:39 PM
 */
public interface TextractAPIService {

    StartDocumentAnalysisResult startDocumentAnalysisAsync(GetDocumentExtractRequest getDocumentExtractRequest,
                                                           List<Query> queryList);

    GetDocumentAnalysisResult getDocumentAnalysis(String jobId);
}
