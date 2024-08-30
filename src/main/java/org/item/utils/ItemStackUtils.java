package org.item.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;

import java.util.function.UnaryOperator;

public class ItemStackUtils {
	public static Component toComponentwithHoverEvent(ItemStack item) {
		TextColor color;
		if (item.hasItemMeta() && item.getItemMeta().hasRarity())
            if (item.getItemMeta().getRarity().equals(ItemRarity.COMMON) || item.getItemMeta().getRarity().equals(ItemRarity.UNCOMMON))
                color = item.getItemMeta().hasEnchants() ? NamedTextColor.AQUA : item.getItemMeta().getRarity().color();
            else color = item.getItemMeta().getRarity().color();
        else color = NamedTextColor.WHITE;
		Component itemName = item.hasItemMeta() && item.getItemMeta().hasDisplayName() ? item.getItemMeta().displayName() : Component.translatable(item.getType().translationKey()).color(color);
        return Component.empty().append(Component.text("[").color(color)).append(itemName.hoverEvent(Bukkit.getItemFactory().asHoverEvent(item, UnaryOperator.identity()))).append(Component.text("]").color(color));
	}
}
