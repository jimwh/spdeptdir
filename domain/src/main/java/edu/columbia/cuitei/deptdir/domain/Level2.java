package edu.columbia.cuitei.deptdir.domain;

import javax.persistence.Entity;

@Entity
public class Level2 extends DeptDirectory {

    private static final String LEVEL="LEVEL2";
    public String getLevel() { return LEVEL; }
    public void setLevel(String level) { super.setLevel(LEVEL); }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LEVEL).append("|")
                .append(getId()).append("|")
                .append(getDirectoryName()).append("|")
                .append(getParent()).append("|")
                .append(getTieLine()).append("|")
                .append(getAddress()).append("|")
                .append(getMailCode()).append("|")
                .append(getPhoneType()).append("|")
                .append(getPhoneNumber());
        return sb.toString();
    }

}
