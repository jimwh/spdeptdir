package edu.columbia.cuitei.deptdir.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int id;
    public int getId() {
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    @Column(name = "role")
    private String role;
    public String getRole() {
        return this.role;
    }
    public void setRole(String role){
        this.role = role;
    }

}
