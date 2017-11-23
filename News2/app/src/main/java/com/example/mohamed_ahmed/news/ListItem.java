package com.example.mohamed_ahmed.news;

public class ListItem {
    private String Type, SectionName, WebPublicationDate, WebUrl, WebTitle;

    public ListItem(String type, String sectionName, String webPublicationDate, String webUrl, String webTitle) {
        Type = type;
        SectionName = sectionName;
        WebPublicationDate = webPublicationDate;
        WebUrl = webUrl;
        WebTitle = webTitle;
    }

    public String getType() {
        return Type;
    }

    public String getSectionName() {
        return SectionName;
    }

    public String getWebPublicationDate() {
        return WebPublicationDate;
    }

    public String getWebUrl() {
        return WebUrl;
    }

    public String getWebTitle() {
        return WebTitle;
    }
}
