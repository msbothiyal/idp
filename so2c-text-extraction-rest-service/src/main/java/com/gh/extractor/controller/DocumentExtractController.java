package com.gh.extractor.controller;

import com.amazonaws.services.textract.model.GetDocumentAnalysisResult;
import com.gh.extractor.model.GetDocumentExtractRequest;
import com.gh.extractor.model.StartExtractResponse;
import com.gh.extractor.model.outbound.CDMResponse;
import com.gh.extractor.service.ExtractService;
import com.gh.extractor.service.TextractAPIService;
import com.gh.extractor.validation.DocumentExtractRequestValidator;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@Api(value = "/tes/v1/", produces = "application/json")
@RestController
@RequestMapping("/tes/v1/")
@RequiredArgsConstructor
public class DocumentExtractController {

    private final TextractAPIService textractAPIService;
    private final DocumentExtractRequestValidator documentExtractRequestValidator;
    private final ExtractService extractService;

    @InitBinder("getDocumentExtractRequest")
    public void initPaymentSecretsBinder(WebDataBinder binder) {
        binder.addValidators(documentExtractRequestValidator);
    }

    @ApiOperation(value = "Get Document Extract Result", response = CDMResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK:  Get Document Analysis Result Successful", response = CDMResponse.class),
    })
    @PostMapping("extract")
    public Mono<CDMResponse> extract(@RequestBody @Valid GetDocumentExtractRequest getDocumentExtractRequest) {
        CDMResponse cdmResponse = extractService.generateCDMResponse(getDocumentExtractRequest);

        return Mono.just(cdmResponse);
    }

    @ApiOperation(value = "Start Document Extract", response = StartExtractResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK: Document Extract Successfully Started", response = StartExtractResponse.class),
    })
    @PostMapping("start-extract")
    public Mono<StartExtractResponse> startExtract(@RequestBody @Valid GetDocumentExtractRequest getDocumentExtractRequest) {
        return null;
    }

    @ApiOperation(value = "Get Document Extract Results", response = GetDocumentAnalysisResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK: Document Extract Successfully Retrieved", response = GetDocumentAnalysisResult.class),
    })
    @GetMapping("extract/{extractId}")
    public Mono<GetDocumentAnalysisResult> getExtract(@ApiParam(value = "extract-id", required = true, type = "String")
            @PathVariable String extractId) {

        return Mono.just(textractAPIService.getDocumentAnalysis(extractId));
    }
}
