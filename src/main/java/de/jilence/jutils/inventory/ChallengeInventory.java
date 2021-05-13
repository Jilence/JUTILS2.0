package de.jilence.jutils.inventory;

import de.jilence.jutils.challenge.Challenge;
import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.utils.InventoryBuilder;
import de.jilence.jutils.utils.ItemBuilder;
import de.jilence.jutils.utils.LoreBuilder;
import de.jilence.jutils.utils.SkullCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class ChallengeInventory {

    public final static String INVENTORY_NAME = "§9Herausforderungen §8● ";

    public static HashMap<UUID, Integer> playerSides = new HashMap<>();

    public enum SIDE {

        SIDE1(0, 44, 1),
        SIDE2(45, 89, 2);

        int sideMin, sideMax, side;

        SIDE(int sideMin, int sideMax, int side) {
            this.side = side;
            this.sideMax = sideMax;
            this.sideMin = sideMin;
        }

        public int getSideMin() {
            return sideMin;
        }

        public int getSideMax() {
            return sideMax;
        }

        public int getSide() {
            return side;
        }
    }


    public static Inventory getChallengeInventory(SIDE side, Player player) {
        Inventory inventory = Bukkit.createInventory(null, 5*9, Component.text(INVENTORY_NAME));

        int minusSlot = 0;

        if(side.getSide() != 1) {
            minusSlot = 44 * (side.getSide() - 1);
            if(side.getSide() == 2) {
                minusSlot++;
            } else
                minusSlot+= 2;

        }



        InventoryBuilder.fillInventory(inventory, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname("").build());
        playerSides.put(player.getUniqueId(), side.getSide());


        if(ChallengeManager.Challenges.values().length == 0) {
            return null;
        }

        for(int i = 0; i < ChallengeManager.Challenges.values().length; i++) {

            Challenge challenge = ChallengeManager.Challenges.values()[i].getChallenge();
            int slot =  (ChallengeManager.Challenges.values()[i].getSlot());

            if(slot > side.getSideMax()) continue;
            if(slot < side.getSideMin()) continue;

            if(challenge.getDisplayItem() == null) {
                continue;
            }

            inventory.setItem((slot - minusSlot), challenge.getDisplayItem());

        }

        boolean lastSide = true;

        for(SIDE side1 : SIDE.values()) {

            String base64Right = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmEzYjhmNjgxZGFhZDhiZjQzNmNhZThkYTNmZTgxMzFmNjJhMTYyYWI4MWFmNjM5YzNlMDY0NGFhNmFiYWMyZiJ9fX0=";
            ItemStack rightArrow = new ItemBuilder(SkullCreator.itemFromBase64(base64Right)).displayname("§cFehler..").build();
            new LoreBuilder(rightArrow)
                    .addSpace()
                    .addLoreLine("§7Du bist bereits auf der §cletzten Seite.")
                    .addSpace();

            if(side1.getSide() > side.getSide()) {
                rightArrow = new ItemBuilder(SkullCreator.itemFromBase64(base64Right)).displayname("§2Vorwärts... §7auf Seite " + (side.getSide() + 1)).build();
                new LoreBuilder(rightArrow)
                        .addSpace()
                        .addLoreLine("§7Gehe auf die nächste §9Seite.")
                        .addSpace();


            }

            inventory.setItem((side.getSideMax() - minusSlot), rightArrow);

            if(!lastSide) continue;

            String base64Left = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODY1MmUyYjkzNmNhODAyNmJkMjg2NTFkN2M5ZjI4MTlkMmU5MjM2OTc3MzRkMThkZmRiMTM1NTBmOGZkYWQ1ZiJ9fX0=";
            ItemStack leftArrow = new ItemBuilder(SkullCreator.itemFromBase64(base64Left)).displayname("§cFehler..").build();
            new LoreBuilder(leftArrow)
                    .addSpace()
                    .addLoreLine("§7Du bist bereits auf der §cletzten Seite.")
                    .addSpace();

            int currentSide = playerSides.get(player.getUniqueId());
            if(side1.getSide() == (currentSide - 1)) {
                leftArrow = new ItemBuilder(SkullCreator.itemFromBase64(base64Left)).displayname("§2Zurück... §7auf Seite " + (currentSide - 1)).build();
                new LoreBuilder(leftArrow)
                        .addSpace()
                        .addLoreLine("§7Gehe auf die vorherige §9Seite.")
                        .addSpace();
                lastSide = false;
            }

            inventory.setItem(((side.getSideMax() - 8)) - minusSlot, leftArrow);
        }


        return inventory;
    }

}
