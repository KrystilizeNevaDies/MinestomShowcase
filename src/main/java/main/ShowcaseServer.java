package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import main.data.PlayerDatabase;
import main.instances.lobby.PrimaryLobbyInstance;
import main.instances.lobby.SecondaryLobbyInstance;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.instance.InstanceManager;

public class ShowcaseServer {
	
	public static Wini config;
	
	private static Random random = new Random();
	
	private static PlayerDatabase database = null; // TODO: Player Database using sqlite/rocksdb
	
    public static void main(String[] args) {
    	// Start initialization
    	MinecraftServer server = MinecraftServer.init();
    	
    	// Load config
    	try {
			config = loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	///////////////
    	// Instances //
    	///////////////
    	InstanceManager instanceManager = MinecraftServer.getInstanceManager();
		// Create uuid
		UUID uuid = UUID.randomUUID();
		
		// Make instance
		SecondaryLobbyInstance instance = new SecondaryLobbyInstance(uuid);
		
		// Register instance
		instanceManager.registerInstance(instance);
		PrimaryLobbyInstance.getLobbyMappings().put(uuid, instance);
    	
    	
    	
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
    	
    	// Authenticate players
    	MojangAuth.init();
    	
    	// If velocity is enabled, turn on and accept secret key
    	if (config.get("Velocity", "enabled", Integer.class) == 1)
    		VelocityProxy.enable(config.get("Velocity", "secret", String.class));
    	
    	
    	// Start Server
    	server.start("0.0.0.0", 25565, (connection, data) -> {
    		data.setDescription(config.get("Server", "MOTD", String.class));
    	});
    }
    
    private static Wini loadConfig() throws InvalidFileFormatException, IOException {
    	InputStream inputStream = new FileInputStream(new File("config.ini"));
		return new Wini(inputStream);
	}

	private static void handleConnection(PlayerLoginEvent event) {
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
