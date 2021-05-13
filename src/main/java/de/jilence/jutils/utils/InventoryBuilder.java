package de.jilence.jutils.utils;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder {

    public static void fillInventory(Inventory inventory, ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, itemStack);
        }
    }

    public static int inventoryClickIntManager(int time, ClickType clickType, int min, int max, int plus, int minus, int shiftPlus, int shiftMinus) {

        int finalTime;

        switch (clickType) {

            case LEFT:
                finalTime = time + plus;
                break;
            case RIGHT:
                finalTime = time - minus;
                break;
            case SHIFT_LEFT:
                finalTime = time + shiftPlus;
                break;
            case SHIFT_RIGHT:
                finalTime = time - shiftMinus;
                break;
            default:
                return time;
        }

        if (finalTime < min || finalTime > max) {
            return time;
        }
        return finalTime;
    }

    public static int inventoryClickIntManagerWithoutShift(int time, ClickType clickType, int min, int max, int plus, int minus) {

        int finalTime;

        switch (clickType) {

            case LEFT:
                finalTime = time + plus;
                break;
            case RIGHT:
                finalTime = time - minus;
                break;
            default:
                return time;
        }
        if (finalTime < min || finalTime > max) {
            return time;
        }
        return finalTime;
    }
}