package com.BeastsMC;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ESLoginListener implements Listener {
	private final EventScheduler plugin;
	public ESLoginListener(EventScheduler main) {
		plugin = main;
	}
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent playerjoin) {
		if(plugin.event != null) {
			Player p = playerjoin.getPlayer();
			p.setScoreboard(plugin.event.getScoreboard());
		}
	}
}
