package com.geek.java.lesson1;

public class Robot implements Competitor, Runnable, Jumpable {
    @Override
    public int jump() {
        return 128;
    }

    @Override
    public boolean canJumpWall(Wall wall) {
        return false;
    }

    @Override
    public int run() {
        return 84000;
    }

    @Override
    public boolean canRunTreadmill(Treadmill treadmill) {
        return false;
    }
}
