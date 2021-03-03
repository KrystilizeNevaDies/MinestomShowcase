package main.instances.lobby;

import java.util.UUID;

import main.instances.ShowcaseInstance;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventCallback;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.SharedInstance;

public class SecondaryLobbyInstance extends SharedInstance implements ShowcaseInstance {

	private EventCallback<PlayerSpawnEvent> firstSpawnEvent = this::firstSpawn;
	
	public SecondaryLobbyInstance(UUID uniqueId) {
		super(uniqueId, PrimaryLobbyInstance.INSTANCE);
	}

	@Override
	public boolean shouldRelogPlayer(PlayerLoginEvent event) {
		return false;
	}

	@Override
	public Instance getInstance() {
		return this;
	}

	@Override
	public void relogPlayer(PlayerLoginEvent event) {
		spawnPlayer(event);
	}

	@Override
	public void spawnPlayer(PlayerLoginEvent event) {
		event.setSpawningInstance(this);
		
		Player player = event.getPlayer();
		
		// Schedule first spawn event
		player.addEventCallback(PlayerSpawnEvent.class, firstSpawnEvent);
	}
	
	private void firstSpawn(PlayerSpawnEvent event) {
		Player player = event.getPlayer();
		
		// Remove callback
		player.removeEventCallback(PlayerSpawnEvent.class, firstSpawnEvent);
		
		// Tp to lobby spawn
		player.teleport(PrimaryLobbyInstance.getSpawn());
	}
}