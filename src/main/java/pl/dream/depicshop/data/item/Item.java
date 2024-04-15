package pl.dream.depicshop.data.item;

import org.bukkit.inventory.ItemStack;
import pl.dream.depicshop.data.LocalPlayer;

public class Item implements IItem {
    private final ItemStack itemStack;

    public Item(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack(){
        return itemStack.clone();
    }

    @Override
    public void onClick(LocalPlayer localPlayer) {

    }
}
