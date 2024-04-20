package pl.dream.depicshop.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.dream.depicshop.DEpicShop;
import pl.dream.depicshop.data.LocalPlayer;
import pl.dream.depicshop.data.ShopCategory;
import pl.dream.depicshop.data.item.IItem;
import pl.dream.dreamlib.ProtectedInventory;

import java.util.List;

public class CategoryInventory extends ProtectedInventory {
    private final DEpicShop plugin;
    private final LocalPlayer localPlayer;

    private final int inventorySize;
    private final int currentPage;
    private final int pageAmount;

    public CategoryInventory(DEpicShop plugin, LocalPlayer localPlayer, ShopCategory category){
        this.plugin = plugin;
        this.localPlayer = localPlayer;

        inventorySize = category.getInventorySize();

        currentPage = localPlayer.path.getCurrentPage();
        pageAmount = category.items.size();

        inv = Bukkit.createInventory(this, category.getInventorySize(), category.getInventoryTitle());

        IItem[] items = category.getPage(currentPage);
        if(items==null){
            return;
        }

        for(int i=0;i<items.length;i++){
            IItem item = items[i];
            if(item!=null){
                inv.setItem(i, item.getItemStack());
            }
        }

        createFooter();
    }

    private void createFooter(){
        inv.setItem(getMoveBackButtonIndex(), convertItem(plugin.configController.moveBackItem.clone()));

        if(currentPage < pageAmount - 1){
            inv.setItem(getNextPageButtonIndex(), convertItem(plugin.configController.nextPageExistItem.clone()));
        }
        else{
            inv.setItem(getNextPageButtonIndex(), convertItem(plugin.configController.nextPageNotExistItem.clone()));
        }

        if(currentPage > 0){
            inv.setItem(getPreviousPageButtonIndex(), convertItem(plugin.configController.previousPageExistItem.clone()));
        }
        else{
            inv.setItem(getPreviousPageButtonIndex(), convertItem(plugin.configController.previousPageNotExistItem.clone()));
        }

        inv.setItem(inventorySize - 9, localPlayer.getPlayerHead());
    }

    public int getMoveBackButtonIndex(){
        return inventorySize - 5;
    }
    public int getPreviousPageButtonIndex(){
        return inventorySize - 6;
    }
    public int getNextPageButtonIndex(){
        return inventorySize - 4;
    }

    private ItemStack convertItem(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(itemMeta.getDisplayName()
                .replace("{CURRENT_PAGE}", String.valueOf(currentPage))
                .replace("{PAGE_AMOUNT}", String.valueOf(pageAmount)));

        List<String> lore = itemMeta.getLore();
        if(lore!=null && !lore.isEmpty()){
            for(int i=0;i<lore.size();i++){
                String line = lore.get(i);
                line = line
                        .replace("{CURRENT_PAGE}", String.valueOf(currentPage))
                        .replace("{PAGE_AMOUNT}", String.valueOf(pageAmount));
                lore.set(i, line);
            }

            itemMeta.setLore(lore);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
