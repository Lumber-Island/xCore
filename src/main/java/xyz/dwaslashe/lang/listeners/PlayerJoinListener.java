package xyz.dwaslashe.lang.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import xyz.dwaslashe.core.Main;

public class PlayerJoinListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        Main.getInstance().getLocalPlayerCache().computeIfAbsent(player);
        player.sendMessage(player.getLocale());
    }
}
