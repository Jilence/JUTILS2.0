package de.jilence.jutils.utils;

import de.jilence.jutils.Main;

import java.util.concurrent.TimeUnit;

public class Messages {

    public static String CONFIG_USER = Main.getError() + "§cDiser Command ist nur für Spieler verfügbar.";
    public static String NO_PERMISSIONS = Main.getError() + "§cDafür hast du keine Rechte.";
    public static String NO_COMMAND_FOUND = Main.getError() + "§cDer Command wurde nicht gefunden";


    public static String timer(long time) {
        long days = TimeUnit.SECONDS.toDays(time);
        long hours = TimeUnit.SECONDS.toHours(time - TimeUnit.DAYS.toSeconds(days));
        long minutes = TimeUnit.SECONDS.toMinutes(time - TimeUnit.DAYS.toSeconds(days) - TimeUnit.HOURS.toSeconds(hours));
        long seconds = TimeUnit.SECONDS.toSeconds(time - TimeUnit.DAYS.toSeconds(days) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes));

        return ((days < 10) ? "0" + days : days) + ((days == 1) ? " Tag, " :  " Tage, ") +
                ((hours < 10) ? "0" + hours : hours) + ":" +
                ((minutes < 10) ? "0" + minutes : minutes) + ":" +
                ((seconds < 10) ? "0" + days : seconds);

    }

    public static String timerWithoutDays(long time) {
        long days = TimeUnit.SECONDS.toDays(time);
        long hours = TimeUnit.SECONDS.toHours(time - TimeUnit.DAYS.toSeconds(days));
        long minutes = TimeUnit.SECONDS.toMinutes(time - TimeUnit.DAYS.toSeconds(days) - TimeUnit.HOURS.toSeconds(hours));
        long seconds = TimeUnit.SECONDS.toSeconds(time - TimeUnit.DAYS.toSeconds(days) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes));

        return (days == 0 ? "" : ((days < 10) ? "0" + days : days) + ((days == 1) ? " Tag, " :  " Tage, ")) +
                ((hours < 10) ? "0" + hours : hours) + ":" +
                ((minutes < 10) ? "0" + minutes : minutes) + ":" +
                ((seconds < 10) ? "0" + seconds : seconds);

    }

    public static String timerWithoutHours(long time) {
        long minutes = TimeUnit.SECONDS.toMinutes(time);
        long seconds = TimeUnit.SECONDS.toSeconds(time - TimeUnit.MINUTES.toSeconds(minutes));

        return ((minutes < 10) ? "0" + minutes : minutes) + ":" +
                ((seconds < 10) ? "0" + seconds : seconds);

    }

}
