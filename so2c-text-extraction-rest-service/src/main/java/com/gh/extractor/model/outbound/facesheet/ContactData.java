package com.gh.extractor.model.outbound.facesheet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.ExtractedOcrData;
import com.gh.extractor.model.FacesheetExtractedOcrData;
import com.gh.extractor.model.builder.OcrDataEntityBuilder;
import com.guardant.so2c.ocr.textextractor.model.outbound.Phone;
import com.guardant.so2c.ocr.textextractor.utility.CommonUtility;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/*
 * @author hsahu
 * @date 17/08/21,1:00 PM
 */
@Getter
public class ContactData extends OcrDataEntityBuilder {

    private static final String PHONE_MAP_KEY = "patient_phone";

    @JsonProperty("Phone")
    private List<Phone> phone;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("PatientContactConsent")
    private boolean patientContactConsent;

    @JsonIgnore
    public boolean isMandatoryFieldEmpty() {
        return CommonUtility.isNullOrEmpty(email);
    }

    @Builder
    public ContactData(FacesheetExtractedOcrData facesheetExtractedOcrData) {
        super(facesheetExtractedOcrData);
        this.phone = new ArrayList<>();
        Phone phone = Phone
                .builder()
                .phoneNumber(findValue(facesheetExtractedOcrData.getFacesheetSynonyms().getPatient_phone()))
                .phoneType("HOME")
                .build();
        this.phone.add(phone);
    }
}
