package com.geek.java.lesson1.obstacles;

public class Treadmill implements RunningObstacle {
    private int length;

    @Override
    public String toString() {
        return "Treadmill{" +
                "length=" + length +
                '}';
    }

    public Treadmill(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public int getLinearSize() {
        return getLength();
    }
}
