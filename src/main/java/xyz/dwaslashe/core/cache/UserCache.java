package xyz.dwaslashe.core.cache;

import xyz.dwaslashe.core.Main;
import xyz.dwaslashe.core.data.User;

import java.util.HashMap;
import java.util.Map;

public class UserCache {

    private final Map<String, User> userMap = new HashMap<>();
    private final Map<String, User> onlineUserMap = new HashMap<>();

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public Map<String, User> getOnlineUserMap() {
        return onlineUserMap;
    }

    public static Map<String, User> getOnlineUsers(){ return Main.getInstance().getUserCache().getOnlineUserMap(); }
    public static Map<String, User> getUsers(){
        return Main.getInstance().getUserCache().getUserMap();
    }

    public User getOnlineUser(String name){
        return onlineUserMap.get(name);
    }

    public User getUser(String name){
        return userMap.get(name);
    }

    public User createIfAbsent(String name){
        return userMap.computeIfAbsent(name, User::new);
    }
}
