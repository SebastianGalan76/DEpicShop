package pl.dream.depicshop.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.dream.depicshop.DEpicShop;
import pl.dream.depicshop.Locale;
import pl.dream.dreamlib.Message;

public class ShopCommand implements CommandExecutor {
    private final DEpicShop plugin;

    public ShopCommand(DEpicShop plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length==0){
            if(sender instanceof Player){
                plugin.players.get(((Player)sender).getUniqueId()).openCategory("main");
            }
        }
        if(args.length==1){
            if(args[0].equalsIgnoreCase("reload")){
                if(sender.hasPermission("depicshop.shop.reload")){
                    plugin.reloadPlugin();
                }
                else{
                    Message.sendMessage(sender, Locale.NO_PERMISSION.getText());
                }
            }
        }

        return true;
    }
}
