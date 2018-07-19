package masonator117.com.nutrilabel;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brkckr.circularprogressbar.CircularProgressBar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private static final Date date = Calendar.getInstance().getTime();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
    private TextView energyView;
    private TextView fatView;
    private TextView saturatesView;
    private TextView sugarView;
    private TextView saltsView;
    String TAG = "MainActivityDebug";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        energyView = findViewById(R.id.energyPercent);
        fatView = findViewById(R.id.fatPercent);
        saturatesView= findViewById(R.id.saturatesPercent);
        sugarView = findViewById(R.id.sugarsPercent);
        saltsView = findViewById(R.id.saltPercent);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        dataIntoProgress();
//        startActivity(new Intent(getApplicationContext(), Main2Activity.class) );
        buttonOnClick();
        final Intent timeline = new Intent(this, Timeline.class);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        if (menuItem.getItemId() == R.id.nav_home){
//            mDrawerLayout.openDrawer(GravityCompat.START);

                        } else if (menuItem.getItemId() == R.id.nav_timeline) {
                            startActivity(timeline);
                        }


                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void buttonOnClick(){
        Button button= (Button)findViewById(R.id.toPicture);
//        Button crop = (Button)findViewById(R.id.cropImage);
//        Button deleteImage = (Button)findViewById(R.id.deleteImage);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), TakePicture.class));
            }
        });


        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        Log.e("navbar", "Home = " + menuItem.getTitle());
                        if (menuItem.getTitle() == "Home"){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });


//        crop.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v){
//                startActivity(new Intent(getApplicationContext(), dataProcessing.class));
//            }
//        });

//        deleteImage.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v){
//                startActivity(new Intent(getApplicationContext(), DeletePhotos.class));
//            }
//        });
    }


    private void dataIntoProgress(){
        DatabaseHandler db = new DatabaseHandler(this);

        String sdate = sdf.format(date);
        Log.d(TAG, "dataIntoProgress: " + db.getNutritionalLabel(sdate));
        ArrayList<NutritionLabel> nl = db.getNutritionalLabel(sdate);
        Log.d(TAG, "Sdate = (" + sdate + "). nutrition date = (" + db.getAllNutritionalLabels().get(0).getDate() + ") DB size = " +  db.getNutritionalLabel(sdate).size());
        int energy =0;
        int fat =0;
        int saturates=0;
        int sugars=0;
        int salts=0;
        Double portion;

        for (int i=0;i<nl.size();i++){
//            Log.e("DataIntoProgress", "Energy = "+nl.get(i).getEnergy());
            //TODO multiple by portion
            portion=nl.get(i).getPortion();
            Log.d(TAG, "Portion = " + portion);
//            Log.e("Portion times", String.valueOf(Double.parseDouble(nl.get(i).getEnergy().replaceAll("%", ""))));
//            Log.e("Portion times", String.valueOf((int)Math.round(Double.parseDouble(nl.get(i).getEnergy().replaceAll("%", ""))*portion)));
            energy = energy +(int)Math.round(Double.parseDouble(nl.get(i).getEnergy().replaceAll("%", ""))*portion);
            fat = fat + (int)Math.round(Double.parseDouble(nl.get(i).getFat().replaceAll("%", ""))*portion);
            saturates = saturates + (int)Math.round(Double.parseDouble(nl.get(i).getSaturates().replaceAll("%", ""))*portion);
            sugars = sugars + (int)Math.round(Double.parseDouble(nl.get(i).getSugars().replaceAll("%", ""))*portion);
            salts = salts + (int)Math.round(Double.parseDouble(nl.get(i).getSalts().replaceAll("%", ""))*portion);


        }


        energyView.setText(String.valueOf(energy + "%"));
        fatView.setText(String.valueOf(fat + "%"));
        saturatesView.setText(String.valueOf(saturates + "%"));
        sugarView.setText(String.valueOf(sugars + "%"));
        saltsView.setText(String.valueOf(salts + "%"));

        CircularProgressBar cpbEnergy = findViewById(R.id.energyProgressBar);
        CircularProgressBar cpbFat = findViewById(R.id.fatProgressBar);
        CircularProgressBar cpbSaturates = findViewById(R.id.saturatesProgressBar);
        CircularProgressBar cpbSugars = findViewById(R.id.sugarsProgressBar);
        CircularProgressBar cpbSalts = findViewById(R.id.saltProgressBar);



        cpbEnergy.setProgressValueWithAnimation(energy, 3500);
        cpbFat.setProgressValueWithAnimation(fat, 3500);
        cpbSaturates.setProgressValueWithAnimation(saturates, 3500);
        cpbSugars.setProgressValueWithAnimation(sugars, 3500);
        cpbSalts.setProgressValueWithAnimation(salts, 3500);


        cpbEnergy.setProgressValue(energy);
        cpbFat.setProgressValue(fat);
        cpbSaturates.setProgressValue(saturates);
        cpbSugars.setProgressValue(sugars);
        cpbSalts.setProgressValue(salts);


        CircularProgressBar[] circulararray = {cpbEnergy, cpbFat, cpbSaturates, cpbSugars, cpbSalts};
        for (int i=0;i<circulararray.length;i++){
            Log.e("Progress value", "Progress array length = " + circulararray.length + " i = " + i + " really?" + cpbSugars.getProgressValue());
            if (circulararray[i].getProgressValue() < 50){
                circulararray[i].setProgressColor(Color.parseColor("#00e500"));
            } else if (circulararray[i].getProgressValue() < 100 ){
                circulararray[i].setProgressColor(Color.parseColor("#ffa500"));
            } else if (circulararray[i].getProgressValue() >= 100){
                Log.e("ProgressValue", "Progress value");
                Toast.makeText(this, "You're over your daily limit for nutrition type", Toast.LENGTH_LONG).show();
                circulararray[i].setProgressColor(Color.parseColor("#e50000"));
            }
            Log.e("ProgressValue", "Progress value = " + circulararray[i].getProgressValue());

        }

//        setBackgroundColor(Color.parseColor("#FF0000")



//        ProgressBar pb1 = findViewById(R.id.progressBar2);
//        pb1.setMax(100);
//        Log.e("Progress =", String.valueOf(pb1.getProgress()));
//        pb1.setProgress(50);
//        pb1.setProgress(10);
//        pb1.setMax(50);
//        pb1.setProgress(50);
//
//        Log.e("Progress =", String.valueOf(pb1.getProgress()));
//        Log.e("Progress =", String.valueOf(pb1.getMax()));

    }


}
