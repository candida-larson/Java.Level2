package com.geek.java.lesson1;

public class Robot implements Competitor, Runnable, Jumpable {
    @Override
    public int jump() {
        return 128;
    }

    @Override
    public int run() {
        return 84000;
    }
}
