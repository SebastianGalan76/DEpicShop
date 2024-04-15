package pl.dream.depicshop.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.dream.depicshop.DEpicShop;
import pl.dream.depicshop.data.LocalPlayer;

public class PlayerJoinListener implements Listener {
    private final DEpicShop plugin;

    public PlayerJoinListener(DEpicShop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        plugin.players.put(player.getUniqueId(), new LocalPlayer(plugin, player));
    }
}
