package com.example.voghdev_pdfviewpager;

public class PdfListModel {
    String name;
    String url;

    public PdfListModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public PdfListModel(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
