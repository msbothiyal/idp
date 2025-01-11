package com.gh.extractor.model.outbound.facesheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.outbound.OrderInformation;
import com.gh.extractor.model.outbound.PatientAuthorization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/*
 * @author hsahu
 * @date 17/08/21,1:00 PM
 */
@Data
@Builder
@AllArgsConstructor
public class Order {
  @JsonProperty("Patient")
  private Patient patient;

  @JsonProperty("OrderInformation")
  private OrderInformation orderInformation;

  @JsonProperty("BillingInformation")
  private BillingInformation billingInformation;

  @JsonProperty("PatientAuthorization")
  private PatientAuthorization patientAuthorization;

  @JsonIgnore
  public boolean isPatientInfoAbsent() {
    return patient.isPatientInfoAbsent();
  }
}
