package pl.dream.depicshop.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import pl.dream.depicshop.DEpicShop;
import pl.dream.depicshop.Utils;
import pl.dream.depicshop.data.LocalPlayer;
import pl.dream.depicshop.data.ShopCategory;
import pl.dream.depicshop.data.item.IItem;
import pl.dream.depicshop.inventory.CategoryInventory;
import pl.dream.depicshop.inventory.ItemInventory;

public class InventoryClickListener implements Listener {
    private final DEpicShop plugin;

    public InventoryClickListener(DEpicShop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        InventoryHolder inventoryHolder = e.getInventory().getHolder();
        LocalPlayer localPlayer = plugin.players.get(e.getWhoClicked().getUniqueId());

        int slot = e.getSlot();
        if(inventoryHolder instanceof CategoryInventory){
            ShopCategory category = localPlayer.openedShopCategory;
            int page = localPlayer.path.getCurrentPage();

            if(slot == category.getMoveBackButtonIndex()){
                localPlayer.moveBack();
                return;
            }
            else if(slot == category.getNextPageButtonIndex()){
                if(page<category.getPageAmount()){
                    localPlayer.nextPage();
                }
                else{
                    Utils.playFailSounds(localPlayer.player);
                }

                return;
            }
            else if(slot == category.getPreviousPageButtonIndex()){
                if(page>0){
                    localPlayer.previousPage();
                }
                else{
                    Utils.playFailSounds(localPlayer.player);
                }

                return;
            }

            IItem item = category.getItem(page, slot);
            if(item!=null){
                item.onClick(localPlayer);
            }
        }
        if(inventoryHolder instanceof ItemInventory){
            ItemInventory itemInventory = localPlayer.openedItemInventory;

            switch (slot){
                case 10:
                    itemInventory.changeAmount(1);
                    return;
                case 19:
                    itemInventory.changeAmount(8);
                    return;
                case 28:
                    itemInventory.changeAmount(16);
                    return;
                case 37:
                    itemInventory.changeAmount(64);
                    return;
                case 16:
                    itemInventory.changeAmount(-1);
                    return;
                case 25:
                    itemInventory.changeAmount(-8);
                    return;
                case 34:
                    itemInventory.changeAmount(-16);
                    return;
                case 43:
                    itemInventory.changeAmount(-64);
                    return;

                case 49:
                    localPlayer.moveBack();
                    return;
            }

            if(slot==13){
                if(e.getClick().isLeftClick()){
                    itemInventory.buyItem();
                }
                if(e.getClick().isRightClick()){
                    if(e.getClick().isShiftClick()){
                        itemInventory.sellAllItem();
                    }
                    else{
                        itemInventory.sellItem();
                    }
                }

                return;
            }
        }
    }
}
