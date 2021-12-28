package xyz.dwaslashe.core;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.slf4j.Marker;
import xyz.dwaslashe.core.cache.UserCache;
import xyz.dwaslashe.core.economy.EconomyHandler;
import xyz.dwaslashe.lang.db.SQL;
import xyz.dwaslashe.lang.listeners.PlayerInventoryListener;
import xyz.dwaslashe.managers.EventManager;
import xyz.dwaslashe.resources.helpers.InventoryHelper;
import xyz.dwaslashe.lang.Lang;
import xyz.dwaslashe.lang.cache.LangCache;
import xyz.dwaslashe.lang.cache.LocalPlayerCache;
import xyz.dwaslashe.lang.helpers.ReflectionHelper;
import xyz.dwaslashe.lang.listeners.PlayerJoinListener;


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

    private final SQL sql = new SQL();
    private final EconomyHandler economyHandler = new EconomyHandler();

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

        try {
            sql.connect(getConfig().getString("host"),
                    getConfig().getString("table"),
                    getConfig().getString("username"),
                    getConfig().getString("password"),
                    getConfig().getInt("port"),
                    getConfig().getBoolean("ssl"));
        } catch (Exception e){
            getSLF4JLogger().warn("Could not to connect with database. Please check host, table, username, password and port for more information's.");
        }

        getServer().getServicesManager().register(Economy.class, economyHandler, this, ServicePriority.Highest);
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