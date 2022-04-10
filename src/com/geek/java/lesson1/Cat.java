package com.geek.java.lesson1;

public class Cat implements Runnable, Jumpable {
    @Override
    public int jump() {
        return 32;
    }

    @Override
    public int run() {
        return 12500;
    }
}
