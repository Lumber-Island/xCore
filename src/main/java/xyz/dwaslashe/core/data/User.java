package xyz.dwaslashe.core.data;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import xyz.dwaslashe.core.Main;
import xyz.dwaslashe.core.economy.EconomyHandler;
import xyz.dwaslashe.lang.data.LocalPlayer;
import xyz.dwaslashe.lang.db.SQL;
import xyz.dwaslashe.resources.helpers.TeleportHelper;

import java.sql.Connection;
import java.util.function.Consumer;

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

    private double money;

    private long firstJoin, lastJoin, joinCount, quitCount;

    public User(String name){
        this.name = name;
    }

    @SneakyThrows
    public synchronized void uploadToDatabase(){
        SQL sql = Main.getInstance().getSql();
        if(sql.isClosed()) return;

        Connection connection = sql.getConnection();
        connection.prepareStatement("CREATE TABLE IF NOT EXISTS `langUser` VALUES (" +
                "name varchar(64)," +
                "lang varchar(64)," +
                "primary key(name)" +
                ")").execute();

        connection.prepareStatement("INSERT INTO `langUsers` (`name`, `lang`)" +
                " VALUES (?, ?)" +
                " on duplicate key update" +
                " name=values(" + name + "), lang=values(" + localPlayer.getLang().getName() + ")");
    }

    public User executor(Consumer<User> consumer){
        consumer.accept(this);
        return this;
    }

    public void setOnline(boolean online) {
        this.online = online;
        if(online) Main.getInstance().getUserCache().getOnlineUserMap().put(name, this);
        else {
            Main.getInstance().getUserCache().getOnlineUserMap().remove(name);
            uploadToDatabase();
        }
    }
}
