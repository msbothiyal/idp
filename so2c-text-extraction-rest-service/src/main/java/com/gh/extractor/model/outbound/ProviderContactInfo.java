package com.gh.extractor.model.outbound;

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
public class ProviderContactInfo {
  @JsonProperty("ProviderEmail")
  private String providerEmail;

  @JsonProperty("ProviderFax")
  private String providerFax;

  @JsonProperty("ProviderPhoneNumber")
  private String providerPhoneNumber;
}
