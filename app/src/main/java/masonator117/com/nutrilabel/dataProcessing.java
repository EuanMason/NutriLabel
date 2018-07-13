package masonator117.com.nutrilabel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class dataProcessing extends AppCompatActivity {

    private static final String TAG = "OCR";
    private TextView textView;
    private NutritionLabel nutritionLabel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_processing);





                processImage();

        buttonOnClick();


    }


    public void buttonOnClick(){
        Button done= (Button)findViewById(R.id.done);
        Button edit = findViewById(R.id.editData);
        //TODO database of correct answers
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                saveCorrectness();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                TextView energyOut = findViewById(R.id.energyOutput);
                TextView fatOut = findViewById(R.id.fatOutput);
                TextView saturatesOut = findViewById(R.id.saturatesOutput);
                TextView sugarOut = findViewById(R.id.sugarsOutput);
                TextView saltOut = findViewById(R.id.saltsOutput);


                Intent intent = new Intent(getApplicationContext(), editInfo.class);
                intent.putExtra("energy", energyOut.getText());
                intent.putExtra("fat", fatOut.getText());
                intent.putExtra("saturate", saturatesOut.getText());
                intent.putExtra("sugar", sugarOut.getText());
                intent.putExtra("salt", saltOut.getText());
                startActivity(intent);
            }
        });



    }

    private void saveCorrectness(){
        try {

            DatabaseHandler db = new DatabaseHandler(this);

            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            nutritionLabel.setDate(sdf.format(date));
            db.addNutritionalLabel();
            db.addNutritionalLabel();
//            String output ="";
            //open file for writing

//            String outputFileName = "correct.txt";
//            File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), outputFileName);
//
//
//
//
////            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(outputFileName, MODE_APPEND));
//            CheckBox energyCheck =(CheckBox)findViewById(R.id.energyCheckBox);
//            CheckBox fatCheck =(CheckBox)findViewById(R.id.fatCheckBox);
//            CheckBox saturatesCheck =(CheckBox)findViewById(R.id.saturatesCheckBox);
//            CheckBox sugarCheck =(CheckBox)findViewById(R.id.sugarsCheckBox);
//            CheckBox saltCheck =(CheckBox)findViewById(R.id.saltCheckBox);
//            CheckBox[] checkboxes = new CheckBox[]{energyCheck,fatCheck,saturatesCheck,sugarCheck,saltCheck};
//            for (int i=0;i < checkboxes.length;i++){
//                if (checkboxes[i].isChecked()){
//                    output = output + "1";
//                } else {
//                    output = output + "0";
//
//                }
//            }
//            output = output + "\n";
//
//
//
//            try {
//                if (!file.exists())
//                    file.createNewFile();
//                FileOutputStream fout = new FileOutputStream(file, true);
//                fout.write(output.getBytes());
//                fout.flush();
//                fout.close();
////                outputStream = openFileOutput(outputFileName, Context.MODE_APPEND);
////                outputStream.write(output.getBytes());
////                outputStream.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


            //write information to file
//            out.write(output);
//            out.write('\n');

            //close file

//            out.close();
//            Toast.makeText(this,"Text Saved",Toast.LENGTH_LONG).show();

        } catch (NullPointerException e) {
            //if caught
            Toast.makeText(this, "Text Could not be added",Toast.LENGTH_LONG).show();
        }
    }






    private void processImage() {
        textView = (TextView)findViewById(R.id.testingText);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        Intent intent = getIntent();

        String outputFileName = intent.getStringExtra("fileName");


        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), outputFileName);
        Uri uriCropped = intent.getParcelableExtra("cropped");
//        Log.e("Fileout", "Fileout = " + file);
        try {
            Bitmap bitmapCropped = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriCropped);
//            Log.e("Fileout", "Bitmap cropped = " + bitmapCropped);

            BackgroundWorkParams params = new BackgroundWorkParams(file, bitmapCropped);
            BackgroundWork backgroundWork = new BackgroundWork();
            backgroundWork.execute(params);
        } catch (IOException e){

        }
