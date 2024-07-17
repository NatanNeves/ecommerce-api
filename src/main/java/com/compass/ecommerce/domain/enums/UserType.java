package com.compass.ecommerce.domain.enums;

public enum UserType {

    COMMON("comum"),
    ADMIN("admin");

    private String role;

     UserType(String role){
      this.role =role;
    }

    public String getRole(){
         return this.role;
    }
}
