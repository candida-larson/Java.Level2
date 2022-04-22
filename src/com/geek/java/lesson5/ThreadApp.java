package com.geek.java.lesson5;

public class ThreadApp {
    static final int SIZE = 10_000_000;
    static float[] numbers = new float[SIZE];

    public static void main(String[] args) throws InterruptedException {
        calculationsInOneThread();
        calculationsInMultipleThreads(2);
    }

    public static void calculationsInOneThread() {
        initNumbers();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = getProceedNumber(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("calculationsInOneThread: " + (endTime - startTime));
    }

    private static void initNumbers() {
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = 1;
        }
    }

    private static float getProceedNumber(int i) {
        return (float) (numbers[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                Math.cos(0.4f + i / 2));
    }

    public static void calculationsInMultipleThreads(int countThreads) throws InterruptedException {
        initNumbers();
        int countNumbersInOnePart = SIZE / countThreads;
        long startTime = System.currentTimeMillis();

        for (int threadIndex = 0; threadIndex < countThreads; threadIndex++) {
            int startIndex = threadIndex * (countNumbersInOnePart - 1);
            int endIndex = threadIndex * countNumbersInOnePart;
            if (threadIndex == countThreads - 1) {
                endIndex = SIZE;
            }
            int finalEndIndex = endIndex;
            Thread thread = new Thread(() -> {
                for (int i = startIndex; i < finalEndIndex; i++) {
                    numbers[i] = getProceedNumber(i);
                }
            });
            thread.start();
            thread.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("calculationsInMultipleThreads[" + countThreads + "]: " + (endTime - startTime));
    }

}
