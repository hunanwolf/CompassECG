package com.compassecg.test720.compassecg.tooclass.domain;

import java.io.Serializable;

/**
 * Created by hp on 2016/10/13.
 */

public class phone implements Serializable {


    public phone(String value,String phoneid) {
        this.name=value;
        this.phone=phoneid;
    }

    private String name;
    private String phone;
    private String header;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", header='" + header + '\'' +
                '}';
    }
}
