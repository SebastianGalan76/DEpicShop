package pl.dream.depicshop.data.price;

import pl.dream.depicshop.data.LocalPlayer;

import java.text.DecimalFormat;

public class Price {
    private final double defaultValue;
    public static String symbol;
    public static String color;

    public Price(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public double getDefaultValue() {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(defaultValue));
    }

    public double getDefaultValueForAmount(int amount){
        return amount * getDefaultValue();
    }

    public double getValueForAmount(int amount){
        return amount * getDefaultValue();
    }

    public boolean deposit(LocalPlayer localPlayer, double value){

        return true;
    }

    public boolean withdraw(LocalPlayer localPlayer, double value){

        return false;
    }
}
