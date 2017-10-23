package fpt.capstone.ats.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dovie on 10/18/2017.
 */

public class DBAdapter {
    private static final String TAG = DBAdapter.class.getSimpleName();
    private static final String DATABASE_NAME = "MyDB";
    private static final int DATABASE_VERSION = 1;
    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public DBAdapter(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    // lớp nội hỗ trợ, thừa kế SQLiteOpenHelper
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TransactionDetail.TABLE_NAME + "("
                    + TransactionDetail.TRANSACTION_ID + " TEXT PRIMARY KEY, "
                    + TransactionDetail.STATION_NAME + " TEXT NOT NULL, "
                    + TransactionDetail.STATION_ID + " INTEGER NOT NULL, "
                    + TransactionDetail.ZONE + " TEXT NOT NULL, "
                    + TransactionDetail.DATE_TIME + " TEXT NOT NULL, "
                    + TransactionDetail.PRICE + " REAL NOT NULL, "
                    + TransactionDetail.STATUS + " TEXT NOT NULL, "
                    + TransactionDetail.VEHICLE_TYPE + " TEXT NOT NULL, "
                    + TransactionDetail.TYPE + " TEXT NOT NULL, "
                    + TransactionDetail.LAST_MODIFIED + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion +
                    ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TransactionDetail.TABLE_NAME);
            onCreate(db);
        }
    } // end of DatabaseHelper

    // mở cơ sở dữ liệu cho phép ghi
    public DBAdapter open() {
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    // đóng cơ sở dữ liệu
    public void close() {
        databaseHelper.close();
    }

    // chèn một record đến cơ sở dữ liệu
    public long insertInfo(String transactionId, String stationName, int stationId, String zone, String dateTime,
                           double price, String status, String vehicleType, String type, String lastModified) {
        ContentValues cv = new ContentValues();
        cv.put(TransactionDetail.TRANSACTION_ID, transactionId);
        cv.put(TransactionDetail.STATION_NAME, stationName);
        cv.put(TransactionDetail.STATION_ID, stationId);
        cv.put(TransactionDetail.ZONE, zone);
        cv.put(TransactionDetail.DATE_TIME, dateTime);
        cv.put(TransactionDetail.PRICE, price);
        cv.put(TransactionDetail.STATUS, status);
        cv.put(TransactionDetail.VEHICLE_TYPE, vehicleType);
        cv.put(TransactionDetail.TYPE, type);
        cv.put(TransactionDetail.LAST_MODIFIED, lastModified);
        return database.insert(TransactionDetail.TABLE_NAME, null, cv);
    }

    // xóa một record chỉ định
    public boolean deleteInfo(String transactionId) {
        String[] whereArgs = new String[]{transactionId};
        return database.delete(TransactionDetail.TABLE_NAME, TransactionDetail.TRANSACTION_ID + "= ? ", whereArgs) > 0;
    }

    // xóa các records sau 30 ngày
    public boolean deleteInfoAfter30Days() {
        String[] whereArgs = new String[]{"date('now','-29 day')"};
        return database.delete(TransactionDetail.TABLE_NAME, TransactionDetail.LAST_MODIFIED + "<= ? ", whereArgs) > 0;
    }

    // xóa toàn bộ cơ sở dữ liệu
    public void deleteDatabase() {
        context.deleteDatabase(DATABASE_NAME);
    }

    // lấy tất cả các record
    public Cursor getAllInfo() {
        String[] columns = {TransactionDetail.TRANSACTION_ID, TransactionDetail.STATION_NAME, TransactionDetail.STATION_ID, TransactionDetail.ZONE,
                TransactionDetail.DATE_TIME, TransactionDetail.PRICE, TransactionDetail.STATUS, TransactionDetail.VEHICLE_TYPE, TransactionDetail.TYPE, TransactionDetail.LAST_MODIFIED};
        return database.query(TransactionDetail.TABLE_NAME, columns, null, null, null, null, null);
    }

    // lấy một record chỉ định
    public Cursor getInfo(String transactionId) {
        String[] columns = {TransactionDetail.TRANSACTION_ID, TransactionDetail.STATION_NAME, TransactionDetail.STATION_ID, TransactionDetail.ZONE,
                TransactionDetail.DATE_TIME, TransactionDetail.PRICE, TransactionDetail.STATUS, TransactionDetail.VEHICLE_TYPE, TransactionDetail.TYPE, TransactionDetail.LAST_MODIFIED};
        String[] selectionArgs = new String[]{transactionId};
        Cursor cursor = database.query(true, TransactionDetail.TABLE_NAME, columns,
                TransactionDetail.TRANSACTION_ID + "= ? ", selectionArgs, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    // cập nhật một record
    public boolean updateInfo(String transactionId, String stationName, int stationId, String zone, String dateTime,
                              double price, String status, String vehicleType, String type, String lastModified) {
        ContentValues cv = new ContentValues();
        cv.put(TransactionDetail.STATION_NAME, stationName);
        cv.put(TransactionDetail.STATION_ID, stationId);
        cv.put(TransactionDetail.ZONE, zone);
        cv.put(TransactionDetail.DATE_TIME, dateTime);
        cv.put(TransactionDetail.PRICE, price);
        cv.put(TransactionDetail.STATUS, status);
        cv.put(TransactionDetail.VEHICLE_TYPE, vehicleType);
        cv.put(TransactionDetail.TYPE, type);
        cv.put(TransactionDetail.LAST_MODIFIED, lastModified);
        String[] selectionArgs = new String[]{transactionId};
        return database.update(TransactionDetail.TABLE_NAME, cv, TransactionDetail.TRANSACTION_ID + "= ? ", selectionArgs) > 0;
    }
}
