package main.instances.lobby;

import java.util.UUID;

import main.instances.ShowcaseInstance;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.SharedInstance;

public class SecondaryLobbyInstance extends SharedInstance implements ShowcaseInstance {

	public SecondaryLobbyInstance(UUID uniqueId) {
		super(uniqueId, PrimaryLobbyInstance.INSTANCE);
	}

	@Override
	public boolean shouldRelogPlayer(PlayerLoginEvent event) {
		return true;
	}

	@Override
	public Instance getInstance() {
		return this;
	}

	@Override
	public void relogPlayer(PlayerLoginEvent event) {
		event.setSpawningInstance(this);
		// TODO: Spawn location
	}

	@Override
	public void spawnPlayer(PlayerLoginEvent event) {
		event.setSpawningInstance(this);
		// TODO: Spawn location
	}
	
}
