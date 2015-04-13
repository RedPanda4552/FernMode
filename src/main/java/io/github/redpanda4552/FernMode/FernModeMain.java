package io.github.redpanda4552.FernMode;

import io.github.redpanda4552.FernMode.util.ExitReason;
import io.github.redpanda4552.FernMode.util.Finder;
import io.github.redpanda4552.FernMode.util.Pair;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class FernModeMain extends JavaPlugin {
	
	protected Logger log;
	
	private Finder finder;
	private FernModeAction action;
	
	/**
	 * ArrayList containing every active pair in FernMode
	 */
	public ArrayList<Pair> pairArray = new ArrayList<Pair>();
	
	public void onEnable() {
		this.log = this.getLogger();
		
		finder = new Finder(this);
		action = new FernModeAction(this, finder);
		
		getCommand("fernmode").setExecutor(new FernModeCommand(this, action));

		getServer().getPluginManager().registerEvents(new FernModeEventHandler(action, finder), this);
	}
	
	public void onDisable() {
		for (Pair pair : this.pairArray) {
			action.exit(pair.getPlayer(), ExitReason.PLUGIN_SHUTDOWN);
		}
		//To avoid phantom pairs
		this.pairArray.clear();
	}
	
	/**
	 * Sends a single message to a player with the [FernMode] prefix.
	 * @param player - The player to send the message to.
	 * @param message - The message to send.
	 */
	public void sendMessage(Player player, String message) {
		player.sendMessage(ChatColor.GOLD + "[FernMode] " + ChatColor.AQUA + message);
	}
	
	/**
	 * Sends a String array of messages to a player with the [FernMode] prefix.
	 * @param player - The player to send the message to.
	 * @param messages - The String array to send.
	 */
	public void sendMessageArray(Player player, String[] messages) {
		for (String message : messages) {
			this.sendMessage(player, message);
		}
	}
}
