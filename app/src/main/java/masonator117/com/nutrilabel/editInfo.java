package masonator117.com.nutrilabel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class editInfo extends AppCompatActivity {


    double portion;
    private static final Date date = Calendar.getInstance().getTime();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
    TextView enerygyEdit;
    TextView fatEdit;
    TextView saturatesEdit;
    TextView sugarsEdit;
    TextView saltsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String enerygy = intent.getStringExtra("energy");
        String fat = intent.getStringExtra("fat");
        String saturate = intent.getStringExtra("saturate");
        String sugar = intent.getStringExtra("sugar");
        String salt = intent.getStringExtra("salt");
        portion = intent.getDoubleExtra("portion", 1.0);

         enerygyEdit = findViewById(R.id.editEnergy);
         fatEdit = findViewById(R.id.editFat);
         saturatesEdit = findViewById(R.id.editSaturate);
         sugarsEdit = findViewById(R.id.editSugar);
         saltsEdit = findViewById(R.id.editSalt);


        enerygyEdit.setText(enerygy);
        fatEdit.setText(fat);
        saturatesEdit.setText(saturate);
        sugarsEdit.setText(sugar);
        saltsEdit.setText(salt);

        spinners();
        buttonOnClick();

    }

    private boolean canContinue(){
        TextView enerygyEdit = findViewById(R.id.editEnergy);
        TextView fatEdit = findViewById(R.id.editFat);
        TextView saturatesEdit = findViewById(R.id.editSaturate);
        TextView sugarsEdit = findViewById(R.id.editSugar);
        TextView saltsEdit = findViewById(R.id.editSalt);

        ArrayList<String> outputs = new ArrayList<>();
        outputs.add(containsPercent(enerygyEdit.getText().toString()).get(0));
        outputs.add(containsPercent(fatEdit.getText().toString()).get(0));
        outputs.add(containsPercent(saturatesEdit.getText().toString()).get(0));
        outputs.add(containsPercent(sugarsEdit.getText().toString()).get(0));
        outputs.add(containsPercent(saltsEdit.getText().toString()).get(0));

        for (int i=0;i<outputs.size();i++){
            if (outputs.get(i).contains("Error")){
                Toast.makeText(this,"Incorrect percentages", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    private void checkForNaN(){



        Log.e("Changing NaN", "Changing NaN");

        String[] checkNan = {enerygyEdit.getText().toString(),fatEdit.getText().toString(),saturatesEdit.getText().toString(),
                sugarsEdit.getText().toString(),saltsEdit.getText().toString()};

        for (int i=0; i< checkNan.length;i++){
            if (checkNan[i]== "NaN"){
                Log.e("Changing NaN", "changing at i =" + i);
                checkNan[i] ="0%";
            }
        }

        enerygyEdit.setText(checkNan[0]);
        fatEdit.setText(checkNan[1]);
        saturatesEdit.setText(checkNan[2]);
        sugarsEdit.setText(checkNan[3]);
        saltsEdit.setText(checkNan[4]);


    }



    public void buttonOnClick(){
        Button finish= (Button)findViewById(R.id.finish);


        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if (canContinue()){
                    saveToDatabase();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
            }
        });


    }


    private void spinners(){
        Log.e("Portion 2", "Portion spinner start = " + portion);




        final Spinner spinnerDecimal = findViewById(R.id.spinnerDecimal2);
        final Spinner spinnerFraction = findViewById(R.id.spinnerFraction2);





        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(editInfo.this,
                R.array.decimalNumbers, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDecimal.setAdapter(adapter);
        getPrevDecimal(spinnerDecimal);


        spinnerDecimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String selected = spinnerDecimal.getSelectedItem().toString();
                portion = updatePortion(Double.parseDouble(selected), "decimal");
                Log.e("Portion 2", "Portion decimal change = " + portion);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(editInfo.this,
                R.array.fractionNumbers, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFraction.setAdapter(adapter2);
//        spinnerFraction.setSelection(0);
        getPrevFraction(spinnerFraction);

        spinnerFraction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                double[] fractionArray ={0.0, 0.25, 0.5, 0.75};

                int i =spinnerFraction.getSelectedItemPosition();

                portion = updatePortion(fractionArray[i], "fraction");
                Log.e("Portion 2", "Portion fraction change = " + portion);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });

    }

    private void saveToDatabase(){
        try {

            checkForNaN();
            DatabaseHandler db = new DatabaseHandler(this);



            NutritionLabel nl = new NutritionLabel(sdf.format(date), enerygyEdit.getText().toString(), fatEdit.getText().toString(),
                    saturatesEdit.getText().toString(), sugarsEdit.getText().toString(), saltsEdit.getText().toString(), portion);
            Log.e("Nutrition Label Outputs", "Date = " + sdf.format(date) + " Energy? =" + nl.getEnergy() + " Fat = "+ nl.getFat() + " Saturates = " + nl.getSaturates()
                    + " Sugar = " + nl.getSugars() + " Salt " + nl.getSalts() + " Portion = " + portion);


            db.addNutritionalLabel(nl);


//            Log.e("Database working?", "Count? = " + db.getLabelCount());
            Log.e("Database working?", "List all? = " + db.getAllNutritionalLabels());
            Log.e("Database working?", "Get first?? = " + db.getNutritionalLabel("16072018"));

         Toast.makeText(this,"Text Saved",Toast.LENGTH_LONG).show();

        } catch (NullPointerException e) {
            //if caught
            Toast.makeText(this, "Text Could not be added",Toast.LENGTH_LONG).show();
        }
    }

    private Double updatePortion(Double port, String type){
        String str=Double.toString(port);
        String strarray[]=str.split("\\.");
        String str2=Double.toString(portion);
        String strarray2[]=str2.split("\\.");
        if (type.equals("decimal")){
            Log.e("Decimal", "decimal");
            return portion = Double.parseDouble(strarray[0] +"."+ strarray2[1]);
        } else if (type.equals("fraction")) {
            Log.e("Decimal", "fraction");

            return portion = Double.parseDouble(strarray2[0] +"."+ strarray[1]);

        }
        return portion;
    }

    private void getPrevDecimal(Spinner spin){
        String str=Double.toString(portion);
        String strarray[]=str.split("\\.");
        String decimalArray[] = getResources().getStringArray(R.array.decimalNumbers);
        for (int i=0; i<decimalArray.length;i++){
            if (strarray[0].equals(decimalArray[i])){
                spin.setSelection(i);

            }
        }
    }

    private void getPrevFraction(Spinner spin){
        String str=Double.toString(portion);
        String strarray[]=str.split("\\.");
        strarray[1]= "0."+strarray[1];
        double[] fractions ={0.0, 0.25, 0.5, 0.75};

        for (int i=0; i<fractions.length;i++){
            Log.e("getPrevFraction", strarray[1] + " equals? = " +fractions[i]);

            if (Double.parseDouble(strarray[1])==fractions[i]){
                spin.setSelection(i);

            }
        }
    }


    private ArrayList<String> containsPercent(String block){

        block = block.replaceAll(" ","");
//        Log.e("Output", "Block = "+ block);
        ArrayList<String> matcher = new ArrayList();
        Pattern pp = Pattern.compile("\\b(?<!\\.)(?:\\d|[1-9]\\d|100)(?:(?<!100)\\.\\d+)?%");
        Matcher mm = pp.matcher(block);
//        (?!0+(?:\.0+)?%)
        while (mm.find()) {
//            Log.e("Pattern matcher", "mm to string "+mm.group());
            matcher.add(mm.group());

        }
        if (matcher.size() == 0){
            matcher.add("Error");
        }
//        output = matcher.toString();
//        Log.e("Output", "output = "+ output);
//        output = block;

        return matcher;
    }
}
