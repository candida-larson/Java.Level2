package com.geek.java.lesson3;

public class PhoneNumber {
    private String number;

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number;
    }

    public PhoneNumber(String number) {
        this.number = number;
    }
}
