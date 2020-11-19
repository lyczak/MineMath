package net.lyczak.MineMath;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ToolFactory {
    public static ItemStack getWand() {
        ItemStack wand = new ItemStack(Material.GOLDEN_HOE, 1);
        ItemMeta wandMeta = wand.getItemMeta();
        wandMeta.setDisplayName(String.format("%s%sMineMath Wand", ChatColor.BOLD, ChatColor.GOLD));
        wand.setItemMeta(wandMeta);
        return wand;
    }
}
