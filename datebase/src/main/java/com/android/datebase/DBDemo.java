package com.android.datebase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * qt
 * 2019-10-18
 */
@Entity
public class DBDemo {
    @Id(autoincrement = true)
    private long id;
    @Index(unique = true)
    private int perNo;
    private String name;
    private String content;
    private String sex;
    private String sex1;
    @Generated(hash = 1136039563)
    public DBDemo(long id, int perNo, String name, String content, String sex,
            String sex1) {
        this.id = id;
        this.perNo = perNo;
        this.name = name;
        this.content = content;
        this.sex = sex;
        this.sex1 = sex1;
    }
    @Generated(hash = 1661425160)
    public DBDemo() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getPerNo() {
        return this.perNo;
    }
    public void setPerNo(int perNo) {
        this.perNo = perNo;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex1() {
        return this.sex1;
    }
    public void setSex1(String sex1) {
        this.sex1 = sex1;
    }

}
