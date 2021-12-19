package xyz.dwaslashe.core;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import xyz.dwaslashe.core.cache.UserCache;
import xyz.dwaslashe.lang.listeners.PlayerInventoryListener;
import xyz.dwaslashe.managers.EventManager;
import xyz.dwaslashe.resources.helpers.InventoryHelper;
import xyz.dwaslashe.lang.Lang;
import xyz.dwaslashe.lang.cache.LangCache;
import xyz.dwaslashe.lang.cache.LocalPlayerCache;
import xyz.dwaslashe.lang.helpers.ReflectionHelper;
import xyz.dwaslashe.lang.listeners.PlayerJoinListener;
import xyz.dwaslashe.resources.helpers.TimeHelper;

import java.io.File;

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

    /**
     * @Core
     */

    private final EventManager eventManager = new EventManager();
    private final UserCache userCache = new UserCache();

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        instance = this;
        new ReflectionHelper().initialize();

        Lang.loadAllLanguages(new File(Main.getInstance().getDataFolder()+"/langs"));
        Lang lang = Lang.create("default");
        new LangMessages(lang).register();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(InventoryHelper.createEmptyToEvent(), this);

        eventManager.registerIntegrityConsumer(event -> event.setFormat("<gradient:dark_gray:gray>%1$s</gradient>&8: &f%2$s"));

        loadDBConfig();
        loadEvents();
    }

    private void loadEvents() {
        new Reflections("xyz.dwaslashe.core.bukkit.listeners").getSubTypesOf(Listener.class)
                .forEach(clazz -> Bukkit.getPluginManager().registerEvents(ReflectionHelper.newInstance(clazz), this));
    }

    @SneakyThrows
    private void loadDBConfig(){
        File file = new File(getDataFolder(), "config.yml");
        if(file.createNewFile()) {
            getConfig().load(file);
            getConfig().set("host", "localhost");
            getConfig().set("username", "root");
            getConfig().set("password", "");
            getConfig().set("table", "test");
            getConfig().set("port", 3306);
            getConfig().set("ssl", false);
            getConfig().save(file);
            return;
        }
        getConfig().load(new File(getDataFolder(), "config.yml"));
    }
}