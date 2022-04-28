package entity;

import java.io.Serializable;

public class Education implements Serializable {
    //学历表的实体类
    private Integer eid;
    private String ename;

    public Education() {
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }
}
