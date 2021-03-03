package main.instances.lobby;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.storage.StorageLocation;
import net.minestom.server.world.DimensionType;
/*
 * The primary lobby instance is the instance in which the lobby work resides.
 * 
 * This world is then shared among distributed ssecondary lobby instances
 */
public class PrimaryLobbyInstance extends InstanceContainer {
	
	private static Map<UUID, SecondaryLobbyInstance> lobbyMappings = new HashMap<UUID, SecondaryLobbyInstance>();
	
	public static PrimaryLobbyInstance INSTANCE = new PrimaryLobbyInstance(PrimaryLobbyInstance.getLobbyStorageLocation());
	
	private PrimaryLobbyInstance(StorageLocation storageLocation) {
		super(UUID.randomUUID(), DimensionType.OVERWORLD, storageLocation);
	}
	
	private static StorageLocation getLobbyStorageLocation() {
		// TODO: Storage location
		return null;
	}

	public static Map<UUID, SecondaryLobbyInstance> getLobbyMappings() {
		return lobbyMappings;
	}
}
