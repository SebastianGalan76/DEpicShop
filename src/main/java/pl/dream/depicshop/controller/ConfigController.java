package pl.dream.depicshop.controller;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.dream.depicshop.DEpicShop;
import pl.dream.depicshop.data.price.Price;
import pl.dream.depicshop.data.price.PriceTime;
import pl.dream.depicshop.data.price.PriceToken;
import pl.dream.dreamlib.Config;

public class ConfigController {
    private final DEpicShop plugin;
    private final FileConfiguration config;

    public ItemStack playerHeadItem;
    public ItemStack moveBackItem;
    public ItemStack nextPageExistItem, nextPageNotExistItem;
    public ItemStack previousPageExistItem, previousPageNotExistItem;
    public ItemStack addAmountItem, subtractAmountItem;


    public ConfigController(DEpicShop plugin) {
        this.plugin = plugin;

        this.config = plugin.getConfig();

        loadItems();
        loadPrices();
    }

    private void loadItems(){
        playerHeadItem = Config.getItemStack(config, "items.playerHead");

        moveBackItem = Config.getItemStack(config, "items.moveBack");

        nextPageExistItem = Config.getItemStack(config, "items.nextPage.exist");
        nextPageNotExistItem = Config.getItemStack(config, "items.nextPage.notExist");

        previousPageExistItem = Config.getItemStack(config, "items.previousPage.exist");
        previousPageNotExistItem = Config.getItemStack(config, "items.previousPage.notExist");

        addAmountItem = Config.getItemStack(config, "items.addAmount");
        subtractAmountItem = Config.getItemStack(config, "items.subtractAmount");
    }

    private void loadPrices(){
        Price.symbol = config.getString("prices.default.symbol");
        Price.color = config.getString("prices.default.color");

        PriceToken.symbol = config.getString("prices.token.symbol");
        PriceToken.color = config.getString("prices.token.color");

        PriceTime.symbol = config.getString("prices.time.symbol");
        PriceTime.color = config.getString("prices.time.color");
    }
}
