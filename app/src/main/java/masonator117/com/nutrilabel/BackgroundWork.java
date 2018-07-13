package masonator117.com.nutrilabel;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Euan on 10/07/2018.
 */





public class BackgroundWork extends AsyncTask<BackgroundWorkParams, Void, Void> {


//    @Override
//    protected void onPreExecute() {
//        /*
//         *    do things before doInBackground() code runs
//         *    such as preparing and showing a Dialog or ProgressBar
//        */
//    }

//    @Override
//    protected void onProgressUpdate(Uri... uri) {
//        /*
//         *    updating data
//         *    such a Dialog or ProgressBar
//        */
//
//
//
//    }

    @Override
    protected Void doInBackground(BackgroundWorkParams... params) {
        File file = params[0].output;
        Bitmap bitmap = params[0].bitmap;




        OutputStream fOut;
        try {
            if (file != null) {
                if (file.exists()) {
                    file.delete();
                    if (file.exists()) {
                        file.getCanonicalFile().delete();

                    }
                }
            }
            file.createNewFile();
            fOut = new FileOutputStream(file);
            // 100 means no compression, the lower you go, the stronger the compression
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
//            Log.e("Saved", "Saved to file");

        } catch (IOException e){

        }



        return null;
    }
//
//    @Override
//    protected void onPostExecute(Void result) {
//        /*
//         *    do something with data here
//         *    display it or send to mainactivity
//         *    close any dialogs/ProgressBars/etc...
//        */
//    }


}
