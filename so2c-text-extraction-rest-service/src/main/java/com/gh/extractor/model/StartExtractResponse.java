package com.gh.extractor.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StartExtractResponse {
    private String jobId;
}
