package xyz.dwaslashe.core;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import xyz.dwaslashe.lang.Lang;
import xyz.dwaslashe.lang.cache.LangCache;
import xyz.dwaslashe.lang.cache.LocalPlayerCache;
import xyz.dwaslashe.lang.data.LocalPlayer;
import xyz.dwaslashe.lang.helpers.ReflectionHelper;

import java.util.Arrays;

@Getter
@Setter
public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    /**
     * @Lang
     */

    private final LangCache langCache = new LangCache();
    private final LocalPlayerCache localPlayerCache = new LocalPlayerCache();

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("Włączam się");

        Lang lang = Lang.create("polski");

        lang.register("command.health.executor", "Zostales/as wyleczony/a");
        lang.register("event.join.player", Arrays.asList("&7&m  &8&m  &7&m  >&e SERVER &7&m<  &8&m  &7&m  &r",
                " ",
                " &7Gracze&8: &f{PLAYERS}"));

        localPlayerCache.getPlayerMap().values().forEach(localPlayer -> {
            localPlayer.getMessage("command.health.executor").send();
        });

        new Reflections("xyz.dwaslashe.core.lang.listeners").getSubTypesOf(Listener.class)
                .forEach(clazz -> Bukkit.getPluginManager().registerEvents(ReflectionHelper.newInstance(clazz), this));

    }

    private void loadEvents() {

    }

    private void loadCommands() {

    }

    private void loadTasks() {

    }
}