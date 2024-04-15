package pl.dream.depicshop.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import pl.dream.depicshop.DEpicShop;
import pl.dream.depicshop.inventory.CategoryInventory;

import java.util.List;

public class LocalPlayer {
    private final DEpicShop plugin;

    public final Player player;
    public final Path path;

    public ShopCategory openedShopCategory;

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

    public ItemStack getPlayerHead(){
        ItemStack itemStack = plugin.configController.playerHead.clone();

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName()
                .replace("{PLAYER}", player.getDisplayName()));

        List<String> lore = itemMeta.getLore();
        if(lore!=null){
            for(int i=0;i<lore.size();i++){
                String line = lore.get(i);
                //TODO load player balance
                line = line.replace("{BALANCE_MONEY}", "")
                        .replace("{BALANCE_TOKENS}", "")
                        .replace("{BALANCE_TIME}", "");
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
