package pl.dream.depicshop.data;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<Category> categories;

    public Path(){
        categories = new ArrayList<>();
    }

    public void moveForward(String categoryName){
        categories.add(new Category(categoryName));
    }

    //Returns the name of the category that should be opened when the player moves back.
    //Returns null if there is no previous category
    @Nullable
    public Category moveBack(){
        //Remove current categories
        if(!categories.isEmpty()){
            categories.remove(getLastCategoryIndex());
        }

        //Return the previous category
        if(!categories.isEmpty()){
            return categories.get(getLastCategoryIndex());
        }

        return null;
    }

    public void openNextPage(){
        if(categories.isEmpty()){
            return;
        }

        categories.get(getLastCategoryIndex()).changePage((short) 1);
    }
    public void openPreviousPage(){
        if(categories.isEmpty()){
            return;
        }

        categories.get(getLastCategoryIndex()).changePage((short) -1);
    }

    public int getCurrentPage(){
        if(categories.isEmpty()){
            return 0;
        }

        return categories.get(getLastCategoryIndex()).getPage();
    }
    public void clear(){
        categories.clear();
    }

    private int getLastCategoryIndex(){
        return categories.size() - 1;
    }

    public static class Category{
        private final String name;
        private short page;

        public Category(String name){
            this.name = name;
            page = 0;
        }

        public void changePage(short value){
            page += value;
            if(page<0){
                page = 0;
            }
        }

        public short getPage(){
            return page;
        }

        public String getName(){
            return name;
        }
    }
}
