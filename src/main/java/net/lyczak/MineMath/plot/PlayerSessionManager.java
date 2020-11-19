package net.lyczak.MineMath.plot;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class PlayerSessionManager {
    private Map<UUID, PlayerSession> playerSessions = new HashMap<UUID, PlayerSession>();

    private Logger logger;
    private File sessionsFile;
    private Gson gson = new Gson();

    public PlayerSessionManager(Logger logger, File sessionsFile) {
        this.logger = logger;
        this.sessionsFile = sessionsFile;
    }

    public PlayerSessionManager(File sessionsFile) {
        this.sessionsFile = sessionsFile;
    }

    public PlayerSession get(Player p) {
        PlayerSession s = playerSessions.get(p.getUniqueId());
        if(s == null) {
            s = new PlayerSession();
            playerSessions.put(p.getUniqueId(), s);
        }
        return s;
    }

    public PlayerSession put(Player p, PlayerSession s) {
        return playerSessions.put(p.getUniqueId(), s);
    }

    public void remove(Player p) {
        playerSessions.remove(p);
    }

    public void save() {
        try {
            gson.toJson(playerSessions, new FileWriter(sessionsFile));
        } catch (JsonIOException e) {
            logger.severe("Failed to serialize PlayerSessions to JSON.");
            e.printStackTrace();
        } catch (IOException e) {
            logger.severe("Failed to save PlayerSessions.");
            e.printStackTrace();
        }
    }
}
