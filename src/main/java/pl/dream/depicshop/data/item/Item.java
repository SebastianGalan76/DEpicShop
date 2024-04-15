package pl.dream.depicshop.data.item;

import org.bukkit.inventory.ItemStack;

public class Item {
    private final ItemStack itemStack;

    public Item(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack(){
        return itemStack.clone();
    }
}
