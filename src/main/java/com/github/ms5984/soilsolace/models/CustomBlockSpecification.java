package com.github.ms5984.soilsolace.models;

import com.github.ms5984.soilsolace.SoilSolace;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class CustomBlockSpecification {
    protected final SoilSolace plugin;
    protected final String title;
    protected final Material material;
    protected transient ItemStack template;
    protected final NamespacedKey recipeKey;
    protected final NamespacedKey blockDataKey;

    public CustomBlockSpecification(SoilSolace plugin, String title, Material material) {
        this.plugin = plugin;
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.material = material;
        this.recipeKey = getRecipeKey();
        this.blockDataKey = getBlockDataKey();
        generateTemplate();
        registerRecipe();
        final Listener listener = eventHandler();
        if (listener != null) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    protected abstract void generateTemplate();
    protected abstract NamespacedKey getRecipeKey();
    protected abstract NamespacedKey getBlockDataKey();
    protected abstract void registerRecipe();
    private void unregisterRecipe() {
        plugin.getServer().removeRecipe(getRecipeKey());
    }
    protected Listener eventHandler() {
        return null;
    }

    public ItemStack getTemplate() {
        return template;
    }
    public String getTitle() {
        return title;
    }
    public Material getType() {
        return material;
    }
}
