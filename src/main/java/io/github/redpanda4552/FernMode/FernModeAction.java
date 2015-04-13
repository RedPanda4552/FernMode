package io.github.redpanda4552.FernMode;

import io.github.redpanda4552.FernMode.util.ExitReason;
import io.github.redpanda4552.FernMode.util.Finder;
import io.github.redpanda4552.FernMode.util.Pair;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;


/**
 * Contains routines for putting players in and out of Fern Mode.
 */
public class FernModeAction {
	
	private FernModeMain main;
	private Finder finder;

	public FernModeAction(FernModeMain main, Finder finder) {
		this.main = main;
		this.finder = finder;
	}
	
	
	/**
	 * Put the specified player into Fern Mode.
	 * @param p - The Player to put into Fern Mode.
	 */
	@SuppressWarnings("deprecation")
	public void enter(Player p, boolean silent) {
		//Local variables wut wut
		World world = p.getWorld();
		//Using the floored values instead of p.getLocation()
		Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
		Block fern = world.getBlockAt(loc);
		//If in water or lava
		if (fern.isLiquid()) {
			main.sendMessage(p, "You cannot enter Fern Mode in water or lava!");
			return;
		}		
		//If standing in a block
		if (fern.getType() != Material.AIR) {
			main.sendMessage(p, "You cannot enter Fern Mode while standing in transparent blocks!");
			return;
		}		
		//Check if the block beneath the fern is a valid spot
		if (p.isFlying() || fern.getRelative(BlockFace.DOWN).isLiquid() || fern.getRelative(BlockFace.DOWN).getType().isTransparent() == true) {
			main.sendMessage(p, "You must be standing on solid ground to enter Fern Mode!");
			return;
		}
		
		//Create pair
		Pair pair = new Pair(p, fern);
		
		//Add to array
		main.pairArray.add(pair);
		
		//Vanish
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			pl.hidePlayer(p);
		}
		
		//Set block data
		fern.setType(Material.LONG_GRASS);
		fern.setData((byte) 2);
			
		//Message
		if (silent == false) {
			p.chat("I am a fern.");
		} else {
			main.sendMessage(p, "Silently entered Fern Mode.");
		}
	}
	
	/**
	 * Take a player out of Fern Mode.
	 * @param p - The player to remove from Fern Mode.
	 * @param reason - The reason they are being removed.
	 */
	public void exit(Player p, ExitReason reason) {
		//Local variables wut wut
		World world = p.getWorld();
		Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
		
		//Message
		main.sendMessage(p, reason.getString());
				
		//Unvanish
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			pl.showPlayer(p);			
		}
		
		//Set block to air
		world.getBlockAt(loc).setType(Material.AIR);
		
		//Remove pair from array
		main.pairArray.remove(finder.getPairFromPlayer(p));
	}
}	
