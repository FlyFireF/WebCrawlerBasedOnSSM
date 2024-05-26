package com.flyfiref.crawler.pojo;

import java.util.Objects;

/**
 * 视频
 */
public class Video {
    private String bvid;//视频的BV号，同时作为主键
    private String title;//标题
    private String uploader;//UP主
    private String rcmd_reason;//推荐原因
    private String description;//简介
    private Integer v_view;//播放量
    private Integer danmaku;//弹幕数
    private Integer reply;//评论数
    private Integer v_like;//点赞数
    private Integer coin;//投币数
    private Integer fav;//收藏数
    private Integer share;//分享数

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(bvid, video.bvid) && Objects.equals(title, video.title) && Objects.equals(uploader, video.uploader) && Objects.equals(rcmd_reason, video.rcmd_reason) && Objects.equals(description, video.description) && Objects.equals(v_view, video.v_view) && Objects.equals(danmaku, video.danmaku) && Objects.equals(reply, video.reply) && Objects.equals(v_like, video.v_like) && Objects.equals(coin, video.coin) && Objects.equals(fav, video.fav) && Objects.equals(share, video.share);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bvid, title, uploader, rcmd_reason, description, v_view, danmaku, reply, v_like, coin, fav, share);
    }

    @Override
    public String toString() {
        return "Video{" +
                "bvid='" + bvid + '\'' +
                ", title='" + title + '\'' +
                ", uploader='" + uploader + '\'' +
                ", rcmd_reason='" + rcmd_reason + '\'' +
                ", description='" + description + '\'' +
                ", v_view=" + v_view +
                ", danmaku=" + danmaku +
                ", reply=" + reply +
                ", v_like=" + v_like +
                ", coin=" + coin +
                ", fav=" + fav +
                ", share=" + share +
                '}';
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getRcmd_reason() {
        return rcmd_reason;
    }

    public void setRcmd_reason(String rcmd_reason) {
        this.rcmd_reason = rcmd_reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getV_view() {
        return v_view;
    }

    public void setV_view(Integer v_view) {
        this.v_view = v_view;
    }

    public Integer getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(Integer danmaku) {
        this.danmaku = danmaku;
    }

    public Integer getReply() {
        return reply;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }

    public Integer getV_like() {
        return v_like;
    }

    public void setV_like(Integer v_like) {
        this.v_like = v_like;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getFav() {
        return fav;
    }

    public void setFav(Integer fav) {
        this.fav = fav;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Video(String bvid, String title, String uploader, String rcmd_reason, String description, Integer v_view, Integer danmaku, Integer reply, Integer v_like, Integer coin, Integer fav, Integer share) {
        this.bvid = bvid;
        this.title = title;
        this.uploader = uploader;
        this.rcmd_reason = rcmd_reason;
        this.description = description;
        this.v_view = v_view;
        this.danmaku = danmaku;
        this.reply = reply;
        this.v_like = v_like;
        this.coin = coin;
        this.fav = fav;
        this.share = share;
    }

    public Video() {
    }
}
