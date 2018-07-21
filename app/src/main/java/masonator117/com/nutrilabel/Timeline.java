package masonator117.com.nutrilabel;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.brkckr.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class Timeline extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    ArrayList<NutritionLabel> nutriLabels;
    String TAG = "Timeline_Debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        mDrawerLayout = findViewById(R.id.drawer_layout2);

        buttonOnClick();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        getDatabase();

        spinners();

//        mRecyclerView = (RecyclerView) findViewById(R.id.nutrivalues);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
////        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        // specify an adapter (see also next example)
////        mAdapter = new RecyclerViewAdapter(nutriLabels);
//        mRecyclerView.setAdapter(mAdapter);



        recycle();


//        adapter=new RecyclerViewAdapter(this,nutriLabels);



    }

    private void buttonOnClick(){
        NavigationView navigationView = findViewById(R.id.nav_view2);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        Log.d(TAG, "Menu item = "+ menuItem.getItemId() + " Home = " + R.id.nav_home + " Timeline = " + R.id.nav_timeline);

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

        final Intent mainActivity = new Intent(this, MainActivity.class);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Log.d(TAG, "Menu item = "+ menuItem.getItemId() + " Home = " + R.id.nav_home + " Timeline = " + R.id.nav_timeline);

                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        if (menuItem.getItemId() == R.id.nav_home){
//            mDrawerLayout.openDrawer(GravityCompat.START);
                            startActivity(mainActivity);


                        } else if (menuItem.getItemId() == R.id.nav_timeline) {
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
                Log.d(TAG, "Drawer opened");

                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void recycle(){
        RecyclerView recyclerView = findViewById(R.id.nutrivalues);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, nutriLabels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getDatabase(){
        DatabaseHandler db = new DatabaseHandler(this);
        nutriLabels = db.getAllNutritionalLabels();
    }

    private void spinners(){


        final Spinner timelineSpinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Timeline.this,
                R.array.calendarView, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        timelineSpinner.setAdapter(adapter);
        timelineSpinner.setSelection(0);


        timelineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Spinner spinner = findViewById(R.id.spinner);
                int i = spinner.getSelectedItemPosition();
                switch (i) {
                    case 0:
                        nutriLabels = dayView();
                        recycle();

                        getDatabase();

                        break;
                    case 1:
                        weekView();
                        recycle();
                        getDatabase();
                        break;
                    case 2:
                        monthView();
                        recycle();
                        getDatabase();
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });


    }

    private ArrayList<NutritionLabel> dayView(){
//        DatabaseHandler db = new DatabaseHandler(this);
//        db.getDay()
        ArrayList<NutritionLabel> nlArray  = new ArrayList<>();

        ArrayList<NutritionLabel> nlFinal = new ArrayList<>();
        String date = "";
        for (int i=0;i<nutriLabels.size();i++){
            if (!nutriLabels.get(i).getDate().equals(date)){
                Log.d(TAG, "Date = " + date+ " and nutrilabel date = "+ nutriLabels.get(i).getDate());
                date = nutriLabels.get(i).getDate();
                if (nlArray.size()>0){
                    nlFinal.add(getTotal(nlArray));
                }
                nlArray.clear();
                nlArray.add(nutriLabels.get(i));
            } else {
                nlArray.add(nutriLabels.get(i));
            }
        }

        nlFinal.add(getTotal(nlArray));
        Collections.reverse(nlFinal);
        return nlFinal;
    }

    private void weekView(){
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<NutritionLabel> nla = new ArrayList<>();

        NutritionLabel nl;
        int en;
        int fat;
        int sat;
        int sug;
        int salt;
        int week = 7;


        for (int i=0;i<52;i++){
            nl = getTotal(db.getWeek(i));


            if (nl!=null) {
//                Log.d(TAG, "Not null i = "+i+"db get month i = "+ db.getMonth(i).size());




                en = (int)Math.round(Double.parseDouble(nl.getEnergy().replaceAll("%", "")) / week);
                fat = (int)Math.round(Double.parseDouble(nl.getFat().replaceAll("%", "")) / week);
                sat = (int)Math.round(Double.parseDouble(nl.getSaturates().replaceAll("%", "")) / week);
                sug = (int)Math.round(Double.parseDouble(nl.getSugars().replaceAll("%", "")) / week);
                salt = (int)Math.round(Double.parseDouble(nl.getSalts().replaceAll("%", "")) / week);

                nl.setPortion(1.0);
                nl.setEnergy(String.valueOf(en) + "%");
                nl.setFat(String.valueOf(fat) + "%");
                nl.setSaturates(String.valueOf(sat) + "%");
                nl.setSugars(String.valueOf(sug) + "%");
                nl.setSalts(String.valueOf(salt) + "%");

                nla.add(nl);
            }
        }
        Collections.reverse(nla);
        nutriLabels=nla;
    }

    private void monthView(){
        ArrayList<NutritionLabel> nla = new ArrayList<>();
        NutritionLabel nl;
        int en;
        int fat;
        int sat;
        int sug;
        int salt;

        DatabaseHandler db = new DatabaseHandler(this);
        int iYear = Calendar.YEAR;

        int iMonth;// 1 (months begin with 0)
        int iDay = Calendar.DAY_OF_MONTH;

        for (int i=0;i<12;i++){
//            Log.d(TAG, "i = "+i+"db get month i = "+ db.getMonth(i).size());

            nl = getTotal(db.getMonth(i));
            if (nl!=null) {
//                Log.d(TAG, "Not null i = "+i+"db get month i = "+ db.getMonth(i).size());

                iMonth = i;

// Create a calendar object and set year and month
                Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);
                int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28


                en = (int)Math.round(Double.parseDouble(nl.getEnergy().replaceAll("%", "")) / daysInMonth);
                fat = (int)Math.round(Double.parseDouble(nl.getFat().replaceAll("%", "")) / daysInMonth);
                sat = (int)Math.round(Double.parseDouble(nl.getSaturates().replaceAll("%", "")) / daysInMonth);
                sug = (int)Math.round(Double.parseDouble(nl.getSugars().replaceAll("%", "")) / daysInMonth);
                salt = (int)Math.round(Double.parseDouble(nl.getSalts().replaceAll("%", "")) / daysInMonth);

                nl.setPortion(1.0);
                nl.setEnergy(String.valueOf(en) + "%");
                nl.setFat(String.valueOf(fat) + "%");
                nl.setSaturates(String.valueOf(sat) + "%");
                nl.setSugars(String.valueOf(sug) + "%");
                nl.setSalts(String.valueOf(salt) + "%");


                nla.add(nl);
            }

// Get the number of days in that month


        }
        Collections.reverse(nla);

        nutriLabels = nla;

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

    public NutritionLabel getTotal(List<NutritionLabel> nlloop){

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
            nl.setDate(nlloop.get(0).getDate());
            return nl;
        }

        return null;


    }






}
