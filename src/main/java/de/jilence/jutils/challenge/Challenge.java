package de.jilence.jutils.challenge;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class Challenge {

    public abstract void onEnable();

    public abstract void onStart();

    public abstract void onPause();

    public abstract void onDisable();

    public abstract void onTick();

    public abstract Inventory getSettingsInventory();

    public abstract ItemStack getDisplayItem();

    public abstract String getInventoryName();

    public abstract void onInventoryClick(Player player, ClickType clickType, Material material, Inventory inventory);

}