package masonator117.com.nutrilabel;

/**
 * Created by Euan on 13/07/2018.
 */

public class NutritionLabel {

    private String date;
    private String energy;
    private String fat;
    private String saturates;
    private String sugars;
    private String salts;
    private int portion;

    public NutritionLabel()
    {
    }

    public NutritionLabel(String date, String energy,String fat, String saturates, String sugars, String salts, int portion)
    {
        this.date=date;
        this.energy=energy;
        this.fat=fat;
        this.saturates=saturates;
        this.sugars=sugars;
        this.salts=salts;
        this.portion=portion;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getSaturates() {
        return saturates;
    }

    public void setSaturates(String saturates) {
        this.saturates = saturates;
    }

    public String getSugars() {
        return sugars;
    }

    public void setSugars(String sugars) {
        this.sugars = sugars;
    }

    public String getSalts() {
        return salts;
    }

    public void setSalts(String salts) {
        this.salts = salts;
    }

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }



}
