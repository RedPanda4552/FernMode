package io.github.redpanda4552.FernMode.util;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * A pair containing a FernPlayer and Block. Used to quickly grab information on a player in FernMode.
 */
public class Pair {
	
	/**
	 * The player in the pair.
	 */
	private Player player;
	
	/**
	 * The block in the pair.
	 */
	private Block block;	
	
	public Pair(Player player, Block block) {
		this.setBlock(block);
		this.setPlayer(player);
	}
	
	/**
	 * Gets the FernPlayer in this pair.
	 * @return The FernPlayer in the pair, or null if no FernPlayer.
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Gets the block in this pair.
	 * @return The block in the pair, or null if no block.
	 */
	public Block getBlock() {
		return this.block;
	}
	
	/**
	 * Sets the FernPlayer in this pair.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Sets the block in this pair.
	 */
	public void setBlock(Block block) {
		this.block = block;
	}
}
