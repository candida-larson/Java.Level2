package com.geek.java.lesson1.competitors;

public class Robot implements Competitor {

    @Override
    public String toString() {
        return "Robot";
    }

    @Override
    public int jump() {
        return 220;
    }

    @Override
    public int run() {
        return 840000;
    }

}
