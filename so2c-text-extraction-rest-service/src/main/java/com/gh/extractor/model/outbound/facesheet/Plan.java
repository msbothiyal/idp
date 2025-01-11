package com.gh.extractor.model.outbound.facesheet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author hsahu
 * @date 17/08/21,1:00 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
  @JsonProperty("PlanId")
  private String planId;

  @JsonProperty("PlanIdType")
  private String planIdType;

  @JsonProperty("PlanType")
  private String planType;

  @JsonProperty("PlanName")
  private String planName;
}
