package io.github.redpanda4552.FernMode.util;

import io.github.redpanda4552.FernMode.FernModeMain;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;


/**
 * Contains routines for finding objects, given others.
 */
public class Finder {

	private FernModeMain main;
	
	public Finder(FernModeMain main) {
		this.main = main;
	}
	
	/**
	 * Cycles through all pairs searching for the given Player. If a pair contains the Player, that pair is returned.
	 * @param player - The Player to look for
	 * @param sendMessage - Whether or not to send a message if the Player is found
	 * @param message - If allowed, the message to be sent to the Player.
	 * @return The Pair containing the given Player, or null if no Pair contains the Player.
	 */
	public Pair getPairFromPlayer(Player player) {
		for (Pair pair : main.pairArray) {
			
			if (pair.getPlayer() == player) {
				return pair;
			}
		}
		return null;
	}
	
	/**
	 * Cycles through all pairs searching for the given Block. If a Pair contains the Block, that Pair is returned.
	 * @param block - The Block to look for
	 * @return The Pair containing the given Block, or null if no Pair contains the Block.
	 */
	public Pair getPairFromBlock(Block block) {
		for (Pair pair : main.pairArray) {
			
			if (pair.getBlock().equals(block)) {
				return pair;
			}
		}
		return null;
	}
}
