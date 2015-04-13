package io.github.redpanda4552.FernMode.util;

public enum ExitReason {
	
	MOVEMENT("Exiting Fern Mode"),
	FIRE("Your fern was set on fire!"),
	BREAK("Your fern was broken!"),
	BREAK_SELF("You broke your own fern!"),
	PLUGIN_SHUTDOWN("FernMode has been disabled by another plugin, server reload or shutdown."),
	//Probably not necessary, but why not.
	DISCONNECT("You left the game.");
	
	public final String reason;
	
	private ExitReason(String str) {
		reason = str;
	}
	
	public String getString() {
		return reason;
	}
}
