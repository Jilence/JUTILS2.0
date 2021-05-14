package de.jilence.jutils.timer;

import de.jilence.jutils.utils.ConfigManager;

public class TimerManager {

    public static boolean isTimerEnabled() {
        if (new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).contains("timer")) {
            return (boolean) new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).get("timer");
        }
        new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).set("timer", false);
        return false;
    }

    public static void toggleTimerActivity() {
        boolean bool = (boolean) new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).get("timer");
        new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).set("timer", !bool);
    }

    public static void setDirection(DIRECTION direction) {
        new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).set("direction", direction.getDirection());
    }

    public static String getDirection() {
        if (new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).contains("direction")) {
            if (new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).get("direction").toString().equalsIgnoreCase("forward")) {
                return "Vorwärts";
            } else
                return "Rückwärts";
        }
        new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).set("direction", DIRECTION.FORWARD.getDirection());
        return "Vorwärts";
    }

    public static void toggleDirection() {
        if (isForward()) {
            setDirection(DIRECTION.BACKWARD);
        } else
            setDirection(DIRECTION.FORWARD);
    }

    public static boolean isForward() {
        if (new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).contains("direction")) {
            return new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).get("direction").toString().equalsIgnoreCase("forward");
        }
        new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).set("direction", DIRECTION.FORWARD.getDirection());
        return true;
    }

    public static boolean isBackward() {
        if (new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).contains("direction")) {
            return new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).get("direction").toString().equalsIgnoreCase("backward");
        }
        new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).set("direction", DIRECTION.BACKWARD.getDirection());
        return true;
    }

    public enum DIRECTION {

        FORWARD("forward"),
        BACKWARD("backward");

        String direction;

        DIRECTION(String direction) {
            this.direction = direction;
        }

        public String getDirection() {
            return direction;
        }
    }

    public static int getCountdownTime() {
        if (new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).contains("countdownTime")) {
            return (int) new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).get("countdownTime");
        } else
            new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).set("countdownTime", 60);
        return 60;
    }

    public static void setCountdownTime(int countdownTime) {
        new ConfigManager(ConfigManager.CONFIGS.TIMER_CONFIG).set("countdownTime", countdownTime);
    }

}
