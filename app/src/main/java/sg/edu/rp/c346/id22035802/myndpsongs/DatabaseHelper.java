package sg.edu.rp.c346.id22035802.myndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "song_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SONGS = "songs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGER = "singer";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_RATING = "rating";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_SONGS = "CREATE TABLE " + TABLE_SONGS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_SINGER + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_RATING + " INTEGER)";
        db.execSQL(CREATE_TABLE_SONGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);
    }

    public boolean insertSong(ToDoItem song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, song.getTitle());
        values.put(COLUMN_SINGER, song.getSinger());
        values.put(COLUMN_DATE, String.valueOf(song.getDate().getTimeInMillis()));
        values.put(COLUMN_RATING, song.getRating());
        long result = db.insert(TABLE_SONGS, null, values);
        db.close();
        return result != -1;
    }

    public ArrayList<ToDoItem> getAllSongs() {
        ArrayList<ToDoItem> songsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SONGS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String singer = cursor.getString(cursor.getColumnIndex(COLUMN_SINGER));
                long dateInMillis = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE));
                int rating = cursor.getInt(cursor.getColumnIndex(COLUMN_RATING));

                Calendar date = Calendar.getInstance();
                date.setTimeInMillis(dateInMillis);

                ToDoItem song = new ToDoItem(id, title, singer, date, rating);
                songsList.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songsList;
    }

    public boolean updateSong(ToDoItem song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, song.getTitle());
        values.put(COLUMN_SINGER, song.getSinger());
        values.put(COLUMN_DATE, String.valueOf(song.getDate().getTimeInMillis()));
        values.put(COLUMN_RATING, song.getRating());

        int rowsAffected = db.update(TABLE_SONGS, values,
                COLUMN_ID + " = ?", new String[]{String.valueOf(song.getId())});

        db.close();
        return rowsAffected > 0;
    }

    public boolean deleteSong(int songId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_SONGS, COLUMN_ID + " = ?", new String[]{String.valueOf(songId)});
        db.close();
        return rowsAffected > 0;
    }
}
