package com.geek.java.lesson3;

import java.sql.Array;
import java.util.*;

public class WordMethods {

    public static List<String> getUniqueWords(String[] words) {
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].toLowerCase(Locale.ROOT);
        }
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(words));
        return new ArrayList<>(uniqueWords);
    }

    public static Map<String, Integer> getUniqueWordsWithRepeats(String[] words) {
        Map<String, Integer> wordsWithRepeats = new HashMap<>();
        for (String word : words) {
            word = word.toLowerCase(Locale.ROOT);
            if (wordsWithRepeats.containsKey(word)) {
                wordsWithRepeats.replace(word, wordsWithRepeats.get(word) + 1);
            } else {
                wordsWithRepeats.put(word, 1);
            }
        }
        return wordsWithRepeats;
    }

}
