package pl.dream.depicshop;

import org.bukkit.plugin.java.JavaPlugin;
import pl.dream.depicshop.controller.ConfigController;
import pl.dream.depicshop.controller.ShopController;
import pl.dream.depicshop.data.LocalPlayer;
import pl.dream.depicshop.data.ShopCategory;
import pl.dream.depicshop.data.item.ShopItem;
import pl.dream.depicshop.listener.PlayerJoinListener;
import pl.dream.depicshop.listener.PlayerQuitListener;

import java.util.HashMap;
import java.util.UUID;

public final class DEpicShop extends JavaPlugin {
    public HashMap<UUID, LocalPlayer> players;
    public HashMap<String, ShopCategory> categories;
    public HashMap<String, ShopItem> shopItems;

    public ConfigController configController;

    @Override
    public void onEnable() {
        players = new HashMap<>();
        categories = new HashMap<>();
        shopItems = new HashMap<>();

        //Load listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

        loadPlugin();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reloadPlugin(){
        reloadConfig();

        loadPlugin();
    }

    private void loadPlugin(){
        categories.clear();
        shopItems.clear();

        Locale.loadMessages(this);

        saveDefaultConfig();
        configController = new ConfigController(this);
        new ShopController(this);
    }
}
