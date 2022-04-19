package com.geek.java.lesson3;

public class Test {
    public static void main(String[] args) {
        String[] words = {"drill", "wind", "neglect", "Friendly", "unrest", "trouble",
                "plagiarize", "recycle", "spokesperson", "neglect", "unrest", "friendly", "friendly"};

        System.out.println(WordMethods.getUniqueWords(words));
        System.out.println(WordMethods.getUniqueWordsWithRepeats(words));
    }
}
