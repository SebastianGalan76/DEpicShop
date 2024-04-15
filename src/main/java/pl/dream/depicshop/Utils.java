package pl.dream.depicshop;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.dream.depicshop.data.price.Price;
import pl.dream.depicshop.data.price.PriceTime;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void playUISounds(Player player){
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1f);
    }

    public static void playFailSounds(Player player){
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.5f, 1f);
    }

    public static void playSuccessSounds(Player player){
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1f);
    }

    public static String getMoneyFormat(double money){
        if(money - (int)money >0){
            return String.format("%,.2f", money);
        }
        return String.format("%,.0f", money);
    }

    public static float roundValue(float value){
        return Math.round(value * 100.0f) / 100.0f;
    }

   public static boolean checkPrices(Price buyPrice, Price sellPrice){
        if(buyPrice!=null && sellPrice!=null){
            if(buyPrice.getDefaultValue() < sellPrice.getDefaultValue()){
                return false;
            }
        }

        if(buyPrice!=null && buyPrice.getDefaultValue()<0){
            return false;
        }

        if(sellPrice!=null && sellPrice.getDefaultValue()<0){
            return false;
        }

        return true;
    }
}
