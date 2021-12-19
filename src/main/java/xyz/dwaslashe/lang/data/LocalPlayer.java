package xyz.dwaslashe.lang.data;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import xyz.dwaslashe.core.Main;
import xyz.dwaslashe.lang.Lang;

import java.util.*;

@Getter
public class LocalPlayer {


    public static LocalPlayer get(HumanEntity humanEntity){
        return get((Player) humanEntity);
    }

    public static LocalPlayer get(String name){
        return Main.getInstance().getLocalPlayerCache().getPlayerMap().get(name);
    }

    public static LocalPlayer get(Player player){
        return get(player.getName());
    }

    public static Map<String, LocalPlayer> getPlayerMap(){
        return Main.getInstance().getLocalPlayerCache().getPlayerMap();
    }

    private final String name;
    private Player player;

    private final List<String> messages = new ArrayList<>();
    private Lang lang;

    public LocalPlayer(String name){
        this.name = name;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public LocalPlayer putMessage(String message){
        messages.add(message);
        return this;
    }

    public LocalPlayer putMessages(String... messages){
        this.messages.addAll(Arrays.stream(messages).toList());
        return this;
    }

    public LocalPlayer putMessages(List<String> messages){
        this.messages.addAll(messages);
        return this;
    }

    public MessageProvider getMessage(String path){
        return new MessageProvider(this, lang.get(path, String.class), false);
    }

    public MessageProvider getListMessage(String path){
        return new MessageProvider(this, lang.get(path, ArrayList.class), true);
    }

    public void send(){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.join("\n", messages)));
        messages.clear();
    }

    public void chooseLanguage(Lang lang){
        if(this.lang != null)
            this.lang.getLocalPlayerList().remove(this);

        this.lang = lang;
        lang.getLocalPlayerList().add(this);
    }
}
