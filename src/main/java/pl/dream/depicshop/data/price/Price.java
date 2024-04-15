package pl.dream.depicshop.data.price;

import java.text.DecimalFormat;

public class Price {
    private final double defaultValue;

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
}
