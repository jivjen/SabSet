package com.example.test1;

public class Word{
    public String getWordText() {
        return WordText;
    }

    public void setWordText(String wordText) {
        WordText = wordText;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    String WordText;
    int left;
    int top;
    int height;
    int width;
}
