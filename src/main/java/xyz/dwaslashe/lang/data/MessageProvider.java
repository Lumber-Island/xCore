package xyz.dwaslashe.lang.data;

import java.util.List;
import java.util.Objects;

public class MessageProvider<T> {

    private final LocalPlayer localPlayer;
    private Object message;

    public MessageProvider(LocalPlayer localPlayer, T messageComponent, boolean list){
        this.localPlayer = localPlayer;
        this.message = messageComponent;
        if(list && !isList()) message = migrateToList();
        else message = migrateToString();
    }

    public LocalPlayer getLocalPlayer() {
        return localPlayer;
    }

    public boolean isList(){
        return message instanceof List;
    }

    private boolean isString(){
        return message instanceof String;
    }

    private List<String> migrateToList(){
        return isList() ? ((List<?>)message).stream().map(Objects::toString).toList() : List.of(migrateToString());
    }

    private String migrateToString(){
        return Objects.toString(message);
    }

    public void send(){
        if(isList()) {
            localPlayer.putMessages(migrateToList());
        } else localPlayer.putMessage(migrateToString());
        localPlayer.send();
    }
}
