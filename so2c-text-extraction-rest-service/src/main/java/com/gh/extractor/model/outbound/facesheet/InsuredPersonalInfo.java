package com.gh.extractor.model.outbound.facesheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.FacesheetExtractedOcrData;
import com.gh.extractor.model.Name;
import com.gh.extractor.model.builder.OcrDataEntityBuilder;
import com.gh.extractor.utility.CommonUtil;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.List;

/*
 * @author hsahu
 * @date 17/08/21,1:00 PM
 */
@Getter
public class InsuredPersonalInfo extends OcrDataEntityBuilder {

  private static final String MEMBER_NAME_MAP_KEY = "member_name";
  private static final String SSN_MAP_KEY = "social_security";
  private static final String RELATION_MAP_KEY = "relation";
  private static final List<String> SELF_PAY_KEYS = List.of("SELF", "HOLDER");

  @JsonIgnore
  private Patient patient;

  @JsonProperty("FirstName")
  private String firstName;

  @JsonProperty("LastName")
  private String lastName;

  @JsonProperty("MiddleName")
  private String middleName;

  @JsonProperty("SSN")
  private String sSN;

  @JsonProperty("DateOfBirth")
  private String dateOfBirth;

  @JsonProperty("Gender")
  private String gender;

  @JsonProperty("PatientRelation")
  private String patientRelation;

  @Builder
  public InsuredPersonalInfo(FacesheetExtractedOcrData facesheetExtractedOcrData, Patient patient) {
    super(facesheetExtractedOcrData);
    this.patient = patient;
    String memberName = findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getMember_name());
    Name insuredName = CommonUtil.computeName(memberName);
    this.lastName = insuredName.getLastName();
    this.firstName = insuredName.getFirstName();
    this.sSN = findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getSocial_security());
    this.patientRelation = findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getRelation());

    if (isSelfPay(this.patientRelation)) {
      this.lastName = this.patient.getDemographics().getPersonalInfo().getLastName();
      this.firstName = this.patient.getDemographics().getPersonalInfo().getFirstName();
      this.sSN = this.patient.getDemographics().getPersonalInfo().getSsn();
      this.dateOfBirth = this.patient.getDemographics().getPersonalInfo().getDob();
      this.gender = this.patient.getDemographics().getPersonalInfo().getSex();
    }
  }
  private boolean isSelfPay(String patientRelation) {
    return StringUtils.hasLength(patientRelation) && SELF_PAY_KEYS.contains(patientRelation.toUpperCase());
  }
}
