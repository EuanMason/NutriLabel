package masonator117.com.nutrilabel;

import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePicture extends AppCompatActivity {


    static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        Intent captureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);

        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "output2.jpeg");



        //Uri capturedImageUri = Uri.parse("file://"+file.getPath());

        Uri photoURI = FileProvider.getUriForFile(TakePicture.this,
                BuildConfig.APPLICATION_ID + ".fileprovider",
                file);

        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        // we will handle the returned data in onActivityResult
        startActivityForResult(captureIntent, REQUEST_IMAGE_CAPTURE);




//        buttonOnClick();
    }

    public void buttonOnClick(){
        Button button= (Button)findViewById(R.id.capture_btn);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                try {

                    // use standard intent to capture an image
                    Intent captureIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);

                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            "output2.jpeg");



                    //Uri capturedImageUri = Uri.parse("file://"+file.getPath());

                    Uri photoURI = FileProvider.getUriForFile(TakePicture.this,
                            BuildConfig.APPLICATION_ID + ".fileprovider",
                            file);

                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                    // we will handle the returned data in onActivityResult
                    startActivityForResult(captureIntent, REQUEST_IMAGE_CAPTURE);


                } catch (ActivityNotFoundException anfe) {
                    Toast toast = Toast.makeText(TakePicture.this, "This device doesn't support the crop action!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        findViewById(R.id.capture_btn).setVisibility(View.GONE);
        startActivity(new Intent(getApplicationContext(), CropImageActivity.class));
//        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//        ImageView imageView = (ImageView)findViewById(R.id.ImageView);
//        Uri uriParse = Uri.parse("C:\\Users\\Euan\\Documents\\NutriLabel\\app\\src\\main\\res\\drawable\\test.png");
//        imageView.setImageURI(uriParse);
      //  imageView.setImageBitmap(bitmap);




//
//        Uri photoURI = FileProvider.getUriForFile(this,
//                "com.example.android.fileprovider",
//                );





 /*   Log.d("Filepath ", "file path = " + file.toString());

        try {
            InputStream in = getContentResolver().openInputStream(data.getData());
//    data.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
            OutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
        } catch (IOException e){
            Log.e("Output", "Can't output image");
        }*/

//        cropImage();
       // storeImage(data, bitmap);
    }



    /*private void storeImage(Intent dataintent, Bitmap bitmap){



        String imageFileName = "output";
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                imageFileName);

        OutputStream os = null;
        byte[] data = new byte[bitmap.get];

        try {
            os = new FileOutputStream(file);
            os.write(data);
            Log.e(TAG, "Outputting file " + file);
            os.close();
        } catch (IOException e) {
            Log.w(TAG, "Cannot write to " + file, e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }

*/



}
