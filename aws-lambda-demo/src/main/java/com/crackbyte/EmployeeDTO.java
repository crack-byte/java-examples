package com.crackbyte;

public class EmployeeDTO {
    private String name;
    private String id;
    private String email;
    private String phone;
    private String address;
    private int hasCode;

    public EmployeeDTO() {
    }
    public EmployeeDTO(String name, String id, String email, String phone, String address) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getHasCode() {
        return hasCode;
    }

    public void setHasCode(int hasCode) {
        this.hasCode = hasCode;
    }
}
