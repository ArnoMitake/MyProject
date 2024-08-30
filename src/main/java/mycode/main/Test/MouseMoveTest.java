package mycode.main.Test;

import java.awt.Robot;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class MouseMoveTest {
    private static final int MOVE_DELAY = 15000;
    private static final int AREA_WIDTH = 800;
    private static final int AREA_HEIGHT = 600;

    public static void main(String[] args) throws Exception {
        int moveCount = 0;
        Robot robot = new Robot();
        LocalDateTime startTime = LocalDateTime.now();

        while (true) {
            Thread.sleep(MOVE_DELAY);
            IntStream.range(0, 5).forEach(i -> moveMouseInArea(robot));
            moveCount++;

            LocalDateTime currentTime = LocalDateTime.now();
            Duration elapsedTime = Duration.between(startTime, currentTime);
            String formattedTime = formatDuration(elapsedTime);
            System.out.println(String.format("Move mouse: %s | Elapsed Time: %s", moveCount, formattedTime));
        }
    }

    public static void moveMouseInArea(Robot robot) {
        try {
            int targetX = ThreadLocalRandom.current().nextInt(AREA_WIDTH);
            int targetY = ThreadLocalRandom.current().nextInt(AREA_HEIGHT);
            robot.mouseMove(targetX, targetY);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        long days = absSeconds / (60 * 60 * 24);
        long hours = (absSeconds % (60 * 60 * 24)) / (60 * 60);
        long minutes = (absSeconds % (60 * 60)) / 60;
        long remainingSeconds = absSeconds % 60;

        StringBuilder formattedDuration = new StringBuilder();
        if (seconds < 0) {
            formattedDuration.append("-");
        }
        if (days > 0) {
            formattedDuration.append(days).append(" day(s) ");
        }
        if (hours > 0) {
            formattedDuration.append(hours).append(" hour(s) ");
        }
        if (minutes > 0) {
            formattedDuration.append(minutes).append(" minute(s) ");
        }
        formattedDuration.append(remainingSeconds).append(" second(s)");

        return formattedDuration.toString();
    }
}
