package pl.dream.depicshop;

import org.bukkit.plugin.java.JavaPlugin;

public final class DEpicShop extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
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
