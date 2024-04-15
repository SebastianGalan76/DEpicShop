package pl.dream.depicshop;

import org.bukkit.plugin.java.JavaPlugin;
import pl.dream.depicshop.data.LocalPlayer;
import pl.dream.depicshop.data.ShopCategory;
import pl.dream.depicshop.listener.PlayerJoinListener;
import pl.dream.depicshop.listener.PlayerQuitListener;

import java.util.HashMap;
import java.util.UUID;

public final class DEpicShop extends JavaPlugin {
    public HashMap<UUID, LocalPlayer> players;
    public HashMap<String, ShopCategory> categories;

    @Override
    public void onEnable() {
        players = new HashMap<>();
        categories = new HashMap<>();

        //Load listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
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
        Locale.loadMessages(this);

        saveDefaultConfig();
    }
}
