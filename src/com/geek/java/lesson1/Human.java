package com.geek.java.lesson1;

public class Human implements Competitor, Jumpable, Runnable {
    @Override
    public int jump() {
        return 75;
    }

    @Override
    public int run() {
        return 45000;
    }
}
