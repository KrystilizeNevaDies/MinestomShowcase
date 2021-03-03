package main.instances;

import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.Instance;

public interface ShowcaseInstance {
	public boolean shouldRelogPlayer(PlayerLoginEvent event);
	
	public Instance getInstance();
	
	public void relogPlayer(PlayerLoginEvent event);
	
	public void spawnPlayer(PlayerLoginEvent event);
}