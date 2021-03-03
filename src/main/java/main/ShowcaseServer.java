package main;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import main.data.LogOffData;
import main.data.PlayerData;
import main.data.PlayerDatabase;
import main.instances.ShowcaseInstance;
import main.instances.lobby.PrimaryLobbyInstance;
import main.instances.lobby.SecondaryLobbyInstance;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceManager;

public class ShowcaseServer {
	
	private static Random random = new Random();
	
	private static PlayerDatabase database = null; // TODO: Player Database using sqlite/rocksdb
	
    public static void main(String[] args) {
    	// Start initialization
    	MinecraftServer server = MinecraftServer.init();
    	
    	
    	
    	///////////////
    	// Instances //
    	///////////////
    	InstanceManager instanceManager = MinecraftServer.getInstanceManager();
    	
    	// TODO: Seperate instance names & uuids into config
    	// Here i've simply implemented a hashing system, this is not optimal and should be changed in the future
    	List<String> lobbyInstanceNames = List.of("Lobby-1", "Lobby-2", "Lobby-3");
    	
    	// For each instance name, create the uuid and instance 
    	for (String name : lobbyInstanceNames) {
    		// Create uuid
    		UUID uuid = new UUID(name.hashCode(), name.length());
    		
    		// Make instance
    		SecondaryLobbyInstance instance = new SecondaryLobbyInstance(uuid);
    		
    		// Register instance
    		instanceManager.registerInstance(instance);
    		PrimaryLobbyInstance.getLobbyMappings().put(uuid, instance);
    	}
    	
    	
    	// TODO: Minigame servers
    	
    	
    	
    	////////////
    	// Events //
    	////////////
    	GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
    	
    	// Initial connections
    	handler.addEventCallback(PlayerLoginEvent.class, ShowcaseServer::handleConnection);
    	
    	
    	
    	//////////////////
    	// Server Start //
    	//////////////////
    	
    	// Enable misc features
    	MojangAuth.init();
    	
    	// Start Server
    	server.start("0.0.0.0", 25565);
    }
    
    private static void handleConnection(PlayerLoginEvent event) {
    	// TODO: Check last logged in server, and try to spawn them there, else spawn them in a lobby server
    	UUID uuid = event.getPlayer().getUuid();
    	
    	// Get player data object
    	PlayerData data = database.getPlayerData(uuid);
    	
    	// Get log off server
    	LogOffData logOff = data.getLastLogOff();
    	ShowcaseInstance instance = (ShowcaseInstance) MinecraftServer.getInstanceManager().getInstance(logOff.getInstanceID());
    	
    	if (instance.shouldRelogPlayer(event)) {
	    	// Relog player
	    	instance.relogPlayer(event);
    	} else {
    		// Else spawn in lobby server
    		joinLobby(event);
    	}
    }
    
    private static void joinLobby(PlayerLoginEvent event) {
    	// Get all lobby servers
    	Map<UUID, SecondaryLobbyInstance> lobbyMappings = PrimaryLobbyInstance.getLobbyMappings();
    	SecondaryLobbyInstance[] instances = lobbyMappings.values().toArray(new SecondaryLobbyInstance[lobbyMappings.size()]);
    	
    	// Choose random and spawn player there
    	int i = random.nextInt(lobbyMappings.size());
    	instances[i].spawnPlayer(event);
    }

	public static PlayerDatabase getDatabase() {
		return database;
	}
}
