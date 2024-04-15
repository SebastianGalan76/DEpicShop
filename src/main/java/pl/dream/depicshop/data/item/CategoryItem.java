package pl.dream.depicshop.data.item;

import org.bukkit.inventory.ItemStack;
import pl.dream.depicshop.data.LocalPlayer;

public class CategoryItem extends Item implements IItem {
    private final String categoryName;

    public CategoryItem(ItemStack itemStack, String categoryName) {
        super(itemStack);
        this.categoryName = categoryName;
    }

    @Override
    public void onClick(LocalPlayer localPlayer) {
        localPlayer.openCategory(categoryName);
    }
}
