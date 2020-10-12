package com.example.test1;

import java.util.List;

public class ParsedResult{
    public List<TextOverlay> getTextOverlays() {
        return textOverlays;
    }

    public void setTextOverlays(List<TextOverlay> textOverlays) {
        this.textOverlays = textOverlays;
    }

    public String getFileParsedExitCode() {
        return FileParsedExitCode;
    }

    public void setFileParsedExitCode(String fileParsedExitCode) {
        FileParsedExitCode = fileParsedExitCode;
    }

    public String getParsedText() {
        return ParsedText;
    }

    public void setParsedText(String parsedText) {
        ParsedText = parsedText;
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

    private List<TextOverlay> textOverlays;
    private String FileParsedExitCode;
    private String ParsedText;
    private String ErrorMessage;
    private String ErrorDetails;


}
