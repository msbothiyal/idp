package com.gh.extractor.model.outbound.facesheet;

import com.amazonaws.services.location.model.Place;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.ExtractedOcrData;
import com.gh.extractor.model.FacesheetExtractedOcrData;
import com.gh.extractor.model.builder.OcrDataEntityBuilder;
import com.gh.extractor.service.AddressLookupService;
import com.guardant.so2c.ocr.textextractor.utility.CommonUtility;
import lombok.*;

/*
 * @author hsahu
 * @date 17/08/21,1:00 PM
 */
@Getter
public class Address extends OcrDataEntityBuilder {

  private static final String ADDRESS_MAP_KEY = "patient_address";

  @JsonIgnore
  private AddressLookupService addressLookupService;

  @JsonProperty("StreetAddress")
  private String streetAddress;

  @JsonProperty("City")
  private String city;

  @JsonProperty("State")
  private String state;

  @JsonProperty("County")
  private String county;

  @JsonProperty("Country")
  private String country;

  @JsonProperty("Zip")
  private String zip;

  @JsonIgnore
  public boolean isMandatoryFieldEmpty() {
    return CommonUtility.isNullOrEmpty(streetAddress) &&
            CommonUtility.isNullOrEmpty(city) &&
            CommonUtility.isNullOrEmpty(zip);
  }
  @Builder
  public Address(FacesheetExtractedOcrData facesheetExtractedOcrData, AddressLookupService addressLookupService) {
    super(facesheetExtractedOcrData);
    this.addressLookupService = addressLookupService;

    String fullAddress = findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getPatient_address());

    if (fullAddress != null) {
      Place place = addressLookupService.computeAddress(fullAddress);

      if (place != null) {
        this.streetAddress = place.getAddressNumber() + " " + place.getStreet();
        this.city = place.getMunicipality();
        this.state = place.getRegion();
        this.county = place.getSubRegion();
        this.country = place.getCountry();
        this.zip = place.getPostalCode();
      }
    }
  }
}
