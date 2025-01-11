package com.gh.extractor.model.outbound.insurancecard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gh.extractor.model.InsuranceCardExtractedOcrData;
import com.gh.extractor.model.Name;
import com.gh.extractor.model.builder.OcrDataEntityBuilder;
import com.gh.extractor.utility.CommonUtil;
import lombok.*;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Insurance extends OcrDataEntityBuilder {

    @JsonProperty("Member ID")
    private String memberId;
    @JsonProperty("Member First Name")
    private String memberFirstName;
    @JsonProperty("Member Last Name")
    private String memberLastName;
    @JsonProperty("Insurance Provider")
    private String insuranceProvider;
    @JsonProperty("Plan")
    private String planNumber;
    @JsonProperty("Group Number")
    private String groupNumber;

    /* optional fields
    @JsonProperty("Company Name")
    private String companyName;
    @JsonProperty("Payor")
    private String payor;
    @JsonProperty("Payor ID")
    private String payorID;
    @JsonProperty("Relationship to Insured")
    private String relationshipToInsured;
    @JsonProperty("Provider Name")
    private String providerName;
    @JsonProperty("Provider Number")
    private String providerNumber;
    @JsonProperty("Member effective Date")
    private String memberEffectiveDate;

     */

    @Builder
    public Insurance(InsuranceCardExtractedOcrData insuranceCardExtractedOcrData) {
        super(insuranceCardExtractedOcrData);
        this.memberId = findValue(insuranceCardExtractedOcrData.getInsuranceCardSynonyms().getMember_id());
        String memberName = findValue(insuranceCardExtractedOcrData.getInsuranceCardSynonyms().getMember_name());
        Name parsedMemberName = CommonUtil.computeName(memberName);
        this.memberLastName = parsedMemberName.getLastName();
        this.memberFirstName = parsedMemberName.getFirstName();
        this.insuranceProvider = findValue(insuranceCardExtractedOcrData.getInsuranceCardSynonyms().getInsurance_provider());
        this.planNumber = findValue(insuranceCardExtractedOcrData.getInsuranceCardSynonyms().getPlan_number());
        this.groupNumber = findValue(insuranceCardExtractedOcrData.getInsuranceCardSynonyms().getGroup_number());
    }
}
