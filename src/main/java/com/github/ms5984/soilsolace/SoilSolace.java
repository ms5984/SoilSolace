package com.github.ms5984.soilsolace;

import com.github.ms5984.soilsolace.models.CustomItemSpecification;
import com.github.ms5984.soilsolace.models.Macerator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Collections;

public final class SoilSolace extends JavaPlugin {

    private static SoilSolace instance;
    public enum Dust {
        Stone(new CustomItemSpecification("&7&oStone Dust", Material.GUNPOWDER) {
            @Override
            protected void generateItem() {
                item = new ItemStack(baseMaterial);
                final ItemMeta stoneDustMeta = item.getItemMeta();
                if (stoneDustMeta == null) return;
                stoneDustMeta.setDisplayName(displayName);
                stoneDustMeta.setLore(Arrays.asList(
                        ChatColor.translateAlternateColorCodes('&', "&7&oFinely ground cobbles...perhaps"),
                        ChatColor.translateAlternateColorCodes('&', "&7&osomething can be made with this?")));
//                stoneDustMeta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
//                stoneDustMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(stoneDustMeta);
            }

            @Override
            protected NamespacedKey getRecipeKey() {
                return new NamespacedKey(instance, "stone_dust");
            }

            @Override
            protected Recipe getRecipe() {
                final ItemStack result = new ItemStack(item);
                result.setAmount(2);
                return new SmokingRecipe(recipeKey, result, Material.COBBLESTONE, 0.2f, 120);
            }
        }), LooseSand(new CustomItemSpecification("&fLoose Sand", Material.SUGAR) {
            @Override
            protected void generateItem() {
                item = new ItemStack(baseMaterial);
                final ItemMeta looseSandMeta = item.getItemMeta();
                if (looseSandMeta == null) return;
                looseSandMeta.setDisplayName(displayName);
                looseSandMeta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&7&oSifted sand?")));
                looseSandMeta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
                looseSandMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(looseSandMeta);
            }

            @Override
            protected NamespacedKey getRecipeKey() {
                return new NamespacedKey(instance, "loose_sand");
            }

            @Override
            protected Recipe getRecipe() {
                final ItemStack result = new ItemStack(item);
                result.setAmount(4);
                final ShapelessRecipe shapelessRecipe = new ShapelessRecipe(recipeKey, result);
                shapelessRecipe.addIngredient(Material.SAND);
                return shapelessRecipe;
            }
        });

        public final CustomItemSpecification spec;

        Dust(CustomItemSpecification itemSpecification) {
            this.spec = itemSpecification;
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
//        new Conf(this);
        instance = this;
        Macerator.initializeStatic(this);
        setupDirtRecipe();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @SuppressWarnings("deprecation")
    private void setupDirtRecipe() {
        final ShapedRecipe dirtRecipe1 = new ShapedRecipe(new NamespacedKey(this, "Dirt"), new ItemStack(Material.DIRT, 4));
        dirtRecipe1.shape("SLS", "LCL", "SLS");
        dirtRecipe1.setIngredient('S', new RecipeChoice.ExactChoice(Dust.Stone.spec.getItem()));
        dirtRecipe1.setIngredient('L', new RecipeChoice.ExactChoice(Dust.LooseSand.spec.getItem()));
        dirtRecipe1.setIngredient('C', new RecipeChoice.MaterialChoice(Material.WHEAT, Material.DRIED_KELP));
        getServer().addRecipe(dirtRecipe1);
//        final ShapedRecipe dirtRecipe2 = new ShapedRecipe(new NamespacedKey(this, "Dirt"), new ItemStack(Material.DIRT, 8));
//        dirtRecipe2.shape("SSS", "SCS", "SSS");
//        dirtRecipe2.setIngredient('S', new RecipeChoice.ExactChoice(Dust.Stone.spec.getItem()));
//        dirtRecipe2.setIngredient('C', new RecipeChoice.MaterialChoice(Material.WHEAT, Material.DRIED_KELP));
//        getServer().addRecipe(dirtRecipe2);
    }

    public static void scheduleAsyncTask(BukkitRunnable bukkitRunnable) {
        if (instance == null) {
            return;
        }
        bukkitRunnable.runTaskAsynchronously(instance);
    }
    public static void scheduleSyncTask(BukkitRunnable bukkitRunnable) {
        if (instance == null) {
            return;
        }
        bukkitRunnable.runTaskLater(instance, 1L);
    }
    public static void registerListener(Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }
}
