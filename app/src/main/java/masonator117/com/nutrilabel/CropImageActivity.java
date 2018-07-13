package masonator117.com.nutrilabel;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CropImageActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
//        Log.e("Where am i", "Very start");


        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "output2.jpeg");
//        Log.d("File ", "File path = " + file.toString());
//        File directory = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
//        File[] files = directory.listFiles();
//        Log.d("Files", "Size: "+ files.length);
//        for (int i = 0; i < files.length; i++)
//        {
//            Log.d("Files", "FileName:" + files[i].getName() + ". Path = " + files[i].getPath());
//        }


//        Uri uri = Uri.fromFile(file);
        Uri uri = Uri.parse("file://" + file.getPath());
//
//        if (uri !=null){
//            Log.d("Uri", "uri = " + uri.toString());
//        }
        try {
            saveImages(uri);
//            Log.e("Where am i", "saved image");

        } catch (IOException e) {
            e.printStackTrace();
        } {

        }


//        ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(uri);





        CropImage.activity(uri)
                .start(this);
//        CropImage.activity(uri)
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setActivityTitle("My Crop")
//                .setCropShape(CropImageView.CropShape.RECTANGLE)
//                .setCropMenuCropButtonTitle("Done")
//                .setRequestedSize(400, 400)
//                .setCropMenuCropButtonIcon(R.drawable.ic_launcher)
//                .start(this);

//        Log.e("Where am i", "After crop image");

       // Bitmap bitmap = BitmapFactory.decodeFile(path);
//        Uri uri = Uri.parse("C:\\Users\\Euan\\Documents\\NutriLabel\\app\\src\\main\\res\\drawable\\test.png");
//        CropImageView cropImageView = (CropImageView) findViewById(R.id.cropImageView);
//        onSelectImageClick();
       //startCropImageActivity(uri);




    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e("Where am i", "Activity result");

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                try {
//                    Log.e("Where am i", "Result code okay");

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String outputFileName = "CroppedImage_" + timeStamp + ".png";
//                    Log.e("Output file name ", "Output file name " + outputFileName);


//                    saveCroppedImages(result.getUri());
                    Intent intent = new Intent(getApplicationContext(), dataProcessing.class);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
//                    Log.e("Fileout", "Fileout = " + file);


                    Bundle bundle = new Bundle();
//                    bundle.


                    intent.putExtra("cropped", result.getUri());
                    intent.putExtra("a", (String)outputFileName);
                    intent.putExtra("fileName", outputFileName);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(
                        this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }


//        try {
//            saveImageToExternalStorage(data.getData());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }



//    public void saveImages(Uri uriImage) throws IOException{
////        ContentResolver contentResolver = getContentResolver();
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String outputFileName = "Image_" + timeStamp + ".png";
//        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), outputFileName);
//
//        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
//        Log.e("Where am i", "Save images - got files");
//
////        View v1 = v.getRootView();
////        v1.setDrawingCacheEnabled(true);
////        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
////        v1.setDrawingCacheEnabled(false);
//
//        OutputStream fOut;
//        fileExists(file);
//        Log.e("Where am i", "File exists");
//
//        file.createNewFile();
//        Log.e("Where am i", "Create new file");
//
//        fOut = new FileOutputStream(file);
//        Log.e("Where am i", "fout");
//
//        // 100 means no compression, the lower you go, the stronger the compression cause I keep forgetting
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//        Log.e("Where am i", "Compress");
//
//        fOut.flush();
//        fOut.close();
//    }
//    public void fileExists(File file){
//        try {
//            if (file.exists()) {
//                file.delete();
//                if (file.exists()) {
//                    file.getCanonicalFile().delete();
//                    if (file.exists()) {
//                        getApplicationContext().deleteFile(file.getName());
//                    }
//                }
//            }
//        } catch (IOException e){
//
//        }
//    }

    public void saveImages(Uri uriImage) throws IOException{
//        ContentResolver contentResolver = getContentResolver();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String outputFileName = "Image_" + timeStamp + ".png";
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), outputFileName);

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
//        Log.e("Save images", "Just before background");
        BackgroundWorkParams params = new BackgroundWorkParams(file,bitmap);
        BackgroundWork backgroundWork = new BackgroundWork();
        backgroundWork.execute(params);
//        Log.e("Save images", "Just after background");

    }
    public void fileExists(File file){
        try {
            if (file.exists()) {
                file.delete();
                if (file.exists()) {
                    file.getCanonicalFile().delete();
                    if (file.exists()) {
                        getApplicationContext().deleteFile(file.getName());
                    }
                }
            }
        } catch (IOException e){

        }
    }

//    public void saveCroppedImages(Uri uriImage) throws IOException{
//        ContentResolver contentResolver = getContentResolver();
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String outputFileName = "CroppedImage_" + timeStamp + ".png";
//        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), outputFileName);
//        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
//
//
//
//        OutputStream fOut;
//        fileExists(file);
//        file.createNewFile();
//        fOut = new FileOutputStream(file);
//        // 100 means no compression, the lower you go, the stronger the compression
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//        fOut.flush();
//        fOut.close();
//    }

    public void saveCroppedImages(Uri uriImage) throws IOException{
        ContentResolver contentResolver = getContentResolver();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String outputFileName = "CroppedImage_" + timeStamp + ".png";
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), outputFileName);
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
        BackgroundWorkParams params = new BackgroundWorkParams(file,bitmap);
        BackgroundWork backgroundWork = new BackgroundWork();
        backgroundWork.execute(params);

    }

    public void saveImageToExternalStorage(Uri uriImage) throws IOException {
        saveCroppedImages(uriImage);
//        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "output4.png");
//        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
//        try
//        {
////            File dir = new File(fullPath);
////            if (!dir.exists()) {
////                dir.mkdirs();
////            }
//            OutputStream fOut = null;
////            File file = new File(fullPath, "image.png");
//            if(file.exists()){
//                file.delete();
//                if(file.exists()){
//                    file.getCanonicalFile().delete();
//                    if(file.exists()){
//                        getApplicationContext().deleteFile(file.getName());
//                    }
//                }
//            }
//            file.createNewFile();
//            fOut = new FileOutputStream(file);
//            // 100 means no compression, the lower you go, the stronger the compression
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//            fOut.flush();
//            fOut.close();
//        }
//        catch (Exception e)
//        {
//            Log.e("saveToExternalStorage()", e.getMessage());
//        }
    }

}
