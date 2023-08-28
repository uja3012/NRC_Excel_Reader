package com.nrc.excelreader.model;

/**
 * Model/Entity Class
 * Date : 27.08.2023
 * Version : 1.0 (Initial Version)
 * @author Ujwala Vanve
 */

public class Participant {

    private String name;
    private String date_of_birth;
    private String address;
    private String phone_number;

    public Participant(String name) {
        this.name=name;
        this.date_of_birth = "";
        this.address = "";
        this.phone_number = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return date_of_birth;
    }

    public void setDateOfBirth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "name='" + name + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", address='" + address + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
