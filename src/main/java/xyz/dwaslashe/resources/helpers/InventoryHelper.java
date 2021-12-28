package xyz.dwaslashe.resources.helpers;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Author ~WuShei
 */
public class InventoryHelper implements Listener {

    private static final Map<String, InventoryHelper> inventoryHelperMap = new HashMap<>();

    public static Map<String, InventoryHelper> getInventoryHelperMap() {
        return inventoryHelperMap;
    }

    public static InventoryHelper createEmptyToEvent(){
        return new InventoryHelper();
    }

    @EventHandler(ignoreCancelled = true)
    private void onClick(InventoryClickEvent event){
        if(event.getCurrentItem() == null) return;

        InventoryHelper inventoryHelper = InventoryHelper.inventoryHelperMap.get(event.getWhoClicked().getName());
        if(inventoryHelper == null || inventoryHelper.eventConsumer == null) return;

        inventoryHelper.eventConsumer.accept(event);
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event){
        InventoryHelper.inventoryHelperMap.remove(event.getPlayer().getName());
    }

    private String title;
    private Inventory inventory;
    private Player player;
    private Consumer<InventoryClickEvent> eventConsumer;

    private InventoryHelper(){}

    public InventoryHelper(Inventory inventory){
        this.inventory = inventory;
    }

    public InventoryHelper(Player player, String title, int sizeInRows){
        this.title = title;
        this.inventory = Bukkit.createInventory(player, sizeInRows * 9, ChatColor.translateAlternateColorCodes('&', title));
        this.player = player;
    }

    public InventoryHelper(String title, int sizeInRows){
        this(null, title, sizeInRows);
    }

    public InventoryHelper(Player player, String title, InventoryType inventoryType){
        this.title = title;
        this.inventory = Bukkit.createInventory(player, inventoryType, ChatColor.translateAlternateColorCodes('&', title));
        this.player = player;
    }

    public InventoryHelper(String title, InventoryType inventoryType){
        this(null, title, inventoryType);
    }

    public void addItem(int slot, ItemStack itemStack){
        inventory.setItem(slot, itemStack);
    }

    public void addItem(ItemStack... itemStacks){
        inventory.addItem(itemStacks);
    }

    public void clearItemSlot(int slot){
        inventory.setItem(slot, new ItemStack(Material.AIR));
    }

    public void fillInventory(ItemStack itemStack){
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, itemStack);
        }
    }

    private void checkBounds(int start, int end){
        if(start > end) throw new IndexOutOfBoundsException("Start is higher than end");
        if(start < 0 || end <= 0) throw new IndexOutOfBoundsException("One from parameters equals zero");
    }

    public void setItemRange(int start, int end, ItemStack itemStack){
        checkBounds(start, end);
        for (int i = 0; i < inventory.getSize(); i++) {
            if(i >= start && i < end)
                inventory.setItem(i, itemStack);
        }
    }

    public void addRawItemStack(int slot, Consumer<ItemStack> itemStackConsumer, Material material){
        ItemStack itemStack = new ItemStack(material);
        itemStackConsumer.accept(itemStack);
        inventory.setItem(slot, itemStack);
    }

    public void addRawItemStack(int slot, Consumer<ItemStack> itemStackConsumer, ItemStack itemStack){
        itemStackConsumer.accept(itemStack);
        inventory.setItem(slot, itemStack);
    }

    public void addRawGodlyStack(int slot, Consumer<GodlyStack> godlyStackConsumer, GodlyStack godlyStack){
        godlyStackConsumer.accept(godlyStack);
        inventory.setItem(slot, godlyStack);
    }

    public void fillInventoryWithRawItemStack(Consumer<ItemStack> itemStackConsumer, Material material){
        ItemStack itemStack = new ItemStack(material);
        itemStackConsumer.accept(itemStack);
        fillInventory(itemStack);
    }

    public void click(Consumer<InventoryClickEvent> eventConsumer){
        this.eventConsumer = eventConsumer;
    }

    public String getTitle() {
        return title;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public synchronized void open(Player player){
        this.player = player;
        inventoryHelperMap.remove(player.getName());
        inventoryHelperMap.put(player.getName(), this);
        player.openInventory(inventory);
    }

}
