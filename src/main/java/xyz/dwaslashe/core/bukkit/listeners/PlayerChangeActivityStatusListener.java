package xyz.dwaslashe.core.bukkit.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.dwaslashe.core.cache.UserCache;
import xyz.dwaslashe.core.data.User;
import xyz.dwaslashe.lang.data.LocalPlayer;

public class PlayerChangeActivityStatusListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        User user = User.createIfAbsent(event.getPlayer().getName());

        if(user.getLocalPlayer() == null) user.setLocalPlayer(LocalPlayer.get(user.getName()));

        user.setOnline(true);

        UserCache.getUsers().forEach((name, other) -> {
            LocalPlayer localPlayer = other.getLocalPlayer();
            if(localPlayer == null || localPlayer.getLang() == null) return;
            if(user.getFirstJoin() == 0)
                localPlayer.getMessage("events.join.message_global_first").replace("{PLAYER}", user.getName()).send();
            else localPlayer.getMessage("events.join.message_global").replace("{PLAYER}", user.getName()).send();
        });

        if(user.getFirstJoin() == 0) user.setFirstJoin(System.currentTimeMillis());

        user.setLastJoin(System.currentTimeMillis());

        user.setJoinCount(user.getJoinCount()+1);

        if(user.getLocalPlayer().getLang() == null) return;

        user.getLocalPlayer().getMessage("events.join.message")
                .replace("{PLAYER}", user.getName())
                .replace("{PLAYERS-ONLINE}", UserCache.getOnlineUsers().size())
                .replace("{PLAYERS-MAX}", Bukkit.getMaxPlayers())
                .replace("{PLAYERS-TOTAL}", UserCache.getUsers().size())
                .send();

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage(null);
        User user = User.getOnline(event.getPlayer().getName());
        user.setOnline(false);
    }

}
