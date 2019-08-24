package domain;

import java.sql.Date;

public class Teacher {

    private int tid;
    private String tname;
    private String tsex;
    private Date tbirthday;

    public Teacher(){}

    public Teacher(int tid, String tname, String tsex, Date tbirthday) {
        this.tid = tid;
        this.tname = tname;
        this.tsex = tsex;
        this.tbirthday = tbirthday;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTsex() {
        return tsex;
    }

    public void setTsex(String tsex) {
        this.tsex = tsex;
    }

    public Date getTbirthday() {
        return tbirthday;
    }

    public void setTbirthday(Date tbirthday) {
        this.tbirthday = tbirthday;
    }
}
