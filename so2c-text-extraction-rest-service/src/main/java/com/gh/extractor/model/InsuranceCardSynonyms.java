package com.gh.extractor.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InsuranceCardSynonyms {
    private Synonym member_id;
    private Synonym member_name;
    private Synonym insurance_provider;
    private Synonym plan_number;
    private Synonym group_number;
    private Synonym provider_name;
    private Synonym effective_date;
}
