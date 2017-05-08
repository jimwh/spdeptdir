package edu.columbia.cuitei.deptdir.domain;

import javax.persistence.Entity;

@Entity
public class Level1 extends DeptDirectory{

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    */

    /*
    @Column(nullable = false)
    private String  directoryName;

    private Integer parent;
    private String tieLine;
    private String address;
    private String mailCode;
    private String phoneType;
    private String phoneNumber;
    */
    /*
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
    */

    /*
    public DeptDirectory GetDeptDirectory() {
        DeptDirectory deptDirectory=new DeptDirectory();
        deptDirectory.setId(getId());
        deptDirectory.setDirectoryName(getDirectoryName());
        deptDirectory.setTieLine(getTieLine());
        deptDirectory.setAddress(getAddress());
        deptDirectory.setParent(getParent());
        deptDirectory.setMailCode(getMailCode());
        deptDirectory.setPhoneType(getPhoneType());
        deptDirectory.setPhoneNumber(getPhoneNumber());
        return deptDirectory;
    }
    */
}
