package com.example.test1;

public class OCRRequest {

    private String apiKey;
    private String file;
    private String isTable;
    private String fileType;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getIsTable() {
        return isTable;
    }

    public void setIsTable(String isTable) {
        this.isTable = isTable;
    }
}
