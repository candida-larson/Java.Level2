package com.geek.java.lesson1;

import com.geek.java.lesson1.obstacles.Obstacle;

public class Wall implements Obstacle {
    private int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
