package com.xxm.demohtmlanalysis.bean;

/**
 * Created by xxm on 2019/4/2 0002
 */
public class BeanFixture {
    //图片url
    private String bgUrl;
    //赛程状态url
    private String stateUrl;
    //游戏ICON
    private String gameIconUrl;
    //星的数量
    private int starNum;
    //标题
    private String title;
    //跳转URL
    private String jumpUrl;

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }

    public String getStateUrl() {
        return stateUrl;
    }

    public void setStateUrl(String stateUrl) {
        this.stateUrl = stateUrl;
    }

    public String getGameIconUrl() {
        return gameIconUrl;
    }

    public void setGameIconUrl(String gameIconUrl) {
        this.gameIconUrl = gameIconUrl;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    @Override
    public String toString() {
        return "BeanFixture{" +
                "bgUrl='" + bgUrl + '\'' +
                ", stateUrl='" + stateUrl + '\'' +
                ", gameIconUrl='" + gameIconUrl + '\'' +
                ", starNum=" + starNum +
                ", title='" + title + '\'' +
                ", jumpUrl='" + jumpUrl + '\'' +
                '}';
    }
}
