package pl.dream.depicshop.data.item;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import pl.dream.depicshop.data.LocalPlayer;

import java.util.List;

public class CommandItem extends Item {
    private final List<String> commands;

    public CommandItem(ItemStack itemStack, List<String> commands) {
        super(itemStack);

        this.commands = commands;
    }

    @Override
    public void onClick(LocalPlayer localPlayer) {
        for(String command:commands){
            localPlayer.player.performCommand(command
                    .replace("{PLAYER}", localPlayer.player.getName())
                    .replace("/",""));
        }
    }
}
