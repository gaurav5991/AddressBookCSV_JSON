package com.bridgelabz.addressbook;

import com.opencsv.bean.CsvBindByName;

public class Contact {
    @CsvBindByName
    public String first_name;

    @CsvBindByName
    public String last_name;

    @CsvBindByName
    public String address;

    @CsvBindByName
    public String city;

    @CsvBindByName
    public String state;

    @CsvBindByName
    public String zip_code;

    @CsvBindByName
    public String phone_number;

    @CsvBindByName
    public String email;

    public int user_id;
    public int type_id;
    public String contact_type;

    public Contact() {
    }

    public Contact(String first_name, String last_name, String address, String city, String state, String zip_code, String phone_number, String email, int user_id, int type_id, String contact_type) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.phone_number = phone_number;
        this.email = email;
        this.user_id = user_id;
        this.type_id = type_id;
        this.contact_type = contact_type;
    }

    /* setter Methods*/

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /* getter Method */

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip_code() {
        return zip_code;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    /*Parameterized Constructor*/

    public Contact(String first_name, String last_name, String address, String city, String state, String zip_code, String phone_number, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.phone_number = phone_number;
        this.email = email;
    }

    @Override
    public String toString() {
        return "ContactDetails: FirstName=" + first_name + ", LastName=" + last_name + ", address=" + address + ", city="
                + city + ", state=" + state + ", email=" + email + ", zip=" + zip_code + ", phoneNumber=" + phone_number;
    }
}