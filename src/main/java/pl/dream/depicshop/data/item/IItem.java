package pl.dream.depicshop.data.item;

import org.bukkit.inventory.ItemStack;
import pl.dream.depicshop.data.LocalPlayer;

public interface IItem {
    void onClick(LocalPlayer localPlayer);
    ItemStack getItemStack();
}
