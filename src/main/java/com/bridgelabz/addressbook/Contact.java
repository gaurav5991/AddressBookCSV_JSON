package com.bridgelabz.addressbook;

import com.opencsv.bean.CsvBindByName;

import java.time.LocalDate;
import java.util.Objects;

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
    public LocalDate startDate;

    public Contact() {
    }

    public Contact(String first_name, String last_name, String address, String city,
                   String state, String zip_code, String phone_number, String email,
                   int user_id, int type_id, String contact_type, LocalDate startDate) {
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
        this.startDate = startDate;
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

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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

    public int getUser_id() {
        return user_id;
    }

    public int getType_id() {
        return type_id;
    }

    public String getContact_type() {
        return contact_type;
    }

    public LocalDate getStartDate() {
        return startDate;
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
        return "Contact{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip_code='" + zip_code + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", user_id=" + user_id +
                ", type_id=" + type_id +
                ", contact_type='" + contact_type + '\'' +
                ", startDate=" + startDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return user_id == contact.user_id &&
                type_id == contact.type_id &&
                Objects.equals(first_name, contact.first_name) &&
                Objects.equals(last_name, contact.last_name) &&
                Objects.equals(address, contact.address) &&
                Objects.equals(city, contact.city) &&
                Objects.equals(state, contact.state) &&
                Objects.equals(zip_code, contact.zip_code) &&
                Objects.equals(phone_number, contact.phone_number) &&
                Objects.equals(email, contact.email) &&
                Objects.equals(contact_type, contact.contact_type) &&
                Objects.equals(startDate, contact.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, last_name, address, city, state, zip_code, phone_number, email, user_id, type_id, contact_type, startDate);
    }
}