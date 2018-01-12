package afeka.battleship;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import afeka.battleship.Model.Score;

public class Database extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database.db";

    public static final SimpleDateFormat sdt = new SimpleDateFormat("dd-MM-yyyy");


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HIGHSCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_HIGHSCORE);
        db.execSQL(SQL_CREATE_HIGHSCORE);
    }

    private static final String SQL_CREATE_HIGHSCORE =
            "CREATE TABLE " + FeedScore.TABLE_NAME + " (" +
                    FeedScore._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedScore.COLUMN_NAME_NAME + " TEXT," +
                    FeedScore.COLUMN_NAME_DIFFICULT + " INTEGER," +
                    FeedScore.COLUMN_NAME_SCORE + " INTEGER," +
                    FeedScore.COLUMN_NAME_Date + " DATE," +
                    FeedScore.COLUMN_NAME_LATITUDE + " REAL," +
                    FeedScore.COLUMN_NAME_LONGITUDE + " REAL" +
                     " );";

    private static final String SQL_DELETE_HIGHSCORE =
            "DROP TABLE IF EXISTS " + FeedScore.TABLE_NAME;

    public void addScores (Score score){
        ContentValues values = new ContentValues();
        values.put(FeedScore.COLUMN_NAME_NAME, score.getName());
        values.put(FeedScore.COLUMN_NAME_DIFFICULT,score.getDifficult());
        values.put(FeedScore.COLUMN_NAME_SCORE,score.getScore());
        String date = sdt.format(score.getDate());
        values.put(FeedScore.COLUMN_NAME_Date,date);
        values.put(FeedScore.COLUMN_NAME_LATITUDE,score.getLocation().getLatitude());
        values.put(FeedScore.COLUMN_NAME_LONGITUDE,score.getLocation().getLongitude());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(FeedScore.TABLE_NAME,null,values);
        trimToTopTen(db,score.getDifficult());
        db.close();
    }

    private void trimToTopTen (SQLiteDatabase db,int difficult){
        final String DELETE_LAST = "DELETE FROM " + FeedScore.TABLE_NAME +
                " WHERE " + FeedScore._ID + " NOT IN ( SELECT" + FeedScore._ID +
                " FROM " + FeedScore.TABLE_NAME +
                " WHERE " + FeedScore.COLUMN_NAME_DIFFICULT + "=" + difficult +
                " ORDER BY " + FeedScore.COLUMN_NAME_SCORE + " DESC" +
                " LIMIT 10)";
        db.execSQL(DELETE_LAST);
    }

    public ArrayList<Score> getScoreList (int difficultList){
        ArrayList<Score> list = new ArrayList<>();
        int i = 0;
        
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {FeedScore.COLUMN_NAME_NAME,FeedScore.COLUMN_NAME_SCORE,
                FeedScore.COLUMN_NAME_DIFFICULT,FeedScore.COLUMN_NAME_Date,
                FeedScore.COLUMN_NAME_LATITUDE,FeedScore.COLUMN_NAME_LONGITUDE};
        String selection = FeedScore.COLUMN_NAME_DIFFICULT + " = ?";
        String[] selectionArgs = { difficultList+"" };
        String sortOrder = FeedScore.COLUMN_NAME_SCORE + " DESC";
        Cursor cursor = db.query(FeedScore.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FeedScore.COLUMN_NAME_NAME));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(FeedScore.COLUMN_NAME_SCORE));
            int difficult = cursor.getInt(cursor.getColumnIndexOrThrow(FeedScore.COLUMN_NAME_DIFFICULT));
            Date date=null;
            try {
                date = sdt.parse(cursor.getString(cursor.getColumnIndexOrThrow(FeedScore.COLUMN_NAME_Date)));
            } catch (ParseException e) {
                date = Calendar.getInstance().getTime();
                e.printStackTrace();
            }
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(FeedScore.COLUMN_NAME_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(FeedScore.COLUMN_NAME_LONGITUDE));
            Location l = new Location(name);
            l.setLatitude(latitude);
            l.setLongitude(longitude);
            list.add(new Score(name,difficult,score,date,l));
        }

        return list;
    }


    public class FeedScore implements BaseColumns {
        private static final String TABLE_NAME = "highScore";
        private static final String COLUMN_NAME_NAME = "name";
        private static final String COLUMN_NAME_SCORE = "score";
        private static final String COLUMN_NAME_DIFFICULT = "difficult";
        private static final String COLUMN_NAME_Date = "date";
        private static final String COLUMN_NAME_LATITUDE = "latitude";
        private static final String COLUMN_NAME_LONGITUDE = "longitude";
    }
}