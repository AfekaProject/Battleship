package afeka.battleship;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class Database extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database.db";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HIGHSCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static final String SQL_CREATE_HIGHSCORE =
            "CREATE TABLE " + FeedReaderContract.FeedScore.TABLE_NAME + " (" +
                    FeedReaderContract.FeedScore._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedScore.COLUMN_NAME_NAME + " TEXT," +
                    FeedReaderContract.FeedScore.COLUMN_NAME_DIFFICULT + " INTEGER," +
                    FeedReaderContract.FeedScore.COLUMN_NAME_SCORE + " INTEGER," +
                    FeedReaderContract.FeedScore.COLUMN_NAME_Date + " DATE," +
                    FeedReaderContract.FeedScore.COLUMN_NAME_LATITUDE + " REAL," +
                    FeedReaderContract.FeedScore.COLUMN_NAME_LONGITUDE + "REAL," +
                     " );";

    private static final String SQL_DELETE_HIGHSCORE =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedScore.TABLE_NAME;

    public final class FeedReaderContract {

        private FeedReaderContract (){
        }

        public class FeedScore implements BaseColumns {
            public static final String TABLE_NAME = "highScore";
            public static final String COLUMN_NAME_NAME = "name";
            public static final String COLUMN_NAME_SCORE = "score";
            public static final String COLUMN_NAME_DIFFICULT = "difficult";
            public static final String COLUMN_NAME_Date = "date";
            public static final String COLUMN_NAME_LATITUDE = "latitude";
            public static final String COLUMN_NAME_LONGITUDE = "longitude";
        }




    }



}
