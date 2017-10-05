package org.onlineshoppingcart.model;
 
public class CustomerInfo {
 
    private String name;
    private String email;
    private String phone;
    private String address;
    
    private boolean valid;
 
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPhone() {
        return phone;
    }
 
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }
     
    public boolean isValid() {
        return valid;
    }
 
    public void setValid(boolean valid) {
        this.valid = valid;
    }
 
}