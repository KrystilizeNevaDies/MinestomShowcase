package main.instances.lobby;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.utils.Position;
import net.minestom.server.world.DimensionType;
/*
 * The primary lobby instance is the instance in which the lobby world resides.
 * 
 * No players are intended to be on this world.
 * 
 * This world is then distributed among secondary lobby instances
 */
public class PrimaryLobbyInstance extends InstanceContainer {
	
	private static Map<UUID, SecondaryLobbyInstance> lobbyMappings = new HashMap<UUID, SecondaryLobbyInstance>();
	
	public static PrimaryLobbyInstance INSTANCE = new PrimaryLobbyInstance();
	
	private PrimaryLobbyInstance() {
		super(UUID.randomUUID(), DimensionType.OVERWORLD, null);
	}

	public static Map<UUID, SecondaryLobbyInstance> getLobbyMappings() {
		return lobbyMappings;
	}

	public static Position getSpawn() {
		// TODO: Choose correct spawn location
		return new Position(0, 0, 0);
	}
}