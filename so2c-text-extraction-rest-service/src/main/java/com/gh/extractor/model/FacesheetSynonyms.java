package com.gh.extractor.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FacesheetSynonyms {
    private Synonym mrn;
    private Synonym patient_dob;
    private Synonym patient_name;
    private Synonym patient_address;
    private Synonym patient_gender;
    private Synonym patient_phone;
    private Synonym insurance_provider;
    private Synonym member_name;
    private Synonym social_security;
    private Synonym policy_number;
    private Synonym group_number;
    private Synonym relation;
}
