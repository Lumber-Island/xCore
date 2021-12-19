package xyz.dwaslashe.lang.listeners;

import de.themoep.resourcepacksplugin.bukkit.events.ResourcePackStatusEvent;
import de.themoep.resourcepacksplugin.core.ResourcePackStatus;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import xyz.dwaslashe.core.Main;
import xyz.dwaslashe.core.data.User;
import xyz.dwaslashe.lang.ChooseInventory;
import xyz.dwaslashe.lang.data.LocalPlayer;


public class PlayerJoinListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        LocalPlayer localPlayer = Main.getInstance().getLocalPlayerCache().computeIfAbsent(player);
        localPlayer.setPlayer(player);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        User user = User.getOnline(player.getName());
        if(user == null) return;

        LocalPlayer localPlayer = user.getLocalPlayer();
        if(localPlayer.getLang() != null) localPlayer.getMessage("lang.actually").send();
    }

    @EventHandler(ignoreCancelled = true)
    public void onCheck(ResourcePackStatusEvent event){
        if(event.getStatus().equals(ResourcePackStatus.ACCEPTED)){
            LocalPlayer localPlayer = LocalPlayer.get(event.getPlayer());
            ChooseInventory.open(localPlayer);
        } else event.getPlayer().kick(Component.text("If you want to play you must\ndownload our server resourcepacks.\n\nAllow servers resourcepacks in your settings."), PlayerKickEvent.Cause.ILLEGAL_CHARACTERS);
    }
}
