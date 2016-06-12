package com.example.neven.fragmentsv2;

/**
 * Created by Neven on 16.5.2016..
 */
public class ContactsObjects {

    long _id;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;

    public ContactsObjects(){


    }

    public ContactsObjects(long _id) {
        this._id = _id;
    }

    public ContactsObjects(long _id, String firstName, String lastName, String phoneNumber, String email) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public ContactsObjects(String firstName, String lastName, String phoneNumber, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
