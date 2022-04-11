package com.geek.java.lesson1.competitors;

public class Human implements Competitor {
    @Override
    public int jump() {
        return 75;
    }

    @Override
    public String toString() {
        return "Human";
    }

    @Override
    public int run() {
        return 45000;
    }
}
