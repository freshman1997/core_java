package com.yuan.cn.proxy.interfaces.pojo;

public class Stu {
    private int sid;
    private String sname;
    private String saddress;
    private String slikes;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSaddress() {
        return saddress;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress;
    }

    public String getSlikes() {
        return slikes;
    }

    public void setSlikes(String slikes) {
        this.slikes = slikes;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", saddress='" + saddress + '\'' +
                ", slikes='" + slikes + '\'' +
                '}';
    }
}
