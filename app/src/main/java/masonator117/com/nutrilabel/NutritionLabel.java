package masonator117.com.nutrilabel;

import java.util.ArrayList;
import java.util.Calendar;

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
    private Double portion;

    public NutritionLabel()
    {
    }

    public NutritionLabel(String date, String energy,String fat, String saturates, String sugars, String salts, Double portion)
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

    public Double getPortion() {
        return portion;
    }

    public void setPortion(Double portion) {
        this.portion = portion;
    }

    public NutritionLabel asOnePortion(NutritionLabel nl){
        Double portion = nl.getPortion();
        int energy =(int)Math.round(Double.parseDouble(nl.getEnergy().replaceAll("%", ""))*portion);
        int fat =(int)Math.round(Double.parseDouble(nl.getFat().replaceAll("%", ""))*portion);
        int saturates =(int)Math.round(Double.parseDouble(nl.getSaturates().replaceAll("%", ""))*portion);
        int sugars =(int)Math.round(Double.parseDouble(nl.getSugars().replaceAll("%", ""))*portion);
        int salts =(int)Math.round(Double.parseDouble(nl.getSalts().replaceAll("%", ""))*portion);

        nl.setPortion(1.0);
        nl.setEnergy(String.valueOf(energy) + "%");
        nl.setFat(String.valueOf(fat) + "%");
        nl.setSaturates(String.valueOf(saturates) + "%");
        nl.setSugars(String.valueOf(sugars) + "%");
        nl.setSalts(String.valueOf(salts) + "%");

        return nl;

    }

    public NutritionLabel getTotal(ArrayList<NutritionLabel> nlloop){

        NutritionLabel nl;

        int en=0;
        int fat=0;
        int sat=0;
        int sug=0;
        int salt=0;

        if (nlloop.size()>0) {
            nl = nlloop.get(0);


            for (int i = 0; i < nlloop.size(); i++) {
                nl = asOnePortion(nlloop.get(i));


                en = en + Integer.parseInt(nl.getEnergy().replaceAll("%", ""));
                fat = fat + Integer.parseInt(nl.getFat().replaceAll("%", ""));
                sat = sat + Integer.parseInt(nl.getSaturates().replaceAll("%", ""));
                sug = sug + Integer.parseInt(nl.getSugars().replaceAll("%", ""));
                salt = salt + Integer.parseInt(nl.getSalts().replaceAll("%", ""));


            }

            nl.setPortion(1.0);
            nl.setEnergy(String.valueOf(en) + "%");
            nl.setFat(String.valueOf(fat) + "%");
            nl.setSaturates(String.valueOf(sat) + "%");
            nl.setSugars(String.valueOf(sug) + "%");
            nl.setSalts(String.valueOf(salt) + "%");
            return nl;
        }

        return null;


    }



}
