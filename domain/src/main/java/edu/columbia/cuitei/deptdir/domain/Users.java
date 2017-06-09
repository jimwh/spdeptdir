package edu.columbia.cuitei.deptdir.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
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

    @Column(name="enable")
    private boolean active;
    public boolean getActive() {
        return this.active;
    }
    public void setActive(boolean active){
        this.active = active;
    }

    @Column(name = "email")
    @Email(message = "Please provide a valid Email")
    @NotEmpty(message = "Please provide an Email")
    private String email;
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    @Transient
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    @Column(name = "first_name")
    private String firstName;
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String name){
        this.firstName = name;
    }

    @Column(name = "last_name")
    private String lastName;
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String name){
        this.lastName = name;
    }

    /*
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles){
        this.roles = roles;
    }
    */

}
