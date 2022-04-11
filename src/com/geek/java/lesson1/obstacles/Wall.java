package com.geek.java.lesson1.obstacles;

public class Wall implements JumpObstacle {
    private int height;

    @Override
    public String toString() {
        return "Wall{" +
                "height=" + height +
                '}';
    }

    public Wall(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getLinearSize() {
        return getHeight();
    }
}
