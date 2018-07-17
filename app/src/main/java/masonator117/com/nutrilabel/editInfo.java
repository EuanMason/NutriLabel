package masonator117.com.nutrilabel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class editInfo extends AppCompatActivity {

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

        TextView enerygyEdit = findViewById(R.id.editEnergy);
        TextView fatEdit = findViewById(R.id.editFat);
        TextView saturatesEdit = findViewById(R.id.editSaturate);
        TextView sugarsEdit = findViewById(R.id.editSugar);
        TextView saltsEdit = findViewById(R.id.editSalt);


        enerygyEdit.setText(enerygy);
        fatEdit.setText(fat);
        saturatesEdit.setText(saturate);
        sugarsEdit.setText(sugar);
        saltsEdit.setText(salt);

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


    public void buttonOnClick(){
        Button finish= (Button)findViewById(R.id.finish);


        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if (canContinue()){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
            }
        });


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
