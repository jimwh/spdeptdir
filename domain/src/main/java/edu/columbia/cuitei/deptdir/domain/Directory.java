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
    public Integer getId() {
        return id;
    }
    public void setId(final Integer id) { this.id=id; }

    @Column(nullable = false)

    private String name;
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    @Column
    private Integer parent;
    public Integer getParent() {
        return parent;
    }
    public void setParent(final Integer parent) {
        this.parent = parent;
    }

    @Column
    private String tieLine;
    public String getTieLine() { return tieLine; }
    public void setTieLine(final String tieLine) {
        this.tieLine = tieLine;
    }

    @Column
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Column
    private String mailCode;
    public String getMailCode() {
        return mailCode;
    }
    public void setMailCode(final String mailCode) {
        this.mailCode = mailCode;
    }

    @Column
    private String phoneType;
    public String getPhoneType() {
        return phoneType;
    }
    public void setPhoneType(final String phoneType) {
        this.phoneType = phoneType;
    }

    @Column
    private String phoneNumber;
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    private transient String level;
    public String getLevel() {return level;}
    public void setLevel(final String level) { this.level= level;}

}
