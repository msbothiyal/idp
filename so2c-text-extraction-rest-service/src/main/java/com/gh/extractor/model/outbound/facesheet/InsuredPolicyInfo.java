package com.gh.extractor.model.outbound.facesheet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.FacesheetExtractedOcrData;
import com.gh.extractor.model.builder.OcrDataEntityBuilder;
import lombok.Builder;
import lombok.Getter;

/*
 * @author hsahu
 * @date 17/08/21,1:00 PM
 */
@Getter
public class InsuredPolicyInfo extends OcrDataEntityBuilder {

  private static final String POLICY_NUMBER_MAP_KEY = "policy_number";
  private static final String GROUP_NUMBER_MAP_KEY = "group_number";
  private static final String INSURANCE_PLAN_MAP_KEY = "insurance_provider";

  @JsonProperty("Plan")
  private Plan plan;

  @JsonProperty("PolicyNumber")
  private String policyNumber;

  @JsonProperty("GroupNumber")
  private String groupNumber;

  @JsonProperty("GroupName")
  private String groupName;

  @JsonProperty("EffectiveDate")
  private String effectiveDate;

  @JsonProperty("ExpirationDate")
  private String expirationDate;

  @JsonProperty("PriorAuthCode")
  private String priorAuthCode;

  @Builder
  public InsuredPolicyInfo(FacesheetExtractedOcrData facesheetExtractedOcrData) {
    super(facesheetExtractedOcrData);

    this.policyNumber = findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getPolicy_number());
    this.groupNumber = findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getGroup_number());

    Plan plan = new Plan();
    plan.setPlanName(findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getInsurance_provider()));
    this.plan = plan;
  }
}
