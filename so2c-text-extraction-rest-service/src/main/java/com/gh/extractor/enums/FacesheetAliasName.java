package com.gh.extractor.enums;

import static com.gh.extractor.constants.FacesheetCommonConstants.*;
import static com.gh.extractor.constants.InsuranceCommonConstants.GROUP_NUMBERS;

public enum FacesheetAliasName implements IAliasName {

    PATIENT_NAME(PATIENT_NAME_KEY),
    MRN(MRN_KEY),
    PATIENT_ADDRESS(PATIENT_ADDRESS_KEY),
    PATIENT_GENDER(PATIENT_GENDER_KEY),
    PATIENT_DOB(PATIENT_DOB_KEY),
    PATIENT_INSURANCE_PROVIDER(PATIENT_INSURANCE_PROVIDER_KEY),
    PLAN_NUMBER(PATIENT_PLAN_KEY),
    GROUP_NUMBER(GROUP_NUMBERS);

    private final String query;
    FacesheetAliasName(String query) {
        this.query = "What is "+query.strip()+"?";
    }

    @Override
    public String getQuery() {
        return this.query;
    }

    @Override
    public String getName() { return this.name(); }
}
