package com.exampleesame.entity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.ws.rs.core.UriBuilder;

import com.exampleesame.boundary.UsersResources;
import com.exampleesame.entity.constant.BaseEntity;
import com.exampleesame.entity.constant.UserRoles;


/**
 * @author piny73
 */
@Entity
@Table(name = "user")
public class User extends  BaseEntity {
    
    @JsonbProperty(value = "first_name")
    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @JsonbProperty(value = "last_name")
    @NotBlank
    @Column(name = "last_name", nullable = false)    
    private String lastName;
    
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)    
    private String email;
    
    @NotBlank
    @Size(min = 4)
    @Column(nullable = false)    
    private String pwd;
        
    @Enumerated(EnumType.STRING)
    UserRoles roleuser;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public UserRoles getRoleuser() {
        return roleuser;
    }

    public void setRoleuser(UserRoles roleuser) {
        this.roleuser = roleuser;
    }
    
    
    

    public JsonObject toJsonSliceName() {

        return Json.createObjectBuilder()
                .add("first_name", this.firstName)
                .add("link", UriBuilder.fromResource(UsersResources.class)
                        .path(UsersResources.class,"find")
                        .build(this.id).toString())
                .build();
    }
  
}
