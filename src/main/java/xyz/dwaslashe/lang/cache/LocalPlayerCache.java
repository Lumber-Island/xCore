package xyz.dwaslashe.lang.cache;

import org.bukkit.entity.Player;
import xyz.dwaslashe.lang.data.LocalPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LocalPlayerCache {

    private final Map<String, LocalPlayer> playerMap = new HashMap<>();

    public Map<String, LocalPlayer> getPlayerMap() {
        return playerMap;
    }

    public LocalPlayer computeIfAbsent(Player player){
        return playerMap.computeIfAbsent(player.getName(), LocalPlayer::new);
    }

    public void addPlayer(LocalPlayer localPlayer){
        Objects.requireNonNull(localPlayer);
        playerMap.put(localPlayer.getName(), localPlayer);
    }

    public void remove(LocalPlayer localPlayer){
        Objects.requireNonNull(localPlayer);
        playerMap.remove(localPlayer.getName());
    }
}
