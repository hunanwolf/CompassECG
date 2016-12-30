package com.compassecg.test720.compassecg.tooclass.domain;

/**
 * Created by hp on 2016/11/19.
 */

public class friend  {
    String friend_phone;
    String nickname;
    String header;
    String id;
    String heerdex;
    String is_friend;
    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(String is_friend) {
        this.is_friend = is_friend;
    }

    public String getHeerdex() {
        return heerdex;
    }

    public void setHeerdex(String heerdex) {
        this.heerdex = heerdex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFriend_phone() {
        return friend_phone;
    }

    public void setFriend_phone(String friend_phone) {
        this.friend_phone = friend_phone;
    }

    @Override
    public String toString() {
        return "friend{" +
                "friend_phone='" + friend_phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", header='" + header + '\'' +
                ", id='" + id + '\'' +
                ", heerdex='" + heerdex + '\'' +
                '}';
    }
}
