package com.gh.extractor.model.outbound.facesheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.FacesheetExtractedOcrData;
import com.gh.extractor.model.Name;
import com.gh.extractor.model.builder.OcrDataEntityBuilder;
import com.gh.extractor.utility.CommonUtil;
import com.guardant.so2c.ocr.textextractor.utility.CommonUtility;
import lombok.Builder;
import lombok.Getter;

/*
 * @author hsahu
 * @date 17/08/21,1:00 PM
 */
@Getter
public class PersonalInfo extends OcrDataEntityBuilder {

  private static final String DOB_MAP_KEY = "patient_dob";
  private static final String GENDER_MAP_KEY = "patient_gender";
  private static final String NAME_MAP_KEY = "patient_name";

  @JsonProperty("LastName")
  private String lastName;

  @JsonProperty("FirstName")
  private String firstName;

  @JsonProperty("MiddleName")
  private String middleName;

  @JsonProperty("DOB")
  private String dob;

  @JsonProperty("Sex")
  private String sex;

  @JsonProperty("SSN")
  private String ssn;

  @JsonProperty("Race")
  private String race;

  @JsonProperty("LanguagePref")
  private String languagePref;

  @JsonIgnore
  public boolean isMandatoryFieldEmpty () {
    return CommonUtility.isNullOrEmpty(lastName) && CommonUtility.isNullOrEmpty(firstName) &&
            CommonUtility.isNullOrEmpty(dob);
  }

  @Builder
  public PersonalInfo(FacesheetExtractedOcrData facesheetExtractedOcrData) {
    super(facesheetExtractedOcrData);

    Name name = CommonUtil.computeName(findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getPatient_name()));
    this.lastName = name.getLastName();
    this.firstName = name.getFirstName();
    this.sex = findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getPatient_gender());
    this.dob = findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getPatient_dob());
  }
}
