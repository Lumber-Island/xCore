package xyz.dwaslashe.resources.helpers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.dwaslashe.lang.helpers.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @Author ~WuShei
 */
public class GodlyStack extends ItemStack {

    public GodlyStack(Material type) {
        super(type);
    }

    public GodlyStack(Material type, int amount) {
        super(type, amount);
    }

    public GodlyStack(Material type, int amount, short damage) {
        super(type, amount, damage);
    }

    public GodlyStack(Material type, int amount, short damage, Byte data) {
        super(type, amount, damage, data);
    }

    public GodlyStack(ItemStack stack) throws IllegalArgumentException {
        super(stack);
    }

    public GodlyStack editMeta(Consumer<ItemMeta> itemMetaConsumer){
        ItemMeta meta = getItemMeta();
        itemMetaConsumer.accept(meta);
        setItemMeta(meta);
        return this;
    }

    public boolean isSimilar(ItemStack stack, SimilarParameter ignoredParameter){
        if(stack == null) return false;
        else if(stack == this) return true;
        else {
            Material comparisonType = this.getType().isLegacy() ? Bukkit.getUnsafe().fromLegacy(this.getData(), true) : this.getType();
            if(ignoredParameter.equals(SimilarParameter.MATERIAL)){
                return this.getDurability() == stack.getDurability() && this.hasItemMeta() == stack.hasItemMeta() && (!this.hasItemMeta() || Bukkit.getItemFactory().equals(this.getItemMeta(), stack.getItemMeta()));
            } else if(ignoredParameter.equals(SimilarParameter.DURABILITY)){
                return comparisonType == stack.getType() && this.hasItemMeta() == stack.hasItemMeta() && (!this.hasItemMeta() || Bukkit.getItemFactory().equals(this.getItemMeta(), stack.getItemMeta()));
            }
        }
        return isSimilar(stack);
    }

    public List<String> getLore(){
        return getItemMeta() != null && getItemMeta().hasLore() ? getItemMeta().getLore() : new ArrayList<>();
    }

    public void setLore(List<String> lore){
        editMeta(itemMeta -> itemMeta.setLore(lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).toList()));
    }

    public void setDisplayName(String displayName){
        editMeta(itemMeta -> itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName)));
    }

    public void setUnbreakable(boolean unbreakable){
        editMeta(itemMeta -> itemMeta.setUnbreakable(unbreakable));
    }

    public void addItemFlag(ItemFlag... itemFlags){
        editMeta(itemMeta -> itemMeta.addItemFlags(itemFlags));
    }

    public void removeItemFlag(ItemFlag... itemFlags){
        editMeta(itemMeta -> itemMeta.removeItemFlags(itemFlags));
    }

    public void setCustomModelData(int data){
        editMeta(itemMeta -> itemMeta.setCustomModelData(data));
    }

    public void addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier){
        editMeta(itemMeta -> itemMeta.addAttributeModifier(attribute, attributeModifier));
    }

    public void removeAttributeModifier(Attribute attribute){
        editMeta(itemMeta -> itemMeta.removeAttributeModifier(attribute));
    }

    public void updateSkullProperty(String value){
        SkullMeta skullMeta = (SkullMeta) getItemMeta();
        if(skullMeta == null) return;

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
        gameProfile.getProperties().put("textures", new Property("textures", value));

        ReflectionHelper.setFieldValue(skullMeta, "profile", gameProfile);
        setItemMeta(skullMeta);
    }

    @Deprecated
    @Override
    public ItemMeta getItemMeta() {
        return super.getItemMeta();
    }

    public enum SimilarParameter {

        DURABILITY,
        MATERIAL,


    }
}