//        splitImage(uri);
        if (textRecognizer.isOperational()) {
//            Log.e(TAG, "processImage: started");


            boolean vertical;
            final Bitmap bitmap;
            int[] errorsArray = new int[8];
            ArrayList<ArrayList<String>> outputs = new ArrayList<>(8);

            Bitmap bitmap2;
            Bitmap bitmap3;
            int lowestError =6;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriCropped);
                if (bitmap.getHeight()>bitmap.getWidth()) {
                    bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() / 2 - 1, bitmap.getHeight() - 1);
                    bitmap3 = Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2 - 1, 0, bitmap.getWidth() / 2 - 1, bitmap.getHeight() - 1);
                    vertical = true;
                } else {
                    bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth()-1, bitmap.getHeight()/2-1);
                    bitmap3 = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight()/2-1, bitmap.getWidth()-1, bitmap.getHeight()/2-1);
                    vertical = false;
                }

                    for (int i = 0; i < 8; i++) {


//                    Matrix matrix = new Matrix();
//                    matrix.postRotate(90, bitmap.getWidth()/2,bitmap.getHeight()/2);

//                    Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth()-1, bitmap.getHeight()-1, matrix, true);

                        if (i<4) {
                            outputs.add(i, splitImage(bitmap2, i));
//                            Log.e(TAG, "Outputs = " + outputs.get(i).toString());

                            errorsArray[i] = errors;
                        } else {
                            outputs.add(i, splitImage(bitmap3, i-4));
//                            Log.e(TAG, "Outputs = " + outputs.get(i).toString());

                            errorsArray[i] = errors;
                        }

//                    Frame frame = new Frame.Builder().setBitmap(bitmap2).setRotation(i).build();
//                    items = textRecognizer.detect(frame);
//                    dataSize[i] = getBlock(items);


                    }





                for (int i=0; i<outputs.size(); i++){
//                    Log.e("Errors", "Error array = " + errorsArray[0] + " Outputs size = " + outputs.size());

                    if (errorsArray[i] < lowestError){
//                        Log.e("Errors", "outputs = " + outputs.get(i).toString());
                        int j = i;
                        if (j>=4)
                            j=j-4;
                        if (vertical && (j == 0 || j ==3)){
//                            Log.e("Reverse", "Not Reversed vert");

                            display(outputs.get(i));


                        } else if (!vertical && (j == 0 || j ==1)){
//                            Log.e("Reverse", "Not reversed horiz");
                            display(outputs.get(i));
                            nutritionLabel.setEnergy(outputs.get(i).get(0));
                            nutritionLabel.setFat(outputs.get(i).get(1));
                            nutritionLabel.setSaturates(outputs.get(i).get(2));
                            nutritionLabel.setSugars(outputs.get(i).get(3));
                            nutritionLabel.setSalts(outputs.get(i).get(4));

                        } else {
//                            Log.e("Reverse", "Reversed");
                            Collections.reverse(outputs.get(i));
                            display(outputs.get(i));


                        }

                        lowestError = errorsArray[i];
//                        Log.e("Errors", "Error array = " + errorsArray[i] + " " + i + " Lowest error = " + lowestError);

                    }

                }

//
//                int largest =0;
//                for (int i=0; i<dataSize.length; i++){
//                    if (dataSize[i]> largest){
//                        Log.e(TAG, "Datasize =" + dataSize.length);
//                        largest = i;
//                    }
//
//                }
//                if (largest<5 && largest >0){
//                    Log.e(TAG, "Largest =" + largest);
//                    splitImage(bitmap2, largest);
//                }





