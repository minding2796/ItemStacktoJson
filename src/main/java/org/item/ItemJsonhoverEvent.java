package org.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.item.utils.ItemStackUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	public void onChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		String thisformat = e.getFormat();
		thisformat = thisformat.replace("%1$s", p.getName());
		thisformat = thisformat.replace("%2$s", e.getMessage());
		if (e.getMessage().contains("[item]")) {
			Component item = ItemStackUtils.toComponentwithHoverEvent(p.getInventory().getItemInMainHand());
			List<Integer> replacetime = new ArrayList<>();
			e.setCancelled(true);
			for (int i = 0; i < thisformat.length(); i++) {
				try {
					if (thisformat.substring(i, i + 6).equalsIgnoreCase("[item]")) {
						replacetime.add(i);
					}
				} catch (StringIndexOutOfBoundsException exception) {
					break;
				}
			}
			Component replacermessage = Component.empty();
			int onei = 0;
			for (Integer i : replacetime) {
				String checkcolor = thisformat.substring(onei, i);
				if(checkcolor.contains("§")) {
					if (checkcolor.contains("§#")) {
						String[] cc = checkcolor.split("§#");
						java.awt.Color prevcolor = null;
						List<TextDecoration> prevdec = new ArrayList<>();
						if(cc[0].contains("§")) {
							String[] cc1 = cc[0].split("§");
							replacermessage = replacermessage.append(Component.text(cc1[0])).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
							for (int index1 = 1; index1 < cc1.length; index1++) {
								java.awt.Color color = Objects.requireNonNullElse(ChatColor.getByChar(cc1[index1].charAt(0)), ChatColor.RESET).getColor();
								if (color == null) {
									color = Objects.requireNonNullElseGet(prevcolor, () -> new java.awt.Color(255, 255, 255));
								}
								prevcolor = color;
								String utilname = Objects.requireNonNullElse(ChatColor.getByChar(cc1[index1].charAt(0)), ChatColor.RESET).name();
								TextDecoration deco = null;
								try {
									deco = TextDecoration.valueOf(utilname);
								} catch (IllegalArgumentException ignored) {
								}
								if (deco != null) {
									if(!prevdec.contains(deco)) {
										prevdec.add(deco);
									}
								}
								if (utilname.equalsIgnoreCase("RESET")) {
									prevdec.clear();
									color = new java.awt.Color(255, 255, 255);
									prevcolor = color;
								}
								if(prevdec.size() == 0) {
									replacermessage = replacermessage.append(Component.text(cc1[index1].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()))).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
								} else {
									Component text = Component.text(cc1[index1].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
									for (TextDecoration decos : prevdec) {
										text = text.decorate(decos);
									}
									replacermessage = replacermessage.append(text).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
								}
							}
						} else {
							replacermessage = replacermessage.append(Component.text(cc[0])).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
						}
						for (int index = 1; index < cc.length; index++) {
							if(cc[index].contains("§")) {
								String[] cc1 = cc[index].split("§");
								prevcolor = new java.awt.Color(Integer.parseInt(cc1[0].substring(0, 2), 16), Integer.parseInt(cc1[0].substring(2, 4), 16), Integer.parseInt(cc1[0].substring(4, 6), 16));
								replacermessage = replacermessage.append(Component.text(cc1[0].substring(6))).color(TextColor.color(prevcolor.getRed(), prevcolor.getGreen(), prevcolor.getBlue())).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
								for (int index1 = 1; index1 < cc1.length; index1++) {
									java.awt.Color color = Objects.requireNonNullElse(ChatColor.getByChar(cc1[index1].charAt(0)), ChatColor.RESET).getColor();
									if (color == null) {
										color = Objects.requireNonNullElseGet(prevcolor, () -> new java.awt.Color(255, 255, 255));
									}
									prevcolor = color;
									String utilname = Objects.requireNonNullElse(ChatColor.getByChar(cc1[index1].charAt(0)), ChatColor.RESET).name();
									TextDecoration deco = null;
									try {
										deco = TextDecoration.valueOf(utilname);
									} catch (IllegalArgumentException ignored) {
									}
									if (deco != null) {
										if(!prevdec.contains(deco)) {
											prevdec.add(deco);
										}
									}
									if (utilname.equalsIgnoreCase("RESET")) {
										prevdec.clear();
										color = new java.awt.Color(255, 255, 255);
										prevcolor = color;
									}
									if(prevdec.size() == 0) {
										replacermessage = replacermessage.append(Component.text(cc1[index1].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()))).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
									} else {
										Component text = Component.text(cc1[index1].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
										for (TextDecoration decos : prevdec) {
											text = text.decorate(decos);
										}
										replacermessage = replacermessage.append(text).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
									}
								}
							} else {
								prevcolor = new java.awt.Color(Integer.parseInt(cc[index].substring(0, 2), 16), Integer.parseInt(cc[index].substring(2, 4), 16), Integer.parseInt(cc[index].substring(4, 6), 16));
								if(prevdec.size() == 0) {
									replacermessage = replacermessage.append(Component.text(cc[index].substring(6)).color(TextColor.color(prevcolor.getRed(), prevcolor.getGreen(), prevcolor.getBlue()))).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
								} else {
									Component text = Component.text(cc[index].substring(6)).color(TextColor.color(prevcolor.getRed(), prevcolor.getGreen(), prevcolor.getBlue()));
									for (TextDecoration decos : prevdec) {
										text = text.decorate(decos);
									}
									replacermessage = replacermessage.append(text).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
								}
							}
						}
					} else {
						String[] cc = checkcolor.split("§");
						replacermessage = replacermessage.append(Component.text(cc[0])).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
						java.awt.Color prevcolor = null;
						List<TextDecoration> prevdec = new ArrayList<>();
						for (int index = 1; index < cc.length; index++) {
							java.awt.Color color = Objects.requireNonNullElse(ChatColor.getByChar(cc[index].charAt(0)), ChatColor.RESET).getColor();
							if (color == null) {
								color = Objects.requireNonNullElseGet(prevcolor, () -> new java.awt.Color(255, 255, 255));
							}
							prevcolor = color;
							String utilname = Objects.requireNonNullElse(ChatColor.getByChar(cc[index].charAt(0)), ChatColor.RESET).name();
							TextDecoration deco = null;
							try {
								deco = TextDecoration.valueOf(utilname);
							} catch (IllegalArgumentException ignored) {
							}
							if (deco != null) {
								if(!prevdec.contains(deco)) {
									prevdec.add(deco);
								}
							}
							if (utilname.equalsIgnoreCase("RESET")) {
								prevdec.clear();
								color = new java.awt.Color(255, 255, 255);
								prevcolor = color;
							}
							if(prevdec.size() == 0) {
								replacermessage = replacermessage.append(Component.text(cc[index].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()))).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
							} else {
								Component text = Component.text(cc[index].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
								for (TextDecoration decos : prevdec) {
									text = text.decorate(decos);
								}
								replacermessage = replacermessage.append(text).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
							}
						}
					}
				} else {
					replacermessage = replacermessage.append(Component.text(checkcolor)).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
				}
				replacermessage = replacermessage.append(item);
				onei = i + 6;
			}
			String colorchecker = thisformat.substring(onei);
			if(colorchecker.contains("§")) {
				if(colorchecker.contains("§#")) {
					String[] cc = colorchecker.split("§#");
					java.awt.Color prevcolor = null;
					List<TextDecoration> prevdec = new ArrayList<>();
					if(cc[0].contains("§")) {
						String[] cc1 = cc[0].split("§");
						replacermessage = replacermessage.append(Component.text(cc1[0])).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
						for (int index1 = 1; index1 < cc1.length; index1++) {
							java.awt.Color color = Objects.requireNonNullElse(ChatColor.getByChar(cc1[index1].charAt(0)), ChatColor.RESET).getColor();
							if (color == null) {
								color = Objects.requireNonNullElseGet(prevcolor, () -> new java.awt.Color(255, 255, 255));
							}
							prevcolor = color;
							String utilname = Objects.requireNonNullElse(ChatColor.getByChar(cc1[index1].charAt(0)), ChatColor.RESET).name();
							TextDecoration deco = null;
							try {
								deco = TextDecoration.valueOf(utilname);
							} catch (IllegalArgumentException ignored) {
							}
							if (deco != null) {
								if(!prevdec.contains(deco)) {
									prevdec.add(deco);
								}
							}
							if (utilname.equalsIgnoreCase("RESET")) {
								prevdec.clear();
								color = new java.awt.Color(255, 255, 255);
								prevcolor = color;
							}
							if(prevdec.size() == 0) {
								replacermessage = replacermessage.append(Component.text(cc1[index1].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()))).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
							} else {
								Component text = Component.text(cc1[index1].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
								for (TextDecoration decos : prevdec) {
									text = text.decorate(decos);
								}
								replacermessage = replacermessage.append(text).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
							}
						}
					} else {
						replacermessage = replacermessage.append(Component.text(cc[0])).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
					}
					for (int index = 1; index < cc.length; index++) {
						if(cc[index].contains("§")) {
							String[] cc1 = cc[index].split("§");
							prevcolor = new java.awt.Color(Integer.parseInt(cc1[0].substring(0, 2), 16), Integer.parseInt(cc1[0].substring(2, 4), 16), Integer.parseInt(cc1[0].substring(4, 6), 16));
							replacermessage = replacermessage.append(Component.text(cc1[0].substring(6))).color(TextColor.color(prevcolor.getRed(), prevcolor.getGreen(), prevcolor.getBlue())).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
							for (int index1 = 1; index1 < cc1.length; index1++) {
								java.awt.Color color = Objects.requireNonNullElse(ChatColor.getByChar(cc1[index1].charAt(0)), ChatColor.RESET).getColor();
								if (color == null) {
									color = Objects.requireNonNullElseGet(prevcolor, () -> new java.awt.Color(255, 255, 255));
								}
								prevcolor = color;
								String utilname = Objects.requireNonNullElse(ChatColor.getByChar(cc1[index1].charAt(0)), ChatColor.RESET).name();
								TextDecoration deco = null;
								try {
									deco = TextDecoration.valueOf(utilname);
								} catch (IllegalArgumentException ignored) {
								}
								if (deco != null) {
									if(!prevdec.contains(deco)) {
										prevdec.add(deco);
									}
								}
								if (utilname.equalsIgnoreCase("RESET")) {
									prevdec.clear();
									color = new java.awt.Color(255, 255, 255);
									prevcolor = color;
								}
								if(prevdec.size() == 0) {
									replacermessage = replacermessage.append(Component.text(cc1[index1].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()))).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
								} else {
									Component text = Component.text(cc1[index1].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
									for (TextDecoration decos : prevdec) {
										text = text.decorate(decos);
									}
									replacermessage = replacermessage.append(text).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
								}
							}
						} else {
							prevcolor = new java.awt.Color(Integer.parseInt(cc[index].substring(0, 2), 16), Integer.parseInt(cc[index].substring(2, 4), 16), Integer.parseInt(cc[index].substring(4, 6), 16));
							if(prevdec.size() == 0) {
								replacermessage = replacermessage.append(Component.text(cc[index].substring(6)).color(TextColor.color(prevcolor.getRed(), prevcolor.getGreen(), prevcolor.getBlue()))).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
							} else {
								Component text = Component.text(cc[index].substring(6)).color(TextColor.color(prevcolor.getRed(), prevcolor.getGreen(), prevcolor.getBlue()));
								for (TextDecoration decos : prevdec) {
									text = text.decorate(decos);
								}
								replacermessage = replacermessage.append(text).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
							}
						}
					}
				} else {
					String[] cc = colorchecker.split("§");
					replacermessage = replacermessage.append(Component.text(cc[0])).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
					java.awt.Color prevcolor = null;
					List<TextDecoration> prevdec = new ArrayList<>();
					for (int index = 1; index < cc.length; index++) {
						java.awt.Color color = Objects.requireNonNullElse(ChatColor.getByChar(cc[index].charAt(0)), ChatColor.RESET).getColor();
						if (color == null) {
							color = Objects.requireNonNullElseGet(prevcolor, () -> new java.awt.Color(255, 255, 255));
						}
						prevcolor = color;
						String utilname = Objects.requireNonNullElse(ChatColor.getByChar(cc[index].charAt(0)), ChatColor.RESET).name();
						TextDecoration deco = null;
						try {
							deco = TextDecoration.valueOf(utilname);
						} catch (IllegalArgumentException ignored) {
						}
						if (deco != null) {
							if(!prevdec.contains(deco)) {
								prevdec.add(deco);
							}
						}
						if (utilname.equalsIgnoreCase("RESET")) {
							prevdec.clear();
							color = new java.awt.Color(255, 255, 255);
							prevcolor = color;
						}
						if(prevdec.size() == 0) {
							replacermessage = replacermessage.append(Component.text(cc[index].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()))).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
						} else {
							Component text = Component.text(cc[index].substring(1)).color(TextColor.color(color.getRed(), color.getGreen(), color.getBlue()));
							for (TextDecoration decos : prevdec) {
								text = text.decorate(decos);
							}
							replacermessage = replacermessage.append(text).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
						}
					}
				}
			} else {
				replacermessage = replacermessage.append(Component.text(colorchecker)).hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(Component.empty()));
			}
			Bukkit.broadcast(replacermessage);
		}
	}
}
