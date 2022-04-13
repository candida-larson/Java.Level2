package com.geek.java.lesson1;

import com.geek.java.lesson1.competitors.Cat;
import com.geek.java.lesson1.competitors.Competitor;
import com.geek.java.lesson1.competitors.Human;
import com.geek.java.lesson1.competitors.Robot;
import com.geek.java.lesson1.obstacles.*;

public class TestCompetition {
    public static void main(String[] args) {

        System.out.println("Test #1: ");
        Human human = new Human();
        Cat cat = new Cat();
        Robot robot = new Robot();

        JumpObstacle wall = new Wall(100);
        JumpObstacle secondWall = new Wall(190);
        RunningObstacle runningObstacle = new Treadmill(45000);

        Obstacle[] obstacles = {wall, runningObstacle, secondWall};
        Competitor[] competitors = {human, cat, robot};

        for (Competitor competitor : competitors) {
            if (CompetitorObstacle.canCompetitorPassObstacles(competitor, obstacles)) {
                System.out.println(competitor + ": can pass obstacles");
            } else {
                System.out.println(competitor + ": can not pass obstacles");
            }
        }

    }
}