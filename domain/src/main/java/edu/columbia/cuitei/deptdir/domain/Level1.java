package edu.columbia.cuitei.deptdir.domain;

import java.io.Serializable;
import javax.persistence.Entity;



@Entity
public class Level1 extends Directory implements Serializable {

    private static final String LEVEL="LEVEL1";
    /*
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "level1")
    private List<Level2> level2List = new ArrayList<Level2>(0);
    public List<Level2> getLevel2List() {
        return level2List;
    }

    public void setLevel2List(List<Level2> level2List) {
        this.level2List = level2List;
    }
    */
    //

    public String getLevel() { return LEVEL; }
    public void setLevel(String level) { super.setLevel(LEVEL); }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
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
