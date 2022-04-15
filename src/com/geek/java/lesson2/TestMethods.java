package com.geek.java.lesson2;

import com.geek.java.lesson2.ArrayMethods;

public class TestMethods {
    public final static int ARRAY_SIZE = 4;

    public static void main(String[] args) {
        System.out.println("Start test methods");

        String[][] array = new String[ARRAY_SIZE][ARRAY_SIZE];

        int sumOfArrayElements = ArrayMethods.sumElementsOfTwoDimensionalArray(array, ARRAY_SIZE);

    }
}
