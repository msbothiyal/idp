package com.gh.extractor.model.outbound;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.outbound.facesheet.Order;
import lombok.Builder;

@Builder
public class FacesheetCdmResponse extends CDMResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Order")
    private Order order;
    @JsonIgnore
    public boolean isPatientInfoAbsent() {
        return order == null || order.isPatientInfoAbsent();
    }
}
