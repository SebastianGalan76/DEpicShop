package pl.dream.depicshop.data;

import org.bukkit.entity.Player;
import pl.dream.depicshop.DEpicShop;

public class LocalPlayer {
    private final DEpicShop plugin;
    private final Player player;

    public final Path path;

    public LocalPlayer(DEpicShop plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        path = new Path();
    }
}
