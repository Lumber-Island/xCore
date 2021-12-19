package xyz.dwaslashe.core.data;

import lombok.Getter;
import lombok.Setter;
import xyz.dwaslashe.core.Main;
import xyz.dwaslashe.lang.data.LocalPlayer;
import xyz.dwaslashe.resources.helpers.TeleportHelper;

@Getter
@Setter
public class User {

    public static User get(String name){
        return Main.getInstance().getUserCache().getUser(name);
    }

    public static User createIfAbsent(String name){
        return Main.getInstance().getUserCache().createIfAbsent(name);
    }

    public static User getOnline(String name){
        return Main.getInstance().getUserCache().getOnlineUser(name);
    }

    private final String name;

    private LocalPlayer localPlayer;
    private boolean chat, notifications, online;
    private TeleportHelper teleportHelper;

    private long firstJoin, lastJoin, joinCount, quitCount;

    public User(String name){
        this.name = name;
    }

    public void setOnline(boolean online) {
        this.online = online;
        if(online) Main.getInstance().getUserCache().getOnlineUserMap().put(name, this);
        else Main.getInstance().getUserCache().getOnlineUserMap().remove(name);
    }
}
