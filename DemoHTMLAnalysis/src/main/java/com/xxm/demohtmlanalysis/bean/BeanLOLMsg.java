package com.xxm.demohtmlanalysis.bean;

/**
 * Created by xxm on 2019/4/2 0002
 */
public class BeanLOLMsg {

    //图片url
    private String imgUrl;
    //标题
    private String title;
    //内容
    private String content;
    //作者
    private String author;
    //时间
    private String timeStr;
    //游戏名字
    private String gameName;
    //跳转URL
    private String jumpUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    @Override
    public String toString() {
        return "BeanLOLMsg{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", timeStr='" + timeStr + '\'' +
                ", gameName='" + gameName + '\'' +
                ", jumpUrl='" + jumpUrl + '\'' +
                '}';
    }
}
