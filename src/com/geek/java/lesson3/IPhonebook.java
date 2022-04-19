package com.geek.java.lesson3;

import java.util.List;

public interface IPhonebook {
    public boolean add(PhonebookItem phonebookItem);

    public List<PhoneNumber> getPhoneNumbersBySurname(String surname);
}
