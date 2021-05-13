package de.jilence.jutils.challenge;

import de.jilence.jutils.Main;
import de.jilence.jutils.challenges.*;
import de.jilence.jutils.timer.Timer;
import de.jilence.jutils.utils.ConfigManager;
import de.jilence.jutils.utils.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChallengeManager {

    public enum Challenges {

        NOBLOCKBREAK(1 + 9, new NoBlockBreak()),
        NOBLOCKPLACE(2 + 9, new NoBlockPlace()),
        CHUNKDESTROYER(3 + 9, new ChunkDestroyer()),
        RANDOMEFFECTONDAMAGE(4 + 9, new RandomEffectOnDamage()),
        AIRBREATH(5 + 9, new AirBreath()),
        RANDOMMOBDROP(6 + 9, new RandomMobDrop()),
        RANDOMBLOCKDROP(7 + 9, new RandomBlockDrop()),
        BLOCKBREAKONSNEAK(1 + 18, new BlockBreakOnSneak()),
        FORCEBLOCKCHALLENGE(2 + 18, new ForceBlockChallenge());

        ItemStack itemStack;
        int slot;
        Challenge challenge;

        Challenges(int slot, Challenge challenge) {
            this.slot = slot;
            this.challenge = challenge;
        }

        public void setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public int getSlot() {
            return slot;
        }

        public void setSlot(int slot) {
            this.slot = slot;
        }

        public Challenge getChallenge() {
            return challenge;
        }

        public void setChallenge(Challenge challenge) {
            this.challenge = challenge;
        }
    }

    public static void enableChallenge(Challenges challenge) {
        new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set(String.valueOf(challenge), true);
    }

    public static void disableChallenge(Challenges challenge) {
        new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set(String.valueOf(challenge), false);
    }

    public static boolean isChallengeEnabled(Challenges challenge) {
        if (new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).contains(String.valueOf(challenge))) {
            return (boolean) new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).get(String.valueOf(challenge));
        }
        return false;
    }

    public static void toggleChallenge(Challenges challenge) {
        if (new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).contains(String.valueOf(challenge))) {
            boolean bool = (boolean) new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).get(String.valueOf(challenge));
            new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set(String.valueOf(challenge), !bool);

            if (bool) {
                for (int i = 0; i < ChallengeManager.Challenges.values().length; i++) {
                    ChallengeManager.Challenges challenges = ChallengeManager.Challenges.values()[i];
                    if (ChallengeManager.isChallengeEnabled(challenges)) {
                        challenges.getChallenge().onDisable();
                    }
                }
            }
        } else {
            new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set(String.valueOf(challenge), false);
        }
    }

    public static void winChallenge(Player player, String because) {

        Bukkit.getOnlinePlayers().forEach(player1 -> player1.setGameMode(GameMode.SPECTATOR));

        Bukkit.broadcast(Component.text(Main.getPrefix() + ""), "bukkit.broadcast");
        Bukkit.broadcast(Component.text(Main.getPrefix() + "§7Die §6Challenge §7wurde geschafft, da "), "bukkit.broadcast");
        Bukkit.broadcast(Component.text(Main.getPrefix() + because.replace("%player", player.getName())), "bukkit.broadcast");
        Bukkit.broadcast(Component.text(Main.getPrefix() + "§7Gebrauchte Zeit: §7§l" + Messages.timer(Timer.getTimerInt())), "bukkit.broadcast");
        Bukkit.broadcast(Component.text(Main.getPrefix() + ""), "bukkit.broadcast");

        Timer.setPause(true);
    }

    public static void loseChallenge(Player player, String because) {

        Bukkit.getOnlinePlayers().forEach(player1 -> player1.setGameMode(GameMode.SPECTATOR));

        Bukkit.broadcast(Component.text(Main.getPrefix() + ""), "bukkit.broadcast");
        Bukkit.broadcast(Component.text(Main.getPrefix() + "§7Die §6Challenge §7wurde §cnicht §7geschafft, da "), "bukkit.broadcast");
        Bukkit.broadcast(Component.text(Main.getPrefix() + because.replace("%player", player.getName())), "bukkit.broadcast");
        Bukkit.broadcast(Component.text(Main.getPrefix() + "§7Verbrauchte Zeit: §7§l" + Messages.timer(Timer.getTimerInt())), "bukkit.broadcast");
        Bukkit.broadcast(Component.text(Main.getPrefix() + ""), "bukkit.broadcast");

        Timer.setPause(true);
    }
}