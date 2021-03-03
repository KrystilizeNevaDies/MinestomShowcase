package main.data;

import java.util.UUID;

public interface PlayerDatabase {
	public PlayerData getPlayerData(UUID uuid);
	
	public void setPlayerData(UUID uuid, PlayerData data);
}