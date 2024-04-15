package pl.dream.depicshop.controller;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.dream.depicshop.DEpicShop;
import pl.dream.dreamlib.Config;

public class ConfigController {
    private final DEpicShop plugin;
    private final FileConfiguration config;

    public ItemStack playerHead;
    public ItemStack moveBackItem;
    public ItemStack nextPageExist, nextPageNotExist;
    public ItemStack previousPageExist, previousPageNotExist;

    public ConfigController(DEpicShop plugin) {
        this.plugin = plugin;

        this.config = plugin.getConfig();

        loadItems();
    }

    private void loadItems(){
        playerHead = Config.getItemStack(config, "items.playerHead");

        moveBackItem = Config.getItemStack(config, "items.moveBack");

        nextPageExist = Config.getItemStack(config, "items.nextPage.exist");
        nextPageNotExist = Config.getItemStack(config, "items.nextPage.notExist");

        previousPageExist = Config.getItemStack(config, "items.previousPage.exist");
        previousPageNotExist= Config.getItemStack(config, "items.previousPage.notExist");
    }
}
