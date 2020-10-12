package com.example.test1;

import java.util.List;

public class Line{
    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getMinTop() {
        return MinTop;
    }

    public void setMinTop(int minTop) {
        MinTop = minTop;
    }

    private List<Word> words;
    private int maxHeight;
    private int MinTop;
}
