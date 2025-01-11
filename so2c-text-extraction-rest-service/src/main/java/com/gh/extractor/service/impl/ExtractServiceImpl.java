package com.gh.extractor.service.impl;

import com.gh.extractor.enums.DocType;
import com.gh.extractor.exception.TESRestException;
import com.gh.extractor.model.FacesheetSynonyms;
import com.gh.extractor.model.GetDocumentExtractRequest;
import com.gh.extractor.model.InsuranceCardSynonyms;
import com.gh.extractor.model.outbound.CDMResponse;
import com.gh.extractor.service.AddressLookupService;
import com.gh.extractor.service.ExtractService;
import com.gh.extractor.service.TextractAPIService;
import com.gh.extractor.strategy.FaceSheetOcrStrategy;
import com.gh.extractor.strategy.InsuranceCardOcrStrategy;
import com.gh.extractor.strategy.OcrStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractServiceImpl implements ExtractService {

    private final AddressLookupService addressLookupService;
    private final TextractAPIService textractAPIService;
    private final FacesheetSynonyms patientFacesheetFieldSynonymList;
    private final InsuranceCardSynonyms insuranceCardFieldSynonymList;

    @Override
    public CDMResponse generateCDMResponse(GetDocumentExtractRequest getDocumentExtractRequest) {

        OcrStrategy ocrStrategy = resolveOcrStrategy(getDocumentExtractRequest.getDocumentType());

        if (ocrStrategy == null) {
            throw new TESRestException("No ocr strategy for given document type");
        }
        return ocrStrategy.getCDMResponse(getDocumentExtractRequest);
    }

    private OcrStrategy resolveOcrStrategy(String documentType) {

        DocType docType = DocType.valueOfLabel(documentType);

        if (docType != null) {
            switch (docType) {
                case PATIENT_FACE_SHEET:
                    return new FaceSheetOcrStrategy(this.addressLookupService,
                            this.textractAPIService, this.patientFacesheetFieldSynonymList);
                case INSURANCE_CARD:
                    return new InsuranceCardOcrStrategy(this.insuranceCardFieldSynonymList, this.textractAPIService);
            }
        }
        return null;
    }

}
