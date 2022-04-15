package com.geek.java.lesson2;

import com.geek.java.lesson2.exceptions.MyArrayDataException;
import com.geek.java.lesson2.exceptions.MyArraySizeException;

import java.util.Random;

public class TestMethods {
    public final static int ARRAY_SIZE = 4;

    public static void main(String[] args) {
        testValidArray();
        testInvalidArraySize();
        testInvalidElementInArray();
    }

    private static void testInvalidElementInArray() {
        System.out.println("START testInvalidElementInArray");

        String[][] array = new String[ARRAY_SIZE][ARRAY_SIZE];
        fillArrayWithRandomElements(array);
        printArray(array);
        array[1][2] = "Opps..."; // not int element

        try {
            int sumOfArrayElements = ArrayMethods.sumElementsOfTwoDimensionalArray(array, ARRAY_SIZE);
            System.out.println("Sum of array elements = " + sumOfArrayElements);
        } catch (MyArraySizeException e) {
            System.out.println(">> Invalid array size");
        } catch (MyArrayDataException e) {
            System.out.println(">> Invalid item in array: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testInvalidArraySize() {
        System.out.println("START testInvalidArraySize");
        String[][] array = new String[ARRAY_SIZE][ARRAY_SIZE];
        fillArrayWithRandomElements(array);
        printArray(array);

        try {
            int sumOfArrayElements = ArrayMethods.sumElementsOfTwoDimensionalArray(array, 6);
            System.out.println("Sum of array elements = " + sumOfArrayElements);
        } catch (MyArraySizeException e) {
            System.out.println(">> Invalid array size");
        } catch (MyArrayDataException e) {
            System.out.println(">> Invalid item in array: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testValidArray() {
        System.out.println("START testValidArray");
        String[][] array = new String[ARRAY_SIZE][ARRAY_SIZE];
        fillArrayWithRandomElements(array);
        printArray(array);

        try {
            int sumOfArrayElements = ArrayMethods.sumElementsOfTwoDimensionalArray(array, ARRAY_SIZE);
            System.out.println("Sum of array elements = " + sumOfArrayElements);
        } catch (MyArraySizeException e) {
            System.out.println(">> Invalid array size");
        } catch (MyArrayDataException e) {
            System.out.println(">> Invalid item in array: " + e.getMessage());
        }

        System.out.println();
    }

    private static void printArray(String[][] array) {
        System.out.println("Array: ");
        for (String[] strings : array) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void fillArrayWithRandomElements(String[][] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = String.valueOf(random.nextInt(10));
            }
        }
    }
}
