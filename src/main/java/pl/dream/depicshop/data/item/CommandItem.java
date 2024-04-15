package pl.dream.depicshop.data.item;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import pl.dream.depicshop.data.LocalPlayer;

import java.util.List;

public class CommandItem extends Item implements IItem {
    private final List<String> commands;
    private final CommandSender console;

    public CommandItem(ItemStack itemStack, List<String> commands) {
        super(itemStack);

        this.commands = commands;
        console = Bukkit.getConsoleSender();
    }

    @Override
    public void onClick(LocalPlayer localPlayer) {
        for(String command:commands){
            Bukkit.dispatchCommand(console, command.replace("{PLAYER}", localPlayer.player.getName()));
        }
    }
}
