package com.github.ms5984.soilsolace.models;

import com.github.ms5984.soilsolace.SoilSolace;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Smoker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class Macerator {
/*    public static class MaceratorListener implements Listener {
        private static final ItemStack stoneDust = SoilSolace.Dust.Stone.spec.getItem();

        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void onMaceratorPlace(BlockPlaceEvent e) {
            if (!e.getItemInHand().isSimilar(maceratorSpec.template)) {
                return;
            }
            new Macerator(e);
        }
        @EventHandler
        public void onMaceratorInteract(PlayerInteractEvent e) {
            if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
                return;
            }
            final Block clickedBlock = e.getClickedBlock();
            if (clickedBlock == null || clickedBlock.getType() != Material.SMOKER) {
                return;
            }
            final Smoker smoker = (Smoker) clickedBlock.getState();
            final PersistentDataContainer pdc = smoker.getPersistentDataContainer();
            if (!pdc.has(maceratorSpec.blockDataKey, PersistentDataType.STRING)) {
                return;
            }
            // TODO Decide what happens when they interact with Macerator
        }
        @EventHandler(priority = EventPriority.LOWEST)
        public void onSmoke(FurnaceSmeltEvent e) {
            final Block block = e.getBlock();
            if (block.getType() != Material.SMOKER) {
                return;
            }
            final Smoker smoker = (Smoker) block.getState();
            final PersistentDataContainer pdc = smoker.getPersistentDataContainer();
            if (!pdc.has(maceratorSpec.blockDataKey, PersistentDataType.STRING)) {
                e.setCancelled(true);
                smoker.update();
            }
        }
        @EventHandler
        public void onSmokerItemAdd(InventoryClickEvent e) {
            final Inventory clickedInventory = e.getClickedInventory();
            if (e.isShiftClick()) {
                final ItemStack currentItem = e.getCurrentItem();
                if (currentItem == null || !(clickedInventory instanceof PlayerInventory)) {
                    return;
                }
                final Inventory inventory = e.getInventory();
                if (!(inventory instanceof FurnaceInventory)) {
                    return;
                }
            } else {
                final ItemStack itemOnCursor = e.getCursor();
                if (itemOnCursor == null || !(clickedInventory instanceof FurnaceInventory)) {
                    return;
                }
                if (!itemOnCursor.isSimilar(stoneDust)) {
                    return;
                }
            }
            if (e.getView().getTitle().equals(maceratorSpec.title)) {
                return;
            }
            e.setCancelled(true);
        }
        @EventHandler
        public void onSmokerItemAdd(InventoryMoveItemEvent e) {
            if (!(e.getDestination() instanceof FurnaceInventory)) {
                return;
            }
            final ItemStack currentItem = e.getItem();
            if (!currentItem.isSimilar(new ItemStack(Material.COBBLESTONE))) {
                return;
            }
            e.setCancelled(true);
        }
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent e) {
            e.getPlayer().getInventory().addItem(maceratorSpec.template);
            e.getPlayer().getInventory().addItem(stoneDust);
        }
    }*/
//    private static ItemStack maceratorTemplate;
//    private static ItemStack stoneDust;
//    private static NamespacedKey recipeKey;
//    private static NamespacedKey maceratorData;
    private static CustomBlockSpecification maceratorSpec;

    private final StoredLocation location;
    private transient Block block;

    private Macerator(BlockPlaceEvent e) {
        this.block = e.getBlock();
        this.location = new StoredLocation(block);
        SoilSolace.scheduleSyncTask(new BukkitRunnable() {
            @Override
            public void run() {
                if (block == null) {
                    final World world = Bukkit.getWorld(location.worldUid);
                    if (world == null) return;
                    block = world.getBlockAt(location.x, location.y, location.z);
                }
                final Smoker smoker = (Smoker) block.getState();
                final PersistentDataContainer pdc = smoker.getPersistentDataContainer();
                pdc.set(maceratorSpec.blockDataKey, PersistentDataType.STRING, "");
                smoker.update();
            }
        });
    }

/*    private static void generateTemplate() {
        maceratorTemplate = new ItemStack(Material.SMOKER);
        final ItemMeta itemMeta = maceratorTemplate.getItemMeta();
        if (itemMeta == null) return;
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8Macerator"));
        maceratorTemplate.setItemMeta(itemMeta);
    }*/

/*    private static void generateDust() {
        stoneDust = new ItemStack(Material.GUNPOWDER);
        final ItemMeta stoneDustMeta = stoneDust.getItemMeta();
        if (stoneDustMeta == null) return;
        stoneDustMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&fStone Dust"));
        stoneDustMeta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        stoneDustMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stoneDust.setItemMeta(stoneDustMeta);
    }*/

/*    private static void registerRecipe(SoilSolace soilSolace) {
        final ShapedRecipe shapedRecipe = new ShapedRecipe(recipeKey, maceratorTemplate);
        shapedRecipe.shape("WSW", "WFW", "WWW");
        shapedRecipe.setIngredient('W', new RecipeChoice.MaterialChoice(Tag.PLANKS));
        shapedRecipe.setIngredient('S', Material.STONECUTTER);
        shapedRecipe.setIngredient('F', Material.FURNACE);
        soilSolace.getServer().addRecipe(shapedRecipe);
    }*/

    public static void initializeStatic(SoilSolace soilSolace) {
//        recipeKey = new NamespacedKey(soilSolace, "macerator");
//        maceratorData = new NamespacedKey(soilSolace, "macerator-data");
//        generateTemplate();
//        generateDust();
//        registerRecipe(soilSolace);
        maceratorSpec = new CustomBlockSpecification(soilSolace, "&8Macerator", Material.SMOKER) {
            @Override
            protected void generateTemplate() {
                template = new ItemStack(material);
                final ItemMeta itemMeta = template.getItemMeta();
                if (itemMeta == null) return;
                itemMeta.setDisplayName(title);
                template.setItemMeta(itemMeta);
            }

            @Override
            protected NamespacedKey getRecipeKey() {
                return new NamespacedKey(plugin, "macerator");
            }

            @Override
            protected NamespacedKey getBlockDataKey() {
                return new NamespacedKey(plugin, "macerator-data");
            }

            @Override
            protected void registerRecipe() {
                final ShapedRecipe shapedRecipe = new ShapedRecipe(recipeKey, template);
                shapedRecipe.shape("WSW", "WFW", "WWW");
                shapedRecipe.setIngredient('W', new RecipeChoice.MaterialChoice(Tag.PLANKS));
                shapedRecipe.setIngredient('S', Material.STONECUTTER);
                shapedRecipe.setIngredient('F', Material.FURNACE);
                plugin.getServer().addRecipe(shapedRecipe);
            }

            @Override
            protected Listener eventHandler() {
                return new Listener() {
                    private final ItemStack stoneDustInput = ((SmokingRecipe) SoilSolace.Dust.Stone.spec.recipe).getInput();
                    private final ItemStack stoneDust = SoilSolace.Dust.Stone.spec.getItem();

                    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
                    public void onMaceratorPlace(BlockPlaceEvent e) {
                        if (!e.getItemInHand().isSimilar(maceratorSpec.template)) {
                            return;
                        }
                        new Macerator(e);
                    }
                    @EventHandler
                    public void onMaceratorInteract(PlayerInteractEvent e) {
                        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
                            return;
                        }
                        final Block clickedBlock = e.getClickedBlock();
                        if (clickedBlock == null || clickedBlock.getType() != Material.SMOKER) {
                            return;
                        }
                        final Smoker smoker = (Smoker) clickedBlock.getState();
                        final PersistentDataContainer pdc = smoker.getPersistentDataContainer();
                        if (!pdc.has(maceratorSpec.blockDataKey, PersistentDataType.STRING)) {
                            return;
                        }
                        // TODO Decide what happens when they interact with Macerator
                    }
/*                    @EventHandler(priority = EventPriority.LOWEST)
                    public void onSmoke(FurnaceSmeltEvent e) {
                        final Block block = e.getBlock();
                        if (block.getType() != Material.SMOKER) {
                            return;
                        }
                        final Smoker smoker = (Smoker) block.getState();
                        final PersistentDataContainer pdc = smoker.getPersistentDataContainer();
                        if (!pdc.has(maceratorSpec.blockDataKey, PersistentDataType.STRING)) {
                            e.setCancelled(true);
                            smoker.update();
                        }
                    }*/
                    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
                    public void onSmokerBurnWithItem(FurnaceBurnEvent e) {
                        final Block block = e.getBlock();
                        if (block.getType() != Material.SMOKER) {
                            return;
                        }
                        final Smoker smoker = (Smoker) block.getState();
                        final ItemStack smelting = smoker.getInventory().getSmelting();
                        if (smelting == null || !smelting.isSimilar(stoneDustInput)) {
                            return;
                        }
                        final String customName = smoker.getCustomName();
                        if (customName != null && customName.equals(maceratorSpec.title)) {
                            return;
                        }
                        e.setCancelled(true);
                    }
                    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
                    public void onSmokerItemAdd(InventoryClickEvent e) {
                        if (e.getView().getTitle().equals(maceratorSpec.title)) {
                            return;
                        }
                        final Inventory clickedInventory = e.getClickedInventory();
                        if (e.isShiftClick()) {
                            final ItemStack currentItem = e.getCurrentItem();
                            if (currentItem == null || !(clickedInventory instanceof PlayerInventory)) {
                                return;
                            }
                            final Inventory inventory = e.getView().getTopInventory();
                            if (!(inventory instanceof FurnaceInventory)) {
                                return;
                            }
                            if (!currentItem.isSimilar(stoneDustInput)) {
                                return;
                            }
                        } else {
                            final ItemStack itemOnCursor = e.getCursor();
                            if (itemOnCursor == null || clickedInventory == null) {
                                return;
                            }
                            if (clickedInventory.getType() != InventoryType.SMOKER) {
                                return;
                            }
                            if (!itemOnCursor.isSimilar(stoneDustInput)) {
                                return;
                            }
                        }
                        e.setCancelled(true);
                    }
                    @EventHandler
                    public void onSmokerItemAddByHopper(InventoryMoveItemEvent e) {
                        if (!(e.getDestination() instanceof FurnaceInventory)) {
                            return;
                        }
                        final ItemStack currentItem = e.getItem();
                        if (!currentItem.isSimilar(new ItemStack(Material.COBBLESTONE))) {
                            return;
                        }
                        e.setCancelled(true);
                    }
                    @EventHandler
                    public void onPlayerJoin(PlayerJoinEvent e) {
                        e.getPlayer().getInventory().addItem(maceratorSpec.template);
                        e.getPlayer().getInventory().addItem(stoneDust);
                    }
                };
            }
        };
//        soilSolace.getServer().getPluginManager().registerEvents(new MaceratorListener(), soilSolace);
    }
/*
    private static void unregisterRecipe(SoilSolace soilSolace) {
        soilSolace.getServer().removeRecipe(recipeKey);
    }*/
}
