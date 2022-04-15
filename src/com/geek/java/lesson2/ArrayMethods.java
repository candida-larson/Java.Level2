package com.geek.java.lesson2;

import com.geek.java.lesson2.exceptions.MyArrayDataException;
import com.geek.java.lesson2.exceptions.MyArraySizeException;

public class ArrayMethods {
    public static int sumElementsOfTwoDimensionalArray(String[][] arrayStrings, int arraySize)
            throws MyArraySizeException, MyArrayDataException {

        if (!isValidSizeOfArray(arrayStrings, arraySize)) {
            throw new MyArraySizeException("Invalid size of array. Need " + arraySize + " size");
        }

        return getSumOfArrayElements(arrayStrings);
    }

    private static int getSumOfArrayElements(String[][] arrayStrings) throws MyArrayDataException {
        int sum = 0;
        for (int i = 0; i < arrayStrings.length; i++) {
            for (int j = 0; j < arrayStrings[i].length; j++) {
                try {
                    int intElementOfArray = Integer.parseInt(arrayStrings[i][j]);
                    sum += intElementOfArray;
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(String.format("Can not parse element \"%s\" to int at position [%d][%d]", arrayStrings[i][j], i, j));
                }
            }
        }
        return sum;
    }

    private static boolean isValidSizeOfArray(String[][] arrayStrings, int arraySize) {
        if (arrayStrings.length != arraySize) {
            return false;
        }

        for (String[] arrayString : arrayStrings) {
            if (arrayString.length != arraySize) {
                return false;
            }
        }

        return true;
    }
}
