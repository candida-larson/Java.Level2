package com.geek.java.lesson3;

public class PhonebookItem {
    private Person person;
    private PhoneNumber phoneNumber;

    public Person getPerson() {
        return person;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public PhonebookItem(Person person, PhoneNumber phoneNumber) {
        this.person = person;
        this.phoneNumber = phoneNumber;
    }
}
