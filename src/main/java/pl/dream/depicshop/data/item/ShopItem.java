package pl.dream.depicshop.data.item;

import org.bukkit.inventory.ItemStack;
import pl.dream.depicshop.data.LocalPlayer;
import pl.dream.depicshop.data.price.Price;

public class ShopItem extends Item {
    public final Price buyPrice;
    public final Price sellPrice;

    public ShopItem(ItemStack itemStack, Price buyPrice, Price sellPrice) {
        super(itemStack);

        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    @Override
    public void onClick(LocalPlayer localPlayer) {

    }
}
