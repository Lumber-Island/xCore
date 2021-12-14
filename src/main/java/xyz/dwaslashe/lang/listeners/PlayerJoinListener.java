package xyz.dwaslashe.lang.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import xyz.dwaslashe.core.Main;
import xyz.dwaslashe.resources.helpers.GodlyStack;
import xyz.dwaslashe.resources.helpers.InventoryHelper;
import xyz.dwaslashe.lang.Lang;
import xyz.dwaslashe.lang.cache.LangCache;
import xyz.dwaslashe.lang.data.LocalPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerJoinListener implements Listener {

    private static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private static List<String> translate(List<String> textList) {
        return textList == null ? new ArrayList<>() : textList.stream().map(PlayerJoinListener::translate).toList();
    }

    @EventHandler(ignoreCancelled = true)
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        LocalPlayer localPlayer = Main.getInstance().getLocalPlayerCache().computeIfAbsent(player);
        localPlayer.setPlayer(player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        InventoryHelper inventoryHelper = new InventoryHelper(player, "Choose your language", 4);
        inventoryHelper.setItemRange(0, 9, new GodlyStack(Material.GRAY_STAINED_GLASS_PANE)
                .editMeta(itemMeta -> {
                            itemMeta.setDisplayName(translate(" "));
                            itemMeta.setLore(translate(Arrays.asList("&fWe currently support &aPolish&2, &aEnglish &flanguages.",
                                    "&7If doesn't exists your language please contact with us.",
                                    " &a&m  &2&m>&f https://lumberisland.com/contact",
                                    "",
                                    " &7We add a language if the application",
                                    " &7supports more than or have 100 people")));
                        }
                )
        );

        inventoryHelper.setItemRange(9, 27, new GodlyStack(Material.ORANGE_STAINED_GLASS_PANE).editMeta(itemMeta -> itemMeta.setDisplayName(" ")));

        inventoryHelper.setItemRange(27, 36, new GodlyStack(Material.GRAY_STAINED_GLASS_PANE)
                .editMeta(itemMeta -> {
                            itemMeta.setDisplayName(translate(" "));
                            itemMeta.setLore(translate(Arrays.asList("&fWe currently support &aPolish&2 and &aEnglish &flanguages.",
                                    "&7If doesn't exists your language please contact with us.",
                                    " &a&m  &2&m>&f https://lumberisland.com/contact",
                                    "",
                                    " &7We add a language if the application",
                                    " &7supports more than or have 100 people")));
                        }
                )
        );

        LangCache langCache = Main.getInstance().getLangCache();
        for (Lang lang : langCache.getLangMap().values()) {
            GodlyStack pol = new GodlyStack(Material.PLAYER_HEAD, 1, (short) 3)
                    .editMeta(itemMeta -> {
                        itemMeta.setDisplayName(translate(lang.get("lang.item.name", String.class)));
                        itemMeta.setLore(lang.getList("lang.item.lore")
                                .stream()
                                .map(s -> ChatColor.translateAlternateColorCodes('&', s)
                                        .replace("{PLAYERS}", lang.getLocalPlayerList().size()+""))
                                .toList());
                    });
            pol.updateSkullProperty(lang.get("lang.head", String.class));

            inventoryHelper.addItem(lang.get("lang.item.slot", int.class), pol);
        }

        inventoryHelper.addItem(30, new GodlyStack(Material.GREEN_STAINED_GLASS_PANE).editMeta(itemMeta -> itemMeta.setDisplayName(" ")));
        inventoryHelper.addItem(32, new GodlyStack(Material.GREEN_STAINED_GLASS_PANE).editMeta(itemMeta -> itemMeta.setDisplayName(" ")));

        inventoryHelper.addRawGodlyStack(31, godlyStack -> {
            godlyStack.updateSkullProperty("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTM1OWQ5MTI3NzI0MmZjMDFjMzA5YWNjYjg3YjUzM2YxOTI5YmUxNzZlY2JhMmNkZTYzYmY2MzVlMDVlNjk5YiJ9fX0=");
            godlyStack.setDisplayName("&7Set default language &cEnglish");
            godlyStack.setLore(Arrays.asList(" &7If you click this icon, you choose &eEnglish&7 else",
                    " &7you must look for other language."));
        }, new GodlyStack(Material.PLAYER_HEAD, 1, (short) 3));

        inventoryHelper.click(event1 -> event1.setCancelled(true));
        inventoryHelper.open(player);
    }
}
