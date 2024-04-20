package pl.dream.depicshop.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import pl.dream.depicshop.DEpicShop;
import pl.dream.depicshop.Utils;
import pl.dream.depicshop.data.item.ShopItem;
import pl.dream.depicshop.data.price.PriceType;
import pl.dream.depicshop.inventory.CategoryInventory;
import pl.dream.depicshop.inventory.ItemInventory;

import java.util.List;

public class LocalPlayer {
    private final DEpicShop plugin;

    public final Player player;
    public final Path path;

    public ShopCategory openedShopCategory;
    public ItemInventory openedItemInventory;

    public LocalPlayer(DEpicShop plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        path = new Path();
    }

    public void openCategory(String categoryName){
        openedShopCategory = plugin.categories.get(categoryName);

        if(openedShopCategory!=null){
            path.moveForward(categoryName);

            player.openInventory(new CategoryInventory(plugin, this, openedShopCategory).getInventory());
        }
        else{
            Bukkit.getLogger().warning("LocalPlayer#openCategory(). Incorrect shop category name: "+categoryName);
        }
    }

    public void openItemInventory(ShopItem shopItem){
        path.moveForward("SHOP_ITEM");
        openedItemInventory = new ItemInventory(plugin, shopItem, this);

        player.openInventory(openedItemInventory.getInventory());
    }

    public double getBalance(PriceType priceType){
        //TODO load player balances
        switch (priceType){
            case DEFAULT:
                return 0;
            case TOKEN:
                return 0;
            case TIME:
                return 0;
        }

        return -1;
    }

    public ItemStack getPlayerHead(){
        ItemStack itemStack = plugin.configController.playerHeadItem.clone();

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName()
                .replace("{PLAYER}", player.getDisplayName()));

        List<String> lore = itemMeta.getLore();
        if(lore!=null){
            for(int i=0;i<lore.size();i++){
                String line = lore.get(i);
                //TODO load player balance
                line = line.replace("{BALANCE_MONEY}", Utils.getMoneyFormat(getBalance(PriceType.DEFAULT)))
                        .replace("{BALANCE_TOKEN}", Utils.getMoneyFormat(getBalance(PriceType.TOKEN)))
                        .replace("{BALANCE_TIME}", Utils.getMoneyFormat(getBalance(PriceType.TIME)));
                lore.set(i, line);
            }

            itemMeta.setLore(lore);
        }

        SkullMeta skullMeta = (SkullMeta) itemMeta;
        skullMeta.setOwningPlayer(player);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
