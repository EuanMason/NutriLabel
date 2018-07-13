package masonator117.com.nutrilabel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOnClick();

    }

    public void buttonOnClick(){
        Button button= (Button)findViewById(R.id.toPicture);
//        Button crop = (Button)findViewById(R.id.cropImage);
        Button deleteImage = (Button)findViewById(R.id.deleteImage);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), TakePicture.class));
            }
        });


//        crop.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v){
//                startActivity(new Intent(getApplicationContext(), dataProcessing.class));
//            }
//        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), DeletePhotos.class));
            }
        });
    }


}
