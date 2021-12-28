package xyz.dwaslashe.lang;

import com.google.common.base.Charsets;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.dwaslashe.core.Main;
import xyz.dwaslashe.lang.data.LocalPlayer;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Author ~WuShei
 */
public class Lang {

    public static Lang create(String name){

        Lang lang = Main.getInstance().getLangCache().getLangMap().get(name);
        if(lang != null) return lang;

        lang = new Lang(name);
        Main.getInstance().getLangCache().getLangMap().put(name, lang);

        return lang.createIfAbsent();
    }

    public static void loadAllLanguages(File folder){
        if(!folder.exists()) return;
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            Lang lang = new Lang(file);
            lang.load();
            Main.getInstance().getLangCache().getLangMap().put(lang.name, lang);
        }
    }

    private final Map<String, Object> langMap = new HashMap<>();
    private final List<LocalPlayer> localPlayerList = new ArrayList<>();

    private String name;
    private File file;
    private FileConfiguration fileConfiguration;

    public List<LocalPlayer> getLocalPlayerList() {
        return localPlayerList;
    }

    public Map<String, Object> getLangMap() {
        return langMap;
    }

    private Lang(File file){
        this.name = file.getName().replace(".yml", "");
        this.file = file;
    }

    public Lang(String name){
        this.name = name;
        this.file = new File(Main.getInstance().getDataFolder() + "/langs", name + ".yml");
    }

    public String getName() {
        return name;
    }

    @SneakyThrows
    private void upsetRegistered(){
        langMap.forEach((key, object) -> {
            fileConfiguration.set(key, object);
        });
        fileConfiguration.save(file);
    }

    @SneakyThrows
    private Lang createIfAbsent() {
        Objects.requireNonNull(file, "Please insert the file name and create.");

        file.getParentFile().mkdirs();

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        InputStream stream = Main.getInstance().getResource(name + ".yml");
        if (stream != null)
            fileConfiguration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(stream, Charsets.UTF_8)));
        upsetRegistered();

        return this;
    }

    private void getFileConfiguration(){
        if(!file.exists()) {
            createIfAbsent();
            return;
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        InputStream stream = Main.getInstance().getResource(name+".yml");

        if(stream != null)
            fileConfiguration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(stream, Charsets.UTF_8)));

        upsetRegistered();

    }

    public void load(){
        getFileConfiguration();
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
        if(fileConfiguration == null) return;
        fileConfiguration.set(path, element);
        fileConfiguration.save(file);
    }

    public <T> T get(String path, Class<? extends T> clazz){
        Object p = langMap.get(path);
        if(p == null)
            return null;
        return (T) p;
    }

    public List<String> getList(String path){
        Object p = langMap.get(path);
        if(p instanceof List)
            return (List<String>) p;
        return null;
    }

    public <T> void register(String path, T element){
        if(langMap.containsKey(path)) return;
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
