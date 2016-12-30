package com.compassecg.test720.compassecg.bean;

/**
 * Created by jie on 2016/9/12.
 */

public class UserList {
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserList{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
