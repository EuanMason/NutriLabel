package masonator117.com.nutrilabel;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by Euan on 10/07/2018.
 */

public class BackgroundWorkParams {

    File output;
    Bitmap bitmap;


    BackgroundWorkParams(File output, Bitmap bitmap) {
        // list all the parameters like in normal class define
        this.output = output;
        this.bitmap = bitmap;

    }

}
