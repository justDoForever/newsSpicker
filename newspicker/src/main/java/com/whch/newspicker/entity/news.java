package com.whch.newspicker.entity;

public class news {
    private String url;

    private Integer clickrate;

    private String keywords;

    private String title;

    private String pubdate;

    private String pubsource;

    private String getdate;

    private String text;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getClickrate() {
        return clickrate;
    }

    public void setClickrate(Integer clickrate) {
        this.clickrate = clickrate;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate == null ? null : pubdate.trim();
    }

    public String getPubsource() {
        return pubsource;
    }

    public void setPubsource(String pubsource) {
        this.pubsource = pubsource == null ? null : pubsource.trim();
    }

    public String getGetdate() {
        return getdate;
    }

    public void setGetdate(String getdate) {
        this.getdate = getdate == null ? null : getdate.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }
}