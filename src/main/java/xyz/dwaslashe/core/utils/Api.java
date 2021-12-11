package xyz.dwaslashe.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Api {

    public static void sendMessage(CommandSender sender, String message){
        sender.sendMessage(fixColor(message));
    }

    public static void sendMessage(CommandSender sender, List<String> messages){
        messages.forEach(message -> sendMessage(sender, message));
    }

    public static void sendMessage(CommandSender sender, String... messages){
        Arrays.asList(messages).forEach(message -> sendMessage(sender, message));
    }

    public static boolean sendMessage(String message) {
        Bukkit.getOnlinePlayers().forEach(p -> sendMessage(p, Api.fixColor(message)));
        return true;
    }

    public static String fixColor(String message){
        return message == null ? "" : ChatColor.translateAlternateColorCodes
                        ('&', message)
                .replace("<CUSTOM_RED>", net.md_5.bungee.api.ChatColor.of("#FF3131") + "")
                .replace("<CUSTOM_GREEN>", net.md_5.bungee.api.ChatColor.of("#39FF14") + "")
                .replace("<CUSTOM_YELLOW>", net.md_5.bungee.api.ChatColor.of("#FFD700") + "")
                .replace(">>", "»")
                .replace("<<", "«")
                .replace("**", "•")
                .replace(":unlimited:", "∞")
                .replace("<3", "♥")
                .replace(":skull:", "☠")
                .replace(":toxic:", "☢")
                .replace(":crown:", "♚")
                .replace(":stop1:", "▂")
                .replace(":stop2:", "▃")
                .replace(":stop3:", "▄")
                .replace(":stop4:", "▅")
                .replace(":stop5:", "▆")
                .replace(":stop6:", "▇")
                .replace(":stop7:", "█")
                .replace(":ok:", "✔")
                .replace(":no:", "✗");
    }

    public static List<String> fixColor(List<String> message){
        return message.stream().map(Api::fixColor).collect(Collectors.toList());
    }
}
