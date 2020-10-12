package com.example.test1;

import java.util.List;

public class OCRResponse {
    private List<ParsedResult> parsedResults;
    private String OCRExitCode;
    private String IsErroredOnProcessing;
    private String ErrorMessage;
    private String ErrorDetails;

    public List<ParsedResult> getParsedResults() {
        return parsedResults;
    }

    public void setParsedResults(List<ParsedResult> parsedResults) {
        this.parsedResults = parsedResults;
    }

    public String getOCRExitCode() {
        return OCRExitCode;
    }

    public void setOCRExitCode(String OCRExitCode) {
        this.OCRExitCode = OCRExitCode;
    }

    public String getIsErroredOnProcessing() {
        return IsErroredOnProcessing;
    }

    public void setIsErroredOnProcessing(String isErroredOnProcessing) {
        IsErroredOnProcessing = isErroredOnProcessing;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getErrorDetails() {
        return ErrorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        ErrorDetails = errorDetails;
    }

    public String getSearchablePDFURL() {
        return SearchablePDFURL;
    }

    public void setSearchablePDFURL(String searchablePDFURL) {
        SearchablePDFURL = searchablePDFURL;
    }

    public String getProcessingTimeInMilliseconds() {
        return ProcessingTimeInMilliseconds;
    }

    public void setProcessingTimeInMilliseconds(String processingTimeInMilliseconds) {
        ProcessingTimeInMilliseconds = processingTimeInMilliseconds;
    }

    private String SearchablePDFURL;
    private String ProcessingTimeInMilliseconds;

}


