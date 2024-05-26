package com.flyfiref.crawler.pojo;

import java.util.Objects;

/**
 * 视频评论
 */
public class Reply {
    private Long rid;//数据库主键
    private String bvid;//BV号
    private Integer r_like;//点赞数
    private String content;//评论内容
    private String uname;//用户名
    private Long ctime;//评论时间戳

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return Objects.equals(rid, reply.rid) && Objects.equals(bvid, reply.bvid) && Objects.equals(r_like, reply.r_like) && Objects.equals(content, reply.content) && Objects.equals(uname, reply.uname) && Objects.equals(ctime, reply.ctime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rid, bvid, r_like, content, uname, ctime);
    }

    @Override
    public String toString() {
        return "Reply{" +
                "rid=" + rid +
                ", bvid='" + bvid + '\'' +
                ", r_like=" + r_like +
                ", content='" + content + '\'' +
                ", uname='" + uname + '\'' +
                ", ctime=" + ctime +
                '}';
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public Integer getR_like() {
        return r_like;
    }

    public void setR_like(Integer r_like) {
        this.r_like = r_like;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Reply(Long rid, String bvid, Integer r_like, String content, String uname, Long ctime) {
        this.rid = rid;
        this.bvid = bvid;
        this.r_like = r_like;
        this.content = content;
        this.uname = uname;
        this.ctime = ctime;
    }

    public Reply() {
    }
}
