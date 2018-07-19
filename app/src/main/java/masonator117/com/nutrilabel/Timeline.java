package masonator117.com.nutrilabel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Timeline extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<NutritionLabel> nutriLabels;
    String TAG = "Timeline Debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        DatabaseHandler db = new DatabaseHandler(this);
        nutriLabels = db.getAllNutritionalLabels();

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

        RecyclerView recyclerView = findViewById(R.id.nutrivalues);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, nutriLabels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




//        adapter=new RecyclerViewAdapter(this,nutriLabels);



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
                        dayView();
                        break;
                    case 1:
                        weekView();
                        break;
                    case 2:
                        monthView();
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });


    }

    private void dayView(){
//        DatabaseHandler db = new DatabaseHandler(this);
//        db.getDay()

    }

    private void weekView(){

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
            Log.d(TAG, "i = "+i+"db get month i = "+ db.getMonth(i).size());

            nl = getTotal(db.getMonth(i));
            if (nl!=null) {
                Log.d(TAG, "Not null i = "+i+"db get month i = "+ db.getMonth(i).size());

                iMonth = i;

// Create a calendar object and set year and month
                Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);
                int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28


                en = Integer.parseInt(nl.getEnergy().replaceAll("%", "")) / daysInMonth;
                fat = Integer.parseInt(nl.getFat().replaceAll("%", "")) / daysInMonth;
                sat = Integer.parseInt(nl.getSaturates().replaceAll("%", "")) / daysInMonth;
                sug = Integer.parseInt(nl.getSugars().replaceAll("%", "")) / daysInMonth;
                salt = Integer.parseInt(nl.getSalts().replaceAll("%", "")) / daysInMonth;

                nl.setPortion(1.0);
                nl.setEnergy(String.valueOf(en) + "%");
                nl.setFat(String.valueOf(fat) + "%");
                nl.setSaturates(String.valueOf(sat) + "%");
                nl.setSugars(String.valueOf(sug) + "%");
                nl.setSalts(String.valueOf(salt) + "%");
                nl.setDate("01" + String.valueOf(i) + String.valueOf(iYear));

                nla.add(nl);
            }

// Get the number of days in that month


        }

        nutriLabels =nla;

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
            return nl;
        }

        return null;


    }



}
