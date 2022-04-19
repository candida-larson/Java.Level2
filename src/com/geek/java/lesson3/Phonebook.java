package com.geek.java.lesson3;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Phonebook implements IPhonebook {
    private List<PhonebookItem> items;

    public Phonebook() {
        items = new ArrayList<>();
    }

    @Override
    public boolean add(PhonebookItem phonebookItem) {
        return items.add(phonebookItem);
    }

    @Override
    public List<PhoneNumber> getPhoneNumbersBySurname(String surname) {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        for (PhonebookItem item : items) {
            if (item.getPerson().getSurname().toLowerCase(Locale.ROOT).equals(surname.toLowerCase(Locale.ROOT))) {
                phoneNumbers.add(item.getPhoneNumber());
            }
        }
        return phoneNumbers;
    }
}
