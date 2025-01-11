package com.gh.extractor.model.outbound.facesheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.FacesheetExtractedOcrData;
import com.gh.extractor.model.builder.OcrDataEntityBuilder;
import com.gh.extractor.service.AddressLookupService;
import lombok.*;

import java.util.Objects;

/*
 * @author hsahu
 * @date 17/08/21,1:00 PM
 */
@Getter
public class Demographics extends OcrDataEntityBuilder {

  @JsonIgnore
  private AddressLookupService addressLookupService;

  @JsonProperty("PersonalInfo")
  private PersonalInfo personalInfo;

  @JsonProperty("Address")
  private Address address;

  @JsonProperty("ContactData")
  private ContactData contactData;

  @JsonIgnore
  public boolean isMandatoryFieldAbsent() {
    return (Objects.isNull(contactData) || contactData.isMandatoryFieldEmpty())
            && (Objects.isNull(personalInfo) || personalInfo.isMandatoryFieldEmpty()
            && (Objects.isNull(address) || address.isMandatoryFieldEmpty()));
  }

  @Builder
  public Demographics(FacesheetExtractedOcrData facesheetExtractedOcrData, AddressLookupService addressLookupService) {
    super(facesheetExtractedOcrData);
    this.addressLookupService = addressLookupService;

    PersonalInfo personalInfo = PersonalInfo.builder().facesheetExtractedOcrData(facesheetExtractedOcrData).build();
    Address address = Address.builder().facesheetExtractedOcrData(facesheetExtractedOcrData).addressLookupService(addressLookupService)
            .build();
    ContactData contactData = ContactData.builder().facesheetExtractedOcrData(facesheetExtractedOcrData).build();

    this.personalInfo = personalInfo;
    this.address = address;
    this.contactData = contactData;
  }
}
