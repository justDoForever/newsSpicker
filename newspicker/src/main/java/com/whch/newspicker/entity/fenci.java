package com.whch.newspicker.entity;
/**
 * 依次是文本的分词 关键字 摘要 人名 词云图
 */
public class fenci {
    private String fenzi;
    private String keywords;
    private String extracts;
    private String name;
    private String imgUrl;

    public String getFenzi() {
        return fenzi;
    }

    public void setFenzi(String fenzi) {
        this.fenzi = fenzi;
    }


    public String getExtracts() {
        return extracts;
    }

    public void setExtracts(String extracts) {
        this.extracts = extracts;
    }


    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
