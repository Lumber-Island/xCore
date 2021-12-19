package xyz.dwaslashe.core.bukkit.listeners;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.dwaslashe.core.Main;
import xyz.dwaslashe.core.cache.UserCache;
import xyz.dwaslashe.resources.helpers.Helper;

public class PlayerChatListener implements Listener {

    private static final Helper helper = new Helper();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event){
        if(event.getPlayer().hasPermission("xcore.chat.color")) {
            Main.getInstance().getEventManager().getChatEvent().forEach(eventConsumer -> eventConsumer.accept(event));

            String text = helper.translateAlternateColorCodes(event.getMessage().replace("\\n", ""));
            String format = String.format(helper.translateAlternateColorCodes(event.getFormat()), event.getPlayer().getDisplayName(), text);
            UserCache.getOnlineUsers().forEach((name, user) -> user.getLocalPlayer().getPlayer().sendMessage(MiniMessage.get().parse(format)));
            event.setCancelled(true);
        }
    }
}
