package pl.dream.depicshop.data;

import pl.dream.depicshop.data.item.IItem;
import pl.dream.depicshop.data.item.Item;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ShopCategory {
    private final String title;
    private int rows;

    public final List<IItem[]> items;

    public ShopCategory(String title, int rows) {
        this.title = title;
        this.rows = rows;

        items = new ArrayList<>();
    }

    public int addPage(IItem[] items){
        int pageSize = items.length;
        int requiredRows = pageSize/9;
        if(pageSize % 9 != 0){
            requiredRows++;
        }

        //+1 because we need space for the footer
        if(requiredRows + 1 > rows){
            rows = requiredRows + 1;

            if(rows>6){
                return -1;
            }
        }

        this.items.add(items);
        return this.items.size() - 1;
    }

    @Nullable
    public IItem[] getPage(int page){
        if(items.size()>page){
            return items.get(page);
        }

        return null;
    }
    @Nullable
    public IItem getItem(int page, int slot){
        IItem[] pageItems = getPage(page);

        if(pageItems!=null){
            if(pageItems.length>slot){
                return pageItems[slot];
            }
        }

        return null;
    }

    public void updateItem(int page, int slot, IItem item){
        IItem[] itemsOnPage = items.get(page);

        if(slot<itemsOnPage.length){
            itemsOnPage[slot] = item;
        }
    }

    public int getPageAmount(){
        return items.size();
    }
    public String getInventoryTitle(){
        return title;
    }
    public int getInventorySize(){
        if(rows <= 0 || rows > 6){
            return 54;
        }

        return rows * 9;
    }

    public int getMoveBackButtonIndex(){
        return getInventorySize() - 5;
    }
    public int getPreviousPageButtonIndex(){
        return getInventorySize() - 6;
    }
    public int getNextPageButtonIndex(){
        return getInventorySize() - 4;
    }
}
