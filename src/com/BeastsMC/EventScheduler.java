package com.BeastsMC;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class EventScheduler extends JavaPlugin {
	public Logger log;
	public ScheduledEvent event = null;
	
	public void onEnable() {
		log = getLogger();
		getCommand("event").setExecutor(this);
		getCommand("cancelevent").setExecutor(this);
	}
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if(cmd.equalsIgnoreCase("event")) {
			if(event==null) {
				if(args.length < 2) {
					return false;
				}
				String eventName = "";
				for(int i=0; i < args.length-1; i++) {
					eventName += args[i] + " ";
				}
				if(eventName.length()>16) {
					sender.sendMessage(ChatColor.RED + "Sorry, Minecraft scoreboards have a limit of 16 characters '" +eventName+"' was too long.");
				}
				Integer seconds = timeToSeconds(args[args.length-1]);
				if(seconds==null) {
					sender.sendMessage(ChatColor.RED + "Unit for time is invalid, must be s, m, or h");
					return false;
				}
				event = new ScheduledEvent(eventName, seconds, this);
				sender.sendMessage(ChatColor.AQUA + "Scheduled event '" + eventName + "' to occur in " + seconds + " seconds");
			} else {
				sender.sendMessage(ChatColor.RED + "There is already a scheduled event.");
			}
		} else if(cmd.equalsIgnoreCase("cancelevent")) {
			if(event!=null) {
				event.destroy();
				event = null;
				sender.sendMessage(ChatColor.AQUA + "Canceled scheduled event.");
			} else {
				sender.sendMessage(ChatColor.RED + "There are no scheduled events.");
			}
		}
		return true;
	}
	private Integer timeToSeconds(String time) {
		String unit = time.substring(time.length() - 1);
		Integer rawTimeValue = Integer.parseInt(time.substring(0, time.length() - 1));
		Integer intervalSeconds;
		if(unit.equalsIgnoreCase("s")) {
			intervalSeconds = rawTimeValue;
		} else if(unit.equalsIgnoreCase("m")) {
			intervalSeconds = rawTimeValue*60;
		} else if(unit.equalsIgnoreCase("h")) {
			intervalSeconds = rawTimeValue*3600;
		} else {
			return null;
		}
		return intervalSeconds;
	}
}
