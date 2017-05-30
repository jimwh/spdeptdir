package edu.columbia.cuitei.deptdir.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Level2 extends Directory {

    private static final String LEVEL="LEVEL2";

    @JoinColumn(name = "parent", referencedColumnName="parent", insertable=false, updatable=false)
    @ManyToOne(optional=false)
    private Level1 level1;
    public Level1 getLevel1() {
        return level1;
    }
    public void setLevel1(Level1 level1) {
        this.level1 = level1;
    }

    public String getLevel() { return LEVEL; }
    public void setLevel(String level) { super.setLevel(LEVEL); }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LEVEL).append("|")
                .append(getId()).append("|")
                .append(getName()).append("|")
                .append(getParent()).append("|")
                .append(getTieLine()).append("|")
                .append(getAddress()).append("|")
                .append(getMailCode()).append("|")
                .append(getPhoneType()).append("|")
                .append(getPhoneNumber());
        return sb.toString();
    }

}
