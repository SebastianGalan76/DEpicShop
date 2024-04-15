package pl.dream.depicshop.controller;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.dream.depicshop.DEpicShop;
import pl.dream.depicshop.Utils;
import pl.dream.depicshop.data.item.CategoryItem;
import pl.dream.depicshop.data.item.IItem;
import pl.dream.depicshop.data.item.Item;
import pl.dream.depicshop.data.ShopCategory;
import pl.dream.depicshop.data.item.ShopItem;
import pl.dream.depicshop.data.price.Price;
import pl.dream.depicshop.data.price.PriceTime;
import pl.dream.depicshop.data.price.PriceToken;
import pl.dream.dreamlib.Config;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopController {
    private final DEpicShop plugin;
    private FileConfiguration shop;

    private final List<String> loadedCategories, requiredCategories;
    private final List<String> aliasPaths;

    public ShopController(DEpicShop plugin){
        this.plugin = plugin;

        loadedCategories = new ArrayList<>();
        requiredCategories = new ArrayList<>();
        aliasPaths = new ArrayList<>();

        loadShop();
        loadAliases();
    }

    private void loadShop(){
        File file = new File(plugin.getDataFolder(), "shop.yml");
        if (!file.exists()) {
            plugin.saveResource("shop.yml", false);
            file = new File(plugin.getDataFolder(), "shop.yml");
        }

        shop = YamlConfiguration.loadConfiguration(file);
        if(shop.get("categories")==null){
            Bukkit.getLogger().warning("Incorrect shop.yml file! Disabled this plugin.");
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        }

        for(String categoryName:shop.getConfigurationSection("categories").getKeys(false)){
            loadCategory(categoryName);
        }

        checkLoadedData();
    }

    private void loadCategory(String categoryName){
        String path = "categories."+categoryName;

        String title = shop.getString(path+".title", "");
        int row = shop.getInt(path+".row", 6);

        ShopCategory category = new ShopCategory(title, row);
        if(shop.get(path+".items")!=null){
            loadPage(categoryName, path+".items", category);
        }
        else if(shop.get(path+".pages")!=null){
            for(String pageString:shop.getConfigurationSection(path+".pages").getKeys(false)){
                loadPage(categoryName, path+".pages."+pageString, category);
            }
        }
        else{
            Bukkit.getLogger().warning("Incorrect category configuration: "+categoryName);
        }

        loadedCategories.add(categoryName);
    }

    private void loadPage(String categoryName, String path, ShopCategory category){
        HashMap<Integer, IItem> itemsHashMap = new HashMap<>();
        int maxIndex = 0;
        for(String indexString : shop.getConfigurationSection(path).getKeys(false)){
            try{
                int index = Integer.parseInt(indexString);
                if(maxIndex < index){
                    maxIndex = index;
                }

                IItem item = loadItem(categoryName,category.getPageAmount(),index, path+"."+indexString);
                if(item==null){
                    continue;
                }

                itemsHashMap.put(index, item);
            }catch (NumberFormatException e){
                Bukkit.getLogger().warning("NumberFormatException! Incorrect slot index:"+path+"."+indexString);
            }
        }

        IItem[] items = new IItem[maxIndex];
        for(int i=0;i<maxIndex;i++){
            if(itemsHashMap.containsKey(i)){
                items[i] = itemsHashMap.get(i);
            }
        }

        if(category.addPage(items) == -1){
            Bukkit.getLogger().warning("Loaded page is too big: "+path);
        }
    }

    @Nullable
    private IItem loadItem(String categoryName, int pageIndex, int slotIndex, String path){
        if(shop.get(path+".alias")!=null){
            aliasPaths.add(path+".alias");

            return null;
        }

        ItemStack itemStack = Config.getItemStack(shop, path);
        if(itemStack==null){
            Bukkit.getLogger().warning("Incorrect item: "+path);
            return null;
        }

        String category= shop.getString("category");
        if(category!=null){
            requiredCategories.add(category);
            return new CategoryItem(itemStack, category);
        }

        String id = categoryName+"/"+pageIndex+"/"+slotIndex+"/"+itemStack.getType();
        ShopItem shopItem = loadShopItem(id, itemStack, path);
        if(shopItem!=null){
            if(Utils.checkPrices(shopItem.buyPrice, shopItem.sellPrice)){
                plugin.shopItems.put(id, shopItem);
                return shopItem;
            }

            Bukkit.getLogger().warning("Incorrect prices for item: "+id);
        }

        return new Item(itemStack);
    }

    @Nullable
    private ShopItem loadShopItem(String id, ItemStack itemStack, String path){
        Price buy = null, sell = null;

        if(shop.get(path+".buy")!=null){
            buy = new Price(shop.getDouble(path+".buy"));
        }
        if(shop.get(path+".sell")!=null){
            sell = new Price(shop.getDouble(path+".sell"));
        }

        if(shop.get(path+".price.buy")!=null){
            buy = new Price(shop.getDouble(path+".price.buy"));
        }
        if(shop.get(path+".price.sell")!=null){
            sell = new Price(shop.getDouble(path+".price.sell"));
        }

        if(shop.get(path+".tokenPrice.buy")!=null){
            buy = new PriceToken(shop.getDouble(path+".tokenPrice.buy"));
        }
        if(shop.get(path+".tokenPrice.sell")!=null){
            sell = new PriceToken(shop.getDouble(path+".tokenPrice.sell"));
        }

        if(shop.get(path+".timePrice.buy")!=null){
            buy = new PriceTime(shop.getDouble(path+".timePrice.buy"));
        }
        if(shop.get(path+".timePrice.sell")!=null){
            sell = new PriceTime(shop.getDouble(path+".timePrice.sell"));
        }

        if(buy!=null || sell!=null){
            return new ShopItem(id, itemStack, buy, sell);
        }

        return null;
    }
    private void checkLoadedData(){
        for(String requiredCategory:requiredCategories){
            if(!loadedCategories.contains(requiredCategory)){
                Bukkit.getLogger().warning("WARNING: There is no required category: "+requiredCategory);
            }
        }

        loadedCategories.clear();
        requiredCategories.clear();
    }

    private void loadAliases(){
        for(String path: aliasPaths){
            String[] pathArray = path.split("\\.");

            if(pathArray.length>5){
                String categoryName = pathArray[1];
                String pageIndex = "-1", slotIndex = "-1";

                if(pathArray[2].equalsIgnoreCase("items")){
                    pageIndex = "0";
                    slotIndex = pathArray[3];
                }
                else if(pathArray[2].equalsIgnoreCase("pages")){
                    pageIndex = pathArray[3];
                    slotIndex = pathArray[4];
                }

                ShopCategory category = plugin.categories.get(categoryName);
                if(category==null){
                    Bukkit.getLogger().warning("Incorrect category name: "+categoryName);
                    continue;
                }

                IItem item = getAlias(path);
                if(item == null){
                    continue;
                }

                try{
                    category.updateItem(Integer.parseInt(pageIndex), Integer.parseInt(slotIndex), item);
                }
                catch (NumberFormatException e){
                    Bukkit.getLogger().warning("NumberFormatException: "+path +" | "+pageIndex +" or "+slotIndex);
                }
            }
            else{
                Bukkit.getLogger().warning("Incorrect alias path"+aliasPaths);
            }
        }

        aliasPaths.clear();
    }
    @Nullable
    private IItem getAlias(String path){
        String alias = shop.getString(path);
        if(alias==null){
            Bukkit.getLogger().warning("Incorrect alias path: "+path);
            return null;
        }

        String[] aliasArray = alias.split("/");
        if(aliasArray.length!=4){
            Bukkit.getLogger().warning("Incorrect alias format: "+alias);
            return null;
        }

        String aliasCategory = aliasArray[0];
        int aliasPage;
        int aliasSlot;
        try{
            aliasPage = Integer.parseInt(aliasArray[1]);
            aliasSlot = Integer.parseInt(aliasArray[2]);
        }catch (NumberFormatException e){
            Bukkit.getLogger().warning("NumberFormatException: "+path +" | "+aliasArray[1]+" or "+aliasArray[2]);

            return null;
        }

        String aliasType = aliasArray[3];

        ShopCategory category = plugin.categories.get(aliasCategory);
        if(category == null){
            Bukkit.getLogger().warning("Incorrect alias category name: "+alias);
            return null;
        }

        IItem[] items = category.getPage(aliasPage);
        if(items == null){
            Bukkit.getLogger().warning("Incorrect alias page"+alias);
            return null;
        }
        if(aliasSlot>=items.length){
            Bukkit.getLogger().warning("Incorrect alias slot"+alias);
            return null;
        }

        IItem item = items[aliasSlot];
        if(!item.getItemStack().getType().toString().equalsIgnoreCase(aliasType)){
            Bukkit.getLogger().warning("Incorrect alias item type"+alias);
            return null;
        }

        return item;
    }
}
