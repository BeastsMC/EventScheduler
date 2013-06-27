package com.BeastsMC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScheduledEvent extends BukkitRunnable {
	private final EventScheduler plugin;
	private final String name;
	private Integer seconds;
	private final Objective objective;
	private final Scoreboard board;
	public ScheduledEvent(String eventName, Integer secondsUntil, EventScheduler main) {
		name = eventName;
		seconds = secondsUntil;
		plugin = main;
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		objective = board.registerNewObjective("eventtime", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(name);
		for(Player online : Bukkit.getOnlinePlayers()) {
			online.setScoreboard(board);
		}
		this.runTaskTimer(plugin, 20, 20);
	}
	@Override
	public void run() {
		seconds--;
		if(seconds==0) {
			plugin.log.info("Event '" + name + "' scheduled to happen now!");
			plugin.getServer().broadcastMessage(ChatColor.AQUA + name + " is happening now!");
			this.destroy();
			plugin.event = null;
			return;
		}
		this.updateDisplays();
	}
	private void updateDisplays() {
		OfflinePlayer eventLine;
		Score score;
		if(seconds==59) {
			eventLine = Bukkit.getOfflinePlayer(ChatColor.RED + "Minutes:");
			board.resetScores(eventLine);
		}
		if(seconds>59) {
			eventLine = Bukkit.getOfflinePlayer(ChatColor.RED + "Minutes:");
			Integer minutesToRestart = seconds/60;
			score = objective.getScore(eventLine);
			score.setScore(minutesToRestart);

		} else {
			eventLine = Bukkit.getOfflinePlayer(ChatColor.RED + "Seconds:");
			score = objective.getScore(eventLine);
			score.setScore(seconds);
		}
	}
	public void destroy() {
		this.cancel();
		objective.unregister();
	}
	public Scoreboard getScoreboard() {
		return board;
	}
	public String getFormatedTime() {
		if(seconds<60) {
			return seconds + " seconds";
		} else if(seconds<3600) {
			return (double)Math.round(seconds/60.0 * 100) / 100 + " minutes";
		} else {
			return (double)Math.round(seconds/3600.0 * 100) / 100 + " hours";
		}
	}
}
