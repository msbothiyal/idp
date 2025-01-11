package com.gh.extractor.strategy;

import com.amazonaws.services.textract.model.Query;
import com.gh.extractor.enums.InsuranceCardAliasName;
import com.gh.extractor.model.ExtractedOcrData;
import com.gh.extractor.model.GetDocumentExtractRequest;
import com.gh.extractor.model.InsuranceCardExtractedOcrData;
import com.gh.extractor.model.InsuranceCardSynonyms;
import com.gh.extractor.model.outbound.CDMResponse;
import com.gh.extractor.model.outbound.insurancecard.Insurance;
import com.gh.extractor.model.outbound.InsuranceCardCdmResponse;
import com.gh.extractor.service.TextractAPIService;
import com.gh.extractor.utility.CommonUtil;

import java.util.List;

public class InsuranceCardOcrStrategy extends OcrStrategy {

    private InsuranceCardSynonyms insuranceCardFieldSynonymList;

    public InsuranceCardOcrStrategy(InsuranceCardSynonyms insuranceCardFieldSynonymList,
                                    TextractAPIService textractAPIService) {
        super(textractAPIService);
        this.insuranceCardFieldSynonymList = insuranceCardFieldSynonymList;
    }

    @Override
    public CDMResponse getCDMResponse(GetDocumentExtractRequest getDocumentExtractRequest) {
        var ocrData = getDocumentAnalysisResults(getDocumentExtractRequest, getTextractQueries());
        InsuranceCardExtractedOcrData insuranceCardExtractedOcrData = InsuranceCardExtractedOcrData.builder()
                .extractedDataKeyValueMap(ocrData).insuranceCardSynonyms(insuranceCardFieldSynonymList).build();

        Insurance insurance = Insurance.builder().insuranceCardExtractedOcrData(insuranceCardExtractedOcrData).build();

        return InsuranceCardCdmResponse.builder().insurance(insurance).build();
    }

    @Override
    protected Boolean isValidDocument(ExtractedOcrData extractedData) {
        return true;
    }

    @Override
    protected List<Query> getTextractQueries() {
        return CommonUtil.getTextractQueries(InsuranceCardAliasName.values());
    }
}
