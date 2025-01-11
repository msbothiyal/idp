package com.gh.extractor.model.outbound.facesheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.FacesheetExtractedOcrData;
import com.gh.extractor.model.builder.OcrDataEntityBuilder;
import com.gh.extractor.model.outbound.*;
import com.gh.extractor.service.AddressLookupService;
import lombok.Builder;
import lombok.Getter;

/*
 * @author hsahu
 * @date 17/08/21,1:00 PM
 */
@Getter
public class BillingInformation extends OcrDataEntityBuilder {

  @JsonIgnore
  private AddressLookupService addressLookupService;

  @JsonIgnore
  private Patient patient;

  @JsonProperty("InsuredPersonalInfo")
  private InsuredPersonalInfo insuredPersonalInfo;

  @JsonProperty("InsuredPolicyInfo")
  private InsuredPolicyInfo insuredPolicyInfo;

  @JsonProperty("InsuranceCompany")
  private InsuranceCompany insuranceCompany;

  @JsonProperty("InsuredAddress")
  private Address insuredAddress;

  @JsonProperty("BillingLocation")
  private BillingLocation billingLocation;

  @JsonProperty("SelfPay")
  private boolean selfPay = true;

  @Builder
  public BillingInformation(FacesheetExtractedOcrData facesheetExtractedOcrData, AddressLookupService addressLookupService, Patient patient) {
    super(facesheetExtractedOcrData);
    this.addressLookupService = addressLookupService;
    this.patient = patient;

    InsuredPersonalInfo insuredPersonalInfo = InsuredPersonalInfo.builder().facesheetExtractedOcrData(facesheetExtractedOcrData)
            .patient(this.patient)
            .build();
    this.insuredPersonalInfo = insuredPersonalInfo;

    InsuredPolicyInfo insuredPolicyInfo = InsuredPolicyInfo.builder().facesheetExtractedOcrData(facesheetExtractedOcrData).build();
    this.insuredPolicyInfo = insuredPolicyInfo;

    InsuranceCompany insuranceCompany = InsuranceCompany.builder()
            .facesheetExtractedOcrData(facesheetExtractedOcrData)
            .addressLookupService(addressLookupService)
            .build();
    this.insuranceCompany = insuranceCompany;
  }
}
