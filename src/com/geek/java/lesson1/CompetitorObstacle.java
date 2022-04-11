package com.geek.java.lesson1;

import com.geek.java.lesson1.competitors.Competitor;
import com.geek.java.lesson1.obstacles.JumpObstacle;
import com.geek.java.lesson1.obstacles.Obstacle;
import com.geek.java.lesson1.obstacles.RunningObstacle;

public class CompetitorObstacle {
    public static boolean canCompetitorPassObstacles(Competitor competitor, Obstacle[] obstacles) {
        for (Obstacle obstacle : obstacles) {
            if (!getCanPass(competitor, obstacle)) {
                return false;
            }
        }
        return true;
    }

    private static Boolean getCanPass(Competitor competitor, Obstacle obstacle) {
        if (obstacle instanceof JumpObstacle) {
            return competitor.jump() >= obstacle.getLinearSize();
        }
        if (obstacle instanceof RunningObstacle) {
            return competitor.run() >= obstacle.getLinearSize();
        }
        return false;
    }
}
