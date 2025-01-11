package com.gh.extractor.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetDocumentExtractRequest {

    private String documentType;
    private String s3BucketFileLocation;
}
