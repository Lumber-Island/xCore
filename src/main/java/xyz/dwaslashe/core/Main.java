package xyz.dwaslashe.core;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    public Main() {
        plugin = this;
    }


    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        System.out.println("Włączam się");
        getConfig().addDefault("reason", "You got banned because you died!");
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void loadEvents() {

    }

    private void loadCommands() {

    }

    private void loadTasks() {

    }
}