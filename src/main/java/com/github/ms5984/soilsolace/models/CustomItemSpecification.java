package com.github.ms5984.soilsolace.models;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Objects;

import static com.github.ms5984.soilsolace.SoilSolace.registerListener;

public abstract class CustomItemSpecification {
    protected final String displayName;
    protected final Material baseMaterial;
    protected transient ItemStack item;
    protected final NamespacedKey recipeKey;
    protected final Recipe recipe;

    protected CustomItemSpecification(String displayName, Material baseMaterial) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        this.baseMaterial = baseMaterial;
        generateItem();
        this.recipeKey = getRecipeKey();
        this.recipe = getRecipe();
        Bukkit.getServer().addRecipe(recipe);
        registerListener(blockCraftingListener());
    }

    protected abstract void generateItem();
    protected abstract NamespacedKey getRecipeKey();
    protected abstract Recipe getRecipe();
    protected Listener blockCraftingListener() {
        return new Listener() {
            @EventHandler(priority = EventPriority.HIGH)
            public void onBaseRecipePreCraft(PrepareItemCraftEvent e) {
                final Recipe recipe = e.getRecipe();
                if (recipeContainsItem(recipe)) {
                    return;
                }
                if (!e.getInventory().contains(item)) {
                    return;
                }
                e.getInventory().setResult(null);
            }
            @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
            public void onBaseRecipeCraft(CraftItemEvent e) {
                final Recipe recipe = e.getRecipe();
/*                if (recipe instanceof ShapedRecipe) {
                    final ShapedRecipe shaped = (ShapedRecipe) recipe;
                    if (shaped.getIngredientMap().values().stream().filter(Objects::nonNull).filter(i -> i.getType() == baseMaterial).anyMatch(item::isSimilar)) {
                        return;
                    }
                } else if (recipe instanceof ShapelessRecipe) {
                    final ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
                    if (shapeless.getIngredientList().stream().filter(i -> i.getType() == baseMaterial).anyMatch(item::isSimilar)) {
                        return;
                    }
                } else {
                    return;
                }*/
                if (recipeContainsItem(recipe)) {
                    return;
                }
                if (!e.getInventory().contains(item)) {
                    return;
                }
                e.setCancelled(true);
            }

            private boolean recipeContainsItem(final Recipe recipe) {
                if (recipe instanceof ShapedRecipe) {
                    final ShapedRecipe shaped = (ShapedRecipe) recipe;
                    return shaped.getIngredientMap().values().stream().filter(Objects::nonNull).filter(i -> i.getType() == baseMaterial).anyMatch(item::isSimilar);
                } else if (recipe instanceof ShapelessRecipe) {
                    final ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
                    return shapeless.getIngredientList().stream().filter(i -> i.getType() == baseMaterial).anyMatch(item::isSimilar);
                } else {
                    return false;
                }
            }
        };
    }

    public ItemStack getItem() {
        return item;
    }
}
