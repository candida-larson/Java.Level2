package com.geek.java.lesson3;

public class Test {
    public static void main(String[] args) {
        System.out.println("Test word methods");
        String[] words = {"drill", "wind", "neglect", "Friendly", "unrest", "trouble",
                "plagiarize", "recycle", "spokesperson", "neglect", "unrest", "friendly", "friendly"};

        System.out.println(WordMethods.getUniqueWords(words));
        System.out.println(WordMethods.getUniqueWordsWithRepeats(words));

        System.out.println();
        System.out.println("Test phonebook");
        Phonebook phonebook = new Phonebook();
        for (int i = 0; i < 9999; i++) {
            phonebook.add(new PhonebookItem(new Person("Testname #" + i), new PhoneNumber("+79221110500")));
        }
        phonebook.add(new PhonebookItem(new Person("Petrov"), new PhoneNumber("+79221110500")));
        phonebook.add(new PhonebookItem(new Person("Petrov"), new PhoneNumber("+79221110501")));
        phonebook.add(new PhonebookItem(new Person("Nikolaev"), new PhoneNumber("+79221110503")));
        phonebook.add(new PhonebookItem(new Person("Ohmatov"), new PhoneNumber("+79221110507")));
        phonebook.add(new PhonebookItem(new Person("Pushkin"), new PhoneNumber("+79221110509")));

        System.out.println("Petrov phones: " + phonebook.getPhoneNumbersBySurname("petrov"));
        System.out.println("Ivanov phones: " + phonebook.getPhoneNumbersBySurname("ivanov"));

    }
}
