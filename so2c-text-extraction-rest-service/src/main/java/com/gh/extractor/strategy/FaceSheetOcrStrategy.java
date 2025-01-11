package com.gh.extractor.strategy;

import com.amazonaws.services.textract.model.Query;
import com.gh.extractor.enums.FacesheetAliasName;
import com.gh.extractor.model.*;
import com.gh.extractor.model.outbound.*;
import com.gh.extractor.model.outbound.facesheet.BillingInformation;
import com.gh.extractor.model.outbound.facesheet.Order;
import com.gh.extractor.model.outbound.facesheet.Patient;
import com.gh.extractor.service.AddressLookupService;
import com.gh.extractor.service.TextractAPIService;
import com.gh.extractor.utility.CommonUtil;

import java.util.List;

public class FaceSheetOcrStrategy extends OcrStrategy {

    private final AddressLookupService addressLookupService;
    private final FacesheetSynonyms patientFacesheetFieldSynonymList;

    public FaceSheetOcrStrategy(AddressLookupService addressLookupService, TextractAPIService textractAPIService,
                                FacesheetSynonyms patientFacesheetFieldSynonymList) {
        super(textractAPIService);
        this.addressLookupService = addressLookupService;
        this.patientFacesheetFieldSynonymList = patientFacesheetFieldSynonymList;
    }

    @Override
    public CDMResponse getCDMResponse(GetDocumentExtractRequest getDocumentExtractRequest) {
        var ocrData = getDocumentAnalysisResults(getDocumentExtractRequest, getTextractQueries());
        FacesheetExtractedOcrData facesheetExtractedOcrData = FacesheetExtractedOcrData
                .builder()
                .extractedDataKeyValueMap(ocrData)
                .facesheetSynonyms(this.patientFacesheetFieldSynonymList)
                .build();

        Patient patient = Patient.builder()
                .facesheetExtractedOcrData(facesheetExtractedOcrData)
                .addressLookupService(addressLookupService)
                .build();

        BillingInformation billingInformation = BillingInformation.builder()
                .facesheetExtractedOcrData(facesheetExtractedOcrData).addressLookupService(addressLookupService)
                .patient(patient)
                .build();

        Order order = Order.builder().patient(patient).billingInformation(billingInformation).build();
        return FacesheetCdmResponse.builder().order(order).build();
    }

    @Override
    protected Boolean isValidDocument(ExtractedOcrData extractedData) {
        return true;
    }

    @Override
    protected List<Query> getTextractQueries() {
        return CommonUtil.getTextractQueries(FacesheetAliasName.values());
    }
}
