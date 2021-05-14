package de.jilence.jutils.challenge;

import de.jilence.jutils.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class ChallengeSystem {

    public static void startChallenges() {

        for (int i = 0; i < ChallengeManager.Challenges.values().length; i++) {
            ChallengeManager.Challenges challenges = ChallengeManager.Challenges.values()[i];
            if (ChallengeManager.isChallengeEnabled(challenges)) {
                challenges.getChallenge().onStart();
            }
        }
    }

    public static void stopChallenges() {
        HandlerList.unregisterAll(Main.getPlugin(Main.class));
        Main.getPlugin(Main.class).listenerRegistration(Bukkit.getPluginManager());

        for (int i = 0; i < ChallengeManager.Challenges.values().length; i++) {
            ChallengeManager.Challenges challenges = ChallengeManager.Challenges.values()[i];

            if (ChallengeManager.isChallengeEnabled(challenges)) {
                challenges.getChallenge().onDisable();
            }
        }
    }

    public static void restartChallenges() {
        stopChallenges();
        startChallenges();
    }
}