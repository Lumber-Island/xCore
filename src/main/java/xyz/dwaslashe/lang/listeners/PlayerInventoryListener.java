package xyz.dwaslashe.lang.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;
import xyz.dwaslashe.core.Main;
import xyz.dwaslashe.lang.ChooseInventory;
import xyz.dwaslashe.lang.data.LocalPlayer;

import java.util.HashMap;
import java.util.Map;

public class PlayerInventoryListener implements Listener {

    private final Map<String, BukkitTask> bukkitTaskMap = new HashMap<>();

    private void forceOpen(LocalPlayer localPlayer){
        bukkitTaskMap.remove(localPlayer.getName());
        bukkitTaskMap.put(localPlayer.getName(), Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            ChooseInventory.open(localPlayer);
            bukkitTaskMap.remove(localPlayer.getName());
        }, 5));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        LocalPlayer localPlayer = LocalPlayer.get(event.getPlayer());
        if(localPlayer.getLang() != null) return;
        forceOpen(localPlayer);
    }
}
