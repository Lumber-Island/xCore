package xyz.dwaslashe.managers;

import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventManager {

    private final List<Consumer<AsyncPlayerChatEvent>> chatEvent = new ArrayList<>();

    public List<Consumer<AsyncPlayerChatEvent>> getChatEvent() {
        return chatEvent;
    }

    public void registerIntegrityConsumer(Consumer<AsyncPlayerChatEvent> eventConsumer){
        chatEvent.add(eventConsumer);
    }
}
