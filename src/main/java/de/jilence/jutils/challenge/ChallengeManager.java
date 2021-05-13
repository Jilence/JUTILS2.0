package de.jilence.jutils.challenge;

import de.jilence.jutils.Main;
import de.jilence.jutils.challenge.challenge.*;
import de.jilence.jutils.timer.Timer;
import de.jilence.jutils.utils.ConfigManager;
import de.jilence.jutils.utils.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChallengeManager {

    private static final Challenge NoBlockBreakChallenge = new NoBlockBreak();
    private static final Challenge NoBlockPlaceChallenge = new NoBlockPlace();
    private static final Challenge ChunkDestroyer = new ChunkDestroyer();
    private static final Challenge RandomEffectOnDamage = new RandomEffectOnDamage();
    private static final Challenge AirBreath = new AirBreath();
    private static final Challenge RandomMobDrop = new RandomMobDrop();
    private static final Challenge RandomBlockDrop = new RandomBlockDrop();
    private static final Challenge BlockBreakOnSneak = new BlockBreakOnSneak();
    private static final Challenge ForceBlockChallenge = new ForceBlockChallenge();

    public enum Challenges {

        NOBLOCKBREAK(NoBlockBreakChallenge.getDisplayItem(), 1+9, NoBlockBreakChallenge),
        NOBLOCKPLACE(NoBlockPlaceChallenge.getDisplayItem(), 2+9, NoBlockPlaceChallenge),
        CHUNKDESTROYER(ChunkDestroyer.getDisplayItem(), 3+9, ChunkDestroyer),
        RANDOMEFFECTONDAMAGE(RandomEffectOnDamage.getDisplayItem(), 4+9, RandomEffectOnDamage),
        AIRBREATH(AirBreath.getDisplayItem(), 5+9, AirBreath),
        RANDOMMOBDROP(RandomMobDrop.getDisplayItem(), 6+9, RandomMobDrop),
        RANDOMBLOCKDROP(RandomBlockDrop.getDisplayItem(), 7+9, RandomBlockDrop),
        BLOCKBREAKONSNEAK(BlockBreakOnSneak.getDisplayItem(), 1+18, BlockBreakOnSneak),
        FORCEBLOCKCHALLENGE(ForceBlockChallenge.getDisplayItem(), 2+18, ForceBlockChallenge);

        ItemStack itemStack;
        int slot;
        Challenge challenge;

        Challenges(ItemStack itemStack, int slot, Challenge challenge) {
            this.itemStack = itemStack;
            this.slot = slot;
            this.challenge = challenge;
        }

        public ItemStack getItemStack() {
            return itemStack;
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
        if(new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).contains(String.valueOf(challenge))) {
            return (boolean) new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).get(String.valueOf(challenge));
        }
        return false;
    }
    public static void toggleChallenge(Challenges challenge) {
        if(new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).contains(String.valueOf(challenge))) {
            boolean bool = (boolean) new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).get(String.valueOf(challenge));
            new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set(String.valueOf(challenge), !bool);

            if(bool) {
                for(int i = 0; i < ChallengeManager.Challenges.values().length; i++) {
                    ChallengeManager.Challenges challenges = ChallengeManager.Challenges.values()[i];

                    if(ChallengeManager.isChallengeEnabled(challenges)) {
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
