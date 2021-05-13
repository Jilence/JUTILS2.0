package de.jilence.jutils.timer;

import de.jilence.jutils.Main;
import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.challenge.ChallengeSystem;
import de.jilence.jutils.utils.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Timer {

    private static BukkitTask bukkitTask;
    private static boolean pause;
    private static int timerInt;
    private static int countdownInt;

    public static void stopTimer() {
        bukkitTask.cancel();
    }

    public static void setPause(boolean pause) {
        Timer.pause = pause;

        if(!pause) {
            for(int i = 0; i < ChallengeManager.Challenges.values().length; i++) {
                ChallengeManager.Challenges challenges = ChallengeManager.Challenges.values()[i];

                if(ChallengeManager.isChallengeEnabled(challenges)) {
                    challenges.getChallenge().onPause();
                }

            }
        }

    }

    public static boolean isPause() {
        return pause;
    }

    public static boolean isResumed() {
        return !pause;
    }

    public static void restartTimer() {
        bukkitTask.cancel();
        startTimer();
    }

    public static void startTimer() {

        countdownInt = TimerManager.getCountdownTime();

        if(!TimerManager.isTimerEnabled()) {
            return;
        }

        bukkitTask = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(Main.class), new Runnable() {
            @Override
            public void run() {

                if(isPause()) {
                    ChallengeSystem.stopChallenges();
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendActionBar(Component.text("§6Der Timer ist pausiert")));
                    return;
                }

                for(int i = 0; i < ChallengeManager.Challenges.values().length; i++) {
                    ChallengeManager.Challenges challenges = ChallengeManager.Challenges.values()[i];

                    if(ChallengeManager.isChallengeEnabled(challenges)) {
                        challenges.getChallenge().onTick();
                    }

                }

                if(TimerManager.isForward()) {
                    setTimerInt(getTimerInt() + 1);
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendActionBar(Component.text("§6§l" + Messages.timerWithoutDays(getTimerInt()))));
                    return;
                }

                if(TimerManager.isBackward()) {

                    if(getCountdownInt() <= 0) {
                        Bukkit.broadcast(Component.text(Main.getPrefix() + "§7Der §9Timer §7ist §cabgelaufen."), "bukkit.broadcast");
                        Timer.setPause(true);
                        ChallengeSystem.stopChallenges();
                        stopTimer();
                    }

                    setCountdownInt(getCountdownInt() - 1);
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendActionBar(Component.text("§6§l" + Messages.timerWithoutDays(getCountdownInt()))));

                }

            }
        }, 0, 20);

    }

    public static int getTimerInt() {
        return timerInt;
    }

    public static void setTimerInt(int timerInt) {
        Timer.timerInt = timerInt;
    }

    public static int getCountdownInt() {

        return countdownInt;
    }

    public static void setCountdownInt(int countdownInt) {
        Timer.countdownInt = countdownInt;
    }

    public static void resetTimer() {
        Timer.setPause(true);
        setTimerInt(0);
        setCountdownInt(TimerManager.getCountdownTime());
    }
}
