package xyz.dwaslashe.lang;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.dwaslashe.core.Main;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Lang {

    public static Lang create(String name){
        return new Lang(name).createIfAbsent();
    }

    public static void loadAllLanguages(File folder){
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            Lang lang = new Lang(file);
            lang.load();
            Main.getInstance().getLangCache().getLangMap().put(file.getName(), lang);
        }
    }

    private final Map<String, Object> langMap = new HashMap<>();

    private String name;
    private File file;
    private FileConfiguration fileConfiguration;

    private Lang(File file){
        this.name = file.getName();
        this.file = file;
    }

    public Lang(String name){
        this.name = name;
        this.file = new File(Main.getInstance().getDataFolder() + "/langs", name + ".yml");
    }

    @SneakyThrows
    private void upsetRegistered(){
        langMap.forEach((key, object) -> {
            fileConfiguration.set(key, object);
        });
        fileConfiguration.save(file);
    }

    @SneakyThrows
    private Lang createIfAbsent(){
        Objects.requireNonNull(file, "Please insert the file name and create.");

        if(file.mkdirs()) {
            fileConfiguration = YamlConfiguration.loadConfiguration(new InputStreamReader(file.toURL().openStream(), StandardCharsets.UTF_8));
            upsetRegistered();
        }
        return this;
    }

    public void load(){
        createIfAbsent();
        langMap.clear();
        fileConfiguration.getKeys(true).forEach(s -> langMap.put(s, fileConfiguration.get(s)));
    }

    @SneakyThrows
    public void save(){
        langMap.forEach((key, object) -> fileConfiguration.set(key, object));
        fileConfiguration.save(file);
    }

    @SneakyThrows
    private <T> void set(String path, T element){
        fileConfiguration.set(path, element);
        fileConfiguration.save(file);
    }

    public <T> T get(String path, Class<? extends T> looking){
        Object p = langMap.get(path);
        if(p == null || !p.getClass().equals(looking)) return null;
        return (T) p;
    }

    public <T> void register(String path, T element){
        langMap.put(path, element);
        set(path, element);
    }

    public <T> void update(String path, T element){
        langMap.remove(path);
        langMap.put(path, element);
        set(path, element);
    }

    public void remove(String path){
        langMap.remove(path);
    }
}
