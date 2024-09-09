package com.exampleesame.entity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.core.UriBuilder;

import com.exampleesame.boundary.CustomersResources;
import com.exampleesame.entity.constant.BaseEntity;


/**
 * @author piny73
 */
@Entity
@Table(name = "customer")
public class Customer extends  BaseEntity {
    
    @JsonbProperty(value = "customer_name")
    @NotBlank
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    
    
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)    
    private String email;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    UnsupportedOperationException getError() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
  
   
   
    public JsonObject toJsonSliceName() {

        return Json.createObjectBuilder()
                .add("Customer_name", this.customerName)
                .add("link", UriBuilder.fromResource(CustomersResources.class)
                        .path(CustomersResources.class,"find")
                        .build(this.id).toString())
                .build();
    }
  
}
