package edu.columbia.cuitei.deptdir.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String  directoryName;

    @Column
    private Integer parent;

    @Column
    private String tieLine;

    @Column
    private String address;

    @Column
    private String mailCode;

    @Column
    private String phoneType;

    @Column
    private String phoneNumber;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) { this.id=id; }

    public String getDirectoryName() {
        return directoryName;
    }
    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public Integer getParent() {
        return parent;
    }
    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getTieLine() { return tieLine; }
    public void setTieLine(String tieLine) {
        this.tieLine = tieLine;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getMailCode() {
        return mailCode;
    }
    public void setMailCode(String mailCode) {
        this.mailCode = mailCode;
    }

    public String getPhoneType() {
        return phoneType;
    }
    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private transient String level;
    public String getLevel() {return level;}
    public void setLevel(String level) { this.level= level;}

}