//                    Log.e(TAG, "Datasize =" + dataSize.length);
//                    Log.e("Display", "Outputs lowest error" +outputs.get(lowestError).toString());
//
//                    display(outputs.get(lowestError));



            } catch (IOException e) {
                e.printStackTrace();
            }


            //StringBuilder stringBuilder = new StringBuilder();






        } else {
//            Log.d(TAG, "processImage: not operational");
        }
    }
        String wordsall = "";
    private String googleVision(SparseArray<TextBlock> items){
        String words ="";


        for (int i = 0; i < items.size(); i++) {



            TextBlock textBlock = items.valueAt(i);

//            Log.e("components", getComponenets(textBlock));

//            Log.e(TAG, "Textblock = " + textBlock.getValue());
//                stringBuilder.append(textBlock.getValue());
//                stringBuilder.append("\n");
            words = words + "\n" + textBlock.getValue();
            wordsall = wordsall + words;
            textView.setText(wordsall);


        }
        return words;
    }



    private int getBlock(SparseArray<TextBlock> items){

        String words = googleVision(items);
//            words = words.concat(" wfwsvasrgwgwr   "+containsPercent(words));
        //words = containsPercent(words);
//        words.replaceAll("/n", "");
//        Log.e("Replace", "Length is equal to " + words.length() + " minus " + words.replaceAll("%","").length() + " equals "  + (words.length() - words.replaceAll("%","").length()));
        ArrayList<String> matcher = containsPercent(words);
        if (matcher.size() == 5){
//            Log.e(TAG, "matchersize =" + matcher.size());
            display(matcher);
        }

        return matcher.size();
    }

    private void display(ArrayList<String> matcher){

        TextView enerygy = findViewById(R.id.energyOutput);
        TextView fat = findViewById(R.id.fatOutput);
        TextView saturates = findViewById(R.id.saturatesOutput);
        TextView sugars = findViewById(R.id.sugarsOutput);
        TextView salts = findViewById(R.id.saltsOutput);


        enerygy.setText(matcher.get(0));
        fat.setText(matcher.get(1));
        saturates.setText(matcher.get(2));
        sugars.setText(matcher.get(3));
        salts.setText(matcher.get(4));
    }


    int errors;
    private ArrayList<String> splitImage(Bitmap bitmap, int rotation){
        SparseArray<TextBlock> items;
        errors = 0;
        ArrayList<Bitmap> bitmaps = new ArrayList<>(5);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        ArrayList<String> blocks = new ArrayList<>();
        ArrayList<String> text = new ArrayList<>(5);

        if (bitmap.getWidth() < bitmap.getHeight()) {
            bitmaps.add(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight() / 5));
            bitmaps.add(Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 5, bitmap.getWidth(), bitmap.getHeight() / 5));
            bitmaps.add(Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 5 * 2, bitmap.getWidth(), bitmap.getHeight() / 5));
            bitmaps.add(Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 5 * 3, bitmap.getWidth(), bitmap.getHeight() / 5));
            bitmaps.add(Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 5 * 4, bitmap.getWidth(), bitmap.getHeight() / 5));
        } else {

            bitmaps.add(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth()/5, bitmap.getHeight()));
            bitmaps.add(Bitmap.createBitmap(bitmap, bitmap.getWidth()/5, 0, bitmap.getWidth()/5, bitmap.getHeight()));
            bitmaps.add(Bitmap.createBitmap(bitmap, bitmap.getWidth()/5*2, 0, bitmap.getWidth()/5, bitmap.getHeight()));
            bitmaps.add(Bitmap.createBitmap(bitmap, bitmap.getWidth()/5*3, 0, bitmap.getWidth()/5, bitmap.getHeight()));
            bitmaps.add(Bitmap.createBitmap(bitmap, bitmap.getWidth()/5*4, 0, bitmap.getWidth()/5, bitmap.getHeight()));

        }
        ArrayList<String> percentsArray;
        //TODO  make it go through all bitmaps in same rotation first before rotating
        for (int i=0; i < bitmaps.size();i++){

            Frame frame = new Frame.Builder().setBitmap(bitmaps.get(i)).setRotation(rotation).build();
            items = textRecognizer.detect(frame);
            percentsArray = containsPercent(googleVision(items));
//            Log.e(TAG, "percentsArray "+percentsArray.toString());

            for (int k=0;k<percentsArray.size();k++){
//                Log.e(TAG, "percentsArray "+percentsArray.toString());
                text.add(k, percentsArray.get(k));
            }
            /*
//            text = containsPercent(googleVision(items));
            for (int j=0;j<text.size();j++){
                if (!text.get(j).contains("%")){
                    text.set(j, "0%");
                    errors++;

                }
            }*/
//            if (text.size() == 0){
//                text.add(0, "0%");
//                errors++;
//            }
//            Log.e("Errors", "Errors = " + errors + " Text size = " + text.size() + " Does contain percent? = " + text.get(0).contains("%"));

            blocks.add(text.get(0));
        }


//        Log.e("Blocks", "Block of text = " + blocks + " Errors = " + errors);

        return blocks;
    }


    private ArrayList<String> containsPercent(String block){
        ArrayList<String> array = new ArrayList<>();
        /*if (block.contains("%")){
            int index = block.indexOf("%");
            if (index >=2 && index != -1) {
                if (block.charAt(index - 2) == (char) ' ') {
                    output = "Percent = " + block.substring(index - 1, index);
                } else {
                    output = "Percent = " + block.substring(index - 2, index);
                }
            } else if (index ==1){

                output = "Percent = " + block.substring(index - 1, index);
            } else {
                output = "Percent = " + block;
            }
        }

        block = block.replaceAll("[^0-9kK%.]"," ");
        block = block.replaceAll("[ ]","");
        block = TextUtils.join(" ", array);
        for (String retval:block.split(" ")){
            array.add(retval);
        }

        for (int i=0;i<array.size()-1;i++){
            String percent = array.get(i);
            int len = percent.length();
            if (len >= 2){
                array.set(i,percent.substring(len-2));
            }
        }
        array.remove(array.size()-1);
        output = TextUtils.join(",", array);

        */

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
            matcher.add("NaN");
            errors++;
        }
//        output = matcher.toString();
//        Log.e("Output", "output = "+ output);
//        output = block;

        return matcher;
    }


    private String getComponenets(TextBlock textBlock){
        List<? extends com.google.android.gms.vision.text.Text> list = textBlock.getComponents();
        textBlock.getComponents();
        return list.toString();
    }

}
