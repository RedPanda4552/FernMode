package io.github.redpanda4552.FernMode;

import io.github.redpanda4552.FernMode.util.ExitReason;
import io.github.redpanda4552.FernMode.util.Finder;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;


/**
 * Primary Event Listener.
 */
public class FernModeEventHandler implements Listener {
	
	private FernModeAction action;
	private Finder finder;
	
	public FernModeEventHandler(FernModeAction action, Finder finder) {
		this.action = action;
		this.finder = finder;
	}
	
	/**
	 * When a player leaves the server.
	 */
	@EventHandler(ignoreCancelled = true)
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		//If this player isn't in a pair
		if (finder.getPairFromPlayer(player) == null) {
			return;
		}
		action.exit(player, ExitReason.DISCONNECT);
	}
	
	/**
	 * When a player in fern mode moves
	 */
	@EventHandler(ignoreCancelled = true)
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location to = event.getTo();
		Location from = event.getFrom();
		//If the player's pitch, yaw, or world changes. Checks world to avoid failing, in the case of a surgically precise teleport to another world.
		if (to.getX() == from.getX() && to.getY() == from.getY() && to.getZ() == from.getZ() && to.getWorld() == from.getWorld()) {
			return;
		}
		//If no pair belongs to this Player
		if (finder.getPairFromPlayer(player) == null) {
			return;
		}
		action.exit(event.getPlayer(), ExitReason.MOVEMENT);
	}
	
	/**
	 * When a player breaks a fern mode fern
	 */
	@EventHandler(ignoreCancelled = true)
	@SuppressWarnings("deprecation")
	public void onFernHit(BlockBreakEvent event) {
		Block blockHit = event.getBlock();
		//If not a fern
		if (blockHit.getType() != Material.LONG_GRASS || blockHit.getData() != 2) {
			return;
		}
		//If a pair does not exist at the event location
		if (finder.getPairFromBlock(blockHit) == null) {
			return;
		}
		//If the player broke their own fern
		if (event.getPlayer() == finder.getPairFromBlock(blockHit).getPlayer()) {
			action.exit(finder.getPairFromBlock(blockHit).getPlayer(), ExitReason.BREAK_SELF);
			return;
		}
		action.exit(finder.getPairFromBlock(blockHit).getPlayer(), ExitReason.BREAK);
	}
	
	/**
	 * When a fern mode fern is destroyed by fire
	 */
	@EventHandler(ignoreCancelled = true)
	@SuppressWarnings("deprecation")
	public void onFernBurn(BlockBurnEvent event) {
		Block blockBurned = event.getBlock();
		//If not a fern
		if (blockBurned.getType() != Material.LONG_GRASS || blockBurned.getData() != 2) {
			return;
		}
		//If a pair does not exist at the event location
		if (finder.getPairFromBlock(blockBurned) == null) {
			return;
		}
		action.exit(finder.getPairFromBlock(blockBurned).getPlayer(), ExitReason.FIRE);
	}
}
