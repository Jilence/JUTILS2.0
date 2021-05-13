package de.jilence.jutils.inventory;

import de.jilence.jutils.timer.TimerManager;
import de.jilence.jutils.utils.InventoryBuilder;
import de.jilence.jutils.utils.ItemBuilder;
import de.jilence.jutils.utils.LoreBuilder;
import de.jilence.jutils.utils.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TimerInventory {

    public final static String INVENTORY_NAME = "§9Timer-Einstellungen §8● ";

    public static Inventory getTimerInventory() {
        Inventory inventory = Bukkit.createInventory(null, 3*9, Component.text(INVENTORY_NAME));
        InventoryBuilder.fillInventory(inventory, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname("").build());

        ItemStack timerOnOffItem = new ItemBuilder((TimerManager.isTimerEnabled() ? Material.LIME_DYE : Material.GRAY_DYE)).displayname("§9Timer §7aktivieren/deaktivieren").build();
        new LoreBuilder(timerOnOffItem)
                .addSpace()
                .addLoreLine("§7Schalte den §9Timer §7an und aus")
                .addLoreLine("§7Wenn der §9Timer §4deaktiviert§7, kann er nicht")
                .addLoreLine("§7gestartet werdet.")
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.RIGHT_LEFT_ON_OFF_CLICK)
                .addSpace()
                .addLoreLine("§7Status:")
                .addBoolSettings(TimerManager.isTimerEnabled(), "§7Timer Status")
                .addSpace();

        inventory.setItem(2+9, timerOnOffItem);

        ItemStack timerDirection = new ItemBuilder(Material.ANVIL).displayname("§9Timer §7vorwärts/rückwärts laufen lassen").build();
        new LoreBuilder(timerDirection)
                .addSpace()
                .addLoreLine("§7Lass den §9Timer §7vor oder zurück laufen,")
                .addLoreLine("§7indem du hier die §9Einstellung §7änderst.")
                .addSpace()
                .addLoreLine("§9Vorwärts:")
                .addLoreLine("§7Der §9Timer §7läuft vorwärst und die Zeit wird dadurch")
                .addLoreLine("§7größer. Funktioniert wie eine Stoppuhr.")
                .addSpace()
                .addLoreLine("§9Rückwärts:")
                .addLoreLine("§7Lass den §9Timer §7wie ein §9Countdown §7laufen.")
                .addLoreLine("§7Der Timer läuft von einer gesetzten §9Zeit §7runter.")
                .addSpace()
                .addLoreLine("§7Status:")
                .addLoreLine("§7Aktueller Status: §9" + TimerManager.getDirection())
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 Ändere den Status.")
                .addLoreLine(LoreBuilder.MESSAGES.RIGHTCLICK.getMessage() + "§7 Ändere den Status.")
                .addSpace();

        inventory.setItem(3+9, timerDirection);

        ItemStack timerSetter = new ItemBuilder(Material.CLOCK).displayname("§7Setze den §9Countdown").build();
        new LoreBuilder(timerSetter)
                .addSpace()
                .addLoreLine("§7Setze eine §9Zeit §7die runterläuft wenn der Timer")
                .addLoreLine("§9rückwärts §7läuft. Wenn nicht funktioniert diese gesetzte")
                .addLoreLine("§7Zeit §cnicht.")
                .addSpace()
                .addLoreLine("§cSteuerung:")
                .addParagraph(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + " §7+10")
                .addParagraph(LoreBuilder.MESSAGES.RIGHTCLICK.getMessage() + " §7-10")
                .addSpace()
                .addParagraph(LoreBuilder.MESSAGES.SHIFT_LEFTCLICK.getMessage() + " §7+60")
                .addParagraph(LoreBuilder.MESSAGES.SHIFT_RIGHTCLICK.getMessage() + " §7-60")
                .addSpace()
                .addLoreLine("§7Gesetze Zeit:")
                .addParagraph("§7Zeit: §6" + Messages.timer(TimerManager.getCountdownTime()))
                .addSpace();

        inventory.setItem(4+9, timerSetter);

        ItemStack timerReset = new ItemBuilder(Material.RED_CONCRETE).displayname("§7Resete den §cTimer").build();
        new LoreBuilder(timerReset)
                .addSpace()
                .addLoreLine("§7Setzte den Timer §czurück")
                .addLoreLine("§7Der §9Timer §7wird wieder auf 00:00:00 gestellt,")
                .addLoreLine("§7und pausiert")
                .addSpace()
                .addLoreLine("§cVorsicht:")
                .addParagraph("§7Diese Funktion kann §cnicht §7zurückgestellt werden.")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 Schalte die Einstellung an/aus")
                .addSpace();

        inventory.setItem(5+9, timerReset);

        ItemStack timerCredits = new ItemBuilder(Material.PAPER).displayname("§7Timer §9Credits").build();
        new LoreBuilder(timerCredits)
                .addSpace()
                .addLoreLine("§7Dieser Timer wurde von §cJilence programmiert")
                .addLoreLine("§7Und nur für dieses Plugin verwendet.")
                .addSpace();

        inventory.setItem(6+9, timerCredits);


        return inventory;

    }

}
