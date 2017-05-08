package edu.columbia.cuitei.deptdir.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Level2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String  directoryName;

    private Integer parent;
    private String tieLine;
    private String address;
    private String mailCode;
    private String phoneType;
    private String phoneNumber;


    public Integer getId() {
        return id;
    }

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


    public String getTieLine() {
        return tieLine;
    }
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

    /*
    public static DeptDirectory GetDeptDirectory(Level2 level2) {
        DeptDirectory deptDirectory=new DeptDirectory();
        deptDirectory.setId(level2.getId());
        deptDirectory.setDirectoryName(level2.getDirectoryName());
        deptDirectory.setTieLine(level2.getTieLine());
        deptDirectory.setAddress(level2.getAddress());
        deptDirectory.setParent(level2.getParent());
        deptDirectory.setMailCode(level2.getMailCode());
        deptDirectory.setPhoneType(level2.getPhoneType());
        deptDirectory.setPhoneNumber(level2.getPhoneNumber());
        return deptDirectory;
    }
    */
}
