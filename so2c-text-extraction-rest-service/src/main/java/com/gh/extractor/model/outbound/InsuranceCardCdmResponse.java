package com.gh.extractor.model.outbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.outbound.insurancecard.Insurance;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class InsuranceCardCdmResponse extends CDMResponse {
    @JsonProperty("Insurance")
    private Insurance insurance;
}
