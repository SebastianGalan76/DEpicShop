package pl.dream.depicshop.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.dream.depicshop.DEpicShop;

public class PlayerQuitListener implements Listener {
    private final DEpicShop plugin;

    public PlayerQuitListener(DEpicShop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        plugin.players.remove(e.getPlayer().getUniqueId());
    }
}
