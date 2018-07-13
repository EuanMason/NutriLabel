package masonator117.com.nutrilabel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Euan on 13/07/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "nutritionValues";

    // Contacts table name
    private static final String TABLE_NVALUES = "nvalues";

    // Shops Table Columns names

    private static final String DATE = "date";
    private static final String ENERGY = "energy";
    private static final String FAT = "fat";
    private static final String SATURATES = "saturates";
    private static final String SUGARS = "sugars";
    private static final String SALTS = "salts";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NVALUES + "("
                + DATABASE_NAME + " INTEGER PRIMARY KEY," + DATE + " TEXT,"
                + ENERGY + " TEXT" + FAT + " TEXT" +SATURATES+ " TEXT"
                + SUGARS+ " TEXT" + SALTS+ " TEXT" +")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NVALUES);
        // Creating tables again
        onCreate(db);
    }

    // Adding new shop
    public void addNutritionalLabel(NutritionLabel label) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DATE, label.getDate()); // Shop Name
        values.put(ENERGY, label.getEnergy()); // Shop Name
        values.put(FAT, label.getFat()); // Shop Name
        values.put(SATURATES, label.getSaturates()); // Shop Name
        values.put(SUGARS, label.getSugars()); // Shop Name
        values.put(SALTS, label.getSalts()); // Shop Name

        // Inserting Row
        db.insert(TABLE_NVALUES, null, values);
        db.close(); // Closing database connection
    }

    // Getting one shop
    public NutritionLabel getNutritionalLabel(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NVALUES, new String[]{DATE,
                        ENERGY, FAT, SATURATES,SUGARS,SALTS}, DATE + "=?",
                new String[]{String.valueOf(date)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        NutritionLabel label = new NutritionLabel(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5));
        // return nutlabel
        return label;
    }

    // Getting All nutlabels
    public List<NutritionLabel> getAllNutritionalLabels() {
        List<NutritionLabel> nutritionLabelList = new ArrayList<NutritionLabel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NVALUES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NutritionLabel nutlabel = new NutritionLabel();
                nutlabel.setDate(cursor.getString(0));
                nutlabel.setEnergy(cursor.getString(1));
                nutlabel.setFat(cursor.getString(2));
                nutlabel.setSaturates(cursor.getString(3));
                nutlabel.setSugars(cursor.getString(4));
                nutlabel.setSalts(cursor.getString(5));
                // Adding contact to list
                nutritionLabelList.add(nutlabel);
            } while (cursor.moveToNext());
        }

        return nutritionLabelList;
    }

    public int getLabelCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NVALUES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


//    public int updateShop(Shop shop) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, shop.getName());
//        values.put(KEY_SH_ADDR, shop.getAddress());
//
//        // updating row
//        return db.update(TABLE_SHOPS, values, KEY_ID + " = ?",
//                new String[]{String.valueOf(shop.getId())});
//    }

    // Deleting a shop
//    public void deleteShop(Shop shop) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_SHOPS, KEY_ID + " = ?",
//                new String[] { String.valueOf(shop.getId()) });
//        db.close();
//    }

}