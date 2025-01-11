package com.gh.extractor.model.outbound.facesheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.ExtractedOcrData;
import com.gh.extractor.model.FacesheetExtractedOcrData;
import com.gh.extractor.model.builder.OcrDataEntityBuilder;
import com.gh.extractor.service.AddressLookupService;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * @author hsahu
 * @date 17/08/21,1:00 PM
 */
@Getter
public class InsuranceCompany extends OcrDataEntityBuilder {

  private static final String INSURANCE_PROVIDER = "insurance_provider";

  @JsonIgnore
  private AddressLookupService addressLookupService;

  @JsonProperty("InsuranceCompanyName")
  private String insuranceCompanyName;

  @JsonProperty("InsuranceCompanyId")
  private String insuranceCompanyId;

  @JsonProperty("InsuranceCompanyAddress")
  private String insuranceCompanyAddress;

  @JsonProperty("InsuranceCompanyState")
  private String insuranceCompanyState;

  @JsonProperty("InsuranceCompanyCity")
  private String insuranceCompanyCity;

  @JsonProperty("InsuranceCompanyZip")
  private String insuranceCompanyZip;

  @JsonProperty("InsuranceCompanyCountry")
  private String insuranceCompanyCountry;

  @JsonProperty("InsuranceCompanyPhone")
  private String insuranceCompanyPhone;

  @Builder
  public InsuranceCompany(FacesheetExtractedOcrData facesheetExtractedOcrData, AddressLookupService addressLookupService) {
    super(facesheetExtractedOcrData);
    this.addressLookupService = addressLookupService;
    this.insuranceCompanyName = findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getInsurance_provider());
  }
}
