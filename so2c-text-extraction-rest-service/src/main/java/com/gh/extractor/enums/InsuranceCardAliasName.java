package com.gh.extractor.enums;

import static com.gh.extractor.constants.InsuranceCommonConstants.*;

public enum InsuranceCardAliasName implements IAliasName {
    MEMBER_ID(MEMBER_IDS),
    MEMBER_NAME(MEMBER_NAMES),
    INSURANCE_PROVIDER(INSURANCE_PROVIDERS),
    PLAN_NUMBER(PLAN_NUMBERS),
    GROUP_NUMBER(GROUP_NUMBERS),

    //Optional fields
    RELATIONSHIP_TO_INSURED(RELATIONSHIP_TO_INSUREDS),
    PROVIDER_NAME(PROVIDER_NAMES),
    PROVIDER_NUMBER(PROVIDER_NUMBERS),
    MEMBER_EFFECTIVE_DATE(MEMBER_EFFECTIVE_DATES);
    //COMPANY_NAME(""),
    //PAYOR(""),
    //PAYOR_ID("");


    private final String query;

    InsuranceCardAliasName(String query) {
        this.query = "What is " + query.strip() + "?";
    }

    @Override
    public String getQuery() {
        return this.query;
    }

    @Override
    public String getName() {
        return this.name();
    }
}