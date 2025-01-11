package com.gh.extractor.service.impl;

import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.model.*;
import com.gh.extractor.model.GetDocumentExtractRequest;
import com.gh.extractor.service.TextractAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * @author msbothiyal
 * @date 28/07/22,4:40 PM
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class TextractAPIServiceImpl implements TextractAPIService {

    private final AmazonTextract textractClient;

    @Override
    public StartDocumentAnalysisResult startDocumentAnalysisAsync(GetDocumentExtractRequest getDocumentExtractRequest,
                                                                  List<Query> queryList) {

        AmazonS3URI s3URI = new AmazonS3URI(getDocumentExtractRequest.getS3BucketFileLocation());
        var startDocumentAnalysisRequest = new StartDocumentAnalysisRequest()
                .withFeatureTypes(FeatureType.FORMS, FeatureType.QUERIES)
                .withQueriesConfig(new QueriesConfig().withQueries(queryList))
                .withDocumentLocation(new DocumentLocation().withS3Object(new S3Object()
                        .withName(s3URI.getKey()).withBucket(s3URI.getBucket())));
        return textractClient.startDocumentAnalysis(startDocumentAnalysisRequest);
    }

    @Override
    public GetDocumentAnalysisResult getDocumentAnalysis(String jobId) {

        boolean done = false;
        String jobStatus;
        while (!done) {
            jobStatus = getJobResults(jobId);
            if (jobStatus.equals("SUCCEEDED")){
                done = true;
            }
        }

        GetDocumentAnalysisResult getDocumentAnalysisResult = null;
        List<Block> totalBlocks = new ArrayList<>();
        String paginationToken = null;
        boolean finished = false;
        while (!finished) {
            GetDocumentAnalysisRequest analysisRequest = new GetDocumentAnalysisRequest()
                    .withJobId(jobId)
                    .withNextToken(paginationToken);

            getDocumentAnalysisResult = textractClient.getDocumentAnalysis(analysisRequest);
            paginationToken = getDocumentAnalysisResult.getNextToken();
            totalBlocks.addAll(getDocumentAnalysisResult.getBlocks());

            if (paginationToken == null)
                finished = true;
        }
        getDocumentAnalysisResult.setBlocks(totalBlocks);
        return getDocumentAnalysisResult;
    }
    private String getJobResults(String jobId) {

        GetDocumentAnalysisResult response = textractClient.getDocumentAnalysis(new GetDocumentAnalysisRequest()
                .withJobId(jobId).withMaxResults(1000));
        return response.getJobStatus();
    }
}
