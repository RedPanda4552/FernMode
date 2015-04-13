package io.github.redpanda4552.FernMode;

import io.github.redpanda4552.FernMode.util.Pair;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 * Primary CommandExecutor.
 */
public class FernModeCommand implements CommandExecutor {

	private FernModeMain main;
	private FernModeAction action;
	
	private String[] help = {"To enter Fern Mode, use '/fernmode'.", "To enter Fern Mode without broadcasting it in chat, use '/fernmode silent'.", "To exit Fern Mode, simply move somewhere.", "Your fern is not invincible! Others can break them, and if it burns away, you will be exposed.", "You cannot enter Fern Mode if you are already inside a transparent block (tall grass, flowers, etc.), or in fluids."};
	
	public FernModeCommand(FernModeMain main, FernModeAction action) {
		this.action = action;
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if (p.hasPermission("FernMode.use")) {
				
				if (commandLabel.equalsIgnoreCase("fernmode")) {
					
					if (args.length == 1) {
						
						if (args[0].equalsIgnoreCase("help")) {
							main.sendMessageArray(p, help);
							return true;
						}
						
						if (args[0].equalsIgnoreCase("silent")) {
							
							if (p.hasPermission("FernMode.silent")) {
								
								for (Pair pair : main.pairArray) {
									
									if (pair.getPlayer() == p) {
										main.sendMessage(p, "You are already in a fern!");
										return true;
									}
								}
								action.enter(p, true);
								return true;
							} else {
								main.sendMessage(p, "You do not have permission to enter Fern Mode silently!");
							}
							return true;
						}
					}
					
					for (Pair pair : main.pairArray) {
						
						if (pair.getPlayer() == p) {
							main.sendMessage(p, "You are already in a fern!");
							return true;
						}
					}
					action.enter(p, false);
					return true;
				}
			} else {
				main.sendMessage(p, "You do not have permission to enter Fern Mode!");
				return true;
			}
		} else {
			//This one is special, because it isn't a player object.
			sender.sendMessage(ChatColor.GOLD + "[FernMode]" + ChatColor.AQUA + "You cannot use FernMode in console!");
			return true;
		}
		return false;
	}
}
