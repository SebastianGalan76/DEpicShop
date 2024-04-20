package pl.dream.depicshop.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.dream.depicshop.DEpicShop;
import pl.dream.depicshop.Utils;
import pl.dream.depicshop.data.LocalPlayer;
import pl.dream.depicshop.data.item.ShopItem;
import pl.dream.dreamlib.Color;
import pl.dream.dreamlib.ProtectedInventory;

import java.util.List;

public class ItemInventory extends ProtectedInventory {
    private final DEpicShop plugin;
    private final ShopItem shopItem;
    private final LocalPlayer localPlayer;
    private int amount;

    public ItemInventory(DEpicShop plugin, ShopItem shopItem, LocalPlayer localPlayer) {
        setInventory(Bukkit.createInventory(this, 54, Color.fixAll("&cSklep")));

        this.plugin = plugin;
        this.shopItem = shopItem;
        this.localPlayer = localPlayer;
        this.amount = 0;

        ItemStack addAmountItem = plugin.configController.addAmountItem;
        inv.setItem(10, getConvertedItemStack(addAmountItem.clone(), 1));
        inv.setItem(19, getConvertedItemStack(addAmountItem.clone(), 8));
        inv.setItem(28, getConvertedItemStack(addAmountItem.clone(), 16));
        inv.setItem(37, getConvertedItemStack(addAmountItem.clone(), 64));

        ItemStack subtractAmountItem = plugin.configController.subtractAmountItem;
        inv.setItem(16, getConvertedItemStack(subtractAmountItem.clone(), 1));
        inv.setItem(25, getConvertedItemStack(subtractAmountItem.clone(), 8));
        inv.setItem(34, getConvertedItemStack(subtractAmountItem.clone(), 16));
        inv.setItem(43, getConvertedItemStack(subtractAmountItem.clone(), 64));

        inv.setItem(13, getConvertedItemStack(shopItem.getItemStack(), 0));

        inv.setItem(45, localPlayer.getPlayerHead());
        inv.setItem(49, plugin.configController.moveBackItem);
    }

    private ItemStack getConvertedItemStack(ItemStack itemStack, int value){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(convertPlaceholders(itemMeta.getDisplayName(), value));

        List<String> lore = itemMeta.getLore();
        if(lore!=null && !lore.isEmpty()){
            for(int i=0;i<lore.size();i++){
                String line = convertPlaceholders(lore.get(i), value);
                lore.set(i, line);
            }
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private String convertPlaceholders(String text, int value){
        text = text.replace("{VALUE}", String.valueOf(value))
                .replace("{AMOUNT}", String.valueOf(amount));

        if(shopItem.buyPrice!=null){
            text = text.replace("{PRICE_BUY}", Utils.getMoneyFormat(shopItem.buyPrice.getDefaultValueForAmount(amount)));
        }
        if(shopItem.sellPrice!=null){
            text = text.replace("{PRICE_SELL}", Utils.getMoneyFormat(shopItem.sellPrice.getDefaultValueForAmount(amount)));
        }

        return text;
    }
}
