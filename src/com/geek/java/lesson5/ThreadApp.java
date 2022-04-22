package com.geek.java.lesson5;

public class ThreadApp {
    static final int SIZE = 10_000_000;
    static final int HALF_OF_SIZE = SIZE / 2;
    static float[] numbers = new float[SIZE];

    public static void main(String[] args) throws InterruptedException {
        calculationsInOneThread();
        calculationsInTwoThreads();
    }

    public static void calculationsInOneThread() {
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = 1;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = getProceedNumber(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("calculationsInOneThread: " + (endTime - startTime));
    }

    private static float getProceedNumber(int i) {
        return (float) (numbers[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                Math.cos(0.4f + i / 2));
    }

    public static void calculationsInTwoThreads() throws InterruptedException {
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = 1;
        }
        long startTime = System.currentTimeMillis();

        Thread firstThread = new Thread(() -> {
            for (int i = 0; i < HALF_OF_SIZE; i++) {
                numbers[i] = getProceedNumber(i);
            }
        });

        Thread secondThread = new Thread(() -> {
            for (int i = HALF_OF_SIZE; i < SIZE; i++) {
                numbers[i] = getProceedNumber(i);
            }
        });

        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();

        long endTime = System.currentTimeMillis();
        System.out.println("calculationsInTwoThreads: " + (endTime - startTime));
    }

}
