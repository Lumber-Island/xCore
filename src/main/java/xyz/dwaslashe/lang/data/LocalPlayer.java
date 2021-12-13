package xyz.dwaslashe.lang.data;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import xyz.dwaslashe.core.Main;
import xyz.dwaslashe.lang.Lang;

import java.util.*;

@Getter
public class LocalPlayer {


    public static LocalPlayer get(String name){
        return Main.getInstance().getLocalPlayerCache().getPlayerMap().get(name);
    }

    private final String name;
    private Player player;

    private List<String> messages = new ArrayList<>();
    private Lang lang;

    public LocalPlayer(String name){
        this.name = name;
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

    public MessageProvider<String> getMessage(String path){
        return new MessageProvider<>(this, lang.get(path, String.class), false);
    }

    public MessageProvider<ArrayList<String>> getListMessage(String path){
        return new MessageProvider<>(this, lang.get(path, ArrayList.class), true);
    }

    public void send(){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.join("\n", messages)));
        messages.clear();
    }
}
