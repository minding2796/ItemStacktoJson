package org.item.utils;

import io.papermc.paper.inventory.ItemRarity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

public class ItemStackUtils {
	public static Component toComponentwithHoverEvent(ItemStack item) {
		Component itemcomponent;
		if(item.getType().getItemRarity().equals(ItemRarity.COMMON) || item.getType().getItemRarity().equals(ItemRarity.UNCOMMON)) {
			if (item.hasItemMeta()) {
				if(item.getItemMeta().hasEnchants()) {
					if (item.getItemMeta().hasDisplayName()) {
						itemcomponent = Component.text("[").color(NamedTextColor.AQUA).append(item.getItemMeta().displayName().color(NamedTextColor.AQUA)).hoverEvent(HoverEvent.showItem(Key.key(Key.MINECRAFT_NAMESPACE, item.getType().toString().toLowerCase()), 1, BinaryTagHolder.binaryTagHolder(item.getItemMeta().getAsString()))).append(Component.text("]")).color(NamedTextColor.AQUA);
					} else {
						itemcomponent = Component.text("[").color(NamedTextColor.AQUA).append(Component.translatable(item.getType().translationKey())).color(NamedTextColor.AQUA).hoverEvent(HoverEvent.showItem(Key.key(Key.MINECRAFT_NAMESPACE, item.getType().toString().toLowerCase()), 1, BinaryTagHolder.binaryTagHolder(item.getItemMeta().getAsString()))).append(Component.text("]")).color(NamedTextColor.AQUA);
					}
				} else {
					if (item.getItemMeta().hasDisplayName()) {
						itemcomponent = Component.text("[").color(item.getType().getItemRarity().getColor()).append(item.getItemMeta().displayName().color(item.getType().getItemRarity().getColor())).hoverEvent(HoverEvent.showItem(Key.key(Key.MINECRAFT_NAMESPACE, item.getType().toString().toLowerCase()), 1, BinaryTagHolder.binaryTagHolder(item.getItemMeta().getAsString()))).append(Component.text("]")).color(item.getType().getItemRarity().getColor());
					} else {
						itemcomponent = Component.text("[").color(item.getType().getItemRarity().getColor()).append(Component.translatable(item.getType().translationKey())).color(item.getType().getItemRarity().getColor()).hoverEvent(HoverEvent.showItem(Key.key(Key.MINECRAFT_NAMESPACE, item.getType().toString().toLowerCase()), 1, BinaryTagHolder.binaryTagHolder(item.getItemMeta().getAsString()))).append(Component.text("]")).color(item.getType().getItemRarity().getColor());
					}
				}
			} else {
				itemcomponent = Component.text("[").color(item.getType().getItemRarity().getColor()).append(Component.translatable(item.getType().translationKey())).color(item.getType().getItemRarity().getColor()).hoverEvent(HoverEvent.showItem(Key.key(Key.MINECRAFT_NAMESPACE, item.getType().toString().toLowerCase()), 1)).append(Component.text("]")).color(item.getType().getItemRarity().getColor());
			}
		} else {
			if (item.hasItemMeta()) {
				if (item.getItemMeta().hasDisplayName()) {
					itemcomponent = Component.text("[").color(item.getType().getItemRarity().getColor()).append(item.getItemMeta().displayName().color(item.getType().getItemRarity().getColor())).hoverEvent(HoverEvent.showItem(Key.key(Key.MINECRAFT_NAMESPACE, item.getType().toString().toLowerCase()), 1, BinaryTagHolder.binaryTagHolder(item.getItemMeta().getAsString()))).append(Component.text("]")).color(item.getType().getItemRarity().getColor());
				} else {
					itemcomponent = Component.text("[").color(item.getType().getItemRarity().getColor()).append(Component.translatable(item.getType().translationKey())).color(item.getType().getItemRarity().getColor()).hoverEvent(HoverEvent.showItem(Key.key(Key.MINECRAFT_NAMESPACE, item.getType().toString().toLowerCase()), 1, BinaryTagHolder.binaryTagHolder(item.getItemMeta().getAsString()))).append(Component.text("]")).color(item.getType().getItemRarity().getColor());
				}
			} else {
				itemcomponent = Component.text("[").color(item.getType().getItemRarity().getColor()).append(Component.translatable(item.getType().translationKey())).color(item.getType().getItemRarity().getColor()).hoverEvent(HoverEvent.showItem(Key.key(Key.MINECRAFT_NAMESPACE, item.getType().toString().toLowerCase()), 1)).append(Component.text("]")).color(item.getType().getItemRarity().getColor());
			}
		}
		return itemcomponent;
	}
}
