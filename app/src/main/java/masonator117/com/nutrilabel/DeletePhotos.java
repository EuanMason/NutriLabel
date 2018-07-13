package masonator117.com.nutrilabel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class DeletePhotos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_photos);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        getFileList();



        try{
            buttonOnClick(files);

            getFiles(files);
        } catch (NullPointerException e){

        }



    }

    public void getFileList(){
        try {
            File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            files = directory.listFiles();
            for (int i=0;i<files.length;i++){
//                Log.e("File list", files[i].getName());

            }
        } catch (NullPointerException e){
            noImages();
        }

    }

    File[] files;


    int i=0;
//    String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
//    File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
File directory;


    private void getFiles(File[] files){
        updateImage();

//        Log.d("Files", "Path: " + directory.getPath());



//        Log.d("Files", "Size: "+ files.length);
//        for (int i = 0; i < files.length; i++)
//        {
//            Log.d("Files", "FileName:" + files[i].getName());
//        }
    }

    public void updateImage(){
        if (noImages()){
            return;
        }
        Uri uri = Uri.parse("file://" + files[i].getPath());
//        Log.e("Files", "FileName:" + files[i].getName());

        ((ImageView) findViewById(R.id.imageView)).setImageURI(uri);
    }

    public boolean noImages(){
        if (files.length ==0){
            Toast.makeText(this, "No images left", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return true;
        }
        return false;
    }

    public boolean deleteImage(){
        noImages();

        Uri uri = Uri.parse("file://" + files[i].getPath());
        if (files[i].delete()){
            if (files.length > i && i >0) {
                i--;
                updateImage();
            }
            return true;
        } else {
            return false;
        }

    }

    public boolean checkImageNumber(){
        int filesize = files.length;
        if (i < filesize-1) {
            return true;
        }
        return false;
    }

    public void buttonOnClick(File[] files){
        Button backButton = findViewById(R.id.backButton);
        Button next = (Button)findViewById(R.id.next);
        Button prev = (Button)findViewById(R.id.previous);
        Button delete = (Button)findViewById(R.id.delete);

        final int filesSize = files.length;

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if (checkImageNumber()){
                    i++;
                    updateImage();
                }
            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if (i>0){
                    i--;
                    updateImage();
                }
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
//                Log.e("Delete image", "Trying to delete image");
            if (deleteImage()){
                Toast.makeText(getApplicationContext(), "Image deleted", Toast.LENGTH_SHORT).show();

//                Log.e("Delete image", "Deleted image");

            } else {
                Toast.makeText(getApplicationContext(), "Image could not be deleted", Toast.LENGTH_SHORT).show();
//                Log.e("Delete image", "Image could not be deleted");


            }
            getFileList();
            }
        });
    }

}
