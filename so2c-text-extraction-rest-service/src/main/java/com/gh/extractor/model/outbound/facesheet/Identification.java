package com.gh.extractor.model.outbound.facesheet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.ExtractedOcrData;
import com.gh.extractor.model.FacesheetExtractedOcrData;
import com.gh.extractor.model.builder.OcrDataEntityBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * @author hsahu
 * @date 17/08/21,1:00 PM
 */
@Getter
public class Identification extends OcrDataEntityBuilder {

  private static final String MRN = "mrn";

  @JsonProperty("MedicalRecordNumber")
  private String medicalRecordNumber;

  @Builder
  public Identification(FacesheetExtractedOcrData facesheetExtractedOcrData) {
    super(facesheetExtractedOcrData);
    this.medicalRecordNumber = findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getMrn());

  }
}
