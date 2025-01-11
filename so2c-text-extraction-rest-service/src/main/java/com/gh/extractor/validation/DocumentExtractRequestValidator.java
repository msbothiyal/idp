package com.gh.extractor.validation;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;
import com.gh.extractor.enums.DocType;
import com.gh.extractor.model.GetDocumentExtractRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;

public class DocumentExtractRequestValidator implements Validator {

    private final static String ERROR_KEY = "ERROR";

    @Autowired
    private AmazonS3 amazonS3Client;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(GetDocumentExtractRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GetDocumentExtractRequest getDocumentExtractRequest = (GetDocumentExtractRequest) target;

        if (!Arrays.stream(DocType.values()).anyMatch(dt -> dt.docName.equals(getDocumentExtractRequest.getDocumentType()))) {
            errors.reject(ERROR_KEY,
                    String.format("Document type must be one of the following: %1s", DocType.docTypeValuesAsStrList()));
        }

        AmazonS3URI s3URI = new AmazonS3URI(getDocumentExtractRequest.getS3BucketFileLocation());
        if (!amazonS3Client.doesObjectExist(s3URI.getBucket(), s3URI.getKey())) {
            errors.reject(ERROR_KEY,
                    String.format("Specified file does not exist: %1s", getDocumentExtractRequest.getS3BucketFileLocation()));
        }
    }
}
