package com.example.test1;

import java.util.List;

public class TextOverlay{
    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public String getHasOverlay() {
        return HasOverlay;
    }

    public void setHasOverlay(String hasOverlay) {
        HasOverlay = hasOverlay;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    private List<Line> lines;
    private String HasOverlay;
    private String Message;
}
