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
public class Patient extends OcrDataEntityBuilder {

  @JsonIgnore
  private AddressLookupService addressLookupService;

  @JsonProperty("Identification")
  private Identification identification;

  @JsonProperty("Demographics")
  private Demographics demographics;

  @JsonIgnore
  public boolean isPatientInfoAbsent() {
    return Objects.isNull(demographics) || demographics.isMandatoryFieldAbsent();
  }

  @Builder
  public Patient(FacesheetExtractedOcrData facesheetExtractedOcrData, AddressLookupService addressLookupService) {
    super(facesheetExtractedOcrData);
    this.addressLookupService = addressLookupService;

    Identification identification = Identification.builder().facesheetExtractedOcrData(facesheetExtractedOcrData).build();

    Demographics demographics = Demographics.builder().facesheetExtractedOcrData(facesheetExtractedOcrData)
            .addressLookupService(addressLookupService).build();

    this.identification = identification;
    this.demographics = demographics;
  }
}
