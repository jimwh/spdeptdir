package edu.columbia.cuitei.deptdir.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Level1 extends Directory {

    private static final String LEVEL="LEVEL1";

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "level1")
    // @OrderBy("id desc")
    private List<Level2> level2List = new ArrayList<Level2>(0);
    public List<Level2> getLevel2List() {
        return getLevel2List();
    }
    public void setLevel2List(List<Level2> level2List) {
        this.level2List = level2List;
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
