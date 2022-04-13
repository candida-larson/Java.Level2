package com.geek.java.lesson1.competitors;

public class Cat implements Competitor {
    @Override
    public int jump() {
        return 32;
    }

    @Override
    public int run() {
        return 12500;
    }

    @Override
    public String toString() {
        return "Cat";
    }

}
