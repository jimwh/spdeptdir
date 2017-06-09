package edu.columbia.cuitei.deptdir.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="authorities")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    public int getId() {
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    @Column(name = "username")
    private String username;
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    @Column(name = "authority")
    private String authority;
    public String getAuthority() {
        return this.authority;
    }
    public void setAuthority(String authority){
        this.authority = authority;
    }

}
