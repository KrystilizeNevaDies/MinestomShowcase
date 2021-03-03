package main.data;

import java.util.UUID;

import net.minestom.server.entity.Player;
import net.minestom.server.utils.Position;

public class LogOffData {
	
	private UUID instanceID;
	private Position position;
	
	public LogOffData(UUID instanceID, Position position) {
		this.instanceID = instanceID;
		this.position = position;
	}
	
	public LogOffData(Player player) {
		this.instanceID = player.getInstance().getUniqueId();
		this.position = player.getPosition();
	}

	public UUID getInstanceID() {
		return instanceID;
	}

	public Position getPosition() {
		return position;
	}
}
