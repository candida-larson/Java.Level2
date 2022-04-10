package com.geek.java.lesson1;

public class Human implements Competitor, Jumpable, Runnable {
    @Override
    public int jump() {
        return 75;
    }

    @Override
    public boolean canJumpWall(Wall wall) {
        return false;
    }

    @Override
    public int run() {
        return 45000;
    }

    @Override
    public boolean canRunTreadmill(Treadmill treadmill) {
        return false;
    }
}
