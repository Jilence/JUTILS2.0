package de.jilence.jutils.utils;

import org.bukkit.inventory.ItemStack;

public class LoreBuilder {

    private final ItemStack itemStack;

    public LoreBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public LoreBuilder addLoreLine(String lore) {
        new ItemBuilder(itemStack).addLoreLine(lore);
        return this;
    }

    public LoreBuilder addBoolSettings(boolean enabled, String message) {
        new ItemBuilder(itemStack).addLoreLine(message + ": " + (enabled ? "§2aktiviert" : "§4deaktiviert"));
        return this;
    }

    public LoreBuilder addBoolSettingsWithDot(boolean enabled, String message) {
        new ItemBuilder(itemStack).addLoreLine("§8● " + message + ": " + (enabled ? "§2aktiviert" : "§4deaktiviert"));
        return this;
    }

    public LoreBuilder addDescription(DESCRIPTIONS descriptions) {
        new ItemBuilder(itemStack).addLoreLine(descriptions.getMessage());
        return this;
    }

    public LoreBuilder addSpace() {
        new ItemBuilder(itemStack).addLoreLine("");
        return this;
    }

    public LoreBuilder addParagraph(String message) {
        new ItemBuilder(itemStack).addLoreLine("§8● " + message);
        return this;
    }

    public enum MESSAGES {

        RIGHTCLICK("§8[§7Rechtsklick§8]"),
        LEFTCLICK("§8[§7Linksklick§8]"),
        SHIFT_LEFTCLICK("§8[§7Shift + Linksklick§8]"),
        SHIFT_RIGHTCLICK("§8[§7Shift + Linksklick§8]"),
        UNDER_CONSTRUCTION("§c§lUNDER CONSTRUCTION");

        String message;

        MESSAGES(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum DESCRIPTIONS {

        RIGHT_LEFT_CLICK("§8[§7Linksklick§8] §7Challenge aktivieren/deaktivieren", "§8[§7Rechtsklick§8] §7Einstellungen zur Herausforderung"),
        SETTINGS("§9Einstellungen:"),
        HEADER("§9Herausforderung-Erklärung: "),
        RIGHT_LEFT_ON_OFF_CLICK("§8[§7Linksklick§8] §2Aktivieren", "§8[§7Rechtsklick§8] §4Deaktivieren");


        String[] message;

        DESCRIPTIONS(String... message) {
            this.message = message;
        }

        public String[] getMessage() {
            return message;
        }
    }

}
