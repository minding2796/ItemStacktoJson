package org.item;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.item.utils.ItemStackUtils;

public final class ItemJsonhoverEvent extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		// Plugin startup logic
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("ItemJsonhoverEvent Enabled");
	}
	
	@Override
	public void onDisable() {
		// Plugin shutdown logic
		getLogger().info("ItemJsonhoverEvent Disabled");
	}
	
	@EventHandler
	public void onChat(AsyncChatEvent e) {
		Player p = e.getPlayer();
		Component item = ItemStackUtils.toComponentwithHoverEvent(p.getInventory().getItemInMainHand());
		e.message(e.message().replaceText(builder -> builder.matchLiteral("[item]").replacement(item)));
	}
}
