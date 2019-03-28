package com.xxm.demohtmlanalysis;

/**
 * Created by Administrator on 2019/3/28 0016
 */
public class BeanInformation {
    private String thum_url;
    private String name;
    private String content;
    private String url;

    public String getThum_url() {
        return thum_url;
    }

    public void setThum_url(String thum_url) {
        this.thum_url = thum_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BeanInformation{" +
                "thum_url='" + thum_url + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
