package com.geek.java.lesson1;

public class Cat implements Competitor, Runnable, Jumpable {
    @Override
    public int jump() {
        return 32;
    }

    @Override
    public boolean canJumpWall(Wall wall) {
        return false;
    }

    @Override
    public int run() {
        return 12500;
    }

    @Override
    public boolean canRunTreadmill(Treadmill treadmill) {
        return false;
    }
}
